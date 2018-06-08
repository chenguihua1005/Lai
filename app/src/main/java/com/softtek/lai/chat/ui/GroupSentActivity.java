/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.chat.ui;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.ChatUserInfoModel;
import com.hyphenate.easeui.domain.ChatUserModel;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.widget.EaseChatExtendMenu;
import com.hyphenate.easeui.widget.EaseChatInputMenu;
import com.hyphenate.easeui.widget.EaseVoiceRecorderView;
import com.hyphenate.util.PathUtil;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.bodygame3.conversation.model.ChatContactModel;
import com.softtek.lai.widgets.PopUpWindow.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_group_sent_chat)
public class GroupSentActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "GroupSentActivity";

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

    protected int[] itemStrings = {R.string.attach_take_pic, R.string.attach_picture, R.string.attach_location};
    protected int[] itemdrawables = {R.drawable.ease_chat_takepic_selector, R.drawable.ease_chat_image_selector,
            R.drawable.ease_chat_location_selector};
    protected int[] itemIds = {ITEM_TAKE_PICTURE, ITEM_PICTURE, ITEM_LOCATION};

    protected MyItemClickListener extendMenuItemClickListener;

    List<ChatContactModel> list;

    public AlertDialog.Builder builder = null;
    private EMConnectionListener connectionListener;

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

        inputMenu.setChatInputMenuListener(new EaseChatInputMenu.ChatInputMenuListener() {
            @Override
            public void onSendMessage(final String content) {
                // 发送文本消息
                for (int i = 0; i < list.size(); i++) {
                    final ChatContactModel model = list.get(i);
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
                            final ChatContactModel model = list.get(i);
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
                    final ChatContactModel model = list.get(i);
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

    @Override
    public void onResume() {
        super.onResume();
        EaseConstant.IS_GROUP_SENT = "true";
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
        list = (ArrayList<ChatContactModel>) getIntent().getSerializableExtra("list");
        text_count.setText("你将发送消息给" + list.size() + "位朋友:");
        String value = "";
        for (int i = 0; i < list.size(); i++) {
            ChatContactModel model = list.get(i);
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

    /**
     * 照相获取图片
     */
    protected void selectPicFromCamera() {
        if (!EaseCommonUtils.isSdcardExist()) {
            Toast.makeText(this, R.string.sd_card_does_not_exist, Toast.LENGTH_SHORT).show();
            return;
        }

        cameraFile = new File(PathUtil.getInstance().getImagePath(), EMClient.getInstance().getCurrentUser()
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
                        final ChatContactModel model = list.get(i);
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
                    zilla.libcore.util.Util.toastMsg("不支持群发图片");
//                    selectPicFromCamera();
                    break;
                case ITEM_PICTURE:
//                    selectPicFromLocal(); // 图库选择图片
                    zilla.libcore.util.Util.toastMsg("不支持群发图片");
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
    protected void sendTextMessage(String content, ChatContactModel model) {
        EMMessage message = EMMessage.createTxtSendMessage(content, model.getHXAccountId().toLowerCase());
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(model.getHXAccountId().toLowerCase(), EMConversation.EMConversationType.Chat, true);
        sendMessage(message, conversation, model);
    }

    protected void sendBigExpressionMessage(String name, String identityCode, ChatContactModel model) {
        EMMessage message = EaseCommonUtils.createExpressionMessage(model.getHXAccountId().toLowerCase(), name, identityCode);
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(model.getHXAccountId().toLowerCase(), EMConversation.EMConversationType.Chat, true);
        sendMessage(message, conversation, model);
    }

    protected void sendVoiceMessage(String filePath, int length, ChatContactModel model) {
        EMMessage message = EMMessage.createVoiceSendMessage(filePath, length, model.getHXAccountId().toLowerCase());
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(model.getHXAccountId().toLowerCase(), EMConversation.EMConversationType.Chat, true);
        sendMessage(message, conversation, model);
    }

    protected void sendImageMessage(String imagePath, ChatContactModel model) {
        EMMessage message = EMMessage.createImageSendMessage(imagePath, false, model.getHXAccountId().toLowerCase());
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(model.getHXAccountId().toLowerCase(), EMConversation.EMConversationType.Chat, true);
        sendMessage(message, conversation, model);
    }

    protected void sendMessage(EMMessage message, EMConversation conversation, ChatContactModel model) {
        ChatUserModel chatUserModel = ChatUserInfoModel.getInstance().getUser();

        Log.i(TAG, "发送人的个人信息 = nickname = " + chatUserModel.getUserName() + " avatarURL = " + chatUserModel.getUserPhone() + " userId = " + chatUserModel.getUserId().toLowerCase());
        if (chatUserModel != null) {
            message.setAttribute("nickname", chatUserModel.getUserName());
            message.setAttribute("avatarURL", chatUserModel.getUserPhone());
            message.setAttribute("userId", chatUserModel.getUserId().toLowerCase());
        }

        if (model != null && conversation != null) {
            setProfile(conversation, model);
        }
        message.setChatType(EMMessage.ChatType.Chat);
        //发送消息
//        EMClient.getInstance().chatManager().sendMessage(message, null);//jessica
        EMClient.getInstance().chatManager().sendMessage(message);
//        message.setChatType(EMMessage.ChatType.Chat);


        //send message
//        EMClient.getInstance().chatManager().sendMessage(message);

        Intent intent = new Intent(this, ConversationListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    protected void setProfile(EMConversation conversation, ChatContactModel model) {
        String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");

        Log.i("+++", "path = " + path);
        Log.i("+++", "model= " + new Gson().toJson(model));
        if (model != null) {
            String name = model.getUserName();
            String photo = model.getPhoto();
            conversation.setExtField(name + "," + path + photo);
        }
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
                Toast toast = Toast.makeText(this, R.string.cant_find_pictures, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }
            for (int i = 0; i < list.size(); i++) {
                final ChatContactModel model = list.get(i);
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
                Toast toast = Toast.makeText(this, R.string.cant_find_pictures, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;

            }
            for (int i = 0; i < list.size(); i++) {
                final ChatContactModel model = list.get(i);
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
