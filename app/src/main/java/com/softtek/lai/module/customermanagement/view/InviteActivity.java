package com.softtek.lai.module.customermanagement.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.customermanagement.adapter.InviteRecyclerViewAdapter;
import com.softtek.lai.module.customermanagement.model.InviteModel;
import com.softtek.lai.module.customermanagement.service.ClubService;
import com.softtek.lai.utils.RequestCallback;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jia.lu on 11/30/2017.
 */

public class InviteActivity extends MakiBaseActivity implements View.OnClickListener{
    private LinearLayout mBack;
    private RecyclerView mRecyclerView;
    private TextView mTitle;
    private TextView mInvite;
    private InviteRecyclerViewAdapter inviteRecyclerViewAdapter;
    private List<InviteModel.ItemsBean> inviteModelList = new ArrayList<>();
    private ClubService service;
    private int clubId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        service = ZillaApi.NormalRestAdapter.create(ClubService.class);
        initView();
        initData();
    }

    private void initView(){
        mBack = findViewById(R.id.ll_left);
        mTitle = findViewById(R.id.tv_title);
        mInvite = findViewById(R.id.tv_right);
        mInvite.setText("+");
        mInvite.setTextSize(getResources().getDimension(R.dimen.textSize13));
        mInvite.setOnClickListener(this);
        mRecyclerView = findViewById(R.id.rcv_content);
        mBack.setOnClickListener(this);
        mTitle.setText("邀请工作人员");
        inviteRecyclerViewAdapter = new InviteRecyclerViewAdapter(inviteModelList, new InviteRecyclerViewAdapter.ItemListener() {
            @Override
            public void onItemClick(InviteModel.ItemsBean item) {

            }
        }, new InviteRecyclerViewAdapter.InviteListener() {
            @Override
            public void onInviteClickListener(View view,int position) {

            }
        },this);
        mRecyclerView.setAdapter(inviteRecyclerViewAdapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
    }

    private void initData(){
        dialogShow("正在加载");
        Intent intent = getIntent();
        clubId = intent.getIntExtra("maki",-1);
        service.getListOfInvitedMessage(UserInfoModel.getInstance().getToken(), String.valueOf(clubId), 0, 999,
                new RequestCallback<ResponseData<InviteModel>>() {
                    @Override
                    public void success(ResponseData<InviteModel> responseData, Response response) {
                        if (responseData.getStatus() == 200){
                            inviteModelList.addAll(responseData.getData().getItems());
                            inviteRecyclerViewAdapter.notifyDataSetChanged();
                        }else {
                            Toast.makeText(InviteActivity.this,responseData.getMsg(),Toast.LENGTH_SHORT).show();
                        }
                        dialogDismiss();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        dialogDismiss();
                        super.failure(error);
                        Toast.makeText(InviteActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_left:
                finish();
                break;
            case R.id.tv_right:
                Intent intent = new Intent(this,FindAccountsActivity.class);
                intent.putExtra("maki",clubId);
                startActivity(intent);
        }
    }
}
