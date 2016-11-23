package com.softtek.lai.module.bodygame3.more.view;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.more.model.ClassInvitater;
import com.softtek.lai.module.bodygame3.more.model.SendInvitation;
import com.softtek.lai.module.bodygame3.more.net.MoreService;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.utils.StringUtil;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_invitation_setting)
public class InvitationSettingActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.head_image)
    CircleImageView head_image;
    @InjectView(R.id.tv_name)
    TextView tv_name;
    @InjectView(R.id.tv_tianshi)
    TextView tv_tianshi;
    @InjectView(R.id.tv_class_name)
    TextView tv_class_name;
    @InjectView(R.id.tv_number)
    TextView tv_number;
    @InjectView(R.id.tv_create_time)
    TextView tv_create_time;
    @InjectView(R.id.tv_group_name)
    TextView tv_group_name;
    @InjectView(R.id.tv_role)
    TextView tv_role;

    @InjectView(R.id.rl_group)
    RelativeLayout rl_group;
    @InjectView(R.id.rl_role)
    RelativeLayout rl_role;

    @InjectView(R.id.tv_invitation)
    TextView tv_invitation;

    SendInvitation invitation;
    @Override
    protected void initViews() {
        tv_title.setText("邀请小伙伴");
        ll_left.setOnClickListener(this);
        rl_group.setOnClickListener(this);
        rl_role.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        invitation=new SendInvitation();
        String  classId=getIntent().getStringExtra("classId");
        long invitaterId=getIntent().getLongExtra("inviterId",0);
        invitation.setClassId(classId);
        invitation.setInviterId(invitaterId);
        dialogShow();
        ZillaApi.NormalRestAdapter.create(MoreService.class)
                .getClassInfoForInvite(UserInfoModel.getInstance().getToken(),
                        classId, UserInfoModel.getInstance().getUserId(), invitaterId,
                        new RequestCallback<ResponseData<ClassInvitater>>() {
                            @Override
                            public void success(ResponseData<ClassInvitater> data, Response response) {
                                dialogDissmiss();
                                Log.i("数据="+data);
                                if(data.getStatus()==200){
                                    onResult(data.getData());
                                }else if(data.getStatus()==201){//改用户已经加入班级
                                    tv_invitation.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                dialogDissmiss();
                                super.failure(error);
                            }
                        });
    }

    private void onResult(ClassInvitater invitater){
        if(!TextUtils.isEmpty(invitater.getInviterPhoto())){
            Picasso.with(this).load(AddressManager.get("photoHost")+invitater.getInviterPhoto())
                    .error(R.drawable.img_default).placeholder(R.drawable.img_default).into(head_image);
        }else {
            Picasso.with(this).load(R.drawable.img_default).into(head_image);
        }
        tv_name.setText(StringUtil.showName(invitater.getInviterName(),invitater.getInviterMobile()));
        tv_tianshi.setText("奶昔天使 "+invitater.getInviterMLUserName());
        tv_class_name.setText(invitater.getClassName());
        tv_create_time.setText(DateUtil.getInstance(DateUtil.yyyy_MM_dd).convertDateStr(invitater.getStartDate(),"yyyy年MM月dd日"));
        tv_number.setText(invitater.getClassCode());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_left:
                finish();
                break;
        }
    }
}
