package com.softtek.lai.module.laiClassroom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.bodygame3.conversation.adapter.ChatContantAdapter;
import com.softtek.lai.module.laiClassroom.model.CollectlistModel;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.widgets.RectangleImage;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;

/**
 * Created by shelly.xu on 3/10/2017.
 */

public class CollectAdapter extends BaseAdapter {

    private List<CollectlistModel> collectlistModels;
    Context mContext;
    public static final int SINGle = 0;
    public static final int MANY = 1;
    public static final int VIDEO = 2;
     public static final int TYPE_COUNT=3;
    private int currenttype;

    public CollectAdapter(Context context, List<CollectlistModel> collectlistModelList) {
        this.mContext = context;
        this.collectlistModels = collectlistModelList;
    }

    public void updateCollect(List<CollectlistModel> collectlistModelList) {
        this.collectlistModels = collectlistModelList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return collectlistModels.size();
    }

    @Override
    public Object getItem(int position) {
        return collectlistModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderOne viewHolderOne = null;
        ViewHolderTwo viewHolderTwo = null;
        ViewHolderThree viewHolderThree = null;
        CollectlistModel collectModel = collectlistModels.get(position);
        currenttype = getItemViewType(position);
        if (currenttype == VIDEO) {//视频
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.list_video, parent,false);
                viewHolderOne = new ViewHolderOne();
                viewHolderOne.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                viewHolderOne.iv_video = (RectangleImage) convertView.findViewById(R.id.iv_video);
                viewHolderOne.tv_relese = (TextView) convertView.findViewById(R.id.tv_relese);
                viewHolderOne.tv_hotnum = (TextView) convertView.findViewById(R.id.tv_hotnum);
                viewHolderOne.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
                convertView.setTag(viewHolderOne);
            } else {
                viewHolderOne = (ViewHolderOne) convertView.getTag();
            }
            viewHolderOne.tv_title.setText(collectModel.getTitle());
            viewHolderOne.tv_hotnum.setText(collectModel.getClicks() + "");
            Picasso.with(mContext).load(AddressManager.get("photoHost") + collectModel.getArticImg().get(0)).into(viewHolderOne.iv_video);
            String time = "";
            long[] days = DateUtil.getInstance().getDaysForNow(collectModel.getCreateDate());
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
            viewHolderOne.tv_relese.setText(time);//日期
            viewHolderOne.tv_time.setText(collectModel.getVideoTime());

        } else if (currenttype == SINGle) {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.sigle_photo, parent,false);
                viewHolderTwo = new ViewHolderTwo();
                viewHolderTwo.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                viewHolderTwo.tv_relese = (TextView) convertView.findViewById(R.id.tv_relese);
                viewHolderTwo.tv_hotnum = (TextView) convertView.findViewById(R.id.tv_hotnum);
                viewHolderTwo.iv_single = (ImageView) convertView.findViewById(R.id.iv_single);
                convertView.setTag(viewHolderTwo);
            } else {
                viewHolderTwo = (ViewHolderTwo) convertView.getTag();
            }
            viewHolderTwo.tv_title.setText(collectModel.getTitle());
            viewHolderTwo.tv_hotnum.setText(collectModel.getClicks() + "");
            Picasso.with(mContext).load(AddressManager.get("photoHost") + collectModel.getArticImg().get(0)).into(viewHolderTwo.iv_single);
            String time = "";
            long[] days = DateUtil.getInstance().getDaysForNow(collectModel.getCreateDate());
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
            viewHolderTwo.tv_relese.setText(time);//日期

        } else if (currenttype == MANY) {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.many_photos, parent,false);
                viewHolderThree = new ViewHolderThree();
                viewHolderThree.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                viewHolderThree.iv_one = (ImageView) convertView.findViewById(R.id.iv_one);
                viewHolderThree.iv_two = (ImageView) convertView.findViewById(R.id.iv_two);
                viewHolderThree.iv_three = (ImageView) convertView.findViewById(R.id.iv_three);
                viewHolderThree.tv_release = (TextView) convertView.findViewById(R.id.tv_release);
                viewHolderThree.tv_hotnum = (TextView) convertView.findViewById(R.id.tv_hotnum);
                convertView.setTag(viewHolderThree);
            } else {
                viewHolderThree = (ViewHolderThree) convertView.getTag();
            }
            viewHolderThree.tv_title.setText(collectModel.getTitle());
            viewHolderThree.tv_hotnum.setText(collectModel.getClicks() + "");
            Picasso.with(mContext).load(AddressManager.get("photoHost") + collectModel.getArticImg().get(0)).into(viewHolderThree.iv_one);
            Picasso.with(mContext).load(AddressManager.get("photoHost") + collectModel.getArticImg().get(1)).into(viewHolderThree.iv_two);
            Picasso.with(mContext).load(AddressManager.get("photoHost") + collectModel.getArticImg().get(2)).into(viewHolderThree.iv_three);
            String time = "";
            long[] days = DateUtil.getInstance().getDaysForNow(collectModel.getCreateDate());
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
            viewHolderThree.tv_release.setText(time);//日期
        }

        return convertView;
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

    class ViewHolderOne {
        TextView tv_title;
        ImageView iv_video;
        TextView tv_relese;
        TextView tv_hotnum;
        TextView tv_time;

    }

    class ViewHolderTwo {
        TextView tv_title;
        TextView tv_relese;
        TextView tv_hotnum;
        ImageView iv_single;
    }

    class ViewHolderThree {
        TextView tv_title;
        ImageView iv_one;
        ImageView iv_two;
        ImageView iv_three;
        TextView tv_release;
        TextView tv_hotnum;
    }
}
