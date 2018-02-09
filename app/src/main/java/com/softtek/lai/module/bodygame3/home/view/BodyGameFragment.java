package com.softtek.lai.module.bodygame3.home.view;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.head.model.ClassdataModel;
import com.softtek.lai.module.bodygame3.head.net.HeadService;
import com.softtek.lai.module.bodygame3.head.view.HeadGameFragment;
import com.softtek.lai.module.bodygame3.head.view.HeadGameFragment2;
import com.softtek.lai.module.customermanagement.view.GymClubActivity;
import com.softtek.lai.utils.RequestCallback;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.PUT;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_bodygame)
public class BodyGameFragment extends LazyBaseFragment implements HeadGameFragment2.DeleteClass, HeadGameFragment.AddClass {

    @InjectView(R.id.again_tv)
    TextView again_tv;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    private String classId = "";

    public BodyGameFragment() {
        // Required empty public constructor
    }

    HeadGameFragment2 headGameFragment2;

    @Override
    protected void lazyLoad() {
        setPrepared(false);
        dialogShow("数据载入...");
        ZillaApi.NormalRestAdapter.create(HeadService.class).getclass(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), new RequestCallback<ResponseData<ClassdataModel>>() {
            @Override
            public void success(ResponseData<ClassdataModel> data, Response response) {
                Log.i("2343444444",data.toString());
                try {
                    again_tv.setVisibility(View.GONE);
                    dialogDissmiss();
                    if (200 == data.getStatus()) {
                        ClassdataModel classdataModel = data.getData();
                        int HasClass = classdataModel.getHasClass();//0：没有班级，大于0有班级
                        if (HasClass > 0) {
                            headGameFragment2 = HeadGameFragment2.getInstance(BodyGameFragment.this,classId);
                            getChildFragmentManager().beginTransaction().replace(R.id.contain_frg,headGameFragment2).commitAllowingStateLoss();

                        } else {
                            getChildFragmentManager().beginTransaction().replace(R.id.contain_frg, HeadGameFragment.getInstance(BodyGameFragment.this,classId)).commitAllowingStateLoss();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("2343444444",error.toString());
                error.printStackTrace();
                try {
                    dialogDissmiss();
                    again_tv.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    super.failure(error);
                }
            }
        });
    }

    @Override
    protected void initViews() {
        if (getArguments() != null) {
            classId = getArguments().getString("classId");
        }
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (classId != null){
                    startActivity(new Intent(getActivity(), GymClubActivity.class));
                }else {
                    getActivity().finish();
                }
            }
        });
        again_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZillaApi.NormalRestAdapter.create(HeadService.class).getclass(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), new RequestCallback<ResponseData<ClassdataModel>>() {
                    @Override
                    public void success(ResponseData<ClassdataModel> data, Response response) {
                        try {
                            again_tv.setVisibility(View.GONE);
                            dialogDissmiss();
                            if (200 == data.getStatus()) {
                                ClassdataModel classdataModel = data.getData();
                                int HasClass = classdataModel.getHasClass();//0：没有班级，大于0有班级
                                if (HasClass > 0) {
                                    headGameFragment2 = HeadGameFragment2.getInstance(BodyGameFragment.this,classId);
//                                    getChildFragmentManager().beginTransaction().replace(R.id.contain_frg,headGameFragment2).commitAllowingStateLoss();
                                    getChildFragmentManager().beginTransaction().replace(R.id.contain_frg,headGameFragment2).commitAllowingStateLoss();
                                } else {
                                    getChildFragmentManager().beginTransaction().replace(R.id.contain_frg, HeadGameFragment.getInstance(BodyGameFragment.this,classId)).commitAllowingStateLoss();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        try {
                            dialogDissmiss();
                            super.failure(error);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void initDatas() {


    }


    @Override
    public void deletClass() {
        getChildFragmentManager().beginTransaction().replace(R.id.contain_frg, HeadGameFragment.getInstance(this,classId)).commitAllowingStateLoss();
    }

    @Override
    public void addclass() {
        getChildFragmentManager().beginTransaction().replace(R.id.contain_frg, HeadGameFragment2.getInstance(this,classId)).commitAllowingStateLoss();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d("maki","123123");
        if (isVisibleToUser && headGameFragment2 != null) {
            headGameFragment2.refresh();
        }
    }
}
