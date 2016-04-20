package com.softtek.lai.module.bodygamest.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygamest.Adapter.DownPhotoAdapter;
import com.softtek.lai.module.bodygamest.Adapter.SelectPhotoAdapter;
import com.softtek.lai.module.bodygamest.eventModel.PhotoListEvent;
import com.softtek.lai.module.bodygamest.model.DownPhotoModel;
import com.softtek.lai.module.bodygamest.present.PhotoListIml;
import com.softtek.lai.module.bodygamest.present.PhotoListPre;
import com.softtek.lai.module.newmemberentry.view.GetPhotoDialog;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_select_photo)
public class SelectPhotoActivity extends BaseActivity implements View.OnClickListener {
    //toolbar标题栏
    @InjectView(R.id.tv_left)
    TextView tv_left;
    //照片列表listview
    @InjectView(R.id.pull_refresh_grid)
    PullToRefreshGridView pull_refresh_grid;
    private PhotoListPre photoListPre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(PhotoListEvent photoListEvent) {
        System.out.println(photoListEvent);
        SelectPhotoAdapter adapter=new SelectPhotoAdapter(this,photoListEvent.getDownPhotoModels());
        pull_refresh_grid.setAdapter(adapter);
    }

    @Override
    protected void initViews() {



    }

    @Override
    protected void initDatas() {
        photoListPre = new PhotoListIml();
        String id= UserInfoModel.getInstance().getUser().getUserid();
        photoListPre.getUploadPhoto(id, "0");
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tv_left:
                finish();
                break;

        }



    }

}
