/*
 * 文 件 名  :  HttpClientUtils.java
 * 版    权    :  Ltd. Copyright (c) 2015 深圳创维数字技术有限公司,All rights reserved
 * 描    述    :  &lt;描述&gt;
 * 创建人    :  韩红强
 * 创建时间:  2016-6-1 上午9:07:11
 */
package com.sportswork.sportswork.utils;

import com.alibaba.fastjson.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * @author  韩红强
 * @version  [版本号, 2016-6-1 上午9:07:11]
 */
public class HttpClientUtils {

	private final static Logger log = LogManager.getLogger(HttpClientUtils.class);

    //连接超时时间，默认10秒
    private static int socketTimeout = 10000;

    //传输超时时间，默认30秒
    private static int connectTimeout = 30000;
    
    public static CloseableHttpClient getHttpClient(){
        return HttpClients.createDefault();
    }
    
    /**
     * 以POST的方式提交数据
     * <功能详细描述>
     * @param url
     * @param xmlData
     * @return [参数说明]
     * @return String 
     * @exception throws [违例类型] [违例说明]
     */
    public static String postData(String url , String xmlData){
        String result = "";
        CloseableHttpClient httpClient = getHttpClient();
        try {
            RequestConfig requestConfig =
                    RequestConfig.custom()
                    .setSocketTimeout(socketTimeout)
                    .setConnectTimeout(connectTimeout).build();
            
            HttpPost post = new HttpPost(url);          //这里用上本机的某个工程做测试
            
            StringEntity postEntity = new StringEntity(xmlData, "UTF-8");
            post.addHeader("Content-Type", "application/json");
            post.setEntity(postEntity);
            post.setConfig(requestConfig);
            
            //执行请求
            CloseableHttpResponse httpResponse = httpClient.execute(post);
            try{
                HttpEntity entity = httpResponse.getEntity();
                if (null != entity){
                    result = EntityUtils.toString(entity , "UTF-8");
                }
            } finally{
                httpResponse.close();
            }
             
        }catch( UnsupportedEncodingException e){
            log.info(e.getMessage());
        }catch(IOException e) {
            log.info(e.getMessage());
        }finally{
            try{
                closeHttpClient(httpClient);                
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        
        return result;
    }
    
    private static void closeHttpClient(CloseableHttpClient client) throws IOException {
        if (client != null){
            client.close();
        }
    }
    
    public static String getStringFromHttp(String url) {
        return getStringFromHttp(url, null);
    }
    
    public static boolean postUrl(String url) {
        return postUrl(url, null);
    }

    public static String getStringFromHttp(String url, String charset) {
    	String result = "";
        
        CloseableHttpClient httpClient = getHttpClient();
        try {
            RequestConfig requestConfig =
                    RequestConfig.custom()
                    .setSocketTimeout(socketTimeout)
                    .setConnectTimeout(connectTimeout).build();

            HttpGet getMethod = new HttpGet(url);
            getMethod.setConfig(requestConfig);
            CloseableHttpResponse response = httpClient.execute(getMethod);
            
            //执行请求
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                if (charset != null) {
                	result = new String(EntityUtils.toByteArray(entity), charset);
                } else {
                	result = EntityUtils.toString(entity, "utf-8");
                }
            }
        } catch (Exception e) {
        	 log.info(e.getMessage());
        } finally {
        	try{
                closeHttpClient(httpClient);                
            } catch(Exception e){
            	 log.info(e.getMessage());
            }
        }
        
        return result;
    }
    
    public static boolean postUrl(String url, String charset) {
    	boolean postResult=false;
    	String result = "";
        
        CloseableHttpClient httpClient = getHttpClient();
        try {
            RequestConfig requestConfig =
                    RequestConfig.custom()
                    .setSocketTimeout(socketTimeout)
                    .setConnectTimeout(connectTimeout).build();

            HttpGet getMethod = new HttpGet(url);
            getMethod.setConfig(requestConfig);
            CloseableHttpResponse response = httpClient.execute(getMethod);
            
            //执行请求
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                if (charset != null) {
                	result = new String(EntityUtils.toByteArray(entity), charset);
                } else {
                	result = EntityUtils.toString(entity, "utf-8");
                }
            }
            log.info("result=="+result);
            postResult=true;
        } catch (Exception e) {
            log.info(e.getMessage());
        } finally {
        	try{
                closeHttpClient(httpClient);                
            } catch(Exception e){
                log.info(e.getMessage());
            }
        }
        
        return postResult;
    }
    
    public static String postWithParams(String url, List<NameValuePair> params) {
    	return postWithParams(url, params, null);
    }
    public static String postWithParams(String url, List<NameValuePair> params, Map<String, String> headers) {
    	try {
    		CloseableHttpClient httpClient = getHttpClient();
    		RequestConfig requestConfig =
                    RequestConfig.custom()
                    .setSocketTimeout(socketTimeout)
                    .setConnectTimeout(connectTimeout)
                    .setConnectionRequestTimeout(connectTimeout).build();
        	HttpPost httpPost = new HttpPost(url);
        	httpPost.setEntity(new UrlEncodedFormEntity(params,"utf-8"));
        	httpPost.setConfig(requestConfig);
        	if(headers != null && !headers.isEmpty()) {
        		for (Map.Entry<String, String> entry : headers.entrySet()) {
        			httpPost.setHeader(entry.getKey(),entry.getValue());
        		}
        	}
        	HttpResponse httpResponse = httpClient.execute(httpPost);
        	if (httpResponse.getStatusLine().getStatusCode() == 200) {
        		HttpEntity entity = httpResponse.getEntity();
        		if(entity != null){
        			String jsonContent = EntityUtils.toString(entity, "UTF-8");
        			return jsonContent;
        		}
        	}else{
        		log.info("statusCode:"+httpResponse.getStatusLine().getStatusCode());
        	}
		} catch (Exception e) {
			log.error("syn post error1:{}",e.getMessage());
		}
    	return null;
    }
    
    public static String postWithBody(String url, String body) {
    	try {
    		CloseableHttpClient httpClient = getHttpClient();
    		RequestConfig requestConfig =
                    RequestConfig.custom()
                    .setSocketTimeout(socketTimeout)
                    .setConnectTimeout(connectTimeout)
                    .setConnectionRequestTimeout(connectTimeout).build();
        	HttpPost httpPost = new HttpPost(url);
        	httpPost.setEntity(new StringEntity(body,"utf-8"));
        	httpPost.setConfig(requestConfig);
        	HttpResponse httpResponse = httpClient.execute(httpPost);
        	HttpEntity entity = httpResponse.getEntity();
        	if (httpResponse.getStatusLine().getStatusCode() == 200) {
        		if(entity != null){
        			String jsonContent = EntityUtils.toString(entity, "UTF-8");
        			return jsonContent;
        		}
        	}else{
        		log.info("statusCode:"+httpResponse.getStatusLine().getStatusCode());
        		String jsonContent = EntityUtils.toString(entity, "UTF-8");
        		log.info("error:"+jsonContent);
        	}
		} catch (Exception e) {
			log.error("syn post error1:{}",e.getMessage());
		}
    	return null;
    }




}
