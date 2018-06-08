package com.softtek.lai.module.personalPK.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.personalPK.adapter.SearchAdapter;
import com.softtek.lai.module.personalPK.model.PKCreatModel;
import com.softtek.lai.module.personalPK.model.PKObjModel;
import com.softtek.lai.module.personalPK.presenter.PKListManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_select_opponent)
public class SelectOpponentActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;


    @InjectView(R.id.ll_search)
    LinearLayout ll_search;

    @InjectView(R.id.lv)
    ListView lv;
    List<PKObjModel> modelList=new ArrayList<>();
    SearchAdapter adapter;
    PKListManager manager;

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        ll_search.setOnClickListener(this);
        lv.setOnItemClickListener(this);
        tv_title.setText("选择PK挑战对手");
    }

    @Override
    protected void initDatas() {
        manager=new PKListManager();
        adapter=new SearchAdapter(this,modelList);
        lv.setAdapter(adapter);
        dialogShow("载入跑团成员");
        manager.getCurrentPaoTuanMember(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
            case R.id.ll_search:
                //跳转至搜索界面
                Intent pk=new Intent(this,SearchActivity.class);
                pk.putExtra("pkmodel",getIntent().getParcelableExtra("pkmodel"));
                startActivity(pk);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PKObjModel model=modelList.get(position);
        PKCreatModel creatModel=getIntent().getParcelableExtra("pkmodel");
        creatModel.setBeUserName(model.getUserName());
        creatModel.setBeChallenged(model.getRGAccId());
        creatModel.setBeUserPhoto(model.getPhoto());
        Intent intent=new Intent(this,SelectTimeActivity.class);
        intent.putExtra("pkmodel",creatModel);
        startActivity(intent);
    }

    public void loadData(List<PKObjModel> models){
        dialogDissmiss();
        if(models==null||models.isEmpty()){
            return;
        }
        try {
            modelList.clear();
            modelList.addAll(models);
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
