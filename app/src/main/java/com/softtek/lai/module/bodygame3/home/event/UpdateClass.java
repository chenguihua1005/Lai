package com.softtek.lai.module.bodygame3.home.event;

import com.softtek.lai.module.bodygame3.more.model.ClassModel;

/**
 * @author jerry.Guan
 *         created by 2016/11/28
 */

public class UpdateClass {

    private int status;//0是修改 1是添加 2是移除班级
    private ClassModel model;

    public UpdateClass() {
    }

    public UpdateClass(int status, ClassModel model) {
        this.status = status;
        this.model = model;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ClassModel getModel() {
        return model;
    }

    public void setModel(ClassModel model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "UpdateClass{" +
                "status=" + status +
                ", model=" + model +
                '}';
    }
}
