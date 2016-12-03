package com.softtek.lai.module.bodygame3.head.view;

import android.os.Bundle;

import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.module.ranking.model.RankModel;
import com.softtek.lai.module.ranking.persenter.RankManager;

/**
 * Created by lareina.qiao on 11/25/2016.
 */
public class TotalHonorFragment extends LazyBaseFragment implements RankManager.RankManagerCallback  {

    public static TotalHonorFragment getInstance(){
        TotalHonorFragment fragment=new TotalHonorFragment();
        Bundle data=new Bundle();
        fragment.setArguments(data);
        return fragment;
    }
    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void getResult(RankModel result) {

    }
}
