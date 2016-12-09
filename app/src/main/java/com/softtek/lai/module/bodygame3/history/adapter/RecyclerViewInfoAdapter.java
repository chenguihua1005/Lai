package com.softtek.lai.module.bodygame3.history.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
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

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.module.bodygame3.head.model.PhotoWallslistModel;
import com.softtek.lai.module.bodygame3.head.view.PhotoWallActivity;
import com.softtek.lai.module.community.adapter.PhotosAdapter;
import com.softtek.lai.module.picture.view.PictureMoreActivity;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.widgets.CircleImageView;
import com.softtek.lai.widgets.CustomGridView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import zilla.libcore.file.AddressManager;


public class RecyclerViewInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<PhotoWallslistModel> myItems;
    private ItemListener myListener;
    private Context mContext;
    private View mPopView;
    private int width;

    private static final int ITEM = 1;
    private static final int FOOTER = 2;
    private static final int EMPTY = 3;

    public RecyclerViewInfoAdapter(List<PhotoWallslistModel> items,
                                   ItemListener listener,
                                   Context context, View popView) {
        myItems = items;
        myListener = listener;
        mContext = context;
        mPopView = popView;
        width = DisplayUtil.getMobileWidth(mContext);
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
        void onItemClick(PhotoWallslistModel item, int pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public PhotoWallslistModel item;
        private ImageView mPopImg;
        private LinearLayout mZanLayout;
        private LinearLayout mCommentLayout;
        private TextView mUsername;
        private CheckBox mIsFocus;
        private CircleImageView mHeaderImg;
        private TextView mContent;
        private CustomGridView mPhotos;
        private TextView mDate;

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

        private void setData(PhotoWallslistModel item) {
            //用户名
            mUsername.setText(item.getUserName());
            //关注
            if (item.getIsFocus().equals("1")) {
                mIsFocus.setChecked(true);
            }
            //用户头像
            if (!TextUtils.isEmpty(item.getUserThPhoto())) {
                Picasso.with(mContext).load(AddressManager.get("photoHost") + item.getUserThPhoto()).fit().error(R.drawable.img_default)
                        .into(mHeaderImg);
            }
            //3给按钮
            mPopImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initPopupWindow(v, mPopView);
                }
            });

            //日期
            mDate.setText(item.getCreatedate());

            //发表留言内容
            if (item.getIsHasTheme().equals("1")) {
                String content = item.getContent();
                SpannableString ss = new SpannableString(content);
                ss.setSpan(new ForegroundColorSpan(0xFFFFA200), 0, 7, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
                mContent.setText(ss);
            } else {
                mContent.setText(item.getContent());
            }


            //点赞的人
            if (item.getPraiseNameList().size() > 0) {
                String name = "";
                mZanLayout.setVisibility(View.VISIBLE);
                for (int i = 0; i < item.getPraiseNameList().size(); i++) {
                    name = item.getPraiseNameList().get(i) + ",";
                    if (i == item.getPraiseNameList().size() - 1) {
                        name = name.substring(0, name.length() - 1);
                    }
                }
                TextView zanText = new TextView(mContext);
                zanText.setText(name);
                zanText.setTextColor(0xFF576a80);
                mZanLayout.addView(zanText);
            }

            //评论
            if (!item.getCommendsNum().equals("0")) {
                for (int i = 0; i < item.getPhotoWallCommendsList().size(); i++) {
                    String commendsName = item.getPhotoWallCommendsList().get(i).getCommentUserName();
                    String commendsContent = item.getPhotoWallCommendsList().get(i).getCommnets();
                    TextView commendText = new TextView(mContext);
                    commendText.setText(commendsName + ":" + commendsContent);
                    mCommentLayout.addView(commendText);
                }
            }
        }

        private void initPopupWindow(View v, View popView) {
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
            mZan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mZanLayout.getChildCount() > 0) {
                        mZanLayout.setVisibility(View.VISIBLE);
                    }
                    TextView zanText = new TextView(mContext);
                    String name = ",test";
                    if (mZanLayout.getChildCount() == 1) {
                        name = name.substring(1, name.length());
                    }
                    zanText.setText(name);
                    mZanLayout.addView(zanText);
                }
            });

            mComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView commentText = new TextView(mContext);
                    String commentName = "test123";
                    String commentContent = "neirong123";
                    commentText.setText(commentName + ":" + commentContent);
                    mCommentLayout.addView(commentText);
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
                                