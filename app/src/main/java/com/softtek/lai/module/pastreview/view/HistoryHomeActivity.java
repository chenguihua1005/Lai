package com.softtek.lai.module.pastreview.view;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.pastreview.honors.Medal;
import com.softtek.lai.module.pastreview.model.Honor;
import com.softtek.lai.module.pastreview.model.LossStory;
import com.softtek.lai.module.pastreview.model.PastBaseData;
import com.softtek.lai.module.pastreview.model.PastClass;
import com.softtek.lai.module.pastreview.model.Photo;
import com.softtek.lai.module.pastreview.presenter.PastReviewManager;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.StringUtil;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_history_home)
public class HistoryHomeActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_title_date)
    TextView tv_title_date;

    @InjectView(R.id.tv_totle_lw)
    TextView tv_totle_lw;//共减重
    @InjectView(R.id.iv_loss_before)
    ImageView iv_loss_before;
    @InjectView(R.id.iv_loss_after)
    ImageView iv_loss_after;
    @InjectView(R.id.tv_loss_before)
    TextView tv_loss_before;
    @InjectView(R.id.tv_loss_after)
    TextView tv_loss_after;
    @InjectView(R.id.ll_story)
    LinearLayout ll_story;

    @InjectView(R.id.tv_day)
    TextView tv_day;
    @InjectView(R.id.tv_month)
    TextView tv_month;
    @InjectView(R.id.iv_image)
    ImageView iv_image;
    @InjectView(R.id.tv_content)
    TextView tv_content;
    @InjectView(R.id.cb_zan)
    CheckBox cb_zan;

    @InjectView(R.id.iv_first)
    ImageView iv_first;
    @InjectView(R.id.iv_second)
    ImageView iv_second;
    @InjectView(R.id.iv_third)
    ImageView iv_third;

    @InjectView(R.id.rel_my_base)
    RelativeLayout rel_my_base;
    @InjectView(R.id.rel_my_honor)
    RelativeLayout rel_my_honor;
    @InjectView(R.id.rel_my_weight)
    RelativeLayout rel_my_weight;
    @InjectView(R.id.rel_my_photo)
    RelativeLayout rel_my_photo;

    private PastReviewManager manager;

    @InjectView(R.id.medal1)
    Medal medal1;
    @InjectView(R.id.medal2)
    Medal medal2;
    @InjectView(R.id.medal3)
    Medal medal3;
    @InjectView(R.id.tv_medal1)
    TextView tv_medal1;
    @InjectView(R.id.tv_medal2)
    TextView tv_medal2;
    @InjectView(R.id.tv_medal3)
    TextView tv_medal3;

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        rel_my_base.setOnClickListener(this);
        rel_my_honor.setOnClickListener(this);
        rel_my_weight.setOnClickListener(this);
        rel_my_photo.setOnClickListener(this);
        iv_first.setOnClickListener(this);
        iv_second.setOnClickListener(this);
        iv_third.setOnClickListener(this);
        ll_story.setOnClickListener(this);

    }

    long userId = 0;
    long classId = 0;

    @Override
    protected void initDatas() {
        String className = getIntent().getStringExtra("className");
        String classDate = getIntent().getStringExtra("classDate");
        tv_title.setText(className);
        tv_title_date.setText(classDate);
        userId = Long.parseLong(getIntent().getStringExtra("userId"));
        classId = Long.parseLong(getIntent().getStringExtra("classId"));
        manager = new PastReviewManager();
        dialogShow("加载中...");
        manager.loadClassDetail(this, userId, classId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.rel_my_base:
                Intent baseIntent = new Intent(this, PcPastBaseDataActivity.class);
                baseIntent.putExtra("userId", userId);
                baseIntent.putExtra("classId", classId);
                startActivity(baseIntent);
                break;
            case R.id.rel_my_honor:
                Intent honorIntent = new Intent(this, HistoryStudentHonorActivity.class);
                honorIntent.putExtra("userId", userId);
                honorIntent.putExtra("classId", classId);
                startActivity(honorIntent);
                break;
            case R.id.ll_story:
            case R.id.rel_my_weight:
                Intent storyIntent = new Intent(this, StoryActivity.class);
                storyIntent.putExtra("userId", userId);
                storyIntent.putExtra("classId", classId);
                startActivity(storyIntent);
                break;
            case R.id.rel_my_photo:
                Intent photoIntent = new Intent(this, PassPhotoActivity.class);
                photoIntent.putExtra("userId", userId);
                photoIntent.putExtra("classId", classId);
                startActivity(photoIntent);
                break;
            case R.id.iv_first://点击相册第一张图片
                Intent photoIntent1 = new Intent(this, PassPhotoActivity.class);
                photoIntent1.putExtra("userId", userId);
                photoIntent1.putExtra("classId", classId);
                startActivity(photoIntent1);
                break;
            case R.id.iv_second://点击相册第一张图片
                Intent photoIntent2 = new Intent(this, PassPhotoActivity.class);
                photoIntent2.putExtra("userId", userId);
                photoIntent2.putExtra("classId", classId);
                startActivity(photoIntent2);
                break;
            case R.id.iv_third://点击相册第一张图片
                Intent photoIntent3 = new Intent(this, PassPhotoActivity.class);
                photoIntent3.putExtra("userId", userId);
                photoIntent3.putExtra("classId", classId);
                startActivity(photoIntent3);
                break;
        }
    }

    public void loadData(PastClass pastClass) {
        dialogDissmiss();
        try {
            if (pastClass == null) {
                return;
            }
            PastBaseData baseData = pastClass.getBaseData();
            String basePath = AddressManager.get("photoHost");
            if (baseData != null) {
                setText(tv_totle_lw, StringUtil.getFloatValue(baseData.getTotalLoss()) + "斤");
                setText(tv_loss_before, StringUtil.getFloatValue(baseData.getBeforeWeight()) + "斤");
                setText(tv_loss_after, StringUtil.getFloatValue(baseData.getAfterWeight()) + "斤");
                if (iv_loss_after != null || iv_loss_before != null) {
                    if (StringUtils.isNotEmpty(baseData.getBeforeImage())) {
                        Picasso.with(this).load(basePath + baseData.getBeforeImage()).fit().placeholder(R.drawable.default_icon_square).error(R.drawable.default_icon_square)
                                .into(iv_loss_before);
                    } else {
                        Picasso.with(this).load(R.drawable.default_icon_square).into(iv_loss_before);
                    }
                    if (StringUtils.isNotEmpty(baseData.getAfterImage())) {
                        Picasso.with(this).load(basePath + baseData.getAfterImage()).fit().placeholder(R.drawable.default_icon_square).error(R.drawable.default_icon_square)
                                .into(iv_loss_after);
                    } else {
                        Picasso.with(this).load(R.drawable.default_icon_square).into(iv_loss_after);
                    }
                }
            }

            LossStory story = pastClass.getLossLog();
            if (story != null) {
                int day = DateUtil.getInstance(DateUtil.yyyy_MM_dd).getDay(story.getCreateDate());
                int month = DateUtil.getInstance(DateUtil.yyyy_MM_dd).getMonth(story.getCreateDate());
                setText(tv_day, day + "");
                setText(tv_month, month + "月");
                setText(tv_content, story.getLogContent());
                if (cb_zan != null) {
                    cb_zan.setText(story.getPriase());
                }
                if (StringUtils.isNotEmpty(story.getImgUrl())) {
                    Picasso.with(this).load(basePath + story.getImgUrl()).fit().placeholder(R.drawable.default_icon_square).error(R.drawable.default_icon_square)
                            .into(iv_image);
                } else {
                    Picasso.with(this).load(R.drawable.default_icon_square).into(iv_image);
                }
            }
            List<Honor> honors = pastClass.getHonor();
            if (honors != null && !honors.isEmpty()) {
                for (int i = 0; i < honors.size(); i++) {
                    Honor honor = honors.get(i);
                    switch (i) {
                        case 0:
                            setMedal(honor, medal1, tv_medal1);
                            break;
                        case 1:
                            setMedal(honor, medal2, tv_medal2);
                            break;
                        case 2:
                            setMedal(honor, medal3, tv_medal3);
                            break;
                    }
                }
            }

            List<Photo> photos = pastClass.getImgBook();
            int px = (DisplayUtil.getMobileWidth(this) - 16) / 3;
            if (photos != null && !photos.isEmpty()) {
                for (int i = 0; i < photos.size(); i++) {
                    String url = basePath + photos.get(i).getImgUrl();
                    Log.i("我的相册>>>" + url);
                    switch (i) {
                        case 0:
                            if (iv_first != null) {
                                iv_first.setVisibility(View.VISIBLE);
                                ViewGroup.LayoutParams params = iv_first.getLayoutParams();
                                params.height = px;
                                params.width = px;
                                iv_first.setLayoutParams(params);
                                Picasso.with(this).load(url).fit().placeholder(R.drawable.default_icon_square).error(R.drawable.default_icon_square)
                                        .into(iv_first);
                            }
                            break;
                        case 1:
                            if (iv_second != null) {
                                iv_second.setVisibility(View.VISIBLE);
                                ViewGroup.LayoutParams params = iv_second.getLayoutParams();
                                params.height = px;
                                params.width = px;
                                iv_second.setLayoutParams(params);
                                Picasso.with(this).load(url).fit().placeholder(R.drawable.default_icon_square).error(R.drawable.default_icon_square)
                                        .into(iv_second);
                            }
                            break;
                        case 2:
                            if (iv_third != null) {
                                iv_third.setVisibility(View.VISIBLE);
                                ViewGroup.LayoutParams params = iv_third.getLayoutParams();
                                params.height = px;
                                params.width = px;
                                iv_third.setLayoutParams(params);
                                Picasso.with(this).load(url).fit().placeholder(R.drawable.default_icon_square).error(R.drawable.default_icon_square)
                                        .into(iv_third);
                            }
                            break;
                    }
                }
            }
        }catch (Exception e){

        }

    }

    private void setMedal(Honor honor, Medal medal, TextView tv) {
        int medalType = Integer.parseInt(honor.getHonorType());
        if (medalType == Medal.LOSS_WEIGHT) {
            medal.setType(Medal.LOSS_WEIGHT);
            medal.setmText(honor.getValue() + "斤");
            tv.setText("减重" + honor.getValue() + "斤奖章");
        } else if (medalType == Medal.FUCE) {//复测
            medal.setType(Medal.FUCE);
            if ("3".equals(honor.getValue())) {
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
        }
        medal.refreshView(this);
    }

    private void setText(TextView view, String text) {
        if (view != null) {
            view.setText(text);
        }
    }

}
