/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.chat;


import android.app.ProgressDialog;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.counselor.adapter.InviteContantAdapter;
import com.softtek.lai.module.counselor.model.ContactListInfoModel;
import com.softtek.lai.module.counselor.presenter.IStudentPresenter;
import com.softtek.lai.module.counselor.presenter.StudentImpl;
import com.softtek.lai.module.counselor.view.SearchContantActivity;
import com.softtek.lai.module.login.model.RoleInfo;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.net.LoginService;
import com.softtek.lai.utils.ACache;
import com.softtek.lai.utils.HanziToPinyin;

import org.greenrobot.eventbus.EventBus;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
@InjectLayout(R.layout.activity_chat_contant_list)
public class ContantListActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener, BaseFragment.OnFragmentInteractionListener {

    @LifeCircleInject
    ValidateLife validateLife;


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

    ChatContantAdapter adapter;
    List<ChatContactInfoModel> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_left.setOnClickListener(this);
        lin_group_send.setOnClickListener(this);
        fl.setOnClickListener(this);
        et_search.setOnClickListener(this);

        list = new ArrayList<ChatContactInfoModel>();
        setData();
        list_contant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChatContactInfoModel model = list.get(position);
                Intent intent = new Intent(ContantListActivity.this, ChatActivity.class);
                String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
                intent.putExtra(Constant.EXTRA_USER_ID, model.getHXAccountId());
                intent.putExtra("name", model.getUserName());
                intent.putExtra("photo", path + model.getPhoto());
                startActivity(intent);
            }
        });
    }

    private void setData() {
        dialogShow("加载中");
        LoginService service = ZillaApi.NormalRestAdapter.create(LoginService.class);
        String token = UserInfoModel.getInstance().getToken();
        service.getEMchatContacts(token, new Callback<ResponseData<List<ChatContactInfoModel>>>() {
            @Override
            public void success(ResponseData<List<ChatContactInfoModel>> userResponseData, Response response) {
                System.out.println("userResponseData:" + userResponseData);
                int status = userResponseData.getStatus();
                dialogDissmiss();
                switch (status) {
                    case 200:
                        list = userResponseData.getData();
                        adapter = new ChatContantAdapter(ContantListActivity.this, list);
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
                ZillaApi.dealNetError(error);
                error.printStackTrace();
                dialogDissmiss();
            }
        });

    }

    @Override
    protected void initViews() {
        //tv_left.setLayoutParams(new Toolbar.LayoutParams(DisplayUtil.dip2px(this,15),DisplayUtil.dip2px(this,30)));
        tv_title.setText("通讯录");

    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;

            case R.id.lin_group_send:
                Intent intent = new Intent(this, SeceltGroupSentActivity.class);
                intent.putExtra("list", (Serializable) list);
                startActivity(intent);
                break;
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView, failedRule);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
