package com.softtek.lai.widgets;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sina.weibo.sdk.api.share.Base;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_test)
public class TestActivity extends BaseActivity {

    @InjectView(R.id.recycle)
    RecyclerView recyclerView;
    List<String> infos=new ArrayList<>();

    @Override
    protected void initViews() {
        for (int i=0;i<4;i++){
            infos.add(i+"item");
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new RecyclerViewAdapter(infos));
    }

    @Override
    protected void initDatas() {

    }
}
