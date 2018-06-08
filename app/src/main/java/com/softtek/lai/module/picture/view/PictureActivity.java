package com.softtek.lai.module.picture.view;

import android.net.Uri;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.picture.adapter.PictureFragementAdapter;
import com.softtek.lai.utils.SystemUtils;
import com.softtek.lai.widgets.DebugViewPage;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_picture_layout)
public class PictureActivity extends BaseActivity implements BaseFragment.OnFragmentInteractionListener {


    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.vp)
    DebugViewPage content;

    private List<Fragment> fragments=new ArrayList<>();
    private List<String> images;
    private PictureFragementAdapter adapter;
    private int position=0;

    @Override
    protected void initViews() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        tintManager.setStatusBarTintResource(android.R.color.transparent);
        images=getIntent().getStringArrayListExtra("images");
        position=getIntent().getIntExtra("position",0);
        tv_title.setText(position+1+"/"+images.size());

        toggleHideyBar();
    }

    public void toggleHideyBar() {

        // The UI options currently enabled are represented by a bitfield.
        // getSystemUiVisibility() gives us that bitfield.
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        boolean isImmersiveModeEnabled =
                ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);

        // Navigation bar hiding:  Backwards compatible to ICS.
        if (SystemUtils.getSDKInt() >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        }

        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        // Immersive mode: Backward compatible to KitKat.
        // Note that this flag doesn't do anything by itself, it only augments the behavior
        // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
        // all three flags are being toggled together.
        // Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
        // Sticky immersive mode differs in that it makes the navigation and status bars
        // semi-transparent, and the UI flag does not get cleared when the user interacts with
        // the screen.
        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }

        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }


    @Override
    protected void initDatas() {
        if(images!=null){
            for (int i=0;i<images.size();i++){
                fragments.add(PictureFragment.getInstance(images.get(i)));
            }
        }
        adapter=new PictureFragementAdapter(getSupportFragmentManager(),fragments);
        content.setAdapter(adapter);
        content.setCurrentItem(position);
        content.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tv_title.setText(position+1+"/"+images.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
