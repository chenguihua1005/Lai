package com.softtek.lai.module.File.presenter;

import com.softtek.lai.module.File.model.File;

/**
 * Created by julie.zhu on 3/7/2016.
 */
public interface CreateFilepresenter {

    //创建用户档案
    void CreateFile(String token,String appid,String nickname, String brithday,int height,int weight,int gender);

    void createFile(String token,String appid,File file);
}
