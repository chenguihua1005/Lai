/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.community.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.community.eventModel.DeleteRecommedEvent;
import com.softtek.lai.module.community.eventModel.Where;
import com.softtek.lai.module.community.model.PersonalListModel;
import com.softtek.lai.module.community.model.TopicList;
import com.softtek.lai.module.community.net.CommunityService;
import com.softtek.lai.module.community.view.DynamicDetailActivity;
import com.softtek.lai.module.community.view.PersionalActivity;
import com.softtek.lai.module.community.view.TopicDetailActivity;
import com.softtek.lai.picture.LookBigPicActivity;
import com.softtek.lai.picture.bean.EaluationPicBean;
import com.softtek.lai.picture.util.EvaluateUtil;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CustomGridView;
import com.softtek.lai.widgets.TextViewExpandableAnimation;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 *
 * Created by John on 2016/3/27.
 */
public class DynamicRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{

    private static final int ITEM=1;
    private static final int FOOTER=2;
    private static final int EMPTY=3;

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    private List<PersonalListModel> infos;
    private Context context;
    private boolean isMine;

    public DynamicRecyclerViewAdapter(Context mContext, List infos,boolean isMine) {
        this.context=mContext;
        this.infos = infos;
        this.isMine=isMine;
    }

    @Override
    public RecyclerView.ViewHolder  onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==ITEM){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.persional_dynamic_item, parent, false);
            view.setOnClickListener(this);
            return new ViewHolder(view);
        }else if(viewType==FOOTER){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.up_load_view, parent, false);
            return new FooterHolder(view);
        }else if(viewType==EMPTY){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_view, parent, false);
            return new EmptyHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //绑定数据
        if(holder instanceof ViewHolder){
            final PersonalListModel model=infos.get(position);
            ((ViewHolder) holder).tv_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("0".equals(model.getMinetype())) {//动态
                        Intent logDetail = new Intent(context, DynamicDetailActivity.class);
                        logDetail.putExtra("dynamicId", model.getID());
                        context.startActivity(logDetail);
                    }
                }
            });
            final String content=model.getContent();
            SpannableStringBuilder builder=new SpannableStringBuilder(content);
            if(model.getIsTopic()==1&&model.getTopicList()!=null){
                /**
                 * 0  1 2 3 4 5   6 7  8  9 10
                 * 哈哈哈哈 # 金 彩 踢 馆 赛 #
                 */
                int from=0;
                int lastIndex=content.lastIndexOf("#");
                do {
                    //先获取第一个#号出现的下标
                    int firstIndex=content.indexOf("#",from);
                    //然后获取下一个#号出现的位置
                    int next=content.indexOf("#",firstIndex+1);
                    if(next==-1){
                        break;
                    }
                    //截取两个#号之间的字符
                    String sub=content.substring(firstIndex+1,next);
                    //将开始下标移动至下一个#号出现的位置
                    from=next;
                    for (final TopicList topic:model.getTopicList()){
                        if(sub.equals(topic.getTopicName())){
                            from=next+1;
                            builder.setSpan(new ClickableSpan() {
                                @Override
                                public void onClick(View widget) {
                                    Intent intent=new Intent(context, TopicDetailActivity.class);
                                    intent.putExtra("topicId",topic.getTopicType());
                                    context.startActivity(intent);
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setColor(0xFFFFA202);
                                    ds.setUnderlineText(false);//去除超链接的下划线
                                }
                            }, firstIndex, next+1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                            break;
                        }
                    }
                }while (from<lastIndex);
            }
            ((ViewHolder) holder).tv_content.getTextView().setHighlightColor(ContextCompat.getColor(context,android.R.color.transparent));
            ((ViewHolder) holder).tv_content.setText(builder);
            ((ViewHolder) holder).tv_content.getTextView().setMovementMethod(LinkMovementMethod.getInstance());
            ((ViewHolder) holder).tv_content.setOnStateChangeListener(new TextViewExpandableAnimation.OnStateChangeListener() {
                @Override
                public void onStateChange(boolean isShrink) {
                    model.setOpen(!isShrink);
                }
            });
            ((ViewHolder) holder).tv_content.resetState(!model.isOpen());
            int[] dates=DateUtil.getInstance().getDates(model.getCreateDate());
            if(position==0){//第一条
                ((ViewHolder) holder).tv_month.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).tv_month.setText(getString(dates[2]+" "+dates[1]+"月",18,11,2));
                if(position+1<infos.size()){
                    PersonalListModel next=infos.get(position+1);
                    int[] ndates=DateUtil.getInstance().getDates(next.getCreateDate());
                    if(dates[1]==ndates[1]&&dates[2]==ndates[2]){//下一条和当前是同一天
                        ((ViewHolder) holder).tv_time.setVisibility(View.VISIBLE);
                    }else{
                        ((ViewHolder) holder).tv_time.setVisibility(View.GONE);
                    }
                }else {
                    ((ViewHolder) holder).tv_time.setVisibility(View.VISIBLE);
                }
            }else {
                PersonalListModel previous=infos.get(position-1);
                int[] pdates=DateUtil.getInstance().getDates(previous.getCreateDate());
                //判断两条数据是否是同一天
                if(dates[1]==pdates[1]&&dates[2]==pdates[2]){//同一天
                    ((ViewHolder) holder).tv_month.setVisibility(View.GONE);
                    ((ViewHolder) holder).tv_time.setVisibility(View.VISIBLE);
                }else {
                    ((ViewHolder) holder).tv_month.setVisibility(View.VISIBLE);
                    ((ViewHolder) holder).tv_month.setText(getString(dates[2]+" "+dates[1]+"月",18,11,2));
                    if(position+1<infos.size()){
                        PersonalListModel next=infos.get(position+1);
                        int[] ndates=DateUtil.getInstance().getDates(next.getCreateDate());
                        if(dates[1]==ndates[1]&&dates[2]==ndates[2]){//下一条和当前是同一天
                            ((ViewHolder) holder).tv_time.setVisibility(View.VISIBLE);
                        }else{
                            ((ViewHolder) holder).tv_time.setVisibility(View.GONE);
                        }
                    }else {
                        ((ViewHolder) holder).tv_time.setVisibility(View.GONE);
                    }
                }
            }
            ((ViewHolder) holder).tv_time.setText(dates[6]==0?"上午":"下午");
            ((ViewHolder) holder).tv_time.append(dates[3]+":"+(dates[4]<10?"0"+dates[4]:dates[4]));
            final List<String> listImg=Arrays.asList(model.getImgCollection().split(","));
            ((ViewHolder) holder).photos.setAdapter(new PhotosAdapter(listImg, context,new Object()));
            ((ViewHolder) holder).photos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    Intent intent = new Intent(context, LookBigPicActivity.class);
                    Bundle bundle = new Bundle();
                    List<EaluationPicBean> list= EvaluateUtil.setupCoords(context,(ImageView) v,listImg,position);
                    bundle.putSerializable(LookBigPicActivity.PICDATALIST, (Serializable) list);
                    intent.putExtras(bundle);
                    intent.putExtra(LookBigPicActivity.CURRENTITEM, position);
                    context.startActivity(intent);
                    ((AppCompatActivity) context).overridePendingTransition(0,0);
                }
            });
            if(isMine&&"0".equals(model.getMinetype())){
                ((ViewHolder) holder).tv_delete.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).tv_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(context).setTitle("温馨提示").setMessage("确定删除吗？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ZillaApi.NormalRestAdapter.create(CommunityService.class)
                                                .deleteHealth(UserInfoModel.getInstance().getToken(), model.getID(),
                                                new RequestCallback<ResponseData>() {
                                                    @Override
                                                    public void success(ResponseData responseData, Response response) {
                                                        if (responseData.getStatus() == 200) {
                                                            EventBus.getDefault().post(new DeleteRecommedEvent(model.getID(), Where.PERSONAL_DYNAMIC_LIST));
                                                            infos.remove(model);
                                                            notifyDataSetChanged();
                                                            if(context instanceof PersionalActivity){
                                                                PersionalActivity activity= (PersionalActivity) context;
                                                                activity.updateNum();
                                                            }
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
            }else {
                ((ViewHolder) holder).tv_delete.setVisibility(View.GONE);
            }

            //将数据保存在itemView的Tag中，以便点击时进行获取
            /*holder.itemView.setTag(position);*/
        }

    }

    private SpannableString getString(String value, int size1, int size2,int position) {
        SpannableString spannableString = new SpannableString(value);
        spannableString.setSpan(new AbsoluteSizeSpan(size1,true), 0, position, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan(size2,true), position, value.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    @Override
    public int getItemCount() {
        return infos.size()==0?0:infos.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        int type;
        if(position+1==getItemCount()){
            type=getItemCount()<6?EMPTY:FOOTER;
        }else{
            type=ITEM;
        }
        return type;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v,(Integer) v.getTag());
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_month,tv_time,tv_delete;
        public CustomGridView photos;
        public TextViewExpandableAnimation tv_content;

        public ViewHolder(View view) {
            super(view);
            tv_month= (TextView) view.findViewById(R.id.tv_month);
            tv_content= (TextViewExpandableAnimation) view.findViewById(R.id.tv_content);
            tv_time= (TextView) view.findViewById(R.id.tv_time);
            tv_delete= (TextView) view.findViewById(R.id.tv_delete);
            photos= (CustomGridView) view.findViewById(R.id.photos);
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