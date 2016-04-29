package com.softtek.lai.module.bodygamest.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygamest.Adapter.DownPhotoAdapter;
import com.softtek.lai.module.bodygamest.model.DownPhotoModel;
import com.softtek.lai.module.bodygamest.model.LogListModel;
import com.softtek.lai.module.bodygamest.model.LossModel;
import com.softtek.lai.module.bodygamest.present.DownloadManager;
import com.softtek.lai.module.bodygamest.present.PhotoListIml;
import com.softtek.lai.module.bodygamest.present.PhotoListPre;
import com.softtek.lai.module.grade.model.BannerModel;
import com.softtek.lai.module.grade.net.GradeService;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.ShareUtils;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;
import com.sw926.imagefileselector.ImageFileCropSelector;
import com.umeng.socialize.bean.SocializeConfig;
import com.umeng.socialize.sso.UMSsoHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_upload_photo)
public class UploadPhotoActivity extends BaseActivity implements PullToRefreshBase.OnRefreshListener2<ListView>, View.OnClickListener, AdapterView.OnItemClickListener, DownloadManager.DownloadCallBack
        , PhotoListIml.PhotoListCallback,ImageFileCropSelector.Callback {
    //toolbar标题栏
    @InjectView(R.id.tv_left)
    TextView tv_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.imtest)
    ImageView imtest;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    @InjectView(R.id.ptrlvlist)
    PullToRefreshListView ptrlvlist;
    @InjectView(R.id.im_uploadphoto_banner)
    ImageView im_uploadphoto_banner;
    @InjectView(R.id.cir_downphoto_head)
    CircleImageView cir_downphoto_head;
    @InjectView(R.id.tv_downphoto_nick)
    TextView tv_downphoto_nick;
    int pageIndex = 0;
    private List<LogListModel> logListModelList = new ArrayList<LogListModel>();
    private DownPhotoAdapter downPhotoAdapter;
    private PhotoListPre photoListPre;
    String path = "";
    private CharSequence[] items = {"拍照", "从相册选择照片"};
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
    private ProgressDialog progressDialog;
    DownPhotoModel downPhotoModel;
    LogListModel logListModel;
    DownloadManager downloadManager;
    UserInfoModel userInfoModel = UserInfoModel.getInstance();
    String username = userInfoModel.getUser().getNickname();
    private ImageFileCropSelector imageFileCropSelector;
    private GradeService service;
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
    public void onEvent(ResponseData listResponseData) {
        photoListPre.getLossData(UserInfoModel.getInstance().getUser().getUserid());

    }

    @Subscribe
    public void onEvent(LossModel lossModel) {
        System.out.println("lossModel:" + lossModel);
        ShareUtils shareUtils = new ShareUtils(UploadPhotoActivity.this);
        String path = AddressManager.get("shareHost", "http://172.16.98.167/Share/");
        String url = path + "SharePhotoAblum?AccountId=" + UserInfoModel.getInstance().getUser().getUserid();
        System.out.println("url:" + url);
        shareUtils.setShareContent("康宝莱体重管理挑战赛，坚持只为改变！", url, R.drawable.img_default, lossModel.getContent(), lossModel.getContent() + url);
        shareUtils.getController().openShare(UploadPhotoActivity.this, false);

    }

    @Override
    protected void initViews() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("正在加载内容...");
        tv_title.setText("上传照片");
        tv_right.setText("分享");
        //监听点击事件
        tv_left.setOnClickListener(this);
        imtest.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        ptrlvlist.setMode(PullToRefreshBase.Mode.BOTH);
        ptrlvlist.setOnItemClickListener(this);
        ptrlvlist.setOnRefreshListener(this);

        imageFileCropSelector=new ImageFileCropSelector(this);
        imageFileCropSelector.setOutPutImageSize(DisplayUtil.getMobileWidth(this),DisplayUtil.dip2px(this,195));
        imageFileCropSelector.setOutPutAspect(4,3);
        imageFileCropSelector.setOutPut(DisplayUtil.getMobileWidth(this),DisplayUtil.dip2px(this,195));
        imageFileCropSelector.setCallback(this);
        im_uploadphoto_banner.setLongClickable(true);
        im_uploadphoto_banner.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(UploadPhotoActivity.this).setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                imageFileCropSelector.takePhoto(UploadPhotoActivity.this);
                                break;
                            case 1:
                                imageFileCropSelector.selectImage(UploadPhotoActivity.this);
                                break;
                        }
                    }
                }).create().show();
                return false;
            }
        });

    }

    @Override
    protected void initDatas() {
        service= ZillaApi.NormalRestAdapter.create(GradeService.class);
        downloadManager = new DownloadManager(this);
        downPhotoModel = new DownPhotoModel();
        logListModel = new LogListModel();
        tv_downphoto_nick.setText(username);
        photoListPre = new PhotoListIml(this);
        downPhotoAdapter = new DownPhotoAdapter(this, logListModelList);
        ptrlvlist.setAdapter(downPhotoAdapter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrlvlist.setRefreshing();
            }
        }, 500);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;
            case R.id.fl_right:
                Intent intent = new Intent(this, SelectPhotoActivity.class);
                startActivityForResult(intent, 100);
                break;
            case R.id.imtest:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            takecamera();

                        } else if (which == 1) {
                            //照片
                            takepic();
                        }
                    }
                }).create().show();
                break;
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
        com.github.snowdream.android.util.Log.i("path:" + path);
    }

    public void takepic() {
        Intent picture = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(picture, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageFileCropSelector.onActivityResult(requestCode,resultCode,data);
        imageFileCropSelector.getmImageCropperHelper().onActivityResult(requestCode,resultCode,data);
        UMSsoHandler ssoHandler = SocializeConfig.getSocializeConfig().getSsoHandler(requestCode);
        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }

        if (requestCode == 100 && resultCode == RESULT_OK) {
            String result = data.getExtras().getString("result");//得到新Activity 关闭后返回的数据
            if (!"".equals(result)) {
                photoListPre.getUserPhotos(result);
            }
        }
        if (resultCode == RESULT_OK && requestCode == PHOTO) {
            progressDialog.setMessage("图片正在上传...");
            progressDialog.show();
            photoListPre.doUploadPhoto(UserInfoModel.getInstance().getUser().getUserid(), path.toString(), progressDialog);
        }
        if (requestCode == 101 && resultCode == Activity.RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = this.getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            progressDialog.setMessage("图片正在上传...");
            progressDialog.show();
            photoListPre.doUploadPhoto(UserInfoModel.getInstance().getUser().getUserid(), picturePath, progressDialog);
        }

    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        String userId = UserInfoModel.getInstance().getUser().getUserid();
        pageIndex = 1;
        downloadManager.doGetDownPhoto(userId, 1);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        String userId = UserInfoModel.getInstance().getUser().getUserid();
        pageIndex++;
        downloadManager.doGetDownPhoto(userId, pageIndex);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


    @Override
    public void getStroyList(DownPhotoModel downPhotoModel) {
        ptrlvlist.onRefreshComplete();
        if (downPhotoModel.getUserName() != null) {
//            tv_downphoto_nick.setText(downPhotoModel.getUserName());
            if (!TextUtils.isEmpty(downPhotoModel.getPhoto())) {
                Picasso.with(this).load("http://172.16.98.167/UpFiles/" + downPhotoModel.getPhoto()).fit().placeholder(R.drawable.img_default).error(R.drawable.img_default).into(cir_downphoto_head);
            } else {
                Picasso.with(this).load("www").placeholder(R.drawable.img_default).error(R.drawable.img_default).fit().into(cir_downphoto_head);
            }

            if (!TextUtils.isEmpty(downPhotoModel.getBanner())) {
                Picasso.with(this).load("http://172.16.98.167/UpFiles/" + downPhotoModel.getBanner()).fit().placeholder(R.drawable.default_pic).error(R.drawable.default_pic).into(im_uploadphoto_banner);
            } else {
                Picasso.with(this).load("www").placeholder(R.drawable.default_pic).error(R.drawable.default_pic).fit().into(im_uploadphoto_banner);
            }
        }

        if (downPhotoModel == null) {
            pageIndex = --pageIndex < 1 ? 1 : pageIndex;
            return;
        }
        com.github.snowdream.android.util.Log.i("列表" + downPhotoModel.toString());
        List<LogListModel> models = downPhotoModel.getLogList();
        if (models == null || models.isEmpty()) {
            pageIndex = --pageIndex < 1 ? 1 : pageIndex;
            return;
        }
        if (pageIndex == 1) {
            logListModelList.clear();
        }
        logListModelList.addAll(models);
        downPhotoAdapter.notifyDataSetChanged();

    }

    @Override
    public void uoploadPhotoSuccess(boolean result, String photo) {
        if (result) {
            // Picasso.with(this).load(AddressManager.get("photoHost")+photo).fit().placeholder(R.drawable.takephoto_upload).error(R.drawable.takephoto_upload).into(imtest);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ptrlvlist.setRefreshing();
                }
            }, 300);
        }
    }

    @Override
    public void onSuccess(final String file) {
        String token= UserInfoModel.getInstance().getToken();
        service.updateClassBanner(token, Long.parseLong(UserInfoModel.getInstance().getUser().getUserid()),
                "1",
                new TypedFile("image/*", new File(file)),
                new Callback<ResponseData<BannerModel>>() {
                    @Override
                    public void success(ResponseData<BannerModel> bannerModelResponseData, Response response) {
                        Log.i("logbanner===="+bannerModelResponseData.getData().getPath());
                        Picasso.with(UploadPhotoActivity.this).load(AddressManager.get("photoHost")+bannerModelResponseData.getData().getPath()).fit().
                                placeholder(R.drawable.default_pic).error(R.drawable.default_pic).fit().into(im_uploadphoto_banner);
                        new File(file).delete();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        ZillaApi.dealNetError(error);
                    }
                });
    }

    @Override
    public void onError() {

    }
}
