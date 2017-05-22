package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Simple worker whose job is to produce an PNG image with dimensions 200x200
 * with a single filled circle. The circle color is randomly chosen.
 *
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class CircleWorker implements IWebWorker {

	@Override
	public void processRequest(final RequestContext context) {
		final int width = 200;
		final int height = 200;

		final BufferedImage bim = new BufferedImage(width, height,
				BufferedImage.TYPE_3BYTE_BGR);
		final Graphics2D g = bim.createGraphics();

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);

		g.setColor(new Color((float) Math.random(), (float) Math.random(),
				(float) Math.random()));
		g.fillOval(0, 0, width, height);

		g.dispose();

		context.setMimeType("image/png");
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ImageIO.write(bim, "png", bos);
			context.write(bos.toByteArray());
		} catch (final IOException exception) {
			exception.printStackTrace();
		}
	}
}
