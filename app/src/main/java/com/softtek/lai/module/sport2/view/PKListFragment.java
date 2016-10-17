package com.softtek.lai.module.sport2.view;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.personalPK.adapter.PKListAdapter;
import com.softtek.lai.module.personalPK.model.PKListModel;
import com.softtek.lai.module.personalPK.presenter.PKListManager;
import com.softtek.lai.module.personalPK.view.CreatePKActivity;
import com.softtek.lai.module.personalPK.view.PKDetailActivity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_pklist)
public class PKListFragment extends LazyBaseFragment implements View.OnClickListener,AdapterView.OnItemClickListener,PullToRefreshBase.OnRefreshListener2<ListView>{


    public PKListFragment() {
        // Required empty public constructor
    }

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.img_mo_message)
    ImageView img_mo_message;

    @InjectView(R.id.ptrlv)
    PullToRefreshListView ptrlv;

    @InjectView(R.id.tv_sendpk)
    TextView tv_sendpk;

    private PKListAdapter adapter;
    private List<PKListModel> models=new ArrayList<>();
    int pageIndex=1;
    int totalPage;
    private PKListManager manager;
    AnimatorSet set=new AnimatorSet();
    int lastVisibleItemPosition;//标记上次的显示位置
    @Override
    protected void initViews() {
        tv_title.setText("挑战");
        set.setDuration(500);
        tv_sendpk.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        ptrlv.setOnItemClickListener(this);
        ptrlv.setMode(PullToRefreshBase.Mode.BOTH);
        ptrlv.setOnRefreshListener(this);
        ptrlv.setEmptyView(img_mo_message);
        ILoadingLayout startLabelse = ptrlv.getLoadingLayoutProxy(true,false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setRefreshingLabel("正在刷新数据");// 刷新时
        startLabelse.setReleaseLabel("松开立即刷新中");// 下来达到一定距离时，显示的提示
        ILoadingLayout endLabelsr = ptrlv.getLoadingLayoutProxy(false, true);
        endLabelsr.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
        endLabelsr.setRefreshingLabel("正在加载数据中");
        endLabelsr.setReleaseLabel("松开立即加载");// 下来达到一定距离时，显示的提示
        ptrlv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(lastVisibleItemPosition<firstVisibleItem){
                    //上滑
                    hiden();
                }else if(lastVisibleItemPosition>firstVisibleItem){
                    //下滑
                    show();
                }
                lastVisibleItemPosition=firstVisibleItem;
            }
        });

    }

    private void hiden(){
        if(!set.isRunning()&&tv_sendpk.getVisibility()==View.VISIBLE){
            ObjectAnimator scaleX=ObjectAnimator.ofFloat(tv_sendpk,"scaleX",1.0f,0f);
            ObjectAnimator scaleY=ObjectAnimator.ofFloat(tv_sendpk,"scaleY",1.0f,0f);
            set.playTogether(scaleX,scaleY);
            set.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    tv_sendpk.setVisibility(View.GONE);
                }
            });
            set.start();
        }
    }
    private void show(){
        if(!set.isRunning()&&tv_sendpk.getVisibility()==View.GONE){
            tv_sendpk.setVisibility(View.VISIBLE);
            ObjectAnimator scaleX=ObjectAnimator.ofFloat(tv_sendpk,"scaleX",0f,1f);
            ObjectAnimator scaleY=ObjectAnimator.ofFloat(tv_sendpk,"scaleY",0f,1f);
            set.playTogether(scaleX,scaleY);
            set.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    tv_sendpk.setVisibility(View.VISIBLE);
                }
            });
            set.start();
        }
    }

    @Override
    protected void initDatas() {
        manager=new PKListManager();
        adapter=new PKListAdapter(getContext(),models);
        ptrlv.setAdapter(adapter);

    }

    @Override
    protected void lazyLoad() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
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
                getActivity().startActivity(new Intent(getContext(), HomeActviity.class));
                break;
            case R.id.tv_sendpk:
                startActivity(new Intent(getContext(), CreatePKActivity.class));
                break;
        }
    }

    private static final int PKLIST_JUMP=1;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //点击列表跳转至详情
        PKListModel model=models.get(position-1);
        Intent intent=new Intent(getContext(),PKDetailActivity.class);
        intent.putExtra("pkType", Constants.LIST_PK);
        intent.putExtra("position",position-1);
        intent.putExtra("pkId",model.getPKId());
        startActivityForResult(intent, PKLIST_JUMP);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK){
            if(requestCode==PKLIST_JUMP){
                int position=data.getIntExtra("position", -1);
                if(position!=-1){
                    boolean isCancle=data.getBooleanExtra("isCancel",false);
                    if(isCancle){
                        //做删除操作
                        models.remove(position);
                    }else {
                        PKListModel model = models.get(position);
                        String chp=data.getStringExtra("ChP");
                        String bchp=data.getStringExtra("BChP");
                        model.setChP(Integer.parseInt(StringUtils.isEmpty(chp)?"0":chp));
                        model.setBChp(Integer.parseInt(StringUtils.isEmpty(bchp)?"0":bchp));
                    }
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
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
