/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.message.model;

import java.io.Serializable;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class MeasureRemindInfo implements Serializable {

    private String MessageId;
    private String Content;
    private String SentTime;

    public String getMessageId() {
        return MessageId;
    }

    public void setMessageId(String messageId) {
        MessageId = messageId;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    @Override
    public String toString() {
        return "MeasureRemindInfo{" +
                "MessageId='" + MessageId + '\'' +
                ", Content='" + Content + '\'' +
                ", SentTime='" + SentTime + '\'' +
                '}';
    }

    public String getSentTime() {
        return SentTime;
    }

    public void setSentTime(String sentTime) {
        SentTime = sentTime;
    }
}
