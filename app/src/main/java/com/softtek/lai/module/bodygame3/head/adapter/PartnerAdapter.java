package com.softtek.lai.module.bodygame3.head.adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.bodygame3.head.model.PartnersModel;
import com.softtek.lai.module.counselor.model.ApplyAssistantModel;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import zilla.libcore.file.AddressManager;


/**
 * Created by shelly.xu on 11/21/2016.
 */

public class PartnerAdapter extends BaseAdapter {
    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局
    private List<PartnersModel> partnersModels;
    BaseActivity context;

    public PartnerAdapter(BaseActivity context, List<PartnersModel> partnersModels) {
        this.context = context;
        this.partnersModels = partnersModels;
        LayoutInflater.from(context);

    }

    public void update(List<PartnersModel> partnersModels) {
        this.partnersModels = partnersModels;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return partnersModels.size();
    }

    @Override
    public Object getItem(int postion) {
        return partnersModels.get(postion);
    }

    @Override
    public long getItemId(int postion) {
        return postion;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.partner_list, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        PartnersModel model = partnersModels.get(position);
        viewHolder.paiming.setText(model.getRanking());
        viewHolder.name_tv.setText(model.getStuName());
        viewHolder.weight_first.setText("初始体重" + model.getWeight() + "斤");
        viewHolder.jianzhong_tv.setText(model.getLoss());
        viewHolder.group_tv.setText(model.getGroupName());
//        viewHolder.head_img.setImageResource();
        String basePath = AddressManager.get("photoHost");
        if (StringUtils.isNotEmpty(model.getStuImg())) {
            Picasso.with(context).load(basePath + model.getStuImg()).into(viewHolder.head_img);
        }
        if(model.getStuGender().equals("1")){
            viewHolder.fale.setImageResource(R.drawable.female_iv);
        }else {
            viewHolder.fale.setImageResource(R.drawable.male_iv);
        }

//        Picasso.with(context).load(model.getStuImg()).into(viewHolder.head_img);
        return convertView;
    }

    class ViewHolder {
        TextView paiming;
        ImageView fale;
        TextView name_tv;
        TextView group_tv;
        TextView weight_first;
        TextView jianzhong_tv;
        TextView tv_bi;
        ImageView head_img;

        public ViewHolder(View view) {
            paiming = (TextView) view.findViewById(R.id.paiming);
            fale = (ImageView) view.findViewById(R.id.fale);
            name_tv = (TextView) view.findViewById(R.id.name_tv);
            group_tv = (TextView) view.findViewById(R.id.group_tv);
            weight_first = (TextView) view.findViewById(R.id.weight_first);
            jianzhong_tv = (TextView) view.findViewById(R.id.jianzhong_tv);
            tv_bi = (TextView) view.findViewById(R.id.tv_bi);
            head_img = (ImageView) view.findViewById(R.id.head_img);

        }
    }
}
