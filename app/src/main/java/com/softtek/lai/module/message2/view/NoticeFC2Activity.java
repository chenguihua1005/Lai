/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.message2.view;


import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.message2.adapter.MessageNoticeAdapter;
import com.softtek.lai.module.message2.model.NoticeMsgModel;
import com.softtek.lai.module.message2.model.OperateMsgModel;
import com.softtek.lai.module.message2.model.SelectNoticeMsgModel;
import com.softtek.lai.module.message2.presenter.DeleteMessageManager;
import com.softtek.lai.module.message2.presenter.MessageMainManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
@InjectLayout(R.layout.activity_notice_list)
public class NoticeFC2Activity extends BaseActivity implements View.OnClickListener, MessageMainManager.GetNoticeFCMsgCallBack, DeleteMessageManager.DeleteMsgCallBack {

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;

    @InjectView(R.id.list)
    ListView list;

    @InjectView(R.id.footer)
    LinearLayout footer;

    @InjectView(R.id.tv_delete)
    TextView tv_delete;
    @InjectView(R.id.lin_select)
    LinearLayout lin_select;
    @InjectView(R.id.cb_all)
    CheckBox cb_all;

    @InjectView(R.id.img_no_message)
    ImageView img_no_message;

    MessageMainManager manager;
    DeleteMessageManager delManager;
    private UserModel model;
    String type;
    private MessageNoticeAdapter adapter;

    public static boolean isSelsetAll = false;

    List<SelectNoticeMsgModel> noticeList;
    public static List<OperateMsgModel> operatList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = new MessageMainManager(this);

    }

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        lin_select.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        tv_right.setText("编辑");
        list.setEmptyView(img_no_message);
//        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                if (adapter != null) {
//                    tv_right.setText("完成");
//                    cb_all.setChecked(false);
//                    isSelsetAll = false;
//                    noticeList.get(position).setSelect(true);
//                    footer.setVisibility(View.VISIBLE);
//                    adapter.isDel = true;
//                    adapter.select_count++;
//                    adapter.notifyDataSetChanged();
//                }
//                return false;
//            }
//        });
    }

    @Override
    protected void initDatas() {
        model = UserInfoModel.getInstance().getUser();
        if (model == null) {
            return;
        }
        type = getIntent().getStringExtra("type");
        manager = new MessageMainManager(this);
        delManager = new DeleteMessageManager(this);
        dialogShow("加载中");
        if ("fc".equals(type)) {
            tv_title.setText("复测提醒");
            manager.doGetMeasureMsg(model.getUserid());
        } else if ("notice".equals(type)) {
            tv_title.setText("服务窗");
            manager.doGetNoticeMsg(model.getUserid());
        } else if ("xzs".equals(type)) {
            tv_title.setText("小助手");
            manager.doGetOperateMsg(model.getUserid());
        }
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
//            if (adapter == null) {
//                finish();
//            } else {
//                if (adapter.isDel) {
//                    for (int i = 0; i < noticeList.size(); i++) {
//                        noticeList.get(i).setSelect(false);
//                    }
//                    adapter.select_count = 0;
//                    isSelsetAll = false;
//                    footer.setVisibility(View.GONE);
//                    adapter.isDel = false;
//                    adapter.notifyDataSetChanged();
//                } else {
//                    finish();
//                }
//            }
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    private String getMsgId() {
        String msgId = "";
        for (int i = 0; i < noticeList.size(); i++) {
            SelectNoticeMsgModel selectNoticeMsgModel = noticeList.get(i);
            if (selectNoticeMsgModel.isSelect()) {
                if ("".equals(msgId)) {
                    msgId = selectNoticeMsgModel.getNoticeMsgModel().getMessageId();
                } else {
                    msgId = msgId + "," + selectNoticeMsgModel.getNoticeMsgModel().getMessageId();
                }
            }
        }
        return msgId;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_right:
                if("编辑".equals(tv_right.getText())){
                    tv_right.setText("完成");
                    if (adapter != null) {
                        cb_all.setChecked(false);
                        isSelsetAll = false;
                        footer.setVisibility(View.VISIBLE);
                        adapter.isDel = true;
                        adapter.notifyDataSetChanged();
                    }
                }else {
                    tv_right.setText("编辑");
                    for (int i = 0; i < noticeList.size(); i++) {
                        noticeList.get(i).setSelect(false);
                    }
                    isSelsetAll = false;
                    adapter.select_count = 0;
                    footer.setVisibility(View.GONE);
                    adapter.isDel = false;
                    adapter.notifyDataSetChanged();
                }

                break;
            case R.id.tv_delete:
                String msgId = getMsgId();
                System.out.println("msgId:" + msgId);
                if ("".equals(msgId)) {
                    Util.toastMsg("请先选择要删除的数据");
                    return;
                }
                if ("notice".equals(type)) {
                    delManager.DodeleteOneMsg("1", msgId);
                } else if ("fc".equals(type)) {
                    delManager.DodeleteOneMsg("1", msgId);
                } else if ("xzs".equals(type)) {
                    String userrole = UserInfoModel.getInstance().getUser().getUserrole();
                    if (String.valueOf(Constants.SR).equals(userrole)) {
                        //助教身份跳转复测页面
                        delManager.DodeleteOneMsg("2", msgId);

                    } else if (String.valueOf(Constants.SP).equals(userrole)) {
                        //顾问身份跳转复测页面
                        delManager.DodeleteOneMsg("3", msgId);
                    } else {
                        delManager.DodeleteOneMsg("4", msgId);
                    }
                }
                break;
            case R.id.lin_select:
                if (adapter == null) {
                    return;
                }
                if (isSelsetAll) {
                    isSelsetAll = false;
                    adapter.select_count = 0;
                    cb_all.setChecked(false);
                    for (int i = 0; i < noticeList.size(); i++) {
                        noticeList.get(i).setSelect(false);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    isSelsetAll = true;
                    adapter.select_count = noticeList.size();
                    cb_all.setChecked(true);
                    for (int i = 0; i < noticeList.size(); i++) {
                        noticeList.get(i).setSelect(true);
                    }
                    adapter.notifyDataSetChanged();
                }
                break;
            case R.id.ll_left:
                finish();
                break;
        }
    }

    @Override
    public void getNoticeMsg(String type, List<NoticeMsgModel> l) {
        dialogDissmiss();
        if ("true".equals(type)) {
            noticeList = new ArrayList<SelectNoticeMsgModel>();
            for (int i = 0; i < l.size(); i++) {
                SelectNoticeMsgModel m = new SelectNoticeMsgModel(false, l.get(i));
                noticeList.add(m);
            }
            adapter = new MessageNoticeAdapter(NoticeFC2Activity.this, noticeList, "notice", cb_all);
            list.setAdapter(adapter);
        }
    }

    @Override
    public void getMeasureMsg(String type, List<NoticeMsgModel> l) {
        dialogDissmiss();
        if ("true".equals(type)) {
            noticeList = new ArrayList<SelectNoticeMsgModel>();
            for (int i = 0; i < l.size(); i++) {
                SelectNoticeMsgModel m = new SelectNoticeMsgModel(false, l.get(i));
                noticeList.add(m);
            }
            adapter = new MessageNoticeAdapter(NoticeFC2Activity.this, noticeList, "fc", cb_all);
            list.setAdapter(adapter);
        }
    }

    @Override
    public void getOperateMsg(String type, List<OperateMsgModel> l) {
        dialogDissmiss();
        if ("true".equals(type)) {
            operatList = l;
            noticeList = new ArrayList<SelectNoticeMsgModel>();
            for (int i = 0; i < operatList.size(); i++) {
                OperateMsgModel op = l.get(i);
                NoticeMsgModel m = new NoticeMsgModel(op.getMsgType(), op.getMsgId(), op.getContent(), op.getSendTime(), op.getIsRead());
                SelectNoticeMsgModel model = new SelectNoticeMsgModel(false, m);
                noticeList.add(model);
            }
            adapter = new MessageNoticeAdapter(NoticeFC2Activity.this, noticeList, "xzs", cb_all);
            list.setAdapter(adapter);
        }
    }

    @Override
    public void upRedTime(String type) {

    }

    @Override
    public void deleteMsg(String type) {
        if ("true".equals(type)) {
            List<SelectNoticeMsgModel> nList = new ArrayList<SelectNoticeMsgModel>();
            nList.addAll(noticeList);
            List<OperateMsgModel> oList = new ArrayList<OperateMsgModel>();
            if ("xzs".equals(type)) {
                oList.addAll(operatList);
            }
            for (int i = 0; i < nList.size(); i++) {
                SelectNoticeMsgModel selectNoticeMsgModel = nList.get(i);
                if (selectNoticeMsgModel.isSelect()) {
                    noticeList.remove(selectNoticeMsgModel);
                    if ("xzs".equals(type)) {
                        OperateMsgModel operateMsgModel = oList.get(i);
                        operatList.remove(operateMsgModel);
                    }
                }
            }
            adapter.select_count = 0;
            adapter.notifyDataSetChanged();
            if(noticeList.size()==0){
                cb_all.setChecked(false);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            dialogShow("加载中");
            String t = data.getStringExtra("type");
            if ("fc".equals(t)) {
                tv_title.setText("复测提醒");
                manager.doGetMeasureMsg(model.getUserid());
            } else if ("notice".equals(t)) {
                tv_title.setText("服务窗");
                manager.doGetNoticeMsg(model.getUserid());
            } else if ("xzs".equals(t)) {
                tv_title.setText("小助手");
                manager.doGetOperateMsg(model.getUserid());
            }
        }
    }


}
