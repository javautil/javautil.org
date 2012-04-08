package org.javautil.proxy.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javautil.controller.ProxyController;
import org.javautil.proxy.util.ProxyParameterChecker;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 * Proxies web requests between internal web sites and internal web services.
 * 
 * Provides a single point of authentication and logging. Additionally can
 * control access to resources that otherwise have no authentication
 * capabilities.
 * 
 * Ensures that url's conform to requirements. For example see
 * http://securitytracker.com/alerts/2005/Jul/1014524.html as type of
 * vulnerability addressed by this proxy.
 * 
 * @author mnr
 * 
 */
public class Proxy extends MultiActionController {

	private ProxyController proxyController;

	private ProxyParameterChecker parameterChecker;

	/**
	 * Takes an incoming request from the website and proxys the call to the
	 * WorkbookServer.
	 * 
	 * The JAAS username is used as the datasource name on the WorkbookServer
	 * and is added as an argument in the call as datasourceName.
	 * 
	 * @throws Exception
	 * @throws Exception
	 * 
	 */
	public ModelAndView download(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> parms = new LinkedHashMap<String, Object>();
		parms.put(this.getAuthenticatedUsrName(request), null);
		parms.put("datasourceName", this.getAuthenticatedUsrName(request));
		proxyController.setAdditionalParameters(parms);
		proxyController.setStripPrefix(getParameterChecker().getRequestRule()
				.getStripPrefix());
		proxyController.afterPropertiesSet();
		getParameterChecker().checkParameters(request);

		return proxyController.handleRequest(request, response);
	}

	/**
	 * Returns the authenticated userName
	 */
	private String getAuthenticatedUsrName(HttpServletRequest request) {
		return request.getRemoteUser();
	}

	/**
	 * @return the parameterChecker
	 */
	public ProxyParameterChecker getParameterChecker() {
		return parameterChecker;
	}

	/**
	 * @param parameterChecker
	 *            the parameterChecker to set
	 */
	public void setParameterChecker(ProxyParameterChecker parameterChecker) {
		this.parameterChecker = parameterChecker;
	}

	/**
	 * @return the proxyController
	 */
	public ProxyController getProxyController() {
		return proxyController;
	}

	/**
	 * @param proxyController
	 *            the proxyController to set
	 */
	public void setProxyController(ProxyController proxyController) {
		this.proxyController = proxyController;
	}

}
