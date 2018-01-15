package com.softtek.lai.module.bodygame3.more.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.more.model.ClassGroup;
import com.softtek.lai.module.bodygame3.more.net.MoreService;
import com.softtek.lai.module.customermanagement.adapter.GroupRecyclerViewAdapter;
import com.softtek.lai.module.customermanagement.view.MakiBaseActivity;
import com.softtek.lai.utils.RequestCallback;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jia.lu on 1/4/2018.
 */

public class CreateGroupActivity extends MakiBaseActivity implements View.OnClickListener {
    private LinearLayout mBack;
    private TextView mTitle;
    private TextView mPass;
    private RecyclerView mRecyclerView;
    private Button mCreate;
    private Button mSubmit;
    private GroupRecyclerViewAdapter adapter;
    private List<String> groupNames = new ArrayList<>();
    private AlertDialog groupDialog;
    private String classId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        initView();
        initData();
    }

    private void initView() {
        mBack = findViewById(R.id.ll_left);
        mTitle = findViewById(R.id.tv_title);
        mPass = findViewById(R.id.tv_right);
        mRecyclerView = findViewById(R.id.rcv_content);
        mCreate = findViewById(R.id.btn_create);
        mSubmit = findViewById(R.id.btn_submit);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        adapter = new GroupRecyclerViewAdapter(groupNames, this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
        mBack.setVisibility(View.INVISIBLE);
        mTitle.setText("创建小组");
        mPass.setText("跳过");
        mCreate.setOnClickListener(this);
        mSubmit.setOnClickListener(this);
        mPass.setOnClickListener(this);
    }

    private void initData() {
        classId = getIntent().getStringExtra("classId");
    }

    private void showGroupDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_rename, null);
        final EditText mInput = dialogView.findViewById(R.id.edt_rename);
        TextView mDialogTitle = dialogView.findViewById(R.id.title);
        mDialogTitle.setText("创建小组");
        Button mRename = dialogView.findViewById(R.id.btn_rename);
        Button mCancel = dialogView.findViewById(R.id.btn_cancel);
        mRename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInput.getText().toString().trim().equals("")) {
                    Toast.makeText(CreateGroupActivity.this, "小组名不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    groupNames.add(mInput.getText().toString());
                    adapter.notifyDataSetChanged();
                    mInput.setText("");
                    groupDialog.dismiss();
                }
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groupDialog.dismiss();
            }
        });
        if (groupDialog == null) {
            groupDialog = new AlertDialog.Builder(this).create();
            groupDialog.setView(dialogView, 0, 0, 0, 0);
        }
        groupDialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                Intent intent = new Intent(CreateGroupActivity.this, ContactsActivity.class);
                intent.putExtra("classId", classId);
                intent.putExtra("createClass", true);
                startActivity(intent);
                break;
            case R.id.btn_create:
                showGroupDialog();
                break;
            case R.id.btn_submit:
                if (groupNames.size() < 1){
                    Toast.makeText(this,"请创建一个小组再保存哦",Toast.LENGTH_SHORT).show();
                    break;
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < groupNames.size(); i++) {
                    sb.append(groupNames.get(i));
                    if (i != groupNames.size() - 1) {
                        sb.append(",");
                    }
                }
                ZillaApi.NormalRestAdapter.create(MoreService.class)
                        .addGroup(classId, UserInfoModel.getInstance().getToken(),
                                classId,
                                sb.toString(),
                                UserInfoModel.getInstance().getUserId(),
                                new RequestCallback<ResponseData<ClassGroup>>() {
                                    @Override
                                    public void success(ResponseData<ClassGroup> responseData, Response response) {
                                        if (responseData.getStatus() == 200) {
                                            Intent intent = new Intent(CreateGroupActivity.this, ContactsActivity.class);
                                            intent.putExtra("classId", classId);
                                            intent.putExtra("createClass", true);
                                            startActivity(intent);
                                        } else {
                                            Util.toastMsg(responseData.getMsg());
                                        }
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        super.failure(error);
                                    }
                                });

                break;
        }
    }

    @Override
    public void onBackPressed() {

    }
}
