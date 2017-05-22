package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Class which starts the program and draws the scene using parallelization.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class RayCasterParallel {
	
	/**
	 * Main method which starts the program.
	 * Takes no arguments.
	 * 
	 * @param args - none
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0),
				new Point3D(0, 0, 0), new Point3D(0, 0, 10), 20, 20);
	}
	
	/**
	 * Class representing a single job which calculates the pixel color depending on the given
	 * light sources and objects in the scene.
	 * 
	 * @author Goran Brlas
	 * @version 1.0
	 *
	 */
	public static class Posao extends RecursiveAction {
 
		
		private static final long serialVersionUID = 1L;
		Point3D eye;
		Point3D view;
		Point3D viewUp;
		double horizontal;
		double vertical;
		int width;
		int height;
		short[] red;
		short[] green;
		short[] blue;
		int yMin;
		int yMax;
		
		/**
		 * Job constructor.
		 * 
		 * @param eye 3DPoint representing observer's eye.
		 * @param view Position that is observed.
		 * @param viewUp Specification of view-up vector which is used to determine y-axis for screen.
		 * @param horizontal Horizontal width of observed space.
		 * @param vertical Vertical height of observed space.
		 * @param width Number of pixels per screen row.
		 * @param height Number of pixels per screen column.
		 * @param red Array representing red pixels.
		 * @param green Array representing red pixels.
		 * @param blue Array representing red pixels.
		 * @param yMin Y- line from which the field is updated (inclusive).
		 * @param yMax Y- line to which the field is updated (inclusive).
		 */
		public Posao (Point3D eye, Point3D view, Point3D viewUp,
				double horizontal, double vertical, int width, int height,
				short[] red, short[] green, short[] blue, int yMin, int yMax) {
			super();
			this.eye = eye;
			this.view = view;
			this.viewUp = viewUp;
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.width = width;
			this.height = height;
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.yMin = yMin;
			this.yMax = yMax;
		}
 
		/**
		 * Method which creates parallel jobs.
		 */
		@Override
		protected void compute() {
			if (yMax - yMin + 1 < 32) {
				findColors();
				return;
			}
 
			invokeAll(new Posao(eye, view, viewUp, horizontal, vertical,
					width, height, red, green, blue, yMin, yMin + (yMax - yMin)
							/ 2), new Posao(eye, view, viewUp,
					horizontal, vertical, width, height, red, green, blue, yMin
							+ (yMax - yMin) / 2, yMax));
		}
 
		/**
		 * Računa boje koje se trebaju prikazati na svakom pikselu unutar
		 * zadanog posla
		 */
		private void findColors() {
 
			int offset = yMin * width;
 
			Point3D OG = view.sub(eye);
 
			Point3D yAxis = viewUp.sub(
					OG.scalarMultiply(OG.scalarProduct(viewUp))).normalize();
			Point3D xAxis = OG.vectorProduct(yAxis).normalize();
 
			Point3D screenCorner = view.sub(
					xAxis.scalarMultiply(horizontal / 2)).add(
					yAxis.scalarMultiply(vertical / 2));
			
			@SuppressWarnings("unused")
			Point3D zAxis = xAxis.vectorProduct(yAxis).negate().normalize();
 
			Scene scene = RayTracerViewer.createPredefinedScene();
 
			short[] rgb = new short[3];
 
			for (int y = yMin; y < yMax; y++) {
				for (int x = 0; x < width; x++) {
					Point3D screenPoint = screenCorner.add(
							xAxis.scalarMultiply(x * horizontal / (width - 1)))
							.sub(yAxis.scalarMultiply(y * vertical
									/ (height - 1)));
					
					Ray ray = Ray.fromPoints(eye, screenPoint);
					
					tracer(scene, ray, rgb);
					
					red[offset] = rgb[0] > 255 ? 255 : rgb[0];
					green[offset] = rgb[1] > 255 ? 255 : rgb[1];
					blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
					
					offset++;
				}
			}
 
		}
		
		/**
		 * Method which calculates intersections and determines their color.
		 * 
		 * @param scene Scene containing graphical objects and light sources.
		 * @param intersection Ray-object intersection.
		 * @param rgb Array holding color values.
		 */
		private void tracer(Scene scene, Ray ray, short[] rgb) {
			rgb[0] = 0;
			rgb[1] = 0;
			rgb[2] = 0;
			
			RayIntersection closest = null;
			
			for (GraphicalObject obj : scene.getObjects()) {
				RayIntersection intersection = obj
						.findClosestRayIntersection(ray);
				
				if (intersection == null) {
					continue;
				}
 
				if (closest == null
						|| intersection.getDistance() < closest.getDistance()) {
					closest = intersection;
				}
			}
 
			if (closest == null) {
				return;
			}
			
			determineColorFor(scene, closest, rgb);
		}
		
		/**
		 * Determines the color for the provided intersection.
		 * 
		 * @param scene Scene containing graphical objects and light sources.
		 * @param intersection Ray-object intersection.
		 * @param rgb Array holding color values.
		 */
		private void determineColorFor(Scene scene, RayIntersection intersection,
				short[] rgb) {
			rgb[0] = 15;
			rgb[1] = 15;
			rgb[2] = 15;
 
			for (LightSource light : scene.getLights()) {
				Ray zraka = Ray.fromPoints(light.getPoint(), intersection.getPoint());
 
				RayIntersection closest = null;
				for (GraphicalObject object : scene.getObjects()) {
					RayIntersection intersection2 = object
							.findClosestRayIntersection(zraka);
					
					if (intersection2 == null) {
						continue;
					}
 
					if (closest == null
							|| intersection2.getDistance() < closest
									.getDistance()) {
						closest = intersection2;
					}
						
				}
 
				if (closest != null
						&& closest.getDistance() < (light.getPoint().sub(intersection
								.getPoint())).norm()
						&& Math.abs(closest.getDistance()
								- (light.getPoint().sub(intersection.getPoint())).norm()) > 1e-12) {
					continue;
				} else {
					Point3D n = intersection.getNormal();
					Point3D l = light.getPoint().sub(intersection.getPoint()).normalize();
 
					Point3D r = n.scalarMultiply(2)
							.scalarMultiply(n.scalarProduct(l) / n.norm())
							.sub(l).normalize();
 
					Point3D v = eye.sub(intersection.getPoint()).normalize();
 
					double rv = r.scalarProduct(v);
					double ln = l.scalarProduct(n);
					
					rgb[0] += light.getR() * intersection.getKdr() * ln;
					rgb[1] += light.getG() * intersection.getKdg() * ln;
					rgb[2] += light.getB() * intersection.getKdb() * ln;
					
					if (rv > 0) {
						rgb[0] += light.getR() * intersection.getKrr()
								* Math.pow(rv, intersection.getKrn());
						rgb[1] += light.getG() * intersection.getKrg()
								* Math.pow(rv, intersection.getKrn());
						rgb[2] += light.getB() * intersection.getKrb()
								* Math.pow(rv, intersection.getKrn());
					}
				}
			}
		}
	}
	
	/**
	 * Method which returns a new RayTracerProducer.
	 * 
	 * @return New RayTracerProducer.
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
 
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp,
					double horizontal, double vertical, int width, int height,
					long requestNo, IRayTracerResultObserver observer) {
				System.out.println("Započinjem izračune...");
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];
 
				ForkJoinPool pool = new ForkJoinPool();
 
				pool.invoke(new Posao(eye, view, viewUp, horizontal,
						vertical, width, height, red, green, blue, 0, height));
 
				pool.shutdown();
 
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
 
		};
	}
}
