/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.grade.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.grade.model.SRInfoModel;
import com.softtek.lai.module.grade.presenter.GradeImpl;
import com.softtek.lai.module.grade.presenter.IGrade;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.util.Util;

/**
 * Created by jerry.guan on 3/23/2016.
 */
public class TutorAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<SRInfoModel> infos;
    private int screenWidth;
    private IGrade grade;
    private int review_flag;
    private Context context;

    public TutorAdapter(Context context, List<SRInfoModel> infos,int review_flag) {
        this.context=context;
        inflater = LayoutInflater.from(context);
        this.infos = infos;
        screenWidth = DisplayUtil.getMobileWidth(context);
        grade=new GradeImpl();
        this.review_flag=review_flag;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.tutor_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final SRInfoModel info = infos.get(position);
        holder.tv_name.setText(info.getUserName());
        holder.tv_phone.setText(info.getMobile());
        if(!"".equals(info.getPhoto())&&null!=info.getPhoto()){
            Picasso.with(context).load(AddressManager.get("photoHost")+info.getPhoto()).fit().placeholder(R.drawable.img_default)
                    .error(R.drawable.img_default).into(holder.circleImageView);
        }
        if (Integer.parseInt(info.getIsInvited()) == Constants.NOT_INVITED) {
            holder.not_invited.setVisibility(View.VISIBLE);
            holder.ll_context.setVisibility(View.GONE);
        } else {
            holder.not_invited.setVisibility(View.GONE);
            holder.ll_context.setVisibility(View.VISIBLE);
            holder.tv_num.setText(info.getNum());
            holder.tv_reset.setText(info.getRtest());
            holder.tv_totleweight.setText(info.getTotalWight() + "斤");
        }
        if(review_flag==0){
            holder.btn_delete.setVisibility(View.GONE);
        }
        holder.ll.setLayoutParams(new LinearLayout.LayoutParams(screenWidth, LinearLayout.LayoutParams.MATCH_PARENT));
        if (holder.horizontalScrollView.getScrollX() > 0) {
            holder.horizontalScrollView.smoothScrollTo(0, 0);
        }
        convertView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        int actionWidth = holder.btn_delete.getWidth();
                        int scrollX = holder.horizontalScrollView.getScrollX();
                        if (scrollX > actionWidth / 2) {
                            holder.horizontalScrollView.smoothScrollTo(actionWidth, 0);
                        } else {
                            holder.horizontalScrollView.smoothScrollTo(0, 0);
                        }
                        return true;
                }
                return false;
            }
        });
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.horizontalScrollView.smoothScrollTo(0,0);
                grade.removeTutorRole(1, 1, new Callback<ResponseData>() {

                    @Override
                    public void success(ResponseData responseData, Response response) {
                        if (responseData.getStatus() == 200) {
                            infos.remove(position);
                            notifyDataSetChanged();
                        }
                        Util.toastMsg(responseData.getMsg());
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        ZillaApi.dealNetError(error);
                    }
                });
            }
        });
        return convertView;
    }

    static class ViewHolder {

        HorizontalScrollView horizontalScrollView;
        LinearLayout ll_context, ll;
        Button btn_delete;
        CircleImageView circleImageView;
        TextView tv_name, tv_num, tv_reset, tv_totleweight, not_invited, tv_phone;

        public ViewHolder(View view) {
            horizontalScrollView = (HorizontalScrollView) view.findViewById(R.id.hs);
            ll_context = (LinearLayout) view.findViewById(R.id.ll_context);
            btn_delete = (Button) view.findViewById(R.id.btn_delete);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_num = (TextView) view.findViewById(R.id.tv_num);
            tv_reset = (TextView) view.findViewById(R.id.tv_reset);
            tv_totleweight = (TextView) view.findViewById(R.id.tv_totalwight);
            not_invited = (TextView) view.findViewById(R.id.tv_not_invited);
            circleImageView = (CircleImageView) view.findViewById(R.id.civ_header_image);
            tv_phone = (TextView) view.findViewById(R.id.tv_phone);
            ll = (LinearLayout) view.findViewById(R.id.ll);
        }
    }
}
