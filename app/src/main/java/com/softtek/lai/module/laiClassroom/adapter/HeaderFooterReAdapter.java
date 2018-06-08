package com.softtek.lai.module.laiClassroom.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.laiClassroom.SubjectdetailActivity;
import com.softtek.lai.module.laiClassroom.model.ArticleTopicModel;
import com.softtek.lai.widgets.RectangleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by 87356 on 2017/3/17.
 */

public class HeaderFooterReAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEAD=0;
    private static final int TYPE_ITEM=1;

    private Context contex;
    private List<ArticleTopicModel> models;
    private View header;
    public HeaderFooterReAdapter(View header, Context context, List<ArticleTopicModel> data) {
        this.contex=context;
        this.header=header;
        this.models=data;
    }

    @Override
    public int getItemViewType(int position) {
        return position==0? TYPE_HEAD:TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==TYPE_HEAD){
            return new HeadViewHolder(header);
        }
        View view=LayoutInflater.from(contex).inflate(R.layout.fra_superrecycle_subject_item,parent,false);
        return new ArticalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(position==0){
            return;
        }
        ArticalViewHolder ah= (ArticalViewHolder) holder;
        ArticleTopicModel item=models.get(position-1);
        ah.tv_subject_name1.setText(item.getTopicName());
        ah.tv_clickhot1.setText(String.valueOf(item.getClicks()));
        Picasso.with(contex).load(AddressManager.get("photoHost")+item.getTopicImg()).fit()
                .centerCrop()
                .placeholder(R.drawable.default_laiclass_15).error(R.drawable.default_laiclass_15)
                .into(ah.im_photo);
        ah.im_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contex, SubjectdetailActivity.class);
                intent.putExtra("topictitle", models.get(position-1).getTopicName());
                intent.putExtra("topicId", models.get(position-1).getTopicId());
                contex.startActivity(intent);
            }
        });
        ah.tv_subject_name1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(contex, SubjectdetailActivity.class);
                intent.putExtra("topictitle", models.get(position-1).getTopicName());
                intent.putExtra("topicId", models.get(position-1).getTopicId());
                contex.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size()+1;
    }

    public class ArticalViewHolder extends RecyclerView.ViewHolder{

        RectangleImageView im_photo;
        TextView tv_subject_name1;
        TextView tv_clickhot1;

        public ArticalViewHolder(View itemView) {
            super(itemView);
            im_photo= (RectangleImageView) itemView.findViewById(R.id.im_photo);
            tv_subject_name1= (TextView) itemView.findViewById(R.id.tv_subject_name1);
            tv_clickhot1= (TextView) itemView.findViewById(R.id.tv_clickhot1);

        }
    }

    public class HeadViewHolder extends RecyclerView.ViewHolder{

        public HeadViewHolder(View itemView) {
            super(itemView);
        }
    }
}
