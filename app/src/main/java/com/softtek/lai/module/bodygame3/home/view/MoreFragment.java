package com.softtek.lai.module.bodygame3.home.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame3.head.view.HonorActivity;
import com.softtek.lai.module.bodygame3.home.event.UpdateClass;
import com.softtek.lai.module.bodygame3.more.model.ClassModel;
import com.softtek.lai.module.bodygame3.more.net.MoreService;
import com.softtek.lai.module.bodygame3.more.view.CreateClassActivity;
import com.softtek.lai.module.bodygame3.more.view.MoreHasFragment;
import com.softtek.lai.module.bodygame3.more.view.MoreNoClassFragment;
import com.softtek.lai.module.bodygame3.more.view.PastReviewActivity;
import com.softtek.lai.module.counselor.view.GameActivity;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_more)
public class MoreFragment extends LazyBaseFragment implements MoreHasFragment.DeleteClass{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.iv_left)
    ImageView iv_left;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;

    @InjectView(R.id.head_image)
    CircleImageView head_image;
    @InjectView(R.id.tv_name)
    TextView tv_name;

    @InjectView(R.id.ll_saikuang)
    LinearLayout ll_saikuang;
    @InjectView(R.id.ll_history)
    LinearLayout ll_history;
    @InjectView(R.id.ll_honor)
    LinearLayout  ll_honor;



    public MoreFragment() {

    }

    private int classCount=0;

    @Override
    protected void lazyLoad() {
        ZillaApi.NormalRestAdapter.create(MoreService.class)
                .getMoreInfo(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId()
                        , new RequestCallback<ResponseData<List<ClassModel>>>() {
                            @Override
                            public void success(ResponseData<List<ClassModel>> listResponseData, Response response) {
                                if (listResponseData.getData() != null
                                        && !listResponseData.getData().isEmpty()) {
                                    MoreHasFragment fragment=MoreHasFragment.getInstance(MoreFragment.this);
                                    classCount=listResponseData.getData().size();
                                    Bundle bundle=new Bundle();
                                    bundle.putParcelableArrayList("class", (ArrayList<ClassModel>) listResponseData.getData());
                                    fragment.setArguments(bundle);
                                    getChildFragmentManager().beginTransaction().replace(R.id.fl_container,fragment).commit();
                                }else {
                                    //没有班级的样式
                                    classCount=0;
                                    getChildFragmentManager().beginTransaction().replace(R.id.fl_container,new MoreNoClassFragment()).commit();
                                }
                            }
                        });
    }

    @Override
    protected void initViews() {
        tv_title.setText("更多");

        UserModel user = UserInfoModel.getInstance().getUser();
        if (user != null) {
            tv_name.setText(user.getNickname());
            if (TextUtils.isEmpty(user.getPhoto())){
                Picasso.with(getContext()).load(R.drawable.img_default).into(head_image);
            }else {
                Picasso.with(getContext()).load( AddressManager.get("photoHost")+user.getPhoto()).fit()
                        .error(R.drawable.img_default)
                        .placeholder(R.drawable.img_default).into(head_image);
            }
            if(Constants.SP==Integer.parseInt(user.getUserrole())){
                tv_right.setText("开班");
                fl_right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getContext(), CreateClassActivity.class));
                    }
                });
            }
        }
        ll_saikuang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), GameActivity.class));
            }
        });
        ll_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), PastReviewActivity.class));
            }
        });
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        ll_honor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), HonorActivity.class));
            }
        });
    }

    @Override
    protected void initDatas() {
        iv_left.setImageResource(R.drawable.back_home);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Override
    public void deletClass(int classCount) {
        if(classCount==0){
            //没有班级的样式
            getChildFragmentManager().beginTransaction().replace(R.id.fl_container,new MoreNoClassFragment()).commitAllowingStateLoss();
        }
    }

    @Subscribe
    public void updateClass(UpdateClass clazz) {
         if(classCount==0&&clazz.getStatus()==1){
             classCount+=1;
             Log.i("新班级添加了");
            //添加新班级
             MoreHasFragment fragment=MoreHasFragment.getInstance(MoreFragment.this);
             Bundle bundle=new Bundle();
             ArrayList<ClassModel> list=new ArrayList<>();
             list.add(clazz.getModel());
             bundle.putParcelableArrayList("class", list);
             fragment.setArguments(bundle);
             getChildFragmentManager().beginTransaction().replace(R.id.fl_container,fragment).commitAllowingStateLoss();
        }else if(clazz.getStatus()==2){
            classCount=classCount-1<0?0:classCount-1;
         }
    }
}
