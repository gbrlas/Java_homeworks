package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Simple worker whose job is to output back to the user parameters it obtained
 * in a HTML table from the web browser.
 *
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class EchoWorker implements IWebWorker {

	@Override
	public void processRequest(final RequestContext context) {
		final StringBuilder sb = new StringBuilder("<html>\r\n" + " <head>\r\n"
				+ " <title>Recieved parameters</title>\r\n"
				+ " <h1>Recieved parameters: </h1>\r\n"
				+ " <table border='1'>\r\n");

		for (final String parameter : context.getParameterNames()) {
			sb.append(" <tr><td>").append(parameter).append("</td><td>")
					.append(context.getParameter(parameter))
					.append("</td></tr>\r\n");
		}

		sb.append(" </table>\r\n" + " </body>\r\n" + "</html>\r\n");

		final byte[] tijeloOdgovora = sb.toString().getBytes(
				StandardCharsets.UTF_8);

		try {
			context.write(tijeloOdgovora);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
}
