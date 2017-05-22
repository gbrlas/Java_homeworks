package hr.fer.zemris.java.tecaj.hw1;

import java.io.*;

/**
 * Takes user provided width and height and calculates rectangle's area and perimeter.
 * 
 * @author Goran Brlas - 0036476746
 *
 */
public class Rectangle {
	
	/**
	 * Method called when starting the program.
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) throws IOException {
		float width, height, area, perimeter;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		//take input from standard input if not provided via command line
		if (args.length == 0) {
			
			width = getWidthHeight(reader, "width");
			height = getWidthHeight(reader, "height");
			
			area = width*height;
			perimeter = 2*(width + height);
			
			System.out.println("You have specified a rectangle with width " + width + " and height " + height + ". Its area is " + area + " and its perimeter is " + perimeter + ".");
		} else if (args.length != 2) {
			System.err.println("Invalid number of arguments was provided, closing the program.");
			return;
		} else {
			width = Float.parseFloat(args[0]);
			height = Float.parseFloat(args[1]);
			
			if (width < 0) {
				System.err.println("Width is negative");
				width = getWidthHeight(reader, "width");
			}
			if (height < 0) {
				System.err.println("Height is negative");
				height = getWidthHeight(reader, "height");
			}
			
			area = width*height;
			perimeter = 2*(width + height);
			
			System.out.println("You have specified a rectangle with width " + width + " and height " + height + ". Its area is " + area + " and its perimeter is " + perimeter + ".");
		}
		
		reader.close();
	}
	
	
	/**
	 * Asks user for input and returns valid rectangle width/height.
	 * @param reader
	 * @return Returns rectangle width/height.
	 * @throws IOException
	 */
	public static float getWidthHeight (BufferedReader reader, String type) throws IOException {
		String redak;
		float broj;
		
		while (true) {
			System.out.print("Please provide " + type + ": ");
			redak = reader.readLine();
			redak.trim();
			
			if (redak.isEmpty()) {
				System.err.println("Nothing was given.");
				continue;
			} 
			
			broj = Float.parseFloat(redak);
			if (broj < 0) {
				System.err.println(type + " is negative.");
				continue;
			} else
				break;
		}
		
		return broj;
	}
	
}
