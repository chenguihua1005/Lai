package com.softtek.lai.module.personalPK.view;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.personalPK.adapter.SearchAdapter;
import com.softtek.lai.module.personalPK.model.PKCreatModel;
import com.softtek.lai.module.personalPK.model.PKObjModel;
import com.softtek.lai.module.personalPK.presenter.PKListManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_search)
public class SearchActivity extends BaseActivity implements View.OnClickListener,TextWatcher,AdapterView.OnItemClickListener{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;

    @InjectView(R.id.et_search)
    EditText search;
    @InjectView(R.id.lv)
    ListView lv;
    List<PKObjModel> modelList=new ArrayList<>();
    SearchAdapter adapter;
    PKListManager manager;

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        search.addTextChangedListener(this);
        tv_title.setText("选择PK挑战对手");
        tv_right.setText("搜索");
        fl_right.setEnabled(false);
        lv.setOnItemClickListener(this);
    }

    @Override
    protected void initDatas() {
        manager=new PKListManager();
        adapter=new SearchAdapter(this,modelList);
        lv.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
            case R.id.fl_right:
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                dialogShow("搜索中...");
                manager.searchPKObj(this,search.getText().toString());
                break;

        }
    }

    public void loadData(List<PKObjModel> models){
        dialogDissmiss();

        if(models==null||models.isEmpty()){
            return;
        }
        modelList.clear();
        modelList.addAll(models);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(s.length()>0){
            fl_right.setEnabled(true);
        }else{
            fl_right.setEnabled(false);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PKObjModel model=modelList.get(position);
        PKCreatModel creatModel=getIntent().getParcelableExtra("pkmodel");
        creatModel.setBeUserName(model.getUserName());
        creatModel.setBeChallenged(model.getRGId());
        creatModel.setBeUserPhoto(model.getPhoto());
    }
}
