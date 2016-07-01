package com.softtek.lai.module.pastreview.adapter;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.pastreview.model.ClassListModel;
import com.softtek.lai.module.pastreview.model.MyPhotoListModel;
import com.softtek.lai.utils.ChMonth;
import com.softtek.lai.utils.DateUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by lareina.qiao on 6/28/2016.
 */
public class MyPhotoListAdapter extends BaseAdapter {
    private Context context;
    DateUtil dateUtil;
    private List<MyPhotoListModel> myPhotoListModelList=new ArrayList<MyPhotoListModel>();

    public MyPhotoListAdapter(Context context, List<MyPhotoListModel> myPhotoListModelList) {
        this.context = context;
        this.myPhotoListModelList = myPhotoListModelList;
    }

    @Override
    public int getCount() {
        return myPhotoListModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return myPhotoListModelList.get(position);
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
            convertView=LayoutInflater.from(context).inflate(R.layout.listview_myphoto_listitem,parent,false);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        MyPhotoListModel myPhotoListModel=myPhotoListModelList.get(position);
        String path = AddressManager.get("photoHost");
        String day="26";
        String month="二月";
        ChMonth chMonth=new ChMonth();
        viewHolder.tv_photodate.setText(day);
        viewHolder.tv_photoday.setText(chMonth.tomonth(month));
        if (!TextUtils.isEmpty(myPhotoListModel.getImgUrl()))
        {
            Picasso.with(context).load(path+myPhotoListModel.getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_uploadphoto);
        }
        else {
            Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_uploadphoto);
        }
        return convertView;
    }
    class ViewHolder{
        TextView tv_photodate;
        TextView tv_photoday;
        ImageView im_uploadphoto;
        public ViewHolder(View view){
            tv_photodate=(TextView)view.findViewById(R.id.tv_photodate);
            tv_photoday= (TextView) view.findViewById(R.id.tv_photoday);
            im_uploadphoto=(ImageView) view.findViewById(R.id.im_uploadphoto);
        }
    }
}
