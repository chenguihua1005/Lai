/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.group.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.counselor.presenter.IAssistantPresenter;
import com.softtek.lai.module.group.model.GroupModel;
import com.softtek.lai.module.group.presenter.SportGroupManager;
import com.softtek.lai.module.group.view.GroupMainActivity;
import com.softtek.lai.module.login.model.UserModel;

import java.util.List;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class GroupAdapter extends BaseAdapter {
    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局
    private List<GroupModel> list;
    private BaseActivity context;
    private IAssistantPresenter assistantPresenter;

    /**
     * 构造函数
     */
    public GroupAdapter(BaseActivity context, List<GroupModel> list) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
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
            convertView = mInflater.inflate(R.layout.group_item, null);
            holder = new ViewHolder();
            /**得到各个控件的对象*/
            holder.text_id = (TextView) convertView.findViewById(R.id.text_id);
            holder.text_join = (TextView) convertView.findViewById(R.id.text_join);
            holder.text_name = (TextView) convertView.findViewById(R.id.text_name);
            holder.img_more = (ImageView) convertView.findViewById(R.id.img_more);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }
        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        final GroupModel groupModel = list.get(position);
        if ("1".equals(groupModel.getIsHasSonRG())) {
            holder.img_more.setVisibility(View.VISIBLE);
            holder.text_join.setVisibility(View.GONE);
        } else {
            holder.img_more.setVisibility(View.GONE);
            holder.text_join.setVisibility(View.VISIBLE);
        }
        holder.text_id.setText("跑团号:" + groupModel.getRGNum());
        holder.text_name.setText(groupModel.getRGName());
        holder.text_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SportGroupManager sportGroupManager = new SportGroupManager();
                context.dialogShow("加载中");
                sportGroupManager.joinRunGroup(groupModel.getRGId(), UserInfoModel.getInstance().getUser().getUserid(), new SportGroupManager.JoinRunGroupCallBack() {
                    @Override
                    public void joinRunGroup(boolean b) {
                        context.dialogDissmiss();
                        if(b){
                            UserModel model=UserInfoModel.getInstance().getUser();
                            model.setIsJoin("1");
                            UserInfoModel.getInstance().saveUserCache(model);
                            Intent intent=new Intent(context, GroupMainActivity.class);
                            context.startActivity(intent);
                        }
                    }
                });
            }
        });
        return convertView;
    }

    /**
     * 存放控件
     */
    public class ViewHolder {
        public TextView text_name;
        public TextView text_id;
        public TextView text_join;
        public ImageView img_more;
    }
}



