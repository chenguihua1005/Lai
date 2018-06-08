package com.softtek.lai.module.customermanagement.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.customermanagement.adapter.HistoryRecyclerViewAdapter;
import com.softtek.lai.module.healthyreport.model.HistoryDataModel;
import com.softtek.lai.module.healthyreport.net.HistoryDataService;
import com.softtek.lai.utils.RequestCallback;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jia.lu on 1/11/2018.
 */

public class SelectHistoryActivity extends MakiBaseActivity implements View.OnClickListener {

    private LinearLayout mBack;
    private TextView mTitle;
    private RecyclerView mRecyclerView;
    private HistoryRecyclerViewAdapter adapter;
    private List<HistoryDataModel.RecordsBean> historyList = new ArrayList<>();
    private HistoryDataService service;
    private long accountId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        service = ZillaApi.NormalRestAdapter.create(HistoryDataService.class);
        setContentView(R.layout.activity_select_history);
        initView();
        initData();
    }

    private void initView(){
        mBack = findViewById(R.id.ll_left);
        mTitle = findViewById(R.id.tv_title);
        mBack.setOnClickListener(this);
        mTitle.setText("历史数据");
        mRecyclerView = findViewById(R.id.rcv_content);
        adapter = new HistoryRecyclerViewAdapter(historyList, new HistoryRecyclerViewAdapter.ItemListener() {
            @Override
            public void onItemClick(HistoryDataModel.RecordsBean item) {
                Intent intent = getIntent();
                intent.putExtra("weight",item.getWeight());
                intent.putExtra("bodyFat",item.getBodyFatRate());
                intent.putExtra("internalFat",item.getViscusFatIndex());
                setResult(RESULT_OK,intent);
                finish();
            }
        },this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
    }

    private void initData(){
        accountId = getIntent().getLongExtra("accountId",0);
//        accountId = UserInfoModel.getInstance().getUserId();
        historyList.clear();
        service.getHistoryDataList(UserInfoModel.getInstance().getToken(), 1, accountId, 1, new RequestCallback<ResponseData<HistoryDataModel>>() {
            @Override
            public void success(ResponseData<HistoryDataModel> data, Response response) {
                if (data.getStatus() == 200){
                    historyList.addAll(data.getData().getRecords());
                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(SelectHistoryActivity.this,data.getMsg(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
//                super.failure(error);
                dealNetError(error);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_left:
                finish();
                break;
        }
    }
}
