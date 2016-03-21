package com.softtek.lai.module.newmemberentry.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.File.view.DimensionRecordActivity;

import java.io.File;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_member_entry)
public class EntryActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.tv_left)
    TextView tv_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.tv_right)
    TextView tv_right;

    //照片上传
    @InjectView(R.id.tv_photoupload)
    TextView tv_photoupload;

    @InjectView(R.id.img1)
    ImageView img1;

    //添加身体围度
    @InjectView(R.id.btn_Add_bodydimension)
    Button btn_Add_bodydimension;

    StringBuffer path=new StringBuffer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_left.setOnClickListener(this);
        tv_photoupload.setOnClickListener(this);
        btn_Add_bodydimension.setOnClickListener(this);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        tv_title.setText("新学员录入");
        tv_left.setBackgroundResource(R.drawable.back);
        tv_right.setText("确定");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_left:
                Intent intent3 = new Intent(EntryActivity.this, EntryActivity.class);
                startActivity(intent3);
                finish();
                break;
            case R.id.tv_right:

                finish();
                break;
            case R.id.btn_Add_bodydimension:
                startActivity(new Intent(EntryActivity.this, DimensionRecordActivity.class));
                finish();
            case R.id.tv_photoupload:
                //File outputImage=new File(Environment.getExternalStorageDirectory(),"output_image.jpg");
                takepic();
                break;
        }
    }

    public void takepic() {
        //可以这么写不是使用”android.provider.MediaStore.EXTRA_OUTPUT“参数
//        Intent itent=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(itent, 1);
        path.append(Environment.getExternalStorageDirectory().getPath()).append("/123.jpg");
        File file=new File(path.toString());
        Uri uri= Uri.fromFile(file);
        Intent intent=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK){
            // 可以这么写
            // Bundle b=data.getExtras();
            // Bitmap bm=(Bitmap) b.get("data");
            Bitmap bm= BitmapFactory.decodeFile(path.toString());
            img1.setImageBitmap(bm);
        }
    }
}
