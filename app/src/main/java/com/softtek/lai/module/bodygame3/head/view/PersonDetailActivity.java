package com.softtek.lai.module.bodygame3.head.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

import static com.softtek.lai.R.id.img;

@InjectLayout(R.layout.activity_person_detail)
public class PersonDetailActivity extends BaseActivity {
    private int[] mImgIds;
    private LayoutInflater mInflater;
    @InjectView(R.id.gallery)
    LinearLayout gallery;
    @Override
    protected void initViews() {
        for(int i=0;i<mImgIds.length;i++)
        {

            View view=mInflater.inflate(R.layout.activity_index_gallery_item,gallery,false);
            ImageView img = (ImageView)view.findViewById(R.id.img);
            img.setImageResource(mImgIds[i]);
            TextView txt =(TextView)view.findViewById(R.id.tv_te);
            txt.setText("some info ");
            gallery.addView(view);

        }
    }

    @Override
    protected void initDatas() {
        mInflater=LayoutInflater.from(this);
        mImgIds = new int[] { R.drawable.img_default,R.drawable.img_default,R.drawable.img_default,
            R.drawable.img_default,R.drawable.img_default,R.drawable.img_default,R.drawable.img_default,
                R.drawable.img_default,R.drawable.img_default};
    }
}
