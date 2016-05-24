package com.softtek.lai.module.sport.view;

import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.sport.adapter.HistorySportAdapter;
import com.softtek.lai.module.sport.model.HistorySportModel;
import com.softtek.lai.module.sport.presenter.SportManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_history_sport_list)
public class StartSportActivity extends BaseActivity implements View.OnClickListener, BaseFragment.OnFragmentInteractionListener, SportManager.GetMovementListCallBack {
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.sport_list)
    ListView sport_list;

    SportManager sportManager;

    HistorySportAdapter adapter;
    private List<HistorySportModel> list = new ArrayList<HistorySportModel>();

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        sport_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @Override
    protected void initDatas() {
        tv_title.setText("我的运动");
        sportManager.getMovementList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void getMovementList(String type, List<HistorySportModel> list) {
        adapter = new HistorySportAdapter(this, list);
        sport_list.setAdapter(adapter);
    }
}
