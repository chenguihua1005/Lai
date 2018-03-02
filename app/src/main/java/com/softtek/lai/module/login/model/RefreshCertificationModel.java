package com.softtek.lai.module.login.model;

/**
 * Created by jia.lu on 2/28/2018.
 */

public class RefreshCertificationModel {

    /**
     * Role : SR
     * CertTime : 2018-02-28 11:14:49
     * Certification : CN1190849
     * SuperCerti : CN640521
     */

    private String Role;
    private String CertTime;
    private String Certification;
    private String SuperCerti;

    public String getRole() {
        return Role;
    }

    public void setRole(String Role) {
        this.Role = Role;
    }

    public String getCertTime() {
        return CertTime;
    }

    public void setCertTime(String CertTime) {
        this.CertTime = CertTime;
    }

    public String getCertification() {
        return Certification;
    }

    public void setCertification(String Certification) {
        this.Certification = Certification;
    }

    public String getSuperCerti() {
        return SuperCerti;
    }

    public void setSuperCerti(String SuperCerti) {
        this.SuperCerti = SuperCerti;
    }
}
