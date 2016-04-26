package com.softtek.lai.module.community.view;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.community.model.DoZan;
import com.softtek.lai.module.community.model.HealthyDynamicModel;
import com.softtek.lai.module.community.net.CommunityService;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.studetail.adapter.LogDetailGridAdapter;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.utils.StringUtil;
import com.softtek.lai.widgets.CircleImageView;
import com.softtek.lai.widgets.CustomGridView;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

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
public class HealthyDetailActivity extends BaseActivity implements View.OnClickListener {

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
    @InjectView(R.id.tv_zan_name)
    TextView tv_zan_name;

    private CommunityService service;
    private HealthyDynamicModel model;
    private List<String> images=new ArrayList<>();
    private LogDetailGridAdapter adapter;
    private long accountId=-1;

    @Override
    protected void initViews() {
        tv_title.setText("动态详情");
        ll_left.setOnClickListener(this);
        cb_zan.setOnClickListener(this);
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
        String date=model.getCreateDate();
        tv_date.setText(DateUtil.getInstance().getYear(date)+
                "年"+DateUtil.getInstance().getMonth(date)+
                "月"+DateUtil.getInstance().getDay(date)+"日");
        tv_content.setText(model.getContent());
        cb_zan.setText(model.getPraiseNum());
        tv_zan_name.setText(model.getUsernameSet());
        if("".equals(UserInfoModel.getInstance().getToken())){
            cb_zan.setChecked(false);
            cb_zan.setEnabled(false);
        }else{
            if(Constants.HAS_ZAN.equals(model.getIsPraise())){
                cb_zan.setChecked(true);
                cb_zan.setEnabled(false);
            }else if(Constants.NO_ZAN.equals(model.getIsPraise())){
                cb_zan.setChecked(false);
                cb_zan.setEnabled(true);
            }
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
        if("".equals(UserInfoModel.getInstance().getToken())){
            accountId=-1;
        }else{
            accountId=Long.parseLong(user.getUserid());
        }
        service.getHealthyDynamciDetail(accountId, model.getHealtId(),
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
                Intent i=getIntent();
                i.putExtra("dynamicModel",model);
                setResult(RESULT_OK,i);
                finish();
                break;
            case R.id.cb_zan:
                final UserInfoModel infoModel = UserInfoModel.getInstance();
                model.setPraiseNum(Integer.parseInt(model.getPraiseNum()) + 1 + "");
                model.setIsPraise(Constants.HAS_ZAN);
                model.setUsernameSet(StringUtil.appendDotAll(model.getUsernameSet(),infoModel.getUser().getNickname(),infoModel.getUser().getMobile()));
                setValue(model);
                //向服务器提交
                String token = infoModel.getToken();
                service.clickLike(token,new DoZan(Long.parseLong(infoModel.getUser().getUserid()),model.getHealtId()),
                        new RequestCallback<ResponseData>() {
                            @Override
                            public void success(ResponseData responseData, Response response) {}
                            @Override
                            public void failure(RetrofitError error) {
                                super.failure(error);
                                int priase = Integer.parseInt(model.getPraiseNum()) - 1 < 0 ? 0 : Integer.parseInt(model.getPraiseNum()) - 1;
                                model.setPraiseNum(priase + "");
                                String del= StringUtils.removeEnd(StringUtils.removeEnd(model.getUsernameSet(),infoModel.getUser().getNickname()),",");
                                model.setUsernameSet(del);
                                model.setIsPraise(Constants.NO_ZAN);
                                setValue(model);
                            }
                        });
                break;
        }
    }

    private void setValue(HealthyDynamicModel dynamicModel){
        if(dynamicModel==null){
            return;
        }
        cb_zan.setText(dynamicModel.getPraiseNum());
        String date=dynamicModel.getCreateDate();
        tv_date.setText(DateUtil.getInstance().getYear(date)+
                "年"+DateUtil.getInstance().getMonth(date)+
                "月"+DateUtil.getInstance().getDay(date)+"日");
        tv_name.setText(dynamicModel.getUserName());
        tv_content.setText(dynamicModel.getContent());
        tv_zan_name.setText(dynamicModel.getUsernameSet());
        //判断是否点过赞
        if(accountId==-1){
            cb_zan.setChecked(false);
            cb_zan.setEnabled(false);
        }else{
            if(Constants.HAS_ZAN.equals(dynamicModel.getIsPraise())){
                cb_zan.setChecked(true);
                cb_zan.setEnabled(false);
            }else if(Constants.NO_ZAN.equals(dynamicModel.getIsPraise())){
                cb_zan.setChecked(false);
                cb_zan.setEnabled(true);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            Intent i=getIntent();
            i.putExtra("dynamicModel",model);
            setResult(RESULT_OK,i);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
