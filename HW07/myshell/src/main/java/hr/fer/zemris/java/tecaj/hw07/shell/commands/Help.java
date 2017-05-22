package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.tecaj.hw07.shell.interfaces.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.interfaces.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.interfaces.ShellStatus;

/**
 * Class which represents the help command.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class Help implements ShellCommand {
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Map<String, ShellCommand> commands = env.getCommands();
		
		String[] args = arguments.trim().split(" ");
		
		try {
			if (args.length > 1) {
				System.err.println("Invalid arguments for the tree command!");
				return ShellStatus.CONTINUE;
			} else if (args[0].isEmpty()) {
				StringBuilder builder = new StringBuilder();
				builder.append("Available commands: ");
				for (String command : commands.keySet()) {
					builder.append(command + ", ");
				}
				
				builder.deleteCharAt(builder.length() - 2);
				env.writeStringln(builder.toString());
			} else {
				ShellCommand command = null;
				
				for (ShellCommand c: env.commands()) {
					if (c.getCommandName().equals(args[0])) {
						command = c;
					}
				}
				
				if (command == null) {
					env.writeStringln("Such command does not exist");
				} else {
					env.writeStringln(command.getCommandName());
					for (String str : command.getCommandDescription()) {
						env.writeStringln(str);
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
		
		list.add("Command help takes either no arguments or one argument.");
		list.add("If no arguments are provided, it lists all supported commands.");
		list.add("If an shell command name is provided, it prints the name and the description of the selected command.");
		
		return Collections.unmodifiableList(list);
	}
	
	@Override
	public String getCommandName() {
		return "help";
	}
}
