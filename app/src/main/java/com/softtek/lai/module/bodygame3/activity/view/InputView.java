package com.softtek.lai.module.bodygame3.activity.view;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.bodygame3.activity.model.TodayactModel;
import com.squareup.picasso.Picasso;

import zilla.libcore.file.AddressManager;

/**
 * Created by shelly.xu on 12/17/2016.
 */

public class InputView extends LinearLayout {
    private int role;
    private TodayactModel todayactModel;
    private Fragment fragment;
    private TextView activity_name;
    private TextView activity_time;
    private LinearLayout onclick_lin;
    private ImageView activityicon;
    private String activityid;
    private int counts;

    public InputView(Fragment fragment, TodayactModel todayactModel, int counts, String classid, int classrole) {
        super(fragment.getContext());
        this.fragment = fragment;
        this.todayactModel = todayactModel;
        this.role = classrole;
        this.counts = counts;

        initView();
        initData();
    }

    private void initView() {
        inflate(getContext(), R.layout.activity_list, this);
        activity_name = (TextView) findViewById(R.id.activity_name);
        activity_time = (TextView) findViewById(R.id.activity_time);
        activityicon = (ImageView) findViewById(R.id.activityicon);
        onclick_lin = (LinearLayout) findViewById(R.id.onclick_lin);
    }

    private void initData() {

        activity_name.setText(todayactModel.getActivityName() + "(" + todayactModel.getCount() + ")");
        activity_time.setText("集合时间" + todayactModel.getActivityStartDate());
        Picasso.with(getContext()).load(AddressManager.get("photoHost") + todayactModel.getActivityIcon())
                .into(activityicon);
        activityid = todayactModel.getActivityId();
        onclick_lin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ActivitydetailActivity.class);
                intent.putExtra("activityId", activityid);
                intent.putExtra("classrole", role);
                intent.putExtra("counts", counts);
                fragment.startActivityForResult(intent, 110);
            }
        });
    }

}
