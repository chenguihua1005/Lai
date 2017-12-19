package com.softtek.lai.module.customermanagement.view;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.customermanagement.service.CustomerService;

import org.greenrobot.eventbus.EventBus;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

/**
 * Created by jessica.zhang on 11/23/2017.
 */

@InjectLayout(R.layout.activity_add_remark)
public class AddRemarkActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;

    @InjectView(R.id.remark_et)
    EditText remark_et;

    private String mobile = "";

    @Override
    protected void initViews() {
        tv_title.setText("添加备注");
        tv_right.setText("保存");
        ll_left.setOnClickListener(this);
        fl_right.setOnClickListener(this);

    }

    @Override
    protected void initDatas() {
        mobile = getIntent().getStringExtra("mobile");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.fl_right:
                saveRemark();
                break;

        }

    }

    private void saveRemark() {
        String input = remark_et.getText().toString().trim();

        if (TextUtils.isEmpty(input)) {
            Util.toastMsg("请输入备注信息！");
            return;
        } else {
            dialogShow("正在保存数据...");
            CustomerService service = ZillaApi.NormalRestAdapter.create(CustomerService.class);
            service.addRemark(UserInfoModel.getInstance().getToken(), "", mobile, input, new Callback<ResponseData>() {
                @Override
                public void success(ResponseData responseData, Response response) {
                    dialogDissmiss();
                    int status = responseData.getStatus();
                    Util.toastMsg(responseData.getMsg());
                    if (200 == status) {
                        //发送事件
                        LocalBroadcastManager.getInstance(AddRemarkActivity.this).sendBroadcast(new Intent(RemarkFragment.UPDATE_REMARKLIST));
                        finish();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    dialogDissmiss();
                    ZillaApi.dealNetError(error);
                }
            });
        }
    }


}
