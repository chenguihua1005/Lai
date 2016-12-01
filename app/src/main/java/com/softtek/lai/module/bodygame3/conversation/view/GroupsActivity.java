package com.softtek.lai.module.bodygame3.conversation.view;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.exceptions.HyphenateException;
import com.softtek.lai.R;
import com.softtek.lai.chat.ui.ChatActivity;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.conversation.adapter.GroupAdapter;
import com.softtek.lai.module.bodygame3.conversation.model.ContactClassModel;
import com.softtek.lai.module.bodygame3.conversation.service.ContactService;
import com.softtek.lai.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jessica.zhang on 2016/11/28.
 */

@InjectLayout(R.layout.activity_groups)
public class GroupsActivity extends BaseActivity implements View.OnClickListener {
    public static final String TAG = "GroupsActivity";
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.group_list)
    ListView group_list;

    @InjectView(R.id.iv_email)
    ImageView iv_email;
    @InjectView(R.id.img_red)
    ImageView img_red;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;

    @InjectView(R.id.swipe_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    public static GroupsActivity instance;
    private List<ContactClassModel> classModels;


//    protected List<EMGroup> grouplist;

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
        if (DisplayUtil.getSDKInt() > 18) {
            int status = DisplayUtil.getStatusHeight(this);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) toolbar.getLayoutParams();
            params.topMargin = status;
            toolbar.setLayoutParams(params);
        }
        ll_left.setVisibility(View.VISIBLE);
        ll_left.setOnClickListener(this);
        tv_title.setText("群聊");
//        fl_right.setVisibility(View.VISIBLE);
//        iv_email.setBackground(ContextCompat.getDrawable(this, R.drawable.groupicon));
    }

    @Override
    protected void initDatas() {
        classModels = new ArrayList<ContactClassModel>();
        groupAdapter = new GroupAdapter(this, classModels);
        group_list.setAdapter(groupAdapter);

        getContactGroups();
        group_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(GroupsActivity.this, ChatActivity.class);
                // it is group chat
                intent.putExtra("chatType", EaseConstant.CHATTYPE_GROUP);
                intent.putExtra("userId", groupAdapter.getItem(i).getHXGroupId());
                intent.putExtra("name", groupAdapter.getItem(i).getClassName());//组名

                intent.putExtra("classId", groupAdapter.getItem(i).getClassId());

                intent.putExtra("classModel", groupAdapter.getItem(i));

//                intent.putExtra("userId", groupAdapter.getItem(i).getGroupId());
//                intent.putExtra("name", groupAdapter.getItem(i).getGroupName());
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
//        classModels = EMClient.getInstance().groupManager().getAllGroups();
//        groupAdapter = new GroupAdapter(this, classModels);
//        group_list.setAdapter(groupAdapter);
//        groupAdapter.notifyDataSetChanged();

        getContactGroups();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance = null;
    }

    public void back(View view) {
        finish();
    }


    /**
     * 获取群聊列表信息（即班级列表）
     */
    private void getContactGroups() {
        Log.i(TAG, UserInfoModel.getInstance().getToken() + "..." + UserInfoModel.getInstance().getUserId());

//        ZillaApi.NormalRestAdapter.create(MoreService.class).getMoreInfo(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), new Callback<ResponseData<List<ClassModel>>>() {
//            @Override
//            public void success(ResponseData<List<ClassModel>> listResponseData, Response response) {
//                classModels = listResponseData.getData();
//                Log.i(TAG, "classModels = " + classModels);
//                groupAdapter.updateData(classModels);
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                ZillaApi.dealNetError(error);
//
//            }
//        });

        ContactService service = ZillaApi.NormalRestAdapter.create(ContactService.class);
        service.GetClassListByAccountId(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId() + "", new Callback<ResponseData<List<ContactClassModel>>>() {
            @Override
            public void success(ResponseData<List<ContactClassModel>> listResponseData, Response response) {
                classModels = listResponseData.getData();
                Log.i(TAG, "classModels = " + classModels);
                if (classModels != null) {
                    groupAdapter.updateData(classModels);
                }

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
        }
    }

}
