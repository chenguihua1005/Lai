package com.softtek.lai.module.laiClassroom.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.softtek.lai.module.laiClassroom.model.Artical;

import java.util.List;

/**
 * Created by jerry.guan on 3/10/2017.
 */

public class WholeAdapter extends BaseAdapter{

    private static final int VIDEO=1;
    private static final int SINGLE_PIC=2;
    private static final int MUTIL_PIC=3;

    private Context context;
    private List<Artical> articals;

    public WholeAdapter(Context context, List<Artical> articals) {
        this.context = context;
        this.articals = articals;
    }

    @Override
    public int getCount() {
        return articals==null?0:articals.size();
    }

    @Override
    public Object getItem(int position) {
        return articals==null?null:articals.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        Artical artical=articals.get(position);
        return artical.getMediaType()==2?VIDEO:artical.getMediaType()==1?artical.getIsMultiPic()==1?MUTIL_PIC:SINGLE_PIC:-1;
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int itemType=getItemViewType(position);
        if(itemType==VIDEO){
            if(convertView==null){

            }
        }
        return null;
    }

    private static class VideoHolder{

    }
}
