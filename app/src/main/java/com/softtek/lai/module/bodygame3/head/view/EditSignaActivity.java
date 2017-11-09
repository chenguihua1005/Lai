package com.softtek.lai.module.bodygame3.head.view;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.activity.model.EditSignaModel;
import com.softtek.lai.module.bodygame3.head.net.HeadService;
import com.softtek.lai.utils.RequestCallback;

import butterknife.InjectView;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_edit_signa)
public class EditSignaActivity extends BaseActivity implements View.OnClickListener{
    @InjectView(R.id.bt_sina)
    Button bt_sina;
    @InjectView(R.id.edit_content)
    EditText edit_content;

    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    HeadService headService;
    EditSignaModel editSignaModel;

    @Override
    protected void initViews() {
        tv_title.setText("编辑签名");
        edit_content.setText(getIntent().getStringExtra("sina"));
        edit_content.setSelection(getIntent().getStringExtra("sina").length());
        editSignaModel=new EditSignaModel();
        editSignaModel.setAccountId(UserInfoModel.getInstance().getUserId());
        headService= ZillaApi.NormalRestAdapter.create(HeadService.class);
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        bt_sina.setOnClickListener(this);
        edit_content.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i==keyEvent.KEYCODE_ENTER)
                {
                    doPostService();
                }
                return false;
            }
        });


    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_sina:
                doPostService();
                break;
        }

    }
    private void doPostService()
    {
        dialogShow("请稍候...");
        editSignaModel.setPName(edit_content.getText().toString().trim());
        headService.doCommitSina(UserInfoModel.getInstance().getToken(),editSignaModel, new RequestCallback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                dialogDissmiss();
                int status = responseData.getStatus();
                switch (status) {
                    case 200:
                        Intent intent = new Intent();
                        intent.putExtra("sina", edit_content.getText().toString());
                        setResult(RESULT_OK, intent);
                        finish();
                        break;
                    default:
                        Util.toastMsg(responseData.getMsg());
                        break;
                }

            }
        });
    }
}
