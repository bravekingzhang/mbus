package com.tencent.hoollyzhang.mbus.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tencent.hoollyzhang.mbus.R;
import com.tencent.hoollyzhang.mbus.dataobject.BusRuningInfo;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Title: Campus<br>
 * Description: <br>
 * Create DateTime: 2015/1/22 20:26 <br>
 * SVN last modify person: hoollyzhang <br>
 * SVN last modify DateTime: 2015/1/22 时间需+1小时 <br>
 * SVN last version: V1.0.0 <br>
 *
 * @author hoollyzhang
 */
public class BusComingAdapter extends BaseAdapter {

    private int mResource = R.layout.comming_bus_item;
    private Context mContext;

    private List<BusRuningInfo> busInfoList;

    public BusComingAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        if (busInfoList != null) {
            Log.e("hoolly", "adapter size is " + busInfoList.size());
            return busInfoList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        if (busInfoList != null) {
            return busInfoList.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(mContext).inflate(mResource, viewGroup, false);
        TextView coming_bus_tv_name = (TextView) view.findViewById(R.id.coming_bus_tv_name);
        TextView comming_bus_distance_tv = (TextView) view.findViewById(R.id.comming_bus_distance);
        TextView comming_bus_time_tv = (TextView) view.findViewById(R.id.comming_bus_time);
        coming_bus_tv_name.setText(busInfoList.get(i).car_name);
        comming_bus_distance_tv.setText(busInfoList.get(i).distance);
        comming_bus_time_tv.setText(getTimeDistance(busInfoList.get(i).waittime));
        return view;
    }

    public List<BusRuningInfo> getBusInfoList() {
        return busInfoList;
    }

    public void setBusInfoList(List<BusRuningInfo> busInfoList) {
        this.busInfoList = busInfoList;
    }

    private String  getTimeDistance(String milll){
        int timpstamp =  Integer.valueOf(milll);
        int miao = timpstamp % 60;
        int miniute = timpstamp/60;
        SimpleDateFormat ss=new SimpleDateFormat("ss");
        return miniute+"分"+miao+"秒";

    }

}
