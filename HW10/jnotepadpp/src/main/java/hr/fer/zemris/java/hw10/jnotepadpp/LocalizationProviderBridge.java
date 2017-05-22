package hr.fer.zemris.java.hw10.jnotepadpp;

/**
 * Class which is a decorator for some other ILocalizationProvider. Offers two
 * additional methods: connect() and disconnect(), and it manages a connection
 * status. Extends AbstractLocalizationProvider.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {
	/**
	 * Boolean representing whether the bridge is connected.
	 */
	private boolean connected = false;
	/**
	 * Localization provider.
	 */
	public ILocalizationProvider provider;
	/**
	 * Localization listener.
	 */
	private ILocalizationListener listener;

	/**
	 * Constructor which sets the localization provider to the provided one.
	 * 
	 * @param provider
	 *            Localization provider to be set.
	 */
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		this.provider = provider;
	}

	/**
	 * Method which registers the ILocalizationListener on the decorated object.
	 */
	public void connect() {
		if (!connected) {
			connected = true;
		}

		listener = new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				fire();
			}
		};

		provider.addLocalizationListener(listener);
	}

	/**
	 * Method which de-registers this object from the decorated object.
	 */
	public void disconnect() {
		connected = false;
		provider.removeLocalizationListener(listener);
		provider = null;
	}

	@Override
	public String getString(String str) {
		return provider.getString(str);
	}
}
