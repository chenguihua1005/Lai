package com.softtek.lai.module.review.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.counselor.model.ClassInfoModel;
import com.softtek.lai.module.grade.view.GradeHomeActivity;
import com.softtek.lai.module.review.presenter.IReviewPresenter;
import com.softtek.lai.module.review.presenter.ReviewPresenterImpl;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_review)
public class ReviewActivity extends BaseActivity implements View.OnClickListener{

    private static final int REVIEW_FLAG=0;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.expand_list)
    ListView expand_list;


    @InjectView(R.id.img_mo_message)
    ImageView img_mo_message;


    private IReviewPresenter reviewPresenter=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_left.setOnClickListener(this);
        expand_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ReviewActivity.this, GradeHomeActivity.class);
                ClassInfoModel classInfo = (ClassInfoModel) expand_list.getAdapter().getItem(position);
                intent.putExtra("classId", classInfo.getClassId());
                intent.putExtra("review",REVIEW_FLAG);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initViews() {
        tv_title.setText(R.string.review);

    }

    @Override
    protected void initDatas() {
        reviewPresenter = new ReviewPresenterImpl(this);
        reviewPresenter.getClassList(expand_list, img_mo_message);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
        }
    }



}
