package com.softtek.lai.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softtek.lai.R;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import zilla.libcore.lifecircle.LifeCircle;
import zilla.libcore.ui.LayoutInjectUtil;

/**
 * Created by jerry.guan on 7/7/2016.
 */
public abstract class LazyBaseFragment2<T extends BasePresenter> extends ProgressFragment implements BaseView{

    private boolean isVisible=false;//可否可见
    protected boolean isPrepared=false;//是否加载过
    private boolean isCreatedView=false;//是否加载完成试图

    protected View contentView;
    private T presenter;

    public T getPresenter() {
        return presenter;
    }

    public void setPresenter(T presenter) {
        this.presenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(LayoutInjectUtil.getInjectLayoutId(this), container, false);
        LifeCircle.onCreate(this);
        ButterKnife.inject(this, contentView);
        isPrepared=false;
        initViews();
        return inflater.inflate(R.layout.fragment_progress, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setContentView(contentView);
        setEmptyText("暂无数据");
        isCreatedView=true;
        initDatas();
        //如果当前的frgment可见且没有加载过数据则 正常加载数据
        if(isVisible&&!isPrepared){
            isPrepared=true;
            setContentShown(false);
            lazyLoad();
        }
    }

    @Override
    public void onDestroyView() {
        isCreatedView=false;
        if(presenter!=null){
            presenter.recycle();
        }
        LifeCircle.onDestory(this);
        ButterKnife.reset(this);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getContext());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getContext());
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

    @Override
    public void dialogShow(String message) {

    }

    @Override
    public void dialogDissmiss() {

    }

    @Override
    public void dialogShow() {

    }
}
