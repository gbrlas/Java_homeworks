package hr.fer.zemris.java.hw10.jnotepadpp;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Singleton class which provides the localization. Currently supports Croatian,
 * English and German. Extends AbstractLocalizationProvider.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {
	/**
	 * Instance of the LocalizationProvider.
	 */
	private static final LocalizationProvider instance = new LocalizationProvider();
	/**
	 * String representing the current language.
	 */
	private String language;
	/**
	 * Language resource bundle.
	 */
	private ResourceBundle bundle;

	/**
	 * Constructor which sets the default language to English, loads the
	 * resource bundle for that language and stores reference to it.
	 */
	private LocalizationProvider() {
		language = "en";
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle(
				"hr.fer.zemris.java.hw10.jnotepadpp.prijevodi", locale);
	}

	/**
	 * Returns an instance of the LocalizationProvider.
	 * 
	 * @return LocalizationProvider instance.
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}

	/**
	 * Method which sets the current language to the provided one. Notifies all
	 * the listeners of this change.
	 * 
	 * @param language
	 *            Language to be set as the current language.
	 */
	public void setLanguage(String language) {
		this.language = language;
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle(
				"hr.fer.zemris.java.hw10.jnotepadpp.prijevodi", locale);
		fire();
	}

	public String getString(String str) {
		return bundle.getString(str);
	}
}
