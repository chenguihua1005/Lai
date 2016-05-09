package com.softtek.lai.module.personalPK.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.personalPK.model.PKListModel;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by jerry.guan on 5/5/2016.
 */
public class PKListAdapter extends BaseAdapter{

    public static final int NAIXI=0;
    public static final int NAIXICAO=1;
    public static final int CUSTOM=2;
    public static final int NOSTART=0;
    public static final int PROCESSING=1;
    public static final int Completed=2;

    private Context context;
    private List<PKListModel> datas;

    public PKListAdapter(Context context, List<PKListModel> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PKListHolder holder;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.pklist_item,parent,false);
            holder=new PKListHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (PKListHolder) convertView.getTag();
        }
        //绑定数据
        PKListModel model=datas.get(position);
        holder.cb_zan_left.setText(model.getChP()+"");
        holder.cb_zan_right.setText(model.getBChp()+"");
        holder.pk_name1.setText(model.getUserName());
        holder.pk_name2.setText(model.getBUserName());
        holder.tv_time.setText(DateUtil.getInstance().convertDateStr(model.getStart(),"yyyy年MM月dd日")+"——"+
                DateUtil.getInstance().convertDateStr(model.getEnd(),"yyyy年MM月dd日"));
        if(model.getChipType()==NAIXI){
            holder.iv_jiangli.setBackgroundResource(R.drawable.pk_naixi);
        }else if(model.getChipType()==NAIXICAO){
            holder.iv_jiangli.setBackgroundResource(R.drawable.pk_list_naixicao);
        }else if(model.getChipType()==CUSTOM){
            //holder.iv_jiangli.setBackgroundResource(R.drawable.pk_list_);
        }
        if(model.getTStatus()==NOSTART){
            holder.tv_status.setBackgroundResource(R.drawable.pk_list_jingxingzhong);
            holder.tv_status.setText("未开始");
        }else if(model.getTStatus()==PROCESSING){
            holder.tv_status.setBackgroundResource(R.drawable.pk_list_jingxingzhong);
            holder.tv_status.setText("进行中");
        }else if(model.getTStatus()==Completed){
            holder.tv_status.setBackgroundResource(R.drawable.pk_list_jingxingzhong);
            holder.tv_status.setText("已结束");
        }
        //载入头像
        String path= AddressManager.get("photoHost");
        if(StringUtils.isNotEmpty(model.getPhoto())){
            Picasso.with(context).load(path+model.getPhoto()).fit()
                    .placeholder(R.drawable.img_default)
                    .error(R.drawable.img_default)
                    .into(holder.sender1_header);
        }
        if(StringUtils.isNotEmpty(model.getBPhoto())){
            Picasso.with(context).load(path+model.getPhoto()).fit()
                    .placeholder(R.drawable.img_default)
                    .error(R.drawable.img_default)
                    .into(holder.sender2_header);
        }
        return convertView;
    }

    static class PKListHolder{
        public TextView tv_status,tv_time,pk_name1,pk_name2;
        public CheckBox cb_zan_right,cb_zan_left;
        public ImageView iv_jiangli,sender1,sender2;
        public CircleImageView sender1_header,sender2_header;

        public PKListHolder(View view){
            tv_status= (TextView) view.findViewById(R.id.tv_status);
            tv_time= (TextView) view.findViewById(R.id.tv_time);
            pk_name1= (TextView) view.findViewById(R.id.tv_pk_name1);
            pk_name2= (TextView) view.findViewById(R.id.tv_pk_name2);
            cb_zan_right= (CheckBox) view.findViewById(R.id.cb_zan_right);
            cb_zan_left= (CheckBox) view.findViewById(R.id.cb_zan_left);
            iv_jiangli= (ImageView) view.findViewById(R.id.iv_jiangli);
            sender1= (ImageView) view.findViewById(R.id.sender1);
            sender2= (ImageView) view.findViewById(R.id.sender2);
            sender1_header= (CircleImageView) view.findViewById(R.id.sender1_header);
            sender2_header= (CircleImageView) view.findViewById(R.id.sender2_header);

        }
    }
}
