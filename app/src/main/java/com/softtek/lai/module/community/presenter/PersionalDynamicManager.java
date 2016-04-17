package com.softtek.lai.module.community.presenter;

import com.github.snowdream.android.util.Log;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.community.model.CommunityModel;
import com.softtek.lai.module.community.net.CommunityService;
import com.softtek.lai.module.lossweightstory.model.UploadImage;
import com.softtek.lai.utils.RequestCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.file.PropertiesManager;
import zilla.libcore.util.Util;

/**
 * Created by John on 2016/4/17.
 *
 */
public class PersionalDynamicManager implements Runnable{

    private CountDownLatch latch;
    private List<UploadImage> images;
    private String token;
    private String appId;
    private String url;
    private CommunityService service;
    private CommunityModel model;
    private String photos=null;

    public PersionalDynamicManager(List<UploadImage> images) {
        this.images = images;
        latch=new CountDownLatch(1);
        token=UserInfoModel.getInstance().getToken();
        appId=PropertiesManager.get("appid");
        url= AddressManager.getHost()+"/CompetitionLog/PostMultiImgs";
        service= ZillaApi.NormalRestAdapter.create(CommunityService.class);
    }

    public void sendDynamic(CommunityModel model){
        this.model=model;
        new Thread(this).start();
        try {
            Log.i("开始上传第一阶段");
            AsyncHttpClient client=new AsyncHttpClient();
            client.addHeader("appid", appId);
            client.addHeader("token", token);
            RequestParams params=new RequestParams();
            int size=images.size()<9?images.size()-1:images.size();
            for(int i=0;i<size;i++){
                UploadImage image=images.get(i);
                params.put("image"+i,image.getImage());
            }
            client.post(url, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    latch.countDown();
                    try {
                        JSONObject obj=response.getJSONObject("data");
                        photos=obj.getString("Imgs");
                        Util.toastMsg("上传成功"+photos);
                        Log.i("第一阶段上传成功"+photos);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                    latch.countDown();
                    Util.toastMsg(responseString);
                    throwable.printStackTrace();
                }
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try {
            latch.await(2, TimeUnit.MINUTES);
            //上传内容
            if(photos==null){
                Log.i("图片没传成功上传终止");
                return;
            }else{
                model.setPhotoes(photos);
            }

            Log.i("开始上传第二阶段");
            service.saveDynamic(token, model, new RequestCallback<ResponseData>() {
                @Override
                public void success(ResponseData responseData, Response response) {
                    Util.toastMsg(responseData.getMsg());
                }

                @Override
                public void failure(RetrofitError error) {
                    super.failure(error);
                    Log.i("上传失败");
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
