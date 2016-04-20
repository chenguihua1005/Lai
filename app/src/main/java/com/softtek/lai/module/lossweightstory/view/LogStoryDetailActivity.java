package com.softtek.lai.module.lossweightstory.view;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.lossweightstory.model.LogStoryDetailModel;
import com.softtek.lai.module.lossweightstory.model.LossWeightStoryModel;
import com.softtek.lai.module.lossweightstory.model.Zan;
import com.softtek.lai.module.lossweightstory.net.LossWeightLogService;
import com.softtek.lai.module.lossweightstory.presenter.LogStoryDetailManager;
import com.softtek.lai.module.studetail.adapter.LogDetailGridAdapter;
import com.softtek.lai.module.studetail.adapter.LossWeightLogAdapter;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CircleImageView;
import com.softtek.lai.widgets.CustomGridView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_log_detail)
public class LogStoryDetailActivity extends BaseActivity implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener,LogStoryDetailManager.LogStoryDetailManagerCallback{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.civ_header_image)
    CircleImageView civ_header_image;
    @InjectView(R.id.tv_name)
    TextView tv_name;
    @InjectView(R.id.tv_totle_lw)
    TextView tv_totle_lw;
    @InjectView(R.id.tv_log_title)
    TextView tv_log_title;
    @InjectView(R.id.tv_content)
    TextView tv_content;
    @InjectView(R.id.tv_date)
    TextView tv_date;
    @InjectView(R.id.cb_zan)
    CheckBox cb_zan;
    @InjectView(R.id.cgv_list_image)
    CustomGridView cgv_list_image;
    List<String> images=new ArrayList<>();

    private LossWeightLogService service;
    private LossWeightStoryModel log;
    private LogDetailGridAdapter adapter;
    private LogStoryDetailManager manager;

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        cb_zan.setOnCheckedChangeListener(this);
        tv_title.setText("日志详情");
    }

    @Override
    protected void initDatas() {
        service= ZillaApi.NormalRestAdapter.create(LossWeightLogService.class);
        manager=new LogStoryDetailManager(this);
        log= (LossWeightStoryModel) getIntent().getSerializableExtra("log");
        if(null!=log.getPhoto()&&!"".equals(log.getPhoto())){
            Picasso.with(this).load(log.getPhoto()).placeholder(R.drawable.img_default)
                    .error(R.drawable.img_default).into(civ_header_image);
        }
        tv_name.setText(log.getUserName());
        tv_log_title.setText(log.getLogTitle());
        tv_content.setText(log.getLogContent());
        tv_date.setText(log.getCreateDate());
        tv_totle_lw.setText(log.getAfterWeight()+"斤");
        cb_zan.setText(log.getPriase());
        if(Constants.HAS_ZAN.equals(log.getIsClicked())){
            cb_zan.setChecked(true);
            cb_zan.setEnabled(false);
        }else if(Constants.NO_ZAN.equals(log.getIsClicked())){
            cb_zan.setChecked(false);
            cb_zan.setEnabled(true);
        }
        //拆分字符串图片列表,并添加到图片集合中
        if(!"".equals(log.getImgCollection())&&!(null==log.getImgCollection())){
            String[] image=log.getImgCollection().split(",");
            images.addAll(Arrays.asList(image));
        }
        adapter=new LogDetailGridAdapter(this,images);
        cgv_list_image.setAdapter(adapter);
        dialogShow("加载中...");
        manager.getLogDetail(Long.parseLong(log.getLossLogId()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
        }
    }

    @Override
    public void onCheckedChanged(final CompoundButton buttonView, boolean isChecked) {
        cb_zan.setChecked(isChecked);
        if(isChecked){
            log.setPriase(Integer.parseInt(log.getPriase())+1+"");
            cb_zan.setText(log.getPriase());
            //向服务器提交
            UserModel info= UserInfoModel.getInstance().getUser();
            service.clickLike(UserInfoModel.getInstance().getToken(),
                    Long.parseLong(info.getUserid()), Long.parseLong(log.getLossLogId()),
                    new RequestCallback<ResponseData<Zan>>() {
                        @Override
                        public void success(ResponseData<Zan> zanResponseData, Response response) {
                            ((CheckBox)buttonView).setEnabled(false);
                            cb_zan.setText(zanResponseData.getData().getTotalNum());
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            super.failure(error);
                            ((CheckBox)buttonView).setEnabled(true);
                            int priase=Integer.parseInt(log.getPriase())-1<0?0:Integer.parseInt(log.getPriase())-1;
                            log.setPriase(priase+"");
                            cb_zan.setText(priase+"");
                        }
                    });
        }else{
            int priase=Integer.parseInt(log.getPriase())-1<0?0:Integer.parseInt(log.getPriase())-1;
            log.setPriase(priase+"");
            cb_zan.setText(priase+"");
            ((CheckBox)buttonView).setEnabled(true);
        }
    }

    @Override
    public void getLogDetail(LogStoryDetailModel log) {
        dialogDissmiss();
        if(log==null){
            return;
        }

        tv_name.setText(log.getUserName());
        tv_log_title.setText(log.getLogTitle());
        tv_content.setText(log.getLogContent());
        tv_date.setText(log.getCreateDate());
        tv_totle_lw.setText(log.getAfterWeight()+"斤");
        cb_zan.setText(log.getPriasenum());
        if(Constants.HAS_ZAN.equals(log.getIfpriasenum())){
            cb_zan.setChecked(true);
            cb_zan.setEnabled(false);
        }else if(Constants.NO_ZAN.equals(log.getIfpriasenum())){
            cb_zan.setChecked(false);
            cb_zan.setEnabled(true);
        }
        //拆分字符串图片列表,并添加到图片集合中
        if(!"".equals(log.getImgCollection())&&!(null==log.getImgCollection())){
            String[] image=log.getImgCollection().split(",");
            images.clear();
            images.addAll(Arrays.asList(image));
        }
        adapter.notifyDataSetChanged();

    }
}
