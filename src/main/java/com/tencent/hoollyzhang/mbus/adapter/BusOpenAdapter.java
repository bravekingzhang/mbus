package com.tencent.hoollyzhang.mbus.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tencent.hoollyzhang.mbus.R;
import com.tencent.hoollyzhang.mbus.dataobject.OpenBus;
import com.tencent.hoollyzhang.mbus.view.AddBusDetailActivity;

import java.util.ArrayList;

/**
 * Created by brzhang on 15/5/24.
 */
public class BusOpenAdapter extends BaseAdapter {

    private ArrayList<OpenBus> buses;
    private Activity           mActivity;

    public BusOpenAdapter(Activity activity) {
        this.mActivity = activity;
    }

    public ArrayList<OpenBus> getBuses() {
        return buses;
    }

    public void setBuses(ArrayList<OpenBus> buses) {
        this.buses = buses;
    }


    @Override
    public int getCount() {
        if (buses != null)
            return buses.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (buses != null) {
            return buses.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mActivity.getLayoutInflater().inflate(R.layout.open_bus_item, null);
            viewHolder.busLine = (TextView) convertView.findViewById(R.id.open_bus_line_name);
            viewHolder.busStationDes = (TextView) convertView.findViewById(R.id.open_bus_line_station_des);
            viewHolder.busTimeDes = (TextView) convertView.findViewById(R.id.open_bus_line_time_des);
            viewHolder.addBus = (TextView) convertView.findViewById(R.id.open_bus_line_add);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        bindClickEvient(viewHolder,position);
        fillData(viewHolder,position);

        return convertView;
    }

    private void bindClickEvient(ViewHolder viewHolder, int position) {
        final OpenBus bus = buses.get(position);
        if (bus==null){
            return;
        }
        viewHolder.addBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity,AddBusDetailActivity.class);
                intent.putExtra(AddBusDetailActivity.OPEN_BUS_ID,bus.getBuslineId());
                intent.putExtra(AddBusDetailActivity.OPEN_BUS_NAME,bus.getBusLine());
                mActivity.startActivityForResult(intent,1);
            }
        });

    }

    private void fillData(ViewHolder viewHolder, int position) {
        OpenBus bus = buses.get(position);
        if (bus==null){
            return;
        }
        viewHolder.busLine.setText(bus.getBusLine()+"");
        viewHolder.busStationDes.setText(bus.getBeginStation()+" -- "+bus.getEndStation());
        viewHolder.busTimeDes.setText(bus.getStartTime()+" -- "+bus.getEndTime());
    }

    class ViewHolder{
        TextView busLine;
        TextView busStationDes;
        TextView busTimeDes;
        TextView addBus;
    }
}
