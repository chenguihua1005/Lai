package com.softtek.lai.module.tips.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.snowdream.android.util.Log;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.tips.adapter.VideoAdapter;
import com.softtek.lai.module.tips.model.AskHealthyModel;
import com.softtek.lai.module.tips.model.AskHealthyResponseModel;
import com.softtek.lai.module.tips.presenter.VideoManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jerry.guan on 4/27/2016.
 */
@InjectLayout(R.layout.fragment_healthy_ask)
public class VideoFragment extends BaseFragment implements
        PullToRefreshBase.OnRefreshListener2<ListView>,VideoManager.VideoManagerCallback,AdapterView.OnItemClickListener{

    @InjectView(R.id.ptrlv)
    PullToRefreshListView ptrlv;

    private List<AskHealthyModel> modelList=new ArrayList<>();
    private VideoManager manager;
    private VideoAdapter adapter;
    private int totalPage=0;
    private int pageIndex=1;

    @Override
    protected void initViews() {
        ptrlv.setOnRefreshListener(this);
        ptrlv.setMode(PullToRefreshBase.Mode.BOTH);
        ptrlv.setOnItemClickListener(this);
    }

    @Override
    protected void initDatas() {
        manager=new VideoManager(this);
        adapter=new VideoAdapter(modelList,getContext());
        ptrlv.setAdapter(adapter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(ptrlv!=null)
                    ptrlv.setRefreshing();
            }
        },500);

    }



    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex=1;
        manager.getVideoList(1);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex++;
        if(pageIndex<=totalPage){
            manager.getVideoList(pageIndex);
        }else{
            pageIndex--;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ptrlv.onRefreshComplete();
                }
            }, 200);
        }
    }

    @Override
    public void getHealthyList(AskHealthyResponseModel model) {
        ptrlv.onRefreshComplete();
        if(model==null||(model.getTotalPage()==null||model.getTipsList()==null)){
            pageIndex=--pageIndex<1?1:pageIndex;
            return;
        }
        totalPage=Integer.parseInt(model.getTotalPage());
        if(model.getTipsList()==null||model.getTipsList().isEmpty()){
            pageIndex=--pageIndex<1?1:pageIndex;
            return;
        }
        if(pageIndex==1){
            modelList.clear();
        }
        modelList.addAll(model.getTipsList());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AskHealthyModel model=modelList.get(position-1);
        Uri uri=Uri.parse(AddressManager.get("photoHost")+model.getTips_Addr());
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri,"video/*");
        startActivity(intent);
    }
}
