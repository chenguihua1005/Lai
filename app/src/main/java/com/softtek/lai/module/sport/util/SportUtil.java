package com.softtek.lai.module.sport.util;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.softtek.lai.module.sport.model.LatLon;
import com.softtek.lai.module.sport.model.SportModel;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import zilla.libcore.db.DBHelper;
import zilla.libcore.util.Util;

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
        values.put("kilometre",model.iskilometre()?1:0);
        values.put("time_consuming",model.getConsumingTime());
        values.put("step",model.getStep());
        values.put("hasProblem",model.isHasProblem()?1:0);
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

    public List<SportModel> querySport(){
        List<SportModel> sportModels=new ArrayList<>();
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.query("sport_data",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                Util.toastMsg("有数据");
                String id=cursor.getString(cursor.getColumnIndex("id"));
                String longitude=cursor.getString(cursor.getColumnIndex("longitude"));
                String latitude=cursor.getString(cursor.getColumnIndex("latitude"));
                String speed=cursor.getString(cursor.getColumnIndex("speed"));
                int kilometre=cursor.getInt(cursor.getColumnIndex("kilometre"));
                long consuming=cursor.getLong(cursor.getColumnIndex("time_consuming"));
                SportModel model=new SportModel();
                model.setId(id);
                model.setLatitude(Double.parseDouble(latitude));
                model.setLongitude(Double.parseDouble(longitude));
                model.setSpeed(speed);
                model.setIskilometre(kilometre==0?false:true);
                model.setConsumingTime(consuming);
                sportModels.add(model);
            }while (cursor.moveToNext());
        }
        return sportModels;
    }
}
