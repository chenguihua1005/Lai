package com.softtek.lai.stepcount.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.stepcount.model.UserStep;
import com.softtek.lai.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

import zilla.libcore.db.DBHelper;
import zilla.libcore.db.ZillaDB;

/**
 * Created by jerry.guan on 5/26/2016.
 */
public class StepUtil {

    private static StepUtil util;
    private DBHelper dbHelper;
    //获取数据库中当前最新步数
    public int getCurrentStep(String accountId){
        String[] condition={accountId, DateUtil.getInstance("yyyy-MM-dd").getCurrentDate()};
        SQLiteDatabase db= dbHelper.getWritableDatabase();
        String sql="select max(stepCount) from user_step where accountId=? and recordTime=? group by recordTime,accountId";
        Cursor cursor=db.rawQuery(sql,condition);
        int stepCount=0;
        try {
            if (cursor.moveToFirst()) {
                stepCount = cursor.getInt(0);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cursor.close();
            db.close();
        }
        return stepCount;
    }

    /**
     * 检查旧数据,每次都获取小于等于当前时间的数据
     *
     */
    public List<UserStep> checkOldStep(String accountId){
        String sql="select max(stepCount),recordTime,accountId from user_step where accountId=? and recordTime<=? group by recordTime,accountId";
        String[] condition={accountId, DateUtil.getInstance("yyyy-MM-dd").getCurrentDate()};
        SQLiteDatabase db= dbHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery(sql,condition);
        List<UserStep> result=new ArrayList<>();
        try {
            if (cursor.moveToFirst()) {
                do {
                    int step = cursor.getInt(0);
                    String recordTime = cursor.getString(1);
                    String userId = cursor.getString(2);
                    UserStep userStep = new UserStep();
                    userStep.setAccountId(Long.parseLong(userId));
                    userStep.setStepCount(step);
                    userStep.setRecordTime(recordTime);
                    result.add(userStep);
                } while (cursor.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cursor.close();
            db.close();
        }
        return result;
    }

    /**
     * 删除旧数据
     */
    public boolean deleteOldDate(String currentDate,String userId){
        String cause="accountId=? and recordTime<?";
        String[] condition={userId,currentDate};
        SQLiteDatabase db= dbHelper.getReadableDatabase();
        int i=db.delete("user_step",cause,condition);
        db.close();
        if(i==0){
            Log.i("删除失败");
        }else{
            Log.i("删除成功");
        }
        return i==0?false:true;
    }

    /**
     * 保存步数
     */
    public  long saveStep(UserStep step){
//        ZillaDB.getInstance().save(step);
        SQLiteDatabase db= dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("accountId",step.getAccountId()+"");
        values.put("recordTime",step.getRecordTime());
        values.put("stepCount",step.getStepCount());
//        db.beginTransaction();
//        long i=db.insert("user_step",null,values);
//        db.execSQL("insert into user_step (accountId,stepCount,recordTime) values(1,40,'2016-05-27')");
//        db.endTransaction();

        long i = db.insertWithOnConflict("user_step", "", values,
                SQLiteDatabase.CONFLICT_NONE);//主键冲突策略，替换掉以往的数据
        db.close();
        if(i!=-1){
            Log.i("步数保存成功");
        }else{
            Log.i("步数保存失败");
        }
        return i;
    }

    //查询当天所有数据
    public void queryAll(){
        SQLiteDatabase db= dbHelper.getReadableDatabase();
        Cursor cursor=db.query("user_step",null,null,null,null,null,null);
        Log.i("查询数据....."+cursor.moveToFirst());
        if(cursor.moveToFirst()){
            Log.i("检索到数据");
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String recordTime = cursor.getString(cursor.getColumnIndex("recordTime"));
                String userId = cursor.getString(cursor.getColumnIndex("accountId"));
                int step=cursor.getInt(cursor.getColumnIndex("stepCount"));
                UserStep userStep = new UserStep();
                userStep.setAccountId(Long.parseLong(userId));
                userStep.setStepCount(step);
                userStep.setId(id);
                userStep.setRecordTime(recordTime);
                Log.i(userStep.toString());
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
    }

    private StepUtil(){
        dbHelper=DBHelper.getInstance();
    }

    public static StepUtil getInstance(){
        if(util==null){
            util=new StepUtil();
        }
        return util;
    }
}
