package com.softtek.lai.module.laijumine;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.more.model.LossWeightAndFat;
import com.softtek.lai.module.bodygame3.more.view.LossWeightAndFatActivity;
import com.softtek.lai.module.community.view.PersionalActivity;
import com.softtek.lai.module.health.view.HealthyRecordActivity;
import com.softtek.lai.module.health.view.HealthyRecordActivity$$ViewInjector;
import com.softtek.lai.module.healthrecords.view.HealthEntryActivity;
import com.softtek.lai.module.home.view.ActivityRecordFragment;
import com.softtek.lai.module.login.model.UserModel;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_mine_fragment)
public class MineTwoFragment extends LazyBaseFragment implements View.OnClickListener {

    //跳转
    @InjectView(R.id.re_mydy)
    RelativeLayout re_mydy;
    @InjectView(R.id.re_guanzhu)
    RelativeLayout re_guanzhu;
    @InjectView(R.id.re_fans)
    RelativeLayout re_fans;
    @InjectView(R.id.re_health)
    RelativeLayout re_health;
    @InjectView(R.id.re_mycustomers)
    RelativeLayout re_mycustomers;
    @InjectView(R.id.re_losslevel)
    RelativeLayout re_losslevel;
    @InjectView(R.id.re_sportlevel)
    RelativeLayout re_sportlevel;
    @InjectView(R.id.re_task)
    RelativeLayout re_task;
    @InjectView(R.id.re_mynews)
    RelativeLayout re_mynews;
    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initViews() {
        re_mydy.setOnClickListener(this);
        re_guanzhu.setOnClickListener(this);
        re_fans.setOnClickListener(this);
        re_health.setOnClickListener(this);
        re_mycustomers.setOnClickListener(this);
        re_losslevel.setOnClickListener(this);
        re_sportlevel.setOnClickListener(this);
        re_task.setOnClickListener(this);
        re_mynews.setOnClickListener(this);

    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            //跳转到我的动态
            case R.id.re_mydy:
                Intent personal = new Intent(getContext(), PersionalActivity.class);
                personal.putExtra("isFocus", 1);
                personal.putExtra("personalId", String.valueOf(UserInfoModel.getInstance().getUserId()));
//                personal.putExtra("personalName", );
                startActivity(personal);
                break;
            //跳转关注
            case R.id.re_guanzhu:
                break;
            //跳转粉丝
            case R.id.re_fans:
                break;
            //跳转健康记录
            case R.id.re_health:
                startActivity(new Intent(getContext(), HealthyRecordActivity.class));
                break;
            //跳转我的顾客
            case R.id.re_mycustomers:
                break;
            //跳转减重等级
            case R.id.re_losslevel:
                startActivity(new Intent(getContext(), LossWeightAndFatActivity.class));
                break;
            //跳转运动等级
            case R.id.re_sportlevel:
                break;
            //跳转任务与积分
            case R.id.re_task:
                break;
            //跳转消息中心
            case R.id.re_mynews:
                break;
        }
    }
}
