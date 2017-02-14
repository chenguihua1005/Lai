package com.softtek.lai.module.community.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.github.snowdream.android.util.Log;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.photowall.net.PhotoWallService;
import com.softtek.lai.module.community.adapter.CommentAdapter;
import com.softtek.lai.module.community.adapter.PhotosAdapter;
import com.softtek.lai.module.community.eventModel.RefreshRecommedEvent;
import com.softtek.lai.module.community.model.Comment;
import com.softtek.lai.module.community.model.DoZan;
import com.softtek.lai.module.community.model.DynamicModel;
import com.softtek.lai.module.community.model.HealthyRecommendModel;
import com.softtek.lai.module.community.model.TopicInfo;
import com.softtek.lai.module.community.net.CommunityService;
import com.softtek.lai.module.community.presenter.OpenComment;
import com.softtek.lai.module.community.presenter.SendCommend;
import com.softtek.lai.module.picture.view.PictureMoreActivity;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.utils.SoftInputUtil;
import com.softtek.lai.widgets.CircleImageView;
import com.softtek.lai.widgets.CustomGridView;
import com.softtek.lai.widgets.LinearLayoutManagerWrapper;
import com.softtek.lai.widgets.MyPullToListView;
import com.softtek.lai.widgets.TextViewExpandableAnimation;
import com.softtek.lai.widgets.YLListView;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

import static android.view.View.GONE;

@InjectLayout(R.layout.activity_topic_detail)
public class TopicDetailActivity extends BaseActivity implements OpenComment, SendCommend, View.OnLayoutChangeListener,
        PullToRefreshBase.OnRefreshListener2<YLListView>, YLListView.OnRefreshListener {

    @InjectView(R.id.ptrlv)
    MyPullToListView ptrlv;

    @InjectView(R.id.rl_content)
    RelativeLayout rl_content;
    @InjectView(R.id.rl_toolbar)
    RelativeLayout rl_toolbar;
    @InjectView(R.id.fl_bg)
    FrameLayout fl_bg;
    @InjectView(R.id.ll_title)
    LinearLayout ll_title;
    @InjectView(R.id.pb)
    ProgressBar pb;

    @InjectView(R.id.rl_send)
    RelativeLayout rl_send;
    @InjectView(R.id.et_input)
    EditText et_input;
    @InjectView(R.id.btn_send)
    Button btn_send;

    @InjectView(R.id.fat)
    FloatingActionButton fat;

    ImageView iv_banner;
    TextView tv_topticName;
    TextView tv_dynamicNum;
    TextView tv_explin;

    private List<DynamicModel> datas;
    private EasyAdapter<DynamicModel> adapter;
    private Object tag;
    String topicId;

    int pageIndex;
    int totalPage;

    @Override
    protected void initViews() {
        fl_bg.setAlpha(0);
        tintManager.setStatusBarTintColor(android.R.color.transparent);
        if (DisplayUtil.getSDKInt() >= Build.VERSION_CODES.KITKAT) {
            RelativeLayout.LayoutParams llTitleParams= (RelativeLayout.LayoutParams) ll_title.getLayoutParams();
            llTitleParams.topMargin=DisplayUtil.getStatusHeight(this);
            ll_title.setLayoutParams(llTitleParams);

            RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) fl_bg.getLayoutParams();
            params.height=DisplayUtil.getStatusHeight(this)+DisplayUtil.dip2px(this,50);
            fl_bg.setLayoutParams(params);

            ContentFrameLayout.LayoutParams params1= (ContentFrameLayout.LayoutParams) rl_content.getLayoutParams();
            params1.topMargin=-DisplayUtil.getStatusHeight(this);
            rl_content.setLayoutParams(params1);

        }
        ptrlv.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        ptrlv.setOnRefreshListener(this);
        ILoadingLayout endLabelsr = ptrlv.getLoadingLayoutProxy(false, true);
        endLabelsr.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
        endLabelsr.setRefreshingLabel("正在刷新数据");
        endLabelsr.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        ptrlv.getRefreshableView().setRefreshListener(this);
        //**************************
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
        tag = new Object();
        ptrlv.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_TOUCH_SCROLL || scrollState == SCROLL_STATE_FLING) {//正在滚动或手指快速一滑
                    Picasso.with(TopicDetailActivity.this).pauseTag(tag);
                } else if (scrollState == SCROLL_STATE_IDLE) {//停止滑动
                    Picasso.with(TopicDetailActivity.this).resumeTag(tag);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                View c = view.getChildAt(0);
                if (c == null) {
                    return;
                }
                int firstVisiblePosition = view.getFirstVisiblePosition();
                int top = c.getTop();
                int y= -top + firstVisiblePosition * c.getHeight() ;
                fl_bg.setAlpha(y*0.3f/1000.0f);
            }
        });
    }

    @Override
    protected void initDatas() {
        topicId = getIntent().getStringExtra("topicId");
        datas = new ArrayList<>();
        ptrlv.getRefreshableView().addHeaderView(createHeadView());
        adapter = new EasyAdapter<DynamicModel>(this, datas, R.layout.loss_weight_story_item) {
            @Override
            public void convert(final ViewHolder holder, final DynamicModel model, final int position) {
                TextView tv_name = holder.getView(R.id.tv_name);
                tv_name.setText(model.getUserName());
                final String content = model.getContent();
                SpannableStringBuilder builder = new SpannableStringBuilder(content);
                if (model.getIsTopic() == 1) {
                    /**
                     * 0  1 2 3 4 5   6 7  8  9 10
                     * 哈哈哈哈 # 金 彩 踢 馆 赛 #
                     */
                    String theme = "#" + model.getThemeName() + "#";
                    int from = 0;
                    int lastIndex = content.lastIndexOf("#");
                    do {
                        int firstIndex = content.indexOf("#", from);
                        int nextIndex = firstIndex + model.getThemeName().length() + 1;
                        if (nextIndex <= lastIndex) {
                            String sub = content.substring(firstIndex, nextIndex + 1);
                            if (sub.equals(theme)) {
                                builder.setSpan(new ClickableSpan() {
                                    @Override
                                    public void onClick(View widget) {
                                        Log.i("点击了主题");
                                        startActivity(new Intent(TopicDetailActivity.this, TopicDetailActivity.class));
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setColor(0xFFFFA202);
                                        ds.setUnderlineText(false);//去除超链接的下划线
                                    }
                                }, firstIndex, nextIndex + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                            }
                        }
                        from = nextIndex;
                    } while (from < lastIndex);
                }
                TextViewExpandableAnimation tv_content = holder.getView(R.id.tv_content);
                tv_content.getTextView().setHighlightColor(ContextCompat.getColor(TopicDetailActivity.this, android.R.color.transparent));
                tv_content.setText(builder);
                tv_content.getTextView().setMovementMethod(LinkMovementMethod.getInstance());
                tv_content.resetState(true);
                final long[] days = DateUtil.getInstance().getDaysForNow(model.getCreateDate());
                StringBuilder time = new StringBuilder();
                if (days[0] == 0) {//今天
                    if (days[3] < 60) {//小于1分钟
                        time.append("刚刚");
                    } else if (days[3] >= 60 && days[3] < 3600) {//>=一分钟小于一小时
                        time.append(days[2]);
                        time.append("分钟前");
                    } else {//大于一小时
                        time.append(days[1]);
                        time.append("小时前");
                    }
                } else if (days[0] == 1) {//昨天
                    time.append("昨天");
                } else {
                    time.append(days[0]);
                    time.append("天前");
                }
                TextView tv_date = holder.getView(R.id.tv_date);
                tv_date.setText(time);
                //关注
                CheckBox cb_focus = holder.getView(R.id.cb_focus);
                cb_focus.setVisibility(View.VISIBLE);
                //看一下是否被关注了
                cb_focus.setChecked(true);
                cb_focus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<DynamicModel> models = new ArrayList<>();
                        for (int i = 0, j = datas.size(); i < j; i++) {
                            DynamicModel item = datas.get(i);
                            if (item.getAccountId() == model.getAccountId()) {
                                models.add(item);
                            }
                        }
                        datas.removeAll(models);
                        notifyDataSetChanged();
                        EventBus.getDefault().post(new RefreshRecommedEvent(String.valueOf(model.getAccountId()), 0));
                        ZillaApi.NormalRestAdapter.create(CommunityService.class).cancleFocusAccount(UserInfoModel.getInstance().getToken(),
                                UserInfoModel.getInstance().getUserId(),
                                model.getAccountId(),
                                new RequestCallback<ResponseData>() {
                                    @Override
                                    public void success(ResponseData responseData, Response response) {
                                    }
                                });
                    }
                });
                //点赞
                //如果没有人点赞就隐藏点咱人姓名显示
                StringBuilder nameSet = new StringBuilder();
                for (int i = 0, j = model.getUsernameSet().size(); i < j; i++) {
                    nameSet.append(model.getUsernameSet().get(i));
                    if (i < j - 1) {
                        nameSet.append(",");
                    }
                }
                TextView tv_zan_name = holder.getView(R.id.tv_zan_name);
                if (0 != model.getPraiseNum()) {
                    tv_zan_name.setText(nameSet.toString());
                    tv_zan_name.setVisibility(View.VISIBLE);
                } else {
                    tv_zan_name.setVisibility(View.GONE);
                }
                //加载图片
                String path = AddressManager.get("photoHost");
                CircleImageView civ_header_image = holder.getView(R.id.civ_header_image);
                Picasso.with(TopicDetailActivity.this).load(path + model.getUserPhoto()).fit()
                        .placeholder(R.drawable.img_default).error(R.drawable.img_default).into(civ_header_image);
                civ_header_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent personal = new Intent(TopicDetailActivity.this, PersionalActivity.class);
                        personal.putExtra("isFocus", model.getIsFocus());
                        personal.putExtra("personalId", String.valueOf(model.getAccountId()));
                        personal.putExtra("personalName", model.getUserName());
                        startActivity(personal);
                    }
                });
                CustomGridView photos = holder.getView(R.id.photos);
                photos.setAdapter(new PhotosAdapter(model.getThumbnailPhotoList(), TopicDetailActivity.this, tag));
                photos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                        Intent in = new Intent(TopicDetailActivity.this, PictureMoreActivity.class);
                        in.putStringArrayListExtra("images", (ArrayList<String>) model.getPhotoList());
                        in.putExtra("position", position);
                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(v, v.getWidth() / 2, v.getHeight() / 2, 0, 0);
                        ActivityCompat.startActivity(TopicDetailActivity.this, in, optionsCompat.toBundle());
                    }
                });
                ImageView iv_operator = holder.getView(R.id.iv_operator);

                iv_operator.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RelativeLayout rl_item = holder.getView(R.id.rl_item);
                        PopupWindow popupWindow = doOperation(model, rl_item.getHeight(), position);
                        int width = popupWindow.getContentView().getMeasuredWidth();
                        int[] location = new int[2];
                        v.getLocationOnScreen(location);
                        popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0] - width, location[1]);
                    }
                });
                CommentAdapter adapter = new CommentAdapter(TopicDetailActivity.this, model.getCommendsList());
                RecyclerView rv_comment = holder.getView(R.id.rv_comment);
                rv_comment.setLayoutManager(new LinearLayoutManagerWrapper(TopicDetailActivity.this));
                rv_comment.setAdapter(adapter);
            }
        };
        ptrlv.setAdapter(adapter);
        onRefresh();
        fat.setOnClickListener(new View.OnClickListener() {
            int i=20;
            @Override
            public void onClick(View v) {
                ((ListView) ptrlv.getRefreshableView()).setSelectionFromTop(2,i);
                i+=20;
            }
        });
    }

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
        TextView tv_zan = (TextView) contentView.findViewById(R.id.tv_oper_zan);
        //点击点赞按钮
        tv_zan.setEnabled(data.getIsPraise() != 1);
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
                ZillaApi.NormalRestAdapter.create(CommunityService.class).clickLike(token, new DoZan(Long.parseLong(infoModel.getUser().getUserid()), data.getDynamicId()),
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
                    new AlertDialog.Builder(TopicDetailActivity.this).setTitle("温馨提示").setMessage("确定删除吗？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ZillaApi.NormalRestAdapter.create(CommunityService.class).deleteHealth(UserInfoModel.getInstance().getToken(),
                                            data.getDynamicId(),
                                            new RequestCallback<ResponseData>() {
                                                @Override
                                                public void success(ResponseData responseData, Response response) {
                                                    try {
                                                        if (responseData.getStatus() == 200) {
                                                            datas.remove(data);
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

    private View createHeadView() {
        //创建一个高度为162dp的relativeLayout容器
        RelativeLayout rl = new RelativeLayout(this);
        rl.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200,
                        getResources().getDisplayMetrics())));

        //添加一个imageView进去
        iv_banner = new ImageView(this);
        iv_banner.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv_banner.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.default_icon_rect));
        rl.addView(iv_banner);

        //添加一段描述性文本
        LinearLayout ll_text = new LinearLayout(this);
        ll_text.setOrientation(LinearLayout.VERTICAL);
        ll_text.setBackgroundColor(0xFFFAFAFA);
        RelativeLayout.LayoutParams llParams = new RelativeLayout
                .LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        llParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        ll_text.setLayoutParams(llParams);
        tv_explin = new TextView(this);
        tv_explin.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        tv_explin.setTextColor(0xFF333333);
        tv_explin.setGravity(Gravity.CENTER | Gravity.LEFT);
        tv_explin.setBackgroundColor(0xFFFFFFFF);
        tv_explin.setPadding((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14, getResources().getDisplayMetrics()),0,0,0);
        tv_explin.setHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, getResources().getDisplayMetrics()));
        ll_text.addView(tv_explin);

        //在画一条线
        View line = new View(this);
        LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));
        line.setLayoutParams(lineParams);
        ll_text.addView(line);
        rl.addView(ll_text);

        LinearLayout ll = new LinearLayout(this);
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        rlp.bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, getResources().getDisplayMetrics());
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        rlp.bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 56, getResources().getDisplayMetrics());
        rlp.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());
        ll.setLayoutParams(rlp);
        ll.setOrientation(LinearLayout.VERTICAL);
        tv_topticName = new TextView(this);
        tv_topticName.setTextColor(0xFFFFFFFF);
        tv_topticName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        LinearLayout.LayoutParams params1 = new LinearLayout
                .LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        tv_topticName.setLayoutParams(params1);

        tv_dynamicNum = new TextView(this);
        tv_dynamicNum.setTextColor(0xFFFFFFFF);
        tv_dynamicNum.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
        LinearLayout.LayoutParams params = new LinearLayout
                .LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, getResources().getDisplayMetrics());
        tv_dynamicNum.setLayoutParams(params);
        ll.addView(tv_topticName);
        ll.addView(tv_dynamicNum);
        rl.addView(ll);
        return rl;
    }

    @Override
    public void doOpen(final int position, final int itemHeight, String tag) {
        btn_send.setTag(position);
        rl_send.setVisibility(View.VISIBLE);
        et_input.setFocusable(true);
        et_input.setFocusableInTouchMode(true);
        et_input.requestFocus();
        et_input.postDelayed(new Runnable() {
            @Override
            public void run() {
                SoftInputUtil.showInputAsView(TopicDetailActivity.this, et_input);
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
    public void doScroll(int index, int itemHeight, int inputY) {
        Log.i("index="+index);
        int[] position = new int[2];
        ptrlv.getLocationOnScreen(position);
        //用弹出软件盘输入框在屏幕中的y值减去listView的顶部在屏幕中的Y值就是listView的剩余可显示高度。
        int emptyHeight = inputY - position[1];
        if (emptyHeight >= itemHeight) {
            //如果可显示高度大于整个item的高度则只需移动这个item到第一个区域即可
            ((ListView) ptrlv.getRefreshableView()).setSelectionFromTop(index + 2, 0);
        } else {
            //把被软件盘遮住的部分显示出来
            ((ListView) ptrlv.getRefreshableView()).setSelectionFromTop(index + 2, (inputY - position[1]) - itemHeight);
        }
    }

    @Override
    public void doSend(int position, Comment comment) {
        DynamicModel model = datas.get(position);
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
    public void onPullDownToRefresh(PullToRefreshBase<YLListView> refreshView) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<YLListView> refreshView) {
        pageIndex++;
        ZillaApi.NormalRestAdapter.create(CommunityService.class)
                .getTopicDetail(UserInfoModel.getInstance().getUserId(), topicId, pageIndex, 10, new RequestCallback<ResponseData<HealthyRecommendModel>>() {
                    @Override
                    public void success(ResponseData<HealthyRecommendModel> data, Response response) {
                        ptrlv.onRefreshComplete();
                        if (data.getStatus() == 200 && data.getData().getDynamiclist() != null && !data.getData().getDynamiclist().isEmpty()) {
                            datas.addAll(data.getData().getDynamiclist());
                            adapter.notifyDataSetChanged();
                        } else {
                            pageIndex = --pageIndex < 1 ? 1 : pageIndex;
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        ptrlv.onRefreshComplete();
                        super.failure(error);
                    }
                });
    }

    @Override
    public void onRefresh() {
        pageIndex = 1;
        pb.setVisibility(View.VISIBLE);
        ZillaApi.NormalRestAdapter.create(CommunityService.class)
                .getTopicDetail(UserInfoModel.getInstance().getUserId(), topicId, 1, 10, new RequestCallback<ResponseData<HealthyRecommendModel>>() {
                    @Override
                    public void success(ResponseData<HealthyRecommendModel> data, Response response) {
                        pb.setVisibility(GONE);
                        ptrlv.getRefreshableView().onCompletedRefresh();
                        if (data.getStatus() == 200 && data.getData().getDynamiclist() != null && !data.getData().getDynamiclist().isEmpty()) {
                            datas.clear();
                            datas.addAll(data.getData().getDynamiclist());
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        pb.setVisibility(GONE);
                        ptrlv.getRefreshableView().onCompletedRefresh();
                        super.failure(error);
                    }
                });
        ZillaApi.NormalRestAdapter.create(CommunityService.class)
                .getTopicCover(topicId, new RequestCallback<ResponseData<TopicInfo>>() {
                    @Override
                    public void success(ResponseData<TopicInfo> data, Response response) {
                        Log.i("刷新加载完成");
                        if (data.getStatus() == 200) {
                            TopicInfo info = data.getData();
                            tv_topticName.setText("#");
                            tv_topticName.append(info.getTopicName());
                            tv_topticName.append("#");
                            tv_dynamicNum.setText(String.valueOf(info.getDynamicNum()));
                            tv_dynamicNum.append("条动态");
                            tv_explin.setText(info.getTopicExplain());
                            if (TextUtils.isEmpty(info.getTopicPhoto())) {
                                Picasso.with(TopicDetailActivity.this)
                                        .load(R.drawable.default_icon_rect).placeholder(R.drawable.default_icon_rect).into(iv_banner);
                            } else {
                                Picasso.with(TopicDetailActivity.this)
                                        .load(AddressManager.get("photoHost") + info.getTopicPhoto())
                                        .fit()
                                        .error(R.drawable.default_icon_rect)
                                        .placeholder(R.drawable.default_icon_rect)
                                        .into(iv_banner);
                            }
                        }
                    }
                });
    }

}
