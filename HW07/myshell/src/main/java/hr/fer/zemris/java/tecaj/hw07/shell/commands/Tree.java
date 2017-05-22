package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.interfaces.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.interfaces.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.interfaces.ShellStatus;

/**
 * Class which represents the tree command.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class Tree implements ShellCommand {
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		arguments = arguments.trim().replaceAll("\"", "");
		
		if (arguments.contains(" ")) {
			if (arguments.charAt(arguments.length()-1) != '/') {
				arguments = arguments + "/";
			}
		}
		
		File root = new File(arguments);
		
		if (!root.exists()) {
			System.err.println("Directory does not exist");
			return ShellStatus.CONTINUE;
		}
		if (!root.isDirectory()) {
			System.err.println(root.getName() + " is not a directory.");
			return ShellStatus.CONTINUE;
		}
		
		return recursiveList(root, 0, env);
	}
	
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		
		list.add("Command tree takes a single argument - directory name.");
		list.add("Command prints a tree starting from the given directory.");
		
		return Collections.unmodifiableList(list);
	}
	
	@Override
	public String getCommandName() {
		return "tree";
	}
	
	/**
	 * Helper method used for recursively traveling the directory tree.
	 * @param dir Root directory.
	 * @param indentLevel Level of indentation for this node level.
	 * @param env Environment used.
	 * @return Shell status after processing the user request.
	 */
	private ShellStatus recursiveList (File dir, int indentLevel, Environment env) {
		try {
			if (indentLevel == 0) {
				env.writeStringln(dir.getName());
			} else {
				env.write(String.format("%" + indentLevel +"s%s%n", "", dir.getName()));
			}
			
			File[] children = dir.listFiles();
			
			if (children == null) {
				return ShellStatus.CONTINUE;
			}
			
			indentLevel += 2;
			
			for (File child : children) {
				if (child.isFile()) {
					env.write(String.format("%" + indentLevel +"s%s%n", "", child.getName()));
				} else if (child.isDirectory()) {
					recursiveList(child, indentLevel, env);
				}
			}
		} catch (Exception exception) {
			System.err.println(exception.getMessage());
		}
		
		return ShellStatus.CONTINUE;
	}
	
}
