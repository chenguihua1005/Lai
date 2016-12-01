/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.message2.view;


import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.message2.model.InvitationConfirmShow;
import com.softtek.lai.module.message2.net.Message2Service;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

/**
 * Created by jarvis.liu on 3/22/2016.
 *
 */
@InjectLayout(R.layout.activity_message_confirm)
public class MessageConfirmActivity extends BaseActivity implements View.OnClickListener{

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
    @InjectView(R.id.cb_zqs)
    CheckBox cb_zqs;
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
    }

    @Override
    protected void initDatas() {
        msgId=getIntent().getStringExtra("msgId");
        service=ZillaApi.NormalRestAdapter.create(Message2Service.class);
        service.getInvitationDetail(UserInfoModel.getInstance().getToken(),
                        msgId,
                        new RequestCallback<ResponseData<InvitationConfirmShow>>() {
                            @Override
                            public void success(ResponseData<InvitationConfirmShow> data, Response response) {
                                if(data.getStatus()==200){
                                    onResult(data.getData());
                                }
                            }
                        });

    }

    public void onResult(InvitationConfirmShow show){
        this.show=show;
        tv_invitater_name.setText(show.getSender());
        tv_head_coach_name.setText(show.getClassMasterName());
        if (TextUtils.isEmpty(show.getClassMasterPhoto())){
            Picasso.with(this).load(R.drawable.img_default).into(head_image);
        }else {
            Picasso.with(this).load(R.drawable.img_default).fit()
                    .error(R.drawable.img_default)
                    .placeholder(R.drawable.img_default).into(head_image);
        }
        tv_class_name.setText(show.getClassName());
        tv_class_code.setText(show.getClassCode());
        tv_first_time.setText(DateUtil.getInstance(DateUtil.yyyy_MM_dd)
                .convertDateStr(show.getClassStart(),"yyyy年MM月dd日"));
        int role=show.getClassRole();
        tv_role_name.setText(role == 1 ? "总教练" : role == 2 ? "教练" : role == 3 ? "助教" : role == 4 ? "学员" : "");
        tv_group_name.setText(show.getCGName());
        btn_no.setEnabled(true);
        btn_yes.setEnabled(true);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.btn_yes:
                dialogShow();
                service.makeSureJoin(UserInfoModel.getInstance().getToken(),
                        msgId,
                        1,
                        introducerId,
                        new RequestCallback<ResponseData>() {
                            @Override
                            public void success(ResponseData responseData, Response response) {
                                dialogDissmiss();
                                if(responseData.getStatus()==200){

                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                dialogDissmiss();
                                super.failure(error);
                            }
                        });
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
                                if(responseData.getStatus()==200){
                                    //确认成功
                                    Util.toastMsg(responseData.getMsg());


                                }else if(responseData.getStatus()==201){
                                    //该用户已经加入班级
                                    Util.toastMsg(responseData.getMsg());
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                dialogDissmiss();
                                super.failure(error);
                            }
                        });
                break;
            case R.id.rl_aixin:
                startActivityForResult(new Intent(this,EditorPhoneActivity.class),100);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==200&&resultCode==100){
            introducerId=data.getLongExtra("accountId",0);
        }
    }
}