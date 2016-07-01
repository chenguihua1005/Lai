package com.softtek.lai.module.pastreview.presenter;

import android.util.Log;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.pastreview.model.MyPhotoListModel;
import com.softtek.lai.module.pastreview.net.PCPastReview;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by lareina.qiao on 5/12/2016.
 */
public class MyPhotoListManager {
    private PCPastReview service;
    private MyPhotoListCallback cb;

    public MyPhotoListManager(MyPhotoListCallback cb) {
        this.cb = cb;
        service = ZillaApi.NormalRestAdapter.create(PCPastReview.class);
    }
    public void doGetMyPhotoList(String accountid,String PageIndex,String classid)
    {
        String token = UserInfoModel.getInstance().getToken();
        service.doGetMyPhotoList(token, accountid, PageIndex, classid, new Callback<ResponseData<List<MyPhotoListModel>>>() {
            @Override
            public void success(ResponseData<List<MyPhotoListModel>> listResponseData, Response response) {
                int status=listResponseData.getStatus();
                switch (status)
                {
                    case 200:
                        if (cb!=null)
                        {
                            Log.i("相册信息》》》》",listResponseData.getData().toString());
                            cb.getMyPhotoList(listResponseData.getData());
                        }
                        break;
                    default:
                        if (cb!=null)
                        {
//                            cb.getMyPhotoList(null);
                            MyPhotoListModel myPhotoListModel=new MyPhotoListModel();
                            myPhotoListModel.setLLId("1");
                            myPhotoListModel.setImgUrl("");
                            myPhotoListModel.setCreateDate("2016-05-29 15:40:26");
                            myPhotoListModel.setClassId("1");
                            List<MyPhotoListModel> myPhotoListModels=new ArrayList<MyPhotoListModel>();
                            myPhotoListModels.add(myPhotoListModel);
                            cb.getMyPhotoList(myPhotoListModels);
                        }
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (cb!=null)
                {
                    cb.getMyPhotoList(null);
                }
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });
    }
    public interface MyPhotoListCallback {
        void getMyPhotoList(List<MyPhotoListModel> myPhotoListModels);
    }
}