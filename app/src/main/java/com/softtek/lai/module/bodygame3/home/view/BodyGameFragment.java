package com.softtek.lai.module.bodygame3.home.view;

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

    public BodyGameFragment() {
        // Required empty public constructor
    }


    @Override
    protected void lazyLoad() {
    }

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        again_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZillaApi.NormalRestAdapter.create(HeadService.class).getclass(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), new RequestCallback<ResponseData<ClassdataModel>>() {
                    @Override
                    public void success(ResponseData<ClassdataModel> data, Response response) {
                        try {
                            dialogDissmiss();
                            if (200 == data.getStatus()) {
                                ClassdataModel classdataModel = data.getData();
                                int HasClass = classdataModel.getHasClass();//0：没有班级，大于0有班级
                                if (HasClass > 0) {
                                    getChildFragmentManager().beginTransaction().replace(R.id.contain_frg, HeadGameFragment2.getInstance(BodyGameFragment.this)).commitAllowingStateLoss();
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
        dialogShow("数据载入...");
        ZillaApi.NormalRestAdapter.create(HeadService.class).getclass(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), new RequestCallback<ResponseData<ClassdataModel>>() {
            @Override
            public void success(ResponseData<ClassdataModel> data, Response response) {
                try {
                    dialogDissmiss();
                    if (200 == data.getStatus()) {
                        ClassdataModel classdataModel = data.getData();
                        int HasClass = classdataModel.getHasClass();//0：没有班级，大于0有班级
                        if (HasClass > 0) {
                            getChildFragmentManager().beginTransaction().replace(R.id.contain_frg, HeadGameFragment2.getInstance(BodyGameFragment.this)).commitAllowingStateLoss();

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
                try {
                    dialogDissmiss();
                    again_tv.setVisibility(View.VISIBLE);
                    super.failure(error);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }


    @Override
    public void deletClass() {
        getChildFragmentManager().beginTransaction().replace(R.id.contain_frg, HeadGameFragment.getInstance(this)).commitAllowingStateLoss();
    }

    @Override
    public void addclass() {
        getChildFragmentManager().beginTransaction().replace(R.id.contain_frg, HeadGameFragment2.getInstance(this)).commitAllowingStateLoss();
    }


}
