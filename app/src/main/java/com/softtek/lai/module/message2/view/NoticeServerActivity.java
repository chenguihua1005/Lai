package com.softtek.lai.module.message2.view;

import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
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
import com.softtek.lai.module.message2.model.NoticeModel;
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

@InjectLayout(R.layout.activity_message_operator)
public class NoticeServerActivity extends BaseActivity implements View.OnClickListener {

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
    private List<Integer> deleteIndex=new ArrayList<>();
    private boolean doOperator=false;

    EasyAdapter<NoticeModel> adapter;
    private List<NoticeModel> operatList=new ArrayList<>();
    @Override
    protected void initViews() {
        tv_title.setText("服务窗");
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
        adapter=new EasyAdapter<NoticeModel>(this,operatList,R.layout.message_2_remind_item) {
            @Override
            public void convert(ViewHolder holder, NoticeModel data, final int position) {
                TextView tv_time=holder.getView(R.id.text_time);
                String time = data.getSendTime();
                if (!TextUtils.isEmpty(time)) {
                    String[] str1 = time.split(" ");
                    String[] str = str1[0].split("-");
                    tv_time.setText(str[0] + "年" + str[1] + "月" + str[2] + "日");
                } else {
                    tv_time.setText("");
                }
                TextView tv_content=holder.getView(R.id.tv_content);
                tv_content.setText(data.getMsgContent());
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
                ImageView iv_red=holder.getView(R.id.iv_red);
                if ("0".equals(data.getIsRead())) {
                    iv_red.setVisibility(View.VISIBLE);
                } else {
                    iv_red.setVisibility(View.GONE);
                }
                TextView tv_title=holder.getView(R.id.tv_title);
                tv_title.setText("系统通知");
                TextView tv_more=holder.getView(R.id.tv_more);
                tv_more.setVisibility(View.GONE);

            }
        };
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NoticeModel model=operatList.get(i);
                if(doOperator){
                    //正在操作的话
                    if(model.isSelected()){
                        isSelsetAll=false;
                        cb_all.setChecked(false);
                        model.setSelected(false);
                        deleteIndex.remove(i);
                    }else {
                        model.setSelected(true);
                        deleteIndex.add(i);
                        if(operatList.size()==deleteIndex.size()){
                            isSelsetAll=true;
                            cb_all.setChecked(true);
                        }else {
                            cb_all.setChecked(false);
                        }

                    }
                    adapter.notifyDataSetChanged();
                    return;
                }
                //===================================================



            }
        });
    }

    @Override
    protected void initDatas() {
        dialogShow("加载中");
        ZillaApi.NormalRestAdapter.create(Message2Service.class)
                .getNoticeMsgList(UserInfoModel.getInstance().getToken(),
                        UserInfoModel.getInstance().getUserId(),
                        new RequestCallback<ResponseData<List<NoticeModel>>>() {
                            @Override
                            public void success(ResponseData<List<NoticeModel>> data, Response response) {
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

    private void onResult(List<NoticeModel> data){
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
                    .getNoticeMsgList(UserInfoModel.getInstance().getToken(),
                            UserInfoModel.getInstance().getUserId(),
                            new RequestCallback<ResponseData<List<NoticeModel>>>() {
                                @Override
                                public void success(ResponseData<List<NoticeModel>> data, Response response) {
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
                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.tv_delete:
                dialogShow("正在删除");
                StringBuilder builder=new StringBuilder();
                for(int i=0,j=deleteIndex.size();i<j;i++){
                    builder.append(operatList.get(deleteIndex.get(i)).getMsgid());
                    if(i<j-1){
                        builder.append(",");
                    }
                }
                ZillaApi.NormalRestAdapter.create(Message2Service.class)
                        .deleteMssage(UserInfoModel.getInstance().getToken(),
                                builder.toString(),
                                2,
                                new RequestCallback<ResponseData>() {
                                    @Override
                                    public void success(ResponseData responseData, Response response) {
                                        if(responseData.getStatus()!=200){
                                            return;
                                        }
                                        for(int i=0,j=deleteIndex.size();i<j;i++){
                                            operatList.remove(deleteIndex.get(i).intValue());
                                        }
                                        deleteIndex.clear();
                                        cb_all.setChecked(false);
                                        adapter.notifyDataSetChanged();
                                        dialogDissmiss();


                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        dialogDissmiss();
                                        super.failure(error);
                                    }
                                });

                break;
            case R.id.lin_select:
                if (isSelsetAll) {
                    isSelsetAll = false;
                    cb_all.setChecked(false);
                    deleteIndex.clear();
                    for (NoticeModel model:operatList){
                        model.setSelected(false);
                    }
                } else {
                    isSelsetAll = true;
                    cb_all.setChecked(true);
                    deleteIndex.clear();
                    for (int i=0;i<operatList.size();i++){
                        operatList.get(i).setSelected(true);
                        deleteIndex.add(i);
                    }
                }
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(doOperator&&keyCode==KeyEvent.KEYCODE_BACK){
            doOperator=false;
            tv_right.setText("编辑");
            footer.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
