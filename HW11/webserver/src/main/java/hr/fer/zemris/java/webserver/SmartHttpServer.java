package hr.fer.zemris.java.webserver;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;
import hr.fer.zemris.java.webserver.workers.EchoWorker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class representing a simple server capable of providing files and smart
 * script results to the client. Server configuration is detailed in the
 * server.properties file. The server can be stopped by typing "stop" or "quit"
 * in the console.
 *
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class SmartHttpServer {
	/**
	 * Server's address.
	 */
	private final String address;
	/**
	 * Server listens to this port.
	 */
	private final int port;
	/**
	 * Number of worker threads available to the server.
	 */
	private final int workerThreads;
	/**
	 * Time after which the session will be automatically closed by a specific
	 * thread.
	 */
	private static int sessionTimeout;
	/**
	 * Map holding all available mime types.
	 */
	private final Map<String, String> mimeTypes = new HashMap<String, String>();
	/**
	 * Main server thread.
	 */
	private ServerThread serverThread;
	/**
	 * ThreadPool used for creating new workers and processing requests.
	 */
	private ExecutorService threadPool;
	/**
	 * Document tree root.
	 */
	private final Path documentRoot;
	/**
	 * Map containing all available workers.
	 */
	private final Map<String, IWebWorker> workersMap = new HashMap<String, IWebWorker>();
	/**
	 * Map containing all currently active sessions.
	 */
	private static final Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();

	/**
	 * Milliseconds in one second.
	 */
	private static final int MILIS = 1000;

	/**
	 * Constructor which sets the server configuration file to the provided one
	 * and configures the server.
	 *
	 * @param configFileName
	 *            Configuration file name.
	 * @throws IOException
	 *             if there is an error while accessing configuration files.
	 */
	public SmartHttpServer(final String configFileName) throws IOException {
		final Properties properties = new Properties();

		InputStream input = new FileInputStream(configFileName);
		properties.load(input);

		address = properties.getProperty("server.address");
		port = Integer.parseInt(properties.getProperty("server.port"));
		workerThreads = Integer.parseInt(properties
				.getProperty("server.workerThreads"));
		sessionTimeout = Integer.parseInt(properties
				.getProperty("session.timeout"));

		final File root = new File(
				properties.getProperty("server.documentRoot"));
		documentRoot = root.toPath();

		input = new FileInputStream(properties.getProperty("server.mimeConfig"));
		final Properties mimeProperties = new Properties();
		mimeProperties.load(input);

		final Enumeration<Object> keys = mimeProperties.keys();
		while (keys.hasMoreElements()) {
			final String key = (String) keys.nextElement();
			final String value = mimeProperties.getProperty(key);
			mimeTypes.put(key, value);
		}

		input = new FileInputStream(properties.getProperty("server.workers"));
		final Properties workersProperties = new Properties();
		workersProperties.load(input);

		try {
			final Enumeration<Object> workerKeys = workersProperties.keys();
			while (workerKeys.hasMoreElements()) {
				final String key = (String) workerKeys.nextElement();
				final Class<?> referenceToClass = this.getClass()
						.getClassLoader()
						.loadClass(workersProperties.getProperty(key));
				final Object newObject = referenceToClass.newInstance();
				final IWebWorker iww = (IWebWorker) newObject;

				workersMap.put(key, iww);
			}
		} catch (final Exception exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * Starts the serverThread, if one is not already running.
	 */
	protected final synchronized void start() {
		if (serverThread != null) {
			return;
		}

		threadPool = Executors.newFixedThreadPool(workerThreads);
		serverThread = new ServerThread();
		serverThread.setDaemon(true);
		serverThread.start();
	}

	/**
	 * Stops the running serverThread, effectively shutting down the server.
	 */
	protected final synchronized void stop() {
		serverThread.interrupt();
		threadPool.shutdown();
	}

	/**
	 * Class representing a single session map entry.
	 *
	 * @author Goran Brlas
	 * @version 1.0
	 *
	 */
	private static class SessionMapEntry {
		/**
		 * Session ID.
		 */
		String sidString;
		/**
		 * Represents how long the session is valid.
		 */
		long validUntil;
		/**
		 * Session parameters map.
		 */
		Map<String, String> map;

		/**
		 * Constructor which creates an random session ID which consists of
		 * random 20 characters in range of A-Z and sets the valid session time
		 * to current time + 10 minutes.
		 */
		public SessionMapEntry() {
			super();
			String temp = "";

			for (int i = 0; i < 20; i++) {
				temp += (char) ('A' + Math.random() * ('Z' - 'A' + 1));
			}

			this.sidString = temp;
			this.validUntil = new Date().getTime() + sessionTimeout * MILIS;
			map = new ConcurrentHashMap<String, String>();
		}

		/**
		 * Gets the session ID.
		 *
		 * @return Session ID.
		 */
		public String getSidString() {
			return sidString;
		}

		/**
		 * Gets the time until this session is valid.
		 *
		 * @return Time until this session is valid.
		 */
		public long getValidUntil() {
			return validUntil;
		}

		/**
		 * Sets the time until this session is valid.
		 *
		 * @param validUntil
		 *            Time until this session is valid.
		 */
		public void setValidUntil(final long validUntil) {
			this.validUntil = validUntil;
		}

		/**
		 * Gets the session parameters map.
		 *
		 * @return Session parameters.
		 */
		public Map<String, String> getMap() {
			return map;
		}

	}

	/**
	 * Class which represents a server thread. Listens for requests on the
	 * provided port and serves them by creating new worker threads.
	 *
	 * @author Goran Brlas
	 * @version 1.0
	 *
	 */
	protected class ServerThread extends Thread {

		@Override
		public final void run() {
			ServerSocket serverSocket;
			try {
				serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress((InetAddress) null,
						port));

				while (true) {
					final Socket client = serverSocket.accept();
					final ClientWorker cw = new ClientWorker(client);
					threadPool.submit(cw);
				}
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Class which represents a client worker which serves the request.
	 *
	 * @author Goran Brlas
	 * @version 1.0
	 *
	 */
	private class ClientWorker implements Runnable {
		/**
		 * Socket connecting the client and the server.
		 */
		private final Socket cSocket;
		/**
		 * Input stream used for reading received requests.
		 */
		private PushbackInputStream istream;
		/**
		 * Output stream used for sending appropriate answers to the client.
		 */
		private OutputStream ostream;
		/**
		 * Client worker parameters.
		 */
		private final Map<String, String> params = new HashMap<String, String>();
		/**
		 * Client worker persistent parameters.
		 */
		private Map<String, String> permParams = null;
		/**
		 * List containing all output cookies.
		 */
		private final List<RCCookie> outputCookies = new ArrayList<RCCookie>();

		/**
		 * Constructor which sets the class instance socket to the provided one.
		 *
		 * @param socket
		 */
		public ClientWorker(final Socket socket) {
			super();
			this.cSocket = socket;
		}

		@Override
		public void run() {
			try {
				istream = new PushbackInputStream(cSocket.getInputStream());
				ostream = cSocket.getOutputStream();

				final byte[] request = readRequest();

				if (request == null) {
					sendError(400, "Bad request");
					return;
				}

				final String requestStr = new String(request,
						StandardCharsets.US_ASCII);

				final List<String> requestHeader = extractHeaders(requestStr);
				final String[] firstLine = requestHeader.isEmpty() ? null
						: requestHeader.get(0).split(" ");

				if (firstLine == null || firstLine.length != 3) {
					sendError(405, "Bad request");
					return;
				}

				final String method = firstLine[0].toUpperCase();
				if (!method.equals("GET")) {
					sendError(505, "Method not supported");
					return;
				}

				final String version = firstLine[2].toUpperCase();
				if (!(version.equals("HTTP/1.1") || version.equals("HTTP/1.0"))) {
					sendError(400, "HTTP Version Not Supported");
					return;
				}

				SessionMapEntry entry = checkSession(requestHeader);
				if (entry == null) {
					entry = new SessionMapEntry();
					sessions.put(entry.getSidString(), entry);
				}

				permParams = entry.getMap();

				outputCookies.add(new RCCookie("sid", entry.getSidString(),
						null, address, "/"));

				String requestedPath = firstLine[1];
				final String[] temp = requestedPath.split("\\?");
				String paramString = null;
				final String path = temp[0];
				if (temp.length != 1) {
					paramString = temp[1];
				}

				if (paramString != null) {
					parseParameters(paramString);
				}

				if (path.contains("/ext/EchoParams")) {
					final RequestContext rc = new RequestContext(ostream,
							params, null, outputCookies);
					rc.setStatusCode(200);

					final IWebWorker echo = new EchoWorker();
					echo.processRequest(rc);

					cSocket.close();
					return;
				} else if (path.contains("EchoParams")) {
					sendError(405, "Bad request");

					cSocket.close();
					return;
				}

				if (path.contains("hello")) {
					final RequestContext rc = new RequestContext(ostream,
							params, null, outputCookies);
					rc.setStatusCode(200);

					workersMap.get("hello").processRequest(rc);

					cSocket.close();
					return;
				} else if (path.contains("cw")) {
					final RequestContext rc = new RequestContext(ostream,
							params, null, outputCookies);
					rc.setStatusCode(200);

					workersMap.get("cw").processRequest(rc);

					cSocket.close();
					return;
				}

				requestedPath = documentRoot.toString() + path;
				final File file = new File(requestedPath);

				if (!file.exists() || !file.isFile() || !file.canRead()) {
					sendError(403, "Forbidden file");
					cSocket.close();

					return;
				}

				if (path.contains("smscr")) {
					final String docBody = new String(Files.readAllBytes(Paths
							.get(requestedPath)), StandardCharsets.UTF_8);

					final Map<String, String> parameters = new HashMap<String, String>();

					if (requestedPath.contains("zbrajanje")) {
						parameters.put("a", "4");
						parameters.put("b", "4");
					} else if (requestedPath.contains("brojPoziva")) {
						if (permParams.isEmpty()) {
							permParams.put("brojPoziva", "1");
						} else {
							final String temp2 = new String(
									Integer.toString((int) Double
											.parseDouble(permParams
													.get("brojPoziva"))));
							permParams.put("brojPoziva", temp2);
						}
					}

					final RequestContext rc = new RequestContext(ostream,
							parameters, permParams, outputCookies);

					rc.setMimeType("text/plain");
					rc.setStatusCode(200);

					new SmartScriptEngine(
							new SmartScriptParser(docBody).getDocumentNode(),
							rc).execute();

				} else {
					final String extension = requestedPath.substring(
							requestedPath.lastIndexOf(".") + 1,
							requestedPath.length());
					String mimeType = mimeTypes.get(extension);

					if (mimeType == null) {
						mimeType = "application/octet-stream";
					}

					final RequestContext rc = new RequestContext(ostream,
							params, null, outputCookies);
					rc.setMimeType(mimeType);
					rc.setStatusCode(200);

					composeResponse(requestedPath, rc);
				}

				cSocket.close();

			} catch (final IOException exception) {
				System.err.println("There has been an error regarding I/O.");
			}

		}

		/**
		 * Checks whether there is an active session. If there is none, a new
		 * session is created.
		 *
		 * @param requestHeader
		 *            Client request header.
		 * @return New session entry.
		 */
		private synchronized SessionMapEntry checkSession(
				final List<String> requestHeader) {
			String sidCandidate = null;
			SessionMapEntry entry = null;

			for (String line : requestHeader) {
				if (!line.startsWith("Cookie:")) {
					continue;
				}

				line = line.substring(line.indexOf(" ")).trim();
				final String[] temp = line.split(";");

				if (temp.length == 0) {
					continue;
				}

				for (final String sid : temp) {
					if (sid.contains("sid")) {
						sidCandidate = sid.split("=")[1].replace("\"", "")
								.trim();
						break;
					}
				}

				if (sidCandidate == null) {
					entry = new SessionMapEntry();
					sessions.put(sidCandidate, entry);
				} else {
					entry = sessions.get(sidCandidate);
					if (entry == null) {
						entry = new SessionMapEntry();
						sessions.put(entry.getSidString(), entry);
						break;
					}

					final long newTime = new Date().getTime();
					if (entry.getValidUntil() < newTime
							|| !entry.getSidString().equals(sidCandidate)) {
						sessions.remove(entry.getSidString());
						entry = new SessionMapEntry();
						sessions.put(entry.getSidString(), entry);
					} else {
						entry.setValidUntil(new Date().getTime()
								+ sessionTimeout * MILIS);
					}
				}

				break;
			}

			return entry;
		}

		/**
		 * Composes the response to be written to the client.
		 *
		 * @param path
		 *            File path.
		 * @param rc
		 *            Request context which displays the data.
		 * @throws IOException
		 *             if there is an error while reading from file.
		 */
		private void composeResponse(final String path, final RequestContext rc)
				throws IOException {
			final Path pathTemp = Paths.get(path);
			final byte[] data = Files.readAllBytes(pathTemp);

			rc.write(data);
		}

		/**
		 * Parses the provided queries and puts provided parameters in the map.
		 *
		 * @param paramString
		 *            Client query.
		 * @throws IllegalArgumentException
		 *             if provided string is null.
		 */
		private void parseParameters(final String paramString)
				throws IllegalArgumentException {
			if (paramString == null) {
				throw new IllegalArgumentException(
						"Passed paramString must not be null.");
			}

			final String[] queries = paramString.split("&");

			for (final String str : queries) {
				final String name = str.split("=")[0];
				final String value = str.split("=")[1];
				params.put(name, value);
			}

		}

		/**
		 * Sends the error message to the client with appropirate status code
		 * and status text to the client.
		 *
		 * @param statusCode
		 *            Error status code.
		 * @param statusText
		 *            Error status message.
		 * @throws IOException
		 *             if there is an error while sending the response.
		 */
		private void sendError(final int statusCode, final String statusText)
				throws IOException {

			final String response = "<html><head><title>" + statusText
					+ "</title></head>"

					+ "<body><b>" + statusCode + " " + statusText
					+ "</b></body><html>";

			ostream.write(

			("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" +

			"Server: simple java server\r\n" +

			"Content-Type: text/html;charset=UTF-8\r\n" +

			"Content-Length: " + response.length() + "\r\n" +

			"Connection: close\r\n" +

			"\r\n" + response).getBytes(StandardCharsets.US_ASCII));

			ostream.flush();

		}

		/**
		 * Extracts all the request header lines from the request string and
		 * returns them in a list.
		 *
		 * @param requestHeader
		 *            Request header.
		 * @return List containing all request header lines.
		 */
		private List<String> extractHeaders(final String requestHeader) {
			final List<String> headers = new ArrayList<String>();
			String currentLine = null;

			for (final String s : requestHeader.split("\n")) {
				if (s.isEmpty()) {
					break;
				}
				final char c = s.charAt(0);
				if (c == 9 || c == 32) {
					currentLine += s;
				} else {
					if (currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}

			}

			if (!currentLine.isEmpty()) {
				headers.add(currentLine);
			}

			return headers;
		}

		/**
		 * DFA which reads the request header and returns it as a byte array.
		 *
		 * @return Request header.
		 * @throws IOException
		 *             if there is an error reading the request header.
		 */
		private byte[] readRequest() throws IOException {
			final ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;

			l: while (true) {
				final int b = istream.read();

				if (b != 13) {
					bos.write(b);
				}

				switch (state) {
				case 0:
					if (b == 13) {
						state = 1;
					} else if (b == 10) {
						state = 4;
					}
					break;
				case 1:
					if (b == 10) {
						state = 2;
					} else {
						state = 0;
					}
					break;
				case 2:
					if (b == 13) {
						state = 3;
					} else {
						state = 0;
					}
					break;
				case 3:
					if (b == 10) {
						break l;
					} else {
						state = 0;
					}
					break;
				case 4:
					if (b == 10) {
						break l;
					} else {
						state = 0;
					}
					break;
				default:
					break;
				}
			}

			return bos.toByteArray();
		}
	}

	/**
	 * Main method which starts the program. Takes the server configuration file
	 * name as an argument. The server can be shut down by entering either
	 * "quit" or "stop" in the console.
	 *
	 * @param args
	 *            Server configuration file name.
	 * @throws IOException
	 *             if there is an error starting the server.
	 */
	public static void main(final String[] args) throws IOException {
		if (args.length != 1) {
			System.err.println("Invalid argument provided.");
			System.exit(-1);
		}

		System.out.println("Starting the server...");

		final SmartHttpServer httpServer = new SmartHttpServer(args[0]);
		httpServer.start();

		// thread which periodically checks all active sessions and removes
		// invalid ones
		final TimerTask killSessions = new TimerTask() {

			@Override
			public void run() {
				final Set<String> keyset = sessions.keySet();
				SessionMapEntry entry;

				for (final String key : keyset) {
					entry = sessions.get(key);
					if (entry.getValidUntil() < new Date().getTime()) {
						sessions.remove(key);
					}
				}
			}
		};

		final Timer timer = new Timer();
		timer.schedule(killSessions, MILIS * 300, MILIS * 300);

		final Scanner scanner = new Scanner(System.in);

		System.out
				.println("If you wish to shut down the server type \"quit\" or \"stop\".");

		while (true) {
			final String line = scanner.nextLine();
			if (line.toLowerCase().equals("stop")
					|| line.toLowerCase().equals("quit")) {
				System.out.println("Shutting down the server...");
				httpServer.stop();
				timer.cancel();
				break;
			}
		}

		scanner.close();
	}
}
