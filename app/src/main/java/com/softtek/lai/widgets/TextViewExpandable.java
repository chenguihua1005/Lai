package com.softtek.lai.widgets;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;


/**
 * 按行数进行折叠带过渡动画的TextView
 * <br>custom TextView that can be expanded with a smooth transition animation
 */
public class TextViewExpandable extends LinearLayout
        implements
        OnClickListener {

    /**
     * TextView
     */
    private CommentTextView textView;

    /**
     * 收起/全部TextView
     * <br>shrink/expand TextView
     */
    private TextView tvState;


    /**
     * 底部是否折叠/收起的父类布局
     * <br>shrink/expand layout parent
     */
    private RelativeLayout rlToggleLayout;

    /**
     * 全部/收起文本的字体颜色
     * <br>color of shrink/expand text
     */
    private int textViewStateColor;
    /**
     * 展开提示文本
     * <br>expand text
     */
    private String textExpand;
    /**
     * 收缩提示文本
     * <br>shrink text
     */
    private String textShrink;

    /**
     * 是否折叠显示的标示
     * <br>flag of shrink/expand
     */
    private boolean isShrink = false;

    /**
     * 是否需要折叠的标示
     * <br>flag of expand needed
     */
    private boolean isExpandNeeded = false;

    /**
     * 是否初始化TextView
     * <br>flag of TextView Initialization
     */
    private boolean isInitTextView = true;

    /**
     * 折叠显示的行数
     * <br>number of lines to expand
     */
    private int expandLines;

    /**
     * 文本的行数
     * <br>Original number of lines
     */
    private int textLines;

    /**
     * 显示的文本
     * <br>content text
     */
    private CharSequence textContent;

    /**
     * 显示的文本颜色
     * <br>content color
     */
    private int textContentColor;

    /**
     * 显示的文本字体大小
     * <br>content text size
     */
    private float textContentSize;

    public OnStateChangeListener onStateChangeListener;


    public TextViewExpandable(Context context, AttributeSet attrs) {
        super(context, attrs);
        initValue(context, attrs);
        initView(context);
        initClick();
    }

    private void initValue(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.TextViewExpandableAnimation);

        expandLines = ta.getInteger(
                R.styleable.TextViewExpandableAnimation_tvea_expandLines, 5);

        textViewStateColor = ta.getColor(R.styleable.TextViewExpandableAnimation_tvea_textStateColor, ContextCompat.getColor(context, R.color.blue));

        textShrink = ta.getString(R.styleable.TextViewExpandableAnimation_tvea_textShrink);
        textExpand = ta.getString(R.styleable.TextViewExpandableAnimation_tvea_textExpand);


        if (TextUtils.isEmpty(textShrink)) {
            textShrink = "收起";
        }

        if (TextUtils.isEmpty(textExpand)) {
            textExpand = "展开";
        }


        textContentColor = ta.getColor(R.styleable.TextViewExpandableAnimation_tvea_textContentColor, ContextCompat.getColor(context, R.color.colorAccent));
        textContentSize = ta.getDimension(R.styleable.TextViewExpandableAnimation_tvea_textContentSize, 14);

        ta.recycle();
    }

    private void initView(Context context) {
        setOrientation(VERTICAL);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_textview_expand, this);

        rlToggleLayout = (RelativeLayout) findViewById(R.id.rl_expand_text_view_animation_toggle_layout);

        textView = (CommentTextView) findViewById(R.id.tv_expand_text_view_animation);
        textView.setTextColor(textContentColor);
        textView.getPaint().setTextSize(textContentSize);


        tvState = (TextView) findViewById(R.id.tv_expand_text_view_animation_hint);
        tvState.setTextColor(textViewStateColor);

    }

    private void initClick() {
        tvState.setOnClickListener(this);
        rlToggleLayout.setOnClickListener(this);
    }

    public void setText(CharSequence charSequence) {

        textContent = charSequence;

        textView.setText(charSequence);

        ViewTreeObserver viewTreeObserver = textView.getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(new OnPreDrawListener() {

            @Override
            public boolean onPreDraw() {
                if (!isInitTextView) {
                    return true;
                }
                textLines = textView.getLineCount();
                isExpandNeeded = textLines > expandLines;
                isInitTextView = false;
                if (isExpandNeeded) {
                    isShrink = true;
                    textView.setMaxLines(expandLines);
                    textView.invalidate();
                    setExpandState(expandLines);
                } else {
                    isShrink = false;
                    doNotExpand();
                }
                return true;
            }
        });

        if (!isInitTextView) {
            textLines = textView.getLineCount();
        }

    }

    /**
     * 改变折叠状态（仅仅改变折叠与展开状态，不会隐藏折叠/展开图片布局）
     * change shrink/expand state(only change state,but not hide shrink/expand icon)
     *
     * @param endIndex
     */
    @SuppressWarnings("deprecation")
    private void changeExpandState(int endIndex) {
        rlToggleLayout.setVisibility(View.VISIBLE);
        if (endIndex < textLines) {
            tvState.setText(textExpand);
        } else {
            tvState.setText(textShrink);
        }
        if (!isExpand) {
            //展开
            isExpand = true;
            animateArrow(isExpand);
            tvState.invalidate();

        } else {
            isExpand = false;
            animateArrow(isExpand);
            //收起
            tvState.invalidate();
        }


    }

    /**
     * 设置折叠状态（如果折叠行数设定大于文本行数，那么折叠/展开图片布局将会隐藏,文本将一直处于展开状态）
     * change shrink/expand state(if number of expand lines bigger than original text lines,hide shrink/expand icon,and TextView will always be at expand state)
     *
     * @param endIndex
     */
    @SuppressWarnings("deprecation")
    private void setExpandState(int endIndex) {
        if (endIndex < textLines) {
            isShrink = true;
            rlToggleLayout.setVisibility(View.VISIBLE);
            textView.setOnClickListener(this);
            tvState.setText(textExpand);
        } else {
            isShrink = false;
            rlToggleLayout.setVisibility(View.GONE);
            textView.setOnClickListener(null);
            tvState.setText(textShrink);
        }
    }
    boolean isExpand;
    private void animateArrow(boolean shouldRotateUp) {
        int start = shouldRotateUp ? 0 : 10000;
        int end = shouldRotateUp ? 10000 : 0;
        ObjectAnimator animator = ObjectAnimator.ofInt(tvState.getCompoundDrawables()[2], "level", start, end);
        animator.setInterpolator(new LinearOutSlowInInterpolator());
        animator.setDuration(500);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                tvState.setText(!isExpand ? "展开" : "收起");
            }
        });
        animator.start();
    }

    /**
     * 无需折叠
     * do not expand
     */
    private void doNotExpand() {
        textView.setMaxLines(expandLines);
        rlToggleLayout.setVisibility(View.GONE);
        textView.setOnClickListener(null);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rl_expand_text_view_animation_toggle_layout || v.getId() == R.id.tv_expand_text_view_animation_hint) {
            clickImageToggle();
            if (null != onStateChangeListener) onStateChangeListener.onStateChange(isShrink);
        }

    }

    private void clickImageToggle() {
        if (isShrink) {
            // 如果是已经折叠，那么进行非折叠处理
            // do shrink action
            textView.setMaxLines(textLines);
            textView.invalidate();
            changeExpandState(textLines);
        } else {
            // 如果是非折叠，那么进行折叠处理
            // do expand action
            textView.setMaxLines(expandLines);
            textView.invalidate();
            changeExpandState(expandLines);
        }

        // 切换状态
        // set flag
        isShrink = !isShrink;
    }

    public void resetState(boolean isShrink) {

        this.isShrink = isShrink;
        if (textLines > expandLines) {
            if (isShrink) {
                rlToggleLayout.setVisibility(View.VISIBLE);
                textView.setOnClickListener(this);
                textView.setMaxLines(expandLines);
                tvState.setText(textExpand);
            } else {
                rlToggleLayout.setVisibility(View.VISIBLE);
                textView.setOnClickListener(this);
                textView.setMaxLines(textLines);
                tvState.setText(textShrink);
            }
        } else {
            doNotExpand();
        }
    }

    public int getExpandLines() {
        return expandLines;
    }

    public void setExpandLines(int newExpandLines) {
        int start = isShrink ? this.expandLines : textLines;
        int end = textLines < newExpandLines ? textLines : newExpandLines;
        textView.setMaxLines(end);
        textView.invalidate();
        setExpandState(end);
        this.expandLines = newExpandLines;
    }

    /**
     * 取得显示的文本内容
     * get content text
     *
     * @return content text
     */
    public CharSequence getTextContent() {
        return textContent;
    }


    public interface OnStateChangeListener {
        void onStateChange(boolean isShrink);
    }

    public void setOnStateChangeListener(OnStateChangeListener onStateChangeListener) {
        this.onStateChangeListener = onStateChangeListener;
    }

    public TextView getTextView() {
        return textView;
    }
}
