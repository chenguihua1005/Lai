package com.softtek.lai.module.message2.model;

/**
 * 总教练确认申请信息模型
 * @author jerry.Guan
 *         created by 2016/12/6
 */

public class ApplyModel {

    public String applyId;
    public long reviewerId;
    public long accountId;
    public String classId;
    public String groupId;
    public int classRole;
    public int status;
    public int Target;//学员目标 1增重0减重
}
