package com.softtek.lai.module.bodygame3.head.view;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.head.model.TitleModel;
import com.softtek.lai.module.bodygame3.head.net.HeadService;
import com.softtek.lai.module.community.adapter.CommunityPhotoGridViewAdapter;
import com.softtek.lai.module.community.view.EditPersonalDynamicActivity;
import com.softtek.lai.module.community.view.PreviewImageActivity;
import com.softtek.lai.module.picture.model.UploadImage;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CircleImageView;
import com.softtek.lai.widgets.CustomGridView;
import com.squareup.picasso.Picasso;
import com.sw926.imagefileselector.ImageFileSelector;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_publish_dy)
public class PublishDyActivity extends BaseActivity implements AdapterView.OnItemClickListener,ImageFileSelector.Callback,View.OnClickListener{
    @InjectView(R.id.cgv)
    CustomGridView cgv;
    @InjectView(R.id.et_content)
    EditText et_content;
    @InjectView(R.id.list_title)
    ListView list_title;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    EasyAdapter<TitleModel> titleModelEasyAdapter;
    List<TitleModel> titleModels=new ArrayList<TitleModel>();
    private List<UploadImage> images=new ArrayList<>();
    private ImageFileSelector imageFileSelector;
    CommunityPhotoGridViewAdapter adapter;
    HeadService headService;
    @Override
    protected void initViews() {
        cgv.setOnItemClickListener(this);
        ll_left.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        tv_title.setText("发布动态");
        tv_right.setText("发送");
    }

    @Override
    protected void initDatas() {
        doGetTitle();
        UploadImage image= getIntent().getParcelableExtra("uploadImage");
        if(image!=null){
            try {
                image.setBitmap(BitmapFactory.decodeStream(getContentResolver().openInputStream(image.getUri())));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            images.add(image);
        }
        images.add(new UploadImage(null, BitmapFactory.decodeResource(getResources(), R.drawable.add_img_icon)));
        adapter=new CommunityPhotoGridViewAdapter(images,this);
        cgv.setAdapter(adapter);
        titleModelEasyAdapter=new EasyAdapter<TitleModel>(this,titleModels,R.layout.title_list_item) {
            @Override
            public void convert(ViewHolder holder, TitleModel data, int position) {
                TextView tv_title_name=holder.getView(R.id.tv_title_name);
                tv_title_name.setText("#"+data.getWordKey()+"#");
                TextView tv_hot_num=holder.getView(R.id.tv_hot_num);
                tv_hot_num.setText(data.getThemeHot());
                CircleImageView cir_title=holder.getView(R.id.cir_title);
                CheckBox ck_select=holder.getView(R.id.ck_select);
                if (!TextUtils.isEmpty(data.getThemePhoto()))
                {
                    Picasso.with(PublishDyActivity.this).load(AddressManager.get("photoHost")+data.getThemePhoto()).fit().into(cir_title);
                }

            }
        };
        list_title.setAdapter(titleModelEasyAdapter);
        list_title.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        int px= DisplayUtil.dip2px(this,300);
        //*************************

        imageFileSelector=new ImageFileSelector(this);
        imageFileSelector.setOutPutImageSize(px,px);
        imageFileSelector.setQuality(30);
        imageFileSelector.setCallback(this);
    }

    private void doGetTitle() {
        headService= ZillaApi.NormalRestAdapter.create(HeadService.class);
        headService.doGetPhWallTheme(UserInfoModel.getInstance().getToken(), new RequestCallback<ResponseData<List<TitleModel>>>() {
            @Override
            public void success(ResponseData<List<TitleModel>> listResponseData, Response response) {
                int status=listResponseData.getStatus();
                switch (status)
                {
                    case 200:
                        titleModels.addAll(listResponseData.getData());
                        titleModelEasyAdapter.notifyDataSetChanged();
                        break;
                    default:
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }
        });
    }

    CharSequence[] options={"拍照","选择个人相册"};
    private static final int OPEN_PREVIEW=3;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        UploadImage image=images.get(position);
        if(image.getImage()==null){
            new AlertDialog.Builder(this).setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(which==0){
                        //检查权限
                        if(ActivityCompat.checkSelfPermission(PublishDyActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                            //可以得到一个是否需要弹出解释申请该权限的提示给用户如果为true则表示可以弹
                            if(ActivityCompat.shouldShowRequestPermissionRationale(PublishDyActivity.this,Manifest.permission.CAMERA)){
                                //允许弹出提示
                                ActivityCompat.requestPermissions(PublishDyActivity.this,
                                        new String[]{Manifest.permission.CAMERA},CAMERA_PREMISSION);

                            }else{
                                //不允许弹出提示
                                ActivityCompat.requestPermissions(PublishDyActivity.this,
                                        new String[]{Manifest.permission.CAMERA},CAMERA_PREMISSION);
                            }
                        }else {
                            imageFileSelector.takePhoto(PublishDyActivity.this);
                        }
                    }else if(which==1){
                        //打开图库
                        imageFileSelector.selectImage(PublishDyActivity.this);
                    }
                }
            }).create().show();
        }else{
            Intent intent=new Intent(this,PreviewImageActivity.class);
            intent.putExtra("uri", Uri.fromFile(image.getImage()));
            intent.putExtra("position",position);
            startActivityForResult(intent, OPEN_PREVIEW);
        }
    }
    private static final int CAMERA_PREMISSION=100;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==CAMERA_PREMISSION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay! Do the
                // contacts-related task you need to do.
                imageFileSelector.takePhoto(PublishDyActivity.this);

            } else {

                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageFileSelector.onActivityResult(requestCode,resultCode,data);
        if(resultCode==RESULT_OK){
            if(requestCode==OPEN_PREVIEW){
                int position= data.getIntExtra("position", 0);
                images.remove(position);
                if(images.get(images.size()-1).getImage()!=null){
                    images.add(new UploadImage(null, BitmapFactory.decodeResource(getResources(), R.drawable.shizi)));
                }
            }
            adapter.notifyDataSetChanged();


        }
    }

    @Override
    public void onSuccess(String file) {
        UploadImage image=new UploadImage();
        File outFile=new File(file);
        image.setImage(outFile);
        image.setBitmap(BitmapFactory.decodeFile(outFile.getAbsolutePath()));
        images.add(0, image);
        if(images.size()==10){
            images.remove(9);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onError() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.ll_left:
                finish();
                break;

        }
    }
}
