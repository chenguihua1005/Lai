package com.softtek.lai.common;

import android.content.Context;
import android.content.res.AssetManager;

import com.github.snowdream.android.util.Log;
import com.google.gson.Gson;
import com.softtek.lai.chat.Constant;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.premission.Power;
import com.softtek.lai.premission.Role;
import com.softtek.lai.utils.ACache;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import zilla.libcore.file.SharedPreferenceService;

/**
 * Created by jerry.guan on 3/31/2016.
 * 用户登录信息
 */
public class UserInfoModel {

    private static UserInfoModel info=null;
    private UserModel user;
    private String token=null;
    private ACache aCache;
    private Role role;


    private UserInfoModel(Context context){
        aCache=ACache.get(context,Constants.USER_ACACHE_DATA_DIR);
        token=SharedPreferenceService.getInstance().get(Constants.TOKEN, "");
        //载入权限数据
        role=loadingRole(context);
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
     * 退出登录
     */
    public void loginOut(){
        //请出用户数据
        setUser(null);
        token=null;
        //清除token
        SharedPreferenceService.getInstance().put("token", "");
        SharedPreferenceService.getInstance().put(Constants.USER, "");
        SharedPreferenceService.getInstance().put(Constants.PDW, "");
        //清除本地用户
        aCache.remove(Constants.USER_ACACHE_KEY);
    }

    /**
     * 存储用户本地缓存对象
     * @return
     */
    public void saveUserCache(UserModel user){
        //存入内存
        setUser(user);
        setToken(user.getToken());
        //存储本地
        aCache.remove(Constants.USER_ACACHE_KEY);
        aCache.put(Constants.USER_ACACHE_KEY,user);
        SharedPreferenceService.getInstance().put(Constants.TOKEN,token);

    }

    /**
     * 游客登录
     */
    public void visitorLogin(){
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
        aCache.put(Constants.USER_ACACHE_KEY,user);
        SharedPreferenceService.getInstance().put(Constants.TOKEN,token);
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
            case Constants.CHAT:
                has=power.isChat();
                break;
            case Constants.LAI_EXCLE:
                has=power.isLaiExcel();
                break;
            case Constants.LAI_SHOP:
                has=power.isLaiShop();
                break;
        }
        return has;
    }

    private Power getUserPower(){
        Power power=null;
        if("".equals(user.getUserrole())||null==user.getUserrole()){
            Log.i("没有用户角色");
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
            user= (UserModel) aCache.getAsObject(Constants.USER_ACACHE_KEY);
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


}
