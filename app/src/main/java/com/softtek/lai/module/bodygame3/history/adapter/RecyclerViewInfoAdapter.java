package com.softtek.lai.module.bodygame3.history.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.history.model.CommentEvent;
import com.softtek.lai.module.bodygame3.history.model.DynamicBean;
import com.softtek.lai.module.bodygame3.history.net.HistoryService;
import com.softtek.lai.module.community.adapter.PhotosAdapter;
import com.softtek.lai.module.picture.view.PictureMoreActivity;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CircleImageView;
import com.softtek.lai.widgets.CustomGridView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.util.Util;


public class RecyclerViewInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<DynamicBean.PhotoWallslistBean> myItems = new ArrayList<>();
    private ItemListener myListener;
    private Context mContext;
    private View mPopView;
    private CommentListener mCommentListener;

    private static final int ITEM = 1;
    private static final int FOOTER = 2;
    private static final int EMPTY = 3;

    public RecyclerViewInfoAdapter(List<DynamicBean.PhotoWallslistBean> items,
                                   ItemListener listener,
                                   CommentListener commentListener,
                                   Context context, View popView) {
        myItems = items;
        myListener = listener;
        mCommentListener = commentListener;
        mContext = context;
        mPopView = popView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_history_info, parent, false);
            return new ViewHolder(view);
        } else if (viewType == FOOTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.up_load_view, parent, false);
            return new FooterHolder(view);
        } else if (viewType == EMPTY) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_view, parent, false);
            return new EmptyHolder(view);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return myItems.size() == 0 ? 0 : myItems.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        int type;
        if (position + 1 == getItemCount()) {
            type = getItemCount() < 6 ? EMPTY : FOOTER;
        } else {
            type = ITEM;
        }
        return type;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //绑定数据
        if (holder instanceof RecyclerViewInfoAdapter.ViewHolder) {
            ((ViewHolder) holder).setData(myItems.get(position));
        }
    }

    public interface ItemListener {
        void onItemClick(DynamicBean.PhotoWallslistBean item, int pos);
    }

    public interface CommentListener {
        void onCommentClick(CommentEvent event);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public DynamicBean.PhotoWallslistBean item;
        private ImageView mPopImg;
        private LinearLayout mZanLayout;
        private LinearLayout mCommentLayout;
        private TextView mUsername;
        private CheckBox mIsFocus;
        private CircleImageView mHeaderImg;
        private TextView mContent;
        private CustomGridView mPhotos;
        private TextView mDate;
        private TextView mZanName;
        private String path = AddressManager.get("photoHost");
        private boolean isMyselfFocus;
        private boolean isFocus;
        private HistoryService service;
        private boolean hasZaned = false;


        public ViewHolder(View itemView) {
            super(itemView);
            mPopImg = (ImageView) itemView.findViewById(R.id.iv_pop_img);
            mZanLayout = (LinearLayout) itemView.findViewById(R.id.ll_zan_person);
            mCommentLayout = (LinearLayout) itemView.findViewById(R.id.ll_comment_person);
            mUsername = (TextView) itemView.findViewById(R.id.tv_name);
            mIsFocus = (CheckBox) itemView.findViewById(R.id.cb_focus);
            mHeaderImg = (CircleImageView) itemView.findViewById(R.id.civ_header_image);
            mContent = (TextView) itemView.findViewById(R.id.tv_content);
            mPhotos = (CustomGridView) itemView.findViewById(R.id.cgv_photos);
            mDate = (TextView) itemView.findViewById(R.id.tv_date);
            mZanName = (TextView) itemView.findViewById(R.id.tv_zan_name);

            if (mZanLayout.getChildCount() <= 1) {
                mZanLayout.setVisibility(View.GONE);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (myListener != null) {
                        myListener.onItemClick(item, pos);
                    }
                }
            });
        }

        private void setData(final DynamicBean.PhotoWallslistBean item) {
//            isMyselfFocus = item.getAccountid().equals(UserInfoModel.getInstance().getUser().getUserid());
            isMyselfFocus = (item.getAccountid() == UserInfoModel.getInstance().getUserId());
            isFocus = item.getIsFocus() == 1;
            service = ZillaApi.NormalRestAdapter.create(HistoryService.class);

            //用户名
            mUsername.setText(item.getUserName());
//            关注
            if (isMyselfFocus) {
                mIsFocus.setVisibility(View.INVISIBLE);
            } else if (isFocus) {
                mIsFocus.setVisibility(View.VISIBLE);
                mIsFocus.setChecked(true);
            }
            mIsFocus.setClickable(false);
//            mIsFocus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                    if (!mIsFocus.isChecked()) {
//                        try {
//                            service.doCancleFocusAccount(
//                                    UserInfoModel.getInstance().getToken(),
//                                    UserInfoModel.getInstance().getUserId(),
//                                    item.getAccountid(),
//                                    new RequestCallback<ResponseData>() {
//                                        @Override
//                                        public void success(ResponseData responseData, Response response) {
//                                            if (responseData.getStatus() != 200) {
//                                                mIsFocus.setChecked(true);
//                                            }
//                                        }
//
//                                        @Override
//                                        public void failure(RetrofitError error) {
//                                            super.failure(error);
//                                        }
//                                    });
//                        } catch (NumberFormatException e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        try {
//                            service.doFocusAccount(
//                                    UserInfoModel.getInstance().getToken(),
//                                    UserInfoModel.getInstance().getUserId(),
////                                    Long.parseLong(item.getAccountid()),
//                                    (item.getAccountid()),
//                                    new RequestCallback<ResponseData>() {
//                                        @Override
//                                        public void success(ResponseData responseData, Response response) {
//                                            if (responseData.getStatus() != 200) {
//                                                mIsFocus.setChecked(false);
//                                            }
//                                        }
//
//                                        @Override
//                                        public void failure(RetrofitError error) {
//                                            super.failure(error);
//                                        }
//                                    }
//                            );
//                        } catch (NumberFormatException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            });

            //用户头像
            if (!TextUtils.isEmpty(item.getUserPhoto())) {
                Picasso.with(mContext).load(path + item.getUserPhoto()).fit().error(R.drawable.img_default)
                        .placeholder(R.drawable.img_default).into(mHeaderImg);
            }
            //3个按钮
//            mPopImg.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    initPopupWindow(v, mPopView, item);
//                }
//            });

            //日期
//            long[] days = DateUtil.getInstance().getDaysForNow(item.getCreatedate());
//            String time;
//            if (days[0] == 0) {//今天
//                if (days[3] < 60) {//小于1分钟
//                    time = "刚刚";
//                } else if (days[3] >= 60 && days[3] < 3600) {//>=一分钟小于一小时
//                    time = days[2] + "分钟前";
//                } else {//大于一小时
//                    time = days[1] + "小时前";
//                }
//            } else if (days[0] == 1) {//昨天
//                time = "昨天";
//            } else {
//                time = days[0] + "天前";
//            }
//            mDate.setText(time);
            mDate.setText(item.getCreatedate());

            //发表留言内容
            if (item.getIsHasTheme() == 1) {
                String content = item.getContent();
                SpannableStringBuilder builder = new SpannableStringBuilder(content);
                String theme = "#" + item.getThemeName() + "#";
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(0xFFFFA202);
                int from = 0;
                int lastIndex = content.lastIndexOf("#");
                do {
                    int firstIndex = content.indexOf("#", from);
                    int nextIndex = firstIndex + item.getThemeName().length() + 1;
                    if (nextIndex <= lastIndex) {
                        String sub = content.substring(firstIndex, nextIndex + 1);
                        if (sub.equals(theme)) {
                            builder.setSpan(colorSpan, firstIndex, nextIndex + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        }
                    }
                    from = nextIndex;
                } while (from < lastIndex);
                mContent.setText(builder);
//                SpannableString ss = new SpannableString(content);
//                ss.setSpan(new ForegroundColorSpan(0xFFFFA200), 0, 7, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
//                mContent.setText(ss);

            } else {
                mContent.setText(item.getContent());
            }
            //点赞的人
            if (!item.getPraiseNameList().isEmpty()) {
                hasZaned = item.getIsPraise() == 1;
                mZanLayout.setVisibility(View.VISIBLE);//显示点赞人
                for (int i = 0; i < item.getPraiseNameList().size(); i++) {
                    if (i == 0) {
                        mZanName.setText(item.getPraiseNameList().get(i));
                    } else {
                        mZanName.append("," + item.getPraiseNameList().get(i));
                    }
                }
            } else {
                mZanLayout.setVisibility(View.GONE);//显示点赞人
            }
//            if (item.getPraiseNameList().size() > 0) {
//                String name = "";
//                mZanLayout.setVisibility(View.VISIBLE);
//                for (int i = 0; i < item.getPraiseNameList().size(); i++) {
//                    name = item.getPraiseNameList().get(i) + ",";
//                    if (i == item.getPraiseNameList().size() - 1) {
//                        name = name.substring(0, name.length() - 1);
//                    }
//                }
//                TextView zanText = new TextView(mContext);
//                zanText.setText(name);
//                zanText.setTextColor(0xFF576a80);
//                mZanLayout.addView(zanText);
//            }

            //评论
            if (!item.getPhotoWallCommendsList().isEmpty()) {
                mCommentLayout.removeAllViews();
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMarginStart(DisplayUtil.dip2px(mContext, 8));
                for (int i = 0; i < item.getPhotoWallCommendsList().size(); i++) {
                    TextView commendText = new TextView(mContext);
                    String commendsName = item.getPhotoWallCommendsList().get(i).getCommentUserName();
                    SpannableString ss = new SpannableString(commendsName + "：");
                    ss.setSpan(new ForegroundColorSpan(0xFF576A80), 0, ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    commendText.setText(ss);
                    String commendsContent = item.getPhotoWallCommendsList().get(i).getCommnets();
                    commendText.append(commendsContent);
                    commendText.setLayoutParams(lp);
                    mCommentLayout.addView(commendText);
                }
            }

            //照片墙的缩略图
            if (item.getThumbnailPhotoList().size() > 0) {
                mPhotos.setAdapter((new PhotosAdapter(item.getThumbnailPhotoList(), mContext,new Object())));
                mPhotos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent in = new Intent(mContext, PictureMoreActivity.class);
                        in.putStringArrayListExtra("images", (ArrayList<String>) item.getThumbnailPhotoList());
                        in.putExtra("position", i);
                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
                        ActivityCompat.startActivity(mContext, in, optionsCompat.toBundle());
                    }
                });
            }
        }

        private void initPopupWindow(View v, View popView, final DynamicBean.PhotoWallslistBean model) {
            final LinearLayout mZan = (LinearLayout) popView.findViewById(R.id.ll_zan);
            final LinearLayout mComment = (LinearLayout) popView.findViewById(R.id.ll_comment);
            final LinearLayout mReport = (LinearLayout) popView.findViewById(R.id.ll_report);
            int popupWidth = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int popupHeight = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            popView.measure(popupWidth, popupHeight);
            popupHeight = popView.getMeasuredHeight();
            popupWidth = popView.getMeasuredWidth();
            final PopupWindow popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, popupHeight, true);
            int[] location = new int[2];
            v.getLocationOnScreen(location);
            final int mSettingWidth = location[0];
            final int mSettingHigh = location[1];
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, mSettingWidth - popupWidth, mSettingHigh + v.getHeight() / 2 - popupHeight / 2);
            popupWindow.setTouchable(true);
            popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                }
            });
            popupWindow.update();
            if (model.getPraiseNameList() != null) {
                if (hasZaned) {
                    mZan.setClickable(false);
                } else {
                    mZan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                            try {
                                service.postZan(UserInfoModel.getInstance().getToken(),
                                        UserInfoModel.getInstance().getUserId(),
                                        UserInfoModel.getInstance().getUser().getNickname(),
                                        model.getHealtId(),
                                        new RequestCallback<ResponseData>() {
                                            @Override
                                            public void success(ResponseData responseData, Response response) {
                                                if (responseData.getStatus() == 200) {
                                                    hasZaned = true;
                                                    mZan.setClickable(false);
                                                    String name = "," + UserInfoModel.getInstance().getUser().getNickname();
                                                    if (model.getPraiseNameList().isEmpty()) {
                                                        name = name.substring(1, name.length());
                                                    }
                                                    mZanName.append(name);
                                                }else {
                                                    hasZaned = false;
                                                    mZan.setClickable(true);
                                                }
                                            }

                                            @Override
                                            public void failure(RetrofitError error) {
                                                hasZaned = false;
                                                mZan.setClickable(true);
//                                                if (item.getPraiseNameList().isEmpty()) {
//                                                    mZanLayout.setVisibility(View.GONE);
//                                                    mZanName.setText("");
//                                                }
                                                super.failure(error);
                                            }
                                        });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }


            mComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    popupWindow.dismiss();
                    CommentEvent event = new CommentEvent();
                    event.setAccountId(UserInfoModel.getInstance().getUserId());
                    event.setHealthId(model.getHealtId());
                    event.setView(itemView);
                    mCommentListener.onCommentClick(event);
//                    EventBus.getDefault().post(event);
                }
            });

            mReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    public class FooterHolder extends RecyclerView.ViewHolder {

        public FooterHolder(View view) {
            super(view);
        }
    }

    public class EmptyHolder extends RecyclerView.ViewHolder {

        public EmptyHolder(View view) {
            super(view);
        }
    }
}
                                