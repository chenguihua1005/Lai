package com.softtek.lai.module.laisportmine.view;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.laisportmine.adapter.MyActionAdapter;
import com.softtek.lai.module.laisportmine.adapter.MyPublicWealfareAdapter;
import com.softtek.lai.module.laisportmine.model.ActionModel;
import com.softtek.lai.module.laisportmine.model.PublicWewlfModel;
import com.softtek.lai.module.laisportmine.present.ActionListManager;
import com.softtek.lai.module.laisportmine.present.UpdateMsgRTimeManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_my_action_list)
public class MyActionListActivity extends BaseActivity implements View.OnClickListener,ActionListManager.ActionListCallback,UpdateMsgRTimeManager.UpdateMsgRTimeCallback{
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.list_action)
    ListView list_action;
    MyActionAdapter myActionAdapter;
    private List<ActionModel> actionModelLists=new ArrayList<ActionModel>();
    ActionListManager actionListManager;
    UpdateMsgRTimeManager updateMsgRTimeManager;
    String accountid;

    @Override
    protected void initViews() {
        tv_title.setText("活动邀请");
        ll_left.setOnClickListener(this);

    }

    @Override
    protected void initDatas() {
        UserInfoModel userInfoModel=UserInfoModel.getInstance();
        accountid=userInfoModel.getUser().getUserid();
        myActionAdapter=new MyActionAdapter(this,actionModelLists);
        list_action.setAdapter(myActionAdapter);
        actionListManager=new ActionListManager(this);
        actionListManager.GetActiveMsg(accountid);
        updateMsgRTimeManager=new UpdateMsgRTimeManager(this);
        updateMsgRTimeManager.doUpdateMsgRTime(accountid,"22");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ll_left:
            finish();
            break;
        }

    }

    @Override
    public void getActionList(List<ActionModel> actionModelList) {
        actionModelLists=actionModelList;
        myActionAdapter.updateData(actionModelList);
    }
}
