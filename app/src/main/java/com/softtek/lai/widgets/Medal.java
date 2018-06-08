package com.softtek.lai.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;

/**
 * Created by jerry.guan on 6/30/2016.
 */
public class Medal extends FrameLayout{
    //金银铜
    public static final int GOLD=0;
    public static final int SILVER=1;
    public static final int COPPER=2;
    public static final int NORMAL=4;

    //奖章类型
    public static final int LOSS_WEIGHT=0;
    public static final int FUCE=1;
    public static final int MONTH_CHAMPION=2;
    public static final int NATION=3;
    public static final int NATION_SPEC=4;

    private String mText="";
    private int type;//什么类型的布局
    private String date;
    private int honorType;//奖章类型 金银铜
    private View mView;

    public Medal(Context context) {
        super(context);
        init(context,null);
    }

    public Medal(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public Medal(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs){
        TypedArray ta=context.obtainStyledAttributes(attrs,R.styleable.Medal);
        mText=ta.getString(R.styleable.Medal_medaltext);
        type=ta.getInt(R.styleable.Medal_medaltype,-1);
        date=ta.getString(R.styleable.Medal_medaldate);
        honorType=ta.getInt(R.styleable.Medal_medallevel,COPPER);
        ta.recycle();
        initView(context);
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setHonorType(int honorType) {
        this.honorType = honorType;
    }

    public void refreshView(Context context) {
        removeAllViews();
        mView=null;
        initView(context);
        invalidate();
    }

    private void initView(Context context){
        switch (type){
            case LOSS_WEIGHT:
                mView=LayoutInflater.from(context).inflate(R.layout.medal_loss_weight,this);
                TextView tv= (TextView) mView.findViewById(R.id.tv_loss_weight);
                tv.setText(mText);
                break;
            case FUCE:
                mView=LayoutInflater.from(context).inflate(R.layout.medal_fuce,this);
                ImageView iv= (ImageView) mView.findViewById(R.id.iv_fuce_honor);
                if(honorType==GOLD){
                    iv.setBackgroundResource(R.drawable.img_student_honor_jin);
                }else if(honorType==SILVER){
                    iv.setBackgroundResource(R.drawable.img_student_honor_yin);
                }else{
                    iv.setBackgroundResource(R.drawable.img_student_honor_tong);
                }
                break;
            case MONTH_CHAMPION:
                mView=LayoutInflater.from(context).inflate(R.layout.medal_month_champion,this);
                TextView monthTv= (TextView) mView.findViewById(R.id.tv_month_champion);
                monthTv.setText(mText);
                break;
            case NATION:
                mView=LayoutInflater.from(context).inflate(R.layout.medal_nation,this);
                ImageView nationIv= (ImageView) mView.findViewById(R.id.iv_honor);
                nationIv.setBackgroundResource(R.drawable.img_student_honor_star);
                TextView nationCh= (TextView) mView.findViewById(R.id.tv_month_champion);
                nationCh.setText(mText);
                TextView nationTime= (TextView) mView.findViewById(R.id.tv_nation_time);
                nationTime.setText(date);
                break;
            case NATION_SPEC:
                mView=LayoutInflater.from(context).inflate(R.layout.medal_nation,this);
                ImageView nationIv_spec= (ImageView) mView.findViewById(R.id.iv_honor);
                if(honorType==GOLD){
                    nationIv_spec.setBackgroundResource(R.drawable.img_student_honor_1);
                }else if(honorType==SILVER){
                    nationIv_spec.setBackgroundResource(R.drawable.img_student_honor_2);
                }else if(honorType==COPPER){
                    nationIv_spec.setBackgroundResource(R.drawable.img_student_honor_3);
                }else {
                    nationIv_spec.setBackgroundResource(R.drawable.img_student_honor_star);
                }
                TextView nationCh_spec= (TextView) mView.findViewById(R.id.tv_month_champion);
                nationCh_spec.setText(mText);
                TextView nationTime_spec= (TextView) mView.findViewById(R.id.tv_nation_time);
                nationTime_spec.setText(date);
                break;
        }
    }


}
