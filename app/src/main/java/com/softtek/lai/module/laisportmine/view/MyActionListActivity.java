package com.softtek.lai.module.laisportmine.view;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.act.view.ActActivity;
import com.softtek.lai.module.laisportmine.adapter.MyActionAdapter;
import com.softtek.lai.module.laisportmine.model.ActionModel;
import com.softtek.lai.module.laisportmine.present.ActionListManager;
import com.softtek.lai.module.laisportmine.present.DelNoticeOrMeasureManager;
import com.softtek.lai.module.laisportmine.present.UpdateMsgRTimeManager;
import com.softtek.lai.module.message2.presenter.DeleteMessageManager;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_my_action_list)
public class MyActionListActivity extends BaseActivity implements View.OnClickListener, ActionListManager.ActionListCallback, UpdateMsgRTimeManager.UpdateMsgRTimeCallback,
         DelNoticeOrMeasureManager.DelNoticeOrMeasureCallback, AdapterView.OnItemClickListener,DeleteMessageManager.DeleteMsgCallBack {
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
    @InjectView(R.id.tv_delete)
            TextView tv_delete;
    MyActionAdapter myActionAdapter;
    DeleteMessageManager delManager;
    private List<ActionModel> actionModelLists = new ArrayList<>();
    ActionListManager actionListManager;
    UpdateMsgRTimeManager updateMsgRTimeManager;
    DelNoticeOrMeasureManager delNoticeOrMeasureManager;
    String accountid;
    boolean isdelpage=false;

    @Override
    protected void initViews() {
        tv_title.setText("活动邀请");
        tv_right.setText("编辑");
        tv_delete.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        ll_select.setOnClickListener(this);
        cb_all.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        list_action.setOnItemClickListener(this);
    }

    @Override
    protected void initDatas() {
        UserInfoModel userInfoModel = UserInfoModel.getInstance();
        accountid = userInfoModel.getUser().getUserid();
        myActionAdapter = new MyActionAdapter(this, actionModelLists, false,cb_all);
        list_action.setAdapter(myActionAdapter);
        actionListManager = new ActionListManager(this);
        dialogShow("加载中");
        actionListManager.GetActiveMsg(accountid);
        updateMsgRTimeManager = new UpdateMsgRTimeManager(this);
        updateMsgRTimeManager.doUpdateMsgRTime(accountid, "22");
        delNoticeOrMeasureManager = new DelNoticeOrMeasureManager(this);
        delManager=new DeleteMessageManager(this);


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            dialogShow("加载中");
            updateMsgRTimeManager.doUpdateMsgRTime(accountid, "22");
            actionListManager.GetActiveMsg(accountid);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                    finish();
                break;
            case R.id.tv_delete:
                String msgId = getMsgId();
                if ("".equals(msgId)) {
                    Util.toastMsg("请先选择要删除的数据");
                    return;
                }
                dialogShow("正在删除");
                delManager.DodeleteOneMsg("1", msgId);

                break;
            case R.id.cb_all:
            case R.id.ll_select:
                /*点击全选，若为true选中状态，则设为false，重置为未选中状态*/

                if (myActionAdapter.isselec)
                {
                    for (int i = 0; i < actionModelLists.size(); i++) {
                        actionModelLists.get(i).setIsselect(false);
                    }
                    myActionAdapter = new MyActionAdapter(this, actionModelLists, true,cb_all);
                    list_action.setAdapter(myActionAdapter);
                    myActionAdapter.isselec=false;
                    cb_all.setChecked(false);
                }
                else {
                    for (int i = 0; i < actionModelLists.size(); i++) {
                        actionModelLists.get(i).setIsselect(true);
                    }
                    myActionAdapter = new MyActionAdapter(this, actionModelLists, true,cb_all);
                    list_action.setAdapter(myActionAdapter);
                    myActionAdapter.isselec=true;
                    cb_all.setChecked(true);
                }
                break;
            case R.id.tv_right:
                if (isdelpage)
                {
                    tv_right.setText("编辑");
                    cb_all.setChecked(false);
                    footer.setVisibility(View.GONE);
                    myActionAdapter = new MyActionAdapter(this, actionModelLists, false, cb_all);
                    list_action.setAdapter(myActionAdapter);
                    isdelpage=false;
                }
                else
                {
                    tv_right.setText("完成");
                    footer.setVisibility(View.VISIBLE);
                    for (int i = 0; i < actionModelLists.size(); i++) {
                        actionModelLists.get(i).setIsselect(false);

                    }
                    myActionAdapter = new MyActionAdapter(this, actionModelLists, true, cb_all);
                    list_action.setAdapter(myActionAdapter);
                    isdelpage=true;
                }
                break;

        }

    }

    @Override
    public void getActionList(List<ActionModel> actionModelList) {
        dialogDissmiss();
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
    private String getMsgId() {
        String msgId = "";
        //遍历取出为选中状态的消息id
        for (int i = 0; i < actionModelLists.size(); i++) {
            ActionModel actionModel = actionModelLists.get(i);
            if (actionModel.isselect()) {
                if ("".equals(msgId)) {
                    msgId = actionModel.getMessageId();
                } else {
                    msgId = msgId + "," + actionModel.getMessageId();
                }
            }
        }
        return msgId;
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
            startActivityForResult(intent, 0);
        }
    }

    @Override
    public void deleteMsg(String type) {
        dialogDissmiss();
        if ("true".equals(type)) {
            List<ActionModel> nList = new ArrayList<ActionModel>();
            nList.addAll(actionModelLists);
            for (int i = 0; i < nList.size(); i++) {
                ActionModel actionModel = nList.get(i);
                if (actionModel.isselect()) {
                    actionModelLists.remove(actionModel);
                }
            }
            myActionAdapter = new MyActionAdapter(this, actionModelLists, true, cb_all);
            list_action.setAdapter(myActionAdapter);
            myActionAdapter.notifyDataSetChanged();
        }
    }
}
