package com.softtek.lai.module.laiClassroom.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.softtek.lai.R;
import com.softtek.lai.module.laiClassroom.SubjectNewFragment;
import com.softtek.lai.module.laiClassroom.model.ArticleTopicModel;
import com.softtek.lai.module.laiClassroom.model.RecommendModel;
import com.squareup.picasso.Picasso;
import com.superrecycleview.superlibrary.adapter.BaseViewHolder;
import com.superrecycleview.superlibrary.adapter.SuperBaseAdapter;

import java.util.List;

import zilla.libcore.file.AddressManager;
import zilla.libcore.util.Util;

/**
 * Created by 87356 on 2017/3/17.
 */

public class HeaderFooterReAdapter extends SuperBaseAdapter<ArticleTopicModel> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private Context contex;
    public HeaderFooterReAdapter(Context context, List<ArticleTopicModel> data) {
        super(context, data);
        this.contex=context;
    }


    @Override
    protected void convert(BaseViewHolder holder, ArticleTopicModel item, int position) {
        holder.setText(R.id.tv_subject_name1,item.getTopicName());
        ImageView imphoto=holder.getView(R.id.im_photo);
        Picasso.with(contex).load(AddressManager.get("photoHost")+item.getTopicImg()).centerCrop().fit()
                .placeholder(R.drawable.default_icon_square).error(R.drawable.default_icon_square)
                .into(imphoto);
        holder.setText(R.id.tv_clickhot1,String.valueOf(item.getClicks()));
    }

    @Override
    protected int getItemViewLayoutId(int position, ArticleTopicModel item) {
        return R.layout.fra_superrecycle_subject_item;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_HEADER
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }


}
