/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.message.view;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.counselor.adapter.HonorStudentAdapter;
import com.softtek.lai.module.counselor.model.HonorInfoModel;
import com.softtek.lai.module.counselor.model.HonorTable1Model;
import com.softtek.lai.module.counselor.model.HonorTableModel;
import com.softtek.lai.module.counselor.presenter.HonorImpl;
import com.softtek.lai.module.counselor.presenter.IHonorPresenter;
import com.softtek.lai.module.counselor.view.AssistantActivity;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.message.model.MeasureRemindInfo;
import com.softtek.lai.module.message.model.MessageDetailInfo;
import com.softtek.lai.module.message.model.MessageModel;
import com.softtek.lai.module.message.presenter.IMessagePresenter;
import com.softtek.lai.module.message.presenter.MessageImpl;
import com.softtek.lai.utils.ACache;
import com.softtek.lai.utils.SoftInputUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jarvis.liu on 3/22/2016.
 * 荣誉榜
 */
@InjectLayout(R.layout.activity_message)
public class MessageActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener, BaseFragment.OnFragmentInteractionListener {

    @LifeCircleInject
    ValidateLife validateLife;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.text_value)
    TextView text_value;

    @InjectView(R.id.text_value1)
    TextView text_value1;

    @InjectView(R.id.text_value2)
    TextView text_value2;

    @InjectView(R.id.text_title)
    TextView text_title;

    @InjectView(R.id.img)
    ImageView img;

    @InjectView(R.id.rel_fc)
    RelativeLayout rel_fc;

    @InjectView(R.id.rel_1)
    RelativeLayout rel_1;

    @InjectView(R.id.rel_2)
    RelativeLayout rel_2;


    private IMessagePresenter messagePresenter;
    private ACache aCache;
    MessageModel messageModel;
    ArrayList<MessageDetailInfo> list_remove = new ArrayList<MessageDetailInfo>();
    ArrayList<MessageDetailInfo> list_apply = new ArrayList<MessageDetailInfo>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_left.setOnClickListener(this);
        rel_fc.setOnClickListener(this);
        rel_1.setOnClickListener(this);
        rel_2.setOnClickListener(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(MessageModel messageModel) {
        this.messageModel = messageModel;
        System.out.println("messageModel:" + messageModel);
        String userrole = UserInfoModel.getInstance().getUser().getUserrole();
        if (String.valueOf(Constants.SR).equals(userrole)) {
            if (messageModel.getSPInviteSR().size() == 0) {
                rel_1.setVisibility(View.GONE);
            } else {
                rel_1.setVisibility(View.VISIBLE);
                text_title.setText(getResources().getText(R.string.message1));
                img.setImageResource(R.drawable.img_assistant_apply);
                text_value1.setText(messageModel.getSPInviteSR().get(0).getComments());
            }
        } else if (String.valueOf(Constants.SP).equals(userrole)) {
            ArrayList<MessageDetailInfo> list = messageModel.getSRandPCApply();
            for (int i = 0; i < list.size(); i++) {
                MessageDetailInfo messageDetailInfo = list.get(i);
                String type = messageDetailInfo.getMsgType();
                if ("0".equals(type)) {
                    list_apply.add(messageDetailInfo);
                } else {
                    list_remove.add(messageDetailInfo);
                }
            }
            if (list_remove.size() == 0) {
                rel_2.setVisibility(View.GONE);
            } else {
                rel_2.setVisibility(View.VISIBLE);
                text_value2.setText(list_remove.get(0).getComments());
            }

            if (list_apply.size() == 0) {
                rel_1.setVisibility(View.GONE);
            } else {
                rel_1.setVisibility(View.VISIBLE);
                text_title.setText("助教申请");
                img.setImageResource(R.drawable.img_assistant_apply);
                text_value1.setText(list_apply.get(0).getComments());
            }
        } else {
            if (messageModel.getPCJoin().size() == 0) {
                rel_1.setVisibility(View.GONE);
            } else {
                rel_1.setVisibility(View.VISIBLE);
                text_title.setText(getResources().getText(R.string.message3));
                text_value1.setText(messageModel.getPCJoin().get(0).getComments());
                img.setImageResource(R.drawable.img_game_invite);
            }
        }
        if (messageModel.getMeasureRemind().size() == 0) {
            rel_fc.setVisibility(View.GONE);
        } else {
            rel_fc.setVisibility(View.VISIBLE);
            text_value.setText(messageModel.getMeasureRemind().get(0).getContent());
        }
    }

    @Override
    protected void initViews() {
        //tv_left.setLayoutParams(new Toolbar.LayoutParams(DisplayUtil.dip2px(this,15),DisplayUtil.dip2px(this,30)));
        tv_title.setText(R.string.CounselorJ);

    }

    @Override
    public void onResume() {
        super.onResume();
        messagePresenter = new MessageImpl(this);
        aCache = ACache.get(this, Constants.USER_ACACHE_DATA_DIR);
        String id = UserInfoModel.getInstance().getUser().getUserid();
        messagePresenter.getMsgList(id);
    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.rel_1:
                String userrole = UserInfoModel.getInstance().getUser().getUserrole();
                ArrayList<MessageDetailInfo> list;
                if (String.valueOf(Constants.SR).equals(userrole)) {
                    list = messageModel.getSPInviteSR();
                    Intent intent = new Intent(this, MessageSrRemindActivity.class);
                    intent.putExtra("list", list);
                    startActivity(intent);
                } else if (String.valueOf(Constants.SP).equals(userrole)) {
                    Intent intent = new Intent(this, AssistantActivity.class);
                    startActivity(intent);
                } else {
                    list = messageModel.getPCJoin();
                    Intent intent = new Intent(this, MessagePcRemindActivity.class);
                    intent.putExtra("list", list);
                    startActivity(intent);
                }
                break;
            case R.id.rel_2:
                Intent intents = new Intent(this, MessageRemoveSrRemindActivity.class);
                System.out.println("list_remove:"+list_remove);
                intents.putExtra("list", list_remove);
                startActivity(intents);
                break;

            case R.id.rel_fc:
                ArrayList<MeasureRemindInfo> MeasureRemind = messageModel.getMeasureRemind();
                ArrayList<MeasureRemindInfo> fcRemind = new ArrayList<MeasureRemindInfo>();
                for (int i = 0; i < MeasureRemind.size(); i++) {
                    String type = MeasureRemind.get(i).getMessageType();
                    if ("2".equals(type)) {
                        fcRemind.add(MeasureRemind.get(i));
                    }
                }
                Intent intent = new Intent(this, MessageFcRemindActivity.class);
                intent.putExtra("list", MeasureRemind);
                startActivity(intent);
                break;

        }
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView, failedRule);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
