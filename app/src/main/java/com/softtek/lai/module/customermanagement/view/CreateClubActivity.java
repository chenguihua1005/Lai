package com.softtek.lai.module.customermanagement.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.customermanagement.adapter.InviteRecyclerViewAdapter;
import com.softtek.lai.module.customermanagement.model.ClubCreateResponse;
import com.softtek.lai.module.customermanagement.model.InviteModel;
import com.softtek.lai.module.customermanagement.service.ClubService;
import com.softtek.lai.utils.RequestCallback;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jia.lu on 11/21/2017.
 */

public class CreateClubActivity extends MakiBaseActivity implements View.OnClickListener{
    private EditText mClubName;
    private ClubService service;
    private TextView mConfim;
    private LinearLayout mBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_club);
        service = ZillaApi.NormalRestAdapter.create(ClubService.class);
        initView();
    }

    private void initView(){
        mClubName = findViewById(R.id.edt_name);
        mConfim = findViewById(R.id.tv_right);
        mBack = findViewById(R.id.ll_left);
        mConfim.setText("确定");
        mConfim.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mClubName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_GO){
                    createClub();
                    return true;
                }
                return false;
            }
        });
    }

    private void createClub(){
        if (mClubName.getText().toString().equals("")){
            Toast.makeText(CreateClubActivity.this,"请输入俱乐部名称",Toast.LENGTH_SHORT).show();
            return;
        }
        service.createClub(UserInfoModel.getInstance().getToken(), mClubName.getText().toString().trim(), new RequestCallback<ResponseData<ClubCreateResponse>>() {
            @Override
            public void success(ResponseData<ClubCreateResponse> responseData, Response response) {
                if (responseData.getStatus() == 200){
                    Toast.makeText(CreateClubActivity.this,"创建" + responseData.getData().getName() + "成功", Toast.LENGTH_LONG).show();
                    finish();
                }else {
                    Toast.makeText(CreateClubActivity.this,responseData.getMsg(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_right:
                createClub();
                break;
            case R.id.ll_left:
                finish();
                break;
        }
    }
}
