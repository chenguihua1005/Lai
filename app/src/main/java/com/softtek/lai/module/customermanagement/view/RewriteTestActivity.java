package com.softtek.lai.module.customermanagement.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.customermanagement.service.InviteService;
import com.softtek.lai.utils.RequestCallback;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jia.lu on 1/11/2018.
 */

public class RewriteTestActivity extends MakiBaseActivity implements View.OnClickListener {
    private LinearLayout mWeightContent;
    private TextView mWeight;
    private LinearLayout mBodyFatContent;
    private TextView mBodyFat;
    private LinearLayout mInternalFatContent;
    private TextView mInternalFat;
    private LinearLayout mBack;
    private TextView mSubmit;
    private Button mSelect;
    private TextView mTitle;

    private String gender;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private InviteService service;
    private long accountId;
    private String classId = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        service = ZillaApi.NormalRestAdapter.create(InviteService.class);
        accountId = getIntent().getLongExtra("accountId",0);
        classId = getIntent().getStringExtra("classId");
        setContentView(R.layout.activity_rewrite_test);
        initView();
    }

    private void initView() {
        if (getIntent().getStringExtra("gender").equals("女")) {
            gender = "1";
        } else {
            gender = "0";
        }
        mWeightContent = findViewById(R.id.ll_weight);
        mWeight = findViewById(R.id.tv_weight);
        mBodyFatContent = findViewById(R.id.ll_body_fat);
        mBodyFat = findViewById(R.id.tv_body_fat);
        mInternalFatContent = findViewById(R.id.ll_internal_fat);
        mInternalFat = findViewById(R.id.tv_internal_fat);
        mBack = findViewById(R.id.ll_left);
        mTitle = findViewById(R.id.tv_title);
        mSubmit = findViewById(R.id.tv_right);
        mSelect = findViewById(R.id.btn_select);

        mTitle.setText("初始数据录入");
        mSubmit.setText("提交");
        mWeightContent.setOnClickListener(this);
        mBodyFatContent.setOnClickListener(this);
        mInternalFatContent.setOnClickListener(this);
        mSubmit.setOnClickListener(this);
        mSelect.setOnClickListener(this);
        mBack.setOnClickListener(this);
    }

    private void doSubmit(){
        if (mWeight.getText().toString().equals("") | mBodyFat.getText().toString().equals("") | mInternalFat.getText().toString().equals("")){
            Toast.makeText(this,"请全部填写完整后点击提交",Toast.LENGTH_SHORT).show();
            return;
        }
        dialogShow("加载中");
        service.saveInitialMeasuredData(UserInfoModel.getInstance().getToken(),
                accountId,
                classId,
                Float.valueOf(mWeight.getText().toString().split("斤")[0]),
                Float.valueOf(mBodyFat.getText().toString().split("%")[0]),
                Float.valueOf(mInternalFat.getText().toString()),
                new RequestCallback<ResponseData>() {
                    @Override
                    public void success(ResponseData responseData, Response response) {
                        Toast.makeText(RewriteTestActivity.this,responseData.getMsg(),Toast.LENGTH_SHORT).show();
                        dialogDismiss();
                        if (responseData.getStatus() == 200) {
                            finish();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        dialogDismiss();
                        super.failure(error);
                    }
                });
    }

    private void createDialog(String title, int min, int max, int defaultValue, final TextView textView, final String unity) {
        dialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dimension_dialog, null);
        final NumberPicker np1 = view.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = view.findViewById(R.id.numberPicker2);
        np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np1.setMaxValue(max);
        np1.setValue(defaultValue);
        np1.setMinValue(min);
        np1.setWrapSelectorWheel(false);
        np2.setMaxValue(9);
        np2.setValue(0);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(false);
        dialogBuilder.setTitle(title).setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int v1 = np1.getValue();
                textView.setText(v1 + Float.valueOf("0." + np2.getValue()) + unity);
                dialog.dismiss();

            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        dialog = dialogBuilder.create();
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_weight:
                createDialog("选择体重(单位：斤)", 50, 600, "0".equals(gender) ? 150 : 100, mWeight,"斤");
                break;
            case R.id.ll_body_fat:
                createDialog("选择体脂", 1, 50, 25, mBodyFat,"%");
                break;
            case R.id.ll_internal_fat:
                createDialog("选择内脂", 1, 30, 2, mInternalFat,"");
                break;
            case R.id.ll_left:
                finish();
                break;un
            case R.id.tv_right:
                doSubmit();
                break;
            case R.id.btn_select:
                Intent intent = new Intent(this,SelectHistoryActivity.class);
                intent.putExtra("accountId",accountId);
                startActivityForResult(intent,233);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            mWeight.setText(data.getStringExtra("weight"));
            mBodyFat.setText(data.getStringExtra("bodyFat"));
            mInternalFat.setText(data.getStringExtra("internalFat"));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
