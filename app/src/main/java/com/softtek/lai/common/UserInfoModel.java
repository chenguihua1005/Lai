package com.softtek.lai.common;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import com.github.snowdream.android.util.Log;
import com.google.gson.Gson;
import com.softtek.lai.LaiApplication;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.jpush.JpushSet;
import com.softtek.lai.module.bodygame3.head.view.HeadGameFragment2;
import com.softtek.lai.module.bodygame3.home.event.SaveClassModel;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.premission.Power;
import com.softtek.lai.premission.Role;
import com.softtek.lai.utils.ACache;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import zilla.libcore.file.SharedPreferenceService;

/**
 * Created by jerry.guan on 3/31/2016.
 * 用户登录信息
 */
public class UserInfoModel {

    private static final String USER_ID="user_id";

    private static UserInfoModel info=null;
    private UserModel user;
    private String token=null;
    private ACache aCache;
    private ACache classCache;
    private Role role;
    private boolean isVr=true;
    private UserDao dao;

    private UserInfoModel(Context context){
        aCache=ACache.get(context,Constants.USER_ACACHE_DATA_DIR);
        classCache=ACache.get(context, HeadGameFragment2.SAVE_CLASS_DIR);
        token=SharedPreferenceService.getInstance().get(Constants.TOKEN, "");
        //载入权限数据
        role=loadingRole(context);
        dao=UserDao.getInstance();
    }

    private Role loadingRole(Context context){
        Role role=null;
        AssetManager manager=context.getAssets();
        try {
            InputStream is=manager.open("config/premission.json");
            Gson gson=new Gson();
            role=gson.fromJson(new InputStreamReader(is), Role.class);
            Log.i(role.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return role;
    }

    public static UserInfoModel getInstance(Context context){
        if(info==null){
            info=new UserInfoModel(context);
        }
        return info;
    }

    public static UserInfoModel getInstance(){
        return info;
    }

    /**
     * 清楚班级默认存储
     */
    public void clearClassSave(String classId){
        SaveClassModel model= (SaveClassModel) classCache.getAsObject(HeadGameFragment2.SAVE_CLASS);
        if(model!=null&&classId.equals(model.classId)){
            classCache.remove(HeadGameFragment2.SAVE_CLASS);
        }
    }
    public void clearClassSave(){
        classCache.remove(HeadGameFragment2.SAVE_CLASS);
    }

    /**
     * 退出登录
     */
    public void loginOut(){
        clear();
        JpushSet set = new JpushSet(LaiApplication.getInstance());
        set.setAlias("");
        MobclickAgent.onProfileSignOff();
    }
    /**
     * 退出登录
     */
    public void clear(){
        //请出用户数据
        token=null;
        //清除token
        SharedPreferenceService.getInstance().put("token", "");
        SharedPreferenceService.getInstance().put(Constants.USER, "");
        SharedPreferenceService.getInstance().put(Constants.PDW, "");
        SharedPreferenceService.getInstance().get(USER_ID,-1L);
        isVr=true;
        //清除本地用户
        setUser(null);
        classCache.remove(HeadGameFragment2.SAVE_CLASS);
    }
    public long getUserId(){
        return SharedPreferenceService.getInstance().get(USER_ID,0L);
    }

    /**
     * 存储用户本地缓存对象
     * @return
     */
    public void saveUserCache(UserModel user){
        //存入文件
        isVr=false;
        SharedPreferenceService.getInstance().put(USER_ID, Long.parseLong(user.getUserid()));
        setUser(user);
        setToken(user.getToken());
        //存储本地
        aCache.remove(Constants.USER_ACACHE_KEY);
        dao.saveUserOrUpdate(user);
        SharedPreferenceService.getInstance().put(Constants.TOKEN,token);
        MobclickAgent.onProfileSignIn(user.getUserid());
    }

    /**
     * 游客登录
     */
    public void visitorLogin(){
        isVr=true;
        setToken("");
        user=new UserModel();
        user.setUserrole(String.valueOf(Constants.VR));
        user.setUserid("-1");
        user.setGender("1");
        user.setToken(token);
        user.setNickname("游客");
        user.setCertification("");
        user.setCertTime("");
        user.setIsJoin("0");
        user.setMobile("");
        user.setHXAccountId("");
        user.setHasEmchat("0");
        setUser(user);
        //存储本地
        dao.saveUserOrUpdate(user);
//        aCache.put(Constants.USER_ACACHE_KEY,user);
        SharedPreferenceService.getInstance().put(Constants.TOKEN,token);
        SharedPreferenceService.getInstance().put(USER_ID, Long.parseLong(user.getUserid()));
    }

    /**
     * 判断用户是否拥有此权限
     * @param model 功能模块编号
     * @return true or false
     */
    public boolean hasPower(int model){
        boolean has=false;
        if(getRole()==null){
            return has;
        }
        Power power=getUserPower();
        switch (model){
            case Constants.BODY_GAME:
                has=power.isBodyGame();
                break;
            case Constants.LAI_YUNDONG:
                has=power.isLaiYunDong();
                break;
            case Constants.LAI_CLASS:
                has=power.isLaiClass();
                break;
            case Constants.LAI_CHEN:
                has=power.isLaiChen();
                break;
            case Constants.LAI_SHOP:
                has=power.isLaiShop();
                break;
        }
        return has;
    }

    private Power getUserPower(){
        Power power=null;
        if(user==null||TextUtils.isEmpty(user.getUserrole())){
            Log.i("没有用户角色");
            power=new Power();
            power.setBodyGame(false);
            power.setLaiChen(false);
            power.setLaiYunDong(false);
            power.setLaiClass(false);
            power.setLaiShop(false);
            return power;
        }
        int userRole=Integer.parseInt(user.getUserrole());
        switch (userRole){
            case Constants.VR:
                power=role.getVr();
                break;
            case Constants.NC:
                power=role.getNc();
                break;
            case Constants.INC:
                power=role.getInc();
                break;
            case Constants.PC:
                power=role.getPc();
                break;
            case Constants.SP:
                power=role.getSp();
                break;
            case Constants.SR:
                power=role.getSr();
                break;
        }

        return power;
    }

    public UserModel getUser() {
        if(user==null){
            user=dao.queryUser(String.valueOf(getUserId()));
        }
        return user;
    }

    private void setUser(UserModel user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        SharedPreferenceService.getInstance().put("token", token);
    }

    public Role getRole() {
        return role;
    }

    public boolean isVr() {
        return TextUtils.isEmpty(getToken())&&isVr;
    }
}
