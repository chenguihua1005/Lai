package com.softtek.lai.module.bodygame3.more.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.github.snowdream.android.util.Log;
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

    @InjectView(R.id.rl_choose_type)
    RelativeLayout mChooseTypeContent;
    @InjectView(R.id.tv_choose_type)
    TextView mChooseType;


    SendInvitation invitation;
    ClassInvitater classInvitater;

    private String inviterHXId;

    private int Target = -1;//0减重1增重

    @InjectView(R.id.tip_tv)
    TextView tip_tv;

    @Override
    protected void initViews() {
        tv_title.setText("邀请小伙伴");
        ll_left.setOnClickListener(this);

        SpannableString spannableString = new SpannableString(this.getResources().getString(R.string.tip));
        Drawable drawable = getResources().getDrawable(R.drawable.law_tip);
        drawable.setBounds(0, 0, 50, 50);
        spannableString.setSpan(new ImageSpan(drawable), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tip_tv.setText(spannableString);
        mChooseTypeContent.setOnClickListener(this);
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
        invitation.setTarget(-1);
        dialogShow();
        ZillaApi.NormalRestAdapter.create(MoreService.class)
                .getClassInfoForInvite(classId, UserInfoModel.getInstance().getToken(),
                        classId, UserInfoModel.getInstance().getUserId(), invitaterId,
                        new RequestCallback<ResponseData<ClassInvitater>>() {
                            @Override
                            public void success(ResponseData<ClassInvitater> data, Response response) {
                                dialogDissmiss();
                                Log.i("数据=" + data);
                                if (data.getStatus() == 200) {
                                    try {
                                        onResult(data.getData());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                dialogDissmiss();
                                super.failure(error);
                            }
                        });

    }

    private void onResult(ClassInvitater invitater) {
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
        tv_create_time.setText(invitater.getStartDate());
        tv_number.setText(invitater.getClassCode());
        if (invitater.getIsCurrentClassMember() == 1) {//在当前班级
            tv_group_name.setText(invitater.getCurrentClassGroup());
            tv_group_name.setCompoundDrawables(null, null, null, null);
            tv_role.setText(invitater.getCurrentClassRole());
            tv_role.setCompoundDrawables(null, null, null, null);
            mChooseType.setCompoundDrawables(null, null, null, null);
            tv_invitation.setEnabled(false);
            tv_invitation.setText("小伙伴已经加入该班级");
            tv_invitation.setVisibility(View.VISIBLE);

            mChooseTypeContent.setEnabled(false);//参赛目标禁用
        } else if (invitater.getIsCurrentClassMember() == 0) {//不再当前班级
            tv_invitation.setEnabled(true);
            tv_invitation.setOnClickListener(this);
            tv_invitation.setVisibility(View.VISIBLE);
            tv_invitation.setText("给小伙伴发送邀请吧");
            tv_group_name.setCompoundDrawables(null, null, ContextCompat.getDrawable(this, R.drawable.bodygame3_arrow), null);
            tv_role.setCompoundDrawables(null, null, ContextCompat.getDrawable(this, R.drawable.bodygame3_arrow), null);
            mChooseType.setCompoundDrawables(null, null, ContextCompat.getDrawable(this, R.drawable.bodygame3_arrow), null);
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
                Intent intent = new Intent(InvitationSettingActivity.this, InvitationListActivity.class);
                intent.putExtra("classId", invitation.getClassId());
                dialogDissmiss();
                startActivity(intent);
                finish();
                break;
            case R.id.rl_group:
                showGroupName(true, new EasyAdapter<ClassGroup>(this, classGroupList, android.R.layout.simple_list_item_single_choice) {
                    @Override
                    public void convert(ViewHolder holder, ClassGroup data, int position) {
                        CheckedTextView tv = holder.getView(android.R.id.text1);
                        tv.setText(data.getCGName());
                    }
                });
                break;
            case R.id.rl_role:
                showGroupName(false, new EasyAdapter<ClassRole>(this, classRole, android.R.layout.simple_list_item_single_choice) {
                    @Override
                    public void convert(ViewHolder holder, ClassRole data, int position) {
                        CheckedTextView tv = holder.getView(android.R.id.text1);
                        tv.setText(data.getRoleName());
                        SpannableString ss = null;
                        if ("助教".equals(data.getRoleName())) {
                            tv.append("\n");
                            ss = new SpannableString("(线下体管赛班级中的助教及复测，摄影等工作人员)");
                            ss.setSpan(new AbsoluteSizeSpan(12, true), 0, ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        } else if ("教练".equals(data.getRoleName())) {
                            tv.append("\n");
                            ss = new SpannableString("(线下体管赛班级中的小组长)");
                            ss.setSpan(new AbsoluteSizeSpan(12, true), 0, ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        } else if ("学员".equals(data.getRoleName())) {
                            tv.append("\n");
                            ss = new SpannableString("(线下体管赛班级中的学员)");
                            ss.setSpan(new AbsoluteSizeSpan(12, true), 0, ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        }
                        if (ss != null) {
                            tv.append(ss);
                        }
                    }
                });
                break;
            case R.id.tv_invitation:
                if (TextUtils.isEmpty(invitation.getClassGroupId())) {
                    Util.toastMsg("请为用户分配小组");
                    return;
                } else if (invitation.getClassRole() == 0) {
                    Util.toastMsg("请为用户分配角色");
                    return;
                } else if (invitation.getTarget() == -1) {
                    Util.toastMsg("请选择参赛目标");
                    return;
                } else {
                    dialogShow(getResources().getString(R.string.Is_sending_a_request));
                    ZillaApi.NormalRestAdapter.create(MoreService.class)
                            .sendInviter(UserInfoModel.getInstance().getToken(),
                                    invitation,
                                    new RequestCallback<ResponseData>() {
                                        @Override
                                        public void success(final ResponseData responseData, final Response response) {
                                            if (responseData.getStatus() == 200) {
                                                Util.toastMsg("成功发送邀请！");
//                                                InvitatedContact contact = new InvitatedContact();
//                                                contact.setClassRole(invitation.getClassRole());
//                                                contact.setInviterCertification("");
//                                                contact.setInviterId((int) invitation.getInviterId());
//                                                contact.setInviterMobile(classInvitater.getInviterMobile());
//                                                contact.setInviterPhoto(classInvitater.getInviterPhoto());
//                                                contact.setInviterStatus(0);
//                                                contact.setMessageId("0");
//                                                contact.setInviterUserName(classInvitater.getInviterName());
//                                                contact.setJoinGroupId(invitation.getClassGroupId());
//                                                contact.setJoinGroupName(tv_group_name.getText().toString());
//                                                contact.setTarget(invitation.getTarget());
                                                Intent intent = new Intent(InvitationSettingActivity.this, InvitationListActivity.class);
//                                                intent.putExtra("invitater", contact);
                                                intent.putExtra("classId", invitation.getClassId());
                                                dialogDissmiss();
                                                startActivity(intent);
                                                finish();

                                            } else {
                                                dialogDissmiss();
                                                Util.toastMsg(responseData.getMsg());
                                            }
                                        }

                                        @Override
                                        public void failure(RetrofitError error) {
                                            super.failure(error);
                                            dialogDissmiss();
                                            Util.toastMsg("邀请发送失败！");
                                        }
                                    });
                }
                break;
            case R.id.rl_choose_type:
                showEntryGoalDialog();
                break;
        }
    }


    private Dialog entryGoalDialog;

    private void showEntryGoalDialog() {
        if (entryGoalDialog == null) {
            entryGoalDialog = new Dialog(this, R.style.custom_dialog);
            entryGoalDialog.setCanceledOnTouchOutside(true);
            Window win = entryGoalDialog.getWindow();
            WindowManager.LayoutParams params = new WindowManager.LayoutParams();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.x = 120;
            params.y = 100;
            assert win != null;
            win.setAttributes(params);
            entryGoalDialog.setContentView(R.layout.entry_goal_dialog);
            final TextView lossWeight = entryGoalDialog.findViewById(R.id.tv_loss_weight);
            final TextView addWeight = entryGoalDialog.findViewById(R.id.tv_add_weight);
            lossWeight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lossWeight.setTextColor(getResources().getColor(R.color.mytoolbar_green));
                    addWeight.setTextColor(getResources().getColor(R.color.word));
                    mChooseType.setText("减重");
                    invitation.setTarget(0);
                    dialogDismiss();
                }
            });
            addWeight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addWeight.setTextColor(getResources().getColor(R.color.mytoolbar_green));
                    lossWeight.setTextColor(getResources().getColor(R.color.word));
                    mChooseType.setText("增重");
                    invitation.setTarget(1);
                    dialogDismiss();
                }
            });
            entryGoalDialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogDismiss();
                }
            });
            entryGoalDialog.findViewById(R.id.space).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogDismiss();
                }
            });
        }
        entryGoalDialog.show();
    }

    private void dialogDismiss() {
        if (entryGoalDialog != null && entryGoalDialog.isShowing()) {
            entryGoalDialog.dismiss();
        }
    }

    BottomSheetDialog dialog;
    int checkedGroup;
    int checkedRole;

    private void showGroupName(final boolean isGroup, EasyAdapter adapter) {
        View view = LayoutInflater.from(this).inflate(R.layout.pop_trans_view, null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv);
        tv_title.setText(isGroup ? "选择小组" : "选择角色");
        final ListView lv = (ListView) view.findViewById(R.id.lv);
        View footer = LayoutInflater.from(this).inflate(R.layout.trans_group_footer, null);
        lv.addFooterView(footer);
        lv.setAdapter(adapter);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv.setItemChecked(isGroup ? checkedGroup : checkedRole, true);
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
        TextView tv_ok = (TextView) footer.findViewById(R.id.tv_ok);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int checkedPosition = lv.getCheckedItemPosition();
                if (checkedPosition > -1) {
                    if (isGroup) {
                        checkedGroup = checkedPosition;
                        if (checkedPosition < classGroupList.size()) {
                            ClassGroup group = classGroupList.get(checkedPosition);
                            tv_group_name.setText(group.getCGName());
                            invitation.setClassGroupId(group.getCGId());
                        }
                    } else {
                        checkedRole = checkedPosition;
                        if (checkedPosition < classRole.size()) {
                            ClassRole role = classRole.get(checkedPosition);
                            tv_role.setText(role.getRoleName());
                            invitation.setClassRole(role.getRoleId());
                        }
                    }
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                }
            }
        });
        TextView tv_cancel = (TextView) footer.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);
        dialog.show();

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                dialog = null;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(InvitationSettingActivity.this, InvitationListActivity.class);
        intent.putExtra("classId", invitation.getClassId());
        dialogDissmiss();
        startActivity(intent);
        finish();
    }
}
