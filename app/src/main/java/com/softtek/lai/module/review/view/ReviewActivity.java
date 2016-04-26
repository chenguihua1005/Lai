package com.softtek.lai.module.review.view;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.counselor.model.ClassInfoModel;
import com.softtek.lai.module.grade.view.GradeHomeActivity;
import com.softtek.lai.module.review.adapter.ReviewAdapter;
import com.softtek.lai.module.review.presenter.ReviewPresenterManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_review)
public class ReviewActivity extends BaseActivity implements View.OnClickListener,ReviewPresenterManager.ReviewManagerCallback{

    private static final int REVIEW_FLAG=0;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.expand_list)
    ListView expand_list;


    @InjectView(R.id.img_mo_message)
    ImageView img_mo_message;

    private ReviewPresenterManager reviewPresenter;
    private ReviewAdapter adapter;
    private List<ClassInfoModel> classInfoModels=new ArrayList<>();

    @Override
    protected void initViews() {
        tv_title.setText(R.string.review);
        ll_left.setOnClickListener(this);
        img_mo_message.setVisibility(View.VISIBLE);
        expand_list.setVisibility(View.GONE);
        expand_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ReviewActivity.this, GradeHomeActivity.class);
                ClassInfoModel classInfo = (ClassInfoModel) expand_list.getAdapter().getItem(position);
                intent.putExtra("classId", classInfo.getClassId());
                intent.putExtra("review", REVIEW_FLAG);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initDatas() {
        reviewPresenter = new ReviewPresenterManager(this);
        //3表示已结束
        reviewPresenter.getClassList("3", expand_list, img_mo_message);
        adapter=new ReviewAdapter(this,classInfoModels);
        expand_list.setAdapter(adapter);
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
    public void getClassList(List<ClassInfoModel> list) {
        classInfoModels.clear();
        classInfoModels.addAll(list);
        if (list.size() > 0) {
            expand_list.setVisibility(View.VISIBLE);
            img_mo_message.setVisibility(View.GONE);
        } else {
            expand_list.setVisibility(View.GONE);
            img_mo_message.setVisibility(View.VISIBLE);
        }
        adapter.notifyDataSetChanged();
    }
}
