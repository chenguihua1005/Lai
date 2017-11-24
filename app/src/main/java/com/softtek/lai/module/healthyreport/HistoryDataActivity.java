package com.softtek.lai.module.healthyreport;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.IdRes;
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
import android.widget.RadioGroup;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.healthyreport.adapter.HistoryDataAdapter;
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
public class HistoryDataActivity extends BaseActivity<HistoryDataManager> implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, View.OnClickListener
        , HistoryDataManager.HistoryDataManagerCallback, PullToRefreshBase.OnRefreshListener2<ListView>
        , RadioGroup.OnCheckedChangeListener {

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

    @InjectView(R.id.rg)
    RadioGroup rg;

    @InjectView(R.id.pk_btn)
    TextView pk_btn;


    private List<HistoryDataItemModel> dataItemModels = new ArrayList<>();
    private HistoryDataAdapter adapter;
    private int pageIndex = 0;
    private int totalPage = 0;
    private int type = 0;//默认莱称选中
    private long accountId;

    @Override
    protected void initViews() {
        rg.check(R.id.rb_laichen);
        ptrlv.setOnItemClickListener(this);
        ptrlv.getRefreshableView().setOnItemLongClickListener(this);
        ptrlv.getRefreshableView().setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        ptrlv.setOnRefreshListener(this);
        ptrlv.setMode(PullToRefreshBase.Mode.BOTH);
        ll_left.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        cb_all.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        tv_cancle.setOnClickListener(this);
        rg.setOnCheckedChangeListener(this);
        pk_btn.setOnClickListener(this);

    }

    @Override
    protected void initDatas() {
        setPresenter(new HistoryDataManager(this));
        adapter = new HistoryDataAdapter(this, dataItemModels, cb_all);
        ptrlv.setAdapter(adapter);
        pageIndex = 1;
        //默认加载莱称的数据
        accountId = getIntent().getLongExtra("accountId", 0);
        getPresenter().getHistoryDataList(0, pageIndex, accountId, false);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (isEditor) {
            return;
        }
        //点击进入健康报告
        HistoryDataModel.RecordsBean bean = dataItemModels.get(position - 1).getDataModel();
        Intent intent = new Intent(this, HealthyReportActivity.class);
        intent.putExtra("reportId", bean.getRecordId());
        intent.putExtra("since", type == 0 ? HealthyReportActivity.SINCE_LAICHEN : HealthyReportActivity.SINCE_OTHER);
        intent.putExtra("isVisitor", HealthyReportActivity.NON_VISITOR);
        startActivity(intent);
    }

    boolean isEditor;

    private void openEditor(int position) {
        for (int i = 0, j = dataItemModels.size(); i < j; i++) {
            HistoryDataItemModel model = dataItemModels.get(i);
            model.setChecked(position == i);
            model.setShow(isEditor);
        }

        ptrlv.setMode(PullToRefreshBase.Mode.DISABLED);
        ll_footer.setVisibility(View.VISIBLE);
        adapter.notifyDataSetChanged();
        if (empty == null) {
            empty = new View(this);
            empty.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(this, 48)));
        }
        ptrlv.getRefreshableView().addFooterView(empty);

    }

    private void closeEditor() {
        for (int i = 0, j = dataItemModels.size(); i < j; i++) {
            HistoryDataItemModel model = dataItemModels.get(i);
            model.setChecked(false);
            model.setShow(false);
        }
        ptrlv.setMode(PullToRefreshBase.Mode.BOTH);
        ll_footer.setVisibility(View.GONE);
        adapter.notifyDataSetChanged();
        ptrlv.getRefreshableView().removeFooterView(empty);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        isEditor = !isEditor;
        if (isEditor) {//长按出现底层菜单
            openEditor(position - 1);
        } else {
            closeEditor();
        }
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
                Intent intent2 = new Intent(this, HealthEntryActivity.class);
//                intent2.putExtra("type", type);
                startActivityForResult(intent2, 0x0001);//如果当前选中的是全部（type = 1），添加成功回来需要刷新列表
                break;
            case R.id.tv_delete:
                //提交删除选项
                StringBuffer ids = new StringBuffer();
                for (int i = 0; i < dataItemModels.size(); i++) {
                    HistoryDataItemModel model = dataItemModels.get(i);
                    if (model.isChecked()) {
                        ids.append(model.getDataModel().getRecordId());
                        ids.append(",");
                    }
                }
                if (TextUtils.isEmpty(ids)) {
                    break;
                }
                getPresenter().deleteHistoryData(type, ids.deleteCharAt(ids.length() - 1).toString());
                break;
            case R.id.cb_all:
                for (HistoryDataItemModel model : dataItemModels) {
                    model.setChecked(cb_all.isChecked());
                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.tv_cancle:
                closeEditor();
                break;
            case R.id.pk_btn:
                Intent intent = new Intent(HistoryDataActivity.this, PKSelectActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0x0001 && type == 1) {
                pageIndex = 1;
                getPresenter().getHistoryDataList(type, pageIndex, accountId, true);
            }
        }
    }

    @Override
    public void historyDataCallback(HistoryDataModel model) {
        if (model != null) {
            totalPage = model.getTotalPages();
            List<HistoryDataModel.RecordsBean> datas = model.getRecords();
            if (pageIndex == 1) {
                dataItemModels.clear();
            }
            if (datas != null && !datas.isEmpty()) {
                for (HistoryDataModel.RecordsBean data : datas) {
                    dataItemModels.add(new HistoryDataItemModel(false, false, data));
                }
            } else {
                pageIndex = --pageIndex < 1 ? 1 : pageIndex;
            }
            adapter.notifyDataSetChanged();
        } else {
            dataItemModels.clear();
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void deleteResult() {
        Iterator<HistoryDataItemModel> its = dataItemModels.iterator();
        while (its.hasNext()) {
            HistoryDataItemModel model = its.next();
            if (model.isChecked()) {
                its.remove();
            } else {
                model.setShow(false);
            }
        }
        isEditor = false;
        ptrlv.setMode(PullToRefreshBase.Mode.BOTH);
        ll_footer.setVisibility(View.GONE);
        adapter.notifyDataSetChanged();
        ptrlv.getRefreshableView().removeFooterView(empty);
    }

    @Override
    public void hidenPull() {
        ptrlv.onRefreshComplete();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex = 1;
        getPresenter().getHistoryDataList(type, pageIndex, accountId, true);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex++;
        if (pageIndex <= totalPage) {
            getPresenter().getHistoryDataList(type, pageIndex, accountId, true);
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
            if (isEditor) {
                isEditor = false;
                closeEditor();
                return true;
            }
            return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        if (checkedId == R.id.rb_laichen) {
            type = 0;
        } else if (checkedId == R.id.rb_all) {
            type = 1;
        } else if (checkedId == R.id.rb_fuce) {//复测
            type = 2;
        }
        pageIndex = 1;
        getPresenter().getHistoryDataList(type, pageIndex, accountId, false);
    }
}
