package com.softtek.lai.module.laiClassroom.presenter;

import com.softtek.lai.common.mvp.BasePersent;
import com.softtek.lai.common.mvp.BaseView;

/**
 * Created by jerry.guan on 3/8/2017.
 */

public class WholePresenter extends BasePersent<WholePresenter.WholeView>{


    public WholePresenter(WholeView baseView) {
        super(baseView);
    }



    public interface WholeView extends BaseView{

    }
}
