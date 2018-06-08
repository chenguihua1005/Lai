package com.softtek.lai.module.laiClassroom.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.softtek.lai.module.laiClassroom.model.CollectlistModel;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.widgets.RectangleImage;
import com.squareup.picasso.Picasso;

import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by shelly.xu on 3/10/2017.
 */

public class CollectAdapter extends BaseAdapter {

    private List<CollectlistModel> collectlistModels;
    private Context mContext;
    private static final int SINGle = 0;
    private static final int MANY = 1;
    private static final int VIDEO = 2;
    private static final int TYPE_COUNT = 3;

    public CollectAdapter(Context context, List<CollectlistModel> collectlistModelList) {
        this.mContext = context;
        this.collectlistModels = collectlistModelList;
    }


    @Override
    public int getCount() {
        return collectlistModels == null ? 0 : collectlistModels.size();
    }

    @Override
    public Object getItem(int position) {
        return collectlistModels == null ? null : collectlistModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderOne viewHolderOne;
        ViewHolderTwo viewHolderTwo;
        ViewHolderThree viewHolderThree;
        final CollectlistModel collectModel = collectlistModels.get(position);
        int currenttype = getItemViewType(position);
        if (currenttype == VIDEO) {//视频
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.list_video, parent, false);
                viewHolderOne = new ViewHolderOne();
                viewHolderOne.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                viewHolderOne.iv_video = (RectangleImage) convertView.findViewById(R.id.iv_video);
                viewHolderOne.tv_relese = (TextView) convertView.findViewById(R.id.tv_relese);
                viewHolderOne.tv_hotnum = (TextView) convertView.findViewById(R.id.tv_hotnum);
                viewHolderOne.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
                viewHolderOne.tv_subject = (TextView) convertView.findViewById(R.id.tv_subject);
                convertView.setTag(viewHolderOne);
            } else {
                viewHolderOne = (ViewHolderOne) convertView.getTag();
            }
            viewHolderOne.tv_title.setText(collectModel.getTitle());
            viewHolderOne.tv_hotnum.setText(String.valueOf(collectModel.getClicks()));
            if (collectModel.getArticImg() != null && !collectModel.getArticImg().isEmpty()) {
                Picasso.with(mContext).load(AddressManager.get("photoHost") + collectModel.getArticImg().get(0))
                        .placeholder(R.drawable.default_laiclass12)
                        .error(R.drawable.default_laiclass12)
                        .into(viewHolderOne.iv_video);
            } else {
                Picasso.with(mContext).load(R.drawable.default_laiclass12)
                        .placeholder(R.drawable.default_laiclass12)
                        .error(R.drawable.default_laiclass12)
                        .into(viewHolderOne.iv_video);
            }
            SpannableString ss = new SpannableString(collectModel.getTopic());
            ss.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    Intent intent = new Intent(mContext, SubjectdetailActivity.class);
                    intent.putExtra("topicId", collectModel.getTopicId());
                    intent.putExtra("topictitle", collectModel.getTopic());
                    mContext.startActivity(intent);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(0xFF75BA2B);
                    ds.setUnderlineText(false);//去除超链接的下划线
                }
            }, 0, ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            viewHolderOne.tv_subject.setText(ss);
            viewHolderOne.tv_subject.setMovementMethod(LinkMovementMethod.getInstance());
            viewHolderOne.tv_relese.setText(getTime(collectModel.getCreateDate()));//日期
            viewHolderOne.tv_time.setText(collectModel.getVideoTime());
            viewHolderOne.iv_video.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, VideoDetailActivity.class);
                    intent.putExtra("articleId", collectModel.getArticleId());
                    intent.putExtra("cover", AddressManager.get("photoHost") + collectModel.getArticImg().get(0));
                    intent.putExtra("videoUrl", AddressManager.get("photoHost") + collectModel.getArticUrl());
                    mContext.startActivity(intent);
                }
            });

        } else if (currenttype == SINGle) {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.sigle_photo, parent, false);
                viewHolderTwo = new ViewHolderTwo();
                viewHolderTwo.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                viewHolderTwo.tv_relese = (TextView) convertView.findViewById(R.id.tv_relese);
                viewHolderTwo.tv_hotnum = (TextView) convertView.findViewById(R.id.tv_hotnum);
                viewHolderTwo.iv_single = (ImageView) convertView.findViewById(R.id.iv_single);
                viewHolderTwo.tv_subject = (TextView) convertView.findViewById(R.id.tv_subject);
                viewHolderTwo.rl_single = (RelativeLayout) convertView.findViewById(R.id.rl_single);
                convertView.setTag(viewHolderTwo);
            } else {
                viewHolderTwo = (ViewHolderTwo) convertView.getTag();
            }
            viewHolderTwo.tv_title.setText(collectModel.getTitle());
            viewHolderTwo.tv_hotnum.setText(String.valueOf(collectModel.getClicks()));
            if (collectModel.getArticImg() != null && !collectModel.getArticImg().isEmpty()) {
                Picasso.with(mContext).load(AddressManager.get("photoHost") + collectModel.getArticImg().get(0))
                        .placeholder(R.drawable.default_laiclass12)
                        .error(R.drawable.default_laiclass12)
                        .into(viewHolderTwo.iv_single);
            } else {
                Picasso.with(mContext).load(R.drawable.default_laiclass12)
                        .placeholder(R.drawable.default_laiclass12)
                        .error(R.drawable.default_laiclass12)
                        .into(viewHolderTwo.iv_single);
            }
            SpannableString ss = new SpannableString(collectModel.getTopic());
            ss.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    Intent intent = new Intent(mContext, SubjectdetailActivity.class);
                    intent.putExtra("topicId", collectModel.getTopicId());
                    intent.putExtra("topictitle", collectModel.getTopic());
                    mContext.startActivity(intent);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(0xFF75BA2B);
                    ds.setUnderlineText(false);//去除超链接的下划线
                }
            }, 0, ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            viewHolderTwo.tv_subject.setText(ss);
            viewHolderTwo.tv_subject.setMovementMethod(LinkMovementMethod.getInstance());
            viewHolderTwo.tv_relese.setText(getTime(collectModel.getCreateDate()));//日期
            viewHolderTwo.rl_single.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转图文详情
                    Intent intent = new Intent(mContext, ArticdetailActivity.class);
                    intent.putExtra("articaltitle", collectModel.getTitle());
                    intent.putExtra("articalUrl", collectModel.getArticUrl());
                    mContext.startActivity(intent);

                }
            });
        } else if (currenttype == MANY) {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.many_photos, parent, false);
                viewHolderThree = new ViewHolderThree();
                viewHolderThree.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                viewHolderThree.iv_one = (ImageView) convertView.findViewById(R.id.iv_one);
                viewHolderThree.iv_two = (ImageView) convertView.findViewById(R.id.iv_two);
                viewHolderThree.iv_three = (ImageView) convertView.findViewById(R.id.iv_three);
                viewHolderThree.tv_release = (TextView) convertView.findViewById(R.id.tv_relese);
                viewHolderThree.tv_hotnum = (TextView) convertView.findViewById(R.id.tv_hotnum);
                viewHolderThree.tv_subject = (TextView) convertView.findViewById(R.id.tv_subject);
                viewHolderThree.lin_image = (LinearLayout) convertView.findViewById(R.id.lin_image);
                viewHolderThree.rl_onclick = (RelativeLayout) convertView.findViewById(R.id.rl_onclick);
                convertView.setTag(viewHolderThree);
            } else {
                viewHolderThree = (ViewHolderThree) convertView.getTag();
            }
            viewHolderThree.tv_title.setText(collectModel.getTitle());
            viewHolderThree.tv_hotnum.setText(String.valueOf(collectModel.getClicks()));
            SpannableString ss = new SpannableString(collectModel.getTopic());
            ss.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    Intent intent = new Intent(mContext, SubjectdetailActivity.class);
                    intent.putExtra("topicId", collectModel.getTopicId());
                    intent.putExtra("topictitle", collectModel.getTopic());
                    mContext.startActivity(intent);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(0xFF75BA2B);
                    ds.setUnderlineText(false);//去除超链接的下划线
                }
            }, 0, ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            viewHolderThree.tv_subject.setText(ss);
            viewHolderThree.tv_subject.setMovementMethod(LinkMovementMethod.getInstance());
            if (collectModel.getArticImg() != null && !collectModel.getArticImg().isEmpty()) {
                for (int i = 0; i < collectModel.getArticImg().size(); i++) {
                    String imgUrl = collectModel.getArticImg().get(i);
                    ImageView iv = null;
                    switch (i) {
                        case 0:
                            iv = viewHolderThree.iv_one;
                            break;
                        case 1:
                            iv = viewHolderThree.iv_two;
                            break;
                        case 2:
                            iv = viewHolderThree.iv_three;
                            break;
                    }
                    if (iv != null) {
                        if (!TextUtils.isEmpty(imgUrl)) {
                            Picasso.with(mContext).load(AddressManager.get("photoHost") + imgUrl)
                                    .fit()
                                    .placeholder(R.drawable.default_laiclass12)
                                    .error(R.drawable.default_laiclass12)
                                    .into(iv);
                        } else {
                            Picasso.with(mContext).load(R.drawable.default_laiclass12)
                                    .placeholder(R.drawable.default_laiclass12)
                                    .error(R.drawable.default_laiclass12)
                                    .into(iv);
                        }
                    }
                }
            } else {
                viewHolderThree.lin_image.setVisibility(View.GONE);
            }
            viewHolderThree.tv_release.setText(getTime(collectModel.getCreateDate()));//日期

            viewHolderThree.rl_onclick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转图文详情
                    Intent intent = new Intent(mContext, ArticdetailActivity.class);
                    intent.putExtra("articaltitle", collectModel.getTitle());
                    intent.putExtra("articalUrl", collectModel.getArticUrl());
                    mContext.startActivity(intent);
                }
            });
        }

        return convertView;
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

    @Override
    public int getItemViewType(int position) {
        int type;
        type = collectlistModels.get(position).getMediaType();
        if (type == 2) {
            return VIDEO;
        } else if (type == 1) {
            if (collectlistModels.get(position).getIsMultiPic() == 1) {
                return MANY;
            } else if (collectlistModels.get(position).getIsMultiPic() == 0) {
                return SINGle;
            }
        }
        return -1;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    private static class ViewHolderOne {
        TextView tv_title;
        ImageView iv_video;
        TextView tv_relese;
        TextView tv_hotnum;
        TextView tv_time;
        TextView tv_subject;

    }

    private static class ViewHolderTwo {
        TextView tv_title;
        TextView tv_relese;
        TextView tv_hotnum;
        ImageView iv_single;
        TextView tv_subject;
        RelativeLayout rl_single;
    }

    private static class ViewHolderThree {
        TextView tv_title;
        ImageView iv_one;
        ImageView iv_two;
        ImageView iv_three;
        TextView tv_release;
        TextView tv_hotnum;
        TextView tv_subject;
        LinearLayout lin_image;
        RelativeLayout rl_onclick;
    }
}
