package hr.fer.zemris.java.linearna.demo;

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.Matrix;
import hr.fer.zemris.linearna.Vector;

/**
 * Class which tests created matrices and vectors class with the IRG example.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class Demo {
	
	public static void main(String[] args) {
		IMatrix a = Matrix.parseSimple("3 5 | 2 10"); 
		IMatrix r = Matrix.parseSimple("2 | 8");
		IMatrix v = a.nInvert().nMultiply(r);
		System.out.println("Rjesenje sustava je: ");
		System.out.println(v);
		
		IVector vector = Vector.parseSimple("3 4 7");
		System.out.println(vector);
	}
}
