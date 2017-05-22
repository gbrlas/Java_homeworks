package hr.fer.zemris.java.raytracer.model;

/**
 * Class which represents a sphere and provides a method for finding the closest ray intersection.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class Sphere extends GraphicalObject {
	/**
	 * Sphere center.
	 */
	Point3D center;
	/**
	 * Sphere radius.
	 */
	double radius;
	/**
	 * Coefficient for diffuse component for red color.
	 */
	double kdr;
	/**
	 * Coefficient for diffuse component for green color.
	 */
	double kdg;
	/**
	 * Coefficient for diffuse component for blue color.
	 */
	double kdb;
	/**
	 * Coefficient for reflective component for red color.
	 */
	double krr;
	/**
	 * Coefficient for reflective component for green color.
	 */
	double krg;
	/**
	 * Coefficient for reflective component for blue color.
	 */
	double krb;
	/**
	 * Coefficient n for reflective component.
	 */
	double krn;
	
	/**
	 * Constructor which sets the class values to the provided ones.
	 * 
	 * @param center Sphere center.
	 * @param radius Sphere radius.
	 * @param kdr Coefficient for diffuse component for red color.
	 * @param kdg Coefficient for diffuse component for green color.
	 * @param kdb Coefficient for diffuse component for blue color.
	 * @param krr Coefficient for reflective component for red color.
	 * @param krg Coefficient for reflective component for green color.
	 * @param krb Coefficient for reflective component for blue color.
	 * @param krn Coefficient n for reflective component.
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg,
			double kdb, double krr, double krg, double krb, double krn) {
		super();
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}
	
	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) throws IllegalArgumentException {
		if (ray == null) {
			throw new IllegalArgumentException("Passed argument cannot be null.");
		}
		
		Point3D l = ray.direction;
		Point3D o = ray.start;
		
		double temp = Math.sqrt(Math.pow(l.scalarProduct(o.sub(center)), 2) - 
				o.sub(center).scalarProduct(o.sub(center)) + radius * radius);
		double result;
		
		if (Double.isNaN(temp)) {
			return null;
		} else {
			double result1 = -l.scalarProduct(o.sub(center)) + temp;
			double result2 = -l.scalarProduct(o.sub(center)) - temp;
			
			if (result1 < 0) {
				return null;
			} else if (result1 > 0 && result2 < 0) {
				result = result1;
				Point3D intersectionPoint = o.add(l.scalarMultiply(result));
				return new SphereRayIntersection(intersectionPoint, result, false);				
			} else {
				result = result1 < result2 ? result1 : result2;
			}
		}
		
		Point3D intersectionPoint = o.add(l.scalarMultiply(result));
		
		return new SphereRayIntersection(intersectionPoint, result, true);
	}
	
	/**
	 * Class which represents the intersection of the ray and a sphere.
	 * 
	 * @author Goran Brlas
	 * @version 1.0
	 *
	 */
	public class SphereRayIntersection extends RayIntersection {
		
		/**
		 * Constructor which constructs the intersection according to the provided values.
		 * 
		 * @param point Intersection point.
		 * @param distance Distance from the ray starting point.
		 * @param outer Boolean representing whether the ray is entering the object or leaving it.
		 */
		public SphereRayIntersection(Point3D point, double distance, boolean outer) {
			super(point, distance, outer);
		}
			
		@Override
		public double getKdb() {
			return kdb;
		}
		
		@Override
		public double getKdg() {
			return kdg;
		}
		
		@Override
		public double getKdr() {
			return kdr;
		}
		
		@Override
		public double getKrb() {
			return krb;
		}
		
		@Override
		public double getKrg() {
			return krg;
		}
		
		@Override
		public double getKrn() {
			return krn;
		}
		
		@Override
		public double getKrr() {
			return krr;
		}
		
		@Override
		public Point3D getNormal() {
			Point3D point = this.getPoint();
			Point3D normal = point.sub(center).normalize();
			return normal;
		}
	}
}
