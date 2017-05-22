package hr.fer.zemris.java.student0036476746.hw08.newton;

import org.junit.Test;
import org.junit.Assert;

/**
 * Class which jUnit tests the ComplexPolynomial class.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class ComplexPolynomialTest {
	
	@Test
	public void polyOrder () {
		Complex c1 = new Complex(1,2);
		Complex c2 = new Complex(4,1);
		ComplexPolynomial poly = new ComplexPolynomial(c1, c2);
		
		Assert.assertEquals(poly.order() == 1, true);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void polyMultiplyNullArgument () {
		Complex c1 = new Complex(1,2);
		Complex c2 = new Complex(4,1);
		ComplexPolynomial poly = new ComplexPolynomial(c1, c2);
		
		poly.multiply(null);
	}
	
	@Test 
	public void polyMultiplyV1 () {
		Complex c1 = new Complex(1,2);
		Complex c2 = new Complex(4,1);
		Complex c3 = new Complex(2,2);
		Complex c4 = new Complex(6,1);
		ComplexPolynomial poly = new ComplexPolynomial(c1, c2);
		ComplexPolynomial poly2 = new ComplexPolynomial(c3, c4);
		
		Assert.assertEquals(poly.multiply(poly2).order() == 2, true);
	}
	
	@Test 
	public void polyMultiplyV2 () {
		Complex c1 = new Complex(1,2);
		Complex c2 = new Complex(4,1);
		Complex c3 = new Complex(2,2);
		Complex c4 = new Complex(0,0);
		ComplexPolynomial poly = new ComplexPolynomial(c1, c2);
		ComplexPolynomial poly2 = new ComplexPolynomial(c3, c4);
		
		Assert.assertEquals(poly.multiply(poly2).order() == 2, true);
	}
	
	@Test 
	public void polyMultiplyV3 () {
		Complex c1 = new Complex(1,2);
		Complex c2 = new Complex(4,1);
		Complex c3 = new Complex(2,2);
		Complex c4 = new Complex(6,1);
		ComplexPolynomial poly = new ComplexPolynomial(c1, c2, c3, c4);
		
		Assert.assertEquals(poly.derive().order() == poly.order() - 1, true);
	}
	
	@Test 
	public void polyMultiplyV5 () {
		Complex c1 = new Complex(5,1);
		Complex c2 = new Complex(0,0);
		Complex c3 = new Complex(2,0);
		Complex c4 = new Complex(0,0);
		ComplexPolynomial poly = new ComplexPolynomial(c1, c2);
		ComplexPolynomial poly2 = new ComplexPolynomial(c3, c4);
		
		Assert.assertEquals(poly.multiply(poly2).order() == 2, true);
	}
	
	@Test 
	public void polyMultiplyV6 () {
		Complex c1 = new Complex(5,1);
		Complex c2 = new Complex(0,1);
		Complex c3 = new Complex(2,2);
		Complex c4 = new Complex(0,1);
		ComplexPolynomial poly = new ComplexPolynomial(c1, c2);
		ComplexPolynomial poly2 = new ComplexPolynomial(c3, c4);
		
		Assert.assertEquals(poly.multiply(poly2).order() == 2, true);
	}
	
	@Test 
	public void polyMultiplyV7 () {
		Complex c1 = new Complex(1,0);
		Complex c2 = new Complex(2,0);
		ComplexPolynomial poly = new ComplexPolynomial(c1, c1, c1);
		ComplexPolynomial poly2 = new ComplexPolynomial(c2, c1, c1);
		
		Assert.assertEquals(poly.multiply(poly2).order() == 0, false);
	}
	
	@Test
	public void polyApply () {
		Complex c1 = new Complex(1,2);
		Complex c2 = new Complex(4,1);
		Complex c3 = new Complex(2,2);
		Complex c4 = new Complex(6,1);
		ComplexPolynomial poly = new ComplexPolynomial(c1, c2, c3, c4);
		
		Complex applied = poly.apply(c1);
		Assert.assertTrue(applied.toString().equals("-19 - 4i"));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void polyApplyNull () {
		Complex c1 = new Complex(1,2);
		Complex c2 = new Complex(4,1);
		Complex c3 = new Complex(2,2);
		Complex c4 = new Complex(6,1);
		ComplexPolynomial poly = new ComplexPolynomial(c1, c2, c3, c4);
		
		Complex applied = poly.apply(null);
		Assert.assertTrue(applied.toString().equals("-19 - 4i"));
	}
	
	@Test
	public void polyPower () {
		Complex c1 = new Complex(1,2);
		Complex c2 = new Complex(4,1);
		Complex c3 = new Complex(2,2);
		Complex c4 = new Complex(6,1);
		ComplexPolynomial poly = new ComplexPolynomial(c1, c2, c3, c4);
		@SuppressWarnings("unused")
		String string = poly.toString();
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void powerV1 () {
		Complex c1 = new Complex(1,2);
		ComplexPolynomial poly = new ComplexPolynomial(c1);
		
		poly.power(c1, -1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void powerV2 () {
		Complex c1 = new Complex(1,2);
		ComplexPolynomial poly = new ComplexPolynomial(c1);
		
		poly.power(null, 1);
	}
	
	@Test 
	public void powerV3 () {
		Complex c1 = new Complex(1,2);
		ComplexPolynomial poly = new ComplexPolynomial(c1);
		
		Assert.assertTrue(poly.power(c1, 0).getReal() == 1);;
	}
}
