package com.softtek.lai.module.sport.util;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.softtek.lai.module.sport.model.KilometrePace;
import com.softtek.lai.module.sport.model.SportModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import zilla.libcore.db.DBHelper;

/**
 * Created by jerry.guan on 7/28/2016.
 */
public class SportUtil {

    private static SportUtil sportUtil;
    private DBHelper dbHelper;

    private SportUtil(){
        dbHelper=DBHelper.getInstance();
    }

    public static SportUtil getInstance(){
        if(sportUtil==null){
            sportUtil=new SportUtil();
        }
        return sportUtil;
    }

    //add to db
    public void addSport(SportModel model){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("id", UUID.randomUUID().toString().replaceAll("-",""));
        values.put("longitude",model.getLongitude());
        values.put("latitude",model.getLatitude());
        values.put("speed",model.getSpeed());
        values.put("kilometre",model.getIskilometre());
        values.put("time_consuming",model.getConsumingTime());
        values.put("step",model.getStep());
        values.put("currentkm",model.getCurrentKM());
        values.put("hasProblem",model.getHasProblem());
        values.put("index_count",model.getIndex());
        values.put("kilometre_time",model.getKilometreTime());
        values.put("user_id",model.getUser());
        db.insertWithOnConflict("sport_data",null,values,SQLiteDatabase.CONFLICT_NONE);
        db.close();
    }
    //delete all data
    public void deleteSport(){
        SQLiteDatabase db = null;
        try {
            db= dbHelper.getWritableDatabase();
            db.delete("sport_data", null, null);
        }finally {
            if(db!=null) {
                db.close();
            }
        }
    }

    //queryAll
    public List<SportModel> querySport(String userId){
        List<SportModel> sportModels=new ArrayList<>();
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.query("sport_data",null,"user_id=?",new String[]{userId},null,null,null);
        if (cursor.moveToFirst()){
            do {
                String id=cursor.getString(cursor.getColumnIndex("id"));
                String longitude=cursor.getString(cursor.getColumnIndex("longitude"));
                String latitude=cursor.getString(cursor.getColumnIndex("latitude"));
                String speed=cursor.getString(cursor.getColumnIndex("speed"));
                int step=cursor.getInt(cursor.getColumnIndex("step"));
                String user=cursor.getString(cursor.getColumnIndex("user_id"));
                String currentKM=cursor.getString(cursor.getColumnIndex("currentkm"));
                int kilometre=cursor.getInt(cursor.getColumnIndex("kilometre"));
                int hasProblem=cursor.getInt(cursor.getColumnIndex("hasProblem"));
                String index=cursor.getString(cursor.getColumnIndex("index_count"));
                long consuming=cursor.getLong(cursor.getColumnIndex("time_consuming"));
                long kilometreTime=cursor.getLong(cursor.getColumnIndex("kilometre_time"));
                SportModel model=new SportModel();
                model.setId(id);
                model.setUser(user);
                model.setLatitude(Double.parseDouble(latitude));
                model.setLongitude(Double.parseDouble(longitude));
                model.setSpeed(speed);
                model.setKilometreTime(kilometreTime);
                model.setIskilometre(kilometre+"");
                model.setHasProblem(hasProblem+"");
                model.setConsumingTime(consuming);
                model.setCurrentKM(Double.parseDouble(currentKM));
                model.setStep(step);
                model.setIndex(index);

                sportModels.add(model);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return sportModels;
    }

    //query one kilmoetre
    public List<KilometrePace> queryKilmoetre(String userId){
        List<KilometrePace> kilometrePaces=new ArrayList<>();
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String selection="user_id=? and kilometre=?";
        Cursor cursor=db.query("sport_data",null,selection,new String[]{userId,"1"},null,null,null);
        if (cursor.moveToFirst()){
            do {
                String id=cursor.getString(cursor.getColumnIndex("id"));
                String longitude=cursor.getString(cursor.getColumnIndex("longitude"));
                String latitude=cursor.getString(cursor.getColumnIndex("latitude"));
                String speed=cursor.getString(cursor.getColumnIndex("speed"));
                int step=cursor.getInt(cursor.getColumnIndex("step"));
                String user=cursor.getString(cursor.getColumnIndex("user_id"));
                String currentKM=cursor.getString(cursor.getColumnIndex("currentkm"));
                int kilometre=cursor.getInt(cursor.getColumnIndex("kilometre"));
                int hasProblem=cursor.getInt(cursor.getColumnIndex("hasProblem"));
                String index=cursor.getString(cursor.getColumnIndex("index_count"));
                long kilometreTime=cursor.getLong(cursor.getColumnIndex("kilometre_time"));
                long consuming=cursor.getLong(cursor.getColumnIndex("time_consuming"));

                KilometrePace model=new KilometrePace();
                model.setId(id);
                model.setSpeed(speed);
                model.setUser(user);
                model.setStep(step);
                model.setConsumingTime(consuming);
                model.setCurrentKM(Double.parseDouble(currentKM));
                model.setIskilometre(kilometre+"");
                model.setLatitude(Double.parseDouble(latitude));
                model.setLongitude(Double.parseDouble(longitude));
                model.setHasProblem(hasProblem+"");
                model.setKilometreTime(kilometreTime);
                model.setIndex(index);
                kilometrePaces.add(model);
            }while (cursor.moveToNext());
        }
        db.close();
        return kilometrePaces;
    }
}
