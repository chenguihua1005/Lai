package com.softtek.lai.module.laisportmine.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.laisportmine.model.ActionModel;
import com.softtek.lai.module.laisportmine.model.PkNoticeModel;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import zilla.libcore.file.AddressManager;

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
        String path = AddressManager.get("photoHost");
        if (pkNoticeModel.getMsgType().equals("1"))
        {
            viewHolder.tv_pk_title.setText(pkNoticeModel.getUserName()+"向你发了一次挑战");
            if (!TextUtils.isEmpty(pkNoticeModel.getPhoto())) {
                Picasso.with(context).load(path + pkNoticeModel.getPhoto()).fit().placeholder(R.drawable.img_default).error(R.drawable.img_default).into(viewHolder.im_pk_head);
            }

        }
        else if (pkNoticeModel.getMsgType().equals("2"))
        {
            viewHolder.tv_pk_title.setText(pkNoticeModel.getUserName()+"接受了你的挑战");
            if (!TextUtils.isEmpty(pkNoticeModel.getPhoto())) {
                Picasso.with(context).load(path + pkNoticeModel.getPhoto()).fit().placeholder(R.drawable.img_default).error(R.drawable.img_default).into(viewHolder.im_pk_head);
            }
        }
        else if (pkNoticeModel.getMsgType().equals("3"))
        {
            viewHolder.tv_pk_title.setText(pkNoticeModel.getUserName()+"拒绝了你的挑战");
            if (!TextUtils.isEmpty(pkNoticeModel.getPhoto())) {
                Picasso.with(context).load(path + pkNoticeModel.getPhoto()).fit().placeholder(R.drawable.img_default).error(R.drawable.img_default).into(viewHolder.im_pk_head);
            }
        }
        else if (pkNoticeModel.getMsgType().equals("4"))
        {
            viewHolder.tv_pk_title.setText(pkNoticeModel.getUserName()+"取消了挑战");
            if (!TextUtils.isEmpty(pkNoticeModel.getPhoto())) {
                Picasso.with(context).load(path + pkNoticeModel.getPhoto()).fit().placeholder(R.drawable.img_default).error(R.drawable.img_default).into(viewHolder.im_pk_head);
            }
        }
        else if (pkNoticeModel.getMsgType().equals("5"))
        {
            viewHolder.tv_pk_title.setText(pkNoticeModel.getChip());
            if (!TextUtils.isEmpty(pkNoticeModel.getPhoto())) {
                Picasso.with(context).load(path + pkNoticeModel.getPhoto()).fit().placeholder(R.drawable.img_default).error(R.drawable.img_default).into(viewHolder.im_pk_head);
            }
        }
        else {
            viewHolder.tv_pk_title.setText(pkNoticeModel.getChip());
            if (!TextUtils.isEmpty(pkNoticeModel.getPhoto())) {
                Picasso.with(context).load(path + pkNoticeModel.getPhoto()).fit().placeholder(R.drawable.img_default).error(R.drawable.img_default).into(viewHolder.im_pk_head);
            }
        }

        viewHolder.tv_pk_person1.setText(pkNoticeModel.getComments());
        String date[]=pkNoticeModel.getCreateTime().split("-");
        String date1[]=date[2].split(" ");
        String date2=date[1].substring(0,1);
        if (date[1].substring(0,1).equals("0"))
        {
            viewHolder.tv_pk_date.setText(date[1].substring(1,2)+"-"+date1[0]);
        }
        else {
            viewHolder.tv_pk_date.setText(date[1] + "-" + date1[0]);
        }
        return convertView;
    }


    class ViewHolder{
        TextView tv_pk_person1;
        TextView tv_pk_title;
        TextView tv_pk_date;
        CircleImageView im_pk_head;
        public ViewHolder(View view){
            tv_pk_person1=(TextView)view.findViewById(R.id.tv_pk_person1);
            tv_pk_title= (TextView) view.findViewById(R.id.tv_pk_title);
            im_pk_head=(CircleImageView)view.findViewById(R.id.im_pk_head);
            tv_pk_date= (TextView) view.findViewById(R.id.tv_pk_date);

        }
    }
}
