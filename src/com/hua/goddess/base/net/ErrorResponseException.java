package com.hua.goddess.base.net;

/**
 * 非200相应码异常
 * @author Evil
 *
 */
public class ErrorResponseException extends Exception {

	private static final long serialVersionUID = 8182552513724570473L;

	private int statusCode;
	
	public ErrorResponseException(int statusCode)
	{
		super("response code is not 200!current response status code is "+statusCode);
		this.statusCode = statusCode;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	
}
