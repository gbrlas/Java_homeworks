package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.interfaces.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.interfaces.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.interfaces.ShellStatus;

/**
 * Class which represents the hexdump command.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class Hexdump implements ShellCommand {
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
//		String[] args = arguments.trim().split(" ");
//		
//		if (args.length != 1) {
//			System.err.println("Invalid arguments for the hexdump command!");
//			return ShellStatus.CONTINUE;
//		}
		
		try {
			InputStream input = new FileInputStream(new File(arguments));
			return calculateHex(input, env);
		} catch (Exception exception) {
			System.err.println(exception.getMessage());
		}
		
		return ShellStatus.CONTINUE;
		
	}
	
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		
		list.add("Command hexdump takes a single argument - file name.");
		list.add("Command prints a hex-output generated from the file.");
		
		return  Collections.unmodifiableList(list);
	}
	
	@Override
	public String getCommandName() {
		return "hexdump";
	}
	
	/**
	 * Helper function used for calculating hex values of the given input.
	 * @param input Input stream.
	 * @param env Environment used.
	 * @return Shell status after processing the user request.
	 */
	private ShellStatus calculateHex (InputStream input, Environment env) {
		int bytesCounter = 0;		
		int value = 0;
		StringBuilder sbHex = new StringBuilder();
		StringBuilder sbText = new StringBuilder();
		StringBuilder sbResult = new StringBuilder();
		
		try {
			while ((value = input.read()) != -1) {    
				//convert to hex value with "X" formatter
				sbHex.append(String.format("%02X ", value));
	 
				//If the chracater is not convertable, just print a dot symbol "." 
				if (!Character.isISOControl(value)) {
					sbText.append((char)value);
				} else {
					sbText.append(".");
				}
	 
				//if 16 bytes are read, reset the counter, 
					//clear the StringBuilder for formatting purpose only.
				if (bytesCounter == 7) {
					sbHex.append("|");
				}
				if (bytesCounter==15){
					sbResult.append(sbHex).append(" | ").append(sbText).append("\n");
					sbHex.setLength(0);
					sbText.setLength(0);
					bytesCounter=0;
				} else {
					bytesCounter++;
				}
	       }
	 
			//if still got content
			if (bytesCounter!=0){			
				//add spaces more formatting purpose only
				for (; bytesCounter<16; bytesCounter++){
					//1 character 3 spaces
					sbHex.append("   ");
				}
				sbResult.append(sbHex).append("      ").append(sbText).append("\n");
			}
		
			env.write(sbResult.toString());
			input.close();
	    	
	    } catch (Exception exception) {
	    	System.err.println(exception.getMessage());
	    }
	    
	    
	    
	    return ShellStatus.CONTINUE;
	}
}
