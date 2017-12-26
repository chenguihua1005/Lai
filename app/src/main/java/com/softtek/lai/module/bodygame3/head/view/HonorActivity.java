package com.softtek.lai.module.bodygame3.head.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.bodygame3.head.adapter.HonorFragmentAdapter;
import com.softtek.lai.module.bodygame3.head.model.HonorFragmentModel;
import com.softtek.lai.module.bodygame3.home.HonorFragment;
import com.softtek.lai.widgets.NoSlidingViewPage;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jessica.zhang on 12/25/2017.
 */

@InjectLayout(R.layout.activity_honor)
public class HonorActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.tv_right)
    TextView tv_right;

    @InjectView(R.id.tab)
    TabLayout tab;
    @InjectView(R.id.tab_content)
    NoSlidingViewPage tab_content;
    List<HonorFragmentModel> fragmentList = new ArrayList<>();



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.tv_right:
                startActivity(new Intent(this, HonorRuleActivity.class));
                break;
        }
    }

    @Override
    protected void initViews() {
        Intent intent = getIntent();
        String classId = intent.getStringExtra("classId");
        boolean isPast = intent.getBooleanExtra("isPast", false);
        if (isPast) {
            tv_title.setText(R.string.past_ranking);
        } else {
            tv_title.setText(R.string.honorRank);
        }

        tv_right.setText(R.string.rule);

        int selector = 0;

        String[] tabtitle = {"体重周榜", "体重月榜", "体重总榜", "体脂周榜", "体脂月榜", "体脂总榜"};
        String[] ByWhichRatio = {"ByWeightRatio", "ByFatRatio"};
        String[] SortTimeType = {"ByWeek", "ByMonth", "ByTotal"};
//    getPresenter().getHonorData(UID, ClassId, ByWhichRatio, SortTimeType, WhichTime, is_first);

        for (int i = 0; i < 6; i++) {
            Bundle bundle = new Bundle();
//            bundle.putString("title", tabtitle[i]);
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

        tab_content.setAdapter(new HonorFragmentAdapter(getSupportFragmentManager(), fragmentList));
        tab.setupWithViewPager(tab_content);
        tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        tab_content.setOffscreenPageLimit(4);
        tab_content.setCurrentItem(selector, false);


        ll_left.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {

    }

    public static void startHonorActivity(Context context, String classId) {
        Intent intent = new Intent(context, HonorActivity.class);
        intent.putExtra("classId", classId);
        context.startActivity(intent);
    }

    public static void startHonorActivity2(Context context, String classId, boolean isPast) {
        Intent intent = new Intent(context, HonorActivity.class);
        intent.putExtra("classId", classId);
        intent.putExtra("isPast", isPast);
        context.startActivity(intent);
    }
}
