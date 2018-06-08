package com.softtek.lai.module.customermanagement.model;

/**
 * Created by jia.lu on 11/23/2017.
 */

public class ClubNameModel {
    private String clubName;
    private boolean isSelected;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
