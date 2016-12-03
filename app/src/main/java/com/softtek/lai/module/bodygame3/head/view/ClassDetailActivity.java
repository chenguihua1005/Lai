package com.softtek.lai.module.bodygame3.head.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

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
    Button btn_joinclass;
    HeadService headService;

    ClasslistModel classlistModel;
    @Override
    protected void initViews() {
        tv_zhiqing.setOnClickListener(this);
        btn_joinclass.setOnClickListener(this);
        classlistModel= (ClasslistModel) getIntent().getSerializableExtra("ClasslistModel");
        try {
            if (!TextUtils.isEmpty(classlistModel.getClassMasterPhoto()))
            {
                //教练头像
                Picasso.with(this).load(AddressManager.get("PhotoHost")+classlistModel.getClassMasterPhoto()).fit().into(cir_img);
            }
            tv_coach_name.setText(classlistModel.getClassMasterName());//总教练名称
            tv_classname.setText(classlistModel.getClassName());//班级名称
            tv_classid.setText(classlistModel.getClassCode());//班级编号
            String[] date=classlistModel.getClassStart().split("-");
            String[] date1=date[2].split(" ");
            tv_StaClassDate.setText(date[0]+"年"+date[1]+"月"+date1[0]+"日");//开班日期
            tv_classPerNum.setText(classlistModel.getClassMemberNum()+"");
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        }
    }

    private void doJoinClass() {
        headService.doPostClass(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), classlistModel.getClassId(), new Callback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                int status=responseData.getStatus();
                if (status==200)
                {
                    btn_joinclass.setClickable(false);
                    btn_joinclass.setText("已加入班级");
                    UserInfoModel.getInstance().getUser().setHasThClass(1);
                }
                else {
                    Util.toastMsg(responseData.getMsg());
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
