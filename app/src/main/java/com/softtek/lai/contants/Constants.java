package com.softtek.lai.contants;

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

    //首页缓存数据
    public static final String HOME_CACHE_DATA_DIR="homeCache";

    //用户数据缓存key
    public static final String HOEM_ACACHE_KEY="homeInfo";
    //活动记录缓存key
    public static final String HOEM_ACTIVITY_KEY="activityRecord";
    //产品信息缓存key
    public static final String HOEM_PRODUCT_KEY="productInfo";
    //销售信息key
    public static final String HOEM_SALE_KEY="saleInfo";

    //token
    public static final String TOKEN="token";
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

    /**
     * 动态发布标识
     * 0:复测提示
     * 1:广告
     * 2:sp发布
     * 3:自动生成
     */
    public static final int RESET_TIP=0;
    public static final int BANNER=1;
    public static final int SP_SEND=2;
    public static final int AUTO_GENERATE=3;

    /**
     * 学员列表获取数据类别
     * 0：按减重比
     * 1：按减重百分比
     * 2：按体质
     * 3：按腰围
     */
    public static final String LOSS_WEIGHT="0";
    public static final String LOSS_WEIGHT_PER="1";
    public static final String PHYSIQUE="2";
    public static final String WAISTLINE="3";

    /**
     * 助教邀请以及未接受邀请
     * 0：未被邀请
     * 1：被邀请
     */
    public static final int NOT_INVITED=0;
    public static final int INVITED=1;

    /**
     * 主页活动类型
     * 1：活动记录
     * 2：产品信息
     * 6：销售信息
     */
    public static final int ACTIVITY_RECORD=1;
    public static final int PRODUCT_INFO=2;
    public static final int SALE_INFO=6;

    /**
     * 刷新和加载标识
     * 0：刷新
     * 1：加载
     */
    public static final int REFRESH=0;
    public static final int LOADING=1;

    /**
     * 功能模块常量标识符
     */
    public static final int BODY_GAME=0;
    public static final int LAI_YUNDONG=1;
    public static final int OFFICE=2;
    public static final int LAI_EXCLE=3;
    public static final int LAI_SHOP=4;

    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

}
