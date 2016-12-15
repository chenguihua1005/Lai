package com.softtek.lai.module.bodygame3.home.view;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.FrameLayout;

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
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.MySwipRefreshView;

import butterknife.InjectView;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_activity2)
public class ActivityFragment extends LazyBaseFragment implements ClassedFragment.DeleteClass, NoClassFragment.AddClass, ClassStuFragment.DeleteClass, SwipeRefreshLayout.OnRefreshListener {
    @InjectView(R.id.pull)
    MySwipRefreshView pull;
//    @InjectView(R.id.appbar)
//    AppBarLayout appbar;
    @InjectView(R.id.contain_act)
    FrameLayout contain_act;
    private int ClassRole;

    public ActivityFragment() {
        // Required empty public constructor
    }

    @Override
    protected void lazyLoad() {
        pull.setRefreshing(true);
        onRefresh();
    }

    @Override
    protected void initViews() {
        pull.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
//        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if (verticalOffset >= 0) {
//                    pull.setEnabled(true);
//                } else {
//                    pull.setEnabled(false);
//                }
//            }
//        });
        pull.setOnRefreshListener(this);
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

    @Override
    public void onRefresh() {
        ZillaApi.NormalRestAdapter.create(ActivityService.class).getactivity(UserInfoModel.getInstance().getToken(),
                UserInfoModel.getInstance().getUserId(),
                "",
                new RequestCallback<ResponseData<ActivitydataModel>>() {
                    @Override
                    public void success(ResponseData<ActivitydataModel> activitydataModelResponseData, Response response) {
                        pull.setRefreshing(false);
                        if (200 == activitydataModelResponseData.getStatus()) {
                            if (activitydataModelResponseData.getData() != null) {
                                ActivitydataModel activitydataModel = activitydataModelResponseData.getData();
                                ClassRole = activitydataModel.getClassRole();
                                if (UserInfoModel.getInstance().getUser().getHasThClass() == 0) {
                                    NoClassFragment fragment = NoClassFragment.getInstance(ActivityFragment.this);
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("classrole", ClassRole);
//                                bundle.putParcelable("classrole", activitydataModel);
                                    fragment.setArguments(bundle);
                                    getChildFragmentManager().beginTransaction().replace(R.id.contain_act, fragment).commitAllowingStateLoss();
                                } else {
                                    ClassStuFragment stuFragment = ClassStuFragment.getInstance(ActivityFragment.this);
                                    Bundle bundle = new Bundle();
                                    bundle.putParcelable("activitydatemodel", activitydataModel);
                                    bundle.putInt("classrole", ClassRole);
                                    stuFragment.setArguments(bundle);
                                    if (ClassRole == Constants.STUDENT) {
                                        getChildFragmentManager().beginTransaction().replace(R.id.contain_act, stuFragment).commitAllowingStateLoss();
                                    } else {
                                        ClassedFragment classedFragment = ClassedFragment.getInstance(ActivityFragment.this);
                                        classedFragment.setArguments(bundle);
                                        getChildFragmentManager().beginTransaction().replace(R.id.contain_act, classedFragment).commitAllowingStateLoss();
                                    }
                                }
                            }
                        }
                    }
                });
    }
}
