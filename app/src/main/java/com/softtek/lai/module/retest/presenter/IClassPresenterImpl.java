package com.softtek.lai.module.retest.presenter;

import com.softtek.lai.module.retest.net.RetestService;

import zilla.libcore.api.ZillaApi;

/**
 * Created by lareina.qiao on 3/18/2016.
 */
public class IClassPresenterImpl implements IClassPresenter {
    private RetestService service;

    public IClassPresenterImpl(){
        service= ZillaApi.NormalRestAdapter.create(RetestService.class);
    }


    @Override
    public void dogetclass() {

    }
}
