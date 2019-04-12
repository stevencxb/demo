package com.test.http;

import java.io.File;
import java.util.Map;

/**
 * http请求类
 * @author zhangyf
 * @date 2017/03/02
 * @version 1.2
 */
public class HttpRequest {

	/** HTTP GET method */
	public static final String METHOD_GET = "GET";

	/** HTTP POST method */
	public static final String METHOD_POST = "POST";

	/**
	 * 待请求的url
	 */
	private String url = null;

	/**
	 * 默认的请求方式
	 */
	private String method = METHOD_POST;

	private int timeout = 0;

	private int connectionTimeout = 0;

	private Map<String, Object> params = null;
	
	private String postString = null;

	private File file = null;

	/**
	 * 默认的请求编码方式
	 */
	private String charset = "UTF-8";

	/**
	 * 请求发起方的ip地址
	 */
	private String clientIp;
	
	public HttpRequest() {
		
	}

	public HttpRequest(String url, String method) {
		super();
		this.url = url;
		this.method = method;
	}

	public HttpRequest(String url, String method, Map<String, Object> params) {
		super();
		this.url = url;
		this.method = method;
		this.params = params;
	}

	public HttpRequest(String url, String method, Map<String, Object> params,
			String postString) {
		super();
		this.url = url;
		this.method = method;
		this.params = params;
		this.postString = postString;
	}

	public HttpRequest(String url, String method, Map<String, Object> params,
			String postString, File file) {
		super();
		this.url = url;
		this.method = method;
		this.params = params;
		this.postString = postString;
		this.file = file;
	}

	public HttpRequest(String url, String method, int timeout,
			int connectionTimeout, Map<String, Object> params,
			String postString, File file, String charset, String clientIp) {
		super();
		this.url = url;
		this.method = method;
		this.timeout = timeout;
		this.connectionTimeout = connectionTimeout;
		this.params = params;
		this.postString = postString;
		this.file = file;
		this.charset = charset;
		this.clientIp = clientIp;
	}

	public String getClientIp() {
		return clientIp;
	}

	public HttpRequest setClientIp(String clientIp) {
		this.clientIp = clientIp;
		return this;
	}

	public String getUrl() {
		return url;
	}

	public HttpRequest setUrl(String url) {
		this.url = url;
		return this;
	}

	public String getMethod() {
		return method;
	}

	public HttpRequest setMethod(String method) {
		this.method = method;
		return this;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public HttpRequest setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
		return this;
	}

	public int getTimeout() {
		return timeout;
	}

	public HttpRequest setTimeout(int timeout) {
		this.timeout = timeout;
		return this;
	}

	public String getCharset() {
		return charset;
	}

	public HttpRequest setCharset(String charset) {
		this.charset = charset;
		return this;
	}

	public File getFile() {
		return file;
	}

	public HttpRequest setFile(File file) {
		this.file = file;
		return this;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public HttpRequest setParams(Map<String, Object> params) {
		this.params = params;
		return this;
	}

	public String getPostString() {
		return postString;
	}

	public HttpRequest setPostString(String postString) {
		this.postString = postString;
		return this;
	}
	
}
