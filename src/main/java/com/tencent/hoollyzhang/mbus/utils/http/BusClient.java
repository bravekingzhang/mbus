package com.tencent.hoollyzhang.mbus.utils.http;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Title: Campus<br>
 * Description:获取即将来临的bus信息 <br>
 * Create DateTime: 2015/1/22 21:03 <br>
 * SVN last modify person: hoollyzhang <br>
 * SVN last modify DateTime: 2015/1/22 时间需+1小时 <br>
 * SVN last version: V1.0.0 <br>
 *
 * @author hoollyzhang
 */
public class BusClient {
    private String TAG = "BusClient";

    public void setHttpListener(HttpListener httpListener) {
        this.httpListener = httpListener;
    }

    private HttpListener httpListener;

    public void getCommingBusInfo(String BusLine, String lat, String lng) {
        RequestParams params = new RequestParams();
        params.put("method", "getnearcarinfov4");
        params.put("sublineid", Integer.valueOf(BusLine));
        params.put("mapType", "BAIDU");
        params.put("citycode", 860515);//这估计是深圳市的代码
        params.put("cn", "gm");
        params.put("posmaptype", "BAIDU");
        /**
         * 传0或者传比现在时间戳有点差距的话，
         * 接口会返回bus的信息,所以这里直接给一个比较大的值，
         * 或者给比当前时间戳稍微小点的值都是可以有效减小数据包大小。
         */
        params.put("lastmodi", System.currentTimeMillis() - 90);//比当前事件戳慢一分半钟
        params.put("lat", Double.valueOf(lat));
        params.put("lat", Double.valueOf(lng));


        BusBaseHttpClient.get("v1/bus/mbcommonservice", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                try {
                    Log.e(TAG, response.getString("success"));
                    if (response != null) {
                        JSONObject data = response.getJSONObject("data");
                        if (data != null) {
                            JSONArray carsArr = data.getJSONArray("cars");
                            for (int i = 0; i < carsArr.length(); i++) {

                            }
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
                // Pull out the first event on the public timeline
                //JSONObject firstEvent = jsonArray.get(0);
                /*String tweetText = firstEvent.getString("text");
                // Do something with the response
                System.out.println(tweetText);*/
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    public void getCommingBusInfo(String url) {
        BusBaseHttpClient.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                try {
                    Log.e(TAG, response.getString("success"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(statusCode==200)
                    httpListener.onSuccess(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                httpListener.onFailed();
            }
        });

    }
}
