package com.softtek.lai.module.bodygame3.home.view;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.hyphenate.chat.EMClient;
import com.softtek.lai.R;
import com.softtek.lai.chat.Constant;
import com.softtek.lai.chat.adapter.ChatContantAdapter;
import com.softtek.lai.chat.model.ChatContactInfoModel;
import com.softtek.lai.chat.ui.ChatActivity;
import com.softtek.lai.chat.ui.SeceltGroupSentActivity;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.conversation.adapter.ContactMenuAdapter;
import com.softtek.lai.module.login.net.LoginService;
import com.softtek.lai.widgets.CustomGridView;

import java.io.Serializable;
import java.util.List;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.fragment_contact)
public class ContactFragment extends LazyBaseFragment implements View.OnClickListener {
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.fl)
    FrameLayout fl;
    @InjectView(R.id.et_search)
    TextView et_search;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.list_contant)
    ListView list_contant;
    @InjectView(R.id.lin_group_send)
    LinearLayout lin_group_send;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.menu_gridview)
    CustomGridView menu_gridview;


    //通讯录联系人列表
    List<ChatContactInfoModel> list;
    //通讯录联系人列表适配器
    ChatContantAdapter adapter;

    ContactMenuAdapter menuAdapter;


    @Override
    protected void initViews() {
        ll_left.setVisibility(View.INVISIBLE);
        tv_title.setText("通讯录");

    }

    @Override
    protected void initDatas() {
        //顶上几个菜单列表
        menuAdapter = new ContactMenuAdapter(getActivity());
        menu_gridview.setAdapter(menuAdapter);

        lin_group_send.setOnClickListener(this);

        list_contant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean isLogin = EMClient.getInstance().isLoggedInBefore();
                if (isLogin) {
                    ChatContactInfoModel model = list.get(position);
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
                    intent.putExtra(Constant.EXTRA_USER_ID, model.getHXAccountId().toLowerCase());
                    intent.putExtra("name", model.getUserName());
                    intent.putExtra("photo", path + model.getPhoto());
                    startActivity(intent);
                } else {
                    Util.toastMsg("会话功能开通中，请稍后再试");
                }
            }
        });

    }

    @Override
    protected void lazyLoad() {
        Log.i("ContactFragment 加载数据");
        //加载数据
        loadingData();
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        if(getContext() instanceof BodyGameActivity){
            BodyGameActivity activity=(BodyGameActivity)getContext();
            activity.setAlpha(1);
        }
    }

    private void loadingData() {
        dialogShow("加载中");
        LoginService service = ZillaApi.NormalRestAdapter.create(LoginService.class);
        String token = UserInfoModel.getInstance().getToken();
        service.getEMchatContacts(token, new Callback<ResponseData<List<ChatContactInfoModel>>>() {
            @Override
            public void success(ResponseData<List<ChatContactInfoModel>> userResponseData, Response response) {
                int status = userResponseData.getStatus();
                dialogDissmiss();
                switch (status) {
                    case 200:
                        list = userResponseData.getData();
                        adapter = new ChatContantAdapter(getContext(), list);
                        list_contant.setAdapter(adapter);
                        break;
                    case 201:

                        break;
                    default:
                        Util.toastMsg(userResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                dialogDissmiss();
                ZillaApi.dealNetError(error);
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lin_group_send:
                boolean isLogin = EMClient.getInstance().isLoggedInBefore();
                if (isLogin) {
                    Intent intent = new Intent(getActivity(), SeceltGroupSentActivity.class);
                    intent.putExtra("list", (Serializable) list);
                    startActivity(intent);
                } else {
                    Util.toastMsg("会话异常，请稍后再试");
                }

                break;
        }

    }


}
