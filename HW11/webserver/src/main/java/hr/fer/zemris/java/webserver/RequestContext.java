package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class which represents a request context.
 *
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class RequestContext {

	/**
	 * Class which represents a cookie. Has read-only properties.
	 *
	 * @author Goran Brlas
	 * @version 1.0
	 *
	 */
	public static class RCCookie {
		/**
		 * Cookie name.
		 */
		private final String name;
		/**
		 * Cookie name value.
		 */
		private final String value;
		/**
		 * Cookie domain.
		 */
		private final String domain;
		/**
		 * Cookie path.
		 */
		private final String path;
		/**
		 * Cookie maximum age.
		 */
		private final int maxAge;

		/**
		 * Constructor which sets the class instance variables to the provided
		 * ones.
		 *
		 * @param name
		 *            Cookie name.
		 * @param value
		 *            Cookie name value.
		 * @param maxAge
		 *            Cookie maxAge.
		 * @param domain
		 *            Cookie domain.
		 * @param path
		 *            Cookie path.
		 */
		public RCCookie(final String name, final String value,
				final Integer maxAge, final String domain, final String path) {
			super();
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge == null ? 0 : maxAge;
		}

		/**
		 * Cookie name getter.
		 *
		 * @return Cookie name.
		 */
		public String getName() {
			return name;
		}

		/**
		 * Cookie value getter.
		 *
		 * @return Cookie name value.
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Cookie domain getter.
		 *
		 * @return Cookie domain.
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * Cookie path getter.
		 *
		 * @return Cookie path.
		 */
		public String getPath() {
			return path;
		}

		/**
		 * Cookie maximum age getter.
		 *
		 * @return Cookie maximum age.
		 */
		public int getMaxAge() {
			return maxAge;
		}
	}

	/**
	 * Context output stream.
	 */
	private final OutputStream outputStream;
	/**
	 * Context charset.
	 */
	private Charset charset;
	/**
	 * Context encoding.
	 */
	private String encoding = "UTF-8";
	/**
	 * Content status code.
	 */
	private int statusCode = 200;
	/**
	 * Content status text.
	 */
	private String statusText = "OK";
	/**
	 * Context mime type.
	 */
	private String mimeType = "text/html";
	/**
	 * Context persistent parameters map.
	 */
	private final Map<String, String> persistentParameters;
	/**
	 * Context temporary parameters map.
	 */
	private final Map<String, String> temporaryParameters = new HashMap<String, String>();
	/**
	 * Context parameters map.
	 */
	private final Map<String, String> parameters;
	/**
	 * List containing all context cookies.
	 */
	private List<RCCookie> outputCookies;
	/**
	 * Boolean representing whether a header has already been generated.
	 */
	private boolean headerGenerated = false;

	/**
	 * Constructor which sets the class instance variables to the provided ones.
	 *
	 * @param outputStream
	 *            Context output stream.
	 * @param parameters
	 *            Context parameters.
	 * @param persistentParameters
	 *            Context persistent parameters.
	 * @param outputCookies
	 *            Context output cookies.
	 * @throws IllegalArgumentException
	 *             if provided outputStream is null.
	 */
	public RequestContext(final OutputStream outputStream,
			final Map<String, String> parameters,
			final Map<String, String> persistentParameters,
			final List<RCCookie> outputCookies) {

		if (outputStream == null) {
			throw new IllegalArgumentException(
					"Provided output stream must not be null.");
		}

		this.outputStream = outputStream;
		this.parameters = parameters == null ? new HashMap<String, String>()
				: parameters;
		this.persistentParameters = persistentParameters == null ? new HashMap<String, String>()
				: persistentParameters;
		this.outputCookies = outputCookies == null ? new ArrayList<RequestContext.RCCookie>()
				: outputCookies;
	}

	/**
	 * Returns the parameter stored under provided name, or null if the map
	 * contains no mapping for the key.
	 *
	 * @param name
	 *            Map key.
	 * @return Parameter stored under provided key.
	 * @throws IllegalArgumentException
	 *             - if provided key is null.
	 */
	public String getParameter(final String name)
			throws IllegalArgumentException {
		if (name == null) {
			throw new IllegalArgumentException(
					"Passed parameter must not be null.");
		}
		return parameters.get(name);
	}

	/**
	 * Return an unmodifiable set containing all parameter names.
	 *
	 * @return Unmodifiable set.
	 */
	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet(parameters.keySet());
	}

	/**
	 * Returns the persistent parameter stored under provided name, or null if
	 * the map contains no mapping for the key.
	 *
	 * @param name
	 *            Map key.
	 * @return Parameter stored under provided key.
	 * @throws IllegalArgumentException
	 *             - if provided key is null.
	 */
	public String getPersistentParameter(final String name)
			throws IllegalArgumentException {
		if (name == null) {
			throw new IllegalArgumentException(
					"Passed parameter must not be null.");
		}
		return persistentParameters.get(name);
	}

	/**
	 * Return an unmodifiable set containing all persistent parameter names.
	 *
	 * @return Unmodifiable set.
	 */
	public Set<String> getPersistentParameterNames() {
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}

	/**
	 * Stores provided value to the persistent parameters map.
	 *
	 * @param name
	 *            Map key.
	 * @param value
	 *            Value to be stored.
	 * @throws IllegalArgumentException
	 *             - if key or value are null.
	 */
	public void setPersistentParameter(final String name, final String value)
			throws IllegalArgumentException {
		if (name == null || value == null) {
			throw new IllegalArgumentException(
					"Passed parameters must not be null.");
		}

		persistentParameters.put(name, value);
	}

	/**
	 * Removes a value from the persistent parameters map stored under provided
	 * key.
	 *
	 * @param name
	 *            Map key.
	 * @throws IllegalArgumentException
	 *             - if provided key is null.
	 */
	public void removePersistentParameter(final String name)
			throws IllegalArgumentException {
		if (name == null) {
			throw new IllegalArgumentException(
					"Passed parameter must not be null.");
		}

		persistentParameters.remove(name);
	}

	/**
	 * Returns the temporary parameter stored under provided name, or null if
	 * the map contains no mapping for the key.
	 *
	 * @param name
	 *            Map key.
	 * @return Parameter stored under provided key.
	 * @throws IllegalArgumentException
	 *             - if provided key is null.
	 */
	public String getTemporaryParameter(final String name)
			throws IllegalArgumentException {
		if (name == null) {
			throw new IllegalArgumentException(
					"Passed parameter must not be null.");
		}

		return temporaryParameters.get(name);
	}

	/**
	 * Return an unmodifiable set containing all temporary parameter names.
	 *
	 * @return Unmodifiable set.
	 */
	public Set<String> getTemporaryParameterNames() {
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}

	/**
	 * Stores provided value to the temporary parameters map.
	 *
	 * @param name
	 *            Map key.
	 * @param value
	 *            Value to be stored.
	 * @throws IllegalArgumentException
	 *             - if key or value are null.
	 */
	public void setTemporaryParameter(final String name, final String value)
			throws IllegalArgumentException {
		if (name == null || value == null) {
			throw new IllegalArgumentException(
					"Passed parameter must not be null.");
		}

		temporaryParameters.put(name, value);
	}

	/**
	 * Removes a value from the temporary parameters map stored under provided
	 * key.
	 *
	 * @param name
	 *            Map key.
	 * @throws IllegalArgumentException
	 *             - if provided key is null.
	 */
	public void removeTemporaryParameter(final String name)
			throws IllegalArgumentException {
		if (name == null) {
			throw new IllegalArgumentException(
					"Passed parameter must not be null.");
		}

		temporaryParameters.remove(name);
	}

	/**
	 * Writes provided data into the outputStream previously provided to the
	 * RequestContext.
	 *
	 * @param data
	 *            Byte array to be written into the outputStream.
	 * @return This class instance.
	 * @throws IOException
	 *             - if there is a problem while writing data.
	 */
	public RequestContext write(final byte[] data) throws IOException {
		if (!headerGenerated) {
			generateHeader();
		}

		outputStream.write(data);
		outputStream.flush();
		return this;
	}

	/**
	 * Writes provided data into the outputStream previously provided to the
	 * RequestContext. Converts given string into bytes using previously
	 * configured encoding.
	 *
	 * @param text
	 *            Byte array to be written into the outputStream.
	 * @return This class instance.
	 * @throws IOException
	 *             - if there is a problem while writing data.
	 */
	public RequestContext write(final String text) throws IOException {
		if (!headerGenerated) {
			generateHeader();
		}

		final byte[] data = text.replace("\"", "").replace("\\r\\n", "\r\n")
				.getBytes(charset);
		outputStream.write(data);

		return this;
	}

	/**
	 * Private method which generates the context header.
	 *
	 * @throws IOException
	 *             - if there is a problem while writing data.
	 */
	private void generateHeader() throws IOException {
		charset = Charset.forName(encoding);

		final byte[] header = ("HTTP/1.1 "
				+ statusCode
				+ " "
				+ statusText
				+ "\r\n"
				+ "Content-Type: "
				+ mimeType
				+ (mimeType.substring(0, 5).equals("text/") ? "; charset="
						+ encoding : "") + "\r\n" + printCookies() + "\r\n")
				.getBytes(StandardCharsets.ISO_8859_1);

		outputStream.write(header);

		headerGenerated = true;
	}

	/**
	 * Private method which puts all the context cookies into a string.
	 *
	 * @return String containing all the context cookies.
	 */
	private String printCookies() {
		if (outputCookies.isEmpty()) {
			return "";
		}

		final StringBuilder builder = new StringBuilder();

		for (final RCCookie cookie : outputCookies) {
			builder.append("Set-Cookie: " + cookie.getName() + "=\""
					+ cookie.getValue() + "\"");
			if (cookie.getDomain() != null) {
				builder.append("; Domain=" + cookie.getDomain());
			}
			if (cookie.getPath() != null) {
				builder.append("; Path=" + cookie.getPath());
			}
			if (cookie.getMaxAge() == 3600) {
				builder.append("; Max-Age=" + cookie.getMaxAge());
			}
			builder.append("\r\n");
		}

		return builder.toString();
	}

	/**
	 * Adds the provided RCCookie to the cookies list.
	 *
	 * @param cookie
	 *            Cookie to be added to the cookies list.
	 * @throws IllegalArgumentException
	 *             - if provided cookie is null.
	 */
	public void addRCCookies(final RCCookie cookie)
			throws IllegalArgumentException {
		if (cookie == null) {
			throw new IllegalArgumentException(
					"Passed parameter must not be null.");
		}
		outputCookies.add(cookie);
	}

	/**
	 * Sets the encoding to the provided one. Can be used only before the header
	 * is generated, otherwise throws runtime exception.
	 *
	 * @param encoding
	 *            String representing the encoding.
	 * @throws RuntimeException
	 *             - if the method is called after the header has already been
	 *             generated.
	 */
	public void setEncoding(final String encoding) throws RuntimeException {
		if (headerGenerated) {
			throw new RuntimeException(
					"Header has already been generated, cannot change properties.");
		}
		this.encoding = encoding;
	}

	/**
	 * Sets the status code to the provided one. Can be used only before the
	 * header is generated, otherwise throws exception.
	 *
	 * @param statusCode
	 *            Integer representing the status code.
	 * @throws RuntimeException
	 *             - if the method is called after the header has already been
	 *             generated.
	 */
	public void setStatusCode(final int statusCode) throws RuntimeException {
		if (headerGenerated) {
			throw new RuntimeException(
					"Header has already been generated, cannot change properties.");
		}
		this.statusCode = statusCode;
	}

	/**
	 * Sets the status text to the provided one. Can be used only before the
	 * header is generated, otherwise throws exception.
	 *
	 * @param statusText
	 *            String representing the status text.
	 * @throws RuntimeException
	 *             - if the method is called after the header has already been
	 *             generated.
	 */
	public void setStatusText(final String statusText) throws RuntimeException {
		if (headerGenerated) {
			throw new RuntimeException(
					"Header has already been generated, cannot change properties.");
		}
		this.statusText = statusText;
	}

	/**
	 * Sets the mime type to the provided one. Can be used only before the
	 * header is generated, otherwise throws exception.
	 *
	 * @param mimeType
	 *            String representing the mime type.
	 * @throws RuntimeException
	 *             - if the method is called after the header has already been
	 *             generated.
	 */
	public void setMimeType(final String mimeType) throws RuntimeException {
		if (headerGenerated) {
			throw new RuntimeException(
					"Header has already been generated, cannot change properties.");
		}
		this.mimeType = mimeType;
	}

	/**
	 * Sets the outputCookies list to the provided one. Can be used only before
	 * the header is generated, otherwise throws exception.
	 *
	 * @param outputCookies
	 *            RCCookie list.
	 * @throws RuntimeException
	 *             - if the method is called after the header has already been
	 *             generated.
	 */
	public void setOutputCookies(final List<RCCookie> outputCookies)
			throws RuntimeException {
		if (headerGenerated) {
			throw new RuntimeException(
					"Header has already been generated, cannot change properties.");
		}
		this.outputCookies = outputCookies;
	}

}
