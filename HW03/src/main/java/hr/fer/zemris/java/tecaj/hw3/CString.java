package hr.fer.zemris.java.tecaj.hw3;



/**
 * Class which offers similar functionality as the old official implementation of the String class.
 * It represents unmodifiable strings on which substring methods are executed in O(1) complexity,
 * which is achieved by sharing the character array.
 * 
 * @author Goran Brlas
 * @version 1.0
 * 
 */
public class CString {
	/**
	 * Private CString character array containing string elements.
	 */
	private final char[] data;
	/**
	 * Offset of the string, can range from 0 to length - 1.
	 */
	private final int offset;
	/**
	 * Length of the string.
	 */
	private final int length;
	
	/**
	 * Constructor which sets the data, offset, and length values according to the given character array.
	 * @param data Character array.
	 */
	public CString (char... data) {
		this.data = data.clone();
		offset = 0;
		length = data.length;
	}
	
	/**
	 * Constructor which sets the data, offset, and length values according to the given character array,
	 * offset and length.
	 * @param data Character array.
	 * @param offset String offset.
	 * @param length String length.
	 * @throws IllegalArgumentException if the substring length is bigger than the caller's or the offset or length are negative.
	 */
	public CString (int offset, int length, char... data) {
		this(offset, length, 0, data);
	}
	
	/**
	 * Private constructor which sets the data, offset, and length values according to the given character array,
	 * offset and length.
	 * If called by a user, the given char array will be copied.
	 * If called by a substring(), left() or right() the same char array will be referenced.
	 * @param offset String offset.
	 * @param length String length.
	 * @param caller Defines the method caller - can be 0(the user) or 1(substring, left or right).
	 * @param data Character array.
	 * @throws IllegalArgumentException if the offset or length are negative.
	 */
	private CString (int offset, int length, int caller, char... data) throws IllegalArgumentException {
		if (offset < 0 || length < 0) {
			throw new IllegalArgumentException("Offset and length cannot be negative!");
		}
		//called by the user
		if (caller == 0) {
			this.length = length;
			this.data = new char[length];
			this.offset = 0;
			
			for (int i = 0; i < data.length; i++) {
				this.data[i] = data[i];
			}
		//called by a substring, left or right
		} else {
			this.data = data;
			this.offset = offset;
			this.length = length;
		}
	}
	
	/**
	 * Allocates its own character array of minimal required size and copies data if original's array is 
	 * larger than needed, otherwise it reuses the original's character array.
	 * @param original Original string.
	 */
	public CString (CString original) {
		int i;
		
		//get the index of the last valid element
		for (i = 0; i < original.length(); i++) {
			if (original.charAt(i) == 0) {
				break;
			}
		}
		
		if (i == original.length()) {
			this.data = original.data;
			offset = 0;
			length = original.length();
		} else {
			//if there are empty cells create a new char array
			char[] data2 = new char[i];
			for (int j = 0; j < i; j++) {
				data2[j] = original.charAt(j);
			}
			
			this.data = data2;
			offset = 0;
			length = data2.length;
		}
	}
	
	/**
	 * Represents same character data as Java's String.
	 * @param str String 
	 */
	public CString (String str) {
		data = str.toCharArray();
		offset = 0;
		length = str.length();
	}
	
	/**
	 * Returns integer value representing CString length.
	 * @return Length of the CString.
	 */
	public int length () {
		return length;
	}
	
	/**
	 * Returns the character at the given index.
	 * @param index Index of the character.
	 * @return Character at given index.
	 * @throws IndexOutOfBoundsException if index is lower than 0 or greater than length.
	 */
	public char charAt (int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= length) {
			throw new IndexOutOfBoundsException("Index must be between 0 and length-1 !");
		}
		
		return data[offset + index];
	}
	
	/**
	 * Returns the character array which contains the characters of the CString.
	 * @return Character array.
	 */
	public char[] toCharArray () {
		char[] temp = new char[length];
		
		for (int i = offset; i < offset + length; i++) {
			temp[i] = data[i];
		}
		
		return temp;
	}
	
	@Override
	/**
	 * Returns the string representation of the CString. Ovverides java.lang.Object.toString().
	 */
	public String toString () {
		StringBuilder builder = new StringBuilder();
		
		for (int i = offset; i < offset + length; i++) {
			builder.append(data[i]);
		}
		
		return builder.toString();
	}
	
	/**
	 * Returns the index of the character's first occurrence or -1 if character is not found.
	 * @param character Character whose index is being searched for.
	 * @return Index of the character's first occurrence.
	 */
	public int indexOf (char character) {
		int test = -1;
		
		for (int i = offset; i < offset + length; i++) {
			if (data[i] == character) {
				test = i;
				break;
			}
		}
		
		return test;
	}
	
	/**
	 * Returns true if this string begins with the given string, false otherwise.
	 * @param str String to be checked.
	 * @return Boolean representation of whether the CString starts with the given string.
	 * @throws IllegalArgumentException if given string is empty and CString is not empty.
	 */
	public boolean startsWith (CString str) throws IllegalArgumentException {
		if (str.length() == 0 && length != 0) {
			throw new IllegalArgumentException("String must not be empty!");
		} else if (str.length > length) {
			return false;
		}
		
		boolean temp = true;
		
		for (int i = offset, len = str.length(), j = 0; j < len; i++, j++) {
			if (data[i] != str.charAt(j)) {
				temp = false;
				break;
			}
		}
		
		return temp;
	}
	
	/**
	 * Returns true if this string ends with the given string, false otherwise.
	 * @param str String to be checked.
	 * @return Boolean representation of whether the CString ends with the given string.
	 * @throws IllegalArgumentException if given string is empty and CString is not empty.
	 */
	public boolean endsWith (CString str) throws IllegalArgumentException {
		if (str.length() == 0 && length != 0) {
			throw new IllegalArgumentException("String must not be empty!");
		}else if (str.length > length) {
			return false;
		}
		
		
		boolean temp = true;
		
		for (int i = offset + length - 1, k = str.length() - 1; k >= 0; k--, i--) {
			if (data[i] != str.charAt(k)) {
				temp = false;
				break;
			}
		}
		
		return temp;
	}
	
	/**
	 * Returns true if this string contains the given string, false otherwise.
	 * @param str String to be checked.
	 * @return Boolean representation of whether the CString contains the given string.
	 * @throws IllegalArgumentException if given string is empty and CString is not empty.
	 */
	public boolean contains (CString str) throws IllegalArgumentException {
		if (str.length() == 0 && length != 0) {
			throw new IllegalArgumentException("String must not be empty!");
		}
		
		final int MIN_NUMBER_OF_SUBSTRINGS = 1;
		
		int test = 1;
		int counter = 0;
		
		for (int i = offset, k = 0; i < offset + length; i++) {
			if (data[i] == str.charAt(0)) {
				for (int j = i; k < str.length(); k++, j++) {
					if (data[j] != str.charAt(k) || j >= offset + length) {
						test = 0;
						break;
					}
				}
				if (test == MIN_NUMBER_OF_SUBSTRINGS) {
					counter++;
					break;
				} else {
					k = 0;
					test = 0;
				}
			}
		}
		
		return counter != 0;
	}
	
	/**
	 * Creates new CString which represents the substring with the given startIndex and endIndex.
	 * @param startIndex Starting index of the substring.
	 * @param endIndex Ending index of the substring.
	 * @return New CString which represents the substring.
	 * @throws IndexOutOfBoundsException if index not between 0 and length-1 or endIndex lower than startIndex!
	 */
	public CString substring (int startIndex, int endIndex) throws IndexOutOfBoundsException {
		if (startIndex < 0 || endIndex > length || endIndex < startIndex) {
			throw new IndexOutOfBoundsException("Index must be between 0 and length-1 and endIndex must be greater than startIndex!");
		}
		
		return new CString(offset + startIndex, endIndex - startIndex, 1, data);
	}
	
	/**
	 * Returns new CString which represents starting part of original string and is of length n.
	 * @param num Length of the new string.
	 * @return New CString.
	 * @throws IndexOutOfBoundsException if n is not between 1 and length-1.
	 */
	public CString left (int num) throws IndexOutOfBoundsException {
		if (num <= 0 || num >= length) {
			throw new IndexOutOfBoundsException("Index must be between 1 and length-1");
		}
		
		return new CString(offset, num, 1, data);
	}
	
	/**
	 * Returns new CString which represents ending part of original string and is of length n.
	 * @param num Length of the new string.
	 * @return New CString.
	 * @throws IndexOutOfBoundsException if n is not between 0 and length-1.
	 */
	public CString right (int num) throws IndexOutOfBoundsException {
		if (num < 0 || num > length) {
			throw new IndexOutOfBoundsException("Index must be between 0 and length-1");
		}
		
		return new CString(offset + length - num, num, 1, data);
	}
	
	/**
	 * Creates a new CString which is the result of concatenation of current and given string.
	 * @param str String to be concatenated to this string.
	 * @return New CString.
	 */
	public CString add (CString str) {
		int templength = length + str.length();
		char[] tempdata = new char[templength];
		
		int j = 0;
		for (int i = offset; i < offset + length; i++) {
			tempdata[j] = data[i];
			j++;
		}
		
		for (int i = 0; i < str.length(); i++) {
			tempdata[j] = str.charAt(i);
			j++;
		}
		
		return new CString(tempdata);
	}
	
	/**
	 * Creates a new CString in which each occurrence of old character is replaced with the given new character.
	 * @param oldChar Character to be replaced.
	 * @param newChar Character which replaces the old character.
	 * @return New CString.
	 */
	public CString replaceAll (char oldChar, char newChar) {
		char[] tempdata = new char[length];
		
		int j = 0;
		for (int i = offset; i < offset + length; i++) {
			if (data[i] == oldChar) {
				tempdata[j] = newChar;
				j++;
			} else {
				tempdata[j] = data[i];
				j++;
			}
		}
		
		return new CString(tempdata);
	}
	
	/**
	 * Creates a new CString in which each occurrence of old substring is replaced with the new substring.
	 * @param oldStr String to be replaced.
	 * @param newStr String which replaces the old string.
	 * @return New CString.
	 * @throws IllegalArgumentException if old string has less characters than the caller.
	 */
	public CString replaceAll (CString oldStr, CString newStr) throws IllegalArgumentException  {
		if (oldStr.length() > length()) {
			throw new IllegalArgumentException("Old string must have less characters than the caller!");
		}
		boolean temp;
		char[] tempdata = new char[length * newStr.length()];
		
		for (int i = offset, j = 0; i < offset + length;) {
			temp = true;
			if (data[i] == oldStr.charAt(0)) {
				for (int tempi = i, k = 0; k < oldStr.length(); k++, tempi++) {
					if (data[tempi] != oldStr.charAt(k)) {
						temp = false;
					}
				}
				if (temp) {
					for (int k = 0; k < newStr.length(); k++) {
						tempdata[j] = newStr.charAt(k);
						j++;
					}
					i += oldStr.length;
				} else {
					tempdata[j] = data[i];
					j++;
					i++;
				}
			} else {
				tempdata[j] = data[i];
				i++;
				j++;
			}
		}
		
		//remove empty cells by creating a new array
		int i;
		for (i = 0; i < tempdata.length; i++) {
			if (tempdata[i] == 0) {
				break;
			}
		}
		
		char[] data2 = new char[i];
		for (int j = 0; j < i; j++) {
			data2[j] = tempdata[j];
		}
		
		return new CString(data2);
	}
	
}
