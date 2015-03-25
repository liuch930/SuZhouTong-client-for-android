package com.hua.goddess.base.exception;

import java.util.HashMap;

/**
 * 服务器端错误代码异常
 * 该自定义异常类用于服务器端返回的业务错误，根据错误代码自动获取对应的异常信息
 * @author Evil
 *
 */
public class ErrorCodeException extends Exception {

	private static final long serialVersionUID = 1844042033635771773L;
	
	private String errorCode;
	private String errorMsg;
	
	private static HashMap<String,String> errorMap = new HashMap<String,String>();
	
	static{
		errorMap.put("001","连接服务器失败，请稍候重试(001)");
		errorMap.put("901","获取列表总数失败(901)");
		errorMap.put("335","服务器错误(335)");
		
	}
	
	public ErrorCodeException(String errorCode) {
		errorMsg = errorMap.get(errorCode);
		if(errorMsg==null)
			errorMsg = errorMap.get("001");
		
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getErrorMessage(){
		return errorMsg;
	}
	
}
