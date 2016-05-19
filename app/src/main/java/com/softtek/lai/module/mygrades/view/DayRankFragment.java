package com.softtek.lai.module.mygrades.view;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
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
 * 日排名选项卡
 */
@InjectLayout(R.layout.fagment_rank_sport)
public class DayRankFragment extends BaseFragment {

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
    private FragmentManager manager;


    @Override
    protected void initViews() {
        rankAdapter = new RankAdapter(getContext(),orderDataModelList);
        list_rank.setAdapter(rankAdapter);
        manager = getActivity().getFragmentManager();
        Log.i("----DayRankFragment....................>"+manager.toString());

    }

    @Override
    protected void initDatas() {
        iGradesPresenter = new GradesImpl();
        gradesService= ZillaApi.NormalRestAdapter.create(GradesService.class);

        Bundle bundle1 = getArguments();
        int str=bundle1.getInt("id");
        Log.i("---------------------Daystr----------------"+str);

//        //listview str 0跑团，1全国
//        //接口信息：跑团数据1，全国数据0
//        if (str==0){
//            getCurrentDateOrder(1);
//        }else  if (str==1){
//            getCurrentDateOrder(0);
//        }else {
//            getCurrentDateOrder(1);
//        }


        //listview str 0跑团，1全国
        //接口信息：跑团数据1，全国数据0
        if (str==0){
            getCurrentDateOrder(1);
        }
        if (str==1){
            getCurrentDateOrder(0);
        }

    }

    public void updata() {

        //clubName.setText(clubname);



    }

    public void getCurrentDateOrder(int RGIdType) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        gradesService.getCurrentDateOrder(token, RGIdType, new Callback<ResponseData<DayRankModel>>() {
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
                                String qian=mobile.substring(0,3);
                                String hou=mobile.substring(mobile.length()-4,mobile.length());
                                tv_name.setText(qian+"****"+hou);
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
                            //Util.toastMsg("我的日排名--暂无数据");
                        }else {
                            orderDataModelList = dayRankModel.getOrderData();
                            rankAdapter.updateData(orderDataModelList);
                        }
                         //Util.toastMsg("我的日排名--查询正确");
                        break;
                    case 500:
                        Util.toastMsg("我的日排名--查询出bug");
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
