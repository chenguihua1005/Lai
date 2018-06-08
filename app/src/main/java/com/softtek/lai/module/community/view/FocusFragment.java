package com.softtek.lai.module.community.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.photowall.net.PhotoWallService;
import com.softtek.lai.module.community.adapter.HealthyCommunityAdapter;
import com.softtek.lai.module.community.adapter.HealthyCommunityFocusAdapter;
import com.softtek.lai.module.community.eventModel.DeleteFocusEvent;
import com.softtek.lai.module.community.eventModel.DeleteRecommedEvent;
import com.softtek.lai.module.community.eventModel.FocusEvent;
import com.softtek.lai.module.community.eventModel.FocusReload;
import com.softtek.lai.module.community.eventModel.Where;
import com.softtek.lai.module.community.eventModel.ZanEvent;
import com.softtek.lai.module.community.model.Comment;
import com.softtek.lai.module.community.model.DoZan;
import com.softtek.lai.module.community.model.DynamicModel;
import com.softtek.lai.module.community.model.HealthyRecommendModel;
import com.softtek.lai.module.community.net.CommunityService;
import com.softtek.lai.module.community.presenter.CommunityManager;
import com.softtek.lai.module.community.presenter.OpenComment;
import com.softtek.lai.module.community.presenter.SendCommend;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.RequestCallback;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

import static android.view.View.GONE;

/**
 * Created by jerry.guan on 4/11/2016.
 *
 */
@InjectLayout(R.layout.fragment_mine_healthy)
public class FocusFragment extends LazyBaseFragment implements PullToRefreshBase.OnRefreshListener2<ListView>,CommunityManager.CommunityManagerCallback<HealthyRecommendModel>,View.OnClickListener
,HealthyCommunityAdapter.OperationCall,SendCommend {

//    @InjectView(R.id.iv_left)
//    ImageView iv_left;
//    @InjectView(R.id.tv_title)
//    TextView tv_title;

    @InjectView(R.id.ptrlv)
    PullToRefreshListView ptrlv;
    @InjectView(R.id.lin_is_vr)
    LinearLayout lin_is_vr;
    @InjectView(R.id.but_login)
    Button but_login;
    @InjectView(R.id.empty)
    FrameLayout empty;

//    @InjectView(R.id.toolbar)
//    Toolbar toolbar;

    private CommunityManager community;
    private HealthyCommunityFocusAdapter adapter;
    private List<DynamicModel> communityModels=new ArrayList<>();
    int pageIndex=1;
    int totalPage=0;
    boolean isLogin=false;
    boolean hasFocus=false;
    private OpenComment openComment;

    public static final String FOCUSFRAGMENT="focusFragment";

    @Override
    protected void onVisible() {
        if(hasFocus){
            isPrepared=false;
        }
        super.onVisible();
    }

    @Override
    protected void lazyLoad() {
        if(isLogin){
            pageIndex=1;
            community.getHealthyFocus(1);
        }
    }
    public static FocusFragment getInstance(OpenComment openComment){
        FocusFragment fragment=new FocusFragment();
        fragment.setOpenComment(openComment);
        return fragment;
    }

    public void setOpenComment(OpenComment openComment) {
        this.openComment = openComment;
    }

    @Override
    protected void initViews() {
//        if(toolbar!=null){
//            LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) toolbar.getLayoutParams();
//            params.topMargin= DisplayUtil.getStatusHeight(getActivity());
//            toolbar.setLayoutParams(params);
//        }
//        tv_title.setText("关注");
//        iv_left.setVisibility(View.INVISIBLE);
        EventBus.getDefault().register(this);
        but_login.setOnClickListener(this);
        ptrlv.setMode(PullToRefreshBase.Mode.BOTH);
        ptrlv.setOnRefreshListener(this);
        ptrlv.setEmptyView(empty);
        ILoadingLayout startLabelse = ptrlv.getLoadingLayoutProxy(true,false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setRefreshingLabel("正在刷新数据");// 刷新时
        startLabelse.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        ILoadingLayout endLabelsr = ptrlv.getLoadingLayoutProxy(false, true);
        endLabelsr.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
//        endLabelsr.setLastUpdatedLabel("正在刷新数据");// 刷新时
        endLabelsr.setRefreshingLabel("正在刷新数据");
        endLabelsr.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void refreshList(DeleteFocusEvent event){
        Iterator<DynamicModel> iterator=communityModels.iterator();
        while (iterator.hasNext()){
            DynamicModel model=iterator.next();
            if (model.getAccountId()==Long.parseLong(event.getAccountId())) {
                iterator.remove();
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Subscribe
    public void refreshListDelete(DeleteRecommedEvent event) {
        if(event.getWhere()!= Where.FOCUS_LIST){
            Iterator<DynamicModel> iterator=communityModels.iterator();
            while (iterator.hasNext()){
                DynamicModel model=iterator.next();
                if (model.getDynamicId().equals(event.getDynamicId())) {
                    iterator.remove();
                    break;
                }
            }
            adapter.notifyDataSetChanged();

        }
    }

    @Subscribe
    public void refreshListZan(ZanEvent event){
        if(event.getWhere()!= Where.FOCUS_LIST){
            for (DynamicModel model:communityModels){
                if(model.getDynamicId().equals(event.getDynamicId())){
                    model.setIsPraise(1);
                    model.setPraiseNum(model.getPraiseNum() + 1);
                    UserInfoModel infoModel = UserInfoModel.getInstance();
                    model.getUsernameSet().add(infoModel.getUser().getNickname());
                    adapter.notifyDataSetChanged();
                    break;
                }
            }
        }
    }

    @Subscribe
    public void refreshList(FocusEvent event) {
        for (DynamicModel model : communityModels) {
            if (model.getAccountId() == Integer.parseInt(event.getAccountId())) {
                model.setIsFocus(event.getFocusStatus());
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onReload(FocusReload reload){
        if(communityModels.isEmpty()){
            hasFocus=true;
        }else {
            hasFocus=false;
        }
    }

    @Override
    protected void initDatas() {
        service = ZillaApi.NormalRestAdapter.create(CommunityService.class);
        community=new CommunityManager(this);
        //加载数据适配器
        adapter=new HealthyCommunityFocusAdapter(this,new Object(),getContext(),communityModels);
        ptrlv.getRefreshableView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (openComment != null) {
                    openComment.hiden();
                }
                return false;
            }
        });
        ptrlv.setAdapter(adapter);
        String token=UserInfoModel.getInstance().getToken();
        //判断token是否为空
        if(StringUtils.isEmpty(token)){
            //token为空，游客模式显示立即登陆页面
            isLogin=false;
            lin_is_vr.setVisibility(View.VISIBLE);
            ptrlv.setVisibility(View.GONE);
        }else{
            //token不为空，非游客模式，隐藏立即登陆页面
            isLogin=true;
            lin_is_vr.setVisibility(View.GONE);
            ptrlv.setVisibility(View.VISIBLE);
        }
        //自动加载
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        //获取健康我的动态
        Log.i("加载健康圈我的动态");
        pageIndex=1;
        community.getHealthyFocus(1);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex++;
        if(pageIndex<=totalPage){
            community.getHealthyFocus(pageIndex);
        }else{
            pageIndex--;
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(ptrlv!=null){
                        ptrlv.onRefreshComplete();
                    }

                }
            },300);
        }
    }


    @Override
    public void getMineDynamic(HealthyRecommendModel model) {
        try {
            hasFocus=false;
            ptrlv.onRefreshComplete();
            if(model==null){
                pageIndex=--pageIndex<1?1:pageIndex;
                return;
            }
            if(model.getTotalPage()==null&&model.getDynamiclist()==null){
                pageIndex=--pageIndex<1?1:pageIndex;
                return;
            }
            totalPage=Integer.parseInt(model.getTotalPage());
            List<DynamicModel> models=model.getDynamiclist();

            if(pageIndex==1){
                this.communityModels.clear();
            }else {
                if(models==null||models.isEmpty()){
                    pageIndex=--pageIndex<1?1:pageIndex;
                    return;
                }
            }

            this.communityModels.addAll(models);
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.but_login:
                Intent toLoginIntent=new Intent(getContext(), LoginActivity.class);
                toLoginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                toLoginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(toLoginIntent);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==-1){
        }
    }
    private CommunityService service;
    @Override
    public PopupWindow doOperation(final DynamicModel data, final int itemHeight, final int position) {
        //弹出popwindow
        final PopupWindow popupWindow = new PopupWindow(getContext());
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(DisplayUtil.dip2px(getContext(),30));
        popupWindow.setAnimationStyle(R.style.operation_anim_style);
        popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.opteration_drawable));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        final View contentView = LayoutInflater.from(getContext()).inflate(R.layout.pop_operator, null);
        final TextView tv_zan = (TextView) contentView.findViewById(R.id.tv_oper_zan);
        //点击点赞按钮
        tv_zan.setEnabled(data.getIsPraise()!=1);
        if(data.getIsPraise() ==1){
            tv_zan.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getContext(),R.drawable.zan_has),null,null,null);
        }
        tv_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                final UserInfoModel infoModel = UserInfoModel.getInstance();
                data.setPraiseNum(data.getPraiseNum() + 1);
                data.setIsPraise(1);
                List<String> praise=data.getUsernameSet();
                praise.add(infoModel.getUser().getNickname());
                data.setUsernameSet(praise);
                //向服务器提交
                String token = infoModel.getToken();

                service.clickLike(token, new DoZan(Long.parseLong(infoModel.getUser().getUserid()), data.getDynamicId()),
                        new RequestCallback<ResponseData>() {
                            @Override
                            public void success(ResponseData responseData, Response response) {
                                int status = responseData.getStatus();
                                if (200 == status) {
                                    EventBus.getDefault().post(new ZanEvent(data.getDynamicId(),true,Where.FOCUS_LIST));
                                    adapter.notifyDataSetChanged();
                                } else {
                                    Util.toastMsg(responseData.getMsg());
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                super.failure(error);
                                int priase = data.getPraiseNum() - 1 < 0 ? 0 : data.getPraiseNum() - 1;
                                data.setPraiseNum(priase);
                                data.setIsPraise(0);
                                List<String> praise=data.getUsernameSet();
                                praise.remove(praise.size()-1);
                                data.setUsernameSet(praise);
                                adapter.notifyDataSetChanged();
                            }
                        });

            }


        });
        TextView tv_comment = (TextView) contentView.findViewById(R.id.tv_oper_comment);
        //点击评论按钮
        tv_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                if(openComment!=null){
                    openComment.doOpen(position,itemHeight,FOCUSFRAGMENT);
                }
            }
        });
        TextView tv_jubao = (TextView) contentView.findViewById(R.id.tv_oper_jubao);
        //点击举报按钮
        tv_jubao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        TextView tv_delete = (TextView) contentView.findViewById(R.id.tv_oper_delete);
        //点击删除按钮
        if(data.getAccountId() == UserInfoModel.getInstance().getUserId()){
            tv_delete.setVisibility(View.VISIBLE);
            tv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                    new AlertDialog.Builder(getContext()).setTitle("温馨提示").setMessage("确定删除吗？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    service.deleteHealth(UserInfoModel.getInstance().getToken(),
                                            data.getDynamicId(),
                                            new RequestCallback<ResponseData>() {
                                                @Override
                                                public void success(ResponseData responseData, Response response) {
                                                    try {
                                                        if (responseData.getStatus() == 200) {
                                                            EventBus.getDefault().post(new DeleteRecommedEvent(data.getDynamicId(), Where.FOCUS_LIST));
                                                            communityModels.remove(data);
                                                            adapter.notifyDataSetChanged();
                                                        }
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            });
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).create().show();
                }
            });
        }else {
            tv_delete.setVisibility(GONE);
        }
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupWindow.setContentView(contentView);
        return popupWindow;
    }

    @Override
    public void doScroll(int index,int itemHeight,int inputY) {
        int[] position = new int[2];
        ptrlv.getLocationOnScreen(position);
        //用弹出软件盘输入框在屏幕中的y值减去listView的顶部在屏幕中的Y值就是listView的剩余可显示高度。
        int emptyHeight=inputY - position[1];
        if (emptyHeight>=itemHeight){
            //如果可显示高度大于整个item的高度则只需移动这个item到第一个区域即可
            ptrlv.getRefreshableView().setSelectionFromTop(index + 1,0);
        }else {
            //把被软件盘遮住的部分显示出来
            ptrlv.getRefreshableView().setSelectionFromTop(index + 1,(inputY - position[1])-itemHeight);
        }
    }

    @Override
    public void doSend(int  position,Comment comment) {
        DynamicModel model=communityModels.get(position);
        model.getCommendsList().add(comment);
        adapter.notifyDataSetChanged();
        ZillaApi.NormalRestAdapter.create(PhotoWallService.class)
                .commitComment(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(),
                        model.getDynamicId(), comment.Comment, new RequestCallback<ResponseData>() {
                            @Override
                            public void success(ResponseData responseData, Response response) {

                            }
                        });
    }
}
