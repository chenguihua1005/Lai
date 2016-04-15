package com.softtek.lai.module.confirmInfo.presenter;

import com.softtek.lai.module.confirmInfo.model.ConinfoModel;

/**
 * Created by zcy on 2016/4/13.
 */
public interface IUpConfirmInfopresenter {
    //2.16.3修改参赛数据
    void changeUpConfirmInfo(String token,ConinfoModel coninfoModel);
}
