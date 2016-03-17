package com.softtek.lai.module.login.contants;

/**
 * Created by jerry.guan on 3/4/2016.
 */
public class Constants {

    //注册验证码标识
    public static final String REGIST_IDENTIFY="0";

    //重置密码验证码标识
    public static final String RESET_PASSWORD_IDENTIFY="1";

    //用户缓存数据目录名称
    public static final String USER_ACACHE_DATA_DIR="useraCache";

    //用户数据缓存key
    public static final String USER_ACACHE_KEY="user";

    /**
     * 用户角色常量
     *  0:NC 普通顾客，未认证用户
     *  1:PC 高级顾客
     *  2:SR 助教
     *  3:SP 顾问
     *  4:INC 受邀普通顾客
     *  5: VR 游客
     */
    public static final int NC=0;
    public static final int PC=1;
    public static final int SR=2;
    public static final int SP=3;
    public static final int INC=4;
    public static final int VR=5;

}
