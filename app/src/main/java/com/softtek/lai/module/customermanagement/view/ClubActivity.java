package com.softtek.lai.module.customermanagement.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.softtek.lai.module.customermanagement.adapter.ChooseClubRecyclerViewAdapter;
import com.softtek.lai.module.customermanagement.adapter.ClueRecyclerViewAdapter;
import com.softtek.lai.module.customermanagement.model.ClubNameModel;
import com.softtek.lai.module.customermanagement.model.PersonnelModel;
import com.softtek.lai.widgets.MySwipRefreshView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jia.lu on 11/21/2017.
 */

public class ClubActivity extends Activity implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private TextView mCustomerNum;
    private TextView mCustomerToday;
    private TextView mInvitePersonnel;
    private TextView mChangeClubName;
    private TextView mCloseClub;
    private TextView mTitle;
    private LinearLayout mBack;
    private TextView mAddClub;
    private MySwipRefreshView mSwipRefreshView;
    private ClueRecyclerViewAdapter recyclerViewAdapter;
    private AlertDialog renameDialog;
    private List<PersonnelModel> personnelModelList = new ArrayList<>();
    private AlertDialog chooseDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club);
        initView();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.rcv_personnel_content);
        mCustomerNum = findViewById(R.id.tv_customer_number);
        mCustomerToday = findViewById(R.id.tv_customer_add);
        mInvitePersonnel = findViewById(R.id.tv_invite_personnel);
        mChangeClubName = findViewById(R.id.tv_change_club_name);
        mCloseClub = findViewById(R.id.tv_close_club);
        mTitle = findViewById(R.id.tv_title);
        mBack = findViewById(R.id.ll_left);
        mAddClub = findViewById(R.id.tv_right);
        mSwipRefreshView = findViewById(R.id.msrv_pull);
        mBack.setOnClickListener(this);
        mInvitePersonnel.setOnClickListener(this);
        mChangeClubName.setOnClickListener(this);
        mCloseClub.setOnClickListener(this);
        mAddClub.setOnClickListener(this);
        mTitle.setOnClickListener(this);
        for (int i = 0; i < 20; i++) {
            PersonnelModel personnelModel = new PersonnelModel();
            personnelModel.setCustomerSum(10);
            personnelModel.setCustomerToday(1 + i);
            personnelModel.setMarketSum(12);
            personnelModel.setMarketToday(2);
            personnelModel.setPersonnelPhone("123");
            personnelModel.setPersonnelName("maki");
            personnelModelList.add(personnelModel);
        }
        mSwipRefreshView.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        mSwipRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(ClubActivity.this, "pull success", Toast.LENGTH_SHORT).show();
                if (mSwipRefreshView.isRefreshing()) {
                    mSwipRefreshView.setRefreshing(false);
                }
            }
        });
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerViewAdapter = new ClueRecyclerViewAdapter(personnelModelList, new ClueRecyclerViewAdapter.ItemListener() {
            @Override
            public void onItemClick(PersonnelModel item) {

            }
        }, this);
        mRecyclerView.setAdapter(recyclerViewAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void createChangeDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_choose_club, null);
        RecyclerView clubRecyclerView = dialogView.findViewById(R.id.rcv_club);
        List<ClubNameModel> nameModels = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            ClubNameModel clubNameModel = new ClubNameModel();
            clubNameModel.setClubName("maki" + i);
            clubNameModel.setSelected(true);
            nameModels.add(clubNameModel);
        }
        ClubNameModel clubNameModel5 = new ClubNameModel();
        clubNameModel5.setClubName("maki5");
        clubNameModel5.setSelected(false);
        nameModels.add(clubNameModel5);
        ChooseClubRecyclerViewAdapter adapter = new ChooseClubRecyclerViewAdapter(nameModels, new ChooseClubRecyclerViewAdapter.ItemListener() {
            @Override
            public void onItemClick(ClubNameModel item,int position) {
                Toast.makeText(ClubActivity.this,position + "",Toast.LENGTH_SHORT).show();
            }
        },this);
        clubRecyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        clubRecyclerView.setLayoutManager(manager);
        if (chooseDialog == null) {
            chooseDialog = new AlertDialog.Builder(this).create();
            chooseDialog.setView(dialogView, 0, 0, 0, 0);
        }
        chooseDialog.show();
    }

    private void createRenameDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_rename, null);
        final EditText mInput = dialogView.findViewById(R.id.edt_rename);
        TextView mDialogTitle = dialogView.findViewById(R.id.title);
        Button mRename = dialogView.findViewById(R.id.btn_rename);
        Button mCancel = dialogView.findViewById(R.id.btn_cancel);

        mDialogTitle.setText("修改俱乐部名称");
        mRename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTitle.setText(mInput.getText());
                renameDialog.dismiss();
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                renameDialog.dismiss();
            }
        });
        if (renameDialog == null) {
            renameDialog = new AlertDialog.Builder(this).create();
            renameDialog.setView(dialogView, 0, 0, 0, 0);
        }
        renameDialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_invite_personnel:
                startActivity(new Intent(this, CreateClubActivity.class));
                break;
            case R.id.tv_change_club_name:
                createRenameDialog();
                break;
            case R.id.tv_close_club:
                break;
            case R.id.ll_left:
                finish();
                break;
            case R.id.tv_title:
                createChangeDialog();
                break;
        }
    }
}
