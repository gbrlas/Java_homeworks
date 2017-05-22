package hr.fer.zemris.java.linearna.test;



import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.IncompatibleOperandException;
import hr.fer.zemris.linearna.Matrix;
import hr.fer.zemris.linearna.MatrixSubMatrixView;
import hr.fer.zemris.linearna.MatrixTransposeView;
import hr.fer.zemris.linearna.UnmodifiableObjectException;
import hr.fer.zemris.linearna.Vector;
import hr.fer.zemris.linearna.VectorMatrixView;

import org.junit.Assert;
import org.junit.Test;

/**
 * Class which tests the functionality of classes developed for solving the 4. problem
 * in this homework concerning the matrices.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class MatrixTest {
	
	//MATRIX TESTS
	@Test (expected = IllegalArgumentException.class)
	public void matrixConstructorAndParse() {
		Matrix matrix = (Matrix) Matrix.parseSimple("2 4 6 | 1 3 5");
		Assert.assertEquals(matrix.getColsCount(), 3);
		
		@SuppressWarnings("unused")
		Matrix matrix2 = Matrix.parseSimple("");
	}
	
	@Test (expected = IllegalArgumentException.class) 
	public void matrixConstructorV1() {
		@SuppressWarnings("unused")
		Matrix matrix = new Matrix(-1, 2);
	}
	
	@Test (expected = IllegalArgumentException.class) 
	public void matrixConstructorV2() {
		@SuppressWarnings("unused")
		Matrix matrix = new Matrix(2, -1);
	}
	
	@Test 
	public void matrixConstructorV3() {
		double[][] array = new double[][] {{1, 2, 3}, {4, 5, 6}};
		Matrix matrix = new Matrix(2, 3, array, true);
		Assert.assertEquals(matrix.getColsCount(), 3);
	}
	
	@Test (expected = IllegalArgumentException.class) 
	public void matrixConstructorV4() {
		@SuppressWarnings("unused")
		Matrix matrix = new Matrix(2, 2, null, false);
	}
	
	@Test
	public void matrixCopy() {
		Matrix matrix = (Matrix) Matrix.parseSimple("2 4 6 | 1 3 5");
		Matrix matrix2 = (Matrix) matrix.copy();
		
		Assert.assertEquals(5.0, matrix2.get(1, 2), 0);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void matrixGetV1() {
		Matrix matrix = (Matrix) Matrix.parseSimple("2 4 6 | 1 3 5");
		
		matrix.get(-1, 1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void matrixGetV2() {
		Matrix matrix = (Matrix) Matrix.parseSimple("2 4 6 | 1 3 5");
		
		matrix.get(5, 2);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void matrixGetV3() {
		Matrix matrix = (Matrix) Matrix.parseSimple("2 4 6 | 1 3 5");
		
		matrix.get(1, -1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void matrixGetV4() {
		Matrix matrix = (Matrix) Matrix.parseSimple("2 4 6 | 1 3 5");
		
		matrix.get(2, 5);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void matrixSetV1() {
		Matrix matrix = (Matrix) Matrix.parseSimple("2 4 6 | 1 3 5");
		
		matrix.set(-1, 1, 10);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void matrixSetV2() {
		Matrix matrix = (Matrix) Matrix.parseSimple("2 4 6 | 1 3 5");
		
		matrix.set(5, 8, 10);
	}
	
	@Test (expected = UnmodifiableObjectException.class)
	public void matrixSetV3() {
		Matrix matrix = (Matrix) Matrix.parseSimple("2 4 6 | 1 3 5");
		
		matrix.set(1, 1, 10);
		Assert.assertEquals(10, matrix.get(1, 1), 0);
		matrix.notModifiable = true;
		matrix.set(1, 1, 10);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void matrixSetV4() {
		Matrix matrix = (Matrix) Matrix.parseSimple("2 4 6 | 1 3 5");
		
		matrix.set(1, -10, 10);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void matrixSetV5() {
		Matrix matrix = (Matrix) Matrix.parseSimple("2 4 6 | 1 3 5");
		
		matrix.set(1, 10, 10);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void matrixNewInstanceV1() {
		Matrix matrix = (Matrix) Matrix.parseSimple("2 4 6 | 1 3 5");
		
		Assert.assertEquals(matrix.newInstance(2, 5).getColsCount() == 5, true);
		matrix.newInstance(-1, 5);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void matrixNewInstanceV2() {
		Matrix matrix = (Matrix) Matrix.parseSimple("2 4 6 | 1 3 5");
		matrix.newInstance(5, -1);
	}
	
	@Test (expected = IncompatibleOperandException.class)
	public void matrixMakeIdentity() {
		Matrix matrix = (Matrix) Matrix.parseSimple("2 4 6 | 1 3 5 | 4 6 7");
		
		matrix = (Matrix) matrix.makeIdentity();
		Assert.assertEquals(1.0, matrix.get(1, 1), 0);
		
		Matrix matrix2 = (Matrix) Matrix.parseSimple("2 4 6 | 1 3 5 | 4 6 7 | 1 2 3");
		matrix2.makeIdentity();
	}
	
	@Test
	public void matrixScalarMultiply() {
		Matrix matrix = (Matrix) Matrix.parseSimple("2 4 6 | 1 3 5 | 4 6 7");
		
		matrix = (Matrix) matrix.nScalarMultiply(2);
		Assert.assertEquals(6.0, matrix.get(1, 1), 0);
		matrix.toString();
	}
	
	@SuppressWarnings("unused")
	@Test (expected = IncompatibleOperandException.class)
	public void matrixToVector() {
		Matrix matrix = (Matrix) Matrix.parseSimple("2 4 6 | 1 3 5 | 4 6 7");
		Matrix matrix1 = (Matrix) Matrix.parseSimple("2 4 6");
		Matrix matrix2 = (Matrix) Matrix.parseSimple("2 | 4 | 6");
		
		VectorMatrixView v1 = (VectorMatrixView) matrix1.toVector(true);
		
		Vector v2 = (Vector) matrix1.toVector(false);
		Vector v3 = (Vector) matrix2.toVector(false);
		
		
		matrix.toVector(false);
	}
	
	@Test
	public void matrixToArray() {
		Matrix matrix = (Matrix) Matrix.parseSimple("2 4 6 | 1 3 5 | 4 6 7");
		
		double[][] array = matrix.toArray();
		Assert.assertEquals(6.0, array[0][2], 0);
	}
	
	@Test
	public void matrixnInvert() {
		IMatrix a = Matrix.parseSimple("3 5 | 2 10"); 
		IMatrix r = Matrix.parseSimple("2 | 8");
		IMatrix v = a.nInvert().nMultiply(r);
		Assert.assertEquals(-1.0, v.get(0, 0), 0);
	}
	
	@Test 
	public void matrixSubmatrix() {
		Matrix matrix = (Matrix) Matrix.parseSimple("2 4 6 | 1 3 5 | 4 6 7");
		Matrix m2 = (Matrix) matrix.subMatrix(0, 0, false);
		Assert.assertEquals(3.0, m2.get(0, 0), 0);
		
		MatrixSubMatrixView view = (MatrixSubMatrixView) matrix.subMatrix(0, 0, true);
		Assert.assertEquals(3.0, view.get(0, 0), 0);
	}
	
	@Test (expected = IncompatibleOperandException.class)
	public void matrixDeterminant() {
		Matrix matrix = (Matrix) Matrix.parseSimple("2");
		Assert.assertEquals(2.0, matrix.determinant(), 0);
		
		Matrix matrix2 = (Matrix) Matrix.parseSimple("1 2 | 3 5");
		Assert.assertEquals(-1.0, matrix2.determinant(), 0);
		
		Matrix matrix4 = (Matrix) Matrix.parseSimple("1 2 2 | 3 5 5 | 4 5 6");
		matrix4.determinant();
		
		Matrix matrix3 = (Matrix) Matrix.parseSimple("1 2 | 3 5 | 4 5");
		matrix3.determinant();
		
	}
	
	@SuppressWarnings("unused")
	@Test (expected = IncompatibleOperandException.class)
	public void matrixnMultiply() {
		IMatrix a = Matrix.parseSimple("3 5 | 2 10"); 
		IMatrix r = Matrix.parseSimple("2 | 8");
		IMatrix v = a.nMultiply(r);

		IMatrix c = Matrix.parseSimple("2 | 8 | 6");
		a.nMultiply(c);
	}
	
	@SuppressWarnings("unused")
	@Test (expected = IncompatibleOperandException.class)
	public void matrixSubV1() {
		IMatrix a = Matrix.parseSimple("3 5 | 2 10"); 
		IMatrix r = Matrix.parseSimple("2 | 8");
		IMatrix v = a.nSub(r);
	}
	
	@SuppressWarnings("unused")
	@Test (expected = IncompatibleOperandException.class)
	public void matrixSubV2() {
		IMatrix a = Matrix.parseSimple("3 5 | 2 10"); 
		IMatrix r = Matrix.parseSimple("2 5 | 8 5 | 7 5");
		IMatrix v = a.nSub(r);
	}
	
	
	@Test 
	public void matrixSubV3() {
		IMatrix a = Matrix.parseSimple("3 5 | 2 10"); 
		IMatrix r = Matrix.parseSimple("2 5 | 8 5");
		IMatrix v = a.nSub(r);
		
		Assert.assertEquals(5.0, v.get(1, 1), 0);
	}
	
	@SuppressWarnings("unused")
	@Test (expected = IncompatibleOperandException.class)
	public void matrixAddV1() {
		IMatrix a = Matrix.parseSimple("3 5 | 2 10"); 
		IMatrix r = Matrix.parseSimple("2 | 8");
		IMatrix v = a.nAdd(r);
	}
	
	@SuppressWarnings("unused")
	@Test (expected = IncompatibleOperandException.class)
	public void matrixAddV2() {
		IMatrix a = Matrix.parseSimple("3 5 | 2 10"); 
		IMatrix r = Matrix.parseSimple("2 5 | 8 5 | 7 5");
		IMatrix v = a.nAdd(r);
	}
	
	
	@Test 
	public void matrixAddV3() {
		IMatrix a = Matrix.parseSimple("3 5 | 2 10"); 
		IMatrix r = Matrix.parseSimple("2 5 | 8 5");
		IMatrix v = a.nAdd(r);
		
		Assert.assertEquals(15.0, v.get(1, 1), 0);
	}
	
	@Test
	public void matrixTranspose() {
		IMatrix a = Matrix.parseSimple("3 5 | 2 10");
		MatrixTransposeView view = (MatrixTransposeView) a.nTranspose(true);
		Assert.assertEquals(5.0, view.get(1, 0), 0);
		
		Matrix b = (Matrix) a.nTranspose(false);
		Assert.assertEquals(5.0, b.get(1, 0), 0);
	}
	

}
