package com.brzhang.mbus.listener;

import com.brzhang.mbus.reponse.StationResponse;

/**
 * Created by brzhang on 15/5/24.
 */
public interface StatitionListener extends AsyncListener {
    void onGetListener(StationResponse stationResponse);
}
