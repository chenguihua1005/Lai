package com.softtek.lai.module.personalPK.view;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.personalPK.adapter.PKListAdapter;
import com.softtek.lai.module.personalPK.model.PKListModel;
import com.softtek.lai.module.personalPK.presenter.PKListManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * 莱运动全国挑战赛列表
 */
@InjectLayout(R.layout.activity_pklist)
public class PKListActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener,PullToRefreshBase.OnRefreshListener2<ListView>{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.iv_email)
    ImageView iv_email;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    @InjectView(R.id.img_mo_message)
    ImageView img_mo_message;

    @InjectView(R.id.ptrlv)
    PullToRefreshListView ptrlv;

    private PKListAdapter adapter;
    private List<PKListModel> models=new ArrayList<>();
    int pageIndex=1;
    int totalPage;
    private PKListManager manager;

    @Override
    protected void initViews() {
        tv_title.setText("PK挑战列表");
        iv_email.setBackgroundResource(R.drawable.pk_list_mine);
        ll_left.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        ptrlv.setOnItemClickListener(this);
        ptrlv.setMode(PullToRefreshBase.Mode.BOTH);
        ptrlv.setOnRefreshListener(this);
        ptrlv.setEmptyView(img_mo_message);
    }

    @Override
    protected void initDatas() {
        manager=new PKListManager();
        adapter=new PKListAdapter(this,models);
        ptrlv.setAdapter(adapter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrlv.setRefreshing();
            }
        }, 300);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
            case R.id.fl_right:
                //我的挑战
                startActivity(new Intent(this,PKListMineActivity.class));
                break;
        }
    }

    private static final int PKLIST_JUMP=1;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //点击列表跳转至详情
        PKListModel model=models.get(position-1);
        Intent intent=new Intent(this,PKDetailActivity.class);
        intent.putExtra("pkmodel",model);
        intent.putExtra("position",position-1);
        startActivityForResult(intent, PKLIST_JUMP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==PKLIST_JUMP){
                int position=data.getIntExtra("position", -1);
                if(position!=-1){
                    PKListModel model=models.get(position);
                    PKListModel returnModel=data.getParcelableExtra("pkmodel");
                    model.setChP(returnModel.getChP());
                    model.setBChp(returnModel.getBChp());
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex=1;
        //刷新数据
        manager.getPKList(this, pageIndex);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex++;
        if(pageIndex<=totalPage){
            //请求更多
            manager.getPKList(this,pageIndex);
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ptrlv.onRefreshComplete();
                }
            },200);
        }
    }

    public void getModels(ResponseData<List<PKListModel>> model){
        ptrlv.onRefreshComplete();
        if(model==null){
            pageIndex=--pageIndex<1?1:pageIndex;
            return;
        }
        totalPage=model.getPageCount();
        List<PKListModel> list=model.getData();
        if(list==null||list.isEmpty()){
            pageIndex=--pageIndex<1?1:pageIndex;
            return;
        }
        if(pageIndex==1){
            this.models.clear();
        }
        this.models.addAll(list);
        adapter.notifyDataSetChanged();
    }
}
