package com.softtek.lai.module.laisportmine.view;

import android.content.Intent;
import android.view.KeyEvent;
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
import com.softtek.lai.module.laisportmine.adapter.MyPkNoticeAdapter;
import com.softtek.lai.module.laisportmine.model.PkNoticeModel;
import com.softtek.lai.module.laisportmine.present.MyPkDelPKMsgManager;
import com.softtek.lai.module.laisportmine.present.PkNoticeManager;
import com.softtek.lai.module.laisportmine.present.UpdateMsgRTimeManager;
import com.softtek.lai.module.message2.presenter.DeleteMessageManager;
import com.softtek.lai.module.personalPK.view.PKDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_my_pk_list)
public class MyPkListActivity extends BaseActivity implements View.OnClickListener,PkNoticeManager.PkNoticeCallback,
        MyPkDelPKMsgManager.MyPkDelPKMsgCallback,UpdateMsgRTimeManager.UpdateMsgRTimeCallback,
        AdapterView.OnItemClickListener,DeleteMessageManager.DeleteMsgCallBack{
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
    @InjectView(R.id.tv_delete)
    TextView tv_delete;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    private PkNoticeManager pkNoticeManager;
    private UserInfoModel userInfoModel;
    String accountid;
    private MyPkNoticeAdapter myPkNoticeAdapter;
    private List<PkNoticeModel>pkNoticeModelList=new ArrayList<>();
    boolean isdelpage=false;
    DeleteMessageManager delManager;
    UpdateMsgRTimeManager updateMsgRTimeManager;
    MyPkDelPKMsgManager myPkDelPKMsgManager;
    @Override
    protected void initViews() {
        tv_title.setText("运动挑战");
        tv_right.setText("编辑");
        tv_right.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        ll_select.setOnClickListener(this);
        cb_all.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        listview_pk.setOnItemClickListener(this);
    }

    @Override
    protected void initDatas() {
        userInfoModel=UserInfoModel.getInstance();
        if (!userInfoModel.getUser().getUserid().isEmpty()) {
            accountid = userInfoModel.getUser().getUserid();
            pkNoticeManager=new PkNoticeManager(this);
            dialogShow("加载中");
            pkNoticeManager.doGetPKINotice(accountid);
            updateMsgRTimeManager=new UpdateMsgRTimeManager(this);
            updateMsgRTimeManager.doUpdateMsgRTime(accountid,"0");
        }
        myPkNoticeAdapter=new MyPkNoticeAdapter(this,pkNoticeModelList,false,cb_all);
        listview_pk.setAdapter(myPkNoticeAdapter);
        myPkDelPKMsgManager=new MyPkDelPKMsgManager(this);
        delManager = new DeleteMessageManager(this);


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            updateMsgRTimeManager.doUpdateMsgRTime(accountid,"0");
            dialogShow("加载中");
            pkNoticeManager.doGetPKINotice(accountid);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ll_left:
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.tv_delete:
                String msgId = getMsgId();
                if ("".equals(msgId)) {
                    Util.toastMsg("请先选择要删除的数据");
                    return;
                }
                dialogShow("删除中");
                    delManager.DodeleteOneMsg(6, msgId);
                break;
            case R.id.cb_all:
            case R.id.ll_select:
                if (myPkNoticeAdapter.isselec)
                {
                    for (int i = 0; i < pkNoticeModelList.size(); i++) {
                            pkNoticeModelList.get(i).setIsselect(false);
                        }
                        myPkNoticeAdapter = new MyPkNoticeAdapter(this, pkNoticeModelList, true,cb_all);
                        listview_pk.setAdapter(myPkNoticeAdapter);
                        myPkNoticeAdapter.isselec=false;
                        cb_all.setChecked(false);
                }
                else {
                    for (int i = 0; i < pkNoticeModelList.size(); i++) {
                            pkNoticeModelList.get(i).setIsselect(true);
                        }
                        myPkNoticeAdapter = new MyPkNoticeAdapter(this, pkNoticeModelList, true,cb_all);
                        listview_pk.setAdapter(myPkNoticeAdapter);
                    myPkNoticeAdapter.isselec=true;
                        cb_all.setChecked(true);
                }
                break;
            case R.id.tv_right:
                if (isdelpage)
                {
                    tv_right.setText("编辑");
                    cb_all.setChecked(false);
                    footer.setVisibility(View.GONE);
                    myPkNoticeAdapter = new MyPkNoticeAdapter(this, pkNoticeModelList, false, cb_all);
                    listview_pk.setAdapter(myPkNoticeAdapter);
                    isdelpage=false;
                }
                else
            {
                tv_right.setText("完成");
                footer.setVisibility(View.VISIBLE);
                for (int i = 0; i < pkNoticeModelList.size(); i++) {
                    pkNoticeModelList.get(i).setIsselect(false);

                }
                myPkNoticeAdapter = new MyPkNoticeAdapter(this, pkNoticeModelList, true, cb_all);
                listview_pk.setAdapter(myPkNoticeAdapter);
                isdelpage=true;
            }
                break;
        }
    }
    private String getMsgId() {
        String msgId = "";
        //遍历取出为选中状态的消息id
        for (int i = 0; i < pkNoticeModelList.size(); i++) {
            PkNoticeModel pkNoticeModel = pkNoticeModelList.get(i);
            if (pkNoticeModel.getIsselect()) {
                if ("".equals(msgId)) {
                    msgId = pkNoticeModel.getMsgid();
                } else {
                    msgId = msgId + "," + pkNoticeModel.getMsgid();
                }
            }
        }
        return msgId;
    }
    @Override
    public void getPkNotice(List<PkNoticeModel> pkNoticeModels) {
        dialogDissmiss();
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(this, PKDetailActivity.class);
        intent.putExtra("pkId",Long.parseLong(pkNoticeModelList.get(position).getPKId()));
        intent.putExtra("pkType", Constants.MESSAGE_PK);
        startActivityForResult(intent, 0);
    }

    @Override
    public void deleteMsg(String type) {
        dialogDissmiss();
        if ("true".equals(type)) {
            List<PkNoticeModel> nList = new ArrayList<PkNoticeModel>();
            nList.addAll(pkNoticeModelList);
            for (int i = 0; i < nList.size(); i++) {
                PkNoticeModel pkNoticeModel = nList.get(i);
                if (pkNoticeModel.getIsselect()) {
                    pkNoticeModelList.remove(pkNoticeModel);
                }
            }
            myPkNoticeAdapter.account = 0;
            myPkNoticeAdapter = new MyPkNoticeAdapter(this, pkNoticeModelList, true,cb_all);
            listview_pk.setAdapter(myPkNoticeAdapter);
            myPkNoticeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            setResult(RESULT_OK);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
