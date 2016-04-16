/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.grade.presenter;

import android.app.ProgressDialog;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.common.ResponseData;

import java.io.File;

import retrofit.Callback;

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
    void sendDynamic(long classId, String dynamicTitle, String dyContent, int dyType, long accountId);

    /**
     * 根据类型和班级获取学员列表
     */
    void getStudentList(String orderType, String classId, PullToRefreshListView lv);

    /**
     * 获取助教列表
     */
    void getTutorList(long classId, PullToRefreshListView lv);

    /*
    修改班级主页的banner
     */
    void updateClassBanner(long classId, String type, File image);

    void removeTutorRole(long classId,long tutorId, Callback<ResponseData> callback);

    /*
    获取动态
     */
    void getClassDynamic(long classId,int pageIndex);
}
