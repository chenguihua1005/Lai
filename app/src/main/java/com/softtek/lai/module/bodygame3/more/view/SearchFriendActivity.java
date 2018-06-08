package com.softtek.lai.module.bodygame3.more.view;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.conversation.service.ContactService;
import com.softtek.lai.module.bodygame3.more.model.SearchFriendModel;
import com.softtek.lai.module.bodygame3.more.model.UserListModel;
import com.softtek.lai.module.bodygame3.more.net.SearchFriendService;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.utils.SoftInputUtil;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_search_friend)
public class SearchFriendActivity extends BaseActivity implements AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener2<ListView>, View.OnClickListener, TextWatcher {
    @InjectView(R.id.edit)
    EditText edit;
    @InjectView(R.id.lv)
    PullToRefreshListView lv;
    @InjectView(R.id.im_search_bottom)
    ImageView im_search_bottom;
    @InjectView(R.id.tv_cancel)
    TextView tv_cancel;

    @InjectView(R.id.tip_tv)
    TextView tip_tv;

    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    int pageIndex = 1;
    SearchFriendService searchFriendService;
    EasyAdapter<UserListModel> adapter;
    List<UserListModel> userListModels = new ArrayList<UserListModel>();

    @Override
    protected void initViews() {
        tv_title.setText("添加好友");
        ll_left.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        im_search_bottom.setOnClickListener(this);
        lv.setOnItemClickListener(this);
        lv.setMode(PullToRefreshBase.Mode.DISABLED);
        lv.setOnRefreshListener(this);
//        lv.setEmptyView(ll_nomessage);
        ILoadingLayout startLabelse = lv.getLoadingLayoutProxy(true, false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setRefreshingLabel("正在刷新数据");// 刷新时
        startLabelse.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        ILoadingLayout endLabelsr = lv.getLoadingLayoutProxy(false, true);
        endLabelsr.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
        endLabelsr.setRefreshingLabel("正在加载数据");
        endLabelsr.setReleaseLabel("松开立即加载");// 下来达到一定距离时，显示的提示
        searchFriendService = ZillaApi.NormalRestAdapter.create(SearchFriendService.class);
        edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                //判断是不是搜索
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                     /*隐藏软键盘*/
                    SoftInputUtil.hidden(SearchFriendActivity.this);
                    UserModel user = UserInfoModel.getInstance().getUser();
                    if (edit.length() == 0) {
                        edit.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                edit.requestFocus();
                            }
                        }, 500);
                        edit.setError(Html.fromHtml("<font color=#FFFFFF>请输入姓名/手机号</font>"));
                        return false;
                    } else if (edit.getText().toString().equals(user.getMobile()) ||
                            edit.getText().toString().equals(user.getCertification())) {
                        edit.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                edit.requestFocus();
                            }
                        }, 500);
                        edit.setError(Html.fromHtml("<font color=#FFFFFF>无此用户</font>"));
                        return false;
                    }
                    lv.setMode(PullToRefreshBase.Mode.BOTH);
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            lv.setRefreshing();
                        }

                    }, 300);
                    return true;
                }
                return false;
            }
        });
        edit.addTextChangedListener(this);

        SpannableString spannableString = new SpannableString(this.getResources().getString(R.string.tip));
        Drawable drawable = getResources().getDrawable(R.drawable.law_tip_gray);
        drawable.setBounds(0, 0, 50, 50);
        spannableString.setSpan(new ImageSpan(drawable), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tip_tv.setText(spannableString);
    }

    @Override
    protected void initDatas() {
//        tip_tv.clearFocus();
//        SoftInputUtil.hidden(SearchFriendActivity.this);

        adapter = new EasyAdapter<UserListModel>(this, userListModels, R.layout.search_friend_item) {
            @Override
            public void convert(ViewHolder holder, final UserListModel data, final int position) {
                TextView tv_name = holder.getView(R.id.tv_name);
                tv_name.setText(data.getUsername());
                ImageView im_gender = holder.getView(R.id.im_gender);
                if (data.getGender() == 1) {
                    im_gender.setImageResource(R.drawable.female_iv);
                } else {
                    im_gender.setImageResource(R.drawable.male_iv);
                }
                CircleImageView head_image = holder.getView(R.id.head_image);
                if (!TextUtils.isEmpty(data.getPhotoUrl())) {
                    Picasso.with(SearchFriendActivity.this).load(data.getPhotoUrl()).placeholder(R.drawable.img_default)
                            .error(R.drawable.img_default).into(head_image);
                } else {
                    Picasso.with(SearchFriendActivity.this).load(R.drawable.img_default);
                }
                TextView tv_phone = holder.getView(R.id.tv_phone);
                tv_phone.setText(data.getPhoneNo());
                final TextView tv_isfriend_state = holder.getView(R.id.tv_isfriend_state);
                switch (data.getIsFriend()) {
                    case 0:
                        tv_isfriend_state.setTextColor(0xffffffff);
                        tv_isfriend_state.setText("添加");
                        tv_isfriend_state.setBackground(getResources().getDrawable(R.drawable.bg_addguy_btn));
                        break;
                    case 1:
                        tv_isfriend_state.setTextColor(0xffB2B2B2);
                        tv_isfriend_state.setText("已添加");
                        tv_isfriend_state.setBackground(null);
                        break;
                    case 2:
                        tv_isfriend_state.setTextColor(0xffB2B2B2);
                        tv_isfriend_state.setText("已发送");
                        tv_isfriend_state.setBackground(null);
                        break;
                }
                tv_isfriend_state.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (data.getIsFriend() == 0) {
                            ContactService contactService = ZillaApi.NormalRestAdapter.create(ContactService.class);
                            contactService.doSentFriendApply(UserInfoModel.getInstance().getToken(), "", UserInfoModel.getInstance().getUserId(),
                                    Long.parseLong(data.getAccountId()), "", 1, new RequestCallback<ResponseData>() {
                                        @Override
                                        public void success(ResponseData responseData, Response response) {
                                            int status = responseData.getStatus();
                                            switch (status) {
                                                case 200:
                                                    tv_isfriend_state.setTextColor(0xffB2B2B2);
                                                    tv_isfriend_state.setText("已发送");
                                                    tv_isfriend_state.setBackground(null);
                                                    userListModels.get(position).setIsFriend(2);
                                                    break;
                                                default:
                                                    Util.toastMsg(responseData.getMsg());
                                                    break;
                                            }
                                        }
                                    });
                        }
                    }
                });


            }
        };
        lv.setAdapter(adapter);


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex = 1;
        userListModels.clear();
        doGetService();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex++;
        doGetService();


    }

    private void doGetService() {
        searchFriendService.doFindFriends(UserInfoModel.getInstance().getToken(), edit.getText().toString(), pageIndex, new RequestCallback<ResponseData<SearchFriendModel>>() {
            @Override
            public void success(ResponseData<SearchFriendModel> searchFriendModelResponseData, Response response) {
                try {
                    int status = searchFriendModelResponseData.getStatus();
                    lv.onRefreshComplete();
                    switch (status) {
                        case 200:
                            userListModels.addAll(searchFriendModelResponseData.getData().getUserList());
                            if (userListModels == null || userListModels.isEmpty()) {
                                UserModel user = UserInfoModel.getInstance().getUser();
                                if (edit.getText().toString().equals(user.getNickname())) {
                                    edit.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            edit.requestFocus();
                                        }
                                    }, 500);
                                    edit.setError(Html.fromHtml("<font color=#FFFFFF>无此用户</font>"));
                                } else {
                                    Util.toastMsg("无此用户只支持精确查询");
                                }

                            }
                            adapter.notifyDataSetChanged();
                            break;
                        default:
                            Util.toastMsg(searchFriendModelResponseData.getMsg());
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_search_bottom:
                UserModel user = UserInfoModel.getInstance().getUser();
                if (edit.length() == 0) {
                    edit.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            edit.requestFocus();
                        }
                    }, 500);
                    edit.setError(Html.fromHtml("<font color=#FFFFFF>请输入姓名/手机号</font>"));
                    return;
                } else if (edit.getText().toString().equals(user.getMobile()) ||
                        edit.getText().toString().equals(user.getCertification())) {
                    edit.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            edit.requestFocus();
                        }
                    }, 500);
                    edit.setError(Html.fromHtml("<font color=#FFFFFF>无此用户</font>"));
                    return;
                }
                lv.setMode(PullToRefreshBase.Mode.BOTH);
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

                    @Override

                    public void run() {
                        lv.setRefreshing();
                    }

                }, 300);
                break;
            case R.id.ll_left:
                finish();
                break;
            case R.id.tv_cancel:
                finish();
                break;

        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        isOut = true;
    }

    boolean isOut;

    @Override
    public void afterTextChanged(Editable editable) {
        lv.setMode(PullToRefreshBase.Mode.DISABLED);
        isOut = false;
        String text = editable.toString();
        userListModels.clear();//清除列表数据
        if (text.trim().length() == 0) {//关键字为空时
            adapter.notifyDataSetChanged();
            return;
        }
        adapter.notifyDataSetChanged();


    }
}
