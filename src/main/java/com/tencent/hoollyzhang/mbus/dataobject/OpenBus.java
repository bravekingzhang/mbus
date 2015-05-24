package com.tencent.hoollyzhang.mbus.dataobject;

import java.io.Serializable;

/**
 * Created by brzhang on 15/5/24.
 */
public class OpenBus implements Serializable{
    private String buslineId;
    private String busLine;
    private String startTime;
    private String endTime;
    private String beginStation;
    private String endStation;
    private boolean isOpen;

    public String getBusLine() {
        return busLine;
    }

    public void setBusLine(String busLine) {
        this.busLine = busLine;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getBeginStation() {
        return beginStation;
    }

    public void setBeginStation(String beginStation) {
        this.beginStation = beginStation;
    }

    public String getEndStation() {
        return endStation;
    }

    public void setEndStation(String endStation) {
        this.endStation = endStation;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    @Override
    public String toString() {
        return "OpenBus{" +
                "busLine='" + busLine + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", beginStation='" + beginStation + '\'' +
                ", endStation='" + endStation + '\'' +
                ", isOpen=" + isOpen +
                '}';
    }

    public String getBuslineId() {
        return buslineId;
    }

    public void setBuslineId(String buslineId) {
        this.buslineId = buslineId;
    }
}
