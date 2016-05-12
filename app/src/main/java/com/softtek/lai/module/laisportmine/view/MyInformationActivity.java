package com.softtek.lai.module.laisportmine.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.home.view.ValidateCertificationActivity;
import com.softtek.lai.module.laisportmine.net.MineService;
import com.softtek.lai.module.laisportmine.present.MyRunTeamManager;
import com.softtek.lai.module.sport.view.JoinGroupActivity;
import com.softtek.lai.utils.StringUtil;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_my_information)
public class MyInformationActivity extends BaseActivity implements View.OnClickListener,MyRunTeamManager.MyRunTeamCallback{
@InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_runteamname)
    TextView tv_runteamname;
    @InjectView(R.id.btn_signout)
    Button btn_signout;
    //消息提醒标志
    @InjectView(R.id.im_news_flag)
    ImageView im_news_flag;
    //消息点击进入
    @InjectView(R.id.Re_mynews)
    RelativeLayout Re_mynews;
    MyRunTeamManager myRunTeamManager;
    UserInfoModel userInfoModel=UserInfoModel.getInstance();
    long accountid=Long.parseLong(userInfoModel.getUser().getUserid());
    private MineService service;
    AlertDialog.Builder information_dialog = null;
    @Override
    protected void initViews() {
        tv_title.setText("我的资料");
        ll_left.setOnClickListener(this);
        btn_signout.setOnClickListener(this);
        Re_mynews.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        myRunTeamManager=new MyRunTeamManager(this);
        myRunTeamManager.doGetNowRgName(accountid);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ll_left:
                finish();
                break;
            case R.id.btn_signout:
                information_dialog = new AlertDialog.Builder(this);
                information_dialog.setTitle("确认退出跑团？").setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //跳转到身份认证界面
                        doSignOutRG(accountid);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create().show();
                break;
            case R.id.Re_mynews:
                startActivity(new Intent(this,MyNewsActivity.class));
                break;
        }
    }

    @Override
    public void getRunTeamName(String data,String flag) {
        tv_runteamname.setText(data);
        if (flag.equals(""))
        {}
        else
        if (flag.equals("True"))
        {
            im_news_flag.setVisibility(View.VISIBLE);
        }
        else {
            im_news_flag.setVisibility(View.GONE);
        }
    }
    public void doSignOutRG(long accountid)
    {
        String token= UserInfoModel.getInstance().getToken();
        service= ZillaApi.NormalRestAdapter.create(MineService.class);
        service.doSignOutRG(token, accountid, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                int status=responseData.getStatus();
                switch (status)
                {
                    case 200:
                        Intent intent=new Intent(MyInformationActivity.this,JoinGroupActivity.class);
                        startActivity(intent);
                        break;
                    case 100:
                        Log.i(responseData.getMsg());
                        break;
                    default:
                        Log.i(responseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });
    }
}
