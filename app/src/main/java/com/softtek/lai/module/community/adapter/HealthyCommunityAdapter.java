package com.softtek.lai.module.community.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import com.softtek.lai.module.community.model.DoZan;
import com.softtek.lai.module.community.model.HealthyCommunityModel;
import com.softtek.lai.module.community.model.HealthyDynamicModel;
import com.softtek.lai.module.community.net.CommunityService;
import com.softtek.lai.module.community.view.HealthyDetailActivity;
import com.softtek.lai.module.community.view.PersionalActivity;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.module.lossweightstory.model.LossWeightStoryModel;
import com.softtek.lai.module.lossweightstory.model.Zan;
import com.softtek.lai.module.lossweightstory.net.LossWeightLogService;
import com.softtek.lai.module.lossweightstory.view.LogStoryDetailActivity;
import com.softtek.lai.module.lossweightstory.view.PictureMoreActivity;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.utils.StringUtil;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;

/**
 * Created by John on 2016/4/14.
 */
public class HealthyCommunityAdapter extends BaseAdapter {

    private Context context;
    private Fragment fragment;
    private List<HealthyCommunityModel> lossWeightStoryModels;
    private boolean isVR = false;
    private CommunityService service;
    private LossWeightLogService service1;
    private int px;
    private int type = 1;
    private static final int LIST_JUMP=1;
    private static final int LIST_JUMP_2=2;


    public HealthyCommunityAdapter(Fragment fragment,Context context, List<HealthyCommunityModel> lossWeightStoryModels, boolean isVR, int type) {
        this.fragment=fragment;
        this.context = context;
        this.lossWeightStoryModels = lossWeightStoryModels;
        this.isVR = isVR;
        this.type = type;
        service = ZillaApi.NormalRestAdapter.create(CommunityService.class);
        service1 = ZillaApi.NormalRestAdapter.create(LossWeightLogService.class);
        px = DisplayUtil.dip2px(context.getApplicationContext(), 79);
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
        final int pos=position;
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
                if("1".equals(model.getMinetype())){//减重日志
                    Intent logDetail=new Intent(context, LogStoryDetailActivity.class);
                    logDetail.putExtra("log",copyModel(model));
                    logDetail.putExtra("position",pos);
                    logDetail.putExtra("type","1");
                    fragment.startActivityForResult(logDetail,LIST_JUMP_2);
                }else if("0".equals(model.getMinetype())){//动态
                    Intent logDetail=new Intent(context, HealthyDetailActivity.class);
                    logDetail.putExtra("dynamicModel",copyModeltoDynamci(model));
                    logDetail.putExtra("position",pos);
                    logDetail.putExtra("type","1");
                    fragment.startActivityForResult(logDetail,LIST_JUMP);
                }
            }
        });
        holder.tv_name.setText(model.getUserName());
        holder.tv_content.setText(model.getContent());
        String date = model.getCreateDate();
        holder.tv_date.setText(DateUtil.getInstance().getYear(date) +
                "年" + DateUtil.getInstance().getMonth(date) +
                "月" + DateUtil.getInstance().getDay(date) + "日");
        holder.tv_zan_name.setText(model.getUsernameSet());
        holder.cb_zan.setText(model.getPraiseNum());
        if ("1".equals(model.getMinetype())) {
            holder.tv_delete.setVisibility(View.GONE);
        } else {
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
        if ("0".equals(model.getPraiseNum())) {
            holder.ll_dianzan.setVisibility(View.INVISIBLE);
        } else {
            holder.ll_dianzan.setVisibility(View.VISIBLE);
        }

        if (type == 1) {
            if ("1".equals(model.getMinetype())) {
                holder.tv_delete.setVisibility(View.INVISIBLE);
            } else {
                holder.tv_delete.setVisibility(View.VISIBLE);
            }
            holder.cb_zan.setChecked(true);
            holder.cb_zan.setEnabled(false);
            if (Constants.NO_ZAN.equals(model.getIsPraise())) {
                holder.ll_dianzan.setVisibility(View.INVISIBLE);
            } else {
                holder.ll_dianzan.setVisibility(View.VISIBLE);
            }
        } else {
            holder.tv_delete.setVisibility(View.INVISIBLE);
            if (isVR) {
                holder.cb_zan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.cb_zan.setChecked(false);
                        AlertDialog.Builder information_dialog = new AlertDialog.Builder(context);
                        information_dialog.setMessage("您当前是游客身份，请登录后再试").setPositiveButton("确定", new DialogInterface.OnClickListener() {
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
                    }
                });
            } else {
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
                            if ("0".equals(model.getMinetype())) {
                                if (holder.cb_zan.isChecked()) {
                                    final UserInfoModel infoModel = UserInfoModel.getInstance();
                                    model.setPraiseNum(Integer.parseInt(model.getPraiseNum()) + 1 + "");
                                    model.setIsPraise(Constants.HAS_ZAN);
                                    model.setUsernameSet(StringUtil.appendDot(model.getUsernameSet(), infoModel.getUser().getNickname(),
                                            infoModel.getUser().getMobile()));
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

                            } else {
                                if (holder.cb_zan.isChecked()) {
                                    final UserInfoModel infoModel = UserInfoModel.getInstance();
                                    model.setPraiseNum(Integer.parseInt(model.getPraiseNum()) + 1 + "");
                                    model.setIsPraise(Constants.HAS_ZAN);
                                    model.setUsernameSet(StringUtil.appendDot(model.getUsernameSet(), infoModel.getUser().getNickname(),
                                            infoModel.getUser().getMobile()));
                                    //向服务器提交
                                    service1.clickLike(UserInfoModel.getInstance().getToken(),
                                            Long.parseLong(infoModel.getUser().getUserid()), Long.parseLong(model.getID()),
                                            new RequestCallback<ResponseData<Zan>>() {
                                                @Override
                                                public void success(ResponseData<Zan> zanResponseData, Response response) {
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
                    });
                }
            }

        }
        //加载图片
        String path = AddressManager.get("photoHost");
        Picasso.with(context).load(path + model.getPhoto()).fit()
                .placeholder(R.drawable.img_default).error(R.drawable.img_default).into(holder.civ_header_image);
        holder.civ_header_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, PersionalActivity.class));
            }
        }); //测试
        String[] imgs = model.getImgCollection().split(",");
        final ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < imgs.length; i++) {
            list.add(imgs[i]);
        }
        holder.photos.setAdapter(new PhotosAdapter(list,context));
        holder.photos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent in = new Intent(context, PictureMoreActivity.class);
                in.putStringArrayListExtra("images", list);
                in.putExtra("position", position);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(v, v.getWidth() / 2, v.getHeight() / 2, 0, 0);
                ActivityCompat.startActivity((AppCompatActivity) context, in, optionsCompat.toBundle());
            }
        });
        return convertView;

    }


    static class ViewHolder {
        CircleImageView civ_header_image;
        TextView tv_name, tv_content, tv_date, tv_zan_name, tv_delete;
        LinearLayout ll_dianzan,ll_content;
        CheckBox cb_zan;
        com.softtek.lai.module.mygrades.view.MyGridView photos;

        public ViewHolder(View view) {
            civ_header_image = (CircleImageView) view.findViewById(R.id.civ_header_image);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            ll_content= (LinearLayout) view.findViewById(R.id.ll_content);
            tv_content = (TextView) view.findViewById(R.id.tv_content);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
            tv_zan_name = (TextView) view.findViewById(R.id.tv_zan_name);
            ll_dianzan = (LinearLayout) view.findViewById(R.id.ll_dianzan);
            photos= (com.softtek.lai.module.mygrades.view.MyGridView) view.findViewById(R.id.photos);
            cb_zan = (CheckBox) view.findViewById(R.id.cb_zan);
            tv_delete = (TextView) view.findViewById(R.id.tv_delete);
        }
    }

    private LossWeightStoryModel copyModel(HealthyCommunityModel model){
        LossWeightStoryModel storyModel=new LossWeightStoryModel();
        storyModel.setPriase(model.getPraiseNum());
        storyModel.setLogContent(model.getContent());
        storyModel.setLogTitle(model.getTitle());
        storyModel.setAfterWeight("0");
        storyModel.setCreateDate(model.getCreateDate());
        storyModel.setImgCollection(model.getImgCollection());
        storyModel.setIsClicked(model.getIsPraise());
        storyModel.setLossLogId(model.getID());
        storyModel.setPhoto(model.getPhoto());
        storyModel.setUserName(model.getUserName());
        storyModel.setUsernameSet(model.getUsernameSet());

        return storyModel;
    }

    private HealthyDynamicModel copyModeltoDynamci(HealthyCommunityModel model){
        HealthyDynamicModel dynamicModel=new HealthyDynamicModel();
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

}
