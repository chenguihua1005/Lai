package com.softtek.lai.module.bodygame3.activity.presenter;

import com.softtek.lai.module.bodygame3.activity.model.FcAuditPostModel;
import com.softtek.lai.module.bodygame3.activity.model.FcUnputPostModel;
import com.softtek.lai.module.bodygame3.activity.net.FuceSevice;

import zilla.libcore.api.ZillaApi;

/**
 * Created by jessica.zhang on 4/19/2017.
 */

public class EnteredManager implements DisplayManager{
    FuceSevice fuceSevice = ZillaApi.NormalRestAdapter.create(FuceSevice.class);


    @Override
    public void doGetData(String acmId) {

    }

    @Override
    public void doSumbit(FcUnputPostModel model) {

    }

}
