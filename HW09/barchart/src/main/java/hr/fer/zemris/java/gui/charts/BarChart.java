package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Class representing char values.
 * Provides basic retrieval methods.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class BarChart {
	/**
	 * List holding chart values.
	 */
	private List<XYValue> valuesList;
	/**
	 * X-axis description.
	 */
	private String xOpis;
	/**
	 * Y-axis description.
	 */
	private String yOpis;
	/**
	 * Minimal y-axis value.
	 */
	private int yMin;
	/**
	 * Maximal y-axis value.
	 */
	private int yMax;
	/**
	 * Spacing between y-axis values.
	 */
	private int spacing;
	
	/**
	 * Constructor which sets the class values to the provided ones.
	 * 
	 * @param values List holding chart values.
	 * @param xOpis X-axis description.
	 * @param yOpis Y-axis description.
	 * @param yMin Minimal y-axis value.
	 * @param yMax Maximal y-axis value.
	 * @param spacing Spacing between y-axis values.
	 */
	public BarChart(List<XYValue> values, String xOpis, String yOpis, int yMin,
			int yMax, int spacing) {
		valuesList = values;
		this.xOpis = xOpis;
		this.yOpis = yOpis;
		this.yMin = yMin;
		this.yMax = yMax;
		
		if ((yMax - yMin) % spacing != 0) {
			this.spacing = (int) Math.ceil((yMax - yMin) / spacing);
		} else {
			this.spacing = spacing;
		}
	}
	
	/**
	 * List values getter.
	 * 
	 * @return List holding chart values.
	 */
	public List<XYValue> getValuesList() {
		return valuesList;
	}
	
	/**
	 * X-axis description getter.
	 * 
	 * @return X-axis description
	 */
	public String getxOpis() {
		return xOpis;
	}
	
	/**
	 * Y-axis description getter.
	 * @return Y-axis description
	 */
	public String getyOpis() {
		return yOpis;
	}
	
	/**
	 * Minimal y-axis value getter.
	 * 
	 * @return Minimal y-axis value.
	 */
	public int getyMin() {
		return yMin;
	}
	
	/**
	 * Maximal y-axis value getter.
	 * 
	 * @return Minimal y-axis value.
	 */
	public int getyMax() {
		return yMax;
	}
	
	/**
	 * Y-axis spacing getter.
	 * 
	 * @return Y-axis spacing.
	 */
	public int getSpacing() {
		return spacing;
	}
	
	
}
