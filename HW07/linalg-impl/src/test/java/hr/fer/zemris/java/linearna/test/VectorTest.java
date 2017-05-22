package hr.fer.zemris.java.linearna.test;

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.IncompatibleOperandException;
import hr.fer.zemris.linearna.UnmodifiableObjectException;
import hr.fer.zemris.linearna.Vector;
import org.junit.Assert;
import org.junit.Test;

/**
 * Class which tests the functionality of classes developed for solving the 4. problem
 * in this homework concerning the vectors.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class VectorTest {
	
	//VECTOR TESTS
	@Test (expected = IllegalArgumentException.class)
	public void vectorConstructorAndParse() {
		Vector vector = Vector.parseSimple("2 3 5");
		Assert.assertEquals(vector.getDimension(), 3);
		
		@SuppressWarnings("unused")
		Vector vector2 = Vector.parseSimple("");
	}
	
	@Test (expected = IllegalArgumentException.class) 
	public void vectorConstructor() {
		@SuppressWarnings("unused")
		Vector vector = new Vector(null);
	}
	
	@Test 
	public void vectorConstructor2() {
		double[] array = new double[] {1, 2, 4};
		Vector vector = new Vector(true, true, array);
		Assert.assertEquals(vector.getDimension(), 3);
	}
	
	
	@Test (expected = IllegalArgumentException.class)
	public void vectorGetV1() {
		Vector vector = Vector.parseSimple("2 3 5");
		Assert.assertEquals(5.0, vector.get(2), 0);
		vector.get(-1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void vectorGetV2() {
		Vector vector = Vector.parseSimple("2 3 5");
		vector.get(4);
	}
	
	@Test
	public void vectorCopy() {
		Vector vector = Vector.parseSimple("2 3 5");
		Vector v2 = (Vector) vector.copy();
		
		Assert.assertEquals(5.0, v2.get(2), 0);
	}
	
	@Test
	public void vectorNewInstance() {
		Vector v1 = Vector.parseSimple("2 4 5");
		Vector v2 = (Vector) v1.newInstance(10);
		
		Assert.assertEquals(v2.getDimension(), 10);
	}
	
	@Test (expected = UnmodifiableObjectException.class)
	public void vectorSetV1() {
		double[] array = new double[] {1, 2, 4};
		Vector v1 = new Vector(true, false, array);
		v1.set(1, 6);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void vectorSetV2() {
		double[] array = new double[] {1, 2, 4};
		Vector v1 = new Vector(true, false, array);
		v1.set(5, 6);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void vectorSetV3() {
		double[] array = new double[] {1, 2, 4};
		Vector v1 = new Vector(true, false, array);
		v1.set(-1, 6);
	}
	
	@Test 
	public void vectorSetV4() {
		double[] array = new double[] {1, 2, 4};
		Vector v1 = new Vector(false, false, array);
		v1.set(1, 6);
		Assert.assertEquals(6.0, v1.get(1), 0);
	}
	
	@Test
	public void vectorToString() {
		Vector v1 = Vector.parseSimple("2 4 5");
		Assert.assertEquals(v1.toString().equals("[2,000, 4,000, 5,000]"), true);
	}
	
	@Test
	public void vectorToMatrix() {
		Vector v1 = Vector.parseSimple("2 4 5");
		IMatrix matrix = v1.toColumnMatrix(false);
		IMatrix matrix2 = v1.toRowMatrix(false);
		
		Assert.assertEquals(matrix.get(2, 0) == matrix2.get(0, 2), true);
	}
	
	@Test
	public void vectorToMatrixV2() {
		Vector v1 = Vector.parseSimple("2 4 5");
		IMatrix matrix = v1.toColumnMatrix(true);
		IMatrix matrix2 = v1.toRowMatrix(true);
		
		Assert.assertEquals(matrix.get(2, 0) == matrix2.get(0, 2), true);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void vectorCopyPart() {
		Vector v1 = Vector.parseSimple("2 4 5");
		Vector v2 = (Vector) v1.copyPart(2);
		Vector v3 = (Vector) v1.copyPart(8);
		
		
		Assert.assertEquals(4.0, v2.get(1), 0);
		Assert.assertEquals(0, v3.get(5), 0);
		
		v1.copyPart(-9);
	}
	
	@Test
	public void vectorToArray() {
		Vector v1 = Vector.parseSimple("2 4 5");
		Assert.assertEquals(4.0, v1.toArray()[1], 0);
	}
	
	@Test
	public void vectorFromHomogenous() {
		Vector v1 = Vector.parseSimple("10 8 2");
		Vector v2 = (Vector) v1.nFromHomogeneus();
		Assert.assertEquals(v2.getDimension(), 2);
		Assert.assertEquals(5.0, v2.get(0), 0);
	}
	
	@Test (expected = IncompatibleOperandException.class)
	public void vectorVectorProduct() {
		Vector v1 = Vector.parseSimple("2 4 8");
		Vector v2 = Vector.parseSimple("2 3 6");
		Vector v3 = (Vector) v1.nVectorProduct(v2);
		
		Assert.assertEquals(-2.0, v3.get(2), 0);
		
		Vector v4 = Vector.parseSimple("2 3 6 8 10");
		
		v1.nVectorProduct(v4);	
	}
	
	@Test (expected = IncompatibleOperandException.class)
	public void vectorVectorProductV2() {
		Vector v1 = Vector.parseSimple("2 4 8 9");
		Vector v2 = Vector.parseSimple("2 3 6");
		
		v1.nVectorProduct(v2);
	}
	
	@Test (expected = IncompatibleOperandException.class)
	public void vectorScalarProduct() {
		Vector v1 = Vector.parseSimple("2 4 8");
		Vector v2 = Vector.parseSimple("2 3 6");
		
		Assert.assertEquals(64.0, v1.scalarProduct(v2), 0);
		
		Vector v4 = Vector.parseSimple("2 3 6 8 10");
		
		v1.scalarProduct(v4);	
	}
	
	@Test (expected = IncompatibleOperandException.class)
	public void vectorNormalize() {
		Vector v1 = Vector.parseSimple("2 4 8");
		Vector v2 = Vector.parseSimple("0 0 0");
		
		v1 = (Vector) v1.nNormalize();
		v2 = (Vector) v2.nNormalize();
		
		Vector v4 = Vector.parseSimple("2 3 6 8 10");
		v1.cosine(v4);
	}
	
	@Test
	public void vectorScalarMultipy() {
		Vector v1 = Vector.parseSimple("2 4 8");
		
		v1 = (Vector) v1.nScalarMultiply(2);
		Assert.assertEquals(8.0, v1.get(1), 0);
	}
	
	@Test (expected = IncompatibleOperandException.class)
	public void vectorSub() {
		Vector v1 = Vector.parseSimple("2 4 8");
		Vector v2 = Vector.parseSimple("1 2 4");
		Vector v3 = Vector.parseSimple("1 2 4 8");
		
		v1 = (Vector) v1.nSub(v2);
		Assert.assertEquals(2.0, v1.get(1), 0);
		v1.nSub(v3);
	}
	
	@Test (expected = IncompatibleOperandException.class)
	public void vectorAdd() {
		Vector v1 = Vector.parseSimple("2 4 8");
		Vector v2 = Vector.parseSimple("1 2 4");
		Vector v3 = Vector.parseSimple("1 2 4 8");
		
		v1 = (Vector) v1.nAdd(v2);
		Assert.assertEquals(6.0, v1.get(1), 0);
		v1.nAdd(v3);
	}
	

}
