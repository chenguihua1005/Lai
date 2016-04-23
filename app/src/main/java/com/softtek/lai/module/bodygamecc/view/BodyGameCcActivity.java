package com.softtek.lai.module.bodygamecc.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame.model.TiGuanSaiModel;
import com.softtek.lai.module.bodygame.model.TotolModel;
import com.softtek.lai.module.bodygame.presenter.ITiGuanSai;
import com.softtek.lai.module.bodygame.presenter.TiGuanSaiImpl;
import com.softtek.lai.module.bodygame.view.TipsActivity;
import com.softtek.lai.module.counselor.view.GameActivity;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_body_game_cc)
public class BodyGameCcActivity extends BaseActivity implements View.OnClickListener{
    //标题栏
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.ll_match)
    LinearLayout ll_match;

    @InjectView(R.id.ll_tipcc)
    LinearLayout ll_tipcc;

    @InjectView(R.id.tv_total_losscc)
    TextView tv_total_losscc;
    @InjectView(R.id.tv_totalpersoncc)
    TextView tv_totalpersoncc;
    //BANNER图片
    @InjectView(R.id.iv_advcc)
    ImageView iv_advcc;
    //刷新
    @InjectView(R.id.tv_totalcc)
    ImageView tv_totalcc;
    private ITiGuanSai iTiGuanSai;
    UserInfoModel userInfoModel=UserInfoModel.getInstance();
    long loginid=Long.parseLong(userInfoModel.getUser().getUserid());
    private ProgressDialog progressDialog;
    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        progressDialog = new ProgressDialog(this);
        ll_left.setOnClickListener(this);
        ll_tipcc.setOnClickListener(this);
        ll_match.setOnClickListener(this);
        tv_totalcc.setOnClickListener(this);

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        tv_totalcc.setOnClickListener(this);
        super.onDestroy();
    }

    @Override
    protected void initDatas() {
        tv_title.setText("体管赛（普通顾客版）");
        iTiGuanSai=new TiGuanSaiImpl();
        iTiGuanSai.doGetTotal(progressDialog);
        iTiGuanSai.getTiGuanSai();

    }
    @Subscribe
    public void onEvent(TiGuanSaiModel tiGuanSai){
        iv_advcc.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (!TextUtils.isEmpty(tiGuanSai.getImg_Addr())) {
            iv_advcc.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Picasso.with(this).load(tiGuanSai.getImg_Addr()).placeholder(R.drawable.default_pic).fit().error(R.drawable.default_pic).into(iv_advcc);
        }
        else
        {
            Picasso.with(this).load("www").placeholder(R.drawable.default_pic).fit().error(R.drawable.default_pic).into(iv_advcc);
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.ll_left:
                finish();
                break;
            case R.id.ll_tipcc:
                Intent intent=new Intent(this,TipsActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_match:
                Intent intents=new Intent(this,GameActivity.class);
                startActivity(intents);
                break;
            case R.id.tv_totalcc:
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setMessage("数据刷新中...");
                progressDialog.show();
                iTiGuanSai.doGetTotal(progressDialog);
                break;

        }

    }
    @Subscribe
    public void doGetTotol(List<TotolModel> totolModels){
        System.out.println("dsadasdsadasda>>》》》》》》》》》》》》》》"+totolModels.get(0).getTotal_loss());
        tv_totalpersoncc.setText(totolModels.get(0).getTotal_person());
        tv_total_losscc.setText(totolModels.get(0).getTotal_loss());
    }
}
