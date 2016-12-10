package com.softtek.lai.module.bodygame3.home.view;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ggx.widgets.adapter.ViewHolder;
import com.ggx.widgets.nicespinner.ArrowSpinnerAdapter;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame3.activity.model.ActivitydataModel;
import com.softtek.lai.module.bodygame3.activity.net.ActivityService;
import com.softtek.lai.module.bodygame3.activity.view.ClassStuFragment;
import com.softtek.lai.module.bodygame3.activity.view.ClassedFragment;
import com.softtek.lai.module.bodygame3.activity.view.NoClassFragment;
import com.softtek.lai.module.bodygame3.head.adapter.ListRecyclerAdapter;
import com.softtek.lai.module.bodygame3.head.model.ClassModel;
import com.softtek.lai.module.bodygame3.head.view.HeadGameFragment1;
import com.softtek.lai.module.bodygame3.head.view.SearchClassActivity;
import com.softtek.lai.module.bodygame3.more.view.MoreHasFragment;
import com.softtek.lai.utils.RequestCallback;

import java.util.ArrayList;
import java.util.concurrent.Executors;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_activity2)
public class ActivityFragment extends LazyBaseFragment implements ClassedFragment.DeleteClass, NoClassFragment.AddClass, ClassStuFragment.DeleteClass {

    @InjectView(R.id.contain_act)
    FrameLayout contain_act;
    private int ClassRole;

    public ActivityFragment() {
        // Required empty public constructor
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initViews() {
        ZillaApi.NormalRestAdapter.create(ActivityService.class).getactivity(UserInfoModel.getInstance().getToken(),
                UserInfoModel.getInstance().getUserId(),
                "",
                new RequestCallback<ResponseData<ActivitydataModel>>() {
                    @Override
                    public void success(ResponseData<ActivitydataModel> activitydataModelResponseData, Response response) {
                        if (activitydataModelResponseData.getData() != null) {
                            ActivitydataModel activitydataModel = activitydataModelResponseData.getData();
                            ClassRole = activitydataModel.getClassRole();
                            if (UserInfoModel.getInstance().getUser().getHasThClass() == 0) {

                                getChildFragmentManager().beginTransaction().replace(R.id.contain_act, NoClassFragment.getInstance(ActivityFragment.this)).commit();
                            } else {
                                if (ClassRole == Constants.STUDENT) {
                                    getChildFragmentManager().beginTransaction().replace(R.id.contain_act, ClassStuFragment.getInstance(ActivityFragment.this)).commit();
                                } else {
                                    getChildFragmentManager().beginTransaction().replace(R.id.contain_act, ClassedFragment.getInstance(ActivityFragment.this)).commit();
                                }
                            }
                        }
                    }
                });
    }

    @Override
    protected void initDatas() {


    }

    @Override
    public void deletClass() {
        getChildFragmentManager().beginTransaction().replace(R.id.contain_act, NoClassFragment.getInstance(this)).commitAllowingStateLoss();
    }

    @Override
    public void addclass(int classRole) {
        if (classRole == Constants.STUDENT) {
            getChildFragmentManager().beginTransaction().replace(R.id.contain_act, ClassStuFragment.getInstance(this)).commitAllowingStateLoss();
        } else {
            getChildFragmentManager().beginTransaction().replace(R.id.contain_act, ClassedFragment.getInstance(this)).commitAllowingStateLoss();
        }
    }
}
