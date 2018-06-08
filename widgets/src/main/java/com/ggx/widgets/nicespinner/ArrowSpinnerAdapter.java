package com.ggx.widgets.nicespinner;

import android.content.Context;

import com.ggx.widgets.adapter.EasyAdapter;

import java.util.List;

/**
 * Created by jerry.guan on 11/16/2016.
 */

public abstract class ArrowSpinnerAdapter<T> extends EasyAdapter<T>{


    public ArrowSpinnerAdapter(Context context, List<T> datas, int resource) {
        super(context, datas, resource);
    }

    public abstract String getText(int position);
}
