package com.softtek.lai.module.laiClassroom.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.module.laiClassroom.model.SubjectModel;
import com.softtek.lai.module.laiClassroom.net.LaiClassroomService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by lareina.qiao on 3/13/2017.
 */

public class SubjectPresenter extends BasePresenter<SubjectPresenter.getSubject> {
    LaiClassroomService service;
    SubjectModel subjectModel;

    public SubjectPresenter(getSubject baseView) {
        super(baseView);
    }


    public void getSubjectData(int pageindex, int pagesize) {
        service = ZillaApi.NormalRestAdapter.create(LaiClassroomService.class);
        service.doGetArticleTopic(UserInfoModel.getInstance().getToken(), pageindex, pagesize, new Callback<ResponseData<SubjectModel>>() {
            @Override
            public void success(ResponseData<SubjectModel> subjectModelResponseData, Response response) {
                int status = subjectModelResponseData.getStatus();
                if (status == 200) {
                    if (getView() != null) {
                        getView().getSubjectart(subjectModelResponseData.getData());
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });


    }

    public interface getSubject extends BaseView {
        void getSubjectart(SubjectModel subjectModel);
    }
}
