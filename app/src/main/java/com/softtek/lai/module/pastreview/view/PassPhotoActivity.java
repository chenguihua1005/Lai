package com.softtek.lai.module.pastreview.view;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
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
    MyPhotoListAdapter myPhotoListAdapter;
    List<MyPhotoListModel> myPhotoListModelList = new ArrayList<MyPhotoListModel>();

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        ptrlvpassclasslist.setMode(PullToRefreshBase.Mode.BOTH);
        ptrlvpassclasslist.setOnItemClickListener(this);
        ptrlvpassclasslist.setOnRefreshListener(this);
        ILoadingLayout startLabelse = ptrlvpassclasslist.getLoadingLayoutProxy(true,false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setRefreshingLabel("正在刷新数据");// 刷新时
        startLabelse.setReleaseLabel("松开立即刷新中");// 下来达到一定距离时，显示的提示
        ILoadingLayout endLabelsr = ptrlvpassclasslist.getLoadingLayoutProxy(false, true);
        endLabelsr.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
//        endLabelsr.setLastUpdatedLabel("正在刷新数据");// 最后一次更新标签
        endLabelsr.setRefreshingLabel("正在刷新数据中");
        endLabelsr.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
    }

    @Override
    protected void initDatas() {
        Intent intent = getIntent();
        int type=intent.getIntExtra("type",0);
        if (type==1) {
            tv_title.setText("学员相册");
        }
        else {
            tv_title.setText("我的相册");
        }
        userId = intent.getLongExtra("userId",0) + "";
        classId = intent.getLongExtra("classId",0) + "";
        myPhotoListAdapter = new MyPhotoListAdapter(this, myPhotoListModelList);
        ptrlvpassclasslist.setAdapter(myPhotoListAdapter);
        myPhotoListManager = new MyPhotoListManager(this);
        myPhotoListManager.doGetMyPhotoList(userId,"1",classId);
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
        if (ptrlvpassclasslist != null) {
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
}
