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

/**
 * Class which starts the program and draws the scene sequentially.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class RayCaster {
	
	/**
	 * Main method which starts the program.
	 * Takes no arguments.
	 * 
	 * @param args - none.
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0),
				new Point3D(0, 0, 0), new Point3D(0, 0, 10), 20, 20);
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
				short[] red = new short[width*height];
				short[] green = new short[width*height];
				short[] blue = new short[width*height];
				
				Point3D OG = view.sub(eye);
				
				Point3D yAxis = viewUp.sub(
						OG.scalarMultiply(OG.scalarProduct(viewUp)))
						.normalize();
				Point3D xAxis = OG.vectorProduct(yAxis).normalize();
				@SuppressWarnings("unused")
				Point3D zAxis = xAxis.vectorProduct(yAxis).negate().normalize();
				
				Point3D screenCorner = view.sub(
						xAxis.scalarMultiply(horizontal / 2)).add(
						yAxis.scalarMultiply(vertical / 2));
				
				Scene scene = RayTracerViewer.createPredefinedScene();
				
				short[] rgb = new short[3];
				int offset = 0;
				
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						Point3D screenPoint = screenCorner.add(xAxis
								.scalarMultiply((double) x * horizontal / (double) (width - 1)))
								.sub(yAxis.scalarMultiply((double) y * vertical
										/ (double) (height - 1)));
						
						Ray ray = Ray.fromPoints(eye, screenPoint);
						
						tracer(scene, ray, rgb, eye);
						
						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						
						offset++;
					}
				}
				
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
		};
	}
	
	/**
	 * Method which calculates intersections and determines their color.
	 * 
	 * @param scene Scene containing graphical objects and light sources.
	 * @param intersection Ray-object intersection.
	 * @param rgb Array holding color values.
	 * @param eye 3DPoint representing observer's eye.
	 */
	private static void tracer (Scene scene, Ray ray, short[] rgb, Point3D eye) {
		rgb[0] = 0;
		rgb[1] = 0;
		rgb[2] = 0;
		
		RayIntersection closest = null;
		
		for (GraphicalObject object : scene.getObjects()) {
			RayIntersection intersection = object.findClosestRayIntersection(ray);
			
			if (intersection == null) {
				continue;
			}
			
			if (closest == null || intersection.getDistance() < closest.getDistance()) {
				closest = intersection;
			}
		}
		
		if (closest == null) {
			return;
		}
		
		determineColorFor(scene, closest, rgb, eye);
	}
	
	/**
	 * Determines the color for the provided intersection.
	 * 
	 * @param scene Scene containing graphical objects and light sources.
	 * @param intersection Ray-object intersection.
	 * @param rgb Array holding color values.
	 * @param eye 3DPoint representing observer's eye.
	 */
	private static void determineColorFor(Scene scene, RayIntersection intersection,
			short[] rgb, Point3D eye) {
		rgb[0] = rgb[1] = rgb[2] = 15;
		
		for (LightSource light : scene.getLights()) {
			Ray zraka = Ray.fromPoints(light.getPoint(), intersection.getPoint());
			
			RayIntersection closest = null;
			
			for (GraphicalObject object : scene.getObjects()) {
				RayIntersection interection2 = object.findClosestRayIntersection(zraka);
				
				if (interection2 == null) {
					continue;
				}
				
				if (closest == null || interection2.getDistance() < closest.getDistance()) {
					closest = interection2;
				}
			}
			
			//if the light is obscured by a closer object
			if (closest != null
					&& closest.getDistance() - 0.01 < (light.getPoint()
							.sub(intersection.getPoint())).norm() 
					&& Math.abs(closest.getDistance()
							- (light.getPoint().sub(intersection.getPoint())).norm()) > 1e-12) {
				continue;
			} else {
				Point3D n = intersection.getNormal().normalize();
				Point3D l = light.getPoint().sub(intersection.getPoint())
						.normalize();
				Point3D r = n.scalarMultiply(2)
						.scalarMultiply(n.scalarProduct(l) / n.norm()).sub(l)
						.normalize();
				
				Point3D v = eye.sub(intersection.getPoint()).normalize();
				
				double rv = r.scalarProduct(v);
				double ln = l.scalarProduct(n);
				
				rgb[0] += (short) (light.getR() * intersection.getKdr() * ln);
				rgb[1] += (short) (light.getG() * intersection.getKdg() * ln);
				rgb[2] += (short) (light.getB() * intersection.getKdb() * ln);

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
