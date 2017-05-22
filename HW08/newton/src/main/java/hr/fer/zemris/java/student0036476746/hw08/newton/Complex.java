package hr.fer.zemris.java.student0036476746.hw08.newton;

import java.text.DecimalFormat;

/**
 * Class which represents an immutable model of a complex number.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class Complex {
	
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
	 * Instance of complex number with values (0, 0).
	 */
	public static final Complex ZERO = new Complex(0, 0);
	/**
	 * Instance of complex number with values (1, 0).
	 */
	public static final Complex ONE = new Complex(1, 0);
	/**
	 * Instance of complex number with values (-1, 0).
	 */
	public static final Complex ONE_NEG = new Complex(-1, 0);
	/**
	 * Instance of complex number with values (0, 1).
	 */
	public static final Complex IM = new Complex(0, 1);
	/**
	 * Instance of complex number with values (0, -1).
	 */
	public static final Complex IM_NEG = new Complex(0, -1);
	
	/**
	 * Empty constructor which constructs a complex number with values (0, 0).
	 */
	public Complex () {
		this(0, 0);
	}
	
	/**
	 * Constructor which constructs a complex number with provided values.
	 * 
	 * @param re Real part of the complex number.
	 * @param im Imaginary part of the complex number.
	 */
	public Complex (double re, double im) {
		this.real = re;
		this.imaginary = im;
		magnitude = Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2));
		angle = Math.atan2(imaginary, real);
	}
	
	/**
	 * Returns the module of this complex number.
	 * 
	 * @return Module of this complex number.
	 */
	public double module() {
		return Math.abs(magnitude);
	}
	
	/**
	 * Multiplies the given complex number with the method caller.
	 * 
	 * @param c Complex number to be multiplied with the method caller.
	 * @return New instance of Complex after multiplication.
	 * @throws IllegalArgumentException if null parameter is passed.
	 */
	public Complex multiply (Complex c) throws IllegalArgumentException {
		if (c == null) {
			throw new IllegalArgumentException("You cannot pass a null parameter!");
		}
		
		double newReal = real * c.getReal() - imaginary * c.getImaginary();
		double newImaginary = real * c.getImaginary() + imaginary * c.getReal();
		
		return new Complex(newReal, newImaginary);
	}
	
	/**
	 * Divides the method caller with the given complex number.
	 * 
	 * @param c Complex number to divide the method caller.
	 * @return New instance of Complex after division.
	 * @throws IllegalArgumentException if null parameter is passed.
	 */
	public Complex divide(Complex c) throws IllegalArgumentException {
		if (c == null) {
			throw new IllegalArgumentException("You cannot pass a null parameter!");
		} else if ((c.getReal() == 0 && c.getImaginary() == 0)) {
			throw new IllegalArgumentException("Division by zero is not posible!");
		}
		
		double newReal = (real * c.getReal() + imaginary * c.getImaginary())
				/ (Math.pow(c.getReal(), 2) + Math.pow(c.getImaginary(), 2));
		double newImaginary = (imaginary * c.getReal() - real
				* c.getImaginary())
				/ (Math.pow(c.getReal(), 2) + Math.pow(c.getImaginary(), 2));

		return new Complex(newReal, newImaginary);
	}
	
	/**
	 * Adds the given complex number to the method caller.
	 * 
	 * @param c Complex number to be added to the method caller.
	 * @return New instance of Complex after addition.
	 * @throws IllegalArgumentException if null parameter is passed.
	 */
	public Complex add (Complex c) throws IllegalArgumentException {
		if (c == null) {
			throw new IllegalArgumentException("You cannot pass a null parameter!");
		}
		
		double newReal = real + c.getReal();
		double newImaginary = imaginary + c.getImaginary();
		
		return new Complex(newReal, newImaginary);
	}
	
	/**
	 * Subtracts the given complex number from the method caller.
	 * 
	 * @param c Complex number to be subtracted from the method caller.
	 * @return New instance of Complex after subtraction.
	 * @throws IllegalArgumentException if null parameter is passed.
	 */
	public Complex sub (Complex c) throws IllegalArgumentException {
		if (c == null) {
			throw new IllegalArgumentException("You cannot pass a null parameter!");
		}
		
		double newReal = real - c.getReal();
		double newImaginary = imaginary - c.getImaginary();
		
		return new Complex(newReal, newImaginary);
	}
	
	/**
	 * Return negated value of this complex number.
	 * 
	 * @return Negated value of this complex number.
	 */
	public Complex negate() {
		return new Complex(-real, -imaginary);
	}
	
	public String toString () {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append(formatHelp(real, "0.##"))
				.append(formatHelp(imaginary, " + 0.##; - 0.##")).append("i");

		return builder.toString();
	}
	
	/**
	 * Returns the string representation of the given number.
	 * @param number Number to be returned as string.
	 * @param format Specifies how to format the given number.
	 * @return Formatted number.
	 */
	private String formatHelp (double number, String format) {
		
		DecimalFormat formatter = new DecimalFormat(format);
		
		return formatter.format(number);
	}

	/**
	 * Gets the real part of the complex number.
	 * 
	 * @return Real part of the complex number.
	 */
	public double getReal() {
		return real;
	}
	
	/**
	 * Gets the imaginary part of the complex number.
	 * 
	 * @return Imaginary part of the complex number.
	 */
	public double getImaginary() {
		return imaginary;
	}
	
	/**
	 * Gets the magnitude of the complex number.
	 * 
	 * @return Magnitude of the complex number.
	 */
	public double getMagnitude() {
		return magnitude;
	}
	
	/**
	 * Gets the angle of the complex number.
	 * 
	 * @return Angle of the complex number.
	 */
	public double getAngle() {
		return angle;
	}
	
	

}
