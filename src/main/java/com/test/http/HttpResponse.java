package com.test.http;

import org.apache.commons.httpclient.Header;

import java.io.InputStream;

/**
 * http响应类
 * @author zhangyf
 * @date 2017/03/02
 * @version 1.2
 * @param <T> 响应内容类型
 */
public class HttpResponse<T> {

    /**
     * 返回中的Header信息
     */
    private Header[] responseHeaders;
    
    private T result;
    
    public static Class<byte[]> BYTE_ARRAY = byte[].class;
    public static Class<String> STRING = String.class;
    public static Class<InputStream> INPUTSTREAM = InputStream.class;
    
    HttpResponse() {}
   
    
    public Header[] getResponseHeaders() {
        return responseHeaders;
    }

    public void setResponseHeaders(Header[] responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}
}
