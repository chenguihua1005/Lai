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
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygamest.Adapter.DownPhotoAdapter;
import com.softtek.lai.module.bodygamest.model.DownPhotoModel;
import com.softtek.lai.module.bodygamest.model.GifModel;
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
import com.softtek.lai.widgets.SelectPicPopupWindow;
import com.squareup.picasso.Picasso;
import com.sw926.imagefileselector.ImageFileCropSelector;
import com.sw926.imagefileselector.ImageFileSelector;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
        , PhotoListIml.PhotoListCallback, ImageFileCropSelector.Callback, ImageFileSelector.Callback {
    //toolbar标题栏
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.iv_email)
    ImageView iv_email;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    @InjectView(R.id.ptrlvlist)
    PullToRefreshListView ptrlvlist;
    String result;
    //    @InjectView(R.id.im_uploadphoto_banner)
//    ImageView im_uploadphoto_banner;
//    @InjectView(R.id.cir_downphoto_head)
//    CircleImageView cir_downphoto_head;
//    @InjectView(R.id.tv_downphoto_nick)
//    TextView tv_downphoto_nick;

    SelectPicPopupWindow menuWindow;
    int pageIndex = 0;
    private List<LogListModel> logListModelList = new ArrayList<>();
    private DownPhotoAdapter downPhotoAdapter;
    private PhotoListPre photoListPre;
    String path = "";
    private CharSequence[] items = {"拍照", "从相册选择照片"};
    private static final int PHOTO = 1;

    private ProgressDialog progressDialog;
    DownPhotoModel downPhotoModel;
    LogListModel logListModel;
    DownloadManager downloadManager;
    UserInfoModel userInfoModel = UserInfoModel.getInstance();
    String username = userInfoModel.getUser().getNickname();
    private ImageFileCropSelector imageFileCropSelector;
    private GradeService service;
    private ImageView im_uploadphoto_banner_list;
    private CircleImageView cir_downphoto_head_list;
    private ImageView imtest_list;
    private TextView tv_downphoto_nick;
    private ImageFileSelector imageFileSelector;
    boolean flag = true;
    ShareUtils shareUtils;
    String url;
    LossModel lossModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
        com.umeng.socialize.utils.Log.LOG = true;

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(ResponseData responseData) {
        progressDialog.setMessage("加载中");
        progressDialog.show();
        photoListPre.getLossData(UserInfoModel.getInstance().getUser().getUserid(), progressDialog);

    }

    @Subscribe
    public void onEvent(LossModel model) {
        lossModel = model;
        System.out.println("lossModel:" + lossModel);
        String path = AddressManager.get("shareHost");
        url = path + "SharePhotoAblum?AccountId=" + UserInfoModel.getInstance().getUser().getUserid();
        System.out.println("url:"+url);
        menuWindow = new SelectPicPopupWindow(UploadPhotoActivity.this, itemsOnClick);
        //显示窗口
        menuWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        menuWindow.showAtLocation(UploadPhotoActivity.this.findViewById(R.id.ptrlvlist), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
    }

    @Override
    protected void initViews() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("正在加载内容...");
        tv_title.setText("上传照片");
        iv_email.setVisibility(View.VISIBLE);
        iv_email.setImageResource(R.drawable.img_share_bt);
        iv_email.setOnClickListener(this);
        imageFileSelector = new ImageFileSelector(this);
        imageFileSelector.setOutPutImageSize(DisplayUtil.dip2px(this, 600),
                DisplayUtil.dip2px(this, 400));
        imageFileSelector.setQuality(80);
        imageFileSelector.setCallback(this);
        //监听点击事件
        ll_left.setOnClickListener(this);

        fl_right.setOnClickListener(this);
        ptrlvlist.setMode(PullToRefreshBase.Mode.BOTH);
        ptrlvlist.setOnItemClickListener(this);
        ptrlvlist.setOnRefreshListener(this);
        View view = getLayoutInflater().inflate(R.layout.loadphotolist_header_layout, null, false);
        im_uploadphoto_banner_list = (ImageView) view.findViewById(R.id.im_uploadphoto_banner_list);
        //tv_today = (TextView) view.findViewById(R.id.tv_today);
        cir_downphoto_head_list = (CircleImageView) view.findViewById(R.id.cir_downphoto_head_list);
        imtest_list = (ImageView) view.findViewById(R.id.imtest_list);
        tv_downphoto_nick = (TextView) view.findViewById(R.id.tv_downphoto_nick);
        ptrlvlist.getRefreshableView().addHeaderView(view);
        imtest_list.setOnClickListener(this);
        imageFileCropSelector = new ImageFileCropSelector(this);
        imageFileCropSelector.setOutPutAspect(1, 1);
        imageFileCropSelector.setOutPut(DisplayUtil.getMobileWidth(this), DisplayUtil.getMobileWidth(this));
        imageFileCropSelector.setCallback(this);
        im_uploadphoto_banner_list.setLongClickable(true);
        im_uploadphoto_banner_list.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                flag = true;
                new AlertDialog.Builder(UploadPhotoActivity.this).setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
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

        service = ZillaApi.NormalRestAdapter.create(GradeService.class);
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
            case R.id.ll_left:
                finish();
                break;
            case R.id.iv_email:
            case R.id.fl_right:
                Intent intent = new Intent(this, SelectPhotoActivity.class);
                startActivityForResult(intent, 100);
                break;
            case R.id.imtest_list:
                flag = false;
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            imageFileSelector.takePhoto(UploadPhotoActivity.this);
//                            takecamera();

                        } else if (which == 1) {
                            //照片
//                            imageFileSelector.selectImage(UploadPhotoActivity.this);
                            imageFileSelector.selectImage(UploadPhotoActivity.this);
//                            takepic();
                        }
                    }
                }).create().show();
                break;
        }


    }

    // android获取一个用于打开PDF文件的intent
    public static Intent getPdfFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {
            String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
            String str_url="";
            if(result.contains(",")){
                str_url=result.split(",")[0];
            }else {
                str_url=result;
            }
            System.out.println("path:"+path+str_url);
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.lin_weixin:
                    new ShareAction(UploadPhotoActivity.this)
                            .setPlatform(SHARE_MEDIA.WEIXIN)
                            .withTitle("康宝莱体重管理挑战赛，坚持只为改变！")
                            .withText(lossModel.getContent())
                            .withTargetUrl(url)
                            .withMedia(new UMImage(UploadPhotoActivity.this,path+str_url))
//                            .withMedia(new UMImage(UploadPhotoActivity.this, R.drawable.img_share_logo))
                            .share();
                    break;
                case R.id.lin_circle:
                    new ShareAction(UploadPhotoActivity.this)
                            .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                            .withTitle("康宝莱体重管理挑战赛，坚持只为改变！")
                            .withText(lossModel.getContent())
                            .withTargetUrl(url)
                            .withMedia(new UMImage(UploadPhotoActivity.this,path+str_url))
                            .share();
                    break;
                case R.id.lin_sina:
                    new ShareAction(UploadPhotoActivity.this)
                            .setPlatform(SHARE_MEDIA.SINA)
                            .withText(lossModel.getContent() + url)
                            .withMedia(new UMImage(UploadPhotoActivity.this,path+str_url))
                            .share();
                    break;
                default:
                    break;
            }


        }

    };

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
        if (!flag) {
            imageFileSelector.onActivityResult(requestCode, resultCode, data);
        } else {
            imageFileCropSelector.onActivityResult(requestCode, resultCode, data);
        }
        imageFileCropSelector.getmImageCropperHelper().onActivityResult(requestCode, resultCode, data);
//        UMSsoHandler ssoHandler = SocializeConfig.getSocializeConfig().getSsoHandler(requestCode);
//        System.out.println("ssoHandler:" + ssoHandler + "   requestCode:" + requestCode + "   resultCode:" + resultCode);
//        if (ssoHandler != null) {
//            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
//        }

        if (requestCode == 100 && resultCode == RESULT_OK) {
            result = data.getExtras().getString("result");//得到新Activity 关闭后返回的数据
            if (!"".equals(result)) {
                progressDialog.setMessage("加载中");
                progressDialog.show();
                photoListPre.getUserPhotos(result, progressDialog);
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
        if (downPhotoModel == null) {
            pageIndex = --pageIndex < 1 ? 1 : pageIndex;
            return;
        }
        String path = AddressManager.get("photoHost");
        if (downPhotoModel.getUserName() != null) {
            if (!TextUtils.isEmpty(downPhotoModel.getPhoto())) {
                Picasso.with(this).load(path + downPhotoModel.getPhoto()).fit().placeholder(R.drawable.img_default).error(R.drawable.img_default).into(cir_downphoto_head_list);
            }

            if (!TextUtils.isEmpty(downPhotoModel.getBanner())) {
                Picasso.with(this).load(path + downPhotoModel.getBanner()).fit().centerCrop().placeholder(R.drawable.default_icon_square).error(R.drawable.default_icon_square).into(im_uploadphoto_banner_list);
            }
        }

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

        String token = UserInfoModel.getInstance().getToken();
        if (!flag) {
            progressDialog.setMessage("图片正在上传...");
            progressDialog.show();
            photoListPre.doUploadPhoto(UserInfoModel.getInstance().getUser().getUserid(), file, progressDialog);
        } else {
            service.updateClassBanner(token, Long.parseLong(UserInfoModel.getInstance().getUser().getUserid()),
                    "1",
                    new TypedFile("image/*", new File(file)),
                    new Callback<ResponseData<BannerModel>>() {
                        @Override
                        public void success(ResponseData<BannerModel> bannerModelResponseData, Response response) {
                            Picasso.with(UploadPhotoActivity.this).load(AddressManager.get("photoHost") + bannerModelResponseData.getData().getPath()).fit().
                                    placeholder(R.drawable.default_icon_rect).error(R.drawable.default_icon_rect).into(im_uploadphoto_banner_list);

                            new File(file).delete();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            ZillaApi.dealNetError(error);
                        }
                    });
        }
    }

    @Override
    public void onError() {

    }

}
