package com.softtek.lai.module.login.model;

import java.io.Serializable;

/**
 * Created by jerry.guan on 3/3/2016.
 */
public class RoleInfo implements Serializable{

    private String Role;
    private String CertTime;
    private String Certification;

    public String getCertification() {
        return Certification;
    }

    public void setCertification(String certification) {
        Certification = certification;
    }

    public String getCertTime() {
        return CertTime;
    }

    public void setCertTime(String CertTime) {
        this.CertTime = CertTime;
    }

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
                ", CertTime='" + CertTime + '\'' +
                ", Certification='" + Certification + '\'' +
                '}';
    }
}
