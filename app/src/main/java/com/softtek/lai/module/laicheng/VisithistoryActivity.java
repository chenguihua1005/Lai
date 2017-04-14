package com.softtek.lai.module.laicheng;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
public class VisithistoryActivity extends BaseActivity<HistoryVisitorPresenter> implements View.OnClickListener, HistoryVisitorPresenter.HistoryVisitorView {
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
    @InjectView(R.id.ll_nodata)
    RelativeLayout ll_nodata;//searchview
    @InjectView(R.id.et_input)
    EditText et_input;
    @InjectView(R.id.imageView10)
    ImageView imageView10;
    private List<HistoryModel> historyModelList = new ArrayList<>();
    HistoryAdapter adapter;

    @Override
    protected void initViews() {
        tv_title.setText("访客历史记录");
        ll_left.setOnClickListener(this);
        imageView10.setOnClickListener(this);

        et_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (et_input.getText().toString().isEmpty()) {
                    adapter.getFilter().filter(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    protected void initDatas() {
        setPresenter(new HistoryVisitorPresenter(this));
        getPresenter().GetData();
//        ptrlv.setEmptyView(im_nomessage);
        ptrlv.setTextFilterEnabled(true);
        adapter = new HistoryAdapter(this, historyModelList, et_input);
        ptrlv.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.imageView10:
                if (TextUtils.isEmpty(et_input.getText())) {
                    adapter.getFilter().filter(null);  // 清楚ListView的过滤
                } else {
                    adapter.getFilter().filter(et_input.getText().toString()); // 设置ListView的过滤关键词
                }
                break;
        }
    }


    @Override
    public void getInfo(List<HistoryModel> historyModels) {
        if (historyModels.isEmpty()) {
            im_nomessage.setVisibility(View.VISIBLE);
            ptrlv.setVisibility(View.GONE);

        } else {
            historyModelList = historyModels;
            rl_search.setVisibility(View.VISIBLE);
            ptrlv.setEmptyView(ll_nodata);
        }
    }


}
