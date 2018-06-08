package com.softtek.lai.module.bodygame3.head.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.et_phone)
    EditText et_phone;
    HeadService headService;
    Long AccountId;
    String ClassId;


    @Override
    protected void initViews() {
        fl_right.setOnClickListener(this);
        ll_left.setOnClickListener(this);
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
                    Util.toastMsg("请输入手机号");
                }
                else {
                    // TODO Auto-generated method stub

                    boolean isphone=isMobileNO(et_phone.getText().toString());
                    if (isphone)
                    {
                        setLoveStu(AccountId, ClassId, et_phone.getText().toString());
                    }
                    else {
                        Util.toastMsg("请输入正确的手机号");
                    }

                }
                break;
            case R.id.ll_left:
                finish();
                break;
        }
    }

    /*
   * 修改爱心学员
   *
   * */
    private void setLoveStu(Long accountid,String classid,String mobile) {
        headService.doPostAddMineLovePC(classid,UserInfoModel.getInstance().getToken(), accountid, classid, mobile, new RequestCallback<ResponseData>() {
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
    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188、152、182、183、184、147、178
    联通：130、131、132、152、155、156、185、186、145、176
    电信：133、153、180、189、173、177、181（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1][34578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        return mobiles.matches(telRegex);
    }
}
