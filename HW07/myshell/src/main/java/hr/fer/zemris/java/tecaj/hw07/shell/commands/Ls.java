package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.interfaces.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.interfaces.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.interfaces.ShellStatus;

/**
 * Class which represents the ls command.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class Ls implements ShellCommand {
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		arguments = arguments.trim().replaceAll("\"", "");
		
		if (arguments.contains(" ")) {
			if (arguments.charAt(arguments.length()-1) != '/') {
				arguments = arguments + "/";
			}
		}
		
//		if (args.length != 1) {
//			System.err.println("Invalid arguments for the ls command!");
//			return ShellStatus.CONTINUE;
//		}
		
		File root = new File(arguments);
		if (!root.exists()) {
			System.err.println("Directory does not exist");
			return ShellStatus.CONTINUE;
		}
		if (!root.isDirectory()) {
			System.err.println(root.getName() + " is not a directory.");
			return ShellStatus.CONTINUE;
		}
		
		return processChildren(root.listFiles(), env);
	}
	
	/**
	 * Helper method used for processing provided directory children.
	 * @param children Files in the directory.
	 * @param env Environment used.
	 * @return Shell status after processing the user request.
	 */
	private ShellStatus processChildren(File[] children, Environment env) {
		if (children == null) {
			return ShellStatus.CONTINUE;
		}
		
		for (File child : children) {
			write(child, env);
		}
		
		return ShellStatus.CONTINUE;
		
	}
	
	/**
	 * Helper method used for formatting the shell output.
	 * @param child File whose characteristics shall be printed out.
	 * @param env Environment used.
	 */
	private void write(File child, Environment env) {
		StringBuilder builder = new StringBuilder();
		
		if (child.isDirectory()) {
			builder.append("d");
		} else {
			builder.append("-");
		}
		
		if (child.canRead()) {
			builder.append("r");
		} else {
			builder.append("-");
		}
		
		if (child.canWrite()) {
			builder.append("w");
		} else {
			builder.append("-");
		}
		
		if (child.canExecute()) {
			builder.append("x");
		} else {
			builder.append("-");
		}
		
		
		builder.append(String.format(" %10d ", child.length()));
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			BasicFileAttributeView faView = Files.getFileAttributeView(child.toPath(),
					BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
			BasicFileAttributes attributes = faView.readAttributes();
			FileTime fileTime = attributes.creationTime();
			String formattedDateTimeString = sdf.format(new Date(fileTime.toMillis()));
			
			builder.append(formattedDateTimeString + " ");
			builder.append(child.getName());
			
			env.writeStringln(builder.toString());
		} catch (Exception exception) {
			System.err.println(exception.getMessage());
		}
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		
		list.add("Command ls takes a single argument - directory name.");
		list.add("Command writes a directory listing.");
		
		return Collections.unmodifiableList(list);
	}
	
	@Override
	public String getCommandName() {
		return "ls";
	}
	
}
