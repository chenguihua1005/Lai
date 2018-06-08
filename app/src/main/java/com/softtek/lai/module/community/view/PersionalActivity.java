package com.softtek.lai.module.community.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.community.adapter.DynamicRecyclerViewAdapter;
import com.softtek.lai.module.community.eventModel.DeleteFocusEvent;
import com.softtek.lai.module.community.eventModel.DeleteRecommedEvent;
import com.softtek.lai.module.community.eventModel.FocusEvent;
import com.softtek.lai.module.community.eventModel.Where;
import com.softtek.lai.module.community.model.PersonalListModel;
import com.softtek.lai.module.community.model.PersonalRecommendModel;
import com.softtek.lai.module.community.net.CommunityService;
import com.softtek.lai.module.community.presenter.CommunityManager;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.utils.StringUtil;
import com.softtek.lai.widgets.CircleImageView;
import com.softtek.lai.widgets.LinearLayoutManagerWrapper;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.InjectView;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_persional)
public class PersionalActivity extends BaseActivity implements CommunityManager.CommunityManagerCallback<PersonalRecommendModel>{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.cir_image)
    CircleImageView circleImageView;
    @InjectView(R.id.tv_name)
    TextView tv_name;
    @InjectView(R.id.tv_dynamic_num)
    TextView tv_dynamic_num;
    @InjectView(R.id.cb_attention)
    CheckBox cb_attention;

    @InjectView(R.id.recycleView)
    RecyclerView recyclerView;
    @InjectView(R.id.refresh)
    SwipeRefreshLayout refresh;
    @InjectView(R.id.empty_view)
    ImageView empty_view;

    private List<PersonalListModel> dynamics;
    private DynamicRecyclerViewAdapter adapter;
    private CommunityManager manager;

    private int totalPage=0;
    private int pageIndex=1;
    long personalId=0;
    private static final int LOADCOUNT=5;
    private int lastVisitableItem;
    private boolean isLoading=false;
    int isFocus;
    private int total;
    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("isFocus",isFocus );
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManagerWrapper(this));
        refresh.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex=1;
                manager.getHealthyMine(personalId,1);
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int count=adapter.getItemCount();
                if(newState==RecyclerView.SCROLL_STATE_IDLE&&count>LOADCOUNT&&lastVisitableItem+1==count){

                    if(!isLoading&&pageIndex<=totalPage){
                        pageIndex++;
                        if(pageIndex<=totalPage){
                            isLoading=true;
                            //加载更多数据
                            manager.getHealthyMine(personalId,pageIndex);
                        }else {
                            pageIndex--;
                            adapter.notifyItemRemoved(adapter.getItemCount());
                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager llm= (LinearLayoutManager) recyclerView.getLayoutManager();
                lastVisitableItem=llm.findLastVisibleItemPosition();
            }
        });
    }

    boolean isMine=false;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        personalId=Long.parseLong(intent.getStringExtra("personalId"));
        if(personalId == UserInfoModel.getInstance().getUserId()){
            cb_attention.setVisibility(View.GONE);
            tv_title.setText("我");
            UserModel user=UserInfoModel.getInstance().getUser();
            if(user!=null){
                tv_name.setText(StringUtil.showName(user.getNickname(),user.getMobile()));
            }else {
                tv_name.setText("我");
            }
            isMine=true;
        }else {
            String userName=intent.getStringExtra("personalName");
            tv_title.setText(userName);
            tv_name.setText(userName);
            cb_attention.setVisibility(View.VISIBLE);
            isMine=false;
        }
        tv_title.append("的动态");
        isFocus=intent.getIntExtra("isFocus",0);
        if(isFocus==0){
            cb_attention.setChecked(false);
        }else {
            cb_attention.setChecked(true);
        }
        refresh.setRefreshing(true);
        manager.getHealthyMine(personalId,1);
    }

    @Override
    protected void initDatas() {
        personalId=Long.parseLong(getIntent().getStringExtra("personalId"));
        if(personalId == UserInfoModel.getInstance().getUserId()){
            cb_attention.setVisibility(View.GONE);
            tv_title.setText("我");
            UserModel user=UserInfoModel.getInstance().getUser();
            if(user!=null){
                tv_name.setText(StringUtil.showName(user.getNickname(),user.getMobile()));
            }else {
                tv_name.setText("我");
            }
            isMine=true;
        }else {
            String userName=getIntent().getStringExtra("personalName");
            tv_title.setText(userName);
            tv_name.setText(userName);
            cb_attention.setVisibility(View.VISIBLE);
            isMine=false;
        }
        tv_title.append("的动态");
        dynamics=new ArrayList();
        manager=new CommunityManager(this);
        adapter=new DynamicRecyclerViewAdapter(this,dynamics,isMine);
        recyclerView.setAdapter(adapter);

        isFocus=getIntent().getIntExtra("isFocus",0);
        if(isFocus==0){
            cb_attention.setChecked(false);
        }else {
            cb_attention.setChecked(true);
        }
        cb_attention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserInfoModel.getInstance().isVr()){
                    cb_attention.setChecked(false);
                    new AlertDialog.Builder(PersionalActivity.this).setMessage("您当前是游客身份，请登录后再试").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent login = new Intent(PersionalActivity.this, LoginActivity.class);
                            login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(login);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).create().show();
                }else {
                    UserInfoModel infoModel = UserInfoModel.getInstance();
                    if (cb_attention.isChecked()) {
                        EventBus.getDefault().post(new FocusEvent(personalId + "", 1,Where.PERSONAL_DYNAMIC_LIST));
                        ZillaApi.NormalRestAdapter.create(CommunityService.class)
                                .focusAccount(infoModel.getToken(),
                                        infoModel.getUserId(),
                                        personalId,
                                        new RequestCallback<ResponseData>() {
                                            @Override
                                            public void success(ResponseData responseData, Response response) {
                                                int status=responseData.getStatus();
                                                switch (status)
                                                {
                                                    case 200:
                                                        isFocus=1;
                                                        break;
                                                }
                                            }
                                        });

                    } else {
                        EventBus.getDefault().post(new FocusEvent(personalId + "", 0,Where.PERSONAL_DYNAMIC_LIST));
                        EventBus.getDefault().post(new DeleteFocusEvent(personalId+""));
                        ZillaApi.NormalRestAdapter.create(CommunityService.class)
                                .cancleFocusAccount(infoModel.getToken(),
                                        infoModel.getUserId(),
                                        personalId,
                                        new RequestCallback<ResponseData>() {
                                            @Override
                                            public void success(ResponseData responseData, Response response) {
                                                int status=responseData.getStatus();
                                                switch (status)
                                                {
                                                    case 200:
                                                        isFocus=0;
                                                        break;
                                                }
                                            }
                                        });
                    }
                }
            }
        });
        refresh.setRefreshing(true);
        manager.getHealthyMine(personalId,1);
    }

    @Override
    public void getMineDynamic(PersonalRecommendModel model) {
        try {
            if(isLoading==true){
                isLoading=false;
                adapter.notifyItemRemoved(adapter.getItemCount());
            }else {
                refresh.setRefreshing(false);
            }
            //加载图片
            String path = AddressManager.get("photoHost");
            Picasso.with(this).load(path + model.getPhoto()).fit()
                    .placeholder(R.drawable.img_default).error(R.drawable.img_default).into(circleImageView);
            tv_name.setText(model.getUserName());
            totalPage=model.getTotalPage();
            total=model.getHealthCount();
            tv_dynamic_num.setText("共有");
            tv_dynamic_num.append(String.valueOf(model.getHealthCount()));
            tv_dynamic_num.append("条动态");
            List<PersonalListModel> models=model.getHealthList();
            if(models==null||models.isEmpty()){
                pageIndex=--pageIndex<1?1:pageIndex;
                return;
            }
            if(pageIndex==1){
                dynamics.clear();
            }
            dynamics.addAll(models);
            adapter.notifyDataSetChanged();
            if(dynamics.isEmpty()){
                empty_view.setVisibility(View.VISIBLE);
            }else{
                empty_view.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void updateNum(){
        Log.i("更新了");
        try {
            tv_dynamic_num.setText("共有");
            total=(total-1)<0?0:(total-1);
            tv_dynamic_num.append(String.valueOf(total));
            tv_dynamic_num.append("条动态");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            //do something...
            Intent intent=new Intent();
            intent.putExtra("isFocus",isFocus );
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void refreshList(FocusEvent event) {
        if(event.getWhere()!=Where.PERSONAL_DYNAMIC_LIST){
            if(personalId==Long.parseLong(event.getAccountId())){
                isFocus=event.getFocusStatus();
                if(isFocus==0){
                    cb_attention.setChecked(false);
                }else {
                    cb_attention.setChecked(true);
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Subscribe
    public void refreshListDelete(DeleteRecommedEvent event) {
        if(event.getWhere()!= Where.PERSONAL_DYNAMIC_LIST){
            Iterator<PersonalListModel> iterator=dynamics.iterator();
            while (iterator.hasNext()){
                PersonalListModel model=iterator.next();
                if (model.getID().equals(event.getDynamicId())) {
                    iterator.remove();
                    break;
                }
            }
            adapter.notifyDataSetChanged();

        }
    }
}
