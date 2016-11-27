package com.softtek.lai.module.bodygame3.more.model;

import java.util.List;

/**
 * @author jerry.Guan
 *         created by 2016/11/27
 */

public class ClassMember {

    private List<ClassGroup> Groups;

    private List<Member> Members;

    public List<ClassGroup> getGroups() {
        return Groups;
    }

    public void setGroups(List<ClassGroup> groups) {
        Groups = groups;
    }

    public List<Member> getMembers() {
        return Members;
    }

    public void setMembers(List<Member> members) {
        Members = members;
    }
}
