package com.softtek.lai.module.lossweightstory.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.module.lossweightstory.model.LogStoryDetailModel;
import com.softtek.lai.module.lossweightstory.model.LossWeightStoryModel;
import com.softtek.lai.module.lossweightstory.model.Zan;
import com.softtek.lai.module.lossweightstory.net.LossWeightLogService;
import com.softtek.lai.module.lossweightstory.presenter.LogStoryDetailManager;
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

@InjectLayout(R.layout.activity_log_detail)
public class LogStoryDetailActivity extends BaseActivity implements View.OnClickListener,
        LogStoryDetailManager.LogStoryDetailManagerCallback,AdapterView.OnItemClickListener{

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

    @InjectView(R.id.tv_content)
    TextView tv_content;
    @InjectView(R.id.tv_date)
    TextView tv_date;
    @InjectView(R.id.cb_zan)
    CheckBox cb_zan;
    @InjectView(R.id.cgv_list_image)
    CustomGridView cgv_list_image;
    @InjectView(R.id.tv_zan_name)
    TextView tv_zan_name;
    @InjectView(R.id.ll_zan)
    LinearLayout ll_zan;

    List<String> images=new ArrayList<>();

    private LossWeightLogService service;
    private LogDetailGridAdapter adapter;
    private LogStoryDetailManager manager;
    private LossWeightStoryModel log;
    private LogStoryDetailModel logDetail;

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        cb_zan.setOnClickListener(this);
        tv_title.setText("日志详情");
        cgv_list_image.setOnItemClickListener(this);
    }

    @Override
    protected void initDatas() {
        String type="0";

        type=getIntent().getStringExtra("type");
        if ("0".equals(type))
        {
            cb_zan.setVisibility(View.GONE);
        }
        service= ZillaApi.NormalRestAdapter.create(LossWeightLogService.class);
        manager=new LogStoryDetailManager(this);
        log= getIntent().getParcelableExtra("log");
        if(log!=null){
            if(StringUtils.isNotEmpty(log.getPhoto())){
                Picasso.with(this).load(AddressManager.get("photoHost")+log.getPhoto()).fit().placeholder(R.drawable.img_default)
                        .error(R.drawable.img_default).into(civ_header_image);
            }
            tv_name.setText(log.getUserName());
            tv_content.setText(log.getLogContent());
            String date=log.getCreateDate();
            tv_date.setText(DateUtil.getInstance().convertDateStr(date,"yyyy年MM月dd日"));
            tv_totle_lw.setText(log.getAfterWeight()+"斤");
            cb_zan.setText(log.getPriase());
            tv_zan_name.setText(log.getUsernameSet());
            zanSet();
            //拆分字符串图片列表,并添加到图片集合中
            if(!"".equals(log.getImgCollection())&&!(null==log.getImgCollection())){
                String[] image=log.getImgCollection().split(",");
                images.addAll(Arrays.asList(image));
            }
        }
        adapter=new LogDetailGridAdapter(this,images);
        cgv_list_image.setAdapter(adapter);
        dialogShow("加载中...");
        long logId=getIntent().getLongExtra("logId",0);
        if(log!=null){
            manager.getLogDetail(Long.parseLong(log.getLossLogId()));
        }else {
            manager.getLogDetail(logId);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                Intent i=getIntent();
                if(log!=null){
                    i.putExtra("log",log);
                }
                setResult(RESULT_OK,i);
                finish();
                break;
            case R.id.cb_zan:
                if (TextUtils.isEmpty(UserInfoModel.getInstance().getToken()))
                {
                    cb_zan.setChecked(false);
                    AlertDialog.Builder information_dialog = new AlertDialog.Builder(this);
                    information_dialog.setTitle("您当前是游客身份，请登录后再试").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent login = new Intent(getBaseContext(), LoginActivity.class);
                            login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(login);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).create().show();
                }
                else {
                    if(logDetail!=null){
                        ll_zan.setVisibility(View.VISIBLE);
                        final UserInfoModel infoModel = UserInfoModel.getInstance();
                        logDetail.setPriasenum(Integer.parseInt(logDetail.getPriasenum()) + 1 + "");
                        logDetail.setIfpriasenum(Constants.HAS_ZAN);
                        logDetail.setUserNames(StringUtil.appendDotAll(logDetail.getUserNames(), infoModel.getUser().getNickname(), infoModel.getUser().getMobile()));
                        cb_zan.setText(logDetail.getPriasenum());
                        tv_zan_name.setText(logDetail.getUserNames());
                        zanSet();
                        //向服务器提交
                        UserModel info = UserInfoModel.getInstance().getUser();
                        service.clickLike(UserInfoModel.getInstance().getToken(),
                                Long.parseLong(info.getUserid()), Long.parseLong(logDetail.getLossLogId()),
                                new RequestCallback<ResponseData<Zan>>() {
                                    @Override
                                    public void success(ResponseData<Zan> zanResponseData, Response response) {
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        super.failure(error);
                                        int priase = Integer.parseInt(logDetail.getPriasenum()) - 1 < 0 ? 0 : Integer.parseInt(logDetail.getPriasenum()) - 1;
                                        logDetail.setPriasenum(priase+"");
                                        String del = StringUtils.removeEnd(StringUtils.removeEnd(logDetail.getUserNames(), infoModel.getUser().getNickname()), ",");
                                        logDetail.setUserNames(del);
                                        logDetail.setIfpriasenum(Constants.NO_ZAN);
                                        cb_zan.setText(priase + "");
                                        tv_zan_name.setText(logDetail.getUserNames());
                                        zanSet();
                                    }
                                });
                    }
                }
                break;
        }
    }

    @Override
    public void getLogDetail(LogStoryDetailModel log) {
        dialogDissmiss();
        try {
            if(log==null){
                return;
            }
            this.logDetail=log;
            tv_name.setText(log.getUserName());
            tv_content.setText(log.getLogContent());
            String date=log.getCreateDate();
            tv_date.setText(DateUtil.getInstance().convertDateStr(date,"yyyy年MM月dd日 HH:mm"));
            tv_totle_lw.setText(log.getAfterWeight()+"斤");
            cb_zan.setText(log.getPriasenum());
            if(StringUtils.isNotEmpty(log.getUserNames())){
                ll_zan.setVisibility(View.VISIBLE);
                tv_zan_name.setText(log.getUserNames());
            }else {
                ll_zan.setVisibility(View.GONE);
            }
            if(Constants.HAS_ZAN.equals(log.getIfpriasenum())){
                cb_zan.setChecked(true);
                cb_zan.setEnabled(false);
            }else if(Constants.NO_ZAN.equals(log.getIfpriasenum())){
                cb_zan.setChecked(false);
                cb_zan.setEnabled(true);
            }
            if(StringUtils.isNotEmpty(log.getPhoto())){
                Picasso.with(this).load(AddressManager.get("photoHost")+log.getPhoto()).fit().placeholder(R.drawable.img_default)
                        .error(R.drawable.img_default).into(civ_header_image);
            }else {
                Picasso.with(this).load(R.drawable.img_default).into(civ_header_image);
            }
            //拆分字符串图片列表,并添加到图片集合中
            if(!"".equals(log.getImgCollection())&&!(null==log.getImgCollection())){
                String[] image=log.getImgCollection().split(",");
                images.clear();
                images.addAll(Arrays.asList(image));
            }
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }

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
            if(log!=null){
                i.putExtra("log",log);
            }
            setResult(RESULT_OK,i);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent in=new Intent(this, PictureMoreActivity.class);
        in.putStringArrayListExtra("images", (ArrayList<String>) images);
        in.putExtra("position",position);
        ActivityOptionsCompat optionsCompat=ActivityOptionsCompat.makeScaleUpAnimation(view,view.getWidth()/2,view.getHeight()/2,0,0);
        ActivityCompat.startActivity(this,in,optionsCompat.toBundle());
    }
}
