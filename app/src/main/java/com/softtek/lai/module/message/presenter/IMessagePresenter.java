/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.message.presenter;

import android.widget.ListView;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.message.model.MessageDetailInfo;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.Header;

/**
 * Created by jarvis on 3/3/2016.
 */
public interface IMessagePresenter {

    //消息中心消息列表
    void getMsgList();

    //PC接受参赛要求
    void acceptInviterToClass(String inviters, String classId, String acceptType, MessageDetailInfo messageDetailInfo);

    //SR接受SP邀请
    void acceptInviter(String inviters, String classId, String acceptType);

    //更改阅读时间
    void upReadTime(String msgtype, String recevieid, String senderid, String classid);

    //删除通知或复测消息
    void delNoticeOrMeasureMsg(String messageId);

    //主页是否有提示消息
    void getMessageRead(String accountID);

}
