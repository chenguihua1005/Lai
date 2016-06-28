package com.softtek.lai.module.pastreview.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.lossweightstory.view.PictureActivity;
import com.softtek.lai.module.pastreview.model.StoryModel;
import com.softtek.lai.module.studetail.model.LossWeightLogModel;
import com.softtek.lai.utils.DateUtil;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by John on 2016/4/3.
 */
public class StoryAdapter extends BaseAdapter{


    private LayoutInflater inflater;
    private List<StoryModel> logs;
    private Context context;

    public StoryAdapter(Context context, List<StoryModel> logs){
        this.context=context;
        inflater=LayoutInflater.from(context);
        this.logs=logs;
    }

    @Override
    public int getCount() {
        return logs.size();
    }

    @Override
    public Object getItem(int position) {
        return logs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final LogHolder holder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.story_item,parent,false);
            holder=new LogHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (LogHolder) convertView.getTag();
        }
        final StoryModel log=logs.get(position);
        holder.cb_zan.setText(log.getPriase());
        if(StringUtils.isNotEmpty(log.getImgCollectionFirst())){
            holder.iv_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in=new Intent(context, PictureActivity.class);
                    ArrayList<String> imgs=new ArrayList<String>();
                    imgs.add(log.getImgCollectionFirst());
                    in.putStringArrayListExtra("images", imgs);
                    in.putExtra("position",0);
                    context.startActivity(in);
                }
            });
            Picasso.with(context).load(AddressManager.get("photoHost")+log.getImgCollectionFirst()).fit().placeholder(R.drawable.default_icon_square).error(R.drawable.default_icon_square).into(holder.iv_image);
        }
        holder.tv_content.setText(log.getLogContent());
        String date=log.getCreateDate();
        holder.tv_month.setText(DateUtil.getInstance().getMonth(date)+"月");
        holder.tv_day.setText(DateUtil.getInstance().getDay(date)+"");
        return convertView;
    }

    private static class LogHolder{

        public TextView tv_day,tv_month,tv_content;
        public ImageView iv_image;
        public CheckBox cb_zan;

        public LogHolder(View view){
            tv_day= (TextView) view.findViewById(R.id.tv_day);
            tv_month= (TextView) view.findViewById(R.id.tv_month);
            tv_content= (TextView) view.findViewById(R.id.tv_content);
            iv_image= (ImageView) view.findViewById(R.id.iv_image);
            cb_zan= (CheckBox) view.findViewById(R.id.cb_zan);
        }
    }
}
