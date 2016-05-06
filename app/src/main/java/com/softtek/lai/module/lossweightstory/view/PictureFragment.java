package com.softtek.lai.module.lossweightstory.view;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.utils.DisplayUtil;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;
import uk.co.senab.photoview.PhotoView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jerry.guan on 5/5/2016.
 */
@InjectLayout(R.layout.activity_picture)
public class PictureFragment extends BaseFragment{

    @InjectView(R.id.iv_image)
    PhotoView iv_image;

    private static PictureFragment fragment;

    public static PictureFragment getInstance(String path_image){
        fragment=new PictureFragment();
        Bundle bundle=new Bundle();
        bundle.putString("image_path",path_image);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        String uri=getArguments().getString("image_path");
        Picasso.with(getContext()).load(AddressManager.get("photoHost")+uri).placeholder(R.drawable.default_pic).error(R.drawable.default_pic).into(iv_image);
    }
}