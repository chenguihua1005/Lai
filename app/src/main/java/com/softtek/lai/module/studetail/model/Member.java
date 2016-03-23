package com.softtek.lai.module.studetail.model;

/**
 * Created by julie.zhu on 3/22/2016.
 */
public class Member {

    private int AccountId;  //学员ID
    private int ClassId;    //班级ID
    private int OrderNum;   //排序(此接口不使用该字段)
    private String UserName;//用户名
    private String AfterImg;//减重前图片
    private String  BeforImg; //减重后图片
    private int LogCount;   //日志篇数
    private String LossAfter;//	减重前重量
    private String LossBefor;//	减重后重量
    private String LossWeight;//减重斤数
}
