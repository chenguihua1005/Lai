package com.softtek.lai.module.lossweightstory.view;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import butterknife.InjectView;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_picture)
public class PictureActivity extends BaseActivity {

    @InjectView(R.id.iv_image)
    PhotoView iv_image;
    PhotoViewAttacher mAttacher;
    private Bitmap bitmap;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                if(bitmap!=null){
                    iv_image.setImageBitmap(bitmap);
                    mAttacher.update();
                }
            }
        }
    };

    @Override
    protected void initViews() {
    }

    @Override
    protected void initDatas() {
        mAttacher = new PhotoViewAttacher(iv_image);
        final String uri=getIntent().getStringExtra("image_uri");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    bitmap=Picasso.with(PictureActivity.this).load(AddressManager.get("photoHost")+uri).placeholder(R.drawable.default_pic).error(R.drawable.default_pic).get();
                    handler.sendEmptyMessage(1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
        //Picasso.with(this).load(AddressManager.get("photoHost")+uri).placeholder(R.drawable.default_pic).error(R.drawable.default_pic).into(iv_image);
    }
}
