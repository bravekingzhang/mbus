package com.brzhang.mbus.manger;

import android.content.Context;

import com.brzhang.mbus.dataobject.Bus;
import com.brzhang.mbus.listener.AsyncListener;
import com.brzhang.mbus.listener.BusListener;
import com.brzhang.mbus.listener.StatitionListener;
import com.brzhang.mbus.logic.BusLogic;

/**
 * Created by brzhang on 15/5/21.
 */
public class BusManger {
    public static void listBus(Context context, AsyncListener listener) {
        new BusLogic(context, listener).listBus();
    }

    public static void insertBus(Context context,AsyncListener listener,Bus bus){
        new BusLogic(context,listener).insertBus(bus);
    }
    public static void deleteBus(Context context,AsyncListener listener,Bus bus){
        new BusLogic(context,listener).deleteBus(bus);
    }

    public static void initAllopenBusLine(Context context, BusListener listener, int cityId) {
        new BusLogic(context,listener).initAllopenBusLine(cityId);
    }


    public static void queryOpenBus(Context context, BusListener listener, String search) {
        new BusLogic(context,listener).queryOpenBusLine(search);
    }

    public static void QueryStations(Context context, StatitionListener listener, String busLineID) {
        new BusLogic(context,listener).queryStations(busLineID);
    }

    public static void getComingBus(Context context, BusListener listener, Bus mBus) {
        new BusLogic(context,listener).getComingBuses(mBus);
    }
}
