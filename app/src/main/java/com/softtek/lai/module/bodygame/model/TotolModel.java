package com.softtek.lai.module.bodygame.model;

/**
 * Created by lareina.qiao on 4/11/2016.
 */
public class TotolModel {
    private String total_person;
    private String total_loss;

    @Override
    public String toString() {
        return "TotolModel{" +
                "total_person='" + total_person + '\'' +
                ", total_loss='" + total_loss + '\'' +
                '}';
    }

    public String getTotal_person() {
        return total_person;
    }

    public void setTotal_person(String total_person) {
        this.total_person = total_person;
    }

    public String getTotal_loss() {
        return total_loss;
    }

    public void setTotal_loss(String total_loss) {
        this.total_loss = total_loss;
    }
}
