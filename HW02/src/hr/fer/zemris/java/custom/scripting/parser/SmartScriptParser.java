package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.tokens.Token;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantDouble;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantInteger;
import hr.fer.zemris.java.custom.scripting.tokens.TokenFunction;
import hr.fer.zemris.java.custom.scripting.tokens.TokenOperator;
import hr.fer.zemris.java.custom.scripting.tokens.TokenString;
import hr.fer.zemris.java.custom.scripting.tokens.TokenVariable;

/**
 * Parser for a structured document format.
 * 
 * @author Goran Brlas
 *
 */
public class SmartScriptParser {
	private String documentBody;
	private ObjectStack stack = new ObjectStack();
	
	//0 - starting state, 1 - text state, 2 - tag state, 3 - escape state
	private int state = 0;
	
	/**
	 * Constructor which sets the string documentBody.
	 * @param documentBody String that contains the document body.
	 */
	public SmartScriptParser (String documentBody) {
		this.documentBody = documentBody;
		parseTheDocument();
	}
	
	/**
	 * Creates a DFA which parses the given document body.
	 * @throws SmartScriptParserException
	 */
	private void parseTheDocument() throws SmartScriptParserException {
		//create first document node and push it to the stack
		DocumentNode document = new DocumentNode();
		stack.push(document);
		
		//read the first character and determine the next state
		documentBody = documentBody.trim();
		char c = documentBody.charAt(0);
		documentBody = documentBody.substring(1);
		StringBuilder builder = new StringBuilder();
		
		//define starting state
		if (c == '{')
			state = 2;
		else if (c == '\\')
			state = 3;
		else {
			builder.append(c);
			state = 1;
		}
		
		while (documentBody.length() != 0)  {
			c = documentBody.charAt(0);
			
			//depending on which state the DFA is in, react accordingly
			switch (state) {
				//text state
				case 1:
					if (c == '{') {
						//check if next character is completes the tag
						String temp = documentBody.substring(1);
						char tempchar = temp.charAt(0);
						
						if (tempchar == '$') {
							documentBody = temp.trim();
							state = 2;
						} else {
							c = documentBody.charAt(0);
							builder.append(c);
							documentBody = documentBody.substring(1);
						}
						//documentBody = documentBody.substring(1).trim();
						//state = 2;
					} else if (c == '\\') {
						state = 3;
						documentBody = documentBody.substring(1);
					} else {
						builder.append(c);	
						documentBody = documentBody.substring(1);
					}
					break;
				
				//tag state
				case 2:
					if (c == '$') 
						documentBody = documentBody.substring(1).trim();
					
					else if (c == 'F' || c == 'f') {
						createTextNode(builder);
						createForNode();
						documentBody = documentBody.substring(1);
						state = 1;
					} else if (c == '=') {
						createTextNode(builder);
						createEchoNode();
						documentBody = documentBody.substring(1);
						state = 1;
					} else if (c == 'E' || c == 'e') {
						if (builder.length() != 0) {
							TextNode text = new TextNode(builder.toString());
							Node temp = (Node) stack.peek();
							temp.addChildNode(text);
						}
						removeFromStack();
						state = 1;
						builder.setLength(0);
					} else {
						throw new SmartScriptParserException("Document contains an invalid expression!");
					}
					break;
					
				//escape state
				case 3:
					if (c != '\\' && c != '{' && c != '\"' && c != 'n' && c != 'r' && c != 't' && c != '\n' && c != '\r' && c != '\t') {
						throw new SmartScriptParserException("You cannot escape character " + c + ".");
					}
					
					builder.append("\\");
					builder.append(c);
					state = 1;
					documentBody = documentBody.substring(1);
					break;
				default:
					break;
			}
		}
		
		//if needed create the final text node
		if (builder.length() > 0)
			createTextNode(builder);
		
		//if there is something else on the stack, the document structure is invalid
		if (stack.size() > 1) {
			throw new SmartScriptParserException("Error in the document, there weren't enough end tags!");
		} else if (stack.size() < 1){
			throw new SmartScriptParserException("Error in the document, there were too many end tags.");
		}
	}
	
	/**
	 * Creates a new ForLoopNode.
	 * @throws SmartScriptParserException
	 */
	private void createForNode() throws SmartScriptParserException {
		//remove word FOR and take the variable
		documentBody = documentBody.substring(3).trim();
		TokenVariable variable = new TokenVariable(documentBody.substring(0, documentBody.indexOf(" ")));
		documentBody = documentBody.substring(documentBody.indexOf(" "));
		documentBody = documentBody.trim();
		
		if (Character.isLetter(variable.asText().charAt(0)) == false)
			throw new SmartScriptParserException("Variable must have a name.");
		
		String temp = documentBody.substring(0, documentBody.indexOf("$"));
		String[] str = temp.split("\\s+");
		
		//if for loop parameters are not divided by whitespace
		if (str.length == 1) {
			if (str[0].charAt(0) == '\"')
				str[0] = str[0].substring(1);
			str = str[0].split("\"");
				if (str.length < 2 || str.length > 3) {
					throw new SmartScriptParserException("For loop must have three or four parameters.");
				}
		}
		
		Token[] tokens = new Token[str.length];
		
		//create tokens from the loop body
		int i = 0;
		for (String str2 : str) {
			if (str2.contains("\""))
				tokens[i] = new TokenString(str2);
			else if ((str2.charAt(0) == '+' || str2.charAt(0) == '-' || str2.charAt(0) == '*' || str2.charAt(0) == '/') && str2.length() == 1)
				throw new SmartScriptParserException("An operator can't be a for loop parameter.");
			else if (str2.charAt(0) == '@')
				throw new SmartScriptParserException("A function cannot be a for loop parameter!");
			else if (Character.isLetter(str2.charAt(0)))
				tokens[i] = new TokenVariable(str2);
			else if (Character.isDigit(str2.charAt(0)) && str2.contains("."))
				tokens[i] = new TokenConstantDouble(Double.parseDouble(str2));
			else if (Character.isDigit(str2.charAt(0)))
				tokens[i] = new TokenConstantInteger(Integer.parseInt(str2));
			//case where parameters are concatenated
			else 
				tokens[i] = new TokenString(str2);
			i++;
		}
		
		//create a new ForLoopNode and push it on the stack
		ForLoopNode flop = new ForLoopNode(variable, tokens);
		
		Node temp2 = (Node) stack.peek();
		temp2.addChildNode(flop);
		stack.push(flop);
		
		documentBody = documentBody.substring(documentBody.indexOf("}"));
	}
	
	/**
	 * Creates a new TextNode from data provided by StringBuilder.
	 * @param builder StringBuilder with data needed to create a TextNode.
	 */
	private void createTextNode (StringBuilder builder) {
		TextNode text = new TextNode(builder.toString());
		builder.setLength(0);
		Node temp = (Node) stack.peek();
		temp.addChildNode(text);
	}
	
	/**
	 * Creates a new EchoNode.
	 */
	private void createEchoNode () {
		documentBody = documentBody.substring(1).trim();
		String temp = documentBody.substring(0, documentBody.indexOf("$"));
		
		
		String[] temp2 = temp.split("\\s+");
		
		//if echo tag is empty
		if (temp2.length == 1 && temp2[0].equals(""))
			temp2[0] = " ";
		
		Token[] tokens = new Token[temp2.length];
		
		//create tokens from loop body
		int i = 0;
		for (String str : temp2) {
			if (str.contains("\""))
				tokens[i] = new TokenString(str);
			else if ((str.charAt(0) == '+' || str.charAt(0) == '-' || str.charAt(0) == '*' || str.charAt(0) == '/') && str.length() == 1)
				tokens[i] = new TokenOperator(str);
			else if (str.charAt(0) == '@') {
				if (Character.isLetter(str.charAt(1)) == false)
					throw new SmartScriptParserException("Function name must start with a letter !");
				tokens[i] = new TokenFunction(str);
			}
			else 
				tokens[i] = new TokenVariable(str);
			i++;
		}
		
		EchoNode echo = new EchoNode(tokens);
		
		Node temp3 = (Node) stack.peek();
		temp3.addChildNode(echo);
		
		documentBody = documentBody.substring(documentBody.indexOf("}"));
	}
	
	/**
	 * Removes the top node on the stack.
	 */
	private void removeFromStack () {
		stack.pop();
		documentBody = documentBody.substring(documentBody.indexOf("}") + 1);
		
	}
	
	/**
	 * Returns the DocumentNode after parsing is complete.
	 * @return DocumentNode.
	 * @throws SmartScriptParserException
	 */
	public DocumentNode getDocumentNode() throws SmartScriptParserException {
		DocumentNode temp = (DocumentNode) stack.pop();
		
		if (stack.size() != 0)
			throw new SmartScriptParserException("Error in the document, there weren't enough end tags!");
		
		return temp;
	}
	
}

