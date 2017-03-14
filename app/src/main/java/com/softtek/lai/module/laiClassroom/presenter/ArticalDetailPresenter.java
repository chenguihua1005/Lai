package com.softtek.lai.module.laiClassroom.presenter;

import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.module.laiClassroom.net.CollectService;

/**
 * Created by shelly.xu on 3/14/2017.
 */

public class ArticalDetailPresenter extends BasePresenter<ArticalDetailPresenter.getAddHotAndHistory> {

    CollectService service;

    public ArticalDetailPresenter(getAddHotAndHistory baseView) {
        super(baseView);
    }

    public void UpdateAddHot() {

    }

    public interface getAddHotAndHistory extends BaseView {

    }
}
