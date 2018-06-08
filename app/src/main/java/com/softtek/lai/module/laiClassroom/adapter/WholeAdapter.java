package com.softtek.lai.module.laiClassroom.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.laiClassroom.ArticdetailActivity;
import com.softtek.lai.module.laiClassroom.SubjectdetailActivity;
import com.softtek.lai.module.laiClassroom.VideoDetailActivity;
import com.softtek.lai.module.laiClassroom.model.Artical;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.widgets.CommentTextView;
import com.softtek.lai.widgets.RectangleImage;
import com.squareup.picasso.Picasso;

import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by jerry.guan on 3/10/2017.
 */

public class WholeAdapter extends BaseAdapter {

    private static final int VIDEO = 1;
    private static final int SINGLE_PIC = 2;
    private static final int MUTIL_PIC = 3;

    private Context context;
    private List<Artical> articals;

    public WholeAdapter(Context context, List<Artical> articals) {
        this.context = context;
        this.articals = articals;
    }

    @Override
    public int getCount() {
        return articals == null ? 0 : articals.size();
    }

    @Override
    public Object getItem(int position) {
        return articals == null ? null : articals.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        Artical artical = articals.get(position);
        return artical.getMediaType() == 2 ? VIDEO : artical.getMediaType() == 1 ? artical.getIsMultiPic() == 1 ? MUTIL_PIC : SINGLE_PIC : -1;
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int itemType = getItemViewType(position);
        if (itemType == VIDEO) {
            VideoHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.list_video, parent, false);
                holder = new VideoHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (VideoHolder) convertView.getTag();
            }
            dealWithVideo(articals.get(position), holder);

        } else if (itemType == SINGLE_PIC) {
            SinglePicHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.sigle_photo, parent, false);
                holder = new SinglePicHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (SinglePicHolder) convertView.getTag();
            }
            dealWithSingle(articals.get(position), holder);
        } else if (itemType == MUTIL_PIC) {
            MutilPicHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.many_photos, parent, false);
                holder = new MutilPicHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (MutilPicHolder) convertView.getTag();
            }
            dealWithMutil(articals.get(position), holder);
        } else {
            convertView = new View(context);
        }
        return convertView;
    }

    private void dealWithVideo(final Artical artical, VideoHolder holder) {
        holder.tv_title.setText(artical.getTitle());
        holder.tv_time.setText(artical.getVideoTime());
        holder.tv_relese.setText(getTime(artical.getCreateDate()));
        holder.tv_hotnum.setText(String.valueOf(artical.getClicks()));
        holder.tv_subject.setHighlightColor(ContextCompat.getColor(context, android.R.color.transparent));
        SpannableString ss = new SpannableString(artical.getTopic());
        ss.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(context, SubjectdetailActivity.class);
                intent.putExtra("topicId", artical.getTopicId());
                intent.putExtra("topictitle", artical.getTopic());
                context.startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(0xFF75BA2B);
                ds.setUnderlineText(false);//去除超链接的下划线
            }
        }, 0, ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        holder.tv_subject.setText(ss);
        holder.tv_subject.setMovementMethod(LinkMovementMethod.getInstance());
        String videoImage = null;
        if (artical.getArticImg() != null && !artical.getArticImg().isEmpty()) {
            videoImage = artical.getArticImg().get(0);
        }
        if (!TextUtils.isEmpty(videoImage)) {
            Picasso.with(context).load(AddressManager.get("photoHost") + videoImage)
                    .fit()
                    .placeholder(R.drawable.default_laiclass12)
                    .error(R.drawable.default_laiclass12)
                    .into(holder.iv_video);
        } else {
            Picasso.with(context).load(R.drawable.default_laiclass12)
                    .placeholder(R.drawable.default_laiclass12)
                    .error(R.drawable.default_laiclass12)
                    .into(holder.iv_video);
        }
        if (!TextUtils.isEmpty(artical.getArticUrl())) {
            final String finalVideoImage = videoImage;
            holder.iv_video.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转视频详情
                    Intent intent = new Intent(context, VideoDetailActivity.class);
                    intent.putExtra("articleId", artical.getArticleId());
                    intent.putExtra("cover", AddressManager.get("photoHost") + finalVideoImage);
                    intent.putExtra("videoUrl", AddressManager.get("photoHost") + artical.getArticUrl());
                    context.startActivity(intent);
                }
            });
        }
    }

    private void dealWithSingle(final Artical artical, SinglePicHolder holder) {
        holder.tv_title.setText(artical.getTitle());
        holder.tv_relese.setText(getTime(artical.getCreateDate()));
        holder.tv_hotnum.setText(String.valueOf(artical.getClicks()));
        holder.tv_subject.setHighlightColor(ContextCompat.getColor(context, android.R.color.transparent));
        SpannableString ss = new SpannableString(artical.getTopic());
        ss.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(context, SubjectdetailActivity.class);
                intent.putExtra("topictitle", artical.getTopic());
                intent.putExtra("topicId", artical.getTopicId());
                context.startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(0xFF75BA2B);
                ds.setUnderlineText(false);//去除超链接的下划线
            }
        }, 0, ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        holder.tv_subject.setText(ss);
        holder.tv_subject.setMovementMethod(LinkMovementMethod.getInstance());
        String videoImage = null;
        if (artical.getArticImg() != null && !artical.getArticImg().isEmpty()) {
            videoImage = artical.getArticImg().get(0);
        }
        if (!TextUtils.isEmpty(videoImage)) {
            Picasso.with(context).load(AddressManager.get("photoHost") + videoImage)
                    .fit()
                    .placeholder(R.drawable.default_laiclass12)
                    .error(R.drawable.default_laiclass12)
                    .into(holder.iv_single);
        } else {
            Picasso.with(context).load(R.drawable.default_laiclass12)
                    .placeholder(R.drawable.default_laiclass12)
                    .error(R.drawable.default_laiclass12)
                    .into(holder.iv_single);
        }
        holder.rl_single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转图文详情
                Intent intent = new Intent(context, ArticdetailActivity.class);
                intent.putExtra("articaltitle", artical.getTitle());
                intent.putExtra("articalUrl", artical.getArticUrl());
                context.startActivity(intent);
            }
        });
    }

    private void dealWithMutil(final Artical artical, MutilPicHolder holder) {
        holder.tv_title.setText(artical.getTitle());
        holder.tv_relese.setText(getTime(artical.getCreateDate()));
        holder.tv_hotnum.setText(String.valueOf(artical.getClicks()));
        holder.tv_subject.setHighlightColor(ContextCompat.getColor(context, android.R.color.transparent));
        SpannableString ss = new SpannableString(artical.getTopic());
        ss.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(context, SubjectdetailActivity.class);
                intent.putExtra("topicId", artical.getTopicId());
                intent.putExtra("topictitle", artical.getTopic());
                context.startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(0xFF75BA2B);
                ds.setUnderlineText(false);//去除超链接的下划线
            }
        }, 0, ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        holder.tv_subject.setText(ss);
        holder.tv_subject.setMovementMethod(LinkMovementMethod.getInstance());
        if (artical.getArticImg() != null && !artical.getArticImg().isEmpty()) {
            for (int i = 0; i < artical.getArticImg().size(); i++) {
                String imgUrl = artical.getArticImg().get(i);
                ImageView iv = null;
                switch (i) {
                    case 0:
                        iv = holder.iv_one;
                        break;
                    case 1:
                        iv = holder.iv_two;
                        break;
                    case 2:
                        iv = holder.iv_three;
                        break;
                }
                if (iv != null) {
                    if (!TextUtils.isEmpty(imgUrl)) {
                        Picasso.with(context).load(AddressManager.get("photoHost") + imgUrl)
                                .fit()
                                .placeholder(R.drawable.default_laiclass12)
                                .error(R.drawable.default_laiclass12)
                                .into(iv);
                    } else {
                        Picasso.with(context).load(R.drawable.default_laiclass12)
                                .placeholder(R.drawable.default_laiclass12)
                                .error(R.drawable.default_laiclass12)
                                .into(iv);
                    }
                }
            }
        } else {
            holder.lin_image.setVisibility(View.GONE);
        }

        holder.rl_onclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转图文详情
                Intent intent = new Intent(context, ArticdetailActivity.class);
                intent.putExtra("articaltitle", artical.getTitle());
                intent.putExtra("articalUrl", artical.getArticUrl());
                context.startActivity(intent);
            }
        });
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

    private static class VideoHolder {
        TextView tv_title;
        TextView tv_time;
        TextView tv_relese;
        TextView tv_hotnum;
        CommentTextView tv_subject;
        RectangleImage iv_video;

        private VideoHolder(View view) {
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_relese = (TextView) view.findViewById(R.id.tv_relese);
            tv_hotnum = (TextView) view.findViewById(R.id.tv_hotnum);
            tv_subject = (CommentTextView) view.findViewById(R.id.tv_subject);
            iv_video = (RectangleImage) view.findViewById(R.id.iv_video);
        }
    }

    private static class SinglePicHolder {
        TextView tv_title;
        TextView tv_relese;
        TextView tv_hotnum;
        ImageView iv_single;
        CommentTextView tv_subject;
        RelativeLayout rl_single;

        private SinglePicHolder(View view) {
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_relese = (TextView) view.findViewById(R.id.tv_relese);
            tv_hotnum = (TextView) view.findViewById(R.id.tv_hotnum);
            iv_single = (ImageView) view.findViewById(R.id.iv_single);
            rl_single = (RelativeLayout) view.findViewById(R.id.rl_single);
            tv_subject = (CommentTextView) view.findViewById(R.id.tv_subject);
        }
    }

    private static class MutilPicHolder {
        TextView tv_title;
        TextView tv_relese;
        TextView tv_hotnum;
        CommentTextView tv_subject;
        ImageView iv_one;
        ImageView iv_two;
        ImageView iv_three;
        LinearLayout lin_image;
        RelativeLayout rl_onclick;

        private MutilPicHolder(View view) {
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_relese = (TextView) view.findViewById(R.id.tv_relese);
            tv_hotnum = (TextView) view.findViewById(R.id.tv_hotnum);
            iv_one = (ImageView) view.findViewById(R.id.iv_one);
            iv_two = (ImageView) view.findViewById(R.id.iv_two);
            iv_three = (ImageView) view.findViewById(R.id.iv_three);
            lin_image = (LinearLayout) view.findViewById(R.id.lin_image);
            tv_subject = (CommentTextView) view.findViewById(R.id.tv_subject);
            rl_onclick = (RelativeLayout) view.findViewById(R.id.rl_onclick);
        }
    }
}
