package com.softtek.lai.module.studentbasedate.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.grade.adapter.DynamicAdapter;
import com.softtek.lai.module.grade.model.DynamicInfoModel;
import com.softtek.lai.module.studentbasedate.net.StudentBaseDateService;
import com.softtek.lai.module.studentbasedate.presenter.IStudentBaseDate;
import com.softtek.lai.module.studentbasedate.presenter.StudentBaseDateImpl;
import com.softtek.lai.utils.RequestCallback;

import java.io.Serializable;
import java.util.List;

import butterknife.InjectView;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

/**
 * Created by jerry.guan on 4/13/2016.
 */
@InjectLayout(R.layout.fragment_basedate_dynamic)
public class ClassDynamicFragment extends BaseFragment{

    @InjectView(R.id.lv)
    ListView lv;
    @InjectView(R.id.img_no_message)
    ImageView no_message_img;

    private StudentBaseDateService service;
    private DynamicAdapter dynamicAdapter;
    public static ClassDynamicFragment getInstance(long classId){
            ClassDynamicFragment fragment=new ClassDynamicFragment();
            Bundle bundle=new Bundle();
            bundle.putLong("classId",classId);
            fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        service= ZillaApi.NormalRestAdapter.create(StudentBaseDateService.class);
        Bundle bundle=getArguments();
        if(bundle!=null){
            long classId=bundle.getLong("classId",0);
            String token= UserInfoModel.getInstance().getToken();
            service.getClassDynamic(
                    token,
                    classId,
                    new RequestCallback<ResponseData<List<DynamicInfoModel>>>() {
                @Override
                public void success(ResponseData<List<DynamicInfoModel>> listResponseData, Response response) {
                    switch (listResponseData.getStatus()){
                        case 200:
                            if(!listResponseData.getData().isEmpty()){
                                no_message_img.setVisibility(View.GONE);
                                lv.setVisibility(View.VISIBLE);
                                dynamicAdapter=new DynamicAdapter(getContext(),listResponseData.getData());
                                lv.setAdapter(dynamicAdapter);
                            }else{
                                lv.setVisibility(View.GONE);
                                no_message_img.setVisibility(View.VISIBLE);
                            }
                            break;
                        default:
                            Util.toastMsg(listResponseData.getMsg());
                            break;
                    }
                }
            });
        }
    }
}
