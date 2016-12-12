package com.softtek.lai.module.bodygame3.conversation.view;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.conversation.adapter.FriendAdapter;
import com.softtek.lai.module.bodygame3.conversation.model.FriendModel;
import com.softtek.lai.module.bodygame3.conversation.service.ContactService;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jessica.zhang on 2016/11/30.
 */

@InjectLayout(R.layout.activity_newfriend)
public class NewFriendActivity extends BaseActivity implements View.OnClickListener {
    public static final String TAG = "NewFriendActivity";
//    @InjectView(R.id.toolbar)
//    Toolbar toolbar;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.friend_list)
    ListView friend_list;

    private List<FriendModel> friendList;

    private FriendAdapter friendAdapter;

    @InjectView(R.id.swipe_layout)
    SwipeRefreshLayout swipeRefreshLayout;


    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            swipeRefreshLayout.setRefreshing(false);
            switch (msg.what) {
                case 0:
                    refresh();
                    break;
                case 1:
                    Toast.makeText(NewFriendActivity.this, "获取数据失败！", Toast.LENGTH_LONG).show();
                    break;

                default:
                    break;
            }
        }
    };

    private void refresh() {
        getNewFriendList();
    }


    @Override
    protected void initViews() {
//        if (DisplayUtil.getSDKInt() > 18) {
//            int status = DisplayUtil.getStatusHeight(this);
//            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) toolbar.getLayoutParams();
//            params.topMargin = status;
//            toolbar.setLayoutParams(params);
//        }
        ll_left.setVisibility(View.VISIBLE);
        ll_left.setOnClickListener(this);
        tv_title.setText("新朋友");
    }

    @Override
    protected void initDatas() {
        friendList = new ArrayList<FriendModel>();
        friendAdapter = new FriendAdapter(NewFriendActivity.this, friendList, handler);
        friend_list.setAdapter(friendAdapter);

        getNewFriendList();//获取网络数据

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
                            handler.sendEmptyMessage(0);
                        } catch (Exception e) {
                            e.printStackTrace();
                            handler.sendEmptyMessage(1);
                        }
                    }
                }.start();
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

    //获取新朋友列表
    private void getNewFriendList() {
        friendList.clear();
        ContactService service = ZillaApi.NormalRestAdapter.create(ContactService.class);
        Log.i(TAG, (UserInfoModel.getInstance().getToken() + " ++++ " + UserInfoModel.getInstance().getUserId()));
        service.getFriendApplyList(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), new Callback<ResponseData<List<FriendModel>>>() {
            @Override
            public void success(ResponseData<List<FriendModel>> listResponseData, Response response) {
                friendList.addAll(listResponseData.getData());

                Log.i(TAG, "friendList = " + new Gson().toJson(friendList));
                friendAdapter.updateData(friendList);
            }

            @Override
            public void failure(RetrofitError error) {
                ZillaApi.dealNetError(error);
            }
        });
    }
}
