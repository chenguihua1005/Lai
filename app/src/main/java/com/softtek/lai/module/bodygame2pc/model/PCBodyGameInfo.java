package com.softtek.lai.module.bodygame2pc.model;

import com.softtek.lai.module.bodygame2.model.CompetitionModel;
import com.softtek.lai.module.bodygame2.model.SPPCMoldel;
import com.softtek.lai.module.bodygame2.model.Tips;

import java.util.List;

/**
 * Created by jerry.guan on 7/11/2016.
 */
public class PCBodyGameInfo {

    private String Banner;
    private String TotalPc;
    private String TotalLoss;
    private String PCLoss;
    private String PCLossOrde;
    private String PCwaistline;
    private String PCwaistlineOrder;
    private String PCPysical;
    private String PCPysicalOrder;
    private String PCLossPrecent;
    private String PCLossPrecentOrder;
    private String PCLossAfter;
    private String PCLossAfterImg;
    private String PCLossBefore;
    private String PCLossBeforeImg;
    //tips
    private String Tips_video_name;
    private String Tips_video_url;
    private String Tips_video_timelen;
    private String Tips_video_backPicture;

    //咨询
    private List<Tips> Tips_content;

    public String getBanner() {
        return Banner;
    }

    public void setBanner(String banner) {
        Banner = banner;
    }

    public String getTotalPc() {
        return TotalPc;
    }

    public void setTotalPc(String totalPc) {
        TotalPc = totalPc;
    }

    public String getTotalLoss() {
        return TotalLoss;
    }

    public void setTotalLoss(String totalLoss) {
        TotalLoss = totalLoss;
    }

    public String getPCLoss() {
        return PCLoss;
    }

    public void setPCLoss(String PCLoss) {
        this.PCLoss = PCLoss;
    }

    public String getPCLossOrde() {
        return PCLossOrde;
    }

    public void setPCLossOrde(String PCLossOrde) {
        this.PCLossOrde = PCLossOrde;
    }

    public String getPCwaistline() {
        return PCwaistline;
    }

    public void setPCwaistline(String PCwaistline) {
        this.PCwaistline = PCwaistline;
    }

    public String getPCwaistlineOrder() {
        return PCwaistlineOrder;
    }

    public void setPCwaistlineOrder(String PCwaistlineOrder) {
        this.PCwaistlineOrder = PCwaistlineOrder;
    }

    public String getPCPysical() {
        return PCPysical;
    }

    public void setPCPysical(String PCPysical) {
        this.PCPysical = PCPysical;
    }

    public String getPCPysicalOrder() {
        return PCPysicalOrder;
    }

    public void setPCPysicalOrder(String PCPysicalOrder) {
        this.PCPysicalOrder = PCPysicalOrder;
    }

    public String getPCLossPrecent() {
        return PCLossPrecent;
    }

    public void setPCLossPrecent(String PCLossPrecent) {
        this.PCLossPrecent = PCLossPrecent;
    }

    public String getPCLossPrecentOrder() {
        return PCLossPrecentOrder;
    }

    public void setPCLossPrecentOrder(String PCLossPrecentOrder) {
        this.PCLossPrecentOrder = PCLossPrecentOrder;
    }

    public String getPCLossAfter() {
        return PCLossAfter;
    }

    public void setPCLossAfter(String PCLossAfter) {
        this.PCLossAfter = PCLossAfter;
    }

    public String getPCLossAfterImg() {
        return PCLossAfterImg;
    }

    public void setPCLossAfterImg(String PCLossAfterImg) {
        this.PCLossAfterImg = PCLossAfterImg;
    }

    public String getPCLossBefore() {
        return PCLossBefore;
    }

    public void setPCLossBefore(String PCLossBefore) {
        this.PCLossBefore = PCLossBefore;
    }

    public String getPCLossBeforeImg() {
        return PCLossBeforeImg;
    }

    public void setPCLossBeforeImg(String PCLossBeforeImg) {
        this.PCLossBeforeImg = PCLossBeforeImg;
    }

    public String getTips_video_name() {
        return Tips_video_name;
    }

    public void setTips_video_name(String tips_video_name) {
        Tips_video_name = tips_video_name;
    }

    public String getTips_video_url() {
        return Tips_video_url;
    }

    public void setTips_video_url(String tips_video_url) {
        Tips_video_url = tips_video_url;
    }

    public String getTips_video_timelen() {
        return Tips_video_timelen;
    }

    public void setTips_video_timelen(String tips_video_timelen) {
        Tips_video_timelen = tips_video_timelen;
    }

    public String getTips_video_backPicture() {
        return Tips_video_backPicture;
    }

    public void setTips_video_backPicture(String tips_video_backPicture) {
        Tips_video_backPicture = tips_video_backPicture;
    }

    public List<Tips> getTips_content() {
        return Tips_content;
    }

    public void setTips_content(List<Tips> tips_content) {
        Tips_content = tips_content;
    }
}
