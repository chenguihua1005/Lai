package com.softtek.lai.module.lossweightstory.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.lossweightstory.model.LossWeightStoryModel;
import com.softtek.lai.module.lossweightstory.model.Zan;
import com.softtek.lai.module.lossweightstory.net.LossWeightLogService;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;

/**
 * Created by John on 2016/4/14.
 */
public class LossWeightStoryAdapter extends BaseAdapter{

    private Context context;
    private List<LossWeightStoryModel> lossWeightStoryModels;
    private LossWeightLogService service;
    public LossWeightStoryAdapter(Context context, List<LossWeightStoryModel> lossWeightStoryModels) {
        this.context = context;
        this.lossWeightStoryModels = lossWeightStoryModels;
        service= ZillaApi.NormalRestAdapter.create(LossWeightLogService.class);
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
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.loss_weight_story_item,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        final LossWeightStoryModel model=lossWeightStoryModels.get(position);
        if(Constants.HAS_ZAN.equals(model.getIsClicked())){
            holder.cb_zan.setChecked(true);
            holder.cb_zan.setEnabled(false);
        }else {
            holder.cb_zan.setEnabled(true);
            holder.cb_zan.setChecked(false);
            holder.cb_zan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(final CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        final UserInfoModel infoModel = UserInfoModel.getInstance();
                        model.setPriase(Integer.parseInt(model.getPriase()) + 1 + "");
                        String before = "".equals(model.getUsernameSet()) ? "" : ",";
                        model.setIsClicked(Constants.HAS_ZAN);
                        model.setUsernameSet(before + infoModel.getUser().getNickname());
                        //向服务器提交
                        String token = infoModel.getToken();
                        service.clickLike(token, Long.parseLong(infoModel.getUser().getUserid()),
                                Long.parseLong(model.getLossLogId()),
                                new RequestCallback<ResponseData<Zan>>() {
                                    @Override
                                    public void success(ResponseData<Zan> zanResponseData, Response response) {
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        super.failure(error);
                                        int priase = Integer.parseInt(model.getPriase()) - 1 < 0 ? 0 : Integer.parseInt(model.getPriase()) - 1;
                                        model.setPriase(priase + "");
                                        model.setUsernameSet(model.getUsernameSet().substring(0, model.getUsernameSet().lastIndexOf(",")));
                                        model.setIsClicked(Constants.NO_ZAN);
                                        notifyDataSetChanged();
                                    }
                                });
                    }
                    notifyDataSetChanged();
                }
            });
        }
        holder.tv_name.setText(model.getUserName());
        holder.tv_content.setText(model.getLogContent());
        holder.tv_date.setText(model.getCreateDate());
        holder.tv_zan_name.setText(model.getUsernameSet());
        holder.cb_zan.setText(model.getPriase());
        //加载图片
        String path= AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
        Picasso.with(context).load(path+model.getPhoto())
                .placeholder(R.drawable.img_default).error(R.drawable.img_default).into(holder.civ_header_image);
        visitableOrGone(holder,model.getImgCollection().split(","));
        return convertView;
    }
    static class ViewHolder{
        CircleImageView civ_header_image;
        TextView tv_name,tv_content,tv_date,tv_zan_name;
        ImageView img1,img2,img3,img4,img5,img6,img7,img8,img9;
        CheckBox cb_zan;

        public ViewHolder(View view){
            civ_header_image= (CircleImageView) view.findViewById(R.id.civ_header_image);
            tv_name= (TextView) view.findViewById(R.id.tv_name);
            tv_content=(TextView)view.findViewById(R.id.tv_content);
            tv_date=(TextView)view.findViewById(R.id.tv_date);
            tv_zan_name=(TextView)view.findViewById(R.id.tv_zan_name);
            img1= (ImageView) view.findViewById(R.id.img_1);
            img2=(ImageView)view.findViewById(R.id.img_2);
            img3=(ImageView)view.findViewById(R.id.img_3);
            img4=(ImageView)view.findViewById(R.id.img_4);
            img5=(ImageView)view.findViewById(R.id.img_5);
            img6=(ImageView)view.findViewById(R.id.img_6);
            img7=(ImageView)view.findViewById(R.id.img_7);
            img8=(ImageView)view.findViewById(R.id.img_8);
            img9= (ImageView) view.findViewById(R.id.img_9);
            cb_zan= (CheckBox) view.findViewById(R.id.cb_zan);
        }

    }

    private void visitableOrGone(ViewHolder holder,String[] imgs) {
        for (int i = 0; i < imgs.length; i++) {
            try {
                switch (i + 1) {
                    case 1:
                        holder.img1.setVisibility(View.VISIBLE);
                        holder.img2.setVisibility(View.GONE);
                        holder.img3.setVisibility(View.GONE);
                        holder.img4.setVisibility(View.GONE);
                        holder.img5.setVisibility(View.GONE);
                        holder.img6.setVisibility(View.GONE);
                        holder.img7.setVisibility(View.GONE);
                        holder.img8.setVisibility(View.GONE);
                        holder.img9.setVisibility(View.GONE);
                        Picasso.with(context).load(imgs[i]).fit()
                                .placeholder(R.drawable.default_pic)
                                .error(R.drawable.default_pic)
                                .into(holder.img1);
                        break;
                    case 2:
                        holder.img2.setVisibility(View.VISIBLE);
                        holder.img3.setVisibility(View.GONE);
                        holder.img4.setVisibility(View.GONE);
                        holder.img5.setVisibility(View.GONE);
                        holder.img6.setVisibility(View.GONE);
                        holder.img7.setVisibility(View.GONE);
                        holder.img8.setVisibility(View.GONE);
                        holder.img9.setVisibility(View.GONE);
                        Picasso.with(context).load(imgs[i]).fit()
                                .placeholder(R.drawable.default_pic)
                                .error(R.drawable.default_pic)
                                .into(holder.img2);
                        break;
                    case 3:
                        holder.img3.setVisibility(View.VISIBLE);
                        holder.img4.setVisibility(View.GONE);
                        holder.img5.setVisibility(View.GONE);
                        holder.img6.setVisibility(View.GONE);
                        holder.img7.setVisibility(View.GONE);
                        holder.img8.setVisibility(View.GONE);
                        holder.img9.setVisibility(View.GONE);
                        Picasso.with(context).load(imgs[i]).fit()
                                .placeholder(R.drawable.default_pic)
                                .error(R.drawable.default_pic)
                                .into(holder.img3);
                        break;
                    case 4:
                        holder.img4.setVisibility(View.VISIBLE);
                        holder.img5.setVisibility(View.GONE);
                        holder.img6.setVisibility(View.GONE);
                        holder.img7.setVisibility(View.GONE);
                        holder.img8.setVisibility(View.GONE);
                        holder.img9.setVisibility(View.GONE);
                        Picasso.with(context).load(imgs[i]).fit()
                                .placeholder(R.drawable.default_pic)
                                .error(R.drawable.default_pic)
                                .into(holder.img4);
                        break;
                    case 5:
                        holder.img5.setVisibility(View.VISIBLE);
                        holder.img6.setVisibility(View.GONE);
                        holder.img7.setVisibility(View.GONE);
                        holder.img8.setVisibility(View.GONE);
                        holder.img9.setVisibility(View.GONE);
                        Picasso.with(context).load(imgs[i]).fit()
                                .placeholder(R.drawable.default_pic)
                                .error(R.drawable.default_pic)
                                .into(holder.img5);
                        break;
                    case 6:
                        holder.img6.setVisibility(View.VISIBLE);
                        holder.img7.setVisibility(View.GONE);
                        holder.img8.setVisibility(View.GONE);
                        holder.img9.setVisibility(View.GONE);
                        Picasso.with(context).load(imgs[i]).fit()
                                .placeholder(R.drawable.default_pic)
                                .error(R.drawable.default_pic)
                                .into(holder.img6);
                        break;
                    case 7:
                        holder.img7.setVisibility(View.VISIBLE);
                        holder.img8.setVisibility(View.GONE);
                        holder.img9.setVisibility(View.GONE);
                        Picasso.with(context).load(imgs[i]).fit()
                                .placeholder(R.drawable.default_pic)
                                .error(R.drawable.default_pic)
                                .into(holder.img7);
                        break;
                    case 8:
                        holder.img8.setVisibility(View.VISIBLE);
                        holder.img9.setVisibility(View.GONE);
                        Picasso.with(context).load(imgs[i]).fit()
                                .placeholder(R.drawable.default_pic)
                                .error(R.drawable.default_pic)
                                .into(holder.img8);
                        break;
                    case 9:
                        holder.img9.setVisibility(View.VISIBLE);
                        Picasso.with(context).load(imgs[i]).fit()
                                .placeholder(R.drawable.default_pic)
                                .error(R.drawable.default_pic)
                                .into(holder.img9);
                        break;
                }
            } catch (Exception e) {

            }
        }
    }
}
