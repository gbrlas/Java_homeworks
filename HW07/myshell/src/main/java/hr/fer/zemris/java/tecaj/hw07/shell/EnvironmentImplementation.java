package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import hr.fer.zemris.java.tecaj.hw07.shell.interfaces.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.interfaces.ShellCommand;

/**
 * Class which represents the environment in which the user communicates with 
 * the shell.
 * 
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class EnvironmentImplementation implements Environment {
	/**
	 * Character representing the multilines symbol.
	 */
	private char MULTILINESYMBOL = '|';
	/**
	 * Character representing the prompt symbol.
	 */
	private char PROMPTSYMBOL = '>';
	/**
	 * Character representing the morelines symbol.
	 */
	private char MORELINESSYMBOL = '\\';
	/**
	 * Map holding all the commands accessible by the user.
	 */
	Map<String, ShellCommand> commands = new HashMap<String, ShellCommand>();
	/**
	 * User input scanner.
	 */
	Scanner scanner = new Scanner(System.in);
	
	
	@Override
	public String readLine() throws IOException {
		
		StringBuilder builder = new StringBuilder();
		
		String temp;
		
		while (true) {
			temp = scanner.nextLine().trim();
			if (temp.charAt(temp.length() - 1) == MORELINESSYMBOL) {
				builder.append(temp);
				builder.deleteCharAt(builder.length() - 1);
				System.out.print(MULTILINESYMBOL + " ");
			} else {
				builder.append(temp);
				break;
			}
		}
		
		return builder.toString();
	}
	
	@Override
	public Iterable<ShellCommand> commands() {
		return new Iterable<ShellCommand>() {
			
			@Override
			public Iterator<ShellCommand> iterator() {
				
				return new Iterator<ShellCommand>() {
					private Iterator<ShellCommand> it = commands.values().iterator();
					
					@Override
					public boolean hasNext() {
						return it.hasNext();
					}
					
					@Override
					public ShellCommand next() {
						if (it.hasNext()) {
							return it.next();
						} else {
							throw new NoSuchElementException("No more elements.");
						}
					}
				};
			}
		};
		
	}
	
	
	
	@Override
	public String write(String text) throws IOException {
		System.out.print(text);
		return null;
	}
	
	@Override
	public String writeStringln(String text) throws IOException {
		System.out.println(text);
		return null;
	}
	
	
	
	@Override
	public Character getMultilineSymbol() {
		return MULTILINESYMBOL;
	}
	
	@Override
	public void setMultilineSymbol(Character symbol) {
		MULTILINESYMBOL = symbol.charValue();
	}
	
	@Override
	public Character getMorelinesSymbol() {
		return MULTILINESYMBOL;
	}
	
	@Override
	public void setMoreLinesSymbol(Character symbol) {
		MORELINESSYMBOL = symbol.charValue();
	}
	
	@Override
	public Character getPromptSymbol() {
		return PROMPTSYMBOL;
	}
	
	@Override
	public void setPromptSymbol(Character symbol) {
		PROMPTSYMBOL = symbol.charValue();
	}
	
	public Map<String, ShellCommand> getCommands() {
		return commands;
	}

	public void setCommands(Map<String, ShellCommand> commands) {
		this.commands = commands;
	}
	
	
}
