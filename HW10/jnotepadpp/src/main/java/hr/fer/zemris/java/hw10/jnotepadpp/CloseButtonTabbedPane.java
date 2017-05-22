package hr.fer.zemris.java.hw10.jnotepadpp;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.plaf.metal.MetalIconFactory;

/**
 * Class which represents a tabbed pane with added functionality - a button used
 * to close a tab. Extends JTabbedPane.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class CloseButtonTabbedPane extends JTabbedPane {
	/**
	 * Instance of a notepad.
	 */
	private JNotepadPP notepadPP;
	/**
	 * Form localization provider instance.
	 */
	private FormLocalizationProvider flp;
	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor which sets the notepad and flp to the provided values.
	 * 
	 * @param notepadPP
	 *            Instance of a notepad.
	 * @param flp
	 *            Form localization provider instance.
	 */
	public CloseButtonTabbedPane(JNotepadPP notepadPP,
			FormLocalizationProvider flp) {
		this.notepadPP = notepadPP;
		this.flp = flp;
	}

	@Override
	public void addTab(String title, Icon icon, Component component, String tip) {
		super.addTab(title, icon, component, tip);
		int count = this.getTabCount() - 1;
		setTabComponentAt(count, new CloseButtonTab(component, title, icon));
	}

	@Override
	public void addTab(String title, Icon icon, Component component) {
		addTab(title, icon, component, null);
	}

	@Override
	public void addTab(String title, Component component) {
		addTab(title, null, component);
	}

	/**
	 * Class which represents a tab with a closeable button functionality.
	 * 
	 * @author Goran Brlas
	 * @version 1.0
	 *
	 */
	public class CloseButtonTab extends JPanel {
		/**
		 * SerialVersionUID
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Constructor which sets the values according to the provided ones.
		 * 
		 * @param tab
		 *            Tab component.
		 * @param title
		 *            Tab title.
		 * @param icon
		 *            Tab icon.
		 */
		public CloseButtonTab(final Component tab, String title, Icon icon) {
			setOpaque(false);
			FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 3, 3);
			setLayout(flowLayout);
			setVisible(true);

			JLabel jLabel = new JLabel(title);
			jLabel.setIcon(icon);
			add(jLabel);

			JButton button = new JButton(
					MetalIconFactory.getInternalFrameCloseIcon(16));
			button.setMargin(new Insets(0, 0, 0, 0));
			button.setBorder(BorderFactory
					.createLineBorder(Color.LIGHT_GRAY, 1));

			button.addMouseListener(new MouseListener() {
				public void mouseClicked(MouseEvent e) {
					JTabbedPane tabbedPane = (JTabbedPane) getParent()
							.getParent();

					int r = JOptionPane.showConfirmDialog(notepadPP,
							flp.getString("areYou"), flp.getString("warning"),
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (r != JOptionPane.YES_OPTION) {
						return;
					} else {
						tabbedPane.remove(tab);
						JNotepadPP.tabIndex--;
					}
				}

				public void mousePressed(MouseEvent e) {
				}

				public void mouseReleased(MouseEvent e) {
				}

				public void mouseEntered(MouseEvent e) {
					JButton button = (JButton) e.getSource();
					button.setBorder(BorderFactory.createLineBorder(
							Color.DARK_GRAY, 1));
				}

				public void mouseExited(MouseEvent e) {
					JButton button = (JButton) e.getSource();
					button.setBorder(BorderFactory.createLineBorder(
							Color.LIGHT_GRAY, 1));
				}
			});

			add(button);
		}
	}
}