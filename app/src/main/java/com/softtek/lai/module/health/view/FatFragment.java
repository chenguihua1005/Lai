package com.softtek.lai.module.health.view;

import android.app.ProgressDialog;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.health.model.FatlistModel;
import com.softtek.lai.module.health.model.HealthCircrumModel;
import com.softtek.lai.module.health.model.HealthFatModel;
import com.softtek.lai.module.health.model.HealthHiplieModel;
import com.softtek.lai.module.health.model.HealthUpArmGirthModel;
import com.softtek.lai.module.health.model.HealthWaistlineModel;
import com.softtek.lai.module.health.model.HealthWeightModel;
import com.softtek.lai.module.health.model.HealthdoLegGirthModel;
import com.softtek.lai.module.health.model.HealthupLegGirthModel;
import com.softtek.lai.module.health.model.PysicalModel;
import com.softtek.lai.module.health.presenter.HealthRecordManager;
import com.softtek.lai.module.health.presenter.HealthyRecordImpl;
import com.softtek.lai.module.health.presenter.IHealthyRecord;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.widgets.chart.Chart;
import com.softtek.lai.widgets.chart.Entry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by John on 2016/4/12.
 */
@InjectLayout(R.layout.fragment_weight)
public class FatFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener, HealthRecordManager.HealthRecordCallBack, View.OnClickListener {

    @InjectView(R.id.chart)
    Chart chart;

    @InjectView(R.id.rg)
    RadioGroup radio_group;
    @InjectView(R.id.week)
    RadioButton week;
    @InjectView(R.id.month)
    RadioButton month;
    @InjectView(R.id.quarter)
    RadioButton quarter;
    @InjectView(R.id.year)
    RadioButton year;
    @InjectView(R.id.bt_left)
    Button bt_left;
    @InjectView(R.id.bt_right)
    Button bt_right;

    List<Entry> dates = new ArrayList<>();
    List<String> days = new ArrayList<>();
    char type = '6';
    int n = 7;
    boolean state = true;
    int flag = 0;
    IHealthyRecord iHealthyRecord;
    private ProgressDialog progressDialog;

    HealthRecordManager healthRecordManager;

    @Override
    protected void initViews() {
        radio_group.setOnCheckedChangeListener(this);
        bt_left.setOnClickListener(this);
        bt_right.setOnClickListener(this);
        week.setOnClickListener(this);
        month.setOnClickListener(this);
        quarter.setOnClickListener(this);
        year.setOnClickListener(this);

        GradientDrawable gradient = new GradientDrawable();
        gradient.setColors(new int[]{0xFF77BA2B, 0xFFA6C225});
        gradient.setCornerRadius(DisplayUtil.dip2px(getContext(), 5));
        chart.setBackground(gradient);
    }

    @Override
    protected void initDatas() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("加载中...");
        progressDialog.setCanceledOnTouchOutside(false);
        healthRecordManager = new HealthRecordManager(this);
        dates.clear();
        iHealthyRecord = new HealthyRecordImpl();
        String nowdate7 = getPeriodDate(type, 0).toString();
        String nowdate6 = getPeriodDate(type, 1).toString();
        String nowdate5 = getPeriodDate(type, 2).toString();
        String nowdate4 = getPeriodDate(type, 3).toString();
        String nowdate3 = getPeriodDate(type, 4).toString();
        String nowdate2 = getPeriodDate(type, 5).toString();
        String nowdate1 = getPeriodDate(type, 6).toString();
        days.add(formdate(nowdate1));
        days.add(formdate(nowdate2));
        days.add(formdate(nowdate3));
        days.add(formdate(nowdate4));
        days.add(formdate(nowdate5));
        days.add(formdate(nowdate6));
        days.add(formdate(nowdate7));
        progressDialog.show();
        healthRecordManager.doGetHealthfatRecords(getDateform(nowdate1), getDateform(nowdate7), 1);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }

    /**
     * 获取阶段日期
     *
     * @param dateType
     * @author Yangtse
     */
    public static StringBuilder getPeriodDate(char dateType, int n) {
        Calendar c = Calendar.getInstance(); // 当时的日期和时间
        int hour; // 需要更改的小时
        int day; // 需要更改的天数
        switch (dateType) {
            case '0': // 1小时前
                hour = c.get(Calendar.HOUR_OF_DAY) - 1;
                c.set(Calendar.HOUR_OF_DAY, hour);
                break;
            case '1': // 2小时前
                hour = c.get(Calendar.HOUR_OF_DAY) - 2;
                c.set(Calendar.HOUR_OF_DAY, hour);
                break;
            case '2': // 3小时前
                hour = c.get(Calendar.HOUR_OF_DAY) - 3;
                c.set(Calendar.HOUR_OF_DAY, hour);
                break;
            case '3': // 6小时前
                hour = c.get(Calendar.HOUR_OF_DAY) - 6;
                c.set(Calendar.HOUR_OF_DAY, hour);
                break;
            case '4': // 12小时前
                hour = c.get(Calendar.HOUR_OF_DAY) - 12;
                c.set(Calendar.HOUR_OF_DAY, hour);
                break;
            case '5': // 一天前
                day = c.get(Calendar.DAY_OF_MONTH) - 1;
                c.set(Calendar.DAY_OF_MONTH, day);
                break;
            case '6': // 一星期前
                day = c.get(Calendar.DAY_OF_MONTH) - n;
                c.set(Calendar.DAY_OF_MONTH, day);
                break;
            case '7': // 一个月前
                day = c.get(Calendar.DAY_OF_MONTH) - 30 * n;
                c.set(Calendar.DAY_OF_MONTH, day);
                break;
        }
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        StringBuilder strForwardDate = new StringBuilder().append(mYear).append(
                (mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append(
                (mDay < 10) ? "0" + mDay : mDay);
        return strForwardDate;
    }

    public String formdate(String nowdate) {
        String date;
        if (nowdate.substring(4, 5).equals("0")) {
            date = nowdate.substring(5, 6) + "/" + nowdate.substring(6, 8);
        } else {
            date = nowdate.substring(4, 6) + "/" + nowdate.substring(6, 8);

        }
        return date;

    }

    public String getDateform(String nowdate) {
        return nowdate.substring(0, 4) + "-" + nowdate.substring(4, 6) + "-" + nowdate.substring(6, 8);

    }

    @Override
    public void getHealthPysicalRecords(PysicalModel pysicalModel) {

    }

    @Override
    public void getHealthWeightRecords(HealthWeightModel healthWeightModel) {

    }

    @Override
    public void getHealthfatRecords(HealthFatModel healthFatModel) {
        try {
            if (progressDialog != null)
                progressDialog.dismiss();
            if (healthFatModel == null) {
                return;
            }
            List<FatlistModel> models = healthFatModel.getFatlist();
            float max = 0;
            for (int i = 0; i < models.size(); i++) {
                float fat = Float.parseFloat(models.get(i).getFat());
                max = fat > max ? fat : max;
                Entry entry = new Entry(i, fat);
                dates.add(entry);
            }
            chart.setDate(days, dates, max);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getHealthcircumRecords(HealthCircrumModel healthCircrumModel) {

    }

    @Override
    public void getHealthwaistlineRecords(HealthWaistlineModel healthWaistlineModel) {

    }

    @Override
    public void getHealthhiplieRecords(HealthHiplieModel healthHiplieModel) {

    }

    @Override
    public void getHealthupArmGirthRecords(HealthUpArmGirthModel healthUpArmGirthModel) {

    }

    @Override
    public void getGetHealthupLegGirthRecords(HealthupLegGirthModel healthupLegGirthModel) {

    }

    @Override
    public void getHealthdoLegGirthRecords(HealthdoLegGirthModel healthdoLegGirthModel) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.bt_left: {
                switch (flag) {
                    case 0:
                        if (state != true) {
                            n = n + 7;
                        }
                        state = true;
                        days.clear();
                        dates.clear();
                        String nowdate7 = getPeriodDate(type, n) + "";
                        String nowdate6 = getPeriodDate(type, n + 1) + "";
                        String nowdate5 = getPeriodDate(type, n + 2) + "";
                        String nowdate4 = getPeriodDate(type, n + 3) + "";
                        String nowdate3 = getPeriodDate(type, n + 4) + "";
                        String nowdate2 = getPeriodDate(type, n + 5) + "";
                        String nowdate1 = getPeriodDate(type, n + 6) + "";
                        days.add(formdate(nowdate1));
                        days.add(formdate(nowdate2));
                        days.add(formdate(nowdate3));
                        days.add(formdate(nowdate4));
                        days.add(formdate(nowdate5));
                        days.add(formdate(nowdate6));
                        days.add(formdate(nowdate7));
                        progressDialog.show();
                        healthRecordManager.doGetHealthfatRecords(getDateform(nowdate1), getDateform(nowdate7), 1);
                        n = n + 7;
                        bt_right.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        if (state != true) {
                            n = n + 4;
                        }
                        state = true;
                        dates.clear();
                        days.clear();
                        type = '6';
                        String monthdate4 = getPeriodDate(type, 7 * n) + "";
                        String monthdate3 = getPeriodDate(type, 7 * (n + 1)) + "";
                        String monthdate2 = getPeriodDate(type, 7 * (n + 2)) + "";
                        String monthdate1 = getPeriodDate(type, 7 * (n + 3)) + "";
                        days.add(formdate(monthdate1));
                        days.add(formdate(monthdate2));
                        days.add(formdate(monthdate3));
                        days.add(formdate(monthdate4));
                        progressDialog.show();
                        healthRecordManager.doGetHealthfatRecords(getDateform(monthdate1), getDateform(monthdate4), 2);
                        n = n + 4;
                        bt_right.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        if (state != true) {
                            n = n + 4;
                        }
                        state = true;
                        dates.clear();
                        days.clear();
                        type = '6';
                        String quarterdate4 = getPeriodDate(type, 21 * n) + "";
                        String quarterdate3 = getPeriodDate(type, 21 * (n + 1)) + "";
                        String quarterdate2 = getPeriodDate(type, 21 * (n + 2)) + "";
                        String quarterdate1 = getPeriodDate(type, 21 * (n + 3)) + "";
                        days.add(formdate(quarterdate1));
                        days.add(formdate(quarterdate2));
                        days.add(formdate(quarterdate3));
                        days.add(formdate(quarterdate4));
                        progressDialog.show();
                        healthRecordManager.doGetHealthfatRecords(getDateform(quarterdate1), getDateform(quarterdate4), 3);
                        n = n + 4;
                        bt_right.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        if (state != true) {
                            n = n + 4;
                        }
                        dates.clear();
                        days.clear();
                        state = true;
                        type = '7';
                        String yeardate4 = getPeriodDate(type, 2 * n) + "";
                        String yeardate3 = getPeriodDate(type, 2 * (n + 1)) + "";
                        String yeardate2 = getPeriodDate(type, 2 * (n + 2)) + "";
                        String yeardate1 = getPeriodDate(type, 2 * (n + 3)) + "";
                        String yeardate0 = getPeriodDate(type, 2 * (n + 4)) + "";
                        days.add(formyeardate(yeardate1));
                        days.add(formyeardate(yeardate2));
                        days.add(formyeardate(yeardate3));
                        days.add(formyeardate(yeardate4));
                        progressDialog.show();
                        healthRecordManager.doGetHealthfatRecords(getDateform(yeardate0), getDateform(yeardate4), 4);
                        n = n + 4;
                        bt_right.setVisibility(View.VISIBLE);
                        break;
                }


            }
            break;
            case R.id.bt_right:
                switch (flag) {
                    case 0:
                        if (state != false) {
                            n = n - 14;
                        } else {
                            n = n - 7;
                        }
                        dates.clear();
                        days.clear();
                        String nowdate7 = getPeriodDate(type, n) + "";
                        String nowdate6 = getPeriodDate(type, n + 1) + "";
                        String nowdate5 = getPeriodDate(type, n + 2) + "";
                        String nowdate4 = getPeriodDate(type, n + 3) + "";
                        String nowdate3 = getPeriodDate(type, n + 4) + "";
                        String nowdate2 = getPeriodDate(type, n + 5) + "";
                        String nowdate1 = getPeriodDate(type, n + 6) + "";
                        days.add(formdate(nowdate1));
                        days.add(formdate(nowdate2));
                        days.add(formdate(nowdate3));
                        days.add(formdate(nowdate4));
                        days.add(formdate(nowdate5));
                        days.add(formdate(nowdate6));
                        days.add(formdate(nowdate7));
                        progressDialog.show();
                        healthRecordManager.doGetHealthfatRecords(getDateform(nowdate1), getDateform(nowdate7), 1);
                        state = false;
                        if (nowdate7.equals(getPeriodDate(type, 0) + ""))
                            bt_right.setVisibility(View.GONE);
                        break;
                    case 1:
                        if (state != false) {
                            n = n - 8;
                        } else {
                            n = n - 4;

                        }
                        dates.clear();
                        days.clear();
                        type = '6';
                        String monthdate4 = getPeriodDate(type, 7 * n) + "";
                        String monthdate3 = getPeriodDate(type, 7 * (n + 1)) + "";
                        String monthdate2 = getPeriodDate(type, 7 * (n + 2)) + "";
                        String monthdate1 = getPeriodDate(type, 7 * (n + 3)) + "";
                        days.add(formdate(monthdate1));
                        days.add(formdate(monthdate2));
                        days.add(formdate(monthdate3));
                        days.add(formdate(monthdate4));
                        progressDialog.show();
                        healthRecordManager.doGetHealthfatRecords(getDateform(monthdate1), getDateform(monthdate4), 2);
                        state = false;
                        if (monthdate4.equals(getPeriodDate(type, 0) + ""))
                            bt_right.setVisibility(View.GONE);
                        break;
                    case 2:
                        if (state != false) {
                            n = n - 8;
                        } else {
                            n = n - 4;

                        }
                        dates.clear();
                        days.clear();
                        type = '6';
                        String quarterdate4 = getPeriodDate(type, 21 * n) + "";
                        String quarterdate3 = getPeriodDate(type, 21 * (n + 1)) + "";
                        String quarterdate2 = getPeriodDate(type, 21 * (n + 2)) + "";
                        String quarterdate1 = getPeriodDate(type, 21 * (n + 3)) + "";
                        days.add(formdate(quarterdate1));
                        days.add(formdate(quarterdate2));
                        days.add(formdate(quarterdate3));
                        days.add(formdate(quarterdate4));
                        progressDialog.show();
                        healthRecordManager.doGetHealthfatRecords(getDateform(quarterdate1), getDateform(quarterdate4), 3);
                        bt_right.setVisibility(View.VISIBLE);
                        state = false;
                        if (quarterdate4.equals(getPeriodDate(type, 0) + ""))
                            bt_right.setVisibility(View.GONE);
                        break;
                    case 3:
                        if (state != false) {
                            n = n - 8;
                        } else {
                            n = n - 4;

                        }
                        dates.clear();
                        days.clear();
                        type = '7';
                        String yeardate4 = getPeriodDate(type, 2 * n) + "";
                        String yeardate3 = getPeriodDate(type, 2 * (n + 1)) + "";
                        String yeardate2 = getPeriodDate(type, 2 * (n + 2)) + "";
                        String yeardate1 = getPeriodDate(type, 2 * (n + 3)) + "";
                        String yeardate0 = getPeriodDate(type, 2 * (n + 4)) + "";
                        days.add(formyeardate(yeardate1));
                        days.add(formyeardate(yeardate2));
                        days.add(formyeardate(yeardate3));
                        days.add(formyeardate(yeardate4) + "     /");
                        progressDialog.show();
                        healthRecordManager.doGetHealthfatRecords(getDateform(yeardate0), getDateform(yeardate4), 4);
                        bt_right.setVisibility(View.VISIBLE);
                        state = false;
                        if (yeardate4.equals(getPeriodDate(type, 0) + ""))
                            bt_right.setVisibility(View.GONE);
                        break;
                }


                break;
            case R.id.week:
                flag = 0;
                type = '6';
                n = 7;
                state = true;
                bt_right.setVisibility(View.GONE);
                dates.clear();
                days.clear();
                String weekdate7 = getPeriodDate(type, 0) + "";
                String weekdate6 = getPeriodDate(type, 1) + "";
                String weekdate5 = getPeriodDate(type, 2) + "";
                String weekdate4 = getPeriodDate(type, 3) + "";
                String weekdate3 = getPeriodDate(type, 4) + "";
                String weekdate2 = getPeriodDate(type, 5) + "";
                String weekdate1 = getPeriodDate(type, 6) + "";
                days.add(formdate(weekdate1));
                days.add(formdate(weekdate2));
                days.add(formdate(weekdate3));
                days.add(formdate(weekdate4));
                days.add(formdate(weekdate5));
                days.add(formdate(weekdate6));
                days.add(formdate(weekdate7));
                progressDialog.show();
                healthRecordManager.doGetHealthfatRecords(getDateform(weekdate1), getDateform(weekdate7), 1);

                break;
            case R.id.month:
                dates.clear();
                days.clear();
                flag = 1;
                state = true;
                bt_right.setVisibility(View.GONE);
                type = '6';
                n = 4;
                String monthdate4 = getPeriodDate(type, 0) + "";
                String monthdate3 = getPeriodDate(type, 7) + "";
                String monthdate2 = getPeriodDate(type, 14) + "";
                String monthdate1 = getPeriodDate(type, 21) + "";
                days.add(formdate(monthdate1));
                days.add(formdate(monthdate2));
                days.add(formdate(monthdate3));
                days.add(formdate(monthdate4));
                progressDialog.show();
                healthRecordManager.doGetHealthfatRecords(getDateform(monthdate1), getDateform(monthdate4), 2);
                break;
            case R.id.quarter:
                dates.clear();
                days.clear();
                flag = 2;
                state = true;
                bt_right.setVisibility(View.GONE);
                type = '6';
                n = 4;
                String quarterdate4 = getPeriodDate(type, 0) + "";
                String quarterdate3 = getPeriodDate(type, 21) + "";
                String quarterdate2 = getPeriodDate(type, 21 * 2) + "";
                String quarterdate1 = getPeriodDate(type, 21 * 3) + "";
                days.add(formdate(quarterdate1));
                days.add(formdate(quarterdate2));
                days.add(formdate(quarterdate3));
                days.add(formdate(quarterdate4));
                progressDialog.show();
                healthRecordManager.doGetHealthfatRecords(getDateform(quarterdate1), getDateform(quarterdate4), 3);
                break;
            case R.id.year:
                flag = 3;
                dates.clear();
                days.clear();
                state = true;
                bt_right.setVisibility(View.GONE);
                type = '7';
                n = 4;
                String yeardate4 = getPeriodDate(type, 0) + "";
                String yeardate3 = getPeriodDate(type, 2) + "";
                String yeardate2 = getPeriodDate(type, 4) + "";
                String yeardate1 = getPeriodDate(type, 6) + "";
                String yeardate0 = getPeriodDate(type, 8) + "";
                days.add(formyeardate(yeardate1));
                days.add(formyeardate(yeardate2));
                days.add(formyeardate(yeardate3));
                days.add(formyeardate(yeardate4) + "     /");
                progressDialog.show();
                healthRecordManager.doGetHealthfatRecords(getDateform(yeardate0), getDateform(yeardate4), 4);
                break;
        }
    }

    public String formyeardate(String nowdate) {
        String date;
        String sr = nowdate.substring(0, 4);
        date = sr + "/" + nowdate.substring(4, 6);
        return date;

    }

    public void updateFatStatus() {

        week.setChecked(true);
        flag = 0;
        type = '6';
        n = 7;
        state = true;
        bt_right.setVisibility(View.GONE);
        bt_right.setVisibility(View.GONE);
        dates.clear();
        days.clear();
        String weekdate7 = getPeriodDate(type, 0) + "";
        String weekdate6 = getPeriodDate(type, 1) + "";
        String weekdate5 = getPeriodDate(type, 2) + "";
        String weekdate4 = getPeriodDate(type, 3) + "";
        String weekdate3 = getPeriodDate(type, 4) + "";
        String weekdate2 = getPeriodDate(type, 5) + "";
        String weekdate1 = getPeriodDate(type, 6) + "";
        days.add(formdate(weekdate1));
        days.add(formdate(weekdate2));
        days.add(formdate(weekdate3));
        days.add(formdate(weekdate4));
        days.add(formdate(weekdate5));
        days.add(formdate(weekdate6));
        days.add(formdate(weekdate7));
        progressDialog.show();
        healthRecordManager.doGetHealthfatRecords(getDateform(weekdate1), getDateform(weekdate7), 1);
    }
}
