/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.jingdu.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ggx.jerryguan.widget_lib.Chart;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.counselor.model.UserHonorModel;
import com.softtek.lai.module.counselor.presenter.HonorImpl;
import com.softtek.lai.module.counselor.presenter.IHonorPresenter;
import com.softtek.lai.module.grade.view.GradeHomeActivity;
import com.softtek.lai.module.jingdu.Adapter.RankAdapter;
import com.softtek.lai.module.jingdu.model.PaimingModel;
import com.softtek.lai.module.jingdu.model.RankModel;
import com.softtek.lai.module.jingdu.model.Table1Model;
import com.softtek.lai.module.jingdu.model.Table2Model;
import com.softtek.lai.module.jingdu.presenter.GetProinfoImpl;
import com.softtek.lai.module.jingdu.presenter.IGetProinfopresenter;
import com.softtek.lai.module.message.model.PhotosModel;
import com.softtek.lai.module.studetail.view.StudentDetailActivity;
import com.softtek.lai.widgets.SelectPicPopupWindow;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

//import com.umeng.socialize.bean.SocializeConfig;
//import com.umeng.socialize.sso.UMSsoHandler;

@InjectLayout(R.layout.activity_jingdu)
public class JingduActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.list_rank)
    ListView list_rank;

    @InjectView(R.id.tv_newban)
    TextView tv_newban;

    @InjectView(R.id.tv_newmem)
    TextView tv_newmem;

    //班级布局
    @InjectView(R.id.ll_yue1)
    LinearLayout ll_yue1;
    @InjectView(R.id.ll_yue2)
    LinearLayout ll_yue2;
    @InjectView(R.id.ll_yue3)
    LinearLayout ll_yue3;

    @InjectView(R.id.tv_yue1)
    TextView tv_yue1;
    @InjectView(R.id.tv_yue2)
    TextView tv_yue2;
    @InjectView(R.id.tv_yue3)
    TextView tv_yue3;

    @InjectView(R.id.img_oneban)
    ImageView img_oneban;

    @InjectView(R.id.img_twoban)
    ImageView img_twoban;

    @InjectView(R.id.img_threeban)
    ImageView img_threeban;

    @InjectView(R.id.ll_oneban)
    LinearLayout ll_oneban;

    @InjectView(R.id.ll_twoban)
    LinearLayout ll_twoban;

    @InjectView(R.id.ll_threeban)
    LinearLayout ll_threeban;

    //班级累计减重数
    @InjectView(R.id.tv_oneban)
    TextView tv_oneban;

    @InjectView(R.id.tv_twoban)
    TextView tv_twoban;

    @InjectView(R.id.tv_threeban)
    TextView tv_threeban;

    //班级名称
    @InjectView(R.id.tv_classname1)
    TextView tv_classname1;

    @InjectView(R.id.tv_classname2)
    TextView tv_classname2;

    @InjectView(R.id.tv_classname3)
    TextView tv_classname3;

    @InjectView(R.id.total_weight)
    Chart total_weight;

    @InjectView(R.id.top)
    LinearLayout top;

    @InjectView(R.id.iv_email)
    ImageView iv_email;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;

    String url;
    String value;
    SelectPicPopupWindow menuWindow;

    private IHonorPresenter honorPresenter;
    private List<Table1Model> table1ModelList = new ArrayList<Table1Model>();
    private List<PaimingModel> paimingModelList = new ArrayList<PaimingModel>();
    private List<Table2Model> table2ModelList = new ArrayList<Table2Model>();
    private IGetProinfopresenter iGetProinfopresenter;
    UserHonorModel userHonorModel;

    private RankModel rank;
    public RankAdapter rankAdapter;

    //当月新开班级数量,新增学员数量
    String newban = "";
    String newmem = "";

    //班级名称
    String onebanname;
    String twobanname;
    String threebanname;

    //班级累计减重数
    String oneban;
    String twoban;
    String threeban;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        //初始化排名序号
        initpaiming();
        rankAdapter = new RankAdapter(this, table1ModelList, paimingModelList);
        list_rank.setAdapter(rankAdapter);
        list_rank.setOnItemClickListener(this);
        //返回按钮
        ll_left.setOnClickListener(this);
        //分享按钮
        iv_email.setVisibility(View.VISIBLE);
        iv_email.setImageResource(R.drawable.img_share_bt);
        iv_email.setOnClickListener(this);
        fl_right.setOnClickListener(this);
    }

    private void initpaiming() {
        PaimingModel p1 = new PaimingModel(1);
        paimingModelList.add(p1);
        PaimingModel p2 = new PaimingModel(2);
        paimingModelList.add(p2);
        PaimingModel p3 = new PaimingModel(3);
        paimingModelList.add(p3);
        PaimingModel p4 = new PaimingModel(4);
        paimingModelList.add(p4);
        PaimingModel p5 = new PaimingModel(5);
        paimingModelList.add(p5);
        PaimingModel p6 = new PaimingModel(6);
        paimingModelList.add(p6);
        PaimingModel p7 = new PaimingModel(7);
        paimingModelList.add(p7);
        PaimingModel p8 = new PaimingModel(8);
        paimingModelList.add(p8);
        PaimingModel p9 = new PaimingModel(9);
        paimingModelList.add(p9);
        PaimingModel p10 = new PaimingModel(10);
        paimingModelList.add(p10);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void initViews() {
        iGetProinfopresenter = new GetProinfoImpl(this);
        iGetProinfopresenter.getproinfo();
    }

    @Subscribe
    public void onEvent(UserHonorModel model) {
        userHonorModel = model;
        dialogShow("加载中");
        iGetProinfopresenter.upload("/sdcard/screen_test_1.png");

    }

    @Override
    protected void initDatas() {
        tv_title.setText("当期进度");
        honorPresenter = new HonorImpl(this);
    }

    @Subscribe
    public void onEvent(PhotosModel photModel) {
        System.out.println(photModel);
        if (UserInfoModel.getInstance().getUser() == null) {
            return;
        }
        String path = AddressManager.get("shareHost");
        url = path + "ShareSPCurrentPro?AccountId=" + UserInfoModel.getInstance().getUser().getUserid() + "&Image=" + photModel.getImg();
        System.out.println("url:" + url);
        value = "我在" + userHonorModel.getDays() + "天里已累计服务" + userHonorModel.getNum() + "学员，共帮助他们减重" + userHonorModel.getSumLoss() + "斤，快来参加体重管理挑战赛吧！";

        menuWindow = new SelectPicPopupWindow(JingduActivity.this, itemsOnClick);
        //显示窗口
        menuWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        menuWindow.showAtLocation(JingduActivity.this.findViewById(R.id.rel), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
    }

    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.lin_weixin:
                    new ShareAction(JingduActivity.this)
                            .setPlatform(SHARE_MEDIA.WEIXIN)
                            .withTitle("康宝莱体重管理挑战赛，坚持只为改变！")
                            .withText(value)
                            .withTargetUrl(url)
                            .withMedia(new UMImage(JingduActivity.this, R.drawable.img_share_logo))
                            .share();
                    break;
                case R.id.lin_circle:
                    new ShareAction(JingduActivity.this)
                            .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                            .withTitle("康宝莱体重管理挑战赛，坚持只为改变！")
                            .withText(value)
                            .withTargetUrl(url)
                            .withMedia(new UMImage(JingduActivity.this, R.drawable.img_share_logo))
                            .share();
                    break;
                case R.id.lin_sina:
                    new ShareAction(JingduActivity.this)
                            .setPlatform(SHARE_MEDIA.SINA)
                            .withText(value + url)
                            .withMedia(new UMImage(JingduActivity.this, R.drawable.img_share_logo))
                            .share();
                    break;
                default:
                    break;
            }
        }
    };

    @Subscribe
    public void onEvent(final RankModel rank) {
        //Table：教练本月总开班数量，新增学员数量(累计减重数量)
        newban = rank.getTable().get(0).getTotalClass();
        newmem = rank.getTable().get(0).getTotalMember();
        tv_newban.setText(newban);
        tv_newmem.setText(newmem);

        //Table1: 教练所有班级的所有学员减重最多的前10名学员
        table1ModelList = rank.getTable1();
        rankAdapter.updateData(table1ModelList, paimingModelList);

        //Table2:各个班本月累计减重
        table2ModelList = rank.getTable2();
        if (rank.getTable2().size() == 1) {
            img_oneban.setVisibility(View.VISIBLE);
            // 需要解析的日期字符串，是几月班
            String yue1 = rank.getTable2().get(0).getStartDate();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date = format.parse(yue1);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int month = calendar.get(Calendar.MONTH);
                ll_yue1.setVisibility(View.VISIBLE);
                tv_yue1.setText("" + (month + 1));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            onebanname = rank.getTable2().get(0).getClassName();
            oneban = rank.getTable2().get(0).getLoseWeight();
            tv_classname1.setText(onebanname);
            tv_oneban.setText(oneban);
            float a = Float.parseFloat(oneban);
            total_weight.setValue(a, 0, 0);
            ll_oneban.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String classId = rank.getTable2().get(0).getClassId();
                    Intent intent = new Intent(JingduActivity.this, GradeHomeActivity.class);
                    intent.putExtra("classId", classId);
                    intent.putExtra("review", 1);
                    startActivity(intent);
                }
            });

        } else if (rank.getTable2().size() == 2) {
            String yue1 = rank.getTable2().get(0).getStartDate();
            String yue2 = rank.getTable2().get(1).getStartDate();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date1 = format.parse(yue1);
                Date date2 = format.parse(yue2);
                Calendar calendar1 = Calendar.getInstance();
                Calendar calendar2 = Calendar.getInstance();
                calendar1.setTime(date1);
                calendar2.setTime(date2);
                int month1 = calendar1.get(Calendar.MONTH);
                int month2 = calendar2.get(Calendar.MONTH);
                ll_yue1.setVisibility(View.VISIBLE);
                tv_yue1.setText("" + (month1 + 1));
                ll_yue2.setVisibility(View.VISIBLE);
                tv_yue2.setText("" + (month2 + 1));

            } catch (ParseException e) {
                e.printStackTrace();
            }

            img_oneban.setVisibility(View.VISIBLE);
            img_twoban.setVisibility(View.VISIBLE);

            onebanname = rank.getTable2().get(0).getClassName();
            oneban = rank.getTable2().get(0).getLoseWeight();
            twobanname = rank.getTable2().get(1).getClassName();
            twoban = rank.getTable2().get(1).getLoseWeight();
            tv_classname1.setText(onebanname);
            tv_oneban.setText(oneban);
            tv_classname2.setText(twobanname);
            tv_twoban.setText(twoban);
            float a = Float.parseFloat(oneban);
            float b = Float.parseFloat(twoban);
            total_weight.setValue(a, b, 0);

            ll_oneban.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String classId = rank.getTable2().get(0).getClassId();
                    Intent intent = new Intent(JingduActivity.this, GradeHomeActivity.class);
                    intent.putExtra("classId", classId);
                    intent.putExtra("review", 1);
                    startActivity(intent);
                }
            });
            ll_twoban.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String classId = rank.getTable2().get(1).getClassId();
                    Intent intent = new Intent(JingduActivity.this, GradeHomeActivity.class);
                    intent.putExtra("classId", classId);
                    intent.putExtra("review", 1);
                    startActivity(intent);
                }
            });

        } else if (rank.getTable2().size() == 3) {

            String yue1 = rank.getTable2().get(0).getStartDate();
            String yue2 = rank.getTable2().get(1).getStartDate();
            String yue3 = rank.getTable2().get(2).getStartDate();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date1 = format.parse(yue1);
                Date date2 = format.parse(yue2);
                Date date3 = format.parse(yue3);
                Calendar calendar1 = Calendar.getInstance();
                Calendar calendar2 = Calendar.getInstance();
                Calendar calendar3 = Calendar.getInstance();
                calendar1.setTime(date1);
                calendar2.setTime(date2);
                calendar3.setTime(date3);
                int month1 = calendar1.get(Calendar.MONTH);
                int month2 = calendar2.get(Calendar.MONTH);
                int month3 = calendar3.get(Calendar.MONTH);
                ll_yue1.setVisibility(View.VISIBLE);
                tv_yue1.setText("" + (month1 + 1));
                ll_yue2.setVisibility(View.VISIBLE);
                tv_yue2.setText("" + (month2 + 1));
                ll_yue3.setVisibility(View.VISIBLE);
                tv_yue3.setText("" + (month3 + 1));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            img_oneban.setVisibility(View.VISIBLE);
            img_twoban.setVisibility(View.VISIBLE);
            img_threeban.setVisibility(View.INVISIBLE);

            onebanname = rank.getTable2().get(0).getClassName();
            oneban = rank.getTable2().get(0).getLoseWeight();
            twobanname = rank.getTable2().get(1).getClassName();
            twoban = rank.getTable2().get(1).getLoseWeight();
            threebanname = rank.getTable2().get(2).getClassName();
            threeban = rank.getTable2().get(2).getLoseWeight();
            tv_classname1.setText(onebanname);
            tv_oneban.setText(oneban);
            tv_classname2.setText(twobanname);
            tv_twoban.setText(twoban);
            tv_classname3.setText(threebanname);
            tv_threeban.setText(threeban);
            float a = Float.parseFloat(oneban);
            float b = Float.parseFloat(twoban);
            float c = Float.parseFloat(threeban);
            total_weight.setValue(a, b, c);
            ll_oneban.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String classId = rank.getTable2().get(0).getClassId();
                    Intent intent = new Intent(JingduActivity.this, GradeHomeActivity.class);
                    intent.putExtra("classId", classId);
                    intent.putExtra("review", 1);
                    startActivity(intent);
                }
            });
            ll_twoban.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String classId = rank.getTable2().get(1).getClassId();
                    Intent intent = new Intent(JingduActivity.this, GradeHomeActivity.class);
                    intent.putExtra("classId", classId);
                    intent.putExtra("review", 1);
                    startActivity(intent);
                }
            });
            ll_threeban.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String classId = rank.getTable2().get(2).getClassId();
                    Intent intent = new Intent(JingduActivity.this, GradeHomeActivity.class);
                    intent.putExtra("classId", classId);
                    intent.putExtra("review", 1);
                    startActivity(intent);
                }
            });
        }
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Bitmap b1 = getViewBitmap(top);
//                savePic(b1, "/sdcard/screen_test_1.png");
//            }
//        }, 200);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            //分享功能逻辑
            case R.id.iv_email:
            case R.id.fl_right:
                dialogShow("加载中");
                Bitmap b1 = getViewBitmap(top);
                savePic(b1, "/sdcard/screen_test_1.png");
                honorPresenter.getUserHonors();
                //iGetProinfopresenter.upload("/sdcard/screen_test_1.png");
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("resultCode:" + resultCode);
//        UMSsoHandler ssoHandler = SocializeConfig.getSocializeConfig().getSsoHandler(requestCode);
//        if (ssoHandler != null) {
//            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
//        }
    }

    // 保存到sdcard
    public static void savePic(Bitmap b, String strFileName) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(strFileName);
            if (null != fos) {
                b.compress(Bitmap.CompressFormat.PNG, 100, fos);
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
            Log.e("TTTTTTTTActivity", "failed getViewBitmap(" + v + ")",
                    new RuntimeException());
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        // Restore the view
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);

        return bitmap;
    }

    /*
     * listview的点击事件
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Table1Model table1Model = table1ModelList.get(position);
        long userId = Long.parseLong(table1Model.getAccountId());
        long classId = Long.parseLong(table1Model.getClassId());
        Intent intent = new Intent(JingduActivity.this, StudentDetailActivity.class);
        intent.putExtra("userId", userId);
        intent.putExtra("classId", classId);
        intent.putExtra("review", 1);
        startActivity(intent);
    }
}
