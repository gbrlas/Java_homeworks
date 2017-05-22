package hr.fer.zemris.java.student0036476746.hw08.newton;


import org.junit.Assert;
import org.junit.Test;

/**
 * Class which jUnit tests the Complex class.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class ComplexTest {
	//constructor
		@Test
		public void ComplexConstructor () {
			Complex c1 = new Complex();
			Assert.assertEquals(0, c1.getImaginary(), 0);
		}
		
		public void ComplexConstructorV2 () {
			Complex c1 = new Complex(1, 2);
			Assert.assertEquals(2, c1.getImaginary(), 0);
		}
		
		//fromImaginary()
		@Test
		public void ComplexModule () {
			Complex c1 = new Complex(1, 1);
			Assert.assertEquals(c1.module() == Math.sqrt(2), true);	
		}
		
		
		@Test
		public void getReal () {
			Complex c1 = new Complex(4, 5);
			Assert.assertTrue(c1.getReal() == 4);
		}
		
		@Test
		public void getImaginaryShouldReturnCorrectValue () {
			Complex c1 = new Complex(4, 5);
			Assert.assertTrue(c1.getImaginary() == 5);
		}
		
		@Test
		public void getMagnitudeShouldReturnCorrectValue () {
			Complex c1 = new Complex(1,1);
			Assert.assertTrue(c1.getMagnitude() == Math.sqrt(2));
		}
		
		@Test
		public void getAngleShouldReturnCorrectValue () {
			Complex c1 = new Complex(1,1);
			Assert.assertTrue(c1.getAngle() == Math.toRadians(45));
		}
		
		
		//add()
		@Test (expected = IllegalArgumentException.class)
		public void addNullArgument () {
			Complex c1 = new Complex(2, 2);
			Complex c2 = null;
			c1.add(c2);
		}
		
		@Test
		public void add () {
			Complex c1 = new Complex(2, 2);
			Complex c2 = new Complex(3, 3);
			Assert.assertTrue(c1.add(c2).getReal() == 5 && c1.add(c2).getImaginary() == 5);
		}
		
		//sub()
		@Test (expected = IllegalArgumentException.class)
		public void subNullArgument () {
			Complex c1 = new Complex(2, 2);
			Complex c2 = null;
			c1.sub(c2);
		}
		
		@Test
		public void sub () {
			Complex c1 = new Complex(2, 2);
			Complex c2 = new Complex(3, 1);
			Assert.assertTrue(c1.sub(c2).getReal() == -1 && c1.sub(c2).getImaginary() == 1);
		}
		
		//mul()
		@Test (expected = IllegalArgumentException.class)
		public void multiplyNullArgument () {
			Complex c1 = new Complex(2, 2);
			Complex c2 = null;
			c1.multiply(c2);
		}
		
		@Test
		public void multiply () {
			Complex c1 = new Complex(2, 2);
			Complex c2 = new Complex(3, 1);
			Assert.assertTrue(c1.multiply(c2).getReal() == 4 && c1.multiply(c2).getImaginary() == 8);
		}
		
		//div()
		@Test (expected = IllegalArgumentException.class)
		public void divideNullArgument () {
			Complex c1 = new Complex(2, 2);
			Complex c2 = null;
			c1.divide(c2);
		}
		
		@Test (expected = IllegalArgumentException.class)
		public void divideZero () {
			Complex c1 = new Complex(2, 2);
			Complex c2 = new Complex(0, 0);
			c1.divide(c2);
		}
		
		@Test
		public void divide () {
			Complex c1 = new Complex(2, 2);
			Complex c2 = new Complex(3, 1);
			Assert.assertTrue(c1.divide(c2).getReal() == 0.8 && c1.divide(c2).getImaginary() == 0.4);
		}
		
		@Test
		public void negate () {
			Complex c1 = new Complex(2, 2);
			Assert.assertEquals(-2, c1.negate().getImaginary(), 0);
		}
		
		//toString()
		@Test
		public void String () {
			Complex c1 = new Complex(2, 3);
			Assert.assertTrue(c1.toString().equals("2 + 3i"));
		}
}
