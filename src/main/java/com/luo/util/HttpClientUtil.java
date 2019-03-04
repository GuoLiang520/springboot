package com.luo.util;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class HttpClientUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * post请求
     * @param url url
     * @param heads head
     * @param jsonString 请求体
     * @return String
     */
    public static String getPost(String url, Map<String,String> heads, String jsonString){
        HttpClient httpClient;
        HttpEntityEnclosingRequestBase post;
        String rtn ;
        InputStream in = null;
        try {
            //4.3版本之前
            //httpClient = new  DefaultHttpClient;
            httpClient = HttpClients.createDefault();
            //post = new HttpPost(url);
            post = new HttpPatch(url);
            for (String key : heads.keySet()){
                post.setHeader(key, heads.get(key));
            }
            post.setConfig(setTime(0,0,0));
            post.setEntity(setEntity(jsonString,""));
            HttpResponse response = httpClient.execute(post);
            if(HttpStatus.SC_OK != response.getStatusLine().getStatusCode()){
                logger.error(url+"连接失败");
            }
            in = response.getEntity().getContent();
            rtn = inToString(in);
        }catch (IOException e) {
            logger.error("Httpclient 错误", e);
            rtn = "err";
        }finally {
            //FileUtil.closeInputStream(in);
        }
        return rtn;
    }

    /**
     * 设置entity
     * @param parm 请求体
     * @param contentType 请求类型
     * @return stringentity 实例
     */
    public static StringEntity setEntity(String parm,String contentType){
        StringEntity entity = new StringEntity(parm, "utf-8");
        if (!StringUtil.isEmpty(contentType)){
            entity.setContentType(contentType);
        }
        return  entity;
    }

    /**
     * 设置请求时间
     * @param connectionTime 连接时间
     * @param requestTime 获取请求池
     * @param socketTime 读取时间
     * @return RequestConfig 实例
     */
    public static RequestConfig setTime(int connectionTime,int requestTime,int socketTime){
        if(connectionTime <= 0){
            connectionTime = 10000;
        }
        if(requestTime <= 0){
            requestTime = 10000;
        }
        if(socketTime <= 0){
            socketTime = 10000;
        }
        return  RequestConfig.custom()
                .setConnectTimeout(connectionTime)
                .setConnectionRequestTimeout(requestTime)
                .setSocketTimeout(socketTime).build();
    }

    /**
     * inToString
     * @param in 输入流
     * @return String
     */
    public static String inToString(InputStream in){
        byte[] buffer = new byte[1024];
        int length = 0;
        StringBuffer rtn = new StringBuffer();
        try {
            while ((length=in.read(buffer)) != -1){
                rtn.append(new String(buffer, 0, length));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rtn.toString();
    }
}
