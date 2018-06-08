package com.softtek.lai.module.laisportmine.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
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
    private boolean isDel=false;
    private CheckBox cb_all;
    public int account=0;
    public boolean isselec=false;


    public MyPkNoticeAdapter(Context context, List<PkNoticeModel> pkNoticeModelList,boolean isDel,CheckBox cb_all) {
        this.context = context;
        this.isDel = isDel;
        this.cb_all = cb_all;
        this.pkNoticeModelList = pkNoticeModelList;

    }
    public void updateData(List<PkNoticeModel> pkNoticeModelList) {
        this.pkNoticeModelList = pkNoticeModelList;
        notifyDataSetChanged();
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

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listitem_my_pknotice, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final PkNoticeModel pkNoticeModel=pkNoticeModelList.get(position);
        String path = AddressManager.get("photoHost");
        if (pkNoticeModel.getMsgtype()==1) {
            viewHolder.tv_pk_title.setText(pkNoticeModel.getUserName() + "向你发了一次挑战");
        } else if (pkNoticeModel.getMsgtype()==2) {
            viewHolder.tv_pk_title.setText(pkNoticeModel.getUserName() + "接受了你的挑战");
        } else if (pkNoticeModel.getMsgtype()==3) {
            viewHolder.tv_pk_title.setText(pkNoticeModel.getUserName() + "拒绝了你的挑战");
        } else if (pkNoticeModel.getMsgtype()==4) {
            viewHolder.tv_pk_title.setText(pkNoticeModel.getUserName() + "取消了挑战");
        } else if (pkNoticeModel.getMsgtype()==5) {
            viewHolder.tv_pk_title.setText(pkNoticeModel.getChip());
        } else if (pkNoticeModel.getMsgtype()==8) {
            viewHolder.tv_pk_title.setText(pkNoticeModel.getUserName() + "重启了和你的PK");
        } else {
            viewHolder.tv_pk_title.setText(pkNoticeModel.getChip());
        }
        if (!TextUtils.isEmpty(pkNoticeModel.getPhoto())) {
            Picasso.with(context).load(path + pkNoticeModel.getPhoto()).fit().placeholder(R.drawable.img_default).error(R.drawable.img_default).into(viewHolder.im_pk_head);
        } else {
            Picasso.with(context).load(R.drawable.img_default).into(viewHolder.im_pk_head);
        }
        if (0==pkNoticeModel.getIsRead()) {
            viewHolder.img_red.setVisibility(View.VISIBLE);
        } else {
            viewHolder.img_red.setVisibility(View.GONE);
        }
        viewHolder.tv_pk_person1.setText(pkNoticeModel.getMsgContent());
        String date[] = pkNoticeModel.getSendTime().split("-");
        String date1[] = date[2].split(" ");
        if (date[1].substring(0, 1).equals("0")) {
            viewHolder.tv_pk_date.setText(date[1].substring(1, 2) + "-" + date1[0]);
        } else {
            viewHolder.tv_pk_date.setText(date[1] + "-" + date1[0]);
        }
        if (pkNoticeModel.getIsselect()!=null) {
            if (pkNoticeModel.getIsselect()) {
                viewHolder.iv_checke.setImageResource(R.drawable.history_data_circled);
            }
            else {
                viewHolder.iv_checke.setImageResource(R.drawable.history_data_circle);
            }
        }
        if (isDel)
        {
            viewHolder.iv_checke.setVisibility(View.VISIBLE);
        }
        else {
            viewHolder.iv_checke.setVisibility(View.GONE);
        }
        final ViewHolder finalViewHolder = viewHolder;
//
        if (isDel) {
            viewHolder.ll_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                     if (!pkNoticeModel.getIsselect()){
                        finalViewHolder.iv_checke.setImageResource(R.drawable.history_data_circled);
                        pkNoticeModel.setIsselect(true);
                         account++;
                    }
                    else if (pkNoticeModel.getIsselect())
                    {
                        finalViewHolder.iv_checke.setImageResource(R.drawable.history_data_circle);
                        pkNoticeModel.setIsselect(false);
                        account--;
                        isselec=false;
                    }
                    if (account==pkNoticeModelList.size())
                    {
                        cb_all.setChecked(true);
                        isselec=true;
                    }
                    else {
                        cb_all.setChecked(false);
                        isselec=false;
                    }
                }
            });
        }
        return convertView;
    }


    class ViewHolder {
        TextView tv_pk_person1;
        TextView tv_pk_title;
        TextView tv_pk_date;
        CircleImageView im_pk_head;
        ImageView iv_checke;
        LinearLayout ll_click;
        ImageView img_red;

        public ViewHolder(View view) {
            tv_pk_person1 = (TextView) view.findViewById(R.id.tv_pk_person1);
            tv_pk_title = (TextView) view.findViewById(R.id.tv_pk_title);
            im_pk_head = (CircleImageView) view.findViewById(R.id.im_pk_head);
            img_red = (ImageView) view.findViewById(R.id.img_red);
            tv_pk_date = (TextView) view.findViewById(R.id.tv_pk_date);
            iv_checke= (ImageView) view.findViewById(R.id.iv_checke);
            ll_click= (LinearLayout) view.findViewById(R.id.ll_click);

        }
    }
}
