package com.softtek.lai.module.bodygamest.view;

import android.widget.ListView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygamest.Adapter.HistoryClassAdapter;
import com.softtek.lai.module.bodygamest.model.HistoryClassModel;
import com.softtek.lai.module.bodygamest.present.IStudentPresenter;
import com.softtek.lai.module.bodygamest.present.ScoreManager;
import com.softtek.lai.module.bodygamest.present.StudentImpl;
import com.softtek.lai.module.login.model.UserModel;

import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jarvis on 4/27/2016.
 */
@InjectLayout(R.layout.activity_history_class)
public class OldScoresFragment extends BaseFragment implements ScoreManager.GetClassListCallBack {
    @InjectView(R.id.list_student_score)
    ListView list_student_score;
    private ScoreManager manager;
    private UserModel userModel;

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        manager = new ScoreManager(this);
        userModel = UserInfoModel.getInstance().getUser();
        String id = userModel.getUserid();
        manager.getHistoryClassList(id);
    }

    @Override
    public void getclassList(String type, List<HistoryClassModel> list) {
        if ("true".equals(type)) {
            HistoryClassAdapter adapter = new HistoryClassAdapter(getContext(), list);
            list_student_score.setAdapter(adapter);
        }
    }
}
