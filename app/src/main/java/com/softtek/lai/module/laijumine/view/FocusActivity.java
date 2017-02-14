package com.softtek.lai.module.laijumine.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.hyphenate.easeui.widget.EaseAlertDialog;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.laijumine.model.FocusInfoModel;
import com.softtek.lai.module.laijumine.net.MineSevice;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_focus)
public class FocusActivity extends BaseActivity implements View.OnClickListener{
    @InjectView(R.id.list_focus)
    ListView list_focus;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    MineSevice mineSevice;
    EasyAdapter<FocusInfoModel> easyAdapter;
    FocusInfoModel focusInfoModel;
    List<FocusInfoModel>focusInfoModels=new ArrayList<FocusInfoModel>();

    @Override
    protected void initViews() {
        int focusnum=getIntent().getIntExtra("focusnum",0);
        tv_title.setText("关注（"+focusnum+"）");
        ll_left.setOnClickListener(this);
        list_focus.addFooterView(new ViewStub(this));

    }

    @Override
    protected void initDatas() {
        mineSevice= ZillaApi.NormalRestAdapter.create(MineSevice.class);
        easyAdapter=new EasyAdapter<FocusInfoModel>(this,focusInfoModels,R.layout.acitivity_fans_list) {
            @Override
            public void convert(ViewHolder holder, final FocusInfoModel data, int position) {
                final CircleImageView cir_photo=holder.getView(R.id.cir_photo);
                if (!TextUtils.isEmpty(data.getPhoto()))
                {
                    Picasso.with(getBaseContext()).load(AddressManager.get("photoHost")+data.getPhoto())
                            .centerCrop().fit().placeholder(R.drawable.img_default).error(R.drawable.img_default)
                            .into(cir_photo);
                }
                data.setFocus(true);
                focusInfoModel=data;
                TextView tv_fansname=holder.getView(R.id.tv_fansname);
                tv_fansname.setText(data.getUserName());
                TextView tv_fanssignature=holder.getView(R.id.tv_fanssignature);
                tv_fanssignature.setText(data.getSignature());
                ImageView im_guanzhu=holder.getView(R.id.im_guanzhu);
                im_guanzhu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (focusInfoModel.isFocus())
                        {//当前为已关注状态，用户点击提示“确定不再关注此人”，确定请求取消关注，变为加关注状态。
                            AlertDialog.Builder dialog;
                            dialog=new AlertDialog.Builder(FocusActivity.this);
                            dialog.setTitle("确定不再关注此人？").setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).create().show();
//
                        }
                        else {//当前未关注，请求关注接口，成功变为已关注状态

                        }
                    }
                });
            }
        };
        list_focus.setAdapter(easyAdapter);
        doGetData();
    }
    private void doGetData()
    {
        mineSevice.GetFocusPelist(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), new RequestCallback<ResponseData<List<FocusInfoModel>>>() {
            @Override
            public void success(ResponseData<List<FocusInfoModel>> listResponseData, Response response) {
                int status=listResponseData.getStatus();
                switch (status)
                {
                    case 200:
                        focusInfoModels.addAll(listResponseData.getData());
                        easyAdapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.ll_left:
                finish();
                break;
        }
    }
}
