package hr.fer.zemris.java.tecaj.hw07.shell;

import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.java.tecaj.hw07.shell.commands.Cat;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.Charsets;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.Copy;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.Exit;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.Help;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.Hexdump;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.Ls;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.Mkdir;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.Tree;
import hr.fer.zemris.java.tecaj.hw07.shell.interfaces.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.interfaces.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.interfaces.ShellStatus;

/**
 * Class representing a shell used for taking user input and doing basic command
 * line funtions.
 * 
 * @author Goran Brlas
 * @version 1.0
 * 
 */
public class Shell {
	
	/**
	 * Main method which starts the shell program. Takes no arguments.
	 * 
	 * @param args - none.
	 */
	public static void main(String[] args) {
		ShellStatus status = ShellStatus.CONTINUE;
		Environment env = new EnvironmentImplementation();
		Map<String, ShellCommand> commands = new HashMap<String, ShellCommand>();
		
		commands.put("cat", new Cat());
		commands.put("charsets", new Charsets());
		commands.put("copy", new Copy());
		commands.put("hexdump", new Hexdump());
		commands.put("ls", new Ls());
		commands.put("mkdir", new Mkdir());
		commands.put("tree", new Tree());
		commands.put("exit", new Exit());
		commands.put("help", new Help());
		
		env.setCommands(commands);
		
		try {
			env.writeStringln("Welcome to MyShell v 1.0");
			
			while (status != ShellStatus.TERMINATE) {
				env.write(env.getPromptSymbol().toString());
				
				String line = env.readLine();
				line = line.replace("\\n", " ").replaceAll("\\s++", " ");
				
				status = parseLine(line, env, commands);
			}
		} catch (Exception exception) {
			System.err.println(exception.getMessage());
		}
	}
	
	/**
	 * Helper method which parses user entries.
	 * 
	 * @param line Line that the user entered.
	 * @param env Environment used.
	 * @param commands List of command supported by the shell.
	 * @return Shell status after processing the user request.
	 */
	private static ShellStatus parseLine(String line, Environment env, Map<String, ShellCommand> commands) {
		try {
			if (line.contains("symbol") && line.split(" ").length == 2) {
				if (line.toLowerCase().contains("prompt")) {
					env.writeStringln("Symbol for PROMPT is \'" + env.getPromptSymbol() + "\'.");
				} else if (line.toLowerCase().contains("multiline")) {
					env.writeStringln("Symbol for MULTILINE is \'" + env.getMultilineSymbol() + "\'.");
				} else if (line.toLowerCase().contains("morelines")) {
					env.writeStringln("Symbol for MORELINES is \'" + env.getMorelinesSymbol() + "\'.");
				} else {
					env.writeStringln("Invalid command!");
				}
			} else if (line.contains("symbol") && line.split(" ").length == 3) {
				char newSymbol = line.split(" ")[2].charAt(0);
				
				if (line.toLowerCase().contains("prompt")) {
					env.writeStringln("Symbol for PROMPT changed from \'"
							+ env.getPromptSymbol() + "\' to \'" + newSymbol
							+ "\'.");
					env.setPromptSymbol(newSymbol);
				} else if (line.toLowerCase().contains("multiline")) {
					env.writeStringln("Symbol for MULTILINE changed from \'"
							+ env.getMultilineSymbol() + "\' to \'" + newSymbol
							+ "\'.");
					env.setMultilineSymbol(newSymbol);
				} else if (line.toLowerCase().contains("morelines")) {
					env.writeStringln("Symbol for MORELINES changed from \'"
							+ env.getMorelinesSymbol() + "\' to \'" + newSymbol
							+ "\'.");
					env.setMoreLinesSymbol(newSymbol);
				} else {
					env.writeStringln("Invalid command!");
				}
			} else {
				return processCommands(line, env, commands);
			}
		} catch (Exception exception) {
			System.err.println(exception.getMessage());
		}
		
		return ShellStatus.CONTINUE;
		
	}
	
	/**
	 * Parse helper which processes the user command by calling execution methods on correct
	 * command classes.
	 * 
	 * @param line Line that the user entered.
	 * @param env Environment used.
	 * @param commands List of command supported by the shell.
	 * @return Shell status after processing the user request.
	 */
	private static ShellStatus processCommands(String line, Environment env, Map<String, ShellCommand> commands) {
		try {
			if (line.split(" ")[0].equals("cat")) {
				ShellCommand command = commands.get("cat");
				String args = line.substring(3).trim();
				return command.executeCommand(env, args);
			}
			else if (line.split(" ")[0].equals("charsets")) {
				ShellCommand command = commands.get("charsets");
				String args = line.substring(8).trim();
				return command.executeCommand(env, args);
			}
			else if (line.split(" ")[0].equals("copy")) {
				ShellCommand command = commands.get("copy");
				String args = line.substring(4).trim();
				return command.executeCommand(env, args);
			}
			else if (line.split(" ")[0].equals("exit")) {
				ShellCommand command = commands.get("exit");
				String args = line.substring(4).trim();
				return command.executeCommand(env, args);
			}
			else if (line.split(" ")[0].equals("hexdump")) {
				ShellCommand command = commands.get("hexdump");
				String args = line.substring(7).trim();
				return command.executeCommand(env, args);
			}
			else if (line.split(" ")[0].equals("ls")) {
				ShellCommand command = commands.get("ls");
				String args = line.substring(2).trim();
				return command.executeCommand(env, args);
			}
			else if (line.split(" ")[0].equals("mkdir")) {
				ShellCommand command = commands.get("mkdir");
				String args = line.substring(5).trim();
				return command.executeCommand(env, args);
			}
			else if (line.split(" ")[0].equals("tree")) {
				ShellCommand command = commands.get("tree");
				String args = line.substring(4).trim();
				return command.executeCommand(env, args);
			}
			else if (line.split(" ")[0].equals("help")) {
				ShellCommand command = commands.get("help");
				String args = line.substring(4).trim();
				return command.executeCommand(env, args);
			} else {
				env.writeStringln("Invalid command!");
				return ShellStatus.CONTINUE;
			}
		} catch (Exception exception) {
			System.err.println(exception.getMessage());
			System.exit(-1);
		}
		
		return ShellStatus.CONTINUE;
		
	}
	
}
