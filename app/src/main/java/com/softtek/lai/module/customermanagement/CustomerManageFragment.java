package com.softtek.lai.module.customermanagement;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.module.customermanagement.adapter.CustomerMenuAdapter;
import com.softtek.lai.module.customermanagement.adapter.TypeFragmentAdapter;
import com.softtek.lai.module.customermanagement.model.CustomerListModel;
import com.softtek.lai.module.customermanagement.presenter.IntendCustomerPresenter;
import com.softtek.lai.module.customermanagement.view.AddCustomerActivity;
import com.softtek.lai.module.customermanagement.view.ClubActivity;
import com.softtek.lai.module.customermanagement.view.IntendCustomerFragment;
import com.softtek.lai.module.customermanagement.view.RegistForCustomerActivity;
import com.softtek.lai.module.customermanagement.view.SearchCustomerActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jessica.zhang on 11/16/2017.
 */

@InjectLayout(R.layout.activity_customer_manage)
public class CustomerManageFragment extends LazyBaseFragment implements View.OnClickListener{
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    @InjectView(R.id.iv_email)
    ImageView iv_email;

    @InjectView(R.id.menu_gv)
    GridView menu_gv;

    @InjectView(R.id.ll_search)
    LinearLayout ll_search;

    private CustomerMenuAdapter menuAdapter;


    @InjectView(R.id.tab)
    TabLayout tab;
    @InjectView(R.id.container)
    ViewPager container;

    List<Fragment> fragments = new ArrayList<>();
    TypeFragmentAdapter adapter;


    @Override
    protected void initViews() {
        tv_title.setText("客户管理");
        ll_left.setVisibility(View.INVISIBLE);
        iv_email.setBackgroundResource(R.drawable.club);

        ll_search.setOnClickListener(this);
        fl_right.setOnClickListener(this);

        fragments.add(IntendCustomerFragment.getInstance());
        fragments.add(IntendCustomerFragment.getInstance());

        adapter = new TypeFragmentAdapter(getChildFragmentManager(), fragments);
        container.setAdapter(adapter);
        tab.setupWithViewPager(container);


    }

    @Override
    protected void initDatas() {
        menuAdapter = new CustomerMenuAdapter(getContext());
        menu_gv.setAdapter(menuAdapter);
        menu_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(getContext(), AddCustomerActivity.class);
                    startActivity(intent);
                } else if (position == 1) {
                    Intent intent = new Intent(getContext(), RegistForCustomerActivity.class);
                    startActivity(intent);
                }
            }
        });


    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:

                break;
            case R.id.ll_search:
                Intent intent = new Intent(getContext(), SearchCustomerActivity.class);
                startActivity(intent);
                break;
            case R.id.fl_right:
                startActivity(new Intent(getActivity(), ClubActivity.class));
        }

    }

}
