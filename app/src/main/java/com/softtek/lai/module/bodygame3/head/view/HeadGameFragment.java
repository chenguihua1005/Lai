package com.softtek.lai.module.bodygame3.head.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame3.head.model.ClasslistModel;
import com.softtek.lai.module.bodygame3.head.model.HeadModel2;
import com.softtek.lai.module.bodygame3.head.model.NewsModel;
import com.softtek.lai.module.bodygame3.head.net.HeadService;
import com.softtek.lai.module.bodygame3.home.event.UpdateClass;
import com.softtek.lai.module.bodygame3.more.view.CreateClassActivity;
import com.softtek.lai.module.message2.view.Message2Activity;
import com.softtek.lai.utils.RequestCallback;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;


@InjectLayout(R.layout.noclass_fragment)
public class HeadGameFragment extends LazyBaseFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    @InjectView(R.id.tv_totalperson)
    TextView tv_totalperson;
    @InjectView(R.id.tv_total_loss)
    TextView tv_total_loss;
    @InjectView(R.id.fl_right)
    LinearLayout fl_right;
    @InjectView(R.id.iv_banner)
    ImageView iv_banner;
    @InjectView(R.id.searchContent)
    EditText searchContent;
    @InjectView(R.id.pull)
    SwipeRefreshLayout pull;
    @InjectView(R.id.search_btn)
    Button search_btn;
    @InjectView(R.id.ivhead2_refresh)
    ImageView ivhead2_refresh;
    @InjectView(R.id.iv_email)
    ImageView iv_email;
    @InjectView(R.id.button)
    Button button;
    @InjectView(R.id.pc_tv)
    TextView pc_tv;
    @InjectView(R.id.sp_tv)
    TextView sp_tv;
    @InjectView(R.id.classed_tv)
    TextView classed_tv;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.lin_nostart)
    LinearLayout lin_nostart;
    Animation roate;
    HeadService service;

    private AddClass addClass;

    public static HeadGameFragment getInstance(AddClass addClass) {
        HeadGameFragment fragment = new HeadGameFragment();
        fragment.setAddClass(addClass);
        return fragment;
    }

    public void setAddClass(AddClass addClass) {
        this.addClass = addClass;
    }

    @Override
    protected void lazyLoad() {
        Log.i("没有班级数据是否刷新了");

    }

    @Override
    protected void initViews() {
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        pull.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        if (Integer.parseInt(UserInfoModel.getInstance().getUser().getUserrole()) == Constants.SP) {
            if (UserInfoModel.getInstance().getUser().getHasThClass() == 1) {
                if (UserInfoModel.getInstance().getUser().getDoingClass() == 0) {
                    classed_tv.setText("您的班级尚未开始哦, 请耐心等待! 现在可以切换到\n“更多”, 查看新班级并开始邀请小伙伴");
                    sp_tv.setVisibility(View.GONE);
                    pc_tv.setVisibility(View.GONE);
                    button.setVisibility(View.VISIBLE);
                    button.setOnClickListener(this);
                }
            } else if (UserInfoModel.getInstance().getUser().getHasThClass() == 0) {

                sp_tv.setVisibility(View.VISIBLE);
                pc_tv.setVisibility(View.GONE);
                button.setVisibility(View.VISIBLE);
                button.setOnClickListener(this);
            }

        } else {
            if (UserInfoModel.getInstance().getUser().getHasThClass() == 1) {
                if (UserInfoModel.getInstance().getUser().getDoingClass() == 0) {
                    classed_tv.setText("您的班级尚未开始哦, 请耐心等待! 现在可以切换到\n“更多”， 查看新班级");
                    sp_tv.setVisibility(View.GONE);
                    pc_tv.setVisibility(View.GONE);
                    button.setVisibility(View.GONE);
                }
            } else if (UserInfoModel.getInstance().getUser().getHasThClass() == 0) {
                sp_tv.setVisibility(View.GONE);
                pc_tv.setVisibility(View.VISIBLE);
                button.setVisibility(View.GONE);
            }
        }
        pull.setOnRefreshListener(this);
        ll_left.setOnClickListener(this);
        search_btn.setOnClickListener(this);
        service = ZillaApi.NormalRestAdapter.create(HeadService.class);
        ivhead2_refresh.setOnClickListener(this);
        searchContent.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        Log.i("initView没有班级页面加载。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
    }


    @Override
    protected void initDatas() {
        roate = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
        EventBus.getDefault().register(this);
        pull.setRefreshing(true);
        onRefresh();
        Log.i("initData没有班级页面加载。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onRefresh() {
        service.getsecond(UserInfoModel.getInstance().getToken(), new RequestCallback<ResponseData<HeadModel2>>() {
            @Override
            public void success(ResponseData<HeadModel2> headModel2ResponseData, Response response) {
                pull.setRefreshing(false);
                if (headModel2ResponseData.getData() != null) {
                    HeadModel2 model2 = headModel2ResponseData.getData();
                    tv_totalperson.setText(model2.getTotalPerson() + "");
                    tv_total_loss.setText(model2.getTotalLossWeight() + "");
                    String basePath = AddressManager.get("photoHost");
                    //首页banner
                    if (StringUtils.isNotEmpty(model2.getThemePic())) {
                        Picasso.with(getContext()).load(basePath + model2.getThemePic()).fit().placeholder(R.drawable.default_icon_rect)
                                .error(R.drawable.default_icon_rect).into(iv_banner);
                    } else {
                        Picasso.with(getContext()).load(R.drawable.default_icon_rect).into(iv_banner);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                pull.setRefreshing(false);
                super.failure(error);
            }
        });
        service.hasemail(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), new RequestCallback<ResponseData<NewsModel>>() {
            @Override
            public void success(ResponseData<NewsModel> responseData, Response response) {
                pull.setRefreshing(false);
                if (responseData.getData() != null) {
                    NewsModel newsModel = responseData.getData();
                    int has = newsModel.getNum();
                    if (has == 0) {
                        iv_email.setImageResource(R.drawable.email);
                    } else {
                        iv_email.setImageResource(R.drawable.has_email);
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivhead2_refresh:
                ivhead2_refresh.startAnimation(roate);
                roate.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                HeadService service = ZillaApi.NormalRestAdapter.create(HeadService.class);
                                service.getsecond(UserInfoModel.getInstance().getToken(), new RequestCallback<ResponseData<HeadModel2>>() {
                                    @Override
                                    public void success(ResponseData<HeadModel2> headModel2ResponseData, Response response) {
                                        try {
                                            ivhead2_refresh.clearAnimation();
                                            if (headModel2ResponseData.getData() != null) {
                                                HeadModel2 model2 = headModel2ResponseData.getData();
                                                tv_totalperson.setText(model2.getTotalPerson() + "");
                                                tv_total_loss.setText(model2.getTotalLossWeight() + "");
                                                String basePath = AddressManager.get("photoHost");
                                                //首页banner
                                                if (StringUtils.isNotEmpty(model2.getThemePic())) {
                                                    Picasso.with(getContext()).load(basePath + model2.getThemePic()).placeholder(R.drawable.default_icon_rect)
                                                            .error(R.drawable.default_icon_rect).into(iv_banner);
                                                } else {
                                                    Picasso.with(getContext()).load(R.drawable.default_icon_rect).into(iv_banner);
                                                }
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        try {
                                            ivhead2_refresh.clearAnimation();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                            }
                        }, 5000);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                break;
            case R.id.search_btn:
                final String text = searchContent.getText().toString().trim();

                if (StringUtils.isNotEmpty(text)) {
                    dialogShow("正在查找...");
                    ZillaApi.NormalRestAdapter.create(HeadService.class).getclass(UserInfoModel.getInstance().getToken(),
                            text, new RequestCallback<ResponseData<List<ClasslistModel>>>() {
                                @Override
                                public void success(ResponseData<List<ClasslistModel>> data, Response response) {

                                    if (data.getStatus() == 200) {
                                        if (data.getData() != null && !data.getData().isEmpty()) {
                                            Intent intent = new Intent(getContext(), SearchClassActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putParcelableArrayList("class", (ArrayList<ClasslistModel>) data.getData());
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                            dialogDissmiss();

                                        }

                                    } else if (data.getStatus() == 100) {
                                        dialogDissmiss();
                                        Util.toastMsg(data.getMsg());
                                    }
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    dialogDissmiss();
                                    super.failure(error);
                                }
                            });

                } else {
                    Util.toastMsg("请输入班级名称或班级编号");
                }
                break;
            case R.id.button:
                Intent intent = new Intent(getContext(), CreateClassActivity.class);
                startActivity(intent);
                break;
            case R.id.fl_right:
                Intent intent2 = new Intent(getContext(), Message2Activity.class);
                startActivity(intent2);
                break;
            case R.id.ll_left:
                getActivity().finish();
                break;
        }
    }

    @Subscribe
    public void updateClass(UpdateClass clazz) {
        if (clazz.getStatus() == 1) {
            //添加新班级
            if (clazz.getModel().getClassStatus() == 1) {
                Log.i("新增班级接受通知。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
                if (addClass != null) {
                    addClass.addclass();
                }
            } else if (clazz.getModel().getClassStatus() == 0) {
                if (Integer.parseInt(UserInfoModel.getInstance().getUser().getUserrole()) == Constants.SP) {
                    if (UserInfoModel.getInstance().getUser().getHasThClass() == 1) {
                        if (UserInfoModel.getInstance().getUser().getDoingClass() == 0) {
                            classed_tv.setText("您的班级尚未开始哦, 请耐心等待! 现在可以切换到“更多”,\n 查看新班级并开始邀请小伙伴");
                            sp_tv.setVisibility(View.GONE);
                            pc_tv.setVisibility(View.GONE);
                            button.setVisibility(View.VISIBLE);
                            button.setOnClickListener(this);
                        }
                    } else if (UserInfoModel.getInstance().getUser().getHasThClass() == 0) {

                        sp_tv.setVisibility(View.VISIBLE);
                        pc_tv.setVisibility(View.GONE);
                        button.setVisibility(View.VISIBLE);
                        button.setOnClickListener(this);
                    }

                } else {
                    if (UserInfoModel.getInstance().getUser().getHasThClass() == 1) {
                        if (UserInfoModel.getInstance().getUser().getDoingClass() == 0) {
                            classed_tv.setText("您的班级尚未开始哦, 请耐心等待! 现在可以切换到“更多”，\n 查看新班级");
                            sp_tv.setVisibility(View.GONE);
                            pc_tv.setVisibility(View.GONE);
                            button.setVisibility(View.GONE);
                        }
                    } else if (UserInfoModel.getInstance().getUser().getHasThClass() == 0) {
                        sp_tv.setVisibility(View.GONE);
                        pc_tv.setVisibility(View.VISIBLE);
                        button.setVisibility(View.GONE);
                    }
                }
            }

        }

    }

    public interface AddClass {
        void addclass();
    }
}
