package com.softtek.lai.module.bodygame3.head.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.module.bodygame3.head.model.PartnersModel;
import com.squareup.picasso.Picasso;


import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by shelly.xu on 12/6/2016.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> implements View.OnClickListener{
    private List<PartnersModel> partnersModels;
    private Context context;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public MyRecyclerAdapter(Context mcontext, List<PartnersModel> partnersModels) {
       this.context=mcontext;
        this.partnersModels = partnersModels;
    }

    @Override
    public MyRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(context).inflate(R.layout. partner_list,parent, false);
            ViewHolder viewHolder=new ViewHolder(view);
            view.setOnClickListener(this);
            return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyRecyclerAdapter.ViewHolder holder, int position) {
        Log.i("试图加载。/。。。。。。。。。。。。。。。。。。。。。。。。");
        PartnersModel partnersModel=partnersModels.get(position);
        holder.paiming.setText(partnersModel.getRanking());
        holder.name_tv.setText(partnersModel.getStuName());
        if (partnersModel.getStuGender().equals("1")) {
            holder.fale.setImageResource(R.drawable.female_iv);
        } else if (partnersModel.getStuGender().equals("0")) {
            holder.fale.setImageResource(R.drawable.male_iv);
        } else if (partnersModel.getStuGender().equals("2")) {

        }
        holder.group_tv.setText(partnersModel.getGroupName());
        if(TextUtils.isEmpty(partnersModel.getStuThImg())){
            Picasso.with(context).load(R.drawable.img_default).into(holder.head_img);
        }else {
            Picasso.with(context).load(AddressManager.get("photoHost")+partnersModel.getStuThImg())
                    .fit().error(R.drawable.img_default)
                    .placeholder(R.drawable.img_default).into(holder.head_img);
        }
        holder.weight_first.setText(partnersModel.getWeight());
        holder.jianzhong_tv.setText(partnersModel.getLoss());
        holder.tv_bi.setText("减重比");
    }

    @Override
    public int getItemCount() {
        return partnersModels.size();
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(view,(PartnersModel) view.getTag());
        }
    }
    public  class ViewHolder extends RecyclerView.ViewHolder {
        TextView paiming;
        ImageView fale;
        TextView name_tv;
        TextView group_tv;
        TextView weight_first;
        TextView jianzhong_tv;
        TextView tv_bi;
        ImageView head_img;

        public ViewHolder(View itemView) {
            super(itemView);
            paiming=(TextView)itemView.findViewById(R.id.paiming);
            fale=(ImageView)itemView.findViewById(R.id.fale);
             name_tv=(TextView)itemView.findViewById(R.id.name_tv);
            group_tv=(TextView)itemView.findViewById(R.id.group_tv);
            weight_first=(TextView)itemView.findViewById(R.id.weight_first);
            jianzhong_tv=(TextView)itemView.findViewById(R.id.jianzhong_tv);
            tv_bi=(TextView)itemView.findViewById(R.id.tv_bi);
            head_img=(ImageView)itemView.findViewById(R.id.head_img);
        }
    }
//添加数据
    public void addItem(PartnersModel content, int position) {
        partnersModels.add(position, content);
        notifyItemInserted(position); //Attention!
    }
    //删除数据
    public void removeItem(PartnersModel model) {
        int position = partnersModels.indexOf(model);
        partnersModels.remove(position);
        notifyItemRemoved(position);//Attention!
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , PartnersModel data);

    }

}
