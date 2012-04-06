package org.javautil.oracle.servicerequest;

public class ServiceRequestBean extends ServiceRequestIssuer {

	private String requestName;
	private String returnPipe;
	private String args;

	public String getRequestName() {
		return requestName;
	}

	public void setRequestName(final String requestName) {
		this.requestName = requestName;
	}

	public String getReturnPipe() {
		return returnPipe;
	}

	public void setReturnPipe(final String returnPipe) {
		this.returnPipe = returnPipe;
	}

	public String getArgs() {
		return args;
	}

	public void setArgs(final String args) {
		this.args = args;
	}

}