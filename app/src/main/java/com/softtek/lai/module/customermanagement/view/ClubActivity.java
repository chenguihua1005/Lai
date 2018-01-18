package com.softtek.lai.module.customermanagement.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.customermanagement.adapter.ChooseClubRecyclerViewAdapter;
import com.softtek.lai.module.customermanagement.adapter.ClueRecyclerViewAdapter;
import com.softtek.lai.module.customermanagement.model.ClubNameModel;
import com.softtek.lai.module.customermanagement.model.PersonnelModel;
import com.softtek.lai.module.customermanagement.service.ClubService;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.MySwipRefreshView;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jia.lu on 11/21/2017.
 */

public class ClubActivity extends MakiBaseActivity implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private TextView mCustomerNum;
    private TextView mCustomerToday;
    private TextView mInvitePersonnel;
    private TextView mChangeClubName;
    private TextView mCloseClub;
    private TextView mTitle;
    private ImageView mTitleImage;
    private LinearLayout mTilteContent;
    private LinearLayout mBack;
    private TextView mAddClub;
    private TextView mSort;
    private LinearLayout mCustomerOperation;
    private TextView mSelfCustomerSum;
    private TextView mSelfCustomerToday;
    private TextView mSelfMarketSum;
    private TextView mSelfMarketToday;

    private MySwipRefreshView mSwipRefreshView;
    private ClueRecyclerViewAdapter recyclerViewAdapter;
    private AlertDialog renameDialog;
    private List<PersonnelModel.WorkersBean> personnelModelList = new ArrayList<>();
    private AlertDialog chooseDialog;
    private AlertDialog.Builder closeDialogBuilder;
    private AlertDialog closeDialog;
    private boolean hasRuler;
    private PersonnelModel.ClubsBean clubsBean;
    private ClubService service;
    private List<PersonnelModel.ClubsBean> clubs = new ArrayList<>();
    private String nowClubId = "";
    private int nowField = 0;//按照意向客户还是市场人员，默认0,0是意向客户
    private int nowSort = 0;//正序还是倒序，默认0,0是倒序

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club);
        service = ZillaApi.NormalRestAdapter.create(ClubService.class);
        Intent intent = getIntent();
        hasRuler = intent.getBooleanExtra("maki", false);
//        hasRuler = true;
        Log.d("maki",String.valueOf(hasRuler));
        nowClubId = intent.getStringExtra("makise") == null ? "" : intent.getStringExtra("makise");
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData(nowClubId, nowField, nowSort);
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
        mSort = findViewById(R.id.tv_new_personnel);
        mCustomerOperation = findViewById(R.id.ll_customer_operation);
        mTitleImage = findViewById(R.id.iv_title);
        mTilteContent = findViewById(R.id.ll_title);
        mSelfCustomerSum = findViewById(R.id.tv_self_customer_sum);
        mSelfCustomerToday = findViewById(R.id.tv_self_customer_today);
        mSelfMarketSum = findViewById(R.id.tv_self_market_sum);
        mSelfMarketToday = findViewById(R.id.tv_self_market_today);
        mSort.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mInvitePersonnel.setOnClickListener(this);
        mChangeClubName.setOnClickListener(this);
        mCloseClub.setOnClickListener(this);
        mAddClub.setOnClickListener(this);
//        mTitle.setOnClickListener(this);
        mTilteContent.setOnClickListener(this);
        mSwipRefreshView.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        mSwipRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setData(nowClubId, nowField, nowSort);
            }
        });
        initRecyclerView();
        if (!hasRuler){
            mAddClub.setVisibility(View.INVISIBLE);
            mCustomerOperation.setVisibility(View.INVISIBLE);
        }else {
            mAddClub.setVisibility(View.VISIBLE);
            mCustomerOperation.setVisibility(View.VISIBLE);
        }
    }

    private void setData(final String clubId, final int field, final int sort) {
        dialogShow("正在加载");
        personnelModelList.clear();
        service.getClubInfo(UserInfoModel.getInstance().getToken(), clubId, field, sort, new RequestCallback<ResponseData<PersonnelModel>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void success(ResponseData<PersonnelModel> responseData, Response response) {
                dialogDismiss();
                if (responseData.getStatus() == 200) {
                    if (responseData.getData() == null) {
                        return;
                    }
                    if (responseData.getData().getClubs().size() >= 2){
                        mTitleImage.setVisibility(View.VISIBLE);
                    }else {
                        mTitleImage.setVisibility(View.GONE);
                    }
                    clubs = responseData.getData().getClubs();
                    clubsBean = clubs.get(0);//没有clubId的时候默认是第一个
                    for (int i = 0; i < clubs.size(); i++) {
                        if (clubs.get(i).getID().equals(clubId)) {
                            clubsBean = clubs.get(i);
                        }
                    }
                    mTitle.setText(clubsBean.getName());
                    mCustomerNum.setText(responseData.getData().getTotalCustomer() + "");
                    mCustomerToday.setText(responseData.getData().getTodayCustomer() + "");
                    personnelModelList.addAll(responseData.getData().getWorkers());
                    recyclerViewAdapter.notifyDataSetChanged();
                    if (field == 0){
                        mSort.setText("新增意向客户");
                    }else {
                        mSort.setText("新增市场人员");
                    }
                    nowClubId = clubsBean.getID();
                    mSelfCustomerSum.setText("" + responseData.getData().getSelf().getTotalCustomer());
                    mSelfCustomerToday.setText("" + responseData.getData().getSelf().getTodayCustomer());
                    mSelfMarketSum.setText("" + responseData.getData().getSelf().getTotalMarketingStaff());
                    mSelfMarketToday.setText("" + responseData.getData().getSelf().getTodayMarketingStaff());
                }else {
                    Toast.makeText(ClubActivity.this,responseData.getMsg(),Toast.LENGTH_SHORT).show();
                }
                if (mSwipRefreshView.isRefreshing()) {
                    mSwipRefreshView.setRefreshing(false);
                }
                Log.d("maki",nowClubId);
                recyclerViewAdapter.setClubsBean(clubsBean);
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                dialogDismiss();
                if (mSwipRefreshView.isRefreshing()) {
                    mSwipRefreshView.setRefreshing(false);
                }
            }
        });

    }

    private void initRecyclerView() {
        recyclerViewAdapter = new ClueRecyclerViewAdapter(personnelModelList, this, clubsBean);
        recyclerViewAdapter.setHasRuler(hasRuler);
        mRecyclerView.setAdapter(recyclerViewAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void createChangeDialog(boolean isTitle) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_choose_club, null);
        RecyclerView clubRecyclerView = dialogView.findViewById(R.id.rcv_club);
        ChooseClubRecyclerViewAdapter adapter = null;
        if (isTitle) {
            List<ClubNameModel> nameModels = new ArrayList<>();
            for (int i = 0; i < clubs.size(); i++) {
                ClubNameModel clubNameModel = new ClubNameModel();
                clubNameModel.setClubName(clubs.get(i).getName());
                clubNameModel.setId(clubs.get(i).getID());
                if (clubs.get(i).getID().equals(nowClubId)) {
                    clubNameModel.setSelected(true);
                } else {
                    clubNameModel.setSelected(false);
                }
                nameModels.add(clubNameModel);
            }
            adapter = new ChooseClubRecyclerViewAdapter(nameModels, new ChooseClubRecyclerViewAdapter.ItemListener() {
                @Override
                public void onItemClick(ClubNameModel item, int position) {
                    nowClubId = item.getId();
                    setData(nowClubId, nowField, nowSort);
                    chooseDialog.dismiss();
                }
            }, this);
        } else {
            List<ClubNameModel> models = new ArrayList<>();
            ClubNameModel clubNameModel1 = new ClubNameModel();
            clubNameModel1.setClubName("新增意向客户");
            if (nowField == 0) {
                clubNameModel1.setSelected(true);
            } else {
                clubNameModel1.setSelected(false);
            }
            ClubNameModel clubNameModel2 = new ClubNameModel();
            clubNameModel2.setClubName("新增市场人员");
            models.add(clubNameModel1);
            if (nowField == 1) {
                clubNameModel2.setSelected(true);
            } else {
                clubNameModel2.setSelected(false);
            }
            models.add(clubNameModel2);
            adapter = new ChooseClubRecyclerViewAdapter(models, new ChooseClubRecyclerViewAdapter.ItemListener() {
                @Override
                public void onItemClick(ClubNameModel item, int position) {
                    nowField = position;
                    setData(nowClubId, position, nowSort);
                    chooseDialog.dismiss();
                }
            }, this);
        }
        clubRecyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        clubRecyclerView.setLayoutManager(manager);
//        if (chooseDialog == null) {
            chooseDialog = new AlertDialog.Builder(this).create();
            chooseDialog.setView(dialogView, 0, 0, 0, 0);
//        }
        if (isTitle && clubs.size() < 2){
            return;
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
                dialogShow("加载中");
                String name = mInput.getText().toString().trim();
                mInput.setText("");
                if (name.equals("")){
                    Toast.makeText(ClubActivity.this,"俱乐部名字不能为空",Toast.LENGTH_SHORT).show();
                    dialogDismiss();
                    return;
                }
                service.changeClubName(UserInfoModel.getInstance().getToken(), nowClubId, name, new RequestCallback<ResponseData>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void success(ResponseData responseData, Response response) {
                        dialogDismiss();
                        if (responseData.getStatus() == 200) {
                            setData(nowClubId,nowField,nowSort);
                        } else {
                            Toast.makeText(ClubActivity.this, responseData.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                        renameDialog.dismiss();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        super.failure(error);
                        dialogDismiss();
                        renameDialog.dismiss();
                    }
                });

            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                renameDialog.dismiss();
                mInput.setText("");
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
                Intent intent = new Intent(this,InviteActivity.class);
                intent.putExtra("maki",nowClubId);
                startActivity(intent);
                break;
            case R.id.tv_change_club_name:
                createRenameDialog();
                break;
            case R.id.tv_close_club:
                dialogShow("加载中");
                closeDialogBuilder = new AlertDialog.Builder(this);
                closeDialogBuilder
                        .setMessage("确实要关闭俱乐部吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                service.closeClub(UserInfoModel.getInstance().getToken(), nowClubId, new RequestCallback<ResponseData>() {
                                    @Override
                                    public void success(ResponseData responseData, Response response) {
                                        dialogDismiss();
                                        if (responseData.getStatus() == 200) {
                                            closeDialog.dismiss();
                                            finish();
                                        } else {
                                            Toast.makeText(ClubActivity.this, responseData.getMsg(), Toast.LENGTH_SHORT).show();
                                            closeDialog.dismiss();
                                        }
                                    }
                                });

                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogDismiss();
                                closeDialog.dismiss();
                            }
                        });
                if (closeDialog == null) {
                    closeDialog = closeDialogBuilder.create();
                }
                closeDialog.show();
                break;
            case R.id.ll_left:
                finish();
                break;
            case R.id.ll_title:
                createChangeDialog(true);
                break;
            case R.id.tv_right:
                startActivityForResult(new Intent(this, CreateClubActivity.class),233);
                break;
            case R.id.tv_new_personnel:
                createChangeDialog(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            nowClubId = data.getStringExtra("makise");
            hasRuler = data.getBooleanExtra("maki",false);

        }
    }

    //    /**
//     * 通用progressDialog
//     *
//     * @param value
//     */
//    public void dialogShow(String value) {
//        if (progressDialog == null || !progressDialog.isShowing()) {
//            if (!ClubActivity.this.isFinishing()) {
//                progressDialog = CustomProgress.build(this, value);
//                progressDialog.show();
//            }
//        }
//    }
//
//    /**
//     * 通用progressDialog
//     */
//    public void dialogDismiss() {
//        if (progressDialog != null && progressDialog.isShowing()) {
//            progressDialog.dismiss();
//            progressDialog = null;
//        }
//    }
}
