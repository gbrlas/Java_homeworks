package hr.fer.zemris.java.student0036476746.hw08.newton;

/**
 * Class which represents an immutable model of coefficient-based complex
 * polynomial.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class ComplexPolynomial {
	/**
	 * Array holding the factors of the complex polynomial.
	 */
	private final Complex[] complexFactors;

	/**
	 * Constructor which sets the complex factors array to the provided values.
	 * Bigger factors come first.
	 * @param factors Factors of this complex polynomial.
	 */
	public ComplexPolynomial(Complex... factors) {
		complexFactors = factors;
	}

	/**
	 * Returns the order of this polynom.
	 * @return Order of the polynom.
	 */
	public short order() {
		return (short) (complexFactors.length - 1);
	}

	/**
	 * Multiplies this complex polynomial with the provided one.
	 * @param p Complex polynomial to be multiplied.
	 * @return Complex polynomial representing the result of the multiplication.
	 * @throws IllegalArgumentException - if a null parameter is passed.
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) throws IllegalArgumentException {
		if (p == null) {
			throw new IllegalArgumentException("You cannot pass a null parameter!");
		}
		Complex[] newArray = new Complex[this.order() + p.order() + 1];

		for (int i = 0; i < newArray.length; i++) {
			newArray[i] = new Complex(0, 0);
		}

		for (int i = 0; i < complexFactors.length; i++) {
			if (complexFactors[i].getImaginary() == 0 && complexFactors[i].getReal() == 0) {
				continue;
			}
			for (int j = 0; j < p.complexFactors.length; j++) {
				if (p.complexFactors[j].getImaginary() == 0 && p.complexFactors[j].getReal() == 0) {
					continue;
				}
				newArray[i + j] = newArray[i + j].add(complexFactors[i]
						.multiply(p.complexFactors[j]));
			}
		}

		return new ComplexPolynomial(newArray);
	}

	/**
	 * Computes first derivative of this polynomial.
	 * @return Complex polynomial representing the first derivative of this polynomial.
	 */
	public ComplexPolynomial derive() {
		Complex[] newArray = new Complex[complexFactors.length - 1];

		for (int i = 0; i < newArray.length; i++) {
			newArray[i] = complexFactors[i].multiply(new Complex(
					complexFactors.length - 1 - i, 0));
		}

		return new ComplexPolynomial(newArray);
	}

	/**
	 * Computes polynomial value at given point z.
	 * @param z Point at which we calculate the polynomial value.
	 * @return Complex number representing polynomial value at given point z.
	 * @throws IllegalArgumentException - if provided parameter is null.
	 */
	public Complex apply(Complex z) throws IllegalArgumentException {
		if (z == null) {
			throw new IllegalArgumentException("You cannot pass a null parameter!");
		}

		Complex temp;
		Complex multiplied = null;
		Complex sum = null;

		for (int i = 0; i < complexFactors.length - 1; i++) {
			temp = power(z, complexFactors.length - i - 1);
			multiplied = temp.multiply(complexFactors[i]);
			if (i == 0) {
				sum = multiplied;
				continue;
			}
			sum = sum.add(multiplied);
		}

		sum = sum.add(complexFactors[complexFactors.length - 1]);

		return sum;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < complexFactors.length; i++) {
			if (i == complexFactors.length - 1) {
				builder.append(" " + complexFactors[i]);
			} else {
				builder.append("(" + complexFactors[i] + ")z^"
						+ (complexFactors.length - 1 - i) + " + ");
			}
		}

		return builder.toString();
	}

	/**
	 * Method used for calculating n-th power of the complex polynomial.
	 * @param c Complex number.
	 * @param n Power value.
	 * @return New instance of ComplexNumber after calculating the power.
	 * @throws IllegalArgumentException - if provided power value is lower than 0 or provided complex number is null.
	 */
	public Complex power(Complex c, int n) throws IllegalArgumentException {

		if (n < 0) {
			throw new IllegalArgumentException("Provided power value must be greater or equal to 0!");
		}

		if (c == null) {
			throw new IllegalArgumentException("You cannot pass a null parameter!");
		}

		if (n == 0) {
			return new Complex(1, 0);
		}

		double r = Math.sqrt(Math.pow(c.getReal(), 2)
				+ Math.pow(c.getImaginary(), 2));
		r = Math.pow(r, n);

		double newReal = r * Math.cos(n * c.getAngle());
		double newImaginary = r * Math.sin(n * c.getAngle());

		return new Complex(newReal, newImaginary);
	}
}
