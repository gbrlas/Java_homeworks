package hr.fer.zemris.java.hw10.jnotepadpp;

/**
 * Interface which notifies all registered listener when a selected language has
 * changed. Objects which are instances of classes that implement this interface
 * will be able to give us the translations for given keys.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public interface ILocalizationProvider {
	/**
	 * Adds the provided LocalizationListener to the listener list.
	 * 
	 * @param listener
	 *            Listener to be added to the listener list.
	 */
	public void addLocalizationListener(ILocalizationListener listener);

	/**
	 * Removes the provided LocalizationListener from the listener list.
	 * 
	 * @param listener
	 *            Listener to be removed from the listener list.
	 */
	public void removeLocalizationListener(ILocalizationListener listener);

	/**
	 * Method which takes a provided string and returns its localization.
	 * 
	 * @param str
	 *            Localization key.
	 * @return Key localization.
	 */
	public String getString(String str);
}
