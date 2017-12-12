package com.softtek.lai.module.customermanagement;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.customermanagement.adapter.CustomerMenuAdapter;
import com.softtek.lai.module.customermanagement.adapter.TypeFragmentAdapter;
import com.softtek.lai.module.customermanagement.model.ClubAuthorityModel;
import com.softtek.lai.module.customermanagement.service.CustomerService;
import com.softtek.lai.module.customermanagement.view.AddCustomerActivity;
import com.softtek.lai.module.customermanagement.view.ClubActivity;
import com.softtek.lai.module.customermanagement.view.CreateClubActivity;
import com.softtek.lai.module.customermanagement.view.IntendCustomerFragment;
import com.softtek.lai.module.customermanagement.view.MarketerListFragment;
import com.softtek.lai.module.customermanagement.view.RegistForCustomerActivity;
import com.softtek.lai.module.customermanagement.view.SearchCustomerActivity;
import com.softtek.lai.module.login.view.LoginActivity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

/**
 * Created by jessica.zhang on 11/16/2017.
 */

@InjectLayout(R.layout.activity_customer_manage)
public class CustomerManageFragment extends LazyBaseFragment implements View.OnClickListener {
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

    @InjectView(R.id.lin_is_vr)
    LinearLayout lin_is_vr;

    @InjectView(R.id.ll_content)
    LinearLayout ll_content;

    @InjectView(R.id.but_login)
    Button but_login;

    private CustomerMenuAdapter menuAdapter;


    @InjectView(R.id.tab)
    TabLayout tab;
    @InjectView(R.id.container)
    ViewPager container;

    List<Fragment> fragments = new ArrayList<>();
    TypeFragmentAdapter adapter;

    boolean isLogin = false;

    @Override
    protected void initViews() {
        tv_title.setText("客户管理");
        ll_left.setVisibility(View.INVISIBLE);

        String token = UserInfoModel.getInstance().getToken();
        String certification = UserInfoModel.getInstance().getUser().getCertification();
        //判断token是否为空
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(certification)) {
            //token为空，游客模式显示立即登陆页面
            isLogin = false;
            lin_is_vr.setVisibility(View.VISIBLE);
            ll_content.setVisibility(View.GONE);
            but_login.setOnClickListener(this);
        } else {
            //token不为空，非游客模式，隐藏立即登陆页面
            isLogin = true;
            lin_is_vr.setVisibility(View.GONE);
            ll_content.setVisibility(View.VISIBLE);

            iv_email.setBackgroundResource(R.drawable.club);

            ll_search.setOnClickListener(this);
            fl_right.setOnClickListener(this);

            fragments.add(IntendCustomerFragment.getInstance());
            fragments.add(MarketerListFragment.getInstance());

            adapter = new TypeFragmentAdapter(getChildFragmentManager(), fragments);
            container.setAdapter(adapter);
            tab.setupWithViewPager(container);
        }


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
                judgeClubAuthority();
                break;
            case R.id.but_login:
                Intent toLoginIntent = new Intent(getContext(), LoginActivity.class);
                toLoginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                toLoginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(toLoginIntent);
                break;

        }

    }


    private void judgeClubAuthority() {
        CustomerService service = ZillaApi.NormalRestAdapter.create(CustomerService.class);
        service.getClubAuthority(UserInfoModel.getInstance().getToken(), new Callback<ResponseData<ClubAuthorityModel>>() {
            @Override
            public void success(ResponseData<ClubAuthorityModel> responseData, Response response) {
                int status = responseData.getStatus();
                if (200 == status) {
                    ClubAuthorityModel model = responseData.getData();
                    boolean HasClub = model.isHasClub();//是否有俱乐部
                    boolean HasAuthorityOfCreate = model.isHasAuthorityOfCreate();
                    if (HasClub) {
                        Intent intent = new Intent(getContext(), ClubActivity.class);
                        intent.putExtra("maki", HasAuthorityOfCreate);
                        startActivity(intent);
                    } else if (HasAuthorityOfCreate) {
                        startActivity(new Intent(getActivity(), CreateClubActivity.class));
                    }
                } else {
                    Util.toastMsg(responseData.getMsg());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                ZillaApi.dealNetError(error);
            }
        });

    }

}
