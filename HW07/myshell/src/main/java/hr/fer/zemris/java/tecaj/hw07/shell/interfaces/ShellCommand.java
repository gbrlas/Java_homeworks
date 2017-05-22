package hr.fer.zemris.java.tecaj.hw07.shell.interfaces;

import java.util.List;

/**
 * Interface representing shell commands available to the shell user.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public interface ShellCommand {
	/**
	 * Executes the shell command.
	 * @param env Environment used.
	 * @param arguments Arguments provided to the command.
	 * @return Shell status after processing the user request.
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	/**
	 * Gets the command name.
	 * @return Command name.
	 */
	String getCommandName();
	/**
	 * Gets the command description.
	 * @return List containing Strings representing command description.
	 */
	List<String> getCommandDescription();
}
