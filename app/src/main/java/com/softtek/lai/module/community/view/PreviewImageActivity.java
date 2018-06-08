package com.softtek.lai.module.community.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.utils.DisplayUtil;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;
import uk.co.senab.photoview.PhotoView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_preview_image)
public class PreviewImageActivity extends BaseActivity implements View.OnClickListener{


    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;

    @InjectView(R.id.iv_image)
    PhotoView iv_image;
    private int position;


    @Override
    protected void initViews() {
        tintManager.setStatusBarTintResource(android.R.color.transparent);
        ll_left.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        if(DisplayUtil.getSDKInt()>=Build.VERSION_CODES.KITKAT){//大于等于API19
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    protected void initDatas() {
        Uri uri=getIntent().getParcelableExtra("uri");
        position=getIntent().getIntExtra("position",0);
        Picasso.with(this).load(uri).resize(DisplayUtil.getMobileWidth(this),
                DisplayUtil.getMobileHeight(this)+DisplayUtil.getStatusHeight(this)).centerInside()
                .placeholder(R.drawable.default_icon_rect).error(R.drawable.default_icon_rect)
                .into(iv_image);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
            case R.id.fl_right:
                Intent intent=getIntent();
                intent.putExtra("position", position);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
