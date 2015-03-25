package com.hua.goddess.base.exception;


/**
 * 无SD卡异常
 * 用于获取SD卡目录地址等
 * @author Evil
 *
 */
public class NoSDcardException extends Exception {

	private static final long serialVersionUID = 1325313401391742667L;
	
	public NoSDcardException() {
		super("There is no SD card found!");
	}
}
