package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.interfaces.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.interfaces.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.interfaces.ShellStatus;

/**
 * Class which represents the copy command.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class Copy implements ShellCommand {
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = null;
		
		if (arguments.contains("\"")) {
			String temp = arguments.substring(arguments.indexOf("\"") + 1);
			temp = temp.substring(0, temp.substring(1).indexOf("\"")+1);
			arguments = arguments.substring(temp.length() + 2);
			args = new String[] {temp.trim(), arguments.trim()};
		} else {
			args = arguments.replaceAll("\"", "").trim().split(" ");
		}
		
		if (args.length < 1 || args.length > 2) {
			System.err.println("Invalid arguments for the copy command!");
			return ShellStatus.CONTINUE;
		}
		
		String fileName = args[0];
		String destination = args[1];
		
		try {
			return copy(fileName, destination, env);
		} catch (Exception exception) {
			try {
				env.writeStringln(exception.getMessage());
				return ShellStatus.CONTINUE;
			} catch (Exception exception2) {
				return ShellStatus.CONTINUE;
			}	
		}
	}
	
	
	@Override
	public List<String> getCommandDescription() {
	List<String> list = new ArrayList<String>();
		
		list.add("Command cat takes two arguments.");
		list.add("The first argument is the source file name, and the second is the destination file name.");
		list.add("If the destination file exists, user shall be asked if it is allowed to overwrite it.");
		list.add("If provided destination is a directory, the original file shall be copied in that directory using original name.");
		
		return Collections.unmodifiableList(list);
	}
	
	@Override
	public String getCommandName() {
		return "copy";
	}
	
	/**
	 * Helper function which copies the file to the destination.
	 * @param fileName Name of the file to be copied.
	 * @param destination Destination of the copied file.
	 * @param env Environment used.
	 * @return Shell status after processing the user request.
	 */
	private ShellStatus copy (String fileName, String destination, Environment env) {
		try {
	        File fromFile = new File(fileName);
	        File toFile = new File(destination);

	        if (!fromFile.exists()) {
	            throw new IOException("File not found: " + fromFile);
	        }
//	        if (!toFile.exists()) {
//	            throw new IOException("Destination not found: " + toFile);
//	        }
	        if (!fromFile.isFile()) {
	            throw new IOException("Can't copy directories: " + fromFile);
	        }
	        if (!fromFile.canRead()) {
	            throw new IOException("Can't read file: " + fromFile);
	        }

	        if (toFile.isDirectory()) {
	            toFile = new File(toFile, fromFile.getName());
	        }
	        
//	        if (Files.isSameFile(fromFile.toPath(), toFile.toPath())) {
//	        	throw new IOException("Provided files are the same, cannot copy!");
//	        }

	        if (toFile.exists() && toFile.isFile()) {
	            while(true) {
	            	env.writeStringln("Do you want to overwrite the given file? yes/no");
		            String overwrite = env.readLine();
		                
		            if (overwrite.equals("yes")) {
		             	break;
		            } else {
		              	return ShellStatus.CONTINUE;
		            }
	            }
	        }

			FileInputStream inputStream = null;
			FileOutputStream outputStream = null;
			try {

				inputStream = new FileInputStream(fromFile);
				outputStream = new FileOutputStream(toFile);
				byte[] buffer = new byte[4096];
				int bytesRead;

				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}

			} finally {
				if (fromFile != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						System.out.println(e);
					}
				}
				if (toFile != null) {
					try {
						outputStream.close();
					} catch (IOException e) {
						System.out.println(e);
					}
				}
			}

	        } catch (Exception e) {
	            System.out.println("Problems when copying file.");
	        }
		
		return ShellStatus.CONTINUE;
	}
}
