package com.softtek.lai.module.bodygame3.conversation.view;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
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
import com.softtek.lai.module.bodygame3.home.view.ContactFragment;
import com.softtek.lai.widgets.com.baoyz.swipemenulistview.SwipeMenu;
import com.softtek.lai.widgets.com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.softtek.lai.widgets.com.baoyz.swipemenulistview.SwipeMenuItem;
import com.softtek.lai.widgets.com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

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
    SwipeMenuListView friend_list;

    private List<FriendModel> friendList;

    private FriendAdapter friendAdapter;

    @InjectView(R.id.swipe_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @InjectView(R.id.tip_tv)
    TextView tip_tv;


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

        SpannableString spannableString = new SpannableString(this.getResources().getString(R.string.tip));
        Drawable drawable = getResources().getDrawable(R.drawable.law_tip_gray);
        drawable.setBounds(0, 0, 50, 50);
        spannableString.setSpan(new ImageSpan(drawable), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tip_tv.setText(spannableString);


//        SpannableStringBuilder builder = new SpannableStringBuilder(this.getResources().getString(R.string.tip));
//        builder.setSpan(new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM), 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//        tip_tv.setText(builder);

    }

    @Override
    protected void initDatas() {
        friendList = new ArrayList<FriendModel>();
        friendAdapter = new FriendAdapter(NewFriendActivity.this, friendList, handler);
        friend_list.setAdapter(friendAdapter);

        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(70));
                // set item title
                deleteItem.setTitle("删除");
                // set item title fontsize
                deleteItem.setTitleSize(13);
                // set item title font color
                deleteItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        friend_list.setMenuCreator(creator);

        friend_list.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        //删除数据
                        FriendModel friendModel = friendList.get(position);

                        final ProgressDialog pd = new ProgressDialog(NewFriendActivity.this);
                        String str1 = getResources().getString(R.string.Is_sending_a_request);
                        pd.setMessage(str1);
                        pd.setCanceledOnTouchOutside(false);
                        pd.show();
                        ContactService service = ZillaApi.NormalRestAdapter.create(ContactService.class);
                        service.removeFriendApplyInfo(UserInfoModel.getInstance().getToken(), friendModel.getApplyId(), new Callback<ResponseData>() {
                            @Override
                            public void success(ResponseData responseData, Response response) {
                                pd.dismiss();
                                int status = responseData.getStatus();
                                if (200 == status) {
                                    friendList.remove(position);
                                    friendAdapter.notifyDataSetChanged();
                                } else {
                                    Util.toastMsg(responseData.getMsg());
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                pd.dismiss();
                                ZillaApi.dealNetError(error);
                            }
                        });
                        break;
                }
                return false;
            }
        });

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


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                setResult(ContactFragment.REFRESH_UI);
                finish();
                break;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(ContactFragment.REFRESH_UI);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    //获取新朋友列表
    private void getNewFriendList() {
        friendList.clear();
        ContactService service = ZillaApi.NormalRestAdapter.create(ContactService.class);
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
