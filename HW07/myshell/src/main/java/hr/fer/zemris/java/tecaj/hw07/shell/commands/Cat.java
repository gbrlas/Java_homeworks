package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.interfaces.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.interfaces.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.interfaces.ShellStatus;

public class Cat implements ShellCommand {
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws IllegalArgumentException {
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
			System.err.println("Invalid arguments for the cat command!");
			return ShellStatus.CONTINUE;
		}
		
		if (args[1].length() == 0) {
			args = new String[] {args[0]};
		}
		
		String fileName = args[0];
		String charset;
		if (args.length == 2) {
			charset = args[1];
		} else {
			charset = Charset.defaultCharset().toString();
		}
		
		
		
		try {
			InputStream input = new FileInputStream(fileName);
			@SuppressWarnings("unused")
			int numBytes;
			byte[] bytes = new byte[4096];
			
			while ((numBytes = input.read(bytes)) != -1) {
				String decoded = new String(bytes, charset);
				env.write(decoded);
			}
			
			input.close();
		} catch (Exception exception) {
			System.err.println(exception.getMessage());
			System.exit(-1);
		}
		
		return ShellStatus.CONTINUE;
	}
	
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		
		list.add("Command cat takes one or two arguments.");
		list.add("The first argument is path to some file and is mandatory.");
		list.add("The second argument is the charset to be used for interpreting chars from bytes.");
		list.add("If not provided, default plarform charset should be used.");
		list.add("This command opens given file and writes its content to console,");
		
		return Collections.unmodifiableList(list);
	}
	
	@Override
	public String getCommandName() {
		return "cat";
	}
}
