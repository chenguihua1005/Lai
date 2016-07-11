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
import com.softtek.lai.module.laisportmine.adapter.MyPublicWealfareAdapter;
import com.softtek.lai.module.laisportmine.model.PublicWewlfModel;
import com.softtek.lai.module.laisportmine.present.DelNoticeOrMeasureManager;
import com.softtek.lai.module.laisportmine.present.MyPublicWewlListManager;
import com.softtek.lai.module.laisportmine.present.UpdateMsgRTimeManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_my_publicwelfare)
public class MyPublicwelfareActivity extends BaseActivity implements View.OnClickListener ,MyPublicWewlListManager.MyPublicWewlListCallback,UpdateMsgRTimeManager.UpdateMsgRTimeCallback,
        AdapterView.OnItemLongClickListener,DelNoticeOrMeasureManager.DelNoticeOrMeasureCallback{
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.listview_publicwe)
    ListView listview_publicwe;
    @InjectView(R.id.ll_public_nomessage)
    LinearLayout ll_public_nomessage;
    private List<PublicWewlfModel> publicWewlfModelList=new ArrayList<PublicWewlfModel>();
    private MyPublicWealfareAdapter myPublicWealfareAdapter;
    MyPublicWewlListManager myPublicWewlListManager;
    UpdateMsgRTimeManager updateMsgRTimeManager;
    DelNoticeOrMeasureManager delNoticeOrMeasureManager;
    String accouid;
    int positions;
    private CharSequence[] items={"删除"};

    @Override
    protected void initViews() {
        tv_title.setText("慈善公益");
        ll_left.setOnClickListener(this);
        listview_publicwe.setOnItemLongClickListener(this);
    }

    @Override
    protected void initDatas() {
        UserInfoModel userInfoModel=UserInfoModel.getInstance();
        accouid=userInfoModel.getUser().getUserid();
        myPublicWealfareAdapter=new MyPublicWealfareAdapter(this,publicWewlfModelList);
        listview_publicwe.setAdapter(myPublicWealfareAdapter);
        myPublicWewlListManager=new MyPublicWewlListManager(this);
        myPublicWewlListManager.doGetDonateMsg(accouid);
        updateMsgRTimeManager=new UpdateMsgRTimeManager(this);
        updateMsgRTimeManager.doUpdateMsgRTime(accouid,"21");
        delNoticeOrMeasureManager=new DelNoticeOrMeasureManager(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case  R.id.ll_left:
                finish();
                break;
        }
    }

    @Override
    public void getMyPublicWewlList(List<PublicWewlfModel> publicWewlfModel) {
        try {
            if (publicWewlfModel==null||publicWewlfModel.isEmpty())
            {
                ll_public_nomessage.setVisibility(View.VISIBLE);
            }
            else {
                publicWewlfModelList = publicWewlfModel;
                myPublicWealfareAdapter.updateData(publicWewlfModelList);
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
                delNoticeOrMeasureManager.doDelNoticeOrMeasureMsg(publicWewlfModelList.get(position).getMessageId(),"1");
                publicWewlfModelList.remove(positions);
                myPublicWealfareAdapter.notifyDataSetChanged();

            }
        }).create().show();

        return false;
    }
}
