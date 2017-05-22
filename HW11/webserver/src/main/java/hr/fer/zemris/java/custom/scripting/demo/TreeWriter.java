package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.tokens.Token;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Program which accepts a file name from the command line. The file is opened
 * and parsed into a tree which reproduces its approximate original form onto
 * standard output using the visitor pattern.
 *
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class TreeWriter {

	/**
	 * Class which visits all the document nodes and does the needed processing.
	 *
	 * @author Goran Brlas
	 * @version 1.0
	 *
	 */
	public static class WriterVisitor implements INodeVisitor {
		@Override
		public final void visitDocumentNode(final DocumentNode node) {
			if (node.numberOfChildren() == 0) {
				System.out.println("");
				return;
			}

			for (int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}
		}

		@Override
		public final void visitEchoNode(final EchoNode node) {
			final StringBuilder builder = new StringBuilder();

			builder.append("{$=");
			for (final Token tok : node.getTokens()) {
				builder.append(tok.asText() + " ");
			}
			builder.append("$}");

			System.out.print(builder.toString());
		}

		@Override
		public final void visitForLoopNode(final ForLoopNode node) {
			final StringBuilder builder = new StringBuilder();

			builder.append("{$FOR" + " " + node.getVariable() + " "
					+ node.getStartExpression() + " " + node.getEndExpression()
					+ " " + node.getStepExpression() + "$}");

			System.out.print(builder.toString());
			for (int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}

			System.out.print("{$END$}");
		}

		@Override
		public final void visitTextNode(final TextNode node) {
			System.out.print(node.asText());
		}
	}

	/**
	 * Main method which starts the program. Takes one argument - a file path.
	 * The file is then opened and parsed into a tree.
	 *
	 * @param args
	 *            One argument - a file path.
	 * @throws IOException
	 *             if provided argument is not a valid file path.
	 */
	public static void main(final String[] args) throws IOException {

		if (args.length != 1) {
			System.err.println("There must be a file path argument!");
			System.exit(-1);
		}

		final String docBody = new String(
				Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);
		final SmartScriptParser parser = new SmartScriptParser(docBody);

		final WriterVisitor visitor = new WriterVisitor();

		parser.getDocumentNode().accept(visitor);
	}

}
