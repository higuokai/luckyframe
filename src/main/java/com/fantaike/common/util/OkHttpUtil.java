package com.fantaike.common.util;

import com.fantaike.framework.lang.HttpResponse;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OkHttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(OkHttpUtil.class);

    private Request.Builder requestBuilder = new Request.Builder();

    /**
     * 调用okhttp的newCall方法
     * @return
     */
    public HttpResponse execNewCall(){
        Request request = requestBuilder.build();
        Response response = null;
        try {
            OkHttpClient okHttpClient = SpringContextUtil.getBean(OkHttpClient.class);
            response = okHttpClient.newCall(request).execute();
            
            HttpResponse result = new HttpResponse();
            result.setCode(response.code());
            result.setBody(response.body().string());
            result.setHeaders(response.headers());
            String header = response.header("Content-type");
            result.setMediaType(header.substring(header.lastIndexOf("/")+1, header.lastIndexOf(";")));
            return result;
        } catch (Exception e) {
            logger.error("Http请求执行失败", e);
            return null;
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    public void setURL(String url) {
        requestBuilder.url(url);
    }

    public OkHttpUtil addHeader(String name, String value){
        requestBuilder.addHeader(name, value);
        return this;
    }

    /**
     * 设置请求体,
     * @param requestBody 设置请求体
     * @param method 请求方法
     * @return OkHttpUtil
     */
    public OkHttpUtil setBody(RequestBody requestBody, String method) {
        if (method == null) {
            requestBuilder.get();
            return this;
        }
        // 请求方法
        switch (method.toLowerCase()){
            case "get":
                requestBuilder.get();
                break;
            case "post":
                requestBuilder.post(requestBody);
                break;
            case "head":
                requestBuilder.head();
                break;
            // 其他类型
            default:
                break;
        }
        return this;
    }
    
}
