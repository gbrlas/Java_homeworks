package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;

/**
 * Class which represents a calculator button layout. Implements LayoutManager2.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class CalcLayout implements LayoutManager2 {
	/**
	 * Row separation value in pixels.
	 */
	private int rowSeparation;
	/**
	 * Column separation value in pixels.
	 */
	private int columnSeparation;

	/**
	 * Array holding layout components.
	 */
	Component[][] components = new Component[5][7];

	/**
	 * Empty constructor which sets the initial row and column separation to 0.
	 */
	public CalcLayout() {
		this(0);
	}

	/**
	 * Constructor which sets the initial row and column separation according to
	 * the provided values.
	 */
	public CalcLayout(int separation) {
		this.rowSeparation = separation;
		this.columnSeparation = separation;
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		RCPosition cstr;
		if (constraints instanceof String) {
			cstr = parseConstraint((String) constraints);
		} else if (constraints instanceof RCPosition) {
			cstr = (RCPosition) constraints;
		} else {
			throw new IllegalArgumentException("Invalid constraint provided!");
		}

		if (cstr.getRow() == 1
				&& (cstr.getColumn() == 2 || cstr.getColumn() == 3
						|| cstr.getColumn() == 4 || cstr.getColumn() == 5)) {
			throw new IllegalArgumentException(
					"Invalid constraint index provided.");
		}

		if (cstr.getRow() < 1 || cstr.getRow() > 5 || cstr.getColumn() < 1
				|| cstr.getColumn() > 7) {
			throw new IllegalArgumentException(
					"Invalid constraint index provided.");
		}

		if (components[cstr.getRow() - 1][cstr.getColumn() - 1] != null) {
			throw new IllegalArgumentException(
					"Component already exists there!");
		}

		components[cstr.getRow() - 1][cstr.getColumn() - 1] = comp;
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0.5f;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0.5f;
	}

	@Override
	public void invalidateLayout(Container target) {
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		int top = insets.top;
		int left = insets.left;

		int tempHeight = 0;
		int tempWidth = 0;

		int psize1 = parent.getSize().width - insets.left - insets.right;
		int psize2 = parent.getSize().height - insets.top - insets.bottom;

		int tempComp2 = (psize1 - 6 * rowSeparation) / 7;

		Component component;

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 7; j++) {
				component = components[i][j];

				if (component != null) {
					if (i == 0 && j == 0) {
						component.setSize((psize1 - 6 * columnSeparation) / 7
								* 5 + 4 * columnSeparation,
								(psize2 - 4 * rowSeparation) / 5);

						Dimension d = component.getSize();

						component.setBounds(left, top, d.width, d.height);
						tempHeight = d.height;
						tempWidth = d.width + columnSeparation;
						j = 4;
						continue;
					} else {
						component.setSize((psize1 - 6 * columnSeparation) / 7,
								(psize2 - 4 * rowSeparation) / 5);

						Dimension d = component.getSize();

						component.setBounds(tempWidth, top, d.width, d.height);
					}
				}

				tempWidth += tempComp2 + columnSeparation;
			}

			top += tempHeight + rowSeparation;
			tempWidth = 0;
		}
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		int maxWidth = 0, maxHeight = 0;
		int widthtemp = 0;

		Component[] components = target.getComponents();

		for (Component c : components) {
			if (c.getHeight() > maxHeight) {
				maxHeight = c.getMaximumSize().height;
			}

			if (c.getWidth() > maxWidth) {
				widthtemp = maxWidth;
				maxWidth = c.getMaximumSize().width;
			}
		}

		maxHeight = 5 * maxHeight + 4 * rowSeparation;
		if (maxWidth > 2 * widthtemp) {
			maxWidth = maxWidth + 2 * widthtemp + 2 * columnSeparation;
		}

		Insets insets = target.getInsets();
		maxWidth = insets.left + insets.right;
		maxHeight = insets.top + insets.bottom;

		return new Dimension(maxWidth, maxHeight);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		int minWidth = 0, minHeight = 0;
		int widthtemp = 0;

		Insets insets = parent.getInsets();

		Component[] components = parent.getComponents();

		for (Component c : components) {
			if (c.getMinimumSize().height > minHeight) {
				minHeight = c.getMinimumSize().height;
			}

			if (c.getMinimumSize().width > minWidth) {
				widthtemp = minWidth;
				minWidth = c.getMinimumSize().width;
			}
		}

		minHeight = 5 * minHeight + 4 * rowSeparation + insets.top
				+ insets.bottom;
		if (minWidth > 2 * widthtemp) {
			minWidth = minWidth + 2 * widthtemp + 2 * columnSeparation
					+ insets.left + insets.right;
		} else {
			minWidth = widthtemp * 7 + 6 * columnSeparation + insets.left
					+ insets.right;
		}

		return new Dimension(minWidth, minHeight);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		int maxWidth = 0, maxHeight = 0;

		int maxHeightRow = 0, maxWidthRow = 0;

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 7; j++) {
				if (components[i][j] != null) {
					if (components[i][j].getPreferredSize() != null) {
						maxHeight += components[i][j].getPreferredSize().height;
					}

					if (components[i][j].getPreferredSize() != null) {
						maxWidth += components[i][j].getPreferredSize().width;
					}
				}
			}

			if (i == 0) {
				maxHeightRow = maxHeight;
				maxWidthRow = maxWidth;
			}

			if (maxHeight > maxHeightRow) {
				maxHeightRow = maxHeight;
			}

			if (maxWidth > maxWidthRow) {
				maxWidthRow = maxWidth;
			}

			maxHeight = 0;
			maxWidth = 0;
		}

		maxHeightRow += 4 * rowSeparation;
		maxWidthRow += 6 * columnSeparation;

		Insets insets = parent.getInsets();
		maxWidthRow += insets.left + insets.right;
		maxHeightRow += insets.top + insets.bottom;

		return new Dimension(maxWidthRow, maxHeightRow);
	}

	@Override
	public void removeLayoutComponent(Component comp) {
	}

	/**
	 * Helper method used for parsing constraints.
	 * 
	 * @param constraint
	 *            Element position constraint.
	 * @return RCPosition representing a constraint.
	 */
	private RCPosition parseConstraint(String constraint) {
		constraint = constraint.trim().replace("//s++", "");
		String[] nums = constraint.split(",");

		if (nums.length != 2) {
			throw new IllegalArgumentException("Invalid constraint passed!");
		}

		try {
			int rowConstraint = Integer.parseInt(nums[0]);
			int colsConstraint = Integer.parseInt(nums[1]);

			return new RCPosition(rowConstraint, colsConstraint);
		} catch (Exception exception) {
			throw new IllegalArgumentException(
					"Invalid constraint passed, parsing failed.");
		}
	}
}
