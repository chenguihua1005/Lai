package com.softtek.lai.module.laiClassroom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.softtek.lai.R;
import com.softtek.lai.module.laiClassroom.model.TopicModel;

import java.util.List;

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
        if (view!=null)
        {
            view= LayoutInflater.from(context).inflate(R.layout.frag_girdlist_subject_item,viewGroup,false);
            holdel=new SubjectHoldel(view);
            view.setTag(holdel);
        }
        else {
            holdel= (SubjectHoldel) view.getTag();

        }
        return null;
    }
    static class SubjectHoldel
    {

        public SubjectHoldel(View view)
        {

        }
    }

}
