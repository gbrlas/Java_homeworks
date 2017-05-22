package hr.fer.zemris.java.hw10.jnotepadpp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 * Class which represents a label which shows current time.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class ClockLabel extends JLabel implements ActionListener {

	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor which sets the default time.
	 */
	public ClockLabel() {
		super(""
				+ new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
						.format(new Date()), SwingConstants.RIGHT);
		Timer t = new Timer(1000, this);
		t.start();
	}

	public void actionPerformed(ActionEvent ae) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		this.setText(dateFormat.format(new Date()));
	}
}
