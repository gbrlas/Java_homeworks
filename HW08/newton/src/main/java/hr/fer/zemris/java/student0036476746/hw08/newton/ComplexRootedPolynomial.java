package hr.fer.zemris.java.student0036476746.hw08.newton;

/**
 * Class which represents an immutable model of root-based complex polynomial.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class ComplexRootedPolynomial {
	/**
	 * Array holding the roots of the complex polynomial.
	 */
	private final Complex[] complexRoots;
	
	/**
	 * Constructor which sets the complex roots array to the provided values.
	 * @param roots Roots of this complex polynomial.
	 */
	public ComplexRootedPolynomial(Complex... roots) {
		complexRoots = roots;
	}
	
	/**
	 * Computes polynomial value at given point z.
	 * 
	 * @param z Point at which we calculate the polynomial value.
	 * @return Complex number representing polynomial value at given point z.
	 * @throws IllegalArgumentException - if provided parameter is null.
	 */
	public Complex apply (Complex z) throws IllegalArgumentException {
		if (z == null) {
			throw new IllegalArgumentException("You cannot pass a null parameter!");
		}
		
		Complex temp;
		Complex multiplied = null;
		
		int i = 0;
		for (Complex c : complexRoots) {
			temp = z.sub(c);
			if (i == 0) {
				multiplied = temp;
				i++;
				continue;
			}
			multiplied = multiplied.multiply(temp);
		}
		
		return multiplied;
	}
	
	/**
	 * Converts this representation to ComplexPolynomial type.
	 * 
	 * @return ComplexPolynomial representation of this complex number.
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial[] complexPolyArray = new ComplexPolynomial[complexRoots.length];
		for (int i = 0; i < complexPolyArray.length; i++) {
			complexPolyArray[i] = new ComplexPolynomial(Complex.ONE, complexRoots[i].negate());
		}
		
		new Complex();
		ComplexPolynomial temp = new ComplexPolynomial(Complex.ONE);
		
		for (int i = 0; i < complexPolyArray.length; i++) {
			if (i == 0) {
				temp = complexPolyArray[i];
				continue;
			}
			temp = temp.multiply(complexPolyArray[i]);
		}
		
		return temp;	
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		for (Complex c : complexRoots) {
			builder.append("(z - " + c + ")*");
		}
		
		builder.deleteCharAt(builder.length() - 1);
		
		return builder.toString();
	}
	
	/**
	 * Find the index of closest root for given complex number z that is within treshold.
	 * 
	 * @param z Complex number to be compared with the values in the roots array.
	 * @param treshold Treshold within which the result is accepted.
	 * @return Closest index or -1 if there is no such root.
	 * @throws IllegalArgumentException - if provided parameter is null.
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		if (z == null) {
			throw new IllegalArgumentException("You cannot pass a null parameter!");
		}
		int index = - 1;
		
		for (int i = 0; i < complexRoots.length; i++) {
			if (Math.abs(complexRoots[i].getReal() - z.getReal()) <= treshold
					&& Math.abs(complexRoots[i].getImaginary()
							- z.getImaginary()) <= treshold) {
				index = i + 1;
				break;
			}
		}
		
		return index;
	}
}
