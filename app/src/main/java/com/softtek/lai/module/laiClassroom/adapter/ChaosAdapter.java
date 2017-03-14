package com.softtek.lai.module.laiClassroom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.laiClassroom.model.SearchModel;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.widgets.RectangleImage;
import com.squareup.picasso.Picasso;

import java.util.List;

import zilla.libcore.file.AddressManager;


public class ChaosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIDEO = 1;
    private static final int ONE_PIC = 2;
    private static final int THREE_PIC = 3;
    private static final int FOOTER = -1;
    private static final int EMPTY = -2;
    private List<SearchModel.ArticleListBean> myItems;
    private ItemListener myListener;
    private Context context;
    private String searchKey;

    public void updataKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public ChaosAdapter(Context context, List<SearchModel.ArticleListBean> items, ItemListener listener, String searchKey) {
        this.context = context;
        myItems = items;
        myListener = listener;
        this.searchKey = searchKey;
    }

    public void setListener(ItemListener listener) {
        myListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIDEO) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
            return new VideoViewHolder(view);
        } else if (viewType == ONE_PIC) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_one_pic, parent, false);
            return new OnePicHolder(view);
        } else if (viewType == THREE_PIC) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_three_pic, parent, false);
            return new ThreePicHolder(view);
        } else if (viewType == EMPTY) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_view, parent, false);
            return new EmptyHolder(view);
        } else if (viewType == FOOTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_footer, parent, false);
            return new FooterHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        int result = 0;//默认没有
        if (position + 1 == getItemCount()) {
            return getItemCount() < 6 ? EMPTY : FOOTER;
        } else {
            int itemType = myItems.get(position).getMediaType();
            int isMultiPic = myItems.get(position).getIsMultiPic();
            if (itemType == 1) {
                if (isMultiPic == 0) {
                    result = ONE_PIC;//1图
                } else {
                    result = THREE_PIC;//多图
                }
            } else if (itemType == 2) {
                result = VIDEO;//视频
            }
        }

        return result;
    }

    @Override
    public int getItemCount() {
        return myItems.size() == 0 ? 0 : myItems.size() + 1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == 1) {
            ((VideoViewHolder) holder).setData(myItems.get(position));
        } else if (getItemViewType(position) == 2) {
            ((OnePicHolder) holder).setData(myItems.get(position));
        } else if (getItemViewType(position) == 3) {
            ((ThreePicHolder) holder).setData(myItems.get(position));
        }
    }

    public interface ItemListener {
        void onItemClick(SearchModel.ArticleListBean item, int position);
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        private TextView mVideoTitle;
        private RectangleImage mVideoImage;
        private TextView mVideoTime;
        private TextView mRelese;
        private TextView mHotNum;
        private TextView mSubject;
        private SearchModel.ArticleListBean myitem;

        public VideoViewHolder(View itemView) {
            super(itemView);
            mVideoTitle = (TextView) itemView.findViewById(R.id.tv_video_name);
            mVideoImage = (RectangleImage) itemView.findViewById(R.id.iv_video_img);
            mVideoTime = (TextView) itemView.findViewById(R.id.tv_video_time);
            mRelese = (TextView) itemView.findViewById(R.id.tv_relese);
            mHotNum = (TextView) itemView.findViewById(R.id.tv_hotnum);
            mSubject = (TextView) itemView.findViewById(R.id.tv_subject);
        }

        public void setData(SearchModel.ArticleListBean item) {
            this.myitem = item;
            setKeyColor(mVideoTitle, item.getTitle(), true);
//            mVideoTitle.setText(item.getTitle());
            mVideoTime.setText(item.getVideoTime());
            mRelese.setText(getTime(item.getCreateDate()));
            mHotNum.setText(String.valueOf(item.getClicks()));
            setKeyColor(mSubject, item.getTopic(), false);
//            mSubject.setText(item.getTopic());
            String videoImage = null;
            if (item.getArticImg() != null && !item.getArticImg().isEmpty()) {
                videoImage = item.getArticImg().get(0);
            }
            if (!TextUtils.isEmpty(videoImage)) {
                Picasso.with(context).load(AddressManager.get("photoHost") + videoImage)
                        .fit()
                        .placeholder(R.drawable.default_icon_rect)
                        .error(R.drawable.default_icon_rect)
                        .into(mVideoImage);
            } else {
                Picasso.with(context).load(R.drawable.default_icon_rect)
                        .placeholder(R.drawable.default_icon_rect)
                        .error(R.drawable.default_icon_rect)
                        .into(mVideoImage);
            }
            if (!TextUtils.isEmpty(item.getArticUrl())) {
                mVideoImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (myListener != null)
                            myListener.onItemClick(myitem, getAdapterPosition());
                        //跳转视频详情
                    }
                });
            }
        }
    }

    public class OnePicHolder extends RecyclerView.ViewHolder {
        private TextView mOnePicTitle;
        private RectangleImage mOnePicImage;
        private TextView mRelese;
        private TextView mHotNum;
        private TextView mSubject;

        public OnePicHolder(View view) {
            super(view);
            mOnePicTitle = (TextView) view.findViewById(R.id.tv_one_pic_title);
            mOnePicImage = (RectangleImage) view.findViewById(R.id.iv_one_pic);
            mRelese = (TextView) view.findViewById(R.id.tv_relese);
            mHotNum = (TextView) view.findViewById(R.id.tv_hotnum);
            mSubject = (TextView) view.findViewById(R.id.tv_subject);

        }

        public void setData(SearchModel.ArticleListBean item) {
            setKeyColor(mOnePicTitle, item.getTitle(), true);
//            mOnePicTitle.setText(item.getTitle());
            mRelese.setText(getTime(item.getCreateDate()));
            mHotNum.setText(String.valueOf(item.getClicks()));
            setKeyColor(mSubject, item.getTopic(), false);
//            mSubject.setText(item.getTopic());
            String videoImage = null;
            if (item.getArticImg() != null && !item.getArticImg().isEmpty()) {
                videoImage = item.getArticImg().get(0);
            }
            if (!TextUtils.isEmpty(videoImage)) {
                Picasso.with(context).load(AddressManager.get("photoHost") + videoImage)
                        .fit()
                        .placeholder(R.drawable.default_icon_rect)
                        .error(R.drawable.default_icon_rect)
                        .into(mOnePicImage);
            } else {
                Picasso.with(context).load(R.drawable.default_icon_rect)
                        .placeholder(R.drawable.default_icon_rect)
                        .error(R.drawable.default_icon_rect)
                        .into(mOnePicImage);
            }
            mOnePicImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转图文详情
                }
            });
        }
    }

    public class ThreePicHolder extends RecyclerView.ViewHolder {
        private TextView mThreePicTitle;
        private RectangleImage mFirstImg;
        private RectangleImage mSecondImg;
        private RectangleImage mThirdImg;
        private LinearLayout mLLImgContainer;
        private TextView mRelese;
        private TextView mHotNum;
        private TextView mSubject;

        public ThreePicHolder(View view) {
            super(view);
            mThreePicTitle = (TextView) view.findViewById(R.id.tv_title);
            mFirstImg = (RectangleImage) view.findViewById(R.id.iv_one);
            mSecondImg = (RectangleImage) view.findViewById(R.id.iv_two);
            mThirdImg = (RectangleImage) view.findViewById(R.id.iv_three);
            mLLImgContainer = (LinearLayout) view.findViewById(R.id.ll_image);
            mRelese = (TextView) view.findViewById(R.id.tv_relese);
            mHotNum = (TextView) view.findViewById(R.id.tv_hotnum);
            mSubject = (TextView) view.findViewById(R.id.tv_subject);
        }

        public void setData(SearchModel.ArticleListBean item) {
            setKeyColor(mThreePicTitle, item.getTitle(), true);
//            mThreePicTitle.setText(item.getTitle());
            mRelese.setText(getTime(item.getCreateDate()));
            mHotNum.setText(String.valueOf(item.getClicks()));
            setKeyColor(mSubject, item.getTopic(), false);
//            mSubject.setText(item.getTopic());
            if (item.getArticImg() != null && !item.getArticImg().isEmpty()) {
                for (int i = 0; i < item.getArticImg().size(); i++) {
                    String imgUrl = item.getArticImg().get(i);
                    ImageView iv = null;
                    switch (i) {
                        case 0:
                            iv = mFirstImg;
                            break;
                        case 1:
                            iv = mSecondImg;
                            break;
                        case 2:
                            iv = mThirdImg;
                            break;
                    }
                    if (iv != null) {
                        if (!TextUtils.isEmpty(imgUrl)) {
                            Picasso.with(context).load(AddressManager.get("photoHost") + imgUrl)
                                    .fit()
                                    .placeholder(R.drawable.default_icon_rect)
                                    .error(R.drawable.default_icon_rect)
                                    .into(iv);
                        } else {
                            Picasso.with(context).load(R.drawable.default_icon_rect)
                                    .placeholder(R.drawable.default_icon_rect)
                                    .error(R.drawable.default_icon_rect)
                                    .into(iv);
                        }
                    }
                }
            } else {
                mLLImgContainer.setVisibility(View.GONE);
            }
        }
    }

    private String getTime(String createTime) {
        String time = "";
        long[] days = DateUtil.getInstance().getDaysForNow(createTime);
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
        return time;
    }

    private void setKeyColor(TextView view, String changeString, boolean isTitle) {
        if (changeString.equals(searchKey)){
            view.setText(changeString);
            view.setTextColor(context.getResources().getColor(R.color.red));
            return;
        }
        int index = changeString.indexOf(searchKey);
        if (index > 0) {
            int titleLength = changeString.length();
            int length = searchKey.length();
            SpannableStringBuilder builder = new SpannableStringBuilder(changeString);
            ForegroundColorSpan redSpan = new ForegroundColorSpan(context.getResources().getColor(R.color.red));
            ForegroundColorSpan greenSpan = new ForegroundColorSpan(context.getResources().getColor(R.color.green));
            ForegroundColorSpan blackSpan = new ForegroundColorSpan(context.getResources().getColor(R.color.black));
            if (isTitle) {
                builder.setSpan(blackSpan, 0, index, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                builder.setSpan(redSpan, index, index + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                builder.setSpan(blackSpan, index + length, titleLength, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            } else {
                builder.setSpan(greenSpan, 0, index, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                builder.setSpan(redSpan, index, index + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                builder.setSpan(greenSpan, index + length, titleLength, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            }
            builder.setSpan(greenSpan, 0, index, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            builder.setSpan(redSpan, index, index + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(greenSpan, index + length, titleLength, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            view.setText(builder);
        } else {
            view.setText(changeString);
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
                                