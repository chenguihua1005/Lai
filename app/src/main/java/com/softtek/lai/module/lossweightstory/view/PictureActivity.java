package com.softtek.lai.module.lossweightstory.view;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.lossweightstory.adapter.PictureFragementAdapter;
import com.softtek.lai.widgets.DebugViewPage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import uk.co.senab.photoview.PhotoView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_picture_layout)
public class PictureActivity extends BaseActivity implements View.OnClickListener,BaseFragment.OnFragmentInteractionListener {

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
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
        images=getIntent().getStringArrayListExtra("images");
        position=getIntent().getIntExtra("position",0);
        tv_title.setText(position+1+"/"+images.size());
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
