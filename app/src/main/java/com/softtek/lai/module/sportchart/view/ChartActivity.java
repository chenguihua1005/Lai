package com.softtek.lai.module.sportchart.view;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.message2.model.PhotosModel;
import com.softtek.lai.module.sport2.model.ScoreModel;
import com.softtek.lai.module.sport2.net.GradesService;
import com.softtek.lai.module.sportchart.model.PhotModel;
import com.softtek.lai.module.sportchart.model.StepCountModel;
import com.softtek.lai.module.sportchart.model.StepListModel;
import com.softtek.lai.module.sportchart.net.ChartService;
import com.softtek.lai.module.sportchart.presenter.ChartManager;
import com.softtek.lai.module.sportchart.presenter.GetProinfoImpl;
import com.softtek.lai.module.sportchart.presenter.IGetProinfopresenter;
import com.softtek.lai.module.sportchart.presenter.PhotoManager;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.SelectPicPopupWindow;
import com.softtek.lai.widgets.chart.BrokenLine;
import com.softtek.lai.widgets.chart.Entry;
import com.squareup.picasso.Picasso;
import com.sw926.imagefileselector.ImageFileCropSelector;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

/**
 * Created by lareina.qiao on 10/19/2016.
 */
@InjectLayout(R.layout.actitivity_chart)
public class ChartActivity extends BaseActivity implements ChartManager.ChartManagerCallback, View.OnClickListener, PhotoManager.PhotoManagerCallback {
    ChartManager chartManager;
    PhotoManager photoManager;
    @InjectView(R.id.iv_perpage_banner)
    ImageView iv_perpage_banner;
    @InjectView(R.id.im_sport_personhead)
    ImageView im_sport_personhead;
    @InjectView(R.id.tv_perpagename)
    TextView tv_perpagename;
    @InjectView(R.id.tv_step)
    TextView tv_step;
    @InjectView(R.id.tv_kilometre)
    TextView tv_kilometre;
    @InjectView(R.id.btn_add)
    CheckBox btn_add;
    @InjectView(R.id.chart_sport)
    com.softtek.lai.widgets.chart.Chart chart;
    @InjectView(R.id.bt_sport_left)
    Button bt_sport_left;
    @InjectView(R.id.bt_sport_right)
    Button bt_sport_right;
    @InjectView(R.id.fl_pers_right)
    LinearLayout fl_pers_right;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.rel_sy)
    RelativeLayout rel_sy;
    @InjectView(R.id.toolbar1)
    RelativeLayout toolbar1;
    DateForm dateForm;
    @InjectView(R.id.im_cam)
    ImageView im_cam;

    List<Entry> dates = new ArrayList<>();
    List<String> days = new ArrayList<>();
    List<String> day = new ArrayList<>();
    char type = '6';
    int n = 7;
    boolean state = true;
    private ProgressDialog progressDialog;
    private ChartService service;
    CharSequence[] items = {"拍照", "照片"};
    private static final int CAMERA_PREMISSION = 101;
    private ImageFileCropSelector imageFileCropSelector;
    private static final int Chart = 3;
    String Userid = "0";
    private static final int LOCATION_PREMISSION = 100;
    private IGetProinfopresenter iGetProinfopresenter;
    ScoreModel scoreModel;
    String value;
    String url;
    String title_value;
    String isFocusid = "0";
    SelectPicPopupWindow menuWindow;
    List<Entry> list = Arrays.asList(new Entry(0, 0),
            new Entry(1, 0),
            new Entry(2, 0),
            new Entry(3, 0),
            new Entry(4, 0),
            new Entry(5, 0),
            new Entry(6, 0));//默认7天步数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initViews() {
        /*状态栏透明，标题栏高度适应*/
        if (DisplayUtil.getSDKInt() > 18) {
            tintManager.setStatusBarAlpha(0);
            int status = DisplayUtil.getStatusHeight(this);
            /*标题栏适配状态栏高度*/
            RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) toolbar1.getLayoutParams();
            params1.topMargin = status;
            toolbar1.setLayoutParams(params1);
        }
        Userid = UserInfoModel.getInstance().getUser().getUserid();
        photoManager = new PhotoManager(this);
        imageFileCropSelector = new ImageFileCropSelector(ChartActivity.this);
        imageFileCropSelector.setQuality(90);
        imageFileCropSelector.setOutPutAspect(DisplayUtil.getMobileWidth(ChartActivity.this), DisplayUtil.dip2px(ChartActivity.this, 190));
        imageFileCropSelector.setOutPut(DisplayUtil.getMobileWidth(ChartActivity.this), DisplayUtil.dip2px(ChartActivity.this, 190));
        imageFileCropSelector.setCallback(new ImageFileCropSelector.Callback() {
            @Override
            public void onSuccess(String file) {
                progressDialog.show();
                im_cam.setVisibility(View.GONE);
                photoManager.doUploadPhoto(Userid, "1", file, progressDialog);
            }

            @Override
            public void onMutilSuccess(List<String> files) {
                progressDialog.show();
                im_cam.setVisibility(View.GONE);
                photoManager.doUploadPhoto(Userid, "1", files.get(0), progressDialog);
            }

            @Override
            public void onError() {

            }
        });
        btn_add.setOnClickListener(this);
        iv_perpage_banner.setOnClickListener(this);
        bt_sport_left.setOnClickListener(this);
        bt_sport_right.setOnClickListener(this);
        fl_pers_right.setOnClickListener(this);
        ll_left.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        iGetProinfopresenter = new GetProinfoImpl(this);
        chartManager = new ChartManager(this);

        dates.clear();
        day.clear();
        dateForm = new DateForm();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("加载中...");
        progressDialog.setCanceledOnTouchOutside(false);
        String nowdate7 = getPeriodDate(type, 0).toString();
        String nowdate6 = getPeriodDate(type, 1).toString();
        String nowdate5 = getPeriodDate(type, 2).toString();
        String nowdate4 = getPeriodDate(type, 3).toString();
        String nowdate3 = getPeriodDate(type, 4).toString();
        String nowdate2 = getPeriodDate(type, 5).toString();
        String nowdate1 = getPeriodDate(type, 6).toString();
        days.add(formdate(nowdate1, false));
        days.add(formdate(nowdate2, true));
        days.add(formdate(nowdate3, true));
        days.add(formdate(nowdate4, true));
        days.add(formdate(nowdate5, true));
        days.add(formdate(nowdate6, true));
        days.add(formdate(nowdate7, true));
        day.add(dateForm.getDateform(nowdate1));
        day.add(dateForm.getDateform(nowdate2));
        day.add(dateForm.getDateform(nowdate3));
        day.add(dateForm.getDateform(nowdate4));
        day.add(dateForm.getDateform(nowdate5));
        day.add(dateForm.getDateform(nowdate6));
        day.add(dateForm.getDateform(nowdate7));
        progressDialog.show();
        Intent intent = getIntent();
        isFocusid = intent.getStringExtra("isFocusid");
        chartManager.doGetStepCount(isFocusid, dateForm.getDateform(nowdate1), dateForm.getDateform(nowdate7));
    }

    /**
     * 获取阶段日期
     *
     * @param dateType
     * @author Yangtse
     */
    //使用方法 char datetype = '7';
    public static StringBuilder getPeriodDate(char dateType, int n) {
        Calendar c = Calendar.getInstance(); // 当时的日期和时间
        int hour; // 需要更改的小时
        int day; // 需要更改的天数
        switch (dateType) {
            case '0': // 1小时前
                hour = c.get(Calendar.HOUR_OF_DAY) - 1;
                c.set(Calendar.HOUR_OF_DAY, hour);
                break;
            case '1': // 2小时前
                hour = c.get(Calendar.HOUR_OF_DAY) - 2;
                c.set(Calendar.HOUR_OF_DAY, hour);
                break;
            case '2': // 3小时前
                hour = c.get(Calendar.HOUR_OF_DAY) - 3;
                c.set(Calendar.HOUR_OF_DAY, hour);
                break;
            case '3': // 6小时前
                hour = c.get(Calendar.HOUR_OF_DAY) - 6;
                c.set(Calendar.HOUR_OF_DAY, hour);
                break;
            case '4': // 12小时前
                hour = c.get(Calendar.HOUR_OF_DAY) - 12;
                c.set(Calendar.HOUR_OF_DAY, hour);
                break;
            case '5': // 一天前
                day = c.get(Calendar.DAY_OF_MONTH) - 1;
                c.set(Calendar.DAY_OF_MONTH, day);
                break;
            case '6': // 一星期前
                day = c.get(Calendar.DAY_OF_MONTH) - n;
                c.set(Calendar.DAY_OF_MONTH, day);
                break;
            case '7': // 一个月前
                day = c.get(Calendar.DAY_OF_MONTH) - 30 * n;
                c.set(Calendar.DAY_OF_MONTH, day);
                break;
        }
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        StringBuilder strForwardDate = new StringBuilder().append(mYear).append(
                (mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append(
                (mDay < 10) ? "0" + mDay : mDay);
        return strForwardDate;
    }

    public String formdate(String nowdate, boolean getDay) {

        if (getDay) {
            return nowdate.substring(6, 8);
        } else {
            String date;
            if (nowdate.substring(4, 5).equals("0")) {
                date = nowdate.substring(5, 6) + "月" + nowdate.substring(6, 8);
            } else {
                date = nowdate.substring(4, 6) + "月" + nowdate.substring(6, 8);
            }
            return date;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.btn_add:
                if (btn_add.isChecked()) {
                    doCancleFocusAccount(UserInfoModel.getInstance().getUser().getUserid(), isFocusid);
                } else {
                    doFocusAccount(UserInfoModel.getInstance().getUser().getUserid(), isFocusid);
                }
                break;
            case R.id.bt_sport_left:
                if (!state) {
                    n = n + 7;
                }
                state = true;
                days.clear();
                day.clear();
                dates.clear();
                String nowdate7 = getPeriodDate(type, n) + "";
                String nowdate6 = getPeriodDate(type, n + 1) + "";
                String nowdate5 = getPeriodDate(type, n + 2) + "";
                String nowdate4 = getPeriodDate(type, n + 3) + "";
                String nowdate3 = getPeriodDate(type, n + 4) + "";
                String nowdate2 = getPeriodDate(type, n + 5) + "";
                String nowdate1 = getPeriodDate(type, n + 6) + "";
                days.add(formdate(nowdate1, false));
                days.add(formdate(nowdate2, true));
                days.add(formdate(nowdate3, true));
                days.add(formdate(nowdate4, true));
                days.add(formdate(nowdate5, true));
                days.add(formdate(nowdate6, true));
                days.add(formdate(nowdate7, true));
                day.add(dateForm.getDateform(nowdate1));
                day.add(dateForm.getDateform(nowdate2));
                day.add(dateForm.getDateform(nowdate3));
                day.add(dateForm.getDateform(nowdate4));
                day.add(dateForm.getDateform(nowdate5));
                day.add(dateForm.getDateform(nowdate6));
                day.add(dateForm.getDateform(nowdate7));
                progressDialog.show();
                chartManager.doGetStepCount(isFocusid, dateForm.getDateform(nowdate1), dateForm.getDateform(nowdate7));
                n = n + 7;
                bt_sport_right.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_sport_right:
                if (state) {
                    n = n - 14;
                } else {
                    n = n - 7;
                }
                dates.clear();
                day.clear();
                days.clear();
                String nowdat7 = getPeriodDate(type, n) + "";
                String nowdat6 = getPeriodDate(type, n + 1) + "";
                String nowdat5 = getPeriodDate(type, n + 2) + "";
                String nowdat4 = getPeriodDate(type, n + 3) + "";
                String nowdat3 = getPeriodDate(type, n + 4) + "";
                String nowdat2 = getPeriodDate(type, n + 5) + "";
                String nowdat1 = getPeriodDate(type, n + 6) + "";

                days.add(formdate(nowdat1, false));
                days.add(formdate(nowdat2, true));
                days.add(formdate(nowdat3, true));
                days.add(formdate(nowdat4, true));
                days.add(formdate(nowdat5, true));
                days.add(formdate(nowdat6, true));
                days.add(formdate(nowdat7, true));
                day.add(dateForm.getDateform(nowdat1));
                day.add(dateForm.getDateform(nowdat2));
                day.add(dateForm.getDateform(nowdat3));
                day.add(dateForm.getDateform(nowdat4));
                day.add(dateForm.getDateform(nowdat5));
                day.add(dateForm.getDateform(nowdat6));
                day.add(dateForm.getDateform(nowdat7));
                progressDialog.show();
                chartManager.doGetStepCount(isFocusid, dateForm.getDateform(nowdat1), dateForm.getDateform(nowdat7));
                state = false;
                if (nowdat7.equals(getPeriodDate(type, 0) + ""))
                    bt_sport_right.setVisibility(View.GONE);
                break;
            case R.id.iv_perpage_banner:
                //点击编辑banner图片按钮
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            //拍照
                            if (ActivityCompat.checkSelfPermission(ChartActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                //可以得到一个是否需要弹出解释申请该权限的提示给用户如果为true则表示可以弹
                                if (ActivityCompat.shouldShowRequestPermissionRationale(ChartActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) ||
                                        ActivityCompat.shouldShowRequestPermissionRationale(ChartActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                                    //允许弹出提示
                                    ActivityCompat.requestPermissions(ChartActivity.this,
                                            new String[]{Manifest.permission.CAMERA}, CAMERA_PREMISSION);

                                } else {
                                    //不允许弹出提示
                                    ActivityCompat.requestPermissions(ChartActivity.this,
                                            new String[]{Manifest.permission.CAMERA}, CAMERA_PREMISSION);
                                }
                            } else {
                                imageFileCropSelector.takePhoto(ChartActivity.this);
                            }
                        } else if (which == 1) {
                            //照片
                            imageFileCropSelector.selectSystemImage(ChartActivity.this);
                        }
                    }
                }).create().show();
                break;
            case R.id.fl_pers_right:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    com.github.snowdream.android.util.Log.i("检查权限。。。。");
                    //可以得到一个是否需要弹出解释申请该权限的提示给用户如果为true则表示可以弹
                    if (
                            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        //允许弹出提示
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                LOCATION_PREMISSION);

                    } else {
                        //不允许弹出提示
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                LOCATION_PREMISSION);
                    }
                } else {
                    setShare();
                }
                break;

        }


    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageFileCropSelector.onActivityResult(requestCode, resultCode, data);
        imageFileCropSelector.getmImageCropperHelper().onActivityResult(requestCode, resultCode, data);
        if (requestCode == Chart && resultCode == RESULT_OK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        imageFileCropSelector.takePhoto(ChartActivity.this);
                    } else if (which == 1) {
                        //照片
                        imageFileCropSelector.selectImage(ChartActivity.this);
                    }
                }
            }).create().show();
        }

    }

    //6.0权限回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PREMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    imageFileCropSelector.takePhoto(ChartActivity.this);
                }
                break;
            case LOCATION_PREMISSION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    setShare();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                break;
        }
    }

    private void setShare() {
        dialogShow("加载中");
        Bitmap b1 = getViewBitmap(chart);
        savePic(b1, "/sdcard/scores.png");
        GradesService service = ZillaApi.NormalRestAdapter.create(GradesService.class);
        String token = UserInfoModel.getInstance().getToken();
        UserModel model = UserInfoModel.getInstance().getUser();
        service.getMineScore(token, model.getUserid(), new Callback<ResponseData<ScoreModel>>() {
            @Override
            public void success(ResponseData<ScoreModel> responseData, Response response) {
                int status = responseData.getStatus();
                switch (status) {
                    case 200:
                        scoreModel = responseData.getData();
                        System.out.println("scoreModel:" + scoreModel);
                        iGetProinfopresenter.upload("/sdcard/scores.png");
                        break;
                    default:
                        dialogDissmiss();
                        Util.toastMsg(responseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                dialogDissmiss();
                ZillaApi.dealNetError(error);
            }
        });
    }

    // 保存到sdcard
    public static void savePic(Bitmap b, String strFileName) {
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(strFileName);
            if (null != fos) {
                b.compress(Bitmap.CompressFormat.PNG, 80, fos);
                fos.flush();
                fos.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 截取scrollview的屏幕
     **/
    public static Bitmap getBitmapByView(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap;
        // 获取listView实际高度
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
        }
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }

    /**
     * 把View对象转换成bitmap
     */
    public static Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);

        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);

        // Reset the drawing cache background color to fully transparent
        // for the duration of this operation
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);

        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        // Restore the view
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);

        return bitmap;
    }

    @Subscribe
    public void onEvent(PhotosModel photModel) {
        dialogDissmiss();
        System.out.println(photModel);
        if (UserInfoModel.getInstance().getUser() == null) {
            return;
        }
        String path = AddressManager.get("shareHost");
        url = path + "ShareMineSportScore?AccountId=" + UserInfoModel.getInstance().getUser().getUserid() + "&Imgpath=" + photModel.getImg();
        System.out.println("url:" + url);
        value = "我已累计跑步" + scoreModel.getTodayKaluli() + "km，总步数" + scoreModel.getTotalStep() + "步，今日全国排名第" + scoreModel.getContryDayOrder() + "名，跑团排名第" + scoreModel.getDayOrder() + "名。快来和我一起运动吧！";
        System.out.println("value:" + value);
        title_value = "莱运动，陪伴我运动第" + scoreModel.getRgTime() + "天";
        menuWindow = new SelectPicPopupWindow(ChartActivity.this, itemsOnClick);
        //显示窗口
        menuWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        menuWindow.showAtLocation(ChartActivity.this.findViewById(R.id.Re_pers_page), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
    }

    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.lin_weixin:
                    new ShareAction(ChartActivity.this)
                            .setPlatform(SHARE_MEDIA.WEIXIN)
                            .withTitle(title_value)
                            .withText(value)
                            .withTargetUrl(url)
                            .withMedia(new UMImage(ChartActivity.this, R.drawable.img_share_logo))
                            .share();
                    break;
                case R.id.lin_circle:
                    new ShareAction(ChartActivity.this)
                            .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                            .withTitle(title_value)
                            .withText(value)
                            .withTargetUrl(url)
                            .withMedia(new UMImage(ChartActivity.this, R.drawable.img_share_logo))
                            .share();
                    break;
                case R.id.lin_sina:
                    new ShareAction(ChartActivity.this)
                            .setPlatform(SHARE_MEDIA.SINA)
                            .withText(value + url)
                            .withMedia(new UMImage(ChartActivity.this, R.drawable.img_share_logo))
                            .share();
                    break;
                default:
                    break;
            }
        }
    };

    /*
    * 关注
    * */
    void doFocusAccount(String accoutid, String focusaccid) {
        service = ZillaApi.NormalRestAdapter.create(ChartService.class);
        service.doFocusAccount(UserInfoModel.getInstance().getToken(), accoutid, focusaccid, new RequestCallback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                if (responseData.getStatus() == 200) {
                    btn_add.setChecked(false);
                } else {
                    Util.toastMsg(responseData.getMsg());
                }
            }
        });

    }

    /*
   * 取消关注
   * */
    void doCancleFocusAccount(String accoutid, String focusaccid) {
        service = ZillaApi.NormalRestAdapter.create(ChartService.class);
        service.doCancleFocusAccount(UserInfoModel.getInstance().getToken(), accoutid, focusaccid, new RequestCallback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                if (responseData.getStatus() == 200) {
                    btn_add.setChecked(true);
                } else {
                    Util.toastMsg(responseData.getMsg());
                }
            }
        });

    }

    @Override
    public void getResu(StepCountModel result) {
        try {
            if (progressDialog != null)
                progressDialog.dismiss();
            if (result == null) {
                return;
            }
            if (!TextUtils.isEmpty(result.getAcBanner())) {
                im_cam.setVisibility(View.GONE);
                Picasso.with(this).load(AddressManager.get("photoHost") + result.getAcBanner()).centerCrop()
                        .placeholder(R.drawable.default_icon_rect).fit().error(R.drawable.default_icon_rect).into(iv_perpage_banner);
            }
            if (!TextUtils.isEmpty(result.getPhoto())) {
                Picasso.with(this).load(AddressManager.get("photoHost") + result.getPhoto()).centerCrop().placeholder(R.drawable.img_default)
                        .fit().error(R.drawable.img_default).into(im_sport_personhead);
            }
            if (result.getUsername().equals(UserInfoModel.getInstance().getUser().getNickname())) {
                tv_perpagename.setText("我的主页");
                btn_add.setVisibility(View.GONE);
            } else {
                tv_perpagename.setText(result.getUsername());
                tv_perpagename.append("的主页");
                iv_perpage_banner.setClickable(false);
                fl_pers_right.setVisibility(View.INVISIBLE);
                im_cam.setVisibility(View.GONE);
            }
            if (TextUtils.isEmpty(result.getTotalStep())) {
                tv_step.setText("--");
                tv_kilometre.setText("--");
            } else {

                tv_step.setText(result.getTotalStep());
                if (Float.parseFloat(result.getTotalStep()) / 1428 < 0.04) {
                    tv_kilometre.setText("0");
                } else {
                    DecimalFormat df = new DecimalFormat("0.0");
                    tv_kilometre.setText(df.format(Float.parseFloat(result.getTotalStep()) / 1428) + "");
                }
            }
            if (result.getIsFocus().equals("0")) {
                btn_add.setChecked(true);
            } else {
                btn_add.setChecked(false);
            }
            dates.clear();
            //步数
            List<StepListModel> steps = result.getStepList();
            int maxStep = 0;
            if (steps.size() < days.size()) {
                //如果步数数量小于天数表示步数的数量不满7天
                dates.addAll(list);//添加默认天数以及步数
                for (int j = days.size() - 1; j >= 0; j--) {
                    for (int i = 0; i <= steps.size() - 1; i++) {
                        //判断是不是那一天，如果是就重新给那一天设置步数
                        if (day.get(j).equals(steps.get(i).getDate())) {
                            Entry entry = dates.get(j);
                            int step = Integer.parseInt(steps.get(i).getTotalCnt());
                            maxStep = step > maxStep ? step : maxStep;
                            entry.setVal(step);
                            dates.set(j, entry);
                            break;
                        }
                    }
                }
            } else {
                //如果步数大于或者等于天数
                for (int i = 6; i >= 0; i--) {
                    int step = Integer.parseInt(steps.get(6 - i).getTotalCnt());
                    maxStep = step > maxStep ? step : maxStep;
                    Entry entry = new Entry(6 - i, step);
                    dates.add(entry);
                }
            }
            chart.setDate(days, dates, maxStep, BrokenLine.INTEGER);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getResult(PhotModel result) {
        if (result == null) {
            return;
        }
        Picasso.with(ChartActivity.this).load(AddressManager.get("photoHost") + result.getPath()).centerCrop()
                .placeholder(R.drawable.default_icon_rect).fit().into(iv_perpage_banner);
    }
}
