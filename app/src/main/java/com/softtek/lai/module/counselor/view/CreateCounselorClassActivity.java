package com.softtek.lai.module.counselor.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.counselor.adapter.CounselorClassAdapter;
import com.softtek.lai.module.counselor.presenter.CounselorClassImpl;
import com.softtek.lai.module.counselor.presenter.ICounselorClassPresenter;
import com.softtek.lai.module.login.model.User;
import com.softtek.lai.utils.ACache;
import com.softtek.lai.utils.SoftInputUtil;
import com.softtek.lai.widgets.WheelView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;
/**
 * Created by jarvis.liu on 3/22/2016.
 * 体管赛 创建班级
 */
@InjectLayout(R.layout.activity_create_counselor_class)
public class CreateCounselorClassActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener {

    @LifeCircleInject
    ValidateLife validateLife;


    @InjectView(R.id.tv_left)
    TextView tv_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @Required(order = 1, messageResId = R.string.className)
    @InjectView(R.id.edit_class_name)
    EditText edit_class_name;

    @InjectView(R.id.text_time_value)
    TextView text_time_value;

    @InjectView(R.id.img_choose)
    ImageView img_choose;

    @InjectView(R.id.img_next)
    ImageView img_next;

    @InjectView(R.id.lin_time)
    LinearLayout lin_time;


    private ICounselorClassPresenter counselorClassPresenter;
    private ACache aCache;

    private CounselorClassAdapter adapter;
    private List<String> yearList = new ArrayList<String>();
    private List<String> monthList = new ArrayList<String>();
    private String select_year = "";
    private String select_month = "";
    private int year;
    private int monthOfYear;
    private int dayOfMonth;

    private Date start_time;
    private Date end_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_left.setOnClickListener(this);


        img_choose.setOnClickListener(this);

        img_next.setOnClickListener(this);
    }

    @Override
    protected void initViews() {
        tv_left.setBackgroundResource(R.drawable.back);
        //tv_left.setLayoutParams(new Toolbar.LayoutParams(DisplayUtil.dip2px(this,15),DisplayUtil.dip2px(this,30)));
        tv_title.setText("创建班级");

    }

    @Override
    protected void initDatas() {
        counselorClassPresenter = new CounselorClassImpl(this);
        aCache= ACache.get(this, Constants.USER_ACACHE_DATA_DIR);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH) + 1;
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        yearList.add(year + "");

        monthList.add(monthOfYear + "");
        if (monthOfYear == 12) {
            monthList.add(1 + "");
            yearList.add(year + 1 + "");
        } else {
            monthList.add(monthOfYear + 1 + "");
        }

    }

    @Override
    public void onClick(View v) {
        SoftInputUtil.hidden(this);
        switch (v.getId()) {
            case R.id.img_next:
                validateLife.validate();
                break;

            case R.id.tv_left:
                finish();
                break;

            case R.id.img_choose:
                showDateDialog();
                break;
        }
    }

    private void showDateDialog(){
        View outerView = LayoutInflater.from(this).inflate(R.layout.wheel_view, null);

        final WheelView wheel_month = (WheelView) outerView.findViewById(R.id.wheel_month);
        final WheelView wheel_year = (WheelView) outerView.findViewById(R.id.wheel_year);

        wheel_month.setOffset(1);
        wheel_month.setItems(monthList);
        wheel_month.setSeletion(0);
        wheel_month.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                select_month = item;
                if(select_month.equals(monthList.get(0))){//当选择月列表第一位时，强制跳转到年列表第一位
                    wheel_year.setSeletion(0);
                    select_year=yearList.get(0);
                }
                if (yearList.size() == 2) {//年列表有两位，说明是跨年情况
                    if(select_month.equals(monthList.get(1))){//跨年情况考虑，当选择到1月时，强制跳转到年列表第二年
                        wheel_year.setSeletion(1);
                        select_year=yearList.get(1);
                    }
                }
            }
        });

        wheel_year.setOffset(1);
        wheel_year.setItems(yearList);
        wheel_year.setSeletion(0);
        wheel_year.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.d("Jarvis", "[Dialog]selectedIndex: " + selectedIndex + ", item: " + item);
                select_year = item;
                if (yearList.size() == 2) {//年列表有两位，说明是跨年情况
                    if (select_year.equals(yearList.get(1))) {//当选择年列表第二位时，强制跳转到月列表第二位
                        select_month = monthList.get(1);
                        wheel_month.setSeletion(1);
                    } else {//当选择年列表第一位时，强制跳转到月列表第一位
                        select_month = monthList.get(0);
                        wheel_month.setSeletion(0);
                    }
                }
            }
        });

        new AlertDialog.Builder(this)
                .setTitle("请选择起始月")
                .setView(outerView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String timeStr = "";
                        String startTimeStr="";
                        //直接按确定按钮，就无法获得选择到的年月，此时默认给第一位
                        if (select_year.equals("")) {
                            select_year = yearList.get(0);
                        }
                        if (select_month.equals("")) {
                            select_month = monthList.get(0);
                        }

                        if (select_month.equals("12")) {
                            int end_year = Integer.parseInt(select_year) + 1;
                            timeStr = yearList.get(0) + "年" + select_month + "月" + "-" + end_year + "年2月";

                            startTimeStr=yearList.get(0)+"-"+select_month+"-1";
                        } else if (select_month.equals("11")) {
                            int end_year = Integer.parseInt(select_year) + 1;
                            timeStr = yearList.get(0) + "年" + select_month + "月" + "-" + end_year + "年1月";
                            startTimeStr=yearList.get(0)+"-"+select_month+"-1";
                        } else {
                            if (yearList.size() == 2) {
                                if (select_year.equals(yearList.get(1))) {
                                    int end_month = Integer.parseInt(select_month) + 2;
                                    timeStr = yearList.get(1) + "年" + select_month + "月" + "-" + yearList.get(1) + "年" + end_month + "月";
                                    startTimeStr=yearList.get(1)+"-"+select_month+"-1";
                                }else {
                                    int end_month = Integer.parseInt(select_month) + 2;
                                    timeStr = yearList.get(0) + "年" + select_month + "月" + "-" + yearList.get(0) + "年" + end_month + "月";
                                    startTimeStr=yearList.get(0)+"-"+select_month+"-1";
                                }
                            } else {
                                int end_month = Integer.parseInt(select_month) + 2;
                                timeStr = yearList.get(0) + "年" + select_month + "月" + "-" + yearList.get(0) + "年" + end_month + "月";
                                startTimeStr=yearList.get(0)+"-"+select_month+"-1";
                            }

                        }
                        try
                        {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            System.out.println("startTimeStr:"+startTimeStr);
                            start_time = sdf.parse(startTimeStr);
                        }
                        catch (ParseException e)
                        {
                            System.out.println(e.getMessage());
                        }

                        text_time_value.setText(timeStr);
                        select_year = "";
                        select_month = "";
                    }
                })
                .show();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onValidationSucceeded() {
        if ("".equals(text_time_value.getText().toString())) {
            Util.toastMsg("请选择时间段");
        }else {
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(start_time);
            rightNow.add(Calendar.MONTH, 3);//日期加3个月
            Date time_info=rightNow.getTime();
            rightNow.setTime(time_info);
            rightNow.add(Calendar.DAY_OF_YEAR, -1);//日期减1天
            end_time=rightNow.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println("--------end_time-------:" + sdf.format(end_time));
            System.out.println("--------start_time-------:" + sdf.format(start_time));
            User user=(User)aCache.getAsObject(Constants.USER_ACACHE_KEY);
            counselorClassPresenter.createClass(edit_class_name.getText().toString(),sdf.format(start_time),sdf.format(end_time),user.getUserid());
        }
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView, failedRule);
    }


}
