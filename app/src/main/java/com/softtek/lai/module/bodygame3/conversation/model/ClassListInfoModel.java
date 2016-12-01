package com.softtek.lai.module.bodygame3.conversation.model;

import java.util.List;

/**
 * Created by jessica.zhang on 2016/12/1.
 */

public class ClassListInfoModel {
    private int TotalPages;
    private List<ClassMemberModel> Members;

    public ClassListInfoModel(int totalPages, List<ClassMemberModel> members) {
        TotalPages = totalPages;
        Members = members;
    }

    public int getTotalPages() {
        return TotalPages;
    }

    public void setTotalPages(int totalPages) {
        TotalPages = totalPages;
    }

    public List<ClassMemberModel> getMembers() {
        return Members;
    }

    public void setMembers(List<ClassMemberModel> members) {
        Members = members;
    }
}
