package com.softtek.lai.module.healthyreport;

import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_history_data)
public class HistoryDataActivity extends BaseActivity<HistoryDataManager> implements AdapterView.OnItemClickListener, View.OnClickListener
        , HistoryDataManager.HistoryDataManagerCallback, PullToRefreshBase.OnRefreshListener2<ListView> {

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.iv_email)
    ImageView iv_right;
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

    private List<HistoryDataItemModel> dataItemModels = new ArrayList<>();
    private HistoryDataAdapter adapter;
    private int pageIndex = 0;
    private int totalPage = 0;

    @Override
    protected void initViews() {
        tv_title.setText("历史测量数据");
        tv_right.setText("编辑");
        iv_right.setBackgroundResource(R.drawable.healthedit);
        ptrlv.setOnItemClickListener(this);
        ptrlv.setOnRefreshListener(this);
        ptrlv.setMode(PullToRefreshBase.Mode.BOTH);
        ll_left.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        cb_all.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            ptrlv.setRefreshing();
        }
    };

    @Override
    protected void initDatas() {
        setPresenter(new HistoryDataManager(this));
        adapter = new HistoryDataAdapter(this, dataItemModels, cb_all);
        ptrlv.setAdapter(adapter);
        ptrlv.postDelayed(runnable, 300);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private boolean editOrCompleted = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                setResult(RESULT_OK, getIntent());
                finish();
                break;
            case R.id.fl_right:
                if (dataItemModels.isEmpty()) {
                    break;
                }
                for (HistoryDataItemModel model : dataItemModels) {
                    model.setShow(model.isShow() ? false : true);
                    model.setChecked(false);
                }
                editOrCompleted = dataItemModels.get(0).isShow();
                tv_right.setText(editOrCompleted ? "完成" : "编辑");
                if (editOrCompleted) {
                    ptrlv.setMode(PullToRefreshBase.Mode.DISABLED);
                    ll_footer.setVisibility(View.VISIBLE);
                } else {
                    ptrlv.setMode(PullToRefreshBase.Mode.BOTH);
                    ll_footer.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
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
                if (ids.toString().equals("")) {
                    break;
                }
                dialogShow("正在删除。。。");
                getPresenter().deleteHistoryData(ids.toString().substring(1, ids.length()));
                break;
            case R.id.cb_all:
                for (HistoryDataItemModel model : dataItemModels) {
                    model.setChecked(cb_all.isChecked());
                }
                adapter.notifyDataSetChanged();
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
        for (HistoryDataItemModel model : dataItemModels) {
            model.setShow(false);
            model.setChecked(false);
        }
        editOrCompleted = dataItemModels.get(0).isShow();
        tv_right.setText(editOrCompleted ? "完成" : "编辑");
        if (editOrCompleted) {
            ptrlv.setMode(PullToRefreshBase.Mode.DISABLED);
            ll_footer.setVisibility(View.VISIBLE);
        } else {
            ptrlv.setMode(PullToRefreshBase.Mode.BOTH);
            ll_footer.setVisibility(View.GONE);
        }
        adapter.notifyDataSetChanged();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrlv.setRefreshing();
            }
        }, 300);
    }

    @Override
    public void hidenPull() {
        ptrlv.onRefreshComplete();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex = 1;
        getPresenter().getHistoryDataList(1);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex++;
        if (pageIndex <= totalPage) {
            getPresenter().getHistoryDataList(pageIndex);
        } else {
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
            setResult(RESULT_OK, getIntent());
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}