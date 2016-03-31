/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.retest.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import butterknife.InjectView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.retest.Adapter.ClassAdapter;
import com.softtek.lai.module.retest.Adapter.StudentAdapter;
import com.softtek.lai.module.retest.AuditActivity;
import com.softtek.lai.module.retest.WriteActivity;
import com.softtek.lai.module.retest.eventModel.BanJiEvent;
import com.softtek.lai.module.retest.eventModel.BanjiStudentEvent;
import com.softtek.lai.module.retest.model.BanjiModel;
import com.softtek.lai.module.retest.model.BanjiStudentModel;
import com.softtek.lai.module.retest.present.RetestPre;
import com.softtek.lai.module.retest.present.RetestclassImp;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import zilla.libcore.ui.InjectLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lareina.qiao on 3/18/2016.
 */
@InjectLayout(R.layout.activity_retest)
public class RetestActivity extends BaseActivity implements View.OnClickListener {
    private RetestPre retestPre;
    //标题栏
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.tv_title)
    TextView bar_title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.iv_email)
    ImageView iv_email;


    @InjectView(R.id.Iv_fold)
    ImageView Iv_fold;
    @InjectView(R.id.list_class)
    ListView list_class;
    @InjectView(R.id.list_query)
    ListView list_query;
    //展开班级列表
    @InjectView(R.id.ll_classlist)
    RelativeLayout ll_classlist;

    private List<BanjiModel> banjiModelList = new ArrayList<BanjiModel>();
    private List<BanjiStudentModel> banjiStudentModelList = new ArrayList<BanjiStudentModel>();
    private ClassAdapter classAdapter;
    private StudentAdapter studentAdapter;
    boolean h = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
        System.out.println("dsadasdsadasda>>》》》》》》》》》》》》》》-----------");
        classAdapter = new ClassAdapter(this, banjiModelList);
        studentAdapter = new StudentAdapter(this, banjiStudentModelList);
        list_class.setAdapter(classAdapter);
//        queryAdapter=new QueryAdapter(this,banjiStudentModelList);

//        ListView listView=(ListView)findViewById(R.id.list_class);
        System.out.println("dsadasdsadasda>>》》》》》》》》》》》》》》==============");

        list_query.setAdapter(studentAdapter);
        ll_classlist.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        iv_email.setOnClickListener(this);
        list_class.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BanjiModel banjiModel = banjiModelList.get(position);
                retestPre.doGetBanjiStudent(banjiModel.getClassId());
                list_class.setVisibility(View.INVISIBLE);
                Iv_fold.setImageResource(R.drawable.unfold);
                h = false;

            }
        });
        list_query.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BanjiStudentModel banjiStudentModel = banjiStudentModelList.get(position);
                if (banjiStudentModel.getAMStatus() == "") {
                    Intent intent = new Intent(RetestActivity.this, WriteActivity.class);
                    intent.putExtra("accountId", banjiStudentModel.getAccountId());
                    intent.putExtra("classId", banjiStudentModel.getClassId());
                    intent.putExtra("typeDate", banjiStudentModel.getTypeDate());
                    intent.putExtra("loginid", "36");

                    startActivity(intent);

                } else {

                    Intent intent = new Intent(RetestActivity.this, AuditActivity.class);
                    intent.putExtra("accountId", banjiStudentModel.getAccountId());
                    intent.putExtra("classId", banjiStudentModel.getClassId());
                    intent.putExtra("typeDate", banjiStudentModel.getTypeDate());
                    intent.putExtra("loginid", "36");
                    startActivity(intent);
                }
            }
        });
        ll_classlist.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        if (h == false) {
                            list_class.setVisibility(View.VISIBLE);
                            Iv_fold.setImageResource(R.drawable.retract);
                            h = true;
                        } else {
                            list_class.setVisibility(View.INVISIBLE);
                            Iv_fold.setImageResource(R.drawable.unfold);
                            h = false;
                        }
                        break;

                    case MotionEvent.ACTION_BUTTON_PRESS:
                        list_class.setVisibility(View.VISIBLE);
                        break;
                    case MotionEvent.ACTION_UP:
//                        list_class.setVisibility(View.INVISIBLE);
                        break;
                }
                return false;
            }

        });

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {

        bar_title.setText("复测");
//        tv_right.setText("搜索");
        iv_email.setImageResource(R.drawable.retestsearch);
//        tv_right.setBackgroundResource(R.drawable.search);
        retestPre = new RetestclassImp();
        retestPre.doGetRetestclass(36);


    }


    @Subscribe
    public void onEvent(BanJiEvent banji) {
        System.out.println("dsadasdsadasda>>》》》》》》》》》》》》》》" + banji.getBanjiModels());
        banjiModelList = banji.getBanjiModels();
        Log.i("sdfsdfsdfsdfsd", "" + banjiModelList);
        classAdapter.updateData(banjiModelList);

    }

    @Subscribe
    public void onEvent1(BanjiStudentEvent banjiStudent) {
        banjiStudentModelList = banjiStudent.getBanjiStudentModels();
        Log.i("sdfsdfsdfsdfsd", "" + banjiStudentModelList);
        studentAdapter.updateData(banjiStudentModelList);
//        List<StudentModel> students=student.getStudentModels();
//        for (StudentModel st:students){
//            String month=st.getStartDate().substring(5,7);
//            StudentModel lis=new StudentModel(st.getPhoto(),st.getUserName(),st.getMobile(),tomonth(month),st.getWeekth(),st.getAMStatus());
//            studentList.add(lis);
//            queryAdapter.updateData(studentList);
//        }


    }


    public String tomonth(String month) {
        if (month.equals("01")) {
            month = "一月班";
        } else if (month.equals("02")) {
            month = "二月班";
        } else if (month.equals("03")) {
            month = "三月班";
        } else if (month.equals("04")) {
            month = "四月班";

        } else if (month.equals("05")) {
            month = "五月班";
        } else if (month.equals("06")) {
            month = "六月班";
        } else if (month.equals("07")) {
            month = "七月班";
        } else if (month.equals("08")) {
            month = "八月班";
        } else if (month.equals("09")) {
            month = "九月班";
        } else if (month.equals("10")) {
            month = "十月班";
        } else if (month.equals("11")) {
            month = "十一月班";
        } else {
            month = "十二月班";
        }
        return month;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_email: {
                Intent intent = new Intent(RetestActivity.this, QueryActivity.class);
                startActivity(intent);

            }
            break;
            case R.id.ll_left: {
                finish();
            }
            break;

        }

    }
}
