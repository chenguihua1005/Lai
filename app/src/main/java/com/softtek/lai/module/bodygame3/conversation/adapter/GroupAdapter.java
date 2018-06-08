/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.softtek.lai.module.bodygame3.conversation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.bodygame3.conversation.model.ContactClassModel;

import java.util.List;

public class GroupAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<ContactClassModel> list;
    private Context context;


    public GroupAdapter(Context context, List<ContactClassModel> groups) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.list = groups;

    }

    public void updateData(List<ContactClassModel> groups) {
        this.list = groups;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ContactClassModel getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder holder;
        //观察convertView随ListView滚动情况
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.chat_contant_list_item, null);
            holder = new ViewHolder();
            /**得到各个控件的对象*/
            holder.text_name = (TextView) convertView.findViewById(R.id.text_name);
            holder.img = (ImageView) convertView.findViewById(R.id.img);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }
        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        final ContactClassModel contactListInfo = list.get(position);
//        String photo = contactListInfo.getPhoto();
//        String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
//        if ("".equals(photo)) {
//            Picasso.with(context).load("111").fit().error(R.drawable.img_default).into(holder.img);
//        } else {
//            Picasso.with(context).load(path + photo).fit().error(R.drawable.img_default).into(holder.img);
//        }
        holder.text_name.setText(contactListInfo.getClassName());
        holder.img.setImageResource(R.drawable.group_default);
        return convertView;
    }

    /**
     * 存放控件
     */
    private class ViewHolder {
        public TextView text_name;
        public ImageView img;
    }
}
