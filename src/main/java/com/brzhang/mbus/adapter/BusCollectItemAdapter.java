package com.brzhang.mbus.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tencent.hoollyzhang.mbus.R;
import com.brzhang.mbus.dataobject.Bus;
import com.brzhang.mbus.view.BusActivity;

import java.util.ArrayList;


/**
 * Created by brzhang on 15/5/20.
 * bus 信息
 */
public class BusCollectItemAdapter extends BaseAdapter {
    private Activity       mActivity;
    private ArrayList<Bus> busArrayList;

    public BusCollectItemAdapter(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public int getCount() {
        if (busArrayList != null) {
            return busArrayList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (busArrayList != null) {
            return busArrayList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder;
        if (convertView == null) {
            viewholder = new ViewHolder();
            convertView = mActivity.getLayoutInflater().inflate(R.layout.bus_index_bus_info_item, null);
            viewholder.contentLayout = (LinearLayout) convertView.findViewById(R.id.bus_index_bus_item_content_ll);
            viewholder.busName = (TextView) convertView.findViewById(R.id.bus_index_bus_item_bus_name_tv);
            viewholder.busStation = (TextView) convertView.findViewById(R.id.bus_index_bus_item_bus_station_tv);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        bindClickEvent(viewholder, position);
        fillData(viewholder, position);
        return convertView;
    }

    private void bindClickEvent(ViewHolder viewholder, int position) {
        final Bus bus = busArrayList.get(position);
        if (bus == null) {
            return;
        }
        viewholder.contentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, BusActivity.class);
                intent.putExtra(BusActivity.BUS_INFO,bus);
                mActivity.startActivity(intent);
            }
        });
    }


    private void fillData(ViewHolder viewholder, int position) {
        final Bus bus = busArrayList.get(position);
        if (bus == null) {
            return;
        }
        viewholder.busName.setText(""+bus.getName());
        viewholder.busStation.setText(""+bus.getStationName());
    }

    public ArrayList<Bus> getBusArrayList() {
        return busArrayList;
    }

    public void setBusArrayList(ArrayList<Bus> busArrayList) {
        this.busArrayList = busArrayList;
    }


    class ViewHolder {
        LinearLayout contentLayout;
        TextView     busName;
        TextView     busStation;
    }
}
