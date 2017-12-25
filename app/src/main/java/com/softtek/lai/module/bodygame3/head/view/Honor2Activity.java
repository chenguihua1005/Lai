package com.softtek.lai.module.bodygame3.head.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.healthyreport.model.FragmentModel;
import com.softtek.lai.widgets.NoSlidingViewPage;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jessica.zhang on 12/25/2017.
 */

@InjectLayout(R.layout.activity_honor)
public class Honor2Activity extends BaseActivity implements View.OnClickListener {
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
    List<FragmentModel> fragmentList = new ArrayList<>();


    /*    WhichTime:

    *
     */
//    getPresenter().getHonorData(UID, ClassId, ByWhichRatio, SortTimeType, WhichTime, is_first);
    @Override
    public void onClick(View v) {

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

        String[] tabtitle = {"体重周榜", "体重月榜", "体重总榜", "体脂周榜", "体脂月榜", "体脂总榜"};
        String[] ByWhichRatio = {"ByWeightRatio", "ByFatRatio"};
        String[] SortTimeType = {"ByWeek", "ByMonth", "ByTotal"};
//    getPresenter().getHonorData(UID, ClassId, ByWhichRatio, SortTimeType, WhichTime, is_first);
        for (int i = 0; i < 6; i++) {
            Bundle bundle = new Bundle();
            bundle.putString("title", tabtitle[i]);
            bundle.putString("classId", classId);
            if (i >= 0 && i < 3) {
                bundle.putString("ByWhichRatio", ByWhichRatio[0]);
            } else {
                bundle.putString("ByWhichRatio", ByWhichRatio[1]);
            }

            if (i >= 0 && i <= 1) {
                bundle.putString("SortTimeType", SortTimeType[0]);
            } else if (i >= 2 && i <= 3) {
                bundle.putString("SortTimeType", SortTimeType[1]);
            } else {
                bundle.putString("SortTimeType", SortTimeType[2]);
            }
        }

    }

    @Override
    protected void initDatas() {

    }
}
