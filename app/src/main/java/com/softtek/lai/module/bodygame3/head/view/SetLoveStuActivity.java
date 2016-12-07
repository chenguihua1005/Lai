package com.softtek.lai.module.bodygame3.head.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.head.net.HeadService;
import com.softtek.lai.utils.RequestCallback;

import butterknife.InjectView;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_set_love_stu)
public class SetLoveStuActivity extends BaseActivity implements View.OnClickListener{
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.et_phone)
    EditText et_phone;
    HeadService headService;
    Long AccountId;
    String ClassId;


    @Override
    protected void initViews() {
        fl_right.setOnClickListener(this);
        tv_right.setText("确定");
        tv_title.setText("修改爱心学员");
    }

    @Override
    protected void initDatas() {
        headService= ZillaApi.NormalRestAdapter.create(HeadService.class);
        AccountId=getIntent().getLongExtra("AccounId",0);
        ClassId= TextUtils.isEmpty(getIntent().getStringExtra("ClassId"))?" ":getIntent().getStringExtra("ClassId");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.fl_right:
                if ("".equals(et_phone.getText().toString()))
                {
                    Util.toastMsg("手机号不能为空");
                }
                else {
                    setLoveStu(AccountId, ClassId, et_phone.getText().toString());
                }
                break;
        }
    }
    /*
   * 修改爱心学员
   *
   * */
    private void setLoveStu(Long accountid,String classid,String mobile) {
        headService.doPostAddMineLovePC(UserInfoModel.getInstance().getToken(), accountid, classid, mobile, new RequestCallback<ResponseData>() {
                    @Override
                    public void success(ResponseData responseData, Response response) {
                        int staus=responseData.getStatus();
                        switch (staus)
                        {
                            case 200:
                                Util.toastMsg(responseData.getMsg());
                                Intent intent=new Intent();
                                setResult(RESULT_OK,intent);
                                finish();
                                break;
                            default:
                                Util.toastMsg(responseData.getMsg());
                                break;
                        }
                    }
                }
        );
    }
}
