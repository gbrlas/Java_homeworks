package hr.fer.zemris.java.hw2;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;
import hr.fer.zemris.java.custom.scripting.tokens.Token;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Program tests the SmartScriptParser class. Takes a single command-line argument - a string representing a file path.
 * 
 * @author Goran Brlas
 *
 */
public class SmartScriptTester {
	
	/**
	 * Main method which starts the program.
	 * @param args String representing a file path.
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
		if (args.length != 1) {
			System.err.println("There must be a file path argument!");
			System.exit(-1);
		}
		
		String docBody = new String(Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);
		SmartScriptParser parser = null;
		
		try {
			parser = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			System.out.println(e.getMessage());
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		
		//create the second parser and parse the parsed document
		SmartScriptParser parser2 = null;
		try {
			parser2 = new SmartScriptParser(originalDocumentBody);
		} catch (SmartScriptParserException e) {
			System.out.println(e.getMessage());
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		
		DocumentNode document2 = parser2.getDocumentNode();
		
		
		if (compareDocumentBodies(document, document2)) {
			System.out.println("Two document structures are identical.\n");
		} else {
			System.out.println("Two document structures are not identical.\n");
		}
		
		
		//Document text recreation test
		String documentBody2 = createOriginalDocumentBody(document2);
		System.out.println(originalDocumentBody);
		System.out.println();
		System.out.println(documentBody2);
		
	}
	
	/**
	 * Recreates the original document body from given DocumentNode.
	 * @param document DocumentNode needed to recreate the original document body.
	 * @return
	 */
	public static String createOriginalDocumentBody (DocumentNode document) {
		StringBuilder builder = new StringBuilder();
		builder = traverseTheTree(document, builder);
		return builder.toString();
	}
	
	/**
	 * Traverses the node structure, recreating the original document.
	 * @param node Starting DocumentNode.
	 * @param builder StringBuilder which builds the document body.
	 * @return StringBuilder.
	 */
	public static StringBuilder traverseTheTree(Node node, StringBuilder builder) {
		if (node.numberOfChildren() == 0)
			return builder;
		
		//recursively traverses the structure
		else {
			for (int i = 0; i < node.numberOfChildren(); i++) {
				if (node.getChild(i) instanceof ForLoopNode) {
					builder.append("{$FOR" + " " + ((ForLoopNode) node.getChild(i)).getVariable() + " " + ((ForLoopNode) node.getChild(i)).getStartExpression() + " " + ((ForLoopNode) node.getChild(i)).getEndExpression() + " " + ((ForLoopNode) node.getChild(i)).getStepExpression() + "$}");
					traverseTheTree(node.getChild(i), builder);
					builder.append("{$END$}");
				} else if (node.getChild(i) instanceof TextNode) {
					builder.append(((TextNode)(node.getChild(i))).asText());
				} else if (node.getChild(i) instanceof EchoNode) {
					builder.append("{$=");
					for (Token tok : ((EchoNode) node.getChild(i)).getTokens()) {
						builder.append(tok.asText() + " ");
					}
					builder.append("$}");
				}	
			}
		}
		
		return builder;
	}
	
	/**
	 * Returns boolean representation of whether two given document bodies are the same.
	 * @param documentBody1 Document node created after first parsing.
	 * @param documentBody2 Document node created after second parsing.
	 * @return Boolean value.
	 */
	public static boolean compareDocumentBodies (Node document, Node document2) {
		boolean test = true;
		
		if (document.numberOfChildren() != document2.numberOfChildren())
			return false;
		
		//recursively checks whether document structures are the same
		for (int i = 0; i < document.numberOfChildren(); i++) {
			if (document.getChild(i) instanceof ForLoopNode) {
				test = compareDocumentBodies(document.getChild(i), document2.getChild(i));
			}
		}
		
		return test;
	}
}
