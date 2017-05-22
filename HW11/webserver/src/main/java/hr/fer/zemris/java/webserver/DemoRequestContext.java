package hr.fer.zemris.java.webserver;

import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class which demo tests the requestContext class.
 *
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class DemoRequestContext {

	/**
	 * Main method which starts the program. Takes no arguments.
	 *
	 * @param args
	 *            - none
	 * @throws IOException
	 *             If there is an error while writing data.
	 */
	public static void main(final String[] args) throws IOException {
		demo1("primjer1.txt", "ISO-8859-2");
		demo1("primjer2.txt", "UTF-8");
		demo2("primjer3.txt", "UTF-8");
	}

	/**
	 * Method which tests the RCContext class.
	 *
	 * @param filePath
	 *            File path.
	 * @param encoding
	 *            File encoding.
	 * @throws IOException
	 *             if there is an error writing data.
	 */
	private static void demo1(final String filePath, final String encoding)
			throws IOException {
		final OutputStream os = Files.newOutputStream(Paths.get(filePath));

		final RequestContext rc = new RequestContext(os,
				new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());

		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");

		rc.write("Čevapčići i šišćevapćići");

		os.close();
	}

	/**
	 * Method which tests the RCContext class.
	 *
	 * @param filePath
	 *            File path.
	 * @param encoding
	 *            File encoding.
	 * @throws IOException
	 *             if there is an error writing data.
	 */
	private static void demo2(final String filePath, final String encoding)
			throws IOException {
		final OutputStream os = Files.newOutputStream(Paths.get(filePath));

		final RequestContext rc = new RequestContext(os,
				new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());

		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		rc.addRCCookies(new RCCookie("korisnik", "perica", 3600, "127.0.0.1.",
				"/"));
		rc.addRCCookies(new RCCookie("zgrada", "B4", null, null, "/"));

		rc.write("Čevapčići i šišćevapčići");

		os.close();
	}

}
