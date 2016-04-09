/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.grade.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.grade.adapter.LossWeightAdapter;
import com.softtek.lai.module.grade.eventModel.LossWeightEvent;
import com.softtek.lai.module.grade.model.StudentModel;
import com.softtek.lai.module.grade.presenter.GradeImpl;
import com.softtek.lai.module.grade.presenter.IGrade;
import com.softtek.lai.module.studetail.view.StudentDetailActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
@InjectLayout(R.layout.fragment_loss_weight)
public class LossWeightPerFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener<ListView>,
        AdapterView.OnItemClickListener {


    @InjectView(R.id.ptrlv)
    PullToRefreshListView ptrlv;

    private IGrade grade;

    private List<StudentModel> studentModels = new ArrayList<>();
    private LossWeightAdapter adapter;

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
    @Override
    protected void initDatas() {
        classId=getArguments().getString("classId");
        grade = new GradeImpl();
        adapter = new LossWeightAdapter(getContext(), studentModels, Integer.parseInt(Constants.LOSS_WEIGHT_PER));
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateListView(LossWeightEvent event) {
        this.studentModels.clear();
        this.studentModels.addAll(event.getStudents());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onRefresh(PullToRefreshBase<ListView> refreshView) {
        grade.getStudentList(Constants.LOSS_WEIGHT_PER, classId, ptrlv);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        StudentModel studentModel = studentModels.get(position - 1);
        Intent intent = new Intent(getContext(), StudentDetailActivity.class);
        intent.putExtra("userId",studentModel.getAccountId());
        intent.putExtra("classId",studentModel.getClassId());
        startActivity(intent);
    }
}
