/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.jingdu.view;

import android.app.usage.UsageEvents;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ggx.jerryguan.widget_lib.Chart;
import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.jingdu.Adapter.RankAdapter;
import com.softtek.lai.module.jingdu.EventModel.RankEvent;
import com.softtek.lai.module.jingdu.model.RankModel;
import com.softtek.lai.module.jingdu.model.Table1Model;
import com.softtek.lai.module.jingdu.model.Table2Model;
import com.softtek.lai.module.jingdu.presenter.GetProinfoImpl;
import com.softtek.lai.module.jingdu.presenter.IGetProinfopresenter;
import com.softtek.lai.utils.ShareUtils;
import com.umeng.socialize.bean.SocializeConfig;
import com.umeng.socialize.sso.UMSsoHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_jingdu)
public class JingduActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_right)
    TextView tv_right;

    @InjectView(R.id.list_rank)
    ListView list_rank;

    @InjectView(R.id.tv_newban)
    TextView tv_newban;

    @InjectView(R.id.tv_newmem)
    TextView tv_newmem;

    @InjectView(R.id.tv_oneban)
    TextView tv_oneban;

    @InjectView(R.id.tv_twoban)
    TextView tv_twoban;

    @InjectView(R.id.tv_threeban)
    TextView tv_threeban;

    @InjectView(R.id.total_weight)
    Chart total_weight;

    private List<Table1Model> table1ModelList = new ArrayList<Table1Model>();
    private IGetProinfopresenter iGetProinfopresenter;

    private RankModel rank;
    public RankAdapter rankAdapter;

    //当月新开班级数量,新增学员数量
    String newban = "";
    String newmem = "";

    //班级减重
    String oneban;
    String twoban;
    String threeban;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        rankAdapter=new RankAdapter(this,table1ModelList);
        list_rank.setAdapter(rankAdapter);
        ll_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    // Double.parseDouble("160")-Double.parseDouble("120")
    @Override
    protected void initViews() {
        iGetProinfopresenter = new GetProinfoImpl();
        iGetProinfopresenter.getproinfo();
    }

    @Override
    protected void initDatas() {
        tv_right.setText("分享");
    }

    @Subscribe
    public void onEvent(RankModel rank) {
        //Table：教练本月总开班数量，新增学员数量，累计减重数量
        // System.out.println("rankEvent.getRanks()》》》》》》》》》》》》》》" + rank.getTable());
        newban=rank.getTable().get(0).getTotalClass();
        newmem=rank.getTable().get(0).getTotalMember();
        tv_newban.setText(newban);
        tv_newmem.setText(newmem);

        //Table1: 教练所有班级的所有学员减重最多的前10名学员
        table1ModelList=rank.getTable1();
        rankAdapter.updateData(table1ModelList);

        //Table2:各个班本月累计减重
        System.out.println("rankEvent.getRanks()------------------------Table2:" + rank.getTable2());
//       if (rank.getTable2().size()==1){
//
//       }
        Log.i("------------------rank.getTable2().size():----------------"+rank.getTable2().size());
        oneban=rank.getTable2().get(0).getClassId();


        oneban =rank.getTable2().get(0).getLoseWeight();

//        threeban=rank.getTable2().get(2).getLoseWeight();

//        System.out.println("oneban.twoban------------------------Table2:" + rank.getTable2().get(0).getClassId()+"");
        tv_oneban.setText(oneban);
        twoban =rank.getTable2().get(2).getLoseWeight();
        tv_twoban.setText(twoban);
//        tv_threeban.setText("0");

//        float a=Float.parseFloat(oneban);
//        float b=Float.parseFloat(twoban);
//        //float c=Float.parseFloat(threeban);
//        //com.github.snowdream.android.util.Log.i("a="+a+";b="+b+";c="+c);
//        total_weight.setValue(a,b,0);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            //分享功能逻辑
            case R.id.tv_right:
                ShareUtils shareUtils = new ShareUtils(JingduActivity.this);
                shareUtils.setShareContent("康宝莱体重管理挑战赛，坚持只为改变！", "http://www.baidu.com", R.drawable.logo, "我在**天里已累计服务**学员，共帮助他们减重**斤，快来参加体重管理挑战赛吧！", "我在**天里已累计服务**学员，共帮助他们减重**斤，快来参加体重管理挑战赛吧！");
                shareUtils.getController().openShare(JingduActivity.this,false);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("resultCode:" + resultCode);
        UMSsoHandler ssoHandler = SocializeConfig.getSocializeConfig().getSsoHandler(requestCode);
        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
}
