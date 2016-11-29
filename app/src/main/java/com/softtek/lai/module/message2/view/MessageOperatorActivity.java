package com.softtek.lai.module.message2.view;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.message2.model.OperateMsgModel;
import com.softtek.lai.module.message2.net.Message2Service;
import com.softtek.lai.utils.RequestCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

/**
 * 操作类消息
 */
@InjectLayout(R.layout.activity_message_operator2)
public class MessageOperatorActivity extends BaseActivity {

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.lv)
    ListView lv;
    EasyAdapter<OperateMsgModel> adapter;
    private List<OperateMsgModel> operatList=new ArrayList<>();
    @Override
    protected void initViews() {
        tv_title.setText("小助手");
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        adapter=new EasyAdapter<OperateMsgModel>(this,operatList,R.layout.item_message_xzs) {
            @Override
            public void convert(ViewHolder holder, final OperateMsgModel data, final int position) {
                TextView tv_time=holder.getView(R.id.tv_time);
                String time = data.getSendTime();
                if (!TextUtils.isEmpty(time)) {
                    String[] str1 = time.split(" ");
                    String[] str = str1[0].split("-");
                    tv_time.setText(str[0] + "年" + str[1] + "月" + str[2] + "日");
                }
                TextView tv_content=holder.getView(R.id.tv_content);
                tv_content.setText(data.getMsgContent());
                ImageView iv_red=holder.getView(R.id.iv_red);
                if ("0".equals(data.getIsRead())) {
                    iv_red.setVisibility(View.VISIBLE);
                } else {
                    iv_red.setVisibility(View.GONE);
                }
                TextView tv_title=holder.getView(R.id.tv_title);
                if(data.getMsgType()==2){
                    tv_title.setText("邀请成为教练");
                }else if (data.getMsgType()==3){
                    tv_title.setText("邀请成为助教");
                }else if (data.getMsgType()==4){
                    tv_title.setText("邀请成为学员");
                } else if (data.getMsgType()==5){
                    tv_title.setText("申请加入班级");
                }
                TextView tv_detail=holder.getView(R.id.tv_detail);
                if("1".equals(data.getIsDo())){
                    tv_detail.setVisibility(View.GONE);
                }else {
                    tv_detail.setVisibility(View.VISIBLE);
                }
            }
        };
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                OperateMsgModel model=operatList.get(i);
                if ("1".equals(model.getIsDo())) {
                    Util.toastMsg("该消息已操作过, 不能重复操作");
                } else {
                    Intent intent = new Intent(MessageOperatorActivity.this, MessageConfirmActivity.class);
                    intent.putExtra("model", model);
                    startActivityForResult(intent, 0);
                }
            }
        });
    }

    @Override
    protected void initDatas() {
        dialogShow("加载中");
        Log.i("小助手数据加载。。。。。。。。。。。。。。。。。。。。。。。。。。");
        ZillaApi.NormalRestAdapter.create(Message2Service.class)
                .getOperateMsgList(UserInfoModel.getInstance().getToken(),
                        UserInfoModel.getInstance().getUserId(),
                        new RequestCallback<ResponseData<List<OperateMsgModel>>>() {
                            @Override
                            public void success(ResponseData<List<OperateMsgModel>> data, Response response) {
                                dialogDissmiss();
                                if(data.getStatus()==200){
                                    onResult(data.getData());
                                }else {
                                    Util.toastMsg(data.getMsg());
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                dialogDissmiss();
                                super.failure(error);
                            }
                        });
    }

    private void onResult(List<OperateMsgModel> data){
        operatList.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            dialogShow("加载中");
            ZillaApi.NormalRestAdapter.create(Message2Service.class)
                    .getOperateMsgList(UserInfoModel.getInstance().getToken(),
                            UserInfoModel.getInstance().getUserId(),
                            new RequestCallback<ResponseData<List<OperateMsgModel>>>() {
                                @Override
                                public void success(ResponseData<List<OperateMsgModel>> data, Response response) {
                                    dialogDissmiss();
                                    if(data.getStatus()==200){
                                        operatList.clear();
                                        onResult(data.getData());
                                    }else {
                                        Util.toastMsg(data.getMsg());
                                    }
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    dialogDissmiss();
                                    super.failure(error);
                                }
                            });
        }
    }
}
