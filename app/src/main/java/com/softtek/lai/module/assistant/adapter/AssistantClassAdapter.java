package com.softtek.lai.module.assistant.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.assistant.model.AssistantClassInfo;
import com.softtek.lai.module.counselor.model.Assistant;
import com.softtek.lai.module.counselor.presenter.AssistantImpl;
import com.softtek.lai.module.counselor.presenter.IAssistantPresenter;
import com.squareup.picasso.Picasso;

import java.util.List;

import zilla.libcore.file.SharedPreferenceService;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class AssistantClassAdapter extends BaseAdapter {
    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局
    private List<AssistantClassInfo> list;
    private Context context;
    private IAssistantPresenter assistantPresenter;

    /**
     * 构造函数
     */
    public AssistantClassAdapter(Context context, List<AssistantClassInfo> list) {
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
            convertView = mInflater.inflate(R.layout.fragment_assiatant_list_class_item, null);
            holder = new ViewHolder();
            /**得到各个控件的对象*/
            holder.text_value = (TextView) convertView.findViewById(R.id.text_value);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.rel_class_item = (RelativeLayout) convertView.findViewById(R.id.rel_class_item);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }
        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        AssistantClassInfo assistantClassInfo=list.get(position);
        String startTimeStr=assistantClassInfo.getStartDate().toString();
        String str[]=startTimeStr.split("-");
        if("01".equals(str[1])||"1".equals(str[1])){
            holder.text_value.setText("一月班:"+assistantClassInfo.getClassName()+"("+assistantClassInfo.getCnt()+")");
        }else if("02".equals(str[1])||"2".equals(str[1])){
            holder.text_value.setText("二月班:"+assistantClassInfo.getClassName()+"("+assistantClassInfo.getCnt()+")");
        }else if("03".equals(str[1])||"3".equals(str[1])){
            holder.text_value.setText("三月班:"+assistantClassInfo.getClassName()+"("+assistantClassInfo.getCnt()+")");
        }else if("04".equals(str[1])||"4".equals(str[1])){
            holder.text_value.setText("四月班:"+assistantClassInfo.getClassName()+"("+assistantClassInfo.getCnt()+")");
        }else if("05".equals(str[1])||"5".equals(str[1])){
            holder.text_value.setText("五月班:"+assistantClassInfo.getClassName()+"("+assistantClassInfo.getCnt()+")");
        }else if("06".equals(str[1])||"6".equals(str[1])){
            holder.text_value.setText("六月班:"+assistantClassInfo.getClassName()+"("+assistantClassInfo.getCnt()+")");
        }else if("07".equals(str[1])||"7".equals(str[1])){
            holder.text_value.setText("七月班:"+assistantClassInfo.getClassName()+"("+assistantClassInfo.getCnt()+")");
        }else if("08".equals(str[1])||"8".equals(str[1])){
            holder.text_value.setText("八月班:"+assistantClassInfo.getClassName()+"("+assistantClassInfo.getCnt()+")");
        }else if("09".equals(str[1])||"9".equals(str[1])){
            holder.text_value.setText("九月班:"+assistantClassInfo.getClassName()+"("+assistantClassInfo.getCnt()+")");
        }else if("10".equals(str[1])){
            holder.text_value.setText("十月班:"+assistantClassInfo.getClassName()+"("+assistantClassInfo.getCnt()+")");
        }else if("11".equals(str[1])){
            holder.text_value.setText("十一月班:"+assistantClassInfo.getClassName()+"("+assistantClassInfo.getCnt()+")");
        }else if("12".equals(str[1])){
            holder.text_value.setText("十二月班:"+assistantClassInfo.getClassName()+"("+assistantClassInfo.getCnt()+")");
        }

        return convertView;
    }

    /**
     * 存放控件
     */
    public class ViewHolder {
        public TextView text_value;
        public ImageView img;
        public RelativeLayout rel_class_item;
    }
}



