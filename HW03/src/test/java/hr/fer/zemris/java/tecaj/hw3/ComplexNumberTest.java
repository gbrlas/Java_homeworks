package hr.fer.zemris.java.tecaj.hw3;

import org.junit.Test;
import org.junit.Assert;

public class ComplexNumberTest {
	
	//constructor
	@Test
	public void complexNumberConstructorWithRealAndImaginaryArgs () {
		ComplexNumber c1 = new ComplexNumber(1, 3);
		Assert.assertTrue(c1.getReal() == 1 && c1.getImaginary() == 3);
	}
	
	//fromReal()
	@Test
	public void complexNumberConstructionWithRealArg () {
		ComplexNumber c1 = ComplexNumber.fromReal(1);
		Assert.assertTrue(c1.getReal() == 1 && c1.getImaginary() == 0);
	}
	
	//fromImaginary()
	@Test
	public void complexNumberConstructionWithImaginaryArg () {
		ComplexNumber c1 = ComplexNumber.fromImaginary(3);
		Assert.assertTrue(c1.getReal() == 0 && c1.getImaginary() == 3);
	}
	
	//fromMagnitudeAndAngle()
	@Test
	public void complexNumberConstructionWithAngleAndMultitude () {
		ComplexNumber c1 = ComplexNumber.fromMagnitudeAndAngle(2 * Math.sqrt(2), Math.toRadians(45));
		Assert.assertTrue((int) c1.getReal() == 2 && (int) c1.getImaginary() == 2);
	}
	
	//parse()
	@Test (expected = IllegalArgumentException.class)
	public void parseComplexNumberifStringEmptyShouldThrowAnException () {
		ComplexNumber.parse("");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void parseComplexNumberifStringContainsExtraCharactersShouldThrowAnException () {
		ComplexNumber.parse("125 + 4ikh");
	}
	
	@Test
	public void parseComplexNumberShouldReturnValidComplexNumber () {
		ComplexNumber c1 = ComplexNumber.parse("1 + i");
		Assert.assertTrue(c1.getReal() == 1 && c1.getImaginary() == 1);
	}
	
	@Test
	public void parseComplexNumberShouldReturnValidComplexNumberIfOnlyRealIsPassedIn () {
		ComplexNumber c1 = ComplexNumber.parse("4");
		Assert.assertTrue(c1.getReal() == 4 && c1.getImaginary() == 0);
	}
	
	@Test
	public void parseComplexNumberShouldReturnValidComplexNumberIfOnlyImaginaryIsPassedIn () {
		ComplexNumber c1 = ComplexNumber.parse("5i");
		Assert.assertTrue(c1.getReal() == 0 && c1.getImaginary() == 5);
	}
	
	@Test
	public void parseComplexNumberShouldReturnValidComplexNumberIfOnlyImaginaryIsPassedInV2 () {
		ComplexNumber c1 = ComplexNumber.parse("i");
		Assert.assertTrue(c1.getReal() == 0 && c1.getImaginary() == 1);
	}
	
	//getters
	@Test
	public void getRealShouldReturnCorrectValue () {
		ComplexNumber c1 = ComplexNumber.parse("4 + 5i");
		Assert.assertTrue(c1.getReal() == 4);
	}
	
	@Test
	public void getImaginaryShouldReturnCorrectValue () {
		ComplexNumber c1 = ComplexNumber.parse("4 + 5i");
		Assert.assertTrue(c1.getImaginary() == 5);
	}
	
	@Test
	public void getMagnitudeShouldReturnCorrectValue () {
		ComplexNumber c1 = ComplexNumber.parse("1 + i");
		Assert.assertTrue(c1.getMagnitude() == Math.sqrt(2));
	}
	
	@Test
	public void getAngleShouldReturnCorrectValue () {
		ComplexNumber c1 = ComplexNumber.parse("1 + i");
		Assert.assertTrue(c1.getAngle() == Math.toRadians(45));
	}
	
	
	//add()
	@Test (expected = IllegalArgumentException.class)
	public void addIfNullArgumentPassedShouldThrowAnException () {
		ComplexNumber c1 = new ComplexNumber(2, 2);
		ComplexNumber c2 = null;
		c1.add(c2);
	}
	
	@Test
	public void addIfPassedInArgumentIsValid () {
		ComplexNumber c1 = new ComplexNumber(2, 2);
		ComplexNumber c2 = new ComplexNumber(3, 3);
		Assert.assertTrue(c1.add(c2).getReal() == 5 && c1.add(c2).getImaginary() == 5);
	}
	
	//sub()
	@Test (expected = IllegalArgumentException.class)
	public void subIfNullArgumentPassedShouldThrowAnException () {
		ComplexNumber c1 = new ComplexNumber(2, 2);
		ComplexNumber c2 = null;
		c1.sub(c2);
	}
	
	@Test
	public void subIfPassedInArgumentIsValid () {
		ComplexNumber c1 = new ComplexNumber(2, 2);
		ComplexNumber c2 = new ComplexNumber(3, 1);
		Assert.assertTrue(c1.sub(c2).getReal() == -1 && c1.sub(c2).getImaginary() == 1);
	}
	
	//mul()
	@Test (expected = IllegalArgumentException.class)
	public void mulIfNullArgumentPassedShouldThrowAnException () {
		ComplexNumber c1 = new ComplexNumber(2, 2);
		ComplexNumber c2 = null;
		c1.mul(c2);
	}
	
	@Test
	public void mulIfPassedInArgumentIsValid () {
		ComplexNumber c1 = new ComplexNumber(2, 2);
		ComplexNumber c2 = new ComplexNumber(3, 1);
		Assert.assertTrue(c1.mul(c2).getReal() == 4 && c1.mul(c2).getImaginary() == 8);
	}
	
	//div()
	@Test (expected = IllegalArgumentException.class)
	public void divIfNullArgumentPassedShouldThrowAnException () {
		ComplexNumber c1 = new ComplexNumber(2, 2);
		ComplexNumber c2 = null;
		c1.div(c2);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void divIfArgumentWithRealAndImaginaryEqualToZeroPassedShouldThrowAnException () {
		ComplexNumber c1 = new ComplexNumber(2, 2);
		ComplexNumber c2 = new ComplexNumber(0, 0);
		c1.div(c2);
	}
	
	@Test
	public void divIfPassedInArgumentIsValid () {
		ComplexNumber c1 = new ComplexNumber(2, 2);
		ComplexNumber c2 = new ComplexNumber(3, 1);
		Assert.assertTrue(c1.div(c2).getReal() == 0.8 && c1.div(c2).getImaginary() == 0.4);
	}
	
	//power()
	@Test (expected = IllegalArgumentException.class)
	public void powerIfArgumentPassedInIsLowerThanZero () {
		ComplexNumber c1 = new ComplexNumber(2, 2);
		c1.power(-1);
	}
	
	@Test
	public void powerIfArgumentPassedInIsValid () {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		Assert.assertTrue(Math.round(c1.power(2).getReal()) == -5 && Math.round(c1.power(2).getImaginary()) == 12);
	}
	
	//root()
	@Test (expected = IllegalArgumentException.class)
	public void rootIfArgumentPassedInIsLowerThanZero () {
		ComplexNumber c1 = new ComplexNumber(2, 2);
		c1.root(-1);
	}
	
	@Test
	public void rootIfArgumentPassedInIsValid () {
		ComplexNumber c1 = new ComplexNumber(0, 2);
		double real1 = c1.root(2)[0].getReal();
		double real2 = c1.root(2)[1].getReal();
		double imag1 = c1.root(2)[0].getImaginary();
		double imag2 = c1.root(2)[1].getImaginary();
		
		Assert.assertTrue(Math.round(real1) == 1 && Math.round(real2) == -1 && Math.round(imag1) == 1 && Math.round(imag2) == -1);
	}
	
	//toString()
	@Test
	public void toStringShouldReturnValidString () {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		Assert.assertTrue(c1.toString().equals("2 + 3i"));
	}
}
