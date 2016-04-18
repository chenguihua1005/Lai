package com.softtek.lai.module.community.view;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_preview_image)
public class PreviewImageActivity extends BaseActivity implements View.OnClickListener{


    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.iv_email)
    ImageView iv_delete;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;

    @InjectView(R.id.iv_image)
    ImageView iv_image;
    private int position;

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        iv_delete.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.delete));

    }

    @Override
    protected void initDatas() {
        Uri uri=getIntent().getParcelableExtra("uri");
        position=getIntent().getIntExtra("position",0);
        iv_image.setImageURI(uri);
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
