/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.widgets;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.laiClassroom.SubjectdetailActivity;
import com.softtek.lai.module.laiClassroom.model.RecommendModel;
import com.softtek.lai.utils.DisplayUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * android banner图
 */
public class RollHeaderViewT extends FrameLayout implements OnPageChangeListener {

    private Context mContext;
    private ViewPager mViewPager;
    private LinearLayout mDotLl;
    private List<String> mUrlList;
    private List<String> mNameList;
    private List<String> mHotList;
    private TextView tv_subject_name,tv_hotclick;
    private List<RecommendModel>recommendModels=new ArrayList<>();


    private List<ImageView> dotList = null;
    private MyAdapter mAdapter = null;

    private int prePosition = 0;

    private HeaderViewClickListener headerViewClickListener;

    public RollHeaderViewT(Context context) {
        this(context, null);
    }

    public RollHeaderViewT(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RollHeaderViewT(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        initView();
        initData();
        initListener();
    }

    //初始化view
    private void initView() {
        View.inflate(mContext, R.layout.view_header_subject, this);
        mViewPager = (ViewPager) findViewById(R.id.vp);
        mDotLl = (LinearLayout) findViewById(R.id.ll_dot);
        tv_subject_name= (TextView) findViewById(R.id.tv_subject_name);
        tv_hotclick= (TextView) findViewById(R.id.tv_hotclick);


        //让banner的高度是屏幕的1/4
        ViewGroup.LayoutParams vParams = mViewPager.getLayoutParams();
        vParams.height = (int) (DisplayUtil.getMobileHeight(mContext) * 0.25);
        mViewPager.setLayoutParams(vParams);
        tv_subject_name.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SubjectdetailActivity.class);
                intent.putExtra("topictitle", recommendModels.get(prePosition).getTopicName());
                intent.putExtra("topicId", recommendModels.get(prePosition).getTopicId());
                mContext.startActivity(intent);
            }
        });
        mViewPager.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    //初始化数据
    private void initData() {
        dotList = new ArrayList<ImageView>();
        mAdapter = new MyAdapter();

    }

    private void initListener() {
        mViewPager.addOnPageChangeListener(this);
    }


    /**
     * 设置数据
     *
     * @param recommendModels
     */
    public void setImgUrlData(List<RecommendModel>recommendModels) {
        prePosition=0;
        this.recommendModels=recommendModels;
        List<String>urlList=new ArrayList<>();
        List<String>nameList=new ArrayList<>();
        List<String>hotList=new ArrayList<>();
        for (int i=0;i<recommendModels.size();i++) {

            urlList.add(AddressManager.get("photoHost")+recommendModels.get(i).getTopicImg());
            nameList.add(recommendModels.get(i).getTopicName());
            hotList.add(String.valueOf(recommendModels.get(i).getClicks()));

        }
        this.mUrlList = urlList;
        this.mNameList = nameList;
        this.mHotList = hotList;

        if (mUrlList != null && !mUrlList.isEmpty()) {
            //清空数据
            dotList.clear();
            mDotLl.removeAllViews();
            ImageView dotIv;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DisplayUtil.dip2px(mContext, 5), DisplayUtil.dip2px(mContext, 5));
            //Log.i("一共有多少个点？" + mUrlList.size());
            for (int i = 0; i < mUrlList.size(); i++) {
                dotIv = new ImageView(mContext);
                if (i == 0) {
                    dotIv.setBackgroundResource(R.drawable.banner_dot_select);
                } else {
                    dotIv.setBackgroundResource(R.drawable.banner_dot_normal);
                }
                //设置点的间距
                params.setMargins(0, 0, DisplayUtil.dip2px(mContext, 5), 0);
                dotIv.setLayoutParams(params);

                //添加点到view上
                mDotLl.addView(dotIv);
                //添加到集合中, 以便控制其切换
                dotList.add(dotIv);
            }
        }

        mAdapter = new MyAdapter();
        mViewPager.setAdapter(mAdapter);
        tv_subject_name.setText(recommendModels.get(prePosition).getTopicName());
        tv_hotclick.setText(String.valueOf(recommendModels.get(prePosition).getClicks()));


    }


    /**
     * 设置点击事件
     *
     * @param headerViewClickListener
     */
    public void setOnHeaderViewClickListener(HeaderViewClickListener headerViewClickListener) {
        this.headerViewClickListener = headerViewClickListener;

    }

    public interface HeaderViewClickListener {
        void HeaderViewClick(int position);

    }

    private class MyAdapter extends PagerAdapter {

        //为了复用
        private List<RectangleImage> imgCache = new ArrayList<RectangleImage>();



        @Override
        public int getCount() {
            return mUrlList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
//            LinearLayout ll_subject;
            RectangleImage iv;
            Log.i("position",position%2+"");
//            final int positon=0;
//            RectangleImage image;
//            image= (RectangleImage) container.findViewById(R.id.reim_sub);

            //获取ImageView对象
            if (imgCache.size() > 0) {
                iv = imgCache.remove(0);
//                ll_subject=llCache.remove(0);
            } else {
//                ll_subject=new LinearLayout(mContext);
            iv = new RectangleImage(mContext);
            }
            iv.setScaleType(ScaleType.FIT_XY);
            iv.setPadding(10,0,10,0);


            iv.setOnTouchListener(new OnTouchListener() {
                private int downX = 0;
                private long downTime = 0;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
//                            mAutoRollRunnable.stop();
                            //获取按下的x坐标
                            downX = (int) v.getX();
                            downTime = System.currentTimeMillis();
                            break;
                        case MotionEvent.ACTION_UP:
//                            mAutoRollRunnable.start();
                            int moveX = (int) v.getX();
                            long moveTime = System.currentTimeMillis();
                            if (downX == moveX && (moveTime - downTime < 500)) {//点击的条件
                                //轮播图回调点击事件
                                if (headerViewClickListener != null) {
                                    headerViewClickListener.HeaderViewClick(position % mUrlList.size());

                                }
                            }
                            Intent intent = new Intent(getContext(), SubjectdetailActivity.class);
                            intent.putExtra("topictitle", recommendModels.get(prePosition).getTopicName());
                            intent.putExtra("topicId", recommendModels.get(prePosition).getTopicId());
                            mContext.startActivity(intent);
                            break;
                        case MotionEvent.ACTION_CANCEL:
//                            mAutoRollRunnable.start();
                            break;
                    }
                    return true;
                }
            });

            //加载图片
            if (mUrlList.size() > 0) {
                Log.i("positionmUrlList.size()",position % mUrlList.size()+"");
                Picasso.with(mContext).load(mUrlList.get(position % mUrlList.size())).fit().placeholder(R.drawable.default_laiclass12)
                        .error(R.drawable.default_laiclass12).centerCrop().into(iv);
//                View vi= LayoutInflater.from(mContext).inflate(R.layout.contain_subject_layout,null,false);
//                ll_subject= (LinearLayout) vi;
            }
            ((ViewPager) container).addView(iv);
//            ((ViewPager) container).addView(ll_subject);


            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (object != null && object instanceof ImageView) {
                RectangleImage iv = (RectangleImage) object;
                ((ViewPager) container).removeView(iv);
                imgCache.add(iv);
//                LinearLayout ll_subject=(LinearLayout)object;
//
//                ((ViewPager) container).removeView(ll_subject);
//                llCache.add(ll_subject);
            }
        }

    }

    @Override
    public void onPageSelected(int position) {
        dotList.get(prePosition).setBackgroundResource(R.drawable.banner_dot_normal);
        dotList.get(position % dotList.size()).setBackgroundResource(R.drawable.banner_dot_select);
        prePosition = position % dotList.size();
        tv_subject_name.setText(mNameList.get(prePosition));
        tv_hotclick.setText(mHotList.get(prePosition));

    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }


    //停止轮播
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}

