package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.interfaces.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.interfaces.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.interfaces.ShellStatus;

/**
 * Class which represents the exit command.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class Exit implements ShellCommand {
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		return ShellStatus.TERMINATE;
	}
	
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		
		list.add("Comman exit takes no arguments and ends the shell program.");
		
		return Collections.unmodifiableList(list);
	}
	
	@Override
	public String getCommandName() {
		return "exit";
	}
	
}
