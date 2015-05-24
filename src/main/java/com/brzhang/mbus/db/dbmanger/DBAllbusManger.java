package com.brzhang.mbus.db.dbmanger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.brzhang.mbus.dataobject.OpenBus;
import com.brzhang.mbus.db.dbhelper.BusHelper;
import com.brzhang.mbus.reponse.BaseResponse;
import com.brzhang.mbus.reponse.BusResponse;

import java.util.ArrayList;

/**
 * Created by brzhang on 15/5/21.
 */
public class DBAllbusManger {


    private static final String TAG = "BusAllManger";
    private BusHelper      helper;
    private SQLiteDatabase db;

    public DBAllbusManger(Context context) {
        helper = new BusHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }

    public BusResponse listAllBus() {
        BusResponse busResponse = new BusResponse();
        ArrayList<OpenBus> busArrayList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("select * from ").append(BusHelper.TABLE_NAME_ALL_BUS);
        Cursor c = db.rawQuery(sb.toString(), null);
        while (c.moveToNext()) {
            OpenBus bus = new OpenBus();
            bus.setBuslineId(c.getString(c.getColumnIndex(BusHelper.BUS_LINE_ID)));
            bus.setBusLine(c.getString(c.getColumnIndex(BusHelper.BUS_LINE)));
            bus.setBeginStation(c.getString(c.getColumnIndex(BusHelper.START_STATION)));
            bus.setEndStation(c.getString(c.getColumnIndex(BusHelper.END_STATION)));
            bus.setStartTime(c.getString(c.getColumnIndex(BusHelper.START_TIME)));
            bus.setEndTime(c.getString(c.getColumnIndex(BusHelper.END_TIME)));
            busArrayList.add(bus);
        }
        c.close();
        busResponse.setRetCode(0);
        busResponse.setRetMsg("");
        busResponse.setOpenBusArrayList(busArrayList);
        closedb();
        return busResponse;
    }

    public void closedb() {
        if (db != null)
            db.close();
    }

    public BaseResponse insertAllBus(ArrayList<OpenBus> busArrayList) {
        BaseResponse baseResponse = new BaseResponse();
        if (busArrayList==null || busArrayList.size()==0){
            return baseResponse;
        }
        db.beginTransaction();
        try {
            for (int i=0;i<busArrayList.size();i++){
                OpenBus bus = busArrayList.get(i);
                ContentValues contentValues = new ContentValues();
                contentValues.put(BusHelper.BUS_LINE_ID,bus.getBuslineId());
                contentValues.put(BusHelper.BUS_LINE, bus.getBusLine());
                contentValues.put(BusHelper.START_STATION, bus.getBeginStation());
                contentValues.put(BusHelper.END_STATION, bus.getEndStation());
                contentValues.put(BusHelper.START_TIME, bus.getStartTime());
                contentValues.put(BusHelper.END_TIME, bus.getEndTime());
                contentValues.put(BusHelper.IS_OPEN, bus.isOpen());
                db.insertWithOnConflict(BusHelper.TABLE_NAME_ALL_BUS, "", contentValues, 1);
            }
            db.setTransactionSuccessful();
            baseResponse.setRetCode(0);
            Log.e(TAG, "insert all open bus success ,and insert " + busArrayList.size() + " row");
        } catch ( Exception e ) {
            Log.e(TAG, e.getMessage());
            baseResponse.setRetCode(-1);
            baseResponse.setRetMsg("插入失败！");
        } finally {
            db.endTransaction();
        }
        return baseResponse;
    }

    public BusResponse queryBusByLine(String search) {
        BusResponse busResponse = new BusResponse();
        ArrayList<OpenBus> busArrayList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("select * from ").append(BusHelper.TABLE_NAME_ALL_BUS).append(" where ").
                append(BusHelper.BUS_LINE).
                append(" like ").
                append("'%").append(search).append("%'");
        Cursor c = db.rawQuery(sb.toString(), null);
        while (c.moveToNext()) {
            OpenBus bus = new OpenBus();
            bus.setBuslineId(c.getString(c.getColumnIndex(BusHelper.BUS_LINE_ID)));
            bus.setBusLine(c.getString(c.getColumnIndex(BusHelper.BUS_LINE)));
            bus.setBeginStation(c.getString(c.getColumnIndex(BusHelper.START_STATION)));
            bus.setEndStation(c.getString(c.getColumnIndex(BusHelper.END_STATION)));
            bus.setStartTime(c.getString(c.getColumnIndex(BusHelper.START_TIME)));
            bus.setEndTime(c.getString(c.getColumnIndex(BusHelper.END_TIME)));
            busArrayList.add(bus);
        }
        c.close();
        busResponse.setRetCode(0);
        busResponse.setRetMsg("");
        busResponse.setOpenBusArrayList(busArrayList);
        closedb();
        return busResponse;
    }
}
