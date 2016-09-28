package com.softtek.lai.module.mygrades.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ListView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.mygrades.adapter.RankAdapter;
import com.softtek.lai.module.mygrades.model.DayRankModel;
import com.softtek.lai.module.mygrades.model.OrderDataModel;
import com.softtek.lai.module.mygrades.net.GradesService;
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

/**
 * Created by julie.zhu on 5/16/2016.
 * 周排名选项卡
 */

@InjectLayout(R.layout.fagment_rank_sport)
public class WeekRankFragment extends BaseFragment {
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

    private List<OrderDataModel> orderDataModelList = new ArrayList<OrderDataModel>();

    public RankAdapter rankAdapter;

    private GradesService gradesService;
    int accoutid;

    @Override
    protected void initViews() {
        UserInfoModel userInfoModel = UserInfoModel.getInstance();
        accoutid = Integer.parseInt(userInfoModel.getUser().getUserid());
        gradesService = ZillaApi.NormalRestAdapter.create(GradesService.class);

        Bundle bundle2 = getArguments();
        int str = bundle2.getInt("id");
        //接口信息：跑团数据1，全国数据0
        if (str == 0) {
            getCurrentWeekOrder(0);
        }
        if (str == 1) {
            getCurrentWeekOrder(1);
        }
        rankAdapter = new RankAdapter(getContext(), orderDataModelList);
        list_rank.setAdapter(rankAdapter);
    }

    @Override
    protected void initDatas() {

    }

    //切换排名时,更新数据
    public void updateWeekRankStatus(int i) {
        if (i == 0) {
            orderDataModelList.clear();
            getCurrentWeekOrder(0);
            rankAdapter.updateData(orderDataModelList);
        }
        if (i == 1) {
            orderDataModelList.clear();
            getCurrentWeekOrder(1);
            rankAdapter.updateData(orderDataModelList);
        }
    }

    public void updateData(List<OrderDataModel> orderDataModelList) {
        this.orderDataModelList = orderDataModelList;
        notifyAll();
    }

    public void getCurrentWeekOrder(int RGIdType) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        gradesService.getCurrentWeekOrder(token, RGIdType, new Callback<ResponseData<DayRankModel>>() {
            @Override
            public void success(ResponseData<DayRankModel> dayRankModelResponseData, Response response) {
                int status = dayRankModelResponseData.getStatus();
                switch (status) {
                    case 200:
                        try {
                            String path = AddressManager.get("photoHost");
                            if (!TextUtils.isEmpty(dayRankModelResponseData.getData().getOrderPhoto())) {
                                Picasso.with(getContext()).load(path + dayRankModelResponseData.getData().getOrderPhoto()).placeholder(R.drawable.img_default).fit().error(R.drawable.img_default).into(img_myheadportrait);
                            } else {
                                Picasso.with(getContext()).load(R.drawable.img_default).into(img_myheadportrait);
                            }
                            if (dayRankModelResponseData.getData().getOrderName().isEmpty()) {
                                if (dayRankModelResponseData.getData().getOrderMobile().isEmpty()) {
                                    tv_name.setText("");
                                } else {
                                    //(姓名如果为空，手机号码前3后4中间4个*的)
                                    String mobile = dayRankModelResponseData.getData().getOrderMobile();
                                    String before = mobile.substring(0, 3);
                                    String after = mobile.substring(mobile.length() - 4, mobile.length());
                                    tv_name.setText(before + "****" + after);
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

                            if (!dayRankModelResponseData.getData().getOrderData().isEmpty()) {
                                orderDataModelList = dayRankModelResponseData.getData().getOrderData();
                                rankAdapter.updateData(orderDataModelList);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 500:
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
    public void onStart() {
        super.onStart();
    }
}
