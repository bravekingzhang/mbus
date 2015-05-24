package com.tencent.hoollyzhang.mbus.view;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.tencent.hoollyzhang.mbus.R;
import com.tencent.hoollyzhang.mbus.adapter.StationAdapter;
import com.tencent.hoollyzhang.mbus.dataobject.Bus;
import com.tencent.hoollyzhang.mbus.dataobject.Station;
import com.tencent.hoollyzhang.mbus.listener.StatitionListener;
import com.tencent.hoollyzhang.mbus.manger.BusManger;
import com.tencent.hoollyzhang.mbus.reponse.BaseResponse;
import com.tencent.hoollyzhang.mbus.reponse.StationResponse;

import cn.trinea.android.common.util.ToastUtils;

/**
 * Created by brzhang on 15/5/24.
 */
public class AddBusDetailActivity extends SherlockActivity implements StatitionListener, View.OnClickListener {
    public static final String OPEN_BUS_NAME = "openBusName";
    public static final String OPEN_BUS_ID   = "openBusID";
    private String busLineID;
    private String busName;


    private Spinner buslineStationSpinner;

    private StationAdapter stationAdapter;

    private TextView busLineName;

    private TextView add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_bus_detail_acitivity);
        setupView();
        initData();
    }

    private void setupView() {

        buslineStationSpinner = (Spinner) findViewById(R.id.add_bus_detail_line_spinner);
        stationAdapter = new StationAdapter(this);
        buslineStationSpinner.setAdapter(stationAdapter);
        add = (TextView) findViewById(R.id.add_bus_detail_line_add);
        busLineName = (TextView) findViewById(R.id.add_bus_detail_line_name);
        add.setOnClickListener(this);

    }

    private void initData() {
        busName = getIntent().getStringExtra(OPEN_BUS_NAME);
        busLineID = getIntent().getStringExtra(OPEN_BUS_ID);
        if (TextUtils.isEmpty(busLineID)) {
            ToastUtils.show(this, "对不起，你选择的线路有问题！");
            this.finish();
        }
        busLineName.setText(""+busName);
        BusManger.QueryStations(this, this, busLineID);
    }

    @Override
    public void onGetListener(StationResponse stationResponse) {
        if (stationResponse.getStationArrayList() != null) {
            stationAdapter.setStationArrayList(stationResponse.getStationArrayList());
            stationAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onAsyncCallback(BaseResponse baseResponse) {
        if (baseResponse!=null && baseResponse.getRetCode()==0){
            ToastUtils.show(this,"已添加");
            this.finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_bus_detail_line_add:
                addBusLine();
        }
    }

    private void addBusLine() {
        int position = buslineStationSpinner.getSelectedItemPosition();

        Station station = stationAdapter.getStationArrayList().get(position);
        Bus bus = new Bus();
        bus.setName(busName);
        bus.setLine(busLineID);
        bus.setStationId(station.getId());
        bus.setStationLat(station.getLat());
        bus.setStationLng(station.getLng());
        bus.setStationName(station.getName());
        BusManger.insertBus(this, this, bus);//添加到收藏
    }
}
