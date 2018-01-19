package com.softtek.lai.module.healthyreport;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.healthyreport.adapter.HistoryDataPKAdapter;
import com.softtek.lai.module.healthyreport.model.HistoryDataItemModel;
import com.softtek.lai.module.healthyreport.model.HistoryDataModel;
import com.softtek.lai.module.healthyreport.presenter.HistoryDataManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

/**
 * Created by jessica.zhang on 11/8/2017.
 */

@InjectLayout(R.layout.activity_pkselect_data)
public class PKSelectActivity extends BaseActivity<HistoryDataManager> implements View.OnClickListener, AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener2<ListView>, HistoryDataManager.HistoryDataManagerCallback {
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


    private List<HistoryDataItemModel> dataItemModels = new ArrayList<>();
    private HistoryDataPKAdapter adapter;
    private int pageIndex = 0;
    private int totalPage = 0;
    private int type = 1;//默认莱称选中
    private long accountId;

    private int number = 0;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x0011:
                    int count = (int) msg.obj;
                    number = count;
                    tv_tip.setText("开始对比(已选中" + number + "条)");
                    adapter.notifyDataSetChanged();
                    break;
                case 0x0012:
                    int count2 = (int) msg.obj;
                    number = count2;
                    tv_tip.setText("开始对比(已选中" + number + "条)");
                    break;
                case 0x0013:
                    int count3 = (int) msg.obj;
                    number = count3;
                    tv_tip.setText("开始对比(已选中" + number + "条)");
                    break;

            }

            if (number <= 0) {
                tv_tip.setBackgroundColor(getResources().getColor(R.color.btn_grey_true));
            } else {
                tv_tip.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
        }
    };


    @Override
    protected void initViews() {
        tv_title.setText("请选择两条记录");

        ptrlv.setOnItemClickListener(this);
        ptrlv.getRefreshableView().setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        ptrlv.setOnRefreshListener(this);
        ptrlv.setMode(PullToRefreshBase.Mode.BOTH);

        ll_left.setOnClickListener(this);
        ll_footer.setOnClickListener(this);

    }

    @Override
    protected void initDatas() {
        setPresenter(new HistoryDataManager(this));
        adapter = new HistoryDataPKAdapter(this, dataItemModels, handler);
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
            case R.id.footer:
                if (number < 2) {
                    Util.toastMsg("请先选两条记录再开始比较");
                } else {
                    HistoryDataItemModel model1 = null;
                    HistoryDataItemModel model2 = null;

                    int count = 0;
                    for (HistoryDataItemModel itemModel : dataItemModels) {
                        if (itemModel.isChecked()) {
                            count++;
                            if (count == 1) {
                                model1 = itemModel;
                            } else if (count == 2) {
                                model2 = itemModel;
                                break;
                            }
                        }
                    }

                    String path = AddressManager.get("photoBase", "");
                    String url = path + "/MeasuredRecord/DataComparison?AcInfoId1=" + model1.getDataModel().getRecordId() + "&AcInfoId2=" + model2.getDataModel().getRecordId() + "&Type1=" + model1.getDataModel().getSourceType() + "&Type2=" + model2.getDataModel().getSourceType();

                    Intent intent = new Intent(PKSelectActivity.this, HistoryPKDetailActivity.class);
                    intent.putExtra("url", url);
                    startActivity(intent);
                }

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
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

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
