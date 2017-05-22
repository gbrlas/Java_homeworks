package hr.fer.zemris.java.raytracer;


import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Sphere;

import org.junit.Assert;
import org.junit.Test;

/**
 * Class which jUnit tests the intersection method in Sphere class.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class IntersectionTest {
	private static final double DELTA = 1e-9;
	
	@Test (expected = IllegalArgumentException.class)
	public void IntersectionNullArgument () {
		Sphere sphere = new Sphere(new Point3D(-2,-2,2), 5, 1, 1, 1, 0.5, 0.5, 0.5, 10);
		sphere.findClosestRayIntersection(null);
	}
	
	@Test
	public void IntersectionV1() {
		Sphere sphere = new Sphere(new Point3D(-2,-2,2), 5, 1, 1, 1, 0.5, 0.5, 0.5, 10);
		
		Ray ray = Ray.fromPoints(new Point3D(0, 0, 0), new Point3D(-2,-2,2));
		
		RayIntersection intersection = sphere.findClosestRayIntersection(ray);
		Assert.assertEquals(intersection == null, false);
	}
	
	@Test
	public void IntersectionV2() {
		Sphere sphere = new Sphere(new Point3D(-2,-2,2), 5, 1, 1, 1, 0.5, 0.5, 0.5, 10);
		
		Ray ray = Ray.fromPoints(new Point3D(400, 200, 100), new Point3D(-1000,1000,500));
		
		RayIntersection intersection = sphere.findClosestRayIntersection(ray);
		Assert.assertEquals(intersection == null, true);
	}
	
	@Test
	public void IntersectionV3() {
		Sphere sphere = new Sphere(new Point3D(-2,-2,2), 5, 1, 1, 1, 0.5, 0.5, 0.5, 10);
		
		Ray ray = Ray.fromPoints(new Point3D(-2,-2,2), new Point3D(-1000,1000,500));
		
		RayIntersection intersection = sphere.findClosestRayIntersection(ray);
		Assert.assertEquals(intersection == null, false);
	}
	
	@Test
	public void IntersectionV4() {
		Sphere sphere = new Sphere(new Point3D(-2,-2,2), 5, 1, 1, 1, 0.5, 0.5, 0.5, 10);
		
		Ray ray = Ray.fromPoints(new Point3D(-2,-2,10), new Point3D(-2,-2,-10));
		
		RayIntersection intersection = sphere.findClosestRayIntersection(ray);
		Assert.assertEquals(intersection == null, false);
	}
	
	@Test
	public void IntersectionV5() {
		Sphere sphere = new Sphere(new Point3D(-2,-2,2), 5, 1, 1, 1, 0.5, 0.5, 0.5, 10);
		
		Ray ray = Ray.fromPoints(new Point3D(-2,-2,1), new Point3D(-2,-2,-10));
		
		RayIntersection intersection = sphere.findClosestRayIntersection(ray);
		Assert.assertEquals(intersection == null, false);
	}
	
	@Test
	public void IntersectionV6() {
		Sphere sphere = new Sphere(new Point3D(-2,-2,2), 5, 1, 1, 1, 0.5, 0.5, 0.5, 10);
		
		Ray ray = Ray.fromPoints(new Point3D(-2,-2,1), new Point3D(-2,-2,10));
		
		RayIntersection intersection = sphere.findClosestRayIntersection(ray);
		Assert.assertEquals(intersection == null, false);
	}
	
	@Test
	public void IntersectionV7() {
		Sphere sphere = new Sphere(new Point3D(-2,-2,2), 5, 1, 1, 1, 0.5, 0.5, 0.5, 10);
		
		Ray ray = Ray.fromPoints(new Point3D(1000,200,50), new Point3D(-2,-2,-1));
		
		RayIntersection intersection = sphere.findClosestRayIntersection(ray);
		Assert.assertEquals(intersection == null, false);
	}
	
	public void findClosestRayIntersection_ValidResult_Test() {
        Ray ray = Ray.fromPoints(new Point3D(), new Point3D(5, 5, 5));
        Sphere sphere = new Sphere(new Point3D(3.5, 3.5, 3.5), 2, 1, 1, 1, 0.5,
                        0.5, 0.5, 10);
        RayIntersection inter = sphere.findClosestRayIntersection(ray);
        Assert.assertTrue(inter.getKdr() == 1);
        Assert.assertTrue(inter.getKdg() == 1);
        Assert.assertTrue(inter.getKdb() == 1);
        Assert.assertTrue(inter.getKrr() == 0.5);
        Assert.assertTrue(inter.getKrg() == 0.5);
        Assert.assertTrue(inter.getKrb() == 0.5);
        System.out.println(inter.getPoint().x);
        System.out.println(inter.getPoint().y);
        System.out.println(inter.getPoint().z);
        Assert.assertEquals(inter.getPoint().x, 2.34529946162075, DELTA);
        Assert.assertEquals(inter.getPoint().y, 2.34529946162075, DELTA);
        Assert.assertEquals(inter.getPoint().z, 2.34529946162075, DELTA);
	}

	@Test
	public void ffindClosestRayIntersection_ValidResult_Test() {
        Ray ray = Ray.fromPoints(new Point3D(), new Point3D(1, 0, 0));
        Sphere sphere = new Sphere(new Point3D(0, 1, 0), 1, 1, 1, 1, 0.5, 0.5,
                        0.5, 10);
        RayIntersection inter = sphere.findClosestRayIntersection(ray);

        Assert.assertTrue(inter.getKdr() == 1);
        Assert.assertTrue(inter.getKdg() == 1);
        Assert.assertTrue(inter.getKdb() == 1);
        Assert.assertTrue(inter.getKrr() == 0.5);
        Assert.assertTrue(inter.getKrg() == 0.5);
        Assert.assertTrue(inter.getKrb() == 0.5);
        Assert.assertEquals(inter.getPoint().x, 0, DELTA);
        Assert.assertEquals(inter.getPoint().y, 0, DELTA);
        Assert.assertEquals(inter.getPoint().z, 0, DELTA);
	}

	@Test
	public void findClosestRayIntersection_NoIntersection_Test() {
        Ray ray = Ray.fromPoints(new Point3D(), new Point3D(5, 5, 5));
        Sphere sphere = new Sphere(new Point3D(10, 3.5, 3.5), 1, 1, 1, 1, 0.5,
                        0.5, 0.5, 10);
        RayIntersection inter = sphere.findClosestRayIntersection(ray);
        Assert.assertTrue(inter == null);
	}
	
	
}
