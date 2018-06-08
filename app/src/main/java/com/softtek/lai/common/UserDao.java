package com.softtek.lai.common;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.text.TextUtils;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.login.model.UserModel;

import zilla.libcore.db.DBHelper;

/**
 * Created by jerry.guan on 3/2/2017.
 */

public class UserDao {

    private static UserDao dao;
    private DBHelper dbHelper;

    private UserDao(){
        dbHelper=DBHelper.getInstance();
    }

    public static UserDao getInstance(){
        if(dao==null) {
            dao = new UserDao();
        }
        return dao;
    }

    public void saveUserOrUpdate(UserModel user){
        String sql="replace into user_info(userId,userRole,roleName,nickName,gender,weight,height,photo,certification,certTime," +
                "mobile,birthday,isJoin,todayStepCnt,isCreatInfo,HXAccountId,HasEmchat,HasThClass,doingClass) " +
                "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String[] values={user.getUserid(),user.getUserrole(),user.getRoleName(),user.getNickname(),user.getGender(),user.getWeight(),user.getHight(),
        user.getPhoto(),user.getCertification(),user.getCertTime(),user.getMobile(),user.getBirthday(),user.getIsJoin(),user.getTodayStepCnt(),
        user.getIsCreatInfo(),user.getHXAccountId(),user.getHasEmchat(),String.valueOf(user.getHasThClass()),String.valueOf(user.getDoingClass())};
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        SQLiteStatement statement=db.compileStatement(sql);
        statement.bindAllArgsAsStrings(values);
        long i=statement.executeInsert();
        if(i!=-1){
            Log.i(user.toString());
        }
        db.close();
    }


    public UserModel queryUser(String userId){
        UserModel user;
        if(userId.equals("-1")){
            user=new UserModel();
            user.setUserrole(String.valueOf(Constants.VR));
            user.setUserid("-1");
            user.setGender("1");
            user.setToken("");
            user.setNickname("游客");
            user.setCertification("");
            user.setCertTime("");
            user.setIsJoin("0");
            user.setMobile("");
            user.setHXAccountId("");
            user.setHasEmchat("0");
            return user;
        }
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.query("user_info",null,"userId=?",new String[]{userId},null,null,null);
        try {
            if(cursor!=null&&cursor.moveToFirst()){
                String accountId=cursor.getString(cursor.getColumnIndex("userId"));
                String userRole=cursor.getString(cursor.getColumnIndex("userRole"));
                String roleName=cursor.getString(cursor.getColumnIndex("roleName"));
                String nickName=cursor.getString(cursor.getColumnIndex("nickName"));
                String gender=cursor.getString(cursor.getColumnIndex("gender"));
                String weight=cursor.getString(cursor.getColumnIndex("weight"));
                String height=cursor.getString(cursor.getColumnIndex("height"));
                String photo=cursor.getString(cursor.getColumnIndex("photo"));
                String certification=cursor.getString(cursor.getColumnIndex("certification"));
                String certTime=cursor.getString(cursor.getColumnIndex("certTime"));
                String mobile=cursor.getString(cursor.getColumnIndex("mobile"));
                String birthday=cursor.getString(cursor.getColumnIndex("birthday"));
                String isJoin=cursor.getString(cursor.getColumnIndex("isJoin"));
                String todayStepCnt=cursor.getString(cursor.getColumnIndex("todayStepCnt"));
                String isCreatInfo=cursor.getString(cursor.getColumnIndex("isCreatInfo"));
                String HXAccountId=cursor.getString(cursor.getColumnIndex("HXAccountId"));
                String HasEmchat=cursor.getString(cursor.getColumnIndex("HasEmchat"));
                String HasThClass=cursor.getString(cursor.getColumnIndex("HasThClass"));
                String doingClass=cursor.getString(cursor.getColumnIndex("doingClass"));
//                String exit=cursor.getString(cursor.getColumnIndex("exit"));
                user=new UserModel();
                user.setUserid(accountId);
                user.setUserrole(userRole);
                user.setRoleName(roleName);
                user.setNickname(nickName);
                user.setGender(gender);
                user.setWeight(weight);
                user.setHight(height);
                user.setPhoto(photo);
                user.setCertification(certification);
                user.setCertTime(certTime);
                user.setMobile(mobile);
                user.setBirthday(birthday);
                user.setIsJoin(isJoin);
                user.setTodayStepCnt(todayStepCnt);
                user.setIsCreatInfo(isCreatInfo);
                user.setHXAccountId(HXAccountId);
                user.setHasEmchat(HasEmchat);
                user.setHasThClass(TextUtils.isEmpty(HasThClass)?0:Integer.parseInt(HasThClass));
                user.setDoingClass(TextUtils.isEmpty(doingClass)?0:Integer.parseInt(doingClass));
//                user.setExit(TextUtils.isEmpty(exit)?false:Integer.parseInt(exit)==1);
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(cursor!=null&&!cursor.isClosed()){
                cursor.close();
            }
        }
        return null;
    }

}
