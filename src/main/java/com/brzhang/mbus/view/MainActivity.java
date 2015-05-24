package com.brzhang.mbus.view;

import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;
import com.brzhang.mbus.BusApplication;
import com.tencent.hoollyzhang.mbus.R;
import com.brzhang.mbus.adapter.BusCollectItemAdapter;
import com.brzhang.mbus.listener.BusListener;
import com.brzhang.mbus.logic.BaseLogic;
import com.brzhang.mbus.manger.BusManger;
import com.brzhang.mbus.reponse.BaseResponse;
import com.brzhang.mbus.reponse.BusResponse;
import com.brzhang.mbus.utils.Const;
import com.zhy.utils.NetUtils;

import cn.trinea.android.common.util.PreferencesUtils;
import cn.trinea.android.common.util.ToastUtils;
import in.srain.cube.views.GridViewWithHeaderAndFooter;


public class MainActivity extends SherlockActivity implements BusListener {

    private GridViewWithHeaderAndFooter mGridView;
    private BusCollectItemAdapter       busCollectItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupView();
        initData();

    }

    private void setupView() {
        mGridView = (GridViewWithHeaderAndFooter) findViewById(R.id.bus_index_grid_list);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!NetUtils.isConnected(this)) {
            ToastUtils.show(this, "好像没有网络连接诶！");
            return;
        }
        BusManger.listBus(this, this);
    }

    private void initData() {
        busCollectItemAdapter = new BusCollectItemAdapter(this);
        mGridView.setAdapter(busCollectItemAdapter);
        if (!PreferencesUtils.getBoolean(this, BusApplication.isNotFristRun)) {
            BusManger.initAllopenBusLine(this, this, Const.CITY_ID_OF_SHENZHEN);
        } else {
            BusManger.listBus(this, this);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
        super.onCreateOptionsMenu(menu);
        getSupportMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
        switch (item.getItemId()) {//得到被点击的item的itemId
            case R.id.action_add:
                Intent intent = new Intent(this, AddBusLineActivity.class);
                startActivityForResult(intent, AddBusLineActivity.ADD_BUS);
                break;
            case R.id.action_about:
                Intent intent1 = new Intent(this, AboutActivity.class);
                startActivity(intent1);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGetBusList(BusResponse busResponse) {
        if (busResponse.getRetCode() == 0 && busResponse.getBusArrayList() != null &&
                busResponse.getBusArrayList().size() > 0) {
            busCollectItemAdapter.setBusArrayList(busResponse.getBusArrayList());
        }
    }

    @Override
    public void onGetSearchBusList(BusResponse busResponse) {

    }

    @Override
    public void ongetBusRuningList(BusResponse busResponse) {

    }

    @Override
    public void onAsyncCallback(BaseResponse baseResponse) {
        if (baseResponse.getCmd()==BaseLogic.CMD_INIT_ALL_OPEN_BUS){
            if (baseResponse.getRetCode()==0){//如果已经写入成功，那么后续就使用db中的所以开通的bus，否则，我们在次启动继续尝试写入db中。
                PreferencesUtils.putBoolean(this,BusApplication.isNotFristRun,true);
            }
            BusManger.listBus(this, this);
        }else if (baseResponse.getCmd() ==BaseLogic.CMD_QUERY_COLLECT_BUS){

        }
    }
}
