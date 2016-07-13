package com.softtek.lai.module.pastreview.view;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.pastreview.adapter.MyPhotoListAdapter;
import com.softtek.lai.module.pastreview.model.MyPhotoListModel;
import com.softtek.lai.module.pastreview.presenter.MyPhotoListManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_pass_photo)
public class PassPhotoActivity extends BaseActivity implements View.OnClickListener, PullToRefreshBase.OnRefreshListener2<ListView>, AdapterView.OnItemClickListener, MyPhotoListManager.MyPhotoListCallback {
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.im_nomessage)
    ImageView im_nomessage;
    @InjectView(R.id.ptrlvpassclasslist)
    PullToRefreshListView ptrlvpassclasslist;
    int pageIndex = 0;
    MyPhotoListManager myPhotoListManager;
    String classId;
    String userId;
    String accountId;
    MyPhotoListAdapter myPhotoListAdapter;
    List<MyPhotoListModel> myPhotoListModelList = new ArrayList<MyPhotoListModel>();

    @Override
    protected void initViews() {
        tv_title.setText("我的相册");
        ll_left.setOnClickListener(this);
        ptrlvpassclasslist.setMode(PullToRefreshBase.Mode.BOTH);
        ptrlvpassclasslist.setOnItemClickListener(this);
        ptrlvpassclasslist.setOnRefreshListener(this);
    }

    @Override
    protected void initDatas() {
        Intent intent = getIntent();
        userId = intent.getLongExtra("userId",0) + "";
        classId = intent.getLongExtra("classId",0) + "";
        myPhotoListAdapter = new MyPhotoListAdapter(this, myPhotoListModelList);
        ptrlvpassclasslist.setAdapter(myPhotoListAdapter);
        myPhotoListManager = new MyPhotoListManager(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrlvpassclasslist.setRefreshing();
            }
        }, 100);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        String userId = UserInfoModel.getInstance().getUser().getUserid();
        pageIndex = 1;
        myPhotoListManager.doGetMyPhotoList(userId, "1", classId);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        String userId = UserInfoModel.getInstance().getUser().getUserid();
        pageIndex++;
        myPhotoListManager.doGetMyPhotoList(userId, pageIndex + "", classId);
    }


    @Override
    public void getMyPhotoList(MyPhotoListModel myPhotoListModels) {
        ptrlvpassclasslist.onRefreshComplete();
        try {
            if (myPhotoListModels == null) {
                pageIndex = --pageIndex < 1 ? 1 : pageIndex;
                return;
            }
            im_nomessage.setVisibility(View.GONE);

            MyPhotoListModel models = myPhotoListModels;

            if (myPhotoListModels == null) {
                pageIndex = --pageIndex < 1 ? 1 : pageIndex;
                return;
            }
            if (pageIndex == 1) {
                myPhotoListModelList.clear();
            }
            myPhotoListModelList.add(models);
            myPhotoListAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
