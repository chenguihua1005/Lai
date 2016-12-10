package com.softtek.lai.module.bodygame3.photowall;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.github.snowdream.android.util.Log;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.photowall.model.PhotoWallListModel;
import com.softtek.lai.module.bodygame3.photowall.model.PhotoWallslistModel;
import com.softtek.lai.module.bodygame3.head.net.HeadService;
import com.softtek.lai.module.community.adapter.HealthyCommunityAdapter;
import com.softtek.lai.module.community.adapter.PhotosAdapter;
import com.softtek.lai.module.community.model.HealthyCommunityModel;
import com.softtek.lai.module.community.presenter.RecommentHealthyManager;
import com.softtek.lai.module.picture.model.UploadImage;
import com.softtek.lai.module.picture.view.PictureMoreActivity;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CircleImageView;
import com.softtek.lai.widgets.CustomGridView;
import com.squareup.picasso.Picasso;
import com.sw926.imagefileselector.ImageFileSelector;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_photo_wall)
public class PhotoWallActivity extends BaseActivity implements PullToRefreshBase.OnRefreshListener2<ListView> ,View.OnClickListener{
    @InjectView(R.id.ptrlv)
    PullToRefreshListView ptrlv;
    @InjectView(R.id.empty)
    FrameLayout empty;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.iv_email)
    ImageView iv_email;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;

    private RecommentHealthyManager community;
    private HealthyCommunityAdapter adapter;
    private EasyAdapter<PhotoWallslistModel> photoWallListModelEasyAdapter;
    PhotoWallListModel photoWallListModel;
    PhotoWallslistModel photowallItemModel;
    List<PhotoWallslistModel> photoWallItemModels=new ArrayList<PhotoWallslistModel>();
    private List<HealthyCommunityModel> communityModels=new ArrayList<>();
    int pageIndex=1;
    int totalPage=0;
    HeadService headService;
    private CharSequence[] items={"拍照","从相册选择照片"};
    private static final int OPEN_SENDER_REQUEST=1;
    private static final int CAMERA_PREMISSION=100;
    private ImageFileSelector imageFileSelector;
    @Override
    protected void initViews() {
        fl_right.setOnClickListener(this);
        iv_email.setBackground(getResources().getDrawable(R.drawable.camera));
        ptrlv.setOnRefreshListener(this);
        ptrlv.setMode(PullToRefreshBase.Mode.BOTH);
        ptrlv.setEmptyView(empty);
        ILoadingLayout startLabelse = ptrlv.getLoadingLayoutProxy(true,false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setRefreshingLabel("正在刷新数据");// 刷新时
        startLabelse.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        ILoadingLayout endLabelsr = ptrlv.getLoadingLayoutProxy(false, true);
        endLabelsr.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
        endLabelsr.setRefreshingLabel("正在刷新数据");
        endLabelsr.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        int px= DisplayUtil.dip2px(this,300);
        //*************************
        imageFileSelector=new ImageFileSelector(this);
        imageFileSelector.setOutPutImageSize(px,px);
        imageFileSelector.setQuality(60);
        imageFileSelector.setCallback(new ImageFileSelector.Callback() {
            @Override
            public void onSuccess(String file) {
                Intent intent=new Intent(PhotoWallActivity.this,PublishDyActivity.class);//跳转到发布动态界面
                UploadImage image=new UploadImage();
                image.setImage(new File(file));
                image.setUri(Uri.fromFile(new File(file)));
                intent.putExtra("uploadImage",image);
                startActivityForResult(intent,OPEN_SENDER_REQUEST);
            }

            @Override
            public void onError() {

            }
        });
        //**************************
    }

    @Override
    protected void initDatas() {
        headService= ZillaApi.NormalRestAdapter.create(HeadService.class);
        doGetData();
        photoWallListModelEasyAdapter=new EasyAdapter<PhotoWallslistModel>(this,photoWallItemModels,R.layout.photowall_list_item) {
            @Override
            public void convert(ViewHolder holder, final PhotoWallslistModel data, int position) {
                TextView tv_name= holder.getView(R.id.tv_name);
                tv_name.setText(data.getUserName());//用户名
                CircleImageView civ_header_image=holder.getView(R.id.civ_header_image);
                if (!TextUtils.isEmpty(data.getUserPhoto())) {//头像
                    Picasso.with(getParent()).load(AddressManager.get("photoHost")+data.getUserPhoto()).fit().error(R.drawable.img_default)
                            .into(civ_header_image);
                    Log.i("照片墙动态测试头像",AddressManager.get("photoHost")+data.getUserPhoto());
                }
                TextView tv_content=holder.getView(R.id.tv_content);
                tv_content.setText(data.getContent());//正文
                final CheckBox cb_focus=holder.getView(R.id.cb_focus);
                cb_focus.setChecked("1".equals(data.getIsFocus()));//是否关注
                TextView tv_date=holder.getView(R.id.tv_date);
                tv_date.setText(data.getCreatedate());//日期
                LinearLayout ll_dianzan=holder.getView(R.id.ll_dianzan);
                TextView tv_zan_name=holder.getView(R.id.tv_zan_name);
                if (!"0".equals(data.getIsPraise())) {
                    ll_dianzan.setVisibility(View.VISIBLE);//显示点赞人
                    for (int i=0;i<data.getPraiseNameList().size();i++) {
                        if (i==0)
                        {
                            tv_zan_name.append(data.getPraiseNameList().get(i));
                        }
                        else {
                            tv_zan_name.append(","+data.getPraiseNameList().get(i));
                        }

                    }
                }
                cb_focus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (cb_focus.isChecked())
                        {
                            headService.doFocusAccount(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), Long.parseLong("3399"), new RequestCallback<ResponseData>() {
                                @Override
                                public void success(ResponseData responseData, Response response) {
                                    int status=responseData.getStatus();
                                    switch (status)
                                    {
                                        case 200:
                                            Util.toastMsg(responseData.getMsg());
                                            refreshList(data.getAccountid(),"1");
                                            break;
                                        default:
                                            cb_focus.setChecked(false);
                                            Util.toastMsg(responseData.getMsg());
                                            break;
                                    }
                                }
                            });
                        }
                        else {
                            headService.doCancleFocusAccount(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), Long.parseLong("3399"), new RequestCallback<ResponseData>() {
                                @Override
                                public void success(ResponseData responseData, Response response) {
                                    int status=responseData.getStatus();
                                    switch (status)
                                    {
                                        case 200:
                                            Util.toastMsg(responseData.getMsg());
                                            refreshList(data.getAccountid(),"0");
                                            break;
                                        default:
                                            cb_focus.setChecked(true);
                                            Util.toastMsg(responseData.getMsg());
                                            break;
                                    }
                                }
                            });

                        }
                    }
                });
                CustomGridView photos=holder.getView(R.id.photos);
//                String[] imgs = data.getUserThPhoto().split(",");
                final ArrayList<String> list = new ArrayList<>();

                for (int i = 0; i < data.getPhotoList().size(); i++) {
                    list.add(data.getPhotoList().get(i));
                }
                photos.setAdapter(new PhotosAdapter(data.getThumbnailPhotoList(), PhotoWallActivity.this));
                photos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                        Intent in = new Intent(PhotoWallActivity.this, PictureMoreActivity.class);
                        in.putStringArrayListExtra("images", list);
                        in.putExtra("position", position);
                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(v, v.getWidth() / 2, v.getHeight() / 2, 0, 0);
                        ActivityCompat.startActivity(PhotoWallActivity.this, in, optionsCompat.toBundle());
                    }
                });

            }
        }
        ;
        ptrlv.setAdapter(photoWallListModelEasyAdapter);
    }

    private void doGetData() {
        headService.doGetPhotoWalls(UserInfoModel.getInstance().getToken(), Long.parseLong("76363"), "C4E8E179-FD99-4955-8BF9-CF470898788B", "1", "10", new RequestCallback<ResponseData<PhotoWallListModel>>() {
            @Override
            public void success(ResponseData<PhotoWallListModel> photoWallListModelResponseData, Response response) {
                int status=photoWallListModelResponseData.getStatus();
                switch (status)
                {
                    case 200:
                        photoWallListModel=photoWallListModelResponseData.getData();
                        if (photoWallListModel!=null)
                        {
                            empty.setVisibility(View.GONE);
                            photoWallItemModels.addAll(photoWallListModel.getPhotoWallslist());
                            photoWallListModelEasyAdapter.notifyDataSetChanged();

                        }
                        break;
                    case 100:
                        break;
                    default:
                        Util.toastMsg(photoWallListModelResponseData.getMsg());
                        break;
                }
            }
        });
    }
    private void refreshList(String Accountid,String focus)
    {
        for (PhotoWallslistModel model:photoWallItemModels)
        {
            if(model.getAccountid().equals(Accountid)){
                model.setIsFocus(focus);
            }
        }
        photoWallListModelEasyAdapter.notifyDataSetChanged();
    }
    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageFileSelector.onActivityResult(requestCode,resultCode,data);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==CAMERA_PREMISSION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay! Do the
                // contacts-related task you need to do.
                imageFileSelector.takePhoto(PhotoWallActivity.this);

            } else {

                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.fl_right:
//                startActivity(new Intent(this,PublishDyActivity.class));
                //弹出dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            //拍照
                            if (ActivityCompat.checkSelfPermission(PhotoWallActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                //可以得到一个是否需要弹出解释申请该权限的提示给用户如果为true则表示可以弹
                                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                                    //允许弹出提示
                                    requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PREMISSION);

                                } else {
                                    //不允许弹出提示
                                    requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PREMISSION);
                                }
                            } else {
                                imageFileSelector.takePhoto(PhotoWallActivity.this);
                            }
                        } else if (which == 1) {
                            //照片
                            imageFileSelector.selectImage(PhotoWallActivity.this);
                        }
                    }
                }).create().show();
                break;
        }
    }
}
