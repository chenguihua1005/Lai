package com.softtek.lai.module.community.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame3.photowall.PublishDyActivity;
import com.softtek.lai.module.bodygame3.photowall.net.PhotoWallService;
import com.softtek.lai.module.community.adapter.HealthyCommunityAdapter;
import com.softtek.lai.module.community.eventModel.DeleteRecommedEvent;
import com.softtek.lai.module.community.eventModel.FocusEvent;
import com.softtek.lai.module.community.eventModel.Where;
import com.softtek.lai.module.community.eventModel.ZanEvent;
import com.softtek.lai.module.community.model.Comment;
import com.softtek.lai.module.community.model.DoZan;
import com.softtek.lai.module.community.model.DynamicModel;
import com.softtek.lai.module.community.model.HealthyRecommendModel;
import com.softtek.lai.module.community.model.TopicInfo;
import com.softtek.lai.module.community.net.CommunityService;
import com.softtek.lai.module.community.presenter.OpenComment;
import com.softtek.lai.module.community.presenter.RecommentHealthyManager;
import com.softtek.lai.module.community.presenter.SendCommend;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.DragFloatActionButton;
import com.softtek.lai.widgets.SquareImageView;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

import static android.view.View.GONE;

/**
 * Created by jerry.guan on 4/11/2016.
 * 莱聚+动态
 */
@InjectLayout(R.layout.fragment_recommend_healthy)
public class DynamicFragment extends LazyBaseFragment implements PullToRefreshBase.OnRefreshListener2<ListView>,
        RecommentHealthyManager.RecommentHealthyManagerCallback, HealthyCommunityAdapter.OperationCall, SendCommend {

    @InjectView(R.id.iv_left)
    ImageView iv_left;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.ptrlv)
    PullToRefreshListView ptrlv;
    @InjectView(R.id.empty)
    FrameLayout empty;

    @InjectView(R.id.fab_sender)
    DragFloatActionButton fab_sender;

    @InjectView(R.id.siv_topic)
    SquareImageView siv_topic;
    @InjectView(R.id.tv_dynamic_num)
    TextView tv_dynamic_num;
    @InjectView(R.id.tv_hot_topic)
    TextView tv_hot_topic;
    @InjectView(R.id.rl_hot)
    RelativeLayout rl_hot;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    private RecommentHealthyManager community;
    private HealthyCommunityAdapter adapter;
    private List<DynamicModel> communityModels = new ArrayList<>();
    int pageIndex = 1;
    int totalPage = 0;
    private OpenComment openComment;

    private static final int OPEN_SENDER_REQUEST = 2;
    public static final String DYNAMIC="dynamic";


    @Override
    protected void lazyLoad() {
        pageIndex = 1;
        community.getRecommendDynamic(accountId, 1);
        service.getHotTopicInfo(new RequestCallback<ResponseData<TopicInfo>>() {
            @Override
            public void success(ResponseData<TopicInfo> data, Response response) {
                if (data.getStatus() == 200) {
                    final TopicInfo info = data.getData();
                    tv_dynamic_num.setText(String.valueOf(info.getDynamicNum()));
                    tv_dynamic_num.append("条动态");
                    if (!TextUtils.isEmpty(info.getTopicName())) {
                        tv_hot_topic.setText("#");
                        tv_hot_topic.append(info.getTopicName());
                        tv_hot_topic.append("#");
                    }
                    if (TextUtils.isEmpty(info.getTopicPhoto())) {
                        Picasso.with(getContext()).load(R.drawable.default_icon_square)
                                .placeholder(R.drawable.default_icon_square)
                                .into(siv_topic);
                    } else {
                        Picasso.with(getContext()).load(AddressManager.get("photoHost") + data.getData().getTopicPhoto())
                                .resize(DisplayUtil.dip2px(getContext(), 38), DisplayUtil.dip2px(getContext(), 38))
                                .centerCrop()
                                .error(R.drawable.default_icon_square).placeholder(R.drawable.default_icon_square)
                                .into(siv_topic);
                    }
                    rl_hot.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(getContext(),TopicDetailActivity.class);
                            intent.putExtra("topicId",info.getTopicType());
                            startActivity(intent);

                        }
                    });

                }
            }
        });
    }

    public static DynamicFragment getInstance(OpenComment openComment) {
        DynamicFragment fragment = new DynamicFragment();
        fragment.setOpenComment(openComment);
        return fragment;
    }

    public void setOpenComment(OpenComment openComment) {
        this.openComment = openComment;
    }

    @Override
    protected void initViews() {
        if(toolbar!=null){
            RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) toolbar.getLayoutParams();
            params.topMargin= DisplayUtil.getStatusHeight(getActivity());
            toolbar.setLayoutParams(params);
        }
        tv_title.setText("动态");
        iv_left.setVisibility(View.INVISIBLE);
        tv_right.setText("更多话题");
        EventBus.getDefault().register(this);
        ptrlv.setOnRefreshListener(this);
        ptrlv.setMode(PullToRefreshBase.Mode.BOTH);
        ptrlv.setEmptyView(empty);
        ILoadingLayout startLabelse = ptrlv.getLoadingLayoutProxy(true, false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setRefreshingLabel("正在刷新数据");// 刷新时
        startLabelse.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        ILoadingLayout endLabelsr = ptrlv.getLoadingLayoutProxy(false, true);
        endLabelsr.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
        endLabelsr.setRefreshingLabel("正在刷新数据");
        endLabelsr.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        if(UserInfoModel.getInstance().isVr()){
            fab_sender.setVisibility(GONE);
        }else {
            fab_sender.setVisibility(View.VISIBLE);
        }

    }

    long accountId = 0;

    @Override
    protected void initDatas() {
        service = ZillaApi.NormalRestAdapter.create(CommunityService.class);
        community = new RecommentHealthyManager(this);
        String token = UserInfoModel.getInstance().getToken();
        if (StringUtils.isEmpty(token)) {
            accountId = -1;
        } else {
            accountId =UserInfoModel.getInstance().getUserId();
        }
        final Object tag = new Object();
        adapter = new HealthyCommunityAdapter(this, getContext(), communityModels, tag);
        ptrlv.setAdapter(adapter);
        ptrlv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_TOUCH_SCROLL || scrollState == SCROLL_STATE_FLING) {//正在滚动或手指快速一滑
                    Picasso.with(getContext()).pauseTag(tag);
                } else if (scrollState == SCROLL_STATE_IDLE) {//停止滑动
                    Picasso.with(getContext()).resumeTag(tag);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        ptrlv.getRefreshableView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (openComment != null) {
                    openComment.hiden();
                }
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.fab_sender)
    public void sendDynamicClick(View view) {
        Intent intent = new Intent(getContext(), PublishDyActivity.class);//跳转到发布动态界面
        startActivityForResult(intent, OPEN_SENDER_REQUEST);
    }

    @OnClick(R.id.fl_right)
    public void moreTopicClick(View view) {
        startActivity(new Intent(getContext(), TopicListActivity.class));
    }


    @Subscribe
    public void refreshList(FocusEvent event) {
//        if(event.getWhere()!=Where.DYNAMIC_LIST) {
            for (DynamicModel model : communityModels) {
                if (model.getAccountId() == Integer.parseInt(event.getAccountId())) {
                    model.setIsFocus(event.getFocusStatus());
                }
            }
            adapter.notifyDataSetChanged();
//        }
    }

    @Subscribe
    public void refreshListDelete(DeleteRecommedEvent event) {
        if(event.getWhere()!= Where.DYNAMIC_LIST){
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
    public void refreshListZan(ZanEvent event) {
        if (event.getWhere() != Where.DYNAMIC_LIST) {
            for (DynamicModel model : communityModels) {
                if (model.getDynamicId().equals(event.getDynamicId())) {
                    model.setIsPraise(Integer.parseInt(Constants.HAS_ZAN));
                    model.setPraiseNum(model.getPraiseNum() + 1);
                    UserInfoModel infoModel = UserInfoModel.getInstance();
                    model.getUsernameSet().add(infoModel.getUser().getNickname());
                    break;
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        //获取健康推荐动态
        pageIndex = 1;
        community.getRecommendDynamic(accountId, 1);
        service.getHotTopicInfo(new RequestCallback<ResponseData<TopicInfo>>() {
            @Override
            public void success(ResponseData<TopicInfo> data, Response response) {
                if (data.getStatus() == 200) {
                    final TopicInfo info = data.getData();
                    tv_dynamic_num.setText(String.valueOf(info.getDynamicNum()));
                    tv_dynamic_num.append("条动态");
                    if (!TextUtils.isEmpty(info.getTopicName())) {
                        tv_hot_topic.setText("#");
                        tv_hot_topic.append(info.getTopicName()==null?"":info.getTopicName());
                        tv_hot_topic.append("#");
                    }
                    if (TextUtils.isEmpty(info.getTopicPhoto())) {
                        Picasso.with(getContext()).load(R.drawable.default_icon_square)
                                .placeholder(R.drawable.default_icon_square)
                                .into(siv_topic);
                    } else {
                        Picasso.with(getContext()).load(AddressManager.get("photoHost") + data.getData().getTopicPhoto())
                                .resize(DisplayUtil.dip2px(getContext(), 38), DisplayUtil.dip2px(getContext(), 38))
                                .centerCrop()
                                .error(R.drawable.default_icon_square).placeholder(R.drawable.default_icon_square)
                                .into(siv_topic);
                    }
                    rl_hot.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(getContext(),TopicDetailActivity.class);
                            intent.putExtra("topicId",info.getTopicType());
                            startActivity(intent);

                        }
                    });

                }
            }
        });
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex++;
        if (pageIndex <= totalPage) {
            community.getRecommendDynamic(accountId, pageIndex);
        } else {
            pageIndex--;
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    ptrlv.onRefreshComplete();

                }
            }, 300);
        }
    }

    public void updateList() {
        if (ptrlv != null) {
            ptrlv.getRefreshableView().setSelection(0);
            pageIndex = 1;
            community.getRecommendDynamic(accountId, 1);
        }
    }

    @Override
    public void getRecommendDynamic(HealthyRecommendModel model) {
        try {
            ptrlv.onRefreshComplete();
            if (model == null) {
                pageIndex = --pageIndex < 1 ? 1 : pageIndex;
                return;
            }
            if (model.getTotalPage() == null || model.getDynamiclist() == null) {
                pageIndex = --pageIndex < 1 ? 1 : pageIndex;
                return;
            }
            totalPage = Integer.parseInt(model.getTotalPage());
            List<DynamicModel> models = model.getDynamiclist();
            if (models == null || models.isEmpty()) {
                pageIndex = --pageIndex < 1 ? 1 : pageIndex;
                return;
            }
            if (pageIndex == 1) {
                this.communityModels.clear();
            }
            this.communityModels.addAll(models);
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            if (requestCode == OPEN_SENDER_REQUEST) {
                updateList();
            }
        }
    }


    private CommunityService service;

    @Override
    public PopupWindow doOperation(final DynamicModel data, final int itemHeight, final int position) {
        //弹出popwindow
        final PopupWindow popupWindow = new PopupWindow(getContext());
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(DisplayUtil.dip2px(getContext(), 30));
        popupWindow.setAnimationStyle(R.style.operation_anim_style);
        popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.opteration_drawable));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        final View contentView = LayoutInflater.from(getContext()).inflate(R.layout.pop_operator, null);
        final TextView tv_zan = (TextView) contentView.findViewById(R.id.tv_oper_zan);
        //点击点赞按钮
        tv_zan.setEnabled(data.getIsPraise() != 1);
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
                List<String> praise = data.getUsernameSet();
                praise.add(infoModel.getUser().getNickname());
                data.setUsernameSet(praise);
                //向服务器提交
                String token = infoModel.getToken();
                EventBus.getDefault().post(new ZanEvent(data.getDynamicId(),true,Where.DYNAMIC_LIST));
                service.clickLike(token, new DoZan(Long.parseLong(infoModel.getUser().getUserid()), data.getDynamicId()),
                        new RequestCallback<ResponseData>() {
                            @Override
                            public void success(ResponseData responseData, Response response) {
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                super.failure(error);
                                int priase = data.getPraiseNum() - 1 < 0 ? 0 : data.getPraiseNum() - 1;
                                data.setPraiseNum(priase);
                                data.setIsPraise(0);
                                List<String> praise = data.getUsernameSet();
                                praise.remove(praise.size() - 1);
                                data.setUsernameSet(praise);
                                adapter.notifyDataSetChanged();
                            }
                        });
                adapter.notifyDataSetChanged();
            }


        });
        TextView tv_comment = (TextView) contentView.findViewById(R.id.tv_oper_comment);
        //点击评论按钮
        tv_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                if (openComment != null) {
                    openComment.doOpen(position, itemHeight, DYNAMIC);
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
        if (data.getAccountId() == UserInfoModel.getInstance().getUserId()) {
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
                                                            EventBus.getDefault().post(new DeleteRecommedEvent(data.getDynamicId(), Where.DYNAMIC_LIST));
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
        } else {
            tv_delete.setVisibility(GONE);
        }
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupWindow.setContentView(contentView);
        return popupWindow;
    }

    @Override
    public void doScroll(int index, int itemHeight, int inputY) {

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
    public void doSend(int position, Comment comment) {
        DynamicModel model = communityModels.get(position);
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
