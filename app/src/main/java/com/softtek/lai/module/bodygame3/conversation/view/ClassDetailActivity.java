package com.softtek.lai.module.bodygame3.conversation.view;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.conversation.model.ContactClassModel;
import com.softtek.lai.module.bodygame3.conversation.service.ContactService;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

/**
 * Created by jessica.zhang on 2016/11/29.
 */

@InjectLayout(R.layout.activity_classdetail)
public class ClassDetailActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "ClassDetailActivity";
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.coach_img)
    CircleImageView coach_img;
    @InjectView(R.id.coach_name)
    TextView coach_name;

    @InjectView(R.id.tv_classname)
    TextView tv_classname;

    @InjectView(R.id.tv_classNo)
    TextView tv_classNo;

    @InjectView(R.id.tv_classStart_time)
    TextView tv_classStart_time;

    @InjectView(R.id.tv_members_accout)
    TextView tv_members_accout;
    @InjectView(R.id.classNumber_linear)
    RelativeLayout classNumber_linear;

    @InjectView(R.id.btn_dismissclass)
    Button btn_dismissclass;


    private ContactClassModel classModel;
    private String toChatUsername;//环信群id 或者个人Id


    private String HXGroupId = "";
    private String ClassId = "";


    @Override
    protected void initViews() {
        if (DisplayUtil.getSDKInt() > 18) {
            int status = DisplayUtil.getStatusHeight(this);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) toolbar.getLayoutParams();
            params.topMargin = status;
            toolbar.setLayoutParams(params);
        }

        ll_left.setVisibility(View.VISIBLE);
        tv_title.setText("班级群详情");

    }

    @Override
    protected void initDatas() {
        classModel = (ContactClassModel) getIntent().getSerializableExtra("classModel");
        toChatUsername = getIntent().getStringExtra("toChatUsername");
        Log.i(TAG, "classModel = " + new Gson().toJson(classModel) + " toChatUsername = " + toChatUsername);

        if (classModel != null) {
            dialogShow();

            String end_date = classModel.getEndDate();
            long CoachId = classModel.getCoachId();
            int dismiss_status = classModel.getStatus();

            HXGroupId = classModel.getHXGroupId();
            ClassId = classModel.getClassId();

            Log.i(TAG, "HXGroupId = " + HXGroupId + " ClassId = " + ClassId);
            Log.i(TAG, "class info = " + new Gson().toJson(classModel));

            Log.i(TAG, "CoachId = " + CoachId + " UserInfoModel.getInstance().getUserId() = " + UserInfoModel.getInstance().getUserId());
            if (CoachId == UserInfoModel.getInstance().getUserId()) {
                btn_dismissclass.setVisibility(View.VISIBLE);
                if (1 == dismiss_status) {
                    btn_dismissclass.setBackgroundResource(R.drawable.btn_dismissclass);
                    btn_dismissclass.setText(getResources().getString(R.string.please_dismiss_class));
                } else if (0 == dismiss_status) {
                    btn_dismissclass.setBackgroundResource(R.drawable.btn_dismissclass_gray);
                    btn_dismissclass.setText(getResources().getString(R.string.please_close_class));
                    btn_dismissclass.setEnabled(false);
                }
            }

            coach_name.setText(classModel.getCoachName());
            tv_classname.setText(classModel.getClassName());
            tv_classNo.setText(classModel.getClassCode());

            if (!TextUtils.isEmpty(classModel.getStartDate())) {
                String[] arr = classModel.getStartDate().split(" ");
                tv_classStart_time.setText(arr[0]);
            }
            tv_members_accout.setText(String.valueOf(classModel.getTotal()) + "人");

            String photo = classModel.getCoachPhoto();
            String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");

            if (!TextUtils.isEmpty(photo)) {
                Picasso.with(this).load(path + photo).resize(DisplayUtil.dip2px(this,40),DisplayUtil.dip2px(this,40)).centerCrop().placeholder(com.hyphenate.easeui.R.drawable.ease_default_avatar)
                        .error(com.hyphenate.easeui.R.drawable.ease_default_avatar).into(coach_img);
            }
//            if ("".equals(photo)) {
//                Picasso.with(this).load("111").fit().error(R.drawable.img_default).into(coach_img);
//            } else {
//                Picasso.with(this).load(path + photo).fit().error(R.drawable.img_default).into(coach_img);
//            }

            dialogDissmiss();

        } else {
            //从会话群进入 ,  需要调用环信群id查询班级信息
            dialogShow();
            if (!TextUtils.isEmpty(toChatUsername)) {
                ContactService service = ZillaApi.NormalRestAdapter.create(ContactService.class);
                service.getClassByHxGroupId(UserInfoModel.getInstance().getToken(), toChatUsername, UserInfoModel.getInstance().getUserId(), new Callback<ResponseData<ContactClassModel>>() {
                    @Override
                    public void success(ResponseData<ContactClassModel> contactClassModelResponseData, Response response) {
                        int status = contactClassModelResponseData.getStatus();

                        if (200 == status) {
                            classModel = contactClassModelResponseData.getData();
                            if (classModel != null) {
                                String end_date = classModel.getEndDate();
                                long CoachId = classModel.getCoachId();

                                HXGroupId = toChatUsername;
                                ClassId = classModel.getClassId();

                                int dismiss_status = classModel.getStatus();
                                if (CoachId == UserInfoModel.getInstance().getUserId()) {
                                    btn_dismissclass.setVisibility(View.VISIBLE);
                                    if (1 == dismiss_status) {
                                        btn_dismissclass.setBackgroundResource(R.drawable.btn_dismissclass);
                                        btn_dismissclass.setText(getResources().getString(R.string.please_dismiss_class));
                                    } else if (0 == dismiss_status) {
                                        btn_dismissclass.setBackgroundResource(R.drawable.btn_dismissclass_gray);
                                        btn_dismissclass.setText(getResources().getString(R.string.please_close_class));
                                        btn_dismissclass.setEnabled(false);
                                    }
                                }

                                String photo = classModel.getCoachPhoto();
                                String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
                                if ("".equals(photo)) {
                                    Picasso.with(ClassDetailActivity.this).load("111").fit().error(R.drawable.img_default).into(coach_img);
                                } else {
                                    Picasso.with(ClassDetailActivity.this).load(path + photo).fit().error(R.drawable.img_default).into(coach_img);
                                }

                                coach_name.setText(classModel.getCoachName());
                                tv_classname.setText(classModel.getClassName());
                                tv_classNo.setText(classModel.getClassCode());
//                                tv_classStart_time.setText(classModel.getStartDate());
                                if (!TextUtils.isEmpty(classModel.getStartDate())) {
                                    String[] arr = classModel.getStartDate().split(" ");
                                    tv_classStart_time.setText(arr[0]);
                                }

                                tv_members_accout.setText(String.valueOf(classModel.getTotal()) + "人");
                            }

                            dialogDissmiss();
                        } else {
                            Util.toastMsg(contactClassModelResponseData.getMsg());
                            dialogDissmiss();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        dialogDissmiss();
                        ZillaApi.dealNetError(error);
                    }
                });
            }
        }

        ll_left.setOnClickListener(this);
        btn_dismissclass.setOnClickListener(this);
        classNumber_linear.setOnClickListener(this);
    }

    public static Date StringToDate(String dateStr) {
        String formatStr = "yyyy-MM-dd HH:mm:ss";//2017-02-24 00:00:00
        DateFormat sdf = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public Date getNowDate() {
        Date date_now = null;
        String formatStr = "yyyy-MM-dd HH:mm:ss";
        DateFormat sdf = new SimpleDateFormat(formatStr);
        String dateStr = sdf.format(new Date());
        try {
            date_now = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.i("leave", "now time = " + date_now);
        return date_now;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.classNumber_linear:
                Intent intent = new Intent(ClassDetailActivity.this, GroupDetailsActivity.class);
//                intent.putExtra("groupId", toChatUsername);
//                intent.putExtra("classId", classId);
                intent.putExtra("classModel", classModel);
                startActivity(intent);

                break;
            case R.id.btn_dismissclass:
                //解散班级
                dissolutionHxGroup();

                break;

        }
    }

    //解散班级
    private void dissolutionHxGroup() {
        final String st5 = getResources().getString(R.string.Dissolve_group_chat_tofail);
        dialogShow(getResources().getString(R.string.Is_sending_a_request));

        new Thread(new Runnable() {
            public void run() {
                try {
                    EMClient.getInstance().groupManager().destroyGroup(HXGroupId);//需异步处理

                    ContactService service = ZillaApi.NormalRestAdapter.create(ContactService.class);
                    service.dissolutionHxGroup(UserInfoModel.getInstance().getToken(),ClassId, ClassId, new Callback<ResponseData>() {
                        @Override
                        public void success(final ResponseData responseData, Response response) {
                            int status = responseData.getStatus();
                            if (200 == status) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        dialogDissmiss();
                                        //跳转到群聊列表

                                        Intent intent = new Intent(ClassDetailActivity.this, GroupsActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);

                                        finish();
                                    }
                                });

                            } else {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        dialogDissmiss();
                                        Toast.makeText(getApplicationContext(), st5 + responseData.getMsg(), Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }

                        @Override
                        public void failure(final RetrofitError error) {
                            ZillaApi.dealNetError(error);
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    dialogDissmiss();
                                    Toast.makeText(getApplicationContext(), st5 + error.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });


                } catch (final HyphenateException e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            dialogDissmiss();
                            Toast.makeText(getApplicationContext(), st5 + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }).start();


//        ContactService service = ZillaApi.NormalRestAdapter.create(ContactService.class);
//        service.dissolutionHxGroup(UserInfoModel.getInstance().getToken(), classModel.getClassId(), new Callback<ResponseData>() {
//            @Override
//            public void success(ResponseData responseData, Response response) {
//                int status = responseData.getStatus();
//                if (200 == status) {
//                    Util.toastMsg("解散班级成功！");
//
//                    //调用环信
////                    try {
////                        EMClient.getInstance().groupManager().destroyGroup(classModel.getClassId());
////
////                    } catch (HyphenateException e) {
////                        e.printStackTrace();
////                    }
//                    new Thread(new Runnable() {
//                        public void run() {
//                            try {
//                                List<EMGroup> grouplist = EMClient.getInstance().groupManager().getJoinedGroupsFromServer();//需异步处理
//                                for (EMGroup group : grouplist) {
//                                    String groupId = group.getGroupId();
//                                    Log.i(TAG, "groupId= " + groupId);
//                                    if (groupId.equals(classModel.getHXGroupId())) {
//                                        EMClient.getInstance().groupManager().destroyGroup(groupId);//需异步处理
//                                        Log.i(TAG, " 解散成功！" + groupId);
//                                        finish();
//                                    }
//                                }
//
//
//                            } catch (final HyphenateException e) {
//                                Log.i(TAG, "info" + e.toString());
//                            }
//                        }
//                    }).start();
//
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                ZillaApi.dealNetError(error);
//                Util.toastMsg("解散班级失败！");
//            }
//        });
    }
}
