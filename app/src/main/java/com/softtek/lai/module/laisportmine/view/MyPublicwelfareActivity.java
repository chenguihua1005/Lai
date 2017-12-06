package com.softtek.lai.module.laisportmine.view;

import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.laisportmine.adapter.MyPublicWealfareAdapter;
import com.softtek.lai.module.laisportmine.model.SelectPublicWewlfModel;
import com.softtek.lai.module.laisportmine.present.DelNoticeOrMeasureManager;
import com.softtek.lai.module.laisportmine.present.MyPublicWewlListManager;
import com.softtek.lai.module.laisportmine.present.UpdateMsgRTimeManager;
import com.softtek.lai.module.message2.model.NoticeModel;
import com.softtek.lai.module.message2.presenter.DeleteMessageManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_my_publicwelfare)
public class MyPublicwelfareActivity extends BaseActivity implements View.OnClickListener, MyPublicWewlListManager.MyPublicWewlListCallback, UpdateMsgRTimeManager.UpdateMsgRTimeCallback, DeleteMessageManager.DeleteMsgCallBack,
        AdapterView.OnItemLongClickListener, DelNoticeOrMeasureManager.DelNoticeOrMeasureCallback {
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.footer)
    LinearLayout footer;
    @InjectView(R.id.cb_all)
    CheckBox cb_all;
    @InjectView(R.id.tv_delete)
    TextView tv_delete;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    @InjectView(R.id.lin_select)
    LinearLayout lin_select;
    @InjectView(R.id.listview_publicwe)
    ListView listview_publicwe;
    @InjectView(R.id.ll_public_nomessage)
    LinearLayout ll_public_nomessage;
    private List<SelectPublicWewlfModel> publicWewlfModelList = new ArrayList<>();
    private MyPublicWealfareAdapter myPublicWealfareAdapter;
    MyPublicWewlListManager myPublicWewlListManager;
    UpdateMsgRTimeManager updateMsgRTimeManager;
    DeleteMessageManager delManager;
    DelNoticeOrMeasureManager delNoticeOrMeasureManager;
    String accouid;

    public static boolean isSelsetAll = false;

    @Override
    protected void initViews() {
        tv_title.setText("爱心慈善");
        ll_left.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        lin_select.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        tv_right.setText("编辑");
        listview_publicwe.setOnItemLongClickListener(this);
    }

    @Override
    protected void initDatas() {
        UserInfoModel userInfoModel = UserInfoModel.getInstance();
        accouid = userInfoModel.getUser().getUserid();
        delManager = new DeleteMessageManager(this);
        myPublicWewlListManager = new MyPublicWewlListManager(this);
        myPublicWewlListManager.doGetDonateMsg(accouid);
        updateMsgRTimeManager = new UpdateMsgRTimeManager(this);
        updateMsgRTimeManager.doUpdateMsgRTime(accouid, "21");
        delNoticeOrMeasureManager = new DelNoticeOrMeasureManager(this);


    }

    private String getMsgId() {
        String msgId = "";
        for (int i = 0; i < publicWewlfModelList.size(); i++) {
            SelectPublicWewlfModel selectPublicWewlfModel = publicWewlfModelList.get(i);
            if (selectPublicWewlfModel.isSelect()) {
                if ("".equals(msgId)) {
                    msgId = selectPublicWewlfModel.getPublicWewlfModel().getMsgid();
                } else {
                    msgId = msgId + "," + selectPublicWewlfModel.getPublicWewlfModel().getMsgid();
                }
            }
        }
        return msgId;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_right:
                if ("编辑".equals(tv_right.getText())) {
                    tv_right.setText("完成");
                    if (myPublicWealfareAdapter != null) {
                        cb_all.setChecked(false);
                        isSelsetAll = false;
                        footer.setVisibility(View.VISIBLE);
                        myPublicWealfareAdapter.isDel = true;
                        myPublicWealfareAdapter.notifyDataSetChanged();
                    }
                } else {
                    tv_right.setText("编辑");
                    for (int i = 0; i < publicWewlfModelList.size(); i++) {
                        publicWewlfModelList.get(i).setSelect(false);
                    }
                    isSelsetAll = false;
                    myPublicWealfareAdapter.select_count = 0;
                    footer.setVisibility(View.GONE);
                    myPublicWealfareAdapter.isDel = false;
                    myPublicWealfareAdapter.notifyDataSetChanged();
                }

                break;

            case R.id.tv_delete:
                String msgId = getMsgId();
                if ("".equals(msgId)) {
                    Util.toastMsg("请先选择要删除的数据");
                    return;
                }
                delManager.DodeleteOneMsg(1, msgId);
                break;
            case R.id.lin_select:
                if (myPublicWealfareAdapter == null) {
                    return;
                }
                if (isSelsetAll) {
                    isSelsetAll = false;
                    myPublicWealfareAdapter.select_count = 0;
                    cb_all.setChecked(false);
                    for (int i = 0; i < publicWewlfModelList.size(); i++) {
                        publicWewlfModelList.get(i).setSelect(false);
                    }
                    myPublicWealfareAdapter.notifyDataSetChanged();
                } else {
                    isSelsetAll = true;
                    myPublicWealfareAdapter.select_count = publicWewlfModelList.size();
                    cb_all.setChecked(true);
                    for (int i = 0; i < publicWewlfModelList.size(); i++) {
                        publicWewlfModelList.get(i).setSelect(true);
                    }
                    myPublicWealfareAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.ll_left:
                setResult(RESULT_OK);
                finish();
                break;
        }
    }

    @Override
    public void getMyPublicWewlList(List<NoticeModel> publicWewlfModel) {
        try {
            if (publicWewlfModel == null || publicWewlfModel.isEmpty()) {
                ll_public_nomessage.setVisibility(View.VISIBLE);
            } else {
                for (int i = 0; i < publicWewlfModel.size(); i++) {
                    SelectPublicWewlfModel m = new SelectPublicWewlfModel(publicWewlfModel.get(i), false);
                    publicWewlfModelList.add(m);
                }
                myPublicWealfareAdapter = new MyPublicWealfareAdapter(this, publicWewlfModelList, cb_all);
                listview_publicwe.setAdapter(myPublicWealfareAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        if (myPublicWealfareAdapter != null) {

            cb_all.setChecked(false);
            isSelsetAll = false;
            publicWewlfModelList.get(position).setSelect(true);
            footer.setVisibility(View.VISIBLE);
            myPublicWealfareAdapter.isDel = true;
            myPublicWealfareAdapter.select_count++;
            myPublicWealfareAdapter.notifyDataSetChanged();
        }

        return false;
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
    @Override
    public void deleteMsg(String type) {
        if ("true".equals(type)) {
            List<SelectPublicWewlfModel> nList = new ArrayList<>();
            nList.addAll(publicWewlfModelList);
            for (int i = 0; i < nList.size(); i++) {
                SelectPublicWewlfModel selectPublicWewlfModel = nList.get(i);
                if (selectPublicWewlfModel.isSelect()) {
                    publicWewlfModelList.remove(selectPublicWewlfModel);
                }
            }
            myPublicWealfareAdapter.select_count = 0;
            myPublicWealfareAdapter.notifyDataSetChanged();
            if (publicWewlfModelList.size() == 0) {
                cb_all.setChecked(false);
            }
        }
    }
}
