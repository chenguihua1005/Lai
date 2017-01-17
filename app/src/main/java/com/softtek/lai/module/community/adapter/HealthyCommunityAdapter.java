package com.softtek.lai.module.community.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.community.eventModel.DeleteFocusEvent;
import com.softtek.lai.module.community.eventModel.FocusReload;
import com.softtek.lai.module.community.eventModel.RefreshRecommedEvent;
import com.softtek.lai.module.community.eventModel.ZanEvent;
import com.softtek.lai.module.community.model.DoZan;
import com.softtek.lai.module.community.model.HealthyCommunityModel;
import com.softtek.lai.module.community.model.HealthyDynamicModel;
import com.softtek.lai.module.community.net.CommunityService;
import com.softtek.lai.module.community.view.HealthyDetailActivity;
import com.softtek.lai.module.community.view.PersionalActivity;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.module.picture.view.PictureMoreActivity;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.utils.StringUtil;
import com.softtek.lai.widgets.CircleImageView;
import com.softtek.lai.widgets.CustomGridView;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;

/**
 * Created by John on 2016/4/14.
 *
 */
public class HealthyCommunityAdapter extends BaseAdapter {

    private Context context;
    private Fragment fragment;
    private List<HealthyCommunityModel> lossWeightStoryModels;
    private CommunityService service;
    private static final int LIST_JUMP = 1;
    String path = AddressManager.get("photoHost");
    int px;

    public HealthyCommunityAdapter(Fragment fragment, Context context, List<HealthyCommunityModel> lossWeightStoryModels) {
        this.fragment = fragment;
        this.context = context;
        this.lossWeightStoryModels = lossWeightStoryModels;
        service = ZillaApi.NormalRestAdapter.create(CommunityService.class);
        px= DisplayUtil.dip2px(context,45);
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
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final int pos = position;
        final HealthyCommunityModel model = lossWeightStoryModels.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.loss_weight_story_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //设置详情内容跳转
        holder.ll_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("0".equals(model.getMinetype())) {//动态
                    Intent logDetail = new Intent(context, HealthyDetailActivity.class);
                    logDetail.putExtra("dynamicModel", copyModeltoDynamci(model));
                    logDetail.putExtra("position", pos);
                    logDetail.putExtra("type", "1");
                    fragment.startActivityForResult(logDetail, LIST_JUMP);
                }
            }
        });
        holder.tv_name.setText(model.getUserName());
        holder.tv_content.setText(model.getContent());
        long[] days=DateUtil.getInstance().getDaysForNow(model.getCreateDate());
        String time="";
        if(days[0]==0){//今天
            if (days[3]<60){//小于1分钟
                time="刚刚";
            }else if(days[3]>=60&&days[3]<3600){//>=一分钟小于一小时
                time=days[2]+"分钟前";
            }else {//大于一小时
                time=days[1]+"小时前";
            }
        }else if(days[0]==1) {//昨天
            time="昨天";
        }else {
            time=days[0]+"天前";
        }
        holder.tv_date.setText(time);
        holder.tv_zan_name.setText(model.getUsernameSet());
        boolean isMine=Long.parseLong(TextUtils.isEmpty(model.getAccountId())?"0":model.getAccountId()) == UserInfoModel.getInstance().getUserId();
        //如果是自己的则隐藏关注按钮
        if(isMine){
            holder.cb_focus.setVisibility(View.GONE);
        }else {
            holder.cb_focus.setVisibility(View.VISIBLE);
            //看一下是否被关注了
            if (model.getIsFocus() == 0) {
                holder.cb_focus.setChecked(false);//未关注
            } else {
                holder.cb_focus.setChecked(true);//已关注
            }
            holder.cb_focus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkVr()) {
                        holder.cb_focus.setChecked(false);
                    } else {
                        if (holder.cb_focus.isChecked()) {
                            EventBus.getDefault().post(new RefreshRecommedEvent(model.getAccountId(),1));
                            service.focusAccount(UserInfoModel.getInstance().getToken(),
                                    UserInfoModel.getInstance().getUserId(),
                                    Long.parseLong(model.getAccountId()),
                                    new RequestCallback<ResponseData>() {
                                        @Override
                                        public void success(ResponseData responseData, Response response) {
                                            if(responseData.getStatus()==200){
                                                EventBus.getDefault().post(new FocusReload());
                                            }
                                        }
                                    });
                        } else {
                            EventBus.getDefault().post(new RefreshRecommedEvent(model.getAccountId(),0));
                            EventBus.getDefault().post(new DeleteFocusEvent(model.getAccountId()));
                            service.cancleFocusAccount(UserInfoModel.getInstance().getToken(),
                                    UserInfoModel.getInstance().getUserId(),
                                    Long.parseLong(model.getAccountId()),
                                    new RequestCallback<ResponseData>() {
                                        @Override
                                        public void success(ResponseData responseData, Response response) {

                                        }
                                    });


                        }
                    }
                }
            });
        }
        //删除按钮
        //如果不是自己的
        holder.cb_zan.setText(model.getPraiseNum());
        if ( !isMine|| "1".equals(model.getMinetype())) {
            holder.tv_delete.setVisibility(View.GONE);//隐藏删除按钮
        } else {//否则显示删除按钮
            holder.tv_delete.setVisibility(View.VISIBLE);
            holder.tv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(context).setTitle("温馨提示").setMessage("确定删除吗？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    service.deleteHealth(UserInfoModel.getInstance().getToken(), model.getID(),
                                            new RequestCallback<ResponseData>() {
                                                @Override
                                                public void success(ResponseData responseData, Response response) {
                                                    if (responseData.getStatus() == 200) {
                                                        lossWeightStoryModels.remove(model);
                                                        notifyDataSetChanged();
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

        }
        //点赞
        //如果没有人点赞就隐藏点咱人姓名显示
        if (!"0".equals(model.getPraiseNum())) {
            holder.ll_dianzan.setVisibility(View.VISIBLE);
        } else {
            holder.ll_dianzan.setVisibility(View.GONE);
        }

        //如果本人点过赞了 则禁用点赞功能
        if (Constants.HAS_ZAN.equals(model.getIsPraise())) {
            //有点赞
            holder.cb_zan.setChecked(true);
            holder.cb_zan.setEnabled(false);
        } else if (Constants.NO_ZAN.equals(model.getIsPraise())) {
            //没有点赞
            holder.cb_zan.setChecked(false);
            holder.cb_zan.setEnabled(true);
            holder.cb_zan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkVr()) {
                        holder.cb_zan.setChecked(false);
                    } else {
                        if ("0".equals(model.getMinetype())) {
                            if (holder.cb_zan.isChecked()) {
                                final UserInfoModel infoModel = UserInfoModel.getInstance();
                                model.setPraiseNum(Integer.parseInt(model.getPraiseNum()) + 1 + "");
                                model.setIsPraise(Constants.HAS_ZAN);
                                model.setUsernameSet(StringUtil.appendDot(model.getUsernameSet(), infoModel.getUser().getNickname(),
                                        infoModel.getUser().getMobile()));
                                if(model.getIsFocus()!=0){
                                    EventBus.getDefault().post(new ZanEvent(model.getID(),true,1));
                                }
                                //向服务器提交
                                String token = infoModel.getToken();
                                service.clickLike(token, new DoZan(Long.parseLong(infoModel.getUser().getUserid()), model.getID()),
                                        new RequestCallback<ResponseData>() {
                                            @Override
                                            public void success(ResponseData responseData, Response response) {
                                                holder.ll_dianzan.setVisibility(View.VISIBLE);
                                            }

                                            @Override
                                            public void failure(RetrofitError error) {
                                                super.failure(error);
                                                int priase = Integer.parseInt(model.getPraiseNum()) - 1 < 0 ? 0 : Integer.parseInt(model.getPraiseNum()) - 1;
                                                model.setPraiseNum(priase + "");
                                                String del = StringUtils.removeEnd(StringUtils.removeEnd(model.getUsernameSet(), infoModel.getUser().getNickname()), ",");
                                                model.setUsernameSet(del);
                                                model.setIsPraise(Constants.NO_ZAN);
                                                notifyDataSetChanged();
                                            }
                                        });
                            }

                        }
                        notifyDataSetChanged();
                    }
                }
            });

        }
        //加载图片
        Picasso.with(context).load(path + model.getPhoto()).resize(px,px).centerCrop()
                .placeholder(R.drawable.img_default).error(R.drawable.img_default).into(holder.civ_header_image);
        holder.civ_header_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent personal=new Intent(context, PersionalActivity.class);
                personal.putExtra("isFocus",model.getIsFocus());
                personal.putExtra("personalId",model.getAccountId());
                personal.putExtra("personalName",model.getUserName());
                context.startActivity(personal);
            }
        });
        String[] imgs = model.getImgCollection().split(",");
        final ArrayList<String> list =new ArrayList<>(Arrays.asList(imgs));
        holder.photos.setAdapter(new PhotosAdapter(list, context));
        holder.photos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent in = new Intent(context, PictureMoreActivity.class);
                in.putStringArrayListExtra("images", list);
                in.putExtra("position", position);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(v, v.getWidth() / 2, v.getHeight() / 2, 0, 0);
                ActivityCompat.startActivity(context, in, optionsCompat.toBundle());
            }
        });
        return convertView;

    }


    static class ViewHolder {
        CircleImageView civ_header_image;
        TextView tv_name, tv_content, tv_date, tv_zan_name, tv_delete;
        LinearLayout ll_dianzan, ll_content;
        CheckBox cb_zan, cb_focus;
        CustomGridView photos;

        public ViewHolder(View view) {
            civ_header_image = (CircleImageView) view.findViewById(R.id.civ_header_image);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            ll_content = (LinearLayout) view.findViewById(R.id.ll_content);
            tv_content = (TextView) view.findViewById(R.id.tv_content);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
            tv_zan_name = (TextView) view.findViewById(R.id.tv_zan_name);
            ll_dianzan = (LinearLayout) view.findViewById(R.id.ll_dianzan);
            photos = (CustomGridView) view.findViewById(R.id.photos);
            cb_zan = (CheckBox) view.findViewById(R.id.cb_zan);
            tv_delete = (TextView) view.findViewById(R.id.tv_delete);
            cb_focus = (CheckBox) view.findViewById(R.id.cb_focus);
        }
    }

    private HealthyDynamicModel copyModeltoDynamci(HealthyCommunityModel model) {
        HealthyDynamicModel dynamicModel = new HealthyDynamicModel();
        dynamicModel.setPraiseNum(model.getPraiseNum());
        dynamicModel.setUsernameSet(model.getUsernameSet());
        dynamicModel.setContent(model.getContent());
        dynamicModel.setUserName(model.getUserName());
        dynamicModel.setHealtId(model.getID());
        dynamicModel.setIsPraise(model.getIsPraise());
        dynamicModel.setCreateDate(model.getCreateDate());
        dynamicModel.setImgCollection(model.getImgCollection());
        dynamicModel.setPhoto(model.getPhoto());
        return dynamicModel;
    }

    //检查是否为游客
    private boolean checkVr() {
        if (UserInfoModel.getInstance().isVr()) {
            new AlertDialog.Builder(context).setMessage("您当前是游客身份，请登录后再试").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent login = new Intent(context, LoginActivity.class);
                    login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(login);
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }).create().show();
            return true;
        }
        return false;
    }

}
