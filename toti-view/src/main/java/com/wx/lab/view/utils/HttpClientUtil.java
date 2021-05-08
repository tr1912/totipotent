/*
 * @(#)HttpClient.java 2012-12-11
 *
 * Copyright 2012 SH-Menue,Inc. All rights reserved.
 */
package com.wx.lab.view.utils;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.Map.Entry;

/**
 *
 */
public class HttpClientUtil {
    protected static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    public final String FILE_INPUT_NAME = "imageFiles";

    public final String DEFAULT_ENCODING = "UTF-8";
    /**
     * v4.3
     * 模拟GET方式提交
     *
     * @param url
     * @return
     * @throws IOException
     * @throws HttpException
     */
    public String doGet(String url, Map<String, String> headers) throws HttpException, IOException {
        HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod(url);
        if(headers != null && !headers.isEmpty()){
            for(Entry<String, String> entry : headers.entrySet()){
                getMethod.addRequestHeader(entry.getKey(),entry.getValue());
            }
        }
        httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
        httpClient.executeMethod(getMethod);
        return getMethod.getResponseBodyAsString();

    }

    /**
     * v4.3
     * 模拟POST方式提交(json参数)
     *
     * @param url
     * @param jsonParams
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public String doPost(String url, String jsonParams, Map<String, String> headers)
            throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Content-Type", "application/json");
        if(headers != null && !headers.isEmpty()){
            for(Entry<String, String> entry : headers.entrySet()){
                httpPost.setHeader(entry.getKey(),entry.getValue());
            }
        }
        httpPost.setEntity(new StringEntity(jsonParams, DEFAULT_ENCODING));
        String result = null;
        HttpResponse res = null;
        try {
            res = httpClient.execute(httpPost);
            if (res!= null && res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = res.getEntity();
                result = EntityUtils.toString(entity, DEFAULT_ENCODING);
            }
        } catch (Exception e) {
            logger.info("http请求异常{}",e);
            throw new Exception(e);
        } finally {
            // 关闭连接，释放资源
            httpClient.close();
        }
        return result;
    }

    /**
     * v4.3
     * 模拟POST方式提交(表单参数)
     *
     * @param url
     * @param paramsMap
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public String doPost(String url, Map<String, Object> paramsMap) throws ClientProtocolException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        //添加参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        Set<Entry<String, Object>> entrySet = paramsMap.entrySet();
        for (Entry<String, Object> entry : entrySet) {
            list.add(new BasicNameValuePair(entry.getKey(), entry.getValue() + ""));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(list, DEFAULT_ENCODING));
        String result = null;
        try {
            HttpResponse res = httpClient.execute(httpPost);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = res.getEntity();
                result = EntityUtils.toString(entity, DEFAULT_ENCODING);
                // System.out.println(result);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);

        } finally {
            // 关闭连接，释放资源
            httpClient.close();
        }
        return result;
    }
    /**
     * v4.3
     * 模拟POST方式提交(表单参数)
     *
     * @param url
     * @param headers
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public String doUserPost(String url, Map<String, String> headers) throws ClientProtocolException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json");
        if(headers != null && !headers.isEmpty()){
            for(Entry<String, String> entry : headers.entrySet()){
                httpPost.setHeader(entry.getKey(),entry.getValue());
            }
        }
       /* //添加参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        Set<Entry<String, Object>> entrySet = paramsMap.entrySet();
        for (Entry<String, Object> entry : entrySet) {
            list.add(new BasicNameValuePair(entry.getKey(), entry.getValue() + ""));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(list, DEFAULT_ENCODING));*/
        String result = null;
        try {
            HttpResponse res = httpClient.execute(httpPost);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = res.getEntity();
                result = EntityUtils.toString(entity, DEFAULT_ENCODING);
                // System.out.println(result);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);

        } finally {
            // 关闭连接，释放资源
            httpClient.close();
        }
        return result;
    }
    /**
     * v4.3
     * 模拟POST方式提交(表单参数,head)
     *
     * @param url
     * @param paramsMap
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public String doPost(String url, Map<String, Object> paramsMap, Map<String, String> headers, int timeout) throws ClientProtocolException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();
        httpPost.setConfig(requestConfig);
        httpPost.addHeader("Content-type", "application/x-www-form-urlencoded");
        if(headers != null && !headers.isEmpty()){
            for(Entry<String, String> entry : headers.entrySet()){
                httpPost.setHeader(entry.getKey(),entry.getValue());
            }
        }
        //添加参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        Set<Entry<String, Object>> entrySet = paramsMap.entrySet();
        for (Entry<String, Object> entry : entrySet) {
            list.add(new BasicNameValuePair(entry.getKey(), entry.getValue() + ""));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(list, DEFAULT_ENCODING));
        String result = null;
        try {
            HttpResponse res = httpClient.execute(httpPost);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = res.getEntity();
                result = EntityUtils.toString(entity, DEFAULT_ENCODING);
                // System.out.println(result);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);

        } finally {
            // 关闭连接，释放资源
            httpClient.close();
        }
        return result;
    }
    /**
     * 模拟POST提交(图片)
     *
     * @param filePaths
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public String doUpload(List<String> filePaths) throws ClientProtocolException, IOException {
        return doUpload("http://localhost:8080/file/uploadImages", MapUtils.EMPTY_MAP, filePaths, FILE_INPUT_NAME);
    }

    /**
     * 模拟POST提交(图片)
     *
     * @param filePaths
     * @param fieldName <input type='file' name='imageFiles'/>
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public String doUpload(List<String> filePaths, String fieldName) throws ClientProtocolException, IOException {
        return doUpload("http://localhost:8080/file/uploadImages", MapUtils.EMPTY_MAP, filePaths, fieldName);
    }

    /**
     * 模拟POST提交(图片)
     *
     * @param url
     * @param filePaths
     * @param fieldName <input type='file' name='imageFiles'/>
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public String doUpload(String url, List<String> filePaths, String fieldName) throws ClientProtocolException, IOException {
        return doUpload(url, MapUtils.EMPTY_MAP, filePaths, fieldName);
    }

    /**
     * 模拟POST提交(表单参数+图片)
     *
     * @param url
     * @param filePaths
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public String doUpload(String url, Map<String, Object> paramsMap, List<String> filePaths) throws ClientProtocolException, IOException {
        return doUpload(url, MapUtils.EMPTY_MAP, filePaths, FILE_INPUT_NAME);
    }

    /**
     * 模拟POST提交(表单参数+图片)
     *
     * @param url
     * @param paramsMap
     * @param filePaths
     * @param fieldName <input type='file' name='imageFiles'/>
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public String doUpload(String url, Map<String, Object> paramsMap, List<String> filePaths, String fieldName) throws ClientProtocolException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        //httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        MultipartEntityBuilder meBuilder = MultipartEntityBuilder.create();
        //添加参数
        if (CollectionUtils.isNotEmpty(paramsMap)) {
            Set<Entry<String, Object>> entrySet = paramsMap.entrySet();
            for (Entry<String, Object> entry : entrySet) {
                meBuilder.addTextBody(entry.getKey(), entry.getValue() + "");
            }
        }
        //添加图片
        if (CollectionUtils.isNotEmpty(filePaths)) {
            for (String filePath : filePaths) {
                if (StringUtils.isEmpty(filePath)) {
                    continue;
                }
                meBuilder.addBinaryBody(fieldName, new File(filePath));
            }
        }
        httpPost.setEntity(meBuilder.build());
        String result = null;
        try {
            HttpResponse res = httpClient.execute(httpPost);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = res.getEntity();
                result = EntityUtils.toString(entity, DEFAULT_ENCODING);
                // System.out.println(result);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);

        } finally {
            // 关闭连接，释放资源
            httpClient.close();
        }
        return result;
    }

    /**
     * 获取访问者真实IP
     *
     * @param request
     * @return String
     * @Title: getReuqestAgent
     */
    public String getRemoteAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {//"unknown"是找不到IP的标识。
            return ip;
        }

        ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP
            int index = ip.indexOf(',');
            if (index > -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }

        ip = request.getHeader("Proxy-Client-IP");
        if (StringUtils.isNotEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }

        ip = request.getHeader("WL-Proxy-Client-IP");
        if (StringUtils.isNotEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }

        ip = request.getHeader("HTTP_CLIENT_IP");
        if (StringUtils.isNotEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }

        ip = request.getHeader("X-Cluster-Client-IP");
        if (StringUtils.isNotEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

    /**
     * 获取访问源
     *
     * @param request
     * @return String
     * @Title: getReuqestAgent
     */
    public String getReuqestAgent(HttpServletRequest request) {
        return request.getHeader("USER-AGENT") == null ? "" : request.getHeader("USER-AGENT").toLowerCase();
    }

    /**
     * 请求参数处理
     *
     * @param request
     * @return String
     * @Title: paramToString
     */
    public String paramToString(HttpServletRequest request) {
        StringBuilder str = new StringBuilder();
        Enumeration<String> e = request.getParameterNames();
        String paramName;
        while (e.hasMoreElements()) {
            paramName = e.nextElement();
            str.append('&').append(paramName).append("=").append(request.getParameter(paramName));
        }
        if (str.length() > 0) {
            str.deleteCharAt(0);
        }
        return str.toString();
    }

    public String encodeRemotePath(String remotePath) {
        String[] pathSegmentsArr = remotePath.split("/");
        StringBuilder pathBuilder = new StringBuilder();
        for (String pathSegment : pathSegmentsArr) {
            if (pathSegment.trim().isEmpty())
            { continue;}
            try {
                pathBuilder.append("/").append(URLEncoder.encode(pathSegment.trim(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        if (remotePath.endsWith("/")) {
            pathBuilder.append("/");
        }
        return pathBuilder.toString();
    }

    /**
     * 获取完整的项目路径
     *
     * @param request
     * @return String
     * @Title: getContextPath
     */
    public String getContextPath(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(request.getScheme()).append("://").append(request.getServerName()).append(":").append(request.getServerPort()).append(request.getContextPath());
        String path = sb.toString();
        sb = null;
        return path;
    }

    /**
     * 将Map键志对转换为URL参数形式
     *
     * @param paramMap
     * @return String
     * @Title: mapToURL
     */
    public String mapToURL(Map<String, String> paramMap) {
        if (CollectionUtils.isEmpty(paramMap)) {
            return "";
        }
        StringBuilder str = new StringBuilder(0);
        for (Entry<String, String> entry : paramMap.entrySet()) {
            str.append('&').append(entry.getKey()).append("=").append(entry.getValue());
        }

        if (str.length() > 0) {
            str.deleteCharAt(0);
        }
        return str.toString();
    }

    /**
     * 获取指定得cookie值
     *
     * @param request
     * @param cookieName
     * @return String
     * @Title: getCookieValue
     */
    public String getCookieValue(HttpServletRequest request, String cookieName) {
        if (request == null) {
            return null;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 设置cookie值
     *
     * @param response
     * @param cookieName
     * @param value
     * @param cookieAge  void
     * @Title: setCookieValue
     */
    public void setCookieValue(HttpServletResponse response, String cookieName, String value, int cookieAge) {
        Cookie cookie = new Cookie(cookieName, value);
        cookie.setPath("/");
        cookie.setMaxAge(cookieAge);
        response.addCookie(cookie);
    }
    /**
     * 得到字节List
     * @param url
     * @return
     */
    public byte[] doGetImage(String url) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(350000)// 连接主机服务超时时间
                .setConnectionRequestTimeout(350000)// 请求超时时间
                .setSocketTimeout(600000)// 数据读取超时时间
                .build();

        CloseableHttpResponse response = null;
        try {
            // 创建httpGet远程连接实例
            HttpGet httpGet = new HttpGet(url);

            // 为httpGet实例设置配置
            httpGet.setConfig(requestConfig);

            // 执行get请求得到返回对象
            response = httpClient.execute(httpGet);

            // 通过返回对象获取返回数据
            HttpEntity entity = response.getEntity();
            InputStream inStream = entity.getContent();    //通过输入流获取图片数据
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024 * 1024 * 100];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            inStream.close();
            byte[] data = outStream.toByteArray();
            return data;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // 关闭资源
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
