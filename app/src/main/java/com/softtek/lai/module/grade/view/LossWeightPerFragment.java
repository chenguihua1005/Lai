/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.grade.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.grade.adapter.LossWeightPerAdapter;
import com.softtek.lai.module.grade.model.StudentModel;
import com.softtek.lai.module.grade.presenter.LossWeightPerManager;
import com.softtek.lai.module.grade.presenter.StudentListCallback;
import com.softtek.lai.module.studetail.view.StudentDetailActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jerry.guan on 3/21/2016.
 * 减重百分比
 */
@InjectLayout(R.layout.fragment_loss_weight_per)
public class LossWeightPerFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener<ListView>,
        AdapterView.OnItemClickListener,StudentListCallback {


    @InjectView(R.id.ptrlv)
    PullToRefreshListView ptrlv;

    private LossWeightPerManager manager;

    private List<StudentModel> studentModels = new ArrayList<>();
    private LossWeightPerAdapter adapter;

    private static LossWeightPerFragment fragment=null;
    /**
     * 设置一些参数
     * @param params
     * @return
     */
    public static LossWeightPerFragment newInstance( Map<String,String> params) {
        if(fragment==null){
            fragment=new LossWeightPerFragment();
        }
        Bundle args = new Bundle();
        Set<String> keys=params.keySet();
        for(String key:keys){
            args.putString(key,params.get(key));
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews() {
        ptrlv.setOnItemClickListener(this);
    }

    private String classId;
    private String review_flag;
    @Override
    protected void initDatas() {
        classId=getArguments().getString("classId");
        review_flag=getArguments().getString("review");
        manager=new LossWeightPerManager(this);
        adapter = new LossWeightPerAdapter(getContext(), studentModels);
        ptrlv.setAdapter(adapter);
        ptrlv.setOnRefreshListener(this);
        //第一次加载自动刷新
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                ptrlv.setRefreshing();
            }
        }, 500);
    }


    @Override
    public void onRefresh(PullToRefreshBase<ListView> refreshView) {
        manager.loadLossWeightPer(classId);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        StudentModel studentModel = studentModels.get(position - 1);
        if(studentModel.getIsMemberOfAssistant()==1/*||studentModel.getIsTest()==0*/){
            return;
        }
        Intent intent = new Intent(getContext(), StudentDetailActivity.class);
        intent.putExtra("userId",studentModel.getAccountId());
        intent.putExtra("classId",studentModel.getClassId());
        intent.putExtra("review",review_flag);
        startActivity(intent);
    }

    @Override
    public void updataData(List<StudentModel> models) {
        ptrlv.onRefreshComplete();
        try {
            if(models==null||models.isEmpty()){
                return;
            }
            if(!this.studentModels.isEmpty()){
                this.studentModels.clear();
            }
            this.studentModels.addAll(models);
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
