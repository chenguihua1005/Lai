package com.softtek.lai.module.review.presenter;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * Created by John on 2016/4/10.
 */
public interface IReviewPresenter {

    //获取班级列表
    void getClassList(ListView expand_lis,ImageView img_mo_message);
}
