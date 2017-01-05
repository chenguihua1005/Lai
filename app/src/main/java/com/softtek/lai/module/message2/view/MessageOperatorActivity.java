package com.softtek.lai.module.message2.view;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
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
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.message2.model.OperateMsgModel;
import com.softtek.lai.module.message2.net.Message2Service;
import com.softtek.lai.utils.RequestCallback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

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
public class MessageOperatorActivity extends BaseActivity implements View.OnClickListener,PullToRefreshBase.OnRefreshListener<ListView>{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    @InjectView(R.id.lv)
    PullToRefreshListView lv;

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

    EasyAdapter<OperateMsgModel> adapter;
    private List<OperateMsgModel> operatList=new ArrayList<>();
    @Override
    protected void initViews() {

        tv_title.setText("小助手");
        tv_delete.setOnClickListener(this);
        lin_select.setOnClickListener(this);
        tv_right.setText("编辑");
        fl_right.setOnClickListener(this);
        lv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        ILoadingLayout startLabelse = lv.getLoadingLayoutProxy(true,false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setRefreshingLabel("正在刷新数据");// 刷新时
        startLabelse.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        lv.setOnRefreshListener(this);
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
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
                tv_time.setText(data.getSendTime());
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
                OperateMsgModel model=operatList.get(i-1);
                if(doOperator){
                    //正在操作的话
                    if(model.isSelected()){
                        isSelsetAll=false;
                        cb_all.setChecked(false);
                        model.setSelected(false);
                        deleteIndex.remove(Integer.valueOf(i-1));
                    }else {
                        model.setSelected(true);
                        deleteIndex.add(Integer.valueOf(i-1));
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
                if(5==model.getMsgtype()){
                    Intent intent = new Intent(MessageOperatorActivity.this, ExamineActivity.class);
                    intent.putExtra("msgId", model.getMsgid());
                    intent.putExtra("position",i-1);
                    startActivityForResult(intent, 10);
                }else {
                    Intent intent = new Intent(MessageOperatorActivity.this, MessageConfirmActivity.class);
                    intent.putExtra("msgId", model.getMsgid());
                    intent.putExtra("position",i-1);
                    startActivityForResult(intent, 10);
                }
            }
        });
    }

    @Override
    protected void initDatas() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if(lv!=null){
                    lv.setRefreshing();
                }
            }
        },400);

    }

    private void onResult(List<OperateMsgModel> data){
            tv_right.setText("编辑");
            fl_right.setOnClickListener(this);
            operatList.clear();
            operatList.addAll(data);
            adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK) {
            int position=data.getIntExtra("position",-1);
            if(position!=-1){
                int msgStatus=data.getIntExtra("msgStatus",0);
                OperateMsgModel model=operatList.get(position);
                model.setMsgStatus(msgStatus);
                adapter.notifyDataSetChanged(lv.getRefreshableView(),position);
            }
            /*new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(lv!=null){
                        lv.setRefreshing();
                    }
                }
            },400);*/
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_right:
                if(!doOperator){
                    lv.setMode(PullToRefreshBase.Mode.DISABLED);
                    doOperator=true;
                    tv_right.setText("完成");
                    cb_all.setChecked(false);
                    footer.setVisibility(View.VISIBLE);
                }else {
                    lv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
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
                                1,
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
                    for (OperateMsgModel model:operatList){
                        model.setSelected(false);
                    }
                } else {
                    isSelsetAll = true;
                    cb_all.setChecked(true);
                    deleteIndex.clear();
                    for (int i=0;i<operatList.size();i++){
                        operatList.get(i).setSelected(true);
                        deleteIndex.add(Integer.valueOf(i));
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
        }else if(keyCode==KeyEvent.KEYCODE_BACK){
            setResult(RESULT_OK);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onRefresh(PullToRefreshBase<ListView> refreshView) {
        ZillaApi.NormalRestAdapter.create(Message2Service.class)
                .getOperateMsgList(UserInfoModel.getInstance().getToken(),
                        UserInfoModel.getInstance().getUserId(),
                        new RequestCallback<ResponseData<List<OperateMsgModel>>>() {
                            @Override
                            public void success(ResponseData<List<OperateMsgModel>> data, Response response) {
                                try {
                                    lv.onRefreshComplete();
                                    if(data.getStatus()==200){
                                        onResult(data.getData());
                                    }else {
                                        Util.toastMsg(data.getMsg());
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                try {
                                    lv.onRefreshComplete();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                super.failure(error);
                            }
                        });
    }
}
