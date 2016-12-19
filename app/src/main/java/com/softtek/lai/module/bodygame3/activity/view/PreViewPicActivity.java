package com.softtek.lai.module.bodygame3.activity.view;

import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.utils.DisplayUtil;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by lareina.qiao on 12/19/2016.
 */
@InjectLayout(R.layout.activity_preview_layout)
public class PreViewPicActivity extends BaseActivity{
    @InjectView(R.id.im_show_pic)
    ImageView im_show_pic;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    String images;
    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        images=getIntent().getStringExtra("images");
        Picasso.with(this).load(new File(images))
                .resize(DisplayUtil.getMobileWidth(this),
                        DisplayUtil.getMobileHeight(this)+DisplayUtil.getStatusHeight(this)).centerInside()
                .placeholder(R.drawable.default_icon_square).error(R.drawable.default_icon_square).into(im_show_pic);

    }
}
