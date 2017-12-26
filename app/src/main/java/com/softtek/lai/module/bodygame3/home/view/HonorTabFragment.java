package com.softtek.lai.module.bodygame3.home.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ggx.widgets.nicespinner.ListDialog;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.module.bodygame3.head.adapter.HonorFragmentAdapter;
import com.softtek.lai.module.bodygame3.head.model.HonorFragmentModel;
import com.softtek.lai.module.bodygame3.home.HonorFragment;
import com.softtek.lai.widgets.MySwipRefreshView;
import com.softtek.lai.widgets.NoSlidingViewPage;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jessica.zhang on 12/26/2017.
 */

@InjectLayout(R.layout.fragment_honor_roll_tab)
public class HonorTabFragment extends LazyBaseFragment {
    @InjectView(R.id.pull)
    MySwipRefreshView pull;

    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    @InjectView(R.id.iv_right)
    ImageView iv_right;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.spinner_title_honor)
    ListDialog tv_title;

    @InjectView(R.id.tv_right)
    TextView tv_right;

    @InjectView(R.id.tab)
    TabLayout tab;
    @InjectView(R.id.tab_content)
    NoSlidingViewPage tab_content;
    List<HonorFragmentModel> fragmentList = new ArrayList<>();

    private String classId = "";

    public HonorTabFragment() {

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initViews() {
        tv_right.setText(R.string.rule);
        int selector = 0;

        String[] tabtitle = {"体重周榜", "体重月榜", "体重总榜", "体脂周榜", "体脂月榜", "体脂总榜"};
        String[] ByWhichRatio = {"ByWeightRatio", "ByFatRatio"};
        String[] SortTimeType = {"ByWeek", "ByMonth", "ByTotal"};

        for (int i = 0; i < 6; i++) {
            Bundle bundle = new Bundle();
            bundle.putString("classId", classId);
            if (i >= 0 && i < 3) {
                bundle.putString("ByWhichRatio", ByWhichRatio[0]);
            } else {
                bundle.putString("ByWhichRatio", ByWhichRatio[1]);
            }

            if (i == 0 || i == 3) {
                bundle.putString("SortTimeType", SortTimeType[0]);
            } else if (i == 1 || i == 4) {
                bundle.putString("SortTimeType", SortTimeType[1]);
            } else {
                bundle.putString("SortTimeType", SortTimeType[2]);
            }

            fragmentList.add(new HonorFragmentModel(tabtitle[i], HonorFragment.newInstance(bundle)));
        }

        tab_content.setAdapter(new HonorFragmentAdapter(getChildFragmentManager(), fragmentList));
        tab.setupWithViewPager(tab_content);
        tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        tab_content.setOffscreenPageLimit(4);
        tab_content.setCurrentItem(selector, false);

    }

    @Override
    protected void initDatas() {

    }
}
