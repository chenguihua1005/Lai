/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.message2.view;


import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.home.event.UpdateClass;
import com.softtek.lai.module.bodygame3.more.model.ClassModel;
import com.softtek.lai.module.message2.model.InvitationConfirmShow;
import com.softtek.lai.module.message2.net.Message2Service;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

/**
 * 邀请消息确认
 * Created by jerry.guan on 1/12/2016.
 */
@InjectLayout(R.layout.activity_message_confirm)
public class MessageConfirmActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.tv_invitater_name)
    TextView tv_invitater_name;
    @InjectView(R.id.tv_head_coach_name)
    TextView tv_head_coach_name;
    @InjectView(R.id.head_image)
    CircleImageView head_image;
    @InjectView(R.id.tv_class_name)
    TextView tv_class_name;
    @InjectView(R.id.tv_class_code)
    TextView tv_class_code;
    @InjectView(R.id.tv_first_time)
    TextView tv_first_time;
    @InjectView(R.id.tv_role_name)
    TextView tv_role_name;
    @InjectView(R.id.tv_group_name)
    TextView tv_group_name;
    @InjectView(R.id.tv_aixin_phone)
    TextView tv_aixin_phone;
    @InjectView(R.id.rl_aixin)
    RelativeLayout rl_aixin;
    @InjectView(R.id.cb_term)
    CheckBox cb_term;
    @InjectView(R.id.tv_zqs)
    TextView tv_zqs;
    @InjectView(R.id.btn_yes)
    Button btn_yes;
    @InjectView(R.id.btn_no)
    Button btn_no;

    InvitationConfirmShow show;
    Message2Service service;
    String msgId;
    long introducerId;

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        tv_title.setText("参赛邀请");
        btn_no.setOnClickListener(this);
        btn_yes.setOnClickListener(this);
        rl_aixin.setOnClickListener(this);
        tv_zqs.setOnClickListener(this);
        cb_term.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    btn_yes.setEnabled(true);
                } else {
                    btn_yes.setEnabled(false);
                }
            }
        });
    }

    @Override
    protected void initDatas() {
        msgId = getIntent().getStringExtra("msgId");
        service = ZillaApi.NormalRestAdapter.create(Message2Service.class);
        service.getInvitationDetail(UserInfoModel.getInstance().getToken(),
                msgId,
                new RequestCallback<ResponseData<InvitationConfirmShow>>() {
                    @Override
                    public void success(ResponseData<InvitationConfirmShow> data, Response response) {
                        if (data.getStatus() == 200) {
                            onResult(data.getData());
                        }
                    }
                });

    }

    public void onResult(InvitationConfirmShow show) {
        this.show = show;
        Log.i(show.toString());
        tv_invitater_name.setText(show.getSender());
        tv_head_coach_name.setText(show.getClassMasterName());
        if (TextUtils.isEmpty(show.getClassMasterPhoto())) {
            Picasso.with(this).load(R.drawable.img_default).into(head_image);
        } else {
            Picasso.with(this).load(AddressManager.get("photoHost") + show.getClassMasterPhoto()).fit()
                    .error(R.drawable.img_default)
                    .placeholder(R.drawable.img_default).into(head_image);
        }
        tv_class_name.setText(show.getClassName());
        tv_class_code.setText(show.getClassCode());
        tv_first_time.setText(DateUtil.getInstance(DateUtil.yyyy_MM_dd)
                .convertDateStr(show.getClassStart(), "yyyy年MM月dd日"));
        int role = show.getClassRole();
        tv_role_name.setText(role == 1 ? "总教练" : role == 2 ? "教练" : role == 3 ? "助教" : role == 4 ? "学员" : "");
        tv_group_name.setText(show.getCGName());

        if (!TextUtils.isEmpty(show.getIntroducerMobile())) {
            tv_aixin_phone.setText(show.getIntroducerMobile());
        }
        if (show.getMsgStatus() == 0) {
            btn_yes.setVisibility(View.VISIBLE);
            btn_no.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.tv_zqs:
                startActivity(new Intent(this, ZQSActivity.class));
                break;
            case R.id.btn_yes:
                if(!cb_term.isChecked()){
                    Util.toastMsg("请勾选康宝莱用户使用协议");
                    return;
                }
                dialogShow();
                final String str2 = getResources().getString(R.string.Has_agreed_to);
                final String str3 = getResources().getString(R.string.Agree_with_failure);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            EMClient.getInstance().groupManager().acceptInvitation(String.valueOf(show.getClassHxGroupId()), String.valueOf(show.getClassMasterHxId()));
                            //莱后台请求
                            service.makeSureJoin(UserInfoModel.getInstance().getToken(),
                                    msgId,
                                    1,
                                    introducerId,
                                    new RequestCallback<ResponseData>() {
                                        @Override
                                        public void success(final ResponseData responseData, Response response) {
                                            dialogDissmiss();
                                            if (responseData.getStatus() == 200) {
                                                ClassModel model = new ClassModel();
                                                model.setClassId(show.getClassId());
                                                model.setClassName(show.getClassName());
                                                model.setClassCode(show.getClassCode());
                                                model.setHXGroupId(show.getClassHxGroupId());
                                                model.setClassRole(show.getClassRole());
                                                model.setClassMasterName(show.getClassMasterName());
                                                model.setClassStatus(show.getClassStatus());
                                                EventBus.getDefault().post(new UpdateClass(1, model));
                                                setResult(RESULT_OK);
                                                finish();
                                                (MessageConfirmActivity.this).runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Util.toastMsg(str2);
                                                    }
                                                });
                                            } else {// 此时需要环信剔除处理
                                                (MessageConfirmActivity.this).runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Util.toastMsg(str3 + responseData.getMsg());
                                                    }
                                                });
                                                try {
//                                                    EMClient.getInstance().groupManager().removeUserFromGroup(String.valueOf(show.getClassHxGroupId()), String.valueOf(show.getClassMasterHxId()));//需异步处理
                                                    EMClient.getInstance().groupManager().leaveGroup(String.valueOf(show.getClassHxGroupId()));//需异步处理
                                                } catch (HyphenateException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }

                                        @Override
                                        public void failure(final RetrofitError error) {
                                            dialogDissmiss();
                                            MessageConfirmActivity.this.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Util.toastMsg(str3 + error.getMessage());
                                                }
                                            });
                                            super.failure(error);
                                        }
                                    });


                        } catch (final HyphenateException e) {
                            e.printStackTrace();
                            MessageConfirmActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Util.toastMsg(str3 + e.getMessage());
                                }
                            });
                        } finally {
                            dialogDissmiss();
                        }
                    }
                }).start();


//                service.makeSureJoin(UserInfoModel.getInstance().getToken(),
//                        msgId,
//                        1,
//                        introducerId,
//                        new RequestCallback<ResponseData>() {
//                            @Override
//                            public void success(ResponseData responseData, Response response) {
//
//                                if (responseData.getStatus() == 200) {
//                                    //换信加入群 show
////                                    EMClient.getInstance().groupManager().joinGroup(groupid);
////                                    EMClient.getInstance().groupManager().addUsersToGroup(show.getClassHxGroupId(), show.getClassMasterHxId());
//                                    try {
//                                        Log.i(TAG, "ClassHxGroupId = " + show.getClassHxGroupId() + "  show.getClassMasterHxId() = " + String.valueOf(show.getClassMasterHxId()));
////                                        EMClient.getInstance().groupManager().joinGroup(String.valueOf(show.getClassHxGroupId()));
////                                        EMClient.getInstance().groupManager().acceptInvitation(String.valueOf(show.getClassHxGroupId()), String.valueOf(show.getClassMasterHxId()));
//                                        //需要申请和验证才能加入的，即group.isMembersOnly()为true，调用下面方法
////                                        EMClient.getInstance().groupManager().applyJoinToGroup(show.getClassHxGroupId(), "求加入");//需异步处理
////                                        EMClient.getInstance().groupManager().acceptInvitation(String.valueOf(show.getClassHxGroupId()), String.valueOf(show.getClassMasterHxId()));
//
//                                        EMClient.getInstance().groupManager().acceptInvitation(String.valueOf(show.getClassHxGroupId()), String.valueOf(show.getClassMasterHxId()));
//                                        setResult(RESULT_OK);
//                                        finish();
//                                    } catch (HyphenateException e) {
//                                        Util.toastMsg("同意失败:" + e.getMessage());
//                                        e.printStackTrace();
//                                    }finally {
//                                        dialogDissmiss();
//                                    }
//                                }else {
//                                    dialogDissmiss();
//                                }
//                            }
//
//                            @Override
//                            public void failure(RetrofitError error) {
//                                dialogDissmiss();
//                                super.failure(error);
//                            }
//                        });
                break;
            case R.id.btn_no:
                dialogShow();
                service.makeSureJoin(UserInfoModel.getInstance().getToken(),
                        msgId,
                        -1,
                        introducerId,
                        new RequestCallback<ResponseData>() {
                            @Override
                            public void success(ResponseData responseData, Response response) {
                                dialogDissmiss();
                                //确认成
                                setResult(RESULT_OK);
                                finish();
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                dialogDissmiss();
                                super.failure(error);
                            }
                        });
                break;
            case R.id.rl_aixin:
                startActivityForResult(new Intent(this, EditorPhoneActivity.class), 100);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == 100) {
            introducerId = data.getLongExtra("accountId", 0);
        }
    }
}