package com.tencent.hoollyzhang.mbus.db.dbmanger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tencent.hoollyzhang.mbus.dataobject.Bus;
import com.tencent.hoollyzhang.mbus.db.dbhelper.BusHelper;
import com.tencent.hoollyzhang.mbus.reponse.BaseResponse;
import com.tencent.hoollyzhang.mbus.reponse.BusResponse;

import java.util.ArrayList;

/**
 * Created by brzhang on 15/5/21.
 */
public class DBbusManger {


    private static final String TAG = "BusManger";
    private BusHelper      helper;
    private SQLiteDatabase db;

    public DBbusManger(Context context) {
        helper = new BusHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }

    public BusResponse listBus() {
        BusResponse busResponse = new BusResponse();
        ArrayList<Bus> busArrayList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("select * from ").append(BusHelper.TABLE_NAME_BUS);
        Cursor c = db.rawQuery(sb.toString(), null);
        while (c.moveToNext()) {
            Bus bus = new Bus();
            bus.setName(c.getString(c.getColumnIndex(BusHelper.BUS_NAME)));
            bus.setLine(c.getString(c.getColumnIndex(BusHelper.BUS_LINE)));
            bus.setStationId(c.getString(c.getColumnIndex(BusHelper.BUS_STATION_ID)));
            bus.setStationName(c.getString(c.getColumnIndex(BusHelper.BUS_STATION_NAME)));
            bus.setStationLat(c.getString(c.getColumnIndex(BusHelper.BUS_STATION_LAT)));
            bus.setStationLng(c.getString(c.getColumnIndex(BusHelper.BUS_STATION_LNG)));
            busArrayList.add(bus);
        }
        c.close();
        busResponse.setRetCode(0);
        busResponse.setRetMsg("");
        busResponse.setBusArrayList(busArrayList);
        closedb();
        return busResponse;
    }

    public void closedb() {
        if (db != null)
            db.close();
    }

    public BaseResponse insertBus(Bus bus) {
        BaseResponse baseResponse = new BaseResponse();
        db.beginTransaction();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(BusHelper.BUS_NAME, bus.getName());
            contentValues.put(BusHelper.BUS_LINE, bus.getLine());
            contentValues.put(BusHelper.BUS_STATION_ID,bus.getStationId());
            contentValues.put(BusHelper.BUS_STATION_NAME, bus.getStationName());
            contentValues.put(BusHelper.BUS_STATION_LAT, String.valueOf(bus.getStationLat()));
            contentValues.put(BusHelper.BUS_STATION_LNG, String.valueOf(bus.getStationLng()));
            db.insertWithOnConflict(BusHelper.TABLE_NAME_BUS, "", contentValues, 1);
            db.setTransactionSuccessful();
            baseResponse.setRetCode(0);
        } catch ( Exception e ) {
            Log.e(TAG, e.getMessage());
            baseResponse.setRetCode(-1);
            baseResponse.setRetMsg("插入失败！");
        } finally {
            db.endTransaction();
        }
        return baseResponse;
    }

    public BaseResponse deleteBus(Bus bus) {
        BaseResponse baseResponse = new BaseResponse();
        db.beginTransaction();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("delete from ").append(BusHelper.TABLE_NAME_BUS).
                    append(" where ")
                    .append(BusHelper.BUS_LINE).append(" = ").append("'").append(bus.getLine()).append("'");
            Log.e(TAG, "execu sql [" + sb.toString() + "]");
            db.execSQL(sb.toString());
            db.setTransactionSuccessful();
            baseResponse.setRetCode(0);
            baseResponse.setRetMsg("");
        } catch ( Exception e ) {
            Log.e(TAG, e.getMessage());
            baseResponse.setRetCode(-1);
            baseResponse.setRetMsg("删除失败");
        } finally {
            db.endTransaction();
        }
        return baseResponse;
    }
}
