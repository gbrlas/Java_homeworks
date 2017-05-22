package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.tecaj.hw07.shell.interfaces.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.interfaces.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.interfaces.ShellStatus;

/**
 * Class which represents the charset command.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class Charsets implements ShellCommand {
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Map<String, Charset> tempMap;
		
		tempMap = Charset.availableCharsets();
		
		try {
			for (String charset : tempMap.keySet()) {
				env.writeStringln(charset);
			}
		} catch (Exception exception) {
			System.err.println(exception.getMessage());
		}
		
		return ShellStatus.CONTINUE;
		
		
	}
	
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		
		list.add("Command takes no arguments.");
		list.add("Command lists names of supported charsets for your Java platform.");
		
		return Collections.unmodifiableList(list);
	}
	
	@Override
	public String getCommandName() {
		return "charsets";
	}
}
