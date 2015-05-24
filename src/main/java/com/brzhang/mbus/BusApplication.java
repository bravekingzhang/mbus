package com.brzhang.mbus;

import android.app.Application;

/**
 * Created by brzhang on 15/5/24.
 */
public class BusApplication extends Application {

    public static String isNotFristRun = "isfirstrun";

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
