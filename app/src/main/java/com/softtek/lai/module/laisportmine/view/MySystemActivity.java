package com.softtek.lai.module.laisportmine.view;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.laisportmine.adapter.MySystemAdapter;
import com.softtek.lai.module.laisportmine.model.SystemNewsModel;
import com.softtek.lai.module.laisportmine.present.DelNoticeOrMeasureManager;
import com.softtek.lai.module.laisportmine.present.SystemListManager;
import com.softtek.lai.module.laisportmine.present.UpdateMsgRTimeManager;
import com.softtek.lai.module.login.model.UserModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_my_systemaction)
public class MySystemActivity extends BaseActivity implements View.OnClickListener ,SystemListManager.SystemListCallback,UpdateMsgRTimeManager.UpdateMsgRTimeCallback,AdapterView.OnItemLongClickListener,
        DelNoticeOrMeasureManager.DelNoticeOrMeasureCallback{
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    //显示系统消息listview
    @InjectView(R.id.listview_system)
    ListView listview_system;
    @InjectView(R.id.ll_public_nomessage)
    LinearLayout ll_public_nomessage;
    //消息列表数据
    List<SystemNewsModel> systemNewsModelLists=new ArrayList<SystemNewsModel>();
    //消息列表listview适配器
    MySystemAdapter mySystemAdapter;
    SystemListManager systemListManager;
    UpdateMsgRTimeManager updateMsgRTimeManager;
    DelNoticeOrMeasureManager delNoticeOrMeasureManager;
    String accountid;
    int positions;
    private CharSequence[] items={"删除"};

    @Override
    protected void initViews() {
        tv_title.setText("系统消息");
        ll_left.setOnClickListener(this);
        listview_system.setOnItemLongClickListener(this);
        delNoticeOrMeasureManager=new DelNoticeOrMeasureManager(this);
    }

    @Override
    protected void initDatas() {
        //暂不提交内容 开始
        //取用户id
        UserModel userModel=UserInfoModel.getInstance().getUser();
        accountid=userModel.getUserid();
        //listview适配
        mySystemAdapter=new MySystemAdapter(this,systemNewsModelLists);
        listview_system.setAdapter(mySystemAdapter);
        //请求接口获取系统消息列表内容
        systemListManager=new SystemListManager(this);
        systemListManager.doGetSysMsg(accountid);
        updateMsgRTimeManager=new UpdateMsgRTimeManager(this);
        updateMsgRTimeManager.doUpdateMsgRTime(accountid,"23");
        //暂不提交内容 结束
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
        }
    }

    @Override
    public void getSystemList(List<SystemNewsModel> systemNewsModelList) {
        //接口回调，适配listview数据
        try {
            if (systemNewsModelList==null||systemNewsModelList.isEmpty())
            {
                ll_public_nomessage.setVisibility(View.VISIBLE);
            }
            else {
                systemNewsModelLists=systemNewsModelList;
                mySystemAdapter.updateData(systemNewsModelLists);
                ll_public_nomessage.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        positions=position;
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                delNoticeOrMeasureManager.doDelNoticeOrMeasureMsg(systemNewsModelLists.get(position).getMessageId(),"23");
                systemNewsModelLists.remove(positions);
                mySystemAdapter.notifyDataSetChanged();

            }
        }).create().show();
        return true;
    }
}
