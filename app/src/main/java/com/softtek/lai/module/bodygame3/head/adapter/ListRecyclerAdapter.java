package com.softtek.lai.module.bodygame3.head.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.module.bodygame3.head.model.PartnersModel;
import com.softtek.lai.module.community.adapter.PhotosAdapter;
import com.softtek.lai.module.community.eventModel.DeleteRecommedEvent;
import com.softtek.lai.module.community.model.PersonalListModel;
import com.softtek.lai.module.community.net.CommunityService;
import com.softtek.lai.module.community.view.HealthyDetailActivity;
import com.softtek.lai.module.community.view.PersionalActivity;
import com.softtek.lai.module.picture.view.PictureMoreActivity;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CustomGridView;
import com.squareup.picasso.Picasso;

import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by shelly.xu on 12/6/2016.
 */

public class ListRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 1;
    private static final int FOOTER = 2;
    private static final int EMPTY = 3;

    private boolean isFootGone = false;

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    private List<PartnersModel> partnersModels;
    private Context context;
    private int width;

    public ListRecyclerAdapter(Context mContext, List infos) {
        this.context = mContext;
        this.partnersModels = infos;
        width= DisplayUtil.getMobileWidth(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.partner_list, parent, false);
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        //绑定数据
        if (holder instanceof ViewHolder) {
            PartnersModel partnersModel = partnersModels.get(position);
            ((ViewHolder) holder).paiming.setText(partnersModel.getRanking());
            ((ViewHolder) holder).name_tv.setText(partnersModel.getStuName());
            if (partnersModel.getStuGender().equals("1")) {
                ((ViewHolder) holder).fale.setImageResource(R.drawable.female_iv);
            } else if (partnersModel.getStuGender().equals("0")) {
                ((ViewHolder) holder).fale.setImageResource(R.drawable.male_iv);
            } else if (partnersModel.getStuGender().equals("2")) {

            }
            ((ViewHolder) holder).group_tv.setText("("+partnersModel.getGroupName()+")");
            Log.e("photohost",AddressManager.get("photoHost") + partnersModel.getStuThImg());
                Picasso.with(context).load(AddressManager.get("photoHost") + partnersModel.getStuThImg())
                        .fit().error(R.drawable.img_default)
                        .placeholder(R.drawable.img_default).into(((ViewHolder) holder).head_img);

            ((ViewHolder) holder).weight_first.setText("初始体重" + partnersModel.getWeight() + "斤");
            ((ViewHolder) holder).jianzhong_tv.setText(partnersModel.getLoss());
            ((ViewHolder) holder).tv_bi.setText("减重比");
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      int position=holder.getAdapterPosition();
                        mOnItemClickListener.onItemClick(holder.itemView,position);
                    }
                });
            }
        }

    }

    private SpannableString getString(String value, int size1, int size2, int position) {
        SpannableString spannableString = new SpannableString(value);
        spannableString.setSpan(new AbsoluteSizeSpan(size1, true), 0, position, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan(size2, true), position, value.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    @Override
    public int getItemCount() {
        return partnersModels.size() == 0 ? 0 : partnersModels.size() + 1;
    }
    @Override
    public int getItemViewType(int position) {
        int type;
        if (position + 1 == getItemCount()) {
            type = getItemCount() < 10 ? EMPTY : FOOTER;
            if (isFootGone){
                type = EMPTY;
                isFootGone = false;
            }
        } else {
            type = ITEM;
        }
        return type;
    }

    public void setFootGone(boolean isGone){
        isFootGone = isGone;
    }


    private class ViewHolder extends RecyclerView.ViewHolder {
        TextView paiming;
        ImageView fale;
        TextView name_tv;
        TextView group_tv;
        TextView weight_first;
        TextView jianzhong_tv;
        TextView tv_bi;
        ImageView head_img;

        public ViewHolder(View view) {
            super(view);
            paiming = (TextView) itemView.findViewById(R.id.paiming);
            fale = (ImageView) itemView.findViewById(R.id.fale);
            name_tv = (TextView) itemView.findViewById(R.id.name_tv);
            group_tv = (TextView) itemView.findViewById(R.id.group_tv);
            weight_first = (TextView) itemView.findViewById(R.id.weight_first);
            jianzhong_tv = (TextView) itemView.findViewById(R.id.jianzhong_tv);
            tv_bi = (TextView) itemView.findViewById(R.id.tv_bi);
            head_img = (ImageView) itemView.findViewById(R.id.head_img);
        }
    }

    private class FooterHolder extends RecyclerView.ViewHolder {

        public FooterHolder(View view) {
            super(view);
        }
    }

    private class EmptyHolder extends RecyclerView.ViewHolder {

        public EmptyHolder(View view) {
            super(view);
        }
    }

    //定义点击接口
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}

