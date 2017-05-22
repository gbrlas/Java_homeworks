package hr.fer.zemris.java.tecaj.hw3;


import java.util.Arrays;

import org.junit.Test;
import org.junit.Assert;

import static org.junit.Assert.assertEquals;

/**
 * Class which contains methods for CString testing by using junit.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class CStringTest {
	
	//constructors
	@Test
	public void constructorWhichRecievesAnotherCStringWithLargerThanNeededArray () {
		char[] temp = new char[10];
		temp[0] = '1';
		temp[1] = '2';
		CString c2 = new CString(temp);
		CString c1 = new CString(c2);
		Assert.assertTrue(c1.toString().equals("12"));
	}
	
	@Test
	public void constructorWhichRecievesAnotherCStringWithCorrectArraySize () {
		char[] temp = new char[2];
		temp[0] = '1';
		temp[1] = '2';
		CString c2 = new CString(temp);
		CString c1 = new CString(c2);
		Assert.assertTrue(c1.toString().equals("12"));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void constructorWhichRecievesNegativeOffsetShouldThrowAnException () {
		char[] temp = new char[2];
		temp[0] = '1';
		temp[1] = '2';
		CString c1 = new CString(-1, 10, temp);
		System.out.println(c1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void constructorWhichRecievesNegativeLengthShouldThrowAnException () {
		char[] temp = new char[2];
		temp[0] = '1';
		temp[1] = '2';
		CString c1 = new CString(10, -1, temp);
		System.out.println(c1);
	}
	
	@Test
	public void constructorWithLengthLargerThanArraySizeShouldCreateLargerArray () {
		char[] temp = new char[2];
		temp[0] = '1';
		temp[1] = '2';
		CString c1 = new CString(0, 10, temp);
		Assert.assertTrue(c1.length() == 10);
	}
	
	
	
	//length()
	@Test
	public void lenghtShouldReturnTheCorrectLengthOfTheCString () {
		CString c = new CString("test");
		assertEquals(4, c.length());
	}
	
	//charAt()
	@Test (expected = IndexOutOfBoundsException.class)
	public void charAtWithIndexBelow0ShouldThrowAnException () {
		CString c = new CString("");
		c.charAt(-1);
	}
	
	@Test (expected = IndexOutOfBoundsException.class)
	public void charAtWithIndexHigherThanLengthShouldThrowAnException () {
		CString c = new CString("abc");
		c.charAt(4);
	}
	
	@Test
	public void charAtShouldReturnTheCorrectCharacter () {
		CString c = new CString("abc");
		assertEquals('b', c.charAt(1));
	}
	
	//toCharArray()
	@Test
	public void toCharArrayShouldReturnTheCorrectArray () {
		CString c = new CString("abc");
		char[] temp = new char[]{'a', 'b', 'c'};
		Assert.assertTrue(Arrays.equals(temp, c.toCharArray()));
	}
	
	//toString()
	@Test
	public void toStringShouldReturnTheCorrectString () {
		char[] temp = new char[] {'a', 'b', 'c'};
		CString c = new CString(temp);
		assertEquals("abc", c.toString());
	}
	
	//indexOf()
	@Test
	public void indexOfShouldReturnNegativeIfCharacterNotInString () {
		CString c = new CString("abc");
		assertEquals(-1, c.indexOf('e'));
	}
	
	@Test
	public void indexOfShouldReturnCorrectIndexIfCharacterIsInTheString () {
		CString c = new CString("abc");
		assertEquals(0, c.indexOf('a'));
	}
	
	//startsWith()
	@Test (expected = IllegalArgumentException.class)
	public void StartsWithShouldThrowAnExceptionIfTheGivenStringIsEmptyAndCallerIsNot () {
		CString c = new CString("abc");
		c.startsWith(new CString(""));
	}
	
	@Test
	public void startsWithShouldReturnFalseIfGivenStringIsBiggerThanTheCaller () {
		CString c = new CString("abc");
		Assert.assertFalse(c.startsWith(new CString("abcde")));
	}
	
	@Test
	public void startsWithShouldReturnFalseIfTheCallerDoesntStartWithTheGivenString () {
		CString c = new CString("abc");
		Assert.assertFalse(c.startsWith(new CString("b")));
	}
	
	@Test
	public void startsWithShouldReturnTrueIfTheCallerStartsWithTheGivenString () {
		CString c = new CString("abc");
		Assert.assertTrue(c.startsWith(new CString("ab")));
	}
	
	//endsWith()
	@Test (expected = IllegalArgumentException.class)
	public void EndsWithShouldThrowAnExceptionIfTheGivenStringIsEmptyAndCallerIsNot () {
		CString c = new CString("abc");
		c.endsWith(new CString(""));
	}
	
	@Test
	public void endsWithShouldReturnFalseIfGivenStringIsBiggerThanTheCaller () {
		CString c = new CString("abc");
		Assert.assertFalse(c.endsWith(new CString("abcde")));
	}
	
	@Test
	public void endsWithShouldReturnFalseIfTheCallerDoesntEndWithTheGivenString () {
		CString c = new CString("abc");
		Assert.assertFalse(c.endsWith(new CString("b")));
	}
	
	@Test
	public void endsWithShouldReturnTrueIfTheCallerEndsWithTheGivenString () {
		CString c = new CString("abc");
		Assert.assertTrue(c.endsWith(new CString("bc")));
	}
	
	//contains()
	@Test (expected = IllegalArgumentException.class)
	public void containsShouldThrowAnExceptionIfTheGivenStringIsEmptyAndCallerIsNot () {
		CString c = new CString("abc");
		c.contains(new CString(""));
	}
	
	@Test
	public void containsShouldReturnFalseIfTheCallerDoesntContainTheGivenString () {
		CString c = new CString("abc");
		Assert.assertFalse(c.contains(new CString("efg")));
	}
	
	@Test
	public void containsShouldReturnFalseIfTheCallerDoesntContainTheGivenStringV2 () {
		CString c = new CString("abc");
		Assert.assertFalse(c.contains(new CString("be")));
	}
	
	@Test
	public void containsShouldReturnTrueIfTheCallerContainsTheGivenString () {
		CString c = new CString("abc");
		Assert.assertTrue(c.contains(new CString("bc")));
	}
	
	//substring()
	@Test (expected = IndexOutOfBoundsException.class)
	public void ifSubstringStartingIndexIsGreaterThanEndingIndexShouldThrowAnException () {
		CString c = new CString("abc");
		c.substring(1, 0);
	}
	
	@Test (expected = IndexOutOfBoundsException.class)
	public void ifSubstringEndingIndexIsGreaterThanCallerLengthShouldThrowAnException () {
		CString c = new CString("abc");
		c.substring(0, 5);
	}
	
	@Test (expected = IndexOutOfBoundsException.class)
	public void ifSubstringStartingIndexIsLowerThanZeroShouldThrowAnException () {
		CString c = new CString("abc");
		c.substring(-1, 2);
	}
	
	@Test
	public void substringShouldReturnCorrectSubstring () {
		CString c = new CString("abcdef");
		Assert.assertTrue(c.substring(0, 3).toString().equals("abc"));
	}
	
	//left()
	@Test (expected = IndexOutOfBoundsException.class)
	public void leftGivenValueIsLowerThanZeroShouldThrowAnException () {
		CString c = new CString("abc");
		c.left(-1);
	}
	
	@Test (expected = IndexOutOfBoundsException.class)
	public void leftGivenValueIsGreaterThanCallerLengthShouldThrowAnException () {
		CString c = new CString("abc");
		c.left(5);
	}
	
	@Test 
	public void leftShouldReturnCorrectSubstring () {
		CString c = new CString("abc");
		Assert.assertTrue(c.left(2).toString().equals("ab"));
	}
	
	//right()
	@Test (expected = IndexOutOfBoundsException.class)
	public void rightGivenValueIsLowerThanZeroShouldThrowAnException () {
		CString c = new CString("abc");
		c.right(-1);
	}
	
	@Test (expected = IndexOutOfBoundsException.class)
	public void rightGivenValueIsGreaterThanCallerLengthShouldThrowAnException () {
		CString c = new CString("abc");
		c.right(5);
	}
	
	@Test 
	public void rightShouldReturnCorrectSubstring () {
		CString c = new CString("abc");
		Assert.assertTrue(c.right(2).toString().equals("bc"));
	}
	
	//add()
	@Test
	public void addShouldReturnCorrectSubstring () {
		CString c = new CString("abc");
		Assert.assertTrue(c.add(new CString("def")).toString().equals("abcdef"));
	}
	
	//replaceAll() - char
	@Test
	public void replaceAllShouldNotReplaceNotExistingCharacterIfPassedIn () {
		CString c = new CString("abcabc");
		Assert.assertTrue(c.replaceAll('d', 'g').toString().equals("abcabc"));
	}
	
	@Test
	public void replaceAllShouldReplaceAllCharactersIfCorrectCharacterIsPassedIn () {
		CString c = new CString("abcabc");
		Assert.assertTrue(c.replaceAll('c', 'e').toString().equals("abeabe"));
	}
	
	//replaceAll() - string
	@Test (expected = IllegalArgumentException.class)
	public void replaceAllIfOldStringBiggerThanCallerShouldThrowAnException () {
		CString c = new CString("abcabc");
		c.replaceAll(new CString("abcdefghij"), new CString("a"));
	}
	
	@Test
	public void replaceAllShouldNotReplaceNotExistingSubstringIfPassedIn () {
		CString c = new CString("abcabc");
		Assert.assertTrue(c.replaceAll(new CString("abe"), new CString("efg")).toString().equals("abcabc"));
	}
	
	@Test
	public void replaceAllShouldReplaceAllSubstringsIfCorrectCharacterIsPassedIn () {
		CString c = new CString("abcabc");
		Assert.assertTrue(c.replaceAll(new CString("abc"), new CString("efg")).toString().equals("efgefg"));
	}
}
