package com.softtek.lai.module.community.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

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
    private ProgressDialog progressDialog;
    private Context context;

    public PersionalDynamicManager(List<UploadImage> images, Context context) {
        this.images = images;
        this.context=context;
        token=UserInfoModel.getInstance().getToken();
        appId=PropertiesManager.get("appid");
        url= AddressManager.getHost()+"/CompetitionLog/PostMultiImgs";
        service= ZillaApi.NormalRestAdapter.create(CommunityService.class);
    }

    public void sendDynamic(CommunityModel model){
        this.model=model;
        latch=new CountDownLatch(1);
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("提交中，请稍候...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
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
                    try {
                        JSONObject obj=response.getJSONObject("data");
                        photos=obj.getString("Imgs");
                        latch.countDown();
                        Log.i("第一阶段上传成功"+photos);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.i("上传失败");
                    photos=null;
                    latch.countDown();
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }

            });

        } catch (FileNotFoundException e) {
            latch.countDown();
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try {
            latch.await();
            //上传内容
            if(photos==null){
                Log.i("图片没传成功上传终止");
                if(progressDialog!=null){
                    progressDialog.dismiss();
                }
                return;
            }else{
                model.setPhotoes(photos);
            }

            Log.i("开始上传第二阶段");
            service.saveDynamic(token, model, new RequestCallback<ResponseData>() {
                @Override
                public void success(ResponseData responseData, Response response) {
                    Util.toastMsg(responseData.getMsg());
                    Log.i("第二阶段上传结束->"+responseData.getMsg());
                    if(progressDialog!=null){
                        progressDialog.dismiss();
                    }
                    Intent intent=((AppCompatActivity)context).getIntent();
                    intent.putExtra("uploadImage",new UploadImage());
                    ((AppCompatActivity)context).setResult(-1,intent);
                    ((AppCompatActivity)context).finish();
                }

                @Override
                public void failure(RetrofitError error) {
                    super.failure(error);
                    Log.i("上传失败");
                    if(progressDialog!=null){
                        progressDialog.dismiss();
                    }
                }
            });

        } catch (InterruptedException e) {
            if(progressDialog!=null){
                progressDialog.dismiss();
            }
            e.printStackTrace();
        }

    }
}
