/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.jingdu.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.InjectView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.jingdu.Adapter.RankAdapter;
import com.softtek.lai.module.jingdu.model.RankModel;
import com.softtek.lai.module.jingdu.model.Table1Model;
import com.softtek.lai.module.jingdu.presenter.GetProinfoImpl;
import com.softtek.lai.module.jingdu.presenter.IGetProinfopresenter;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import zilla.libcore.ui.InjectLayout;

import java.util.ArrayList;
import java.util.List;

@InjectLayout(R.layout.activity_jingdu)
public class JingduActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_right)
    TextView tv_right;

    @InjectView(R.id.list_rank)
    ListView list_rank;

    @InjectView(R.id.tv_wz)
    TextView tv_wz;

    @InjectView(R.id.tv_wz2)
    TextView tv_wz2;

    private List<Table1Model> table1ModelList = new ArrayList<Table1Model>();
    private IGetProinfopresenter iGetProinfopresenter;

    private RankModel rank;
    public RankAdapter rankAdapter;
    String a = "";
    String b = "";
    String c = "";
    String d = "";
    String e = "";
    String f = "";
    String g = "";
    String text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        rankAdapter=new RankAdapter(this,table1ModelList);
        list_rank.setAdapter(rankAdapter);
        ll_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
//        "TotalWeight": "20.3",
//                "TotalMember": "8",
//                "TotalClass": "5"



//        tv_wz.setText(text);
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
        System.out.println("rankEvent.getRanks()》》》》》》》》》》》》》》" + rank.getTable());
        a=rank.getTable().get(0).getTotalClass();
        b=rank.getTable().get(0).getTotalMember();
        c=rank.getTable().get(0).getTotalWeight();
        tv_wz.setText( "本月新开班级"+a+"个,新增学员"+b+"名,累计减重"+c+"斤 , 其中1月班累计减重"+d+"斤,2月版本月累计减重"+e+"斤,3月版本月累计减重"+f+"斤, 相当于"+g+"头大象.");


//        List<Table1Model> table1 = rank.getTable1();
        table1ModelList=rank.getTable1();
        rankAdapter.updateData(table1ModelList);
//        for (Table1Model table1Model :) {
//            //System.out.println("》》》》》》》" + "AccountId:" + rk.getAccountId() + "ClassIdModel:" + rk.getClassId() + "OrderNum:" + rk.getOrderNum() + "UserName:" + rk.getUserName() + "LossAfter:" + rk.getLossAfter() + "LossBefor:" + rk.getLossBefor() + "LossWeght:" + rk.getLossWeght());
//            RankModel r1 = new RankModel(rk.getAccountId(),rk.getBeforeWight(),rk.getAfterWeight(),rk.getLoseWeight(),rk.getUserName());
//            rankList.add(r1);
//        }
//        rankAdapter.updateData(rankList);
//        Log.i("rankList>>>>>>>>>>>>>>", "" + rankList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            //分享功能逻辑
            case R.id.tv_right:

                break;
        }
    }
}
