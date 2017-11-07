package com.wust.utils;

import okhttp3.*;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.Map;


/**
 * Description:
 * 以get/post的方式发送数据到指定的http接口---利用okhttp3
 */

public class OKHttp3Util {

    private static final Log log = LogFactory.getLog(OKHttp3Util.class);

    /**
      * @Author: WUST_WXL
      * @Date: 上午 10:07 2017/11/7
      * @ModifiedBy:
      * @Description: 除了URL,不带参数的get方法
      */
    public static String getOKHttp3(String url) {
        String responseMsg = "";

        OkHttpClient okHttpClient = new OkHttpClient();

        log.info("URL ------->" + url);
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = okHttpClient.newCall(request);

        try {

            Response response = call.execute();
            log.info("responseMsg ------->" + responseMsg);
            responseMsg = response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseMsg;
    }


    /**
      * @Author: WUST_WXL
      * @Date: 上午 10:07 2017/11/7
      * @ModifiedBy:
      * @Description: 除了URL,有Json参数的post方法
      */
    public static String postOKHttp3(String url, String jsonStr) {

        String responseMsg = "";

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient okHttpClient = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, jsonStr);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = okHttpClient.newCall(request);
        try {

            Response response = call.execute();
            responseMsg = response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("responseMsg ------->" + responseMsg);
        return responseMsg;
    }

    /**
     * @Author: WUST_WXL
     * @Date: 上午 10:07 2017/11/7
     * @ModifiedBy:
     * @Description: 除了URL,有Map集合参数的post方法
     */
    public static String httpPostForm (String url, Map<String, String> param) {


        String responseMsg = "";

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient okHttpClient = new OkHttpClient();

        FormBody.Builder builder = new FormBody.Builder();

        for (Map.Entry<String, String> entry : param.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }

        RequestBody formBody = builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        Call call = okHttpClient.newCall(request);
        try {

            Response response = call.execute();
            responseMsg = response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("responseMsg ------->" + responseMsg);
        return responseMsg;
    }
}
