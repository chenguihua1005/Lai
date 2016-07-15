package com.softtek.lai.module.bodygame2.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easemob.EMCallBack;
import com.easemob.EMConnectionListener;
import com.easemob.EMError;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.github.snowdream.android.util.Log;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.softtek.lai.R;
import com.softtek.lai.chat.Constant;
import com.softtek.lai.chat.adapter.ChatContantAdapter;
import com.softtek.lai.chat.model.ChatContactInfoModel;
import com.softtek.lai.chat.ui.ChatActivity;
import com.softtek.lai.chat.ui.SeceltGroupSentActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame2pc.view.BodyGamePCActivity;
import com.softtek.lai.module.bodygame2sr.view.BodyGameSRActivity;
import com.softtek.lai.module.login.net.LoginService;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.stepcount.service.StepService;
import com.softtek.lai.utils.DisplayUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.fragment_contact)
public class ContactFragment extends LazyBaseFragment implements View.OnClickListener{
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.fl)
    FrameLayout fl;
    @InjectView(R.id.et_search)
    TextView et_search;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.list_contant)
    ListView list_contant;
    @InjectView(R.id.lin_group_send)
    LinearLayout lin_group_send;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    ChatContantAdapter adapter;
    List<ChatContactInfoModel> list;

    public AlertDialog.Builder builder = null;
    private EMConnectionListener connectionListener;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            if (builder != null) {
                return;
            }
            builder = new AlertDialog.Builder(getActivity())
                    .setTitle("温馨提示").setMessage("您的帐号已经在其他设备登录，请重新登录后再试。")
                    .setPositiveButton("现在登录", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            builder = null;
                            UserInfoModel.getInstance().loginOut();
                            getActivity().stopService(new Intent(getActivity(), StepService.class));
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }).setCancelable(false);
            dialog=builder.create();
            if(!getActivity().isFinishing()){
                if(dialog!=null && !dialog.isShowing()){
                    dialog.show();
                }
            }

        }

    };

    @Override
    protected void initViews() {
        ll_left.setVisibility(View.INVISIBLE);
        tv_title.setText("通讯录");
        int status= DisplayUtil.getStatusHeight(getActivity());
        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) toolbar.getLayoutParams();
        params.topMargin=status;
        toolbar.setLayoutParams(params);
    }
    private void setData() {
        dialogShow("加载中");
        LoginService service = ZillaApi.NormalRestAdapter.create(LoginService.class);
        String token = UserInfoModel.getInstance().getToken();
        service.getEMchatContacts(token, new Callback<ResponseData<List<ChatContactInfoModel>>>() {
            @Override
            public void success(ResponseData<List<ChatContactInfoModel>> userResponseData, Response response) {
                System.out.println("userResponseData:" + userResponseData);
                int status = userResponseData.getStatus();
                dialogDissmiss();
                switch (status) {
                    case 200:
                        list = userResponseData.getData();
                        adapter = new ChatContantAdapter(getContext(), list);
                        list_contant.setAdapter(adapter);
                        break;
                    case 201:

                        break;
                    default:
                        Util.toastMsg(userResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                dialogDissmiss();
                ZillaApi.dealNetError(error);
            }
        });

    }

    @Override
    protected void onVisible() {
        super.onVisible();
        if(getContext() instanceof BodyGameSPActivity){
            BodyGameSPActivity activity=(BodyGameSPActivity)getContext();
            activity.setAlpha(1);
        }else if(getContext() instanceof BodyGamePCActivity){
            BodyGamePCActivity activity=(BodyGamePCActivity)getContext();
            activity.setAlpha(1);
        }
        else if(getContext() instanceof BodyGameSRActivity){
            BodyGameSRActivity activity=(BodyGameSRActivity)getContext();
            activity.setAlpha(1);
        }
    }

    @Override
    protected void initDatas() {
        ll_left.setOnClickListener(this);
        lin_group_send.setOnClickListener(this);
        fl.setOnClickListener(this);
        et_search.setOnClickListener(this);

        list = new ArrayList<ChatContactInfoModel>();
        connectionListener = new EMConnectionListener() {
            @Override
            public void onDisconnected(final int error) {
                if (error == EMError.CONNECTION_CONFLICT) {
                    if (!getActivity().isFinishing()) {
                        EMChatManager.getInstance().logout(true, new EMCallBack() {

                            @Override
                            public void onSuccess() {
                                // TODO Auto-generated method stub
                                System.out.println("ContactFragment onSuccess-------");
                                handler.sendEmptyMessage(0);
                            }

                            @Override
                            public void onProgress(int progress, String status) {
                                // TODO Auto-generated method stub

                            }

                            @Override
                            public void onError(int code, String message) {
                                // TODO Auto-generated method stub

                            }
                        });
                    }
                }
            }

            @Override
            public void onConnected() {
                // 当连接到服务器之后，这里开始检查是否有没有发送的ack回执消息，
                // EaseACKUtil.getInstance(ContantListActivity.this).checkACKData();

            }
        };
        //EMChatManager.getInstance().addConnectionListener(connectionListener);

        list_contant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean isLogin = EMChat.getInstance().isLoggedIn();
                System.out.println("isLogin:" + isLogin);
                if (isLogin) {
                    ChatContactInfoModel model = list.get(position);
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
                    intent.putExtra(Constant.EXTRA_USER_ID, model.getHXAccountId().toLowerCase());
                    intent.putExtra("name", model.getUserName());
                    intent.putExtra("photo", path + model.getPhoto());
                    startActivity(intent);
                }else {
                    Util.toastMsg("会话异常，请稍后再试");
                }
            }
        });
    }


    @Override
    protected void lazyLoad() {
        Log.i("ContactFragment 加载数据");
        setData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                getActivity().finish();
                break;

            case R.id.lin_group_send:
                boolean isLogin = EMChat.getInstance().isLoggedIn();
                if (isLogin) {
                    Intent intent = new Intent(getActivity(), SeceltGroupSentActivity.class);
                    intent.putExtra("list", (Serializable) list);
                    startActivity(intent);
                }else {
                    Util.toastMsg("会话异常，请稍后再试");
                }

                break;
        }
    }
}
