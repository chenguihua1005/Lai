/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.view;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.counselor.adapter.InviteContantAdapter;
import com.softtek.lai.module.counselor.model.ContactListInfoModel;
import com.softtek.lai.utils.ACache;
import com.softtek.lai.utils.HanziToPinyin;
import com.softtek.lai.utils.SoftInputUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jarvis.liu on 3/22/2016.
 * 邀请通讯录学员
 */
@InjectLayout(R.layout.activity_search_contant)
public class SearchContantActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.et_search)
    EditText et_search;

    @InjectView(R.id.list_contant)
    ListView list_contant;

    InviteContantAdapter adapter;


    private ArrayList<ContactListInfoModel> contactListValue = new ArrayList<>();
    private List<ContactListInfoModel> contactValue = new ArrayList<>();

    Thread thread;

    private static  MyHandler handler;

    public static class MyHandler extends Handler{

        private WeakReference<SearchContantActivity> mContext;

        public MyHandler(SearchContantActivity activity){
            mContext=new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if(msg.what==0){
                SearchContantActivity activity=mContext.get();
                if(activity!=null){
                    activity.adapter.notifyDataSetChanged();
                }
            }
            super.handleMessage(msg);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_left.setOnClickListener(this);
        contactListValue= (ArrayList<ContactListInfoModel>) ACache.get(this).getAsObject("contactList");
        adapter = new InviteContantAdapter(this, contactValue);
        list_contant.setAdapter(adapter);
        handler=new MyHandler(this);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                contactValue.clear();
                adapter.notifyDataSetChanged();
                if(thread!=null&&thread.isAlive()){
                    thread.interrupt();
                    thread=null;
                }
                final String str=s.toString();
                thread=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (str.length() != 0) {
                            List<ContactListInfoModel> models=contactValue;
                            for (int i = 0,size=contactListValue.size(); i < size; i++) {
                                ContactListInfoModel contactListInfoModel = contactListValue.get(i);
                                String py = getPinYin(contactListInfoModel.getUserName());
                                if (py.contains(str)) {
                                    if (!models.contains(contactListInfoModel)) {
                                        models.add(contactListInfoModel);
                                    }
                                }
                                if (contactListInfoModel.getUserName().contains(str)) {
                                    if (!models.contains(contactListInfoModel)) {
                                        models.add(contactListInfoModel);
                                    }
                                }
                                if (contactListInfoModel.getMobile().contains(str)) {
                                    if (!models.contains(contactListInfoModel)) {
                                        models.add(contactListInfoModel);
                                    }
                                }
                            }
                            if(handler!=null){
                                handler.sendEmptyMessage(0);
                            }
                        }
                    }
                });
                thread.start();
            }
        });

    }

    public static String getPinYin(String input) {
        ArrayList<HanziToPinyin.Token> tokens = HanziToPinyin.getInstance().get(input);
        StringBuilder sb = new StringBuilder();
        if (tokens != null && tokens.size() > 0) {
            for (HanziToPinyin.Token token : tokens) {
                if (token.type == HanziToPinyin.Token.PINYIN) {
                    sb.append(token.target);
                } else {
                    sb.append(token.source);
                }
            }
        }
        return sb.toString().toLowerCase();
    }

    @Override
    protected void initViews() {
        tv_title.setText("搜索联系人");

    }

    @Override
    protected void initDatas() {
        et_search.setFocusable(true);
        et_search.setFocusableInTouchMode(true);
        et_search.requestFocus();
        et_search.findFocus();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;

        }
    }

    /**
     * 点击屏幕隐藏软键盘
     **/
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (SoftInputUtil.isShouldHideKeyboard(v, ev)) {

                SoftInputUtil.hideKeyboard(v.getWindowToken(), this);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    class SearchTask extends AsyncTask<String,Integer,Void>{

        @Override
        protected Void doInBackground(String... params) {
            String str=params[0];

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

        }

        @Override
        protected void onCancelled() {

        }
    }

    @Override
    protected void onDestroy() {
        handler.removeMessages(0);
        super.onDestroy();
    }
}
