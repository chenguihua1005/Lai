package com.softtek.lai.module.customermanagement.model;

/**
 * Created by jessica.zhang on 11/29/2017.
 */

public class ClubAuthorityModel {
    private boolean HasAuthorityOfCreate;//是否有创建俱乐部的权限
    private boolean HasClub;//是否有俱乐部
    private String Identity;//身份名称

    public boolean isHasAuthorityOfCreate() {
        return HasAuthorityOfCreate;
    }

    public void setHasAuthorityOfCreate(boolean hasAuthorityOfCreate) {
        HasAuthorityOfCreate = hasAuthorityOfCreate;
    }

    public boolean isHasClub() {
        return HasClub;
    }

    public void setHasClub(boolean hasClub) {
        HasClub = hasClub;
    }

    public String getIdentity() {
        return Identity;
    }

    public void setIdentity(String identity) {
        Identity = identity;
    }
}
