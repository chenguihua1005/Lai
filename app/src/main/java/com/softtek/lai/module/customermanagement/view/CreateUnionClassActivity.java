package com.softtek.lai.module.customermanagement.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.softtek.lai.R;
import com.softtek.lai.module.customermanagement.adapter.UnionClassRecyclerViewAdapter;
import com.softtek.lai.module.customermanagement.model.UnionClassModel;
import com.softtek.lai.module.customermanagement.service.ClubService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jia.lu on 12/28/2017.
 */

public class CreateUnionClassActivity extends MakiBaseActivity implements View.OnClickListener{
    private EditText mSearch;
    private LinearLayout mBack;
    private TextView mTitle;
    private RecyclerView mRecyclerView;
    private String clubId;
    private UnionClassRecyclerViewAdapter adapter;
    private List<UnionClassModel> unionClassModelList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_union_class);
//        initData();
        initView();
    }

    private void initData(){
        UnionClassModel unionClassModel = new UnionClassModel();
        unionClassModel.setClubName("maki1");
        unionClassModel.setCreateTime("1111-11-11");
        unionClassModel.setType(1);
        unionClassModelList.add(unionClassModel);

        UnionClassModel unionClassModel1 = new UnionClassModel();
        unionClassModel1.setClubName("maki2");
        unionClassModel1.setCreateTime("2222-22-22");
        unionClassModel1.setType(2);
        unionClassModelList.add(unionClassModel1);
        for (int i = 0;i < 5;i++){
            UnionClassModel unionClassModel2 = new UnionClassModel();
            unionClassModel2.setClubName("maki0000");
            unionClassModel2.setCreateTime("2333-11-11");
            unionClassModel2.setType(0);
            unionClassModelList.add(unionClassModel2);
        }
        adapter.notifyDataSetChanged();
    }

    private void initView(){
        mBack = findViewById(R.id.ll_left);
        mTitle = findViewById(R.id.tv_title);
        mSearch = findViewById(R.id.edt_search);
        mRecyclerView = findViewById(R.id.rcv_content);
        mTitle.setText("联合开班");
        mBack.setOnClickListener(this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        adapter = new UnionClassRecyclerViewAdapter(unionClassModelList, this, new UnionClassRecyclerViewAdapter.InviteListener() {
            @Override
            public void onInviteClickListener(View view, int position) {
                ((TextView) view).setText("待处理");
                view.setBackground(getResources().getDrawable(R.drawable.bg_invite_club_clicked));
                Toast.makeText(CreateUnionClassActivity.this, "申请成功", Toast.LENGTH_SHORT).show();
                view.setEnabled(false);
            }
        });
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
        mSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    doSearch();
                    hintKeyboard();
                    return true;
                }
                return false;
            }
        });
    }

    private void doSearch(){
        unionClassModelList.clear();
        if (mSearch.getText().toString().equals("")) {
            Toast.makeText(this, "班级字段不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        initData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_left:
                finish();
                break;
        }
    }

    private void hintKeyboard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        if(imm.isActive()&&getCurrentFocus()!=null){
            if (getCurrentFocus().getWindowToken()!=null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
}
