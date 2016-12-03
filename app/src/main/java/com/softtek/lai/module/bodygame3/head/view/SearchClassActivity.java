package com.softtek.lai.module.bodygame3.head.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.head.model.ClasslistModel;
import com.softtek.lai.module.bodygame3.head.net.HeadService;
import com.softtek.lai.utils.RequestCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;
//查找班级
@InjectLayout(R.layout.activity_search_class)
public class SearchClassActivity extends BaseActivity implements View.OnClickListener ,AdapterView.OnItemClickListener{
    private String content_et;
    @InjectView(R.id.lv_class)
    ListView lv_class;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    ClasslistModel classlistModel;
    private List<ClasslistModel> classlistModels = new ArrayList<ClasslistModel>();
    EasyAdapter<ClasslistModel> adapter;

    @Override
    protected void initViews() {
        tv_title.setText("搜索结果");
        ll_left.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        Intent intent = getIntent();
        content_et = intent.getStringExtra("content");

        dialogShow("正在查找...");
        ZillaApi.NormalRestAdapter.create(HeadService.class).getclass(UserInfoModel.getInstance().getToken(),
                content_et, new RequestCallback<ResponseData<List<ClasslistModel>>>() {
                    @Override
                    public void success(ResponseData<List<ClasslistModel>> data, Response response) {
                        dialogDissmiss();
                        if (data.getStatus() == 200) {
                            classlistModels.clear();
                            classlistModels.addAll(data.getData());
                            if (classlistModels == null || classlistModels.isEmpty()) {
                                try {
                                    new AlertDialog.Builder(SearchClassActivity.this).setMessage("查询失败，无此班级").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    }).create().show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                adapter.notifyDataSetChanged();
                            }

                        }
                    }
                });


        adapter = new EasyAdapter<ClasslistModel>(this, classlistModels, R.layout.class_list) {
            @Override
            public void convert(ViewHolder holder, ClasslistModel data, int position) {
                TextView class_name = holder.getView(R.id.class_name);
                class_name.setText(data.getClassName());
                TextView class_code = holder.getView(R.id.class_code);
                class_code.setText(data.getClassCode());
            }

        };
        lv_class.setAdapter(adapter);
        lv_class.setOnItemClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        classlistModel=classlistModels.get(i);
        Intent intent=new Intent(this,ClassDetailActivity.class);
        intent.putExtra("ClasslistModel",classlistModel);
        startActivity(intent);
    }
}
