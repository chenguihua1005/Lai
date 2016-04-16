package com.softtek.lai.module.studetail.view;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.studetail.adapter.LogDetailGridAdapter;
import com.softtek.lai.module.studetail.adapter.LossWeightLogAdapter;
import com.softtek.lai.module.studetail.model.LossWeightLogModel;
import com.softtek.lai.module.studetail.presenter.IMemberInfopresenter;
import com.softtek.lai.module.studetail.presenter.MemberInfoImpl;
import com.softtek.lai.widgets.CircleImageView;
import com.softtek.lai.widgets.CustomGridView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_log_detail)
public class LogDetailActivity extends BaseActivity implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.civ_header_image)
    CircleImageView civ_header_image;
    @InjectView(R.id.tv_name)
    TextView tv_name;
    @InjectView(R.id.tv_totle_lw)
    TextView tv_totle_lw;
    @InjectView(R.id.tv_log_title)
    TextView tv_log_title;
    @InjectView(R.id.tv_content)
    TextView tv_content;
    @InjectView(R.id.tv_date)
    TextView tv_date;
    @InjectView(R.id.cb_zan)
    CheckBox cb_zan;
    @InjectView(R.id.cgv_list_image)
    CustomGridView cgv_list_image;
    List<String> images=new ArrayList();

    private IMemberInfopresenter memberInfopresenter;
    private LossWeightLogModel log;

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        cb_zan.setOnCheckedChangeListener(this);
        tv_title.setText("日志详情");
    }

    @Override
    protected void initDatas() {
        memberInfopresenter=new MemberInfoImpl(this,null);
        log= (LossWeightLogModel) getIntent().getSerializableExtra("log");
        Picasso.with(this).load(log.getPhoto()).placeholder(R.drawable.img_default)
                .error(R.drawable.img_default).into(civ_header_image);
        tv_name.setText(log.getUserName());
        tv_log_title.setText(log.getLogTitle());
        tv_content.setText(log.getLogContent());
        tv_date.setText(log.getCreateDate());
        tv_totle_lw.setText(log.getAfterWeight()+"kg");
        cb_zan.setText(log.getPriase());
        if(LossWeightLogAdapter.ZAN_NO.equals(log.getIsClicked())){
            cb_zan.setChecked(true);
        }else{
            cb_zan.setChecked(false);
        }
        if(getIntent().getIntExtra("review",0)==0){
            cb_zan.setEnabled(false);
        }
        //拆分字符串图片列表,并添加到图片集合中
        if(!"".equals(log.getImgCollection())&&!(null==log.getImgCollection())){
            String[] image=log.getImgCollection().split(",");
            images.addAll(Arrays.asList(image));
        }
        cgv_list_image.setAdapter(new LogDetailGridAdapter(this,images));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            log.setPriase(Integer.parseInt(log.getPriase())+1+"");
            ((CheckBox)buttonView).setEnabled(false);
            //向服务器提交
            memberInfopresenter.doZan(3,Long.parseLong(log.getLossLogId()));
        }else{
            log.setPriase(Integer.parseInt(log.getPriase())-1+"");
        }
    }
}
