package com.softtek.lai.module.pastreview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.pastreview.model.ClassListModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lareina.qiao on 6/28/2016.
 */
public class ClassListAdapter extends BaseAdapter {
    private Context context;
    private List<ClassListModel> classListModelList=new ArrayList<ClassListModel>();

    public ClassListAdapter(Context context, List<ClassListModel> classListModelList) {
        this.context = context;
        this.classListModelList = classListModelList;
    }

    @Override
    public int getCount() {
        return classListModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return classListModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null)
        {
            convertView=LayoutInflater.from(context).inflate(R.layout.listview_class_listitem,parent,false);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        ClassListModel classListModel=classListModelList.get(position);
        String[] datestar=classListModel.getStartDate().split("-");
        String[] datend=classListModel.getStartDate().split("-");
        viewHolder.tv_passclassname.setText(classListModel.getClassName());
        viewHolder.tv_passclassdate.setText(datestar[0]+"年"+datestar[1]+"月"+"-"+datend[0]+"年"+datend[1]+"月");
        return convertView;
    }
    class ViewHolder{
        TextView tv_passclassname;
        TextView tv_passclassdate;
        public ViewHolder(View view){
            tv_passclassname=(TextView)view.findViewById(R.id.tv_passclassname);
            tv_passclassdate=(TextView)view.findViewById(R.id.tv_passclassdate);
        }
    }
}
