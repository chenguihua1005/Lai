package com.softtek.lai.module.laiClassroom;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.FrameLayout;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.laiClassroom.adapter.TabAdapter;
import com.softtek.lai.module.laiClassroom.model.FragmentModel;
import com.softtek.lai.module.laicheng.MainBaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_classroom)
public class ClassroomActivity extends BaseActivity {

    @InjectView(R.id.tab)
    TabLayout tab;
    @InjectView(R.id.content)
    ViewPager content;
    @InjectView(R.id.fl_right)
    FrameLayout mShareView;

    private List<FragmentModel> fragmentModels;

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        fragmentModels = new ArrayList<>();
        fragmentModels.add(new FragmentModel("全部", new WholeFragment()));
        fragmentModels.add(new FragmentModel("收藏", new CollectionFragment()));
        fragmentModels.add(new FragmentModel("历史", new HistoryFragment()));
        fragmentModels.add(new FragmentModel("专题", new SubjectFragment()));
        content.setOffscreenPageLimit(3);
        content.setAdapter(new TabAdapter(getSupportFragmentManager(), fragmentModels));
        tab.setupWithViewPager(content);
    }

    @OnClick(R.id.fl_left)
    public void doBack() {
        finish();
    }

    @OnClick(R.id.fl_right)
    public void doSearch() {
        Intent intent = new Intent(this, KeywordsSearchActivity.class);
        //Intent intent = new Intent(this, MainBaseActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}
