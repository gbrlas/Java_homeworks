package hr.fer.zemris.java.student0036476746.hw06.Multistack;

import hr.fer.zemris.java.custom.scripting.exec.ObjectMultistack;
import hr.fer.zemris.java.custom.scripting.exec.ObjectMultistack.MultistackEntry;
import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

import org.junit.Assert;
import org.junit.Test;

/**
 * Class which junit tests the ObjectMultistack class.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class ObjectMultiStackTest {
	
	//valueWrapper class tests
	
	/**
	 * Tests the constructor, getters and setters of the valueWrapper class.
	 */
	@Test
	public void valueWrapperV1 () {
		ValueWrapper w1 = new ValueWrapper(2.0);
		ValueWrapper w2 = new ValueWrapper(null);
		Assert.assertEquals((Double) w1.getValue(), Double.valueOf(2.0));
		Assert.assertEquals((Integer) w2.getValue(), Integer.valueOf(0));
		
		w1.setValue("test");
		Assert.assertEquals((String) w1.getValue(), "test");
	}
	
	/**
	 * Tests the increment method if the wrapper value is Integer.
	 */
	@Test
	public void valueWrapperIncrementV1 () {
		ValueWrapper w1 = new ValueWrapper(2);
		w1.increment(null);
		w1.increment(2);
		Assert.assertEquals((Integer) w1.getValue(), Integer.valueOf(4));
		w1.increment("5.3");
		Assert.assertEquals((Double) w1.getValue(), Double.valueOf(9.3));
		w1.increment(4.5);
		Assert.assertEquals((Double) w1.getValue(), Double.valueOf(13.8));
	}
	
	/**
	 * Tests the increment method if the wrapper value is Double.
	 */
	@Test
	public void valueWrapperIncrementV2 () {
		ValueWrapper w1 = new ValueWrapper(2.5);
		w1.increment(2);
		Assert.assertEquals((Double) w1.getValue(), Double.valueOf(4.5));
		w1.increment(5.3);
		Assert.assertEquals((Double) w1.getValue(), Double.valueOf(9.8));
		w1.increment("4.5");
		Assert.assertEquals((Double) w1.getValue(), Double.valueOf(14.3));
	}
	
	/**
	 * Tests the increment method if the wrapper value is String.
	 */
	@Test
	public void valueWrapperIncrementV3 () {
		ValueWrapper w1 = new ValueWrapper("2");
		w1.increment("2");
		Assert.assertEquals((Integer) w1.getValue(), Integer.valueOf(4));
		w1.increment(5.3);
		Assert.assertEquals((Double) w1.getValue(), Double.valueOf(9.3));
		w1.increment("4,5");
		Assert.assertEquals((Double) w1.getValue(), Double.valueOf(13.8));
	}
	
	/**
	 * Tests the decrement method if the wrapper value is Integer.
	 */
	@Test
	public void valueWrapperDecrementV1 () {
		ValueWrapper w1 = new ValueWrapper(10);
		w1.decrement(null);
		w1.decrement(2);
		Assert.assertEquals((Integer) w1.getValue(), Integer.valueOf(8));
		w1.decrement(5.3);
		Assert.assertEquals((Double) w1.getValue(), Double.valueOf(2.7));
		w1.decrement("2.7");
		Assert.assertEquals((Double) w1.getValue(), Double.valueOf(0));
	}
	
	/**
	 * Tests the decrement method if the wrapper value is Double.
	 */
	@Test
	public void valueWrapperDecrementV2 () {
		ValueWrapper w1 = new ValueWrapper(10.0);
		w1.decrement(2);
		Assert.assertEquals((Double) w1.getValue(), Double.valueOf(8));
		w1.decrement(5.3);
		Assert.assertEquals((Double) w1.getValue(), Double.valueOf(2.7));
		w1.decrement("2.7");
		Assert.assertEquals((Double) w1.getValue(), Double.valueOf(0));
	}
	
	/**
	 * Tests the decrement method if the wrapper value is String.
	 */
	@Test
	public void valueWrapperDecrementV3 () {
		ValueWrapper w1 = new ValueWrapper("10");
		w1.decrement(2);
		Assert.assertEquals((Integer) w1.getValue(), Integer.valueOf(8));
		w1.decrement(5.3);
		Assert.assertEquals((Double) w1.getValue(), Double.valueOf(2.7));
		w1.decrement("2.7");
		Assert.assertEquals((Double) w1.getValue(), Double.valueOf(0));
	}
	
	
	/**
	 * Tests the multiply method if the wrapper value is Integer.
	 */
	@Test
	public void valueWrapperMultiplyV1 () {
		ValueWrapper w1 = new ValueWrapper(10);
		w1.multiply("1");
		w1.multiply(2);
		Assert.assertEquals((Integer) w1.getValue(), Integer.valueOf(20));
		w1.multiply(2.0);
		Assert.assertEquals((Double) w1.getValue(), Double.valueOf(40.0));
		w1.multiply("2");
		Assert.assertEquals((Double) w1.getValue(), Double.valueOf(80.0));
		w1.multiply(null);
	}
	
	/**
	 * Tests the multiply method if the wrapper value is Double.
	 */
	@Test
	public void valueWrapperMultiplyV2 () {
		ValueWrapper w1 = new ValueWrapper(10.0);
		w1.multiply(2);
		Assert.assertEquals((Double) w1.getValue(), Double.valueOf(20));
		w1.multiply(2.0);
		Assert.assertEquals((Double) w1.getValue(), Double.valueOf(40.0));
		w1.multiply("2");
		Assert.assertEquals((Double) w1.getValue(), Double.valueOf(80.0));
	}
	
	/**
	 * Tests the multiply method if the wrapper value is String.
	 */
	@Test
	public void valueWrapperMultiplyV3 () {
		ValueWrapper w1 = new ValueWrapper("10");
		w1.multiply("1.0");
		w1.multiply(2);
		Assert.assertEquals((Double) w1.getValue(), Double.valueOf(20));
		w1.multiply("1.0");
		w1.multiply(2.0);
		Assert.assertEquals((Double) w1.getValue(), Double.valueOf(40.0));
		w1.multiply("2");
		Assert.assertEquals((Double) w1.getValue(), Double.valueOf(80.0));
	}
	
	
	/**
	 * Tests the divide method if the wrapper value is Integer.
	 */
	@Test
	public void valueWrapperDivideV1 () {
		ValueWrapper w1 = new ValueWrapper(1000);
		w1.divide(10);
		Assert.assertEquals((Integer) w1.getValue(), Integer.valueOf(100));
		w1.divide(10.0);
		Assert.assertEquals((Double) w1.getValue(), Double.valueOf(10.0));
		w1.divide("5");
		Assert.assertEquals((Double) w1.getValue(), Double.valueOf(2.0));
		w1.divide(null);
	}
	
	/**
	 * Tests the divide method if the wrapper value is Double.
	 */
	@Test
	public void valueWrapperDivideV2 () {
		ValueWrapper w1 = new ValueWrapper(1000.0);
		w1.divide(10);
		Assert.assertEquals((Double) w1.getValue(), Double.valueOf(100));
		w1.divide(10.0);
		Assert.assertEquals((Double) w1.getValue(), Double.valueOf(10.0));
		w1.divide("5");
		Assert.assertEquals((Double) w1.getValue(), Double.valueOf(2.0));
	}
	
	/**
	 * Tests the divide method if the wrapper value is String.
	 */
	@Test
	public void valueWrapperDivideV3 () {
		ValueWrapper w1 = new ValueWrapper("1000");
		w1.divide(10);
		Assert.assertEquals((Integer) w1.getValue(), Integer.valueOf(100));
		w1.divide(10.0);
		Assert.assertEquals((Double) w1.getValue(), Double.valueOf(10.0));
		w1.divide("5");
		Assert.assertEquals((Double) w1.getValue(), Double.valueOf(2.0));
	}
	
	/**
	 * Tests the divide method if the wrapper value is String.
	 */
	@Test
	public void valueWrapperDivideV4 () {
		ValueWrapper w1 = new ValueWrapper("1000");
		ValueWrapper w2 = new ValueWrapper(25.3);
		w1.divide(0);
		Assert.assertEquals((Integer) w1.getValue(), null);
		w2.divide(0.0);
		Assert.assertEquals((Integer) w2.getValue(), null);
	}
	
	/**
	 * Tests the numCompare method if the wrapper value is Integer.
	 */
	@Test
	public void valueWrapperNumCompareV1 () {
		ValueWrapper w1 = new ValueWrapper(1000);
		Assert.assertEquals(w1.numCompare(Double.valueOf(100)) > 0, true);
		Assert.assertEquals(w1.numCompare(null) > 0, true);
		Assert.assertEquals(w1.numCompare(Double.valueOf("100")) > 0, true);
		Assert.assertEquals(w1.numCompare(Integer.valueOf(10000)) < 0, true);
	}
	
	/**
	 * Tests the numCompare method if the wrapper value is Double.
	 */
	@Test
	public void valueWrapperNumCompareV2 () {
		ValueWrapper w1 = new ValueWrapper(1000.0);
		Assert.assertEquals(w1.numCompare(Double.valueOf(100)) > 0, true);
		Assert.assertEquals(w1.numCompare(Double.valueOf("100")) > 0, true);
		Assert.assertEquals(w1.numCompare(Integer.valueOf(10000)) < 0, true);
	}
	
	/**
	 * Tests the numCompare method if the wrapper value is String.
	 */
	@Test
	public void valueWrapperNumCompareV3 () {
		ValueWrapper w1 = new ValueWrapper("1000");
		Assert.assertEquals(w1.numCompare(Double.valueOf(100)) > 0, true);
		Assert.assertEquals(w1.numCompare(Double.valueOf("100")) > 0, true);
		Assert.assertEquals(w1.numCompare(Integer.valueOf(10000)) < 0, true);
	}
	
	
	//ObjectMultistack class test
	/**
	 * Tests the MultistackEntry class.
	 */
	@Test
	public void multistackEntryV1 () {
		ValueWrapper w1 = new ValueWrapper(1000);
		MultistackEntry entry = new MultistackEntry(w1, null);
		Assert.assertEquals(entry.getValue().numCompare(Double.valueOf(1000)), 0);
		
		ValueWrapper w2 = new ValueWrapper(10000);
		MultistackEntry entry2 = new MultistackEntry(w2, entry);
		Assert.assertEquals(entry2.getNext(), entry);
		
		entry.setValue(new ValueWrapper(10000));
		Assert.assertEquals(entry.getValue().numCompare(Integer.valueOf(10000)), 0);
	}
	
	/**
	 * Tests the push method with null argument.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void objectMultistackPushV1 () {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper w1 = new ValueWrapper(1000);
		multistack.push(null, w1);
	}
	
	/**
	 * Tests the push method with valid arguments.
	 */
	@Test
	public void objectMultistackPushV2 () {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper w1 = new ValueWrapper(1000);
		multistack.push("test", w1);
		multistack.push("test", new ValueWrapper("5000"));
	}
	
	/**
	 * Tests the pop method with null argument.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void objectMultistackPopV1 () {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.pop(null);
	}
	
	/**
	 * Tests the pop method with valid arguments.
	 */
	@Test
	public void objectMultistackPopV2 () {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper w1 = new ValueWrapper(1000);
		multistack.push("test", w1);
		multistack.push("test", new ValueWrapper(10000));
		
		Assert.assertEquals(multistack.pop("test").numCompare(Double.valueOf(10000)) == 0, true);
		multistack.pop("test");
		Assert.assertEquals(multistack.pop("test") == null, true);
	}
	
	/**
	 * Tests the peek method with null argument.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void objectMultistackPeekV1 () {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.peek(null);
	}
	
	/**
	 * Tests the peek method with valid arguments.
	 */
	@Test
	public void objectMultistackPeekV2 () {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper w1 = new ValueWrapper(1000);
		multistack.push("test", w1);
		
		Assert.assertEquals(multistack.peek("test").numCompare(Double.valueOf(1000)) == 0, true);
		Assert.assertEquals(multistack.peek("test25") == null, true);
	}
	
	/**
	 * Tests the isEmpty method with null argument.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void objectMultistackIsEmptyV1 () {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.isEmpty(null);
	}
	
	/**
	 * Tests the isEmpty method with valid arguments.
	 */
	@Test
	public void objectMultistackIsEmptyV2 () {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper w1 = new ValueWrapper(1000);
		multistack.push("test", w1);
		Assert.assertEquals(multistack.isEmpty("test"), false);
	}
}
