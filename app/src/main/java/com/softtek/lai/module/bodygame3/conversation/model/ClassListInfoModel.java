package com.softtek.lai.module.bodygame3.conversation.model;

import java.util.List;

/**
 * Created by jessica.zhang on 2016/12/1.
 */

public class ClassListInfoModel {
    private int TotalPages;
    private List<ClassMemberModel> ContactList;

    public ClassListInfoModel(int totalPages, List<ClassMemberModel> contactList) {
        TotalPages = totalPages;
        ContactList = contactList;
    }

    public int getTotalPages() {
        return TotalPages;
    }

    public void setTotalPages(int totalPages) {
        TotalPages = totalPages;
    }

    public List<ClassMemberModel> getContactList() {
        return ContactList;
    }

    public void setContactList(List<ClassMemberModel> contactList) {
        ContactList = contactList;
    }
}
