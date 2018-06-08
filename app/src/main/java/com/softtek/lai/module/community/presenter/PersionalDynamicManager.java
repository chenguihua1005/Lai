package com.softtek.lai.module.community.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.community.model.CommunityModel;
import com.softtek.lai.module.community.model.ImageResponse2;
import com.softtek.lai.module.community.net.CommunityService;
import com.softtek.lai.module.community.net.UploadImageService;
import com.softtek.lai.module.picture.model.UploadImage;
import com.softtek.lai.utils.RequestCallback;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by John on 2016/4/17.
 *
 */
public class PersionalDynamicManager implements Runnable,UploadImageService.UploadImageCallback{

    private CountDownLatch latch;
    private List<UploadImage> images;
    private String token;
    private CommunityService service;
    private CommunityModel model;
    private StringBuffer photo=new StringBuffer();
    private ProgressDialog progressDialog;
    private Context context;
    private Queue<UploadImageService> queue;

    public PersionalDynamicManager(List<UploadImage> images, Context context) {
        this.images = images;
        this.context=context;
        token=UserInfoModel.getInstance().getToken();
        service= ZillaApi.NormalRestAdapter.create(CommunityService.class);
    }
    private int size=0;
    public void sendDynamic(CommunityModel model){
        this.model=model;
        for(int i=0;i<images.size();i++){
            if(images.get(i).getImage()!=null){
                size++;
            }
        }
        Log.i("数量"+size);
        latch=new CountDownLatch(size);
        queue=new ArrayBlockingQueue<>(size);
        for(int i=0;i<size;i++){
            queue.offer(new UploadImageService(this,images.get(i).getImage()));
        }
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("提交中，请稍候...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        new Thread(this).start();
        Log.i("开始上传图片");
        if(queue.size()!=0){
            new Thread(queue.poll()).start();
        }

    }

    @Override
    public void run() {
        try {
            latch.await();
            //上传内容
            if(photo.toString().equals("")){
                Log.i("图片没传成功上传终止");
                if(progressDialog!=null){
                    progressDialog.dismiss();
                }
                return;
            }else{
                model.setPhotos(photo.substring(0, photo.lastIndexOf(",")));
            }
            Log.i("上传的图片"+model.getPhotos());
            Log.i("开始上传第二阶段");
            service.saveDynamic(token, model, new RequestCallback<ResponseData>() {
                @Override
                public void success(ResponseData responseData, Response response) {
                    Log.i("第二阶段上传结束->"+responseData.getMsg());
                    for(UploadImage image:images){
                        Bitmap bit=image.getBitmap();
                        if(bit!=null&&!bit.isRecycled()){
                            bit.recycle();
                        }
                    }
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

    @Override
    public void getImageName(ImageResponse2 imageName) {
        if(imageName!=null){
            photo.append(imageName.imgName);
            photo.append(",");
        }
        latch.countDown();
        Log.i("开始上传图片队列大小" + queue.size());
        if(queue.size()!=0){
            new Thread(queue.poll()).start();
        }
    }
}
