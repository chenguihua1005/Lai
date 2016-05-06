package com.softtek.lai.module.studetail.view;

import android.content.Intent;
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
import com.softtek.lai.module.lossweightstory.model.LogStoryDetailModel;
import com.softtek.lai.module.lossweightstory.model.Zan;
import com.softtek.lai.module.lossweightstory.presenter.LogStoryDetailManager;
import com.softtek.lai.module.lossweightstory.view.PictureActivity;
import com.softtek.lai.module.studetail.adapter.LogDetailGridAdapter;
import com.softtek.lai.module.studetail.adapter.LossWeightLogAdapter;
import com.softtek.lai.module.studetail.model.LossWeightLogModel;
import com.softtek.lai.module.studetail.presenter.IMemberInfopresenter;
import com.softtek.lai.module.studetail.presenter.MemberInfoImpl;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.widgets.CircleImageView;
import com.softtek.lai.widgets.CustomGridView;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_log_detail)
public class LogDetailActivity extends BaseActivity implements View.OnClickListener,LogStoryDetailManager.LogStoryDetailManagerCallback,
        AdapterView.OnItemClickListener{

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
    @InjectView(R.id.tv_zan_name)
    TextView tv_zan_name;

    private LogStoryDetailManager manager;
    List<String> images=new ArrayList();

    private IMemberInfopresenter memberInfopresenter;
    private LossWeightLogModel log;
    private LogDetailGridAdapter adapter;
    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        cb_zan.setOnClickListener(this);
        tv_title.setText("日志详情");
        cgv_list_image.setOnItemClickListener(this);
    }

    @Override
    protected void initDatas() {
        memberInfopresenter=new MemberInfoImpl(this,null);
        manager=new LogStoryDetailManager(this);
        log= (LossWeightLogModel) getIntent().getSerializableExtra("log");
        if(StringUtils.isNotEmpty(log.getPhoto())){
            Picasso.with(this).load(AddressManager.get("photoHost")+log.getPhoto()).fit().placeholder(R.drawable.img_default)
                    .error(R.drawable.img_default).into(civ_header_image);
        }
        tv_name.setText(log.getUserName());
        tv_log_title.setText(log.getLogTitle());
        tv_content.setText(log.getLogContent());
        String date=log.getCreateDate();
        tv_date.setText(DateUtil.getInstance().getYear(date)+
                "年"+DateUtil.getInstance().getMonth(date)+
                "月"+DateUtil.getInstance().getDay(date)+"日");
        tv_totle_lw.setText(log.getAfterWeight()+"斤");
        cb_zan.setText(log.getPriase());
        if(getIntent().getIntExtra("review",0)==0){
            cb_zan.setEnabled(false);
        }else{
            if(LossWeightLogAdapter.ZAN_NO.equals(log.getIsClicked())){
                cb_zan.setChecked(true);
                cb_zan.setEnabled(false);
            }else{
                cb_zan.setChecked(false);
                cb_zan.setEnabled(true);
            }
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
                Intent i=getIntent();
                i.putExtra("log",log);
                setResult(-1,i);
                finish();
                break;
            case R.id.cb_zan:
                log.setPriase(Integer.parseInt(log.getPriase())+1+"");
                log.setIsClicked("1");
                cb_zan.setText(log.getPriase());
                //向服务器提交
                memberInfopresenter.doZan(Long.parseLong(UserInfoModel.getInstance().getUser().getUserid())
                        ,Long.parseLong(log.getLossLogId()),
                        new Callback<ResponseData<Zan>>() {
                            @Override
                            public void success(ResponseData<Zan> zanResponseData, Response response) {}

                            @Override
                            public void failure(RetrofitError error) {
                                log.setPriase(Integer.parseInt(log.getPriase())-1+"");
                                log.setIsClicked("0");
                                cb_zan.setText(log.getPriase());
                                ZillaApi.dealNetError(error);
                            }
                        });
                break;
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

    @Override
    public void getLogDetail(LogStoryDetailModel log) {
        dialogDissmiss();
        if(log==null){
            return;
        }
        tv_name.setText(log.getUserName());
        tv_log_title.setText(log.getLogTitle());
        tv_content.setText(log.getLogContent());
        String date=log.getCreateDate();
        tv_date.setText(DateUtil.getInstance().getYear(date)+
                "年"+DateUtil.getInstance().getMonth(date)+
                "月"+DateUtil.getInstance().getDay(date)+"日");
        tv_totle_lw.setText(log.getAfterWeight()+"斤");
        cb_zan.setText(log.getPriasenum());
        tv_zan_name.setText(log.getUserNames());
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent in=new Intent(this, PictureActivity.class);
        in.putStringArrayListExtra("images", (ArrayList<String>) images);
        in.putExtra("position",position);
        startActivity(in);
    }
}
