/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.retest.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.retest.AuditActivity;
import com.softtek.lai.module.retest.WriteActivity;
import com.softtek.lai.module.retest.adapter.QueryAdapter;
import com.softtek.lai.module.retest.eventModel.StudentEvent;
import com.softtek.lai.module.retest.model.StudentModel;
import com.softtek.lai.module.retest.present.RetestPre;
import com.softtek.lai.module.retest.present.RetestclassImp;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_query)
public class QueryActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.tv_retest_query_cancel)
    TextView tv_retest_query_cancel;
    @InjectView(R.id.List_querystudent)
    ListView List_querystudent;
    @InjectView(R.id.iv_query)
    ImageView iv_query;
    @InjectView(R.id.et_query)
    TextView et_query;
    @InjectView(R.id.ll_query_noresult)
    LinearLayout ll_query_noresult;
    private RetestPre retestPre;
    private List<StudentModel> studentModelList = new ArrayList<StudentModel>();
    private QueryAdapter queryAdapter;
    private static final int BODY=3;
    private ProgressDialog progressDialog;
    UserInfoModel userInfoModel=UserInfoModel.getInstance();
    long loginid=Long.parseLong(userInfoModel.getUser().getUserid());
    String ClassId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        iv_query.setOnClickListener(this);
        queryAdapter = new QueryAdapter(this, studentModelList);
        List_querystudent.setAdapter(queryAdapter);
        ll_query_noresult.setOnClickListener(this);
        tv_retest_query_cancel.setOnClickListener(this);

        List_querystudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StudentModel studentModel=studentModelList.get(position);
                if (studentModel.getAMStatus()=="")
                {
                    Intent intent=new Intent(QueryActivity.this,WriteActivity.class);
                    intent.putExtra("accountId",studentModel.getAccountId());
                    intent.putExtra("classId",studentModel.getClassId());
                    intent.putExtra("typeDate",studentModel.getTypeDate());
                    ClassId=studentModel.getClassId();
                    //开班时间，判断班级名称（几月班）
                    intent.putExtra("StartDate",studentModel.getStartDate());
                    //开始周期
                    intent.putExtra("CurrStart",studentModel.getCurrStart());
                    //结束周期
                    intent.putExtra("CurrEnd",studentModel.getCurrEnd());
                    //昵称
                    intent.putExtra("UserName",studentModel.getUserName());
                    //手机号
                    intent.putExtra("Mobile",studentModel.getMobile());
                    //头像
                    intent.putExtra("Photo",studentModel.getPhoto());
                    //第几周期
                    intent.putExtra("Weekth",studentModel.getWeekth());
                    intent.putExtra("loginid","36");

                    startActivityForResult(intent,BODY);

                }
                else {

                    Intent intent=new Intent(QueryActivity.this, AuditActivity.class);
                    intent.putExtra("accountId",studentModel.getAccountId());
                    intent.putExtra("classId",studentModel.getClassId());
                    intent.putExtra("typeDate",studentModel.getTypeDate());
                    intent.putExtra("loginid","36");
                    ClassId=studentModel.getClassId();
                    //开班时间，判断班级名称（几月班）
                    intent.putExtra("StartDate",studentModel.getStartDate());
                    //开始周期
                    intent.putExtra("CurrStart",studentModel.getCurrStart());
                    //结束周期
                    intent.putExtra("CurrEnd",studentModel.getCurrEnd());
                    //昵称
                    intent.putExtra("UserName",studentModel.getUserName());
                    //手机号
                    intent.putExtra("Mobile",studentModel.getMobile());
                    //头像
                    intent.putExtra("Photo",studentModel.getPhoto());
                    //第几周期
                    intent.putExtra("Weekth",studentModel.getWeekth());
                    Log.i("zhouqizhouqi"+studentModel.getWeekth());
                    startActivityForResult(intent,BODY);
                }
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
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("正在查询，请稍候...");




    }

    @Override
    protected void initDatas() {
        retestPre = new RetestclassImp();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_query:
                if (et_query.getText().toString().isEmpty()) {
                    Util.toastMsg("未输入关键字查询");

                } else {
                    progressDialog.show();
                    studentModelList.clear();
                    queryAdapter.notifyDataSetChanged();
                    retestPre.doGetqueryResult(et_query.getText().toString(),loginid+"",progressDialog,this);
                }
                break;
            case R.id.tv_retest_query_cancel:
                finish();
                break;
        }
    }



    @Subscribe
    public void onEvent1(StudentEvent student) {
        System.out.println(">>》》》》》》》》》》》》》》" + student.getStudentModels());
        studentModelList = student.getStudentModels();
        queryAdapter.updateData(studentModelList);


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==BODY&&resultCode==RESULT_OK){
            studentModelList.clear();
            retestPre.doGetBanjiStudent(Long.parseLong(ClassId),loginid);
            queryAdapter.notifyDataSetChanged();

        }

    }
}
