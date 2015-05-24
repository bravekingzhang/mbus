package com.tencent.hoollyzhang.mbus.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.tencent.hoollyzhang.mbus.R;
import com.tencent.hoollyzhang.mbus.utils.Const;
import com.tencent.hoollyzhang.mbus.adapter.BusComingAdapter;
import com.tencent.hoollyzhang.mbus.dataobject.BusRuningInfo;
import com.tencent.hoollyzhang.mbus.utils.http.BusClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author brzhang
 */
public class BusFragment extends Fragment{
    public static final String BUNDLE_BUS_LINE = "bus_line";
    private             String mBusLine        = "10000";
    private List<BusRuningInfo> busInfoList;
    private BusComingAdapter    busComingAdapter;
    private ListView            listview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bus, container, false);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mBusLine = arguments.getString(BUNDLE_BUS_LINE, "-1");
        }
        listview = (ListView) view.findViewById(R.id.mbus_coming_bus_lv);
        busComingAdapter = new BusComingAdapter(getActivity());
        initData();
        listview.setAdapter(busComingAdapter);
        return view;
    }

    private void initData() {
        busInfoList = new ArrayList<>();
        get_comming_bus_info(mBusLine);
    }


    public static BusFragment newInstance(String mBusLine) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_BUS_LINE, mBusLine);
        BusFragment busFragment = new BusFragment();
        busFragment.setArguments(bundle);
        return busFragment;
    }


    private void get_comming_bus_info(String mBusLine) {
       /* BusClient busClient = new BusClient();
        String url = "v1/bus/mbcommonservice?" +
                "method=getnearcarinfov4" +
                "&sublineid=" +mBusLine+
                "&mapType=BAIDU" +
                "&citycode=860515" +
                "&cn=gm" +
                "&posmaptype=BAIDU" +
                "&lastmodi=" +(System.currentTimeMillis()-90)+ Const.BUS_LOCATION.get(mBusLine);
        busClient.getCommingBusInfo(url);*/
    }

    public void onSuccess(JSONObject response) {
        busInfoList.clear();
        Log.e("hoolly","onSuccess busCommingInfoList size is "+ busInfoList.size());
        try{
            if (response != null) {
                JSONObject data = response.getJSONObject("data");
                if (data != null) {
                    JSONArray carsArr = data.getJSONArray("cars");
                    for (int i = 0; i < carsArr.length(); i++) {
                        JSONObject car = carsArr.getJSONObject(i);
                        if(car != null){
                            BusRuningInfo busInfo = new BusRuningInfo();
                            busInfo.car_name = car.getString("name");
                            busInfo.distance = car.getString("distance");
                            busInfo.waittime = car.getString("waittime");
                            busInfoList.add(busInfo);
                        }
                    }
                }
            }
            Log.e("hoolly","onSuccess finish busCommingInfoList size is "+ busInfoList.size());
            //谁最懂数据准备好了
            busComingAdapter.setBusInfoList(busInfoList);
            busComingAdapter.notifyDataSetChanged();
            if(busInfoList.size()==0){
                Toast.makeText(getActivity(),"哎呀，现在还没有发车，在等等吧",Toast.LENGTH_LONG).show();
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
