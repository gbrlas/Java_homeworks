package webserver;

import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * Class which unit tests the RequestContext class.
 *
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class RequestContextTest {

	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public final void constructorTest() {
		final RequestContext r1 = new RequestContext(System.out,
				new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());

		final RequestContext r2 = new RequestContext(System.out, null, null,
				null);

		final RequestContext r3 = new RequestContext(null, null, null, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public final void getParameterTest() {
		final Map<String, String> map = new HashMap<String, String>();
		map.put("key", "value");

		final RequestContext r1 = new RequestContext(System.out, map,
				new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());

		Assert.assertEquals("value", r1.getParameter("key"));

		r1.getParameter(null);
	}

	@Test
	public final void getParameterNamesTest() {
		final Map<String, String> map = new HashMap<String, String>();
		map.put("key", "value");
		map.put("key2", "value");

		final RequestContext r1 = new RequestContext(System.out, map,
				new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());

		final Set<String> set = r1.getParameterNames();

		Assert.assertEquals("key2", set.toArray()[0]);
	}

	@Test(expected = IllegalArgumentException.class)
	public final void getPersistantParameterTest() {
		final Map<String, String> map = new HashMap<String, String>();
		map.put("key", "value");

		final RequestContext r1 = new RequestContext(System.out,
				new HashMap<String, String>(), map,
				new ArrayList<RequestContext.RCCookie>());

		Assert.assertEquals("value", r1.getPersistentParameter("key"));

		r1.getPersistentParameter(null);
	}

	@Test
	public final void getPersistantParameterNamesTest() {
		final Map<String, String> map = new HashMap<String, String>();
		map.put("key", "value");
		map.put("key2", "value");

		final RequestContext r1 = new RequestContext(System.out,
				new HashMap<String, String>(), map,
				new ArrayList<RequestContext.RCCookie>());

		final Set<String> set = r1.getPersistentParameterNames();

		Assert.assertEquals("key2", set.toArray()[0]);
	}

	@Test(expected = IllegalArgumentException.class)
	public final void setPersistentParameterInvalidArgumentTest() {
		final RequestContext r1 = new RequestContext(System.out,
				new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());

		r1.setPersistentParameter(null, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public final void setPersistentParameterInvalidArgumentTestV1() {
		final RequestContext r1 = new RequestContext(System.out,
				new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());

		r1.setPersistentParameter(null, "value");
	}

	@Test(expected = IllegalArgumentException.class)
	public final void setPersistentParameterInvalidArgumentTestV2() {
		final RequestContext r1 = new RequestContext(System.out,
				new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());

		r1.setPersistentParameter("key", null);
	}

	public final void setPersistentParameterTest() {
		final RequestContext r1 = new RequestContext(System.out,
				new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());

		r1.setPersistentParameter("key", "value");
		Assert.assertEquals("value", r1.getPersistentParameter("key"));
	}

	@Test(expected = IllegalArgumentException.class)
	public final void removePersistentParameterInvalidArgumentTest() {
		final RequestContext r1 = new RequestContext(System.out,
				new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());

		r1.setPersistentParameter("key", "value");
		r1.removePersistentParameter(null);
	}

	@Test
	public final void removePersistentParameterTest() {
		final RequestContext r1 = new RequestContext(System.out,
				new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());

		r1.setPersistentParameter("key", "value");
		r1.removePersistentParameter("key");
	}

	@Test(expected = IllegalArgumentException.class)
	public final void getTemporaryParameterIllegalArgumentTest() {
		final Map<String, String> map = new HashMap<String, String>();
		map.put("key", "value");

		final RequestContext r1 = new RequestContext(System.out,
				new HashMap<String, String>(), map,
				new ArrayList<RequestContext.RCCookie>());

		r1.getTemporaryParameter(null);
	}

	@Test
	public final void getTemporaryParameterTest() {
		final Map<String, String> map = new HashMap<String, String>();
		map.put("key", "value");

		final RequestContext r1 = new RequestContext(System.out,
				new HashMap<String, String>(), map,
				new ArrayList<RequestContext.RCCookie>());

		Assert.assertEquals(null, r1.getTemporaryParameter("name"));
	}

	@Test
	public final void getTemporaryParameterNamesTest() {
		final Map<String, String> map = new HashMap<String, String>();
		map.put("key", "value");

		final RequestContext r1 = new RequestContext(System.out,
				new HashMap<String, String>(), map,
				new ArrayList<RequestContext.RCCookie>());

		Assert.assertEquals(0, r1.getTemporaryParameterNames().size());
	}

	@Test(expected = IllegalArgumentException.class)
	public final void setTemporaryParameterInvalidArgumentTest() {
		final RequestContext r1 = new RequestContext(System.out,
				new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());

		r1.setTemporaryParameter(null, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public final void setTemporaryParameterInvalidArgumentTestV1() {
		final RequestContext r1 = new RequestContext(System.out,
				new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());

		r1.setTemporaryParameter(null, "value");
	}

	@Test(expected = IllegalArgumentException.class)
	public final void setTemporaryInvalidArgumentTestV2() {
		final RequestContext r1 = new RequestContext(System.out,
				new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());

		r1.setTemporaryParameter("key", null);
	}

	@Test
	public final void setTemporaryParameterTest() {
		final RequestContext r1 = new RequestContext(System.out,
				new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());

		r1.setTemporaryParameter("key", "value");
		Assert.assertEquals("value", r1.getTemporaryParameter("key"));
	}

	@Test(expected = IllegalArgumentException.class)
	public final void removeTemporaryParameterInvalidArgumentTest() {
		final RequestContext r1 = new RequestContext(System.out,
				new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());

		r1.setTemporaryParameter("key", "value");
		r1.removeTemporaryParameter(null);
	}

	@Test
	public final void removeTemporaryParameterTest() {
		final RequestContext r1 = new RequestContext(System.out,
				new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());

		r1.setTemporaryParameter("key", "value");
		r1.removeTemporaryParameter("key");
	}

	@Test
	public final void writeBytesTest() {
		final RequestContext r1 = new RequestContext(System.out,
				new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());

		final byte[] data = ("Byte write test").getBytes();
		try {
			r1.write(data);
		} catch (final IOException exception) {
		}
	}

	@Test
	public final void writeStringTest() {
		final RequestContext r1 = new RequestContext(System.out,
				new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());

		r1.addRCCookies(new RCCookie("name", "value", null, "domain", "path"));

		try {
			System.out.println();
			r1.write("Byte write test");
		} catch (final IOException exception) {
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public final void addRCCookieInvalidArgumentTest() {
		final RequestContext r1 = new RequestContext(System.out,
				new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());

		r1.addRCCookies(null);
	}

	// sets the output cookies, encoding, mime type, status code and status text
	@Test
	public final void otherSettersTest() {
		final RequestContext r1 = new RequestContext(System.out,
				new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());

		r1.setEncoding("enc");
		r1.setMimeType("text/plain");
		r1.setOutputCookies(new ArrayList<RequestContext.RCCookie>());
		r1.setStatusCode(200);
		r1.setStatusText("Error");
	}

	// sets the output cookies, encoding, mime type, status code and status text
	@Test(expected = RuntimeException.class)
	public final void otherSettersAfterHeaderCreationTestV1() {
		final RequestContext r1 = new RequestContext(System.out,
				new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());

		try {
			r1.write("");
		} catch (final IOException exception) {
		}

		r1.setEncoding("enc");
	}

	// sets the output cookies, encoding, mime type, status code and status text
	@Test(expected = RuntimeException.class)
	public final void otherSettersAfterHeaderCreationTestV2() {
		final RequestContext r1 = new RequestContext(System.out,
				new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());

		try {
			r1.write("");
		} catch (final IOException exception) {
		}

		r1.setMimeType("text/plain");
	}

	// sets the output cookies, encoding, mime type, status code and status text
	@Test(expected = RuntimeException.class)
	public final void otherSettersAfterHeaderCreationTestV3() {
		final RequestContext r1 = new RequestContext(System.out,
				new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());

		try {
			r1.write("");
		} catch (final IOException exception) {
		}

		r1.setOutputCookies(new ArrayList<RequestContext.RCCookie>());
	}

	// sets the output cookies, encoding, mime type, status code and status text
	@Test(expected = RuntimeException.class)
	public final void otherSettersAfterHeaderCreationTestV4() {
		final RequestContext r1 = new RequestContext(System.out,
				new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());

		try {
			r1.write("");
		} catch (final IOException exception) {
		}

		r1.setStatusCode(200);
	}

	// sets the output cookies, encoding, mime type, status code and status text
	@Test(expected = RuntimeException.class)
	public final void otherSettersAfterHeaderCreationTestV5() {
		final RequestContext r1 = new RequestContext(System.out,
				new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());

		try {
			r1.write("");
		} catch (final IOException exception) {
		}

		r1.setStatusText("Error");
	}

}
