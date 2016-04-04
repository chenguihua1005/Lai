package com.softtek.lai.module.bodygamest.view;

import android.app.Activity;
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

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.bodygamest.Adapter.DownPhotoAdapter;
import com.softtek.lai.module.bodygamest.eventModel.PhotoListEvent;
import com.softtek.lai.module.bodygamest.model.DownPhotoModel;
import com.softtek.lai.module.bodygamest.model.UploadPhotModel;
import com.softtek.lai.module.bodygamest.present.PhotoListIml;
import com.softtek.lai.module.bodygamest.present.PhotoListPre;
import com.softtek.lai.module.newmemberentry.view.GetPhotoDialog;
import com.softtek.lai.module.retest.eventModel.BanJiEvent;
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

@InjectLayout(R.layout.activity_upload_photo)
public class UploadPhotoActivity extends BaseActivity implements View.OnClickListener {
    //toolbar标题栏
    @InjectView(R.id.tv_left)
    TextView tv_left;
    @InjectView(R.id.imtest)
    ImageView imtest;
    //照片列表listview
    @InjectView(R.id.list_uploadphoto)
    ListView list_uploadphoto;
    private List<DownPhotoModel> downPhotoModelList = new ArrayList<DownPhotoModel>();
    private DownPhotoAdapter downPhotoAdapter;
    private PhotoListPre photoListPre;
    String path = "";
    private static final int PHOTO = 1;
    //时间
    Calendar c = Calendar.getInstance();
    //            取得系统日期:
    int years = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH) + 1;
    int day = c.get(Calendar.DAY_OF_MONTH);
    //取得系统时间：
    int hour = c.get(Calendar.HOUR_OF_DAY);
    int minute = c.get(Calendar.MINUTE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
        downPhotoAdapter = new DownPhotoAdapter(this, downPhotoModelList);
        list_uploadphoto.setAdapter(downPhotoAdapter);
        //监听点击事件
        tv_left.setOnClickListener(this);
        imtest.setOnClickListener(this);
        list_uploadphoto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DownPhotoModel downPhotoModel=downPhotoModelList.get(position);
            }
        });

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(PhotoListEvent photoListEvent) {
        System.out.println("dsadasdsadasda>>》》》》》》》》》》》》》》" + photoListEvent.getDownPhotoModels());
//        downPhotoModelList = photoListEvent.getDownPhotoModels();
        Log.i("sdfsdfsdfsdfsd", "" + downPhotoModelList);

        List<DownPhotoModel> downPhotoModels = photoListEvent.getDownPhotoModels();
        for (DownPhotoModel dp : downPhotoModels) {

            String[] date = dp.getCreateDate().split("/");
            String[] year = date[2].split(" ");
            if (years == Integer.parseInt(year[0]) && month==Integer.parseInt(date[0]) && day == Integer.parseInt(date[1])) {

                if(!TextUtils.isEmpty(dp.getImgUrl())){
                    Picasso.with(this).load(dp.getImgUrl()).placeholder(R.drawable.lufei).error(R.drawable.lufei).into(imtest);
                }else{
                    Picasso.with(this).load("www").placeholder(R.drawable.lufei).error(R.drawable.lufei).into(imtest);
                }
            }
            else {

                DownPhotoModel downPhotoModel1 = new DownPhotoModel(dp.getImgUrl(),dp.getCreateDate());
                downPhotoModelList.add(downPhotoModel1);
//                downPhotoAdapter.updateData(downPhotoModelList);
            }
            System.out.print("hihihihihihihihihihihihi" + date + year);
        }


    }

    @Override
    protected void initViews() {
//        DownPhotoModel downPhotoModel1 = new DownPhotoModel("", month + "/" + day + "/" + years);
//        downPhotoModelList.add(downPhotoModel1);
        photoListPre = new PhotoListIml();
        photoListPre.doGetDownPhoto("3");

    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tv_left:
                finish();
                break;
            case R.id.imtest:
                final GetPhotoDialog dialog = new GetPhotoDialog(UploadPhotoActivity.this,
                        new GetPhotoDialog.GetPhotoDialogListener()
                        {
                            @Override
                            public void onClick(View view) {
                                switch (view.getId())
                                {
                                    case R.id.imgbtn_camera:
                                        takecamera();
                                        break;
                                    case R.id.imgbtn_pic:
                                        takepic();
                                        break;
                                }
                            }
                        });
                dialog.setTitle("照片上传");
                dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
                dialog.show();
        }



    }

    public void takecamera() {

        path = (Environment.getExternalStorageDirectory().getPath()) + "/123.jpg";
        File file = new File(path.toString());
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, PHOTO);
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        imtest.setImageBitmap(bitmap);
        com.github.snowdream.android.util.Log.i("path:" + path);
    }

    public void takepic() {
        Intent picture = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(picture, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PHOTO) {
            Bitmap bm = BitmapFactory.decodeFile(path.toString());
            imtest.setImageBitmap(bm);
            photoListPre.doUploadPhoto("3",path.toString());
        }
        if (requestCode == 101 && resultCode == Activity.RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = this.getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String picturePath = c.getString(columnIndex);
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            imtest.setImageBitmap(bitmap);
            photoListPre.doUploadPhoto("3",picturePath.toString());
            com.github.snowdream.android.util.Log.i("picturePath------------------------------------------------:" + picturePath);
            c.close();
        }

    }
}
