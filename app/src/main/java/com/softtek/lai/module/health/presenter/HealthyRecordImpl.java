package com.softtek.lai.module.health.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.health.net.HealthServeice;

import zilla.libcore.api.ZillaApi;

/**
 * Created by jerry.guan on 4/12/2016.
 */
public class HealthyRecordImpl {

    private HealthServeice serveice;
    private HealthyRecordCallback cb;

    public HealthyRecordImpl(HealthyRecordCallback cb) {
        this.cb=cb;
        serveice= ZillaApi.NormalRestAdapter.create(HealthServeice.class);
    }




    public interface HealthyRecordCallback{


    }
}
