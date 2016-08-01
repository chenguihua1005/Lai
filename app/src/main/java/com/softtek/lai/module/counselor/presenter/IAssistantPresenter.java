/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.presenter;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import retrofit.http.Field;

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
    void reviewAssistantApplyList(long applyId, int status,int posion,String type);

    //班级列表（助教）
    void showAllClassList(String managerId, ListView list_class);

    //班级助教列表
    void showAssistantByClass(String managerId, String classId, ListView list_assistant);

    //助教详情
    void showAssistantDetails(String assistantId, String classId);

    //移除助教
    void removeAssistantRoleByClass(String assistantId, String classId,String type);

    //可申请班级助教列表
    void showSRApplyList(String assistantId,ListView list_apply_assistant);

    //申请班级助教（SR）
    void srApplyAssistant(String applyerId,String classManagerId,String classId,String comments,TextView text_apply,TextView text_state);
}
