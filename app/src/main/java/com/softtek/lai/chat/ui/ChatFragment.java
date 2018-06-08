package com.softtek.lai.chat.ui;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.ui.EaseChatFragment.EaseChatFragmentHelper;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.hyphenate.util.PathUtil;
import com.softtek.lai.module.bodygame3.head.view.PersonDetailActivity2;

import java.io.File;
import java.io.FileOutputStream;

public class ChatFragment extends EaseChatFragment implements EaseChatFragmentHelper {

    //避免和基类定义的常量可能发生的冲突，常量从11开始定义
    private static final int ITEM_VIDEO = 11;
    private static final int ITEM_FILE = 12;
    private static final int ITEM_VOICE_CALL = 13;
    private static final int ITEM_VIDEO_CALL = 14;
    private static final int ITEM_READFIRE = 15;
    private static final int ITEM_SEND_MONEY = 16;

    private static final int REQUEST_CODE_SELECT_VIDEO = 11;
    private static final int REQUEST_CODE_SELECT_FILE = 12;
    private static final int REQUEST_CODE_GROUP_DETAIL = 13;
    private static final int REQUEST_CODE_CONTEXT_MENU = 14;
    private static final int REQUEST_CODE_SEND_MONEY = 15;


    private static final int MESSAGE_TYPE_SENT_VOICE_CALL = 1;
    private static final int MESSAGE_TYPE_RECV_VOICE_CALL = 2;
    private static final int MESSAGE_TYPE_SENT_VIDEO_CALL = 3;
    private static final int MESSAGE_TYPE_RECV_VIDEO_CALL = 4;

    private static final int MESSAGE_TYPE_RECV_MONEY = 5;
    private static final int MESSAGE_TYPE_SEND_MONEY = 6;
    private static final int MESSAGE_TYPE_SEND_LUCKY = 7;
    private static final int MESSAGE_TYPE_RECV_LUCKY = 8;


    /**
     * 是否为环信小助手
     */
    private boolean isRobot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void setUpView() {
        setChatFragmentListener(this);
        super.setUpView();
    }

    @Override
    protected void registerExtendMenuItem() {
        //demo这里不覆盖基类已经注册的item,item点击listener沿用基类的
        super.registerExtendMenuItem();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("lzan13", "ChatFragment - onActivityResult - " + requestCode);
        if (requestCode == REQUEST_CODE_CONTEXT_MENU) {
            switch (resultCode) {
                case ContextMenuActivity.RESULT_CODE_COPY: // 复制消息
//                    clipboard.setText(((TextMessageBody) contextMenuMessage.getBody()).getMessage());
                    clipboard.setPrimaryClip(ClipData.newPlainText(null,
                            ((EMTextMessageBody) contextMenuMessage.getBody()).getMessage()));
                    break;
                case ContextMenuActivity.RESULT_CODE_DELETE: // 删除消息
                    conversation.removeMessage(contextMenuMessage.getMsgId());
                    refreshUI();
                    break;
                case ContextMenuActivity.RESULT_CODE_FORWARD: // forward
//                    Intent intent = new Intent(getActivity(), ForwardMessageActivity.class);
//                    intent.putExtra("forward_msg_id", contextMenuMessage.getMsgId());
//                    startActivity(intent);

//                    break;
                default:
                    break;
            }
        }
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SELECT_VIDEO: //发送选中的视频
                    if (data != null) {
                        int duration = data.getIntExtra("dur", 0);
                        String videoPath = data.getStringExtra("path");
                        File file = new File(PathUtil.getInstance().getImagePath(), "thvideo" + System.currentTimeMillis());
                        try {
                            FileOutputStream fos = new FileOutputStream(file);
                            Bitmap ThumbBitmap = ThumbnailUtils.createVideoThumbnail(videoPath, 3);
                            ThumbBitmap.compress(CompressFormat.JPEG, 100, fos);
                            fos.close();
                            sendVideoMessage(videoPath, file.getAbsolutePath(), duration);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case REQUEST_CODE_SELECT_FILE: //发送选中的文件
                    if (data != null) {
                        Uri uri = data.getData();
                        if (uri != null) {
                            sendFileByUri(uri);
                        }
                    }
                    break;
                default:
                    break;
            }
        }

    }

//    @Override
//    public void onEvent(EMNotifierEvent event) {
//        switch (event.getEvent()) {
//            case EventNewCMDMessage:
//                EMMessage cmdMessage = (EMMessage) event.getData();
//                CmdMessageBody cmdMsgBody = (CmdMessageBody) cmdMessage.getBody();
//                final String action = cmdMsgBody.action;//获取自定义action
//                break;
//        }
//        super.onEvent(event);
//    }

    /**
     * 刷新UI界面
     */
    public void refreshUI() {
        messageList.refresh();
    }

    /**
     * 为消息对象添加扩展字段
     * params message   需要处理的消息对象
     */
    @Override
    public void onSetMessageAttributes(EMMessage message) {
        if (isRobot) {
            //设置消息扩展属性
            message.setAttribute("em_robot_message", isRobot);
        }
        // 根据当前状态是否是阅后即焚状态来设置发送消息的扩展
        //delete jessica
//        if (isReadFire && (message.getType() == EMMessage.Type.TXT
//                || message.getType() == EMMessage.Type.IMAGE
//                || message.getType() == EMMessage.Type.VOICE)) {
//            message.setAttribute(EaseConstant.EASE_ATTR_READFIRE, true);
//        }

    }

    @Override
    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
        //设置自定义listview item提供者
        return new CustomChatRowProvider();
    }

    @Override
    public void onEnterToChatDetails() {

    }

    @Override
    public void onAvatarClick(String username) {
        //头像点击事件
        Intent intent = new Intent(getContext(), PersonDetailActivity2.class);
        intent.putExtra("HXAccountId",username);
        intent.putExtra("ClassId","");
        startActivity(intent);
    }

    @Override
    public void onAvatarLongClick(String username) {

    }

    @Override
    public boolean onMessageBubbleClick(final EMMessage message) {
        return false;
    }

    @Override
    public void onMessageBubbleLongClick(EMMessage message) {
        //消息框长按
        startActivityForResult((new Intent(getActivity(), ContextMenuActivity.class)).putExtra("message", message),
                REQUEST_CODE_CONTEXT_MENU);
    }

    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {
        switch (itemId) {
            case ITEM_VIDEO: //视频
                break;
            case ITEM_FILE: //一般文件

                break;
            case ITEM_VOICE_CALL: //音频通话

                break;
            case ITEM_VIDEO_CALL: //视频通话

                break;
            case ITEM_READFIRE:

                break;
            case ITEM_SEND_MONEY://发送红包

                break;
            default:
                break;
        }
        //不覆盖已有的点击事件
        return false;
    }

    /**
     * chat row provider
     */
    private final class CustomChatRowProvider implements EaseCustomChatRowProvider {
        @Override
        public int getCustomChatRowTypeCount() {
            //红包、音、视频通话发送、接收共8种
            return 8;
        }

        @Override
        public int getCustomChatRowType(EMMessage message) {
            return 0;
        }

        @Override
        public EaseChatRow getCustomChatRow(EMMessage message, int position, BaseAdapter adapter) {

            return null;
        }
    }

}
