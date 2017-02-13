package com.softtek.lai.module.laijumine.view;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.head.view.EditSignaActivity;
import com.softtek.lai.module.bodygame3.more.view.LossWeightAndFatActivity;
import com.softtek.lai.module.community.view.FocusFragment;
import com.softtek.lai.module.community.view.PersionalActivity;
import com.softtek.lai.module.home.view.HealthyRecordActivity;
import com.softtek.lai.module.home.view.ModifyPersonActivity;
import com.softtek.lai.module.home.view.ValidateCertificationActivity;
import com.softtek.lai.module.laijumine.model.MyInfoModel;
import com.softtek.lai.module.laijumine.net.MineSevice;
import com.softtek.lai.module.login.model.PhotoModel;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.net.LoginService;
import com.softtek.lai.module.message2.view.Message2Activity;
import com.softtek.lai.module.sportchart.model.PhotModel;
import com.softtek.lai.module.sportchart.presenter.PhotoManager;
import com.softtek.lai.module.sportchart.view.ChartActivity;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;
import com.sw926.imagefileselector.ImageFileCropSelector;

import java.io.File;
import java.util.List;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_mine_fragment)
public class MineTwoFragment extends LazyBaseFragment implements View.OnClickListener,PhotoManager.PhotoManagerCallback {

    private UserModel model;
    private MyInfoModel myinfomodel;
    CharSequence[] items = {"拍照", "照片"};
    private static final int CAMERA_PREMISSION = 101;
    private ImageFileCropSelector imageFileCropSelector;

    @InjectView(R.id.cir_userphoto)
    CircleImageView cir_userphoto;//头像
    @InjectView(R.id.tv_username)
    TextView tv_username;//用户名
    @InjectView(R.id.im_banner)
    ImageView im_banner;//个人封面
    @InjectView(R.id.im_takephicon)
    ImageView im_takephicon;//个人封面拍照图标
    @InjectView(R.id.tv_dynum)
    TextView tv_dynum;
    @InjectView(R.id.tv_guanzhunum)
    TextView tv_guanzhunum;
    @InjectView(R.id.tv_fansnum)
    TextView tv_fansnum;
    @InjectView(R.id.tv_updatetime)
    TextView tv_updatetime;
    @InjectView(R.id.tv_level)
    TextView tv_level;
    @InjectView(R.id.tv_sportlevelnum)
    TextView tv_sportlevelnum;
    @InjectView(R.id.tv_news)
    TextView tv_news;
    @InjectView(R.id.tv_renzh)
    TextView tv_renzh;

    //跳转
    @InjectView(R.id.tv_setting)
    TextView tv_setting;
    @InjectView(R.id.tv_editor_signature)
    TextView tv_editor_signature;
    @InjectView(R.id.re_mydy)
    RelativeLayout re_mydy;
    @InjectView(R.id.re_guanzhu)
    RelativeLayout re_guanzhu;
    @InjectView(R.id.re_fans)
    RelativeLayout re_fans;
    @InjectView(R.id.re_health)
    RelativeLayout re_health;
    @InjectView(R.id.re_losslevel)
    RelativeLayout re_losslevel;
    @InjectView(R.id.re_sportlevel)
    RelativeLayout re_sportlevel;
    @InjectView(R.id.re_task)
    RelativeLayout re_task;
    @InjectView(R.id.re_mynews)
    RelativeLayout re_mynews;
    @InjectView(R.id.re_renzheng)
    RelativeLayout re_renzheng;

    private int GET_Sian=1;//个人签名
    MineSevice mineSevice;
    ProgressDialog progressDialog;
    PhotoManager photoManager;
    @Override
    protected void lazyLoad() {
        GetMyInfo();
    }

    @Override
    protected void initViews() {
        photoManager=new PhotoManager(this);
        tv_setting.setOnClickListener(this);
        tv_editor_signature.setOnClickListener(this);
        re_mydy.setOnClickListener(this);
        re_guanzhu.setOnClickListener(this);
        re_fans.setOnClickListener(this);
        re_health.setOnClickListener(this);
        re_losslevel.setOnClickListener(this);
        re_sportlevel.setOnClickListener(this);
        re_task.setOnClickListener(this);
        re_mynews.setOnClickListener(this);
        re_renzheng.setOnClickListener(this);
        im_banner.setOnClickListener(this);
        cir_userphoto.setOnClickListener(this);
        imageFileCropSelector = new ImageFileCropSelector(getContext());
        imageFileCropSelector.setQuality(90);
        imageFileCropSelector.setOutPutAspect(DisplayUtil.getMobileWidth(getContext()), DisplayUtil.dip2px(getContext(), 190));
        imageFileCropSelector.setOutPut(DisplayUtil.getMobileWidth(getContext()), DisplayUtil.dip2px(getContext(), 190));
        imageFileCropSelector.setCallback(new ImageFileCropSelector.Callback() {
            @Override
            public void onSuccess(String file) {
                Util.toastMsg("拍照》》》》》");
                upload(new File(file));
//                progressDialog.show();
//                im_takephicon.setVisibility(View.GONE);
//                photoManager.doUploadPhoto(UserInfoModel.getInstance().getUserId()+"", "1", file, progressDialog);
            }

            @Override
            public void onMutilSuccess(List<String> files) {
                Util.toastMsg("拍照》》》》》");
                upload(new File(files.get(0)));
//                progressDialog.show();
//                im_takephicon.setVisibility(View.GONE);
//                photoManager.doUploadPhoto(UserInfoModel.getInstance().getUserId()+"", "1", files.get(0), progressDialog);
            }

            @Override
            public void onError() {

            }
        });

    }

    @Override
    protected void initDatas() {
        mineSevice= ZillaApi.NormalRestAdapter.create(MineSevice.class);
        model=UserInfoModel.getInstance().getUser();
        tv_username.setText(model.getNickname());
        tv_renzh.setText(model.getRoleName());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tv_setting:
                startActivity(new Intent(getContext(), MySettingActivity.class));
                break;
            //跳转到我的签名
            case R.id.tv_editor_signature:
                Intent intent1 = new Intent(getContext(), EditSignaActivity.class);
                if (TextUtils.isEmpty(myinfomodel.getSignature())) {
                    intent1.putExtra("sina", "");
                } else {
                    intent1.putExtra("sina",tv_editor_signature.getText().toString());
                }
                startActivityForResult(intent1, GET_Sian);
                break;
            //跳转到我的动态
            case R.id.re_mydy:
                Intent personal = new Intent(getContext(), PersionalActivity.class);
                personal.putExtra("isFocus", 1);
                personal.putExtra("personalId", String.valueOf(UserInfoModel.getInstance().getUserId()));
                startActivity(personal);
                break;
            //跳转关注
            case R.id.re_guanzhu:
                startActivity(new Intent(getContext(), FocusFragment.class));
                break;
            //跳转粉丝
            case R.id.re_fans:
                break;
            //跳转健康记录
            case R.id.re_health:
                startActivity(new Intent(getContext(), HealthyRecordActivity.class));
                break;
            //跳转减重等级
            case R.id.re_losslevel:
                startActivity(new Intent(getContext(), LossWeightAndFatActivity.class));
                break;
            //跳转运动等级
            case R.id.re_sportlevel:
                break;
            //跳转任务与积分
            case R.id.re_task:
                break;
            //跳转消息中心
            case R.id.re_mynews:
                getActivity().startActivity(new Intent(getActivity(), Message2Activity.class));
                break;
            //跳转身份认证
            case R.id.re_renzheng:
                startActivity(new Intent(getContext(), ValidateCertificationActivity.class));
                break;
            case R.id.cir_userphoto:
            case R.id.im_banner:
                //点击编辑个人封面
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext()
                );
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            //拍照
                            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                //可以得到一个是否需要弹出解释申请该权限的提示给用户如果为true则表示可以弹
                                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) ||
                                        ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                                    //允许弹出提示
                                    ActivityCompat.requestPermissions(getActivity(),
                                            new String[]{Manifest.permission.CAMERA}, CAMERA_PREMISSION);

                                } else {
                                    //不允许弹出提示
                                    ActivityCompat.requestPermissions(getActivity(),
                                            new String[]{Manifest.permission.CAMERA}, CAMERA_PREMISSION);
                                }
                            } else {
                                imageFileCropSelector.takePhoto(getActivity());
                            }
                        } else if (which == 1) {
                            //照片
                            imageFileCropSelector.selectImage(getActivity());
                        }
                    }
                }).create().show();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageFileCropSelector.onActivityResult(requestCode, resultCode, data);
        imageFileCropSelector.getmImageCropperHelper().onActivityResult(requestCode, resultCode, data);
        //个人编辑页返回更新本页签名
        if (requestCode == GET_Sian && resultCode == getActivity().RESULT_OK) {
            if (!TextUtils.isEmpty(data.getStringExtra("sina"))) {
                tv_editor_signature.setText(data.getStringExtra("sina"));
                tv_editor_signature.setCompoundDrawables(null, null, null, null);
                myinfomodel.setSignature(data.getStringExtra("sina"));
            }

        }
    }
    //获取我的信息
    private void GetMyInfo()
    {
        mineSevice.GetMyInfo(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), new RequestCallback<ResponseData<MyInfoModel>>() {
            @Override
            public void success(ResponseData<MyInfoModel> myInfoModelResponseData, Response response) {
                int status=myInfoModelResponseData.getStatus();
                switch (status)
                {
                    case 200:
                        myinfomodel=myInfoModelResponseData.getData();
                        doSetData();
                        break;
                    default:
                        break;
                }
            }
        });
    }
    private void doSetData()
    {
        if (myinfomodel==null)
        {
            return;
        }
        else {
            tv_username.setText(myinfomodel.getUserName());
            if (!TextUtils.isEmpty(myinfomodel.getSignature()))
            {
                tv_editor_signature.setText(myinfomodel.getSignature());
                tv_editor_signature.setCompoundDrawables(null, null, null, null);
            }
            if (!TextUtils.isEmpty(myinfomodel.getAcBanner()))
            {
                Picasso.with(getContext()).load(AddressManager.get("photoHost")+myinfomodel.getAcBanner()).fit().centerCrop().into(im_banner);
            }
            if (!TextUtils.isEmpty(myinfomodel.getThPhoto()))
            {
                Picasso.with(getContext()).load(AddressManager.get("photoHost")+myinfomodel.getThPhoto()).fit()
                        .centerCrop().into(cir_userphoto);
            }
            tv_dynum.setText(myinfomodel.getDynamicNum());
            tv_guanzhunum.setText(myinfomodel.getFocusNum());
            tv_fansnum.setText(myinfomodel.getLoveNum());
            if (!TextUtils.isEmpty(myinfomodel.getRecordTime()))
            {
                String[]date=myinfomodel.getRecordTime().split("-");
                tv_updatetime.setText("更新于"+date[0]+"年"+date[1]+"月"+date[2]+"日");
            }
            tv_level.setText("您当前等级为"+myinfomodel.getLossLevel()+"级");
            tv_sportlevelnum.setText("运动等级为"+myinfomodel.getSportLevel()+"级");
            tv_news.setText("您有"+myinfomodel.getUnReadMsgNum()+"条未读消息");
            tv_renzh.setText(""+model.getRoleName());
        }
    }

    @Override
    public void getResult(PhotModel result) {
        if (result == null) {
            return;
        }
        Picasso.with(getContext()).load(AddressManager.get("photoHost") + result.getPath()).centerCrop()
                .placeholder(R.drawable.default_icon_rect).fit().into(im_banner);
    }
    private void upload(final File file){
        ZillaApi.NormalRestAdapter.create(LoginService.class).modifyPicture(UserInfoModel.getInstance().getToken(),
                UserInfoModel.getInstance().getUserId(), new TypedFile("image/*", file), new Callback<ResponseData<PhotoModel>>() {
                    @Override
                    public void success(ResponseData<PhotoModel> responseData, Response response) {
                        dialogDissmiss();
                        try {
                            int status = responseData.getStatus();
                            switch (status) {
                                case 200:
                                    PhotoModel photoModel = responseData.getData();
                                    Picasso.with(getContext()).load(file).placeholder(R.drawable.img_default).fit().error(R.drawable.img_default).into(cir_userphoto);
                                    UserModel userModel = UserInfoModel.getInstance().getUser();
                                    userModel.setPhoto(photoModel.ThubImg);
                                    UserInfoModel.getInstance().saveUserCache(userModel);
                                    break;
                                default:
                                    Util.toastMsg(responseData.getMsg());
                                    break;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        dialogDissmiss();
                        ZillaApi.dealNetError(error);
                    }
                });
    }
}
