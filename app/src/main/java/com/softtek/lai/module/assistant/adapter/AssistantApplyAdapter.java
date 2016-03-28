package com.softtek.lai.module.assistant.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.assistant.model.AssistantApplyInfo;
import com.softtek.lai.module.assistant.model.AssistantClassInfo;
import com.softtek.lai.module.assistant.presenter.AssistantManageImpl;
import com.softtek.lai.module.assistant.presenter.IAssistantManagePresenter;
import com.softtek.lai.module.counselor.presenter.IAssistantPresenter;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class AssistantApplyAdapter extends BaseAdapter {
    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局
    private List<AssistantApplyInfo> list;
    private Context context;
    private IAssistantPresenter assistantPresenter;
    private IAssistantManagePresenter assistantManagePresenter;

    /**
     * 构造函数
     */
    public AssistantApplyAdapter(Context context, List<AssistantApplyInfo> list) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
        assistantManagePresenter=new AssistantManageImpl(context);
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
            convertView = mInflater.inflate(R.layout.fragment_assistant_apply_list_item, null);
            holder = new ViewHolder();
            /**得到各个控件的对象*/
            holder.text_name = (TextView) convertView.findViewById(R.id.text_name);
            holder.text_value = (TextView) convertView.findViewById(R.id.text_value);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.but_accept = (Button) convertView.findViewById(R.id.but_accept);
            holder.img_refuse = (Button) convertView.findViewById(R.id.img_refuse);
            holder.lin_buttons = (LinearLayout) convertView.findViewById(R.id.lin_buttons);
            holder.text_state = (TextView) convertView.findViewById(R.id.text_state);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }
        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        final AssistantApplyInfo assistantApplyInfo=list.get(position);
        if ("".equals(assistantApplyInfo.getPhoto())) {
            Picasso.with(context).load("111").error(R.drawable.img_default).into(holder.img);
        } else {
            Picasso.with(context).load(assistantApplyInfo.getPhoto()).error(R.drawable.img_default).into(holder.img);
        }

        holder.text_name.setText(assistantApplyInfo.getUserName().toString());
        holder.text_value.setText(assistantApplyInfo.getComments().toString());
        holder.but_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("applyId:"+assistantApplyInfo.getApplyId());
                assistantManagePresenter.reviewAssistantApplyList(assistantApplyInfo.getApplyId(),1,holder.lin_buttons,holder.text_state);
            }
        });
        holder.img_refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assistantManagePresenter.reviewAssistantApplyList(assistantApplyInfo.getApplyId(),0,holder.lin_buttons,holder.text_state);
            }
        });
        return convertView;
    }

    /**
     * 存放控件
     */
    public class ViewHolder {
        public TextView text_name;
        public TextView text_value;
        public ImageView img;
        public Button but_accept;
        public Button img_refuse;
        public LinearLayout lin_buttons;
        public TextView text_state;
    }
}



