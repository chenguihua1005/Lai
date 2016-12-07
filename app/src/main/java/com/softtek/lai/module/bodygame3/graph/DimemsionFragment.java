package com.softtek.lai.module.bodygame3.graph;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment2;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.graph.model.GirthModel;
import com.softtek.lai.module.bodygame3.graph.net.GraphService;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.chart.Chart;
import com.softtek.lai.widgets.chart.Entry;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;

/**
 * A simple {@link Fragment} subclass.
 */
@InjectLayout(R.layout.fragment_dimemsion)
public class DimemsionFragment extends LazyBaseFragment2 {

    @InjectView(R.id.bust_chart)
    Chart bust_chart;
    @InjectView(R.id.waist_chart)
    Chart waist_chart;
    @InjectView(R.id.hipline_chart)
    Chart hipline_chart;
    @InjectView(R.id.upper_chart)
    Chart upper_chart;
    @InjectView(R.id.xiaotui_chart)
    Chart xiaotui_chart;
    @InjectView(R.id.datui_chart)
    Chart datui_chart;

    List<String> xAsix=new ArrayList<>();
    List<Entry> bust=new ArrayList<>();
    List<Entry> waist=new ArrayList<>();
    List<Entry> hipline=new ArrayList<>();
    List<Entry> upper=new ArrayList<>();
    List<Entry> xiaotui=new ArrayList<>();
    List<Entry> datui=new ArrayList<>();

    public DimemsionFragment() {
        // Required empty public constructor
    }
    public static Fragment getInstance(long accountId,String classId){
        Fragment fragment=new DimemsionFragment();
        Bundle bundle=new Bundle();
        bundle.putLong("accountId",accountId);
        bundle.putString("classId",classId);
        fragment.setArguments(bundle);
        return  fragment;
    }

    @Override
    protected void lazyLoad() {
        ZillaApi.NormalRestAdapter.create(GraphService.class)
                .getClassMemberGirthChart(
                        UserInfoModel.getInstance().getToken(),
                        getArguments().getLong("accountId"),
                        getArguments().getString("classId"),
                        new RequestCallback<ResponseData<List<GirthModel>>>() {
                            @Override
                            public void success(ResponseData<List<GirthModel>> data, Response response) {
                                setContentEmpty(false);
                                setContentShown(true);
                                onSuccess(data.getData());
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                setContentEmpty(false);
                                setContentShown(true);
                                super.failure(error);
                            }
                        }
                );
    }

    private void onSuccess(List<GirthModel> data){
        try {
            float maxBust = 0;
            float maxWaist=0;
            float maxHipline=0;
            float maxUpper = 0;
            float maxDatui=0;
            float maxXiaotui=0;
            if (data!=null) {
                for (int i = 0, j = data.size(); i < j; i++) {
                    GirthModel model = data.get(i);
                    if (i == 0) {//第一条数据
                        //第一周
                        int diff = model.getWeekDay() - 1;
                        if (diff > 0) {
                            //第一条数据不是第一周的，需要先补点0上去
                            for (int k = 1; k < diff; k++) {
                                xAsix.add("第" + k + "周");
                            }
                        }
                    } else {
                        //不是第一条数据则需要查看此条数据与上一条数据的周数相差
                        GirthModel previous = data.get(i - 1);
                        int diff = model.getWeekDay() - previous.getWeekDay();
                        if (diff > 1) {
                            //第一条数据不是第一周的，需要先补点0上去
                            for (int k = previous.getWeekDay() + 1; k < model.getWeekDay(); k++) {
                                xAsix.add("第" + (k + 1) + "周");
                            }
                        }
                    }
                    xAsix.add("第" + model.getWeekDay() + "周");
                    float bustValue = Float.valueOf(model.getCircum());
                    float waistValue = Float.valueOf(model.getWaistline());
                    float hiplineValue = Float.valueOf(model.getHiplie());
                    float upperValue = Float.valueOf(model.getUpArmGirth());
                    float datuiValue = Float.valueOf(model.getUpLegGirth());
                    float xiaotuiValue = Float.valueOf(model.getDoLegGirth());
                    maxBust = bustValue > maxBust ? bustValue : maxBust;
                    maxWaist = waistValue > maxWaist ? waistValue : maxBust;
                    maxHipline = hiplineValue > maxHipline ? hiplineValue : maxHipline;
                    maxUpper = upperValue > maxUpper ? upperValue : maxUpper;
                    maxDatui = datuiValue > maxDatui ? datuiValue : maxDatui;
                    maxXiaotui = xiaotuiValue > maxXiaotui ? xiaotuiValue : maxXiaotui;
                    bust.add(new Entry(model.getWeekDay() - 1, bustValue));
                    waist.add(new Entry(model.getWeekDay() - 1, waistValue));
                    hipline.add(new Entry(model.getWeekDay() - 1, hiplineValue));
                    upper.add(new Entry(model.getWeekDay() - 1, upperValue));
                    datui.add(new Entry(model.getWeekDay() - 1, datuiValue));
                    xiaotui.add(new Entry(model.getWeekDay() - 1, xiaotuiValue));
                }
            }
            bust_chart.setDate(xAsix,bust,maxBust);
            waist_chart.setDate(xAsix,waist,maxWaist);
            hipline_chart.setDate(xAsix,hipline,maxHipline);
            upper_chart.setDate(xAsix,upper,maxUpper);
            datui_chart.setDate(xAsix,datui,maxDatui);
            xiaotui_chart.setDate(xAsix,xiaotui,maxXiaotui);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void initViews() {
        //设置背景颜色
    }

    @Override
    protected void initDatas() {

    }

}
