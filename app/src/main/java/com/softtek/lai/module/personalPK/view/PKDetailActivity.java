package com.softtek.lai.module.personalPK.view;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.personalPK.adapter.PKListAdapter;
import com.softtek.lai.module.personalPK.model.PKDetailMold;
import com.softtek.lai.module.personalPK.model.PKListModel;
import com.softtek.lai.module.personalPK.presenter.PKListManager;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import butterknife.InjectView;
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

    private PKListManager manager;

    @Override
    protected void initViews() {
        tv_title.setText("PK挑战详情");
        ll_left.setOnClickListener(this);
        btn_cancle_pk.setOnClickListener(this);
        cb_zan_left.setOnClickListener(this);
        cb_zan_right.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        manager=new PKListManager();
        PKListModel model=getIntent().getParcelableExtra("pkmodel");
        tv_pk_name1.setText(model.getUserName());
        tv_pk_name2.setText(model.getBUserName());
        cb_zan_left.setText(model.getChP()+"");
        cb_zan_right.setText(model.getBChp()+"");
        btn_cancle_pk.setEnabled(false);//禁用取消PK挑战按钮
        tv_time.setText(DateUtil.getInstance().convertDateStr(model.getStart(),"yyyy年MM月dd日")+"——"+
                DateUtil.getInstance().convertDateStr(model.getEnd(),"yyyy年MM月dd日"));
        if(model.getTStatus()== PKListAdapter.NOSTART){
            tv_status.setBackgroundResource(R.drawable.pk_list_weikaishi);
            tv_status.setText("未开始");
        }else if(model.getTStatus()==PKListAdapter.PROCESSING){
            tv_status.setBackgroundResource(R.drawable.pk_list_jingxingzhong);
            tv_status.setText("进行中");
        }else if(model.getTStatus()==PKListAdapter.Completed){
            tv_status.setBackgroundResource(R.drawable.pk_list_yijieshu);
            tv_status.setText("已结束");
        }
        if(model.getChipType()==PKListAdapter.NAIXI){
            iv_type.setBackgroundResource(R.drawable.pk_naixi);
            tv_content.setText(R.string.naixi);
        }else if(model.getChipType()==PKListAdapter.NAIXICAO){
            iv_type.setBackgroundResource(R.drawable.pk_list_naixicao);
            tv_content.setText(R.string.naixicao);
        }else if(model.getChipType()==PKListAdapter.CUSTOM){
            iv_type.setBackgroundResource(R.drawable.pk_chouma);
        }
        //载入头像
        String path= AddressManager.get("photoHost");
        if(StringUtils.isNotEmpty(model.getPhoto())){
            Picasso.with(this).load(path+model.getPhoto()).fit()
                    .placeholder(R.drawable.img_default)
                    .error(R.drawable.img_default)
                    .into(sender1_header);
        }
        if(StringUtils.isNotEmpty(model.getBPhoto())){
            Picasso.with(this).load(path+model.getPhoto()).fit()
                    .placeholder(R.drawable.img_default)
                    .error(R.drawable.img_default)
                    .into(sender2_header);
        }
        dialogShow("加载中...");
        manager.getPKDetail(this,model.getPKId());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
            case R.id.btn_cancle_pk:
                break;
            case R.id.cb_zan_left:
                break;
            case R.id.cb_zan_right:
                break;
        }
    }

    public static final int NOCHALLENGE=0;
    public static final int CHALLENGING=1;
    public static final int REFUSE=-1;

    public void getPKDetail(PKDetailMold model){
        dialogDissmiss();
        if(model==null){
            return;
        }
        //更新数据
        tv_pk_name1.setText(model.getUserName());
        tv_pk_name2.setText(model.getBUserName());
        cb_zan_left.setText(model.getChpcou()+"");
        cb_zan_right.setText(model.getBchpcou()+"");

        tv_time.setText(DateUtil.getInstance().convertDateStr(model.getStart(),"yyyy年MM月dd日")+"——"+
                DateUtil.getInstance().convertDateStr(model.getEnd(),"yyyy年MM月dd日"));
        if(model.getStatus()== NOCHALLENGE){
            tv_is_accept.setText("未应战");
        }else if(model.getStatus()==CHALLENGING){
            tv_is_accept.setText("以应战");
        }else if(model.getStatus()==REFUSE){
            tv_is_accept.setText("拒绝");
        }
        if(model.getChipType()==PKListAdapter.NAIXI){
            iv_type.setBackgroundResource(R.drawable.pk_naixi);
            tv_content.setText(R.string.naixi);
        }else if(model.getChipType()==PKListAdapter.NAIXICAO){
            iv_type.setBackgroundResource(R.drawable.pk_list_naixicao);
            tv_content.setText(R.string.naixicao);
        }else if(model.getChipType()==PKListAdapter.CUSTOM){
            iv_type.setBackgroundResource(R.drawable.pk_chouma);
            tv_content.setText(model.getChip());
        }
        //判断当前是步数比赛还是公里数比赛
        int targetType=model.getTargetType();
        if(targetType==1){//公里
            iv_target_icon.setBackgroundResource(R.drawable.pk_km);
            tv_target_content.setText("目标公里数：");
            tv_target.setText(model.getTarget()+"公里");
            tv_unit1.setText("公里");
            tv_unit2.setText("公里");
            zongbushu.setText("当前公里数");
        }else{//步数
            iv_target_icon.setBackgroundResource(R.drawable.pk_bushu);
            tv_target_content.setText("目标步数：");
            tv_target.setText(model.getTarget()+"步");
            tv_unit1.setText("步");
            tv_unit2.setText("步");
            zongbushu.setText("当前步数");
        }
        tv_bushu1.setText(model.getChaTotal()+"");
        tv_bushu2.setText(model.getBchaTotal()+"");
        if(Long.parseLong(UserInfoModel.getInstance().getUser().getUserid())==model.getChallenged()){
            btn_cancle_pk.setEnabled(true);
        }else{
            btn_cancle_pk.setEnabled(false);
        }
    }
}
