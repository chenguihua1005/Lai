package com.softtek.lai.module.bodygame3.conversation.view;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.bodygame3.conversation.model.ContactClassModel;
import com.softtek.lai.utils.DisplayUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jessica.zhang on 2016/11/29.
 */

@InjectLayout(R.layout.activity_classdetail)
public class ClassDetailActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "ClassDetailActivity";

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.coach_img)
    ImageView coach_img;
    @InjectView(R.id.coach_name)
    TextView coach_name;

    @InjectView(R.id.tv_classname)
    TextView tv_classname;

    @InjectView(R.id.tv_classNo)
    TextView tv_classNo;

    @InjectView(R.id.tv_classStart_time)
    TextView tv_classStart_time;

    @InjectView(R.id.tv_members_accout)
    TextView tv_members_accout;
    @InjectView(R.id.classNumber_linear)
    RelativeLayout classNumber_linear;

    @InjectView(R.id.btn_dismissclass)
    Button btn_dismissclass;


    private ContactClassModel classModel;


    @Override
    protected void initViews() {
        if (DisplayUtil.getSDKInt() > 18) {
            int status = DisplayUtil.getStatusHeight(this);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) toolbar.getLayoutParams();
            params.topMargin = status;
            toolbar.setLayoutParams(params);
        }

        ll_left.setVisibility(View.VISIBLE);
        tv_title.setText("班级群详情");

    }

    @Override
    protected void initDatas() {
        classModel = (ContactClassModel) getIntent().getSerializableExtra("classModel");
        Log.i(TAG, "classModel = " + new Gson().toJson(classModel));

        String end_date = classModel.getEndDate();
        if (StringToDate(end_date).before(getNowDate())) {
//            btn_dismissclass.setText("解散班级群");
//            btn_dismissclass.setBackgroundResource(R.drawable.btn_dismissclass);
        } else {
            btn_dismissclass.setVisibility(View.GONE);
//            btn_dismissclass.setText("您尚未关闭班级");
//            btn_dismissclass.setBackgroundResource(R.drawable.btn_disable);
        }

        ll_left.setOnClickListener(this);
        btn_dismissclass.setOnClickListener(this);
        classNumber_linear.setOnClickListener(this);

        coach_name.setText(classModel.getCoachName());
        tv_classname.setText(classModel.getClassName());
        tv_classNo.setText(classModel.getClassCode());
        tv_classStart_time.setText(classModel.getStartDate());
        tv_members_accout.setText(String.valueOf(classModel.getTotal()) + "人");


    }

    public static Date StringToDate(String dateStr) {
        String formatStr = "yyyy-MM-dd HH:mm:ss";//2017-02-24 00:00:00
        DateFormat sdf = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public Date getNowDate() {
        Date date_now = null;
        String formatStr = "yyyy-MM-dd HH:mm:ss";
        DateFormat sdf = new SimpleDateFormat(formatStr);
        String dateStr = sdf.format(new Date());
        try {
            date_now = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.i("leave", "now time = " + date_now);
        return date_now;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.classNumber_linear:
                Intent intent = new Intent(ClassDetailActivity.this, GroupDetailsActivity.class);
//                intent.putExtra("groupId", toChatUsername);
//                intent.putExtra("classId", classId);
                intent.putExtra("classModel", classModel);
                startActivity(intent);

                break;
            case R.id.btn_dismissclass:

                break;

        }
    }
}
