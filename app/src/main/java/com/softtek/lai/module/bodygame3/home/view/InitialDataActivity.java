package com.softtek.lai.module.bodygame3.home.view;

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
import com.softtek.lai.module.bodygame3.activity.adapter.InitialDataRecyclerAdapter;
import com.softtek.lai.module.bodygame3.activity.model.InitialDataModel;
import com.softtek.lai.module.bodygame3.activity.net.ActivityService;
import com.softtek.lai.module.customermanagement.view.MakiBaseActivity;
import com.softtek.lai.utils.RequestCallback;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jia.lu on 5/15/2018.
 */

public class InitialDataActivity extends MakiBaseActivity implements View.OnClickListener {
    private LinearLayout mBack;
    private RecyclerView mRecyclerView;
    private TextView mTitle;
    private InitialDataRecyclerAdapter adapter;
    private List<InitialDataModel> datas = new ArrayList<>();
    private String classId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_data);
        classId = getIntent().getStringExtra("classId");
        initView();
        initData();
    }

    private void initView() {
        mBack = findViewById(R.id.ll_left);
        mRecyclerView = findViewById(R.id.rcv_content);
        mTitle = findViewById(R.id.tv_title);
        mTitle.setText("初始数据");
        mBack.setOnClickListener(this);
        adapter = new InitialDataRecyclerAdapter(datas, new InitialDataRecyclerAdapter.ItemListener() {
            @Override
            public void onItemClick(InitialDataModel item) {
                Intent intent = new Intent(InitialDataActivity.this, InitialDetailActivity.class);
                intent.putExtra("classId", classId);
                intent.putExtra("phone",item.getMobile());
                intent.putExtra("accountId",item.getAccountId());
                intent.putExtra("userName",item.getUserName());
                startActivity(intent);
            }
        }, this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
    }

    private void initData() {
        datas.clear();
        if (classId == null){
            return;
        }
        dialogShow("数据加载中...");
        ZillaApi.NormalRestAdapter.create(ActivityService.class).getInitialData(UserInfoModel.getInstance().getToken(), classId, new RequestCallback<ResponseData<List<InitialDataModel>>>() {
            @Override
            public void success(ResponseData<List<InitialDataModel>> responseData, Response response) {
                dialogDismiss();
                if (responseData.getStatus() == 200) {
                    datas.addAll(responseData.getData());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(InitialDataActivity.this, responseData.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                dialogDismiss();
                super.failure(retrofitError);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
        }
    }
}
