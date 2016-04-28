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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.counselor.model.ApplyAssistantModel;
import com.softtek.lai.module.counselor.model.ClassInfoModel;
import com.softtek.lai.module.counselor.presenter.AssistantImpl;
import com.softtek.lai.module.counselor.presenter.IAssistantPresenter;

import java.util.List;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class ApplyAssistantAdapter extends BaseAdapter {
    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局
    private List<ApplyAssistantModel> list;
    BaseActivity context;
    private IAssistantPresenter assistantPresenter;

    /**
     * 构造函数
     */
    public ApplyAssistantAdapter(BaseActivity context, List<ApplyAssistantModel> list) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
        assistantPresenter = new AssistantImpl(context);
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
            convertView = mInflater.inflate(R.layout.apply_assistant_list_item, null);
            holder = new ViewHolder();
            /**得到各个控件的对象*/
//            holder.rel_item = (RelativeLayout) convertView.findViewById(R.id.rel_item);
//            holder.lin_state = (LinearLayout) convertView.findViewById(R.id.lin_state);
            holder.text_class_name = (TextView) convertView.findViewById(R.id.text_class_name);
            holder.text_class_time = (TextView) convertView.findViewById(R.id.text_class_time);
            holder.text_apply = (TextView) convertView.findViewById(R.id.text_apply);
            holder.text_state = (TextView) convertView.findViewById(R.id.text_state);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }
        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        final ApplyAssistantModel applyAssistantModel = list.get(position);
        holder.text_class_name.setText(applyAssistantModel.getClassName());
        holder.text_class_time.setText("开班日期：" + applyAssistantModel.getStartDate());
        if ("0".equals(applyAssistantModel.getState())) {
            holder.text_apply.setVisibility(View.VISIBLE);
            holder.text_state.setVisibility(View.GONE);
        } else {
            holder.text_apply.setVisibility(View.GONE);
            holder.text_state.setVisibility(View.VISIBLE);
        }
        holder.text_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = UserInfoModel.getInstance().getUser().getUserid();
                String name = UserInfoModel.getInstance().getUser().getNickname();
                if (name == null || "null".equals(name)) {
                    name = "";
                }
                String startDate = applyAssistantModel.getStartDate();
                String[] timeStr = startDate.split("-");
                String time = "";
                if ("01".equals(timeStr[1]) || "1".equals(timeStr[1])) {
                    time = "一月班";
                } else if ("02".equals(timeStr[1]) || "2".equals(timeStr[1])) {
                    time = "二月班";
                } else if ("03".equals(timeStr[1]) || "3".equals(timeStr[1])) {
                    time = "三月班";
                } else if ("04".equals(timeStr[1]) || "4".equals(timeStr[1])) {
                    time = "四月班";
                } else if ("05".equals(timeStr[1]) || "5".equals(timeStr[1])) {
                    time = "五月班";
                } else if ("06".equals(timeStr[1]) || "6".equals(timeStr[1])) {
                    time = "六月班";
                } else if ("07".equals(timeStr[1]) || "7".equals(timeStr[1])) {
                    time = "七月班";
                } else if ("08".equals(timeStr[1]) || "8".equals(timeStr[1])) {
                    time = "八月班";
                } else if ("09".equals(timeStr[1]) || "9".equals(timeStr[1])) {
                    time = "九月班";
                } else if ("10".equals(timeStr[1]) || "10".equals(timeStr[1])) {
                    time = "十月班";
                } else if ("11".equals(timeStr[1]) || "11".equals(timeStr[1])) {
                    time = "十一月班";
                } else if ("12".equals(timeStr[1]) || "12".equals(timeStr[1])) {
                    time = "十二月班";
                }
                String comment = name + "申请了" + time + "助教";
                context.dialogShow("加载中");
                assistantPresenter.srApplyAssistant(id, applyAssistantModel.getManagerId(), applyAssistantModel.getClassId(), comment, holder.text_apply, holder.text_state);
            }
        });
        return convertView;
    }

    /**
     * 存放控件
     */
    public class ViewHolder {
        public TextView text_class_name;
        public TextView text_class_time;
        public TextView text_apply;
        public TextView text_state;
    }
}



