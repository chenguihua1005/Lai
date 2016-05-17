package com.softtek.lai.module.bodygameyk.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.bodygame.model.TiGuanSaiModel;
import com.softtek.lai.module.bodygame.model.TotolModel;
import com.softtek.lai.module.bodygame.presenter.ITiGuanSai;
import com.softtek.lai.module.bodygame.presenter.TiGuanSaiImpl;
import com.softtek.lai.module.counselor.view.GameActivity;
import com.softtek.lai.module.tips.view.TipsActivity;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_bodygame_yk)
public class BodygameYkActivity extends BaseActivity implements View.OnClickListener{
    //标题栏
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_tipyk)
    LinearLayout ll_tipyk;
    @InjectView(R.id.ll_match)
    LinearLayout ll_match;
    @InjectView(R.id.tv_refreshyk)
    ImageView tv_refreshyk;
    @InjectView(R.id.tv_totalpersonyk)
    TextView tv_totalpersonyk;
    @InjectView(R.id.tv_total_lossyk)
    TextView tv_total_lossyk;
    @InjectView(R.id.iv_advyk)
    ImageView iv_advyk;

    private ITiGuanSai iTiGuanSai;
    private ProgressDialog progressDialog;

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        progressDialog = new ProgressDialog(this);
        ll_left.setOnClickListener(this);
        ll_tipyk.setOnClickListener(this);
        ll_match.setOnClickListener(this);
        tv_refreshyk.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void initDatas() {
        tv_title.setText("体管赛（游客版）");
        iTiGuanSai=new TiGuanSaiImpl();
        iTiGuanSai.getTiGuanSai();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("数据刷新中...");
        progressDialog.show();
        iTiGuanSai.doGetTotal(progressDialog);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.ll_left:
                finish();
                break;
            case R.id.ll_tipyk:
                Intent intent=new Intent(this,TipsActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_match:
                Intent intents=new Intent(this,GameActivity.class);
                startActivity(intents);
                break;
            case R.id.tv_refreshyk:
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setMessage("数据刷新中...");
                progressDialog.show();
                iTiGuanSai.doGetTotal(progressDialog);
                break;
        }
    }
    @Subscribe
    public void onEvent(TiGuanSaiModel tiGuanSai){
        iv_advyk.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Picasso.with(this).load(tiGuanSai.getImg_Addr()).placeholder(R.drawable.default_pic).fit().error(R.drawable.default_pic).into(iv_advyk);


    }
    @Subscribe
    public void doGetTotol(List<TotolModel> totolModels){
        System.out.println("dsadasdsadasda>>》》》》》》》》》》》》》》"+totolModels.get(0).getTotal_loss());
        tv_totalpersonyk.setText(totolModels.get(0).getTotal_person());
        tv_total_lossyk.setText(totolModels.get(0).getTotal_loss());
    }
}
