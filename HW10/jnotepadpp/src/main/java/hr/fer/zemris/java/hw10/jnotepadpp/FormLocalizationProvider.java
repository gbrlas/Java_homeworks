package hr.fer.zemris.java.hw10.jnotepadpp;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

/**
 * Class which registers itself as a WindowListener to its JFrame. When frame is
 * opened, it calls connect and when frame is closed, it calls disconnect
 * Extends LocalizationProviderBridge.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	/**
	 * Constructor which call the super() constructor and registers itself as a
	 * WindowListener to the provided frame.
	 * 
	 * @param provider
	 *            Localization provider.
	 * @param frame
	 *            Frame which adds this class as its window listener.
	 */
	public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
		super(provider);

		frame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

}
