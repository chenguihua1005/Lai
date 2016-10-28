/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.bodygamest.view;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygamest.Adapter.StudentHonorJZAdapter;
import com.softtek.lai.module.bodygamest.Adapter.StudentHonorStarAdapter;
import com.softtek.lai.module.bodygamest.Adapter.StudentHonorYGJAdapter;
import com.softtek.lai.module.bodygamest.model.HnumsModel;
import com.softtek.lai.module.bodygamest.model.HonorModel;
import com.softtek.lai.module.bodygamest.model.StudentHonorInfo;
import com.softtek.lai.module.bodygamest.model.StudentHonorTypeInfo;
import com.softtek.lai.module.bodygamest.present.IStudentPresenter;
import com.softtek.lai.module.bodygamest.present.StudentImpl;
import com.softtek.lai.utils.ACache;
import com.softtek.lai.widgets.SelectPicPopupWindow;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

/**
 * Created by jarvis.liu on 3/22/2016.
 * 助教管理页面
 */
@InjectLayout(R.layout.activity_student_honor_grid)
public class StudentHonorGridActivity extends BaseActivity implements View.OnClickListener{


    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.list_star)
    GridView list_star;

    @InjectView(R.id.img_fc_1)
    ImageView img_fc_1;

    @InjectView(R.id.img_fc_2)
    ImageView img_fc_2;

    @InjectView(R.id.img_fc_3)
    ImageView img_fc_3;

    @InjectView(R.id.lin_fc)
    LinearLayout lin_fc;

    @InjectView(R.id.list_ygj)
    GridView list_ygj;
    @InjectView(R.id.rel)
    RelativeLayout rel;

    @InjectView(R.id.hs_jz)
    HorizontalScrollView hs_jz;
    @InjectView(R.id.hs_ygj)
    HorizontalScrollView hs_ygj;
    @InjectView(R.id.hs_star)
    HorizontalScrollView hs_star;

    @InjectView(R.id.list_jz)
    GridView list_jz;

    @InjectView(R.id.lin_jz_left)
    LinearLayout lin_jz_left;

    @InjectView(R.id.lin_jz_right)
    LinearLayout lin_jz_right;

    @InjectView(R.id.lin_ygj_left)
    LinearLayout lin_ygj_left;

    @InjectView(R.id.lin_ygj_right)
    LinearLayout lin_ygj_right;

    @InjectView(R.id.lin_star_left)
    LinearLayout lin_star_left;

    @InjectView(R.id.lin_star_right)
    LinearLayout lin_star_right;

    @InjectView(R.id.lin_jz_value)
    LinearLayout lin_jz_value;
    @InjectView(R.id.lin_jz_sm)
    RelativeLayout lin_jz_sm;
    @InjectView(R.id.text_jz)
    TextView text_jz;

    @InjectView(R.id.lin_fc_value)
    RelativeLayout lin_fc_value;
    @InjectView(R.id.lin_fc_sm)
    RelativeLayout lin_fc_sm;
    @InjectView(R.id.text_fc)
    TextView text_fc;

    @InjectView(R.id.lin_ygj_value)
    LinearLayout lin_ygj_value;
    @InjectView(R.id.lin_ygj_sm)
    RelativeLayout lin_ygj_sm;

    @InjectView(R.id.lin_star_value)
    LinearLayout lin_star_value;
    @InjectView(R.id.lin_star_sm)
    RelativeLayout lin_star_sm;

    @InjectView(R.id.iv_email)
    ImageView iv_email;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;

    String url;
    String value;
    SelectPicPopupWindow menuWindow;


    private IStudentPresenter studentHonorPresenter;
    private ACache aCache;
    int widthPosition;

    private List<StudentHonorInfo> jz_list = new ArrayList<StudentHonorInfo>();
    private List<StudentHonorInfo> fc_list = new ArrayList<StudentHonorInfo>();
    private List<StudentHonorInfo> ygj_list = new ArrayList<StudentHonorInfo>();
    private List<StudentHonorInfo> star_list = new ArrayList<StudentHonorInfo>();

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_left.setOnClickListener(this);
        lin_jz_left.setOnClickListener(this);
        lin_jz_right.setOnClickListener(this);
        lin_ygj_left.setOnClickListener(this);
        lin_ygj_right.setOnClickListener(this);
        lin_star_left.setOnClickListener(this);
        lin_star_right.setOnClickListener(this);
        iv_email.setVisibility(View.VISIBLE);
        iv_email.setImageResource(R.drawable.img_share_bt);
        iv_email.setOnClickListener(this);
        fl_right.setOnClickListener(this);

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(HnumsModel hnumsModel) {
        if (UserInfoModel.getInstance().getUser() == null) {
            return;
        }
        String path = AddressManager.get("shareHost");
        url = path + "ShareStudent?id=" + UserInfoModel.getInstance().getUser().getUserid();
        value = "我已获得" + hnumsModel.getHnums() + "勋章，快来和我一起参加体重管理挑战赛吧！";

        menuWindow = new SelectPicPopupWindow(StudentHonorGridActivity.this, itemsOnClick);
        //显示窗口
        menuWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        menuWindow.showAtLocation(StudentHonorGridActivity.this.findViewById(R.id.rel), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
    }

    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.lin_weixin:
                    new ShareAction(StudentHonorGridActivity.this)
                            .setPlatform(SHARE_MEDIA.WEIXIN)
                            .withTitle("康宝莱体重管理挑战赛，坚持只为改变！")
                            .withText(value)
                            .withTargetUrl(url)
                            .withMedia(new UMImage(StudentHonorGridActivity.this, R.drawable.img_share_logo))
                            .share();
                    break;
                case R.id.lin_circle:
                    new ShareAction(StudentHonorGridActivity.this)
                            .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                            .withTitle("康宝莱体重管理挑战赛，坚持只为改变！")
                            .withText(value)
                            .withTargetUrl(url)
                            .withMedia(new UMImage(StudentHonorGridActivity.this, R.drawable.img_share_logo))
                            .share();
                    break;
                case R.id.lin_sina:
                    new ShareAction(StudentHonorGridActivity.this)
                            .setPlatform(SHARE_MEDIA.SINA)
                            .withText(value + url)
                            .withMedia(new UMImage(StudentHonorGridActivity.this, R.drawable.img_share_logo))
                            .share();
                    break;
                default:
                    break;
            }


        }

    };

    @Subscribe
    public void onEvent(HonorModel honorModel) {
        List<StudentHonorInfo> table1 = honorModel.getTable1();
        List<StudentHonorTypeInfo> table2 = honorModel.getTable2();
        String type = table2.get(0).getIsHave();
        if ("3".equals(type)) {
            Util.toastMsg("新的班级开始了, 您可以在成绩单的往期成绩中查看之前获得的勋章");
        }
        for (int i = 0; i < table1.size(); i++) {
            StudentHonorInfo studentHonorInfo = table1.get(i);
            String honorType = studentHonorInfo.getHonorType().toString();
            if ("0".equals(honorType)) {
                jz_list.add(studentHonorInfo);
            } else if ("1".equals(honorType)) {
                fc_list.add(studentHonorInfo);
            } else if ("2".equals(honorType)) {
                ygj_list.add(studentHonorInfo);
            } else {
                star_list.add(studentHonorInfo);
            }
        }

        setGridView();
        lin_fc_value.setVisibility(View.VISIBLE);
        lin_fc_sm.setVisibility(View.GONE);
        if (fc_list.size() == 0) {
            lin_fc_value.setVisibility(View.GONE);
            lin_fc_sm.setVisibility(View.VISIBLE);
        } else {
            String value = fc_list.get(0).getValue();
            if ("1".equals(value)) {
                img_fc_1.setImageResource(R.drawable.img_student_honor_tong);
            } else if ("2".equals(value)) {
                img_fc_1.setImageResource(R.drawable.img_student_honor_tong);
                img_fc_2.setImageResource(R.drawable.img_student_honor_yin);
            } else if ("3".equals(value)) {
                img_fc_1.setImageResource(R.drawable.img_student_honor_tong);
                img_fc_2.setImageResource(R.drawable.img_student_honor_yin);
                img_fc_3.setImageResource(R.drawable.img_student_honor_jin);
            }
        }
        if (jz_list.size() == 0) {
            lin_jz_value.setVisibility(View.GONE);
            lin_jz_sm.setVisibility(View.VISIBLE);
        } else {
            lin_jz_value.setVisibility(View.VISIBLE);
            lin_jz_sm.setVisibility(View.GONE);
            String value=jz_list.get(jz_list.size()-1).getValue();
            if(Integer.parseInt(value)>=50){
                text_jz.setText("加油! 你离下一枚减重奖章只差10斤了哦");
            }else {
                text_jz.setText("加油! 你离下一枚减重奖章只差5斤了哦");
            }
            StudentHonorInfo StudentHonorInfo=new StudentHonorInfo("future","");
            jz_list.add(StudentHonorInfo);
            setG();
            StudentHonorJZAdapter jz_adapter = new StudentHonorJZAdapter(this, jz_list);
            list_jz.setAdapter(jz_adapter);
        }

        if (ygj_list.size() == 0) {
            lin_ygj_value.setVisibility(View.GONE);
            lin_ygj_sm.setVisibility(View.VISIBLE);
        } else {
            lin_ygj_value.setVisibility(View.VISIBLE);
            lin_ygj_sm.setVisibility(View.GONE);
            StudentHonorYGJAdapter ygj_adapter = new StudentHonorYGJAdapter(this, ygj_list);
            list_ygj.setAdapter(ygj_adapter);
        }

        if (star_list.size() == 0) {
            lin_star_value.setVisibility(View.GONE);
            lin_star_sm.setVisibility(View.VISIBLE);
        } else {
            lin_star_value.setVisibility(View.VISIBLE);
            lin_star_sm.setVisibility(View.GONE);
            StudentHonorStarAdapter star_adapter = new StudentHonorStarAdapter(this, star_list);
            list_star.setAdapter(star_adapter);
        }
    }
    private void setG(){

        int size = jz_list.size();
        int length = 100;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int gridviewWidth = (int) (size * (length + 4) * density);
        int itemWidth = (int) (length * density);
        widthPosition = itemWidth;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridviewWidth, LinearLayout.LayoutParams.FILL_PARENT);
        list_jz.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        list_jz.setColumnWidth(itemWidth); // 设置列表项宽
        list_jz.setStretchMode(GridView.NO_STRETCH);
        list_jz.setNumColumns(size); // 设置列数量=列表集合数
    }
    /**
     * 设置GirdView参数，绑定数据
     */
    private void setGridView() {
        int size1 = ygj_list.size();
        int length1 = 100;
        DisplayMetrics dm1 = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm1);
        float density1 = dm1.density;
        int gridviewWidth1 = (int) (size1 * (length1 + 4) * density1);
        int itemWidth1 = (int) (length1 * density1);

        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                gridviewWidth1, LinearLayout.LayoutParams.FILL_PARENT);
        list_ygj.setLayoutParams(params1); // 设置GirdView布局参数,横向布局的关键
        list_ygj.setColumnWidth(itemWidth1); // 设置列表项宽
        list_ygj.setStretchMode(GridView.NO_STRETCH);
        list_ygj.setNumColumns(size1); // 设置列数量=列表集合数

        int size2 = star_list.size();
        int length2 = 100;
        DisplayMetrics dm2 = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm2);
        float density2 = dm2.density;
        int gridviewWidth2 = (int) (size2 * (length2 + 4) * density2);
        int itemWidth2 = (int) (length2 * density2);

        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                gridviewWidth2, LinearLayout.LayoutParams.FILL_PARENT);
        list_star.setLayoutParams(params2); // 设置GirdView布局参数,横向布局的关键
        list_star.setColumnWidth(itemWidth2); // 设置列表项宽
        list_star.setStretchMode(GridView.NO_STRETCH);
        list_star.setNumColumns(size2); // 设置列数量=列表集合数

    }

    @Override
    protected void initViews() {
        //tv_left.setLayoutParams(new Toolbar.LayoutParams(DisplayUtil.dip2px(this,15),DisplayUtil.dip2px(this,30)));
        tv_title.setText(R.string.CounselorF);


//        img_fc_1.post(new Runnable() {
//            @Override
//            public void run() {
//                width = img_fc_1.getWidth();
//                ViewGroup.LayoutParams para = list_jz.getLayoutParams();
//                para.height = width;
//                list_jz.setLayoutParams(para);
//
//                list_ygj.setLayoutParams(para);
//
//                list_star.setLayoutParams(para);
//
//                //lin_fc.setLayoutParams(para);
//                lin_fc.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, width));
//            }
//        });
    }

    @Override
    protected void initDatas() {
        studentHonorPresenter = new StudentImpl(this);
        aCache = ACache.get(this, Constants.USER_ACACHE_DATA_DIR);
        dialogShow("加载中");
        studentHonorPresenter.getStudentHonor();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_email:
            case R.id.fl_right:
                dialogShow("加载中");
                studentHonorPresenter.getStudentHonours();
                break;

            case R.id.ll_left:
                finish();
                break;

            case R.id.lin_ygj_left:
                hs_ygj.getScrollX();
                hs_ygj.smoothScrollTo(hs_ygj.getScrollX() - widthPosition, 0);
                break;

            case R.id.lin_ygj_right:
                hs_ygj.getScrollX();
                hs_ygj.smoothScrollTo(hs_ygj.getScrollX() + widthPosition, 0);
                break;

            case R.id.lin_jz_left:
                hs_jz.getScrollX();
                hs_jz.smoothScrollTo(hs_jz.getScrollX() - widthPosition, 0);
                break;

            case R.id.lin_jz_right:
                hs_jz.getScrollX();
                hs_jz.smoothScrollTo(hs_jz.getScrollX() + widthPosition, 0);
                break;

            case R.id.lin_star_left:
                hs_star.getScrollX();
                hs_star.smoothScrollTo(hs_star.getScrollX() - widthPosition, 0);
                break;

            case R.id.lin_star_right:
                hs_star.getScrollX();
                hs_star.smoothScrollTo(hs_star.getScrollX() + widthPosition, 0);
                break;
        }
    }



}
