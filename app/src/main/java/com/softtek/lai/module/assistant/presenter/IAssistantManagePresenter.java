package com.softtek.lai.module.assistant.presenter;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by jerry.guan on 3/3/2016.
 */
public interface IAssistantManagePresenter {

    //获取助教申请列表
    void showAllApplyAssistants(String accountId,ListView list);

    //审核助教申请
    void reviewAssistantApplyList(long applyId, int status,LinearLayout lin_buttons,TextView text_state);

    //班级列表（助教）
    void showAllClassList(String managerId,ListView list_class);

    //班级助教列表
    void showAssistantByClass(String managerId,String classId,ListView list_assistant);

    //助教详情
    void showAssistantDetails(String assistantId,String classId);

    //Sp邀请学员参赛学员列表
    void getNotInvitePC(String classid,String spaccid,ListView list_student);

    //Sp邀请学员参赛学员列表
    void sendInviterMsg(String inviters,String classId,ImageView img_invite);


}
