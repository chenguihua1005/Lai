package com.softtek.lai.module.bodygame3.graph;


import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment2;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.graph.model.WeightModel;
import com.softtek.lai.module.bodygame3.graph.net.GraphService;
import com.softtek.lai.module.bodygame3.graph.presenter.GraphPresenter;
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
public class LossWeightFragment extends LazyBaseFragment2<GraphPresenter> implements GraphPresenter.GraphView {

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

    List<String> xAsix = new ArrayList<>();
    List<Entry> weightL = new ArrayList<>();
    List<Entry> bfatL = new ArrayList<>();
    List<Entry> fatL = new ArrayList<>();
    List<Entry> weightR = new ArrayList<>();
    List<Entry> bfatR = new ArrayList<>();
    List<Entry> fatR = new ArrayList<>();
    float maxWeight = 0;
    float maxBFat = 0;
    float maxFat = 0;

    public LossWeightFragment() {
        // Required empty public constructor
    }

    public static Fragment getInstance(long accountId, String classId) {
        Fragment fragment = new LossWeightFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("accountId", accountId);
        bundle.putString("classId", classId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void lazyLoad() {
        if (!TextUtils.isEmpty(getArguments().getString("classId"))) {
            getPresenter().getGraph(getArguments().getLong("accountId"),
                    getArguments().getString("classId"));
        } else {
            setContentEmpty(true);
            setContentShown(true);
        }
    }

    List<String> leftXAsix;
    List<String> rightXAsix;

    @Override
    public void onFaile() {
        setContentEmpty(true);
        setContentShown(false);
    }


    @Override
    public void onSuccess(List<WeightModel> data) {
        setContentEmpty(false);
        setContentShown(true);
        if (data != null) {
            for (int i = 0, j = data.size(); i < j; i++) {
                WeightModel model = data.get(i);
                if(model.getWeekDay()==0){
                    xAsix.add("初始");
                } else {
                    xAsix.add("第" + model.getWeekDay() + "周");
                }
                float weightValue = Float.valueOf(model.getWeight());
                float bfatValue = Float.valueOf(model.getPysical());
                float fatValue = Float.valueOf(model.getFat());
                maxWeight = weightValue > maxWeight ? weightValue : maxWeight;
                maxBFat = bfatValue > maxBFat ? bfatValue : maxBFat;
                maxFat = fatValue > maxFat ? fatValue : maxFat;

                int middle = j / 2;
                if (i < middle) {
                    int index = model.getWeekDay();
                    if (weightValue != 0) {
                        weightL.add(new Entry(index, weightValue));
                    }
                    if (bfatValue != 0) {
                        bfatL.add(new Entry(index, bfatValue));
                    }
                    if (fatValue != 0) {
                        fatL.add(new Entry(index, fatValue));
                    }
                } else {
                    if (weightValue != 0) {
                        weightR.add(new Entry(model.getWeekDay() - middle, weightValue));
                    }
                    if (bfatValue != 0) {
                        bfatR.add(new Entry(model.getWeekDay() - middle, bfatValue));
                    }
                    if (fatValue != 0) {
                        fatR.add(new Entry(model.getWeekDay() - middle, fatValue));
                    }
                }
            }
            leftXAsix = xAsix.subList(0, xAsix.size() / 2);
            rightXAsix = xAsix.subList(xAsix.size() / 2, xAsix.size());
            weight_chart.setDate(leftXAsix,weightL, maxWeight);
            bfat_chart.setDate(leftXAsix,bfatL , maxBFat);
            fat_chart.setDate(leftXAsix, fatL, maxFat);
        }
    }


    @Override
    protected void initViews() {
        int radius = DisplayUtil.dip2px(getContext(), 5);
        GradientDrawable orange = new GradientDrawable();
        orange.setColors(new int[]{0xFFFEA003, 0xFFED7460});
        orange.setCornerRadius(radius);
        GradientDrawable cyan = new GradientDrawable();
        cyan.setColors(new int[]{0xFF77BA2B, 0xFFA6C225});
        cyan.setCornerRadius(radius);
        GradientDrawable indigo = new GradientDrawable();
        indigo.setColors(new int[]{0xFF19BC84, 0xFF1899A0});
        indigo.setCornerRadius(radius);
        weight_chart.setBackground(orange);
        bfat_chart.setBackground(cyan);
        fat_chart.setBackground(indigo);
    }

    @Override
    protected void initDatas() {
        setPresenter(new GraphPresenter(this));
    }

    @OnClick({R.id.btn_weight_right, R.id.btn_weight_left, R.id.btn_bfat_left, R.id.btn_bfat_right, R.id.btn_fat_right, R.id.btn_fat_left})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_weight_left:
                btn_weight_right.setVisibility(View.VISIBLE);
                btn_weight_left.setVisibility(View.GONE);
                weight_chart.setDate(leftXAsix, weightL, maxWeight);
                break;
            case R.id.btn_weight_right:
                btn_weight_right.setVisibility(View.GONE);
                btn_weight_left.setVisibility(View.VISIBLE);
                weight_chart.setDate(rightXAsix,weightR, maxWeight);
                break;
            case R.id.btn_bfat_left:
                btn_bfat_left.setVisibility(View.GONE);
                btn_bfat_right.setVisibility(View.VISIBLE);
                bfat_chart.setDate(leftXAsix,bfatL , maxBFat);
                break;
            case R.id.btn_bfat_right:
                btn_bfat_left.setVisibility(View.VISIBLE);
                btn_bfat_right.setVisibility(View.GONE);
                bfat_chart.setDate(rightXAsix,bfatR , maxBFat);
                break;
            case R.id.btn_fat_left:
                btn_fat_right.setVisibility(View.VISIBLE);
                btn_fat_left.setVisibility(View.GONE);
                fat_chart.setDate(leftXAsix, fatL, maxFat);
                break;
            case R.id.btn_fat_right:
                btn_fat_right.setVisibility(View.GONE);
                btn_fat_left.setVisibility(View.VISIBLE);
                fat_chart.setDate(rightXAsix,fatR , maxFat);
                break;
        }
    }


}
