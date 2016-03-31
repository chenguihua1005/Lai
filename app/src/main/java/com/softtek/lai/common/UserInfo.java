package com.softtek.lai.common;

import android.content.Context;

import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.login.model.User;
import com.softtek.lai.utils.ACache;

import zilla.libcore.file.SharedPreferenceService;

/**
 * Created by jerry.guan on 3/31/2016.
 * 用户登录信息
 */
public class UserInfo {

    private static UserInfo info=null;
    private User user;
    private String token=null;
    private ACache aCache;


    private UserInfo(Context context){
        aCache=ACache.get(context,Constants.USER_ACACHE_DATA_DIR);
        token=SharedPreferenceService.getInstance().get(Constants.TOKEN,"");
    }

    public static UserInfo getInstance(Context context){
        if(info==null){
            info=new UserInfo(context);
        }
        return info;
    }

    public static UserInfo getInstance(){
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
        SharedPreferenceService.getInstance().put("token","");
        //清除本地用户
        aCache.put(Constants.USER_ACACHE_KEY,new User());
    }

    /**
     * 存储用户本地缓存对象
     * @return
     */
    public void saveUserCache(User user){
        //存入内存
        setUser(user);
        setToken(user.getToken());
        //存储本地
        aCache.put(Constants.USER_ACACHE_KEY,user);
        SharedPreferenceService.getInstance().put(Constants.TOKEN,token);

    }

    public void visitorLogin(){
        setToken("");
        user=new User();
        user.setUserrole(String.valueOf(Constants.VR));
        user.setToken(token);
        user.setNickname("游客");
        setUser(user);
        //存储本地
        aCache.put(Constants.USER_ACACHE_KEY,user);
        SharedPreferenceService.getInstance().put(Constants.TOKEN,token);
    }

    public User getUser() {
        return user;
    }

    private void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    private void setToken(String token) {
        this.token = token;
    }
}
