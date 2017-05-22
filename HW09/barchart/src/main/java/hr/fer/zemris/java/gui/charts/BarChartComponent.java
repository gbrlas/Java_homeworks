package hr.fer.zemris.java.gui.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import javax.swing.JComponent;

/**
 * Class representing a display consisting of x-axis and y-axis values. Extends
 * JComponent.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class BarChartComponent extends JComponent {

	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Bar chart to be displayed.
	 */
	private BarChart chart;

	/**
	 * Constructor which sets the initial chart value to the provided one.
	 * 
	 * @param chart
	 *            Bar chart to be displayed.
	 */
	public BarChartComponent(BarChart chart) {
		this.chart = chart;
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		Font font = g2d.getFont();
		FontRenderContext context = g2d.getFontRenderContext();
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);
		g2d.setTransform(at);

		int strWidth = (int) font.getStringBounds(chart.getyOpis(), context)
				.getWidth();
		g2d.drawString(chart.getyOpis(),
				-(this.getHeight() - (this.getHeight() - strWidth) / 2), 20);

		at.rotate(Math.PI / 2);
		g2d.setTransform(at);

		strWidth = (int) font.getStringBounds(chart.getxOpis(), context)
				.getWidth();
		g2d.drawString(chart.getxOpis(), (this.getWidth()
				- (this.getWidth() - strWidth) / 2 - strWidth),
				this.getHeight() - 10);

		int xSpacing = (this.getWidth() - 100) / (chart.getValuesList().size());
		int ySpacing = (this.getHeight() - 60) / (chart.getyMax() / 2) - 2;
		int height = this.getHeight() - 60;
		for (int i = chart.getyMin();; i += chart.getSpacing()) {
			g2d.setColor(Color.BLACK);
			if (i < 10) {
				g2d.drawString(Integer.toString(i), 40, height);
			} else {
				g2d.drawString(Integer.toString(i), 33, height);
			}
			g2d.setColor(Color.DARK_GRAY);
			g2d.drawLine(70, height, this.getWidth() - 20, height);

			height -= ySpacing;

			if (height < 0) {
				break;
			}
		}

		g2d.setStroke(new BasicStroke(3));
		drawArrow(g2d, 70, 50, 70, 17, 5, 5);
		g2d.setStroke(new BasicStroke(1));

		height = this.getHeight() - 60;

		int width = 70;
		int j = 1;
		while (true) {
			g2d.setColor(Color.BLACK);
			g2d.drawString(Integer.toString(j), width + xSpacing / 2,
					this.getHeight() - 30);

			g2d.setColor(Color.RED);

			if (j < chart.getValuesList().size() + 1) {
				g2d.fill3DRect(width, height, xSpacing, -ySpacing
						* (chart.getValuesList().get(j - 1).getY()) / 2, true);
			}
			g2d.setColor(Color.DARK_GRAY);
			if (j == 1) {
				g2d.setStroke(new BasicStroke(3));
				g2d.drawLine(width, height, width, 0);
				g2d.setStroke(new BasicStroke(1));

			} else {
				g2d.drawLine(width, height, width, 0);
			}

			width += xSpacing;

			if (width > this.getWidth()) {
				break;
			}

			j++;
		}

		g2d.setStroke(new BasicStroke(3));
		g2d.draw(new Line2D.Double(70, height, this.getWidth() - 20, height));
		drawArrow(g2d, this.getWidth() - 20, height, this.getWidth() - 17,
				height, 4, 4);

	}

	/**
	 * Method which draws the arrow head.
	 * 
	 * @param g
	 *            Graphics2D object.
	 * @param x1
	 *            Starting point on x-axis.
	 * @param y1
	 *            Starting point on y-axis.
	 * @param x2
	 *            Ending point on x-axis.
	 * @param y2
	 *            Ending point on y-axis.
	 * @param al
	 *            Arrow length.
	 * @param aw
	 *            Arrow width.
	 */
	private void drawArrow(Graphics2D g, double x1, double y1, double x2,
			double y2, double al, double aw) {
		double x, y, length;
		Point2D start = new Point2D.Double(x1, y1);
		Point2D end = new Point2D.Double(x2, y2);
		Point2D base = new Point2D.Double();
		Point2D back_top = new Point2D.Double();
		Point2D back_bottom = new Point2D.Double();

		length = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));

		x = (x2 - x1) / length;
		y = (y2 - y1) / length;

		base.setLocation(x2 - x * al, y2 - y * al);
		back_top.setLocation(base.getX() - aw * y, base.getY() + aw * x);
		back_bottom.setLocation(base.getX() + aw * y, base.getY() - aw * x);

		g.draw(new Line2D.Double(start, end));
		g.draw(new Line2D.Double(end, back_bottom));
		g.draw(new Line2D.Double(end, back_top));
	}

}
