package com.softtek.lai.module.home.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;

import com.ggx.jerryguan.widget_lib.SimpleButton;
import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.home.adapter.MainPageAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import im.fir.sdk.FIR;
import im.fir.sdk.VersionCheckCallback;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_home_actviity)
public class HomeActviity extends BaseActivity implements View.OnClickListener, BaseFragment.OnFragmentInteractionListener {

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

    private int currentId = 0;
    private boolean isClick = false;

    private List<Fragment> fragments=new ArrayList<>();

    @Override
    protected void initViews() {
        btn_home.setOnClickListener(this);
        btn_healthy.setOnClickListener(this);
        btn_healthy_record.setOnClickListener(this);
        btn_mine.setOnClickListener(this);
        //checkUpdate();
        content.setOffscreenPageLimit(3);

    }

    @Override
    protected void initDatas() {
        fragments.add(new HomeFragment());
        fragments.add(new HealthyFragment());
        fragments.add(new HealthyRecordFragment());
        fragments.add(new MineFragment());
        content.setAdapter(new MainPageAdapter(getSupportFragmentManager(),fragments));
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
        if (!isTaskRoot()) {
            finish();
            return;
        }
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
                break;
            case R.id.btn_healthy_record:
                btn_healthy_record.setProgress(1);
                currentId = 2;
                break;
            case R.id.btn_mine:
                btn_mine.setProgress(1);
                currentId = 3;
                break;
        }
        content.setCurrentItem(currentId, false);
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
    public void onBackPressed() {
        moveTaskToBack(true);
        super.onBackPressed();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    //**********************************************************

    /**
     * 获取网上软件版本号
     * 检查本地版本号
     **/

    int version1;
    int build;
    String information;
    String installUrl;

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public void initVersionCode() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            version1 = info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkUpdate() {
        initVersionCode();
        FIR.checkForUpdateInFIR("b405d60358cdcb42b9c9d06f6e1d7918", new VersionCheckCallback() {
            @Override
            public void onSuccess(String versionJson) {

                Log.i( "check from fir.im success! " + "\n" + versionJson);

                try {
                    JSONObject jsonObject = new JSONObject(versionJson);
                    String name = jsonObject.getString("name");
                    String version = jsonObject.getString("version");
                    String changelog = jsonObject.getString("changelog");
                    String updated_at = jsonObject.getString("updated_at");
                    String versionShort = jsonObject.getString("versionShort");
                    build = jsonObject.getInt("build");
                    installUrl = jsonObject.getString("installUrl");
                    information = "更新内容:\n" + changelog + "\n" + "版本号：" + versionShort;
                } catch (Exception E) {
                    E.printStackTrace();
                }

            }

            @Override
            public void onFail(Exception exception) {
                exception.printStackTrace();
                android.util.Log.i("fir", "check fir.im fail! " + "\n" + exception.getMessage());
            }

            @Override
            public void onStart() {
                // Toast.makeText(getApplicationContext(), "正在获取", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFinish() {

                //Toast.makeText(getApplicationContext(), "检测完成", Toast.LENGTH_SHORT).show();
                Log.i(build + "---" + version1);
                if (build > version1) {
                    initDialog();
                }
            }
        });
    }

    /**
     * 初始化dialog
     */
    public void initDialog() {
        /**
         * 状态选择
         */

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("新版提示");
        builder.setMessage(information);
        builder.setPositiveButton("前往下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(installUrl));
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                    return true;
                }
                return false;
            }
        });
        builder.show();
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public String getVersion() {
        String verson_name = "";
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            verson_name = info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return verson_name;
    }

}
