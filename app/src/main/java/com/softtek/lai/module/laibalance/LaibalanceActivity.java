package com.softtek.lai.module.laibalance;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.laiClassroom.adapter.TabAdapter;
import com.softtek.lai.module.laibalance.adapter.BalanceAdapter;
import com.softtek.lai.module.laibalance.model.FragmentModel;
import com.softtek.lai.widgets.CircleImageDrawable;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_laibalance)
public class LaibalanceActivity extends BaseActivity {

    @InjectView(R.id.tab_balance)
    TabLayout tab_balance;
    @InjectView(R.id.content)
    ViewPager content;

    List<FragmentModel> fragmentModels = new ArrayList<>();

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        fragmentModels.add(new FragmentModel("给自己测", new SelftestFragment()));
        fragmentModels.add(new FragmentModel("给访客测", new VisitortestFragment()));
        content.setOffscreenPageLimit(1);
        content.setAdapter(new BalanceAdapter(getSupportFragmentManager(), fragmentModels));
        tab_balance.setupWithViewPager(content);
        final TabLayout.Tab self=tab_balance.getTabAt(0);
        if(self!=null){
            self.setCustomView(R.layout.self_tab);
            TextView tv_tab= (TextView) self.getCustomView().findViewById(R.id.tab_title);
            tv_tab.setText("给自己测");
            CircleImageView civ= (CircleImageView) self.getCustomView().findViewById(R.id.iv_head);
            Picasso.with(this).load(AddressManager.get("photoHost")+UserInfoModel.getInstance().getUser().getPhoto())
                    .fit().placeholder(R.drawable.img_default).error(R.drawable.img_default).into(civ);
        }

    }

    @OnClick(R.id.fl_left)
    public void doBack() {
        finish();
    }
}
