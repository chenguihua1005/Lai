package com.softtek.lai.module.message2.view;

import android.content.Intent;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
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
import com.softtek.lai.utils.DisplayUtil;
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
                } else {
                    tv_time.setText("");
                }
                TextView tv_content=holder.getView(R.id.tv_content);
                tv_content.setText(data.getContent());
                ImageView iv_red=holder.getView(R.id.iv_red);
                if ("0".equals(data.getIsRead())) {
                    iv_red.setVisibility(View.VISIBLE);
                } else {
                    iv_red.setVisibility(View.GONE);
                }
                TextView tv_title=holder.getView(R.id.tv_title);
                tv_title.setText("确认参赛");
                TextView tv_detail=holder.getView(R.id.tv_detail);
                if("1".equals(data.getIsDo())){
                    tv_detail.setVisibility(View.GONE);
                }else {
                    tv_detail.setVisibility(View.VISIBLE);
                }
                //侧滑操作
                final HorizontalScrollView hsv=holder.getView(R.id.hsv);
                final TextView tv_delete=holder.getView(R.id.tv_delete);
                tv_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //删除消息
                        ZillaApi.NormalRestAdapter.create(Message2Service.class)
                                .deleteOneMsg(UserInfoModel.getInstance().getToken(),
                                        data.getMsgType(),
                                        data.getMsgId(),
                                        new RequestCallback<ResponseData>() {
                                            @Override
                                            public void success(ResponseData responseData, Response response) {
                                                if(responseData.getStatus()==200){
                                                    operatList.remove(position);
                                                    adapter.notifyDataSetChanged();
                                                }
                                            }
                                        });
                    }
                });
                LinearLayout container=holder.getView(R.id.ll_container);
                ViewGroup.LayoutParams params= container.getLayoutParams();
                params.width= DisplayUtil.getMobileWidth(MessageOperatorActivity.this);
                container.setLayoutParams(params);
                hsv.setOnTouchListener(new View.OnTouchListener() {
                    int dx;
                    int lastX;
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        switch (motionEvent.getAction()){
                            case MotionEvent.ACTION_DOWN:
                                lastX= (int) motionEvent.getX();
                                break;
                            case MotionEvent.ACTION_MOVE:
                                dx= (int)motionEvent.getX()-lastX;
                                break;
                            case MotionEvent.ACTION_UP:
                                int width=tv_delete.getWidth()/2;
                                boolean show=dx<0?dx<=-width:!(dx>=width);
                                if(show){
                                    hsv.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            hsv.smoothScrollTo(hsv.getMaxScrollAmount(),0);
                                        }
                                    });

                                }else {
                                    hsv.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            hsv.smoothScrollTo(0,0);
                                        }
                                    });

                                }
                                break;
                        }
                        return false;
                    }
                });
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
        ZillaApi.NormalRestAdapter.create(Message2Service.class)
                .getOperateMsg(UserInfoModel.getInstance().getToken(),
                        UserInfoModel.getInstance().getUserId() + "",
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
            //更新小红点
            dialogShow("加载中");
            ZillaApi.NormalRestAdapter.create(Message2Service.class)
                    .getOperateMsg(UserInfoModel.getInstance().getToken(),
                            UserInfoModel.getInstance().getUserId() + "",
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
