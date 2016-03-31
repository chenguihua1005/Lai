/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.softtek.lai.R;
import com.softtek.lai.module.counselor.model.InviteStudentInfoModel;
import com.softtek.lai.module.counselor.presenter.IStudentPresenter;
import com.softtek.lai.module.counselor.presenter.StudentImpl;
import com.squareup.picasso.Picasso;
import zilla.libcore.file.SharedPreferenceService;

import java.util.List;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class InviteStudentAdapter extends BaseAdapter {
    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局
    private List<InviteStudentInfoModel> list;
    private Context context;
    private IStudentPresenter studentPresenter;

    /**
     * 构造函数
     */
    public InviteStudentAdapter(Context context, List<InviteStudentInfoModel> list) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
        studentPresenter = new StudentImpl(context);
        Log.e("jarvis", list.toString());
    }

    @Override
    public int getCount() {
        return list.size();//返回数组的长度
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 书中详细解释该方法
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        //观察convertView随ListView滚动情况
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.invite_student_list_item, null);
            holder = new ViewHolder();
            /**得到各个控件的对象*/
            holder.text_phone = (TextView) convertView.findViewById(R.id.text_phone);
            holder.text_name = (TextView) convertView.findViewById(R.id.text_name);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.img_invite = (ImageView) convertView.findViewById(R.id.img_invite);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }
        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        final InviteStudentInfoModel assistant = list.get(position);
        if ("".equals(assistant.getPhoto())) {
            Picasso.with(context).load("111").error(R.drawable.img_default).into(holder.img);
        } else {
            Picasso.with(context).load(assistant.getPhoto()).error(R.drawable.img_default).into(holder.img);
        }
        holder.text_phone.setText(assistant.getMobile().toString());
        holder.text_name.setText(assistant.getUserName().toString());
        String state = assistant.getIsinvite().toString();

        if ("0".equals(state)) {
            holder.img_invite.setImageDrawable(context.getResources().getDrawable(R.drawable.img_invite));
            holder.img_invite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String classId = SharedPreferenceService.getInstance().get("classId", "");
                    studentPresenter.sendInviterMsg(assistant.getMobile().toString(), classId, holder.img_invite);
                }
            });
        } else {
            holder.img_invite.setImageDrawable(context.getResources().getDrawable(R.drawable.img_invited));
        }
        return convertView;
    }

    /**
     * 存放控件
     */
    public class ViewHolder {
        public TextView text_phone;
        public TextView text_name;
        public ImageView img;
        public ImageView img_invite;
    }
}


