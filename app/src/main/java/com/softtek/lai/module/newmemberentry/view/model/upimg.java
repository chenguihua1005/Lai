package com.softtek.lai.module.newmemberentry.view.model;

/**
 * Created by julie.zhu on 3/23/2016.
 */
public class upimg
{
   private int type;

    public upimg(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "upimg{" +
                "type=" + type +
                '}';
    }
}
