package com.softtek.lai.module.bodygame3.activity.presenter;

import com.softtek.lai.module.bodygame3.activity.model.FcUnputPostModel;

/**
 * Created by jessica.zhang on 4/19/2017.
 */

public interface DisplayManager {

    //获取展示数据
    void doGetData(String acmId);

    //提交数据
    void doSumbit(FcUnputPostModel model);


}
