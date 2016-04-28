package com.softtek.lai.module.lossweightstory.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.community.model.HealthyDynamicModel;
import com.softtek.lai.module.grade.model.BannerModel;
import com.softtek.lai.module.grade.net.GradeService;
import com.softtek.lai.module.lossweightstory.adapter.LossWeightStoryAdapter;
import com.softtek.lai.module.lossweightstory.model.LogList;
import com.softtek.lai.module.lossweightstory.model.LogStoryModel;
import com.softtek.lai.module.lossweightstory.model.LossWeightStoryModel;
import com.softtek.lai.module.lossweightstory.presenter.LossWeightStoryManager;
import com.softtek.lai.module.studetail.view.LossWeightLogActivity;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;
import com.sw926.imagefileselector.ImageFileCropSelector;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_loss_weight_story)
public class LossWeightStoryActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener
,PullToRefreshBase.OnRefreshListener2<ListView>,LossWeightStoryManager.StoryManagerCallBack,ImageFileCropSelector.Callback{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.fl_right)
    FrameLayout fl;

    @InjectView(R.id.ptrlv)
    PullToRefreshListView ptrlv;
    //列表内容
    private ImageView log_banner;
    private TextView tv_name;
    private CircleImageView cir_header_image;

    private ImageFileCropSelector imageFileCropSelector;

    private LossWeightStoryManager lossWeightStoryManager;
    private List<LossWeightStoryModel> lossWeightStoryModels=new ArrayList<>();
    private LossWeightStoryAdapter adapter;
    int pageIndex=1;
    int totalPage;

    CharSequence[] items={"拍照","从相册上传"};
    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        tv_title.setText("减重故事");
        tv_right.setText("新故事");
        fl.setOnClickListener(this);
        View view=getLayoutInflater().inflate(R.layout.loss_weight_log_header,null,false);
        tv_name= (TextView) view.findViewById(R.id.tv_name);
        cir_header_image= (CircleImageView) view.findViewById(R.id.civ_header_image);
        log_banner= (ImageView) view.findViewById(R.id.log_header_image);
        ptrlv.getRefreshableView().addHeaderView(view);
        ptrlv.setMode(PullToRefreshBase.Mode.BOTH);
        ptrlv.setOnItemClickListener(this);
        ptrlv.setOnRefreshListener(this);
        cir_header_image.setOnClickListener(this);
        imageFileCropSelector=new ImageFileCropSelector(this);
        imageFileCropSelector.setOutPutImageSize(DisplayUtil.getMobileWidth(this),DisplayUtil.dip2px(this,195));
        imageFileCropSelector.setOutPutAspect(4,3);
        imageFileCropSelector.setOutPut(DisplayUtil.getMobileWidth(this),DisplayUtil.dip2px(this,195));
        imageFileCropSelector.setCallback(this);
        log_banner.setLongClickable(true);
        log_banner.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(LossWeightStoryActivity.this).setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                imageFileCropSelector.takePhoto(LossWeightStoryActivity.this);
                                break;
                            case 1:
                                imageFileCropSelector.selectImage(LossWeightStoryActivity.this);
                                break;
                        }
                    }
                }).create().show();
                return false;
            }
        });
    }

    @Override
    protected void initDatas() {
        service=ZillaApi.NormalRestAdapter.create(GradeService.class);
        lossWeightStoryManager=new LossWeightStoryManager(this);
        adapter=new LossWeightStoryAdapter(this,lossWeightStoryModels);
        ptrlv.setAdapter(adapter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrlv.setRefreshing();
            }
        },200);
    }

    public static final int SEND_NEW_STORY=1;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
            case R.id.fl_right:
                //跳转新故事
                startActivityForResult(new Intent(this,NewStoryActivity.class),SEND_NEW_STORY);
                break;
            case R.id.civ_header_image:
                Intent i=new Intent(this, LossWeightLogActivity.class);
                i.putExtra("accountId",Long.parseLong(UserInfoModel.getInstance().getUser().getUserid()));
                i.putExtra("review",1);
                startActivity(i);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageFileCropSelector.onActivityResult(requestCode,resultCode,data);
        imageFileCropSelector.getmImageCropperHelper().onActivityResult(requestCode,resultCode,data);
        if(resultCode==RESULT_OK){
            if(requestCode==SEND_NEW_STORY){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptrlv.setRefreshing();
                    }
                }, 200);

            }else if(requestCode==LIST_JUMP){
                int position=data.getIntExtra("position",-1);
                LossWeightStoryModel model=data.getParcelableExtra("log");
                if(position!=-1&&model!=null){
                    lossWeightStoryModels.get(position).setIsClicked(model.getIsClicked());
                    lossWeightStoryModels.get(position).setUsernameSet(model.getUsernameSet());
                    lossWeightStoryModels.get(position).setPriase(model.getPriase());
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    private static final int LIST_JUMP=2;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("position="+position);
        if(position<2){
            return;
        }
        Intent intent=new Intent(this,LogStoryDetailActivity.class);
        intent.putExtra("log",lossWeightStoryModels.get(position-2));
        intent.putExtra("position",position-2);
        startActivityForResult(intent,LIST_JUMP);
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        String userId=UserInfoModel.getInstance().getUser().getUserid();
        pageIndex=1;
        lossWeightStoryManager.getLossWeightLogForClass(Long.parseLong(userId),1);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        String userId=UserInfoModel.getInstance().getUser().getUserid();
        pageIndex++;
        if(pageIndex<=totalPage){
            lossWeightStoryManager.getLossWeightLogForClass(Long.parseLong(userId),pageIndex);
        }else{
            pageIndex--;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ptrlv.onRefreshComplete();

                }
            },300);
        }
    }

    @Override
    public void getStroyList(LogList logList) {
        ptrlv.onRefreshComplete();
        if (logList==null){
            pageIndex=--pageIndex<1?1:pageIndex;
            return;
        }
        tv_name.setText(logList.getUserName());
        totalPage=Integer.parseInt(logList.getTotalPage());
        List<LossWeightStoryModel> models=logList.getLogList();
        if(models==null||models.isEmpty()){
            pageIndex=--pageIndex<1?1:pageIndex;
            return;
        }
        if (pageIndex==1){
            lossWeightStoryModels.clear();
        }
        lossWeightStoryModels.addAll(models);
        adapter.notifyDataSetChanged();
        String path= AddressManager.get("photoHost");
        try {
            Picasso.with(this).load(path + logList.getPhoto()).fit()
                    .placeholder(R.drawable.img_default)
                    .error(R.drawable.img_default)
                    .into(cir_header_image);
            Picasso.with(this).load(path + logList.getBanner()).fit()
                    .placeholder(R.drawable.default_pic)
                    .error(R.drawable.default_pic)
                    .into(log_banner);
        }catch (Exception e){}

    }

    private GradeService service;

    @Override
    public void onSuccess(final String file) {
        String token=UserInfoModel.getInstance().getToken();
        service.updateClassBanner(token, Long.parseLong(UserInfoModel.getInstance().getUser().getUserid()),
                "1",
                new TypedFile("image/*", new File(file)),
                new Callback<ResponseData<BannerModel>>() {
                    @Override
                    public void success(ResponseData<BannerModel> bannerModelResponseData, Response response) {
                        Log.i("logbanner===="+bannerModelResponseData.getData().getPath());
                        Picasso.with(LossWeightStoryActivity.this).load(AddressManager.get("photoHost")+bannerModelResponseData.getData().getPath()).fit().
                                placeholder(R.drawable.default_pic).error(R.drawable.default_pic).into(log_banner);
                        new File(file).delete();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        ZillaApi.dealNetError(error);
                    }
                });
    }

    @Override
    public void onError() {

    }
}
