/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */
package com.softtek.lai.common;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umeng.analytics.MobclickAgent;

import java.io.Serializable;

import butterknife.ButterKnife;
import zilla.libcore.lifecircle.LifeCircle;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.exit.AppExitLife;
import zilla.libcore.ui.LayoutInjectUtil;

/**
 * Fragment基类
 * Created by zilla on 9/10/15.
 */
public abstract class BaseFragment extends Fragment {
    /**
     * 生命周期管理
     */
    @LifeCircleInject
    AppExitLife lifeCicleExit;

    protected View contentView;

    protected Serializable mParamObj;
    private static final String ARG_PARAM = "PARAM";

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param mParamObj Parameter.
     * @return A new instance of fragment OrderListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BaseFragment newInstance(Class<? extends BaseFragment> fragmentClass, Serializable mParamObj) {
        BaseFragment fragment = null;
        try {
            fragment = fragmentClass.newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM, mParamObj);

        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        contentView = inflater.inflate(LayoutInjectUtil.getInjectLayoutId(this), container, false);
        LifeCircle.onCreate(this);
        ButterKnife.inject(this, contentView);
        initViews();

        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDatas();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        LifeCircle.onDestory(this);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onSome(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    protected abstract void initViews();

    protected abstract void initDatas();

}
