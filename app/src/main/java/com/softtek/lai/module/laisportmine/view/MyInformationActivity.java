package com.softtek.lai.module.laisportmine.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
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
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.laisportmine.model.RunTeamModel;
import com.softtek.lai.module.laisportmine.net.MineService;
import com.softtek.lai.module.laisportmine.present.MyRunTeamManager;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.message2.view.Message2Activity;
import com.softtek.lai.module.sport.view.HistorySportListActivity;
import com.softtek.lai.stepcount.service.StepService;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_my_information)
public class MyInformationActivity extends BaseActivity implements View.OnClickListener, MyRunTeamManager.MyRunTeamCallback {
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_runteamname)
    TextView tv_runteamname;
    @InjectView(R.id.btn_signout)
    Button btn_signout;
    @InjectView(R.id.tv_runteamnum)
    TextView tv_runteamnum;
    @InjectView(R.id.tv_administ_name)
    TextView tv_administ_name;
    @InjectView(R.id.circle_teamhead)
    CircleImageView circle_teamhead;

    MyRunTeamManager myRunTeamManager;
    UserInfoModel userInfoModel = UserInfoModel.getInstance();
    long accountid = Long.parseLong(userInfoModel.getUser().getUserid());
    private MineService service;
    RunTeamModel runTeamModels;
    AlertDialog.Builder information_dialog = null;

    @Override
    protected void initViews() {
        tv_title.setText("我的跑团");
        ll_left.setOnClickListener(this);
        btn_signout.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        runTeamModels = new RunTeamModel();
        myRunTeamManager = new MyRunTeamManager(this);
        myRunTeamManager.doGetNowRgName(accountid);
    }

    @Override
    public void onResume() {
        super.onResume();
        myRunTeamManager.doGetNowRgName(accountid);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.btn_signout:
                information_dialog = new AlertDialog.Builder(this);
                information_dialog.setTitle("温馨提示").setMessage("您确认退出跑团吗？").setPositiveButton("确认", new DialogInterface.OnClickListener() {
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
        }
    }

    @Override
    public void getRunTeamName(RunTeamModel runTeamModel) {
        try {
            if (runTeamModel != null) {
                runTeamModels = runTeamModel;
                if (!runTeamModel.getRgName().isEmpty()) {
                    tv_runteamname.setText(runTeamModels.getRgName());
                    tv_runteamnum.setText(runTeamModel.getRgNum());
                    tv_administ_name.setText(runTeamModel.getRgManager());
                    if (!TextUtils.isEmpty(runTeamModel.getRgPhoto()))
                    {
                        Picasso.with(this).load(AddressManager.get("photoHost")+runTeamModel.getRgPhoto()).fit().error(R.drawable.img_default).into(circle_teamhead);
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void doSignOutRG(long accountid) {
        final String token = UserInfoModel.getInstance().getToken();
        service = ZillaApi.NormalRestAdapter.create(MineService.class);
        service.doSignOutRG(token, accountid, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                int status = responseData.getStatus();
                switch (status) {
                    case 200:
                        LocalBroadcastManager.getInstance(MyInformationActivity.this).sendBroadcast(new Intent(StepService.STEP_CLOSE_SELF));
                        UserModel model = UserInfoModel.getInstance().getUser();
                        model.setIsJoin("0");
                        UserInfoModel.getInstance().saveUserCache(model);
                        startActivity(new Intent(MyInformationActivity.this, HomeActviity.class));
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
            }
        });
    }
}
