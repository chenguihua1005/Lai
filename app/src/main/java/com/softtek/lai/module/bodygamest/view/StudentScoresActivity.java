package com.softtek.lai.module.bodygamest.view;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.act.adapter.ActFragmentAdapter;
import com.softtek.lai.module.act.view.ActDetailsFragment;
import com.softtek.lai.module.act.view.ActFragment;
import com.softtek.lai.module.bodygamest.Adapter.ScoresFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_student_scores)
public class StudentScoresActivity extends BaseActivity implements View.OnClickListener, BaseFragment.OnFragmentInteractionListener {
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.tab)
    TabLayout tab;
    @InjectView(R.id.tab_content)
    ViewPager tab_content;

    private List<Fragment> fragments = new ArrayList<>();
    private ScoresFragmentAdapter adapter;

    private NewScoresFragment newScoresFragment;
    private ActFragment actFragment;

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        tv_title.setText("成绩单");
        actFragment = new ActFragment();
        newScoresFragment = new NewScoresFragment();
        fragments.add(newScoresFragment);
        fragments.add(actFragment);
        adapter = new ScoresFragmentAdapter(getSupportFragmentManager(), fragments);
        tab_content.setAdapter(adapter);
        tab.setupWithViewPager(tab_content);
        tab.setTabMode(TabLayout.MODE_FIXED);
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
}
