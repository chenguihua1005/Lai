package com.softtek.lai.module.counselor.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.File.view.CreatFlleActivity;
import com.softtek.lai.module.counselor.adapter.CounselorClassAdapter;
import com.softtek.lai.module.counselor.model.ClassId;
import com.softtek.lai.module.counselor.model.ClassInfo;
import com.softtek.lai.module.counselor.net.CounselorClassService;
import com.softtek.lai.module.counselor.view.AssistantListActivity;
import com.softtek.lai.module.home.cache.HomeInfoCache;
import com.softtek.lai.module.home.model.FunctionModel;
import com.softtek.lai.module.home.model.HomeInfo;
import com.softtek.lai.module.home.net.HomeService;
import com.softtek.lai.module.home.presenter.IHomeInfoPresenter;
import com.softtek.lai.module.login.model.User;
import com.softtek.lai.module.login.net.LoginService;
import com.softtek.lai.utils.ACache;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.ui.ZillaAdapter;
import zilla.libcore.util.Util;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class CounselorClassImpl implements ICounselorClassPresenter{

    private CounselorClassService counselorClassService;
    private Context context;

    public CounselorClassImpl(Context context){
        this.context=context;
        counselorClassService= ZillaApi.NormalRestAdapter.create(CounselorClassService.class);
    }


    @Override
    public void getClassList(final ListView expand_lis, final LinearLayout lin_create_class) {
        String token=SharedPreferenceService.getInstance().get("token","");
        counselorClassService.getClassList(token,new Callback<ResponseData<List<ClassInfo>>>() {

            @Override
            public void success(ResponseData<List<ClassInfo>> listResponseData, Response response) {
                Log.e("jarvis",listResponseData.toString());
                int status=listResponseData.getStatus();
                List<ClassInfo> list=listResponseData.getData();
                switch (status){
                    case 200:
                        CounselorClassAdapter adapter=new CounselorClassAdapter(context,list);
                        expand_lis.setAdapter(adapter);
                        Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int monthOfYear = calendar.get(Calendar.MONTH) + 1;
                        int nextMonth=1;
                        if(monthOfYear==12){
                            nextMonth=1;
                        }else {
                            nextMonth=monthOfYear+1;
                        }
                        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                        System.out.println("list--------------" + list);
                        int count=0;
                        for (int i = 0; i < list.size(); i++) {
                            ClassInfo classInfo=list.get(i);
                            String str[]=classInfo.getStartDate().toString().split("-");
                            if(str[1].equals(monthOfYear+"") || str[1].equals("0"+monthOfYear)){
                                System.out.println("当前月已开班" + str[1]);
                                count++;
                            }else {
                                System.out.println("当前月未开班" + str[1]);
                            }
                            if(str[1].equals(nextMonth+"") || str[1].equals("0"+(nextMonth))){
                                System.out.println("次月已开班" + str[1]);
                                count++;
                            }else {
                                System.out.println("次月未开班" + str[1]);
                            }
                        }
                        System.out.println("count:" +count);
                        if(count==2){
                            lin_create_class.setVisibility(View.GONE);
                        }else {
                            lin_create_class.setVisibility(View.VISIBLE);
                        }
                        break;
                    default:
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }

            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();

                Util.toastMsg("获取列表失败");
            }
        });
    }

    @Override
    public void createClass(String className, String startDate, String endDate, String managerId) {
        String token=SharedPreferenceService.getInstance().get("token","");
        counselorClassService.createClass(token, className, startDate, endDate, managerId, new Callback<ResponseData<ClassId>>() {
            @Override
            public void success(ResponseData<ClassId> classIdResponseData, Response response) {
                Log.e("jarvis",classIdResponseData.toString());
                int status=classIdResponseData.getStatus();
                switch (status){
                    case 200:
                        SharedPreferenceService.getInstance().put("classId", classIdResponseData.getData().getClassId());
                        Intent intent=new Intent(context, AssistantListActivity.class);
                        intent.putExtra("classId",classIdResponseData.getData().getClassId());
                        context.startActivity(intent);
                        break;
                    default:
                        Util.toastMsg(classIdResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Util.toastMsg("创建班级失败");
            }
        });
    }

}
