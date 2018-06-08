package com.softtek.lai.module.bodygame3.conversation.model;

import java.util.List;

/**
 * Created by jessica.zhang on 2016/12/15.
 */

public class ContactListModel {
    private int count;//好友申请未处理列表数量
    private List<ChatContactModel> contacts;//通讯录联系人列表


    public ContactListModel(int count, List<ChatContactModel> contacts) {
        this.count = count;
        this.contacts = contacts;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ChatContactModel> getContacts() {
        return contacts;
    }

    public void setContacts(List<ChatContactModel> contacts) {
        this.contacts = contacts;
    }
}
