package com.brzhang.mbus.reponse;

import com.brzhang.mbus.dataobject.Station;

import java.util.ArrayList;

/**
 * Created by brzhang on 15/5/24.
 */
public class StationResponse extends BaseResponse{
    private ArrayList<Station> stationArrayList;

    public ArrayList<Station> getStationArrayList() {
        return stationArrayList;
    }

    public void setStationArrayList(ArrayList<Station> stationArrayList) {
        this.stationArrayList = stationArrayList;
    }
}
