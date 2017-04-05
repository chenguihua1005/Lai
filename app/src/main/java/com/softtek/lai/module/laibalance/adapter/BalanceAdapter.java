package com.softtek.lai.module.laibalance.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;

import com.softtek.lai.R;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.laibalance.model.FragmentModel;
import com.softtek.lai.widgets.CircleImageDrawable;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by shelly.xu on 4/5/2017.
 */

public class BalanceAdapter extends FragmentPagerAdapter {
    private List<FragmentModel> fragmentModels;

    public BalanceAdapter(FragmentManager fm ,List<FragmentModel> fragementModels) {
        super(fm);
        this.fragmentModels = fragementModels;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentModels.get(position).getFragment();
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        FragmentModel model=fragmentModels.get(position);
        return model.getName();
    }

    @Override
    public int getCount() {
        return fragmentModels.size();
    }
}
