package com.softtek.lai.module.historydate.view;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.historydate.model.HistoryData;
import com.softtek.lai.utils.DateUtil;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_history_data_detail)
public class HistoryDataDetailActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.iv_icon)
    ImageView iv_icon;
    @InjectView(R.id.tv_ymd)
    TextView tv_ymd;
    @InjectView(R.id.tv_hm)
    TextView tv_hm;
    @InjectView(R.id.tv_weight)
    TextView tv_weight;
    @InjectView(R.id.tv_body_fat)
    TextView tv_body_fat;
    @InjectView(R.id.tv_fat)
    TextView tv_fat;
    @InjectView(R.id.tv_bust)
    TextView tv_bust;
    @InjectView(R.id.tv_waistline)
    TextView tv_waistline;
    @InjectView(R.id.tv_hipline)
    TextView tv_hipline;
    @InjectView(R.id.tv_up_hipline)
    TextView tv_up_hipline;
    @InjectView(R.id.tv_thigh)
    TextView tv_thigh;
    @InjectView(R.id.tv_calf)
    TextView tv_calf;

    private HistoryData model;

    @Override
    protected void initViews() {
        tv_title.setText("历史数据测量");
        ll_left.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        model=getIntent().getParcelableExtra("historyData");
        if("0".equals(model.getSourcetype())){
            //莱秤数据
            iv_icon.setBackground(ContextCompat.getDrawable(this,R.drawable.laichen));
        }else if("1".equals(model.getSourcetype())){
            //复测
            iv_icon.setBackground(ContextCompat.getDrawable(this,R.drawable.history_data_fuce));
        }else if("2".equals(model.getSourcetype())){
            iv_icon.setBackground(ContextCompat.getDrawable(this,R.drawable.shoudongluru));
        }
        String date=model.getCreateDate();
        DateUtil util=DateUtil.getInstance();
        tv_ymd.setText(util.convertDateStr(date,"yyyy-MM-dd"));
        tv_hm.setText(util.convertDateStr(date,"HH:mm:ss"));
        tv_weight.setText(model.getWeight());
        tv_body_fat.setText(model.getPysical());
        tv_fat.setText(model.getFat());
        tv_bust.setText(model.getCircum());
        tv_waistline.setText(model.getWaistline());
        tv_hipline.setText(model.getHiplie());
        tv_up_hipline.setText(model.getUpArmGirth());
        tv_thigh.setText(model.getUpLegGirth());
        tv_calf.setText(model.getDoLegGirth());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
        }
    }
}
