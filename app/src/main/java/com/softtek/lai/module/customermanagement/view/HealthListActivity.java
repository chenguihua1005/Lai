package com.softtek.lai.module.customermanagement.view;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.customermanagement.adapter.HealthListAdapter;
import com.softtek.lai.module.healthyreport.HealthEntryActivity;
import com.softtek.lai.module.healthyreport.HealthyReportActivity;
import com.softtek.lai.module.healthyreport.model.HistoryDataItemModel;
import com.softtek.lai.module.healthyreport.model.HistoryDataModel;
import com.softtek.lai.module.healthyreport.presenter.HistoryDataManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jessica.zhang on 11/8/2017.
 */

@InjectLayout(R.layout.activity_pkselect_data)
public class HealthListActivity extends BaseActivity<HistoryDataManager> implements View.OnClickListener, AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener2<ListView>, HistoryDataManager.HistoryDataManagerCallback {
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.ptrlv)
    PullToRefreshListView ptrlv;
    @InjectView(R.id.footer)
    LinearLayout ll_footer;

    @InjectView(R.id.tv_tip)
    TextView tv_tip;

    @InjectView(R.id.iv_email)
    ImageView iv_email;
    @InjectView(R.id.fl_right)
    FrameLayout fl_rightl;


    private List<HistoryDataItemModel> dataItemModels = new ArrayList<>();
    private HealthListAdapter adapter;
    private int pageIndex = 0;
    private int totalPage = 0;
    private int type = 1;//默认莱称选中
    private long accountId;

    private int number = 0;


    @Override
    protected void initViews() {
        tv_title.setText("健康记录");

        iv_email.setImageResource(R.drawable.h_editor_add);
        ll_footer.setVisibility(View.GONE);

        ptrlv.setOnItemClickListener(this);
        ptrlv.getRefreshableView().setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        ptrlv.setOnRefreshListener(this);
        ptrlv.setMode(PullToRefreshBase.Mode.BOTH);

        ll_left.setOnClickListener(this);
        ll_footer.setOnClickListener(this);
        fl_rightl.setOnClickListener(this);

    }

    @Override
    protected void initDatas() {
        setPresenter(new HistoryDataManager(this));
        adapter = new HealthListAdapter(this, dataItemModels);
        ptrlv.setAdapter(adapter);
        pageIndex = 1;
        //默认加载莱称的数据
        accountId = getIntent().getLongExtra("accountId", 0);
        getPresenter().getHistoryDataList(type, pageIndex, accountId, false);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.fl_right:
                Intent intent = new Intent(this, HealthEntryActivity.class);
                intent.putExtra("from", "CustomerDeitail");
                intent.putExtra("accountId",accountId);
                startActivity(intent);
                break;
        }

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
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        //点击进入健康报告
        HistoryDataModel.RecordsBean bean = dataItemModels.get(position - 1).getDataModel();
        Intent intent = new Intent(this, HealthyReportActivity.class);
        intent.putExtra("reportId", bean.getRecordId());
//        intent.putExtra("since", type == 0 ? HealthyReportActivity.SINCE_LAICHEN : HealthyReportActivity.SINCE_OTHER);
        intent.putExtra("since", type == bean.getSourceType() ? HealthyReportActivity.SINCE_LAICHEN : HealthyReportActivity.SINCE_OTHER);

        intent.putExtra("isVisitor", HealthyReportActivity.NON_VISITOR);
        startActivity(intent);
    }

    @Override
    public void historyDataCallback(HistoryDataModel model,int type) {
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

    }

    @Override
    public void hidenPull() {
        ptrlv.onRefreshComplete();
    }
}
