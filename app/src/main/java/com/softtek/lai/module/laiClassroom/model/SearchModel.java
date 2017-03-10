package com.softtek.lai.module.laiClassroom.model;

/**
 * Created by jia.lu on 2017/3/10.
 */

public class SearchModel {
    private int type;
    private int isMultiPic;

    public SearchModel(int type,int isMultiPic){
        this.type = type;
        this.isMultiPic = isMultiPic;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIsMultiPic() {
        return isMultiPic;
    }

    public void setIsMultiPic(int isMultiPic) {
        this.isMultiPic = isMultiPic;
    }
}
