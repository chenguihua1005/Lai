package com.softtek.lai.module.grade.view;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.grade.adapter.TutorAdapter;
import com.softtek.lai.module.grade.eventModel.SRInfoEvent;
import com.softtek.lai.module.grade.model.SRInfo;
import com.softtek.lai.module.grade.presenter.GradeImpl;
import com.softtek.lai.module.grade.presenter.IGrade;
import com.softtek.lai.utils.DisplayUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_tutor)
public class TutorActivity extends BaseActivity implements PullToRefreshBase.OnRefreshListener<ListView>,OnClickListener{

    @InjectView(R.id.ptrlv)
    PullToRefreshListView prlv;

    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.tv_left)
    TextView tv_left;

    private IGrade grade;

    List<SRInfo> infos=new ArrayList<>();
    @Override
    protected void initViews() {
        prlv.setOnRefreshListener(this);
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(this,60));
        View view=getLayoutInflater().inflate(R.layout.tutor_lv_header,null);
        view.setLayoutParams(layoutParams);
        prlv.getRefreshableView().addHeaderView(view);
        tv_left.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        tv_title.setText("助教列表");
        tv_right.setText("添加");
        grade=new GradeImpl();
        for(int i=0;i<1;i++){
            SRInfo info=new SRInfo();
            info.setIsInvited("1");
            info.setUserName("张三");
            info.setMobile("11111111111");
            info.setNum("22");
            info.setRtest("100%");
            SRInfo info1=new SRInfo();
            info1.setIsInvited("0");
            info1.setUserName("张三");
            info1.setMobile("11111111111");
            info1.setNum("22");
            info1.setRtest("100%");
            SRInfo info2=new SRInfo();
            info2.setIsInvited("1");
            info2.setUserName("张三");
            info2.setMobile("11111111111");
            info2.setNum("22");
            info2.setRtest("100%");
            SRInfo info3=new SRInfo();
            info3.setIsInvited("1");
            info3.setUserName("张三");
            info3.setMobile("11111111111");
            info3.setNum("22");
            info3.setRtest("100%");
            infos.add(info);
            infos.add(info1);
            infos.add(info2);
            infos.add(info3);
        }
        prlv.setAdapter(new TutorAdapter(this,infos));
    }

    @Override
    public void onRefresh(PullToRefreshBase<ListView> refreshView) {
        grade.getTutorList(1,prlv);

    }

    @Subscribe
    public void onRefreshTutor(SRInfoEvent event){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_left:
                finish();
                break;
        }
    }
}
