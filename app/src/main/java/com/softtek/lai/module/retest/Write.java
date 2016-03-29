package com.softtek.lai.module.retest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.NumberPicker;
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
//显示照片
    @InjectView(R.id.im_retestwrite_showphoto)
    ImageView im_retestwrite_showphoto;
    //信息点击弹框
    //初始体重
    @InjectView(R.id.ll_retestWrite_chu_weight)
    LinearLayout ll_retestWrite_chu_weight;
    //现在体重
    @InjectView(R.id.ll_retestWrite_nowweight)
    LinearLayout ll_retestWrite_nowweight;
    //体脂
    @InjectView(R.id.ll_retestWrite_tizhi)
    LinearLayout ll_retestWrite_tizhi;
    //内脂
    @InjectView(R.id.ll_retestWrite_neizhi)
    LinearLayout ll_retestWrite_neizhi;

    //信息保存
    @InjectView(R.id.tv_write_chu_weight)
    TextView tv_write_chu_weight;
    //现在体重
    @InjectView(R.id.tv_retestWrite_nowweight)
    TextView tv_retestWrite_nowweight;
    //体脂
    @InjectView(R.id.tv_retestWrite_tizhi)
    TextView tv_retestWrite_tizhi;
    //内脂
    @InjectView(R.id.tv_retestWrite_neizhi)
    TextView tv_retestWrite_neizhi;

    private RetestPre retestPre;
    RetestWrite retestWrite;
    String path="";
    private static final int PHOTO=1;
    private static final int GET_BODY=2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        im_retestwrite_takephoto.setOnClickListener(this);
        btn_retest_write_addbody.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        ll_retestWrite_chu_weight.setOnClickListener(this);
        ll_retestWrite_nowweight.setOnClickListener(this);
        ll_retestWrite_tizhi.setOnClickListener(this);
        ll_retestWrite_neizhi.setOnClickListener(this);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        title.setText("复测录入");
        tv_right.setText("保存");
        iv_email.setVisibility(View.INVISIBLE);
        Intent intent=getIntent();
        String accountId=intent.getStringExtra("accountId");
        String loginId=intent.getStringExtra("loginId");
        String classId=intent.getStringExtra("classId");
        Log.i("chuanzhizhizhizhizhi",accountId+loginId+classId);
        retestPre=new RetestclassImp();
        retestWrite=new RetestWrite();
        retestWrite.setAccountId(accountId);




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
                retestWrite.setWeight(tv_retestWrite_nowweight.getText()+"");
                retestWrite.setPysical(tv_retestWrite_tizhi.getText()+"");
                retestWrite.setFat(tv_retestWrite_neizhi.getText()+"");
                retestPre.doPostWrite(3,36,retestWrite);
                break;
            //拍照事件
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
                intent.putExtra("retestWrite",retestWrite);
                startActivityForResult(intent,GET_BODY);
                break;
            //点击弹框事件
            case R.id.ll_retestWrite_chu_weight:
                show_information("初始体重（kg）",200,70,20,9,5,0,0);
                break;
            case R.id.ll_retestWrite_nowweight:
                show_information("现在体重（kg）",200,70,20,9,5,0,1);
                break;
            case R.id.ll_retestWrite_tizhi:
                show_information("体脂（%）",100,50,0,9,5,0,2);
                break;
            case R.id.ll_retestWrite_neizhi:
                show_information("内脂（%）",100,50,0,9,5,0,3);
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
        im_retestwrite_showphoto.setVisibility(View.VISIBLE);
        im_retestwrite_showphoto.setImageBitmap(bitmap);
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
            im_retestwrite_showphoto.setImageBitmap(bm);
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
            im_retestwrite_showphoto.setVisibility(View.VISIBLE);
            im_retestwrite_showphoto.setImageBitmap(bitmap);
            retestPre.goGetPicture(picturePath.toString());
            Log.i("picturePath------------------------------------------------:"+picturePath);
            c.close();
        }

        //身体围度值传递
        if (requestCode==GET_BODY&&resultCode==RESULT_OK){
            Log.i("》》》》》requestCode："+requestCode+"resultCode："+resultCode);
            retestWrite=(RetestWrite) data.getSerializableExtra("retestWrite");
            Log.i("新学员录入围度:retestWrite"+retestWrite);
        }
    }
    public void show_information(String title, int np1maxvalur, int np1value, int np1minvalue, int np2maxvalue, int np2value, int np2minvalue, final int num) {
        final Dialog information_dialog = new Dialog(this);
        information_dialog.setTitle(title);
        information_dialog.setContentView(R.layout.dimension_dialog);
        Button b1 = (Button) information_dialog.findViewById(R.id.button1);
        Button b2 = (Button) information_dialog.findViewById(R.id.button2);
        final NumberPicker np1 = (NumberPicker) information_dialog.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = (NumberPicker) information_dialog.findViewById(R.id.numberPicker2);
        np1.setMaxValue(np1maxvalur);
        np1.setValue(np1value);
        np1.setMinValue(np1minvalue);
        np1.setWrapSelectorWheel(false);
        np2.setMaxValue(np2maxvalue);
        np2.setValue(np2value);
        np2.setMinValue(np2minvalue);
        np2.setWrapSelectorWheel(false);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num==0) {
                    tv_write_chu_weight.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue())); //set the value to textview
                    information_dialog.dismiss();
                }
                else if (num==1)
                {
                    tv_retestWrite_nowweight.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                    information_dialog.dismiss();
                }
                else if (num==2)
                {
                    tv_retestWrite_tizhi.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                    information_dialog.dismiss();
                }
                else if(num==3)
                {
                    tv_retestWrite_neizhi.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                    information_dialog.dismiss();
                }

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                information_dialog.dismiss();
            }
        });
        information_dialog.show();
        information_dialog.setCanceledOnTouchOutside(false);
    }
}
