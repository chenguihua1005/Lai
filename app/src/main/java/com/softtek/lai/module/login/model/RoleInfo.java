package com.softtek.lai.module.login.model;

import java.io.Serializable;

/**
 * Created by jerry.guan on 3/3/2016.
 */
public class RoleInfo implements Serializable{

    private String Role;

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    @Override
    public String toString() {
        return "RoleInfo{" +
                "Role='" + Role + '\'' +
                '}';
    }
}
