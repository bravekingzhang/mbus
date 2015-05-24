package com.tencent.hoollyzhang.mbus.listener;

import com.tencent.hoollyzhang.mbus.reponse.StationResponse;

/**
 * Created by brzhang on 15/5/24.
 */
public interface StatitionListener extends AsyncListener {
    void onGetListener(StationResponse stationResponse);
}
