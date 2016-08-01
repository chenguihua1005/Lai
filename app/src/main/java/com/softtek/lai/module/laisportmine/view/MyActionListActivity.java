package com.softtek.lai.module.laisportmine.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.act.view.ActActivity;
import com.softtek.lai.module.group.view.GroupMainActivity;
import com.softtek.lai.module.laisportmine.adapter.MyActionAdapter;
import com.softtek.lai.module.laisportmine.adapter.MyPublicWealfareAdapter;
import com.softtek.lai.module.laisportmine.model.ActionModel;
import com.softtek.lai.module.laisportmine.model.PublicWewlfModel;
import com.softtek.lai.module.laisportmine.present.ActionListManager;
import com.softtek.lai.module.laisportmine.present.DelNoticeOrMeasureManager;
import com.softtek.lai.module.laisportmine.present.UpdateMsgRTimeManager;
import com.softtek.lai.module.personalPK.view.PKDetailActivity;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_my_action_list)
public class MyActionListActivity extends BaseActivity implements View.OnClickListener, ActionListManager.ActionListCallback, UpdateMsgRTimeManager.UpdateMsgRTimeCallback,
        AdapterView.OnItemLongClickListener, DelNoticeOrMeasureManager.DelNoticeOrMeasureCallback, AdapterView.OnItemClickListener {
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.list_action)
    ListView list_action;
    @InjectView(R.id.ll_action_nomessage)
    LinearLayout ll_action_nomessage;
    @InjectView(R.id.footer)
    LinearLayout footer;
    @InjectView(R.id.ll_select)
    LinearLayout ll_select;
    @InjectView(R.id.cb_all)
            CheckBox cb_all;
    MyActionAdapter myActionAdapter;
    private List<ActionModel> actionModelLists = new ArrayList<ActionModel>();
    ActionListManager actionListManager;
    UpdateMsgRTimeManager updateMsgRTimeManager;
    DelNoticeOrMeasureManager delNoticeOrMeasureManager;
    String accountid;
    boolean isselec = false;
    boolean isdelpage=false;
    int account=0;
    private CharSequence[] items = {"删除"};

    @Override
    protected void initViews() {
        tv_title.setText("活动邀请");
        ll_select.setOnClickListener(this);
        cb_all.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        list_action.setOnItemLongClickListener(this);
        list_action.setOnItemClickListener(this);
    }

    @Override
    protected void initDatas() {
        UserInfoModel userInfoModel = UserInfoModel.getInstance();
        accountid = userInfoModel.getUser().getUserid();
        myActionAdapter = new MyActionAdapter(this, actionModelLists, false,cb_all);
        list_action.setAdapter(myActionAdapter);
        actionListManager = new ActionListManager(this);
        actionListManager.GetActiveMsg(accountid);
        updateMsgRTimeManager = new UpdateMsgRTimeManager(this);
        updateMsgRTimeManager.doUpdateMsgRTime(accountid, "22");
        delNoticeOrMeasureManager = new DelNoticeOrMeasureManager(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                if (isdelpage)
                {
                    myActionAdapter = new MyActionAdapter(this, actionModelLists, false,cb_all);
                    list_action.setAdapter(myActionAdapter);
                    footer.setVisibility(View.GONE);
                    isdelpage=false;
                }
                else {
                    finish();
                }
                break;
            case R.id.cb_all:
            case R.id.ll_select:
                /*点击全选，若为true选中状态，则设为false，重置为未选中状态*/

                for (int i = 0; i < actionModelLists.size(); i++) {
                    if (actionModelLists.get(i).isselect())
                    {
                        account++;
                    }
                }
                if (account==actionModelLists.size())
                {
                    for (int i = 0; i < actionModelLists.size(); i++) {
                        actionModelLists.get(i).setIsselect(true);
                    }
                    myActionAdapter = new MyActionAdapter(this, actionModelLists, true,cb_all);
                    list_action.setAdapter(myActionAdapter);
                    isselec = true;
                    cb_all.setChecked(true);
                }
                else {
                    if (isselec) {
                        for (int i = 0; i < actionModelLists.size(); i++) {
                            actionModelLists.get(i).setIsselect(false);
                        }
                        myActionAdapter = new MyActionAdapter(this, actionModelLists, true,cb_all);
                        list_action.setAdapter(myActionAdapter);
                        isselec = false;
                        cb_all.setChecked(false);
                    } else {
                        for (int i = 0; i < actionModelLists.size(); i++) {
                            actionModelLists.get(i).setIsselect(true);
                        }
                        myActionAdapter = new MyActionAdapter(this, actionModelLists, true,cb_all);
                        list_action.setAdapter(myActionAdapter);
                        isselec = true;
                        cb_all.setChecked(true);
                    }
                }
                break;

        }

    }

    @Override
    public void getActionList(List<ActionModel> actionModelList) {
        try {
            if (actionModelList == null || (actionModelList.isEmpty())) {
                ll_action_nomessage.setVisibility(View.VISIBLE);
            } else {
                actionModelLists = actionModelList;
                myActionAdapter.updateData(actionModelList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        isdelpage=true;
        footer.setVisibility(View.VISIBLE);
        for (int i = 0; i < actionModelLists.size(); i++) {
            actionModelLists.get(i).setIsselect(false);
        }
        actionModelLists.get(position).setIsselect(true);
        myActionAdapter = new MyActionAdapter(this, actionModelLists, true,cb_all);
        list_action.setAdapter(myActionAdapter);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (actionModelLists.get(position).getIsJoinAct().equals("0")) {
            Util.toastMsg("您不在该活动中，不能查看活动详情！");
        } else if (StringUtils.isEmpty(actionModelLists.get(position).getActId())) {
            Util.toastMsg("抱歉, 该活动已取消！");
        } else {
            Intent intent = new Intent(this, ActActivity.class);
            intent.putExtra("id", actionModelLists.get(position).getActId());
            startActivity(intent);
        }
    }
}
