package com.softtek.lai.module.retest.present;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.newmemberentry.view.model.PhotModel;
import com.softtek.lai.module.retest.eventModel.BanJiEvent;
import com.softtek.lai.module.retest.eventModel.BanjiStudentEvent;
import com.softtek.lai.module.retest.eventModel.StudentEvent;
import com.softtek.lai.module.retest.model.BanjiModel;
import com.softtek.lai.module.retest.model.BanjiStudentModel;
import com.softtek.lai.module.retest.model.RetestAuditModel;
import com.softtek.lai.module.retest.model.RetestWriteModel;
import com.softtek.lai.module.retest.model.StudentModel;
import com.softtek.lai.module.retest.net.RestService;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import retrofit.Callback;
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

    public  RetestclassImp(){
        service= ZillaApi.NormalRestAdapter.create(RestService.class);
    }

    @Override
    public void doGetRetestclass(long id) {
        Log.i("service>>>>>>>>>>>>>>>>>>>>>>>>>>"+service);
        String token=SharedPreferenceService.getInstance().get("token","0");
       service.doGetRetestclass(token,id, new Callback<ResponseData<List<BanjiModel>>>() {
           @Override
           public void success(ResponseData<List<BanjiModel>> banjiResponseData, retrofit.client.Response response) {
               int status=banjiResponseData.getStatus();
               switch (status){
                   case 200:{
                       EventBus.getDefault().post(new BanJiEvent(banjiResponseData.getData()));
                       System.out.println(banjiResponseData);
                       Log.i("全部班级加载成功");
                   }
                   break;
                   case 201:{
                       Log.i("未分配班级");
                   }
                   break;
               }
           }

           @Override
           public void failure(RetrofitError error) {
               error.printStackTrace();
               Util.toastMsg(R.string.neterror);
           }
       });

    }

    @Override
    public void doGetqueryResult(String str) {
        String token=SharedPreferenceService.getInstance().get("token","");
        service.doGetqueryResult(token, str, new Callback<ResponseData<List<StudentModel>>>() {

            @Override
            public void success(ResponseData<List<StudentModel>> listResponseData, Response response) {
                int status=listResponseData.getStatus();
                switch (status){
                    case 200:
                        EventBus.getDefault().post(new StudentEvent(listResponseData.getData()));
                        Log.i("查询成功");
                        break;
                    default:
                        Util.toastMsg("未查询到结果");
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                Util.toastMsg(R.string.neterror);
            }
        });
    }

    @Override
    public void doGetBanjiStudent(long classId) {

        String token=SharedPreferenceService.getInstance().get("token","");
        service.doGetBanjiStudent(token, classId, new Callback<ResponseData<List<BanjiStudentModel>>>() {
            @Override
            public void success(ResponseData<List<BanjiStudentModel>> listResponseData, Response response) {
                int status=listResponseData.getStatus();
                switch (status){
                    case 200:
                        Log.i("service>>>>>>>>>>>>>>>>>>>>>>>dsfdsssssssssssssssssssssssssssssssssssssss>>>"+service);
                        EventBus.getDefault().post(new BanjiStudentEvent(listResponseData.getData()));
                        Log.i("查询成功");
                        break;
                    case 201:
                        Log.i("未查询到结果");
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                Util.toastMsg(R.string.neterror);

            }
        });
    }

    @Override
    public void doGetAudit(long accountId, long classId, String typeDate) {
        String token=SharedPreferenceService.getInstance().get("token","");
        service.doGetAudit(token, accountId, classId, typeDate, new Callback<ResponseData<List<RetestAuditModel>>>() {
            @Override
            public void success(ResponseData<List<RetestAuditModel>> listResponseData, Response response) {
                int status=listResponseData.getStatus();
                switch (status)
                {
                    case 200:
                        Util.toastMsg("保存成功");
                        break;
                    case 500:
                        Util.toastMsg("复测记录获取失败");
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                Util.toastMsg("服务器异常");
            }
        });
    }

//复测写入提交
    @Override
    public void doPostWrite(long accountId, long loginId, RetestWriteModel retestWrite) {
        String token=SharedPreferenceService.getInstance().get("token","");
        service.doPostWrite(token, accountId, loginId, retestWrite, new Callback<ResponseData<RetestWriteModel>>() {

            @Override
            public void success(ResponseData retestWriteResponseData, Response response) {
                System.out.println("retestWriteResponseData==null?"+retestWriteResponseData);
                int status=retestWriteResponseData.getStatus();
                switch (status)
                {
                    case 200:
                        Util.toastMsg("复测记保存取成功");
                        break;
                    case 201:
                        Util.toastMsg("复测记录保存失败");
                        break;
                    case 302:
                        Util.toastMsg("本周复测记录已存在");
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                Util.toastMsg("服务器异常");
            }
        });
    }
//复测审核提交
    @Override
    public void doPostAudit(String loginId, String accountId, String typeDate, RetestAuditModel retestAudit) {
        String token=SharedPreferenceService.getInstance().get("token","");
        service.doPostAudit(token, loginId, accountId, typeDate, retestAudit, new Callback<ResponseData<List<RetestAuditModel>>>() {
            @Override
            public void success(ResponseData<List<RetestAuditModel>> listResponseData, Response response) {
                int status=listResponseData.getStatus();
                switch (status)
                {
                    case 200:
                        Util.toastMsg("复测数据更新成功");
                        break;
                    case 201:
                        Util.toastMsg("复测数据更新失败");
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                Util.toastMsg("服务器异常");
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

                int status=photResponseData.getStatus();
                switch (status) {
                    case 200:
                        PhotModel phot= (PhotModel) photResponseData.getData();
                        EventBus.getDefault().post(phot);
                        Util.toastMsg("获取成功");
                        break;
                    case 500:
                        Util.toastMsg("上传图片异常");
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                Util.toastMsg("服务器异常");
            }
        });
    }

    @Override
    public void doGetMeasure(String accesstoken, String phone) {

    }




}
