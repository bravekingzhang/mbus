package com.tencent.hoollyzhang.mbus.logic;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.hoollyzhang.hlib.log.LoggerDefault;
import com.tencent.hoollyzhang.mbus.dataobject.Bus;
import com.tencent.hoollyzhang.mbus.dataobject.BusRuningInfo;
import com.tencent.hoollyzhang.mbus.dataobject.OpenBus;
import com.tencent.hoollyzhang.mbus.dataobject.Station;
import com.tencent.hoollyzhang.mbus.db.dbhelper.BusHelper;
import com.tencent.hoollyzhang.mbus.db.dbmanger.DBAllbusManger;
import com.tencent.hoollyzhang.mbus.db.dbmanger.DBbusManger;
import com.tencent.hoollyzhang.mbus.listener.AsyncListener;
import com.tencent.hoollyzhang.mbus.listener.BusListener;
import com.tencent.hoollyzhang.mbus.listener.StatitionListener;
import com.tencent.hoollyzhang.mbus.reponse.BaseResponse;
import com.tencent.hoollyzhang.mbus.reponse.BusResponse;
import com.tencent.hoollyzhang.mbus.reponse.StationResponse;
import com.tencent.hoollyzhang.mbus.utils.Const;
import com.zhy.utils.HttpUtils;
import com.zhy.utils.L;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.srain.cube.request.JsonData;

/**
 * Created by brzhang on 15/5/21.
 */
public class BusLogic extends BaseLogic {
    private Context       context;
    private AsyncListener listener;
    private Bus           mBus;

    public BusLogic(Context context, AsyncListener listener) {
        this.context = context;
        this.listener = listener;
    }

    /**
     * 获取数据库中用户收藏的bus信息
     */
    public void listBus() {
        AsyncTask asyncTask = new AsyncTask<Object, Integer, BusResponse>() {
            @Override
            protected BusResponse doInBackground(Object[] params) {
                BusResponse busResponse = new DBbusManger(context).listBus();
                return busResponse;
            }

            @Override
            protected void onPostExecute(BusResponse busResponse) {
                super.onPostExecute(busResponse);
                if (listener != null && listener instanceof BusListener) {
                    ((BusListener) listener).onGetBusList(busResponse);
                }
            }
        };
        asyncTask.execute();
    }

    /**
     * 添加收藏
     */
    public void insertBus(Bus bus) {
        this.mBus = bus;
        AsyncTask asyncTask = new AsyncTask<Object, Integer, BaseResponse>() {

            @Override
            protected BaseResponse doInBackground(Object... params) {
                BaseResponse baseResponse = new DBbusManger(context).insertBus(mBus);
                baseResponse.setCmd(BaseLogic.CMD_QUERY_COLLECT_BUS);
                baseResponse.setRetCode(0);
                baseResponse.setRetMsg("");
                return baseResponse;
            }

            @Override
            protected void onPostExecute(BaseResponse baseResponse) {
                super.onPostExecute(baseResponse);
                if (listener != null) {
                    listener.onAsyncCallback(baseResponse);
                }
            }
        };
        asyncTask.execute(bus);
    }

    /**
     * 取消收藏
     */
    public void deleteBus(Bus bus) {
        this.mBus = bus;
        AsyncTask asyncTask = new AsyncTask<Object, Integer, BaseResponse>() {

            @Override
            protected BaseResponse doInBackground(Object... params) {
                BaseResponse baseResponse = new DBbusManger(context).deleteBus(mBus);
                baseResponse.setRetCode(0);
                baseResponse.setRetMsg("");
                return baseResponse;
            }

            @Override
            protected void onPostExecute(BaseResponse baseResponse) {
                super.onPostExecute(baseResponse);
                if (listener != null) {
                    listener.onAsyncCallback(baseResponse);
                }
            }
        };
        asyncTask.execute(bus);
    }

    public void initAllopenBusLine(int cityId) {
        AsyncTask asyncTask = new AsyncTask<Object, Integer, BaseResponse>() {
            @Override
            protected BaseResponse doInBackground(Object[] params) {
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setCmd(BaseLogic.CMD_INIT_ALL_OPEN_BUS);
                //step1. get all bus info from internet
                String allBusLineString = HttpUtils.doGet(Const.GET_ALL_LINE + String.valueOf(params[0]));
                try {
                    JSONObject jsonObject = new JSONObject(allBusLineString);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (jsonArray == null || jsonArray.length() == 0) {
                        return baseResponse;
                    }
                    ArrayList<OpenBus> buses = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject line = jsonArray.getJSONObject(i);
                        OpenBus bus = new OpenBus();
                        bus.setBuslineId(line.getString("id"));
                        bus.setBusLine(line.getString(BusHelper.BUS_LINE));
                        bus.setBeginStation(line.getString(BusHelper.START_STATION));
                        bus.setEndStation(line.getString(BusHelper.END_STATION));
                        bus.setStartTime(line.getString(BusHelper.START_TIME));
                        bus.setEndTime(line.getString(BusHelper.END_TIME));
                        bus.setIsOpen(Integer.valueOf(line.getString(BusHelper.IS_OPEN)) == 1);
                        buses.add(bus);
                    }
                    //step2.insert all bus info into db
                    BaseResponse baseResponse1 = new DBAllbusManger(context).insertAllBus(buses);
                    baseResponse.setRetCode(baseResponse1.getRetCode());
                    baseResponse.setRetMsg(baseResponse1.getRetMsg());
                } catch ( Exception e ) {
                    L.e("initAllopenBusLine", e.getMessage());
                }
                return baseResponse;
            }

            @Override
            protected void onPostExecute(BaseResponse baseResponse) {
                super.onPostExecute(baseResponse);
                if (listener != null) {
                    listener.onAsyncCallback(baseResponse);
                }
            }
        };
        asyncTask.execute(cityId);
    }

    public void queryOpenBusLine(String search) {
        AsyncTask asyncTask = new AsyncTask<Object, Integer, BusResponse>() {
            @Override
            protected BusResponse doInBackground(Object[] params) {
                BusResponse busResponse = new DBAllbusManger(context).queryBusByLine(String.valueOf(params[0]));
                return busResponse;
            }

            @Override
            protected void onPostExecute(BusResponse busResponse) {
                super.onPostExecute(busResponse);
                if (listener != null && listener instanceof BusListener) {
                    ((BusListener) listener).onGetSearchBusList(busResponse);
                }
            }
        };
        asyncTask.execute(search);
    }

    public void queryStations(String busLineId) {
        AsyncTask asyncTask = new AsyncTask<Object, Integer, StationResponse>() {
            @Override
            protected StationResponse doInBackground(Object[] params) {
                StationResponse stationResponse = new StationResponse();
                Log.e("get_line_ineo", Const.GET_LINE_INFO.replace("%d", String.valueOf(params[0])));
                String stationJson = HttpUtils.doGet(Const.GET_LINE_INFO.replace("%d", String.valueOf(params[0])));
                if (stationJson == null || !stationJson.contains("station")) {
                    stationResponse.setRetCode(1);
                    stationResponse.setRetMsg("请求失败！");
                    return stationResponse;
                }
                try {
                    JSONObject jsonObject = new JSONObject(stationJson);
                    JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONObject("subline").getJSONArray("station");
                    ArrayList<Station> stations = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); ++i) {
                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                        Station station = new Station();
                        station.setId(jsonObject1.getString("id"));
                        station.setName(jsonObject1.getString("name"));
                        station.setLat(jsonObject1.getString("lat"));
                        station.setLng(jsonObject1.getString("lng"));
                        stations.add(station);
                    }
                    stationResponse.setRetCode(0);
                    stationResponse.setStationArrayList(stations);
                } catch ( Exception e ) {
                    e.printStackTrace();
                }
                return stationResponse;
            }

            @Override
            protected void onPostExecute(StationResponse stationResponse) {
                super.onPostExecute(stationResponse);
                if (listener != null && listener instanceof StatitionListener) {
                    ((StatitionListener) listener).onGetListener(stationResponse);
                }
            }
        };
        asyncTask.execute(busLineId);
    }

    public void getComingBuses(Bus mBus) {
        AsyncTask asyncTask = new AsyncTask<Object, Integer, BusResponse>() {
            @Override
            protected BusResponse doInBackground(Object[] params) {
                BusResponse busResponse = new BusResponse();
                Bus bus = null;
                if (params[0] instanceof Bus) {
                    bus = (Bus) params[0];
                }
                if (bus == null) {
                    busResponse.setRetCode(1);
                    busResponse.setRetMsg("请求失败！");
                    return busResponse;
                }

                Log.e("get_coming_bus",Const.GET_COMING_BUS.replace("%A", bus.getLine()).replace("%B",bus.getStationId()));
                String stationJson = HttpUtils.doGet(Const.GET_COMING_BUS.replace("%A", bus.getLine()).replace("%B",bus.getStationId()));
                if (stationJson == null || !stationJson.contains("data")) {
                    busResponse.setRetCode(1);
                    busResponse.setRetMsg("请求失败！");
                    return busResponse;
                }
                try {
                    JSONObject jsonObject = new JSONObject(stationJson);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    ArrayList<BusRuningInfo> busRuningInfos = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); ++i) {
                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                        BusRuningInfo busRuningInfo = new BusRuningInfo();
                        busRuningInfo.car_name = jsonObject1.getString("name");
                        busRuningInfo.distance = jsonObject1.getString("distance");
                        busRuningInfo.waittime = jsonObject1.getString("waittime");
                        busRuningInfos.add(busRuningInfo);
                    }
                    busResponse.setRetCode(0);
                    busResponse.setBusRuningInfos(busRuningInfos);
                } catch ( Exception e ) {
                    e.printStackTrace();
                }
                return busResponse;
            }

            @Override
            protected void onPostExecute(BusResponse busResponse) {
                super.onPostExecute(busResponse);
                if (listener != null && listener instanceof BusListener) {
                    ((BusListener) listener).ongetBusRuningList(busResponse);
                }
            }
        };
        asyncTask.execute(mBus);
    }
}
