package com.softtek.lai.module.retest.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.retest.model.Banji;

import java.util.List;

/**
 * Created by lareina.qiao on 3/18/2016.
 */
public class ClassAdapter extends ArrayAdapter<Banji> {
    private int resourceId;

    public ClassAdapter(Context context,  int textViewResourceId, List<Banji> objects) {
        super(context, textViewResourceId, objects);
        resourceId=textViewResourceId;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Banji banji1=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,null);
        TextView StartDate=(TextView)view.findViewById(R.id.tv_classname);
        TextView ClassName=(TextView)view.findViewById(R.id.tv_title);
        TextView Total=(TextView)view.findViewById(R.id.tv_personum);
        StartDate.setText(banji1.getStartDate());
        ClassName.setText(banji1.getClassName());
        Total.setText(banji1.getTotal()+"");
        return view;
    }
}
