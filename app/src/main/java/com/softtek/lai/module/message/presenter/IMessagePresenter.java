/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.message.presenter;

import android.app.ProgressDialog;
import android.widget.ImageView;
import android.widget.ListView;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.message.model.MessageDetailInfo;
import com.softtek.lai.utils.StringUtil;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.Header;

/**
 * Created by jarvis on 3/3/2016.
 */
public interface IMessagePresenter {

    //消息中心消息列表
    void getMsgList(String accountid);

    //PC接受参赛要求
    void acceptInviterToClass(String inviters, String classId, String acceptType, MessageDetailInfo messageDetailInfo);

    //SR接受SP邀请
    void acceptInviter(String inviters, String classId, String acceptType);

    //更改阅读时间
    void upReadTime(String msgtype, String recevieid, String senderid, String classid);

    //删除通知或复测消息
    void delNoticeOrMeasureMsg(String messageId,String type);

    //主页是否有提示消息
    void getMessageRead(ImageView img_red);

    //检测手机是否已存在
    void phoneIsExist(String mobile,ProgressDialog dialog,int id);

    //此班级周期内学员是否已加入班级
    void accIsJoinClass(String accountid,String classid);

}
