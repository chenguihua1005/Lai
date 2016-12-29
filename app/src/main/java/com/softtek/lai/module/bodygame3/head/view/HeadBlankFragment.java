package com.softtek.lai.module.bodygame3.head.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.head.model.ClassdataModel;
import com.softtek.lai.module.bodygame3.head.net.HeadService;
import com.softtek.lai.module.bodygame3.home.view.BodyGameFragment;
import com.softtek.lai.utils.RequestCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

public class HeadBlankFragment extends Fragment {
    @InjectView(R.id.again_tv)
    TextView again_tv;

    public HeadBlankFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_head_blank, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
        again_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZillaApi.NormalRestAdapter.create(HeadService.class).getclass(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), new RequestCallback<ResponseData<ClassdataModel>>() {
                    @Override
                    public void success(ResponseData<ClassdataModel> data, Response response) {
                        try {
                            if (200 == data.getStatus()) {
                                again_tv.setVisibility(View.GONE);
                                ClassdataModel classdataModel = data.getData();
                                int HasClass = classdataModel.getHasClass();//0：没有班级，大于0有班级
//                        int DoingClass = classdataModel.getDoingClass();//0没有进行中的班级,大于0则有进行中的班级
                                if (HasClass > 0) {
//                            if (DoingClass > 0) {
//                                com.github.snowdream.android.util.Log.i("有班级进入此页面。。。。。。。。");
                                    getChildFragmentManager().beginTransaction().replace(R.id.contain_frg, new HeadGameFragment1()).commit();
//                            } else {
//                                getChildFragmentManager().beginTransaction().replace(R.id.contain_frg, HeadGameFragment.getInstance(BodyGameFragment.this)).commitAllowingStateLoss();
//                            }
                                } else {
                                    getChildFragmentManager().beginTransaction().replace(R.id.contain_frg, new HeadGameFragment()).commit();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        try {
                            getChildFragmentManager().beginTransaction().replace(R.id.contain_frg, new HeadBlankFragment()).commit();
                            super.failure(error);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
