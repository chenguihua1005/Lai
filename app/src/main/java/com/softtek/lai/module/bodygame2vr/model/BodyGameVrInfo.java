package com.softtek.lai.module.bodygame2vr.model;

import com.softtek.lai.module.bodygame2.model.CompetitionModel;
import com.softtek.lai.module.bodygame2.model.Tips;

import java.util.List;

/**
 * Created by jerry.guan on 7/16/2016.
 */
public class BodyGameVrInfo {

    private String Banner;
    private String TotalPc;
    private String TotalLoss;

    //大赛赛况
    private List<CompetitionModel> Competition;
    //tips
    private String Tips_video_name;
    private String Tips_video_url;
    private String Tips_video_id;
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

    public List<CompetitionModel> getCompetition() {
        return Competition;
    }

    public void setCompetition(List<CompetitionModel> competition) {
        Competition = competition;
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

    public String getTips_video_id() {
        return Tips_video_id;
    }

    public void setTips_video_id(String tips_video_id) {
        Tips_video_id = tips_video_id;
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
