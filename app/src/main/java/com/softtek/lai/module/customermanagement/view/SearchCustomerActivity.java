package com.softtek.lai.module.customermanagement.view;

import android.content.Intent;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.customermanagement.adapter.CustomerAdapter;
import com.softtek.lai.module.customermanagement.model.CustomerModel;
import com.softtek.lai.module.customermanagement.model.SearchCustomerOuterModel;
import com.softtek.lai.module.customermanagement.presenter.SearchCustomerPresenter;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.utils.SoftInputUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

import static java.security.AccessController.getContext;

/**
 * Created by jessica.zhang on 11/21/2017.   SearchContactActivity
 */

@InjectLayout(R.layout.activity_search_customer)
public class SearchCustomerActivity extends BaseActivity<SearchCustomerPresenter> implements View.OnClickListener, AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener2<ListView>, SearchCustomerPresenter.SearchCustomerCallBack, TextWatcher {
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.edit)
    EditText edit;
    @InjectView(R.id.tv_cancel)
    TextView tv_cancel;
    @InjectView(R.id.plv_audit)
    PullToRefreshListView plv_audit;

    @InjectView(R.id.pb)
    ProgressBar pb;

    private CustomerAdapter customerAdapter;
    private List<CustomerModel> modelList = new ArrayList<CustomerModel>();
    private List<CustomerModel> modelList_saved = new ArrayList<CustomerModel>();//查询结果先保存一份

    private int pageindex = 1;
    private int pageSize = 10;

    @Override
    protected void initViews() {
        tv_title.setText("查找客户");
        edit.setHint("请输入手机号或姓名查找客户");

        setPresenter(new SearchCustomerPresenter(this));
        customerAdapter = new CustomerAdapter(this, modelList);
        plv_audit.setAdapter(customerAdapter);

        plv_audit.setOnItemClickListener(this);
        plv_audit.setMode(PullToRefreshBase.Mode.BOTH);
        plv_audit.setOnRefreshListener(this);
//        plv_audit.setEmptyView(im_nomessage);
        ILoadingLayout startLabelse = plv_audit.getLoadingLayoutProxy(true, false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setRefreshingLabel("正在刷新数据");// 刷新时
        startLabelse.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        ILoadingLayout endLabelsr = plv_audit.getLoadingLayoutProxy(false, true);
        endLabelsr.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
        endLabelsr.setRefreshingLabel("正在加载数据");
        endLabelsr.setReleaseLabel("松开立即加载");// 下来达到一定距离时，显示的提示


        tv_cancel.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);

        overridePendingTransition(0, 0);
        edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //判断是不是搜索
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                     /*隐藏软键盘*/
                    SoftInputUtil.hidden(SearchCustomerActivity.this);
                    UserModel user = UserInfoModel.getInstance().getUser();
                    if (edit.length() == 0) {
                        edit.requestFocus();
                        edit.setError(Html.fromHtml("<font color=#FFFFFF>请输入姓名/手机号</font>"));
                        return false;
                    } else if (edit.getText().toString().equals(user.getMobile())) {
                        edit.requestFocus();
                        edit.setError(Html.fromHtml("<font color=#FFFFFF>无此用户</font>"));
                        return false;
                    }
                    pb.setVisibility(View.VISIBLE);

                    String input = edit.getText().toString().trim();
                    pageindex = 1;
                    pageSize = 10;
                    modelList.clear();
                    getPresenter().searchCustomer(input, pageindex, pageSize);
                    return true;
                }

                return false;
            }
        });
        edit.addTextChangedListener(this);


    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                SoftInputUtil.hidden(SearchCustomerActivity.this);
                finish();
                overridePendingTransition(0, 0);
                break;
            case R.id.ll_left:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, CustomerDetailActivity.class);
        CustomerModel model = modelList.get(position - 1);
        intent.putExtra("mobile", model.getMobile());
        intent.putExtra("isRegistered", model.isTag());
        startActivity(intent);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageindex = 1;
        modelList.clear();
        String input = edit.getText().toString().trim();
        getPresenter().searchCustomer(input, pageindex, pageSize);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageindex++;
        String input = edit.getText().toString().trim();
        getPresenter().searchCustomer(input, pageindex, pageSize);

    }

    @Override
    public void searchCustomerCallBack(SearchCustomerOuterModel model) {
        if (model.getItems() != null) {
            modelList.addAll(model.getItems());
        }

        modelList_saved.clear();
        modelList_saved.addAll(modelList);
        customerAdapter.notifyDataSetChanged();
        if (modelList == null || modelList.size() <= 0) {
            Util.toastMsg("暂无数据");
        }
    }

    @Override
    public void disMissProgressBar() {
        try {
            pb.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hidenLoading() {
        plv_audit.onRefreshComplete();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    boolean isOut;

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        isOut = true;
    }

    @Override
    public void afterTextChanged(Editable s) {
        isOut = false;
        String text = s.toString();
        modelList.clear();
        if (text.trim().length() == 0) {
            customerAdapter.notifyDataSetChanged();
            return;
        }

        for (CustomerModel model : modelList_saved) {
            if (model.getName().contains(text) || model.getMobile().contains(text)) {
                modelList.add(model);
            }
        }
        customerAdapter.notifyDataSetChanged();

    }
}
