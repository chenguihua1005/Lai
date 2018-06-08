package com.softtek.lai.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by jerry.guan on 7/15/2016.
 */
public class ListViewUtil {

    public static void setListViewHeightBasedOnChildren(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        int listCount=listAdapter.getCount();
        if(listCount!=0){
            View listItem = listAdapter.getView(0, null, listView);
            listItem.measure(0, 0);
            totalHeight=listItem.getMeasuredHeight()*listCount;
        }
       /* for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }*/

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listCount - 1));
        listView.setLayoutParams(params);
    }
}
