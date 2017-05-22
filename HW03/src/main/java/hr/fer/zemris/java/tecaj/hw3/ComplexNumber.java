package hr.fer.zemris.java.tecaj.hw3;

import java.text.DecimalFormat;

/**
 * Class which represents an unmodifiable complex number and provides a variety of operations
 * for modifying them. When creating the object provide the number with the imaginary part following 
 * the real part.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class ComplexNumber {
	/**
	 * Real part of the complex number.
	 */
	private final double real;
	/**
	 * Imaginary part of the complex number.
	 */
	private final double imaginary;
	/**
	 * Magnitude of the complex number.
	 */
	private final double magnitude;
	/**
	 * Angle of the complex number.
	 */
	private final double angle;
	
	/**
	 * Constructor which sets the complex number values to the given values.
	 * @param real Real part of the complex number.
	 * @param imaginary Imaginary part of the complex number.
	 */
	public ComplexNumber (double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
		magnitude = Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 3));
		angle = Math.atan2(imaginary, real);
	}
	
	/**
	 * Creates a new instance of ComplexNumber from the given real parameter.
	 * @param real Real part of the complex number to be created.
	 * @return New instance of ComplexNumber with the given value.
	 */
	public static ComplexNumber fromReal (double real) {
		return new ComplexNumber(real, 0);
	}
	
	/**
	 * Creates a new instance of ComplexNumber from the given imaginary parameter.
	 * @param imaginary Imaginary part of the complex number to be created.
	 * @return New instance of ComplexNumber with the given value.
	 */
	public static ComplexNumber fromImaginary (double imaginary) {
		return new ComplexNumber(0, imaginary);
	}
	
	/**
	 * Creates a new instance of ComplexNumber from the given magnitude and angle.
	 * @param magnitude Magnitude of the complex number to be created.
	 * @param angle Angle of the complex number to be created.
	 * @return New instance of ComplexNumber with the given value.
	 */
	public static ComplexNumber fromMagnitudeAndAngle (double magnitude, double angle) {
		double real = magnitude * Math.cos(angle);
		double imaginary = magnitude * Math.sin(angle);
		
		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * Parses the given string into a complex number. String must be passed in a valid format.
	 * @param str String to be parsed into a complex number.
	 * @return New instance of ComplexNumber with the given value.
	 */
	public static ComplexNumber parse (String str) {
		
		if (str.length() == 0) {
			throw new IllegalArgumentException("String to be parsed must not be empty!");
		}
		
		
		StringBuilder builder = new StringBuilder();
		double newReal = 0;
		double newImaginary = 0;
		
		String str2 = str.trim();
		str2 = str2.replace(" ", "");
		//if only the real part is given
		if (!str2.contains("i")) {
			newReal = Integer.parseInt(str2);
			return new ComplexNumber(newReal, newImaginary);
		}
		
		String temp1 = "";
		String temp2 = "";
		for (int i = 0; i < str2.length(); i++) {
			if (Character.isLetter(str2.charAt(i)) && str2.charAt(i) != 'i') {
				throw new IllegalArgumentException("String to be parsed must have a correct format!");
			}
			if (str2.charAt(i) == 'i') {
				if (i == 0 || builder.charAt(builder.length()-1) == '+'|| builder.charAt(builder.length()-1) == '-') {
					builder.append('1');
				}
				temp2 = builder.toString();
			} else if (str2.charAt(i) == '+' || (str2.charAt(i) == '-' && i != 0)) {
				temp1 = builder.toString();
				builder.setLength(0);
				builder.append(str2.charAt(i));
			} else {
				builder.append(str2.charAt(i));
			}
		}
		
		//check whether the temp strings are empty
		 if (temp1.length() == 0) {
			 newReal = 0;
		 } else {
			 newReal = Double.parseDouble(temp1);
		 }
		 	 
		newImaginary = Double.parseDouble(temp2);
		
		
		return new ComplexNumber(newReal, newImaginary);	
			
	}
	
	/**
	 * Returns the real part of the complex number.
	 * @return Real part of the complex number.
	 */
	public double getReal () {
		return real;
	}
	
	/**
	 * Returns the imaginary part of the complex number.
	 * @return Imaginary part of the complex number.
	 */
	public double getImaginary () {
		return imaginary;
	}
	
	/**
	 * Returns the magnitude of the complex number.
	 * @return Magnitude of the complex number.
	 */
	public double getMagnitude () {
		return magnitude;
	}
	
	/**
	 * Returns the angle of the complex number.
	 * @return Angle of the complex number.
	 */
	public double getAngle () {
		return angle;
	}
	
	
	/**
	 /**
	 * Adds the given complex number to the method caller.
	 * @param complNum Complex number to be added to the method caller.
	 * @return New instance of ComplexNumber after addition.
	 * @throws IllegalArgumentException if null parameter is passed.
	 */
	public ComplexNumber add (ComplexNumber complNum) throws IllegalArgumentException {
		if (complNum == null) {
			throw new IllegalArgumentException("You cannot pass a null parameter!");
		}
		
		double newReal = real + complNum.getReal();
		double newImaginary = imaginary + complNum.getImaginary();
		
		return new ComplexNumber(newReal, newImaginary);
	}
	
	
	/**
	 * Subtracts the given complex number from the method caller.
	 * @param complNum Complex number to be subtracted from the method caller.
	 * @return New instance of ComplexNumber after subtraction.
	 * @throws IllegalArgumentException if null parameter is passed.
	 */
	public ComplexNumber sub (ComplexNumber complNum) throws IllegalArgumentException {
		if (complNum == null) {
			throw new IllegalArgumentException("You cannot pass a null parameter!");
		}
		
		double newReal = real - complNum.getReal();
		double newImaginary = imaginary - complNum.getImaginary();
		
		return new ComplexNumber(newReal, newImaginary);
	}
	
	/**
	 * Multiplies the given complex number with the method caller.
	 * @param c Complex number to be multiplied with the method caller.
	 * @return New instance of ComplexNumber after multiplication.
	 * @throws IllegalArgumentException if null parameter is passed.
	 */
	public ComplexNumber mul (ComplexNumber c) throws IllegalArgumentException {
		if (c == null) {
			throw new IllegalArgumentException("You cannot pass a null parameter!");
		}
		
		double newReal = real * c.getReal() - imaginary * c.getImaginary();
		double newImaginary = real * c.getImaginary() + imaginary * c.getReal();
		
		return new ComplexNumber(newReal, newImaginary);
	}
	
	
	/**
	 * Divides the method caller with the given complex number.
	 * @param complNum Complex number to divide the method caller.
	 * @return New instance of ComplexNumber after division.
	 * @throws IllegalArgumentException if null parameter is passed.
	 */
	public ComplexNumber div (ComplexNumber complNum) throws IllegalArgumentException {
		if (complNum == null) {
			throw new IllegalArgumentException("You cannot pass a null parameter!");
		} else if ((complNum.getReal() == 0 && complNum.getImaginary() == 0)) {
			throw new IllegalArgumentException("Division by zero is not posible!");
		}
		
		double newReal = (real * complNum.getReal() + imaginary * complNum.getImaginary()) / (Math.pow(complNum.getReal(), 2) + Math.pow(complNum.getImaginary(), 2));
		double newImaginary = (imaginary * complNum.getReal() - real * complNum.getImaginary()) / (Math.pow(complNum.getReal(), 2) + Math.pow(complNum.getImaginary(), 2));
		
		return new ComplexNumber(newReal, newImaginary);
	}
	
	/**
	 * Calculates the n-th root of the method caller.
	 * @param num Root value.
	 * @return Array of complex numbers which are the result of calculating the root.
	 * @throws IllegalArgumentException if provided root value is lower or equal to 0.
	 */
	public ComplexNumber[] root (int num) throws IllegalArgumentException {
		
		if (num <= 0) {
			throw new IllegalArgumentException("Provided root value must be greater than 0!");
		}
		
		double r = Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2));
		r = Math.pow(r, (1.0/num));
		
		double newReal, newImaginary;
		ComplexNumber[] temp = new ComplexNumber[num];
		
		for (int i = 0; i < num; i++) {
			newReal = r * Math.cos((angle + 2 * i * Math.PI) / num);
			newImaginary = r * Math.sin((angle + 2 * i * Math.PI) / num);
			
			temp[i] = new ComplexNumber(newReal, newImaginary);
		}
		
		return temp;
	}
	
	/**
	 * Calculates the n-th power of the method caller.
	 * @param n Power value.
	 * @return New instance of ComplexNumber after calculating the power.
	 * @throws IllegalArgumentException if provided power value is lower than 0.
	 */
	public ComplexNumber power (int n) throws IllegalArgumentException {
		
		if (n < 0) {
			throw new IllegalArgumentException("Provided power value must be greater or equal to 0!");
		}
		
		double r = Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2));
		r = Math.pow(r, n);
		
		double newReal = r * Math.cos(n * angle);
		double newImaginary = r * Math.sin(n * angle);
		
		return new ComplexNumber(newReal, newImaginary);
	}
	
	/**
	 * Returns the string representation of the method caller.
	 */
	public String toString () {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append(formatHelp(real, "0.##")).append(formatHelp(imaginary, " + 0.##; - 0.##")).append("i");

		return builder.toString();
	}
	
	/**
	 * Returns the string representation of the given number.
	 * @param number Number to be returned as string.
	 * @param format Specifies how to format the given number.
	 * @return Formatted number.
	 */
	public String formatHelp (double number, String format) {
		
		DecimalFormat formatter = new DecimalFormat(format);
		
		return formatter.format(number);
	}
}
