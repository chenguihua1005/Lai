package com.softtek.lai.module.bodygame3.photowall;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame3.photowall.model.CommentModel;
import com.softtek.lai.module.bodygame3.photowall.model.PhotoWallListModel;
import com.softtek.lai.module.bodygame3.photowall.model.PhotoWallslistModel;
import com.softtek.lai.module.bodygame3.photowall.net.PhotoWallService;
import com.softtek.lai.module.community.adapter.PhotosAdapter;
import com.softtek.lai.module.community.eventModel.DeleteFocusEvent;
import com.softtek.lai.module.community.eventModel.DeleteRecommedEvent;
import com.softtek.lai.module.community.eventModel.FocusEvent;
import com.softtek.lai.module.community.eventModel.FocusReload;
import com.softtek.lai.module.community.eventModel.Where;
import com.softtek.lai.module.community.eventModel.ZanEvent;
import com.softtek.lai.module.community.model.Comment;
import com.softtek.lai.module.community.model.DoZan;
import com.softtek.lai.module.community.model.TopicList;
import com.softtek.lai.module.community.net.CommunityService;
import com.softtek.lai.module.community.presenter.OpenComment;
import com.softtek.lai.module.community.presenter.SendCommend;
import com.softtek.lai.module.community.view.TopicDetailActivity;
import com.softtek.lai.module.picture.model.UploadImage;
import com.softtek.lai.picture.LookBigPicActivity;
import com.softtek.lai.picture.bean.EaluationPicBean;
import com.softtek.lai.picture.util.EvaluateUtil;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.utils.SoftInputUtil;
import com.softtek.lai.widgets.CircleImageView;
import com.softtek.lai.widgets.CustomGridView;
import com.softtek.lai.widgets.TextViewExpandableAnimation;
import com.squareup.picasso.Picasso;
import com.sw926.imagefileselector.ImageFileSelector;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

import static android.view.View.GONE;

@InjectLayout(R.layout.activity_photo_wall)
public class PhotoWallActivity extends BaseActivity implements OpenComment, SendCommend, PullToRefreshBase.OnRefreshListener2<ListView>,
        View.OnClickListener,
        View.OnLayoutChangeListener {

    @InjectView(R.id.ptrlv)
    PullToRefreshListView ptrlv;
    @InjectView(R.id.empty)
    FrameLayout empty;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.iv_email)
    ImageView iv_email;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;

    @InjectView(R.id.rl_send)
    RelativeLayout rl_send;
    @InjectView(R.id.et_input)
    EditText et_input;
    @InjectView(R.id.btn_send)
    Button btn_send;

    private EasyAdapter<PhotoWallslistModel> adapter;
    PhotoWallListModel photoWallListModel;
    List<PhotoWallslistModel> photoWallItemModels = new ArrayList<>();
    int pageIndex = 1;
    int totalPage = 0;
    PhotoWallService photoWallService;
    private CommunityService service;
    private CharSequence[] items = {"拍照", "从相册选择照片"};
    private static final int OPEN_SENDER_REQUEST = 1;
    private static final int CAMERA_PREMISSION = 100;
    private ImageFileSelector imageFileSelector;

    private String classId;//班级id
    private int classRole;

    @Override
    protected void initViews() {
        //阀值设置为屏幕高度的1/3
        classId = getIntent().getStringExtra("classId");
        classRole = getIntent().getIntExtra("classRole", -1);
        tv_title.setText("照片墙");
        fl_right.setOnClickListener(this);
        iv_email.setBackground(ContextCompat.getDrawable(this, R.drawable.camera));
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
        int px = DisplayUtil.dip2px(this, 300);
        et_input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1000)});
        //*************************
        imageFileSelector = new ImageFileSelector(this);
        imageFileSelector.setOutPutImageSize(px, px);
        imageFileSelector.setQuality(60);
        imageFileSelector.setCallback(new ImageFileSelector.Callback() {
            @Override
            public void onSuccess(String file) {
                Intent intent = new Intent(PhotoWallActivity.this, PublishDyActivity.class);//跳转到发布动态界面
                UploadImage image = new UploadImage();
                image.setImage(new File(file));
                image.setUri(Uri.fromFile(new File(file)));
                ArrayList<UploadImage> uploadImages = new ArrayList<>();
                uploadImages.add(image);
                intent.putParcelableArrayListExtra("uploadImages", uploadImages);
                intent.putExtra("classId", classId);
                startActivityForResult(intent, OPEN_SENDER_REQUEST);
            }

            @Override
            public void onMutilSuccess(List<String> files) {
                Intent intent = new Intent(PhotoWallActivity.this, PublishDyActivity.class);//跳转到发布动态界面
                ArrayList<UploadImage> uploadImages = new ArrayList<>();
                for (int i = files.size() - 1; i >= 0; i--) {
                    UploadImage image = new UploadImage();
                    File file = new File(files.get(i));
                    image.setImage(file);
                    image.setUri(Uri.fromFile(file));
                    uploadImages.add(image);
                }
                intent.putParcelableArrayListExtra("uploadImages", uploadImages);
                intent.putExtra("classId", classId);
                startActivityForResult(intent, OPEN_SENDER_REQUEST);
            }

            @Override
            public void onError() {

            }
        });
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
            public void onClick(View view) {
                hiden();
                Integer position = (Integer) view.getTag();
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
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK, getIntent());
                finish();
            }
        });
        ptrlv.getRefreshableView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hiden();
                return false;
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
    protected void initDatas() {
        photoWallService = ZillaApi.NormalRestAdapter.create(PhotoWallService.class);
        service = ZillaApi.NormalRestAdapter.create(CommunityService.class);
        adapter = new EasyAdapter<PhotoWallslistModel>(this, photoWallItemModels, R.layout.photowall_list_item) {
            @Override
            public void convert(final ViewHolder holder, final PhotoWallslistModel data, final int position) {
                TextView tv_name = holder.getView(R.id.tv_name);
                tv_name.setText(data.getUserName());//用户名
                CircleImageView civ_header_image = holder.getView(R.id.civ_header_image);
                if (!TextUtils.isEmpty(data.getUserPhoto())) {//头像
                    Picasso.with(PhotoWallActivity.this).load(AddressManager.get("photoHost") + data.getUserPhoto())
                            .fit()
                            .error(R.drawable.img_default)
                            .placeholder(R.drawable.img_default)
                            .into(civ_header_image);
                } else {
                    Picasso.with(PhotoWallActivity.this).load(R.drawable.img_default)
                            .into(civ_header_image);
                }
                TextView tv_week = holder.getView(R.id.tv_week);
                if (data.getCurrWeek() != 0) {
                    tv_week.setVisibility(View.VISIBLE);
                    tv_week.setText("第");
                    tv_week.append(String.valueOf(data.getCurrWeek()));
                    tv_week.append("体管周");
                } else {
                    tv_week.setVisibility(GONE);
                }
                TextViewExpandableAnimation tv_content = holder.getView(R.id.tv_content);
                final String content = data.getContent();
                SpannableStringBuilder builder = new SpannableStringBuilder(content);
                if (data.getIsHasTheme() == 1 && data.getTopicList() != null) {
                    int from = 0;
                    int lastIndex = content.lastIndexOf("#");
                    do {
                        //先获取第一个#号出现的下标
                        int firstIndex = content.indexOf("#", from);
                        //然后获取下一个#号出现的位置
                        int next = content.indexOf("#", firstIndex + 1);
                        if (next == -1) {
                            break;
                        }
                        //截取两个#号之间的字符
                        String sub = content.substring(firstIndex + 1, next);
                        //将开始下标移动至下一个#号出现的位置
                        from = next;
                        for (final TopicList topic : data.getTopicList()) {
                            if (sub.equals(topic.getTopicName())) {
                                from = next + 1;
                                builder.setSpan(new ClickableSpan() {
                                    @Override
                                    public void onClick(View widget) {
                                        Intent intent = new Intent(PhotoWallActivity.this, TopicDetailActivity.class);
                                        intent.putExtra("topicId", topic.getTopicType());
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setColor(0xFFFFA202);
                                        ds.setUnderlineText(false);//去除超链接的下划线
                                    }
                                }, firstIndex, next + 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                                break;
                            }
                        }
                    } while (from < lastIndex);
                }
                tv_content.getTextView().setHighlightColor(ContextCompat.getColor(PhotoWallActivity.this, android.R.color.transparent));
                tv_content.setText(builder);
                tv_content.getTextView().setMovementMethod(LinkMovementMethod.getInstance());
                tv_content.setOnStateChangeListener(new TextViewExpandableAnimation.OnStateChangeListener() {
                    @Override
                    public void onStateChange(boolean isShrink) {
                        data.setOpen(!isShrink);
                    }
                });
                tv_content.resetState(!data.isOpen());
                final CheckBox cb_focus = holder.getView(R.id.cb_focus);
                boolean isMine = Long.parseLong(TextUtils.isEmpty(data.getAccountid()) ? "0" : data.getAccountid()) == UserInfoModel.getInstance().getUserId();
                if (isMine) {
                    cb_focus.setVisibility(GONE);
                } else {
                    cb_focus.setVisibility(View.VISIBLE);
                    cb_focus.setChecked(1 == data.getIsFocus());//是否关注
                    //关注点击事件
                    cb_focus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (cb_focus.isChecked()) {
                                EventBus.getDefault().post(new FocusEvent(String.valueOf(data.getHealtId()), 1, Where.PHOTOWALL_LIST));
                                service.focusAccount(UserInfoModel.getInstance().getToken(),
                                        UserInfoModel.getInstance().getUserId(),
                                        Long.parseLong(data.getAccountid()),
                                        new RequestCallback<ResponseData>() {
                                            @Override
                                            public void success(ResponseData responseData, Response response) {
                                                int status = responseData.getStatus();
                                                switch (status) {
                                                    case 200:
                                                        EventBus.getDefault().post(new FocusReload());
                                                        //Util.toastMsg(responseData.getMsg());
                                                        refreshList(data.getAccountid(), 1);
                                                        break;
                                                    default:
                                                        cb_focus.setChecked(false);
                                                        //Util.toastMsg(responseData.getMsg());
                                                        break;
                                                }
                                            }
                                        });
                            } else {
                                EventBus.getDefault().post(new FocusEvent(String.valueOf(data.getHealtId()), 1, Where.PHOTOWALL_LIST));
                                EventBus.getDefault().post(new DeleteFocusEvent(String.valueOf(data.getAccountid())));
                                service.cancleFocusAccount(UserInfoModel.getInstance().getToken(),
                                        UserInfoModel.getInstance().getUserId(),
                                        Long.parseLong(data.getAccountid()),
                                        new RequestCallback<ResponseData>() {
                                            @Override
                                            public void success(ResponseData responseData, Response response) {
                                                int status = responseData.getStatus();
                                                switch (status) {
                                                    case 200:
                                                        //Util.toastMsg(responseData.getMsg());
                                                        refreshList(data.getAccountid(), 0);
                                                        break;
                                                    default:
                                                        cb_focus.setChecked(true);
                                                        //Util.toastMsg(responseData.getMsg());
                                                        break;
                                                }
                                            }
                                        });

                            }
                        }
                    });
                }
                TextView tv_date = holder.getView(R.id.tv_date);
                String time = "";
                long[] days = DateUtil.getInstance().getDaysForNow(data.getCreatedate());
                if (days[0] == 0) {//今天
                    if (days[3] < 60) {//小于1分钟
                        time = "刚刚";
                    } else if (days[3] >= 60 && days[3] < 3600) {//>=一分钟小于一小时
                        time = days[2] + "分钟前";
                    } else {//大于一小时
                        time = days[1] + "小时前";
                    }
                } else if (days[0] == 1) {//昨天
                    time = "昨天";
                } else {
                    time = days[0] + "天前";
                }
                tv_date.setText(time);//日期
                LinearLayout ll_dianzan = holder.getView(R.id.ll_dianzan);
                TextView tv_zan_name = holder.getView(R.id.tv_zan_name);
                if (!data.getPraiseNameList().isEmpty()) {
                    ll_dianzan.setVisibility(View.VISIBLE);//显示点赞人
                    for (int i = 0; i < data.getPraiseNameList().size(); i++) {
                        if (i == 0) {
                            tv_zan_name.setText(data.getPraiseNameList().get(i));
                        } else {
                            tv_zan_name.append("," + data.getPraiseNameList().get(i));
                        }

                    }
                } else {
                    ll_dianzan.setVisibility(GONE);//显示点赞人
                }

                CustomGridView photos = holder.getView(R.id.photos);
                photos.setAdapter(new PhotosAdapter(data.getThumbnailPhotoList(), PhotoWallActivity.this, new Object()));
                //添加评论
                LinearLayout ll_comment = holder.getView(R.id.ll_comment);
                if (!data.getPhotoWallCommendsList().isEmpty()) {
                    ll_comment.removeAllViews();
                    ll_comment.setVisibility(View.VISIBLE);
                    for (int i = 0, j = data.getPhotoWallCommendsList().size(); i < j; i++) {
                        CommentModel comment = data.getPhotoWallCommendsList().get(i);
                        TextView tv = new TextView(PhotoWallActivity.this);
                        tv.setTextColor(0xFF333333);
                        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                        int px = DisplayUtil.dip2px(PhotoWallActivity.this, 5);
                        tv.setPadding(px, px, 0, 0);
                        SpannableString ss = new SpannableString(comment.getCommentUserName() + "：");
                        ss.setSpan(new ForegroundColorSpan(0xFF576A80), 0, ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        tv.setText(ss);
                        tv.append(comment.getCommnets());
                        ll_comment.addView(tv);
                    }
                } else {
                    ll_comment.setVisibility(GONE);
                }
                //照片列表点击事件
                photos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                        Intent intent = new Intent(PhotoWallActivity.this, LookBigPicActivity.class);
                        Bundle bundle = new Bundle();
                        List<EaluationPicBean> list = EvaluateUtil.setupCoords(PhotoWallActivity.this, (ImageView) v, data.getPhotoList(), position);
                        bundle.putSerializable(LookBigPicActivity.PICDATALIST, (Serializable) list);
                        intent.putExtras(bundle);
                        intent.putExtra(LookBigPicActivity.CURRENTITEM, position);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                    }
                });
                //操作按钮
                ImageView iv_operator = holder.getView(R.id.iv_operator);
                final RelativeLayout rl_item = holder.getView(R.id.rl_item);
                iv_operator.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PopupWindow popupWindow = createPop(data, rl_item.getHeight(), position);
                        int width = popupWindow.getContentView().getMeasuredWidth();
                        int[] location = new int[2];
                        view.getLocationOnScreen(location);
                        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, location[0] - width, location[1]);

                    }
                });

            }
        };
        ptrlv.setAdapter(adapter);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ptrlv != null) {
                    ptrlv.setRefreshing();
                }
            }
        }, 500);
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
                SoftInputUtil.showInputAsView(PhotoWallActivity.this, et_input);

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

    private PopupWindow createPop(final PhotoWallslistModel data, final int itemHeight, final int position) {
        //弹出popwindow
        final PopupWindow popupWindow = new PopupWindow(PhotoWallActivity.this);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(DisplayUtil.dip2px(PhotoWallActivity.this, 30));
        popupWindow.setAnimationStyle(R.style.operation_anim_style);
        popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(PhotoWallActivity.this, R.drawable.opteration_drawable));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        final View contentView = LayoutInflater.from(PhotoWallActivity.this).inflate(R.layout.pop_operator, null);
        TextView tv_zan = (TextView) contentView.findViewById(R.id.tv_oper_zan);
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
                List<String> praiseName = data.getPraiseNameList();
                praiseName.add(infoModel.getUser().getNickname());
                data.setPraiseNameList(praiseName);
                //向服务器提交
                String token = infoModel.getToken();
                EventBus.getDefault().post(new ZanEvent(data.getHealtId(), true, Where.PHOTOWALL_LIST));
                service.clickLike(token, new DoZan(Long.parseLong(infoModel.getUser().getUserid()), data.getHealtId()),
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
                                List<String> praise = data.getPraiseNameList();
                                praise.remove(praise.size() - 1);
                                data.setPraiseNameList(praise);
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
        //点击删除按钮只有总教练或者自己发的动态才可以有删除功能
        if (classRole == 1
                || Long.parseLong(TextUtils.isEmpty(data.getAccountid()) ? "0" : data.getAccountid()) == UserInfoModel.getInstance().getUserId()) {
            tv_delete.setVisibility(View.VISIBLE);
            tv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                    new AlertDialog.Builder(PhotoWallActivity.this).setTitle("温馨提示").setMessage("确定删除吗？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    service.deleteHealth(UserInfoModel.getInstance().getToken(),
                                            data.getHealtId(),
                                            new RequestCallback<ResponseData>() {
                                                @Override
                                                public void success(ResponseData responseData, Response response) {
                                                    try {
                                                        if (responseData.getStatus() == 200) {
                                                            EventBus.getDefault().post(new DeleteRecommedEvent(data.getHealtId(), Where.PHOTOWALL_LIST));
                                                            photoWallItemModels.remove(data);
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

    @Subscribe
    public void refreshList(FocusEvent event) {
        for (PhotoWallslistModel model : photoWallItemModels) {
            if (model.getAccountid().equals(event.getAccountId())) {
                model.setIsFocus(event.getFocusStatus());
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Subscribe
    public void refreshListDelete(DeleteRecommedEvent event) {
        if (event.getWhere() != Where.DYNAMIC_LIST) {
            Iterator<PhotoWallslistModel> iterator = photoWallItemModels.iterator();
            while (iterator.hasNext()) {
                PhotoWallslistModel model = iterator.next();
                if (model.getHealtId().equals(event.getDynamicId())) {
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
            for (PhotoWallslistModel model : photoWallItemModels) {
                if (model.getHealtId().equals(event.getDynamicId())) {
                    model.setIsPraise(Integer.parseInt(Constants.HAS_ZAN));
                    model.setPraiseNum(model.getPraiseNum() + 1);
                    UserInfoModel infoModel = UserInfoModel.getInstance();
                    model.getPraiseNameList().add(infoModel.getUser().getNickname());
                    break;
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    private void refreshList(String Accountid, int focus) {
        for (PhotoWallslistModel model : photoWallItemModels) {
            if (model.getAccountid().equals(Accountid)) {
                model.setIsFocus(focus);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex = 1;
        photoWallService.doGetPhotoWalls(classId, UserInfoModel.getInstance().getToken(),
                UserInfoModel.getInstance().getUserId(),
                classId,
                pageIndex,
                10,
                new RequestCallback<ResponseData<PhotoWallListModel>>() {
                    @Override
                    public void success(ResponseData<PhotoWallListModel> photoWallListModelResponseData, Response response) {
                        try {
                            ptrlv.onRefreshComplete();
                            int status = photoWallListModelResponseData.getStatus();
                            switch (status) {
                                case 200:
                                    photoWallListModel = photoWallListModelResponseData.getData();
                                    photoWallItemModels.clear();
                                    if (photoWallListModel != null && photoWallListModel.getPhotoWallslist() != null) {
                                        photoWallItemModels.addAll(photoWallListModel.getPhotoWallslist());
                                    }
                                    adapter.notifyDataSetChanged();
                                    break;
                                default:
                                    Util.toastMsg(photoWallListModelResponseData.getMsg());
                                    break;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        try {
                            ptrlv.onRefreshComplete();
                            super.failure(error);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex++;
        photoWallService.doGetPhotoWalls(classId, UserInfoModel.getInstance().getToken(),
                UserInfoModel.getInstance().getUserId(),
                classId,
                pageIndex,
                10,
                new RequestCallback<ResponseData<PhotoWallListModel>>() {
                    @Override
                    public void success(ResponseData<PhotoWallListModel> photoWallListModelResponseData, Response response) {
                        try {
                            ptrlv.onRefreshComplete();
                            int status = photoWallListModelResponseData.getStatus();
                            switch (status) {
                                case 200:

                                    photoWallListModel = photoWallListModelResponseData.getData();
                                    if (photoWallListModel != null) {
                                        if (!photoWallListModel.getPhotoWallslist().isEmpty()) {
                                            photoWallItemModels.addAll(photoWallListModel.getPhotoWallslist());
                                            adapter.notifyDataSetChanged();
                                        } else {
                                            pageIndex--;
                                        }
                                    }

                                    break;
                                default:
                                    Util.toastMsg(photoWallListModelResponseData.getMsg());
                                    break;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        try {
                            ptrlv.onRefreshComplete();
                            super.failure(error);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageFileSelector.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1 && requestCode == OPEN_SENDER_REQUEST) {//result_ok
            onPullDownToRefresh(null);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PREMISSION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay! Do the
                // contacts-related task you need to do.
                imageFileSelector.takePhoto(PhotoWallActivity.this);

            } else {

                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_right:
                hiden();
                //弹出dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            //拍照
                            if (ActivityCompat.checkSelfPermission(PhotoWallActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                //可以得到一个是否需要弹出解释申请该权限的提示给用户如果为true则表示可以弹
                                //允许弹出提示
                                ActivityCompat.requestPermissions(PhotoWallActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PREMISSION);
                            } else {
                                imageFileSelector.takePhoto(PhotoWallActivity.this);
                            }
                        } else if (which == 1) {
                            //照片
                            imageFileSelector.selectMutilImage(PhotoWallActivity.this, 9);
                        }
                    }
                }).create().show();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && rl_send.getVisibility() == View.VISIBLE) {
            rl_send.setVisibility(View.INVISIBLE);
            SoftInputUtil.hidden(this);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK && rl_send.getVisibility() == View.INVISIBLE) {
            setResult(RESULT_OK, getIntent());
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
    public void doScroll(int index, int itemHeight, int inputY) {
        int[] position = new int[2];
        ptrlv.getLocationOnScreen(position);
        //用弹出软件盘输入框在屏幕中的y值减去listView的顶部在屏幕中的Y值就是listView的剩余可显示高度。
        int emptyHeight = inputY - position[1];
        if (emptyHeight >= itemHeight) {
            //如果可显示高度大于整个item的高度则只需移动这个item到第一个区域即可
            ((ListView) ptrlv.getRefreshableView()).setSelectionFromTop(index + 1, 0);
        } else {
            //把被软件盘遮住的部分显示出来
            ((ListView) ptrlv.getRefreshableView()).setSelectionFromTop(index + 1, (inputY - position[1]) - itemHeight);
        }
    }

    @Override
    public void doSend(int position, Comment comment) {
        PhotoWallslistModel model = photoWallItemModels.get(position);
        CommentModel comment1 = new CommentModel();
        comment1.setCommentUserName(comment.CommentUserName);
        comment1.setCommnets(comment.Comment);
        model.getPhotoWallCommendsList().add(comment1);
        adapter.notifyDataSetChanged();
        ZillaApi.NormalRestAdapter.create(PhotoWallService.class)
                .commitComment(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(),
                        model.getHealtId(), comment.Comment, new RequestCallback<ResponseData>() {
                            @Override
                            public void success(ResponseData responseData, Response response) {

                            }
                        });
    }
}
