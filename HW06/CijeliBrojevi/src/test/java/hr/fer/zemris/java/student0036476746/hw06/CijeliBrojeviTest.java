package hr.fer.zemris.java.student0036476746.hw06;
import org.junit.Assert;
import org.junit.Test;

/**
 * Class which junit tests the {@linkplain CijeliBrojevi} class.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class CijeliBrojeviTest {
	
	@Test
	public void isOddV1 () {
		Assert.assertEquals(CijeliBrojevi.isOdd(7), true);
	}
	
	@Test
	public void isOddV2 () {
		Assert.assertEquals(CijeliBrojevi.isOdd(8), false);
	}
	
	@Test
	public void isEvenV1 () {
		Assert.assertEquals(CijeliBrojevi.isEven(8), true);
	}
	
	@Test
	public void isEvenV2 () {
		Assert.assertEquals(CijeliBrojevi.isEven(7), false);
	}
	
	@Test
	public void isPrimeV1 () {
		Assert.assertEquals(CijeliBrojevi.isPrime(8), false);
	}
	
	@Test
	public void isPrimeV2 () {
		Assert.assertEquals(CijeliBrojevi.isPrime(7), true);
	}
}
