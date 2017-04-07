package com.softtek.lai.module.laicheng.presenter;

import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;

/**
 * Created by jerry.guan on 4/7/2017.
 */

public class HealthyReportPresenter extends BasePresenter<HealthyReportPresenter.HealthyReportView>{


    public HealthyReportPresenter(HealthyReportView baseView) {
        super(baseView);
    }

    public interface HealthyReportView extends BaseView{

    }
}
