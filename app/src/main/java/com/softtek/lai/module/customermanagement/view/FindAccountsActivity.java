package com.softtek.lai.module.customermanagement.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.softtek.lai.LaiApplication;
import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.customermanagement.adapter.InviteRecyclerViewAdapter;
import com.softtek.lai.module.customermanagement.model.InviteModel;
import com.softtek.lai.module.customermanagement.service.ClubService;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.utils.RequestCallback;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jia.lu on 12/1/2017.
 */

public class FindAccountsActivity extends MakiBaseActivity implements View.OnClickListener {
    private EditText mSearch;
    private LinearLayout mBack;
    private TextView mTitle;
    private TextView mConfim;
    private ClubService service;
    private RecyclerView mRecyclerView;
    private String clubId;
    private InviteRecyclerViewAdapter inviteRecyclerViewAdapter;
    private List<InviteModel.ItemsBean> inviteModelList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_account);
        Intent intent = getIntent();
        clubId = intent.getStringExtra("maki");
        service = ZillaApi.NormalRestAdapter.create(ClubService.class);
        initView();
    }

    private void initView() {
        mSearch = findViewById(R.id.edt_search);
        mBack = findViewById(R.id.ll_left);
        mTitle = findViewById(R.id.tv_title);
        mConfim = findViewById(R.id.tv_right);
        mRecyclerView = findViewById(R.id.rcv_content);
        mConfim.setText("搜索");
        mTitle.setText("搜索工作人员");
        mConfim.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    doSearch();
                    return true;
                }
                return false;
            }
        });
        inviteRecyclerViewAdapter = new InviteRecyclerViewAdapter(inviteModelList, new InviteRecyclerViewAdapter.ItemListener() {
            @Override
            public void onItemClick(InviteModel.ItemsBean item) {

            }
        }, new InviteRecyclerViewAdapter.InviteListener() {
            @Override
            public void onInviteClickListener(final View view, int position) {
                ((TextView) view).setText("待处理");
                view.setBackground(getResources().getDrawable(R.drawable.bg_invite_club_clicked));
                service.invitetoBeWorker(UserInfoModel.getInstance().getToken(), clubId, inviteModelList.get(position).getAccountId(), new RequestCallback<ResponseData>() {
                    @Override
                    public void success(ResponseData responseData, Response response) {
                        if (responseData.getStatus() == 200) {
                            view.setEnabled(false);
//                            inviteRecyclerViewAdapter.setClickable(false);
                            Toast.makeText(FindAccountsActivity.this, "邀请成功", Toast.LENGTH_SHORT).show();
                        } else {
//                            inviteRecyclerViewAdapter.setClickable(true);
                            Toast.makeText(FindAccountsActivity.this, responseData.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
//                        inviteRecyclerViewAdapter.setClickable(true);
                        Toast.makeText(FindAccountsActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
//                        super.failure(error);
                        dealNetError(error);
                    }
                });
            }
        }, this);
//        inviteRecyclerViewAdapter.setClickable(true);
        inviteRecyclerViewAdapter.setSearch(true);
        mRecyclerView.setAdapter(inviteRecyclerViewAdapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);

    }
    private void doSearch() {
        inviteModelList.clear();
        inviteRecyclerViewAdapter.setClickable(true);
        inviteRecyclerViewAdapter.setClickable(true);
        if (mSearch.getText().toString().equals("")) {
            Toast.makeText(this, "CN号或者电话号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        dialogShow("加载中");
        service.findAccounts(UserInfoModel.getInstance().getToken(), mSearch.getText().toString().trim(), 0, 999, new RequestCallback<ResponseData<InviteModel>>() {
            @Override
            public void success(ResponseData<InviteModel> responseData, Response response) {
                if (responseData.getStatus() == 200) {
                    inviteModelList.addAll(responseData.getData().getItems());
                    inviteRecyclerViewAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(FindAccountsActivity.this, responseData.getMsg(), Toast.LENGTH_SHORT).show();
                }
                dialogDismiss();
            }

            @Override
            public void failure(RetrofitError error) {
                dialogDismiss();
//                super.failure(error);
                dealNetError(error);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.tv_right:
                doSearch();
                break;
        }
    }
}
