package com.softtek.lai.module.healthyreport;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.healthyreport.adapter.HistoryDataAdapter;
import com.softtek.lai.module.healthyreport.model.HistoryData;
import com.softtek.lai.module.healthyreport.model.HistoryDataItemModel;
import com.softtek.lai.module.healthyreport.model.HistoryDataModel;
import com.softtek.lai.module.healthyreport.presenter.HistoryDataManager;
import com.softtek.lai.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_history_data)
public class HistoryDataActivity extends BaseActivity<HistoryDataManager> implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener, View.OnClickListener
        , HistoryDataManager.HistoryDataManagerCallback, PullToRefreshBase.OnRefreshListener2<ListView> {

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

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
    @InjectView(R.id.tv_cancle)
    TextView tv_cancle;


    private List<HistoryDataItemModel> dataItemModels = new ArrayList<>();
    private HistoryDataAdapter adapter;
    private int pageIndex = 0;
    private int totalPage = 0;

    @Override
    protected void initViews() {
        ptrlv.setOnItemClickListener(this);
        ptrlv.getRefreshableView().setOnItemLongClickListener(this);
        ptrlv.setOnRefreshListener(this);
        ptrlv.setMode(PullToRefreshBase.Mode.BOTH);
        ll_left.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        cb_all.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        tv_cancle.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        setPresenter(new HistoryDataManager(this));
        adapter = new HistoryDataAdapter(this, dataItemModels, cb_all);
        ptrlv.setAdapter(adapter);
        dialogShow("加载中");
        pageIndex = 1;
        getPresenter().getHistoryDataList(1,false);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(isEditor){
            return;
        }
        //点击进入健康报告
        
    }

    boolean isEditor;
    private void editor(){
        isEditor=!isEditor;
        for (HistoryDataItemModel model : dataItemModels) {
            model.setShow(isEditor);
            model.setChecked(false);
        }
        if (isEditor) {
            ptrlv.setMode(PullToRefreshBase.Mode.DISABLED);
            ll_footer.setVisibility(View.VISIBLE);
        } else {
            ptrlv.setMode(PullToRefreshBase.Mode.BOTH);
            ll_footer.setVisibility(View.GONE);
        }
        adapter.notifyDataSetChanged();
        if(isEditor){
            if(empty==null){
                empty=new View(this);
                empty.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,DisplayUtil.dip2px(this,48)));
            }
            ptrlv.getRefreshableView().addFooterView(empty);
        }else {
            ptrlv.getRefreshableView().removeFooterView(empty);
        }
    }
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        editor();
        return true;
    }

    View empty;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.fl_right:
                startActivity(new Intent(this,HealthEntryActivity.class));
                break;
            case R.id.tv_delete:
                //提交删除选项
                StringBuffer ids = new StringBuffer("");
                for (int i = 0; i < dataItemModels.size(); i++) {
                    HistoryDataItemModel model = dataItemModels.get(i);
                    if (model.isChecked()) {
                        ids.append("," + model.getDataModel().getAcInfoId());
                    }
                }
                if (TextUtils.isEmpty(ids)) {
                    break;
                }
                getPresenter().deleteHistoryData(ids.toString().substring(1, ids.length()));
                break;
            case R.id.cb_all:
                for (HistoryDataItemModel model : dataItemModels) {
                    model.setChecked(cb_all.isChecked());
                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.tv_cancle:
                editor();
                break;
        }
    }

    @Override
    public void historyDataCallback(HistoryDataModel model) {
        if (model == null) {
            pageIndex = --pageIndex < 1 ? 1 : pageIndex;
            return;
        }

        totalPage = Integer.parseInt(model.getTotalPage());
        List<HistoryData> datas = model.getHistoryList();
        if (pageIndex == 1) {
            dataItemModels.clear();
        }
        if (datas.isEmpty()) {
            pageIndex = --pageIndex < 1 ? 1 : pageIndex;
        }
        for (HistoryData data : model.getHistoryList()) {
            dataItemModels.add(new HistoryDataItemModel(false, false, data));

        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public void deleteResult() {
        Iterator<HistoryDataItemModel> its=dataItemModels.iterator();
        while (its.hasNext()){
            HistoryDataItemModel model=its.next();
            if(model.isChecked()){
                its.remove();
            }else {
                model.setShow(false);
            }
        }
        isEditor=false;
        ptrlv.setMode(PullToRefreshBase.Mode.BOTH);
        ll_footer.setVisibility(View.GONE);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void hidenPull() {
        ptrlv.onRefreshComplete();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex = 1;
        getPresenter().getHistoryDataList(1,true);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex++;
        if (pageIndex <= totalPage) {
            getPresenter().getHistoryDataList(pageIndex,true);
        } else {
            pageIndex--;
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
            if(isEditor) {
                editor();
                return true;
            }
            return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

}
