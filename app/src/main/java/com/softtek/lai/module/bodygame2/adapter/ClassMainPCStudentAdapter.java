/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.bodygame2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame2.model.ClmListModel;
import com.softtek.lai.module.counselor.presenter.IAssistantPresenter;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.utils.StringUtil;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class ClassMainPCStudentAdapter extends BaseAdapter {
    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局
    private List<ClmListModel> list;
    private Context context;
    private IAssistantPresenter assistantPresenter;
    public String type;

    /**
     * 构造函数
     */
    public ClassMainPCStudentAdapter(Context context, List<ClmListModel> list) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
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
            convertView = mInflater.inflate(R.layout.class_main_student_item, null);
            holder = new ViewHolder();
            /**得到各个控件的对象*/

            holder.tv_order = (TextView) convertView.findViewById(R.id.tv_order);
            holder.text_before_weight = (TextView) convertView.findViewById(R.id.text_before_weight);
            holder.text_name = (TextView) convertView.findViewById(R.id.text_name);
            holder.tv_who = (TextView) convertView.findViewById(R.id.tv_who);
            holder.text_value = (TextView) convertView.findViewById(R.id.text_value);
            holder.text_count = (TextView) convertView.findViewById(R.id.text_count);
            holder.cb_gender = (CheckBox) convertView.findViewById(R.id.cb_gender);
            holder.cb_star = (CheckBox) convertView.findViewById(R.id.cb_star);
            holder.cb_fc = (CheckBox) convertView.findViewById(R.id.cb_fc);
            holder.rel = (RelativeLayout) convertView.findViewById(R.id.rel);
            holder.img_type = (ImageView) convertView.findViewById(R.id.img_type);
            holder.civ_header = (ImageView) convertView.findViewById(R.id.civ_header);
            holder.iv_arrow = (ImageView) convertView.findViewById(R.id.iv_arrow);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }
        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        final ClmListModel model = list.get(position);
        if ("0".equals(type)) {
            if ("--".equals(model.getFirstweight())) {
                holder.text_before_weight.setText("初始体重：--");
            } else {
                holder.text_before_weight.setText("初始体重：" + model.getFirstweight() + "斤");
            }
            if (!"--".equals(model.getLoss())) {
                String value = StringUtil.getFloatValue(model.getLoss());
                holder.text_value.setText(model.getLoss() + "斤");
            } else {
                holder.text_value.setText("--");
            }
            holder.img_type.setImageResource(R.drawable.img_bg_jzjs);
        } else if ("1".equals(type)) {
            if ("--".equals(model.getFirstweight())) {
                holder.text_before_weight.setText("初始体重：--");
            } else {
                holder.text_before_weight.setText("初始体重：" + model.getFirstweight() + "斤");
            }
            if (!"--".equals(model.getLoss())) {
                DecimalFormat fnum = new DecimalFormat("##0.00");
                String dd = fnum.format(Float.parseFloat(model.getLoss()));
                System.out.println("dd:" + dd);
                String str = Float.parseFloat(dd) + "";
                String ddd = fnum.format(Float.parseFloat(str));
                System.out.println("ddd:" + ddd);
                String value = StringUtil.getFloatValue(ddd);
                holder.text_value.setText(value + "%");
            } else {
                holder.text_value.setText("--");
            }
            holder.img_type.setImageResource(R.drawable.img_bg_jzbfb);
        } else if ("2".equals(type)) {
            if ("--".equals(model.getFirstweight())) {
                holder.text_before_weight.setText("初始体脂：--");
            } else {
                holder.text_before_weight.setText("初始体脂：" + model.getFirstweight() + "%");
            }

            if (!"--".equals(model.getLoss())) {
                String value = StringUtil.getFloatValue(model.getLoss());
                holder.text_value.setText(value + "%");
            } else {
                holder.text_value.setText("--");
            }
            holder.img_type.setImageResource(R.drawable.img_bg_tzl);
        } else if ("3".equals(type)) {
            if ("--".equals(model.getFirstweight())) {
                holder.text_before_weight.setText("初始腰围：--");
            } else {
                holder.text_before_weight.setText("初始腰围：" + model.getFirstweight() + "cm");
            }
            if (!"--".equals(model.getLoss())) {
                String value = StringUtil.getFloatValue(model.getLoss());
                holder.text_value.setText(value + "cm");
            } else {
                holder.text_value.setText("--");
            }
            holder.img_type.setImageResource(R.drawable.img_bg_ywbh);
        }
        holder.tv_order.setText(model.getOrdernum());
        holder.text_name.setText(model.getUsername());
        holder.tv_who.setText(model.getSuperName());
        holder.text_count.setText("x" + model.getHonorcnt());

        String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
        if ("".equals(model.getPhoto())) {
            Picasso.with(context).load("111").fit().error(R.drawable.img_default).into(holder.civ_header);
        } else {
            Picasso.with(context).load(path + model.getPhoto()).fit().error(R.drawable.img_default).into(holder.civ_header);
        }

        if (model.getGender().equals("1")) {
            holder.cb_gender.setChecked(true);
        } else {
            holder.cb_gender.setChecked(false);
        }
        if (model.getIsStar().equals("1")) {
            holder.cb_star.setChecked(true);
        } else {
            holder.cb_star.setChecked(false);
        }
        if (model.getIsTest().equals("1")) {
            holder.cb_fc.setChecked(true);
        } else {
            holder.cb_fc.setChecked(false);
        }

        String useId = model.getAccountid();
        UserModel userModel = UserInfoModel.getInstance().getUser();
        if (useId.equals(userModel.getUserid())) {
            holder.iv_arrow.setVisibility(View.VISIBLE);
        } else {
            holder.iv_arrow.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    /**
     * 存放控件
     */
    public class ViewHolder {
        public TextView tv_order;
        public TextView text_before_weight;
        public TextView text_name;
        public TextView tv_who;
        public TextView text_value;
        public TextView text_count;
        public CheckBox cb_gender;
        public CheckBox cb_star;
        public CheckBox cb_fc;
        public RelativeLayout rel;
        public ImageView img_type;
        public ImageView civ_header;
        public ImageView iv_arrow;
    }
}



