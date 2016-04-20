package com.softtek.lai.module.community.view;

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
import com.softtek.lai.module.community.model.HealthyDynamicModel;
import com.softtek.lai.module.community.net.CommunityService;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.studetail.adapter.LogDetailGridAdapter;
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
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_healthy_detail)
public class HealthyDetailActivity extends BaseActivity implements View.OnClickListener
,CompoundButton.OnCheckedChangeListener{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.civ_header_image)
    CircleImageView header_image;
    @InjectView(R.id.tv_name)
    TextView tv_name;
    @InjectView(R.id.tv_content)
    TextView tv_content;
    @InjectView(R.id.cgv_list_image)
    CustomGridView list_image;
    @InjectView(R.id.tv_date)
    TextView tv_date;
    @InjectView(R.id.cb_zan)
    CheckBox cb_zan;

    private CommunityService service;
    private HealthyDynamicModel model;
    private List<String> images=new ArrayList<>();
    private LogDetailGridAdapter adapter;

    @Override
    protected void initViews() {
        tv_title.setText("动态详情");
        ll_left.setOnClickListener(this);
        cb_zan.setOnCheckedChangeListener(this);
    }

    @Override
    protected void initDatas() {
        service= ZillaApi.NormalRestAdapter.create(CommunityService.class);
        model=getIntent().getParcelableExtra("dynamicModel");
        if(null!=model.getPhoto()&&"".equals(model.getPhoto())){
            Picasso.with(this).load(AddressManager.get("photoHost")+model.getPhoto())
                    .fit().error(R.drawable.img_default).into(header_image);
        }
        tv_name.setText(model.getUserName());
        tv_date.setText(model.getCreateDate());
        tv_content.setText(model.getContent());
        cb_zan.setText(model.getPraiseNum());
        if(Constants.HAS_ZAN.equals(model.getIsPraise())){
            cb_zan.setChecked(true);
            cb_zan.setEnabled(false);
        }else if(Constants.NO_ZAN.equals(model.getIsPraise())){
            cb_zan.setChecked(false);
            cb_zan.setEnabled(true);
        }
        if("".equals(UserInfoModel.getInstance().getToken())){
            cb_zan.setChecked(false);
            cb_zan.setEnabled(false);
        }
        //拆分字符串图片列表,并添加到图片集合中
        if(!"".equals(model.getImgCollection())&&null!=model.getImgCollection()){
            String[] image=model.getImgCollection().split(",");
            images.addAll(Arrays.asList(image));
        }
        adapter=new LogDetailGridAdapter(this,images);
        list_image.setAdapter(adapter);
        dialogShow("加载中...");
        UserModel user= UserInfoModel.getInstance().getUser();
        service.getHealthyDynamciDetail(Long.parseLong(user.getUserid()), model.getHealtId(),
                new RequestCallback<ResponseData<HealthyDynamicModel>>() {
                    @Override
                    public void success(ResponseData<HealthyDynamicModel> healthyDynamicModelResponseData, Response response) {
                        dialogDissmiss();
                        setValue(healthyDynamicModelResponseData.getData());
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        dialogDissmiss();
                        super.failure(error);

                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
        }
    }

    private void setValue(HealthyDynamicModel dynamicModel){
        if(dynamicModel==null){
            return;
        }
        cb_zan.setText(dynamicModel.getPraiseNum());
        tv_date.setText(dynamicModel.getCreateDate());
        tv_name.setText(dynamicModel.getUserName());
        tv_content.setText(dynamicModel.getContent());
        //判断是否点过赞
        if(Constants.HAS_ZAN.equals(dynamicModel.getIsPraise())){
            cb_zan.setChecked(true);
            cb_zan.setEnabled(false);
        }else if(Constants.NO_ZAN.equals(dynamicModel.getIsPraise())){
            cb_zan.setChecked(false);
            cb_zan.setEnabled(true);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){

        }
    }
}
