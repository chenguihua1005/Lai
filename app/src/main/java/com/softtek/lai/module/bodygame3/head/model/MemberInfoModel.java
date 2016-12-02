package com.softtek.lai.module.bodygame3.head.model;

import java.util.List;

/**
 * Created by lareina.qiao on 12/2/2016.
 */

public class MemberInfoModel {
    private String UserName;//学员姓名
    private String UserPhoto;//学员头像
    private String UserThPhoto;//学员头像缩略图
    private String ClassRole;//学员所在班级所属角色（学员可展示体重变化曲线图，其他角色不能）
    private String HXAccountId;//环信id
    private String MilkAngle;//奶昔天使
    private String MilkAngleId;//奶昔天使id
    private String Introducer;//爱心学员
    private String IntroducerId;//爱心学员id
    private String IsFocus;//是否关注（true关注，false未关注）
    private String IsFriend;//是否为好友（1.是）
    private String TotalLossWeight;//总减重
    private List<NewsTopFourModel> NewsTopFour;//动态图片列表
//    private String
//    private String
//    private String
//    private String
}
