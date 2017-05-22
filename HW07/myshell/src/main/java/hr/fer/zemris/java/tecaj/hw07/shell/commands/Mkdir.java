package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.interfaces.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.interfaces.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.interfaces.ShellStatus;

/**
 * Class which represents the mkdir command.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class Mkdir implements ShellCommand {
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		arguments = arguments.trim();
		File file = new File(arguments);
		
		boolean test = true;
		int len = arguments.length();
		int count = 0;
		//check whether entire directory structure needs to be created or just a single directory
		for (int i = 0; i < len; i++) {
			if (arguments.charAt(i) == '\\' || arguments.charAt(i) == '/') {
				count++;
				if (count > 1) {
					test = false;
					break;
				}
			}
		}
		
		try {
			if (test) {
				if (!file.exists()) {
					if (file.mkdir()) {
						env.writeStringln("Directory is created!");
					} else {
						env.write("Failed to create directory!");
					}
				}
			} else {
				if (!file.exists()) {
					if (file.mkdirs()) {
						env.writeStringln("Directory is created!");
					} else {
						env.writeStringln("Directory is created!");
					}
				}
			}
		} catch (Exception exception) {
			System.err.println(exception.getMessage());
		}
		
		return ShellStatus.CONTINUE;
	}
	
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		
		list.add("Command cat takes a single argument - directory name.");
		list.add("Command creates the appropriate directory structure.");
			
		return Collections.unmodifiableList(list);
	}
	
	@Override
	public String getCommandName() {
		return "mkdir";
	}
}
