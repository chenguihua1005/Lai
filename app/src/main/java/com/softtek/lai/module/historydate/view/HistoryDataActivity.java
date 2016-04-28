package com.softtek.lai.module.historydate.view;

import android.content.Intent;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.historydate.adapter.HistoryDataAdapter;
import com.softtek.lai.module.historydate.model.HistoryData;
import com.softtek.lai.module.historydate.model.HistoryDataItemModel;
import com.softtek.lai.module.historydate.model.HistoryDataModel;
import com.softtek.lai.module.historydate.presenter.HistoryDataManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_history_data)
public class HistoryDataActivity extends BaseActivity implements AdapterView.OnItemClickListener,View.OnClickListener
,HistoryDataManager.HistoryDataManagerCallback,PullToRefreshBase.OnRefreshListener2<ListView>{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    @InjectView(R.id.ptrlv)
    PullToRefreshListView ptrlv;
    @InjectView(R.id.footer)
    LinearLayout ll_footer;
    @InjectView(R.id.cb_all)
    CheckBox cb_all;
    @InjectView(R.id.tv_delete)
    TextView tv_delete;

    private List<HistoryDataItemModel> dataItemModels=new ArrayList<>();
    private HistoryDataAdapter adapter;
    private HistoryDataManager manager;
    private int pageIndex=0;
    private int totalPage=0;
    @Override
    protected void initViews() {
        tv_title.setText("历史数据测量");
        tv_right.setText("编辑");
        ptrlv.setOnItemClickListener(this);
        ptrlv.setOnRefreshListener(this);
        ptrlv.setMode(PullToRefreshBase.Mode.BOTH);
        ll_left.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        cb_all.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        manager=new HistoryDataManager(this);
        adapter=new HistoryDataAdapter(this,dataItemModels,cb_all);
        ptrlv.setAdapter(adapter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrlv.setRefreshing();
            }
        }, 300);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        HistoryData data=dataItemModels.get(position-1).getDataModel();
        Intent intent=new Intent(this,HistoryDataDetailActivity.class);
        intent.putExtra("historyData",data);
        startActivity(intent);
    }

    private boolean editOrCompleted=false;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                setResult(RESULT_OK,getIntent());
                finish();
                break;
            case R.id.fl_right:
                if(dataItemModels.isEmpty()){
                    break;
                }
                for(HistoryDataItemModel model:dataItemModels){
                    model.setShow(model.isShow()?false:true);
                    model.setChecked(false);
                }
                editOrCompleted=dataItemModels.get(0).isShow();
                tv_right.setText(editOrCompleted?"完成":"编辑");
                if(editOrCompleted) {
                    ptrlv.setMode(PullToRefreshBase.Mode.DISABLED);
                    ll_footer.setVisibility(View.VISIBLE);
                }
                else {
                    ptrlv.setMode(PullToRefreshBase.Mode.BOTH);
                    ll_footer.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.tv_delete:
                //提交删除选项
                StringBuffer ids=new StringBuffer("");
                for (int i=0;i<dataItemModels.size();i++){
                    HistoryDataItemModel model=dataItemModels.get(i);
                    if(model.isChecked()){
                        ids.append(","+model.getDataModel().getAcInfoId());
                    }
                }
                if(ids.toString().equals("")){
                    break;
                }
                Log.i("删除的id="+ids.toString().substring(1,ids.length()));
                dialogShow("正在删除。。。");
                manager.deleteHistoryData(ids.toString().substring(1,ids.length()));
                break;
            case R.id.cb_all:
                for(HistoryDataItemModel model:dataItemModels){
                    model.setChecked(cb_all.isChecked());
                }
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void historyDataCallback(HistoryDataModel model) {
        ptrlv.onRefreshComplete();
        if(model==null){
            pageIndex=--pageIndex<1?1:pageIndex;
            return;
        }

        totalPage=Integer.parseInt(model.getTotalPage());
        List<HistoryData> datas=model.getHistoryList();
        if(pageIndex==1){
            dataItemModels.clear();
        }
        if(datas.isEmpty()){
            pageIndex=--pageIndex<1?1:pageIndex;
        }
        for(HistoryData data:model.getHistoryList()){
            dataItemModels.add(new HistoryDataItemModel(false,false,data));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void deleteResult(boolean result) {
        dialogDissmiss();
        if(result){
            for(HistoryDataItemModel model:dataItemModels){
                model.setShow(false);
                model.setChecked(false);
            }
            editOrCompleted=dataItemModels.get(0).isShow();
            tv_right.setText(editOrCompleted?"完成":"编辑");
            if(editOrCompleted) {
                ptrlv.setMode(PullToRefreshBase.Mode.DISABLED);
                ll_footer.setVisibility(View.VISIBLE);
            }
            else {
                ptrlv.setMode(PullToRefreshBase.Mode.BOTH);
                ll_footer.setVisibility(View.GONE);
            }
            adapter.notifyDataSetChanged();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ptrlv.setRefreshing();
                }
            }, 300);
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex=1;
        manager.getHistoryDataList(1);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex++;
        if(pageIndex<=totalPage){
            manager.getHistoryDataList(pageIndex);
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ptrlv.onRefreshComplete();
                }
            }, 300);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            setResult(RESULT_OK,getIntent());
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
