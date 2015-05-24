package com.tencent.hoollyzhang.mbus.listener;

import com.tencent.hoollyzhang.mbus.reponse.BusResponse;

/**
 * Created by brzhang on 15/5/21.
 */
public interface BusListener extends AsyncListener {
    void onGetBusList(BusResponse busResponse);

    void onGetSearchBusList(BusResponse busResponse);

    void ongetBusRuningList(BusResponse busResponse);
}
