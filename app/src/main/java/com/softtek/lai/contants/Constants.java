package com.softtek.lai.contants;

/**
 * Created by jerry.guan on 3/4/2016.
 */
public class Constants {
    //是否在登录环信
    public static String IS_LOGINIMG = "0";  //0不再登录 1在登录

    //注册验证码标识
    public static final String REGIST_IDENTIFY = "0";

    //重置密码验证码标识
    public static final String RESET_PASSWORD_IDENTIFY = "1";

    //用户缓存数据目录名称
    public static final String USER_ACACHE_DATA_DIR = "useraCache";

    //用户数据缓存key
    public static final String USER_ACACHE_KEY = "user";

    //首页缓存数据
    public static final String HOME_CACHE_DATA_DIR = "homeCache";

    //用户数据缓存key
    public static final String HOEM_ACACHE_KEY = "homeInfo";


    //token
    public static final String TOKEN = "token";
    public static final String USER = "account";
    public static final String PDW = "password";
    /**
     * 用户角色常量
     * 0:NC 普通顾客，未认证用户
     * 1:PC 高级顾客
     * 2:SR 助教
     * 3:SP 顾问
     * 4:INC 受邀普通顾客
     * 5: VR 游客
     */
    public static final int NC = 0;
    public static final int PC = 1;
    public static final int SR = 2;
    public static final int SP = 3;
    public static final int INC = 4;
    public static final int VR = 5;


    /**
     * 主页活动类型
     * 1：活动记录
     * 2：产品信息
     * 6：销售信息
     */
    public static final int ACTIVITY_RECORD = 1;
    public static final int PRODUCT_INFO = 2;
    public static final int SALE_INFO = 6;

    /**
     * 功能模块常量标识符
     */
    public static final int BODY_GAME = 0;
    public static final int LAI_YUNDONG = 1;
    public static final int LAI_CLASS = 2;
    public static final int LAI_CHEN = 3;
    public static final int LAI_SHOP = 4;

    public static final String MESSAGE_RECEIVED_ACTION = "jpush.MESSAGE_RECEIVED_ACTION";
    public static final String MESSAGE_CHAT_ACTION = "chat.MESSAGE_RECEIVED_ACTION";
    public static final String MESSAGE_CREATE_CLASS_ACTION = "create.class.action";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    /**
     * 点赞标识
     */
    public static final String NO_ZAN = "0";
    public static final String HAS_ZAN = "1";

    /**
     * 奶昔0，奶昔操1.自定义2
     */
    public static final int NAIXI = 0;
    public static final int NAIXICAO = 1;
    public static final int ZIDINGYI = 2;

    //pk规则
    public static final int BUSHU = 0;//步数
    public static final int KM = 1;//公里数

    //跳转至PK详情的数据
    public static final int CREATE_PK = 1;//创建PK
    public static final int LIST_PK = 2;//PK列表
    public static final int MESSAGE_PK = 3;//PK通知


    //日期类型
    public static final int ACTIVITY = 1;
    public static final int CREATECLASS = 2;
    public static final int RESET = 3;
    public static final int FREE = 4;

    //班级角色1：开班教练Head coach	2：组别教练	3：组别助教assistant	4：学员
    public static final int HEADCOACH=1;
    public static final int COACH=2;
    public static final int ASSISTANT=3;
    public static final int STUDENT=4;


    public static final int FROM_CONTACT = 0x0008;  //来自于通讯录
    public static final int FROM_OLD_CLASS = 0;//来自往期班级



}
