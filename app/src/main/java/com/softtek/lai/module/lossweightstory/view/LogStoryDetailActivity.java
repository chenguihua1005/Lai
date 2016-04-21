package com.softtek.lai.module.lossweightstory.view;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
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
        LogStoryDetailManager.LogStoryDetailManagerCallback{

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
        cb_zan.setOnClickListener(this);
        tv_title.setText("日志详情");
    }

    @Override
    protected void initDatas() {
        service= ZillaApi.NormalRestAdapter.create(LossWeightLogService.class);
        manager=new LogStoryDetailManager(this);
        log= (LossWeightStoryModel) getIntent().getParcelableExtra("log");
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
        zanSet();
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
                Intent i=getIntent();
                i.putExtra("log",log);
                setResult(RESULT_OK,i);
                finish();
                break;
            case R.id.cb_zan:
                UserInfoModel infoModel = UserInfoModel.getInstance();
                log.setPriase(Integer.parseInt(log.getPriase())+1+"");
                log.setIsClicked(Constants.HAS_ZAN);
                String before = "".equals(log.getUsernameSet()) ? "" : ",";
                log.setUsernameSet(before+infoModel.getUser().getNickname());
                cb_zan.setText(log.getPriase());
                zanSet();
                //向服务器提交
                UserModel info= UserInfoModel.getInstance().getUser();
                service.clickLike(UserInfoModel.getInstance().getToken(),
                        Long.parseLong(info.getUserid()), Long.parseLong(log.getLossLogId()),
                        new RequestCallback<ResponseData<Zan>>() {
                            @Override
                            public void success(ResponseData<Zan> zanResponseData, Response response) {
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                super.failure(error);
                                int priase=Integer.parseInt(log.getPriase())-1<0?0:Integer.parseInt(log.getPriase())-1;
                                log.setPriase(priase+"");
                                log.setUsernameSet(log.getUsernameSet().substring(0, log.getUsernameSet().lastIndexOf(",")));
                                log.setIsClicked(Constants.NO_ZAN);
                                cb_zan.setText(priase+"");
                                zanSet();
                            }
                        });
                break;
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

    private void zanSet(){
        if(!"".equals(UserInfoModel.getInstance().getToken())){
            if(Constants.HAS_ZAN.equals(log.getIsClicked())){
                cb_zan.setChecked(true);
                cb_zan.setEnabled(false);
            }else if(Constants.NO_ZAN.equals(log.getIsClicked())){
                cb_zan.setChecked(false);
                cb_zan.setEnabled(true);
            }
        }else{
            cb_zan.setChecked(false);
            cb_zan.setEnabled(false);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            Intent i=getIntent();
            i.putExtra("log",log);
            setResult(RESULT_OK,i);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
