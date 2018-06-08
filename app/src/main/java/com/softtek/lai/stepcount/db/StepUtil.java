package com.softtek.lai.stepcount.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.stepcount.model.UserStep;
import com.softtek.lai.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import zilla.libcore.db.DBHelper;

/**
 * Created by jerry.guan on 5/26/2016.
 *
 */
public class StepUtil {

    private static StepUtil util;
    private DBHelper dbHelper;
    //获取数据库中当天最新步数,取首位相减
    public int getCurrentStep(String accountId){
        String dateStar=DateUtil.weeHours(0);
        String dateEnd=DateUtil.weeHours(1);
        List<UserStep> steps=getCurrentData(accountId,dateStar,dateEnd);
        int stepCount=0;
        if(!steps.isEmpty()){//if have datas
            UserStep stepEnd=steps.get(steps.size()-1);
            stepCount= (int) stepEnd.getStepCount();
        }
        return stepCount;
    }

    /**
     * 获取当天的数据
     * @param accountId
     * @return
     */
    public List<UserStep> getCurrentData(String accountId,String dateStar,String dateEnd){
        String sql="select id,stepCount,recordTime,accountId from user_step where accountId=? and recordTime between ? and ? order by stepCount asc";
        String[] condition={accountId,dateStar,dateEnd};
        SQLiteDatabase db= dbHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery(sql,condition);
        List<UserStep> result=new ArrayList<>();
        try {
            if (cursor.moveToFirst()) {
                do {
                    String id=cursor.getString(cursor.getColumnIndex("id"));
                    long step = cursor.getLong(cursor.getColumnIndex("stepCount"));
                    String recordTime = cursor.getString(cursor.getColumnIndex("recordTime"));
                    String userId = cursor.getString(cursor.getColumnIndex("accountId"));
                    UserStep userStep = new UserStep();
                    userStep.setId(id);
                    userStep.setAccountId(Long.parseLong(userId));
                    userStep.setStepCount(step);
                    userStep.setRecordTime(recordTime);
                    result.add(userStep);
                } while (cursor.moveToNext());
            }
        }finally {
            cursor.close();
            db.close();
        }
        return result;
    }


    /**
     * 删除某用户当天之前的所有数据
     */
    public void deleteOldDate(String currentDate){
        String cause="recordTime<=?";
        String[] hasOldCon={currentDate};
        SQLiteDatabase db= dbHelper.getWritableDatabase();
        Cursor cursor=db.query("user_step",null,cause,hasOldCon,null,null,null);
        try {
            if (cursor.moveToFirst()) {
                int i = db.delete("user_step", cause, hasOldCon);
                if (i == 0) {
                    Log.i("删除失败");
                } else {
                    Log.i("删除成功");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cursor.close();
            db.close();
        }

    }

    /**
     * 删除某用户的所有数据
     */
    public void deleteDateByPersonal(String userId){
        String cause="accountId=?";
        String[] hasOldCon={userId};
        SQLiteDatabase db= dbHelper.getReadableDatabase();
        Cursor cursor=db.query("user_step",null,cause,hasOldCon,null,null,null);
        try {
            if (cursor.moveToFirst()) {
                int i = db.delete("user_step", cause, hasOldCon);
                if (i == 0) {
                    Log.i("删除失败");
                } else {
                    Log.i("删除成功");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(cursor!=null)
                cursor.close();
            if(db!=null)
                db.close();
        }

    }

    /**
     * 保存步数
     */
    public  long saveStep(UserStep step){

        long i = 0;
        SQLiteDatabase db = null;
        try {
            db= dbHelper.getWritableDatabase();
            ContentValues values=new ContentValues();
            values.put("id", UUID.randomUUID().toString().replaceAll("-",""));
            values.put("accountId",step.getAccountId()+"");
            values.put("recordTime",step.getRecordTime());
            values.put("stepCount",step.getStepCount());
            i = db.insertWithOnConflict("user_step", "", values,
                    SQLiteDatabase.CONFLICT_NONE);//主键冲突策略，替换掉以往的数据
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(db!=null){
                db.close();
            }
        }
        return i;
    }

    private StepUtil(){
        dbHelper=DBHelper.getInstance();
    }

    public static StepUtil getInstance(){
        if(util==null) {
            util = new StepUtil();
        }
        return util;
    }
}
