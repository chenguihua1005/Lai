package com.softtek.lai.module.bodygame2.view;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.bodygame2.model.memberDetialModel;
import com.softtek.lai.module.bodygame2.present.PersonDateManager;
import com.softtek.lai.module.studentbasedate.adapter.BaseDataFragmentAdapter;
import com.softtek.lai.module.studentbasedate.model.StudentBaseInfoModel;
import com.softtek.lai.module.studentbasedate.presenter.IStudentBaseDate;
import com.softtek.lai.module.studentbasedate.presenter.StudentBaseDateImpl;
import com.softtek.lai.module.studentbasedate.view.BaseDateFragment;
import com.softtek.lai.module.studentbasedate.view.ClassDynamicFragment;
import com.softtek.lai.module.studentbasedate.view.DimensionChartFragmentPC;
import com.softtek.lai.module.studentbasedate.view.LossWeightChartFragmentPC;
import com.softtek.lai.module.studetail.adapter.StudentDetailFragmentAdapter;
import com.softtek.lai.module.studetail.model.MemberModel;
import com.softtek.lai.module.studetail.presenter.IMemberInfopresenter;
import com.softtek.lai.module.studetail.presenter.MemberInfoImpl;
import com.softtek.lai.module.studetail.view.DimensionChartFragment;
import com.softtek.lai.module.studetail.view.LossWeightChartFragment;
import com.softtek.lai.module.studetail.view.LossWeightLogActivity;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.StringUtil;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_personal_data)
public class PersonalDataActivity extends BaseActivity implements View.OnClickListener, BaseFragment.OnFragmentInteractionListener {
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
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
    //奖章一
    @InjectView(R.id.ll_honorn1)
    LinearLayout ll_honorn1;
    //奖章二
    @InjectView(R.id.ll_honorn2)
    LinearLayout ll_honorn2;
    //奖章三
    @InjectView(R.id.ll_honorn3)
    LinearLayout ll_honorn3;
    @InjectView(R.id.cir_headim)
    CircleImageView cir_headim;
    @InjectView(R.id.tv_username)
    TextView tv_username;
    @InjectView(R.id.tv_tel)
    TextView tv_tel;
    @InjectView(R.id.tv_personclassname)
    TextView tv_personclassname;
    @InjectView(R.id.tv_SuperName)
    TextView tv_SuperName;
    @InjectView(R.id.tv_classdate)
    TextView tv_classdate;
    @InjectView(R.id.tv_weightday)
    TextView tv_weightday;
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
    private long accountId = 0;
    private long classId = 0;
    private String review_flag = "1";

    private IMemberInfopresenter memberInfopresenter;
    private List<Fragment> fragmentList = new ArrayList<>();
    PersonDateManager persondatemanager;

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        accountId = getIntent().getLongExtra("userId", 72);
        classId = getIntent().getLongExtra("classId", 15);
        review_flag = getIntent().getStringExtra("review");
        review_flag = review_flag == null ? "1" : review_flag;
        Map<String, String> params = new HashMap<>();
        params.put("userId", accountId + "");
        params.put("classId", classId + "");
        LossWeightChartFragment lwcf = LossWeightChartFragment.newInstance(params);
        DimensionChartFragment dcf = DimensionChartFragment.newInstance(params);
        fragmentList.add(lwcf);
        fragmentList.add(dcf);
        tabcontent.setAdapter(new StudentDetailFragmentAdapter(getSupportFragmentManager(), fragmentList));
        tabLayout.setupWithViewPager(tabcontent);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    protected void initDatas() {

        persondatemanager = new PersonDateManager();
        persondatemanager.doGetClmemberDetial(this, "301", "15");
    }


    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.ll:
                Intent intent = new Intent(this, LossWeightLogActivity.class);
                intent.putExtra("accountId", accountId);
                intent.putExtra("review", Integer.parseInt(review_flag));
                startActivity(intent);
                break;
        }
    }

    public void onloadCompleted(memberDetialModel data) {
        if (data != null) {
            String path = AddressManager.get("photoHost");
            if (!TextUtils.isEmpty(data.getClmInfo().getPhoto())) {
                Picasso.with(this).load(path + data.getClmInfo().getPhoto()).fit().error(R.drawable.default_icon_square).into(cir_headim);
                android.util.Log.i(">>>头像图片", path + data.getClmInfo().getPhoto());
            }
            tv_username.setText(data.getClmInfo().getUserName());
            tv_tel.setText(data.getClmInfo().getMobile());
            tv_personclassname.setText("班级：" + data.getClmInfo().getClassName());
            tv_SuperName.setText("助教：" + data.getClmInfo().getSuperName());
            String[] star = data.getClmInfo().getStartDate().split(" ");
            String[] stardate = star[0].split("-");
            String[] end = data.getClmInfo().getEndDate().split(" ");
            String[] enddate = end[0].split("-");
            tv_classdate.setText("（" + stardate[0] + "." + stardate[1] + "." + stardate[2] + "-" + enddate[0] + "." + enddate[1] + "." + enddate[2] + "）");
            if (data.getLossStory() == null) {

            } else {

            }
            for (int i=0;i<data.getHonorList().size();i++)
            {
                    getview(data.getHonorList().get(i).getHonorType(), i, data.getHonorList().get(i).getValue());

            }
//            for (int i = 0; i < data.getHonorList().size(); i++) {
//                if (data.getHonorList().get(i).getHonorStatus().equals("true")) {
//                    if (data.getHonorList().get(i).getHonorType().equals("1")) {
//                        if (data.getHonorList().get(i).getValue().equals("1")) {
//                            LayoutInflater.from(this).inflate(R.layout.person_honor_fc_item, ll_honorn1);
//                            ImageView img_fuce = (ImageView) findViewById(R.id.img_fuce);
//                            img_fuce.setImageResource(R.drawable.img_student_honor_tong);
//                        } else if (data.getHonorList().get(i).getValue().equals("2")) {
//                            LayoutInflater.from(this).inflate(R.layout.person_honor_fc_item, ll_honorn1);
//                            ImageView img_fuce = (ImageView) findViewById(R.id.img_fuce);
//                            img_fuce.setImageResource(R.drawable.img_student_honor_yin);
//                        } else if (data.getHonorList().get(i).getValue().equals("3")) {
//                            LayoutInflater.from(this).inflate(R.layout.person_honor_fc_item, ll_honorn1);
//                            ImageView img_fuce = (ImageView) findViewById(R.id.img_fuce);
//                            img_fuce.setImageResource(R.drawable.img_student_honor_jin);
//                        }
//                    }
//                }
//            }
            for (int j = 0; j < data.getPhotoList().size(); j++) {
                if (!TextUtils.isEmpty(data.getPhotoList().get(j).getImgUrl())) {
                    if (j == 0) {
                        im_pict1.setVisibility(View.VISIBLE);
                        Picasso.with(this).load(path + data.getPhotoList().get(j).getImgUrl()).fit().error(R.drawable.default_icon_square).into(im_pict1);
                    } else if (j == 1) {
                        im_pict2.setVisibility(View.VISIBLE);
                        Picasso.with(this).load(path + data.getPhotoList().get(j).getImgUrl()).fit().error(R.drawable.default_icon_square).into(im_pict2);
                    } else if (j == 2) {
                        im_pict3.setVisibility(View.VISIBLE);
                        Picasso.with(this).load(path + data.getPhotoList().get(j).getImgUrl()).fit().error(R.drawable.default_icon_square).into(im_pict3);
                    } else if (j == 3) {
                        im_pict4.setVisibility(View.VISIBLE);
                        Picasso.with(this).load(path + data.getPhotoList().get(j).getImgUrl()).fit().error(R.drawable.default_icon_square).into(im_pict4);
                    } else if (j == 4) {
                        im_pict5.setVisibility(View.VISIBLE);
                        Picasso.with(this).load(path + data.getPhotoList().get(j).getImgUrl()).fit().error(R.drawable.default_icon_square).into(im_pict5);
                    } else if (j == 5) {
                        im_pict6.setVisibility(View.VISIBLE);
                        Picasso.with(this).load(path + data.getPhotoList().get(j).getImgUrl()).fit().error(R.drawable.default_icon_square).into(im_pict6);
                    }

                }
            }
            if (StringUtils.isNotEmpty(data.getClmInfo().getTotalLoss()) && Float.parseFloat(data.getClmInfo().getTotalLoss()) < 0) {
                tv_total_loss_tip.setText("共增重");
            } else {
                tv_total_loss_tip.setText("共减重");
            }
            tv_totle_lw.setText(Float.parseFloat(StringUtils.isEmpty(data.getClmInfo().getAfterWeight()) ? "0" : data.getClmInfo().getAfterWeight()) == 0 ? "0斤" : Math.abs(Float.parseFloat(data.getClmInfo().getTotalLoss())) + "斤");
            tv_loss_before.setText(StringUtil.getFloatValue(data.getClmInfo().getBeforeWeight()) + "斤");
            float lossAfter = StringUtil.getFloat(data.getClmInfo().getAfterWeight());
            tv_loss_after.setText(lossAfter == 0 ? "尚未复测" : StringUtil.getFloat(data.getClmInfo().getAfterWeight()) + "斤");
            if (lossAfter == 0) {
                tv_loss_after_tip.setVisibility(View.GONE);
            } else {
                tv_loss_after_tip.setVisibility(View.VISIBLE);
            }

            if (!StringUtils.isEmpty(data.getClmInfo().getBeforeImage())) {
                Picasso.with(this).load(path + data.getClmInfo().getBeforeImage()).fit().placeholder(R.drawable.default_icon_rect).error(R.drawable.default_icon_rect).into(iv_loss_before);
                android.util.Log.i(">>>>减重前图片", path + data.getClmInfo().getBeforeImage());
            }
            if (!StringUtils.isEmpty(data.getClmInfo().getAfterImage())) {
                Picasso.with(this).load(path + data.getClmInfo().getAfterImage()).fit().placeholder(R.drawable.default_icon_rect).error(R.drawable.default_icon_rect).into(iv_loss_after);
                android.util.Log.i(">>>>减重后图片", path + data.getClmInfo().getAfterImage());
            }

        }
    }

    public void getview(String type, int num, String value) {
        if (type.equals("0")) {

            LayoutInflater.from(this).inflate(R.layout.person_honor_jz_item, getview1(num,type,value));
            TextView tv_jz_value = (TextView) findViewById(R.id.tv_jz_value);
            tv_jz_value.setText(value);

        } else if (type.equals("1")) {
            LayoutInflater.from(this).inflate(R.layout.person_honor_fc_item, getview1(num,type,value));
            ImageView img_fuce= (ImageView) findViewById(R.id.img_fuce);
            if (value.equals("1"))
            {
                if (num==0)
                {
                    tv_valuetext.setText("复测铜牌");
                }
                else if (num==1)
                {
                    tv_valuetext2.setText("复测铜牌");
                }
                else {
                    tv_valuetext3.setText("复测铜牌");
                }
            }
            else if (value.equals("2")){
                img_fuce.setImageResource(R.drawable.img_student_honor_yin);
                if (num==0)
                {
                    tv_valuetext.setText("复测银牌");
                }
                else if (num==1)
                {
                    tv_valuetext2.setText("复测银牌");
                }
                else {
                    tv_valuetext3.setText("复测银牌");
                }
            }
            else {
                img_fuce.setImageResource(R.drawable.img_student_honor_jin);
                if (num==0)
                {
                    tv_valuetext.setText("复测银牌");
                }
                else if (num==1)
                {
                    tv_valuetext2.setText("复测银牌");
                }
                else {
                    tv_valuetext3.setText("复测银牌");
                }
            }
        } else if (type.equals("2")) {
            LayoutInflater.from(this).inflate(R.layout.person_honor_ygj_item, getview1(num,type,value));
            TextView tv_yuegj_value = (TextView) findViewById(R.id.tv_yuegj_value);
            tv_yuegj_value.setText("2016年7月");

        } else if (type.equals("3")) {
            LayoutInflater.from(this).inflate(R.layout.person_honor_star_item,getview1(num,type,value));
            TextView tv_jzstar_value= (TextView) findViewById(R.id.tv_jzstar_value);
            tv_jzstar_value.setText("20");
        }
    }

    public LinearLayout getview1(int n,String ty,String value) {
        LinearLayout view = ll_honorn1;
        if (n == 0) {
            view = ll_honorn1;
            if (ty.equals("1"))
            {
                if (value.equals("1"))
                {
                    tv_valuetext.setText("复测铜牌");
                }
                else if (value.equals("2"))
                {
                    tv_valuetext.setText("复测银牌");
                }
                else if (value.equals("3"))
                {
                    tv_valuetext.setText("复测金牌");
                }
            }
            else if (ty.equals("0"))
            {
                tv_valuetext.setText("减重"+value+"斤奖章");
            }
            else if (ty.equals("2"))
            {
                tv_valuetext.setText("月冠军"+value+"名奖章");
            }
            else if (ty.equals(""))
            {
                tv_valuetext.setText("全国排名第"+value+"名奖章");
            }
        } else if (n == 1) {
            view = ll_honorn2;
            if (ty.equals("1"))
            {
                if (value.equals("1"))
                {
                    tv_valuetext2.setText("复测铜牌");
                }
                else if (value.equals("2"))
                {
                    tv_valuetext2.setText("复测银牌");
                }
                else if (value.equals("3"))
                {
                    tv_valuetext2.setText("复测金牌");
                }
            }
            else if (ty.equals("0"))
            {
                tv_valuetext2.setText("减重"+value+"斤奖章");
            }
            else if (ty.equals("2"))
            {
                tv_valuetext2.setText("月冠军"+value+"名奖章");
            }
            else if (ty.equals(""))
            {
                tv_valuetext2.setText("全国排名第"+value+"名奖章");
            }
        } else {
            view = ll_honorn3;
            if (ty.equals("1"))
            {
                if (value.equals("1"))
                {
                    tv_valuetext3.setText("复测铜牌");
                }
                else if (value.equals("2"))
                {
                    tv_valuetext3.setText("复测银牌");
                }
                else if (value.equals("3"))
                {
                    tv_valuetext3.setText("复测金牌");
                }
            }
            else if (ty.equals("0"))
            {
                tv_valuetext3.setText("减重"+value+"斤奖章");
            }
            else if (ty.equals("2"))
            {
                tv_valuetext3.setText("月冠军"+value+"名奖章");
            }
            else if (ty.equals(""))
            {
                tv_valuetext3.setText("全国排名第"+value+"名奖章");
            }
        }
        return view;
    }
}