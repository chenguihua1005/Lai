package com.softtek.lai.module.picture.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.utils.DisplayUtil;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

import static com.squareup.picasso.MemoryPolicy.NO_CACHE;
import static com.squareup.picasso.MemoryPolicy.NO_STORE;

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
        iv_image.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                getActivity().finish();
            }

            @Override
            public void onOutsidePhotoTap() {

            }
        });

    }

    @Override
    protected void initDatas() {
        String uri=getArguments().getString("image_path");
        if(iv_image!=null){
            Bitmap cache=iv_image.getDrawingCache();
            if(cache!=null&&!cache.isRecycled()){
                cache.recycle();
            }
        }
        //int px=Math.min(DisplayUtil.getMobileHeight(getContext()),DisplayUtil.getMobileWidth(getContext()));
        Picasso.with(getContext()).load(AddressManager.get("photoHost")+uri)
                .resize(DisplayUtil.getMobileWidth(getContext()),
                        DisplayUtil.getMobileHeight(getContext())+DisplayUtil.getStatusHeight(getActivity()))
                .centerInside().memoryPolicy(NO_CACHE, NO_STORE)
                .placeholder(R.drawable.default_icon_square)
                .error(R.drawable.default_icon_square)
                .into(iv_image);

    }

    @Override
    public void onDestroyView() {
        Bitmap cache=iv_image.getDrawingCache();
        if(cache!=null&&!cache.isRecycled()){
            cache.recycle();
        }
        super.onDestroyView();
    }
}
