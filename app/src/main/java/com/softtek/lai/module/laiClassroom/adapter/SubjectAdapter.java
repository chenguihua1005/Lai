package com.softtek.lai.module.laiClassroom.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.laiClassroom.model.TopicModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by lareina.qiao on 3/14/2017.
 */

public class SubjectAdapter extends BaseAdapter {
    private Context context;
    private List<TopicModel> topicModels;

    public SubjectAdapter(Context context,List<TopicModel> topicModels)
    {
        this.context=context;
        this.topicModels=topicModels;
    }
    private void updataSubject(List<TopicModel> topicModels)
    {
        this.topicModels=topicModels;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return topicModels.size();
    }

    @Override
    public Object getItem(int i) {
        return topicModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        SubjectHoldel holdel;
        if (view==null)
        {
            view= LayoutInflater.from(context).inflate(R.layout.frag_girdlist_subject_item,viewGroup,false);
            holdel=new SubjectHoldel(view);
            view.setTag(holdel);
        }
        else {
            holdel= (SubjectHoldel) view.getTag();
        }
        TopicModel topicModel=topicModels.get(i);
        if (topicModel.getTopicId().size()==2)
        {
            holdel.ll_subitem1.setVisibility(View.VISIBLE);
            holdel.ll_subitem2.setVisibility(View.VISIBLE);
            holdel.tv_subject_name1.setText(topicModel.getTopicName().get(0));
            holdel.tv_subject_name2.setText(topicModel.getTopicName().get(1));
            if (!TextUtils.isEmpty(topicModel.getTopicImg().get(0)))
            {
                Picasso.with(context).load(AddressManager.get("photoHost")+topicModel.getTopicImg().get(0)).centerCrop().placeholder(R.drawable.default_icon_rect)
                .fit().error(R.drawable.default_icon_rect).into(holdel.im_photo);
            }
            else {
                Picasso.with(context).load(R.drawable.default_icon_rect).centerCrop().centerCrop()
                        .fit().into(holdel.im_photo);
            }
            if (!TextUtils.isEmpty(topicModel.getTopicImg().get(1)))
            {
                Picasso.with(context).load(AddressManager.get("photoHost")+topicModel.getTopicImg().get(1)).centerCrop().placeholder(R.drawable.default_icon_rect)
                        .fit().error(R.drawable.default_icon_rect).into(holdel.im_photo);
            }
            else {
                Picasso.with(context).load(R.drawable.default_icon_rect).centerCrop().centerCrop()
                        .fit().into(holdel.im_photo);
            }
        }else {
            holdel.ll_subitem1.setVisibility(View.VISIBLE);
            holdel.tv_subject_name1.setText(topicModel.getTopicName().get(0));
            if (!TextUtils.isEmpty(topicModel.getTopicImg().get(0)))
            {
                Picasso.with(context).load(AddressManager.get("photoHost")+topicModel.getTopicImg().get(0)).centerCrop().placeholder(R.drawable.default_icon_rect)
                        .fit().error(R.drawable.default_icon_rect).into(holdel.im_photo);
            }
            else {
                Picasso.with(context).load(R.drawable.default_icon_rect).centerCrop().centerCrop()
                        .fit().into(holdel.im_photo);
            }
        }


        return view;
    }
    static class SubjectHoldel
    {
        LinearLayout ll_subitem1,ll_subitem2;
        ImageView im_photo,im_photo2;
        TextView tv_subject_name1,tv_subject_name2;

        public SubjectHoldel(View view)
        {
            ll_subitem1= (LinearLayout) view.findViewById(R.id.ll_subitem1);
            ll_subitem2= (LinearLayout) view.findViewById(R.id.ll_subitem2);
            im_photo= (ImageView) view.findViewById(R.id.im_photo);
            im_photo2= (ImageView) view.findViewById(R.id.im_photo1);
            tv_subject_name1= (TextView) view.findViewById(R.id.tv_subject_name1);
            tv_subject_name2= (TextView) view.findViewById(R.id.tv_subject_name2);

        }
    }

}
