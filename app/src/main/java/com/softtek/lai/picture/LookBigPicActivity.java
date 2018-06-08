package com.softtek.lai.picture;

import android.Manifest;
import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.picture.adapter.ImageScaleAdapter;
import com.softtek.lai.picture.bean.EaluationPicBean;
import com.softtek.lai.picture.util.BigPicService;
import com.softtek.lai.picture.util.EvaluateUtil;
import com.softtek.lai.picture.view.HackyViewPager;
import com.softtek.lai.utils.DisplayUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import uk.co.senab.photoview.PhotoView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.util.Util;

/**
 *
 * Created by ggx on 2017/2/20.
 */
public class LookBigPicActivity extends Activity implements  ViewTreeObserver.OnPreDrawListener, ImageScaleAdapter.SavePic {
    private List<EaluationPicBean> picDataList;
    private List<View> dotList = new ArrayList<>();
    public static String PICDATALIST = "PICDATALIST";
    public static String CURRENTITEM = "CURRENTITEM";
    private int currentItem;
    public int mPositon;
    private ImageScaleAdapter imageScaleAdapter;
    private HackyViewPager viewPager;
    private LinearLayout ll_dots;
    private TextView tv_pager;
    private LinearLayout ll_root;
    private int height;
    private int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //toggleHideyBar();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_big_pic);
        getData();
        intiView();
        setUpEvent();
        initDot(currentItem);

    }
    public void toggleHideyBar() {

        // The UI options currently enabled are represented by a bitfield.
        // getSystemUiVisibility() gives us that bitfield.
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        // Immersive mode: Backward compatible to KitKat.
        // Note that this flag doesn't do anything by itself, it only augments the behavior
        // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
        // all three flags are being toggled together.
        // Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
        // Sticky immersive mode differs in that it makes the navigation and status bars
        // semi-transparent, and the UI flag does not get cleared when the user interacts with
        // the screen.
        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }else
        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }



        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }

    private void setUpEvent() {
        viewPager.setAdapter(imageScaleAdapter);
        viewPager.setCurrentItem(currentItem);
        setTitleNum(currentItem);
        setPagerChangeListener(viewPager);
        viewPager.getViewTreeObserver().addOnPreDrawListener(this);
    }

    private void getData() {
        Intent intent = getIntent();
        picDataList = (List<EaluationPicBean>) intent.getSerializableExtra("PICDATALIST");
        currentItem = intent.getIntExtra(CURRENTITEM, 0);
        mPositon = currentItem;
        imageScaleAdapter = new ImageScaleAdapter(this, this, picDataList);
    }

    private void intiView() {
        ll_dots = (LinearLayout) findViewById(R.id.ll_dots);
        ll_root = (LinearLayout) findViewById(R.id.ll_root);
        tv_pager = (TextView) findViewById(R.id.tv_pager);
        viewPager = (HackyViewPager) findViewById(R.id.viewpager);
    }

    /**
     * 绘制前开始动画
     *
     */
    @Override
    public boolean onPreDraw() {
        viewPager.getViewTreeObserver().removeOnPreDrawListener(this);
        final View view = imageScaleAdapter.getPrimaryItem();
        final PhotoView imageView = (PhotoView) ((ViewGroup) view).getChildAt(0);

        computeImageWidthAndHeight(imageView);

        final EaluationPicBean ealuationPicBean = picDataList.get(mPositon);
        final float vx = ealuationPicBean.width * 1.0f / width;
        final float vy = ealuationPicBean.height * 1.0f / height;

        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1.0f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float animatedFraction = animation.getAnimatedFraction();

                view.setTranslationX(EvaluateUtil.evaluateInt(animatedFraction, ealuationPicBean.x + ealuationPicBean.width / 2 - imageView.getWidth() / 2, 0));
                view.setTranslationY(EvaluateUtil.evaluateInt(animatedFraction, ealuationPicBean.y + ealuationPicBean.height / 2 - imageView.getHeight() / 2, 0));
                view.setScaleX(EvaluateUtil.evaluateFloat(animatedFraction, vx, 1));
                view.setScaleY(EvaluateUtil.evaluateFloat(animatedFraction, vy, 1));

                ll_root.setBackgroundColor((int) EvaluateUtil.evaluateArgb(animatedFraction, 0x0, 0xff000000));
            }
        });

        addIntoListener(valueAnimator);
        valueAnimator.setDuration(300);
        valueAnimator.start();
        return true;
    }

    /**
     * 进场动画过程监听
     *
     * @param valueAnimator
     */
    private void addIntoListener(ValueAnimator valueAnimator) {
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                ll_root.setBackgroundColor(0x0);
                ll_dots.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                ll_dots.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    /**
     * 页面改动监听
     *
     * @param viewPager
     */
    private void setPagerChangeListener(HackyViewPager viewPager) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPositon = position;
                setTitleNum(position);
                initDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setTitleNum(int position) {
        tv_pager.setText((position + 1) + "/" + picDataList.size());
    }

    /**
     * 初始化轮播图点
     */
    private void initDot(int index) {
        // 清空点所在集合
        dotList.clear();
        ll_dots.removeAllViews();
        for (int i = 0; i < picDataList.size(); i++) {
            ImageView view = new ImageView(this);
            if (i == index || picDataList.size() == 1) {
                view.setBackgroundResource(R.mipmap.type_selected);
            } else {
                view.setBackgroundResource(R.mipmap.type_normal);
            }
            // 指定点的大小
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    DisplayUtil.dip2px(this, 5), DisplayUtil.dip2px(this, 5));
            // 指定点的间距
            layoutParams.setMargins(DisplayUtil.dip2px(this, 2), 0, DisplayUtil.dip2px(this, 2), 0);
            // 添加到线性布局中
            ll_dots.addView(view, layoutParams);
            // 添加到集合中去
            dotList.add(view);
        }
    }


    /**
     * 旋转
     *
     * @param v
     */
//    private void rotation(float v) {
//        View primaryView = imageScaleAdapter.getPrimaryItem();
//        if (primaryView != null) {
//            primaryView.getRotation();
//            float rotation = primaryView.getRotation();
//            primaryView.setRotation(rotation + v);
//            primaryView.requestLayout();
//        }
//    }

    /**
     * 下面几个回调主要是优化体验的，模范的QQ空间的看图模式
     */
//    @Override
//    public void isDown() {
//        ll_bottom.setVisibility(View.GONE);
//    }
//
//    @Override
//    public void isUp() {
//        ll_bottom.setVisibility(View.VISIBLE);
//    }
//
//    @Override
//    public void isCancel() {
//        ll_bottom.setVisibility(View.VISIBLE);
//    }
    @Override
    public void onBackPressed() {
        startActivityAnim();
    }

    /**
     * 开始activity的动画
     */
    public void startActivityAnim() {
//        得到当前页的View
        final View view = imageScaleAdapter.getPrimaryItem();
        final PhotoView imageView = (PhotoView) ((ViewGroup) view).getChildAt(0);
//      当图片被放大时，需要把其缩放回原来大小再做动画
        imageView.setZoomable(false);
        computeImageWidthAndHeight(imageView);

        final EaluationPicBean ealuationPicBean = picDataList.get(mPositon);
        final float vx = ealuationPicBean.width * 1.0f / width;
        final float vy = ealuationPicBean.height * 1.0f / height;

        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1.0f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float animatedFraction = animation.getAnimatedFraction();

                view.setTranslationX(EvaluateUtil.evaluateInt(animatedFraction, 0, ealuationPicBean.x + ealuationPicBean.width / 2 - imageView.getWidth() / 2));
                view.setTranslationY(EvaluateUtil.evaluateInt(animatedFraction, 0, ealuationPicBean.y + ealuationPicBean.height / 2 - imageView.getHeight() / 2));
                view.setScaleX(EvaluateUtil.evaluateFloat(animatedFraction, 1, vx));
                view.setScaleY(EvaluateUtil.evaluateFloat(animatedFraction, 1, vy));
                ll_root.setBackgroundColor((int) EvaluateUtil.evaluateArgb(animatedFraction, 0xff000000, 0x0));

                if (animatedFraction > 0.95) {
                    view.setAlpha(1 - animatedFraction);
                }
            }
        });
        addOutListener(valueAnimator);
        valueAnimator.setDuration(300);
        valueAnimator.start();
    }

    /**
     * 计算图片的宽高
     *
     * @param imageView
     */
    private void computeImageWidthAndHeight(PhotoView imageView) {

//      获取真实大小
        Drawable drawable = imageView.getDrawable();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        int intrinsicWidth = drawable.getIntrinsicWidth();
//        计算出与屏幕的比例，用于比较以宽的比例为准还是高的比例为准，因为很多时候不是高度没充满，就是宽度没充满
        float h = DisplayUtil.getMobileHeight(this) * 1.0f / intrinsicHeight;
        float w = DisplayUtil.getMobileWidth(this) * 1.0f / intrinsicWidth;
        if (h > w) {
            h = w;
        } else {
            w = h;
        }
//      得出当宽高至少有一个充满的时候图片对应的宽高
        height = (int) (intrinsicHeight * h);
        width = (int) (intrinsicWidth * w);
    }

    /**
     * 退场动画过程监听
     *
     * @param valueAnimator
     */
    private void addOutListener(ValueAnimator valueAnimator) {
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                ll_dots.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    private static final int READ_WRITER = 0X10;
    private static final String[] RW = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    EaluationPicBean saveBean;

    @Override
    public void onSave(EaluationPicBean ealuationPicBean) {
        this.saveBean=ealuationPicBean;
        new AlertDialog.Builder(this,R.style.AlertDialogTheme)
                .setItems(new String[]{"保存"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (ActivityCompat.checkSelfPermission(LookBigPicActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                                || ActivityCompat.checkSelfPermission(LookBigPicActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            //可以得到一个是否需要弹出解释申请该权限的提示给用户如果为true则表示可以弹
                            if (ActivityCompat.shouldShowRequestPermissionRationale(LookBigPicActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                                    || ActivityCompat.shouldShowRequestPermissionRationale(LookBigPicActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                //允许弹出提示
                                ActivityCompat.requestPermissions(LookBigPicActivity.this, RW
                                        , READ_WRITER);

                            } else {
                                //不允许弹出提示
                                ActivityCompat.requestPermissions(LookBigPicActivity.this, RW
                                        , READ_WRITER);
                            }
                        } else {
                            //保存
                            getImage();

                        }

                    }
                }).create().show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_WRITER) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getImage();
            } else {
                Util.toastMsg("请开启权限后在保存");
            }
        }
    }

    private void getImage(){
        if(saveBean==null|| TextUtils.isEmpty(saveBean.imageUrl)){
            return;
        }
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(AddressManager.get("photoBase"))
                .build();
        restAdapter.create(BigPicService.class)
                .downloadPic(saveBean.imageUrl, new Callback<Response>() {
                    @Override
                    public void success(Response responseBody, Response response) {
                        writeResponseBodyToDisk(responseBody);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        error.printStackTrace();
                    }
                });
    }

    private boolean writeResponseBodyToDisk(Response body) {
        try {
            // 首先保存图片
            File appDir = new File(Environment.getExternalStorageDirectory(), "lai_img");
            if (!appDir.exists()) {
                appDir.mkdir();
            }
            File file = new File(appDir, System.currentTimeMillis() + ".png");

            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.getBody().length()/*.contentLength()*/;
                long fileSizeDownloaded = 0;

                inputStream = body.getBody().in();
                outputStream = new FileOutputStream(file);
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                    Log.i("file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();
                Util.toastMsg("保存成功");
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri = Uri.fromFile(file);
                intent.setData(uri);
                sendBroadcast(intent);
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }


}
