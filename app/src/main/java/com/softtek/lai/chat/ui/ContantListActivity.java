///*
// * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
// * Date:2016-03-31
// */
//
//package com.softtek.lai.chat.ui;
//
//
//import android.app.Dialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v4.content.LocalBroadcastManager;
//import android.support.v7.app.AlertDialog;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.FrameLayout;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.hyphenate.EMCallBack;
//import com.hyphenate.EMConnectionListener;
//import com.hyphenate.EMError;
//import com.hyphenate.chat.EMClient;
//import com.softtek.lai.LaiApplication;
//import com.softtek.lai.R;
//import com.softtek.lai.chat.Constant;
//import com.softtek.lai.chat.adapter.ChatContantAdapter;
//import com.softtek.lai.chat.model.ChatContactInfoModel;
//import com.softtek.lai.common.BaseActivity;
//import com.softtek.lai.common.BaseFragment;
//import com.softtek.lai.common.ResponseData;
//import com.softtek.lai.common.UserInfoModel;
//import com.softtek.lai.module.login.net.LoginService;
//import com.softtek.lai.module.login.view.LoginActivity;
//import com.softtek.lai.stepcount.service.StepService;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.InjectView;
//import retrofit.Callback;
//import retrofit.RetrofitError;
//import retrofit.client.Response;
//import zilla.libcore.api.ZillaApi;
//import zilla.libcore.file.AddressManager;
//import zilla.libcore.file.SharedPreferenceService;
//import zilla.libcore.ui.InjectLayout;
//import zilla.libcore.util.Util;
//
///**
// * Created by jarvis.liu on 3/22/2016.
// */
//@InjectLayout(R.layout.activity_chat_contant_list)
//public class ContantListActivity extends BaseActivity implements View.OnClickListener, BaseFragment.OnFragmentInteractionListener {
//
//    @InjectView(R.id.ll_left)
//    LinearLayout ll_left;
//
//    @InjectView(R.id.fl)
//    FrameLayout fl;
//    @InjectView(R.id.et_search)
//    TextView et_search;
//
//    @InjectView(R.id.tv_title)
//    TextView tv_title;
//
//    @InjectView(R.id.list_contant)
//    ListView list_contant;
//    @InjectView(R.id.lin_group_send)
//    LinearLayout lin_group_send;
//
//    ChatContantAdapter adapter;
//    List<ChatContactInfoModel> list;
//
//    public AlertDialog.Builder builder = null;
//    private EMConnectionListener connectionListener;
//    private Handler handler = new Handler() {
//
//        @Override
//        public void handleMessage(Message msg) {
//            // TODO Auto-generated method stub
//            if (builder != null) {
//                return;
//            }
//            builder = new AlertDialog.Builder(ContantListActivity.this)
//                    .setTitle("温馨提示").setMessage("您的帐号已经在其他设备登录，请重新登录后再试。")
//                    .setPositiveButton("现在登录", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            builder = null;
//                            UserInfoModel.getInstance().loginOut();
//                            LocalBroadcastManager.getInstance(LaiApplication.getInstance()).sendBroadcast(new Intent(StepService.STEP_CLOSE_SELF));
//                            Intent intent = new Intent(ContantListActivity.this, LoginActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            startActivity(intent);
//                        }
//                    }).setCancelable(false);
//            Dialog dialog=builder.create();
//            if(!isFinishing()){
//                if(dialog!=null && !dialog.isShowing()){
//                    dialog.show();
//                }
//            }
//
//        }
//
//    };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        ll_left.setOnClickListener(this);
//        lin_group_send.setOnClickListener(this);
//        fl.setOnClickListener(this);
//        et_search.setOnClickListener(this);
//
//        list = new ArrayList<ChatContactInfoModel>();
//        setData();
//        connectionListener = new EMConnectionListener() {
//            @Override
//            public void onDisconnected(final int error) {
//
//                if (error == EMError.USER_ALREADY_LOGIN) {
//                    SharedPreferenceService.getInstance().put("HXID", "-1");
//                    if (!isFinishing()) {
////                        EMClient.getInstance().logout(true, new EMCallBack() {
////
////                            @Override
////                            public void onSuccess() {
////                                // TODO Auto-generated method stub
////                                handler.sendEmptyMessage(0);
////
////                            }
////
////                            @Override
////                            public void onProgress(int progress, String status) {
////                                // TODO Auto-generated method stub
////
////                            }
////
////                            @Override
////                            public void onError(int code, String message) {
////                                // TODO Auto-generated method stub
////
////                            }
////                        });
//
//                        EMClient.getInstance().logout(true, new EMCallBack() {
//                            @Override
//                            public void onSuccess() {
//                                handler.sendEmptyMessage(0);
//                            }
//
//                            @Override
//                            public void onError(int i, String s) {
//
//                            }
//
//                            @Override
//                            public void onProgress(int i, String s) {
//
//                            }
//                        });
//                    }
//                }
//            }
//
//            @Override
//            public void onConnected() {
//                // 当连接到服务器之后，这里开始检查是否有没有发送的ack回执消息，
//               // EaseACKUtil.getInstance(ContantListActivity.this).checkACKData();
//
//            }
//        };
//        EMClient.getInstance().addConnectionListener(connectionListener);
//
//        list_contant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ChatContactInfoModel model = list.get(position);
//                Intent intent = new Intent(ContantListActivity.this, ChatActivity.class);
//                String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
//                intent.putExtra(Constant.EXTRA_USER_ID, model.getHXAccountId().toLowerCase());
//                intent.putExtra("name", model.getUserName());
//                intent.putExtra("photo", path + model.getPhoto());
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void setData() {
//        dialogShow("加载中");
//        LoginService service = ZillaApi.NormalRestAdapter.create(LoginService.class);
//        String token = UserInfoModel.getInstance().getToken();
//        service.getEMchatContacts(token, new Callback<ResponseData<List<ChatContactInfoModel>>>() {
//            @Override
//            public void success(ResponseData<List<ChatContactInfoModel>> userResponseData, Response response) {
//                int status = userResponseData.getStatus();
//                dialogDissmiss();
//                switch (status) {
//                    case 200:
//                        list = userResponseData.getData();
//                        adapter = new ChatContantAdapter(ContantListActivity.this, list);
//                        list_contant.setAdapter(adapter);
//                        break;
//                    case 201:
//
//                        break;
//                    default:
//                        Util.toastMsg(userResponseData.getMsg());
//                        break;
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                ZillaApi.dealNetError(error);
//                error.printStackTrace();
//                dialogDissmiss();
//            }
//        });
//
//    }
//
//    @Override
//    protected void initViews() {
//        //tv_left.setLayoutParams(new Toolbar.LayoutParams(DisplayUtil.dip2px(this,15),DisplayUtil.dip2px(this,30)));
//        tv_title.setText("通讯录");
//
//    }
//
//    @Override
//    protected void initDatas() {
//
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.ll_left:
//                finish();
//                break;
//
//            case R.id.lin_group_send:
//                Intent intent = new Intent(this, SeceltGroupSentActivity.class);
//                intent.putExtra("list", (Serializable) list);
//                startActivity(intent);
//                break;
//        }
//    }
//
//
//    @Override
//    public void onStop() {
//        super.onStop();
//    }
//
//
//    @Override
//    public void onFragmentInteraction(Uri uri) {
//
//    }
//}
