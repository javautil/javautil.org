package org.javautil.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class ProxyControllerTest {

	private Server server;

	@Before
	public void setupJetty() throws Exception {
		server = new Server(8675);
		Context root = new Context(server, "/", Context.SESSIONS);
		root.addServlet(new ServletHolder(new HelloServlet("hello")), "/hello");
		root.addServlet(new ServletHolder(new HelloServlet("hallo")),
				"/hello.de");
		server.start();
	}

	@After
	public void takedownJetty() throws Exception {
		server.stop();
		server = null;
	}

	@Test
	public void testProxySimple() throws Exception {
		ProxyController controller = new ProxyController();
		controller.setRemoteUri("http://localhost:8675");
		controller.afterPropertiesSet();
		MockHttpServletRequest request = new MockHttpServletRequest("GET",
				"hello");
		MockHttpServletResponse response = new MockHttpServletResponse();
		controller.handleRequest(request, response);
		Assert.assertEquals("hello", response.getContentAsString());
		Assert.assertEquals(200, response.getStatus());
	}

	@Test
	public void testProxyHttpHeaders() throws Exception {
		ProxyController controller = new ProxyController();
		controller.setRemoteUri("http://localhost:8675");
		controller.afterPropertiesSet();
		MockHttpServletRequest request = new MockHttpServletRequest("GET",
				"hello");
		MockHttpServletResponse response = new MockHttpServletResponse();
		controller.handleRequest(request, response);
		Assert.assertEquals("test2", response.getHeader("test1"));
	}

	@Test
	public void testProxyErrorStatus() throws Exception {
		ProxyController controller = new ProxyController();
		controller.setRemoteUri("http://localhost:8675");
		controller.afterPropertiesSet();
		MockHttpServletRequest request = new MockHttpServletRequest("GET",
				"hello");
		request.setParameter("500", "");
		MockHttpServletResponse response = new MockHttpServletResponse();
		controller.handleRequest(request, response);
		Assert.assertEquals(500, response.getStatus());
	}

	@Test
	public void testProxyStripLocalUri() throws Exception {
		ProxyController controller = new ProxyController();
		controller.setRemoteUri("http://localhost:8675");
		controller.setStripPrefix("proxy/");
		controller.afterPropertiesSet();
		MockHttpServletRequest request = new MockHttpServletRequest("GET",
				"proxy/hello.de");
		MockHttpServletResponse response = new MockHttpServletResponse();
		controller.handleRequest(request, response);
		Assert.assertEquals("hallo", response.getContentAsString());
	}

	@Test
	public void testProxyWithSingleParameter() throws Exception {
		ProxyController controller = new ProxyController();
		controller.setRemoteUri("http://localhost:8675");
		controller.afterPropertiesSet();
		MockHttpServletRequest request = new MockHttpServletRequest("GET",
				"hello");
		request.setParameter("foo", "");
		MockHttpServletResponse response = new MockHttpServletResponse();
		controller.handleRequest(request, response);
		Assert.assertEquals("bar", response.getContentAsString());
	}

	@Test
	public void testProxyWithMultipleParameters() throws Exception {
		ProxyController controller = new ProxyController();
		controller.setRemoteUri("http://localhost:8675");
		controller.afterPropertiesSet();
		MockHttpServletRequest request = new MockHttpServletRequest("GET",
				"hello");
		request.setParameter("onefish", "twofish");
		request.setParameter("redfish", "bluefish");
		MockHttpServletResponse response = new MockHttpServletResponse();
		controller.handleRequest(request, response);
		Assert.assertEquals(
				"http://localhost:8675/hello?onefish=twofish&redfish=bluefish",
				controller.buildRemoteURL(request).toString());
	}

	@Test
	public void testProxyParameterOrder() throws Exception {
		ProxyController controller = new ProxyController();
		controller.setRemoteUri("http://localhost:8675");
		controller.afterPropertiesSet();
		MockHttpServletRequest request = new MockHttpServletRequest("GET",
				"hello");
		request.setParameter("onefish", "twofish");
		request.setParameter("redfish", "bluefish");
		MockHttpServletResponse response = new MockHttpServletResponse();
		controller.handleRequest(request, response);
		Assert.assertEquals(
				"http://localhost:8675/hello?onefish=twofish&redfish=bluefish",
				controller.buildRemoteURL(request).toString());
		request.removeAllParameters();
		request.setParameter("redfish", "bluefish");
		request.setParameter("onefish", "twofish");
		Assert.assertEquals(
				"http://localhost:8675/hello?redfish=bluefish&onefish=twofish",
				controller.buildRemoteURL(request).toString());
	}

	@Test
	public void testProxyWithParametersOff() throws Exception {
		ProxyController controller = new ProxyController();
		controller.setProxyRequestParameters(false);
		controller.setRemoteUri("http://localhost:8675");
		controller.afterPropertiesSet();
		MockHttpServletRequest request = new MockHttpServletRequest("GET",
				"hello");
		request.setParameter("foo", "");
		MockHttpServletResponse response = new MockHttpServletResponse();
		controller.handleRequest(request, response);
		Assert.assertEquals("hello", response.getContentAsString());
	}

	@Test
	public void testProxyWithParametersHardcoded() throws Exception {
		ProxyController controller = new ProxyController();
		controller.setProxyRequestParameters(false);
		Map<String, Object> parms = new LinkedHashMap<String, Object>();
		parms.put("foo", "bar");
		controller.setAdditionalParameters(parms);
		controller.setRemoteUri("http://localhost:8675");
		controller.afterPropertiesSet();
		MockHttpServletRequest request = new MockHttpServletRequest("GET",
				"hello");
		MockHttpServletResponse response = new MockHttpServletResponse();
		controller.handleRequest(request, response);
		Assert.assertEquals("baz", response.getContentAsString());
	}

	@Test
	public void testProxyWithParametersOverriden() throws Exception {
		ProxyController controller = new ProxyController();
		controller.setProxyRequestParameters(false);
		Map<String, Object> parms = new LinkedHashMap<String, Object>();
		parms.put("foo", "bar");
		controller.setAdditionalParameters(parms);
		controller.setRemoteUri("http://localhost:8675");
		controller.afterPropertiesSet();
		MockHttpServletRequest request = new MockHttpServletRequest("GET",
				"hello");
		request.setParameter("foo", "");
		MockHttpServletResponse response = new MockHttpServletResponse();
		controller.handleRequest(request, response);
		Assert.assertEquals("baz", response.getContentAsString());
	}

	class HelloServlet extends HttpServlet {
		private static final long serialVersionUID = 3025114951037735265L;
		private final String data;

		public HelloServlet(String data) {
			this.data = data;
		}

		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			resp.setHeader("test1", "test2");
			ServletOutputStream os = resp.getOutputStream();
			if (req.getParameter("500") != null) {
				resp.setStatus(500);
			}
			if (req.getParameter("foo") != null) {
				if (req.getParameter("foo").equals("bar")) {
					os.write("baz".getBytes());
				} else {
					os.write("bar".getBytes());
				}
			} else {
				os.write(data.getBytes());
			}
			os.flush();
		}
	}

}
