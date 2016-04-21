/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.view;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.counselor.adapter.HonorStudentAdapter;
import com.softtek.lai.module.counselor.model.HonorInfoModel;
import com.softtek.lai.module.counselor.model.HonorTable1Model;
import com.softtek.lai.module.counselor.model.HonorTableModel;
import com.softtek.lai.module.counselor.presenter.HonorImpl;
import com.softtek.lai.module.counselor.presenter.IHonorPresenter;
import com.softtek.lai.module.studetail.view.StudentDetailActivity;
import com.softtek.lai.utils.ACache;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jarvis.liu on 3/22/2016.
 * 荣誉榜
 */
@InjectLayout(R.layout.activity_sp_honor)
public class SRHonorActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener, BaseFragment.OnFragmentInteractionListener {

    @LifeCircleInject
    ValidateLife validateLife;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.text_servernum)
    TextView text_servernum;

    @InjectView(R.id.text_fwrs_top)
    TextView text_fwrs_top;

    @InjectView(R.id.text_fwrs_mc)
    TextView text_fwrs_mc;

    @InjectView(R.id.text_weight)
    TextView text_weight;

    @InjectView(R.id.text_jzjs_top)
    TextView text_jzjs_top;

    @InjectView(R.id.text_jzjs_mc)
    TextView text_jzjs_mc;

    @InjectView(R.id.text_rtest)
    TextView text_rtest;

    @InjectView(R.id.text_fc_top)
    TextView text_fc_top;

    @InjectView(R.id.text_fc_mc)
    TextView text_fc_mc;


    @InjectView(R.id.list_stars)
    ListView list_stars;

    @InjectView(R.id.img_fwrs)
    ImageView img_fwrs;
    @InjectView(R.id.img_jzjs)
    ImageView img_jzjs;
    @InjectView(R.id.img_fc)
    ImageView img_fc;


    private IHonorPresenter honorPresenter;
    private ACache aCache;

    List<HonorTable1Model> honorTable1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_left.setOnClickListener(this);
        EventBus.getDefault().register(this);


    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(HonorInfoModel honorInfo) {
        System.out.println("honorInfo:" + honorInfo);
        honorTable1 = honorInfo.getTable1();
        List<HonorTableModel> honorTable = honorInfo.getTable();
        HonorStudentAdapter adapter = new HonorStudentAdapter(this, honorTable1);
        list_stars.setAdapter(adapter);

        for (int i = 0; i < honorTable.size(); i++) {
            HonorTableModel honor = honorTable.get(i);
            String rowname = honor.getRowname().toString();
            String num = honor.getNum().toString();
            String rank_num = honor.getRank_num().toString();

            if (Integer.parseInt(rank_num) <= 10) {
                if ("rtest_rank".equals(rowname)) {
                    text_rtest.setText(num);
                    text_fc_mc.setText(rank_num);
                    img_fc.setImageResource(R.drawable.img_sp_honor_jin);
                    text_fc_mc.setTextColor(getResources().getColor(R.color.word8));
                } else if ("servernum_rank".equals(rowname)) {
                    text_servernum.setText(num);
                    text_fwrs_mc.setText(rank_num);
                    img_fwrs.setImageResource(R.drawable.img_sp_honor_jin);
                    text_fwrs_mc.setTextColor(getResources().getColor(R.color.word8));
                } else if ("starnum_rank".equals(rowname)) {
                    //text_starnum.setText(num + "/" + rank_num);
                } else if ("weight_rank".equals(rowname)) {
                    text_weight.setText(num);
                    text_jzjs_mc.setText(rank_num);
                    img_jzjs.setImageResource(R.drawable.img_sp_honor_jin);
                    text_jzjs_mc.setTextColor(getResources().getColor(R.color.word8));
                }
            } else {
                if ("rtest_rank".equals(rowname)) {
                    text_rtest.setText(num);
                    text_fc_mc.setText(rank_num);
                    img_fc.setImageResource(R.drawable.img_sp_honor_yin);
                    text_fc_mc.setTextColor(getResources().getColor(R.color.word7));
                } else if ("servernum_rank".equals(rowname)) {
                    text_servernum.setText(num);
                    text_fwrs_mc.setText(rank_num);
                    img_fwrs.setImageResource(R.drawable.img_sp_honor_yin);
                    text_fwrs_mc.setTextColor(getResources().getColor(R.color.word7));
                } else if ("starnum_rank".equals(rowname)) {
                    //text_starnum.setText(num + "/" + rank_num);
                } else if ("weight_rank".equals(rowname)) {
                    text_weight.setText(num);
                    text_jzjs_mc.setText(rank_num);
                    img_jzjs.setImageResource(R.drawable.img_sp_honor_yin);
                    text_jzjs_mc.setTextColor(getResources().getColor(R.color.word7));
                }
            }
        }


    }

    @Override
    protected void initViews() {
        //tv_left.setLayoutParams(new Toolbar.LayoutParams(DisplayUtil.dip2px(this,15),DisplayUtil.dip2px(this,30)));
        tv_title.setText(R.string.CounselorF);
        list_stars.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String accountId = honorTable1.get(position).getAccountId();
                String classId = honorTable1.get(position).getClassId();
                Intent intent = new Intent(SRHonorActivity.this, StudentDetailActivity.class);
                intent.putExtra("userId", Long.parseLong(accountId));
                intent.putExtra("classId", Long.parseLong(classId));
                intent.putExtra("review", 1);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initDatas() {
        honorPresenter = new HonorImpl(this);
        aCache = ACache.get(this, Constants.USER_ACACHE_DATA_DIR);
        honorPresenter.getSRHonor();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
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

}