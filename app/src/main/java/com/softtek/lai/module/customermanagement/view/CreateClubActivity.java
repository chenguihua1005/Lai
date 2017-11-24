package com.softtek.lai.module.customermanagement.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.softtek.lai.R;
import com.softtek.lai.module.customermanagement.adapter.InviteRecyclerViewAdapter;
import com.softtek.lai.module.customermanagement.model.InviteModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jia.lu on 11/21/2017.
 */

public class CreateClubActivity extends Activity implements View.OnClickListener{
    private LinearLayout mBack;
    private RecyclerView mRecyclerView;
    private InviteRecyclerViewAdapter inviteRecyclerViewAdapter;
    private List<InviteModel> inviteModelList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_club);
        initView();
    }

    private void initView(){
        mBack = findViewById(R.id.ll_left);
        mBack.setOnClickListener(this   );
        mRecyclerView = findViewById(R.id.rcv_content);
        for (int i = 0;i < 10;i++){
            InviteModel inviteModel = new InviteModel();
            inviteModel.setUsername("maki");
            inviteModel.setUserPhoto("233");
            inviteModel.setState((int)(Math.random() * 3));
            inviteModelList.add(inviteModel);
        }

        inviteRecyclerViewAdapter = new InviteRecyclerViewAdapter(inviteModelList, new InviteRecyclerViewAdapter.ItemListener() {
            @Override
            public void onItemClick(InviteModel item) {

            }
        },this);
        mRecyclerView.setAdapter(inviteRecyclerViewAdapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_left:
                finish();
                break;
        }
    }
}
