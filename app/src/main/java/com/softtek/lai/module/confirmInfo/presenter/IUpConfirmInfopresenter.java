package com.softtek.lai.module.confirmInfo.presenter;

import com.softtek.lai.module.confirmInfo.model.ConinfoModel;

/**
 * Created by zcy on 2016/4/13.
 */
public interface IUpConfirmInfopresenter {
    //获取参赛确认信息
    void getConfirmInfo(long accountid,long classid);

    //修改参赛数据
    void changeUpConfirmInfo(String token,ConinfoModel coninfoModel);
}
