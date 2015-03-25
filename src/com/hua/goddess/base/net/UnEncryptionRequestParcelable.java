package com.hua.goddess.base.net;

import android.os.Parcel;

/**
 * 网络通信参数---非加密方式
 * @author evil
 *
 */
public class UnEncryptionRequestParcelable extends RequestParcelable {

	public UnEncryptionRequestParcelable(String url) {
		super(url);
	}
	
	public UnEncryptionRequestParcelable(Parcel in) {
		super(in);
	}

	public void setPostStream(byte[] postStream) {
		this.postStream = postStream;
	}
}
