package com.softtek.lai.module.bodygame3.home.view;


import android.widget.FrameLayout;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.activity.view.ClassedFragment;
import com.softtek.lai.module.bodygame3.activity.view.NoClassFragment;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_activity2)
public class ActivityFragment extends LazyBaseFragment {

    @InjectView(R.id.contain_act)
    FrameLayout contain_act;
    public ActivityFragment() {
        // Required empty public constructor
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initViews() {
        if(UserInfoModel.getInstance().getUser().getHasThClass()==0){
            getChildFragmentManager().beginTransaction().replace(R.id.contain_act,new NoClassFragment()).commit();
        }else {
            getChildFragmentManager().beginTransaction().replace(R.id.contain_act,new ClassedFragment()).commit();
        }

    }

    @Override
    protected void initDatas() {


    }

}
