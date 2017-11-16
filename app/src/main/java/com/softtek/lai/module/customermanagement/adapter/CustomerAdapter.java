package com.softtek.lai.module.customermanagement.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.softtek.lai.module.customermanagement.model.CustomerModel;

import java.util.List;

/**
 * Created by jessica.zhang on 11/16/2017.
 */

public class CustomerAdapter extends BaseAdapter {
    private Context context;
    private List<CustomerModel> modelList;

    public CustomerAdapter(Context context, List<CustomerModel> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @Override
    public int getCount() {
        return modelList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    private final class ViewHolder {


    }
}
