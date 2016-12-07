package com.softtek.lai.module.laisportmine.model;

import java.io.Serializable;

/**
 * 活动通知列表模型
 */
public class NoticeActModel implements Serializable {
    

    public int Msgtype;
    public String Msgid;
    public String ActId;
    public String ActTitle;
    public String MsgContent;
    public String SendTime;
    public int IsJoinAct;
    public int IsRead;
    
}
