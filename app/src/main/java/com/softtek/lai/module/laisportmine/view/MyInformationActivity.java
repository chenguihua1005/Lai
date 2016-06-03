package com.softtek.lai.module.laisportmine.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
import com.softtek.lai.module.historydate.view.HistoryDataActivity;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.laisportmine.model.RunTeamModel;
import com.softtek.lai.module.laisportmine.net.MineService;
import com.softtek.lai.module.laisportmine.present.MyRunTeamManager;
import com.softtek.lai.module.group.view.JoinGroupActivity;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.sport.view.HistorySportListActivity;

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
    //我的运动点击
    @InjectView(R.id.re_pk_mysport)
    RelativeLayout re_pk_mysport;
    MyRunTeamManager myRunTeamManager;
    UserInfoModel userInfoModel=UserInfoModel.getInstance();
    long accountid=Long.parseLong(userInfoModel.getUser().getUserid());
    private MineService service;
    RunTeamModel runTeamModels;
    AlertDialog.Builder information_dialog = null;
    @Override
    protected void initViews() {
        tv_title.setText("我的资料");
        ll_left.setOnClickListener(this);
        btn_signout.setOnClickListener(this);
        Re_mynews.setOnClickListener(this);
        re_pk_mysport.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        runTeamModels=new RunTeamModel();
        myRunTeamManager=new MyRunTeamManager(this);
        myRunTeamManager.doGetNowRgName(accountid);
    }

    @Override
    public void onResume() {
        super.onResume();
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
                Intent intent=new Intent(this,MyNewsActivity.class);
                Log.i("retestWrite="+runTeamModels.toString());
                intent.putExtra("runTeamModels", runTeamModels);
                startActivity(intent);
                break;
            case R.id.re_pk_mysport:
                startActivity(new Intent(this,HistorySportListActivity.class));
                break;
        }
    }

    @Override
    public void getRunTeamName(RunTeamModel runTeamModel) {
        if (runTeamModel!=null) {
            runTeamModels = runTeamModel;
            if (!runTeamModel.getRgName().isEmpty()) {
                tv_runteamname.setText(runTeamModels.getRgName());
            }
            if (!runTeamModels.getIsHasMsg().isEmpty()) {
                if (runTeamModel.getIsHasMsg().equals("True")) {
                    im_news_flag.setVisibility(View.VISIBLE);
                } else {
                    im_news_flag.setVisibility(View.GONE);
                }
            }
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
                        UserModel model=UserInfoModel.getInstance().getUser();
                        model.setIsJoin("0");
                        UserInfoModel.getInstance().saveUserCache(model);
                        Intent intent=new Intent(MyInformationActivity.this,HomeActviity.class);
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
