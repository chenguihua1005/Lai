package com.softtek.lai.module.customermanagement;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.module.customermanagement.adapter.CustomerMenuAdapter;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jessica.zhang on 11/16/2017.
 */

@InjectLayout(R.layout.activity_customer_manage)
public class CustomerManageFragment extends LazyBaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener2<ListView> {
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.fl_right)
    FrameLayout fl_right;

    @InjectView(R.id.menu_gv)
    GridView menu_gv;
    private CustomerMenuAdapter menuAdapter;

    @InjectView(R.id.plv_audit)
    PullToRefreshListView plv_audit;
    @InjectView(R.id.ll_nomessage)
    RelativeLayout im_nomessage;


    @Override
    protected void initViews() {
        tv_title.setText("客户管理");
        ll_left.setVisibility(View.GONE);

        plv_audit.setOnItemClickListener(this);
        plv_audit.setMode(PullToRefreshBase.Mode.BOTH);
        plv_audit.setOnRefreshListener(this);
        plv_audit.setEmptyView(im_nomessage);
        ILoadingLayout startLabelse = plv_audit.getLoadingLayoutProxy(true, false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setRefreshingLabel("正在刷新数据");// 刷新时
        startLabelse.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        ILoadingLayout endLabelsr = plv_audit.getLoadingLayoutProxy(false, true);
        endLabelsr.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
        endLabelsr.setRefreshingLabel("正在加载数据");
        endLabelsr.setReleaseLabel("松开立即加载");// 下来达到一定距离时，显示的提示


    }

    @Override
    protected void initDatas() {
        menuAdapter = new CustomerMenuAdapter(getContext());
        menu_gv.setAdapter(menuAdapter);


    }

    @Override
    protected void lazyLoad() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                plv_audit.setRefreshing();
            }
        }, 300);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:

                break;


        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

    }
}
