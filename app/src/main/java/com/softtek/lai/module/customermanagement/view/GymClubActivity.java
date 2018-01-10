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
import com.softtek.lai.module.bodygame3.more.view.CreateClassActivity;
import com.softtek.lai.module.customermanagement.adapter.GymRecyclerViewAdapter;
import com.softtek.lai.module.customermanagement.model.GymModel;
import com.softtek.lai.module.customermanagement.service.GymClubService;
import com.softtek.lai.utils.RequestCallback;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jia.lu on 12/27/2017.
 */

public class GymClubActivity extends MakiBaseActivity implements View.OnClickListener{
    private RecyclerView mRcvNotStart;
    private RecyclerView mRcvStart;
    private RecyclerView mRcvEnd;
    private TextView mOpenClass;
    private TextView mOpenUnionClass;
    private TextView mTitle;
    private LinearLayout mBack;

    GymRecyclerViewAdapter gymStartAdapter;
    GymRecyclerViewAdapter gymEndAdapter;
    GymRecyclerViewAdapter gymNotStartAdapter;
    List<GymModel> gymStarts = new ArrayList<>();
    List<GymModel> gymNotStarts = new ArrayList<>();
    List<GymModel> gymEnds = new ArrayList<>();
    GymClubService service;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        service = ZillaApi.create(GymClubService.class);
        setContentView(R.layout.activity_gym_club);
        initView();
    }

    @Override
    protected void onResume() {
        initData();
        super.onResume();
    }

    private void initView() {
        mRcvNotStart = findViewById(R.id.rcv_not_start);
        mRcvStart = findViewById(R.id.rcv_start);
        mRcvEnd = findViewById(R.id.rcv_end);
        mOpenClass = findViewById(R.id.tv_open_class);
        mOpenUnionClass = findViewById(R.id.tv_open_union_class);
        mTitle = findViewById(R.id.tv_title);
        mBack = findViewById(R.id.ll_left);
        mTitle.setText("体管班");
        mOpenClass.setOnClickListener(this);
        mOpenUnionClass.setOnClickListener(this);
        mBack.setOnClickListener(this);


        gymNotStartAdapter = new GymRecyclerViewAdapter(gymNotStarts, new GymRecyclerViewAdapter.ItemListener() {
            @Override
            public void onItemClick(GymModel item) {

            }
        },this);
        gymStartAdapter = new GymRecyclerViewAdapter(gymStarts, new GymRecyclerViewAdapter.ItemListener() {
            @Override
            public void onItemClick(GymModel item) {

            }
        },this);
        gymEndAdapter = new GymRecyclerViewAdapter(gymEnds, new GymRecyclerViewAdapter.ItemListener() {
            @Override
            public void onItemClick(GymModel item) {

            }
        },this);

        RecyclerView.LayoutManager managerNotStart = new LinearLayoutManager(this);
        mRcvNotStart.setAdapter(gymNotStartAdapter);
        mRcvNotStart.setLayoutManager(managerNotStart);

        RecyclerView.LayoutManager managerStart = new LinearLayoutManager(this);
        mRcvStart.setAdapter(gymStartAdapter);
        mRcvStart.setLayoutManager(managerStart);

        RecyclerView.LayoutManager managerEnd = new LinearLayoutManager(this);
        mRcvEnd.setAdapter(gymEndAdapter);
        mRcvEnd.setLayoutManager(managerEnd);
    }

    private void initData() {
        dialogShow("加载中...");
        gymStarts.clear();
        gymNotStarts.clear();
        gymEnds.clear();
        service.getClubClasses(UserInfoModel.getInstance().getToken(), new RequestCallback<ResponseData<List<GymModel>>>() {
            @Override
            public void success(ResponseData<List<GymModel>> listResponseData, Response response) {
                dialogDismiss();
                if (listResponseData.getStatus() == 200) {
                    for (int i = 0; i < listResponseData.getData().size();i++ ){
                        if (listResponseData.getData().get(i).getStatus().equals("未开始")){
                            gymNotStarts.add(listResponseData.getData().get(i));
                        } else if (listResponseData.getData().get(i).getStatus().equals("进行中")) {
                            gymStarts.add(listResponseData.getData().get(i));
                        }else if (listResponseData.getData().get(i).getStatus().equals("已结束")){
                            gymEnds.add(listResponseData.getData().get(i));
                        }
                    }
                    gymNotStartAdapter.notifyDataSetChanged();
                    gymStartAdapter.notifyDataSetChanged();
                    gymEndAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(GymClubActivity.this,listResponseData.getMsg(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                dialogDismiss();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_open_class:
                Intent openClassIntent = new Intent(this,CreateClassActivity.class);
                startActivity(openClassIntent);
                break;
            case R.id.tv_open_union_class:
                Intent unionIntent = new Intent(this,CreateUnionClassActivity.class);
                startActivity(unionIntent);
                break;
            case R.id.ll_left:
                finish();
                break;
        }
    }



}
