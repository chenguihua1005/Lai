package com.softtek.lai.module.bodygame3.more.view;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
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
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_search_friend)
public class SearchFriendActivity extends BaseActivity implements AdapterView.OnItemClickListener,PullToRefreshBase.OnRefreshListener2<ListView>, View.OnClickListener {
    @InjectView(R.id.edit)
    EditText edit;
    @InjectView(R.id.lv)
    PullToRefreshListView lv;
    @InjectView(R.id.im_search_bottom)
    ImageView im_search_bottom;
    @InjectView(R.id.tv_cancel)
    TextView tv_cancel;

    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    int pageIndex=1;
    SearchFriendService searchFriendService;
    EasyAdapter<UserListModel>adapter;
    List<UserListModel>userListModels=new ArrayList<UserListModel>();

    @Override
    protected void initViews() {
        tv_title.setText("添加好友");
        ll_left.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        im_search_bottom.setOnClickListener(this);
        lv.setOnItemClickListener(this);
        lv.setMode(PullToRefreshBase.Mode.BOTH);
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
        searchFriendService= ZillaApi.NormalRestAdapter.create(SearchFriendService.class);

    }

    @Override
    protected void initDatas() {
        final String url= AddressManager.get("photoHost");
        adapter=new EasyAdapter<UserListModel>(this,userListModels,R.layout.search_friend_item) {
            @Override
            public void convert(ViewHolder holder, final UserListModel data, int position) {
                TextView tv_name=holder.getView(R.id.tv_name);
                tv_name.setText(data.getUsername());
                CircleImageView head_image=holder.getView(R.id.head_image);
                if (!TextUtils.isEmpty(data.getPhotoUrl()))
                {
                    Picasso.with(getParent()).load(url+data.getPhotoUrl()).placeholder(R.drawable.img_default)
                            .error(R.drawable.img_default).into(head_image);
                }else {
                    Picasso.with(getParent()).load(R.drawable.img_default);
                }
                TextView tv_certificate=holder.getView(R.id.tv_certificate);
                switch (data.getRoleId())
                {
                    case 0:
                        tv_certificate.setText("（未认证）");
                        break;
                    case 1:
                        tv_certificate.setText("（助教）");
                        break;
                    case 2:
                        tv_certificate.setText("（教练）");
                        break;
                    case 3:
                        tv_certificate.setText("（总教练）");
                        break;
                }
                TextView tv_phone=holder.getView(R.id.tv_phone);
                tv_phone.setText(data.getPhoneNo());
                TextView tv_isfriend_state=holder.getView(R.id.tv_isfriend_state);
                switch (data.getIsFriend())
                {
                    case 0:
                        tv_isfriend_state.setText("加好友");
                        break;
                    case 1:
                        tv_isfriend_state.setText("已添加");
                        break;
                    case 2:
                        tv_isfriend_state.setText("已发送");
                        break;
                }
                tv_isfriend_state.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (data.getIsFriend()==0)
                        {
//                            ContactService contactService=ZillaApi.NormalRestAdapter.create(ContactService.class);
//                            contactService.sentFriendApply(UserInfoModel.getInstance().getToken(),);
                            Util.toastMsg("加个好友吧");
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
        pageIndex=1;
        userListModels.clear();
        doGetService();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex++;
        doGetService();


    }
    private void doGetService()
    {
        searchFriendService.doFindFriends(UserInfoModel.getInstance().getToken(), edit.getText().toString(), pageIndex, new RequestCallback<ResponseData<SearchFriendModel>>() {
            @Override
            public void success(ResponseData<SearchFriendModel> searchFriendModelResponseData, Response response) {
                try {
                    int status=searchFriendModelResponseData.getStatus();
                    lv.onRefreshComplete();
                    switch (status)
                    {
                        case 200:
                            userListModels.addAll(searchFriendModelResponseData.getData().getUserList());
                            adapter.notifyDataSetChanged();
                            Util.toastMsg(searchFriendModelResponseData.getMsg());
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
        switch (view.getId())
        {
            case R.id.im_search_bottom:
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
                break;

        }
    }
}
