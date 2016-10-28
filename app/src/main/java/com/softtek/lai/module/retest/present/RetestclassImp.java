package com.softtek.lai.module.retest.present;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.newmemberentry.model.PhotModel;
import com.softtek.lai.module.retest.eventModel.BanJiEvent;
import com.softtek.lai.module.retest.eventModel.BanjiStudentEvent;
import com.softtek.lai.module.retest.eventModel.RetestAuditModelEvent;
import com.softtek.lai.module.retest.eventModel.StudentEvent;
import com.softtek.lai.module.retest.model.BanjiModel;
import com.softtek.lai.module.retest.model.BanjiStudentModel;
import com.softtek.lai.module.retest.model.LaichModel;
import com.softtek.lai.module.retest.model.RetestAuditModel;
import com.softtek.lai.module.retest.model.RetestWriteModel;
import com.softtek.lai.module.retest.model.StudentModel;
import com.softtek.lai.module.retest.net.LaiChenService;
import com.softtek.lai.module.retest.net.RestService;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.util.Util;

/**
 * Created by lareina.qiao on 3/22/2016.
 */
public class RetestclassImp implements RetestPre{
    private RestService service;
    private LaiChenService laiChenService;

    public  RetestclassImp(){
        service= ZillaApi.NormalRestAdapter.create(RestService.class);
        laiChenService=ZillaApi.getCustomRESTAdapter("http://qa-api.yunyingyang.com", new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {

            }
        }).create(LaiChenService.class);
    }

    @Override
    public void doGetRetestclass(long id) {
        String token=SharedPreferenceService.getInstance().get("token","");
       service.doGetRetestclass(token,id, new Callback<ResponseData<List<BanjiModel>>>() {
           @Override
           public void success(ResponseData<List<BanjiModel>> banjiResponseData, retrofit.client.Response response) {
               int status=banjiResponseData.getStatus();
               switch (status){
                   case 200:{
                       EventBus.getDefault().post(new BanJiEvent(banjiResponseData.getData()));
                   }
                   break;
                   case 201:{
                   }
                   break;
                   default:
                       Log.i(banjiResponseData.getMsg());
                       break;
               }
           }

           @Override
           public void failure(RetrofitError error) {
               ZillaApi.dealNetError(error);

           }
       });

    }

    @Override
    public void doGetqueryResult(String str, String accountId, final ProgressDialog dialog, final Context context) {
        String token=SharedPreferenceService.getInstance().get("token","");
        service.doGetqueryResult(token, str, accountId, new Callback<ResponseData<List<StudentModel>>>() {
            @Override
            public void success(ResponseData<List<StudentModel>> listResponseData, Response response) {
                int status=listResponseData.getStatus();
                dialog.dismiss();
                switch (status){
                    case 200:
                        EventBus.getDefault().post(new StudentEvent(listResponseData.getData()));
                        Log.i("查询成功");
                        break;
                    default:
//                        final AlertDialog.Builder information_dialog=new AlertDialog.Builder(context);
//                        information_dialog.setTitle("查询").setMessage(listResponseData.getMsg()).setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        }).create().show();
                        Util.toastMsg(listResponseData.getMsg());
                        
                }
            }

            @Override
            public void failure(RetrofitError error) {
                dialog.dismiss();
                ZillaApi.dealNetError(error);
            }
        });
    }


    @Override
    public void doGetBanjiStudent(long classId,long id) {

        String token=SharedPreferenceService.getInstance().get("token","");
        service.doGetBanjiStudent(token, classId,id, new Callback<ResponseData<List<BanjiStudentModel>>>() {
            @Override
            public void success(ResponseData<List<BanjiStudentModel>> listResponseData, Response response) {
                int status=listResponseData.getStatus();
                switch (status){
                    case 200:
                        EventBus.getDefault().post(new BanjiStudentEvent(listResponseData.getData()));
                        Log.i("查询成功");
                        break;
                    case 201:
                        EventBus.getDefault().post(new BanjiStudentEvent(null));
                        Log.i("未查询到结果");
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                ZillaApi.dealNetError(error);
                error.printStackTrace();


            }
        });
    }

    @Override
    public void doGetAudit(long accountId, long classId, String typeDate) {
        String token=SharedPreferenceService.getInstance().get("token","");
        service.doGetAudit(token, accountId, classId, typeDate, new Callback<ResponseData<List<RetestAuditModel>>>() {

            @Override
            public void success(ResponseData<List<RetestAuditModel>> listResponseData, Response response) {
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200:
                        EventBus.getDefault().post(new RetestAuditModelEvent(listResponseData.getData()));
                        break;
                    case 500:
                        Util.toastMsg("复测记录获取失败");
                        break;
                    default:
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });
    }

//复测写入提交
    @Override
    public void doPostWrite(long accountId, long loginId, RetestWriteModel retestWrite, final Context context, final ProgressDialog progressDialog) {
        String token=SharedPreferenceService.getInstance().get("token","");
        service.doPostWrite(token, accountId, loginId, retestWrite, new Callback<ResponseData<RetestWriteModel>>() {
            @Override
            public void success(ResponseData<RetestWriteModel> retestWriteResponseData, Response response) {
                progressDialog.dismiss();
                if(retestWriteResponseData!=null){
                    int status=retestWriteResponseData.getStatus();
                    switch (status)
                    {
                        case 200:
                            Intent intent=((AppCompatActivity)context).getIntent();
                            //intent.putExtra("story",model);
                            ((AppCompatActivity)context).setResult(-1,intent);
                            ((AppCompatActivity)context).finish();
                            break;
                        case 201:
                            Util.toastMsg(retestWriteResponseData.getMsg());
                            break;
                        case 302:
                            Util.toastMsg(retestWriteResponseData.getMsg());
                            break;
                        default:
                            Util.toastMsg(retestWriteResponseData.getMsg());
                            break;
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });
    }
//复测审核提交
    @Override
    public void doPostAudit(String loginId, String accountId, String typeDate, RetestAuditModel retestAudit,final Context context) {
        String token=SharedPreferenceService.getInstance().get("token","");
        service.doPostAudit(token, loginId, accountId, typeDate, retestAudit, new Callback<ResponseData<RetestAuditModel>>() {
            @Override
            public void success(ResponseData<RetestAuditModel> listResponseData, Response response) {
                if(listResponseData!=null) {
                    int status = listResponseData.getStatus();
                    switch (status) {
                        case 200:
                            Intent intent = ((AppCompatActivity) context).getIntent();
                            //intent.putExtra("story",model);
                            ((AppCompatActivity) context).setResult(-1, intent);
                            ((AppCompatActivity) context).finish();
                            Log.i("复测数据更新成功");
                            break;
                        case 201:
                            Util.toastMsg(listResponseData.getMsg());
                            break;
                        default:
                            Util.toastMsg(listResponseData.getMsg());
                            break;
                    }
                }
            }


            @Override
            public void failure(RetrofitError error) {
                ZillaApi.dealNetError(error);
                error.printStackTrace();

            }
        });
    }
//图片上传
    @Override
    public void goGetPicture(String filePath) {
        System.out.println("上传图片路径>>>>>>>"+filePath);
        String token=SharedPreferenceService.getInstance().get("token","");
        service.goGetPicture(token, new TypedFile("image/png", new File(filePath)), new Callback<ResponseData<PhotModel>>() {
            @Override
            public void success(ResponseData<PhotModel> photResponseData, Response response) {
                Log.i(">>>>>>>>>>>>>>>>>>>>>>>>>>>>"+photResponseData.getData().getImg());
                int status=photResponseData.getStatus();
                switch (status) {
                    case 200:
                        PhotModel phot= (PhotModel) photResponseData.getData();
                        EventBus.getDefault().post(phot);
//                        Util.toastMsg("获取成功");
                        break;
                    case 500:
                        Util.toastMsg("上传图片异常");
                        break;
                    default:
                        Util.toastMsg(photResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                ZillaApi.dealNetError(error);
                error.printStackTrace();

            }
        });
    }


    @Override
    public void GetUserMeasuredInfo(String phone) {
        String token=SharedPreferenceService.getInstance().get("token","");
        service.GetUserMeasuredInfo(token, phone, new Callback<ResponseData<LaichModel>>() {
            @Override
            public void success(ResponseData<LaichModel> laichModelResponseData, Response response) {
                int status=laichModelResponseData.getStatus();
                switch (status)
                {
                    case 200:
                        LaichModel laichModel= (LaichModel)laichModelResponseData.getData();
                        EventBus.getDefault().post(laichModel);
                        break;
                    case 400:
                        Log.i("暂无数据");
                        break;
                        default:
                            Util.toastMsg(laichModelResponseData.getMsg());
                            break;
                }

            }

            @Override
            public void failure(RetrofitError error) {
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });

    }


}



