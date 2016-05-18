package com.softtek.lai.module.mygrades.view;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.mygrades.adapter.RankAdapter;
import com.softtek.lai.module.mygrades.model.DayRankModel;
import com.softtek.lai.module.mygrades.model.OrderDataModel;
import com.softtek.lai.module.mygrades.net.GradesService;
import com.softtek.lai.module.mygrades.presenter.GradesImpl;
import com.softtek.lai.module.mygrades.presenter.IGradesPresenter;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

/**
 * Created by julie.zhu on 5/16/2016.
 */

@InjectLayout(R.layout.fagment_rank_sport)
public class WeekRankFragment extends BaseFragment {
//    default_icon_square
//    default_icon_rect

    //我的排名情况
    @InjectView(R.id.img_myheadportrait)
    ImageView img_myheadportrait;
    @InjectView(R.id.tv_name)
    TextView tv_name;
    @InjectView(R.id.tv_bushu1)
    TextView tv_bushu1;
    @InjectView(R.id.tv_ranking)
    TextView tv_ranking;


    @InjectView(R.id.list_rank)
    ListView list_rank;

    private DayRankModel dayRankModel;

    private List<OrderDataModel> orderDataModelList = new ArrayList<OrderDataModel>();
    private OrderDataModel orderDataModel;
    public RankAdapter rankAdapter;

    private IGradesPresenter iGradesPresenter;
    private GradesService gradesService;

    @Override
    protected void initViews() {
        initrankdate();
        rankAdapter = new RankAdapter(getContext(),orderDataModelList);
        list_rank.setAdapter(rankAdapter);
    }

    @Override
    protected void initDatas() {
        iGradesPresenter = new GradesImpl();
        gradesService= ZillaApi.NormalRestAdapter.create(GradesService.class);


        Bundle bundle2 = getArguments();
        int str=bundle2.getInt("id");
        Log.i("---------------------Weekstr----------------"+str);

        //跑团1，全国0
        getCurrentWeekOrder(1);
        //getCurrentWeekOrder(0);
    }

    private void initrankdate() {
        OrderDataModel p1 = new OrderDataModel("1","20898983403","1","18329726809","23435","zhang");
        orderDataModelList.add(p1);
        OrderDataModel p2 = new OrderDataModel("2","20898983403","2","18329726809","97556","wang");
        orderDataModelList.add(p2);
        OrderDataModel p3 = new OrderDataModel("3","20898983403","3","18329726809","86343","li");
        orderDataModelList.add(p3);
        OrderDataModel p4 = new OrderDataModel("4","20898983403","4","18329726809","45766","cheng");
        orderDataModelList.add(p4);
        OrderDataModel p5 = new OrderDataModel("5","20898983403","5","18329726809","64783","xu");
        orderDataModelList.add(p5);
    }

    public void getCurrentWeekOrder(int RGIdType) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        gradesService.getCurrentWeekOrder(token, RGIdType, new Callback<ResponseData<DayRankModel>>() {
            @Override
            public void success(ResponseData<DayRankModel> dayRankModelResponseData, Response response) {
                int status=dayRankModelResponseData.getStatus();
                switch (status)
                {
                    case 200:
                        String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
                        if (dayRankModelResponseData.getData().getOrderPhoto().isEmpty()){
                            Picasso.with(getContext()).load(path + dayRankModelResponseData.getData().getOrderPhoto()).placeholder(R.drawable.img_default).fit().error(R.drawable.img_default).into(img_myheadportrait);
                        }else {
                            Picasso.with(getContext()).load("www").placeholder(R.drawable.img_default).fit().error(R.drawable.img_default).into(img_myheadportrait);
                        }
                        if (dayRankModelResponseData.getData().getOrderName().isEmpty()){
                            if (dayRankModelResponseData.getData().getOrderMobile().isEmpty()){
                                tv_name.setText("lee");
                            }else {
                                //(姓名如果为空，手机号码前3后4中间4个*的)
                                String mobile=dayRankModelResponseData.getData().getOrderMobile();
                                String before=mobile.substring(0,3);
                                String after=mobile.substring(mobile.length()-4,mobile.length());
                                tv_name.setText(before+"****"+after);
                            }
                        }else {
                            tv_name.setText(dayRankModelResponseData.getData().getOrderName());
                        }
                        if (dayRankModelResponseData.getData().getOrderSteps().isEmpty()){
                            tv_bushu1.setText("0");
                        }else {
                            tv_bushu1.setText(dayRankModelResponseData.getData().getOrderSteps());
                        }
                         if (dayRankModelResponseData.getData().getOrderInfo().isEmpty()){
                             tv_ranking.setText("0");
                         }else {
                             tv_ranking.setText(dayRankModelResponseData.getData().getOrderInfo());
                         }

                        if (dayRankModelResponseData.getData().getOrderData().isEmpty()){
                            //Util.toastMsg("我的周排名--暂无数据");
                        }else {
                            orderDataModelList = dayRankModel.getOrderData();
                            rankAdapter.updateData(orderDataModelList);
                        }
//                        Util.toastMsg("我的周排名--查询正确");
                        break;
                    case 500:
                        Util.toastMsg("我的周排名--查询出bug");
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
    public void onStart() {
        super.onStart();
    }
}
