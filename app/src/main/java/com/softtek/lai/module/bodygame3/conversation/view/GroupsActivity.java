package com.softtek.lai.module.bodygame3.conversation.view;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;
import com.softtek.lai.R;
import com.softtek.lai.chat.Constant;
import com.softtek.lai.chat.ui.ChatActivity;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.bodygame3.conversation.adapter.GroupAdapter;

import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jessica.zhang on 2016/11/28.
 */

@InjectLayout(R.layout.activity_groups)
public class GroupsActivity extends BaseActivity {
    public static final String TAG = "GroupsActivity";

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.group_list)
    ListView group_list;

    @InjectView(R.id.swipe_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    public static GroupsActivity instance;


    protected List<EMGroup> grouplist;
    private GroupAdapter groupAdapter;

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            swipeRefreshLayout.setRefreshing(false);
            switch (msg.what) {
                case 0:
                    refresh();
                    break;
                case 1:
                    Toast.makeText(GroupsActivity.this, R.string.Failed_to_get_group_chat_information, Toast.LENGTH_LONG).show();
                    break;

                default:
                    break;
            }
        }
    };


    @Override
    protected void initViews() {
        ll_left.setVisibility(View.INVISIBLE);
        tv_title.setText("群聊");
    }

    @Override
    protected void initDatas() {

        grouplist = EMClient.getInstance().groupManager().getAllGroups();

        Log.i(TAG, "grouplist = " + new Gson().toJson(grouplist));

        groupAdapter = new GroupAdapter(this, grouplist);
        group_list.setAdapter(groupAdapter);
        group_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(GroupsActivity.this, ChatActivity.class);
                // it is group chat
                intent.putExtra("chatType", Constant.CHATTYPE_GROUP);
                intent.putExtra("userId", groupAdapter.getItem(i).getGroupId());
                startActivityForResult(intent, 0);

            }
        });


        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.holo_blue_bright, R.color.holo_green_light,
                R.color.holo_orange_light, R.color.holo_red_light);
        //pull down to refresh
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
                            handler.sendEmptyMessage(0);
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                            handler.sendEmptyMessage(1);
                        }
                    }
                }.start();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        refresh();
        super.onResume();
    }

    private void refresh() {
        grouplist = EMClient.getInstance().groupManager().getAllGroups();
        groupAdapter = new GroupAdapter(this, grouplist);
        group_list.setAdapter(groupAdapter);
        groupAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance = null;
    }

    public void back(View view) {
        finish();
    }
}
