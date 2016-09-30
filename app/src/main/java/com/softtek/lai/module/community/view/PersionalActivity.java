package com.softtek.lai.module.community.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.community.adapter.DynamicRecyclerViewAdapter;
import com.softtek.lai.widgets.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_persional)
public class PersionalActivity extends BaseActivity {

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.cir_image)
    CircleImageView circleImageView;
    @InjectView(R.id.tv_name)
    TextView tv_name;
    @InjectView(R.id.tv_dynamic_num)
    TextView tv_dynamic_num;
    @InjectView(R.id.cb_attention)
    CheckBox cb_attention;

    @InjectView(R.id.recycleView)
    RecyclerView recyclerView;

    private List dynamics;
    private DynamicRecyclerViewAdapter adapter;


    @Override
    protected void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initDatas() {
        dynamics=new ArrayList();
        for(int i=0;i<20;i++){
            dynamics.add(i);
        }
        adapter=new DynamicRecyclerViewAdapter(this,dynamics);
        recyclerView.setAdapter(adapter);
    }
}
