package com.softtek.lai.module.community.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.community.eventModel.DeleteFocusEvent;
import com.softtek.lai.module.community.eventModel.FocusEvent;
import com.softtek.lai.module.community.eventModel.Where;
import com.softtek.lai.module.community.model.DynamicModel;
import com.softtek.lai.module.community.net.CommunityService;
import com.softtek.lai.module.community.view.PersionalActivity;
import com.softtek.lai.module.community.view.TopicDetailActivity;
import com.softtek.lai.module.picture.view.PictureMoreActivity;
import com.softtek.lai.picture.LookBigPicActivity;
import com.softtek.lai.picture.bean.EaluationPicBean;
import com.softtek.lai.picture.util.EvaluateUtil;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CircleImageView;
import com.softtek.lai.widgets.CustomGridView;
import com.softtek.lai.widgets.LinearLayoutManagerWrapper;
import com.softtek.lai.widgets.TextViewExpandableAnimation;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;

/**
 * 健康圈关注adapter
 * Created by John on 2016/4/14.
 */
public class HealthyCommunityFocusAdapter extends BaseAdapter {
    private HealthyCommunityAdapter.OperationCall call;
    private Context context;
    private List<DynamicModel> lossWeightStoryModels;
    private CommunityService service;
    private Object tag;


    public HealthyCommunityFocusAdapter(HealthyCommunityAdapter.OperationCall call,Object tag, Context context, List<DynamicModel> lossWeightStoryModels) {
        this.call=call;
        this.tag=tag;
        this.context = context;
        this.lossWeightStoryModels = lossWeightStoryModels;
        service = ZillaApi.NormalRestAdapter.create(CommunityService.class);
    }

    @Override
    public int getCount() {
        return lossWeightStoryModels.size();
    }

    @Override
    public Object getItem(int position) {
        return lossWeightStoryModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final DynamicModel model = lossWeightStoryModels.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.loss_weight_story_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_name.setText(model.getUserName());
        final String content=model.getContent();
        SpannableStringBuilder builder=new SpannableStringBuilder(content);
        if(model.getIsTopic()==1){
            /**
             * 0  1 2 3 4 5   6 7  8  9 10
             * 哈哈哈哈 # 金 彩 踢 馆 赛 #
             */
            String theme="#"+model.getThemeName()+"#";
            int from=0;
            int lastIndex=content.lastIndexOf("#");
            do {
                int firstIndex=content.indexOf("#",from);
                int nextIndex=firstIndex+model.getThemeName().length()+1;
                if(nextIndex<=lastIndex){
                    String sub=content.substring(firstIndex,nextIndex+1);
                    if(sub.equals(theme)){
                        builder.setSpan(new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                Intent intent=new Intent(context, TopicDetailActivity.class);
                                intent.putExtra("topicId",model.getTopicType());
                                context.startActivity(intent);
                            }

                            @Override
                            public void updateDrawState(TextPaint ds) {
                                super.updateDrawState(ds);
                                ds.setColor(0xFFFFA202);
                                ds.setUnderlineText(false);//去除超链接的下划线
                            }
                        }, firstIndex, nextIndex + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    }
                }
                from=nextIndex;
            }while (from<lastIndex);
        }
        holder.tv_content.getTextView().setHighlightColor(ContextCompat.getColor(context,android.R.color.transparent));
        holder.tv_content.setText(builder);
        holder.tv_content.getTextView().setMovementMethod(LinkMovementMethod.getInstance());
        holder.tv_content.resetState(true);
        long[] days=DateUtil.getInstance().getDaysForNow(model.getCreateDate());
        StringBuilder time=new StringBuilder();
        if(days[0]==0){//今天
            if (days[3]<60){//小于1分钟
                time.append("刚刚");
            }else if(days[3]>=60&&days[3]<3600){//>=一分钟小于一小时
                time.append(days[2]);
                time.append("分钟前");
            }else {//大于一小时
                time.append(days[1]);
                time.append("小时前");
            }
        }else if(days[0]==1) {//昨天
            time.append("昨天");
        }else {
            time.append(days[0]);
            time.append("天前");
        }
        holder.tv_date.setText(time);
        //关注
        holder.cb_focus.setVisibility(View.VISIBLE);
        //看一下是否被关注了
        holder.cb_focus.setChecked(true);
        holder.cb_focus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DeleteFocusEvent(String.valueOf(model.getAccountId())));
                EventBus.getDefault().post(new FocusEvent(String.valueOf(model.getAccountId()),0, Where.FOCUS_LIST));
                service.cancleFocusAccount(UserInfoModel.getInstance().getToken(),
                        UserInfoModel.getInstance().getUserId(),
                        model.getAccountId(),
                        new RequestCallback<ResponseData>() {
                            @Override
                            public void success(ResponseData responseData, Response response) {
                            }
                        });
            }
        });
        //点赞
        //如果没有人点赞就隐藏点咱人姓名显示
        if (0!=model.getPraiseNum()) {
            StringBuilder nameSet=new StringBuilder();
            for (int i=0,j=model.getUsernameSet().size();i<j;i++){
                nameSet.append(model.getUsernameSet().get(i));
                if(i<j-1){
                    nameSet.append(",");
                }
            }
            holder.tv_zan_name.setText(nameSet.toString());
            holder.tv_zan_name.setVisibility(View.VISIBLE);
        } else {
            holder.tv_zan_name.setVisibility(View.GONE);
        }
        //加载图片
        String path = AddressManager.get("photoHost");
        Picasso.with(context).load(path + model.getUserPhoto()).fit()
                .placeholder(R.drawable.img_default).error(R.drawable.img_default).into(holder.civ_header_image);
        holder.civ_header_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent personal=new Intent(context, PersionalActivity.class);
                personal.putExtra("isFocus",model.getIsFocus());
                personal.putExtra("personalId",String.valueOf(model.getAccountId()));
                personal.putExtra("personalName",model.getUserName());
                context.startActivity(personal);
            }
        });

        holder.photos.setAdapter(new PhotosAdapter(model.getThumbnailPhotoList(), context,tag));
        holder.photos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//                Intent in = new Intent(context, PictureMoreActivity.class);
//                in.putStringArrayListExtra("images", (ArrayList<String>) model.getPhotoList());
//                in.putExtra("position", position);
//                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(v, v.getWidth() / 2, v.getHeight() / 2, 0, 0);
//                ActivityCompat.startActivity(context, in, optionsCompat.toBundle());
                Intent intent = new Intent(context, LookBigPicActivity.class);
                Bundle bundle = new Bundle();
                List<EaluationPicBean> list= EvaluateUtil.setupCoords(context,(ImageView) v,model.getPhotoList(),position);
                bundle.putSerializable(LookBigPicActivity.PICDATALIST, (Serializable) list);
                intent.putExtras(bundle);
                intent.putExtra(LookBigPicActivity.CURRENTITEM, position);
                context.startActivity(intent);
                ((AppCompatActivity) context).overridePendingTransition(0,0);
            }
        });
        holder.iv_operator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(call!=null){
                    PopupWindow popupWindow=call.doOperation(model,holder.rl_item.getHeight(),position);
                    int width=popupWindow.getContentView().getMeasuredWidth();
                    int[] location = new int[2];
                    v.getLocationOnScreen(location);
                    popupWindow.showAtLocation(v, Gravity.NO_GRAVITY,  location[0]-width, location[1]);
                }
            }
        });
        CommentAdapter adapter=new CommentAdapter(context,model.getCommendsList());
        holder.rv_comment.setLayoutManager(new LinearLayoutManagerWrapper(context));
        holder.rv_comment.setAdapter(adapter);
        return convertView;

    }


    static class ViewHolder {
        CircleImageView civ_header_image;
        TextView tv_name, tv_date, tv_zan_name;
        LinearLayout  ll_content;
        CheckBox cb_focus;
        CustomGridView photos;
        RecyclerView rv_comment;
        ImageView iv_operator;
        View itemBottom;
        TextViewExpandableAnimation tv_content;
        RelativeLayout rl_item;

        public ViewHolder(View view) {
            civ_header_image = (CircleImageView) view.findViewById(R.id.civ_header_image);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            ll_content = (LinearLayout) view.findViewById(R.id.ll_content);
            tv_content= (TextViewExpandableAnimation) view.findViewById(R.id.tv_content);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
            tv_zan_name = (TextView) view.findViewById(R.id.tv_zan_name);
            photos = (CustomGridView) view.findViewById(R.id.photos);
            cb_focus = (CheckBox) view.findViewById(R.id.cb_focus);
            rv_comment= (RecyclerView) view.findViewById(R.id.rv_comment);
            iv_operator= (ImageView) view.findViewById(R.id.iv_operator);
            itemBottom=view.findViewById(R.id.item_bottom);
            rl_item= (RelativeLayout) view.findViewById(R.id.rl_item);
        }
    }

}
