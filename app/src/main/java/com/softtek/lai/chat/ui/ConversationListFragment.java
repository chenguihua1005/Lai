package com.softtek.lai.chat.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.domain.ChatUserInfoModel;
import com.hyphenate.easeui.domain.ChatUserModel;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.NetUtils;
import com.softtek.lai.R;
import com.softtek.lai.chat.Constant;
import com.softtek.lai.contants.Constants;

public class ConversationListFragment extends EaseConversationListFragment {
    private static final String TAG = "会话列表";

    private TextView errorText;

    @Override
    protected void initView() {
        super.initView();
        View errorView = View.inflate(getActivity(), R.layout.em_chat_neterror_item, null);
        errorItemContainer.addView(errorView);
        errorItemContainer.setVisibility(View.GONE);
        errorText = (TextView) errorView.findViewById(R.id.tv_connect_errormsg);
    }

    @Override
    protected void setUpView() {
        //隐藏toolbar
        super.hideTitleBar();
        // 注册上下文菜单
        registerForContextMenu(conversationListView);
        conversationListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EMConversation conversation = conversationListView.getItem(position);
                String username = conversation.conversationId().toLowerCase();

                if (username.equals(EMClient.getInstance().getCurrentUser()))
                    Toast.makeText(getActivity(), R.string.Cant_chat_with_yourself, Toast.LENGTH_SHORT).show();
                else {
                    //进入聊天页面
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    if (conversation.isGroup()) {
                        if (conversation.getType() == EMConversation.EMConversationType.ChatRoom) {
                            // it's group chat
                            intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_CHATROOM);
                        } else {
                            intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_GROUP);
                        }

                    }
                    String name = "";
                    String photo = "";
                    ChatUserModel chatUserModel = ChatUserInfoModel.getInstance().getUser();
                    String userId = chatUserModel.getUserId().toLowerCase();
                    EMMessage lastMessage = conversation.getLastMessage();
                    String f = lastMessage.getFrom().toLowerCase();
                    try {
                        name = lastMessage.getStringAttribute("nickname");
                        photo = lastMessage.getStringAttribute("avatarURL");
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                    }
                    if (f.equals(userId)) {
                        String str = conversation.getExtField();
                        if (!TextUtils.isEmpty(str)) {
                            String[] field = str.split(",");
                            if (field.length >= 2) {
                                name = field[0];
                                photo = field[1];
                            }
                        }
                    }

                    Log.i(TAG, "hxId = " + username + " name= " + name + " photo= " + photo);
                    intent.putExtra(Constant.EXTRA_USER_ID, username);//环信ID
                    intent.putExtra("name", name);
                    intent.putExtra("photo", photo);
                    startActivity(intent);
                }
            }
        });
        super.setUpView();
    }

    @Override
    protected void onConnectionDisconnected() {
        super.onConnectionDisconnected();
        if (NetUtils.hasNetwork(getActivity())) {
            errorText.setText(R.string.can_not_connect_chat_server_connection);
        } else {
            errorText.setText(R.string.the_current_network);
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.em_delete_message, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        boolean handled = false;
        boolean deleteMessage = false;
        /*if (item.getItemId() == R.id.delete_message) {
            deleteMessage = true;
            handled = true;
        } else*/
        if (item.getItemId() == R.id.delete_conversation) {
            deleteMessage = true;
            EMConversation tobeDeleteCons = conversationListView.getItem(((AdapterContextMenuInfo) item.getMenuInfo()).position);
            // 删除此会话
//            EMClient.getInstance().chatManager().deleteConversation(tobeDeleteCons.getUserName(), tobeDeleteCons.isGroup(), deleteMessage);

            //3.0    jessica
            EMClient.getInstance().chatManager().deleteConversation(tobeDeleteCons.conversationId().toLowerCase(), deleteMessage);

            refresh();
            int unreadNum = EMClient.getInstance().chatManager().getUnreadMsgsCount();
            Intent msgIntent = new Intent(Constants.MESSAGE_CHAT_ACTION);
            msgIntent.putExtra("count", unreadNum);
            getContext().sendBroadcast(msgIntent);
        }
        return true;
    }

}
