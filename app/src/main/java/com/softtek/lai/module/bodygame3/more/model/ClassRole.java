package com.softtek.lai.module.bodygame3.more.model;

/**
 * Created by jerry.guan on 11/22/2016.
 * 班级角色模型
 */

public class ClassRole {

    private String RoleName;
    private int RoleId;
    public boolean isSelected;

    public String getRoleName() {
        return RoleName;
    }

    public void setRoleName(String roleName) {
        RoleName = roleName;
    }

    public int getRoleId() {
        return RoleId;
    }

    public void setRoleId(int roleId) {
        RoleId = roleId;
    }
}
