package com.softtek.lai.module.home.view;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.WindowInsetsCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.community.model.Comment;
import com.softtek.lai.module.community.presenter.OpenComment;
import com.softtek.lai.module.community.view.DynamicFragment;
import com.softtek.lai.module.community.view.FocusFragment;
import com.softtek.lai.module.home.adapter.MainPageAdapter;
import com.softtek.lai.module.laijumine.view.MineFragment;
import com.softtek.lai.utils.SoftInputUtil;
import com.softtek.lai.widgets.SimpleButton;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_home_actviity)
public class HomeActviity extends BaseActivity implements View.OnClickListener, BaseFragment.OnFragmentInteractionListener
        , OpenComment, View.OnLayoutChangeListener {

    @InjectView(R.id.content)
    ViewPager content;

    @InjectView(R.id.btn_home)
    SimpleButton btn_home;

    @InjectView(R.id.btn_healthy)
    SimpleButton btn_healthy;

    @InjectView(R.id.btn_healthy_record)
    SimpleButton btn_healthy_record;

    @InjectView(R.id.btn_mine)
    SimpleButton btn_mine;

    @InjectView(R.id.rl_content)
    RelativeLayout rl_content;

    private int currentId = 0;
    private boolean isClick = false;

    private List<Fragment> fragments = new ArrayList<>();

    @InjectView(R.id.rl_send)
    RelativeLayout rl_send;
    @InjectView(R.id.et_input)
    EditText et_input;
    @InjectView(R.id.btn_send)
    Button btn_send;

    String tag;


    @Override
    protected void initViews() {
        ViewCompat.setOnApplyWindowInsetsListener(rl_content, new OnApplyWindowInsetsListener() {
            @Override
            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                if(v.getFitsSystemWindows()){
                    Log.i("需要适配");
                    return insets.consumeSystemWindowInsets();
                }
                Log.i("不不不不不需要适配");
                return null;
            }
        });
        btn_home.setOnClickListener(this);
        btn_healthy.setOnClickListener(this);
        btn_healthy_record.setOnClickListener(this);
        btn_mine.setOnClickListener(this);
        content.setOffscreenPageLimit(4);
        //**************************
        et_input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1000)});
        et_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    btn_send.setEnabled(false);
                } else {
                    btn_send.setEnabled(true);
                }
            }
        });
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiden();
                Integer position = (Integer) v.getTag();
                String commentStr = et_input.getText().toString();
                et_input.setText("");
                Comment comment = new Comment();
                comment.Comment = commentStr;
                comment.CommentUserId = UserInfoModel.getInstance().getUserId();
                comment.CommentUserName = UserInfoModel.getInstance().getUser().getNickname();
                comment.isReply = 0;
                if (DynamicFragment.DYNAMIC.equals(tag)) {
                    ((DynamicFragment) fragments.get(1)).doSend(position, comment);
                } else if (FocusFragment.FOCUSFRAGMENT.equals(tag)) {
                    ((FocusFragment) fragments.get(2)).doSend(position, comment);
                }
            }
        });
    }

    @Override
    protected void initDatas() {
        fragments.add(new HomeFragment());
        fragments.add(DynamicFragment.getInstance(this));
        fragments.add(FocusFragment.getInstance(this));//关注
        fragments.add(new MineFragment());
        content.setAdapter(new MainPageAdapter(getSupportFragmentManager(), fragments));
        content.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (!isClick) {
                    setChildProgress(position, 1 - positionOffset);
                    setChildProgress(position + 1, positionOffset);
                }

            }

            @Override
            public void onPageSelected(int position) {
                //页面切换了
                isClick = false;
                if (!UserInfoModel.getInstance().isVr()){
                    TypedValue typedValue = new  TypedValue();
                    getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
                    if(position==3){
                        ViewGroup contentLayout = (ViewGroup)findViewById(android.R.id.content);
                        // 设置Activity layout的fitsSystemWindows
                        View contentChild = contentLayout.getChildAt(0);
                        contentChild.setFitsSystemWindows(false);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(typedValue.data);
                        }
                        tintManager.setStatusBarTintColor(typedValue.data);
                    }else {
                        ViewGroup contentLayout = (ViewGroup)findViewById(android.R.id.content);
                        // 设置Activity layout的fitsSystemWindows
                        View contentChild = contentLayout.getChildAt(0);
                        contentChild.setFitsSystemWindows(true);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(typedValue.data);
                        }
                        tintManager.setStatusBarTintResource(typedValue.resourceId);
                    }
                }else {
                    ViewGroup contentLayout = (ViewGroup)findViewById(android.R.id.content);
                    // 设置Activity layout的fitsSystemWindows
                    View contentChild = contentLayout.getChildAt(0);
                    contentChild.setFitsSystemWindows(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                currentId = state;
            }
        });

        restoreState();
        btn_home.setProgress(1);
        currentId = 0;
        content.setCurrentItem(0);
//        if (!isTaskRoot()) {
//            finish();
//            return;
//        }
        //测试更新
    }


    @Override
    public void onClick(View v) {
        isClick = true;
        restoreState();
        switch (v.getId()) {
            case R.id.btn_home:
                btn_home.setProgress(1);
                currentId = 0;
                break;
            case R.id.btn_healthy:
                btn_healthy.setProgress(1);
                currentId = 1;
                MobclickAgent.onEvent(this, "HealthyCommunityEvent");
                break;
            case R.id.btn_healthy_record:
                btn_healthy_record.setProgress(1);
                currentId = 2;
                MobclickAgent.onEvent(this, "HealthyRecordEvent");
                break;
            case R.id.btn_mine:
                btn_mine.setProgress(1);
                currentId = 3;
                break;
        }
        content.setCurrentItem(currentId, false);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }


    private void setChildProgress(int position, float progress) {
        switch (position) {
            case 0:
                btn_home.setProgress(progress);
                break;
            case 1:
                btn_healthy.setProgress(progress);
                break;
            case 2:
                btn_healthy_record.setProgress(progress);
                break;
            case 3:
                btn_mine.setProgress(progress);
                break;
        }
    }

    private void restoreState() {
        btn_home.setProgress(0);
        btn_healthy.setProgress(0);
        btn_healthy_record.setProgress(0);
        btn_mine.setProgress(0);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && rl_send.getVisibility() == View.VISIBLE) {
            rl_send.setVisibility(View.INVISIBLE);
            SoftInputUtil.hidden(this);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void doOpen(final int position, final int itemHeight, final String tag) {
        this.tag = tag;
        btn_send.setTag(position);
        rl_send.setVisibility(View.VISIBLE);
        et_input.setFocusable(true);
        et_input.setFocusableInTouchMode(true);
        et_input.requestFocus();
        et_input.postDelayed(new Runnable() {
            @Override
            public void run() {
                SoftInputUtil.showInputAsView(HomeActviity.this, et_input);
            }
        }, 500);
        rl_send.postDelayed(new Runnable() {
            @Override
            public void run() {
                int[] position2 = new int[2];
                rl_send.getLocationOnScreen(position2);
                if (DynamicFragment.DYNAMIC.equals(tag)) {
                    ((DynamicFragment) fragments.get(1)).doScroll(position, itemHeight, position2[1]);
                } else if (FocusFragment.FOCUSFRAGMENT.equals(tag)) {
                    ((FocusFragment) fragments.get(2)).doScroll(position, itemHeight, position2[1]);
                }
            }
        }, 1000);
    }

    @Override
    public void hiden() {
        if (rl_send.getVisibility() == View.VISIBLE) {
            rl_send.setVisibility(View.INVISIBLE);
            SoftInputUtil.hidden(this);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        rl_send.addOnLayoutChangeListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        rl_send.removeOnLayoutChangeListener(this);
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (rl_send.getVisibility() == View.VISIBLE) {
            if (oldBottom - bottom < 0) {
                //键盘收起来
                rl_send.setVisibility(View.INVISIBLE);
            }

        }
    }

}
