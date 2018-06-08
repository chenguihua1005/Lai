package com.softtek.lai.module.personalPK.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.personalPK.adapter.PKListAdapter;
import com.softtek.lai.module.personalPK.model.PKDetailMold;
import com.softtek.lai.module.personalPK.presenter.PKListManager;
import com.softtek.lai.module.sport2.view.LaiSportActivity;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.utils.StringUtil;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;

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
    @InjectView(R.id.tv_unit1)
    TextView tv_unit1;
    @InjectView(R.id.tv_unit2)
    TextView tv_unit2;
    @InjectView(R.id.zongbushu)
    TextView zongbushu;
    @InjectView(R.id.sender1)
    ImageView sender1;

    @InjectView(R.id.btn_cancle_pk)
    TextView btn_cancle_pk;
    @InjectView(R.id.btn_restart)
    TextView btn_restart;
    @InjectView(R.id.btn_receive)
    TextView btn_receive;
    @InjectView(R.id.btn_refuse)
    TextView btn_refuse;
    @InjectView(R.id.tip_pk)
    TextView tip_pk;
    @InjectView(R.id.rl_load)
    RelativeLayout rl_load;
    @InjectView(R.id.iv_winner1)
    ImageView iv_winner1;
    @InjectView(R.id.iv_winner2)
    ImageView iv_winner2;
    @InjectView(R.id.sv_pk)
    ScrollView sv_pk;


    private PKListManager manager;
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

    int type;
    long pkId;

    @Override
    protected void initDatas() {
        manager = new PKListManager();
        type = getIntent().getIntExtra("pkType", 0);
        pkId = getIntent().getLongExtra("pkId", 0);
        dialogShow("加载中...");
        manager.getPKDetail(this, pkId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                doBack();
                break;
            case R.id.btn_cancle_pk:
                //取消PK赛
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("温馨提示")
                        .setMessage("确定要取消 pk 吗? 取消后不能恢复, 对手会收到一条取消 pk 的消息")
                        .setNegativeButton("稍候再说", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).setPositiveButton("取消PK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialogShow("取消PK...");
                                manager.cancelPK(model.getPKId(), new RequestCallback<ResponseData>() {
                                    @Override
                                    public void success(ResponseData responseData, Response response) {
                                        dialogDissmiss();
                                        if (responseData.getStatus() == 200) {
                                            //取消成功
                                            //列表进来的返回列表，创建进来的返回运动首页
                                            if (type == Constants.CREATE_PK) {
                                                //返回PK首页
                                                startActivity(new Intent(PKDetailActivity.this, LaiSportActivity.class));
                                            } else if (type == Constants.LIST_PK) {
                                                Intent intent = getIntent();
                                                intent.putExtra("isCancel", true);
                                                setResult(RESULT_OK, intent);
                                                finish();
                                            }else if(type== Constants.MESSAGE_PK){
                                                //如果是从消息列表过来的话
                                                Intent intent =getIntent();
                                                //设置返回数据
                                                setResult(RESULT_OK, intent);
                                                finish();
                                            }else {
                                                finish();
                                            }
                                        } else if (responseData.getStatus() == 100) {
                                            rl_load.setVisibility(View.VISIBLE);
                                            sv_pk.setVisibility(View.GONE);
                                        } else if (responseData.getStatus() == 201) {
                                            Toast.makeText(PKDetailActivity.this, responseData.getMsg(), Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(PKDetailActivity.this, "取消失败", Toast.LENGTH_SHORT).show();

                                        }
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        dialogDissmiss();
                                        super.failure(error);
                                    }
                                });
                            }
                        }).create();
                dialog.show();

                break;
            case R.id.cb_zan_left:
                if (cb_zan_left.isEnabled()) {
                    cb_zan_left.setChecked(true);
                    cb_zan_left.setEnabled(false);
                    final int left_zan = Integer.parseInt(cb_zan_left.getText().toString()) + 1;
                    cb_zan_left.setText(left_zan + "");
                    manager.doZan(model.getPKId(), 0, new RequestCallback<ResponseData>() {
                        @Override
                        public void success(ResponseData responseData, Response response) {
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            cb_zan_left.setText(left_zan - 1 + "");
                            cb_zan_left.setChecked(false);
                            cb_zan_left.setEnabled(true);
                            super.failure(error);
                        }
                    });
                }
                break;
            case R.id.cb_zan_right:
                if (cb_zan_right.isEnabled()) {
                    cb_zan_right.setEnabled(false);
                    cb_zan_right.setChecked(true);
                    final int right_zan = Integer.parseInt(cb_zan_right.getText().toString()) + 1;
                    cb_zan_right.setText(right_zan + "");
                    manager.doZan(model.getPKId(), 1, new RequestCallback<ResponseData>() {
                        @Override
                        public void success(ResponseData responseData, Response response) {
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            cb_zan_right.setText(right_zan - 1 + "");
                            cb_zan_right.setEnabled(true);
                            cb_zan_right.setChecked(false);
                            super.failure(error);
                        }
                    });
                }
                break;
            case R.id.btn_receive:
                //接受挑战
                dialogShow("接受中...");
                manager.promiseOrRefuse(model.getPKId(), 1,
                        new RequestCallback<ResponseData>() {
                            @Override
                            public void success(ResponseData responseData, Response response) {
                                dialogDissmiss();
                                if (responseData.getStatus() == 200) {
                                    agreeBehavior();
                                } else {
                                    rl_load.setVisibility(View.VISIBLE);
                                    sv_pk.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                dialogDissmiss();
                                super.failure(error);
                            }
                        });
                break;
            case R.id.btn_refuse:
                //拒绝挑战
                dialogShow("拒绝中...");
                manager.promiseOrRefuse(model.getPKId(), -1,
                        new RequestCallback<ResponseData>() {
                            @Override
                            public void success(ResponseData responseData, Response response) {
                                dialogDissmiss();
                                if (responseData.getStatus() == 200) {
                                    refuseBehavior();
                                } else {
                                    rl_load.setVisibility(View.VISIBLE);
                                    sv_pk.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                dialogDissmiss();
                                super.failure(error);
                            }
                        });
                break;
            case R.id.btn_restart:
                //重新发起挑战
                dialogShow("重新发起...");
                manager.resetPK(model.getPKId(), new RequestCallback<ResponseData>() {
                    @Override
                    public void success(ResponseData responseData, Response response) {
                        dialogDissmiss();
                        if (responseData.getStatus() == 200) {
                            //重启成功、
                            resetPKBehavior();
                        } else if (responseData.getStatus() == 100) {
                            rl_load.setVisibility(View.VISIBLE);
                            sv_pk.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        dialogDissmiss();
                        super.failure(error);
                    }
                });
                break;
        }
    }

    public static final int NOCHALLENGE = 0;
    public static final int CHALLENGING = 1;
    public static final int REFUSE = -1;


    public void getPKDetail(PKDetailMold model, int resultCode) {
        dialogDissmiss();
        try {
            if (model == null && resultCode == -1) {
                return;
            } else if (model == null && resultCode == 100) {
                rl_load.setVisibility(View.VISIBLE);
                sv_pk.setVisibility(View.GONE);
                return;
            }
            rl_load.setVisibility(View.GONE);
            sv_pk.setVisibility(View.VISIBLE);
            this.model = model;
            //更新数据
            tv_pk_name1.setText(StringUtil.showName(model.getUserName(), model.getMobile()));
            tv_pk_name2.setText(StringUtil.showName(model.getBUserName(), model.getBMobile()));
            cb_zan_left.setText(model.getChpcou() + "");
            cb_zan_right.setText(model.getBchpcou() + "");
            tv_time.setText(DateUtil.getInstance().convertDateStr(model.getStart(), "yyyy年MM月dd日") + " — " +
                    DateUtil.getInstance().convertDateStr(model.getEnd(), "yyyy年MM月dd日"));
            if (model.getPraiseStatus() == 0) {//可已点咱
                cb_zan_left.setEnabled(true);
                cb_zan_left.setChecked(false);
            } else {//不可已
                cb_zan_left.setEnabled(false);
                cb_zan_left.setChecked(true);
            }
            if (model.getBPraiseStatus() == 0) {//可已点咱
                cb_zan_right.setEnabled(true);
                cb_zan_right.setChecked(false);
            } else {//不可已
                cb_zan_right.setEnabled(false);
                cb_zan_right.setChecked(true);
            }

            if (model.getStatus() == NOCHALLENGE) {
                tv_is_accept.setText("未应战");
            } else if (model.getStatus() == CHALLENGING) {
                tv_is_accept.setText("已应战");
            } else if (model.getStatus() == REFUSE) {
                tv_is_accept.setText("已拒绝");
            }
            if (model.getTStatus() ==NOSTART) {
                tv_status.setBackgroundResource(R.drawable.pk_list_weikaishi);
                tv_status.setText("未开始");
            } else if (model.getTStatus() == PROCESSING) {
                tv_status.setBackgroundResource(R.drawable.pk_list_jingxingzhong);
                tv_status.setText("进行中");
            } else if (model.getTStatus() == Completed) {
                tv_status.setBackgroundResource(R.drawable.pk_list_yijieshu);
                tv_status.setText("已结束");
            }
            if (model.getChipType() == Constants.NAIXI) {
                iv_type.setBackgroundResource(R.drawable.pk_naixi);
                tv_content.setText(R.string.naixi);
            } else if (model.getChipType() == Constants.NAIXICAO) {
                iv_type.setBackgroundResource(R.drawable.pk_list_naixicao);
                tv_content.setText(R.string.naixicao);
            } else if (model.getChipType() == Constants.ZIDINGYI) {
                iv_type.setBackgroundResource(R.drawable.pk_chouma);
                tv_content.setText(model.getChip());
            }
            //判断当前是步数比赛还是公里数比赛
            int targetType = model.getTargetType();
            if (targetType == 1) {//公里
                iv_target_icon.setBackgroundResource(R.drawable.pk_km);
                tv_target_content.setText("目标公里数：");
                tv_target.setText((int) Double.parseDouble(model.getTarget()) + "公里");
                tv_unit1.setText("公里");
                tv_unit2.setText("公里");
                zongbushu.setText("当前公里数");
                //1公里是1428步
                double gongli = 1.0 * model.getChaTotal() / 1428;
                double bgongli = 1.0 * model.getBchaTotal() / 1428;
                DecimalFormat format = new DecimalFormat("#0.00");
                tv_bushu1.setText(format.format(gongli));
                tv_bushu2.setText(format.format(bgongli));
            } else {//步数
                iv_target_icon.setBackgroundResource(R.drawable.pk_bushu);
                tv_target_content.setText("目标步数：");
                tv_target.setText("在PK期限内，达成更多步数的人即为赢家");
                tv_unit1.setText("步");
                tv_unit2.setText("步");
                zongbushu.setText("当前总步数");
                tv_bushu1.setText(model.getChaTotal() + "");
                tv_bushu2.setText(model.getBchaTotal() + "");
            }

            //载入头像
            String path = AddressManager.get("photoHost");
            if (StringUtils.isNotEmpty(model.getPhoto())) {
                Picasso.with(this).load(path + model.getPhoto()).fit()
                        .placeholder(R.drawable.img_default)
                        .error(R.drawable.img_default)
                        .into(sender1_header);
            }
            if (StringUtils.isNotEmpty(model.getBPhoto())) {
                Picasso.with(this).load(path + model.getBPhoto()).fit()
                        .placeholder(R.drawable.img_default)
                        .error(R.drawable.img_default)
                        .into(sender2_header);
            }
            if (model.getTStatus() == PKListAdapter.Completed) {//如果这个PK是已经结束的就什么操作都不需要显示
                if (StringUtils.isEmpty(model.getWinnerId())) {
                    return;
                }
                if (Long.parseLong(model.getWinnerId()) == model.getChallenged()) {
                    //发起方胜利
                    sender1.setVisibility(View.GONE);//隐藏发起者标识
                    //显示胜利者表示
                    iv_winner1.setVisibility(View.VISIBLE);
                    iv_winner2.setVisibility(View.GONE);
                } else {
                    //接受方胜利
                    sender1.setVisibility(View.VISIBLE);//显示发起者标识
                    iv_winner2.setVisibility(View.VISIBLE);
                    iv_winner1.setVisibility(View.GONE);
                }
                return;
            } else {
                sender1.setVisibility(View.VISIBLE);//显示发起者标识
                //隐藏胜利者标识
                iv_winner1.setVisibility(View.GONE);
                iv_winner2.setVisibility(View.GONE);
            }
            long userId = Long.parseLong(UserInfoModel.getInstance().getUser().getUserid());
            if (userId == model.getChallenged()) {
                //发起方当前PK状态
                changeStatus(model.getTStatus(), model.getStatus());
            } else if (userId == model.getBeChallenged()) {
                //接受方逻辑
                beChangeStatus(model.getTStatus(), model.getStatus());
            } else {//其他用户
                other(model.getTStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //发起方状态操作
    private void changeStatus(int pkStatus, int bStatus) {
        clearStatus();
        switch (pkStatus) {
            case NOSTART://未开始
                if (bStatus == REFUSE) {//如果接受者拒绝
                    btn_cancle_pk.setVisibility(View.VISIBLE);
                    btn_restart.setVisibility(View.VISIBLE);
                } else if (bStatus == NOCHALLENGE) {//接受者未应战
                    btn_cancle_pk.setVisibility(View.VISIBLE);
                } else {//接受者接受
                    tip_pk.setVisibility(View.VISIBLE);
                    tip_pk.setText("PK还没开始，准备好状态吧！");
                }
                break;
            case PROCESSING://进行中
                tip_pk.setVisibility(View.VISIBLE);
                tip_pk.setText("PK已开始，要加油哦！");
                break;
            case Completed://已结束
                break;
        }
    }
    public static final int NOSTART=0;
    public static final int PROCESSING=1;
    public static final int Completed=2;


    //接受方逻辑
    private void beChangeStatus(int pkStatus, int bStatus) {
        clearStatus();
        switch (pkStatus) {
            case NOSTART://未开始
                if (bStatus == REFUSE) {//如果接受者拒绝
                    //显示旁观者提示
                    tip_pk.setVisibility(View.VISIBLE);
                    tip_pk.setText("PK还没开始，欲知详情，敬请围观！");
                } else if (bStatus == NOCHALLENGE) {//接受者未应战
                    btn_refuse.setVisibility(View.VISIBLE);
                    btn_receive.setVisibility(View.VISIBLE);
                } else {//接受者接受
                    tip_pk.setVisibility(View.VISIBLE);
                    tip_pk.setText("PK还没开始，准备好状态吧！");
                }
                break;
            case PROCESSING://进行中
                tip_pk.setVisibility(View.VISIBLE);
                tip_pk.setText("PK已开始，要加油哦！");
                break;
            case Completed://已结束
                break;
        }
    }

    //其他用户
    private void other(int pkStatus) {
        clearStatus();
        switch (pkStatus) {
            case NOSTART://未开始
                tip_pk.setVisibility(View.VISIBLE);
                tip_pk.setText("PK还没开始，欲知详情，敬请围观！");
                break;
            case PROCESSING://进行中
                tip_pk.setVisibility(View.VISIBLE);
                tip_pk.setText("PK正在进行，为他们加油吧！");
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //做返回操作
            doBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void doBack() {
        if (type == Constants.CREATE_PK) {//创建新PK跳转过来,按下返回按钮直接返回PK首页
            Intent intent = new Intent(this, LaiSportActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (type == Constants.MESSAGE_PK) {
            //如果是从消息列表过来的话
            Intent intent = getIntent();
            //设置返回数据
            setResult(RESULT_OK, intent);
            finish();
        } else if (type == Constants.LIST_PK) {
            Intent intent = getIntent();
            intent.putExtra("isCancel", false);
            //需要改变一些状态
            intent.putExtra("ChP", cb_zan_left.getText().toString());
            intent.putExtra("BChP", cb_zan_right.getText().toString());
            intent.putExtra("isPraise",cb_zan_left.isChecked());
            intent.putExtra("isBPraise",cb_zan_right.isChecked());
            setResult(RESULT_OK, intent);
            finish();
        }else {
            finish();
        }
    }

    //拒绝行为
    private void refuseBehavior() {
        //隐藏拒绝按钮，隐藏同意按钮
        tv_is_accept.setText("已拒绝");
        btn_refuse.setVisibility(View.GONE);
        btn_receive.setVisibility(View.GONE);
        //显示旁观者提示
        tip_pk.setVisibility(View.VISIBLE);
        tip_pk.setText("PK还没开始，欲知详情，敬请围观！");

    }

    //同意行为
    private void agreeBehavior() {
        //隐藏拒绝按钮，隐藏同意按钮
        tv_is_accept.setText("已应战");
        btn_refuse.setVisibility(View.GONE);
        btn_receive.setVisibility(View.GONE);
        //显示旁观者提示
        tip_pk.setVisibility(View.VISIBLE);
        tip_pk.setText("PK还没开始，准备好状态吧！");
    }

    //重新发起挑战
    private void resetPKBehavior() {
        tv_is_accept.setText("未应战");
        btn_restart.setVisibility(View.GONE);
        btn_cancle_pk.setVisibility(View.VISIBLE);
    }

    private void clearStatus() {
        btn_refuse.setVisibility(View.GONE);
        btn_receive.setVisibility(View.GONE);
        btn_restart.setVisibility(View.GONE);
        btn_cancle_pk.setVisibility(View.GONE);
        tip_pk.setVisibility(View.GONE);
    }
}
