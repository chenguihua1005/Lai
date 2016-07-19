package com.softtek.lai.module.bodygame.view;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame.model.FuceNumModel;
import com.softtek.lai.module.bodygame.model.TiGuanSaiModel;
import com.softtek.lai.module.bodygame.model.TotolModel;
import com.softtek.lai.module.bodygame.presenter.ITiGuanSai;
import com.softtek.lai.module.bodygame.presenter.TiGuanSaiImpl;
import com.softtek.lai.module.counselor.view.AssistantActivity;
import com.softtek.lai.module.counselor.view.CounselorClassListActivity;
import com.softtek.lai.module.counselor.view.GameActivity;
import com.softtek.lai.module.counselor.view.SPHonorActivity;
import com.softtek.lai.module.jingdu.view.JingduActivity;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.module.message.presenter.IMessagePresenter;
import com.softtek.lai.module.message.presenter.MessageImpl;
import com.softtek.lai.module.message.view.JoinGameDetailActivity;
import com.softtek.lai.module.message.view.MessageActivity;
import com.softtek.lai.module.retest.view.RetestActivity;
import com.softtek.lai.module.review.view.ReviewActivity;
import com.softtek.lai.module.tips.view.TipsActivity;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by lareina.qiao on 3/17/2016.
 * 体管赛页面
 */
@InjectLayout(R.layout.activity_counselor)
public class CounselorActivity extends BaseActivity implements View.OnClickListener {
    //标题栏
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    //banner图片
    @InjectView(R.id.iv_adv)
    ImageView iv_adv;
    //复测按钮
    @InjectView(R.id.ll_counselor_fuce)
    LinearLayout ll_counselor_fuce;
    //体管赛点击
    @InjectView(R.id.ll_tiguansai)
    LinearLayout ll_tiguansai;
    @InjectView(R.id.im_refresh)
    ImageView im_refresh;
    //新学员录入按钮
    @InjectView(R.id.ll_new_student)
    LinearLayout ll_new_student;
    //往期回顾按钮
    @InjectView(R.id.ll_review)
    LinearLayout ll_review;
    //当前进度按钮
    @InjectView(R.id.ll_process)
    LinearLayout ll_process;
    //荣誉榜按钮
    @InjectView(R.id.ll_honor)
    LinearLayout ll_honor;
    //赛况
    @InjectView(R.id.ll_match)
    LinearLayout ll_match;
    //提示页面
    @InjectView(R.id.ll_tip)
    LinearLayout ll_tip;
    //助教管理
    @InjectView(R.id.ll_assistant)
    LinearLayout ll_assistant;
    @InjectView(R.id.tv_fucenum)
    TextView tv_fucenum;
    @InjectView(R.id.tv_totalperson)
    TextView tv_totalperson;
    @InjectView(R.id.tv_total_loss)
    TextView tv_total_loss;

    @InjectView(R.id.iv_email)
    ImageView iv_email;
    @InjectView(R.id.img_red)
    ImageView img_red;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    private IMessagePresenter messagePresenter;
    private MessageReceiver mMessageReceiver;

    private ITiGuanSai iTiGuanSai;
    private TiGuanSaiModel tiGuanSai;
    private FuceNumModel fuceNumModel;
    UserInfoModel userInfoModel = UserInfoModel.getInstance();
    long loginid = Long.parseLong(userInfoModel.getUser().getUserid());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
        tintManager.setStatusBarAlpha(0);
        messagePresenter = new MessageImpl(this);
        registerMessageReceiver();
        iv_email.setBackgroundResource(R.drawable.email);
        fl_right.setOnClickListener(this);
        iv_email.setOnClickListener(this);
        //初始化事件总线，并注册当前类
        //按钮监听
        ll_counselor_fuce.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        ll_tiguansai.setOnClickListener(this);
        ll_new_student.setOnClickListener(this);
        ll_honor.setOnClickListener(this);
        ll_process.setOnClickListener(this);
        ll_review.setOnClickListener(this);
        ll_match.setOnClickListener(this);
        ll_assistant.setOnClickListener(this);
        ll_tip.setOnClickListener(this);
        im_refresh.setOnClickListener(this);

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(TiGuanSaiModel tiGuanSai) {
        iv_adv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Picasso.with(this).load(tiGuanSai.getImg_Addr()).placeholder(R.drawable.default_icon_rect).error(R.drawable.default_icon_rect).into(iv_adv);


    }

    @Subscribe
    public void onEvent1(FuceNumModel fuceNum) {
        if (fuceNum != null && !fuceNum.toString().isEmpty()) {
            if (Integer.parseInt(fuceNum.getCount()) > 10) {
                tv_fucenum.setVisibility(View.VISIBLE);
                tv_fucenum.setText("10+");
            } else if (0 < Integer.parseInt(fuceNum.getCount()) && Integer.parseInt(fuceNum.getCount()) <= 10) {
                tv_fucenum.setVisibility(View.VISIBLE);
                tv_fucenum.setText(fuceNum.getCount());
            } else {
                tv_fucenum.setVisibility(View.GONE);
            }
        }


    }

    @Subscribe
    public void doGetTotol(List<TotolModel> totolModels) {
        tv_totalperson.setText(totolModels.get(0).getTotal_person());
        tv_total_loss.setText(totolModels.get(0).getTotal_loss());
    }


    @Override
    protected void initViews() {
        progressDialog = new ProgressDialog(this);
    }

    @Override
    protected void initDatas() {
        iTiGuanSai = new TiGuanSaiImpl();
        iTiGuanSai.getTiGuanSai();
        iTiGuanSai.doGetFuceNum(loginid);
        dialogShow("数据刷新中...");
        iTiGuanSai.doGetTotal(progressDialog);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        iTiGuanSai.doGetFuceNum(loginid);
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
        iTiGuanSai.doGetFuceNum(loginid);
        String userrole = UserInfoModel.getInstance().getUser().getUserrole();
        if (String.valueOf(Constants.VR).equals(userrole)) {

        } else {
            messagePresenter.getMessageRead(img_red);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
                            Intent login = new Intent(CounselorActivity.this, LoginActivity.class);
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
                    startActivity(new Intent(CounselorActivity.this, MessageActivity.class));
                }
                break;
            //复测按钮点击跳转事件
            case R.id.ll_counselor_fuce: {
                Intent intent = new Intent(this, RetestActivity.class);
                intent.putExtra("type", "1");
                startActivity(intent);
            }
            break;
            //主题栏返回事件
            case R.id.ll_left: {
                finish();
            }
            break;
            //体管赛按钮点击跳转事件
            case R.id.ll_tiguansai: {
                Intent intent = new Intent(this, CounselorClassListActivity.class);
                startActivity(intent);
            }
            break;
            //新学员录入跳转事件
            case R.id.ll_new_student: {
                Intent intent = new Intent(this, JoinGameDetailActivity.class);
                intent.putExtra("type", "0");
                startActivity(intent);
            }
            break;
            case R.id.ll_honor: {
                Intent intent = new Intent(this, SPHonorActivity.class);
                startActivity(intent);

            }
            break;
            //当前进度事件跳转
            case R.id.ll_process: {
                Intent intent = new Intent(this, JingduActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.ll_review: {
                Intent intent = new Intent(this, ReviewActivity.class);
                startActivity(intent);

            }
            break;
            //大赛赛况事件跳转
            case R.id.ll_match: {
                Intent intent = new Intent(this, GameActivity.class);
                startActivity(intent);

            }
            break;
            //提示事件跳转
            case R.id.ll_tip: {
                Intent intent = new Intent(this, TipsActivity.class);
                startActivity(intent);

            }
            break;
            //助教管理跳转事件
            case R.id.ll_assistant: {
                Intent intent = new Intent(this, AssistantActivity.class);
                startActivity(intent);

            }
            break;
            case R.id.im_refresh:
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setMessage("数据刷新中...");
                progressDialog.show();
                iTiGuanSai.doGetTotal(progressDialog);
                break;

        }
    }

}
