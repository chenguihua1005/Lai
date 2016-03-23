package com.softtek.lai.module.grade.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.grade.model.SRInfo;
import com.softtek.lai.utils.DisplayUtil;

import java.util.List;

/**
 * Created by jerry.guan on 3/23/2016.
 */
public class TutorAdapter extends BaseAdapter{

    private LayoutInflater inflater;
    private List<SRInfo> infos;
    private int screenWidth;

    public TutorAdapter(Context context,List<SRInfo> infos){
        inflater=LayoutInflater.from(context);
        this.infos=infos;
        screenWidth= DisplayUtil.getMobileWidth(context);
    }

    @Override
    public int getCount() {
        return infos.size();
    }

    @Override
    public Object getItem(int position) {
        return infos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.tutor_item,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        SRInfo info=infos.get(position);
        holder.tv_name.setText(info.getUserName()+" "+info.getMobile());
        if(Integer.parseInt(info.getIsInvited())== Constants.NOT_INVITED){
            holder.not_invited.setVisibility(View.VISIBLE);
            holder.tv_num.setVisibility(View.GONE);
            holder.tv_totleweight.setVisibility(View.GONE);
            holder.tv_reset.setVisibility(View.GONE);
        }else{
            holder.tv_num.setText(info.getNum());
            holder.tv_reset.setText(info.getRtest());
            holder.tv_totleweight.setText(info.getTotalWight());
        }
        holder.ll_context.setLayoutParams(new LinearLayout.LayoutParams(screenWidth, ViewGroup.LayoutParams.MATCH_PARENT));
        if(holder.horizontalScrollView.getScrollX()>0){
            holder.horizontalScrollView.smoothScrollTo(0,0);
        }
        convertView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        int actionWidth = holder.btn_delete.getWidth();
                        int scrollX =holder.horizontalScrollView.getScrollX();
                        if(scrollX>actionWidth/2){
                            holder.horizontalScrollView.smoothScrollTo(actionWidth, 0);
                        }else {
                            holder.horizontalScrollView.smoothScrollTo(0, 0);
                        }
                        return true;
                }
                return false;
            }
        });
        return convertView;
    }

    static class ViewHolder{

        HorizontalScrollView horizontalScrollView;
        LinearLayout ll_context;
        Button btn_delete;

        TextView tv_name,tv_num,tv_reset,tv_totleweight,not_invited;

        public ViewHolder(View view) {
            horizontalScrollView= (HorizontalScrollView) view.findViewById(R.id.hs);
            ll_context= (LinearLayout) view.findViewById(R.id.ll_context);
            btn_delete= (Button) view.findViewById(R.id.btn_delete);
            tv_name= (TextView) view.findViewById(R.id.tv_name);
            tv_num= (TextView) view.findViewById(R.id.tv_num);
            tv_reset= (TextView) view.findViewById(R.id.tv_reset);
            tv_totleweight= (TextView) view.findViewById(R.id.tv_totalwight);
            not_invited= (TextView) view.findViewById(R.id.tv_not_invited);
        }
    }
}
