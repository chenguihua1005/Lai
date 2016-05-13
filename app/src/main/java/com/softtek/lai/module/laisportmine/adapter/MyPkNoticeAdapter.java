package com.softtek.lai.module.laisportmine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.laisportmine.model.ActionModel;
import com.softtek.lai.module.laisportmine.model.PkNoticeModel;

import java.util.List;

/**
 * Created by lareina.qiao on 5/12/2016.
 */
public class MyPkNoticeAdapter extends BaseAdapter {
    private Context context;
    private List<PkNoticeModel> pkNoticeModelList;
    private LayoutInflater inflater;
    private UserInfoModel userInfoModel;

    public MyPkNoticeAdapter(Context context, List<PkNoticeModel> pkNoticeModelList) {
        this.context=context;
        inflater=LayoutInflater.from(context);
        this.pkNoticeModelList=pkNoticeModelList;
    }


    public void updateData(List<PkNoticeModel> pkNoticeModelList){
        this.pkNoticeModelList=pkNoticeModelList;
        notifyDataSetChanged();;
    }


    @Override
    public int getCount() {
        return pkNoticeModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return pkNoticeModelList.get(position);
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
            convertView=LayoutInflater.from(context).inflate(R.layout.listitem_my_pknotice,parent,false);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        PkNoticeModel pkNoticeModel=pkNoticeModelList.get(position);
        if (userInfoModel.getUser().getNickname().equals(pkNoticeModel.getChallenged())) {
            viewHolder.tv_pk_person1.setText("你");
            viewHolder.tv_pk_person2.setText(pkNoticeModel.getBChallenged());
        }
        else {
            viewHolder.tv_pk_person1.setText(pkNoticeModel.getChallenged());
            viewHolder.tv_pk_person2.setText("你");
        }

        return convertView;
    }


    class ViewHolder{
        TextView tv_pk_person1;
        TextView tv_pk_person2;
        public ViewHolder(View view){
            tv_pk_person1=(TextView)view.findViewById(R.id.tv_pk_person1);
            tv_pk_person2=(TextView)view.findViewById(R.id.tv_pk_person2);

        }
    }
}
