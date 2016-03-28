package com.softtek.lai.module.counselor.presenter;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * Created by jerry.guan on 3/3/2016.
 */
public interface IAssistantPresenter {

    //获取助教列表
    void getAssistantList(String classId,ListView list_assistant);

    //邀请助教
    void sendInviterSR(String classId,String Inviters,ImageView img_invite);
}
