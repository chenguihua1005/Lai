package com.softtek.lai.module.laicheng;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.laicheng.adapter.HistoryAdapter;
import com.softtek.lai.module.laicheng.adapter.HistoryTestRecyclerView;
import com.softtek.lai.module.laicheng.model.HistoryModel;
import com.softtek.lai.module.laicheng.model.VisitorInfoModel;
import com.softtek.lai.module.laicheng.model.VisitorModel;
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
    RecyclerView ptrlv;

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
    HistoryTestRecyclerView mAdapter;

    @Override
    protected void initViews() {
        tv_title.setText("访客历史记录");
        ll_left.setOnClickListener(this);
        imageView10.setOnClickListener(this);
        //监听回车事件

        et_input.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyEvent.KEYCODE_ENTER == keyCode && keyEvent.ACTION_DOWN == keyEvent.getAction()) {
                    if (TextUtils.isEmpty(et_input.getText())) {
                        adapter.getFilter().filter(null);  // 清除adapter的过滤
                    } else {
                        adapter.getFilter().filter(et_input.getText().toString()); // 设置adapt的过滤关键词
                    }
                    return true;
                }
                return false;
            }

        });

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
//        ptrlv.setTextFilterEnabled(true);
        adapter = new HistoryAdapter(this, historyModelList, et_input);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        ptrlv.setLayoutManager(layoutManager);
        mAdapter = new HistoryTestRecyclerView(historyModelList, new HistoryTestRecyclerView.ItemListener() {
            @Override
            public void onItemClick(HistoryModel item) {

            }
        });
        ptrlv.setAdapter(mAdapter);
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
            historyModelList.clear();
            historyModelList.addAll(historyModels);
//            historyModelList = historyModels;
            rl_search.setVisibility(View.VISIBLE);
            mAdapter.notifyDataSetChanged();
//            ptrlv.setEmptyView(ll_nodata);
        }
    }


}
