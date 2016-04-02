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
import com.softtek.lai.module.bodygamest.model.DownPhotoModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by lareina.qiao on 3/31/2016.
 */
public class DownPhotoAdapter extends BaseAdapter {
    private Context context;
    private List<DownPhotoModel> downPhotoModelList;
    private LayoutInflater inflater;
    public DownPhotoAdapter(Context context,List<DownPhotoModel> downPhotoModelList)
    {
        this.context=context;
        inflater=LayoutInflater.from(context);
        this.downPhotoModelList = downPhotoModelList;
    }
    public void updateData(List<DownPhotoModel> downPhotoModelList){
        this.downPhotoModelList = downPhotoModelList;
        notifyDataSetChanged();;
    }

    @Override
    public int getCount() {
        return downPhotoModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return downPhotoModelList.get(position);
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
            convertView=LayoutInflater.from(context).inflate(R.layout.listview_uploadphoto_item,parent,false);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        DownPhotoModel downPhotoModel = downPhotoModelList.get(position);
        String[] date=downPhotoModel.getCreateDate().split("/");
        String[] yearshi=date[2].split(" ");
        viewHolder.tv_uploadphoto_day.setText(date[1]);
        viewHolder.tv_uploadphoto_month.setText(tomonth(date[0]));
        if(!TextUtils.isEmpty(downPhotoModel.getImgUrl())){
            Picasso.with(context).load(downPhotoModel.getImgUrl()).placeholder(R.drawable.lufei).error(R.drawable.lufei).into(viewHolder.im_uploadphoto);
        }else{
            Picasso.with(context).load("www").placeholder(R.drawable.lufei).error(R.drawable.lufei).into(viewHolder.im_uploadphoto);
        }

        return convertView;
    }
    class ViewHolder{
        TextView tv_uploadphoto_day;
        TextView tv_uploadphoto_month;
        ImageView im_uploadphoto;

        public ViewHolder(View view){
            tv_uploadphoto_day=(TextView)view.findViewById(R.id.tv_uploadphoto_day);
            tv_uploadphoto_month=(TextView)view.findViewById(R.id.tv_uploadphoto_month);
            im_uploadphoto=(ImageView) view.findViewById(R.id.im_uploadphoto);

        }

    }
    public String tomonth(String month){

        if (month.equals("01")||month.equals("1")){
            month="一月";
        }
        else if (month.equals("02")||month.equals("2")){
            month="二月";
        }else if (month.equals("03")||month.equals("3"))
        {
            month="三月";
        }else if (month.equals("04")||month.equals("4"))
        {
            month="四月";

        }else if (month.equals("05")||month.equals("5"))
        {
            month="五月";
        }else if (month.equals("06")||month.equals("6"))
        {
            month="六月";
        }else if (month.equals("07")||month.equals("7"))
        {
            month="七月";
        } else if (month.equals("08")||month.equals("8"))
        {
            month="八月";
        }else if (month.equals("09")||month.equals("9"))
        {
            month="九月";
        }else if (month.equals("10"))
        {
            month="十月";
        }else if (month.equals("11"))
        {
            month="十一月";
        }else
        {
            month="十二月";
        }
        return month;
    }
}
