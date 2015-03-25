package com.hua.goddess.base.net;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.NoRouteToHostException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashSet;

import javax.net.ssl.SSLHandshakeException;

import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

import android.os.SystemClock;

/**
 * 网络连接重试的Handler
 * @author loopj
 * created by Nov 16, 2011 03:10:00 PM
 */

public class RetryHandler implements HttpRequestRetryHandler {
	
    private static final int RETRY_SLEEP_TIME_MILLIS = 1500;						//retry时SystemClock睡1.5秒
    private static HashSet<Class<?>> exceptionWhitelist = new HashSet<Class<?>>();  //白名单异常集合，可重试
    private static HashSet<Class<?>> exceptionBlacklist = new HashSet<Class<?>>();  //黑名单异常集合，放弃重试

    static {
        // 无响应时重试
        exceptionWhitelist.add(NoHttpResponseException.class);
        // wifi转换到3G导致UnknownHostException异常时重试
        exceptionWhitelist.add(UnknownHostException.class);
        // wifi转换到3G导致SocketException异常时重试
        exceptionWhitelist.add(SocketException.class);

        // 如果线程被打断就不重试
        exceptionBlacklist.add(InterruptedIOException.class);
        // 握手失败不重试（常见为无证书）
        exceptionBlacklist.add(SSLHandshakeException.class);
        // 无法路由到主机不重试
        exceptionBlacklist.add(NoRouteToHostException.class);
    }

    private final int maxRetries; //最大重试次数

    public RetryHandler(int maxRetries) {
        this.maxRetries = maxRetries;
    }
    
    /**
     * 重试请求
     * exception为IO异常，executionCount为执行次数，context是HttpContext上下文
     * 返回布尔型的retry ：retry为false时不重试 ， retry为true时重试
     */
    public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
        boolean retry;

        Boolean b = (Boolean) context.getAttribute(ExecutionContext.HTTP_REQ_SENT);
        boolean sent = (b != null && b.booleanValue());

        if(executionCount > maxRetries) {
            //如果执行次数大于最大连接数则不再重试
            retry = false;
        } else if (exceptionBlacklist.contains(exception.getClass())) {
            //黑名单集合里的异常不重试
            retry = false;
        } else if (exceptionWhitelist.contains(exception.getClass())) {
            //白名单集合里的立即重试
            retry = true;
        } else if (!sent) {
            // 大部分由于其他错误导致请求还没有被完全发送出去，则立即重试
            retry = true;
        } else {
            // 重发所有幕等的请求
            HttpUriRequest currentReq = (HttpUriRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);
            String requestType = currentReq.getMethod();
            if(!requestType.equals("POST")) {
            	//如果requestType不是POST,则立即重试
                retry = true;
            } else {
                //否则不重试
                retry = false;
            }
        }

        if(retry) {
        	//如果重试，SystemClock睡1.5秒
            SystemClock.sleep(RETRY_SLEEP_TIME_MILLIS);
        } else {
            exception.printStackTrace();
        }

        return retry;
    }
}
