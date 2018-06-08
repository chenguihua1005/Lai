/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.view;


import android.app.ProgressDialog;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.counselor.presenter.GameImpl;
import com.softtek.lai.module.counselor.presenter.IGamePresenter;

import java.util.Calendar;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jarvis.liu on 3/22/2016.
 * 大赛赛况
 */
@InjectLayout(R.layout.activity_game)
public class GameActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.list_game)
    ListView list_game;

    @InjectView(R.id.rel_men_up)
    RelativeLayout rel_men_up;

    @InjectView(R.id.img_men_up_bg)
    ImageView img_men_up_bg;

    @InjectView(R.id.img_men_up_icon)
    ImageView img_men_up_icon;

    @InjectView(R.id.text_men_up)
    TextView text_men_up;


    @InjectView(R.id.rel_men_down)
    RelativeLayout rel_men_down;

    @InjectView(R.id.img_men_down_bg)
    ImageView img_men_down_bg;

    @InjectView(R.id.img_men_down_icon)
    ImageView img_men_down_icon;

    @InjectView(R.id.text_men_down)
    TextView text_men_down;

    @InjectView(R.id.rel_women_up)
    RelativeLayout rel_women_up;

    @InjectView(R.id.img_women_up_bg)
    ImageView img_women_up_bg;

    @InjectView(R.id.img_women_up_icon)
    ImageView img_women_up_icon;

    @InjectView(R.id.text_women_up)
    TextView text_women_up;


    @InjectView(R.id.rel_women_down)
    RelativeLayout rel_women_down;

    @InjectView(R.id.img_women_down_bg)
    ImageView img_women_down_bg;

    @InjectView(R.id.img_women_down_icon)
    ImageView img_women_down_icon;

    @InjectView(R.id.text_women_down)
    TextView text_women_down;


    @InjectView(R.id.text_time)
    TextView text_time;

    @InjectView(R.id.lin_left)
    LinearLayout lin_left;

    @InjectView(R.id.rel_right)
    RelativeLayout rel_right;


    private IGamePresenter gamePresenter;

    private String grade_id = "1";
    private String date = "";
    private int monthInfo;
    private int yearInfo;

    int year;
    int monthOfYear;
    int dayOfMonth;

    private ProgressDialog progressDialog1;

    @Override
    protected void initViews() {
        tv_title.setText(R.string.CounselorG);
        ll_left.setOnClickListener(this);
        rel_men_up.setOnClickListener(this);
        rel_men_down.setOnClickListener(this);
        rel_women_up.setOnClickListener(this);
        rel_women_down.setOnClickListener(this);
        lin_left.setOnClickListener(this);
        rel_right.setOnClickListener(this);

    }

    @Override
    protected void initDatas() {
        gamePresenter = new GameImpl(this);

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        monthOfYear = calendar.get(Calendar.MONTH) + 1;
        monthOfYear--;
        year=monthOfYear==0?--year:year;
        monthOfYear=monthOfYear==0?12:monthOfYear;
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        text_time.setText(year + "年" + monthOfYear + "月");
        monthInfo = monthOfYear;
        yearInfo = year;
        progressDialog1 = new ProgressDialog(this);
        progressDialog1.setCanceledOnTouchOutside(false);
        progressDialog1.setMessage("查询中");
        int zubie=getIntent().getIntExtra("zubie",1);
        switch (zubie){
            case 1:
                grade_id = "1";
                changeView(img_men_up_bg,text_men_up);
                showList();
                break;
            case 4:
                grade_id = "4";
                changeView(img_men_down_bg,text_men_down);
                showList();
                break;
            case 5:
                grade_id = "5";
                changeView(img_women_up_bg,text_women_up);
                showList();
                break;
            case 6:
                grade_id = "6";
                changeView(img_women_down_bg,text_women_down);
                showList();
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.rel_men_up:
                grade_id = "1";
                changeView(img_men_up_bg,text_men_up);
                showList();
                break;
            case R.id.rel_men_down:
                grade_id = "4";
                changeView(img_men_down_bg,text_men_down);
                showList();
                break;
            case R.id.rel_women_up:
                grade_id = "5";
                changeView(img_women_up_bg,text_women_up);
                showList();
                break;
            case R.id.rel_women_down:
                grade_id = "6";
                changeView(img_women_down_bg,text_women_down);
                showList();
                break;

            case R.id.rel_right:
                monthInfo++;
                if(yearInfo==year && monthInfo>monthOfYear){
                    monthInfo--;
                }else {
                    if (monthInfo == 13) {
                        monthInfo = 1;
                        yearInfo++;
                    }
                    text_time.setText(yearInfo + "年" + monthInfo + "月");
                    showList();
                }
                break;
            case R.id.lin_left:
                monthInfo--;
                if (monthInfo == 0) {
                    monthInfo = 12;
                    yearInfo--;
                }
                text_time.setText(yearInfo + "年" + monthInfo + "月");
                showList();
                break;

        }
    }

    private void changeView(ImageView img_bg, TextView tv) {
        img_men_up_bg.setImageResource(R.drawable.but_white_select);
        img_men_down_bg.setImageResource(R.drawable.but_white_select);
        img_women_up_bg.setImageResource(R.drawable.but_white_select);
        img_women_down_bg.setImageResource(R.drawable.but_white_select);

        text_men_up.setTextColor(ContextCompat.getColor(this,R.color.word2));
        text_men_down.setTextColor(ContextCompat.getColor(this,R.color.word2));
        text_women_up.setTextColor(ContextCompat.getColor(this,R.color.word2));
        text_women_down.setTextColor(ContextCompat.getColor(this,R.color.word2));

        img_men_up_icon.setImageResource(R.drawable.img_male_select);
        img_men_down_icon.setImageResource(R.drawable.img_male_select);
        img_women_up_icon.setImageResource(R.drawable.img_female_select);
        img_women_down_icon.setImageResource(R.drawable.img_female_select);

        img_bg.setImageResource(R.drawable.but_yellow_selected);
        tv.setTextColor(ContextCompat.getColor(this,R.color.white));

        if("1".equals(grade_id)){
            img_men_up_icon.setImageResource(R.drawable.img_male_selected);
        }else if("4".equals(grade_id)){
            img_men_down_icon.setImageResource(R.drawable.img_male_selected);
        }else if("5".equals(grade_id)){
            img_women_up_icon.setImageResource(R.drawable.img_female_selected);
        }else if("6".equals(grade_id)){
            img_women_down_icon.setImageResource(R.drawable.img_female_selected);
        }
    }

    private void showList() {
        String info="";
        if(monthInfo==1){
            info="01";
        }else if(monthInfo==2){
            info="02";
        }else if(monthInfo==3){
            info="03";
        }else if(monthInfo==4){
            info="04";
        }else if(monthInfo==5){
            info="05";
        }else if(monthInfo==6){
            info="06";
        }else if(monthInfo==7){
            info="07";
        }else if(monthInfo==8){
            info="08";
        }else if(monthInfo==9){
            info="09";
        }else if(monthInfo==10){
            info="10";
        }else if(monthInfo==11){
            info="11";
        }else if(monthInfo==12){
            info="12";
        }
        date = yearInfo + "-" + info + "-" + dayOfMonth;
        progressDialog1.show();
        gamePresenter.getMatchInfo(date, grade_id, list_game, progressDialog1);
    }

}
