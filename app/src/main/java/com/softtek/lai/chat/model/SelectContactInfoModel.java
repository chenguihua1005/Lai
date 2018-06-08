/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.chat.model;

import com.softtek.lai.module.bodygame3.conversation.model.ChatContactModel;

import java.io.Serializable;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class SelectContactInfoModel implements Serializable {

//    private ChatContactInfoModel model;
    private ChatContactModel model;
    private boolean Selected;

    @Override
    public String toString() {
        return "SelectContactInfoModel{" +
                "model=" + model +
                ", Selected=" + Selected +
                '}';
    }

    public ChatContactModel getModel() {
        return model;
    }

    public void setModel(ChatContactModel model) {
        this.model = model;
    }

    public boolean isSelected() {
        return Selected;
    }

    public void setSelected(boolean selected) {
        Selected = selected;
    }
}
