package com.softtek.lai.module.bodygame3.head.model;

/**
 * Created by shelly.xu on 11/22/2016.
 */

public class TypeModel {
    private int typecode;
    private String typename;

    public TypeModel(int typecode, String typename) {
        this.typecode = typecode;
        this.typename = typename;
    }

    public int getTypecode() {
        return typecode;
    }

    public void setTypecode(int typecode) {
        this.typecode = typecode;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }
}
