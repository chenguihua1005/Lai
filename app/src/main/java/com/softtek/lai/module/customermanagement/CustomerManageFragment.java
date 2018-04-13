package com.softtek.lai.module.customermanagement;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.softtek.lai.module.customermanagement.view.GymClubActivity;
import com.softtek.lai.module.customermanagement.view.IntendCustomerFragment;
import com.softtek.lai.module.customermanagement.view.MarketerListFragment;
import com.softtek.lai.module.customermanagement.view.RegistForCustomerActivity;
import com.softtek.lai.module.customermanagement.view.SearchCustomerActivity;
import com.softtek.lai.module.home.view.ValidateCertificationActivity;
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

    @InjectView(R.id.tv_tip)
    TextView tv_tip;

    private CustomerMenuAdapter menuAdapter;


    @InjectView(R.id.tab)
    TabLayout tab;
    @InjectView(R.id.container)
    ViewPager container;

    List<Fragment> fragments = new ArrayList<>();
    TypeFragmentAdapter adapter;

    boolean isLogin = false;
    String token = "";
    String certification = "";

    boolean HasClub;
    boolean HasAuthorityOfCreate;
    private boolean isGoToClub = false;
    IntendCustomerFragment intendCustomerFragment;
    MarketerListFragment marketerListFragment;
    private TabLayout.Tab mTabOne;
    private TabLayout.Tab mTabTwo;

    @Override
    protected void initViews() {
        tv_title.setText("客户管理");
        ll_left.setVisibility(View.INVISIBLE);

        token = UserInfoModel.getInstance().getToken();
        certification = UserInfoModel.getInstance().getUser().getCertification();
        //判断token是否为空
        if (StringUtils.isEmpty(token)) {
            //token为空，游客模式显示立即登陆页面
            isLogin = false;
            lin_is_vr.setVisibility(View.VISIBLE);
            ll_content.setVisibility(View.GONE);
            but_login.setOnClickListener(this);
        } else if (StringUtils.isEmpty(certification)) {
            lin_is_vr.setVisibility(View.VISIBLE);
            ll_content.setVisibility(View.GONE);

            tv_tip.setText("身份认证后，才能使用更多功能哦");
            but_login.setText("前往认证");
            but_login.setOnClickListener(this);
        } else {
            //token不为空，非游客模式，隐藏立即登陆页面
            isLogin = true;
            lin_is_vr.setVisibility(View.GONE);
            ll_content.setVisibility(View.VISIBLE);

            iv_email.setBackgroundResource(R.drawable.club);

            intendCustomerFragment = (IntendCustomerFragment)IntendCustomerFragment.getInstance(new IntendCustomerFragment.LoadCompleteListener() {
                @Override
                public void onLoadCompleteListener(int count) {
                    mTabOne.setText("意向客户(" + count + ")");
                }
            });
            marketerListFragment = (MarketerListFragment)MarketerListFragment.getInstance(new MarketerListFragment.LoadCompleteListener() {
                @Override
                public void onLoadCompleteListener(int count) {
                    mTabTwo.setText("市场人员(" + count + ")");
                }
            });

            fragments.add(intendCustomerFragment);
            fragments.add(marketerListFragment);

            adapter = new TypeFragmentAdapter(getChildFragmentManager(), fragments);
            container.setAdapter(adapter);
            tab.setupWithViewPager(container);
            mTabOne = tab.getTabAt(0);
            mTabTwo = tab.getTabAt(1);
            mTabOne.setText("意向客户");
            mTabTwo.setText("市场人员");
        }

        ll_search.setOnClickListener(this);
        fl_right.setOnClickListener(this);


        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver, new IntentFilter(ALREADY_CERTIFICATION));

    }

    @Override
    protected void initDatas() {
        menuAdapter = new CustomerMenuAdapter(getContext(), HasClub);
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
                } else if (position == 3) {
                    if (HasClub) {
                        Intent intent = new Intent(getContext(), GymClubActivity.class);
                        startActivity(intent);
                    }
                }else if (position == 2){
                    Toast.makeText(getContext(),"正在开发中...",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    @Override
    protected void lazyLoad() {
        setPrepared(false);
        judgeClubAuthority();//获取用户权限
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
                isGoToClub = true;
                judgeClubAuthority();
                break;
            case R.id.but_login:
                if (StringUtils.isEmpty(token)) {
                    Intent toLoginIntent = new Intent(getContext(), LoginActivity.class);
                    toLoginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    toLoginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(toLoginIntent);
                } else if (StringUtils.isEmpty(certification)) {
                    startActivity(new Intent(getContext(), ValidateCertificationActivity.class));
                }
                break;

        }

    }

    private void dealWithClub() {
        if (HasClub) {
            Intent intent = new Intent(getContext(), ClubActivity.class);
            intent.putExtra("maki", HasAuthorityOfCreate);//是否有权限修改俱乐部
            startActivity(intent);
        } else if (HasAuthorityOfCreate) {
            Intent intent = new Intent(getContext(), CreateClubActivity.class);
            intent.putExtra("maki_isfirst", true);//是不是没有俱乐部第一次创建
            startActivity(intent);
        }else {
            Toast.makeText(getActivity(),"您还没有加入俱乐部, 请联系店主邀请您加入吧",Toast.LENGTH_SHORT).show();
        }
    }


    public void judgeClubAuthority() {
        if (UserInfoModel.getInstance().getToken().equals("")){
            return;
        }
        CustomerService service = ZillaApi.NormalRestAdapter.create(CustomerService.class);
        service.getClubAuthority(UserInfoModel.getInstance().getToken(), new Callback<ResponseData<ClubAuthorityModel>>() {
            @Override
            public void success(ResponseData<ClubAuthorityModel> responseData, Response response) {
                int status = responseData.getStatus();
                if (200 == status) {
                    ClubAuthorityModel model = responseData.getData();
                    HasClub = model.isHasClub();//是否有俱乐部
                    HasAuthorityOfCreate = model.isHasAuthorityOfCreate();

                    menuAdapter.updateData(HasClub);
                    if (isGoToClub){
                        isGoToClub = false;
                        dealWithClub();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
    }

    public static final String ALREADY_CERTIFICATION = "ALREADY_CERTIFICATION";
    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction().equals(ALREADY_CERTIFICATION)) {
                isLogin = true;
                lin_is_vr.setVisibility(View.GONE);
                ll_content.setVisibility(View.VISIBLE);

                iv_email.setBackgroundResource(R.drawable.club);

//                ll_search.setOnClickListener(this);
//                fl_right.setOnClickListener(this);

                fragments.add(intendCustomerFragment);
                fragments.add(marketerListFragment);

                adapter = new TypeFragmentAdapter(getChildFragmentManager(), fragments);
                container.setAdapter(adapter);
                tab.setupWithViewPager(container);
            }
        }
    };
}
