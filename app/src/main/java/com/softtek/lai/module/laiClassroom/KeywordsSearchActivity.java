package com.softtek.lai.module.laiClassroom;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    @InjectView(R.id.tv_title)
    TextView mTitle;
    private ChaosAdapter chaosAdapter;
    private SearchPresenter presenter;
    private List<SearchModel.ArticleListBean> searchList = new ArrayList<>();

    private int page = 1;
    private static final int LOADCOUNT = 6;
    private int lastVisitableItem;
    private boolean isLoading = false;

    private String searchKey;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initViews() {
        mTitle.setText("搜索");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        chaosAdapter = new ChaosAdapter(this, searchList, "");
        mRecyclerView.setAdapter(chaosAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int count = chaosAdapter.getItemCount();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && count > LOADCOUNT && lastVisitableItem + 1 == count) {
                    if (!isLoading) {
                        isLoading = true;
                        page++;
                        presenter.updateChaosData(page, 6, searchKey);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager llm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                lastVisitableItem = llm.findLastVisibleItemPosition();
            }
        });
    }

    @Override
    protected void initDatas() {
        searchKey = "";
        setPresenter(new SearchPresenter(this));
        presenter = getPresenter();
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    SoftInputUtil.hidden(KeywordsSearchActivity.this);
                    searchKey = String.valueOf(mEditText.getText());
                    chaosAdapter.updateKey(searchKey);
                    presenter.getChaosData(searchKey);
                }
                return false;
            }
        });
    }

    @Override
    public void getData(List<SearchModel.ArticleListBean> data) {
        if (data.size() == 0){
            Toast.makeText(KeywordsSearchActivity.this,"暂无数据",Toast.LENGTH_SHORT).show();
        }
        searchList.clear();
        searchList.addAll(data);
        chaosAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateFail() {
        page--;
        isLoading = false;
        chaosAdapter.notifyItemRemoved(chaosAdapter.getItemCount());
    }

    @Override
    public void updateSuccess(List<SearchModel.ArticleListBean> data) {
        isLoading = false;
        searchList.addAll(data);
        chaosAdapter.notifyItemRemoved(chaosAdapter.getItemCount());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.recycle();
    }

    @OnClick({R.id.tv_cancel, R.id.ll_left})
    public void goBack() {
        this.finish();
    }
}
