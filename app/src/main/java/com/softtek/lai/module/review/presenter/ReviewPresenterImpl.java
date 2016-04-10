package com.softtek.lai.module.review.presenter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.counselor.model.ClassInfoModel;
import com.softtek.lai.module.counselor.net.CounselorService;
import com.softtek.lai.module.review.adapter.ReviewAdapter;

import java.util.Calendar;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by John on 2016/4/10.
 *
 */
public class ReviewPresenterImpl implements IReviewPresenter{

    private String token="";
    private CounselorService service;
    private Context context;

    public ReviewPresenterImpl(Context context) {
        this.context=context;
        token= UserInfoModel.getInstance().getToken();
        service= ZillaApi.NormalRestAdapter.create(CounselorService.class);
    }

    @Override
    public void getClassList(final ListView expand_lis,final ImageView img_mo_message) {
        service.getClassList(token, new Callback<ResponseData<List<ClassInfoModel>>>() {

            @Override
            public void success(ResponseData<List<ClassInfoModel>> listResponseData, Response response) {
                int status = listResponseData.getStatus();
                List<ClassInfoModel> list = listResponseData.getData();
                switch (status) {
                    case 200:
                        ReviewAdapter adapter = new ReviewAdapter(context, list);
                        expand_lis.setAdapter(adapter);
                        if (list.size() > 0) {
                            expand_lis.setVisibility(View.VISIBLE);
                            img_mo_message.setVisibility(View.GONE);
                        } else {
                            expand_lis.setVisibility(View.GONE);
                            img_mo_message.setVisibility(View.VISIBLE);
                        }
                        Calendar calendar = Calendar.getInstance();
                        int monthOfYear = calendar.get(Calendar.MONTH) + 1;
                        int nextMonth = 1;
                        if (monthOfYear == 12) {
                            nextMonth = 1;
                        } else {
                            nextMonth = monthOfYear + 1;
                        }
                        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                        int count = 0;
                        for (int i = 0; i < list.size(); i++) {
                            ClassInfoModel classInfo = list.get(i);
                            String str[] = classInfo.getStartDate().toString().split("-");
                            if (str[1].equals(monthOfYear + "") || str[1].equals("0" + monthOfYear)) {
                                System.out.println("当前月已开班" + str[1]);
                                count++;
                            } else {
                                System.out.println("当前月未开班" + str[1]);
                            }
                            if (str[1].equals(nextMonth + "") || str[1].equals("0" + (nextMonth))) {
                                System.out.println("次月已开班" + str[1]);
                                count++;
                            } else {
                                System.out.println("次月未开班" + str[1]);
                            }
                        }
                        System.out.println("count:" + count);
                        break;
                    default:
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }

            }

            @Override
            public void failure(RetrofitError error) {
                ZillaApi.dealNetError(error);
            }
        });
    }
}
