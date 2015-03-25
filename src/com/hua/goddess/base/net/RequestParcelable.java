package com.hua.goddess.base.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.protocol.HTTP;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * HTTP请求参数
 * @author hanchao
 * created by May 5, 2010 2:13:49 PM
 */
public class RequestParcelable implements Parcelable{
	//编码方式
	protected String charset = HTTP.UTF_8;
	//请求方式
	protected int requestMethod;
	//请求地址
	protected String url;
	//请求参数（表单提交键值对）
	protected HashMap<String,String> params;
	//请求头列表
	protected HashMap<String,String> headers;
	//请求流
	protected byte[] postStream;

	public RequestParcelable(String url) {
		this.url = url;
	}
	
	@SuppressWarnings("unchecked")
	public RequestParcelable(Parcel in)
	{
		charset = in.readString();
		requestMethod = in.readInt();
		url = in.readString();
		params = in.readHashMap(ClassLoader.getSystemClassLoader());
		headers = in.readHashMap(ClassLoader.getSystemClassLoader());
		try {
			final int contentBytesLen = in.readInt();
            if (contentBytesLen == -1) {
            	postStream = null;
            } else {
            	postStream = new byte[contentBytesLen];
                in.readByteArray(postStream);
            }
		} catch (Exception e) {e.printStackTrace();
		}
	}
	
	public static final Parcelable.Creator<RequestParcelable> CREATOR = new Parcelable.Creator<RequestParcelable>() {
		public RequestParcelable createFromParcel(Parcel in) {
			return new RequestParcelable(in);
		}

		public RequestParcelable[] newArray(int size) {
			return new RequestParcelable[size];
		}
	};
	
	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(charset);
		dest.writeInt(requestMethod);
		dest.writeString(url);
		dest.writeMap(params);
		dest.writeMap(headers);
		if(postStream==null){
			dest.writeInt(-1);
		}else{
			dest.writeInt(postStream.length);
			dest.writeByteArray(postStream);
		}
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public int getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(int requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public HashMap<String, String> getParams() {
		return params;
	}

	public void setParams(HashMap<String, String> params) {
		this.params = params;
	}

	public HashMap<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(HashMap<String, String> headers) {
		this.headers = headers;
	}

	public byte[] getPostStream() {
		return postStream;
	}

//	public void setPostStream(byte[] postStream) {
//		this.postStream = postStream;
//	}
	
	public void setPostStream(byte[] postStream) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			bos.write(new byte[]{1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,0});
			bos.write(EncryptionUtil.compress(postStream));
		
			this.postStream = bos.toByteArray();
			bos.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}


