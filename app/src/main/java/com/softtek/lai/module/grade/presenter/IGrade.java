package com.softtek.lai.module.grade.presenter;

import android.app.ProgressDialog;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

import zilla.libzilla.dialog.LoadingDialog;

/**
 * Created by jerry.guan on 3/21/2016.
 */
public interface IGrade {

    /**
     * 获取班级主页信息
     */
    void getGradeInfos(long classId, ProgressDialog loadingDialog);

    /**
     * 发布班级动态
     */
    void sendDynamic(long classId,String dynamicTitle,String dyContent,int dyType,long accountId);

    /**
     * 根据类型和班级获取学员列表
     */
    void getStudentList(String orderType, String classId, PullToRefreshListView lv);
}
