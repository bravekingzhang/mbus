package com.tencent.hoollyzhang.mbus.utils.http;

import org.json.JSONObject;

/**
 * Title: Campus<br>
 * Description: <br>
 * Create DateTime: 2015/1/22 22:28 <br>
 * SVN last modify person: hoollyzhang <br>
 * SVN last modify DateTime: 2015/1/22 时间需+1小时 <br>
 * SVN last version: V1.0.0 <br>
 *
 * @author hoollyzhang
 */
public interface HttpListener {
    public void onSuccess(JSONObject response);
    public void onFailed();
}
