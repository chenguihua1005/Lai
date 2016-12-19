package com.softtek.lai.module.bodygame3.graph;


import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment2;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.graph.model.WeightModel;
import com.softtek.lai.module.bodygame3.graph.net.GraphService;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.chart.Chart;
import com.softtek.lai.widgets.chart.Entry;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;

/**
 * A simple {@link Fragment} subclass.
 */
@InjectLayout(R.layout.fragment_loss_weight)
public class LossWeightFragment extends LazyBaseFragment2 {

    @InjectView(R.id.weight_chart)
    Chart weight_chart;
    @InjectView(R.id.bfat_chart)
    Chart bfat_chart;
    @InjectView(R.id.fat_chart)
    Chart fat_chart;

    @InjectView(R.id.btn_weight_left)
    Button btn_weight_left;
    @InjectView(R.id.btn_weight_right)
    Button btn_weight_right;
    @InjectView(R.id.btn_bfat_left)
    Button btn_bfat_left;
    @InjectView(R.id.btn_bfat_right)
    Button btn_bfat_right;
    @InjectView(R.id.btn_fat_left)
    Button btn_fat_left;
    @InjectView(R.id.btn_fat_right)
    Button btn_fat_right;

    List<String> xAsix=new ArrayList<>();
    List<Entry> weight=new ArrayList<>();
    List<Entry> bfat=new ArrayList<>();
    List<Entry> fat=new ArrayList<>();
    float maxWeight = 0;
    float maxBFat=0;
    float maxFat=0;
    public LossWeightFragment() {
        // Required empty public constructor
    }

    public static Fragment getInstance(long accountId,String classId){
        Fragment fragment=new LossWeightFragment();
        Bundle bundle=new Bundle();
        bundle.putLong("accountId",accountId);
        bundle.putString("classId",classId);
        fragment.setArguments(bundle);
        return  fragment;
    }

    @Override
    protected void lazyLoad() {
        ZillaApi.NormalRestAdapter.create(GraphService.class)
                .getClassMemberWeightChart(
                        UserInfoModel.getInstance().getToken(),
                        getArguments().getLong("accountId"),
                        getArguments().getString("classId"),
                        new RequestCallback<ResponseData<List<WeightModel>>>() {
                            @Override
                            public void success(ResponseData<List<WeightModel>> data, Response response) {
                                setContentEmpty(false);
                                setContentShown(true);
                                onSuccess(data.getData());
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                setContentEmpty(false);
                                setContentShown(true);
                                onSuccess(new ArrayList<WeightModel>());
                                super.failure(error);
                            }
                        }
                );
    }

    List<String> leftXAsix;
    List<String> rightXAsix;

    private void onSuccess(List<WeightModel> data){
        /*WeightModel mod=new WeightModel();
        mod.setWeekDay(1);
        mod.setFat("20");
        mod.setPysical("50");
        mod.setWeight("100");
        WeightModel mod1=new WeightModel();
        mod1.setWeekDay(3);
        mod1.setFat("10");
        mod1.setPysical("80");
        mod1.setWeight("90");
        WeightModel mod2=new WeightModel();
        mod2.setWeekDay(4);
        mod2.setFat("60");
        mod2.setPysical("10");
        mod2.setWeight("150");
        WeightModel mod3=new WeightModel();
        mod3.setWeekDay(5);
        mod3.setFat("20");
        mod3.setPysical("78");
        mod3.setWeight("10");
        data.add(mod);
        data.add(mod1);
        data.add(mod2);
        data.add(mod3);*/
        try {

            if (data!=null) {
                for (int i = 0, j = data.size(); i < j; i++) {
                    WeightModel model = data.get(i);
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
                        WeightModel previous = data.get(i - 1);
                        int diff = model.getWeekDay() - previous.getWeekDay();
                        if (diff > 1) {
                            //第一条数据不是第一周的，需要先补点0上去
                            for (int k = previous.getWeekDay() + 1; k < model.getWeekDay(); k++) {
                                xAsix.add("第" + (k + 1) + "周");
                            }
                        }
                    }
                    xAsix.add("第" + model.getWeekDay() + "周");
                    float weightValue = Float.valueOf(model.getWeight());
                    float bfatValue = Float.valueOf(model.getPysical());
                    float fatValue = Float.valueOf(model.getFat());
                    maxWeight = weightValue > maxWeight ? weightValue : maxWeight;
                    maxBFat = bfatValue > maxBFat ? bfatValue : maxBFat;
                    maxFat = fatValue > maxFat ? fatValue : maxFat;
                    weight.add(new Entry(model.getWeekDay() - 1, weightValue));
                    bfat.add(new Entry(model.getWeekDay() - 1, bfatValue));
                    fat.add(new Entry(model.getWeekDay() - 1, fatValue));
                }
                Log.i("xAsix=="+xAsix.size());
                Log.i("weight=="+weight.size());
                Log.i("bfat=="+bfat.size());
                Log.i("fat=="+fat.size());
                leftXAsix=xAsix.subList(0,xAsix.size()/2);
                rightXAsix=xAsix.subList(xAsix.size()/2,xAsix.size());
                weight_chart.setDate(leftXAsix,weight.subList(0,weight.size()/2),maxWeight);
                bfat_chart.setDate(leftXAsix,bfat.subList(0,bfat.size()/2),maxBFat);
                fat_chart.setDate(leftXAsix,fat.subList(0,fat.size()/2),maxFat);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initViews() {
        int radius=DisplayUtil.dip2px(getContext(),5);
        GradientDrawable orange=new GradientDrawable();
        orange.setColors(new int[]{0xFFFEA003,0xFFED7460});
        orange.setCornerRadius(radius);
        GradientDrawable cyan=new GradientDrawable();
        cyan.setColors(new int[]{0xFF77BA2B,0xFFA6C225});
        cyan.setCornerRadius(radius);
        GradientDrawable indigo=new GradientDrawable();
        indigo.setColors(new int[]{0xFF19BC84,0xFF1899A0});
        indigo.setCornerRadius(radius);
        weight_chart.setBackground(orange);
        bfat_chart.setBackground(cyan);
        fat_chart.setBackground(indigo);
    }

    @Override
    protected void initDatas() {
//        btn_weight_left.setOnClickListener(this);
//        btn_weight_right.setOnClickListener(this);
//        btn_bfat_left.setOnClickListener(this);
//        btn_bfat_right.setOnClickListener(this);
//        btn_fat_left.setOnClickListener(this);
//        btn_fat_right.setOnClickListener(this);

    }

    @OnClick({R.id.btn_weight_right,R.id.btn_weight_left,R.id.btn_bfat_left,R.id.btn_bfat_right,R.id.btn_fat_right,R.id.btn_fat_left})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_weight_left:
                weight_chart.setDate(leftXAsix,weight.subList(0,weight.size()/2),maxWeight);
                break;
            case R.id.btn_weight_right:
                weight_chart.setDate(rightXAsix,weight.subList(weight.size()/2,weight.size()),maxWeight);
                break;
            case R.id.btn_bfat_left:
                bfat_chart.setDate(leftXAsix,bfat.subList(0,bfat.size()/2),maxBFat);
                break;
            case R.id.btn_bfat_right:
                bfat_chart.setDate(rightXAsix,bfat.subList(bfat.size()/2,bfat.size()),maxBFat);
                break;
            case R.id.btn_fat_left:
                fat_chart.setDate(leftXAsix,fat.subList(0,fat.size()/2),maxFat);
                break;
            case R.id.btn_fat_right:
                fat_chart.setDate(rightXAsix,fat.subList(fat.size()/2,fat.size()),maxFat);
                break;
        }
    }
}
