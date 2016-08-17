package com.softtek.lai.common;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.utils.SystemBarTintManager;

import butterknife.ButterKnife;
import zilla.libcore.lifecircle.LifeCircle;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.exit.AppExitLife;
import zilla.libcore.ui.LayoutInjectUtil;

/**
 * Created by jerry.guan on 7/7/2016.
 */
public abstract class LazyBaseFragment extends Fragment{

    private boolean isVisible=false;//可否可见
    protected boolean isPrepared=false;//是否加载过
    private boolean isCreatedView=false;//是否加载完成试图

    protected View contentView;
    protected ProgressDialog progressDialogs;
    protected Dialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        contentView = inflater.inflate(LayoutInjectUtil.getInjectLayoutId(this), container, false);
        LifeCircle.onCreate(this);
        ButterKnife.inject(this, contentView);
        isPrepared=false;
        initViews();
        return contentView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isCreatedView=true;
        initDatas();
        //如果当前的frgment可见且没有加载过数据则 正常加载数据
        if(isVisible&&!isPrepared){
            isPrepared=true;
            lazyLoad();
        }
    }

    @Override
    public void onDestroyView() {
        isCreatedView=false;
        super.onDestroyView();
        ButterKnife.reset(this);
    }
    public void dialogShow(String value) {
        if (progressDialogs == null) {
            progressDialogs = new ProgressDialog(getContext());
            progressDialogs.setCanceledOnTouchOutside(false);
            progressDialogs.setCancelable(false);
            progressDialogs.setMessage(value);
            progressDialogs.show();
        }
    }
    public void dialogDissmiss() {
        if (progressDialogs != null) {
            progressDialogs.dismiss();
            progressDialogs = null;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        LifeCircle.onDestory(this);
    }


    public boolean isCreatedView() {
        return isCreatedView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()){
            isVisible=true;
            onVisible();
        }else{
            isVisible=false;
            onInvisible();
        }
    }


    /**
     * 当fragment可见时调用
     * 如果当前frgment可见，并且view是被创建好的且没有加载过数据的时候会自动网络请求加载数据
     */
    protected void onVisible(){
        if(isCreatedView&&!isPrepared){
            isPrepared=true;
            lazyLoad();
        }
    }

    /**
     * 不可见
     */
    protected void onInvisible() {
    }

    public boolean isPrepared() {
        return isPrepared;
    }

    public void setPrepared(boolean prepared) {
        isPrepared = prepared;
    }

    protected abstract void lazyLoad();

    protected abstract void initViews();

    protected abstract void initDatas();
}
