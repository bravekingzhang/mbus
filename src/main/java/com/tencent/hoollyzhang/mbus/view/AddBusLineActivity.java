package com.tencent.hoollyzhang.mbus.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.actionbarsherlock.app.SherlockActivity;
import com.tencent.hoollyzhang.mbus.R;
import com.tencent.hoollyzhang.mbus.adapter.BusOpenAdapter;
import com.tencent.hoollyzhang.mbus.listener.BusListener;
import com.tencent.hoollyzhang.mbus.manger.BusManger;
import com.tencent.hoollyzhang.mbus.reponse.BaseResponse;
import com.tencent.hoollyzhang.mbus.reponse.BusResponse;

/**
 * Created by brzhang on 15/5/21.
 */
public class AddBusLineActivity extends SherlockActivity implements BusListener{
    public static final int ADD_BUS = 1;

    private ViewFlipper searchFlipper;
    private ListView buslineListview;
    private BusOpenAdapter openAdapter;
    private EditText serachContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_addbusline_activity);
        setupView();
        initData();
    }

    private void setupView() {
        setTitle("添加公交线路");
        searchFlipper = (ViewFlipper) findViewById(R.id.bus_serach_line_vf);
        searchFlipper.setDisplayedChild(0);
        buslineListview = (ListView) findViewById(R.id.bus_search_line_listview);
        serachContent  = (EditText) findViewById(R.id.search_body);
        serachContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s))
                    BusManger.queryOpenBus(AddBusLineActivity.this,AddBusLineActivity.this,s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        findViewById(R.id.bus_search_init_rl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchFlipper.setDisplayedChild(1);//显示搜索框
            }
        });

    }

    private void initData() {
        openAdapter = new BusOpenAdapter(this);
        buslineListview.setAdapter(openAdapter);

    }


    @Override
    public void onGetBusList(BusResponse busResponse) {

    }

    @Override
    public void onGetSearchBusList(BusResponse busResponse) {
        if (busResponse.getRetCode()==0 && busResponse.getOpenBusArrayList()!=null){
            openAdapter.setBuses(busResponse.getOpenBusArrayList());
            openAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void ongetBusRuningList(BusResponse busResponse) {

    }

    @Override
    public void onAsyncCallback(BaseResponse baseResponse) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
