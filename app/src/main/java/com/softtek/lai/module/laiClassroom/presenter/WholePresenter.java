package com.softtek.lai.module.laiClassroom.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.common.mvp.BasePersent;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.common.mvp.BaseView1;
import com.softtek.lai.common.mvp.BaseView2;
import com.softtek.lai.module.laiClassroom.model.FilteData;
import com.softtek.lai.module.laiClassroom.net.LaiClassroomService;
import com.softtek.lai.utils.RequestCallback;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jerry.guan on 3/8/2017.
 */

public class WholePresenter extends BasePersent<WholePresenter.WholeView>{

    private LaiClassroomService service;

    public WholePresenter(WholeView baseView) {
        super(baseView);
        service= ZillaApi.NormalRestAdapter.create(LaiClassroomService.class);
    }

    public void getFilterData(){
        service.getFilteData(UserInfoModel.getInstance().getToken(),
                String.valueOf(UserInfoModel.getInstance().getUserId()),
                new RequestCallback<ResponseData<FilteData>>() {
            @Override
            public void success(ResponseData<FilteData> data, Response response) {
                if(data.getStatus()==200){
                    if(getView()!=null){
                        getView().getData(data.getData());
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }

    public void getData(){

    }


    public interface WholeView extends BaseView1<FilteData> {

    }
}
