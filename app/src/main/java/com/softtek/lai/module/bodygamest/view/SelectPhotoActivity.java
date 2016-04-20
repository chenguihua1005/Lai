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
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygamest.Adapter.DownPhotoAdapter;
import com.softtek.lai.module.bodygamest.Adapter.SelectPhotoAdapter;
import com.softtek.lai.module.bodygamest.eventModel.PhotoListEvent;
import com.softtek.lai.module.bodygamest.model.DownPhotoModel;
import com.softtek.lai.module.bodygamest.model.LogListModel;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_select_photo)
public class SelectPhotoActivity extends BaseActivity implements View.OnClickListener ,PhotoListIml.PhotoListCallback{
    //toolbar标题栏
    @InjectView(R.id.text_count)
    TextView text_count;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    //照片列表listview
    @InjectView(R.id.pull_refresh_grid)
    PullToRefreshGridView pull_refresh_grid;
    private PhotoListPre photoListPre;
    private List<LogListModel> list_all;
    private List<LogListModel> list_info;
    public static int count=0;
    private int pageIndex = 1;
    private String id;
    private SelectPhotoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
        ll_left.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        pull_refresh_grid.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                pageIndex = 1;
                photoListPre.getUploadPhoto(id, pageIndex + "");
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                pageIndex++;
                photoListPre.getUploadPhoto(id, pageIndex + "");
            }
        });
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(DownPhotoModel downPhotoModel) {
        System.out.println(downPhotoModel);
        list_info = downPhotoModel.getLogList();
        if (list_info != null) {
            if (pageIndex == 1) {
                count=0;
                list_all = list_info;
                adapter = new SelectPhotoAdapter(this, list_info, new SelectPhotoAdapter.CallBack() {
                    @Override
                    public void getResult(boolean is_select) {
                        if (is_select) {
                            count++;
                        } else {
                            count--;
                        }
                        text_count.setText(count + "");
                    }
                });
                pull_refresh_grid.setAdapter(adapter);
            } else {
                list_all.addAll(list_info);
                adapter.notifyDataSetChanged();
            }
        }
        pull_refresh_grid.onRefreshComplete();
    }

    @Override
    protected void initViews() {
        tv_right.setText("提交");

    }

    @Override
    protected void initDatas() {
        photoListPre = new PhotoListIml(this);
        id = UserInfoModel.getInstance().getUser().getUserid();
        photoListPre.getUploadPhoto(id, pageIndex + "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.fl_right:


//                System.out.println("str:" + str);
//                Intent intent = new Intent();
//                //把返回数据存入Intent
//                intent.putExtra("result", str);
//                setResult(RESULT_OK, intent);
                finish();


                break;

        }


    }

    @Override
    public void uoploadPhotoSuccess(boolean result, String photo) {

    }
}
