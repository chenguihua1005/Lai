/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.chat.ui;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.EMConnectionListener;
import com.easemob.EMError;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.easeui.EaseConstant;
import com.easemob.easeui.domain.ChatUserInfoModel;
import com.easemob.easeui.domain.ChatUserModel;
import com.easemob.easeui.domain.EaseEmojicon;
import com.easemob.easeui.utils.EaseACKUtil;
import com.easemob.easeui.utils.EaseCommonUtils;
import com.easemob.easeui.widget.EaseChatExtendMenu;
import com.easemob.easeui.widget.EaseChatInputMenu;
import com.easemob.easeui.widget.EaseVoiceRecorderView;
import com.easemob.util.PathUtil;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.softtek.lai.R;
import com.softtek.lai.chat.model.ChatContactInfoModel;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.stepcount.service.StepService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_group_sent_chat)
public class GroupSentActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener, BaseFragment.OnFragmentInteractionListener {

    @LifeCircleInject
    ValidateLife validateLife;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.lin_mid)
    LinearLayout lin_mid;

    @InjectView(R.id.voice_recorder)
    EaseVoiceRecorderView voiceRecorderView;

    @InjectView(R.id.input_menu)
    EaseChatInputMenu inputMenu;

    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.text_count)
    TextView text_count;
    @InjectView(R.id.text_value)
    TextView text_value;

    protected File cameraFile;

    protected static final int REQUEST_CODE_MAP = 1;
    protected static final int REQUEST_CODE_CAMERA = 2;
    protected static final int REQUEST_CODE_LOCAL = 3;


    String toChatUsername;
    static final int ITEM_TAKE_PICTURE = 1;
    static final int ITEM_PICTURE = 2;
    static final int ITEM_LOCATION = 3;

    protected int[] itemStrings = {com.easemob.easeui.R.string.attach_take_pic, com.easemob.easeui.R.string.attach_picture, com.easemob.easeui.R.string.attach_location};
    protected int[] itemdrawables = {com.easemob.easeui.R.drawable.ease_chat_takepic_selector, com.easemob.easeui.R.drawable.ease_chat_image_selector,
            com.easemob.easeui.R.drawable.ease_chat_location_selector};
    protected int[] itemIds = {ITEM_TAKE_PICTURE, ITEM_PICTURE, ITEM_LOCATION};

    protected MyItemClickListener extendMenuItemClickListener;

    List<ChatContactInfoModel> list;

    public AlertDialog.Builder builder = null;
    private EMConnectionListener connectionListener;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            System.out.println("9999999");
            if (builder != null) {
                return;
            }
            builder = new AlertDialog.Builder(GroupSentActivity.this)
                    .setTitle("温馨提示").setMessage("您的帐号已经在其他设备登录，请重新登录后再试。")
                    .setPositiveButton("现在登录", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            builder = null;
                            UserInfoModel.getInstance().loginOut();
                            stopService(new Intent(GroupSentActivity.this, StepService.class));
                            Intent intent = new Intent(GroupSentActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }).setCancelable(false);
            Dialog dialog=builder.create();
            if(!isFinishing()){
                if(dialog!=null && !dialog.isShowing()){
                    dialog.show();
                }
            }

        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //可以直接new EaseChatFratFragment使用
        EaseConstant.IS_GROUP_SENT = "true";
        extendMenuItemClickListener = new MyItemClickListener();
        registerExtendMenuItem();
        // init input menu
        lin_mid.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                inputMenu.hideExtendMenuContainer();
                return false;
            }
        });

        inputMenu.init(null);
        connectionListener = new EMConnectionListener() {
            @Override
            public void onDisconnected(final int error) {
                System.out.println("GroupSentActivity onDisconnected-------");
                if (error == EMError.CONNECTION_CONFLICT) {
                    SharedPreferenceService.getInstance().put("HXID", "-1");
                    if (!isFinishing()) {
                        EMChatManager.getInstance().logout(true, new EMCallBack() {

                            @Override
                            public void onSuccess() {
                                // TODO Auto-generated method stub
                                System.out.println("GroupSentActivity onSuccess-------");
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
                //EaseACKUtil.getInstance(GroupSentActivity.this).checkACKData();

            }
        };
        EMChatManager.getInstance().addConnectionListener(connectionListener);


        inputMenu.setChatInputMenuListener(new EaseChatInputMenu.ChatInputMenuListener() {

            @Override
            public void onSendMessage(final String content) {
                // 发送文本消息
                for (int i = 0; i < list.size(); i++) {
                    final ChatContactInfoModel model = list.get(i);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            sendTextMessage(content, model);
                        }
                    }).start();
                }
            }

            @Override
            public boolean onPressToSpeakBtnTouch(View v, MotionEvent event) {
                return voiceRecorderView.onPressToSpeakBtnTouch(v, event, new EaseVoiceRecorderView.EaseVoiceRecorderCallback() {

                    @Override
                    public void onVoiceRecordComplete(final String voiceFilePath, final int voiceTimeLength) {
                        // 发送语音消息
                        for (int i = 0; i < list.size(); i++) {
                            final ChatContactInfoModel model = list.get(i);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    sendVoiceMessage(voiceFilePath, voiceTimeLength, model);
                                }
                            }).start();
                        }


                    }
                });
            }

            @Override
            public void onBigExpressionClicked(final EaseEmojicon emojicon) {
                //发送大表情(动态表情)
                for (int i = 0; i < list.size(); i++) {
                    final ChatContactInfoModel model = list.get(i);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            sendBigExpressionMessage(emojicon.getName(), emojicon.getIdentityCode(), model);
                        }
                    }).start();
                }
            }
        });

    }

    /**
     * 隐藏软键盘
     */
    protected void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 注册底部菜单扩展栏item; 覆盖此方法时如果不覆盖已有item，item的id需大于3
     */
    protected void registerExtendMenuItem() {
        for (int i = 0; i < 2; i++) {
            inputMenu.registerExtendMenuItem(itemStrings[i], itemdrawables[i], itemIds[i], extendMenuItemClickListener);
        }
    }

    @Override
    protected void initViews() {
        tv_title.setText("群发");
        ll_left.setOnClickListener(this);
        text_value.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    @Override
    protected void initDatas() {
        list = (ArrayList<ChatContactInfoModel>) getIntent().getSerializableExtra("list");
        text_count.setText("你将发送消息给" + list.size() + "位朋友:");
        String value = "";
        for (int i = 0; i < list.size(); i++) {
            ChatContactInfoModel model = list.get(i);
            if (i == 0) {
                value = value + model.getUserName();
            } else {
                value = value + "," + model.getUserName();
            }
        }
        text_value.setText(value);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {

    }

    /**
     * 照相获取图片
     */
    protected void selectPicFromCamera() {
        if (!EaseCommonUtils.isExitsSdcard()) {
            Toast.makeText(this, com.easemob.easeui.R.string.sd_card_does_not_exist, Toast.LENGTH_SHORT).show();
            return;
        }

        cameraFile = new File(PathUtil.getInstance().getImagePath(), EMChatManager.getInstance().getCurrentUser()
                + System.currentTimeMillis() + ".jpg");
        cameraFile.getParentFile().mkdirs();
        startActivityForResult(
                new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile)),
                REQUEST_CODE_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_CAMERA) { // 发送照片
                if (cameraFile != null && cameraFile.exists()) {
                    for (int i = 0; i < list.size(); i++) {
                        final ChatContactInfoModel model = list.get(i);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                sendImageMessage(cameraFile.getAbsolutePath(), model);
                            }
                        }).start();
                    }
                }
            } else if (requestCode == REQUEST_CODE_LOCAL) { // 发送本地图片
                if (data != null) {
                    Uri selectedImage = data.getData();
                    if (selectedImage != null) {
                        sendPicByUri(selectedImage);
                    }
                }
            }
        }
    }

    /**
     * 从图库获取图片
     */
    protected void selectPicFromLocal() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");

        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, REQUEST_CODE_LOCAL);
    }

    /**
     * 扩展菜单栏item点击事件
     */
    class MyItemClickListener implements EaseChatExtendMenu.EaseChatExtendMenuItemClickListener {

        @Override
        public void onClick(int itemId, View view) {
            switch (itemId) {
                case ITEM_TAKE_PICTURE: // 拍照
                    selectPicFromCamera();
                    break;
                case ITEM_PICTURE:
                    selectPicFromLocal(); // 图库选择图片
                    break;
                case ITEM_LOCATION: // 位置
                    //startActivityForResult(new Intent(getActivity(), EaseBaiduMapActivity.class), REQUEST_CODE_MAP);
                    break;

                default:
                    break;
            }
        }

    }

    //发送消息方法
    //==========================================================================
    protected void sendTextMessage(String content, ChatContactInfoModel model) {
        EMMessage message = EMMessage.createTxtSendMessage(content, model.getHXAccountId().toLowerCase());
        EMConversation conversation = EMChatManager.getInstance().getConversation(model.getHXAccountId().toLowerCase());
        sendMessage(message, conversation, model);
    }

    protected void sendBigExpressionMessage(String name, String identityCode, ChatContactInfoModel model) {
        EMMessage message = EaseCommonUtils.createExpressionMessage(model.getHXAccountId().toLowerCase(), name, identityCode);
        EMConversation conversation = EMChatManager.getInstance().getConversation(model.getHXAccountId().toLowerCase());
        sendMessage(message, conversation, model);
    }

    protected void sendVoiceMessage(String filePath, int length, ChatContactInfoModel model) {
        EMMessage message = EMMessage.createVoiceSendMessage(filePath, length, model.getHXAccountId().toLowerCase());
        EMConversation conversation = EMChatManager.getInstance().getConversation(model.getHXAccountId().toLowerCase());
        sendMessage(message, conversation, model);
    }

    protected void sendImageMessage(String imagePath, ChatContactInfoModel model) {
        EMMessage message = EMMessage.createImageSendMessage(imagePath, false, model.getHXAccountId().toLowerCase());
        EMConversation conversation = EMChatManager.getInstance().getConversation(model.getHXAccountId().toLowerCase());
        sendMessage(message, conversation, model);
    }

    protected void sendMessage(EMMessage message, EMConversation conversation, ChatContactInfoModel model) {
        ChatUserModel chatUserModel = ChatUserInfoModel.getInstance().getUser();
        message.setAttribute("nickname", chatUserModel.getUserName());
        message.setAttribute("avatarURL", chatUserModel.getUserPhone());
        message.setAttribute("userId", chatUserModel.getUserId().toLowerCase());

        //发送消息
        EMChatManager.getInstance().sendMessage(message, null);
        setProfile(conversation, model);
        Intent intent = new Intent(this, ConversationListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    protected void setProfile(EMConversation conversation, ChatContactInfoModel model) {
        String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
        String name = model.getUserName();
        String photo = model.getPhoto();
        conversation.setExtField(name + "," + path + photo);
    }

    /**
     * 根据图库图片uri发送图片
     *
     * @param selectedImage
     */
    protected void sendPicByUri(Uri selectedImage) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            final String picturePath = cursor.getString(columnIndex);
            cursor.close();
            cursor = null;

            if (picturePath == null || picturePath.equals("null")) {
                Toast toast = Toast.makeText(this, com.easemob.easeui.R.string.cant_find_pictures, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }
            for (int i = 0; i < list.size(); i++) {
                final ChatContactInfoModel model = list.get(i);
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        sendImageMessage(picturePath, model);
                    }
                }).start();
            }
        } else {
            final File file = new File(selectedImage.getPath());
            if (!file.exists()) {
                Toast toast = Toast.makeText(this, com.easemob.easeui.R.string.cant_find_pictures, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;

            }
            for (int i = 0; i < list.size(); i++) {
                final ChatContactInfoModel model = list.get(i);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendImageMessage(file.getAbsolutePath(), model);
                    }
                }).start();
            }


        }

    }
}
