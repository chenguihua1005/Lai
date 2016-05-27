package com.softtek.lai.module.laisportmine.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.laisportmine.adapter.MyPkNoticeAdapter;
import com.softtek.lai.module.laisportmine.model.PkNoticeModel;
import com.softtek.lai.module.laisportmine.present.DelNoticeOrMeasureManager;
import com.softtek.lai.module.laisportmine.present.MyPkDelPKMsgManager;
import com.softtek.lai.module.laisportmine.present.PkNoticeManager;
import com.softtek.lai.module.laisportmine.present.UpdateMsgRTimeManager;
import com.softtek.lai.module.personalPK.view.PKDetailActivity;
import com.softtek.lai.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_my_pk_list)
public class MyPkListActivity extends BaseActivity implements View.OnClickListener,PkNoticeManager.PkNoticeCallback,
        AdapterView.OnItemLongClickListener,MyPkDelPKMsgManager.MyPkDelPKMsgCallback,UpdateMsgRTimeManager.UpdateMsgRTimeCallback,
        AdapterView.OnItemClickListener{
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.listview_pk)
    ListView listview_pk;
    @InjectView(R.id.ll_nomessage)
    LinearLayout ll_nomessage;
    private PkNoticeManager pkNoticeManager;
    private UserInfoModel userInfoModel;
    String accountid;
    private MyPkNoticeAdapter myPkNoticeAdapter;
    private List<PkNoticeModel>pkNoticeModelList=new ArrayList<PkNoticeModel>();
    private CharSequence[] items={"删除"};
    int positions;
    UpdateMsgRTimeManager updateMsgRTimeManager;
    MyPkDelPKMsgManager myPkDelPKMsgManager;
    @Override
    protected void initViews() {
        tv_title.setText("俱乐部助手");
        ll_left.setOnClickListener(this);
        listview_pk.setOnItemLongClickListener(this);
        listview_pk.setOnItemClickListener(this);
    }

    @Override
    protected void initDatas() {
        userInfoModel=UserInfoModel.getInstance();
        if (!userInfoModel.getUser().getUserid().isEmpty()) {
            accountid = userInfoModel.getUser().getUserid();
            pkNoticeManager=new PkNoticeManager(this);
            pkNoticeManager.doGetPKINotice(accountid);
            updateMsgRTimeManager=new UpdateMsgRTimeManager(this);
            updateMsgRTimeManager.doUpdateMsgRTime(accountid,"0");
        }
        myPkNoticeAdapter=new MyPkNoticeAdapter(this,pkNoticeModelList);
        listview_pk.setAdapter(myPkNoticeAdapter);
        myPkDelPKMsgManager=new MyPkDelPKMsgManager(this);


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
    public void getPkNotice(List<PkNoticeModel> pkNoticeModels) {
        if (pkNoticeModels==null||pkNoticeModels.isEmpty())
        {
            ll_nomessage.setVisibility(View.VISIBLE);
        }
        else {
            ll_nomessage.setVisibility(View.GONE);
            pkNoticeModelList = pkNoticeModels;
            myPkNoticeAdapter.updateData(pkNoticeModelList);

        }

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        positions=position;
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myPkDelPKMsgManager.doDelPKMsg(pkNoticeModelList.get(position).getPKMsgId());
                pkNoticeModelList.remove(positions);
                myPkNoticeAdapter.notifyDataSetChanged();


            }
        }).create().show();
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(this, PKDetailActivity.class);
        intent.putExtra("pkId",Long.parseLong(pkNoticeModelList.get(position).getPKId()));
        intent.putExtra("pkType", Constants.MESSAGE_PK);
        startActivity(intent);
    }
}
