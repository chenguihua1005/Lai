package com.softtek.lai.module.community.view;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame3.photowall.net.PhotoWallService;
import com.softtek.lai.module.community.adapter.DynamicDetailAdapter;
import com.softtek.lai.module.community.eventModel.DeleteRecommedEvent;
import com.softtek.lai.module.community.eventModel.FocusEvent;
import com.softtek.lai.module.community.eventModel.Where;
import com.softtek.lai.module.community.eventModel.ZanEvent;
import com.softtek.lai.module.community.model.Comment;
import com.softtek.lai.module.community.model.DoZan;
import com.softtek.lai.module.community.model.DynamicModel;
import com.softtek.lai.module.community.net.CommunityService;
import com.softtek.lai.module.community.presenter.OpenComment;
import com.softtek.lai.module.community.presenter.SendCommend;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.utils.SoftInputUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;

import static android.view.View.GONE;

@InjectLayout(R.layout.activity_dynamic_detail)
public class DynamicDetailActivity extends BaseActivity implements PullToRefreshBase.OnRefreshListener<ListView>
        , DynamicDetailAdapter.OperationCall, SendCommend
        , OpenComment, View.OnLayoutChangeListener {
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.ptrlv)
    PullToRefreshListView ptrlv;

    @InjectView(R.id.rl_send)
    RelativeLayout rl_send;
    @InjectView(R.id.et_input)
    EditText et_input;
    @InjectView(R.id.btn_send)
    Button btn_send;

    @InjectView(R.id.empty)
    RelativeLayout empty;

    private CommunityService service;
    private DynamicDetailAdapter adapter;
    private List<DynamicModel> communityModels = new ArrayList<>();

    String dynamicId;


    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        tv_title.setText("动态详情");
        ptrlv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        ptrlv.setOnRefreshListener(this);
        ILoadingLayout startLabelse = ptrlv.getLoadingLayoutProxy(true, false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setRefreshingLabel("正在刷新数据");// 刷新时
        startLabelse.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        et_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    btn_send.setEnabled(false);
                } else {
                    btn_send.setEnabled(true);
                }
            }
        });
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiden();
                Integer position = (Integer) v.getTag();
                String commentStr = et_input.getText().toString();
                et_input.setText("");
                Comment comment = new Comment();
                comment.Comment = commentStr;
                comment.CommentUserId = UserInfoModel.getInstance().getUserId();
                comment.CommentUserName = UserInfoModel.getInstance().getUser().getNickname();
                comment.isReply = 0;
                doSend(position, comment);
            }
        });
    }


    @Override
    protected void initDatas() {
        dynamicId = getIntent().getStringExtra("dynamicId");
        service = ZillaApi.NormalRestAdapter.create(CommunityService.class);
        //加载数据适配器
        adapter = new DynamicDetailAdapter(this, this, communityModels);
        ptrlv.getRefreshableView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hiden();
                return false;
            }
        });
        ptrlv.setAdapter(adapter);
        //自动加载
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrlv.setRefreshing();
            }
        }, 400);
    }

    @Override
    public void onRefresh(final PullToRefreshBase<ListView> refreshView) {
        //获取健康我的动态
        service.getHealthyDynamciDetail(UserInfoModel.getInstance().getUserId(), dynamicId,
                new RequestCallback<ResponseData<DynamicModel>>() {
                    @Override
                    public void success(ResponseData<DynamicModel> data, Response response) {
                        try {
                            refreshView.onRefreshComplete();
                            communityModels.clear();
                            communityModels.add(data.getData());
                            adapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        try {
                            refreshView.onRefreshComplete();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        super.failure(error);
                    }
                });
    }

    @Override
    public PopupWindow doOperation(final DynamicModel data, final int itemHeight, final int position) {
        //弹出popwindow
        final PopupWindow popupWindow = new PopupWindow(this);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(DisplayUtil.dip2px(this, 30));
        popupWindow.setAnimationStyle(R.style.operation_anim_style);
        popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.opteration_drawable));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        final View contentView = LayoutInflater.from(this).inflate(R.layout.pop_operator, null);
        final TextView tv_zan = (TextView) contentView.findViewById(R.id.tv_oper_zan);
        //点击点赞按钮
        tv_zan.setEnabled(data.getIsPraise() != 1);
        if (data.getIsPraise() == 1) {
            tv_zan.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(this, R.drawable.zan_has), null, null, null);
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
                EventBus.getDefault().post(new ZanEvent(dynamicId, true, Where.DYNAMIC_DETAIL));
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
                doOpen(position, itemHeight, null);

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
                    new AlertDialog.Builder(DynamicDetailActivity.this).setTitle("温馨提示").setMessage("确定删除吗？")
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
                                                            EventBus.getDefault().post(new DeleteRecommedEvent(data.getDynamicId(), Where.DYNAMIC_DETAIL));
                                                            communityModels.remove(data);
                                                            adapter.notifyDataSetChanged();
                                                            ptrlv.setVisibility(GONE);
                                                            empty.setVisibility(View.VISIBLE);
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
        int emptyHeight = inputY - position[1];
        if (emptyHeight >= itemHeight) {
            //如果可显示高度大于整个item的高度则只需移动这个item到第一个区域即可
            ptrlv.getRefreshableView().setSelectionFromTop(index + 1, 0);
        } else {
            //把被软件盘遮住的部分显示出来
            ptrlv.getRefreshableView().setSelectionFromTop(index + 1, (inputY - position[1]) - itemHeight);
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

    @Override
    public void doOpen(final int position, final int itemHeight, final String tag) {
        btn_send.setTag(position);
        rl_send.setVisibility(View.VISIBLE);
        et_input.setFocusable(true);
        et_input.setFocusableInTouchMode(true);
        et_input.requestFocus();
        et_input.postDelayed(new Runnable() {
            @Override
            public void run() {
                SoftInputUtil.showInputAsView(DynamicDetailActivity.this, et_input);
            }
        }, 400);
        rl_send.postDelayed(new Runnable() {
            @Override
            public void run() {
                int[] position2 = new int[2];
                rl_send.getLocationOnScreen(position2);
                doScroll(position, itemHeight, position2[1]);
            }
        }, 1000);
    }

    @Override
    public void hiden() {
        if (rl_send.getVisibility() == View.VISIBLE) {
            rl_send.setVisibility(View.INVISIBLE);
            SoftInputUtil.hidden(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        rl_send.addOnLayoutChangeListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        rl_send.removeOnLayoutChangeListener(this);
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (rl_send.getVisibility() == View.VISIBLE) {
            if (oldBottom - bottom < 0) {
                //键盘收起来
                rl_send.setVisibility(View.INVISIBLE);
            }

        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void refreshListZan(ZanEvent event) {
        if (event.getWhere() != Where.DYNAMIC_DETAIL && !communityModels.isEmpty()) {
            DynamicModel model = communityModels.get(0);
            if (model.getDynamicId().equals(event.getDynamicId())) {
                model.setIsPraise(Integer.parseInt(Constants.HAS_ZAN));
                model.setPraiseNum(model.getPraiseNum() + 1);
                UserInfoModel infoModel = UserInfoModel.getInstance();
                model.getUsernameSet().add(infoModel.getUser().getNickname());
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Subscribe
    public void refreshListDelete(DeleteRecommedEvent event) {
        if(event.getWhere()!=Where.DYNAMIC_DETAIL&&!communityModels.isEmpty()){
            DynamicModel model = communityModels.get(0);
            if (model.getDynamicId().equals(event.getDynamicId())) {
                communityModels.clear();
                adapter.notifyDataSetChanged();
                ptrlv.setVisibility(GONE);
                empty.setVisibility(View.VISIBLE);
            }

        }
    }

    @Subscribe
    public void refreshList(FocusEvent event) {
        if(event.getWhere()!=Where.DYNAMIC_DETAIL){
            for (DynamicModel model : communityModels) {
                if (model.getAccountId() == Integer.parseInt(event.getAccountId())) {
                    model.setIsFocus(event.getFocusStatus());
                }
            }
            adapter.notifyDataSetChanged();
        }
    }
}
