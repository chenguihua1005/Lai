package com.softtek.lai.module.bodygame2.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame2.adapter.SearchPCAdapter;
import com.softtek.lai.module.bodygame2.model.MemberResultModel;
import com.softtek.lai.module.bodygame2.model.SearchMemberModel;
import com.softtek.lai.module.bodygame2.net.BodyGameService;
import com.softtek.lai.utils.RequestCallback;

import java.util.List;

import butterknife.InjectView;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_search_pc)
public class SearchPcActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.lv)
    ListView lv;

    List<MemberResultModel> memberResultModels;

    @Override
    protected void initViews() {
        tv_title.setText("搜索结果");
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MemberResultModel model=memberResultModels.get(position);
                Intent jumpStudent=new Intent(SearchPcActivity.this,PersonalDataActivity.class);
                jumpStudent.putExtra("userId",Long.parseLong(model.getAccountId()));
                jumpStudent.putExtra("classId",Long.parseLong(model.getClassId()));
                startActivity(jumpStudent);
                finish();
            }
        });
    }

    @Override
    protected void initDatas() {
        String key=getIntent().getStringExtra("value");
        String token=UserInfoModel.getInstance().getToken();
        ZillaApi.NormalRestAdapter.create(BodyGameService.class).doSearchMember(token,
                UserInfoModel.getInstance().getUser().getUserid(),
                key, new RequestCallback<ResponseData<SearchMemberModel>>() {
                    @Override
                    public void success(ResponseData<SearchMemberModel> data, Response response) {
                        if(data.getStatus()==200){
                            memberResultModels=data.getData().getResult();
                            if(memberResultModels==null||memberResultModels.isEmpty()){
                                new AlertDialog.Builder(SearchPcActivity.this).setMessage("搜索无结果").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                }).create().show();
                            }else{
                                lv.setAdapter(new SearchPCAdapter(SearchPcActivity.this,memberResultModels));
                            }
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;

        }
    }
}
