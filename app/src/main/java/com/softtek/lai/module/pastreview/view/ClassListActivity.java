package com.softtek.lai.module.pastreview.view;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.pastreview.adapter.ClassListAdapter;
import com.softtek.lai.module.pastreview.model.ClassListModel;
import com.softtek.lai.module.pastreview.net.PCPastReview;
import com.softtek.lai.module.pastreview.presenter.ClassListManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_class_list)
public class ClassListActivity extends BaseActivity implements View.OnClickListener,ClassListManager.ClassListCallback,AdapterView.OnItemClickListener{
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.listview_classlist)
    ListView listview_classlist;
    @InjectView(R.id.im_nomessage)
    ImageView im_nomessage;
    @InjectView(R.id.re_back)
    RelativeLayout re_back;
    ClassListManager classListManager;
    String accountid;
    ClassListAdapter classListAdapter;
    List<ClassListModel>classListModelList=new ArrayList<ClassListModel>();

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        tv_title.setText("往期回顾");
        listview_classlist.setOnItemClickListener(this);
    }

    @Override
    protected void initDatas() {
        classListAdapter=new ClassListAdapter(this,classListModelList);
        listview_classlist.setAdapter(classListAdapter);
        accountid= UserInfoModel.getInstance().getUser().getUserid();
        classListManager=new ClassListManager(this);
        classListManager.doGetHistoryClassList(accountid);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ll_left:
//                finish();
//                startActivity(new Intent(this,PassPhotoActivity.class));
                startActivity(new Intent(this,HistoryStudentHonorActivity.class));
                break;
        }
    }

    @Override
    public void getClassList(List<ClassListModel> classListModels) {
        if (classListModels==null||classListModels.isEmpty())
        {
            re_back.setBackgroundColor(Color.parseColor("#ffffff"));
            listview_classlist.setVisibility(View.GONE);

        }
        else {
            im_nomessage.setVisibility(View.GONE);
            classListModelList.clear();
            classListModelList.addAll(classListModels);
            classListAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ClassListModel classListModel=classListModelList.get(position);
        Intent intent=new Intent(this, PcPastBaseDataActivity.class);
        intent.putExtra("classId",classListModel.getClassId());
        intent.putExtra("className",classListModel.getClassName());
        startActivity(intent);
    }
}
