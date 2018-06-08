/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.view;


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
 * Created by jerry.guan on 3/22/2016.
 * 搜索通讯录学员
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

    private MyHandler handler;

    static class MyHandler extends Handler{
        private WeakReference<SearchContantActivity> mContext;
        public MyHandler(SearchContantActivity activity){
            this.mContext=new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if(msg.what==0){
                SearchContantActivity activity=mContext.get();
                if(activity!=null){
                    activity.adapter.notifyDataSetChanged();
                }
            }
        }
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
        ll_left.setOnClickListener(this);

    }

    DealSearch run;
    Thread thread;

    @Override
    protected void initDatas() {
        et_search.setFocusable(true);
        et_search.setFocusableInTouchMode(true);
        et_search.requestFocus();
        et_search.findFocus();
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
                String str=s.toString();
                if(thread!=null&&thread.isAlive()){
                    run.stop();
                    run=null;
                    thread=null;

                }
                run=new DealSearch(str);
                thread=new Thread(run);
                thread.start();
            }
        });

    }

    class DealSearch implements Runnable{

        private String str;
        private boolean stop;
        public DealSearch(String str){
            this.str=str;
        }

        @Override
        public void run() {
            if (str.length() != 0) {
                List<ContactListInfoModel> models=new ArrayList<>();
                for (int i = 0,size=contactListValue.size(); i < size; i++) {
                    if(stop){
                        return;
                    }
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
                contactValue.clear();
                contactValue.addAll(models);
                if(stop){
                    return;
                }
                handler.sendEmptyMessage(0);
            }
        }

        public void stop(){
            this.stop=true;
        }
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

    @Override
    protected void onDestroy() {
        if(thread!=null&&thread.isAlive()){
            run.stop();
            run=null;
            thread=null;

        }
        super.onDestroy();
    }
}
