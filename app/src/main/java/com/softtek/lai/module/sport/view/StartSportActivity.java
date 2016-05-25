package com.softtek.lai.module.sport.view;

import android.content.Intent;
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

@InjectLayout(R.layout.activity_start_sport)
public class StartSportActivity extends BaseActivity implements View.OnClickListener, BaseFragment.OnFragmentInteractionListener{
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.text_total_distance)
    TextView text_total_distance;


    SportManager sportManager;

    HistorySportAdapter adapter;
    private List<HistorySportModel> list = new ArrayList<HistorySportModel>();

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        text_total_distance.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        tv_title.setText("运动");
        sportManager.getMovementList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.text_total_distance:
                startActivity(new Intent(StartSportActivity.this,HistorySportListActivity.class));
                break;
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
