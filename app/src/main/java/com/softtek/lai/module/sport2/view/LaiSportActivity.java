package com.softtek.lai.module.sport2.view;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.home.adapter.MainPageAdapter;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.sport2.model.Unread;
import com.softtek.lai.module.sport2.net.SportService;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.SimpleButton;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_laisport)
public class LaiSportActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.btn_sport)
    SimpleButton btn_sport;
    @InjectView(R.id.btn_activity)
    SimpleButton btn_activity;
    @InjectView(R.id.btn_challenge)
    SimpleButton btn_challenge;
    @InjectView(R.id.btn_mine)
    SimpleButton btn_mine;

    @InjectView(R.id.content)
    ViewPager content;

    @InjectView(R.id.iv_unread)
    TextView iv_unread;


    private List<Fragment> fragments = new ArrayList<>();
    private int current = 0;
    private boolean isClick = false;
    @Override
    protected void initViews() {
        MobclickAgent.openActivityDurationTrack(false);
        btn_sport.setOnClickListener(this);
        btn_activity.setOnClickListener(this);
        btn_challenge.setOnClickListener(this);
        btn_mine.setOnClickListener(this);

        fragments.add(new SportFragment());
        fragments.add(new ActivityFragment());
        fragments.add(new PKListFragment());
        fragments.add(new SportMineFragment());
        content.setOffscreenPageLimit(3);
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
                current=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        //设置第一个fragment
        int type = getIntent().getIntExtra("type", 0);
        current = type;
        restoreState();
        switch (type) {
            case 0:
                btn_sport.setProgress(1);
                break;
            case 1:
                btn_activity.setProgress(1);
                break;
            case 2:
                btn_challenge.setProgress(1);
                break;
            case 3:
                btn_mine.setProgress(1);
                break;

        }
        content.setCurrentItem(current, false);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int type = intent.getIntExtra("type", 0);
        current = type;
        restoreState();
        switch (type) {
            case 0:
                btn_sport.setProgress(1);
                break;
            case 1:
                btn_activity.setProgress(1);
                break;
            case 2:
                btn_challenge.setProgress(1);
                break;
            case 3:
                btn_mine.setProgress(1);
                break;

        }
        content.setCurrentItem(current, false);
    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onClick(View v) {
        restoreState();
        switch (v.getId()) {
            case R.id.btn_sport:
                btn_sport.setProgress(1);
                if (current == 0) {
                    return;
                }
                current = 0;
                break;
            case R.id.btn_activity:
                btn_activity.setProgress(1);
                if (current == 1) {
                    return;
                }
                current = 1;
                break;
            case R.id.btn_challenge:
                btn_challenge.setProgress(1);
                if (current == 2) {
                    return;
                }
                current = 2;
                break;
            case R.id.btn_mine:
                btn_mine.setProgress(1);
                if (current == 3) {
                    return;
                }
                current = 3;
                break;
        }
        content.setCurrentItem(current, false);

    }

    private void setChildProgress(int position, float progress) {
        switch (position) {
            case 0:
                btn_sport.setProgress(progress);
                break;
            case 1:
                btn_activity.setProgress(progress);
                break;
            case 2:
                btn_challenge.setProgress(progress);
                break;
            case 3:
                btn_mine.setProgress(progress);
                break;
        }
    }

    private void restoreState() {
        btn_sport.setProgress(0);
        btn_activity.setProgress(0);
        btn_challenge.setProgress(0);
        btn_mine.setProgress(0);
    }

    public void setAlpha(float alpha) {
        tintManager.setStatusBarAlpha(alpha);
        tintManager.setStatusBarTintResource(R.color.colorPrimaryDark);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            Intent intent=new Intent(this, HomeActviity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onResume() {
        super.onResume();
        ZillaApi.NormalRestAdapter.create(SportService.class)
        .getNewMsgRemind(UserInfoModel.getInstance().getToken(),
                String.valueOf(UserInfoModel.getInstance().getUserId()),
                new RequestCallback<ResponseData<Unread>>() {
                    @Override
                    public void success(ResponseData<Unread> responseData, Response response) {
                        try {
                            if(responseData.getStatus()==200){
                                String unread=responseData.getData().getUnreadCount();
                                if(!TextUtils.isEmpty(unread)&&Integer.parseInt(unread)>0){
                                    if(Integer.parseInt(unread)>9){
                                        iv_unread.setText("9+");
                                    }else {
                                        iv_unread.setText(unread);
                                    }
                                    iv_unread.setVisibility(View.VISIBLE);
                                }else {
                                    iv_unread.setVisibility(View.GONE);
                                }
                            }else {
                                iv_unread.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void updateUnread(){
        iv_unread.setVisibility(View.GONE);

    }

}
