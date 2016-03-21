package com.softtek.lai.module.grade.presenter;

import com.softtek.lai.module.grade.net.GradeService;

import zilla.libcore.api.ZillaApi;

/**
 * Created by jerry.guan on 3/21/2016.
 *
 */
public class GradeImpl implements IGrade{

    private GradeService service;

    public GradeImpl(){
        service= ZillaApi.NormalRestAdapter.create(GradeService.class);
    }

    @Override
    public void getGradeInfos() {

    }

    @Override
    public void sendDynamic() {

    }
}
