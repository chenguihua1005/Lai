package com.softtek.lai.module.message2.view;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.message2.model.OperateMsgModel;
import com.softtek.lai.module.message2.net.Message2Service;
import com.softtek.lai.utils.RequestCallback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

/**
 * 操作类消息
 */
@InjectLayout(R.layout.activity_message_operator)
public class MessageOperatorActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    @InjectView(R.id.lv)
    ListView lv;

    @InjectView(R.id.footer)
    LinearLayout footer;
    @InjectView(R.id.tv_delete)
    TextView tv_delete;
    @InjectView(R.id.lin_select)
    LinearLayout lin_select;
    @InjectView(R.id.cb_all)
    CheckBox cb_all;

    public boolean isSelsetAll = false;
    private List<String> ids=new ArrayList<>();
    private boolean doOperator=false;

    EasyAdapter<OperateMsgModel> adapter;
    private List<OperateMsgModel> operatList=new ArrayList<>();
    @Override
    protected void initViews() {
        tv_title.setText("小助手");
        tv_delete.setOnClickListener(this);
        lin_select.setOnClickListener(this);
        tv_right.setText("编辑");
        fl_right.setOnClickListener(this);
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        adapter=new EasyAdapter<OperateMsgModel>(this,operatList,R.layout.item_message_xzs) {
            @Override
            public void convert(ViewHolder holder, final OperateMsgModel data, final int position) {
                ImageView iv_select=holder.getView(R.id.iv_select);
                if(doOperator){
                    iv_select.setVisibility(View.VISIBLE);
                }else {
                    iv_select.setImageResource(R.drawable.history_data_circle);
                    iv_select.setVisibility(View.GONE);
                }
                if (data.isSelected()) {
                    iv_select.setImageResource(R.drawable.history_data_circled);
                } else {
                    iv_select.setImageResource(R.drawable.history_data_circle);
                }

                TextView tv_time=holder.getView(R.id.tv_time);
                String time = data.getSendTime();
                if (!TextUtils.isEmpty(time)) {
                    String[] str1 = time.split(" ");
                    String[] str = str1[0].split("-");
                    tv_time.setText(str[0] + "年" + str[1] + "月" + str[2] + "日");
                }
                TextView tv_content=holder.getView(R.id.tv_content);
                tv_content.setText(data.getMsgContent());
                tv_content.append(" >>");
                TextView tv_status=holder.getView(R.id.tv_status);
                //显示此条消息的状态
                if(0==data.getMsgStatus()){
                    //未操作
                    tv_status.setText("未处理");
                }else if(data.getMsgStatus()==1){
                    //接受
                    tv_status.setText("已同意");
                }else if(data.getMsgStatus()==2){
                    //拒绝
                    tv_status.setText("已忽略");
                }
                TextView tv_title=holder.getView(R.id.tv_title);
                if(data.getMsgtype()==2){
                    tv_title.setText("邀请成为教练");
                }else if (data.getMsgtype()==3){
                    tv_title.setText("邀请成为助教");
                }else if (data.getMsgtype()==4){
                    tv_title.setText("邀请成为学员");
                } else if (data.getMsgtype()==5){
                    tv_title.setText("申请加入班级");
                }
                ImageView iv_head=holder.getView(R.id.iv_head);
                if(TextUtils.isEmpty(data.getSenderPhoto())){
                    Picasso.with(MessageOperatorActivity.this).load(R.drawable.img_default).into(iv_head);
                }else {
                    Picasso.with(MessageOperatorActivity.this)
                            .load(AddressManager.get("photoHost")+data.getSenderPhoto())
                            .fit()
                            .error(R.drawable.img_default)
                            .placeholder(R.drawable.img_default).into(iv_head);
                }

            }
        };
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                OperateMsgModel model=operatList.get(i);
                if(doOperator){
                    //正在操作的话
                    if(model.isSelected()){
                        isSelsetAll=false;
                        cb_all.setChecked(false);
                        model.setSelected(false);
                        ids.add(model.getMsgid());
                    }else {
                        model.setSelected(true);
                    }
                    adapter.notifyDataSetChanged();
                    return;
                }
                if(5==model.getMsgtype()){
                    Intent intent = new Intent(MessageOperatorActivity.this, ExamineActivity.class);
                    intent.putExtra("msgId", model.getMsgid());
                    startActivityForResult(intent, 10);
                }else {
                    Intent intent = new Intent(MessageOperatorActivity.this, MessageConfirmActivity.class);
                    intent.putExtra("msgId", model.getMsgid());
                    startActivityForResult(intent, 10);
                }
            }
        });
    }

    @Override
    protected void initDatas() {
        dialogShow("加载中");
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
        tv_right.setText("编辑");
        fl_right.setOnClickListener(this);
        operatList.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK) {
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_right:
                if(!doOperator){
                    doOperator=true;
                    tv_right.setText("完成");
                    cb_all.setChecked(false);
                    footer.setVisibility(View.VISIBLE);
                }else {
                    doOperator=false;
                    tv_right.setText("编辑");
                    footer.setVisibility(View.GONE);
                    //恢复之前所选的
                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.tv_delete:


                break;
            case R.id.lin_select:

                if (isSelsetAll) {
                    isSelsetAll = false;
                    cb_all.setChecked(false);
                    for (OperateMsgModel model:operatList){
                        model.setSelected(false);
                    }
                } else {
                    isSelsetAll = true;
                    cb_all.setChecked(true);
                    for (OperateMsgModel model:operatList){
                        model.setSelected(true);
                    }
                }
                adapter.notifyDataSetChanged();
                break;
        }
    }
}
