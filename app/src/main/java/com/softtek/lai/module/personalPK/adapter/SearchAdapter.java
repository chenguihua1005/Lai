package com.softtek.lai.module.personalPK.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.personalPK.model.PKObjModel;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by jerry.guan on 5/10/2016.
 */
public class SearchAdapter extends BaseAdapter{

    private Context context;
    private List<PKObjModel> models;

    public SearchAdapter(Context context, List<PKObjModel> models) {
        this.context = context;
        this.models = models;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int position) {
        return models.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_pk_search,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        PKObjModel model=models.get(position);
        holder.name.setText(model.getUserName());
        holder.paotuan_name.setText(model.getRGName());
        if(StringUtils.isNotEmpty(model.getPhoto())){
            Picasso.with(context).load(AddressManager.get("photoHost")+model.getPhoto()).fit().error(R.drawable.img_default).into(holder.header_image);
        }else {
            Picasso.with(context).load(R.drawable.img_default).into(holder.header_image);
        }
        return convertView;
    }

    static class ViewHolder{
        public CircleImageView header_image;
        public TextView name,paotuan_name;

        public  ViewHolder(View view){
            header_image= (CircleImageView) view.findViewById(R.id.img);
            name= (TextView) view.findViewById(R.id.tv_name);
            paotuan_name= (TextView) view.findViewById(R.id.paotuan_name);

        }
    }
}
