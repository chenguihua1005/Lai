package com.softtek.lai.module.message2.view;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.google.gson.Gson;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.more.model.ClassGroup;
import com.softtek.lai.module.bodygame3.more.model.ClassRole;
import com.softtek.lai.module.message2.model.ApplyConfirm;
import com.softtek.lai.module.message2.model.ApplyModel;
import com.softtek.lai.module.message2.net.Message2Service;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.BottomSheetDialog;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_examine)
public class ExamineActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "ExamineActivity";

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.head_image)
    CircleImageView head_image;
    @InjectView(R.id.tv_apply_name)
    TextView tv_apply_name;
    @InjectView(R.id.tv_quality)
    TextView tv_quality;
    @InjectView(R.id.tv_phone)
    TextView tv_phone;
    @InjectView(R.id.tv_tianshi)
    TextView tv_tianshi;
    @InjectView(R.id.rl_group)
    RelativeLayout rl_group;
    @InjectView(R.id.rl_role)
    RelativeLayout rl_role;
    @InjectView(R.id.tv_group_name)
    TextView tv_group_name;
    @InjectView(R.id.tv_role_name)
    TextView tv_role_name;
    @InjectView(R.id.tv_class_name)
    TextView tv_class_name;
    @InjectView(R.id.tv_class_code)
    TextView tv_class_code;

    @InjectView(R.id.btn_yes)
    Button btn_yes;
    @InjectView(R.id.btn_no)
    Button btn_no;
    @InjectView(R.id.rl_choose_type)
    RelativeLayout mChooseTypeContent;
    @InjectView(R.id.tv_choose_type)
    TextView mChooseType;

    Message2Service service;
    String msgId;

    ApplyConfirm confirm;
    ApplyModel model;

    @Override
    protected void initViews() {
        tv_title.setText("参赛申请");
        ll_left.setOnClickListener(this);
        rl_group.setOnClickListener(this);
        rl_role.setOnClickListener(this);
        btn_no.setOnClickListener(this);
        btn_yes.setOnClickListener(this);
        mChooseTypeContent.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        msgId = getIntent().getStringExtra("msgId");
        service = ZillaApi.NormalRestAdapter.create(Message2Service.class);
        dialogShow("获取数据中");
        service.getShenQingJoinInfo(UserInfoModel.getInstance().getToken(),
                msgId,
                new RequestCallback<ResponseData<ApplyConfirm>>() {
                    @Override
                    public void success(ResponseData<ApplyConfirm> data, Response response) {
                        dialogDissmiss();
                        if (data.getStatus() == 200) {
                            try {
                                onResult(data.getData());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Util.toastMsg(data.getMsg());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        dialogDissmiss();
                        super.failure(error);
                    }
                });
    }

    public void onResult(ApplyConfirm apply) {
        this.confirm = apply;
        com.github.snowdream.android.util.Log.i("申请确认展示信息" + apply.toString());
        this.model = new ApplyModel();
        model.accountId = apply.getApplyId();//申请人Id
        model.applyId = msgId;//消息id
        model.classId = apply.getClassId();//班级Id
        model.reviewerId = UserInfoModel.getInstance().getUserId();//审核人Id
        tv_apply_name.setText(apply.getApplyName());
        if (!TextUtils.isEmpty(apply.getApplyPhoto())) {
            int px = DisplayUtil.dip2px(this, 30);
            Picasso.with(this).load(AddressManager.get("photoHost") + apply.getApplyPhoto())
                    .resize(px, px)
                    .centerCrop()
                    .placeholder(R.drawable.img_default)
                    .error(R.drawable.img_default)
                    .into(head_image);
        }
        tv_phone.setText(apply.getApplyMobile());
        tv_quality.setText(TextUtils.isEmpty(apply.getApplyCert()) ? "未认证" : apply.getApplyCert());
        tv_tianshi.setText(TextUtils.isEmpty(apply.getApplyMLName()) ? "暂无奶昔天使" : apply.getApplyMLName());
        tv_class_code.setText(apply.getClassCode());
        tv_class_name.setText(apply.getClassName());
        if (apply.getClassGroups() != null) {
            classGroupList.clear();
            classGroupList.addAll(apply.getClassGroups());
        }
        if (apply.getClassRoles() != null) {
            classRole.clear();
            classRole.addAll(apply.getClassRoles());
        }
        if (apply.getMsgStatus() == 0) {
            btn_no.setVisibility(View.VISIBLE);
            btn_yes.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(apply.getClassGroupName())) {
                tv_group_name.setText(apply.getClassGroupName());
                model.groupId = apply.getClassGroupId();
            }
            if (!TextUtils.isEmpty(apply.getClassRoleName())) {
                tv_role_name.setText(apply.getClassRoleName());
                model.classRole = apply.getClassRoleValue();
            }
            int target = apply.getTarget();
            model.Target = apply.getTarget();
            if (1 == target) {//增重
                mChooseType.setText("增重");
            } else {
                mChooseType.setText("减重");
            }

            tv_group_name.setCompoundDrawables(null, null, ContextCompat.getDrawable(this, R.drawable.bodygame3_arrow), null);
            tv_role_name.setCompoundDrawables(null, null, ContextCompat.getDrawable(this, R.drawable.bodygame3_arrow), null);
            mChooseType.setCompoundDrawables(null, null, ContextCompat.getDrawable(this, R.drawable.bodygame3_arrow), null);
        } else {
            btn_no.setVisibility(View.GONE);
            btn_yes.setVisibility(View.GONE);
            //已经处理过的数据
            tv_group_name.setText(apply.getClassGroupName());
            tv_role_name.setText(apply.getClassRoleName());
            tv_group_name.setCompoundDrawables(null, null, null, null);
            tv_role_name.setCompoundDrawables(null, null, null, null);

            int Target = apply.getTarget();
            if (0 == Target) {
                mChooseType.setText("减重");
            } else {
                mChooseType.setText("增重");
            }
            mChooseType.setCompoundDrawables(null, null, null, null);
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
                            model.groupId = group.getCGId();
                        }
                    } else {
                        checkedRole = checkedPosition;
                        if (checkedPosition < classRole.size()) {
                            ClassRole role = classRole.get(checkedPosition);
                            tv_role_name.setText(role.getRoleName());
                            model.classRole = role.getRoleId();
                        }
                    }
                }
                dialog.dismiss();
            }
        });
        TextView tv_cancel = (TextView) footer.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
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


    private List<ClassGroup> classGroupList = new ArrayList<>();
    private List<ClassRole> classRole = new ArrayList<>();

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.rl_group:
                if (confirm == null || confirm.getMsgStatus() != 0) {
                    return;
                }
                showGroupName(true, new EasyAdapter<ClassGroup>(this, classGroupList, android.R.layout.simple_list_item_single_choice) {
                    @Override
                    public void convert(ViewHolder holder, ClassGroup data, int position) {
                        CheckedTextView tv = holder.getView(android.R.id.text1);
                        tv.setText(data.getCGName());
                    }
                });
                break;
            case R.id.rl_role:
                //如果此条消息一经操作过就不能在操作了
                if (confirm == null || confirm.getMsgStatus() != 0) {
                    return;
                }
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
            case R.id.btn_no:
                //忽略
                dialogShow("审批忽略");
                model.status = 2;
                ZillaApi.NormalRestAdapter.create(Message2Service.class)
                        .examine(UserInfoModel.getInstance().getToken(),
                                model,
                                new RequestCallback<ResponseData>() {
                                    @Override
                                    public void success(ResponseData responseData, Response response) {
                                        dialogDissmiss();
                                        int status = responseData.getStatus();
                                        if (200 == status) {
                                            Intent intent = getIntent();
                                            intent.putExtra("msgStatus", 2);
                                            setResult(RESULT_OK, intent);
                                            finish();
                                        } else {
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
            case R.id.btn_yes:
                if (TextUtils.isEmpty(this.model.groupId)) {
                    Util.toastMsg("请为用户分配小组");
                    return;
                } else if (this.model.classRole == 0) {
                    Util.toastMsg("请为用户分配角色");
                    return;
                }

                //确定
                dialogShow("审批确认");
                model.status = 1;

                Log.i(TAG, "token = " + UserInfoModel.getInstance().getToken());
                Log.i(TAG, "model = " + new Gson().toJson(model));

                ZillaApi.NormalRestAdapter.create(Message2Service.class)
                        .examine(UserInfoModel.getInstance().getToken(),
                                model,
                                new RequestCallback<ResponseData>() {
                                    @Override
                                    public void success(final ResponseData responseData, Response response) {
                                        int status = responseData.getStatus();
                                        if (status == 200) {
                                            dialogDissmiss();
                                            Util.toastMsg("加入成功");
                                            Intent intent = getIntent();
                                            intent.putExtra("msgStatus", 1);
                                            setResult(RESULT_OK, intent);
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
                                    }
                                });
                break;
            case R.id.rl_choose_type: {
                //如果此条消息一经操作过就不能在操作了
                if (confirm == null || confirm.getMsgStatus() != 0) {
                    return;
                }
                showEntryGoalDialog();
            }
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
                    dialogDismiss();
                }
            });
            addWeight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addWeight.setTextColor(getResources().getColor(R.color.mytoolbar_green));
                    lossWeight.setTextColor(getResources().getColor(R.color.word));
                    mChooseType.setText("增重");
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
}
