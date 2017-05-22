package hr.fer.zemris.java.student0036476746.hw08.newton;

/**
 * Demo class used for basic complex polynom testing.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class Demo {
	
	public static void main(String[] args) {
		Complex c1 = new Complex(1, 1);
		Complex c2 = new Complex(1, 0);
		ComplexPolynomial cPolynomial = new ComplexPolynomial(c2,c1);
		System.out.println(cPolynomial);
		
		Complex c3 = cPolynomial.apply(new Complex(-1, 1));
		System.out.println(c3);
	}
}
