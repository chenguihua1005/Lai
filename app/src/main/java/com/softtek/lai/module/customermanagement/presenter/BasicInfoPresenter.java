package com.softtek.lai.module.customermanagement.presenter;

import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.module.customermanagement.model.BasicInfoModel;
import com.softtek.lai.module.customermanagement.service.CustomerService;

import zilla.libcore.api.ZillaApi;

/**
 * Created by jessica.zhang on 12/8/2017.
 */

public class BasicInfoPresenter extends BasePresenter<BasicInfoPresenter.BasicInfoCallBack> {
    private CustomerService service;

    public BasicInfoPresenter(BasicInfoCallBack baseView) {
        super(baseView);
        service = ZillaApi.NormalRestAdapter.create(CustomerService.class);
    }

    public void getCustomerBasicInfo() {



    }

    public interface BasicInfoCallBack extends BaseView {
        void getBasicInfo(BasicInfoModel model);

        void disMissDialog();

    }


}
