package com.softtek.lai.module.healthyreport;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.healthyreport.adapter.HealthyReportAdapter;
import com.softtek.lai.module.healthyreport.model.HealthyItem;
import com.softtek.lai.module.healthyreport.model.HealthyReport;
import com.softtek.lai.module.healthyreport.model.HealthyShareData;
import com.softtek.lai.module.laicheng.presenter.HealthyReportPresenter;
import com.softtek.lai.widgets.DividerItemDecoration;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.ArrayList;

import butterknife.InjectView;
import butterknife.OnClick;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_healthy_report)
public class HealthyReportActivity extends BaseActivity<HealthyReportPresenter> implements HealthyReportPresenter.HealthyReportView
        , HealthyReportAdapter.OnItemClickListener {
    //莱称数据来源
    public static final int SINCE_LAICHEN = 1;
    //其他数据来源
    public static final int SINCE_OTHER = 2;

    //访客
    public static final int VISITOR = 1;
    //非访客
    public static final int NON_VISITOR = 2;


    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.tv_user)
    TextView tv_user;
    @InjectView(R.id.tv_time)
    TextView tv_time;

    @InjectView(R.id.scroll)
    ScrollView scrollView;
    @InjectView(R.id.tv_arrow)
    TextView tv_arrow;
    @InjectView(R.id.rl_expand)
    RelativeLayout rl_expand;
    @InjectView(R.id.tv_des)
    TextView tv_des;
    @InjectView(R.id.list)
    RecyclerView list;
    @InjectView(R.id.fl_right)
    FrameLayout fl_share;

    ArrayList<HealthyItem> items = new ArrayList<>();
    HealthyReportAdapter adapter;
    String reportId;
    int since;
    int isVisitor;
    String accountId;

    @Override
    protected void initViews() {
        tv_title.setText("健康报告");
    }

    @OnClick(R.id.ll_left)
    public void doBack() {
        finish();
    }

    @OnClick(R.id.fl_right)
    public void doShare() {
        getPresenter().getShareData(reportId);
    }

    @Override
    protected void initDatas() {
        reportId = getIntent().getStringExtra("reportId");
        since = getIntent().getIntExtra("since", SINCE_LAICHEN);
        isVisitor = getIntent().getIntExtra("isVisitor", NON_VISITOR);

        list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        list.setHasFixedSize(true);
        list.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter = new HealthyReportAdapter(items, this,isVisitor==1);
        adapter.setListener(this);
        list.setAdapter(adapter);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });
        setPresenter(new HealthyReportPresenter(this));
        getPresenter().healthyReport(reportId);
    }

    private void animateArrow(boolean shouldRotateUp) {
        int start = shouldRotateUp ? 0 : 10000;
        int end = shouldRotateUp ? 10000 : 0;
        ObjectAnimator animator = ObjectAnimator.ofInt(tv_arrow.getCompoundDrawables()[2], "level", start, end);
        animator.setInterpolator(new LinearOutSlowInInterpolator());
        animator.setDuration(500);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                tv_arrow.setText(!isExpand ? "展开" : "收起");
            }
        });
        animator.start();
    }

    boolean isExpand;

    @OnClick(R.id.rl_expand)
    public void doExpand() {
        int lineCount = tv_des.getLineCount();
        if (!isExpand) {
            //展开
            isExpand = true;
            animateArrow(isExpand);
            tv_des.setMaxLines(lineCount);
            tv_des.invalidate();

        } else {
            isExpand = false;
            animateArrow(isExpand);
            //收起
            tv_des.setMaxLines(4);
            tv_des.invalidate();
        }
    }

    @Override
    public void getData(HealthyReport data) {
        fl_share.setVisibility(View.VISIBLE);
        accountId = data.getAccountId();
        tv_user.setText(data.getUsername());
        tv_time.setText(data.getMeasureTime());
        String des = data.getBodyTypeDesc();
        String[] split;
        if (TextUtils.isEmpty(des)) {
            tv_des.setVisibility(View.GONE);
        } else {
            tv_des.setVisibility(View.VISIBLE);
            if (des.contains("<br />")) {
                split = des.split("<br />");
            }else {
                split = des.split("<br/>");
            }
            SpannableStringBuilder ssb = new SpannableStringBuilder();
            if (split.length > 1) {
                SpannableString ss = new SpannableString(split[0]);
                ss.setSpan(new ForegroundColorSpan(0xFFF6BB07), 0, ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                ssb.append(ss);
                ssb.append("\n\n");
                ssb.append(split[1]);
            } else {
                if (des.startsWith(data.getBodyTypeTitle())) {
                    //截取开头的字符
                    int lenght = data.getBodyTypeTitle().length();
                    if (des.indexOf(":") == lenght || des.indexOf("：") == lenght) {
                        String title = des.substring(0, data.getBodyTypeTitle().length() + 1);
                        SpannableString ss = new SpannableString(title);
                        ss.setSpan(new ForegroundColorSpan(0xFFF6BB07), 0, ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        ssb.append(ss);
                        ssb.append("\n");
                        ssb.append(des.substring(data.getBodyTypeTitle().length() + 1));
                    }
                } else {
                    ssb.append(des);
                }
            }
            tv_des.setText(ssb);
            final ViewTreeObserver viewTreeObserver = tv_des.getViewTreeObserver();
            viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

                @Override
                public boolean onPreDraw() {
                    viewTreeObserver.removeOnPreDrawListener(this);
                    int lines = tv_des.getLineCount();
                    if (lines > 4) {
                        rl_expand.setVisibility(View.VISIBLE);
                    } else {
                        rl_expand.setVisibility(View.GONE);
                    }
                    return true;
                }
            });
        }
        items.addAll(data.getItemList());
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(int position) {
        //跳转到曲线图
        HealthyItem item = items.get(position);
        Intent intent = new Intent(this, HealthyChartActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("isVisitor", isVisitor);
        bundle.putString("accountId", accountId);
        bundle.putString("recordId", reportId);
        intent.putExtra("base", bundle);
        intent.putExtra("pid", item.getPid());
        intent.putParcelableArrayListExtra("items", items);
        startActivity(intent);
    }

    @Override
    public void getShareData(HealthyShareData data) {
        StringBuilder builder=new StringBuilder();
        builder.append("体重 +");
        builder.append(data.getWeight());
        builder.append("\n");
        builder.append("体脂率 +");
        builder.append(data.getBodyfat());
        builder.append("\n");
        builder.append("身体年龄 +");
        builder.append(data.getAge());
        String url=AddressManager.get("shareHost")+"ShareLBRecord?recordId="+reportId;
        String title_value = "莱聚+体测，精彩人生";
        showDialog(title_value,builder.toString(),url);
    }

    //分享对话框
    private void showDialog(final String title, final String context, final String url) {
        final Dialog dialog = new Dialog(this, R.style.custom_dialog);
        dialog.setCanceledOnTouchOutside(true);
        Window win = dialog.getWindow();
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.x = 120;
        params.y = 100;
        assert win != null;
        win.setAttributes(params);
        dialog.setContentView(R.layout.share_dialog);
        dialog.findViewById(R.id.ll_weixin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShareAction(HealthyReportActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .withTitle(title)
                        .withText(context)
                        .withTargetUrl(url)
                        .withMedia(new UMImage(HealthyReportActivity.this, R.drawable.img_share_logo))
                        .share();
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.ll_circle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShareAction(HealthyReportActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .withTitle(title)
                        .withText(context)
                        .withTargetUrl(url)
                        .withMedia(new UMImage(HealthyReportActivity.this, R.drawable.img_share_logo))
                        .share();
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.ll_sina).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShareAction(HealthyReportActivity.this)
                        .setPlatform(SHARE_MEDIA.SINA)
                        .withText(context + url)
                        .withMedia(new UMImage(HealthyReportActivity.this, R.drawable.img_share_logo))
                        .share();
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.dialog_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.share_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
