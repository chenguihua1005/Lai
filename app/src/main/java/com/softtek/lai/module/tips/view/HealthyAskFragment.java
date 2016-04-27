package com.softtek.lai.module.tips.view;

import android.os.Handler;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.tips.adapter.AskHealthyAdapter;
import com.softtek.lai.module.tips.model.AskHealthyModel;
import com.softtek.lai.module.tips.model.AskHealthyResponseModel;
import com.softtek.lai.module.tips.presenter.AskHealthyManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jerry.guan on 4/27/2016.
 */
@InjectLayout(R.layout.fragment_healthy_ask)
public class HealthyAskFragment extends BaseFragment implements AskHealthyManager.AskHealthyManagerCallback,
        PullToRefreshBase.OnRefreshListener2<ListView>{

    @InjectView(R.id.ptrlv)
    PullToRefreshListView ptrlv;

    private List<AskHealthyModel> modelList=new ArrayList<>();
    private AskHealthyAdapter adapter;
    private AskHealthyManager manager;
    private int totalPage=0;
    private int pageIndex=1;

    @Override
    protected void initViews() {
        ptrlv.setOnRefreshListener(this);
    }

    @Override
    protected void initDatas() {
        manager=new AskHealthyManager(this);
        adapter=new AskHealthyAdapter(modelList,getContext());
        ptrlv.setAdapter(adapter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrlv.setRefreshing();
            }
        },500);

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
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex=1;
        manager.getAskHealthyList(pageIndex);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex++;
        if(pageIndex<=totalPage){
            manager.getAskHealthyList(pageIndex);
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
}
