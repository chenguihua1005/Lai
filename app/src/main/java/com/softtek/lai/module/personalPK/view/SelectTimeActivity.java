package com.softtek.lai.module.personalPK.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.personalPK.model.PKCreatModel;
import com.softtek.lai.module.personalPK.model.PKForm;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.widgets.WheelView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_select_time)
public class SelectTimeActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    @InjectView(R.id.rl_start)
    RelativeLayout rl_start;
    @InjectView(R.id.rl_end)
    RelativeLayout rl_end;
    @InjectView(R.id.tv_start)
    TextView tv_start;
    @InjectView(R.id.tv_end)
    TextView tv_end;

    List<String> years=new ArrayList<>();
    List<String> days=new ArrayList<>();
    List<String> days_31=new ArrayList<>();
    List<String> days_30=new ArrayList<>();
    List<String> days_28=new ArrayList<>();
    List<String> days_29=new ArrayList<>();
    List<String> months=new ArrayList<>();
    List<String> months_year=new ArrayList<>();

    int currentYear;
    int currentMonth;
    int currentDay;

    PKForm form;
    PKCreatModel model;

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        rl_start.setOnClickListener(this);
        rl_end.setOnClickListener(this);
        tv_title.setText("选择PK挑战时间");
        tv_right.setText("保存");
    }

    @Override
    protected void initDatas() {
        model=getIntent().getParcelableExtra("pkmodel");
        form=new PKForm();
        form.setBeChallenged(model.getBeChallenged());
        form.setChallenged(model.getChallenged());
        form.setChip(model.getChip());
        form.setChipType(model.getChipType());
        form.setTarget(model.getTarget());
        form.setTargetType(model.getTargetType());
        fl_right.setEnabled(false);
        //获取当前年月日
        currentYear= DateUtil.getInstance().getCurrentYear();
        currentMonth=DateUtil.getInstance().getCurrentMonth();
        currentDay=DateUtil.getInstance().getCurrentDay();
        years.add(currentYear+"年");
        years.add(currentYear+1+"年");
        for(int i=1;i<=12;i++){
            months_year.add(i+"月");
            if(i>=currentMonth){
                months.add(i+"月");
            }
        }
        Log.i("当前剩余月"+months.size());
        for(int i=1;i<=31;i++){
            days_31.add(i+"日");
            if(i<31){
                days_30.add(i+"日");
            }
            if(i<30){
                days_29.add(i+"日");
            }
            if(i<29){
                days_28.add(i+"日");
            }
        }
        //添加当前月的剩余天数
        int last_day=DateUtil.getInstance().getDay(DateUtil.getLastDateOfMonth(new Date()));
        for(int i=currentDay;i<=last_day;i++){
            days.add(i+"日");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
            case R.id.fl_right:
                //保存
                break;
            case R.id.rl_start:
                showDateDialog();
                break;
            case R.id.rl_end:

                break;

        }
    }


    private void showDateDialog() {
        View outerView = LayoutInflater.from(this).inflate(R.layout.pk_select_time_wheel_view, null);
        final WheelView wheel_year = (WheelView) outerView.findViewById(R.id.year);
        final WheelView wheel_month = (WheelView) outerView.findViewById(R.id.month);
        final WheelView wheel_day = (WheelView) outerView.findViewById(R.id.day);
        wheel_year.setOffset(1);
        wheel_year.setItems(years);

        wheel_year.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                if (selectedIndex == 0) {
                    wheel_month.setItems(months);//设置当前年的剩余月份
                    wheel_month.setSeletion(0);//并设置第一个为起始月分
                    wheel_day.setItems(days);
                    wheel_day.setSeletion(0);
                } else if (selectedIndex == 1) {
                    wheel_month.setItems(months_year);//设置全部的月份
                    wheel_month.setSeletion(months.indexOf(months.get(0)));//设置月为当前月
                }

            }
        });

        wheel_month.setOffset(1);
        wheel_month.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {

                switch (item){
                    //大月
                    case "1月":
                        if(wheel_year.getSeletedIndex()==0){//选择在当前年上
                            wheel_day.setItems(days);
                            wheel_day.setSeletion(0);
                            break;
                        }
                    case "3月":
                        if(wheel_year.getSeletedIndex()==0){//选择在当前年上
                            wheel_day.setItems(days);
                            wheel_day.setSeletion(0);
                            break;
                        }
                    case "5月":
                        if(wheel_year.getSeletedIndex()==0){//选择在当前年上
                            wheel_day.setItems(days);
                            wheel_day.setSeletion(0);
                            break;
                        }
                    case "7月":
                        if(wheel_year.getSeletedIndex()==0){//选择在当前年上
                            wheel_day.setItems(days);
                            wheel_day.setSeletion(0);
                            break;
                        }
                    case "8月":
                        if(wheel_year.getSeletedIndex()==0){//选择在当前年上
                            wheel_day.setItems(days);
                            wheel_day.setSeletion(0);
                            break;
                        }
                    case "10月":
                        if(wheel_year.getSeletedIndex()==0){//选择在当前年上
                            wheel_day.setItems(days);
                            wheel_day.setSeletion(0);
                            break;
                        }
                    case "12月":
                        if(wheel_year.getSeletedIndex()==0){//选择在当前年上
                            wheel_day.setItems(days);
                            wheel_day.setSeletion(0);
                            break;
                        }
                        wheel_day.setItems(days_31);
                        wheel_day.setSeletion(days_31.indexOf(currentDay+"日"));
                        break;
                    //小月
                    case "4月":
                        if(wheel_year.getSeletedIndex()==0){//选择在当前年上
                            wheel_day.setItems(days);
                            wheel_day.setSeletion(0);
                            break;
                        }
                    case "6月":
                        if(wheel_year.getSeletedIndex()==0){//选择在当前年上
                            wheel_day.setItems(days);
                            wheel_day.setSeletion(0);
                            break;
                        }
                    case "9月":
                        if(wheel_year.getSeletedIndex()==0){//选择在当前年上
                            wheel_day.setItems(days);
                            wheel_day.setSeletion(0);
                            break;
                        }
                    case "11月":
                        if(wheel_year.getSeletedIndex()==0){//选择在当前年上
                            wheel_day.setItems(days);
                            wheel_day.setSeletion(0);
                            break;
                        }
                        wheel_day.setItems(days_30);
                        wheel_day.setSeletion(days_30.indexOf(currentDay + "日"));
                        break;
                    case "2月":
                        if(wheel_year.getSeletedIndex()==0){//选择在当前年上
                            wheel_day.setItems(days);
                            wheel_day.setSeletion(0);
                            break;
                        }
                       if(DateUtil.getInstance().commonOrLeapYear(currentYear)==DateUtil.COMMON_YEAR){
                            wheel_day.setItems(days_28);
                            wheel_day.setSeletion(days_28.indexOf(currentDay + "日"));
                       }else{
                           wheel_day.setItems(days_29);
                           wheel_day.setSeletion(days_29.indexOf(currentDay+"日"));
                       }
                    break;
                }


            }
        });
        wheel_day.setOffset(1);
        wheel_day.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {

            }
        });

        //设置初始值
        wheel_year.setSeletion(0);
        new AlertDialog.Builder(this)
                .setTitle("请选择起始年月日")
                .setView(outerView)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {}
                        }).create()
                .show();

    }
}
