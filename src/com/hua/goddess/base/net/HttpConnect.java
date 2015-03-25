package com.hua.goddess.base.net;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.SyncBasicHttpContext;

import com.hua.goddess.global.Globe;

/**
 * HTTP请求相应处理类
 * @author hanchao
 * created by May 5, 2010 5:28:34 PM
 */

public abstract class HttpConnect {

	/**
	 * 请求参数
	 */
	protected RequestParcelable requestParam;
	private DefaultHttpClient httpClient;
	
	private int executionCount;
	private HttpContext httpContext;
	HttpUriRequest httpRequest;
	
	protected HttpConnect(RequestParcelable requestParam) {
		this(true);
		this.requestParam = requestParam;
		
	}
	
	protected HttpConnect() {
	    this(true);
	}
	
	protected HttpConnect(Boolean isretry){
		super();
		init(isretry);
	}
	
	/**
	 * 建立HTTPost对象
	 */ 
	private void init(Boolean isretry){
		BasicHttpParams httpParams = new BasicHttpParams();

        ConnManagerParams.setTimeout(httpParams, Globe.TIMEOUT_SOCKET);
        ConnManagerParams.setMaxConnectionsPerRoute(httpParams, new ConnPerRouteBean(15));
        ConnManagerParams.setMaxTotalConnections(httpParams, 15);
        
        HttpConnectionParams.setConnectionTimeout(httpParams, Globe.TIMEOUT_CONNECTION);
        HttpConnectionParams.setSoTimeout(httpParams, Globe.TIMEOUT_SOCKET);
        HttpConnectionParams.setTcpNoDelay(httpParams, true);

        HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);

        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
        ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(httpParams, schemeRegistry);
        
        httpContext = new SyncBasicHttpContext(new BasicHttpContext());
        httpClient = new DefaultHttpClient(cm, httpParams);
        
        httpClient.addRequestInterceptor(new HttpRequestInterceptor() {
            public void process(HttpRequest request, HttpContext context) {
                if (!request.containsHeader(Globe.HEADER_ACCEPT_ENCODING)) {
                    request.addHeader(Globe.HEADER_ACCEPT_ENCODING, Globe.ENCODING_GZIP);
                }
            }
        });

        httpClient.addResponseInterceptor(new HttpResponseInterceptor() {
            public void process(HttpResponse response, HttpContext context) {
                final HttpEntity entity = response.getEntity();
                final Header encoding = entity.getContentEncoding();
                if (encoding != null) {
                    for (HeaderElement element : encoding.getElements()) {
                        if (element.getName().equalsIgnoreCase(Globe.ENCODING_GZIP)) {
                            response.setEntity(new InflatingEntity(response.getEntity()));
                            break;
                        }
                    }
                }
            }
        });
        
        if(isretry){
        	httpClient.setHttpRequestRetryHandler(new RetryHandler(3));
        }
	}

	/**
	 * 发送请求
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws ErrorResponseException 
	 * @throws UnsupportedEncodingException 
	 * @throws ConnectException 
	 */
	public void connect() throws
			ErrorResponseException, UnsupportedEncodingException, Exception {
        /*
         * 若以.png或.apk结尾，则不加jsessionid
         */
        String requestUri = "";
		if (requestParam.getUrl() != null
				&& (requestParam.getUrl().endsWith(".png")
						|| requestParam.getUrl().endsWith(".jpg")
						|| requestParam.getUrl().endsWith(".jpeg")
						|| requestParam.getUrl().endsWith(".gif") || requestParam
						.getUrl().endsWith(".apk"))){
			//添加引用页头信息
			requestParam.getHeaders().put("Referer", requestParam.getUrl().substring(requestParam.getUrl().lastIndexOf("/"))+"index.html");
			
			requestUri = requestParam.getUrl();
		}else
        	//下载路径，
//        	requestUri = requestParam.getUrl()+"?phpsessid="+Globe.session_id;//+"?jsessionid="+Globe.session_id;
			requestUri = requestParam.getUrl();
        //判断是否需要post方式访问服务器
        byte[] postStream = requestParam.getPostStream();
		if ((postStream != null && postStream.length > 0) || (requestParam.getParams() !=null)) {
			httpRequest = getHttpPostRequest(requestUri);
		}else{
			httpRequest = getHttpGetRequest(requestUri);
		}
        
		makeRequestWithRetries();
	}
	
	/**
	 * 发送请求获取服务器响应
	 * @throws IllegalStateException
	 * @throws Exception
	 */
	private void makeRequest() throws IllegalStateException, Exception {
	
		//throw new UnknownHostException();
	    /*发送请求并等待响应*/
		HttpResponse httpResponse = httpClient.execute(httpRequest);
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		//200
		if (statusCode == 200
				|| httpResponse.getStatusLine().getStatusCode() == 206) {
			onDataReceived(httpResponse);
		} else {
			// 200以外的响应状态码
			throw new ErrorResponseException(httpResponse.getStatusLine()
					.getStatusCode());
		}	

    }
	
	private void makeRequestWithRetries() throws Exception {
        // This is an additional layer of retry logic lifted from droid-fu
        // See: https://github.com/kaeppler/droid-fu/blob/master/src/main/java/com/github/droidfu/http/BetterHttpRequestBase.java
        boolean retry = false;
        IOException cause = null;
        HttpRequestRetryHandler retryHandler = null;
        
        do {
            try {
                makeRequest();
                return;
            } catch (IOException e) {
                cause = e;
                retryHandler = httpClient.getHttpRequestRetryHandler();
                if(retryHandler != null && retryHandler instanceof RetryHandler){
                	retry = retryHandler.retryRequest(cause, ++executionCount, httpContext);
                }
                retry = false;
            } catch (NullPointerException e) {
                // there's a bug in HttpClient 4.0.x that on some occasions causes
                // DefaultRequestExecutor to throw an NPE, see
                // http://code.google.com/p/android/issues/detail?id=5255
                cause = new IOException("NPE in HttpClient" + e.getMessage());
                //retry = retryHandler.retryRequest(cause, ++executionCount, httpContext);
                
                retryHandler = httpClient.getHttpRequestRetryHandler();
                if(retryHandler != null && retryHandler instanceof RetryHandler){
                	retry = retryHandler.retryRequest(cause, ++executionCount, httpContext);
                }
                retry = false;
            }catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
        }while (retry);

		//        // no retries left, crap out with exception
		//        ConnectException ex = new ConnectException();
		//        ex.initCause(cause);
		//        throw ex;
    }
	
	/**
	 * 服务器响应事件
	 * @throws IOException 
	 * @throws IllegalStateException 
	 * @throws IOException 
	 */
	protected void onDataReceived(HttpResponse httpResponse) throws IllegalStateException, IOException,Exception
	{
		onReceiverHeaders(httpResponse);
		
		getResponseEntity(httpResponse);
	}
	
	/**
	 * 获取响应头参数事件
	 * 虚函数，当调用HttpConnect时，需要实现onReceiverHeaders(HttpResponse httpResponse)
	 * 注意：
	 * 	若不需要获取响应头信息，则只实现空函数体即可.
	 * 	若需要获取响应头信息，则可以直接调用super.getResponseHeaders(HttpResponse httpResponse)方法,
	 * 		该方法返回HashMap<String,String>类型的响应头参数
	 * @param httpResponse
	 */
	protected abstract void onReceiverHeaders(HttpResponse httpResponse) throws UnsupportedEncodingException;
	
	/**
	 * 接收相应体触发事件.利用该事件可以实现大文件下载时的进度监控。
	 * 虚函数，当调用HttpConnect时，需要实现onReceiverBodyStream(InputStream is)
	 * 注意：
	 * 	1.该函数是在服务器响应请求时，相应体内有数据的情况下(content-length>0)才会触发。
	 * 
	 * 两种读取方式：
	 * 	1.文本文件类型：
	 * 		//InputStreamReader isr = new InputStreamReader(is);
	 *		//BufferedReader br = new BufferedReader(isr);
	 *		//br.readLine();
	 *
	 *	2.文件类型：
	 *		//读取到的数据长度  
	 *		//int len;  
	 *		//1K的数据缓冲  
	 *		//byte[] bs = new byte[1024];  
	 *		//while ((len = is.read(bs)) != -1)
	 *		//	os.write(bs, 0, len);
	 * @param bytes
	 * @param len
	 */
	protected abstract void onReceiveBodyStream(InputStream is,long contentLength) throws IOException,Exception;
	
	/**
	 * 获取响应头参数
	 * @param httpResponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	protected ParcelMap getResponseHeaders(HttpResponse httpResponse) throws UnsupportedEncodingException
	{
		/*读取相应头参数*/
		ParcelMap resHeaders = new ParcelMap();
		
		HeaderIterator hIte = httpResponse.headerIterator();
		while(hIte.hasNext())
		{
			Header header = hIte.nextHeader();
			String key = header.getName();
			String value = header.getValue();
			value = new String(value.getBytes("ISO-8859-1"),"GB2312");
			resHeaders.put(key, value);
		}
		
		return resHeaders;
	}
	
	/**
	 * 获取相应体
	 * @param httpResponse
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	protected void getResponseEntity(HttpResponse httpResponse) throws IllegalStateException, IOException,Exception
	{
		/*读取相应体*/
		HttpEntity entity = httpResponse.getEntity();
		if (entity != null ) {//&& entity.getContentLength() > 0) {
			InputStream is = null;
//			try {
				is = entity.getContent();
				
				onReceiveBodyStream(is,entity.getContentLength());
				
				if(is!=null)
					is.close();
//			}
//			finally
//			{
//				if(is!=null)
//					is.close();
//			}
		}
	}
	
	/**
	 * 设置请求参数
	 * 
	 * @throws UnsupportedEncodingException
	 */
	private HttpPost getHttpPostRequest(String requestUri) throws UnsupportedEncodingException
	{
		HttpPost httpRequest = new HttpPost(requestUri);
		/*设置请求参数*/
		HashMap<String, String> reqParams = requestParam.getParams();
		if(reqParams!=null&&reqParams.size()>0)
		{
			Set<String> keyset = reqParams.keySet();
			Iterator<String> ite = keyset.iterator();
			
			ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			while(ite.hasNext())
			{
				String key = ite.next();
				String value = reqParams.get(key);
				params.add(new BasicNameValuePair(key,value));
			}
			
			httpRequest.setEntity(new UrlEncodedFormEntity(params, requestParam.getCharset()));
		}
		
		/*设置请求头参数*/
		HashMap<String, String> reqHeaders = requestParam.getHeaders();
		if(reqHeaders!=null&&reqHeaders.size()>0)
		{
			Set<String> keyset = reqHeaders.keySet();
			Iterator<String> ite = keyset.iterator();
			
			while(ite.hasNext())
			{
				String key = ite.next();
				String value = reqHeaders.get(key);
				httpRequest.setHeader(key, value);
			}
		}
		
		/*设置请求实体*/
		byte[] postStream = requestParam.getPostStream();
		if(postStream!=null&&postStream.length>0)
		{
			InputStream is = new ByteArrayInputStream(postStream);
	    	InputStreamEntity reqEntity = new InputStreamEntity(is, postStream.length);  
	    	//reqEntity.setContentType("binary/octet-stream"); 
//	    	reqEntity.setChunked(true);  //设置chunked 传输模式
	    	httpRequest.setEntity(reqEntity);
	    	
		}
		
		return httpRequest;
	}
	
	private HttpGet getHttpGetRequest(String requestUri)
			throws UnsupportedEncodingException {
		
		/*设置请求参数*/
		HashMap<String, String> reqParams = requestParam.getParams();
		if (reqParams != null && reqParams.size() > 0) {
			StringBuffer uriParam = new StringBuffer("");
			
			Set<String> keyset = reqParams.keySet();
			Iterator<String> ite = keyset.iterator();
			
			while (ite.hasNext()) {
				String key = ite.next();
				String value = reqParams.get(key);
				uriParam.append(key+"="+value+"&");
			}
			
			requestUri += uriParam.substring(0, uriParam.length()-1);
		}
		
		HttpGet httpRequest = new HttpGet(requestUri);
		
		/*设置请求头参数*/
		HashMap<String, String> reqHeaders = requestParam.getHeaders();
		if (reqHeaders != null && reqHeaders.size() > 0) {
			Set<String> keyset = reqHeaders.keySet();
			Iterator<String> ite = keyset.iterator();
			
			while (ite.hasNext()) {
				String key = ite.next();
				String value = reqHeaders.get(key);
				httpRequest.setHeader(key, value);
			}
		}
		
		return httpRequest;
	}
	
	private static class InflatingEntity extends HttpEntityWrapper {
        public InflatingEntity(HttpEntity wrapped) {
            super(wrapped);
        }

        @Override
        public InputStream getContent() throws IOException {
            return new GZIPInputStream(wrappedEntity.getContent());
        }

        @Override
        public long getContentLength() {
            return -1;
        }
    }
	
}


