/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.bodygame3.head.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.LaiApplication;
import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.File.view.CreatFlleActivity;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.home.view.ModifyPasswordActivity;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.net.LoginService;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.stepcount.db.StepUtil;
import com.softtek.lai.stepcount.model.UserStep;
import com.softtek.lai.stepcount.service.DaemonService;
import com.softtek.lai.stepcount.service.StepService;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.MD5;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;

/**
 * Created by julie.zhu on 3/18/2016.
 */
public class PhotoPagerAdapter extends PagerAdapter {
    private List<View> views;
    private Activity activity;

    public PhotoPagerAdapter(List<View> views, Activity activity) {
        this.views = views;
        this.activity = activity;
    }

//    @Override
//    public void destroyItem(View arg0, int arg1, Object arg2) {
//        ((ViewPager) arg0).removeView(views.get(arg1));
//    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public int getCount() {
        if (views != null) {
            return views.size();
        }
        return 0;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        return super.instantiateItem(container, position);
    }


    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return (arg0 == arg1);
    }
}

