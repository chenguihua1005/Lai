package com.softtek.lai.premission;

/**
 * Created by jerry.guan on 3/31/2016.
 * 角色
 */
public class Role {

    private Power vr;//游客
    private Power nc;//普通顾客，未认证角色
    private Power inc;//受邀顾客
    private Power pc;//高级顾客
    private Power sr;//助教
    private Power sp;//顾问

    public Power getVr() {
        return vr;
    }

    public void setVr(Power vr) {
        this.vr = vr;
    }

    public Power getNc() {
        return nc;
    }

    public void setNc(Power nc) {
        this.nc = nc;
    }

    public Power getInc() {
        return inc;
    }

    public void setInc(Power inc) {
        this.inc = inc;
    }

    public Power getPc() {
        return pc;
    }

    public void setPc(Power pc) {
        this.pc = pc;
    }

    public Power getSr() {
        return sr;
    }

    public void setSr(Power sr) {
        this.sr = sr;
    }

    public Power getSp() {
        return sp;
    }

    public void setSp(Power sp) {
        this.sp = sp;
    }

    @Override
    public String toString() {
        return "Role{" +
                "vr=" + vr +
                ", nc=" + nc +
                ", inc=" + inc +
                ", pc=" + pc +
                ", sr=" + sr +
                ", sp=" + sp +
                '}';
    }
}
