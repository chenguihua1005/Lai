package com.softtek.lai.module.message2.view;


import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
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
                            onResult(data.getData());
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
        this.model = new ApplyModel();
        model.accountId = apply.getApplyId();//申请人Id
        model.applyId = msgId;//消息id
        model.classId = apply.getClassId();//班级Id
        model.reviewerId = UserInfoModel.getInstance().getUserId();//审核人Id
        tv_apply_name.setText(apply.getApplyName());
        if (TextUtils.isEmpty(apply.getApplyCert())) {
            Picasso.with(this).load(R.drawable.img_default).into(head_image);
        } else {
            Picasso.with(this).load(AddressManager.get("photoHost")).fit()
                    .error(R.drawable.img_default)
                    .placeholder(R.drawable.img_default).into(head_image);
        }
        tv_phone.setText(apply.getApplyMobile());
        tv_quality.setText(TextUtils.isEmpty(apply.getApplyCert()) ? "未认证" : apply.getApplyCert());
        tv_tianshi.setText(TextUtils.isEmpty(apply.getApplyMLName()) ? "暂无" : apply.getApplyMLName());
        tv_class_code.setText(apply.getClassCode());
        tv_class_name.setText(apply.getClassName());
        classGroupList = apply.getClassGroups();
        classRole = apply.getClassRoles();
        if (apply.getMsgStatus() == 0) {
            btn_no.setVisibility(View.VISIBLE);
            btn_yes.setVisibility(View.VISIBLE);
        } else {
            //已经处理过的数据
            tv_group_name.setText(apply.getClassGroupName());
            tv_role_name.setText(apply.getClassRoleName());
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
                    model.groupId = group.getCGId();
                } else {
                    ClassRole role = classRole.get(i - 1);
                    tv_role_name.setText(role.getRoleName());
                    model.classRole = role.getRoleId();
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

    private List<ClassGroup> classGroupList;
    private List<ClassRole> classRole;

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
                showGroupName(true, new EasyAdapter<ClassGroup>(this, classGroupList, R.layout.textview) {
                    @Override
                    public void convert(ViewHolder holder, ClassGroup data, int position) {
                        TextView tv = holder.getView(R.id.tv);
                        tv.setText(data.getCGName());
                    }
                });
                break;
            case R.id.rl_role:
                //如果此条消息一经操作过就不能在操作了
                if (confirm == null || confirm.getMsgStatus() != 0) {
                    return;
                }
                showGroupName(false, new EasyAdapter<ClassRole>(this, classRole, R.layout.textview) {
                    @Override
                    public void convert(ViewHolder holder, ClassRole data, int position) {
                        TextView tv = holder.getView(R.id.tv);
                        tv.setText(data.getRoleName());
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
            case R.id.btn_yes:
                //确定
                dialogShow("审批确认");
                model.status = 1;

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //群主加人调用此方法
                        try {
                            String hxGroupId = confirm.getClassHxId();
                            String[] newmembers = {confirm.getApplyHxId()};

                            EMClient.getInstance().groupManager().addUsersToGroup(hxGroupId, newmembers);//需异步处理
                            ZillaApi.NormalRestAdapter.create(Message2Service.class)
                                    .examine(UserInfoModel.getInstance().getToken(),
                                            model,
                                            new RequestCallback<ResponseData>() {
                                                @Override
                                                public void success(ResponseData responseData, Response response) {
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            dialogDissmiss();
                                                            Util.toastMsg("加人成功！");
                                                        }
                                                    });

                                                    setResult(RESULT_OK);
                                                    finish();
                                                }

                                                @Override
                                                public void failure(RetrofitError error) {
                                                    super.failure(error);
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            dialogDissmiss();
                                                            Util.toastMsg("加人成功！");
                                                        }
                                                    });

                                                }
                                            });


                        } catch (HyphenateException e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dialogDissmiss();
                                    Util.toastMsg("加人失败！");
                                }
                            });
                        }


                    }
                }).start();
                break;

        }
    }
}
