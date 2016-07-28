package com.softtek.lai.module.laisportmine.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.act.adapter.ActZKAdapter;
import com.softtek.lai.module.act.view.ActActivity;
import com.softtek.lai.module.laisportmine.adapter.MyActionAdapter;
import com.softtek.lai.module.laisportmine.model.ActionModel;
import com.softtek.lai.module.laisportmine.present.ActionListManager;
import com.softtek.lai.module.laisportmine.present.DelNoticeOrMeasureManager;
import com.softtek.lai.module.laisportmine.present.UpdateMsgRTimeManager;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_my_action_list)
public class MyActionDelListActivity extends BaseActivity implements View.OnClickListener,ActionListManager.ActionListCallback,UpdateMsgRTimeManager.UpdateMsgRTimeCallback,
        AdapterView.OnItemLongClickListener,DelNoticeOrMeasureManager.DelNoticeOrMeasureCallback,AdapterView.OnItemClickListener{
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
    MyActionAdapter myActionAdapter;
    @InjectView(R.id.ll_ite)
    LinearLayout ll_ite;
    @InjectView(R.id.tv_dele)
    TextView tv_dele;
    private List<ActionModel> actionModelLists=new ArrayList<ActionModel>();
    ActionListManager actionListManager;
    UpdateMsgRTimeManager updateMsgRTimeManager;
    DelNoticeOrMeasureManager delNoticeOrMeasureManager;
    String accountid;
    int positions;
    ImageView iv_checked;
    private CharSequence[] items={"删除"};

    @Override
    protected void initViews() {
        tv_title.setText("活动邀请");
        tv_right.setText("全选");
        tv_right.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        list_action.setOnItemLongClickListener(this);
        tv_dele.setOnClickListener(this);
//        list_action.setOnItemClickListener(this);
    }

    @Override
    protected void initDatas() {
        ll_ite.setVisibility(View.VISIBLE);
        UserInfoModel userInfoModel=UserInfoModel.getInstance();
        accountid=userInfoModel.getUser().getUserid();
        Intent intent=getIntent();
        actionModelLists= (List<ActionModel>) intent.getSerializableExtra("model");
        myActionAdapter=new MyActionAdapter(this,actionModelLists,true);
        list_action.setAdapter(myActionAdapter);
//        actionListManager=new ActionListManager(this);
//        actionListManager.GetActiveMsg(accountid);
//        updateMsgRTimeManager=new UpdateMsgRTimeManager(this);
//        updateMsgRTimeManager.doUpdateMsgRTime(accountid,"22");
//        delNoticeOrMeasureManager=new DelNoticeOrMeasureManager(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ll_left:
            finish();
            break;
            case R.id.tv_right:
//                for (int i=0;i<actionModelLists.size();i++)
//                {
//                    actionModelLists.get(i).setIsselect("true");
//                    Log.i("测试测试i",actionModelLists.get(i).getIsselect());
//                }
//                myActionAdapter=new MyActionAdapter(this,actionModelLists,true);
//                list_action.setAdapter(myActionAdapter);
                break;
            case R.id.tv_dele:
                for (int i=0;i<actionModelLists.size();i++)
                {
                    Log.i("测试测试i",actionModelLists.get(i).getIsselect());
                }
                break;

        }

    }

    @Override
    public void getActionList(List<ActionModel> actionModelList) {
        try {
            if (actionModelList==null||(actionModelList.isEmpty()))
            {
                ll_action_nomessage.setVisibility(View.VISIBLE);
            }
            else {
                actionModelLists = actionModelList;
                myActionAdapter.updateData(actionModelList);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        positions=position;
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                delNoticeOrMeasureManager.doDelNoticeOrMeasureMsg(actionModelLists.get(position).getMessageId(),"1");
//                actionModelLists.remove(positions);
//                myActionAdapter.notifyDataSetChanged();
//            }
//        }).create().show();
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        iv_checked= (ImageView) findViewById(R.id.iv_checked);

//        MyActionAdapter.ViewHolder viewHolder=ne;
//        viewHolder
    }
}
