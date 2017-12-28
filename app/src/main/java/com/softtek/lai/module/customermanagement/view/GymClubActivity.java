package com.softtek.lai.module.customermanagement.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.bodygame3.more.view.CreateClassActivity;
import com.softtek.lai.module.customermanagement.adapter.GymRecyclerViewAdapter;
import com.softtek.lai.module.customermanagement.model.GymModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jia.lu on 12/27/2017.
 */

public class GymClubActivity extends MakiBaseActivity implements View.OnClickListener{
    private RecyclerView mRcvNotStart;
    private RecyclerView mRcvStart;
    private RecyclerView mRcvEnd;
    private TextView mOpenClass;
    private TextView mOpenUnionClass;

    GymRecyclerViewAdapter gymStartAdapter;
    GymRecyclerViewAdapter gymEndAdapter;
    GymRecyclerViewAdapter gymNotStartAdapter;
    List<GymModel> gymStarts = new ArrayList<>();
    List<GymModel> gymNotStarts = new ArrayList<>();
    List<GymModel> gymEnds = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_club);
        initData();
        initView();
    }

    private void initView() {
        mRcvNotStart = findViewById(R.id.rcv_not_start);
        mRcvStart = findViewById(R.id.rcv_start);
        mRcvEnd = findViewById(R.id.rcv_end);
        mOpenClass = findViewById(R.id.tv_open_class);
        mOpenUnionClass = findViewById(R.id.tv_open_union_class);
        mOpenClass.setOnClickListener(this);
        mOpenUnionClass.setOnClickListener(this);


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
        GymModel gymModel0 = new GymModel();
        gymModel0.setClubName("1111");
        gymModel0.setTime("1111-11-11");
        gymModel0.setType("已开始");
        gymStarts.add(gymModel0);

        GymModel gymModel1 = new GymModel();
        gymModel1.setClubName("2222");
        gymModel1.setTime("2222-22-22");
        gymModel1.setType("已开始");
        gymStarts.add(gymModel1);

        GymModel gymModel2 = new GymModel();
        gymModel2.setClubName("3333");
        gymModel2.setTime("3333-33-33");
        gymModel2.setType("未开始");
        gymNotStarts.add(gymModel2);

        for (int i = 0;i <10;i++) {
            GymModel gymModel3 = new GymModel();
            gymModel3.setClubName("4444");
            gymModel3.setTime("4444-44-44");
            gymModel3.setType("未开始");
            gymNotStarts.add(gymModel3);
        }

        GymModel gymModel4 = new GymModel();
        gymModel4.setClubName("5555");
        gymModel4.setTime("5555-55-55");
        gymModel4.setType("已结束");
        gymEnds.add(gymModel4);

        GymModel gymModel5 = new GymModel();
        gymModel5.setClubName("6666");
        gymModel5.setTime("6666-66-66");
        gymModel5.setType("已结束");
        gymEnds.add(gymModel5);
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
        }
    }



}
