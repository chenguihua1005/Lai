package com.softtek.lai.module.counselor.presenter;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * Created by jarvis on 3/3/2016.
 */
public interface ICounselorClassPresenter {

    //获取班级列表
    void getClassList(ListView expand_lis,LinearLayout lin_create_class,ImageView img_mo_message);
    //创建班级
    void createClass(String className,String startDate,String endDate,String managerId);

}
