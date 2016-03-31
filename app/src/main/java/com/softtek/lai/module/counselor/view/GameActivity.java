package com.softtek.lai.module.counselor.view;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.counselor.presenter.GameImpl;
import com.softtek.lai.module.counselor.presenter.IGamePresenter;
import com.softtek.lai.module.login.model.User;
import com.softtek.lai.utils.ACache;
import com.softtek.lai.utils.SoftInputUtil;
import com.softtek.lai.widgets.WheelView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;
/**
 * Created by jarvis.liu on 3/22/2016.
 * 大赛赛况
 */
@InjectLayout(R.layout.activity_game)
public class GameActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener, BaseFragment.OnFragmentInteractionListener {

    @LifeCircleInject
    ValidateLife validateLife;

    @InjectView(R.id.tv_left)
    TextView tv_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.but_select_month)
    Button but_select_month;

    @InjectView(R.id.but_select_grade)
    Button but_select_grade;

    @InjectView(R.id.text_month)
    TextView text_month;

    @InjectView(R.id.text_grade)
    TextView text_grade;

    @InjectView(R.id.list_game)
    ListView list_game;


    private IGamePresenter gamePresenter;
    private ACache aCache;
    private User user;
    private List<String> monthList = new ArrayList<String>();
    private List<String> monthsList = new ArrayList<String>();
    private List<String> gradeList = new ArrayList<String>();
    private List<String> gradeIDList = new ArrayList<String>();

    private String select_month="";
    private String select_grade="";
    private String grade_id="";
    private String date="";
    private String dateInfo="";

    int year;
    int monthOfYear;
    int dayOfMonth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_left.setOnClickListener(this);
        but_select_month.setOnClickListener(this);
        but_select_grade.setOnClickListener(this);
//        View view = LayoutInflater.from(this).inflate(R.layout.game_item, null);
//        list_game.addHeaderView(view);

    }

    @Override
    protected void initViews() {
        tv_left.setBackgroundResource(R.drawable.back);
        //tv_left.setLayoutParams(new Toolbar.LayoutParams(DisplayUtil.dip2px(this,15),DisplayUtil.dip2px(this,30)));
        tv_title.setText("大赛赛况");

    }

    @Override
    protected void initDatas() {
        gamePresenter = new GameImpl(this);
        aCache = ACache.get(this, Constants.USER_ACACHE_DATA_DIR);
        addMonth();
        addGrade();

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        monthOfYear = calendar.get(Calendar.MONTH) + 1;
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);


    }

    private void addGrade() {
        gradeList.add("男子180斤以上");
        gradeList.add("男子180斤以下");
        gradeList.add("女子140斤以上");
        gradeList.add("女子140斤以下");
        gradeIDList.add("1");
        gradeIDList.add("4");
        gradeIDList.add("5");
        gradeIDList.add("6");
    }

    private void addMonth() {
        monthList.add("一月");
        monthList.add("二月");
        monthList.add("三月");
        monthList.add("四月");
        monthList.add("五月");
        monthList.add("六月");
        monthList.add("七月");
        monthList.add("八月");
        monthList.add("九月");
        monthList.add("十月");
        monthList.add("十一月");
        monthList.add("十二月");

        monthsList.add("01");
        monthsList.add("02");
        monthsList.add("03");
        monthsList.add("04");
        monthsList.add("05");
        monthsList.add("06");
        monthsList.add("07");
        monthsList.add("08");
        monthsList.add("09");
        monthsList.add("10");
        monthsList.add("11");
        monthsList.add("12");
    }

    @Override
    public void onClick(View v) {
        SoftInputUtil.hidden(this);
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;

            case R.id.but_select_month:
                showDateDialog();
                break;

            case R.id.but_select_grade:
                showGradeDialog();
                break;
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView, failedRule);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void showDateDialog() {
        View outerView = LayoutInflater.from(this).inflate(R.layout.dialog_select_month, null);

        final WheelView wheel_month = (WheelView) outerView.findViewById(R.id.wheel_month);

        wheel_month.setOffset(1);
        wheel_month.setItems(monthList);
        wheel_month.setSeletion(0);
        wheel_month.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                select_month=item;
                dateInfo=monthsList.get(selectedIndex-1);
            }
        });

        new AlertDialog.Builder(this)
                .setTitle("请选择月份")
                .setView(outerView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if ("".equals(select_month)) {
                            select_month = monthList.get(0);
                            dateInfo=monthsList.get(0);
                            System.out.println("select_month:" + select_month);
                        }
                        text_month.setText(select_month);
                        if (!"".equals(text_grade.getText().toString())) {
                            showList();
                        }
                        select_month="";

                    }
                })
                .show();
    }
    private void showList(){
        date=year+"-"+dateInfo+"-"+dayOfMonth;
        System.out.println("date:"+date+"      SelectGrade"+select_grade);
        gamePresenter.getMatchInfo(date,grade_id,list_game);
    }
    private void showGradeDialog() {
        View outerView = LayoutInflater.from(this).inflate(R.layout.dialog_select_grade, null);

        final WheelView wheel_grade = (WheelView) outerView.findViewById(R.id.wheel_grade);

        wheel_grade.setOffset(1);
        wheel_grade.setItems(gradeList);
        wheel_grade.setSeletion(0);
        wheel_grade.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                select_grade=item;
                grade_id=gradeIDList.get(selectedIndex-1);
            }
        });

        new AlertDialog.Builder(this)
                .setTitle("请选择组别")
                .setView(outerView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if ("".equals(select_grade)) {
                            select_grade=gradeList.get(0);
                            grade_id=gradeIDList.get(0);
                        }
                        text_grade.setText(select_grade);
                        if(!"".equals(text_month.getText().toString())){
                            showList();
                        }
                        select_grade="";
                    }
                })
                .show();
    }

}
