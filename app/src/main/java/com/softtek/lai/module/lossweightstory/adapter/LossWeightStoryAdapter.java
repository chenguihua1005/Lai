package com.softtek.lai.module.lossweightstory.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
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
import com.softtek.lai.module.community.adapter.PhotosAdapter;
import com.softtek.lai.module.lossweightstory.model.LossWeightStoryModel;
import com.softtek.lai.module.lossweightstory.model.Zan;
import com.softtek.lai.module.lossweightstory.net.LossWeightLogService;
import com.softtek.lai.module.lossweightstory.view.PictureMoreActivity;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.utils.StringUtil;
import com.softtek.lai.widgets.CircleImageView;
import com.softtek.lai.widgets.CustomGridView;
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
            convertView= LayoutInflater.from(context).inflate(R.layout.loss_weight_story_item1,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        final LossWeightStoryModel model=lossWeightStoryModels.get(position);
        holder.tv_delete.setVisibility(View.GONE);
        if(Constants.HAS_ZAN.equals(model.getIsClicked())){
            holder.cb_zan.setChecked(true);
            holder.cb_zan.setEnabled(false);
        }else {
            holder.cb_zan.setEnabled(true);
            holder.cb_zan.setChecked(false);
            holder.cb_zan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.cb_zan.isChecked()) {
                        final UserInfoModel infoModel = UserInfoModel.getInstance();
                        model.setPriase(Integer.parseInt(model.getPriase()) + 1 + "");
                        model.setIsClicked(Constants.HAS_ZAN);
                        holder.ll_dianzan.setVisibility(View.VISIBLE);
                        model.setUsernameSet(StringUtil.appendDot(model.getUsernameSet(),infoModel.getUser().getNickname(),
                                infoModel.getUser().getMobile()));
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
                                        String del=StringUtils.removeEnd(StringUtils.removeEnd(model.getUsernameSet(),infoModel.getUser().getNickname()),",");
                                        model.setUsernameSet(del);
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
        String date=model.getCreateDate();
        holder.tv_date.setText(DateUtil.getInstance().getYear(date)+
                "年"+DateUtil.getInstance().getMonth(date)+
                "月"+DateUtil.getInstance().getDay(date)+"日");
        holder.tv_zan_name.setText(model.getUsernameSet());
        holder.cb_zan.setText(model.getPriase());
        if(StringUtils.isEmpty(model.getPriase())||Integer.parseInt(model.getPriase())==0){
            holder.ll_dianzan.setVisibility(View.GONE);
        }else{
            holder.ll_dianzan.setVisibility(View.VISIBLE);
        }
        //加载图片
        String path= AddressManager.get("photoHost");
        if(StringUtils.isNotEmpty(model.getPhoto())){
            Picasso.with(context).load(path+model.getPhoto()).fit()
                    .placeholder(R.drawable.img_default).error(R.drawable.img_default).into(holder.civ_header_image);
        }else{
            Picasso.with(context).load(R.drawable.img_default).into(holder.civ_header_image);
        }
        final ArrayList<String> list=new ArrayList<>();
        String[] imgs=model.getImgCollection().split(",");
        for(int i=0;i<imgs.length;i++){
            list.add(imgs[i]);
        }
        holder.photos.setAdapter(new PhotosAdapter(list, context));
        holder.photos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent in=new Intent(context, PictureMoreActivity.class);
                in.putStringArrayListExtra("images", list);
                in.putExtra("position",position);
                ActivityOptionsCompat optionsCompat=ActivityOptionsCompat.makeScaleUpAnimation(v,v.getWidth()/2,v.getHeight()/2,0,0);
                ActivityCompat.startActivity((AppCompatActivity) context,in,optionsCompat.toBundle());
            }
        });
        return convertView;
    }

    static class ViewHolder{
        CircleImageView civ_header_image;
        TextView tv_name,tv_content,tv_date,tv_zan_name,tv_delete;
        CheckBox cb_zan;
        LinearLayout ll_dianzan;
        CustomGridView photos;

        public ViewHolder(View view){
            civ_header_image= (CircleImageView) view.findViewById(R.id.civ_header_image);
            tv_name= (TextView) view.findViewById(R.id.tv_name);
            tv_content=(TextView)view.findViewById(R.id.tv_content);
            tv_date=(TextView)view.findViewById(R.id.tv_date);
            tv_zan_name=(TextView)view.findViewById(R.id.tv_zan_name);
            cb_zan= (CheckBox) view.findViewById(R.id.cb_zan);
            tv_delete= (TextView) view.findViewById(R.id.tv_delete);
            ll_dianzan= (LinearLayout) view.findViewById(R.id.ll_dianzan);
            photos= (CustomGridView) view.findViewById(R.id.photos);
        }

    }
}
