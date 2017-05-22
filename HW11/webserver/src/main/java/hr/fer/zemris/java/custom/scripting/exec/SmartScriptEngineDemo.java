package hr.fer.zemris.java.custom.scripting.exec;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class which demo tests the SmartScriptEngine.
 *
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class SmartScriptEngineDemo {

	/**
	 * Main method which starts the program. Takes one argument - a file path.
	 *
	 * @param args
	 *            File path.
	 * @throws IOException
	 *             if there is a problem while reading from file.
	 */
	public static void main(final String[] args) throws IOException {
		if (args.length != 1) {
			System.err.println("There must be a file path argument!");
			System.exit(-1);
		}

		final String docBody = new String(
				Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);
		final Map<String, String> parameters = new HashMap<String, String>();
		final Map<String, String> persistentParameters = new HashMap<String, String>();
		final List<RCCookie> cookies = new ArrayList<RCCookie>();
		parameters.put("a", "4");
		parameters.put("b", "2");
		persistentParameters.put("brojPoziva", "3");

		final RequestContext rc = new RequestContext(System.out, parameters,
				persistentParameters, cookies);

		new SmartScriptEngine(new SmartScriptParser(docBody).getDocumentNode(),
				rc).execute();

		System.out.println();
		System.out.println("Vrijednost u mapi: "
				+ rc.getPersistentParameter("brojPoziva"));
	}
}
