package com.softtek.lai.module.customermanagement.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ggx.widgets.drop.DoubleListView;
import com.ggx.widgets.drop.SimpleTextAdapter;
import com.ggx.widgets.view.CheckTextView;
import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.more.model.LaiClass;
import com.softtek.lai.module.bodygame3.more.net.MoreService;
import com.softtek.lai.module.bodygame3.more.view.CreateGroupActivity;
import com.softtek.lai.module.customermanagement.adapter.InviteRecyclerViewAdapter;
import com.softtek.lai.module.customermanagement.model.ClubAndCityModel;
import com.softtek.lai.module.customermanagement.model.ClubCreateResponse;
import com.softtek.lai.module.customermanagement.model.InviteModel;
import com.softtek.lai.module.customermanagement.service.ClubService;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jia.lu on 11/21/2017.
 */

public class CreateClubActivity extends MakiBaseActivity implements View.OnClickListener{
    private ClubService service;
    private MoreService moreService;
    private TextView mConfim;
    private LinearLayout mBack;
    private TextView mTitle;
    private RelativeLayout mClubNameContent;
    private TextView mClubName;
    private RelativeLayout mAreaContent;
    private TextView mXiaoqu;
    private TextView mCity;

    private boolean isFirstClub;
    private String clubName;
    private AlertDialog clubDialog;
    private BottomSheetDialog areaDialog;
    private List<ClubAndCityModel.RegionalCitiesBean> left = new ArrayList<>();
    private int cityId;
    private int regionId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_club);
        service = ZillaApi.NormalRestAdapter.create(ClubService.class);
        initView();
        initData();
    }

    private void initView(){
        isFirstClub = getIntent().getBooleanExtra("maki_isfirst",false);
        mConfim = findViewById(R.id.tv_right);
        mBack = findViewById(R.id.ll_left);
        mTitle = findViewById(R.id.tv_title);
        mClubNameContent = findViewById(R.id.rl_club_name);
        mClubName = findViewById(R.id.tv_club_name);
        mAreaContent = findViewById(R.id.rl_area);
        mXiaoqu = findViewById(R.id.tv_xiaoqu);
        mCity = findViewById(R.id.tv_city);
        mAreaContent.setOnClickListener(this);
        mClubNameContent.setOnClickListener(this);
        mConfim.setText("确定");
        mTitle.setText("创建俱乐部");
        mConfim.setOnClickListener(this);
        mBack.setOnClickListener(this);
    }

    private void initData(){
        dialogShow("加载中...");
        moreService = ZillaApi.NormalRestAdapter.create(MoreService.class);
        moreService.getRegionalAndCitys(UserInfoModel.getInstance().getToken(), new RequestCallback<ResponseData<ClubAndCityModel>>() {
            @Override
            public void success(ResponseData<ClubAndCityModel> data, Response response) {
                dialogDismiss();
                if (data.getStatus() == 200) {
                    left = data.getData().getRegionalCities();
                }else {
                    Toast.makeText(CreateClubActivity.this,data.getMsg(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                dialogDismiss();
            }
        });
    }

    private void showClubDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_rename, null);
        final EditText mInput = dialogView.findViewById(R.id.edt_rename);
        TextView mDialogTitle = dialogView.findViewById(R.id.title);
        mDialogTitle.setText("创建俱乐部");
        Button mOk = dialogView.findViewById(R.id.btn_rename);
        Button mCancel = dialogView.findViewById(R.id.btn_cancel);
        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clubName = mInput.getText().toString().trim();
                if (clubName.equals("")) {
                    Toast.makeText(CreateClubActivity.this, "请输入俱乐部名称", Toast.LENGTH_SHORT).show();
                }else {
                    mClubName.setText(clubName);
                    clubDialog.dismiss();
                }
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clubDialog.dismiss();
            }
        });
        if (clubDialog == null) {
            clubDialog = new AlertDialog.Builder(this).create();
            clubDialog.setView(dialogView, 0, 0, 0, 0);
        }
        clubDialog.show();
    }

    private void showBottomSheet() {
        View view = LayoutInflater.from(this).inflate(R.layout.city_village, null);
        DoubleListView<ClubAndCityModel.RegionalCitiesBean, ClubAndCityModel.RegionalCitiesBean.RegionalCityListBean> dlv = view.findViewById(R.id.dlv);
        dlv.leftAdapter(new SimpleTextAdapter<ClubAndCityModel.RegionalCitiesBean>(this, left) {
            @Override
            public String getText(ClubAndCityModel.RegionalCitiesBean data) {
                return data.getRegionalName();
            }
        }).rightAdapter(new SimpleTextAdapter<ClubAndCityModel.RegionalCitiesBean.RegionalCityListBean>(this, null) {
            @Override
            public String getText(ClubAndCityModel.RegionalCitiesBean.RegionalCityListBean data) {
                return data.getCityName();
            }

            @Override
            protected void initView(CheckTextView textView) {
                textView.setBackgroundResource(android.R.color.white);
            }
        }).onLeftItemClickListener(new DoubleListView.OnLeftItemClickListener<ClubAndCityModel.RegionalCitiesBean, ClubAndCityModel.RegionalCitiesBean.RegionalCityListBean>() {
            @Override
            public List<ClubAndCityModel.RegionalCitiesBean.RegionalCityListBean> provideRightList(ClubAndCityModel.RegionalCitiesBean leftAdapter, int position) {
                return leftAdapter.getRegionalCityList();
            }
        }).onRightItemClickListener(new DoubleListView.OnRightItemClickListener<ClubAndCityModel.RegionalCitiesBean, ClubAndCityModel.RegionalCitiesBean.RegionalCityListBean>() {
            @Override
            public void onRightItemClick(ClubAndCityModel.RegionalCitiesBean item, ClubAndCityModel.RegionalCitiesBean.RegionalCityListBean childItem) {
                cityId = childItem.getCityId();
                regionId = item.getRegionalId();
                mXiaoqu.setText(item.getRegionalName());
                mCity.setText(childItem.getCityName());
                areaDialog.dismiss();
            }
        });
        //初始化选中.
        if (left != null && !left.isEmpty()) {
            dlv.setLeftList(left, 0);
            dlv.setRightList(left.get(0).getRegionalCityList(), -1);
        }
        areaDialog = new BottomSheetDialog(this);
        areaDialog.setContentView(view);
        areaDialog.show();
        areaDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                areaDialog = null;
            }
        });
    }

    private void createClub(){
        if (clubName.equals("")){
            Toast.makeText(CreateClubActivity.this,"请输入俱乐部名称",Toast.LENGTH_SHORT).show();
            return;
        }else if (mXiaoqu.getText().toString().trim().equals("") || mCity.getText().toString().trim().equals("") ){
            Toast.makeText(CreateClubActivity.this,"请选择地区",Toast.LENGTH_SHORT).show();
            return;
        }
        service.createClub(UserInfoModel.getInstance().getToken(),clubName,regionId,cityId, new RequestCallback<ResponseData<ClubCreateResponse>>() {
            @Override
            public void success(ResponseData<ClubCreateResponse> responseData, Response response) {
                if (responseData.getStatus() == 200){
                    Toast.makeText(CreateClubActivity.this,"创建" + responseData.getData().getName() + "成功", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(CreateClubActivity.this, ClubActivity.class);
                        intent.putExtra("maki", true);
                        intent.putExtra("makise", responseData.getData().getID());//俱乐部ID
                    if (!isFirstClub) {
                        setResult(RESULT_OK, intent);
                    }else {
                        startActivity(intent);
                    }
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
            case R.id.rl_club_name:
                showClubDialog();
                break;
            case R.id.rl_area:
                showBottomSheet();
                break;
        }
    }
}
