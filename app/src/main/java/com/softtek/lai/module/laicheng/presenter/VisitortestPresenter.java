package com.softtek.lai.module.laicheng.presenter;

import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.common.mvp.BaseView1;
import com.softtek.lai.module.laicheng.model.BleMainData;

/**
 * Created by jia.lu on 2017/4/10.
 */

public class VisitortestPresenter extends BasePresenter<VisitortestPresenter.VisitortestView> {

    public VisitortestPresenter (VisitortestPresenter.VisitortestView view){
        super(view);
    }

    public void getLastInfo(){

    }

    public interface VisitortestView extends BaseView {
        void getLastInfoSuccess(BleMainData data);
        void getLastInfoFailed();
    }
}
