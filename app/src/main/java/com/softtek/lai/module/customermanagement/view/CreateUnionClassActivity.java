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
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.customermanagement.adapter.UnionClassRecyclerViewAdapter;
import com.softtek.lai.module.customermanagement.model.UnionClassModel;
import com.softtek.lai.module.customermanagement.service.ClubService;
import com.softtek.lai.module.customermanagement.service.GymClubService;
import com.softtek.lai.utils.RequestCallback;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

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
    private List<UnionClassModel.ItemsBean> unionClassModelList = new ArrayList<>();
    GymClubService service;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        service = ZillaApi.create(GymClubService.class);
        setContentView(R.layout.activity_create_union_class);
        initView();
        initData("");
    }

    private void initData(final String className){
        service.getListOfClassJointly(UserInfoModel.getInstance().getToken(), className, 1, 999, new RequestCallback<ResponseData<UnionClassModel>>() {
            @Override
            public void success(ResponseData<UnionClassModel> unionData, Response response) {
                if (unionData.getStatus() == 200){
                    if (unionData.getData().getItems().size() < 1 && !className.equals("")){
                        Toast.makeText(CreateUnionClassActivity.this,"搜索不到相应班级,请重新输入后再试",Toast.LENGTH_SHORT).show();
                    }else {
                        unionClassModelList.addAll(unionData.getData().getItems());
                        adapter.notifyDataSetChanged();
                    }
                }else {
                    Toast.makeText(CreateUnionClassActivity.this,unionData.getMsg(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
//                super.failure(error);
                dealNetError(error);
            }
        });
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
                service.inviteClassJointly(UserInfoModel.getInstance().getToken(), unionClassModelList.get(position).getClassId(), new RequestCallback<ResponseData>() {
                    @Override
                    public void success(ResponseData responseData, Response response) {
                        Toast.makeText(CreateUnionClassActivity.this,responseData.getMsg(),Toast.LENGTH_SHORT).show();
                    }
                });
                ((TextView) view).setText("待处理");
                view.setBackground(getResources().getDrawable(R.drawable.bg_invite_club_clicked));
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
        adapter.setClickable(true);
        if (mSearch.getText().toString().trim().equals("")) {
            Toast.makeText(this, "班级字段不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        initData(mSearch.getText().toString().trim());
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
