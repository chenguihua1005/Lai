/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.jingdu.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.ShareUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeConfig;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;


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

    //@InjectView(R.id.tv_wz)
    TextView tv_wz;

    //@InjectView(R.id.tv_wz2)
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
        tv_wz.setText( "本月新开班级"+a+"个,新增学员"+b+"名,累计减重"+c+"斤,其中1月班累计减重"+d+"斤,2月班本月累计减重"+e+"xx"+"斤,3月班本月累计减重"+f+"xx"+"斤, 相当于"+g+"xx"+"头大象.");

        table1ModelList=rank.getTable1();
        rankAdapter.updateData(table1ModelList);
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
