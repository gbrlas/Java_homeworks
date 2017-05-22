package hr.fer.zemris.java.tecaj.hw07.shell.interfaces;

import java.io.IOException;
import java.util.Map;

public interface Environment {
	/**
	 * Reads one line of user input.
	 * If the input ends with a morelines symbol, method continues to 
	 * read input until there is no more morelines symbol.
	 * @return Line/s read from the shell.
	 * @throws IOException - if scanner could not get input.
	 */
	String readLine() throws IOException;
	/**
	 * Prints the given text to the console, without going into the next line.
	 * @param text Text to be written to the console.
	 * @return Text provided.
	 * @throws IOException - if output could not be written.
	 */
	String write(String text) throws IOException;
	/**
	 * Prints the given text to the console.
	 * @param text Text to be written to the console.
	 * @return Text provided.
	 * @throws IOException - if output could not be written.
	 */
	String writeStringln(String text) throws IOException;
	/**
	 * Returns iterable list of available commands.
	 * @return List of available commands.
	 */
	Iterable<ShellCommand> commands();
	/**
	 * Gets the current multiline symbol.
	 * @return Current multiline symbol.
	 */
	Character getMultilineSymbol();
	/**
	 * Sets the current multiline symbol.
	 * @param symbol - Symbol to be set as new multiline symbol.
	*/
	void setMultilineSymbol(Character symbol);
	/**
	 * Gets the current prompt symbol.
	 * @return Current prompt symbol.
	 */
	Character getPromptSymbol();
	/**
	 * Sets the current prompt symbol.
	 * @param symbol - Symbol to be set as new prompt symbol.
	*/
	void setPromptSymbol(Character symbol);
	/**
	 * Gets the current morelines symbol.
	 * @return Current morelines symbol.
	 */
	Character getMorelinesSymbol();
	/**
	 * Sets the current morelines symbol.
	 * @param symbol - Symbol to be set as new morelines symbol.
	*/
	void setMoreLinesSymbol(Character symbol);
	/**
	 * Gets the map containing available shell commands.
	 * @return Map containing shell commands.
	 */
	Map<String, ShellCommand> getCommands();
	/**
	 * Sets the map containing available shell commands.
	 * @param commands - Map to be given to the environment.
	 */
	void setCommands(Map<String, ShellCommand> commands);
}
