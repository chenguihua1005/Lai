package com.softtek.lai.module.bodygamest.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygamest.Adapter.SelectPhotoAdapter;
import com.softtek.lai.module.bodygamest.model.DownPhotoModel;
import com.softtek.lai.module.bodygamest.model.LogListModel;
import com.softtek.lai.module.bodygamest.present.PhotoListIml;
import com.softtek.lai.module.bodygamest.present.PhotoListPre;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_photo_detail)
public class PhotoDetailActivity extends BaseActivity implements View.OnClickListener {
    //toolbar标题栏
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.img)
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_left.setOnClickListener(this);
    }

    @Override
    protected void initViews() {

        tv_title.setText("照片详情");
    }

    @Override
    protected void initDatas() {
        Intent intent=getIntent();
        String img_url=intent.getStringExtra("photo");
        if (!TextUtils.isEmpty(img_url)) {
            Picasso.with(this).load(img_url).placeholder(R.drawable.lufei).error(R.drawable.lufei).into(img);
        } else {
            Picasso.with(this).load("www").placeholder(R.drawable.lufei).error(R.drawable.lufei).into(img);
        }
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
