package com.softtek.lai.module.studetail.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.studetail.adapter.LossWeightLogAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_loss_weight_log)
public class LossWeightLogActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.ptrlv)
    PullToRefreshListView ptrlv;

    private List logs=new ArrayList<>();

    @Override
    protected void initViews() {
        tv_title.setText("减重日志");
        ll_left.setOnClickListener(this);
        View view=getLayoutInflater().inflate(R.layout.loss_weight_log_header,null,false);
        ptrlv.getRefreshableView().addHeaderView(view);
        ptrlv.setMode(PullToRefreshBase.Mode.BOTH);
        for (int i=0;i<10;i++){
            logs.add(i);
        }
        ptrlv.setOnItemClickListener(this);
        ptrlv.setAdapter(new LossWeightLogAdapter(this,logs));

    }

    @Override
    protected void initDatas() {

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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position==0){
            return;
        }
        
    }
}
