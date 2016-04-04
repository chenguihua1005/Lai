package com.softtek.lai.module.studetail.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.studetail.adapter.LogDetailGridAdapter;
import com.softtek.lai.widgets.CustomGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_log_detail)
public class LogDetailActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.cgv_list_image)
    CustomGridView cgv_list_image;
    List images=new ArrayList();

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        tv_title.setText("日志详情");
    }

    @Override
    protected void initDatas() {
        for (int i=0;i<9;i++){
            images.add(i);
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
}
