package com.ggx.widgets.drop;

import android.content.Context;

import com.ggx.widgets.R;
import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.ggx.widgets.view.CheckTextView;

import java.util.List;

/**
 * @author jerry.Guan
 *         created by 2016/11/18
 */

public abstract class SimpleTextAdapter<T> extends EasyAdapter<T>{


    public SimpleTextAdapter(Context context, List<T> datas) {
        super(context, datas, R.layout.text_item);
    }

    @Override
    public void convert(ViewHolder holder, T data, int position) {
        CheckTextView tv=holder.getView(R.id.tv_item_filter);
        tv.setText(getText(data));
        initView(tv);
    }

    public abstract String getText(T data);
    protected void initView(CheckTextView textView){}
}
