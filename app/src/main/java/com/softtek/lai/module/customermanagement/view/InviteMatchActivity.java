package com.softtek.lai.module.customermanagement.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.customermanagement.adapter.BottomRecyclerViewAdapter;
import com.softtek.lai.module.customermanagement.model.InviteMatchModel;
import com.softtek.lai.module.customermanagement.service.InviteService;
import com.softtek.lai.utils.RequestCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jia.lu on 12/29/2017.
 */

public class InviteMatchActivity extends MakiBaseActivity implements View.OnClickListener {

    private LinearLayout mBack;
    private TextView mTitle;
    private EditText mLoveStudent;
    private EditText mCode;
    private InviteService service;
    private List<InviteMatchModel> infoDataList;
    private List<String> classNames = new ArrayList<>();
    private List<String> classGroups = new ArrayList<>();
    private List<String> classRoles = new ArrayList<>();
    private BottomSheetDialog bottomDialog;
    private LinearLayout mChooseClass;
    private TextView mClassName;
    private LinearLayout mChooseGroup;
    private TextView mGroupName;
    private LinearLayout mChooseRole;
    private TextView mRoleName;
    private LinearLayout mChooseTarget;
    private TextView mTargetName;
    private TextView mName;
    private BottomRecyclerViewAdapter nameAdapter;
    private BottomRecyclerViewAdapter groupAdapter;
    private BottomRecyclerViewAdapter roleAdapter;
    private String viewText;
    private int classIndex;
    private int groupIndex;
    private int roleIndex;
    private PostData postData = new PostData();

    private Button mGetCode;
    private Button mSubmit;
    private Disposable timer;
    private int timeIndex;
    private String gender;
    private long accountId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_match);
        service = ZillaApi.create(InviteService.class);
        initData();
        initView();
    }

    private void initData(){
        gender = getIntent().getStringExtra("gender");
        accountId = getIntent().getLongExtra("accountId",0);
        service.getListOfClassesWithDetail(UserInfoModel.getInstance().getToken(), new RequestCallback<ResponseData<List<InviteMatchModel>>>() {
            @Override
            public void success(ResponseData<List<InviteMatchModel>> inviteMatchModelResponseData, Response response) {
                if (inviteMatchModelResponseData.getStatus() == 200) {
                    infoDataList = inviteMatchModelResponseData.getData();
                    if (infoDataList == null || infoDataList.size() < 1){
                        return;
                    }
                    for (int i = 0; i < infoDataList.size(); i++) {
                        classNames.add(infoDataList.get(i).getClassName());
                    }
                    viewText = infoDataList.get(0).getClassName();
                } else {
                    Toast.makeText(InviteMatchActivity.this, inviteMatchModelResponseData.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
//                super.failure(error);
                dealNetError(error);
            }
        });
    }

    private void initView(){
        mBack = findViewById(R.id.ll_left);
        mTitle = findViewById(R.id.tv_title);
        mName = findViewById(R.id.tv_name);
        mLoveStudent = findViewById(R.id.edt_love_student);
        mCode = findViewById(R.id.edt_code);
        mChooseClass = findViewById(R.id.ll_choose_class);
        mClassName = findViewById(R.id.tv_class_name);
        mChooseGroup = findViewById(R.id.ll_choose_group);
        mGroupName = findViewById(R.id.tv_group_name);
        mChooseRole = findViewById(R.id.ll_choose_role);
        mRoleName = findViewById(R.id.tv_role_name);
        mChooseTarget = findViewById(R.id.ll_choose_target);
        mTargetName = findViewById(R.id.tv_target_name);
        mGetCode = findViewById(R.id.btn_send_code);
        mSubmit = findViewById(R.id.btn_submit);
        mTitle.setText("参赛邀请");
        mLoveStudent.setText(UserInfoModel.getInstance().getUser().getMobile());
        String nameAndPhone =getIntent().getStringExtra("customName") + "(" +  getIntent().getStringExtra("mobile") + ")";
        mName.setText(nameAndPhone);
        mBack.setOnClickListener(this);
        mLoveStudent.setOnClickListener(this);
        mChooseClass.setOnClickListener(this);
        mChooseGroup.setOnClickListener(this);
        mChooseRole.setOnClickListener(this);
        mChooseTarget.setOnClickListener(this);
        mGetCode.setOnClickListener(this);
        mSubmit.setOnClickListener(this);

        nameAdapter = new BottomRecyclerViewAdapter(classNames, new BottomRecyclerViewAdapter.ItemListener() {
            @Override
            public void onItemClick(String item, int position) {
                classIndex = position;
                viewText = item;
                nameAdapter.setSelectPosition(position);
                nameAdapter.notifyDataSetChanged();
            }
        });

        mCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 6){
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);

                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_left:
                finish();
                break;
            case R.id.ll_choose_class:
                if (classNames.size() < 1) {
                    Toast.makeText(this,"暂无可邀请的班级",Toast.LENGTH_SHORT).show();
                } else {
                    showMakiDialog("班级名称", mClassName, nameAdapter);
                }
                break;
            case R.id.ll_choose_group:
                classGroups.clear();
                if (mClassName.getText().toString().trim().equals("")) {
                    Toast.makeText(InviteMatchActivity.this, "请先选择班级", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < infoDataList.get(classIndex).getClassGroups().size(); i++) {
                        classGroups.add(infoDataList.get(classIndex).getClassGroups().get(i).getCGName());
                    }
                    viewText = classGroups.get(0);
                    groupAdapter = new BottomRecyclerViewAdapter(classGroups, new BottomRecyclerViewAdapter.ItemListener() {
                        @Override
                        public void onItemClick(String item, int position) {
                            groupIndex = position;
                            viewText = item;
                            groupAdapter.setSelectPosition(position);
                            groupAdapter.notifyDataSetChanged();
                        }
                    });
                    showMakiDialog("小组名称", mGroupName, groupAdapter);
                }
                break;
            case R.id.ll_choose_role:
                classRoles.clear();
                if (mGroupName.getText().toString().trim().equals("")) {
                    Toast.makeText(InviteMatchActivity.this, "请选择小组名", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < infoDataList.get(classIndex).getClassRoles().size(); i++) {
                        classRoles.add(infoDataList.get(classIndex).getClassRoles().get(i).getClassRoleName());
                    }
                    viewText = classRoles.get(0);
                    roleAdapter = new BottomRecyclerViewAdapter(classRoles, new BottomRecyclerViewAdapter.ItemListener() {
                        @Override
                        public void onItemClick(String item, int position) {
                            roleIndex = position;
                            viewText = item;
                            roleAdapter.setSelectPosition(position);
                            roleAdapter.notifyDataSetChanged();
                        }
                    });
                    showMakiDialog("角色", mRoleName, roleAdapter);
                }
                break;
            case R.id.ll_choose_target:
                showEntryGoalDialog();
                break;
            case R.id.btn_submit:
                doSubmit();
                break;
            case R.id.btn_send_code:
                getCode();
                break;

        }
    }

    private void getCode() {
        timeIndex = 60;
        service.getCodeFromMobile(UserInfoModel.getInstance().getToken(), getIntent().getStringExtra("mobile"), new RequestCallback<ResponseData<RecCode>>() {
            @Override
            public void success(ResponseData<RecCode> data, Response response) {
                if (data.getStatus() == 200) {
                    Toast.makeText(InviteMatchActivity.this, "短信已经发送", Toast.LENGTH_SHORT).show();
                    mGetCode.setEnabled(false);
                    mGetCode.setTextColor(getResources().getColor(R.color.black));
                    mGetCode.setBackground(getResources().getDrawable(R.drawable.bg_create_group_add2));
                    mGetCode.setText("重新发送(" + timeIndex + ")");
                    timer = Flowable.interval(1, TimeUnit.SECONDS)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<Long>() {
                                @Override
                                public void accept(Long aLong) throws Exception {
                                    if (timeIndex == 0) {
                                        if (timer != null) {
                                            timer.dispose();
                                        }
                                        mGetCode.setText("短信验证");
                                        mGetCode.setEnabled(true);
                                        mGetCode.setTextColor(getResources().getColor(R.color.white));
                                        mGetCode.setBackground(getResources().getDrawable(R.drawable.bg_create_group_add));
                                    } else {
                                        timeIndex--;
                                        mGetCode.setText("重新发送(" + timeIndex + ")");
                                    }
                                }
                            });
                } else {
                    Toast.makeText(InviteMatchActivity.this, data.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                //super.failure(error);
                dealNetError(error);
            }
        });
    }

    private void doSubmit() {
        if (mCode.getText().toString().trim().equals("")) {
            Toast.makeText(InviteMatchActivity.this, "请先输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mClassName.getText().toString().equals("") | mGroupName.getText().toString().equals("") |mRoleName.getText().toString().equals("")){
            Toast.makeText(InviteMatchActivity.this, "请先填写完整之后再点击提交验证", Toast.LENGTH_SHORT).show();
            return;
        }
        postData.setMobile(getIntent().getStringExtra("mobile"));
        postData.setClassId(infoDataList.get(classIndex).getClassId());
        postData.setGroupId(infoDataList.get(classIndex).getClassGroups().get(groupIndex).getCGId());
        postData.setClassRole(infoDataList.get(classIndex).getClassRoles().get(roleIndex).getClassRoleId());
        postData.setIntroducerMobile(mLoveStudent.getText().toString().trim());
        postData.setCode(mCode.getText().toString().trim());
        service.InviteFromCustomer(UserInfoModel.getInstance().getToken(), postData, new RequestCallback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                Toast.makeText(InviteMatchActivity.this, responseData.getMsg(), Toast.LENGTH_SHORT).show();
                if (responseData.getStatus() == 200) {
                    Intent intent = new Intent(InviteMatchActivity.this,RewriteTestActivity.class);
                    intent.putExtra("gender",gender);
                    intent.putExtra("accountId",accountId);
                    intent.putExtra("classId",infoDataList.get(classIndex).getClassId());
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void failure(RetrofitError error) {
//                super.failure(error);
                dealNetError(error);
            }
        });
    }

    private void showMakiDialog(String title, final TextView textView, BottomRecyclerViewAdapter adapter) {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        View view = LayoutInflater.from(this).inflate(R.layout.maki_dialog_mach, null);
        TextView mDialogTitle = view.findViewById(R.id.tv_title);
        RecyclerView mContent = view.findViewById(R.id.rcv_content);
        mContent.setAdapter(adapter);
        mContent.setLayoutManager(manager);
        Button mOk = view.findViewById(R.id.btn_ok);
        Button mCancel = view.findViewById(R.id.btn_cancel);
        mDialogTitle.setText(title);
        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(viewText);
                bottomDialog.dismiss();
                viewText = "";
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialog.dismiss();
            }
        });
        bottomDialog = new BottomSheetDialog(this);
        bottomDialog.setContentView(view);
        bottomDialog.show();
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
                    mTargetName.setText("减重");
                    postData.setTarget(0);
                    if (entryGoalDialog != null && entryGoalDialog.isShowing()) {
                        entryGoalDialog.dismiss();
                    }
                }
            });
            addWeight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addWeight.setTextColor(getResources().getColor(R.color.mytoolbar_green));
                    lossWeight.setTextColor(getResources().getColor(R.color.word));
                    mTargetName.setText("增重");
                    postData.setTarget(1);
                    if (entryGoalDialog != null && entryGoalDialog.isShowing()) {
                        entryGoalDialog.dismiss();
                    }
                }
            });
            entryGoalDialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    entryGoalDialog.dismiss();
                }
            });
            entryGoalDialog.findViewById(R.id.space).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (entryGoalDialog != null && entryGoalDialog.isShowing()) {
                        entryGoalDialog.dismiss();
                    }
                }
            });
        }
        entryGoalDialog.show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            if (!timer.isDisposed()) {
                timer.dispose();
            }
        }
    }

    public static class PostData {
        private String Mobile;
        private String ClassId;
        private String GroupId;
        private int ClassRole;
        private int Target;
        private String IntroducerMobile;
        private String Code;

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String mobile) {
            Mobile = mobile;
        }

        public String getClassId() {
            return ClassId;
        }

        public void setClassId(String classId) {
            ClassId = classId;
        }

        public String getGroupId() {
            return GroupId;
        }

        public void setGroupId(String groupId) {
            GroupId = groupId;
        }

        public int getClassRole() {
            return ClassRole;
        }

        public void setClassRole(int classRole) {
            ClassRole = classRole;
        }

        public int getTarget() {
            return Target;
        }

        public void setTarget(int target) {
            Target = target;
        }

        public String getIntroducerMobile() {
            return IntroducerMobile;
        }

        public void setIntroducerMobile(String introducerMobile) {
            IntroducerMobile = introducerMobile;
        }

        public String getCode() {
            return Code;
        }

        public void setCode(String code) {
            Code = code;
        }
    }

    public static class RecCode {

        /**
         * Code : 125635
         */

        private int Code;

        public int getCode() {
            return Code;
        }

        public void setCode(int Code) {
            this.Code = Code;
        }
    }

}
