package com.softtek.lai.module.retest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.File.view.DimensionRecordActivity;
import com.softtek.lai.module.bodygame.Counselor;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.newmemberentry.view.GetPhotoDialog;
import com.softtek.lai.module.newmemberentry.view.model.Newstudents;
import com.softtek.lai.module.retest.model.RetestWrite;
import com.softtek.lai.module.retest.present.RetestPre;
import com.softtek.lai.module.retest.present.RetestclassImp;
import com.softtek.lai.utils.DisplayUtil;

import java.io.File;
import java.io.FileNotFoundException;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_write)
public class Write extends BaseActivity implements View.OnClickListener{
    //标题栏
    @InjectView(R.id.tv_title)
    TextView title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.btn_retest_write_addbody)
    Button btn_retest_write_addbody;
    @InjectView(R.id.iv_email)
    ImageView iv_email;
    @InjectView(R.id.im_retestwrite_takephoto)
    ImageView im_retestwrite_takephoto;
    private RetestPre retestPre;
    private RetestWrite retestWrite;
    String path="";
    private static final int PHOTO=5;
    private static final int GET_BODY=6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        im_retestwrite_takephoto.setOnClickListener(this);
        btn_retest_write_addbody.setOnClickListener(this);

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        title.setText("复测录入");
        tv_right.setText("保存");
        iv_email.setVisibility(View.INVISIBLE);
        retestPre=new RetestclassImp();
        retestWrite=new RetestWrite();
        retestWrite.setCircum("13");
        retestWrite.setAccountId("18175239201");
        retestWrite.setClassId("");
        retestWrite.setDoLegGirth("");
        retestWrite.setFat("");
        retestWrite.setHiplie("");
        retestWrite.setImage("");
        retestWrite.setPysical("");
        retestWrite.setUpArmGirth("");
        retestWrite.setWaistline("");
        retestWrite.setWeight("");
        retestPre.doGetWrite(19,16,retestWrite);




    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            //标题栏左返回
            case R.id.ll_left:
                finish();

            break;
            //标题栏右提交保存事件
            case R.id.tv_right:

                break;
            case R.id.im_retestwrite_takephoto:
                final GetPhotoDialog dialog = new GetPhotoDialog(this,
                        new GetPhotoDialog.GetPhotoDialogListener() {
                            @Override
                            public void onClick(View view) {
                                switch(view.getId()){
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
                break;
            case R.id.btn_retest_write_addbody:
                Intent intent=new Intent(Write.this, DimensionRecordActivity.class);
                intent.putExtra("retestWrite",retestWrite+"");
                startActivityForResult(intent,GET_BODY);
                break;

        }

    }
    public void takecamera() {

        path=(Environment.getExternalStorageDirectory().getPath())+"/123.jpg";
        File file=new File(path.toString());
        Uri uri= Uri.fromFile(file);
        Intent intent=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent,PHOTO);
        Bitmap bitmap= null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        im_retestwrite_takephoto.setImageBitmap(bitmap);
        Log.i("path:"+path);
    }
    public void takepic() {
        Intent picture = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(picture, 101);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK&&requestCode==PHOTO){
            Bitmap bm= BitmapFactory.decodeFile(path.toString());
            im_retestwrite_takephoto.setImageBitmap(bm);
            retestPre.goGetPicture(path.toString());
        }
        if(requestCode == 101 && resultCode == Activity.RESULT_OK && null != data){
            Uri selectedImage = data.getData();
            String[] filePathColumns={MediaStore.Images.Media.DATA};
            Cursor c = this.getContentResolver().query(selectedImage, filePathColumns, null,null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String picturePath= c.getString(columnIndex);
            Bitmap bitmap= null;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            im_retestwrite_takephoto.setImageBitmap(bitmap);
            retestPre.goGetPicture(picturePath.toString());
            Log.i("picturePath------------------------------------------------:"+picturePath);
            c.close();
        }

        //身体围度值传递
        if (requestCode==GET_BODY&&resultCode==RESULT_OK){
            retestWrite=(RetestWrite) data.getSerializableExtra("retestWrite");
            Log.i("新学员录入围度:retestWrite"+retestWrite);
        }
    }
}
