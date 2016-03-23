package com.softtek.lai.module.grade.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.github.snowdream.android.util.Log;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.grade.adapter.LossWeightAdapter;
import com.softtek.lai.module.grade.adapter.LossWeightPerAdapter;
import com.softtek.lai.module.grade.adapter.PysicalAdapter;
import com.softtek.lai.module.grade.adapter.WaistlineAdapter;
import com.softtek.lai.module.grade.eventModel.LossWeightEvent;
import com.softtek.lai.module.grade.model.Student;
import com.softtek.lai.module.grade.presenter.GradeImpl;
import com.softtek.lai.module.grade.presenter.IGrade;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jerry.guan on 3/21/2016.
 */
@InjectLayout(R.layout.fragment_loss_weight)
public class LossWeightFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener<ListView>{


    @InjectView(R.id.ptrlv)
    PullToRefreshListView ptrlv;

    private IGrade grade;

    private List<Student> students=new ArrayList<>();
    private LossWeightAdapter adapter;
    private int flagType=0;

    public int getFlagType() {
        return flagType;
    }

    public void setFlagType(int flagType) {
        this.flagType = flagType;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        grade=new GradeImpl();
        adapter=new LossWeightAdapter(getContext(),students,flagType);
        ptrlv.setAdapter(adapter);
        ptrlv.setOnRefreshListener(this);
        //第一次加载自动刷新
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                ptrlv.setRefreshing();
            }
        }, 1000);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateListView(LossWeightEvent event){
        this.students.clear();
        this.students.addAll(event.getStudents());
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
        grade.getStudentList(String.valueOf(flagType),"1",ptrlv);
    }
}
