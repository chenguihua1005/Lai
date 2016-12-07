package com.softtek.lai.module.bodygame3.head.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.head.model.PartnersModel;
import com.softtek.lai.module.community.adapter.PhotosAdapter;
import com.softtek.lai.module.community.eventModel.DeleteRecommedEvent;
import com.softtek.lai.module.community.model.PersonalListModel;
import com.softtek.lai.module.community.net.CommunityService;
import com.softtek.lai.module.community.view.HealthyDetailActivity;
import com.softtek.lai.module.community.view.PersionalActivity;
import com.softtek.lai.module.picture.view.PictureMoreActivity;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CustomGridView;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;

/**
 * Created by shelly.xu on 12/6/2016.
 */

public class ListRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private static final int ITEM = 1;
    private static final int FOOTER = 2;
    private static final int EMPTY = 3;

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    private List<PartnersModel> partnersModels;
    private Context context;
    private boolean isMine;

    public ListRecyclerAdapter(Context mContext, List infos) {
        this.context = mContext;
        this.partnersModels = infos;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.partner_list, parent, false);
            view.setOnClickListener(this);
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //绑定数据
        if (holder instanceof ViewHolder) {
            Log.i("试图加载。/。。。。。。。。。。。。。。。。。。。。。。。。");
            PartnersModel partnersModel=partnersModels.get(position);
            ((ViewHolder) holder).paiming.setText(partnersModel.getRanking());
            ((ViewHolder) holder).name_tv.setText(partnersModel.getStuName());
            if (partnersModel.getStuGender().equals("1")) {
                ((ViewHolder) holder).fale.setImageResource(R.drawable.female_iv);
            } else if (partnersModel.getStuGender().equals("0")) {
                ((ViewHolder) holder).fale.setImageResource(R.drawable.male_iv);
            } else if (partnersModel.getStuGender().equals("2")) {

            }
            ((ViewHolder) holder).group_tv.setText(partnersModel.getGroupName());
            if(TextUtils.isEmpty(partnersModel.getStuThImg())){
                Picasso.with(context).load(R.drawable.img_default).into(((ViewHolder) holder).head_img);
            }else {
                Picasso.with(context).load(AddressManager.get("photoHost")+partnersModel.getStuThImg())
                        .fit().error(R.drawable.img_default)
                        .placeholder(R.drawable.img_default).into(((ViewHolder) holder).head_img);
            }
            ((ViewHolder) holder).weight_first.setText("初始体重"+partnersModel.getWeight()+"斤");
            ((ViewHolder) holder).jianzhong_tv.setText(partnersModel.getLoss());
            ((ViewHolder) holder).tv_bi.setText("减重比");
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
            type = getItemCount() < 6 ? EMPTY : FOOTER;
        } else {
            type = ITEM;
        }
        return type;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v,(PartnersModel) v.getTag());
        }

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
            paiming=(TextView)itemView.findViewById(R.id.paiming);
            fale=(ImageView)itemView.findViewById(R.id.fale);
            name_tv=(TextView)itemView.findViewById(R.id.name_tv);
            group_tv=(TextView)itemView.findViewById(R.id.group_tv);
            weight_first=(TextView)itemView.findViewById(R.id.weight_first);
            jianzhong_tv=(TextView)itemView.findViewById(R.id.jianzhong_tv);
            tv_bi=(TextView)itemView.findViewById(R.id.tv_bi);
            head_img=(ImageView)itemView.findViewById(R.id.head_img);
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
        void onItemClick(View view, PartnersModel position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}

