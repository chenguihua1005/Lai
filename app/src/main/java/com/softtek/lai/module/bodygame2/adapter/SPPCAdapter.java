package com.softtek.lai.module.bodygame2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.bodygame2.model.SPPCMoldel;
import com.softtek.lai.utils.StringUtil;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by jerry.guan on 7/12/2016.
 */
public class SPPCAdapter extends BaseAdapter{

    private List<SPPCMoldel> pc;
    private Context context;

    public SPPCAdapter(Context context,List<SPPCMoldel> pc) {
        this.pc = pc;
        this.context=context;
    }

    @Override
    public int getCount() {
        return pc.size();
    }

    @Override
    public Object getItem(int position) {
        return pc.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.my_student_item,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        SPPCMoldel model=pc.get(position);
        holder.tv_order.setText(model.getNum()+"");
        holder.tv_start_weight.setText("初始体重："+model.getInitWt()+"斤");
        if(model.getGender()==0){//男
            holder.cb_gender.setChecked(false);
        }else{
            holder.cb_gender.setChecked(true);
        }
        holder.tv_name.setText(model.getUserName());
        holder.tv_who.setText(" "+StringUtil.withValue(model.getSuperName()));
        holder.tv_total_weight.setText(model.getLossW()+"斤");
        if(model.getStarCou()==0){//不是明星学员
            holder.cb_mingxing.setChecked(false);
        }else{
            holder.cb_mingxing.setChecked(true);
        }
        if(model.getAcmCou()==0){//未复测
            holder.cb_fuce.setChecked(false);
        }else{
            holder.cb_fuce.setChecked(true);
        }
        holder.tv_xunzhang.setText("x"+model.getHonCou());
        if(StringUtils.isNotEmpty(model.getPCPhoto())){
            Picasso.with(context).load(AddressManager.get("photoHost")+model.getPCPhoto()).fit().placeholder(R.drawable.img_default)
                    .error(R.drawable.img_default).into(holder.civ_header);
        }else{
            Picasso.with(context).load(R.drawable.img_default).into(holder.civ_header);
        }
        return convertView;
    }

    private static class ViewHolder{
        TextView tv_order;
        CircleImageView civ_header;
        TextView tv_start_weight;
        CheckBox cb_gender;
        TextView tv_name;
        TextView tv_who;
        TextView tv_total_weight;
        CheckBox cb_mingxing;
        CheckBox cb_fuce;
        TextView tv_xunzhang;

        public ViewHolder(View view){
            tv_order= (TextView) view.findViewById(R.id.tv_order);
            civ_header= (CircleImageView) view.findViewById(R.id.civ_header);
            tv_start_weight= (TextView) view.findViewById(R.id.tv_start_weight);
            cb_gender= (CheckBox) view.findViewById(R.id.cb_gender);
            tv_name= (TextView) view.findViewById(R.id.tv_name);
            tv_who= (TextView) view.findViewById(R.id.tv_who);
            tv_total_weight= (TextView) view.findViewById(R.id.tv_total_weight);
            cb_mingxing= (CheckBox) view.findViewById(R.id.cb_mingxing);
            cb_fuce= (CheckBox) view.findViewById(R.id.cb_fuce);
            tv_xunzhang= (TextView) view.findViewById(R.id.tv_xunzhang);
        }

    }
}
