package com.softtek.lai.module.personalPK.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.personalPK.model.PKListModel;
import com.softtek.lai.module.personalPK.presenter.PKListManager;
import com.softtek.lai.module.sport2.eventmodel.PkZanEvent;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.utils.StringUtil;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.file.AddressManager;

public class PKListAdapter extends BaseAdapter{

    public static final int NAIXI=0;
    public static final int NAIXICAO=1;
    public static final int CUSTOM=2;
    public static final int NOSTART=1;
    public static final int PROCESSING=0;
    public static final int Completed=2;

    private Context context;
    private List<PKListModel> datas;
    private PKListManager manager;

    public PKListAdapter(Context context, List<PKListModel> datas) {
        this.context = context;
        this.datas = datas;
        manager = new PKListManager();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final PKListHolder holder;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.pklist_item,parent,false);
            holder=new PKListHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (PKListHolder) convertView.getTag();
        }
        //绑定数据
        final PKListModel model=datas.get(position);
        final long pkId=model.getPKId();
        //发起者点赞
        if(model.getPraiseStatus()==1){
            holder.cb_zan_left.setEnabled(false);//禁止点赞
            holder.cb_zan_left.setChecked(true);
        }else{
            holder.cb_zan_left.setEnabled(true);//允许点赞
            holder.cb_zan_left.setChecked(false);
            holder.cb_zan_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.cb_zan_left.setEnabled(false);
                    holder.cb_zan_left.setChecked(true);
                    final int left_zan = Integer.parseInt(holder.cb_zan_left.getText().toString()) + 1;
                    holder.cb_zan_left.setText(String.valueOf(left_zan));
                    model.setPraiseStatus(1);
                    EventBus.getDefault().post(new PkZanEvent(true,model.getPKId()));
                    manager.doZan(pkId, 0, new RequestCallback<ResponseData>() {
                        @Override
                        public void success(ResponseData responseData, Response response) {
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            holder.cb_zan_left.setText(left_zan - 1 + "");
                            holder.cb_zan_left.setEnabled(true);
                            holder.cb_zan_left.setChecked(false);
                            model.setPraiseStatus(0);
                            super.failure(error);
                        }
                    });
                }
            });
        }
        //接受者点赞
        if(1==model.getBPraiseStatus()){
            holder.cb_zan_right.setEnabled(false);//禁止点赞
            holder.cb_zan_right.setChecked(true);
        }else{
            holder.cb_zan_right.setEnabled(true);//允许点赞
            holder.cb_zan_right.setChecked(false);
            holder.cb_zan_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.cb_zan_right.setChecked(true);
                    holder.cb_zan_right.setEnabled(false);
                    final int right_zan = Integer.parseInt(holder.cb_zan_right.getText().toString()) + 1;
                    holder.cb_zan_right.setText(String.valueOf(right_zan));
                    model.setBPraiseStatus(1);
                    EventBus.getDefault().post(new PkZanEvent(false,model.getPKId()));
                    manager.doZan(pkId, 1, new RequestCallback<ResponseData>() {
                        @Override
                        public void success(ResponseData responseData, Response response) {
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            holder.cb_zan_right.setText(right_zan - 1 + "");
                            holder.cb_zan_right.setChecked(false);
                            holder.cb_zan_right.setEnabled(true);
                            model.setBPraiseStatus(0);
                            super.failure(error);
                        }
                    });
                }
            });
        }
        holder.cb_zan_left.setText(String.valueOf(model.getChP()));
        holder.cb_zan_right.setText(String.valueOf(model.getBChp()));
        holder.pk_name1.setText(StringUtil.showName(model.getUserName(),model.getMobile()));
        holder.pk_name2.setText(StringUtil.showName(model.getBUserName(),model.getBMobile()));
        holder.tv_time.setText(DateUtil.getInstance().convertDateStr(model.getStart(),"yyyy年MM月dd日")+" — "+
                DateUtil.getInstance().convertDateStr(model.getEnd(),"yyyy年MM月dd日"));
        if(model.getChipType()==NAIXI){
            holder.iv_jiangli.setBackgroundResource(R.drawable.pk_naixi);
        }else if(model.getChipType()==NAIXICAO){
            holder.iv_jiangli.setBackgroundResource(R.drawable.pk_list_naixicao);
        }else if(model.getChipType()==CUSTOM){
            holder.iv_jiangli.setBackgroundResource(R.drawable.pk_chouma);
        }
        holder.sender1.setVisibility(View.VISIBLE);//显示发起者标识
        //隐藏胜利者标识
        holder.iv_winner1.setVisibility(View.GONE);
        holder.iv_winner2.setVisibility(View.GONE);
        if(model.getTStatus()==NOSTART){
            holder.tv_status.setBackgroundResource(R.drawable.pk_list_weikaishi);
            holder.tv_status.setText("未开始");
        }else if(model.getTStatus()==PROCESSING){
            holder.tv_status.setBackgroundResource(R.drawable.pk_list_jingxingzhong);
            holder.tv_status.setText("进行中");
        }else if(model.getTStatus()==Completed){
            holder.tv_status.setBackgroundResource(R.drawable.pk_list_yijieshu);
            holder.tv_status.setText("已结束");
            if(StringUtils.isNotEmpty(model.getWinnerId())){
                if (Long.parseLong(model.getWinnerId())==model.getChallenged()){
                    //发起方胜利
                    holder.sender1.setVisibility(View.GONE);//隐藏发起者标识
                    //显示胜利者表示
                    holder.iv_winner1.setVisibility(View.VISIBLE);
                    holder.iv_winner2.setVisibility(View.GONE);
                }else{
                    //接受方胜利
                    holder.sender1.setVisibility(View.VISIBLE);//显示发起者标识
                    holder.iv_winner2.setVisibility(View.VISIBLE);
                    holder.iv_winner1.setVisibility(View.GONE);
                }
            }
        }
        //载入头像
        String path= AddressManager.get("photoHost");
        if(StringUtils.isNotEmpty(model.getPhoto())){
            Picasso.with(context).load(path+model.getPhoto()).fit()
                    .placeholder(R.drawable.img_default)
                    .error(R.drawable.img_default)
                    .into(holder.sender1_header);
        }else{
            Picasso.with(context).load(R.drawable.img_default).into(holder.sender1_header);
        }
        if(StringUtils.isNotEmpty(model.getBPhoto())){
            Picasso.with(context).load(path+model.getBPhoto()).fit()
                    .placeholder(R.drawable.img_default)
                    .error(R.drawable.img_default)
                    .into(holder.sender2_header);
        }else{
            Picasso.with(context).load(R.drawable.img_default).into(holder.sender2_header);
        }
        return convertView;
    }

    static class PKListHolder{
        private TextView tv_status,tv_time,pk_name1,pk_name2;
        private CheckBox cb_zan_right,cb_zan_left;
        private ImageView iv_jiangli,sender1,iv_winner1,iv_winner2;
        private CircleImageView sender1_header,sender2_header;

        public PKListHolder(View view){
            tv_status= (TextView) view.findViewById(R.id.tv_status);
            tv_time= (TextView) view.findViewById(R.id.tv_time);
            pk_name1= (TextView) view.findViewById(R.id.tv_pk_name1);
            pk_name2= (TextView) view.findViewById(R.id.tv_pk_name2);
            cb_zan_right= (CheckBox) view.findViewById(R.id.cb_zan_right);
            cb_zan_left= (CheckBox) view.findViewById(R.id.cb_zan_left);
            iv_jiangli= (ImageView) view.findViewById(R.id.iv_jiangli);
            sender1= (ImageView) view.findViewById(R.id.sender1);
            sender1_header= (CircleImageView) view.findViewById(R.id.sender1_header);
            sender2_header= (CircleImageView) view.findViewById(R.id.sender2_header);
            iv_winner1= (ImageView) view.findViewById(R.id.iv_winner1);
            iv_winner2= (ImageView) view.findViewById(R.id.iv_winner2);

        }
    }
}
