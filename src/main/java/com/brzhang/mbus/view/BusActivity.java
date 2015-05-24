package com.brzhang.mbus.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.tencent.hoollyzhang.mbus.R;
import com.brzhang.mbus.adapter.BusComingAdapter;
import com.brzhang.mbus.dataobject.Bus;
import com.brzhang.mbus.listener.BusListener;
import com.brzhang.mbus.manger.BusManger;
import com.brzhang.mbus.reponse.BaseResponse;
import com.brzhang.mbus.reponse.BusResponse;

import cn.trinea.android.common.util.ToastUtils;

/**
 * create by brzhang
 */
public class BusActivity extends SherlockActivity implements BusListener{

    public static final String BUS_INFO = "busInfo";
    private ListView         busComingListview;
    private BusComingAdapter busComingAdapter;
    private Bus              mBus;
    private TextView busLineName;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.bus_coming_bus_activity);
        Intent intent = getIntent();
        mBus = (Bus) intent.getSerializableExtra(BUS_INFO);
        if (mBus == null) {
            ToastUtils.show(this, getString(R.string.choose_bus_error));
            this.finish();
        }
        initView();
        initDatas();
    }

    private void initDatas() {
        busComingAdapter = new BusComingAdapter(this);
        busComingListview.setAdapter(busComingAdapter);
        refreshComingBus();
    }

    private void initView() {
        busComingListview = (ListView) findViewById(R.id.bus_coming_bus);
        findViewById(R.id.bus_coming_bus_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshComingBus();
            }
        });
        busLineName = (TextView) findViewById(R.id.bus_coming_bus_name);
        busLineName.setText(""+mBus.getName()+"  "+mBus.getStationName());
    }

    /**
     * 刷新bus来临信息
     */
    private void refreshComingBus() {
        BusManger.getComingBus(this,this,mBus);
    }

    @Override
    public void onGetBusList(BusResponse busResponse) {

    }

    @Override
    public void onGetSearchBusList(BusResponse busResponse) {

    }

    @Override
    public void ongetBusRuningList(BusResponse busResponse) {
        if (busResponse!=null && busResponse.getBusRuningInfos()!=null && busResponse.getRetCode()==0){
            busComingAdapter.setBusInfoList(busResponse.getBusRuningInfos());
            busComingAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onAsyncCallback(BaseResponse baseResponse) {

    }
}
