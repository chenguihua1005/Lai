package com.softtek.lai.module.bodygame3.more.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.github.snowdream.android.util.Log;
import com.hyphenate.chat.EMClient;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.more.model.ClassGroup;
import com.softtek.lai.module.bodygame3.more.model.ClassInvitater;
import com.softtek.lai.module.bodygame3.more.model.ClassRole;
import com.softtek.lai.module.bodygame3.more.model.InvitatedContact;
import com.softtek.lai.module.bodygame3.more.model.SendInvitation;
import com.softtek.lai.module.bodygame3.more.net.MoreService;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.utils.StringUtil;
import com.softtek.lai.widgets.BottomSheetDialog;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_invitation_setting)
public class InvitationSettingActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "InvitationSettingActivity";

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
    ClassInvitater classInvitater;

    private String inviterHXId;

    @Override
    protected void initViews() {
        tv_title.setText("邀请小伙伴");
        ll_left.setOnClickListener(this);
        tv_invitation.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        invitation = new SendInvitation();
        invitation.setSenderId(UserInfoModel.getInstance().getUserId());
        String classId = getIntent().getStringExtra("classId");
        long invitaterId = getIntent().getLongExtra("inviterId", 0);
        inviterHXId = getIntent().getStringExtra("inviterHXId");//被邀请人的环信ID


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
                                Log.i("数据=" + data);
                                if (data.getStatus() == 200 || data.getStatus() == 201) {
                                    onResult(data.getData(), data.getStatus());
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                dialogDissmiss();
                                super.failure(error);
                            }
                        });
    }

    private void onResult(ClassInvitater invitater, int status) {
        this.classInvitater = invitater;
        invitater.setClassId(invitater.getClassId());
        if (!TextUtils.isEmpty(invitater.getInviterPhoto())) {
            Picasso.with(this).load(AddressManager.get("photoHost") + invitater.getInviterPhoto()).fit()
                    .error(R.drawable.img_default).placeholder(R.drawable.img_default).into(head_image);
        } else {
            Picasso.with(this).load(R.drawable.img_default).into(head_image);
        }
        tv_name.setText(StringUtil.showName(invitater.getInviterName(), invitater.getInviterMobile()));
        if (TextUtils.isEmpty(invitater.getInviterMLUserName())) {
            tv_tianshi.setText("暂无奶昔天使");
        } else {
            tv_tianshi.setText("奶昔天使 " + invitater.getInviterMLUserName());
        }
        tv_class_name.setText(invitater.getClassName());
        tv_create_time.setText(DateUtil.getInstance(DateUtil.yyyy_MM_dd).convertDateStr(invitater.getStartDate(), "yyyy年MM月dd日"));
        tv_number.setText(invitater.getClassCode());
        ClassGroup group = invitater.getClassGroupList().get(0);
        ClassRole role = invitater.getClassRole().get(0);
        if (status == 201) {
            tv_group_name.setText(group.getCGName());
            tv_group_name.setCompoundDrawables(null, null, null, null);
            this.invitation.setClassGroupId(group.getCGId());
            tv_role.setText(role.getRoleName());
            this.invitation.setClassRole(role.getRoleId());
            tv_role.setCompoundDrawables(null, null, null, null);
            tv_invitation.setVisibility(View.GONE);
        } else {

            tv_group_name.setText(group.getCGName());
            tv_role.setText(role.getRoleName());
            this.invitation.setClassGroupId(group.getCGId());
            this.invitation.setClassRole(role.getRoleId());
            tv_invitation.setVisibility(View.VISIBLE);
            rl_group.setOnClickListener(this);
            rl_role.setOnClickListener(this);
        }
        classGroupList = invitater.getClassGroupList();
        classRole = invitater.getClassRole();

    }

    private List<ClassGroup> classGroupList;
    private List<ClassRole> classRole;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.rl_group:
                showGroupName(true, new EasyAdapter<ClassGroup>(this, classGroupList, R.layout.textview) {

                    @Override
                    public void convert(ViewHolder holder, ClassGroup data, int position) {
                        TextView tv = holder.getView(R.id.tv);
                        tv.setText(data.getCGName());
                    }
                });
                break;
            case R.id.rl_role:
                showGroupName(false, new EasyAdapter<ClassRole>(this, classRole, R.layout.textview) {

                    @Override
                    public void convert(ViewHolder holder, ClassRole data, int position) {
                        TextView tv = holder.getView(R.id.tv);
                        tv.setText(data.getRoleName());
                    }
                });
                break;
            case R.id.tv_invitation:
                if (TextUtils.isEmpty(inviterHXId)) {
                    Util.toastMsg("无法邀请此用户！");
                    return;
                } else {
                    dialogShow(getResources().getString(R.string.Is_sending_a_request));
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String[] inviterHXIds = {inviterHXId};

                            try {//群主加人调用此方法
                                Log.i(TAG, "邀请信息 GroupHxId = " + classInvitater.getClassGroupHxId() + "  inviterHXId =" + inviterHXId);
                                EMClient.getInstance().groupManager().addUsersToGroup(classInvitater.getClassGroupHxId(), inviterHXIds);
//                          EMClient.getInstance().groupManager().inviteUser(classInvitater.getClassGroupHxId(), inviterHXIds, null);//需异步处理

                                ZillaApi.NormalRestAdapter.create(MoreService.class)
                                        .sendInviter(UserInfoModel.getInstance().getToken(),
                                                invitation,
                                                new RequestCallback<ResponseData>() {
                                                    @Override
                                                    public void success(final ResponseData responseData, final Response response) {
                                                        dialogDissmiss();
                                                        if (responseData.getStatus() == 200) {
                                                            runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    dialogDissmiss();
                                                                    Util.toastMsg("成功发送邀请！");

                                                                }
                                                            });

                                                            InvitatedContact contact = new InvitatedContact();
                                                            contact.setClassRole(invitation.getClassRole());
                                                            contact.setInviterCertification("");
                                                            contact.setInviterId((int) invitation.getInviterId());
                                                            contact.setInviterMobile(classInvitater.getInviterMobile());
                                                            contact.setInviterPhoto(classInvitater.getInviterPhoto());
                                                            contact.setInviterStatus(0);
                                                            contact.setMessageId("0");
                                                            contact.setInviterUserName(classInvitater.getInviterName());
                                                            contact.setJoinGroupId(invitation.getClassGroupId());
                                                            contact.setJoinGroupName(tv_group_name.getText().toString());
                                                            Intent intent = new Intent(InvitationSettingActivity.this, InvitationListActivity.class);
                                                            intent.putExtra("invitater", contact);
                                                            startActivity(intent);
                                                        } else {
                                                            runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    dialogDissmiss();
                                                                    Util.toastMsg(responseData.getMsg());
                                                                }
                                                            });

                                                        }
                                                    }

                                                    @Override
                                                    public void failure(RetrofitError error) {
                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                dialogDissmiss();
                                                                Util.toastMsg(getResources().getString(R.string.Add_group_members_fail));

                                                            }
                                                        });
                                                        super.failure(error);
                                                    }
                                                });

                                //莱后台请求
                            } catch (final Exception e) {
                                e.printStackTrace();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        dialogDissmiss();
                                        Util.toastMsg(getResources().getString(R.string.Add_group_members_fail) + e.getMessage());

                                    }
                                });
                            }
                        }
                    }).start();
                }
//                ZillaApi.NormalRestAdapter.create(MoreService.class)
//                        .sendInviter(UserInfoModel.getInstance().getToken(),
//                                invitation,
//                                new RequestCallback<ResponseData>() {
//                                    @Override
//                                    public void success(ResponseData responseData, Response response) {
//                                        dialogDissmiss();
//                                        if (responseData.getStatus() == 200) {
//
//                                            //环信发申请
//                                            //需要申请和验证才能加入的，即group.isMembersOnly()为true，调用下面方法
//                                            Log.i(TAG, "邀请信息 GroupHxId = " + classInvitater.getClassGroupHxId() + "  inviterHXId =" + inviterHXId);
//
//
//                                            new Thread(new Runnable() {
//                                                @Override
//                                                public void run() {
//                                                    String[] inviterHXIds = {inviterHXId};
////                                                    EMClient.getInstance().groupManager().applyJoinToGroup(classInvitater.getClassGroupHxId(), "求加入");//需异步处理
//                                                    try {//群主加人调用此方法
//                                                        EMClient.getInstance().groupManager().addUsersToGroup(classInvitater.getClassGroupHxId(), inviterHXIds);
////                                                        EMClient.getInstance().groupManager().inviteUser(classInvitater.getClassGroupHxId(), inviterHXIds, null);//需异步处理
//                                                    } catch (HyphenateException e) {
//                                                        e.printStackTrace();
//                                                    }
//                                                }
//                                            }).start();
//
//
//                                            //邀请成功
//                                            InvitatedContact contact = new InvitatedContact();
//                                            contact.setClassRole(invitation.getClassRole());
//                                            contact.setInviterCertification("");
//                                            contact.setInviterId((int) invitation.getInviterId());
//                                            contact.setInviterMobile(classInvitater.getInviterMobile());
//                                            contact.setInviterPhoto(classInvitater.getInviterPhoto());
//                                            contact.setInviterStatus(0);
//                                            contact.setMessageId("0");
//                                            contact.setInviterUserName(classInvitater.getInviterName());
//                                            contact.setJoinGroupId(invitation.getClassGroupId());
//                                            contact.setJoinGroupName(tv_group_name.getText().toString());
//                                            Intent intent = new Intent(InvitationSettingActivity.this, InvitationListActivity.class);
//                                            intent.putExtra("invitater", contact);
//                                            startActivity(intent);
//                                        }
//                                        Util.toastMsg(responseData.getMsg());
//                                    }
//
//                                    @Override
//                                    public void failure(RetrofitError error) {
//                                        dialogDissmiss();
//                                        super.failure(error);
//                                    }
//                                });
                break;
        }
    }

    BottomSheetDialog dialog;

    private void showGroupName(final boolean isGroup, EasyAdapter adapter) {
        final ListView lv = new ListView(this);
        lv.setDivider(new ColorDrawable(0xFFE1E1E1));
        lv.setDividerHeight(1);
        lv.setAdapter(adapter);
        TextView title = new TextView(this);
        title.setText(isGroup ? "选择组" : "选择角色");
        title.setTextColor(0xFF999999);
        title.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 5, getResources().getDisplayMetrics()));
        title.setWidth(DisplayUtil.getMobileWidth(this));
        title.setHeight(DisplayUtil.dip2px(this, 40));
        title.setPadding(DisplayUtil.dip2px(this, 15), 0, 0, 0);
        title.setGravity(Gravity.CENTER | Gravity.LEFT);
        lv.addHeaderView(title);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (isGroup) {
                    ClassGroup group = classGroupList.get(i - 1);
                    tv_group_name.setText(group.getCGName());
                    invitation.setClassGroupId(group.getCGId());
                } else {
                    ClassRole role = classRole.get(i - 1);
                    tv_role.setText(role.getRoleName());
                    invitation.setClassRole(role.getRoleId());
                }
                dialog.dismiss();
            }
        });
        lv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (lv.getFirstVisiblePosition() != 0) {
                            lv.getParent().requestDisallowInterceptTouchEvent(true);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return false;
            }
        });
        dialog = new BottomSheetDialog(this);
        dialog.setContentView(lv);
        dialog.show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                dialog = null;
            }
        });
    }

}
