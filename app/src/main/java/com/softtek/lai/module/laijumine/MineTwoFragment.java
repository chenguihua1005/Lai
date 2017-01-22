package com.softtek.lai.module.laijumine;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.head.view.EditSignaActivity;
import com.softtek.lai.module.bodygame3.more.view.LossWeightAndFatActivity;
import com.softtek.lai.module.community.view.FocusFragment;
import com.softtek.lai.module.community.view.PersionalActivity;
import com.softtek.lai.module.health.view.HealthyRecordActivity;
import com.softtek.lai.module.home.view.ModifyPersonActivity;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.message2.view.Message2Activity;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_mine_fragment)
public class MineTwoFragment extends LazyBaseFragment implements View.OnClickListener {

    private UserModel model;

    @InjectView(R.id.cir_userphoto)
    CircleImageView cir_userphoto;//头像
    @InjectView(R.id.tv_username)
    TextView tv_username;//用户名

    //跳转
    @InjectView(R.id.tv_setting)
    TextView tv_setting;
    @InjectView(R.id.tv_editor_signature)
    TextView tv_editor_signature;
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

    private int GET_Sian=1;//个人签名
    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initViews() {
        tv_setting.setOnClickListener(this);
        tv_editor_signature.setOnClickListener(this);
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
        model=UserInfoModel.getInstance().getUser();
        String photo=model.getPhoto();
        if (TextUtils.isEmpty(photo)) {
            Picasso.with(getContext()).load(R.drawable.img_default).into(cir_userphoto);
        } else {
            Picasso.with(getContext()).load(AddressManager.get("photoHost") + photo).fit().centerCrop().placeholder(R.drawable.img_default).error(R.drawable.img_default).into(cir_userphoto);
        }
        tv_username.setText(model.getNickname());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tv_setting:
                startActivity(new Intent(getContext(), ModifyPersonActivity.class));
                break;
            //跳转到我的签名
            case R.id.tv_editor_signature:
                Intent intent1 = new Intent(getContext(), EditSignaActivity.class);
//                if (TextUtils.isEmpty(memberInfoModel.getPersonalityName())) {
//                    intent1.putExtra("sina", "");
//                } else {
                    intent1.putExtra("sina", tv_editor_signature.getText().toString());
//                }
                startActivityForResult(intent1, GET_Sian);
                break;
            //跳转到我的动态
            case R.id.re_mydy:
                Intent personal = new Intent(getContext(), PersionalActivity.class);
                personal.putExtra("isFocus", 1);
                personal.putExtra("personalId", String.valueOf(UserInfoModel.getInstance().getUserId()));
                startActivity(personal);
                break;
            //跳转关注
            case R.id.re_guanzhu:
                startActivity(new Intent(getContext(), FocusFragment.class));
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
                getActivity().startActivity(new Intent(getActivity(), Message2Activity.class));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //个人编辑页返回更新本页签名
        if (requestCode == GET_Sian && resultCode == getActivity().RESULT_OK) {
            if (!TextUtils.isEmpty(data.getStringExtra("sina"))) {
                tv_editor_signature.setText(data.getStringExtra("sina"));
                tv_editor_signature.setCompoundDrawables(null, null, null, null);
            }

        }
    }
}
