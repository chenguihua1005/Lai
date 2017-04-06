package com.softtek.lai.module.laicheng;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.softtek.lai.R;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.laicheng.adapter.BalanceAdapter;
import com.softtek.lai.module.laicheng.model.BleMainData;
import com.softtek.lai.module.laicheng.model.FragmentModel;
import com.softtek.lai.module.laicheng.model.UserInfoEntity;
import com.softtek.lai.mpermission.PermissionFail;
import com.softtek.lai.mpermission.PermissionOK;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_laibalance)
public class LaibalanceActivity extends MainBaseActivity {

    @InjectView(R.id.tab_balance)
    TabLayout tab_balance;
    @InjectView(R.id.content)
    ViewPager content;

    List<FragmentModel> fragmentModels = new ArrayList<>();

    @OnClick(R.id.fl_left)
    public void doBack() {
        finish();
    }

    @SuppressLint("LongLogTag")
    @PermissionOK(id = 1)
    private void initPermissionSuccess() {
        setBleStateListener(bleStateListener);
        mShakeListener.start();
        Log.d("enter bleStateListener --------","bleStateListener");
    }

    @PermissionFail(id = 1)
    private void initPermissionFail() {
        mShakeListener.stop();
        new AlertDialog.Builder(this)
                .setMessage("拒绝授权将无法正常运行软件！")
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Uri packageURI = Uri.parse("package:" + "com.softtek.lai");
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                }).create().show();
    }

    @Override
    public void initUi() {
        permission.apply(1,Manifest.permission.ACCESS_COARSE_LOCATION);
        fragmentModels.add(new FragmentModel("给自己测", new SelftestFragment()));
        fragmentModels.add(new FragmentModel("给访客测", new VisitortestFragment()));
        content.setOffscreenPageLimit(1);
        content.setAdapter(new BalanceAdapter(getSupportFragmentManager(), fragmentModels));
        tab_balance.setupWithViewPager(content);
        final TabLayout.Tab self = tab_balance.getTabAt(0);
        if (self != null) {
            self.setCustomView(R.layout.self_tab);
            TextView tv_tab = (TextView) self.getCustomView().findViewById(R.id.tab_title);
            tv_tab.setText("给自己测");
            @SuppressLint("WrongViewCast")
            CircleImageView civ = (CircleImageView) self.getCustomView().findViewById(R.id.iv_head);
            Picasso.with(this).load(AddressManager.get("photoHost") + UserInfoModel.getInstance().getUser().getPhoto())
                    .fit().placeholder(R.drawable.img_default).error(R.drawable.img_default).into(civ);
        }
    }

    @Override
    public void initUiByBleSuccess(BleMainData data) {
        Toast.makeText(getApplicationContext(), "上传体脂率成功回调", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void initUiByBleFailed() {

    }

    @Override
    public boolean isGuested() {
        return false;
    }

    @Override
    public UserInfoEntity getGuestInfo() {
        return null;
    }
}
