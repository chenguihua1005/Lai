package com.softtek.lai.module.bodygamest.view;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame2pc.view.*;
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
    boolean flag = true;

    String url;
    LossModel lossModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
        com.umeng.socialize.utils.Log.LOG = true;

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent();
        setResult(RESULT_OK,intent);
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
        String path = AddressManager.get("shareHost");
        url = path + "SharePhotoAblum?AccountId=" + UserInfoModel.getInstance().getUser().getUserid();
        menuWindow = new SelectPicPopupWindow(UploadPhotoActivity.this, itemsOnClick);
        //显示窗口
        menuWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        menuWindow.showAtLocation(UploadPhotoActivity.this.findViewById(R.id.rel), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
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
        ll_left.setOnClickListener(this);

        fl_right.setOnClickListener(this);
        ptrlvlist.setMode(PullToRefreshBase.Mode.BOTH);
        ptrlvlist.setOnItemClickListener(this);
        ptrlvlist.setOnRefreshListener(this);
        ILoadingLayout startLabelse = ptrlvlist.getLoadingLayoutProxy(true,false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setRefreshingLabel("正在刷新数据");// 刷新时
        startLabelse.setReleaseLabel("松开立即刷新中");// 下来达到一定距离时，显示的提示
        ILoadingLayout endLabelsr = ptrlvlist.getLoadingLayoutProxy(false, true);
        endLabelsr.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
//        endLabelsr.setLastUpdatedLabel("正在刷新数据");// 刷新时
        endLabelsr.setRefreshingLabel("正在刷新数据中");
        endLabelsr.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        View view = getLayoutInflater().inflate(R.layout.loadphotolist_header_layout, null, false);
        im_uploadphoto_banner_list = (ImageView) view.findViewById(R.id.im_uploadphoto_banner_list);
        cir_downphoto_head_list = (CircleImageView) view.findViewById(R.id.cir_downphoto_head_list);
        imtest_list = (ImageView) view.findViewById(R.id.imtest_list);
        tv_downphoto_nick = (TextView) view.findViewById(R.id.tv_downphoto_nick);
        ptrlvlist.getRefreshableView().addHeaderView(view);
        imtest_list.setOnClickListener(this);
        imageFileCropSelector = new ImageFileCropSelector(this);
        imageFileCropSelector.setOutPutAspect(1, 1);
        int px = Math.min(DisplayUtil.getMobileWidth(this), DisplayUtil.getMobileHeight(this));
        imageFileCropSelector.setOutPut(px, px);
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
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
//            startActivity(new Intent(UploadPhotoActivity.this, com.softtek.lai.module.bodygame2pc.view.BodyGamePCActivity.class));
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                //startActivity(new Intent(UploadPhotoActivity.this, com.softtek.lai.module.bodygame2pc.view.BodyGamePCActivity.class));
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

                            if (ActivityCompat.checkSelfPermission(UploadPhotoActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                //可以得到一个是否需要弹出解释申请该权限的提示给用户如果为true则表示可以弹
                                if (ActivityCompat.shouldShowRequestPermissionRationale(UploadPhotoActivity.this, Manifest.permission.CAMERA)) {
                                    //允许弹出提示
                                    ActivityCompat.requestPermissions(UploadPhotoActivity.this,
                                            new String[]{Manifest.permission.CAMERA}, CAMERA_PREMISSION);

                                } else {
                                    //不允许弹出提示
                                    ActivityCompat.requestPermissions(UploadPhotoActivity.this,
                                            new String[]{Manifest.permission.CAMERA}, CAMERA_PREMISSION);
                                }
                            } else {
                                imageFileCropSelector.takePhoto(UploadPhotoActivity.this);
                            }
                        } else if (which == 1) {
                            //照片
                            //imageFileSelector.selectImage(UploadPhotoActivity.this);
                            imageFileCropSelector.selectImage(UploadPhotoActivity.this);
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
            String str_url = "";
            if (result.contains(",")) {
                str_url = result.split(",")[0];
            } else {
                str_url = result;
            }
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.lin_weixin:
                    new ShareAction(UploadPhotoActivity.this)
                            .setPlatform(SHARE_MEDIA.WEIXIN)
                            .withTitle("康宝莱体重管理挑战赛，坚持只为改变！")
                            .withText(lossModel.getContent())
                            .withTargetUrl(url)
                            .withMedia(new UMImage(UploadPhotoActivity.this, path + str_url))
                            .share();
                    break;
                case R.id.lin_circle:
                    new ShareAction(UploadPhotoActivity.this)
                            .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                            .withTitle("康宝莱体重管理挑战赛，坚持只为改变！")
                            .withText(lossModel.getContent())
                            .withTargetUrl(url)
                            .withMedia(new UMImage(UploadPhotoActivity.this, path + str_url))
                            .share();
                    break;
                case R.id.lin_sina:
                    new ShareAction(UploadPhotoActivity.this)
                            .setPlatform(SHARE_MEDIA.SINA)
                            .withText(lossModel.getContent() + url)
                            .withMedia(new UMImage(UploadPhotoActivity.this, path + str_url))
                            .share();
                    break;
                default:
                    break;
            }


        }

    };
    private static final int CAMERA_PREMISSION = 100;

    // Android 6.0的动态权限
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PREMISSION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay! Do the
                // contacts-related task you need to do.
                imageFileCropSelector.takePhoto(UploadPhotoActivity.this);

            } else {

                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*if (!flag) {
            imageFileSelector.onActivityResult(requestCode, resultCode, data);
        } else {*/
        imageFileCropSelector.onActivityResult(requestCode, resultCode, data);
        imageFileCropSelector.getmImageCropperHelper().onActivityResult(requestCode, resultCode, data);
        /*}*/
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
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void uoploadPhotoSuccess(boolean result, String photo) {
        if (result) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ptrlvlist.setRefreshing();
                    String userId = UserInfoModel.getInstance().getUser().getUserid();
                    pageIndex = 1;
                    downloadManager.doGetDownPhoto(userId, 1);
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
