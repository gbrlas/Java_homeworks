package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Simple worker whose job is to create a HTML page with current time displayed
 * and to give a different message depending if a parameter called "name" was
 * provided in the URL request which started this worker.
 *
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class HelloWorker implements IWebWorker {

	@Override
	public void processRequest(final RequestContext context) {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		final Date now = new Date();

		context.setMimeType("text/html");

		final String name = context.getParameter("name");

		try {
			context.write("<html><body>");
			context.write("<h1>Hello!!!</h1>");
			context.write("<p>Now is: " + sdf.format(now) + "</p>");

			if (name == null || name.trim().isEmpty()) {
				context.write("<p>You did not send me your name!</p>");
			} else {
				context.write("<p>Your name has " + name.trim().length()
						+ " letters.</p>");
			}

			context.write("</body></html>");
		} catch (final IOException exception) {
			exception.printStackTrace();
		}
	}
}
