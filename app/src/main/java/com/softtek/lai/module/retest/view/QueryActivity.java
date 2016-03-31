/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.retest.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.InjectView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.retest.adapter.QueryAdapter;
import com.softtek.lai.module.retest.eventModel.StudentEvent;
import com.softtek.lai.module.retest.model.StudentModel;
import com.softtek.lai.module.retest.present.RetestPre;
import com.softtek.lai.module.retest.present.RetestclassImp;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

import java.util.ArrayList;
import java.util.List;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        iv_query.setOnClickListener(this);
        queryAdapter = new QueryAdapter(this, studentModelList);
        List_querystudent.setAdapter(queryAdapter);
        ll_query_noresult.setOnClickListener(this);
        tv_retest_query_cancel.setOnClickListener(this);
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
        retestPre = new RetestclassImp();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_query:
                if (et_query.getText().toString().isEmpty()) {
                    Util.toastMsg("请给查询提示信号");

                } else {
                    retestPre.doGetqueryResult(et_query.getText().toString());
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
}
