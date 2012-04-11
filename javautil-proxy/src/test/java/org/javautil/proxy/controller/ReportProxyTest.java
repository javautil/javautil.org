package org.javautil.proxy.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.ModelAndView;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:ReportProxyTest.xml" })
public class ReportProxyTest {

	@Autowired
	private Proxy proxy;

	@Test
	public void testGoodUrlDownload1() throws Exception {

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setMethod("POST");
		request.setRemoteUser("web_princeton");
		request.setParameter("p_run", "-9200808");
		request.setParameter("report", "gmreghqbrkcomp");
		MockHttpServletResponse response = new MockHttpServletResponse();

		ModelAndView modelAndView = getProxy().download(request, response);
		Assert.assertNull("Report download ModelAndView", modelAndView);

	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testBadUrlDownload1() throws Exception {

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setMethod("POST");
		request.setRemoteUser("web_princeton");
		request.setParameter("p_run", "-9200808");
		request.setParameter("report", "gmreghqbrkcomp");
		request.setParameter("destname", "/home/workspace");

		MockHttpServletResponse response = new MockHttpServletResponse();

		getProxy().download(request, response);
	}

	/**
	 * @return the proxy
	 */
	public Proxy getProxy() {
		return proxy;
	}

	/**
	 * @param proxy
	 *            the proxy to set
	 */
	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}

}
