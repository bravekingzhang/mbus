package com.tencent.hoollyzhang.mbus.utils.http;
import android.util.Log;

import com.loopj.android.http.*;
/**
 * Title: Campus<br>
 * Description: 一个静态的httpclient<br>
 * Create DateTime: 2015/1/22 20:56 <br>
 * SVN last modify person: hoollyzhang <br>
 * SVN last modify DateTime: 2015/1/22 时间需+1小时 <br>
 * SVN last version: V1.0.0 <br>
 *
 * @author hoollyzhang
 */
public class BusBaseHttpClient {
    private final static String TAG="BusBaseHttpClient";
    private static final String BASE_URL = "http://busapi.gpsoo.net/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    //参数完全自己拼凑
    public static void get(String url,AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url),  responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        Log.e(TAG,"url is "+BASE_URL + relativeUrl);
        return BASE_URL + relativeUrl;
    }
}
