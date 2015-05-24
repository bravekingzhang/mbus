package com.brzhang.mbus.dataobject;

import java.io.Serializable;

/**
 * 用户个人收藏的bus
 * Created by brzhang on 15/5/20.
 */
public class Bus implements Serializable{
    private String name;
    private String line;
    private String stationId;
    private String stationName;

    public String getStationLng() {
        return stationLng;
    }

    public void setStationLng(String stationLng) {
        this.stationLng = stationLng;
    }

    public String getStationLat() {
        return stationLat;
    }

    public void setStationLat(String stationLat) {
        this.stationLat = stationLat;
    }

    private String stationLat;
    private String stationLng;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }


    @Override
    public String toString() {
        return "Bus{" +
                "name='" + name + '\'' +
                ", line='" + line + '\'' +
                ", stationName='" + stationName + '\'' +
                ", stationLat=" + stationLat +
                ", stationLng=" + stationLng +
                '}';
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }
}
