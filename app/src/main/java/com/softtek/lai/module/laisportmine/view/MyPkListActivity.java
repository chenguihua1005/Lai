package com.softtek.lai.module.laisportmine.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.laisportmine.adapter.MyActionAdapter;
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
    @InjectView(R.id.footer)
    LinearLayout footer;//全选删除布局
    @InjectView(R.id.ll_select)
    LinearLayout ll_select;//全选按钮
    @InjectView(R.id.cb_all)
    CheckBox cb_all;
    private PkNoticeManager pkNoticeManager;
    private UserInfoModel userInfoModel;
    String accountid;
    private MyPkNoticeAdapter myPkNoticeAdapter;
    private List<PkNoticeModel>pkNoticeModelList=new ArrayList<PkNoticeModel>();
    private CharSequence[] items={"删除"};
    boolean isselec = false;
    int account=0;
    UpdateMsgRTimeManager updateMsgRTimeManager;
    MyPkDelPKMsgManager myPkDelPKMsgManager;
    @Override
    protected void initViews() {
        tv_title.setText("莱运动PK挑战");
        ll_left.setOnClickListener(this);
        ll_select.setOnClickListener(this);
        cb_all.setOnClickListener(this);
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
        myPkNoticeAdapter=new MyPkNoticeAdapter(this,pkNoticeModelList,false,cb_all);
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
            case R.id.cb_all:
            case R.id.ll_select:
                for (int i = 0; i < pkNoticeModelList.size(); i++) {
                    if (pkNoticeModelList.get(i).getIsselect())
                    {
                        account++;
                    }
                }
                if (account==pkNoticeModelList.size())
                {
                    for (int i = 0; i < pkNoticeModelList.size(); i++) {
                        pkNoticeModelList.get(i).setIsselect(true);
                    }
                    myPkNoticeAdapter = new MyPkNoticeAdapter(this, pkNoticeModelList, true,cb_all);
                    listview_pk.setAdapter(myPkNoticeAdapter);
                    isselec = true;
                    cb_all.setChecked(true);
                }
                else {
                    if (isselec) {
                        for (int i = 0; i < pkNoticeModelList.size(); i++) {
                            pkNoticeModelList.get(i).setIsselect(false);
                        }
                        myPkNoticeAdapter = new MyPkNoticeAdapter(this, pkNoticeModelList, true,cb_all);
                        listview_pk.setAdapter(myPkNoticeAdapter);
                        isselec = false;
                    } else {
                        for (int i = 0; i < pkNoticeModelList.size(); i++) {
                            pkNoticeModelList.get(i).setIsselect(true);
                        }
                        myPkNoticeAdapter = new MyPkNoticeAdapter(this, pkNoticeModelList, true,cb_all);
                        listview_pk.setAdapter(myPkNoticeAdapter);
                        isselec = true;
                    }
                }
                break;
        }
    }

    @Override
    public void getPkNotice(List<PkNoticeModel> pkNoticeModels) {
        try {
            if (pkNoticeModels==null||pkNoticeModels.isEmpty())
            {
                ll_nomessage.setVisibility(View.VISIBLE);
            }
            else {
                ll_nomessage.setVisibility(View.GONE);
                pkNoticeModelList = pkNoticeModels;
                myPkNoticeAdapter.updateData(pkNoticeModelList);

            }
        }catch (Exception e){e.printStackTrace();}


    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        footer.setVisibility(View.VISIBLE);
        for (int i = 0; i < pkNoticeModelList.size(); i++) {
            pkNoticeModelList.get(i).setIsselect(false);

        }
        myPkNoticeAdapter=new MyPkNoticeAdapter(this,pkNoticeModelList,true,cb_all);
        listview_pk.setAdapter(myPkNoticeAdapter);
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        positions=position;
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                myPkDelPKMsgManager.doDelPKMsg(pkNoticeModelList.get(position).getPKMsgId());
//                pkNoticeModelList.remove(positions);
//                myPkNoticeAdapter.notifyDataSetChanged();
//
//
//            }
//        }).create().show();
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(this, PKDetailActivity.class);
        intent.putExtra("pkId",Long.parseLong(pkNoticeModelList.get(position).getPKId()));
        intent.putExtra("pkType", Constants.MESSAGE_PK);
        startActivity(intent);
    }
}
