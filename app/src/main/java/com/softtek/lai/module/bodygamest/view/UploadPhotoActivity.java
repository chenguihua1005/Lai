package com.softtek.lai.module.bodygamest.view;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.bodygamest.Adapter.DownPhotoAdapter;
import com.softtek.lai.module.bodygamest.model.DownPhoto;


import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_upload_photo)
public class UploadPhotoActivity extends BaseActivity {
    //日期
    @InjectView(R.id.tv_uploadphoto_date)
    TextView tv_uploadphoto_date;
    //照片列表listview
    @InjectView(R.id.list_uploadphoto)
    ListView list_uploadphoto;
    private List<DownPhoto>downPhotoList=new ArrayList<DownPhoto>();
    private DownPhotoAdapter downPhotoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        downPhotoAdapter=new DownPhotoAdapter(this,downPhotoList);
        list_uploadphoto.setAdapter(downPhotoAdapter);

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {

    }
}
