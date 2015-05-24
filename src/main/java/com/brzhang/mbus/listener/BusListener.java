package com.brzhang.mbus.listener;

import com.brzhang.mbus.reponse.BusResponse;

/**
 * Created by brzhang on 15/5/21.
 */
public interface BusListener extends AsyncListener {
    void onGetBusList(BusResponse busResponse);

    void onGetSearchBusList(BusResponse busResponse);

    void ongetBusRuningList(BusResponse busResponse);
}
