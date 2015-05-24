package com.brzhang.mbus.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.brzhang.mbus.dataobject.Station;
import com.zhy.utils.DensityUtils;

import java.util.ArrayList;

/**
 * Created by brzhang on 15/5/24.
 */
public class StationAdapter extends BaseAdapter {
    private ArrayList<Station> stationArrayList;
    private Activity           mActivity;

    public StationAdapter(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public int getCount() {
        if (stationArrayList != null) {
            return stationArrayList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (stationArrayList != null) {
            return stationArrayList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null) {
            ((TextView) convertView).setText(stationArrayList.get(position).getName() + "");
        } else {
            TextView textView = new TextView(mActivity);
            textView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
            textView.setText("" + stationArrayList.get(position).getName() + "");
            textView.setTextSize(DensityUtils.dp2px(mActivity,10));
            return textView;
        }
        return convertView;
    }

    public ArrayList<Station> getStationArrayList() {
        return stationArrayList;
    }

    public void setStationArrayList(ArrayList<Station> stationArrayList) {
        this.stationArrayList = stationArrayList;
    }

}
