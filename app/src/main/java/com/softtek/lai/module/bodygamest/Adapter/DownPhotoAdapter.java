package com.softtek.lai.module.bodygamest.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.bodygamest.model.DownPhoto;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by lareina.qiao on 3/31/2016.
 */
public class DownPhotoAdapter extends BaseAdapter {
    private Context context;
    private List<DownPhoto> downPhotoList;
    private LayoutInflater inflater;
    public DownPhotoAdapter(Context context,List<DownPhoto> downPhotoList)
    {
        this.context=context;
        inflater=LayoutInflater.from(context);
        this.downPhotoList=downPhotoList;
    }
    public void updateData(List<DownPhoto> downPhotoList){
        this.downPhotoList=downPhotoList;
        notifyDataSetChanged();;
    }

    @Override
    public int getCount() {
        return downPhotoList.size();
    }

    @Override
    public Object getItem(int position) {
        return downPhotoList.get(position);
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
            convertView=LayoutInflater.from(context).inflate(R.layout.listview_retest_class,parent,false);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        DownPhoto downPhoto=downPhotoList.get(position);
        viewHolder.tv_uploadphoto_date.setText(downPhoto.getCreateDate());
        if(!TextUtils.isEmpty(downPhoto.getImgUrl())){
            Picasso.with(context).load(downPhoto.getImgUrl()).placeholder(R.drawable.lufei).error(R.drawable.lufei).into(viewHolder.im_uploadphoto);
        }else{
            Picasso.with(context).load("www").placeholder(R.drawable.lufei).error(R.drawable.lufei).into(viewHolder.im_uploadphoto);
        }

        return convertView;
    }
    class ViewHolder{
        TextView tv_uploadphoto_date;
        ImageView im_uploadphoto;

        public ViewHolder(View view){
            tv_uploadphoto_date=(TextView)view.findViewById(R.id.tv_uploadphoto_date);
            im_uploadphoto=(ImageView) view.findViewById(R.id.im_uploadphoto);

        }
    }
}
