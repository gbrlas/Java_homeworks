package hr.fer.zemris.java.student0036476746.hw07.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class which is used for getting digest, encrypting or decrypting a given file.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class Crypto {
	
	/**
	 * Main method which starts the program. Takes two or three arguments.
	 * 
	 * @param args
	 *            First argument is the command name. Second argument is the
	 *            file name. Third argument(optional, depends on the command) is
	 *            the destination file name.
	 */
	public static void main(String[] args) {
		
		if (args.length < 2 || args.length > 3) {
			System.err.println("Invalid arguments provided, try again.");
			return;
		}
		
		if (args[0].toLowerCase().equals("checksha")) {
			checkSha(args[1]);
		} else if (args[0].toLowerCase().equals("encrypt")) {
			encryptDecryptFile(args[1], args[2], true);
		} else if (args[0].toLowerCase().equals("decrypt")) {
			encryptDecryptFile(args[1], args[2], false);
		} else {
			System.err.println("Invalid arguments provided, try again.");
			return;
		}
	}

	/**
	 * Method which gets the SHA-256 digest value of the provided file.
	 * 
	 * @param file File whose SHA-256 value is to be found.
	 */
	private static void checkSha(String file) {
		Scanner scanner = new Scanner(System.in);
		String digestProvided;
		
		try {
			FileInputStream input = new FileInputStream(file);
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			
			byte[] digestCalculatedArray= getDigest(input, digest, 4096);
		    
			
			System.out.println("Please provide expected sha-256 digest for " + file + ":");
			digestProvided = scanner.nextLine().trim();
			
			String digestCalculated = byteToHex(digestCalculatedArray);
			
			if (digestCalculated.equals(digestProvided)) {
				System.out.println("Digesting completed. Digest of " + file + " matches expected digest.");
			} else {
				System.out.println("Digesting completed. Digest of " + file
						+ " does not match the expected digest. Digest was: "
						+ digestCalculated); 
			}
			
			input.close();
		} catch	(Exception exception) {
			System.err.println(exception.getMessage());
		}
		
		scanner.close();
		
	}
	
	/**
	 * Helper function which gets the file digest.
	 * 
	 * @param input Input stream of the provided file.
	 * @param digest MessageDigest class used for calculating the digest.
	 * @param byteArraySize File size to be read at once.
	 * @return Byte array containing file digest value.
	 * @throws NoSuchAlgorithmException - if invalid digest algorithm entered.
	 * @throws IOException - if there is an error while reading the file.
	 */
	private static byte[] getDigest(InputStream input, MessageDigest digest, int byteArraySize)
			throws NoSuchAlgorithmException, IOException {

		digest.reset();
		byte[] bytes = new byte[byteArraySize];
		int numBytes;
		
		while ((numBytes = input.read(bytes)) != -1) {
			digest.update(bytes, 0, numBytes);
		}
		
		byte[] digestResult = digest.digest();
		return digestResult;
	}
	
	/**
	 * Converts bytes to hex values.
	 * 
	 * @param bytes Byte array to be converted.
	 * @return String representing hex values of the provided byte array.
	 */
	public static String byteToHex(byte[] bytes) {
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < bytes.length; i++) {
            buffer.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        
        return buffer.toString();

    }
	
	/**
	 * Method used for encryption or decryption of files, depending on user input.
	 * 
	 * @param file File to be encrypted/decrypted.
	 * @param newFileName Name of the file to be created as a result of encryption/decription.
	 * @param encrypt Boolean representing whether the file should be encrypted or decrypted.
	 */
	private static void encryptDecryptFile(String file, String newFileName, boolean encrypt) {
		Scanner scanner = new Scanner(System.in);
		
		try {			
			System.out.println("Please provide expected password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
			String keyText = scanner.nextLine().trim();
			System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
			String ivText = scanner.nextLine().trim();
			
			SecretKeySpec keySpec = new SecretKeySpec(hextobyte(keyText), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(hextobyte(ivText));
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
			
			InputStream input = new BufferedInputStream(new FileInputStream(file));
			OutputStream output = new BufferedOutputStream(new FileOutputStream(newFileName));
			
			byte[] bytes = new byte[4096];
			
			
			byte[] temp;
			
			@SuppressWarnings("unused")
			int numbytes;
			while ((numbytes = input.read(bytes)) != -1) {
				temp = cipher.update(bytes, 0, bytes.length);
				output.write(temp);
			}
			
			try {
				temp = cipher.doFinal();
				output.write(temp);
			} catch (Exception exception) {}
		
			
			input.close();
			output.close();
			
			if (encrypt) {
				System.out.println("Encryption completed. Generated file " + newFileName + " based on file " + file + ".");
			} else {
				System.out.println("Decryption completed. Generated file " + newFileName + " based on file " + file + ".");
			}
		} catch (Exception exception) {
			System.err.println(exception.getMessage());
		}
		
		scanner.close();
	}

	/**
	 * Converts hex values into a byte array.
	 * 
	 * @param hexText String representing hex values.
	 * @return Byte array.
	 */
	public static byte[] hextobyte(String hexText) {
		int len = hexText.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(hexText.charAt(i), 16) << 4)
	                             + Character.digit(hexText.charAt(i+1), 16));
	    }
	    return data;
	}
	
}
