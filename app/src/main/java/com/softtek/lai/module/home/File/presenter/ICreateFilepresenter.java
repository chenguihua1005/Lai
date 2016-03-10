package com.softtek.lai.module.home.File.presenter;

        import com.softtek.lai.module.home.File.model.File;

/**
 * Created by julie.zhu on 3/7/2016.
 */
public interface ICreateFilepresenter {

    //创建用户档案
    void CreateFile(String token,String nickname, String brithday,int height,int weight,int gender);

}
