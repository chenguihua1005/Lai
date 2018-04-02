package com.softtek.lai.module.laijumine.view;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame3.head.view.EditSignaActivity;
import com.softtek.lai.module.community.view.PersionalActivity;
import com.softtek.lai.module.healthyreport.HistoryDataActivity;
import com.softtek.lai.module.home.view.ValidateCertificationActivity;
import com.softtek.lai.module.laijumine.model.MyInfoModel;
import com.softtek.lai.module.laijumine.presenter.MineFragmentPresenter;
import com.softtek.lai.module.login.model.PhotoModel;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.net.LoginService;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.module.message2.view.Message2Activity;
import com.softtek.lai.module.sportchart.model.PhotModel;
import com.softtek.lai.module.sportchart.net.ChartService;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;
import com.sw926.imagefileselector.ImageFileCropSelector;

import org.apache.commons.lang3.StringUtils;

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
public class MineFragment extends LazyBaseFragment<MineFragmentPresenter> implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener, MineFragmentPresenter.MineFragmentCallback {

    private UserModel model;
    private MyInfoModel myinfomodel;
    CharSequence[] items = {"拍照", "照片"};
    private static final int CAMERA_PREMISSION = 100;
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
//    @InjectView(R.id.tv_level)
//    TextView tv_level;
//    @InjectView(R.id.tv_sportlevelnum)
//    TextView tv_sportlevelnum;
    @InjectView(R.id.tv_news)
    TextView tv_news;
    @InjectView(R.id.tv_renzh)
    TextView tv_renzh;
    @InjectView(R.id.lin_not_vr)
    RelativeLayout lin_not_vr;
    @InjectView(R.id.lin_is_vr)
    LinearLayout lin_is_vr;
    @InjectView(R.id.srl_refresh)
    SwipeRefreshLayout srl_refresh;
    @InjectView(R.id.tv_title)
    TextView tv_title;
//    @InjectView(R.id.ll_left)
//    LinearLayout ll_left;

    //跳转
    @InjectView(R.id.rl_setting)
    LinearLayout rl_setting;
    @InjectView(R.id.tv_editor_signature)
    TextView tv_editor_signature;
    @InjectView(R.id.re_mydy)
    RelativeLayout re_mydy;
    @InjectView(R.id.re_guanzhu)
    RelativeLayout re_guanzhu;
    @InjectView(R.id.re_fans)
    RelativeLayout re_fans;
    @InjectView(R.id.re_health)
    LinearLayout re_health;
//    @InjectView(R.id.re_losslevel)
//    RelativeLayout re_losslevel;
//    @InjectView(R.id.re_sportlevel)
//    RelativeLayout re_sportlevel;
    @InjectView(R.id.re_mynews)
    LinearLayout re_mynews;
    @InjectView(R.id.re_renzheng)
    LinearLayout re_renzheng;
    @InjectView(R.id.but_login)
    Button but_login;
    @InjectView(R.id.scro)
    ScrollView scro;


    private int GET_Sian = 1;//个人签名

    private String photo;
    private boolean isUserPhot = true;

    private boolean canRefresh = false;

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initViews() {
        srl_refresh.setEnabled(false);
        srl_refresh.setOnRefreshListener(this);
        srl_refresh.setColorSchemeColors(ContextCompat.getColor(getContext(),R.color.btn_blue_normal));
        rl_setting.setOnClickListener(this);
        tv_editor_signature.setOnClickListener(this);
        re_mydy.setOnClickListener(this);
        re_guanzhu.setOnClickListener(this);
        re_fans.setOnClickListener(this);
        re_health.setOnClickListener(this);
//        re_losslevel.setOnClickListener(this);
//        re_sportlevel.setOnClickListener(this);
        re_mynews.setOnClickListener(this);
        re_renzheng.setOnClickListener(this);
        im_banner.setOnClickListener(this);
        cir_userphoto.setOnClickListener(this);
        but_login.setOnClickListener(this);
        scro.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                srl_refresh.setEnabled(scro.getScrollY() == 0);
            }
        });
        int px = DisplayUtil.dip2px(getContext(), 300);
        imageFileCropSelector = new ImageFileCropSelector(getContext());
        imageFileCropSelector.setOutPutImageSize(px, px);
        imageFileCropSelector.setQuality(30);
        imageFileCropSelector.setOutPutAspect(1, 1);
        imageFileCropSelector.setOutPut(px, px);
        imageFileCropSelector.setCallback(new ImageFileCropSelector.Callback() {
            @Override
            public void onSuccess(String file) {
                dialogShow("上传图片");
                if (isUserPhot) {
                    upload(new File(file));
                } else {
                    uploadBanner(file);
                }
            }

            @Override
            public void onMutilSuccess(List<String> files) {
                dialogShow("上传图片");
                if (isUserPhot) {
                    upload(new File(files.get(0)));
                } else {
                    uploadBanner(files.get(0));
                }
            }

            @Override
            public void onError() {

            }
        });


    }

    @Override
    protected void initDatas() {
        setPresenter(new MineFragmentPresenter(this));
    }


    @Override
    public void onResume() {
        super.onResume();

        model = UserInfoModel.getInstance().getUser();
        if (model == null) {
            return;
        }
        String userrole = model.getUserrole();
        if (String.valueOf(Constants.VR).equals(userrole)) {
            srl_refresh.setVisibility(View.GONE);
            lin_is_vr.setVisibility(View.VISIBLE);
            tv_title.setText("我的");
//            ll_left.setVisibility(View.INVISIBLE);
            return;
        } else {
            lin_not_vr.setVisibility(View.VISIBLE);
            lin_is_vr.setVisibility(View.GONE);
        }
        photo = model.getPhoto();
        String path = AddressManager.get("photoHost");
        if (!TextUtils.isEmpty(photo)) {
            Picasso.with(getContext()).load(path + photo).fit().placeholder(R.drawable.img_default)
                    .centerCrop().error(R.drawable.img_default).into(cir_userphoto);
        }
        if (StringUtils.isEmpty(model.getNickname())) {
            tv_username.setText(model.getMobile() + "");
        } else {
            tv_username.setText(model.getNickname() + "");
        }

//        if (String.valueOf(Constants.SR).equals(userrole) || String.valueOf(Constants.PC).equals(userrole) || String.valueOf(Constants.SP).equals(userrole)) {
//            tv_renzh.setText("已认证");
//            tv_renzh.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
//        } else {
//            tv_renzh.setText("未认证");
//            tv_renzh.setTextColor(ContextCompat.getColor(getContext(), R.color.grey_font));
//        }
        getPresenter().getMyInfo();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.but_login:
                Intent login = new Intent(getContext(), LoginActivity.class);
                login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(login);
                break;
            case R.id.rl_setting:
                startActivity(new Intent(getContext(), MySettingActivity.class));
                break;
            //跳转到我的签名
            case R.id.tv_editor_signature:
                if (myinfomodel == null) {
                    return;
                }
                Intent intent1 = new Intent(getContext(), EditSignaActivity.class);
                if (TextUtils.isEmpty(myinfomodel.getSignature())) {
                    intent1.putExtra("sina", "");
                } else {
                    intent1.putExtra("sina", tv_editor_signature.getText().toString());
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
                String focusnum = tv_guanzhunum.getText().toString();
                if (TextUtils.isEmpty(focusnum)) {
                    return;
                }
                Intent intent = new Intent(getContext(), FocusActivity.class);
                intent.putExtra("focusnum", Integer.parseInt(focusnum));
                startActivity(intent);
                break;
            //跳转粉丝
            case R.id.re_fans:
                String fansnum = tv_fansnum.getText().toString();
                if (TextUtils.isEmpty(fansnum)) {
                    return;
                }
                Intent focusintent = new Intent(getContext(), FansActivity.class);
                focusintent.putExtra("fansnum", Integer.parseInt(fansnum));
                startActivity(focusintent);
                break;
            //跳转历史测量数据
            case R.id.re_health:
                startActivity(new Intent(getContext(), HistoryDataActivity.class).putExtra("accountId",UserInfoModel.getInstance().getUserId()));
                break;
            //跳转减重等级
//            case R.id.re_losslevel:
//                startActivity(new Intent(getContext(), LossWeightAndFatActivity.class));
//                break;
            //跳转运动等级
//            case R.id.re_sportlevel:
//                break;
            //跳转消息中心
            case R.id.re_mynews:
                getActivity().startActivity(new Intent(getActivity(), Message2Activity.class));
                break;
            //跳转身份认证
            case R.id.re_renzheng:
                Intent validateIntent = new Intent(getContext(), ValidateCertificationActivity.class);
                validateIntent.putExtra("CanRefreshCertification",canRefresh);
                startActivity(validateIntent);
                break;
            case R.id.im_banner:
            case R.id.cir_userphoto:
                isUserPhot = R.id.cir_userphoto == view.getId();
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            //拍照
                            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PREMISSION);
                            } else {
                                imageFileCropSelector.takePhoto(MineFragment.this);
                            }
                        } else if (which == 1) {
                            //照片
                            imageFileCropSelector.selectSystemImage(MineFragment.this);
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
        if (requestCode == GET_Sian && resultCode == Activity.RESULT_OK) {
            if (!TextUtils.isEmpty(data.getStringExtra("sina"))) {
                tv_editor_signature.setText(data.getStringExtra("sina"));
                myinfomodel.setSignature(data.getStringExtra("sina"));
            }
        }
    }

    private void upload(final File file) {
        ZillaApi.NormalRestAdapter.create(LoginService.class).modifyPicture(UserInfoModel.getInstance().getToken(),
                UserInfoModel.getInstance().getUserId(), new TypedFile("image/*", file), new Callback<ResponseData<PhotoModel>>() {
                    @Override
                    public void success(ResponseData<PhotoModel> responseData, Response response) {

                        try {
                            int status = responseData.getStatus();
                            switch (status) {
                                case 200:
                                    PhotoModel photoModel = responseData.getData();
                                    UserModel userModel = UserInfoModel.getInstance().getUser();
                                    userModel.setPhoto(photoModel.ThubImg);
                                    UserInfoModel.getInstance().saveUserCache(userModel);
                                    Picasso.with(getContext()).load(file).centerCrop().placeholder(R.drawable.img_default).error(R.drawable.img_default).fit().into(cir_userphoto);
                                    dialogDissmiss();
                                    break;
                                default:
                                    dialogDissmiss();
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

    private void uploadBanner(final String file) {
        ZillaApi.NormalRestAdapter.create(ChartService.class).doUploadPhoto(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId() + "",
                "1", new TypedFile("image/png", new File(file)), new RequestCallback<ResponseData<PhotModel>>() {
                    @Override
                    public void success(ResponseData<PhotModel> photModelResponseData, Response response) {
                        try {
                            int status = photModelResponseData.getStatus();
                            switch (status) {
                                case 200:
                                    Picasso.with(getContext()).load(new File(file)).centerCrop().placeholder(R.drawable.default_icon_rect).fit().into(im_banner);
                                    dialogDissmiss();
                                    break;
                                default:
                                    dialogDissmiss();
                                    Util.toastMsg(photModelResponseData.getMsg());
                                    break;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        super.failure(error);
                        dialogDissmiss();
                    }
                });
    }

    // Android 6.0的动态权限
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imageFileCropSelector.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if (requestCode == CAMERA_PREMISSION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                imageFileCropSelector.takePhoto(MineFragment.this);

            }
        }
    }

    @Override
    public void onRefresh() {
        model = UserInfoModel.getInstance().getUser();
        if (model == null) {
            return;
        }
        String userrole = model.getUserrole();
        if (String.valueOf(Constants.VR).equals(userrole)) {
            srl_refresh.setVisibility(View.GONE);
            lin_is_vr.setVisibility(View.VISIBLE);
            return;
        } else {
            lin_not_vr.setVisibility(View.VISIBLE);
            lin_is_vr.setVisibility(View.GONE);
        }
        photo = model.getPhoto();
        String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
        if (!TextUtils.isEmpty(photo)) {
            Picasso.with(getContext()).load(path + photo).fit().placeholder(R.drawable.img_default)
                    .centerCrop().error(R.drawable.img_default).into(cir_userphoto);
        }
        if (StringUtils.isEmpty(model.getNickname())) {
            tv_username.setText(model.getMobile() + "");
        } else {
            tv_username.setText(model.getNickname() + "");
        }

//        if (String.valueOf(Constants.SR).equals(userrole) || String.valueOf(Constants.PC).equals(userrole) || String.valueOf(Constants.SP).equals(userrole)) {
//            tv_renzh.setText("已认证");
//            tv_renzh.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
//        } else {
//            tv_renzh.setText("未认证");
//            tv_renzh.setTextColor(ContextCompat.getColor(getContext(), R.color.grey_font));
//        }
        getPresenter().getMyInfo();
        srl_refresh.setRefreshing(false);
    }

    @Override
    public void myInfoCallback(MyInfoModel model) {
        myinfomodel = model;
        if (myinfomodel == null) {
            return;
        }
        canRefresh = model.isCanRefreshCertification();
        if (!TextUtils.isEmpty(myinfomodel.getSignature())) {
            tv_editor_signature.setText(myinfomodel.getSignature());
        } else {
            tv_editor_signature.setText("编辑个性签名");
        }
        if (!TextUtils.isEmpty(myinfomodel.getAcBanner())) {
            Picasso.with(getContext()).load(AddressManager.get("photoHost") + myinfomodel.getAcBanner()).placeholder(R.drawable.default_icon_rect).fit().centerCrop().into(im_banner);
        } else {
            Picasso.with(getContext()).load(AddressManager.get("photoHost") + myinfomodel.getAcBanner()).fit().centerCrop().into(im_banner);
        }
        tv_dynum.setText(myinfomodel.getDynamicNum());
        tv_guanzhunum.setText(myinfomodel.getFocusNum());
        tv_fansnum.setText(myinfomodel.getLoveNum());
        if (!TextUtils.isEmpty(myinfomodel.getRecordTime())) {
            String[] date = myinfomodel.getRecordTime().split("-");
            tv_updatetime.setText("更新于" + date[0] + "年" + date[1] + "月" + date[2] + "日");
        }
//        if ("0".equals(myinfomodel.getLossLevel())) {
//            tv_level.setText("暂无减重等级");
//        } else {
//            tv_level.setText("您当前等级为" + myinfomodel.getLossLevel() + "级");
//        }
//        tv_sportlevelnum.setText("开发中，敬请期待");
        if ("0".equals(myinfomodel.getUnReadMsgNum())) {
            tv_news.setText("您有" + myinfomodel.getUnReadMsgNum() + "条未读消息");
        } else {
            String strs = "您有" + myinfomodel.getUnReadMsgNum() + "条未读消息";
            int length = myinfomodel.getUnReadMsgNum().length();
            SpannableStringBuilder style = new SpannableStringBuilder(strs);
            style.setSpan(new ForegroundColorSpan(Color.RED), 2, 2 + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv_news.setText(style);
        }

        if (myinfomodel.getCertification() != null){
            if (!myinfomodel.getCertification().equals("")){
                tv_renzh.setText(myinfomodel.getCertification());
            }else {
                tv_renzh.setText("未认证");
            }
        }else {
            tv_renzh.setText("未认证");
        }

    }
}
