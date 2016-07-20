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
        System.out.println("1111111------------");
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        System.out.println("222222222222------------");
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        System.out.println("3333333333333------------");
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        System.out.println("4444444444444------------");
        //((MarginLayoutParams) params).setMargins(10, 10, 10, 10); // 可删除

        listView.setLayoutParams(params);
        System.out.println("555555555555------------");
    }
}
