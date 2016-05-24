/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.jingdu.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ggx.jerryguan.widget_lib.Chart;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.jingdu.Adapter.RankAdapter;
import com.softtek.lai.module.jingdu.model.PaimingModel;
import com.softtek.lai.module.jingdu.model.SPModel;
import com.softtek.lai.module.jingdu.model.Table1Model;
import com.softtek.lai.module.jingdu.presenter.GetProinfoImpl;
import com.softtek.lai.module.jingdu.presenter.IGetProinfopresenter;
//import com.umeng.socialize.bean.SocializeConfig;
//import com.umeng.socialize.sso.UMSsoHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_zhujiaojingdu)
public class ZhuJiaoJingduActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.tv_right)
    TextView tv_right;

    @InjectView(R.id.list_rank)
    ListView list_rank;

    //班级布局
    @InjectView(R.id.img_oneban)
    ImageView img_oneban;

    @InjectView(R.id.img_twoban)
    ImageView img_twoban;

    @InjectView(R.id.img_threeban)
    ImageView img_threeban;

    @InjectView(R.id.ll_oneban)
    LinearLayout ll_oneban;

    @InjectView(R.id.ll_twoban)
    LinearLayout ll_twoban;

    @InjectView(R.id.ll_threeban)
    LinearLayout ll_threeban;

    //班级累计减重数
    @InjectView(R.id.tv_oneban)
    TextView tv_oneban;

    @InjectView(R.id.tv_twoban)
    TextView tv_twoban;

    @InjectView(R.id.tv_threeban)
    TextView tv_threeban;

    //班级名称
    @InjectView(R.id.tv_classname1)
    TextView tv_classname1;

    @InjectView(R.id.tv_classname2)
    TextView tv_classname2;

    @InjectView(R.id.tv_classname3)
    TextView tv_classname3;

    @InjectView(R.id.total_weight)
    Chart total_weight;

    private List<Table1Model> table1ModelList = new ArrayList<Table1Model>();
    private List<PaimingModel> paimingModelList = new ArrayList<PaimingModel>();
    private IGetProinfopresenter iGetProinfopresenter;

    private SPModel spModel;
    public RankAdapter rankAdapter;

    //班级名称
    String onebanname;
    String twobanname;
    String threebanname;

    //班级累计减重数
    String oneban;
    String twoban;
    String threeban;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        //初始化排名序号
        initpaiming();
        rankAdapter = new RankAdapter(this, table1ModelList, paimingModelList);
        list_rank.setAdapter(rankAdapter);
        ll_left.setOnClickListener(this);
        //tv_right.setOnClickListener(this);
    }

    private void initpaiming() {
        PaimingModel p1 = new PaimingModel(1);
        paimingModelList.add(p1);
        PaimingModel p2 = new PaimingModel(2);
        paimingModelList.add(p2);
        PaimingModel p3 = new PaimingModel(3);
        paimingModelList.add(p3);
        PaimingModel p4 = new PaimingModel(4);
        paimingModelList.add(p4);
        PaimingModel p5 = new PaimingModel(5);
        paimingModelList.add(p5);
        PaimingModel p6 = new PaimingModel(6);
        paimingModelList.add(p6);
        PaimingModel p7 = new PaimingModel(7);
        paimingModelList.add(p7);
        PaimingModel p8 = new PaimingModel(8);
        paimingModelList.add(p8);
        PaimingModel p9 = new PaimingModel(9);
        paimingModelList.add(p9);
        PaimingModel p10 = new PaimingModel(10);
        paimingModelList.add(p10);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void initViews() {
        iGetProinfopresenter = new GetProinfoImpl();
        iGetProinfopresenter.getspproinfo();
    }

    @Override
    protected void initDatas() {
        tv_title.setText("当期进度");
        //tv_right.setText("分享");
    }

    @Subscribe
    public void onEvent(SPModel spModel) {
        //SPTable：教练本月(累计减重数量)
        //System.out.println("rankEvent.getRanks()》》》》》》》》》》》》》》" + spModel.getSpTable().get(0).getTotalWeight());

        //Table1: 教练所有班级的所有学员减重最多的前10名学员
        table1ModelList = spModel.getTable1();
        rankAdapter.updateData(table1ModelList, paimingModelList);

        //Table2:各个班本月累计减重
        System.out.println("spEvent.getRanks()------------------------Table2:" + spModel.getTable2());
        if (spModel.getTable2().size() == 1) {
            img_oneban.setVisibility(View.VISIBLE);

//           ll_twoban.setVisibility(View.GONE);
//           ll_threeban.setVisibility(View.GONE);
            onebanname = spModel.getTable2().get(0).getClassName();
            oneban = spModel.getTable2().get(0).getLoseWeight();
            tv_classname1.setText(onebanname);
            tv_oneban.setText(oneban);
            float a = Float.parseFloat(oneban);
            total_weight.setValue(a, 0, 0);
        } else if (spModel.getTable2().size() == 2) {
            //ll_threeban.setVisibility(View.GONE);
            img_oneban.setVisibility(View.VISIBLE);
            img_twoban.setVisibility(View.VISIBLE);

            onebanname = spModel.getTable2().get(0).getClassName();
            oneban = spModel.getTable2().get(0).getLoseWeight();
            twobanname = spModel.getTable2().get(1).getClassName();
            twoban = spModel.getTable2().get(1).getLoseWeight();
            tv_classname1.setText(onebanname);
            tv_oneban.setText(oneban);
            tv_classname2.setText(twobanname);
            tv_twoban.setText(twoban);
            float a = Float.parseFloat(oneban);
            float b = Float.parseFloat(twoban);
            total_weight.setValue(a, b, 0);
        } else if (spModel.getTable2().size() == 3) {
            img_oneban.setVisibility(View.VISIBLE);
            img_twoban.setVisibility(View.VISIBLE);
            img_threeban.setVisibility(View.INVISIBLE);

            onebanname = spModel.getTable2().get(0).getClassName();
            oneban = spModel.getTable2().get(0).getLoseWeight();
            twobanname = spModel.getTable2().get(1).getClassName();
            twoban = spModel.getTable2().get(1).getLoseWeight();
            threebanname = spModel.getTable2().get(2).getClassName();
            threeban = spModel.getTable2().get(2).getLoseWeight();
            tv_classname1.setText(onebanname);
            tv_oneban.setText(oneban);
            tv_classname2.setText(twobanname);
            tv_twoban.setText(twoban);
            tv_classname3.setText(threebanname);
            tv_threeban.setText(threeban);
            float a = Float.parseFloat(oneban);
            float b = Float.parseFloat(twoban);
            float c = Float.parseFloat(threeban);
            total_weight.setValue(a, b, c);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            //分享功能逻辑
            case R.id.tv_right:
//                ShareUtils shareUtils = new ShareUtils(ZhuJiaoJingduActivity.this);
//                shareUtils.setShareContent("康宝莱体重管理挑战赛，坚持只为改变！", "http://www.baidu.com", R.drawable.logo, "我已成功在**天减重**斤，快来见证我的改变，和我一起参加体重管理挑战赛吧！", "我已成功在**天减重**斤，快来见证我的改变，和我一起参加体重管理挑战赛吧！");
//                shareUtils.getController().openShare(ZhuJiaoJingduActivity.this, false);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("resultCode:" + resultCode);
//        UMSsoHandler ssoHandler = SocializeConfig.getSocializeConfig().getSsoHandler(requestCode);
//        if (ssoHandler != null) {
//            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
//        }
    }
}
