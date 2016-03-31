/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.presenter;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by jarvis on 3/3/2016.
 */
public interface IAssistantPresenter {

    //获取助教列表
    void getAssistantList(String classId, ListView list_assistant);

    //邀请助教
    void sendInviterSR(String classId, String Inviters, ImageView img_invite);

    //获取助教申请列表
    void showAllApplyAssistants(String accountId, ListView list);

    //审核助教申请
    void reviewAssistantApplyList(long applyId, int status, LinearLayout lin_buttons, TextView text_state);

    //班级列表（助教）
    void showAllClassList(String managerId, ListView list_class);

    //班级助教列表
    void showAssistantByClass(String managerId, String classId, ListView list_assistant);

    //助教详情
    void showAssistantDetails(String assistantId, String classId);
}
