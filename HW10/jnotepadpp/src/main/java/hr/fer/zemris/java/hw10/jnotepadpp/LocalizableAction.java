package hr.fer.zemris.java.hw10.jnotepadpp;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * Class which represents a localizable action. Extends AbstractAction.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class LocalizableAction extends AbstractAction {

	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Key representing a localized version of the action name.
	 */
	private String key;
	/**
	 * Key representing a localized version of the action description.
	 */
	private String key2;

	/**
	 * Constructor which sets the action name according to the current
	 * localization
	 * 
	 * @param str
	 *            Localization key.
	 * @param lp
	 *            Localization provider.
	 */
	public LocalizableAction(String key, String key2, ILocalizationProvider lp) {
		this.key = key;
		this.key2 = key2;

		String name = lp.getString(key);
		String desc = lp.getString(key2);

		this.putValue(Action.NAME, name);
		this.putValue(Action.SHORT_DESCRIPTION, desc);

		lp.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				putValue(Action.NAME, lp.getString(LocalizableAction.this.key));
				putValue(Action.SHORT_DESCRIPTION,
						lp.getString(LocalizableAction.this.key2));
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
