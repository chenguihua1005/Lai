package com.softtek.lai.module.community.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.bodygame3.more.model.FucePhotoModel;
import com.softtek.lai.module.bodygame3.more.view.FuceAlbumActivity;
import com.softtek.lai.utils.DisplayUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by jessica.zhang on 3/9/2017.
 */

public class PhotosAdapterFuCe extends BaseAdapter {
    private List<FucePhotoModel> images;
    private Context context;
    private Object tag;
    private int px;

    public PhotosAdapterFuCe(List<FucePhotoModel> images, Context context, Object tag) {
        this.images = images;
        this.context = context;
        px = DisplayUtil.dip2px(context.getApplicationContext(), 90);
        this.tag = tag;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.photo_item_fuce, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final FucePhotoModel model = images.get(position);

        //加载图片
        String path = AddressManager.get("photoHost");
        Picasso.with(context).load(path + model.getImgUrl()).resize(px, px).centerCrop()
                .placeholder(R.drawable.default_icon_square)
                .error(R.drawable.default_icon_square)
                .config(Bitmap.Config.RGB_565)
                .tag(tag)
                .into(holder.image);
        holder.weekth_tv.setText(model.getWeekth());
        if (FuceAlbumActivity.show_photo_circle) {
            holder.img_select_button.setVisibility(View.VISIBLE);
            final boolean isSelect = model.isSelect();
            if (isSelect) {
                holder.img_select_button.setImageResource(R.drawable.circled_fuce_photo);
            } else {
                holder.img_select_button.setImageResource(R.drawable.circle_fuce_photo);
            }
            holder.black_bg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (model.isSelect()) {
                        holder.img_select_button.setImageResource(R.drawable.circle_fuce_photo);
                        model.setSelect(false);
                    } else {
                        holder.img_select_button.setImageResource(R.drawable.circled_fuce_photo);
                        model.setSelect(true);
                    }

                    LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(FuceAlbumActivity.UPDATE_PHOTO_NUMBER));

                }
            });
        }else {
            holder.img_select_button.setVisibility(View.GONE);
        }


        return convertView;
    }

    static class ViewHolder {
        public ImageView image;
        public ImageView img_select_button;
        public TextView weekth_tv;
        public ImageView black_bg;

        public ViewHolder(View view) {
            image = (ImageView) view.findViewById(R.id.iv);
            weekth_tv = (TextView) view.findViewById(R.id.weekth_tv);
            img_select_button = (ImageView) view.findViewById(R.id.img_select_button);
            black_bg = (ImageView) view.findViewById(R.id.black_bg);
        }
    }
}
