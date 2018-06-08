package com.softtek.lai.module.bodygame3.head.model;

import java.util.List;

/**
 * Created by shelly.xu on 12/2/2016.
 */

public class ChooseModel {
    private RongyuModel Honor;
    private List<TuijianModel> ListRec;
    private ZhaopianModel PhotoWall;
    private List<PartnersModel> PartnersList;

    public ChooseModel(RongyuModel honor, List<TuijianModel> listRec, ZhaopianModel photoWall, List<PartnersModel> partnersList) {
        Honor = honor;
        ListRec = listRec;
        PhotoWall = photoWall;
        PartnersList = partnersList;
    }

    public RongyuModel getHonor() {
        return Honor;
    }

    public void setHonor(RongyuModel honor) {
        Honor = honor;
    }

    public List<TuijianModel> getListRec() {
        return ListRec;
    }

    public void setListRec(List<TuijianModel> listRec) {
        ListRec = listRec;
    }

    public ZhaopianModel getPhotoWall() {
        return PhotoWall;
    }

    public void setPhotoWall(ZhaopianModel photoWall) {
        PhotoWall = photoWall;
    }

    public List<PartnersModel> getPartnersList() {
        return PartnersList;
    }

    public void setPartnersList(List<PartnersModel> partnersList) {
        PartnersList = partnersList;
    }
}
