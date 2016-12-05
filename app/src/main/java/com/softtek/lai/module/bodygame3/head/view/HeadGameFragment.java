package com.softtek.lai.module.bodygame3.head.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame3.head.model.ClasslistModel;
import com.softtek.lai.module.bodygame3.head.model.HeadModel2;
import com.softtek.lai.module.bodygame3.head.model.NewsModel;
import com.softtek.lai.module.bodygame3.head.net.HeadService;
import com.softtek.lai.module.bodygame3.more.model.ClassModel;
import com.softtek.lai.utils.RequestCallback;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@linkHeadGameFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@linkHeadGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@InjectLayout(R.layout.fragment_head_game)
public class HeadGameFragment extends LazyBaseFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    @InjectView(R.id.tv_totalperson)
    TextView tv_totalperson;
    @InjectView(R.id.tv_total_loss)
    TextView tv_total_loss;
    @InjectView(R.id.iv_banner)
    ImageView iv_banner;
    @InjectView(R.id.toolbar)
    RelativeLayout relativeLayout;
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
    Animation roate;
    HeadService service;
    private List<ClasslistModel> classlistModels = new ArrayList<ClasslistModel>();
    private List<ClasslistModel> classlistModels_temp = new ArrayList<ClasslistModel>();


    @Override
    protected void lazyLoad() {
        pull.post(new Runnable() {
            @Override
            public void run() {
                pull.setRefreshing(true);
            }
        });
        onRefresh();
    }

    @Override
    protected void initViews() {
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//        if (DisplayUtil.getSDKInt() > 18) {
//            int status = DisplayUtil.getStatusHeight(getActivity());
//            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) relativeLayout.getLayoutParams();
//            params.topMargin = status;
//            relativeLayout.setLayoutParams(params);
//        }

        if (UserInfoModel.getInstance().getRole().getSp().equals(String.valueOf(Constants.SP))) {
            sp_tv.setVisibility(View.GONE);
            pc_tv.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);
        } else {
            sp_tv.setVisibility(View.VISIBLE);
            pc_tv.setVisibility(View.GONE);
            button.setVisibility(View.GONE);
        }
        pull.setOnRefreshListener(this);
        search_btn.setOnClickListener(this);
        service = ZillaApi.NormalRestAdapter.create(HeadService.class);
        ivhead2_refresh.setOnClickListener(this);
        searchContent.setOnClickListener(this);

    }

    private void secondhead2() {
        pull.setRefreshing(false);

        service.getsecond(UserInfoModel.getInstance().getToken(), new RequestCallback<ResponseData<HeadModel2>>() {
            @Override
            public void success(ResponseData<HeadModel2> headModel2ResponseData, Response response) {
//                Util.toastMsg(headModel2ResponseData.getMsg());
                if (headModel2ResponseData.getData() != null) {
                    HeadModel2 model2 = headModel2ResponseData.getData();
                    tv_totalperson.setText(model2.getTotalPerson() + "");
                    tv_total_loss.setText(model2.getTotalLossWeight() + "");
                    String basePath = AddressManager.get("photoHost");
                    //首页banner
                    if (StringUtils.isNotEmpty(model2.getThemePic())) {
                        Picasso.with(getContext()).load(basePath + model2.getThemePic()).placeholder(R.drawable.default_icon_rect)
                                .error(R.drawable.default_icon_rect).into(iv_banner);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }

    @Override
    protected void initDatas() {
        roate = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
        hasemail();
        secondhead2();
    }

    private void hasemail() {
        service.hasemail(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), new RequestCallback<ResponseData<NewsModel>>() {
            @Override
            public void success(ResponseData<NewsModel> responseData, Response response) {
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
    public void onRefresh() {

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
                        }, 500);
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
                dialogShow("正在查找...");
                if (StringUtils.isNotEmpty(text)) {
                    ZillaApi.NormalRestAdapter.create(HeadService.class).getclass(UserInfoModel.getInstance().getToken(),
                            text, new RequestCallback<ResponseData<List<ClasslistModel>>>() {
                                @Override
                                public void success(ResponseData<List<ClasslistModel>> data, Response response) {

                                    if (data.getStatus() == 200) {
                                        boolean hasThisItem = false;//
                                        classlistModels.clear();
                                        classlistModels_temp.clear();
                                        classlistModels.addAll(data.getData());
                                        for (int i = 0; i < classlistModels.size(); i++) {
                                            ClasslistModel model = classlistModels.get(i);
                                            if (model.getClassName().contains(text)) {
                                                classlistModels_temp.add(model);
                                            }
                                        }
                                        if (classlistModels_temp.size()>0){
                                            Intent intent = new Intent(getContext(), SearchClassActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putParcelableArrayList("class", (ArrayList<ClasslistModel>) classlistModels_temp);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                            dialogDissmiss();

                                        }else {
                                            dialogDissmiss();
                                            Util.toastMsg("暂无此班级");
                                            return;

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

                }

//                String content=searchContent.getText().toString().trim();
//                service.getpartner(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUser().getUserid(), content, new Callback<ResponseData<PartnersModel>>() {
//                    @Override
//                    public void success(ResponseData<PartnersModel> partnersModelResponseData, Response response) {
//
//                    }
//
//                    @Override
//                    public void failure(RetrofitError error) {
//
//                    }
//                });
                break;
        }
    }
}
