package com.test.http;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.methods.multipart.*;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.IdleConnectionTimeoutThread;

import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * http请求的封装
 * @see HttpRequest
 * @see HttpResponse
 * @author zhangyf
 * @date 2017/03/02
 * @version 1.2
 */
public class HttpHandler {



    private static String              DEFAULT_CHARSET                     = "UTF-8";

    /** 连接超时时间，由bean factory设置，缺省为8秒钟 */
    private int                        defaultConnectionTimeout            = 5000;

    /** 回应超时时间, 由bean factory设置，缺省为30秒钟 */
    private int                        defaultSoTimeout                    = 30000;

    /** 闲置连接超时时间, 由bean factory设置，缺省为60秒钟 */
    private int                        defaultIdleConnTimeout              = 60000;

    private int                        defaultMaxConnPerHost               = 30;

    private int                      defaultMaxTotalConn                 = 80;

    /** 默认等待HttpConnectionManager返回连接超时（只有在达到最大连接数时起作用）：1秒*/
    private static final long          defaultHttpConnectionManagerTimeout = 3 * 1000;

    /**
     * HTTP连接管理器，该连接管理器必须是线程安全的.
     */
    private HttpConnectionManager      connectionManager;

    private static HttpHandler httpProtocolHandler = new HttpHandler();

    /**
     * 工厂方法
     * 
     * @return
     */
    public static HttpHandler getInstance() {
        return httpProtocolHandler;
    }

    /**
     * 私有的构造方法
     */
    private HttpHandler() {
        // 创建一个线程安全的HTTP连接池
        connectionManager = new MultiThreadedHttpConnectionManager();
        connectionManager.getParams().setDefaultMaxConnectionsPerHost(defaultMaxConnPerHost);
        connectionManager.getParams().setMaxTotalConnections(defaultMaxTotalConn);

        IdleConnectionTimeoutThread ict = new IdleConnectionTimeoutThread();
        ict.addConnectionManager(connectionManager);
        ict.setConnectionTimeout(defaultIdleConnTimeout);

        ict.start();
    }

	@SuppressWarnings("unchecked")
	public <T> HttpResponse<T> execute(HttpRequest request, Class<T> resultType) {
		HttpMethod method = null;
		HttpResponse<T> response = new HttpResponse<T>();
        try {
        	HttpClient httpclient = new HttpClient(connectionManager);

            // 设置连接超时
            int connectionTimeout = defaultConnectionTimeout;
            if (request.getConnectionTimeout() > 0) {
                connectionTimeout = request.getConnectionTimeout();
            }
            httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);

            // 设置回应超时
            int soTimeout = defaultSoTimeout;
            if (request.getTimeout() > 0) {
                soTimeout = request.getTimeout();
            }
            httpclient.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);
            // 设置等待ConnectionManager释放connection的时间
            httpclient.getParams().setConnectionManagerTimeout(defaultHttpConnectionManagerTimeout);
            String charset = request.getCharset();
            charset = charset == null ? DEFAULT_CHARSET : charset;
            

            
            if(HttpRequest.METHOD_GET.equals(request.getMethod())) {
            	
            	method = new GetMethod(request.getUrl());
            	method.getParams().setCredentialCharset(charset);
                method.addRequestHeader("Content-Type", "text/html; charset=" + charset);
                // parseNotifyConfig会保证使用GET方法时，request一定使用QueryString
                if(request.getParams() != null) {
                	method.setQueryString(getUrlParams(request.getParams()));
                }
            } else if(HttpRequest.METHOD_POST.equals(request.getMethod())) {
            	method = new PostMethod(request.getUrl());
            	if(request.getParams() != null || request.getPostString() != null) {
            		if(request.getParams() != null) {
            			((PostMethod) method).addParameters(getNameValuePair(request.getParams()));
            		}
            		if(request.getPostString() != null) {
            			RequestEntity entity = new StringRequestEntity(request.getPostString(), "text/xml",  charset);
                    	((EntityEnclosingMethod) method).setRequestEntity(entity); 
            		}
                	method.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; text/html; charset=" + charset);
            	} else if(request.getFile() != null) {
            		//post模式且带上传文件
                    List<Part> parts = new ArrayList<Part>();
                    if(request.getParams() != null) {
                    	Map<String, Object> params = request.getParams();
                        for (String key: params.keySet()) {
                        	parts.add(new StringPart(key, String.valueOf(params.get(key)), charset));
        				}
                    }
                    //增加文件参数，strParaFileName是参数名，使用本地文件
                    parts.add(new FilePart(request.getFile().getName(), new FilePartSource(request.getFile())));
                    // 设置请求体
                    ((PostMethod) method).setRequestEntity(new MultipartRequestEntity(parts.toArray(new Part[0]), new HttpMethodParams()));
            	}
            	// 设置Http Header中的User-Agent属性
                method.addRequestHeader("User-Agent", "Mozilla/4.0");
            } else {
            	throw new Exception("http method 提交方法错误！");
            }
            
            httpclient.executeMethod(method);
            if (resultType == String.class) {
            	response.setResult((T)method.getResponseBodyAsString());
            } else if (resultType == byte[].class) {
            	response.setResult((T)method.getResponseBody());
            } else if(resultType == InputStream.class) {
            	response.setResult((T)method.getResponseBodyAsStream());
            }
            response.setResponseHeaders(method.getResponseHeaders());
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
            System.out.println("位置请求地址");
        	ex.printStackTrace();
        } catch (Exception e) {
			e.printStackTrace();
            System.out.println("请求异常:");
        }  finally {
            method.releaseConnection();
        }
        return response;
    }
   

    /**
     * 将NameValuePairs数组转变为字符串
     * 
     * @param nameValues
     * @return
     */
    public String pairToString(NameValuePair[] nameValues) {
        if (nameValues == null || nameValues.length == 0) {
            return "null";
        }

        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < nameValues.length; i++) {
            NameValuePair nameValue = nameValues[i];

            if (i == 0) {
                buffer.append(nameValue.getName() + "=" + nameValue.getValue());
            } else {
                buffer.append("&" + nameValue.getName() + "=" + nameValue.getValue());
            }
        }

        return buffer.toString();
    }
    
    public static void main(String[] args) {
    	System.out.println(parseUrlParams("name", "张", "age", "14"));
	}
    
    public static String parseUrlParams(String... nameOrValue) {
    	StringBuilder sb = new StringBuilder();
    	for (int i = 0; i < nameOrValue.length; i++) {
			if(i%2 == 0) {
				sb.append(nameOrValue[i]).append("=");
			} else {
				sb.append(nameOrValue[i]).append("&");
			}
		}
		return sb.deleteCharAt(sb.length()-1).toString();
    }
    
    /**
     * map转NameValuePairs
     * @param map
     * @return
     */
    public static NameValuePair[] getNameValuePair(Map<String, Object> map) {
    	int i = 0;
    	NameValuePair[] nameValuePairs = new NameValuePair[map.size()];
    	for (String key: map.keySet()) {
    		NameValuePair nameValuePair = new NameValuePair(key, String.valueOf(map.get(key)));
    		nameValuePairs[i] = nameValuePair;
			i++;
		}
		return nameValuePairs;
    }
    
    /**
     * map转url格式参数
     * @param map
     * @return
     */
    public static String getUrlParams(Map<String, Object> map) {
    	StringBuilder builder = new StringBuilder();
    	for (String key: map.keySet()) {
    		builder.append(key).append("=").append(map.get(key)).append("&");
		}
    	return builder.deleteCharAt(builder.length()-1).toString();
    }
    
    /**
     * 通过完整url获取？前面链接
     * @param url
     * @return
     */
    public static String getUrlStr(String url) {
			return url.substring(0, url.indexOf("?"));
    }
    
    /**
     * 通过完整链接获取url后面参数
     * @param url
     * @return
     */
    public static String getUrlParams(String url) {
		return url.substring(url.indexOf("?")+1);
    }
    
    /**
     * 通过完整链接获取url后面参数
     * @param url
     * @return
     */
    public static Map<String, Object> getUrlParamsForMap(String url) {
    	Map<String, Object> resultParam = new HashMap<String, Object>();
    	String[] urlParams = url.substring(url.indexOf("?")+1).split("\\&");
    	for (int i = 0; i < urlParams.length; i++) {
    		String[] keyValues = urlParams[i].split("\\=");
    		if(keyValues.length == 1) {
    			resultParam.put(keyValues[0], "");
    		} else {
    			resultParam.put(keyValues[0], keyValues[1]);
    		}
    		
		}
    	return resultParam;
    }
    
    /**
     * 通过完整链接获取url后面参数
     * @param url
     * @return
     */
    public static NameValuePair[] getNameValuePair(String url) {
		String urlParams = url.split("\\?")[1];
		String[] params = urlParams.split("\\&");
		NameValuePair[] nameValuePairs = new NameValuePair[params.length];
		for (int i = 0; i < params.length; i++) {
			String[] onParam = params[i].split("\\=");
			NameValuePair nameValuePair = new NameValuePair(onParam[0], onParam[1]);
			nameValuePairs[i] = nameValuePair;
		}
		return nameValuePairs;
    }
}
