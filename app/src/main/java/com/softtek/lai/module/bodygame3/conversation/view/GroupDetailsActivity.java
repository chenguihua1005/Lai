package com.softtek.lai.module.bodygame3.conversation.view;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyphenate.chat.EMGroup;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.conversation.adapter.MemberAdapter;
import com.softtek.lai.module.bodygame3.conversation.database.ClassMemberTable;
import com.softtek.lai.module.bodygame3.conversation.database.ClassMemberUtil;
import com.softtek.lai.module.bodygame3.conversation.model.ClassListInfoModel;
import com.softtek.lai.module.bodygame3.conversation.model.ClassMemberModel;
import com.softtek.lai.module.bodygame3.conversation.model.ContactClassModel;
import com.softtek.lai.module.bodygame3.conversation.service.ContactService;
import com.softtek.lai.module.bodygame3.head.view.PersonDetailActivity2;
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
 * Created by jessica.zhang on 2016/11/29.
 */

/**
 * 组详情页面
 */
@InjectLayout(R.layout.activity_groups_detail)
public class GroupDetailsActivity extends BaseActivity implements View.OnClickListener {
    public static final String TAG = "GroupDetailsActivity";
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.group_list)
    ListView group_list;

//    @InjectView(R.id.swipe_layout)
//    SwipeRefreshLayout swipeRefreshLayout;

    public static GroupDetailsActivity instance;
    private String groupId;
    private EMGroup group;

    private MemberAdapter memberAdapter;
    private List<ClassMemberModel> members;

    private ClassListInfoModel classListInfoModel;
    private String classId;

    private ContactClassModel classModel;


    @Override
    protected void initViews() {
        if (DisplayUtil.getSDKInt() > 18) {
            int status = DisplayUtil.getStatusHeight(this);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) toolbar.getLayoutParams();
            params.topMargin = status;
            toolbar.setLayoutParams(params);
        }
        ll_left.setVisibility(View.VISIBLE);
        ll_left.setOnClickListener(GroupDetailsActivity.this);
        tv_title.setText("群聊成员");

    }

    @Override
    protected void initDatas() {
        classModel = (ContactClassModel) getIntent().getSerializableExtra("classModel");
        if (classModel != null) {
            classId = classModel.getClassId();

            Log.i(TAG, "classModel = " + new Gson().toJson(classModel));

//        group = EMClient.getInstance().groupManager().getGroup(groupId);
//        if (group == null) {
//            finish();
//            return;
//        }


//        GroupChangeListener groupChangeListener = new GroupChangeListener();
//        EMClient.getInstance().groupManager().addGroupChangeListener(groupChangeListener);

            members = new ArrayList<ClassMemberModel>();
            memberAdapter = new MemberAdapter(members, this);
            group_list.setAdapter(memberAdapter);
            group_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(GroupDetailsActivity.this, PersonDetailActivity2.class);
                    ClassMemberModel classMemberModel = memberAdapter.getItem(i);

                    intent.putExtra("isFriend", classMemberModel.getIsFriend());//1： 好友
                    intent.putExtra("AccountId", classMemberModel.getAccountId());
                    intent.putExtra("HXAccountId", classMemberModel.getHXAccountId());
                    intent.putExtra("UserName", classMemberModel.getUserName());
                    intent.putExtra("AFriendId", classMemberModel.getAFriendId());
                    intent.putExtra("ClassId", classModel.getClassId());


//                    intent.putExtra("classMemberModel", classMemberModel);
                    startActivity(intent);

                }
            });
            getClassMembers(classId);
        }

    }

    private void getClassMembers(String classId) {
        members.clear();
        String token = UserInfoModel.getInstance().getToken();

        try {
            ContactService service = ZillaApi.NormalRestAdapter.create(ContactService.class);
            service.GetContactsByClassId(classId,token, classId, 1, 100, new Callback<ResponseData<ClassListInfoModel>>() {
                @Override
                public void success(ResponseData<ClassListInfoModel> listResponseData, Response response) {
                    classListInfoModel = listResponseData.getData();
                    if (classListInfoModel != null) {
                        members.addAll(classListInfoModel.getContactList());
                    }
                    memberAdapter.updateData(members);


                    if (members != null && members.size() > 0) {
                        Log.i(TAG, "here ..........");
                        //存入数据库
                        if (ClassMemberUtil.getInstance().tableIsExist(ClassMemberTable.TABLE_NAME)) {
                            com.github.snowdream.android.util.Log.i(TAG, "存在。。。。。");
                            ClassMemberUtil.getInstance().insert(members);
                        } else {
                            com.github.snowdream.android.util.Log.i(TAG, "不存在。。。。。");
                        }
                    }

                }

                @Override
                public void failure(RetrofitError error) {
                    ZillaApi.dealNetError(error);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
        }
    }


//    private class GroupChangeListener implements EMGroupChangeListener {
//
//        @Override
//        public void onInvitationReceived(String groupId, String groupName,
//                                         String inviter, String reason) {
//            // TODO Auto-generated method stub
//
//        }
//
//        @Override
//        public void onRequestToJoinReceived(String s, String s1, String s2, String s3) {
//
//        }
//
//        @Override
//        public void onRequestToJoinAccepted(String s, String s1, String s2) {
//
//        }
//
//        @Override
//        public void onRequestToJoinDeclined(String s, String s1, String s2, String s3) {
//
//        }
//
////        @Override
////        public void onApplicationReceived(String groupId, String groupName,
////                                          String applyer, String reason) {
////            // TODO Auto-generated method stub
////
////        }
////
////        @Override
////        public void onApplicationAccept(String groupId, String groupName,
////                                        String accepter) {
////            // TODO Auto-generated method stub
////
////        }
////
////        @Override
////        public void onApplicationDeclined(String groupId, String groupName,
////                                          String decliner, String reason) {
////
////        }
//
//        @Override
//        public void onInvitationAccepted(String groupId, String inviter, String reason) {
//            runOnUiThread(new Runnable() {
//
//                @Override
//                public void run() {
////                    refreshMembers();
//                }
//
//            });
//
//        }
//
//        @Override
//        public void onInvitationDeclined(String groupId, String invitee,
//                                         String reason) {
//            // TODO Auto-generated method stub
//
//        }
//
//        @Override
//        public void onUserRemoved(String groupId, String groupName) {
//            finish();
//
//        }
//
//        @Override
//        public void onGroupDestroyed(String groupId, String groupName) {
//            finish();
//
//        }
//
//        @Override
//        public void onAutoAcceptInvitationFromGroup(String groupId, String inviter, String inviteMessage) {
//            // TODO Auto-generated method stub
//
//        }
//
//    }

}
