package com.softtek.lai.module.bodygamecc.view;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame.model.TiGuanSaiModel;
import com.softtek.lai.module.bodygame.model.TotolModel;
import com.softtek.lai.module.bodygame.presenter.ITiGuanSai;
import com.softtek.lai.module.bodygame.presenter.TiGuanSaiImpl;
import com.softtek.lai.module.counselor.view.GameActivity;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.module.message.presenter.IMessagePresenter;
import com.softtek.lai.module.message.presenter.MessageImpl;
import com.softtek.lai.module.message.view.MessageActivity;
import com.softtek.lai.module.tips.view.TipsActivity;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_body_game_cc)
public class BodyGameCcActivity extends BaseActivity implements View.OnClickListener{
    //标题栏
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.ll_match)
    LinearLayout ll_match;

    @InjectView(R.id.ll_tipcc)
    LinearLayout ll_tipcc;

    @InjectView(R.id.tv_total_losscc)
    TextView tv_total_losscc;
    @InjectView(R.id.tv_totalpersoncc)
    TextView tv_totalpersoncc;
    //BANNER图片
    @InjectView(R.id.iv_advcc)
    ImageView iv_advcc;
    //刷新
    @InjectView(R.id.tv_totalcc)
    ImageView tv_totalcc;
    @InjectView(R.id.iv_email)
    ImageView iv_email;
    @InjectView(R.id.img_red)
    ImageView img_red;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    private IMessagePresenter messagePresenter;
    private MessageReceiver mMessageReceiver;

    private ITiGuanSai iTiGuanSai;
    UserInfoModel userInfoModel=UserInfoModel.getInstance();
    long loginid=Long.parseLong(userInfoModel.getUser().getUserid());
    private ProgressDialog progressDialog;
    String role=userInfoModel.getUser().getUserrole();
    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        progressDialog = new ProgressDialog(this);
        ll_left.setOnClickListener(this);
        ll_tipcc.setOnClickListener(this);
        ll_match.setOnClickListener(this);
        tv_totalcc.setOnClickListener(this);
        tintManager.setStatusBarAlpha(0);
        messagePresenter = new MessageImpl(this);
        registerMessageReceiver();
        iv_email.setBackgroundResource(R.drawable.email);
        fl_right.setOnClickListener(this);
        iv_email.setOnClickListener(this);


    }

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(Constants.MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Constants.MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                img_red.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        UserModel model=UserInfoModel.getInstance().getUser();
        if(model==null){
            return;
        }
        String userrole = UserInfoModel.getInstance().getUser().getUserrole();
        if (String.valueOf(Constants.VR).equals(userrole)) {

        } else {
            messagePresenter.getMessageRead(img_red);
        }
    }
    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        tv_totalcc.setOnClickListener(this);
        super.onDestroy();
    }

    @Override
    protected void initDatas() {

        iTiGuanSai=new TiGuanSaiImpl();
        iTiGuanSai.doGetTotal(progressDialog);
        iTiGuanSai.getTiGuanSai();

    }
    @Subscribe
    public void onEvent(TiGuanSaiModel tiGuanSai){
        iv_advcc.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (!TextUtils.isEmpty(tiGuanSai.getImg_Addr())) {
            iv_advcc.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Picasso.with(this).load(tiGuanSai.getImg_Addr()).placeholder(R.drawable.default_icon_rect).fit().error(R.drawable.default_icon_rect).into(iv_advcc);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.fl_right:
            case R.id.iv_email:
                String userroles = UserInfoModel.getInstance().getUser().getUserrole();
                if (String.valueOf(Constants.VR).equals(userroles)) {
                    //提示用户让他登录或者直接进入2个功能的踢馆赛模块
                    AlertDialog.Builder information_dialog = null;
                    information_dialog = new AlertDialog.Builder(this);
                    information_dialog.setTitle("您当前处于游客模式，需要登录认证").setPositiveButton("现在登录", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent login = new Intent(BodyGameCcActivity.this, LoginActivity.class);
                            login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(login);
                        }
                    }).setNegativeButton("稍候", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
                } else {
                    startActivity(new Intent(BodyGameCcActivity.this, MessageActivity.class));
                }
                break;

            case R.id.ll_left:
                finish();
                break;
            case R.id.ll_tipcc:
                Intent intent=new Intent(this,TipsActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_match:
                Intent intents=new Intent(this,GameActivity.class);
                startActivity(intents);
                break;
            case R.id.tv_totalcc:
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setMessage("数据刷新中...");
                progressDialog.show();
                iTiGuanSai.doGetTotal(progressDialog);
                break;

        }

    }
    @Subscribe
    public void doGetTotol(List<TotolModel> totolModels){
        System.out.println("dsadasdsadasda>>》》》》》》》》》》》》》》"+totolModels.get(0).getTotal_loss());
        tv_totalpersoncc.setText(totolModels.get(0).getTotal_person());
        tv_total_losscc.setText(totolModels.get(0).getTotal_loss());
    }
}
