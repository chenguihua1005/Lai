package com.softtek.lai.module.counselor.presenter;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
/**
 * Created by jarvis.liu on 3/22/2016.
 */
public interface IStudentPresenter {

    //Sp邀请学员参赛学员列表
    void getNotInvitePC(String classid,String spaccid,ListView list_student);

    //发送邀请
    void sendInviterMsg(String inviters,String classId,ImageView img_invite);

}
