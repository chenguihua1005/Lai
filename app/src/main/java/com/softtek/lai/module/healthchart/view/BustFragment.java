package com.softtek.lai.module.healthchart.view;

import android.app.ProgressDialog;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.healthchart.model.CircumlistModel;
import com.softtek.lai.module.healthchart.model.HealthCircrumModel;
import com.softtek.lai.module.healthchart.presenter.HealthRecordManager;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.widgets.chart.Chart;
import com.softtek.lai.widgets.chart.Entry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * @author lareina.qiao
 *         Created by John on 2016/4/12.
 */
@InjectLayout(R.layout.fragment_weight)
public class BustFragment extends BaseFragment implements  HealthRecordManager.HealthRecordCallBack<HealthCircrumModel>,
        View.OnClickListener {

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
    DateForm dateForm;
    List<Entry> dates = new ArrayList<>();
    List<String> days = new ArrayList<>();

    char type = '6';
    int n = 7;
    boolean state = true;
    int flag = 0;

    private ProgressDialog progressDialog;

    SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    String date = sDateFormat.format(new java.util.Date());
    String[] datetime = date.split(" ");
    HealthRecordManager<HealthCircrumModel> healthRecordManager;

    @Override
    protected void initViews() {
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
        dateForm = new DateForm();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("加载中...");
        progressDialog.setCanceledOnTouchOutside(false);
        healthRecordManager = new HealthRecordManager<>(this);
        dates.clear();
        String nowdate7 = DateForm.getPeriodDate(type, 0).toString();
        String nowdate6 = DateForm.getPeriodDate(type, 1).toString();
        String nowdate5 = DateForm.getPeriodDate(type, 2).toString();
        String nowdate4 = DateForm.getPeriodDate(type, 3).toString();
        String nowdate3 = DateForm.getPeriodDate(type, 4).toString();
        String nowdate2 = DateForm.getPeriodDate(type, 5).toString();
        String nowdate1 = DateForm.getPeriodDate(type, 6).toString();
        days.add(dateForm.formdate(nowdate1));
        days.add(dateForm.formdate(nowdate2));
        days.add(dateForm.formdate(nowdate3));
        days.add(dateForm.formdate(nowdate4));
        days.add(dateForm.formdate(nowdate5));
        days.add(dateForm.formdate(nowdate6));
        days.add(dateForm.formdate(nowdate7));
        progressDialog.show();
        healthRecordManager.doGetHealthcircumRecords(dateForm.getDateform(nowdate1) + " " + datetime[1], date, 1);
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
                        String nowdate7 = DateForm.getPeriodDate(type, n) + "";
                        String nowdate6 = DateForm.getPeriodDate(type, n + 1) + "";
                        String nowdate5 = DateForm.getPeriodDate(type, n + 2) + "";
                        String nowdate4 = DateForm.getPeriodDate(type, n + 3) + "";
                        String nowdate3 = DateForm.getPeriodDate(type, n + 4) + "";
                        String nowdate2 = DateForm.getPeriodDate(type, n + 5) + "";
                        String nowdate1 = DateForm.getPeriodDate(type, n + 6) + "";
                        days.add(dateForm.formdate(nowdate1));
                        days.add(dateForm.formdate(nowdate2));
                        days.add(dateForm.formdate(nowdate3));
                        days.add(dateForm.formdate(nowdate4));
                        days.add(dateForm.formdate(nowdate5));
                        days.add(dateForm.formdate(nowdate6));
                        days.add(dateForm.formdate(nowdate7));
                        progressDialog.show();
                        healthRecordManager.doGetHealthcircumRecords(dateForm.getDateform(nowdate1), dateForm.getDateform(nowdate7), 1);
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
                        String monthdate4 = DateForm.getPeriodDate(type, 7 * n) + "";
                        String monthdate3 = DateForm.getPeriodDate(type, 7 * (n + 1)) + "";
                        String monthdate2 = DateForm.getPeriodDate(type, 7 * (n + 2)) + "";
                        String monthdate1 = DateForm.getPeriodDate(type, 7 * (n + 3)) + "";
                        days.add(dateForm.formdate(monthdate1));
                        days.add(dateForm.formdate(monthdate2));
                        days.add(dateForm.formdate(monthdate3));
                        days.add(dateForm.formdate(monthdate4));
                        progressDialog.show();
                        healthRecordManager.doGetHealthcircumRecords(dateForm.getDateform(monthdate1), dateForm.getDateform(monthdate4), 2);
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
                        String quarterdate4 = DateForm.getPeriodDate(type, 21 * n) + "";
                        String quarterdate3 = DateForm.getPeriodDate(type, 21 * (n + 1)) + "";
                        String quarterdate2 = DateForm.getPeriodDate(type, 21 * (n + 2)) + "";
                        String quarterdate1 = DateForm.getPeriodDate(type, 21 * (n + 3)) + "";
                        days.add(dateForm.formdate(quarterdate1));
                        days.add(dateForm.formdate(quarterdate2));
                        days.add(dateForm.formdate(quarterdate3));
                        days.add(dateForm.formdate(quarterdate4));
                        progressDialog.show();
                        healthRecordManager.doGetHealthcircumRecords(dateForm.getDateform(quarterdate1), dateForm.getDateform(quarterdate4), 3);
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
                        String yeardate4 = DateForm.getPeriodDate(type, 2 * n) + "";
                        String yeardate3 = DateForm.getPeriodDate(type, 2 * (n + 1)) + "";
                        String yeardate2 = DateForm.getPeriodDate(type, 2 * (n + 2)) + "";
                        String yeardate1 = DateForm.getPeriodDate(type, 2 * (n + 3)) + "";
                        String yeardate0 = DateForm.getPeriodDate(type, 2 * (n + 4)) + "";
                        days.add(formyeardate(yeardate1));
                        days.add(formyeardate(yeardate2));
                        days.add(formyeardate(yeardate3));
                        days.add(formyeardate(yeardate4));
                        progressDialog.show();
                        healthRecordManager.doGetHealthcircumRecords(dateForm.getDateform(yeardate0), dateForm.getDateform(yeardate4), 4);
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
                        String nowdate7 = DateForm.getPeriodDate(type, n) + "";
                        String nowdate6 = DateForm.getPeriodDate(type, n + 1) + "";
                        String nowdate5 = DateForm.getPeriodDate(type, n + 2) + "";
                        String nowdate4 = DateForm.getPeriodDate(type, n + 3) + "";
                        String nowdate3 = DateForm.getPeriodDate(type, n + 4) + "";
                        String nowdate2 = DateForm.getPeriodDate(type, n + 5) + "";
                        String nowdate1 = DateForm.getPeriodDate(type, n + 6) + "";
                        days.add(dateForm.formdate(nowdate1));
                        days.add(dateForm.formdate(nowdate2));
                        days.add(dateForm.formdate(nowdate3));
                        days.add(dateForm.formdate(nowdate4));
                        days.add(dateForm.formdate(nowdate5));
                        days.add(dateForm.formdate(nowdate6));
                        days.add(dateForm.formdate(nowdate7));
                        progressDialog.show();
                        healthRecordManager.doGetHealthcircumRecords(dateForm.getDateform(nowdate1), dateForm.getDateform(nowdate7), 1);
                        state = false;
                        if (nowdate7.equals(dateForm.getPeriodDate(type, 0) + ""))
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
                        String monthdate4 = DateForm.getPeriodDate(type, 7 * n) + "";
                        String monthdate3 = DateForm.getPeriodDate(type, 7 * (n + 1)) + "";
                        String monthdate2 = DateForm.getPeriodDate(type, 7 * (n + 2)) + "";
                        String monthdate1 = DateForm.getPeriodDate(type, 7 * (n + 3)) + "";
                        days.add(dateForm.formdate(monthdate1));
                        days.add(dateForm.formdate(monthdate2));
                        days.add(dateForm.formdate(monthdate3));
                        days.add(dateForm.formdate(monthdate4));
                        progressDialog.show();
                        healthRecordManager.doGetHealthcircumRecords(dateForm.getDateform(monthdate1), dateForm.getDateform(monthdate4), 2);
                        state = false;
                        if (monthdate4.equals(dateForm.getPeriodDate(type, 0) + ""))
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
                        String quarterdate4 = DateForm.getPeriodDate(type, 21 * n) + "";
                        String quarterdate3 = DateForm.getPeriodDate(type, 21 * (n + 1)) + "";
                        String quarterdate2 = DateForm.getPeriodDate(type, 21 * (n + 2)) + "";
                        String quarterdate1 = DateForm.getPeriodDate(type, 21 * (n + 3)) + "";
                        days.add(dateForm.formdate(quarterdate1));
                        days.add(dateForm.formdate(quarterdate2));
                        days.add(dateForm.formdate(quarterdate3));
                        days.add(dateForm.formdate(quarterdate4));
                        progressDialog.show();
                        healthRecordManager.doGetHealthcircumRecords(dateForm.getDateform(quarterdate1), dateForm.getDateform(quarterdate4), 3);
                        bt_right.setVisibility(View.VISIBLE);
                        state = false;
                        if (quarterdate4.equals(dateForm.getPeriodDate(type, 0) + ""))
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
                        String yeardate4 = DateForm.getPeriodDate(type, 2 * n) + "";
                        String yeardate3 = DateForm.getPeriodDate(type, 2 * (n + 1)) + "";
                        String yeardate2 = DateForm.getPeriodDate(type, 2 * (n + 2)) + "";
                        String yeardate1 = DateForm.getPeriodDate(type, 2 * (n + 3)) + "";
                        String yeardate0 = DateForm.getPeriodDate(type, 2 * (n + 4)) + "";
                        days.add(formyeardate(yeardate1));
                        days.add(formyeardate(yeardate2));
                        days.add(formyeardate(yeardate3));
                        days.add(formyeardate(yeardate4));
                        progressDialog.show();
                        healthRecordManager.doGetHealthcircumRecords(dateForm.getDateform(yeardate0), dateForm.getDateform(yeardate4), 4);
                        bt_right.setVisibility(View.VISIBLE);
                        state = false;
                        if (yeardate4.equals(dateForm.getPeriodDate(type, 0) + ""))
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
                String weekdate7 = DateForm.getPeriodDate(type, 0) + "";
                String weekdate6 = DateForm.getPeriodDate(type, 1) + "";
                String weekdate5 = DateForm.getPeriodDate(type, 2) + "";
                String weekdate4 = DateForm.getPeriodDate(type, 3) + "";
                String weekdate3 = DateForm.getPeriodDate(type, 4) + "";
                String weekdate2 = DateForm.getPeriodDate(type, 5) + "";
                String weekdate1 = DateForm.getPeriodDate(type, 6) + "";
                days.add(dateForm.formdate(weekdate1));
                days.add(dateForm.formdate(weekdate2));
                days.add(dateForm.formdate(weekdate3));
                days.add(dateForm.formdate(weekdate4));
                days.add(dateForm.formdate(weekdate5));
                days.add(dateForm.formdate(weekdate6));
                days.add(dateForm.formdate(weekdate7));
                progressDialog.show();
                healthRecordManager.doGetHealthcircumRecords(dateForm.getDateform(weekdate1), dateForm.getDateform(weekdate7), 1);

                break;
            case R.id.month:
                dates.clear();
                days.clear();
                flag = 1;
                state = true;
                bt_right.setVisibility(View.GONE);
                type = '6';
                n = 4;
                String monthdate4 = DateForm.getPeriodDate(type, 0) + "";
                String monthdate3 = DateForm.getPeriodDate(type, 7) + "";
                String monthdate2 = DateForm.getPeriodDate(type, 14) + "";
                String monthdate1 = DateForm.getPeriodDate(type, 21) + "";
                days.add(dateForm.formdate(monthdate1));
                days.add(dateForm.formdate(monthdate2));
                days.add(dateForm.formdate(monthdate3));
                days.add(dateForm.formdate(monthdate4));
                progressDialog.show();
                healthRecordManager.doGetHealthcircumRecords(dateForm.getDateform(monthdate1), dateForm.getDateform(monthdate4), 2);
                break;
            case R.id.quarter:
                dates.clear();
                days.clear();
                flag = 2;
                state = true;
                bt_right.setVisibility(View.GONE);
                type = '6';
                n = 4;
                String quarterdate4 = DateForm.getPeriodDate(type, 0) + "";
                String quarterdate3 = DateForm.getPeriodDate(type, 21) + "";
                String quarterdate2 = DateForm.getPeriodDate(type, 21 * 2) + "";
                String quarterdate1 = DateForm.getPeriodDate(type, 21 * 3) + "";
                days.add(dateForm.formdate(quarterdate1));
                days.add(dateForm.formdate(quarterdate2));
                days.add(dateForm.formdate(quarterdate3));
                days.add(dateForm.formdate(quarterdate4));
                progressDialog.show();
                healthRecordManager.doGetHealthcircumRecords(dateForm.getDateform(quarterdate1), dateForm.getDateform(quarterdate4), 3);
                break;
            case R.id.year:
                flag = 3;
                dates.clear();
                days.clear();
                state = true;
                bt_right.setVisibility(View.GONE);
                type = '7';
                n = 4;
                String yeardate4 = DateForm.getPeriodDate(type, 0) + "";
                String yeardate3 = DateForm.getPeriodDate(type, 2) + "";
                String yeardate2 = DateForm.getPeriodDate(type, 4) + "";
                String yeardate1 = DateForm.getPeriodDate(type, 6) + "";
                String yeardate0 = DateForm.getPeriodDate(type, 8) + "";
                days.add(formyeardate(yeardate1));
                days.add(formyeardate(yeardate2));
                days.add(formyeardate(yeardate3));
                days.add(formyeardate(yeardate4));
                progressDialog.show();
                healthRecordManager.doGetHealthcircumRecords(dateForm.getDateform(yeardate0), dateForm.getDateform(yeardate4), 4);
                break;
        }
    }

    public String formyeardate(String nowdate) {
        String date;
        String sr = nowdate.substring(0, 4);
        date = sr + "/" + nowdate.substring(4, 6);
        return date;

    }

    public void updateBustStatus() {

        week.setChecked(true);
        flag = 0;
        type = '6';
        n = 7;
        state = true;
        bt_right.setVisibility(View.GONE);
        bt_right.setVisibility(View.GONE);
        dates.clear();
        days.clear();
        String weekdate7 = DateForm.getPeriodDate(type, 0) + "";
        String weekdate6 = DateForm.getPeriodDate(type, 1) + "";
        String weekdate5 = DateForm.getPeriodDate(type, 2) + "";
        String weekdate4 = DateForm.getPeriodDate(type, 3) + "";
        String weekdate3 = DateForm.getPeriodDate(type, 4) + "";
        String weekdate2 = DateForm.getPeriodDate(type, 5) + "";
        String weekdate1 = DateForm.getPeriodDate(type, 6) + "";
        days.add(dateForm.formdate(weekdate1));
        days.add(dateForm.formdate(weekdate2));
        days.add(dateForm.formdate(weekdate3));
        days.add(dateForm.formdate(weekdate4));
        days.add(dateForm.formdate(weekdate5));
        days.add(dateForm.formdate(weekdate6));
        days.add(dateForm.formdate(weekdate7));
        progressDialog.show();
        healthRecordManager.doGetHealthcircumRecords(dateForm.getDateform(weekdate1), dateForm.getDateform(weekdate7), 1);
    }

    @Override
    public void getHealthyData(HealthCircrumModel data) {
        try {
            if (progressDialog != null)
                progressDialog.dismiss();
            if (data == null) {
                return;
            }
            List<CircumlistModel> models = data.getCircumlist();
            float max = 0;
            for (int i = 0; i < models.size(); i++) {
                float weight = Float.parseFloat(models.get(i).getCircum());
                max = weight > max ? weight : max;
                Entry entry = new Entry(i, weight);
                dates.add(entry);
            }
            chart.setDate(days, dates, max);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
