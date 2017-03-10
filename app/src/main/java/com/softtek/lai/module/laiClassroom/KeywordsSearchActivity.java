package com.softtek.lai.module.laiClassroom;

import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.laiClassroom.adapter.ChaosAdapter;
import com.softtek.lai.module.laiClassroom.model.SearchModel;
import com.softtek.lai.module.laiClassroom.presenter.SearchPresenter;
import com.softtek.lai.utils.SoftInputUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_keywords_search)
public class KeywordsSearchActivity extends BaseActivity<SearchPresenter> implements SearchPresenter.SearchView {
    @InjectView(R.id.rv_search_info)
    RecyclerView mRecyclerView;
    @InjectView(R.id.edt_search)
    EditText mEditText;

    private ChaosAdapter chaosAdapter;
    private SearchPresenter presenter;
    private List<SearchModel> modelList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        modelList = new ArrayList<>();
        chaosAdapter = new ChaosAdapter(modelList, new ChaosAdapter.ItemListener() {
            @Override
            public void onItemClick(SearchModel item, int position) {
                Log.d("position", String.valueOf(position));
            }
        });
        mRecyclerView.setAdapter(chaosAdapter);
    }

    @Override
    protected void initDatas() {
        setPresenter(new SearchPresenter(this));
        presenter = getPresenter();
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    SoftInputUtil.hidden(KeywordsSearchActivity.this);
                    presenter.getChaosData();
                }
                return false;
            }
        });
    }

    @Override
    public void getData(List<SearchModel> data) {
        modelList.clear();
        modelList.addAll(data);
        chaosAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.recycle();
    }

    @OnClick(R.id.tv_cancel)
    public void goBack(){
        this.finish();
    }
}
