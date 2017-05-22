package hr.fer.zemris.java.student0036476746.hw07.crypto;

import javax.xml.bind.DatatypeConverter;

import org.junit.Assert;
import org.junit.Test;

/**
 * Class which tests the hexToByte and byteToHex methods in the Crypto class.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class HexToByteTest {
	
	@Test
	public void test() {
		byte[] array = Crypto.hextobyte("A1");
		
		
		Assert.assertEquals("A1".equals(Crypto.byteToHex(array).toUpperCase()), true);
		Assert.assertEquals(
				Crypto.byteToHex(array).toUpperCase()
						.equals(DatatypeConverter.printHexBinary(array)), true);
	}
}
