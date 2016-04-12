package com.softtek.lai.module.review.presenter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.counselor.model.ClassInfoModel;
import com.softtek.lai.module.counselor.net.CounselorService;
import com.softtek.lai.module.review.adapter.ReviewAdapter;
import com.softtek.lai.module.review.net.ReviewService;
import com.softtek.lai.utils.RequestCallback;

import java.util.Calendar;
import java.util.List;

import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by John on 2016/4/10.
 *
 */
public class ReviewPresenterManager {

    private String token="";
    private ReviewService service;
    private Context context;
    private ReviewManagerCallback cb;

    public ReviewPresenterManager(Context context) {
        this.context=context;
        cb=(ReviewManagerCallback) context;
        token= UserInfoModel.getInstance().getToken();
        service= ZillaApi.NormalRestAdapter.create(ReviewService.class);
    }

    public void getClassList(String classType,final ListView expand_lis,final ImageView img_mo_message) {
        service.getClass(token,classType, new RequestCallback<ResponseData<List<ClassInfoModel>>>() {

            @Override
            public void success(ResponseData<List<ClassInfoModel>> listResponseData, Response response) {
                int status = listResponseData.getStatus();
                Log.i("往期回顾="+listResponseData.toString());
                switch (status) {
                    case 200:
                        if(cb!=null){
                            cb.getClassList(listResponseData.getData());
                        }
                        break;
                    default:
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }

            }
        });
    }
    public interface ReviewManagerCallback{

        void getClassList(List<ClassInfoModel> classInfoModels);
    }
}
