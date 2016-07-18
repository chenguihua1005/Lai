package com.softtek.lai.module.bodygame2pc.view;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame2.view.BodyGameSPActivity;
import com.softtek.lai.module.bodygame2.view.StudentHonorPCActivity;
import com.softtek.lai.module.bodygame2pc.model.StuHonorListModel;
import com.softtek.lai.module.bodygame2pc.model.StumemberDetialModel;
import com.softtek.lai.module.bodygame2pc.present.StuPersonDateManager;
import com.softtek.lai.module.bodygamest.view.StudentHonorGridActivity;
import com.softtek.lai.module.bodygamest.view.UploadPhotoActivity;
import com.softtek.lai.module.pastreview.honors.Medal;
import com.softtek.lai.module.pastreview.view.PassPhotoActivity;
import com.softtek.lai.module.studetail.adapter.StudentDetailFragmentAdapter;
import com.softtek.lai.module.studetail.view.DimensionChartFragment;
import com.softtek.lai.module.studetail.view.LossWeightChartFragment;
import com.softtek.lai.module.studetail.view.LossWeightLogActivity;
import com.softtek.lai.utils.ChMonth;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.StringUtil;
import com.softtek.lai.widgets.CircleImageView;
import com.softtek.lai.widgets.ObservableScrollView;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

/**
 * Created by lareina.qiao on 7/14/2016.
 */
@InjectLayout(R.layout.stupersondate_layout)
public class StuPersonDateActivity extends BaseActivity implements View.OnClickListener,BaseFragment.OnFragmentInteractionListener,ObservableScrollView.ScrollViewListener {
    StuPersonDateManager datemanager;
    @InjectView(R.id.StuScrollv)
    ObservableScrollView StuScrollv;
    @InjectView(R.id.rl_color)
    RelativeLayout rl_color;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tablayout)
    TabLayout tabLayout;
    @InjectView(R.id.tabcontent)
    ViewPager tabcontent;
    @InjectView(R.id.tv_totle_lw)
    TextView tv_totle_lw;
    @InjectView(R.id.tv_loss_before)
    TextView tv_loss_before;
    @InjectView(R.id.tv_loss_after)
    TextView tv_loss_after;
    @InjectView(R.id.iv_loss_before)
    ImageView iv_loss_before;
    @InjectView(R.id.iv_loss_after)
    ImageView iv_loss_after;
    @InjectView(R.id.tv_loss_after_tip)
    TextView tv_loss_after_tip;
    @InjectView(R.id.tv_total_loss_tip)
    TextView tv_total_loss_tip;
    @InjectView(R.id.rel_sy)
    RelativeLayout rel_sy;
    //奖章一
    @InjectView(R.id.ll_honorn1)
    LinearLayout ll_honorn1;
    @InjectView(R.id.me_xun1)
    Medal me_xun1;
    //奖章二
    @InjectView(R.id.ll_honorn2)
    LinearLayout ll_honorn2;
    @InjectView(R.id.me_xun2)
    Medal me_xun2;
    //奖章三
    @InjectView(R.id.ll_honorn3)
    LinearLayout ll_honorn3;
    @InjectView(R.id.me_xun3)
    Medal me_xun3;
    @InjectView(R.id.iv_banner)
    ImageView iv_banner;
    @InjectView(R.id.im_headimg)
    CircleImageView im_headimg;
    @InjectView(R.id.ll_personphoto2)
    LinearLayout ll_personphoto2;
    @InjectView(R.id.tv_weightday)
    TextView tv_weightday;
    @InjectView(R.id.tv_mon)
    TextView tv_mon;
    @InjectView(R.id.tv_storycontent)
    TextView tv_storycontent;
    @InjectView(R.id.im_pict1)
    ImageView im_pict1;
    @InjectView(R.id.im_pict2)
    ImageView im_pict2;
    @InjectView(R.id.im_pict3)
    ImageView im_pict3;
    @InjectView(R.id.im_pict4)
    ImageView im_pict4;
    @InjectView(R.id.im_pict5)
    ImageView im_pict5;
    @InjectView(R.id.im_pict6)
    ImageView im_pict6;
    @InjectView(R.id.tv_valuetext)
    TextView tv_valuetext;
    @InjectView(R.id.tv_valuetext2)
    TextView tv_valuetext2;
    @InjectView(R.id.tv_valuetext3)
    TextView tv_valuetext3;
    @InjectView(R.id.tv_jianzhper)
    TextView tv_jianzhper;//减重百分比
    @InjectView(R.id.re_xunzhang)
    RelativeLayout re_xunzhang;
    @InjectView(R.id.ll_xun)
    RelativeLayout ll_xun;
    @InjectView(R.id.re_jianzh)
    RelativeLayout re_jianzh;
    @InjectView(R.id.ll_stoty)
    LinearLayout ll_stoty;
    @InjectView(R.id.tv_jianzhnum)
    TextView tv_jianzhnum;
    @InjectView(R.id.Re_personphoto)
    RelativeLayout Re_personphoto;
    @InjectView(R.id.ll_photo)
    LinearLayout ll_photo;
    @InjectView(R.id.ll_personphoto)
    LinearLayout ll_personphoto;
    @InjectView(R.id.tv_nophoto)
    TextView tv_nophoto;
    @InjectView(R.id.tv_jianzhflag)
    TextView tv_jianzhflag;
    @InjectView(R.id.toolbar1)
    RelativeLayout toolbar;
    @InjectView(R.id.tv_xunzhflag)
    TextView tv_xunzhflag;
    @InjectView(R.id.tv_stuname)
    TextView tv_stuname;
    @InjectView(R.id.tv_stuclassname)
    TextView tv_stuclassname;
    @InjectView(R.id.tv_studate)
    TextView tv_studate;
    private String userId = "0";
    private String classId = "0";
    private String review_flag = "1";
    Boolean photostate=true;
    Boolean storystate=true;
    Boolean xunsatate=true;
    ChMonth chMonth;
    private List<Fragment> fragmentList = new ArrayList<>();

    LossWeightChartFragment lwcf;
    DimensionChartFragment dcf;

    @Override
    protected void initViews() {
        tintManager.setStatusBarAlpha(0);
        int status= DisplayUtil.getStatusHeight(this);
        RelativeLayout.LayoutParams params1= (RelativeLayout.LayoutParams) toolbar.getLayoutParams();
        params1.topMargin=status;
        toolbar.setLayoutParams(params1);
        re_xunzhang.setOnClickListener(this);
        ll_xun.setOnClickListener(this);
        re_jianzh.setOnClickListener(this);
        ll_stoty.setOnClickListener(this);
        Re_personphoto.setOnClickListener(this);
        ll_photo.setOnClickListener(this);
        ll_personphoto.setOnClickListener(this);
        im_pict1.setOnClickListener(this);
        im_pict2.setOnClickListener(this);
        im_pict3.setOnClickListener(this);
        im_pict4.setOnClickListener(this);
        im_pict5.setOnClickListener(this);
        im_pict6.setOnClickListener(this);
        StuScrollv.setScrollViewListener(this);
        ll_left.setOnClickListener(this);
        me_xun1.setOnClickListener(this);
        me_xun2.setOnClickListener(this);
        me_xun3.setOnClickListener(this);
        lwcf = null;
        dcf = null;
        userId=UserInfoModel.getInstance().getUser().getUserid() ;
        classId = getIntent().getStringExtra("classId");
        review_flag = getIntent().getStringExtra("review");
        review_flag = review_flag == null ? "1" : review_flag;
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId + "");
        params.put("classId", classId + "");

        if (lwcf == null) {
            lwcf = LossWeightChartFragment.newInstance(params);
        }
        if (dcf == null) {
            dcf = DimensionChartFragment.newInstance(params);
        }
        fragmentList.add(lwcf);
        fragmentList.add(dcf);
        tabcontent.setAdapter(new StudentDetailFragmentAdapter(getSupportFragmentManager(), fragmentList));
        tabLayout.setupWithViewPager(tabcontent);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        StuScrollv.post(new Runnable() {
            public void run() {
                StuScrollv.scrollTo(0,0);
            }
        });
    }

    @Override
    protected void initDatas() {
        datemanager = new StuPersonDateManager();
        datemanager.doGetStuClmemberDetial(this, 1, userId, classId);
    }

    public void onloadCompleted(StumemberDetialModel stu) {
        if (stu!= null) {
            tv_jianzhnum.setText(stu.getClmInfo().getTotalLoss());
            DecimalFormat df = new DecimalFormat("#0.0");
            if (!TextUtils.isEmpty(stu.getClmInfo().getLossPer())) {
                tv_jianzhper.setText(df.format(Double.parseDouble(stu.getClmInfo().getLossPer())));
            }
            else {
                tv_jianzhper.setText("0");
            }
            String path = AddressManager.get("photoHost");
            if (!TextUtils.isEmpty(stu.getClmInfo().getClassBanner()))
            {
                Picasso.with(this).load(path + stu.getClmInfo().getPhoto()).fit().error(R.drawable.img_default).into(iv_banner);
            }
            if (!TextUtils.isEmpty(stu.getClmInfo().getPhoto())) {
                Picasso.with(this).load(path + stu.getClmInfo().getPhoto()).fit().error(R.drawable.img_default).into(im_headimg);
            }
            tv_stuname.setText(stu.getClmInfo().getUserName());
            tv_stuclassname.setText("班级：" + stu.getClmInfo().getClassName());

            String[] star = stu.getClmInfo().getStartDate().split(" ");
            String[] stardate = star[0].split("-");
            String[] end = stu.getClmInfo().getEndDate().split(" ");
            String[] enddate = end[0].split("-");
            tv_studate.setText(stardate[0] + "年" + stardate[1] + "月"  + "-" + enddate[0] + "年" + enddate[1] + "月" );
            if (stu.getLossStory() == null || TextUtils.isEmpty(stu.getLossStory().getCreateDate())) {
                tv_jianzhflag.setText("这个家伙很懒～没有发布故事哦");
                ll_stoty.setClickable(false);
                storystate=false;
            } else {
                String[] day = stu.getLossStory().getCreateDate().split(" ");
                String[] date = day[0].split("-");
                tv_weightday.setText(date[2]);
                chMonth = new ChMonth();
                tv_mon.setText(chMonth.tomonth(date[1]));
                tv_storycontent.setText(stu.getLossStory().getLogContent());

            }
            if (stu.getHonorList().size() == 0) {
                tv_xunzhflag.setText("加油！完成挑战，获得更多勋章");
                ll_xun.setClickable(false);
                xunsatate=false;
            } else {
                List<StuHonorListModel> honors = stu.getHonorList();
                for (int i = 0; i < stu.getHonorList().size(); i++) {
                    StuHonorListModel honor = honors.get(i);
                    switch (i) {
                        case 0:
                            setMedal(honor, me_xun1, tv_valuetext);
                            break;
                        case 1:
                            setMedal(honor, me_xun2, tv_valuetext2);
                            break;
                        case 2:
                            setMedal(honor, me_xun3, tv_valuetext3);
                            break;
                    }

                }
            }
            if (stu.getPhotoList().size() == 0) {
                tv_nophoto.setText("暂无照片");
                ll_photo.setClickable(false);
                photostate=false;
            }
            if (stu.getPhotoList().size() < 3) {
                ll_personphoto2.setVisibility(View.GONE);
            }
            for (int j = 0; j < stu.getPhotoList().size(); j++) {
                if (!TextUtils.isEmpty(stu.getPhotoList().get(j).getImgUrl())) {
                    if (j == 0) {
                        im_pict1.setVisibility(View.VISIBLE);
                        Picasso.with(this).load(path + stu.getPhotoList().get(j).getImgUrl()).fit().error(R.drawable.default_icon_square).into(im_pict1);
                    } else if (j == 1) {
                        im_pict2.setVisibility(View.VISIBLE);
                        Picasso.with(this).load(path + stu.getPhotoList().get(j).getImgUrl()).fit().error(R.drawable.default_icon_square).into(im_pict2);
                    } else if (j == 2) {
                        im_pict3.setVisibility(View.VISIBLE);
                        Picasso.with(this).load(path + stu.getPhotoList().get(j).getImgUrl()).fit().error(R.drawable.default_icon_square).into(im_pict3);
                    } else if (j == 3) {
                        im_pict4.setVisibility(View.VISIBLE);
                        Picasso.with(this).load(path + stu.getPhotoList().get(j).getImgUrl()).fit().error(R.drawable.default_icon_square).into(im_pict4);
                    } else if (j == 4) {
                        im_pict5.setVisibility(View.VISIBLE);
                        Picasso.with(this).load(path + stu.getPhotoList().get(j).getImgUrl()).fit().error(R.drawable.default_icon_square).into(im_pict5);
                    } else if (j == 5) {
                        im_pict6.setVisibility(View.VISIBLE);
                        Picasso.with(this).load(path + stu.getPhotoList().get(j).getImgUrl()).fit().error(R.drawable.default_icon_square).into(im_pict6);
                    }

                }
            }
            if (StringUtils.isNotEmpty(stu.getClmInfo().getTotalLoss()) && Float.parseFloat(stu.getClmInfo().getTotalLoss()) < 0) {
                tv_total_loss_tip.setText("共增重");
            } else {
                tv_total_loss_tip.setText("共减重");
            }
            tv_totle_lw.setText(Float.parseFloat(StringUtils.isEmpty(stu.getClmInfo().getAfterWeight()) ? "0" : stu.getClmInfo().getAfterWeight()) == 0 ? "0斤" : Math.abs(Float.parseFloat(stu.getClmInfo().getTotalLoss())) + "斤");
            tv_loss_before.setText(StringUtil.getFloatValue(stu.getClmInfo().getBeforeWeight()) + "斤");
            float lossAfter = StringUtil.getFloat(stu.getClmInfo().getAfterWeight());
            tv_loss_after.setText(lossAfter == 0 ? "尚未复测" : StringUtil.getFloat(stu.getClmInfo().getAfterWeight()) + "斤");
            if (lossAfter == 0) {
                tv_loss_after_tip.setVisibility(View.GONE);
            } else {
                tv_loss_after_tip.setVisibility(View.VISIBLE);
            }

            if (!StringUtils.isEmpty(stu.getClmInfo().getBeforeImage())) {
                Picasso.with(this).load(path + stu.getClmInfo().getBeforeImage()).fit().placeholder(R.drawable.default_icon_rect).error(R.drawable.default_icon_rect).into(iv_loss_before);
            }
            if (!StringUtils.isEmpty(stu.getClmInfo().getAfterImage())) {
                Picasso.with(this).load(path + stu.getClmInfo().getAfterImage()).fit().placeholder(R.drawable.default_icon_rect).error(R.drawable.default_icon_rect).into(iv_loss_after);
            }

        }
    }

    private void setMedal(StuHonorListModel honor, Medal medal, TextView tv) {
        int medalType = Integer.parseInt(honor.getHonorType());
        if (medalType == Medal.LOSS_WEIGHT) {
            medal.setType(Medal.LOSS_WEIGHT);
            medal.setmText(honor.getValue() + "斤");
            tv.setText("减重" + honor.getValue() + "斤奖章");
        } else if (medalType == Medal.FUCE) {//复测
            medal.setType(Medal.FUCE);
            if ("1".equals(honor.getValue())) {
                medal.setHonorType(Medal.GOLD);
                tv.setText("复测金牌奖章");
            } else if ("2".equals(honor.getValue())) {
                medal.setHonorType(Medal.SILVER);
                tv.setText("复测银牌奖章");
            } else {
                medal.setHonorType(Medal.COPPER);
                tv.setText("复测铜牌奖章");
            }
        } else if (medalType == Medal.MONTH_CHAMPION) {//月度
            medal.setType(Medal.MONTH_CHAMPION);
            medal.setmText(honor.getCreateDate());
            tv.setText(honor.getValue() + "月月度冠军");
        } else if (medalType == Medal.NATION) {//全国
            String value = honor.getValue();
            medal.setType(Medal.NATION);
            medal.setmText("第" + honor.getValue() + "名");
            medal.setDate(honor.getCreateDate());
            if ("1".equals(value)) {
                medal.setHonorType(Medal.GOLD);
            } else if ("2".equals(value)) {
                medal.setHonorType(Medal.SILVER);
            } else if ("3".equals(value)) {
                medal.setHonorType(Medal.COPPER);
            } else {
                medal.setHonorType(Medal.NORMAL);
            }
            tv.setText("全国减重明星第" + honor.getValue() + "名");
        } else if (medalType == Medal.NATION_SPEC) {
            String value = honor.getValue();
            medal.setType(Medal.NATION_SPEC);
            medal.setmText("第" + honor.getValue() + "名");
            if ("1".equals(value)) {
                medal.setHonorType(Medal.GOLD);
            } else if ("2".equals(value)) {
                medal.setHonorType(Medal.SILVER);
            } else if ("3".equals(value)) {
                medal.setHonorType(Medal.COPPER);
            } else {
                medal.setHonorType(Medal.NORMAL);
            }
            tv.setText("全国减重明星第" + honor.getValue() + "名");
        }
        medal.refreshView(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.me_xun1:
            case R.id.me_xun2:
            case R.id.me_xun3:
            case R.id.re_xunzhang:
            case R.id.ll_xun:
                if (xunsatate) {
                    Intent honor = new Intent(this, StudentHonorGridActivity.class);
                    startActivity(honor);
                }
                else {
                    Util.toastMsg("暂无勋章");
                }
                break;

            case R.id.re_jianzh:
            case R.id.ll_stoty:
                if (storystate) {
                    Intent intent1 = new Intent(this, LossWeightLogActivity.class);
                    intent1.putExtra("accountId", Long.parseLong(userId));
                    intent1.putExtra("review", Integer.parseInt(review_flag));
                    startActivity(intent1);
                }
                else {
                    Util.toastMsg("暂无减重数据");
                }
                break;
            case R.id.im_pict1:
            case R.id.im_pict2:
            case R.id.im_pict3:
            case R.id.im_pict4:
            case R.id.im_pict5:
            case R.id.im_pict6:
            case R.id.ll_photo:
            case R.id.Re_personphoto:
                if (photostate) {
                    Intent intent2 = new Intent(this, UploadPhotoActivity.class);
                    startActivity(intent2);
                }
                else {
                    Util.toastMsg("暂无照片");
                }
                break;



        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        /*if(y<=0){
            pull.setEnabled(true);
        }else {
            pull.setEnabled(false);
        }*/
        float alpha=(1f*y/1000);
        Log.i("alpha色",alpha+"");
        this.setAlpha(alpha);
//        this.onScrollChanged(scrollView,);activity.setAlpha(alpha);
        rl_color.setAlpha(alpha);
    }
    public void setAlpha(float alpha){
        tintManager.setStatusBarAlpha(alpha);
        tintManager.setStatusBarTintResource(R.color.colorPrimaryDark);
    }
}
