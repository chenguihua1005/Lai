package com.softtek.lai.module.personalPK.view;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.personalPK.adapter.SearchAdapter;
import com.softtek.lai.module.personalPK.model.PKCreatModel;
import com.softtek.lai.module.personalPK.model.PKObjModel;
import com.softtek.lai.module.personalPK.model.PKObjRequest;
import com.softtek.lai.module.personalPK.presenter.PKListManager;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_search)
public class SearchActivity extends BaseActivity implements View.OnClickListener,TextWatcher,AdapterView.OnItemClickListener,PullToRefreshBase.OnRefreshListener2<ListView>{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;

    @InjectView(R.id.et_search)
    EditText search;
    @InjectView(R.id.lv)
    PullToRefreshListView lv;
    @InjectView(R.id.no_message)
    ImageView no_message;

    List<PKObjModel> modelList=new ArrayList<>();
    SearchAdapter adapter;
    PKListManager manager;
    int totalPage;
    int pageIndex=1;

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        tv_title.setText("选择PK挑战对手");
        tv_right.setText("搜索");
        lv.setOnItemClickListener(this);
        lv.setOnRefreshListener(this);
        lv.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        lv.setEmptyView(no_message);
    }

    @Override
    protected void initDatas() {
        manager=new PKListManager();
        adapter=new SearchAdapter(this,modelList);
        lv.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
            case R.id.fl_right:
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                if(StringUtils.isEmpty(search.getText().toString())){
                    new AlertDialog.Builder(this).setMessage("请输入姓名或手机号再试").create().show();
                }else{
                    dialogShow("搜索中...");
                    pageIndex=1;
                    manager.searchPKObj(this,search.getText().toString(),pageIndex);
                }
                break;

        }
    }

    public void loadData(PKObjRequest data){
        dialogDissmiss();
        try {
            lv.onRefreshComplete();
            if(data==null){
                pageIndex=--pageIndex<1?1:pageIndex;
                return;
            }
            totalPage=data.getPageCount();
            List<PKObjModel> models=data.getData();
            if(models==null||models.isEmpty()){
                lv.setMode(PullToRefreshBase.Mode.DISABLED);
                if (pageIndex==1){
                    modelList.clear();
                    adapter.notifyDataSetChanged();
                }else {
                    pageIndex=--pageIndex<1?1:pageIndex;
                }
                return;
            }
            lv.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
            if (pageIndex==1){
                modelList.clear();
            }
            modelList.addAll(models);
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(s.length()>0){
            fl_right.setEnabled(true);
        }else{
            fl_right.setEnabled(false);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PKObjModel model=modelList.get(position-1);
        PKCreatModel creatModel=getIntent().getParcelableExtra("pkmodel");
        creatModel.setBeUserName(model.getUserName());
        creatModel.setBeChallenged(model.getRGAccId());
        creatModel.setBeUserPhoto(model.getPhoto());
        Intent intent=new Intent(this,SelectTimeActivity.class);
        intent.putExtra("pkmodel", creatModel);
        startActivity(intent);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex++;
        if(pageIndex<=totalPage){
            manager.searchPKObj(this,search.getText().toString(),pageIndex);
        }else{
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    lv.onRefreshComplete();
                }
            }, 200);
        }
    }
}
