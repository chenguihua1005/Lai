package com.softtek.lai.module.mygrades.view;

import android.app.FragmentManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ListView;
import android.widget.TextView;

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
 * 日排名选项卡
 */
@InjectLayout(R.layout.fagment_rank_sport)
public class DayRankFragment extends BaseFragment {

    //我的排名情况
    @InjectView(R.id.img_myheadportrait)
    CircleImageView img_myheadportrait;
    @InjectView(R.id.tv_name)
    TextView tv_name;
    @InjectView(R.id.tv_bushu1)
    TextView tv_bushu1;
    @InjectView(R.id.tv_ranking)
    TextView tv_ranking;

    @InjectView(R.id.list_rank)
    ListView list_rank;

    private DayRankModel dayRankModel;
    private OrderDataModel orderDataModel;
    private List<OrderDataModel> orderDataModelList = new ArrayList<OrderDataModel>();
    public RankAdapter rankAdapter;

    private IGradesPresenter iGradesPresenter;
    private GradesService gradesService;
    private FragmentManager manager;

    @Override
    protected void initViews() {
        iGradesPresenter = new GradesImpl();
        gradesService = ZillaApi.NormalRestAdapter.create(GradesService.class);
        Bundle bundle1 = getArguments();
        int i = bundle1.getInt("id");
        if (i == 0) {
            getCurrentDateOrder(0);
        }
        if (i == 1) {
            getCurrentDateOrder(1);
        }
        rankAdapter = new RankAdapter(getContext(), orderDataModelList);
        list_rank.setAdapter(rankAdapter);
        manager = getActivity().getFragmentManager();
    }
    // handler类接收数据
//    Handler handler = new Handler() {
//        public void handleMessage(Message msg) {
//            if (msg.what == 1) {
//                getCurrentDateOrder(0);
//                getCurrentDateOrder(1);
//                System.out.println("receive....");
//            }
//        };
//    };
//    // 线程类
//    class ThreadShow implements Runnable {
//        @Override
//        public void run() {
//            while (true) {
//                try {
//                    //半小时刷新一次
//                    Thread.sleep(5000);//1800000
//                    Message msg = new Message();
//                    msg.what = 1;
//                    handler.sendMessage(msg);
//                    System.out.println("send...");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    System.out.println("thread error...");
//                }
//            }
//        }
//    }

    @Override
    protected void initDatas() {
//        new Thread(new ThreadShow()).start();
    }

    //切换日排名，周排名时更新数据
    public void updateDayRankStatus(int i) {
        if (i == 1) {
            orderDataModelList.clear();
            getCurrentDateOrder(1);
            rankAdapter.updateData(orderDataModelList);
        }
        if (i == 0) {
            orderDataModelList.clear();
            getCurrentDateOrder(0);
            rankAdapter.updateData(orderDataModelList);
        }
    }

    //更新list数据
    public void updateData(List<OrderDataModel> orderDataModelList) {
        this.orderDataModelList = orderDataModelList;
        notifyAll();
    }

    public void getCurrentDateOrder(int RGIdType) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        gradesService.getCurrentDateOrder(token, RGIdType, new Callback<ResponseData<DayRankModel>>() {
            @Override
            public void success(ResponseData<DayRankModel> dayRankModelResponseData, Response response) {
                int status = dayRankModelResponseData.getStatus();
                switch (status) {
                    case 200:
                        String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
                        if (!TextUtils.isEmpty(dayRankModelResponseData.getData().getOrderPhoto())) {
                            Picasso.with(getContext()).load(path + dayRankModelResponseData.getData().getOrderPhoto()).placeholder(R.drawable.img_default).fit().error(R.drawable.img_default).into(img_myheadportrait);
                        } else {
                            Picasso.with(getContext()).load("www").placeholder(R.drawable.img_default).fit().error(R.drawable.img_default).into(img_myheadportrait);
                        }

                        if (dayRankModelResponseData.getData().getOrderName().isEmpty()) {
                            if (dayRankModelResponseData.getData().getOrderMobile().isEmpty()) {
                                tv_name.setText("");
                            } else {
                                //(姓名如果为空，手机号码前3后4中间4个*的)
                                String mobile = dayRankModelResponseData.getData().getOrderMobile();
                                String qian = mobile.substring(0, 3);
                                String hou = mobile.substring(mobile.length() - 4, mobile.length());
                                tv_name.setText(qian + "****" + hou);
                            }
                        } else {
                            tv_name.setText(dayRankModelResponseData.getData().getOrderName());
                        }
                        if (dayRankModelResponseData.getData().getOrderSteps().isEmpty()) {
                            tv_bushu1.setText("0");
                        } else {
                            tv_bushu1.setText(dayRankModelResponseData.getData().getOrderSteps());
                        }
                        if (dayRankModelResponseData.getData().getOrderInfo().isEmpty()) {
                            tv_ranking.setText("0");
                        } else {
                            tv_ranking.setText(dayRankModelResponseData.getData().getOrderInfo());
                        }

                        if (dayRankModelResponseData.getData().getOrderData().isEmpty()) {
                            //Util.toastMsg("我的日排名--暂无数据");
                        } else {
                            orderDataModelList = dayRankModelResponseData.getData().getOrderData();
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
