package org.javautil.proxy.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:ProxyParameterCheckerTest.xml" })
public class ProxyParameterCheckerTest {
	
	@Autowired
	private ProxyParameterChecker proxyParameterChecker;
	
	@Test
	public void testGoodUrl1() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("id", "10001");
		getProxyParameterChecker().checkParameters(request);
	}

	@Test
	public void testGoodUrl2() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("id", "10001");
		request.setParameter("name", "myname");
		request.setParameter("address", "myaddress");
		getProxyParameterChecker().checkParameters(request);
	}

	@Test (expected=java.lang.IllegalArgumentException.class)
	public void testBadUrl1() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("id", "10001");
		request.setParameter("destname", "/home/workspace");
		getProxyParameterChecker().checkParameters(request);	
	}

	@Test (expected=java.lang.IllegalArgumentException.class)
	public void testBadUrl2() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("id", "10001");
		request.setParameter("destname", "/home/workspace");
		request.setParameter("name", "myname");
		request.setParameter("address", "myaddress");		
		getProxyParameterChecker().checkParameters(request);
	}
	
	/**
	 * @return the proxyParameterChecker
	 */
	public ProxyParameterChecker getProxyParameterChecker() {
		return proxyParameterChecker;
	}

	/**
	 * @param proxyParameterChecker the proxyParameterChecker to set
	 */
	public void setProxyParameterChecker(ProxyParameterChecker proxyParameterChecker) {
		this.proxyParameterChecker = proxyParameterChecker;
	}
}
