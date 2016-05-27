package com.softtek.lai.stepcount;

import com.softtek.lai.stepcount.model.UserStep;
import com.softtek.lai.utils.DateUtil;

import java.util.List;

import zilla.libcore.db.ZillaDB;

/**
 * Created by jerry.guan on 5/26/2016.
 */
public class StepUtil {

    private static StepUtil util;

    //获取数据库中当前最新步数
    public int getCurrentStep(String accountId){
        String selection="accountId=? and recordTime=?";
        String[] condition={accountId, DateUtil.getInstance("yyyy-MM-dd").getCurrentDate()};
        List<UserStep> userStepList= ZillaDB.getInstance().query(UserStep.class,selection,condition,null);
        int stepCount=0;
        for (UserStep step:userStepList){
            stepCount+=step.getStepCount();
        }
        return stepCount;
    }

    /**
     * 检查旧数据,每次都获取小于等于当前时间的数据
     *
     */
    public List<UserStep> checkOldStep(String accountId){
        String selection="accountId=? and recordTime<=?";
        String orderBy="desc";
        String[] condition={accountId, DateUtil.getInstance("yyyy-MM-dd").getCurrentDate()};
        return ZillaDB.getInstance().query(UserStep.class,selection,condition,null);
    }



    private StepUtil(){}

    public static StepUtil getInstance(){
        if(util==null){
            util=new StepUtil();
        }
        return util;
    }
}
