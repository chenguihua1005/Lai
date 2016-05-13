package com.softtek.lai.module.personalPK.view;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.personalPK.adapter.PKListAdapter;
import com.softtek.lai.module.personalPK.model.PKDetailMold;
import com.softtek.lai.module.personalPK.presenter.PKListManager;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_pkdetail)
public class PKDetailActivity extends BaseActivity implements OnClickListener {

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.cb_zan_left)
    CheckBox cb_zan_left;
    @InjectView(R.id.cb_zan_right)
    CheckBox cb_zan_right;
    @InjectView(R.id.tv_status)
    TextView tv_status;
    @InjectView(R.id.tv_is_accept)
    TextView tv_is_accept;
    @InjectView(R.id.sender1_header)
    CircleImageView sender1_header;
    @InjectView(R.id.sender2_header)
    CircleImageView sender2_header;
    @InjectView(R.id.tv_pk_name1)
    TextView tv_pk_name1;
    @InjectView(R.id.tv_pk_name2)
    TextView tv_pk_name2;
    @InjectView(R.id.tv_bushu1)
    TextView tv_bushu1;
    @InjectView(R.id.tv_bushu2)
    TextView tv_bushu2;
    @InjectView(R.id.iv_type)
    ImageView iv_type;
    @InjectView(R.id.tv_content)
    TextView tv_content;
    @InjectView(R.id.tv_time)
    TextView tv_time;
    @InjectView(R.id.iv_target_icon)
    ImageView iv_target_icon;
    @InjectView(R.id.tv_target_content)
    TextView tv_target_content;
    @InjectView(R.id.tv_target)
    TextView tv_target;
    @InjectView(R.id.btn_cancle_pk)
    TextView btn_cancle_pk;
    @InjectView(R.id.tv_unit1)
    TextView tv_unit1;
    @InjectView(R.id.tv_unit2)
    TextView tv_unit2;
    @InjectView(R.id.zongbushu)
    TextView zongbushu;
    @InjectView(R.id.btn_receive)
    TextView btn_receive;
    @InjectView(R.id.btn_refuse)
    TextView btn_refuse;
    @InjectView(R.id.btn_restart)
    TextView btn_restart;
    @InjectView(R.id.tip_pk)
    TextView tip_pk;



    private PKListManager manager;
    private long pkId;
    private PKDetailMold model;

    @Override
    protected void initViews() {
        tv_title.setText("PK挑战详情");
        ll_left.setOnClickListener(this);
        cb_zan_left.setOnClickListener(this);
        cb_zan_right.setOnClickListener(this);

        btn_cancle_pk.setOnClickListener(this);
        btn_receive.setOnClickListener(this);
        btn_refuse.setOnClickListener(this);
        btn_restart.setOnClickListener(this);
    }

    int tStatus=0;
    int type;

    @Override
    protected void initDatas() {
        manager = new PKListManager();
        String path = AddressManager.get("photoHost");
        tStatus=getIntent().getIntExtra("isEnd",0);
        model = getIntent().getParcelableExtra("pkmodel");
        type=getIntent().getIntExtra("pkType",0);
        if(type== Constants.CREATE_PK){//创建新PK跳转过来
            btn_cancle_pk.setVisibility(View.VISIBLE);
        }else if(type== Constants.LIST_PK){

        }else if(type== Constants.MESSAGE_PK){
            btn_receive.setVisibility(View.VISIBLE);
            btn_refuse.setVisibility(View.VISIBLE);
        }
        pkId=model.getPKId();
        tv_pk_name1.setText(model.getUserName());
        tv_pk_name2.setText(model.getBUserName());
        cb_zan_left.setText(model.getChpcou() + "");
        cb_zan_right.setText(model.getBchpcou() + "");
        tv_time.setText(DateUtil.getInstance().convertDateStr(model.getStart(), "yyyy年MM月dd日") + "——" +
                DateUtil.getInstance().convertDateStr(model.getEnd(), "yyyy年MM月dd日"));
        cb_zan_left.setEnabled(false);
        cb_zan_right.setEnabled(false);
        if (model.getTStatus() == 0) {
            tv_status.setBackgroundResource(R.drawable.pk_list_weikaishi);
            tv_status.setText("未开始");
        } else if (model.getTStatus() == 1) {
            tv_status.setBackgroundResource(R.drawable.pk_list_jingxingzhong);
            tv_status.setText("进行中");
        } else if (model.getTStatus() == 2) {
            tv_status.setBackgroundResource(R.drawable.pk_list_yijieshu);
            tv_status.setText("以结束");
        }
        /*if (model.getStatus() == NOCHALLENGE) {
            tv_is_accept.setText("未应战");
        } else if (model.getStatus() == CHALLENGING) {
            tv_is_accept.setText("已应战");
        } else if (model.getStatus() == REFUSE) {
            tv_is_accept.setText("拒绝");
        }*/
        /*if (model.getChipType() == PKListAdapter.NAIXI) {
            iv_type.setBackgroundResource(R.drawable.pk_naixi);
            tv_content.setText(R.string.naixi);
        } else if (model.getChipType() == PKListAdapter.NAIXICAO) {
            iv_type.setBackgroundResource(R.drawable.pk_list_naixicao);
            tv_content.setText(R.string.naixicao);
        } else if (model.getChipType() == PKListAdapter.CUSTOM) {
            iv_type.setBackgroundResource(R.drawable.pk_chouma);
        }*/
        //判断当前是步数比赛还是公里数比赛
        /*int targetType = model.getTargetType();
        if (targetType == 1) {//公里
            iv_target_icon.setBackgroundResource(R.drawable.pk_km);
            tv_target_content.setText("目标公里数：");
            tv_target.setText(model.getTarget() + "公里");
            tv_unit1.setText("公里");
            tv_unit2.setText("公里");
            zongbushu.setText("当前公里数");
        } else {//步数
            iv_target_icon.setBackgroundResource(R.drawable.pk_bushu);
            tv_target_content.setText("目标步数：");
            tv_target.setText(model.getTarget());
            tv_unit1.setText("步");
            tv_unit2.setText("步");
            zongbushu.setText("当前步数");
        }
        tv_bushu1.setText(model.getChaTotal() + "");
        tv_bushu2.setText(model.getBchaTotal() + "");*/

        //载入头像
        if (StringUtils.isNotEmpty(model.getPhoto())) {
            Picasso.with(this).load(path + model.getPhoto()).fit()
                    .placeholder(R.drawable.img_default)
                    .error(R.drawable.img_default)
                    .into(sender1_header);
        }
        if (StringUtils.isNotEmpty(model.getBPhoto())) {
            Picasso.with(this).load(path + model.getPhoto()).fit()
                    .placeholder(R.drawable.img_default)
                    .error(R.drawable.img_default)
                    .into(sender2_header);
        }

        dialogShow("加载中...");
        manager.getPKDetail(this, model.getPKId());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                doBack();
                break;
            case R.id.btn_cancle_pk:
                //取消PK赛
                break;
            case R.id.cb_zan_left:
                cb_zan_left.setChecked(true);
                final int left_zan=Integer.parseInt(cb_zan_left.getText().toString())+1;
                cb_zan_left.setText(left_zan+"");
                cb_zan_left.setEnabled(false);
                manager.doZan(pkId, 0, new RequestCallback<ResponseData>() {
                    @Override
                    public void success(ResponseData responseData, Response response) {

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        cb_zan_left.setText(left_zan-1+"");
                        cb_zan_left.setChecked(false);
                        cb_zan_left.setEnabled(true);
                        super.failure(error);
                    }
                });
                break;
            case R.id.cb_zan_right:
                final int right_zan=Integer.parseInt(cb_zan_left.getText().toString())+1;
                cb_zan_right.setChecked(true);
                cb_zan_right.setText(right_zan+"");
                cb_zan_right.setEnabled(false);
                manager.doZan(pkId, 1, new RequestCallback<ResponseData>() {
                    @Override
                    public void success(ResponseData responseData, Response response) {

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        cb_zan_right.setText(right_zan-1+"");
                        cb_zan_right.setEnabled(true);
                        cb_zan_right.setChecked(false);
                        super.failure(error);
                    }
                });
                break;
            case R.id.btn_receive:
                //接受挑战
                manager.promiseOrRefuse(pkId,1,
                        new RequestCallback<ResponseData>() {
                            @Override
                            public void success(ResponseData responseData, Response response) {
                                if(responseData.getStatus()==200){
                                    //应战
                                    tv_status.setText("进行中");
                                    tv_is_accept.setText("以应战");
                                    model.setStatus(CHALLENGING);
                                    btn_receive.setVisibility(View.GONE);
                                    btn_refuse.setVisibility(View.GONE);

                                }
                            }

                        });
                break;
            case R.id.btn_refuse:
                //拒绝挑战
                manager.promiseOrRefuse(pkId,-1,
                        new RequestCallback<ResponseData>() {
                            @Override
                            public void success(ResponseData responseData, Response response) {
                                if(responseData.getStatus()==200){
                                    //应战
                                    tv_status.setText("未开始");
                                    tv_is_accept.setText("拒绝");
                                    model.setStatus(REFUSE);
                                    btn_receive.setVisibility(View.GONE);
                                    btn_refuse.setVisibility(View.GONE);
                                    tip_pk.setVisibility(View.VISIBLE);

                                }
                            }

                        });
                break;
            case R.id.btn_restart:
                //重新发起挑战

                break;
        }
    }

    public static final int NOCHALLENGE = 0;
    public static final int CHALLENGING = 1;
    public static final int REFUSE = -1;

    public void getPKDetail(PKDetailMold model) {
        dialogDissmiss();
        if (model == null) {
            return;
        }
        this.model=model;
        //更新数据
        tv_pk_name1.setText(model.getUserName());
        tv_pk_name2.setText(model.getBUserName());
        cb_zan_left.setText(model.getChpcou() + "");
        cb_zan_right.setText(model.getBchpcou() + "");
        tv_time.setText(DateUtil.getInstance().convertDateStr(model.getStart(), "yyyy年MM月dd日") + "——" +
                DateUtil.getInstance().convertDateStr(model.getEnd(), "yyyy年MM月dd日"));
        if(model.getPraiseStatus()==0){//可以点咱
            cb_zan_left.setEnabled(true);
            cb_zan_left.setChecked(false);
        }else{//不可以
            cb_zan_left.setEnabled(false);
            cb_zan_left.setChecked(true);
        }
        if(model.getBPraiseStatus()==0){//可以点咱
            cb_zan_right.setEnabled(true);
            cb_zan_right.setChecked(false);
        }else{//不可以
            cb_zan_right.setEnabled(false);
            cb_zan_right.setChecked(true);
        }

        if (model.getStatus() == NOCHALLENGE) {
            tv_is_accept.setText("未应战");
        } else if (model.getStatus() == CHALLENGING) {
            tv_is_accept.setText("以应战");
        } else if (model.getStatus() == REFUSE) {
            tv_is_accept.setText("拒绝");
        }
        if (model.getChipType() == PKListAdapter.NAIXI) {
            iv_type.setBackgroundResource(R.drawable.pk_naixi);
            tv_content.setText(R.string.naixi);
        } else if (model.getChipType() == PKListAdapter.NAIXICAO) {
            iv_type.setBackgroundResource(R.drawable.pk_list_naixicao);
            tv_content.setText(R.string.naixicao);
        } else if (model.getChipType() == PKListAdapter.CUSTOM) {
            iv_type.setBackgroundResource(R.drawable.pk_chouma);
            tv_content.setText(model.getChip());
        }
        //判断当前是步数比赛还是公里数比赛
        int targetType = model.getTargetType();
        if (targetType == 1) {//公里
            iv_target_icon.setBackgroundResource(R.drawable.pk_km);
            tv_target_content.setText("目标公里数：");
            tv_target.setText(model.getTarget() + "公里");
            tv_unit1.setText("公里");
            tv_unit2.setText("公里");
            zongbushu.setText("当前公里数");
        } else {//步数
            iv_target_icon.setBackgroundResource(R.drawable.pk_bushu);
            tv_target_content.setText("目标步数：");
            tv_target.setText("在PK期限内，达成更多步数的人即为赢家");
            tv_unit1.setText("步");
            tv_unit2.setText("步");
            zongbushu.setText("当前步数");
        }
        tv_bushu1.setText(model.getChaTotal() + "");
        tv_bushu2.setText(model.getBchaTotal() + "");
        if(tStatus==2){//如果这个PK是已经结束的就什么操作都不需要显示
            return;
        }
        long userId=Long.parseLong(UserInfoModel.getInstance().getUser().getUserid());
        if ( userId== model.getChallenged()) {
            //发起方当前PK状态
            changeStatus(model.getStatus());
        }else if(userId==model.getBeChallenged()){
            //接受方逻辑
            beChangeStatus(model.getStatus());
        }
    }

    //发起方状态操作
    private void changeStatus(int pkStatus){
        switch (pkStatus){
            case NOCHALLENGE:
                btn_cancle_pk.setVisibility(View.VISIBLE);//可以取消PK
                break;
            case CHALLENGING:
                break;
            case REFUSE:
                //如果对方拒绝了你的挑战则可以取消PK或者重新发起
                btn_cancle_pk.setVisibility(View.VISIBLE);//可以取消PK
                btn_restart.setVisibility(View.VISIBLE);
                break;
        }
    }
    //接受方逻辑
    /*
    接受方只可能有两种状态
     */
    private void beChangeStatus(int pkStatus){
        switch (pkStatus){
            case NOCHALLENGE:
                btn_receive.setVisibility(View.VISIBLE);
                btn_refuse.setVisibility(View.VISIBLE);
                break;
            case CHALLENGING:
                break;
            case REFUSE:
                tip_pk.setVisibility(View.VISIBLE);//显示提示信息
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_DOWN){
            //做返回操作
            doBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void doBack(){
        if(type== Constants.CREATE_PK){//创建新PK跳转过来,按下返回按钮直接返回PK列表页
            Intent intent=new Intent(this,PKListActivity.class);
            startActivity(intent);
            finish();
        }else if(type== Constants.LIST_PK){
            Intent intent=getIntent();
            //需要改变一些状态
            intent.putExtra("ChP",cb_zan_left.getText().toString());
            intent.putExtra("BChP",cb_zan_right.getText().toString());
            if (model.getStatus() == NOCHALLENGE) {
                intent.putExtra("status",0);//未应战
            } else if (model.getStatus() == CHALLENGING) {
                //这里要更具时间判断
                int eq=DateUtil.getInstance().compare(DateUtil.getInstance().getCurrentDate(),model.getStart());
                if(eq==-1){
                    //小于开始日期
                    intent.putExtra("status",0);//未开始
                }else{
                    eq=DateUtil.getInstance().compare(DateUtil.getInstance().getCurrentDate(),model.getEnd());
                    if(eq==1){
                        intent.putExtra("status",2);//已结束
                    }else{
                        intent.putExtra("status",1);//进行中
                    }
                }

            } else if (model.getStatus() == REFUSE) {
                intent.putExtra("status",0);//拒绝表示未应战
            }
            setResult(RESULT_OK,intent);
            finish();
        }else if(type== Constants.MESSAGE_PK){
            //如果是从消息列表过来的话
            finish();
        }
    }
}
