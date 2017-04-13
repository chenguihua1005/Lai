package com.softtek.lai.module.laicheng;

import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.laicheng.adapter.HistoryAdapter;
import com.softtek.lai.module.laicheng.model.HistoryModel;
import com.softtek.lai.module.laicheng.presenter.HistoryVisitorPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_visithistory)
public class VisithistoryActivity extends BaseActivity<HistoryVisitorPresenter> implements View.OnClickListener, HistoryVisitorPresenter.HistoryVisitorView{
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.ptrlv)
    ListView ptrlv;

    @InjectView(R.id.ll_nomessage)
    RelativeLayout im_nomessage;
    @InjectView(R.id.rl_search)
    RelativeLayout rl_search;
    @InjectView(R.id.et_input)
    SearchView.SearchAutoComplete et_input;
    private List<HistoryModel> historyModelList = new ArrayList<>();
    private List<HistoryModel> historyModes = new ArrayList<>();
    private HistoryAdapter adapter;

    @Override
    protected void initViews() {
        tv_title.setText("访客历史记录");
        ll_left.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        setPresenter(new HistoryVisitorPresenter(this));
        getPresenter().GetData();
        ptrlv.setEmptyView(im_nomessage);
        ptrlv.setTextFilterEnabled(true);
        adapter = new HistoryAdapter(this, historyModelList,et_input);
        ptrlv.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
        }
    }


    @Override
    public void getInfo(List<HistoryModel> historyModels) {
        if (historyModels.isEmpty())
        {
            rl_search.setVisibility(View.GONE);

        }
        else {
            historyModelList.addAll(historyModels);
        }
    }
}
