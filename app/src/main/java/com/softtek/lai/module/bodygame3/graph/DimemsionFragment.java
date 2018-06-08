package com.softtek.lai.module.bodygame3.graph;


import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment2;
import com.softtek.lai.module.bodygame3.graph.model.GirthModel;
import com.softtek.lai.module.bodygame3.graph.presenter.GraphDimemsionPresenter;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.widgets.chart.Chart;
import com.softtek.lai.widgets.chart.Entry;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import zilla.libcore.ui.InjectLayout;

/**
 * A simple {@link Fragment} subclass.
 */
@InjectLayout(R.layout.fragment_dimemsion)
public class DimemsionFragment extends LazyBaseFragment2<GraphDimemsionPresenter> implements GraphDimemsionPresenter.GraphGirthView {

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

    @InjectView(R.id.btn_bust_left)
    Button btn_bust_left;
    @InjectView(R.id.btn_bust_right)
    Button btn_bust_right;
    @InjectView(R.id.btn_waist_left)
    Button btn_waist_left;
    @InjectView(R.id.btn_waist_right)
    Button btn_waist_right;
    @InjectView(R.id.btn_hipline_left)
    Button btn_hipline_left;
    @InjectView(R.id.btn_hipline_right)
    Button btn_hipline_right;
    @InjectView(R.id.btn_upper_left)
    Button btn_upper_left;
    @InjectView(R.id.btn_upper_right)
    Button btn_upper_right;
    @InjectView(R.id.btn_datui_left)
    Button btn_datui_left;
    @InjectView(R.id.btn_datui_right)
    Button btn_datui_right;
    @InjectView(R.id.btn_xiaotui_left)
    Button btn_xiaotui_left;
    @InjectView(R.id.btn_xiaotui_right)
    Button btn_xiaotui_right;


    List<String> xAsix=new ArrayList<>();
    List<Entry> bust=new ArrayList<>();
    List<Entry> waist=new ArrayList<>();
    List<Entry> hipline=new ArrayList<>();
    List<Entry> upper=new ArrayList<>();
    List<Entry> xiaotui=new ArrayList<>();
    List<Entry> datui=new ArrayList<>();

    List<Entry> bustR=new ArrayList<>();
    List<Entry> waistR=new ArrayList<>();
    List<Entry> hiplineR=new ArrayList<>();
    List<Entry> upperR=new ArrayList<>();
    List<Entry> xiaotuiR=new ArrayList<>();
    List<Entry> datuiR=new ArrayList<>();

    float maxBust = 0;
    float maxWaist=0;
    float maxHipline=0;
    float maxUpper = 0;
    float maxDatui=0;
    float maxXiaotui=0;

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
        if (!TextUtils.isEmpty(getArguments().getString("classId"))) {
            getPresenter().getGraph(getArguments().getLong("accountId"),
                    getArguments().getString("classId"));
        }else {
            setContentEmpty(true);
            setContentShown(true);
        }
    }

    List<String> leftXAsix;
    List<String> rightXAsix;

    @Override
    public void onSuccess(List<GirthModel> data){

        setContentEmpty(false);
        setContentShown(true);
        if (data!=null) {
            for (int i = 0, j = data.size(); i < j; i++) {
                GirthModel model = data.get(i);
                if(model.getWeekDay()==0){
                    xAsix.add("初始");
                }else {
                    xAsix.add("第" + model.getWeekDay() + "周");
                }
                float bustValue = Float.valueOf(model.getCircum());
                float waistValue = Float.valueOf(model.getWaistline());
                float hiplineValue = Float.valueOf(model.getHiplie());
                float upperValue = Float.valueOf(model.getUpArmGirth());
                float datuiValue = Float.valueOf(model.getUpLegGirth());
                float xiaotuiValue = Float.valueOf(model.getDoLegGirth());
                maxBust = bustValue > maxBust ? bustValue : maxBust;
                maxWaist = waistValue > maxWaist ? waistValue : maxWaist;
                maxHipline = hiplineValue > maxHipline ? hiplineValue : maxHipline;
                maxUpper = upperValue > maxUpper ? upperValue : maxUpper;
                maxDatui = datuiValue > maxDatui ? datuiValue : maxDatui;
                maxXiaotui = xiaotuiValue > maxXiaotui ? xiaotuiValue : maxXiaotui;

                int middle=j/2;
                int index=i<middle?model.getWeekDay():model.getWeekDay()-middle;
                if(i<middle){
                    if(bustValue!=0){
                        bust.add(new Entry(index, bustValue));
                    }
                    if(waistValue!=0){
                        waist.add(new Entry(index, waistValue));
                    }
                    if(hiplineValue!=0){
                        hipline.add(new Entry(index, hiplineValue));
                    }
                    if(upperValue!=0){
                        upper.add(new Entry(index, upperValue));
                    }
                    if(datuiValue!=0){
                        datui.add(new Entry(index, datuiValue));
                    }
                    if(waistValue!=0){
                        xiaotui.add(new Entry(index, xiaotuiValue));
                    }

                }else {
                    if(bustValue!=0){
                        bustR.add(new Entry(model.getWeekDay() - middle, bustValue));
                    }
                    if(waistValue!=0){
                        waistR.add(new Entry(model.getWeekDay() - middle, waistValue));
                    }
                    if(hiplineValue!=0){
                        hiplineR.add(new Entry(model.getWeekDay() - middle, hiplineValue));
                    }
                    if(upperValue!=0){
                        upperR.add(new Entry(model.getWeekDay() - middle, upperValue));
                    }
                    if(datuiValue!=0){
                        datuiR.add(new Entry(model.getWeekDay() - middle, datuiValue));
                    }
                    if(waistValue!=0){
                        xiaotuiR.add(new Entry(model.getWeekDay() - middle, xiaotuiValue));
                    }
                }

            }
            leftXAsix=xAsix.subList(0,xAsix.size()/2);
            rightXAsix=xAsix.subList(xAsix.size()/2,xAsix.size());
            bust_chart.setDate(leftXAsix,bust,maxBust);
            waist_chart.setDate(leftXAsix,waist ,maxWaist);
            hipline_chart.setDate(leftXAsix,hipline,maxHipline);
            upper_chart.setDate(leftXAsix,upper ,maxUpper);
            datui_chart.setDate(leftXAsix,datui ,maxDatui);
            xiaotui_chart.setDate(leftXAsix,xiaotui,maxXiaotui);
        }

    }

    @Override
    public void onFaile() {
        setContentEmpty(false);
        setContentShown(true);
    }

    @Override
    protected void initViews() {
        //设置背景颜色
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

        bust_chart.setBackground(orange);
        waist_chart.setBackground(cyan);
        hipline_chart.setBackground(indigo);
        upper_chart.setBackground(orange);
        datui_chart.setBackground(cyan);
        xiaotui_chart.setBackground(indigo);
    }

    @Override
    protected void initDatas() {
        setPresenter(new GraphDimemsionPresenter(this));
    }

    @OnClick({R.id.btn_bust_left,
            R.id.btn_bust_right,
            R.id.btn_waist_left,
            R.id.btn_waist_right,
            R.id.btn_hipline_left,
            R.id.btn_hipline_right,
            R.id.btn_upper_left,
            R.id.btn_upper_right,
            R.id.btn_datui_left,
            R.id.btn_datui_right,
            R.id.btn_xiaotui_left,
            R.id.btn_xiaotui_right})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_bust_left:
                btn_bust_right.setVisibility(View.VISIBLE);
                btn_bust_left.setVisibility(View.GONE);
                bust_chart.setDate(leftXAsix,bust,maxBust);
                break;
            case R.id.btn_bust_right:
                btn_bust_right.setVisibility(View.GONE);
                btn_bust_left.setVisibility(View.VISIBLE);
                bust_chart.setDate(rightXAsix,bustR,maxBust);
                break;
            case R.id.btn_waist_left:
                btn_waist_left.setVisibility(View.GONE);
                btn_waist_right.setVisibility(View.VISIBLE);
                waist_chart.setDate(leftXAsix,waist,maxWaist);
                break;
            case R.id.btn_waist_right:
                btn_waist_left.setVisibility(View.VISIBLE);
                btn_waist_right.setVisibility(View.GONE);
                waist_chart.setDate(rightXAsix,waistR ,maxWaist);
                break;
            case R.id.btn_hipline_left:
                btn_hipline_right.setVisibility(View.VISIBLE);
                btn_hipline_left.setVisibility(View.GONE);
                hipline_chart.setDate(leftXAsix,hipline,maxHipline);
                break;
            case R.id.btn_hipline_right:
                btn_hipline_right.setVisibility(View.GONE);
                btn_hipline_left.setVisibility(View.VISIBLE);
                hipline_chart.setDate(rightXAsix,hiplineR,maxHipline);
                break;
            case R.id.btn_upper_left:
                btn_upper_right.setVisibility(View.VISIBLE);
                btn_upper_left.setVisibility(View.GONE);
                upper_chart.setDate(leftXAsix,upper ,maxUpper);
                break;
            case R.id.btn_upper_right:
                btn_upper_right.setVisibility(View.GONE);
                btn_upper_left.setVisibility(View.VISIBLE);
                upper_chart.setDate(rightXAsix,upperR,maxUpper);
                break;
            case R.id.btn_datui_left:
                btn_datui_left.setVisibility(View.GONE);
                btn_datui_right.setVisibility(View.VISIBLE);
                datui_chart.setDate(leftXAsix,datui ,maxDatui);
                break;
            case R.id.btn_datui_right:
                btn_datui_left.setVisibility(View.VISIBLE);
                btn_datui_right.setVisibility(View.GONE);
                datui_chart.setDate(rightXAsix,datuiR,maxDatui);
                break;
            case R.id.btn_xiaotui_left:
                btn_xiaotui_right.setVisibility(View.VISIBLE);
                btn_xiaotui_left.setVisibility(View.GONE);
                xiaotui_chart.setDate(leftXAsix,xiaotui,maxXiaotui);
                break;
            case R.id.btn_xiaotui_right:
                btn_xiaotui_right.setVisibility(View.GONE);
                btn_xiaotui_left.setVisibility(View.VISIBLE);
                xiaotui_chart.setDate(rightXAsix,xiaotuiR,maxXiaotui);
                break;
        }
    }

}
