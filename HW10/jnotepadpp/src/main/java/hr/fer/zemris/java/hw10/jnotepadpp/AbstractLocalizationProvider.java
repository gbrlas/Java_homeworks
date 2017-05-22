package hr.fer.zemris.java.hw10.jnotepadpp;

import java.util.ArrayList;

/**
 * Class which adds the ability to register, de-register and inform localization
 * provider listeners. Implements ILocalizationProvider.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public abstract class AbstractLocalizationProvider implements
		ILocalizationProvider {

	/**
	 * List containing all the listeners.
	 */
	ArrayList<ILocalizationListener> listeners = new ArrayList<ILocalizationListener>();

	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Method which notifies all the listeners that there has been a language
	 * change.
	 */
	public void fire() {
		for (ILocalizationListener listener : listeners) {
			listener.localizationChanged();
		}
	}
}
