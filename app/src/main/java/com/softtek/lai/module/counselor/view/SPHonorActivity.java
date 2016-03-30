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
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.assistant.model.AssistantDetailInfo;
import com.softtek.lai.module.assistant.presenter.GameImpl;
import com.softtek.lai.module.assistant.presenter.IGamePresenter;
import com.softtek.lai.module.counselor.adapter.HonorStudentAdapter;
import com.softtek.lai.module.counselor.model.HonorInfo;
import com.softtek.lai.module.counselor.model.HonorTable;
import com.softtek.lai.module.counselor.model.HonorTable1;
import com.softtek.lai.module.counselor.presenter.CounselorClassImpl;
import com.softtek.lai.module.counselor.presenter.ICounselorClassPresenter;
import com.softtek.lai.module.login.model.User;
import com.softtek.lai.utils.ACache;
import com.softtek.lai.utils.SoftInputUtil;
import com.softtek.lai.widgets.WheelView;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_sp_honor)
public class SPHonorActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener, BaseFragment.OnFragmentInteractionListener {

    @LifeCircleInject
    ValidateLife validateLife;

    @InjectView(R.id.tv_left)
    TextView tv_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.text_servernum)
    TextView text_servernum;

    @InjectView(R.id.text_weight)
    TextView text_weight;

    @InjectView(R.id.text_rtest)
    TextView text_rtest;

    @InjectView(R.id.text_starnum)
    TextView text_starnum;

    @InjectView(R.id.list_stars)
    ListView list_stars;


    private ICounselorClassPresenter counselorClassPresenter;
    private ACache aCache;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_left.setOnClickListener(this);
        EventBus.getDefault().register(this);


    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(HonorInfo honorInfo) {
        System.out.println("honorInfo:" + honorInfo);
        List<HonorTable1> honorTable1=honorInfo.getTable1();
        List<HonorTable> honorTable=honorInfo.getTable();
        HonorStudentAdapter adapter=new HonorStudentAdapter(this,honorTable1);
        list_stars.setAdapter(adapter);

        for (int i = 0; i < honorTable.size(); i++) {
            HonorTable honor=honorTable.get(i);
            String rowname=honor.getRowname().toString();
            String num=honor.getNum().toString();
            String rank_num=honor.getRank_num().toString();
            if("rtest_rank".equals(rowname)){
                text_starnum.setText(num+"/"+rank_num);
            }else if("servernum_rank".equals(rowname)){
                text_servernum.setText(num+"/"+rank_num);
            }else if("starnum_rank".equals(rowname)){
                text_starnum.setText(num+"/"+rank_num);
            }else if("weight_rank".equals(rowname)){
                text_weight.setText(num+"/"+rank_num);
            }
        }


    }

    @Override
    protected void initViews() {
        tv_left.setBackgroundResource(R.drawable.back);
        //tv_left.setLayoutParams(new Toolbar.LayoutParams(DisplayUtil.dip2px(this,15),DisplayUtil.dip2px(this,30)));
        tv_title.setText("荣誉榜");

    }

    @Override
    protected void initDatas() {
        counselorClassPresenter = new CounselorClassImpl(this);
        aCache = ACache.get(this, Constants.USER_ACACHE_DATA_DIR);

        counselorClassPresenter.getSPHonor();
    }

    @Override
    public void onClick(View v) {
        SoftInputUtil.hidden(this);
        switch (v.getId()) {
            case R.id.tv_left:
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
