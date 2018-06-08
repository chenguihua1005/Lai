package com.softtek.lai.module.bodygame3.more.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.bodygame3.more.model.FuceClassAlbumModel;
import com.softtek.lai.module.bodygame3.more.model.FucePhotoModel;
import com.softtek.lai.module.community.adapter.PhotosAdapterFuCe;
import com.softtek.lai.picture.LookBigPicActivity;
import com.softtek.lai.picture.bean.EaluationPicBean;
import com.softtek.lai.picture.util.EvaluateUtil;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.widgets.CustomGridView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by jessica.zhang on 3/9/2017.
 */

public class FuceAlbumAdapter extends BaseAdapter {
    private Context context;
    private List<FuceClassAlbumModel> fucePhotos;
    String path = AddressManager.get("photoHost");
//    private int px;
    private Object tag;

    public FuceAlbumAdapter(Context context, List<FuceClassAlbumModel> fucePhotos, Object tag) {
        this.context = context;
        this.fucePhotos = fucePhotos;
//        this.px = DisplayUtil.dip2px(context, 45);
        this.tag = tag;
    }

    @Override
    public int getCount() {
        return fucePhotos.size();
    }

    @Override
    public Object getItem(int i) {
        return fucePhotos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final FuceClassAlbumModel model = fucePhotos.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.fuce_album_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.className_tv.setText(model.getClassName());
        holder.classDate_tv.setText(model.getJoinDate());

//        List<String> photoPaths = new ArrayList<String>();

        List<FucePhotoModel> photoList = new ArrayList<>();
        photoList = model.getPhotoList();
        if (model.getPhotoList() != null) {
            for (FucePhotoModel temp : photoList) {
                temp.setSelect(false);
//                photoPaths.add(temp.getImgUrl());
            }
        }

        holder.photos_gv.setAdapter(new PhotosAdapterFuCe(photoList, context, tag));
        holder.photos_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                List<String> photoPaths = new ArrayList<String>();
                List<FucePhotoModel> photoList = new ArrayList<>();
                photoList = model.getPhotoList();
                if (model.getPhotoList() != null) {
                    for (FucePhotoModel temp : photoList) {
//                        temp.setSelect(false);
                        photoPaths.add(temp.getImgUrl());
                    }
                }

                Intent intent = new Intent(context, LookBigPicActivity.class);
                Bundle bundle = new Bundle();
                List<EaluationPicBean> list = setupCoords((FrameLayout) view, photoPaths, position);
//                List<EaluationPicBean> list = EvaluateUtil.setupCoords4(context,(FrameLayout) view, photoPaths, position);
                bundle.putSerializable(LookBigPicActivity.PICDATALIST, (Serializable) list);
                intent.putExtras(bundle);
                intent.putExtra(LookBigPicActivity.CURRENTITEM, position);
                context.startActivity(intent);
                ((AppCompatActivity) context).overridePendingTransition(0, 0);

            }
        });


        return convertView;
    }

    /**
     * 计算每个item的坐标
     *
     * @param iv_image
     * @param mAttachmentsList
     * @param position
     */
    private List<EaluationPicBean> setupCoords(FrameLayout iv_image, List<String> mAttachmentsList, int position) {
        //x方向的第几个
        int xn=position%4+1;
        //y方向的第几个
        int yn=position/4+1;
        //x方向的总间距
        int h=(xn-1)* DisplayUtil.dip2px(context,4);
        //y方向的总间距
        int v=h;
        //图片宽高
        int height = iv_image.getHeight();
        int width = iv_image.getWidth();
        //获取当前点击图片在屏幕上的坐标
        int[] points=new int[2];
        iv_image.getLocationInWindow(points);
        //获取第一张图片的坐标
        int x0=points[0]-(width+h)*(xn-1) ;
        int y0=points[1]-(height+v)*(yn-1);
        //给所有图片添加坐标信息
        List<EaluationPicBean> list=new ArrayList<>();
        for(int i=0;i<mAttachmentsList.size();i++){
            String imgUrl=mAttachmentsList.get(i);
            EaluationPicBean ealuationPicBean =new EaluationPicBean();
            ealuationPicBean.imageUrl=imgUrl;
            ealuationPicBean.smallImageUrl=imgUrl;
            ealuationPicBean.width=width;
            ealuationPicBean.height=height;
            ealuationPicBean.x=x0+(i%4)*(width+h);
            ealuationPicBean.y=y0+(i/4)*(height+v)-DisplayUtil.getStatusBarHeight(iv_image);
            list.add(ealuationPicBean);
        }
        return list;
    }

    static class ViewHolder {
        private TextView className_tv;
        private TextView classDate_tv;
        private CustomGridView photos_gv;

        public ViewHolder(View view) {
            this.className_tv = (TextView) view.findViewById(R.id.className_tv);
            this.classDate_tv = (TextView) view.findViewById(R.id.classDate_tv);
            this.photos_gv = (CustomGridView) view.findViewById(R.id.photos_gv);
        }
    }

}
