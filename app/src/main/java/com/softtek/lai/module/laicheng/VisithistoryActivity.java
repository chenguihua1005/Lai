package com.softtek.lai.module.laicheng;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.SearchView;
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

import java.lang.reflect.Field;
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
//    @InjectView(R.id.et_input)
//    SearchView et_input;//searchview
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
                if (et_input.getText().toString().isEmpty())
                {
                    adapter.getFilter().filter(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //searchview
//        et_input.setOnQueryTextListener(new QueryListener());
//        et_input.setIconifiedByDefault(false);
//        if (et_input != null) {
//            try {        //--拿到字节码
//                Class<?> argClass = et_input.getClass();
//                //--指定某个私有属性,mSearchPlate是搜索框父布局的名字
//                Field ownField = argClass.getDeclaredField("mSearchPlate");
//                //--暴力反射,只有暴力反射才能拿到私有属性
//                ownField.setAccessible(true);
//                View mView = (View) ownField.get(et_input);
//                //--设置背景
//                mView.setBackgroundColor(Color.TRANSPARENT);
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//            }}
    }

    @Override
    protected void initDatas() {
        setPresenter(new HistoryVisitorPresenter(this));
        getPresenter().GetData();
        ptrlv.setEmptyView(im_nomessage);
        ptrlv.setTextFilterEnabled(true);
        adapter = new HistoryAdapter(this, historyModelList,historyModelList,et_input);
        ptrlv.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
//                startActivity(new Intent(this,TestActivity.class));
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
        if (historyModels.isEmpty())
        {
            rl_search.setVisibility(View.GONE);

        }
        else {
            historyModelList=historyModels;
//            adapter.notifyDataSetChanged();

        }
    }
    // 搜索文本监听器
    private class QueryListener implements SearchView.OnQueryTextListener {
        // 当内容被提交时执行
        @Override
        public boolean onQueryTextSubmit(String query) {
            return true;
        }

        // 当搜索框内内容发生改变时执行
        @Override
        public boolean onQueryTextChange(String newText) {
            if (TextUtils.isEmpty(newText)) {
                adapter.getFilter().filter(null);  // 清楚ListView的过滤
            } else {
                adapter.getFilter().filter(newText); // 设置ListView的过滤关键词
            }
            return true;
        }
    }


}
