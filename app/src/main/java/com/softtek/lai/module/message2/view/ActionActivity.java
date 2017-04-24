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
import com.softtek.lai.module.act.view.ActActivity;
import com.softtek.lai.module.bodygame3.activity.view.ActivitydetailActivity;
import com.softtek.lai.module.message2.model.ActionNoticeModel;
import com.softtek.lai.module.message2.net.Message2Service;
import com.softtek.lai.utils.RequestCallback;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_action)
public class ActionActivity extends BaseActivity implements View.OnClickListener,PullToRefreshBase.OnRefreshListener<ListView>{

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
    @InjectView(R.id.iv_nomessage)
    ImageView iv_nomessage;
    @InjectView(R.id.footer)
    LinearLayout footer;
    @InjectView(R.id.ll_select)
    LinearLayout ll_select;
    @InjectView(R.id.cb_all)
    CheckBox cb_all;
    @InjectView(R.id.tv_delete)
    TextView tv_delete;



    public boolean isSelsetAll = false;
    private List<Integer> deleteIndex=new ArrayList<>();
    private boolean doOperator=false;

    EasyAdapter<ActionNoticeModel> adapter;
    private List<ActionNoticeModel> operatList=new ArrayList<>();

    @Override
    protected void initViews() {
        tv_title.setText("活动邀请");
        tv_right.setText("编辑");
        ll_left.setOnClickListener(this);
        ll_select.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        lv.setEmptyView(iv_nomessage);
        lv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        ILoadingLayout startLabelse = lv.getLoadingLayoutProxy(true, false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setRefreshingLabel("正在刷新数据");// 刷新时
        startLabelse.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        lv.setOnRefreshListener(this);
    }

    @Override
    protected void initDatas() {
        adapter=new EasyAdapter<ActionNoticeModel>(this,operatList,R.layout.listitem_my_action) {
            @Override
            public void convert(ViewHolder holder, ActionNoticeModel data, final int position) {
                TextView tv_notice_date=holder.getView(R.id.tv_notice_date);
                //String date = DateUtil.getInstance().convertDateStr(data.SendTime, "yyyy年MM月dd日");
                tv_notice_date.setText(data.SendTime);
                ImageView iv_select=holder.getView(R.id.iv_select);
                if(doOperator){
                    iv_select.setVisibility(View.VISIBLE);
                }else {
                    iv_select.setImageResource(R.drawable.history_data_circle);
                    iv_select.setVisibility(View.GONE);
                }
                if (data.isSelected) {
                    iv_select.setImageResource(R.drawable.history_data_circled);
                } else {
                    iv_select.setImageResource(R.drawable.history_data_circle);
                }
                ImageView iv_red=holder.getView(R.id.iv_read);
                if (0==data.IsRead) {
                    iv_red.setVisibility(View.VISIBLE);
                } else {
                    iv_red.setVisibility(View.GONE);
                }
                TextView tv_notice_title=holder.getView(R.id.tv_notice_title);
                tv_notice_title.setText(data.ActTitle);
                TextView tv_action_content=holder.getView(R.id.tv_action_content);
                tv_action_content.setText(data.MsgContent);

            }
        };
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ActionNoticeModel model=operatList.get(i-1);
                if(doOperator){
                    //正在操作的话
                    if(model.isSelected){
                        isSelsetAll=false;
                        cb_all.setChecked(false);
                        model.isSelected=false;
                        deleteIndex.remove(Integer.valueOf(i-1));
                    }else {
                        model.isSelected=true;
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
                //===================================================
                if(model.Msgtype==2){//是莱运动活动
                    if (model.IsJoinAct==0) {
                        Util.toastMsg("您不在该活动中，不能查看活动详情！");
                    } else if (TextUtils.isEmpty(model.ActId)) {
                        Util.toastMsg("抱歉, 该活动已取消！");
                    } else {
                        Intent intent = new Intent(ActionActivity.this, ActActivity.class);
                        intent.putExtra("id", model.ActId);
                        startActivityForResult(intent, 0);
                    }
                }else if(model.Msgtype==1){//体馆赛活动
                    Intent intent = new Intent(ActionActivity.this, ActivitydetailActivity.class);
                    intent.putExtra("activityId",model.ActId);
                    startActivity(intent);
                    model.IsRead=1;
                    adapter.notifyDataSetChanged();
                }


            }
        });
        lv.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(lv!=null){
                    lv.setRefreshing();
                }
            }
        },300);

    }
    private void onResult(List<ActionNoticeModel> data){
        try {
            tv_right.setText("编辑");
            fl_right.setOnClickListener(this);
            operatList.clear();
            operatList.addAll(data);
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_left:
                setResult(RESULT_OK);
                finish();
                break;
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
                    builder.append(operatList.get(deleteIndex.get(i)).Msgid);
                    if(i<j-1){
                        builder.append(",");
                    }
                }
                ZillaApi.NormalRestAdapter.create(Message2Service.class)
                        .deleteMssage(UserInfoModel.getInstance().getToken(),
                                builder.toString(),
                                5,
                                new RequestCallback<ResponseData>() {
                                    @Override
                                    public void success(ResponseData responseData, Response response) {
                                        dialogDissmiss();
                                        if(responseData.getStatus()!=200){
                                            return;
                                        }
                                        Iterator<ActionNoticeModel> iterator=operatList.iterator();
                                        while (iterator.hasNext()){
                                            ActionNoticeModel model=iterator.next();
                                            if(model.isSelected){
                                                iterator.remove();
                                            }

                                        }
                                        deleteIndex.clear();
                                        cb_all.setChecked(false);
                                        adapter.notifyDataSetChanged();



                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        dialogDissmiss();
                                        super.failure(error);
                                    }
                                });

                break;
            case R.id.ll_select:
                if (isSelsetAll) {
                    isSelsetAll = false;
                    cb_all.setChecked(false);
                    deleteIndex.clear();
                    for (ActionNoticeModel model:operatList){
                        model.isSelected=false;
                    }
                } else {
                    isSelsetAll = true;
                    cb_all.setChecked(true);
                    deleteIndex.clear();
                    for (int i=0;i<operatList.size();i++){
                        operatList.get(i).isSelected=true;
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        if(lv!=null){
                            lv.setRefreshing();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 300);
        }
    }

    @Override
    public void onRefresh(PullToRefreshBase<ListView> refreshView) {
        ZillaApi.NormalRestAdapter.create(Message2Service.class)
                .getActiveNoticeMsg(UserInfoModel.getInstance().getToken(),
                        UserInfoModel.getInstance().getUserId(),
                        new RequestCallback<ResponseData<List<ActionNoticeModel>>>() {
                            @Override
                            public void success(ResponseData<List<ActionNoticeModel>> data, Response response) {
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
