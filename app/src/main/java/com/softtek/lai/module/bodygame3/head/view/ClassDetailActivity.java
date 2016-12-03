package com.softtek.lai.module.bodygame3.head.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.head.model.ClasslistModel;
import com.softtek.lai.module.bodygame3.head.net.HeadService;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.message2.view.ZQSActivity;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_class_detail)
public class ClassDetailActivity extends BaseActivity implements View.OnClickListener{
    @InjectView(R.id.cir_img)
    CircleImageView cir_img;//教练头像
    @InjectView(R.id.tv_coach_name)
    TextView tv_coach_name;//教练名称
    @InjectView(R.id.tv_classname)
    TextView tv_classname;//班级名称
    @InjectView(R.id.tv_classid)
    TextView tv_classid;//班级id
    @InjectView(R.id.tv_StaClassDate)
    TextView tv_StaClassDate;//开班日期
    @InjectView(R.id.tv_classPerNum)
    TextView tv_classPerNum;//班级人数
    @InjectView(R.id.tv_zhiqing)
    TextView tv_zhiqing;//跳转知情书说明
    @InjectView(R.id.btn_joinclass)
    Button btn_joinclass;//加入班级按钮
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.cb_term)
    CheckBox cb_term;
    HeadService headService;

    ClasslistModel classlistModel;
    @Override
    protected void initViews() {
        tv_zhiqing.setOnClickListener(this);
        btn_joinclass.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        tv_title.setText("班级推荐");
        classlistModel= (ClasslistModel) getIntent().getSerializableExtra("ClasslistModel");
        try {
            if (!TextUtils.isEmpty(classlistModel.getClassMasterPhoto()))
            {
                //教练头像
                Picasso.with(this).load(AddressManager.get("photoHost")+classlistModel.getClassMasterPhoto()).fit().into(cir_img);
                Log.i("教练头像",AddressManager.get("photoHost")+classlistModel.getClassMasterPhoto());
            }
            tv_coach_name.setText(classlistModel.getClassMasterName());//总教练名称
            tv_classname.setText(classlistModel.getClassName());//班级名称
            tv_classid.setText(classlistModel.getClassCode());//班级编号
            if (!TextUtils.isEmpty(classlistModel.getClassStart())) {
                String[] date = classlistModel.getClassStart().split("-");
                String[] date1 = date[2].split(" ");
                tv_StaClassDate.setText(date[0] + "年" + Long.parseLong(date[1]) + "月" + Long.parseLong(date1[0]) + "日");//开班日期
            }
            tv_classPerNum.setText(classlistModel.getClassMemberNum()+"人");
        } catch (Exception e) {
            e.printStackTrace();
        }
        cb_term.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    btn_joinclass.setEnabled(true);
                } else {
                    btn_joinclass.setEnabled(false);
                }
            }
        });
    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tv_zhiqing:
                startActivity(new Intent(this, ZQSActivity.class));
                break;
            case R.id.btn_joinclass:
                doJoinClass();
                break;
            case R.id.ll_left:
                finish();
                break;
        }
    }

    private void doJoinClass() {
        headService= ZillaApi.NormalRestAdapter.create(HeadService.class);
        headService.doPostClass(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), classlistModel.getClassId(), new Callback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                Util.toastMsg(responseData.getMsg());
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
