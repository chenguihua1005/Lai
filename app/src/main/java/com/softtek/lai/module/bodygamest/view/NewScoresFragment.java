package com.softtek.lai.module.bodygamest.view;

import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygamest.present.IStudentPresenter;
import com.softtek.lai.module.bodygamest.present.StudentImpl;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.utils.ACache;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jarvis on 4/27/2016.
 */
@InjectLayout(R.layout.activity_student_score)
public class NewScoresFragment extends BaseFragment {
    @InjectView(R.id.list_student_score)
    ListView list_student_score;
    private IStudentPresenter studentPresenter;
    private UserModel userModel;

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        studentPresenter = new StudentImpl(getContext());
        userModel = UserInfoModel.getInstance().getUser();
        String id = userModel.getUserid();
        studentPresenter.getTranscrip(id, list_student_score);
    }
}
