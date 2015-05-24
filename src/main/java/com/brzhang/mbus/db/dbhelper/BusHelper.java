package com.brzhang.mbus.db.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by brzhang on 15/5/17.
 */
public class BusHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME    = "bus.db";
    private static final int    DATABASE_VERSION = 1;

    public static final String TABLE_NAME_BUS   = "businfo";
    public static final String ID               = "_id";
    public static final String BUS_NAME         = "name";
    public static final String BUS_STATION_ID = "station_id";
    public static final String BUS_STATION_NAME = "station_name";
    public static final String BUS_LINE         = "line_name";
    public static final String BUS_STATION_LAT  = "bus_station_lat";
    public static final String BUS_STATION_LNG  = "bus_station_lng";
    public static final String BUS_LINE_ID      = "BusLineid";


    public static final String TABLE_NAME_ALL_BUS = "allbusinfo";
    public static final String START_TIME         = "begin_time";
    public static final String END_TIME           = "end_time";
    public static final String START_STATION      = "start_station";
    public static final String END_STATION        = "end_station";
    public static final String IS_OPEN            = "isopen";


    public BusHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public BusHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createBusInfo(db);
        createAllBusInfo(db);
    }

    /**
     * 我收藏的线路
     * @param db
     */
    private void createBusInfo(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("create table if not exists ").append(TABLE_NAME_BUS).append(" (")
                .append(ID).
                append(" INTEGER PRIMARY KEY AUTOINCREMENT, ").
                append(BUS_LINE_ID).append(" char(40), ").
                append(BUS_NAME).append(" char(40), ").
                append(BUS_STATION_ID).append(" varchar(50), ").
                append(BUS_STATION_NAME).append(" varchar(50), ").
                append(BUS_LINE).append(" varchar(50), ").
                append(BUS_STATION_LAT).append(" varchar(50), ").
                append(BUS_STATION_LNG).append(" varchar(50) ").
                append(")");
        db.execSQL(sb.toString());
    }

    /**
     * 所有线路
     */
    private void createAllBusInfo(SQLiteDatabase db){
        StringBuilder sb = new StringBuilder();
        sb.append("create table if not exists ").append(TABLE_NAME_ALL_BUS).append(" (")
                .append(ID).
                append(" INTEGER PRIMARY KEY AUTOINCREMENT, ").
                append(BUS_LINE_ID).append(" char(40), ").
                append(BUS_LINE).append(" varchar(40), ").
                append(START_STATION).append(" varchar(50), ").
                append(END_STATION).append(" varchar(50), ").
                append(START_TIME).append(" varchar(50), ").
                append(END_TIME).append(" varchar(50), ").
                append(IS_OPEN).append(" BOOLEAN ").
                append(")");
        db.execSQL(sb.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
