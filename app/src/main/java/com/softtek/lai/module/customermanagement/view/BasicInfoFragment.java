package com.softtek.lai.module.customermanagement.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.module.customermanagement.model.BasicInfoModel;
import com.softtek.lai.module.customermanagement.model.BasicModel;
import com.softtek.lai.module.customermanagement.presenter.BasicInfoPresenter;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jessica.zhang on 11/17/2017.
 */


@InjectLayout(R.layout.fragment_basicinfo)
public class BasicInfoFragment extends LazyBaseFragment<BasicInfoPresenter> implements BasicInfoPresenter.BasicInfoCallBack {
    @InjectView(R.id.tv_name)
    TextView tv_name;
    @InjectView(R.id.tv_other)
    TextView tv_other;
    @InjectView(R.id.tv_birth)
    TextView tv_birth;
    @InjectView(R.id.tv_zhicheng)
    TextView tv_zhicheng;
    @InjectView(R.id.tv_mobile)
    TextView tv_mobile;
    @InjectView(R.id.tv_cn)
    TextView tv_cn;
    @InjectView(R.id.tv_angle)
    TextView tv_angle;


    private static String mobile = "";

    public static Fragment getInstance(String mobileNum) {
        Fragment fragment = new BasicInfoFragment();
        mobile = mobileNum;
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initViews() {


    }

    @Override
    protected void initDatas() {
        Log.i("BasicInfoFragment", "mobile = " + mobile);
        setPresenter(new BasicInfoPresenter(this));
        getPresenter().getCustomerBasicInfo(mobile);

    }

    @Override
    public void getBasicInfo(BasicInfoModel model) {
        if (model != null) {
            BasicModel basicModel = model.getBasics();
            tv_name.setText(basicModel.getName());
            tv_other.setText(basicModel.getGender().equals("0") ? "男" : " 女" + "|" + basicModel.getHeight());
            tv_birth.setText(basicModel.getBirthDay());
            tv_zhicheng.setText(basicModel.getUserRole());
            tv_mobile.setText(basicModel.getMobile());
            tv_cn.setText(basicModel.getCertification());
            tv_angle.setText(basicModel.getAngel());
        }
    }
}
