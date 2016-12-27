package com.softtek.lai.module.bodygame3.home.view;

import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.ggx.widgets.adapter.ViewHolder;
import com.ggx.widgets.nicespinner.ArrowSpinner2;
import com.ggx.widgets.nicespinner.ArrowSpinnerAdapter;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame3.head.adapter.PartnerAdapter;
import com.softtek.lai.module.bodygame3.head.model.ClassdataModel;
import com.softtek.lai.module.bodygame3.head.model.ClassinfoModel;
import com.softtek.lai.module.bodygame3.head.model.PartnersModel;
import com.softtek.lai.module.bodygame3.head.net.HeadService;
import com.softtek.lai.module.bodygame3.head.view.HeadGameFragment;
import com.softtek.lai.module.bodygame3.head.view.HeadGameFragment1;
import com.softtek.lai.module.bodygame3.more.net.MoreService;
import com.softtek.lai.module.bodygame3.more.view.StudentFragment;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.chart.Chart;
import com.softtek.lai.widgets.chart.Entry;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.fragment_bodygame)
public class BodyGameFragment extends LazyBaseFragment implements HeadGameFragment1.DeleteClass, HeadGameFragment.AddClass {

    public BodyGameFragment() {
        // Required empty public constructor
    }


    @Override
    protected void lazyLoad() {
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        ZillaApi.NormalRestAdapter.create(HeadService.class).getclass(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), new RequestCallback<ResponseData<ClassdataModel>>() {
            @Override
            public void success(ResponseData<ClassdataModel> data, Response response) {
                try {
                    if (200 == data.getStatus()) {
                        ClassdataModel classdataModel = data.getData();
                        int HasClass = classdataModel.getHasClass();//0：没有班级，大于0有班级
//                        int DoingClass = classdataModel.getDoingClass();//0没有进行中的班级,大于0则有进行中的班级
                        if (HasClass > 0) {
//                            if (DoingClass > 0) {
//                                com.github.snowdream.android.util.Log.i("有班级进入此页面。。。。。。。。");
                            getChildFragmentManager().beginTransaction().replace(R.id.contain_frg, HeadGameFragment1.getInstance(BodyGameFragment.this)).commitAllowingStateLoss();
//                            } else {
//                                getChildFragmentManager().beginTransaction().replace(R.id.contain_frg, HeadGameFragment.getInstance(BodyGameFragment.this)).commitAllowingStateLoss();
//                            }
                        } else {
                            getChildFragmentManager().beginTransaction().replace(R.id.contain_frg, HeadGameFragment.getInstance(BodyGameFragment.this)).commitAllowingStateLoss();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });

    }


    @Override
    public void deletClass() {
        getChildFragmentManager().beginTransaction().replace(R.id.contain_frg, HeadGameFragment.getInstance(this)).commitAllowingStateLoss();
    }

    @Override
    public void interceptTouch(boolean touch) {

    }


    @Override
    public void addclass() {
        getChildFragmentManager().beginTransaction().replace(R.id.contain_frg, HeadGameFragment1.getInstance(this)).commitAllowingStateLoss();
    }


}
