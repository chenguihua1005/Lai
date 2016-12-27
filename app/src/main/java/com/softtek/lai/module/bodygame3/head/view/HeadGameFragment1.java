package com.softtek.lai.module.bodygame3.head.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ggx.widgets.adapter.ViewHolder;
import com.ggx.widgets.nicespinner.ArrowSpinner3;
import com.ggx.widgets.nicespinner.ArrowSpinnerAdapter;
import com.ggx.widgets.nicespinner.NiceSpinner;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.head.adapter.ListRecyclerAdapter;
import com.softtek.lai.module.bodygame3.head.model.ChooseModel;
import com.softtek.lai.module.bodygame3.head.model.ClassModel;
import com.softtek.lai.module.bodygame3.head.model.ClassinfoModel;
import com.softtek.lai.module.bodygame3.head.model.NewsModel;
import com.softtek.lai.module.bodygame3.head.model.PartnersModel;
import com.softtek.lai.module.bodygame3.head.model.PartnertotalModel;
import com.softtek.lai.module.bodygame3.head.model.RongyuModel;
import com.softtek.lai.module.bodygame3.head.model.SaveclassModel;
import com.softtek.lai.module.bodygame3.head.model.TuijianModel;
import com.softtek.lai.module.bodygame3.head.model.TypeModel;
import com.softtek.lai.module.bodygame3.head.model.ZhaopianModel;
import com.softtek.lai.module.bodygame3.head.net.HeadService;
import com.softtek.lai.module.bodygame3.home.event.UpdateClass;
import com.softtek.lai.module.bodygame3.photowall.PhotoWallActivity;
import com.softtek.lai.module.message2.view.Message2Activity;
import com.softtek.lai.module.picture.view.PictureMoreActivity;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CircleImageView;
import com.softtek.lai.widgets.LinearLayoutManagerWrapper;
import com.softtek.lai.widgets.MyRelative;
import com.softtek.lai.widgets.MySwipRefreshView;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

import static android.app.Activity.RESULT_OK;

@InjectLayout(R.layout.fragment_head_game_fragment1)
public class HeadGameFragment1 extends LazyBaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    @InjectView(R.id.pull)
    MySwipRefreshView refresh;
    @InjectView(R.id.appbar)
    AppBarLayout appbar;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    //toolbar标题
    @InjectView(R.id.re_photowall)
    RelativeLayout re_photowall;

    @InjectView(R.id.honor_lin)
    LinearLayout honor_lin;
    @InjectView(R.id.spinner_title1)
    ArrowSpinner3 tv_title;
    @InjectView(R.id.spinner_title)
    NiceSpinner spinner_title;
    @InjectView(R.id.list_partner)
    RecyclerView list_partner;
    @InjectView(R.id.searchContent)
    TextView searchContent;
    @InjectView(R.id.group_name)
    TextView group_name;//组名
    @InjectView(R.id.jianzhongbi_tv)
    TextView jianzhongbi_tv;//总减重比
    @InjectView(R.id.iv_group)
    ImageView iv_group;//图标
    @InjectView(R.id.student_tv)
    TextView student_tv;//优秀学员名字
    @InjectView(R.id.iv_studenticon)
    CircleImageView studenticon;//优秀学员头像
    @InjectView(R.id.student_jianzhong)
    TextView student_jianzhong;//优秀学员减重比
    @InjectView(R.id.student_jianzhi)
    TextView student_jianzhi;//优秀学员减脂比
    @InjectView(R.id.iv_imagevideo1)
    RelativeLayout iv_imagevideo1;//本周推荐1
    @InjectView(R.id.iv_imagevideo2)
    RelativeLayout iv_imagevideo2;
    @InjectView(R.id.video_type1)
    TextView video_type1;
    @InjectView(R.id.video_name1)
    TextView video_name1;
    @InjectView(R.id.video_type2)
    TextView video_type2;
    @InjectView(R.id.video_name2)
    TextView video_name2;

    @InjectView(R.id.re_honor)
    RelativeLayout re_honor;//荣誉榜
    HeadService service;
    @InjectView(R.id.iv_right)
    ImageView iv_right;


    @InjectView(R.id.viewpager)
    ViewPager viewPager;
    @InjectView(R.id.rl_container)
    MyRelative rl_container;
    @InjectView(R.id.week_rel)
    RelativeLayout week_rel;

    @InjectView(R.id.iv_types)
    ImageView iv_types;
    @InjectView(R.id.no_photowalll)
    TextView no_photowalll;
    @InjectView(R.id.iv_video2_bg)
    ImageView iv_video2_bg;
    @InjectView(R.id.iv_video1_bg)
    ImageView iv_video1_bg;
    private List<PartnersModel> partnersModels = new ArrayList<>();
    private List<TuijianModel> tuijianModels = new ArrayList<>();
    public int typecode;
    private List<ClassModel> classModels = new ArrayList<>();
    private String classId_first;
    String path = AddressManager.get("photoHost");
    private ArrayList<String> photos = new ArrayList<>();

    private ListRecyclerAdapter partneradapter;
    private List<TypeModel> datas = new ArrayList<>();
    private int lastVisitableItem;
    private DeleteClass deleteClass;
    private static final int LOADCOUNT = 10;
    private int page = 1;
    private String classnum;
    private SaveclassModel saveclassModel;
    private List<String> dataset = new LinkedList<>(Arrays.asList("按减重斤数", "按减重比", "按体脂比"));
    private boolean isLoading = false;
    private List<ImageView> views = new ArrayList<>();

    public void setDeleteClass(DeleteClass deleteClass) {
        this.deleteClass = deleteClass;
    }

    public static HeadGameFragment1 getInstance(DeleteClass deleteClass) {
        HeadGameFragment1 fragment = new HeadGameFragment1();
        fragment.setDeleteClass(deleteClass);
        return fragment;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initViews() {
        list_partner.setLayoutManager(new LinearLayoutManagerWrapper(getContext()));//RecyclerView
        refresh.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    refresh.setEnabled(true);
                } else {
                    refresh.setEnabled(false);
                }
            }
        });
        searchContent.setOnClickListener(this);
        re_honor.setOnClickListener(this);
        week_rel.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        honor_lin.setOnClickListener(this);
        re_photowall.setOnClickListener(this);
        refresh.setOnRefreshListener(this);
        service = ZillaApi.NormalRestAdapter.create(HeadService.class);
        searchContent.setOnClickListener(this);
        list_partner.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int count = partneradapter.getItemCount();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && count > LOADCOUNT && lastVisitableItem + 1 == count) {
                    if (!isLoading) {
                        isLoading = true;
                        //加载更多数据
                        page++;
                        updatepartner(typecode, 10, page);//按类型分页加载小伙伴
                    }

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();
                lastVisitableItem = llm.findLastVisibleItemPosition();
            }
        });

    }

    @Override
    protected void initDatas() {
        partneradapter = new ListRecyclerAdapter(getContext(), partnersModels);
        list_partner.setAdapter(partneradapter);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setPageMargin(10);
        refresh.setRefreshing(true);
        onRefresh();//获取初始数据
        TypeModel model1 = new TypeModel(0, "按减重斤数");//0
        datas.add(model1);
        TypeModel model2 = new TypeModel(1, "按减重比");//1
        datas.add(model2);
        TypeModel model3 = new TypeModel(2, "按体脂比");//2
        datas.add(model3);
        spinner_title.attachDataSource(dataset); //类型（体重比，体脂，减重比）
        typecode = 0;
        partneradapter.setType(typecode);
        spinner_title.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                partnersModels.clear();
                String typename = dataset.get(i);
                if ("按减重斤数".equals(typename)) {
                    typecode = 0;
                    iv_types.setImageResource(R.drawable.weightphoto);
                    partneradapter.setType(typecode);
                } else if ("按减重比".equals(typename)) {
                    typecode = 1;
                    iv_types.setImageResource(R.drawable.jianzhong_iv);
                    partneradapter.setType(typecode);

                } else if ("按体脂比".equals(typename)) {
                    typecode = 2;
                    iv_types.setImageResource(R.drawable.jianzhiphoto);
                    partneradapter.setType(typecode);
                }
                page = 1;
                updatepartner(typecode, 10, page);//按类型分页加载小伙伴
            }
        });

        //点击小伙伴进入小伙伴个人详情页
        partneradapter.setOnItemClickListener(new ListRecyclerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                PartnersModel partnersModel = partnersModels.get(position);
                String stu_id = partnersModel.getAccountId();
                long stu_ids = Long.parseLong(stu_id);
                Intent intent = new Intent(getContext(), PersonDetailActivity.class);
                intent.putExtra("AccountId", stu_ids);
                intent.putExtra("ClassId", classId_first);
                startActivity(intent);
            }
        });
        //根据不同的班级加载数据
        tv_title.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                classId_first = classModels.get(i).getClassId();
                classnum = classModels.get(i).getClassWeek();
                saveclassModel = new SaveclassModel();
                saveclassModel.setClassName(classModels.get(i).getClassName());
                saveclassModel.setClassCode(classModels.get(i).getClassCode());
                saveclassModel.setClassId(classModels.get(i).getClassId());
                saveclassModel.setClassWeek(classModels.get(i).getClassWeek());
                saveclassModel.setClassRole(classModels.get(i).getClassRole());
                classinfo(classId_first, classnum);

            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i("fdfdff", "ScrollX=====" + viewPager.getScrollX());
            }
        });

        rl_container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return viewPager.dispatchTouchEvent(event);
            }
        });

        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    //是否有消息
    private void gethasemail() {
        refresh.setRefreshing(false);
        service.hasemail(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), new RequestCallback<ResponseData<NewsModel>>() {
            @Override
            public void success(ResponseData<NewsModel> responseData, Response response) {
                if (responseData.getData() != null) {
                    try {
                        NewsModel newsModel = responseData.getData();
                        int has = newsModel.getNum();
                        if (has == 0) {
                            iv_right.setImageResource(R.drawable.email);
                        } else {
                            iv_right.setImageResource(R.drawable.has_email);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    //按类型分页加载小伙伴
    private void updatepartner(int sorttype, int pagesize, int pageindex) {
        service.getpartnertype(UserInfoModel.getInstance().getToken(), classId_first, sorttype, pagesize,
                pageindex, new RequestCallback<ResponseData<PartnertotalModel>>() {
                    @Override
                    public void success(ResponseData<PartnertotalModel> partnersModelResponseData, Response response) {
                        isLoading = false;
                        partneradapter.notifyItemRemoved(partneradapter.getItemCount());
                        if (200 == partnersModelResponseData.getStatus()) {
                            PartnertotalModel partnertotalModel = partnersModelResponseData.getData();
                            if (partnertotalModel.getPartnersList() != null) {
                                partnersModels.addAll(partnertotalModel.getPartnersList());
                                partneradapter.notifyDataSetChanged();
                            }
                        } else {
                            Util.toastMsg(partnersModelResponseData.getMsg());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        isLoading = false;
                        partneradapter.notifyItemRemoved(partneradapter.getItemCount());
                        super.failure(error);
                    }
                });
    }

    private void classinfo(String classId_first, String classnum) {
        ZillaApi.NormalRestAdapter.create(HeadService.class).choose(UserInfoModel.getInstance().getToken(), classId_first,
                classnum, 10, new RequestCallback<ResponseData<ChooseModel>>() {
                    @Override
                    public void success(ResponseData<ChooseModel> chooseModelResponseData, Response response) {
                        try {
                            if (chooseModelResponseData.getData() != null) {
                                final ChooseModel classinfoModel = chooseModelResponseData.getData();
                                //荣誉榜
                                if (classinfoModel.getHonor() != null) {
                                    RongyuModel rongyuModel = classinfoModel.getHonor();
                                    group_name.setText(rongyuModel.getGroupName());
                                    if (!TextUtils.isEmpty(rongyuModel.getGroupLossPre())) {
                                        jianzhongbi_tv.setText("总减重比" + rongyuModel.getGroupLossPre() + " %");
                                    } else {
                                        jianzhongbi_tv.setText("总减重比 %");
                                    }
                                    student_tv.setText(rongyuModel.getStuName());

                                    if (StringUtils.isNotEmpty(rongyuModel.getStuPhoto())) {
                                        Picasso.with(getContext()).load(path + rongyuModel.getStuPhoto()).fit()
                                                .error(R.drawable.img_default)
                                                .placeholder(R.drawable.img_default).into(studenticon);
                                    }
                                    if (!TextUtils.isEmpty(rongyuModel.getLossPre())) {
                                        student_jianzhong.setText("减重比" + rongyuModel.getLossPre() + " %");
                                    } else {
                                        student_jianzhong.setText("减重比 %");
                                    }
                                    if (!TextUtils.isEmpty(rongyuModel.getPysPre())) {
                                        student_jianzhi.setText("减脂比" + rongyuModel.getPysPre() + " %");
                                    } else {
                                        student_jianzhi.setText("减脂比 %");
                                    }
                                }

                                //班级赛况
                                if (classinfoModel.getPartnersList() != null) {
                                    partnersModels.clear();
                                    partnersModels.addAll(classinfoModel.getPartnersList());
                                    partneradapter.notifyDataSetChanged();

                                }

                                //本周推荐
                                if (classinfoModel.getListRec() != null) {
                                    tuijianModels.addAll(classinfoModel.getListRec());
                                    if (tuijianModels.size() >= 2) {
                                        video_type1.setText(tuijianModels.get(0).getVideoType());
                                        video_name1.setText(tuijianModels.get(0).getTitle());
                                        Picasso.with(getContext()).load(path + tuijianModels.get(0).getPhoto()).fit().error(R.drawable.default_icon_rect)
                                                .error(R.drawable.default_icon_rect).into(iv_video1_bg);

                                        iv_imagevideo1.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent it = new Intent(Intent.ACTION_VIEW);
                                                it.setDataAndType(Uri.parse(path + tuijianModels.get(0).getVideoUrl()), "video/mp4");
                                                startActivity(it);
                                            }
                                        });
                                        video_type2.setText(tuijianModels.get(1).getVideoType());
                                        video_name2.setText(tuijianModels.get(1).getTitle());
                                        Picasso.with(getContext()).load(path + tuijianModels.get(1).getPhoto()).fit().error(R.drawable.default_icon_rect)
                                                .error(R.drawable.default_icon_rect).into(iv_video2_bg);

                                        iv_imagevideo2.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent it = new Intent(Intent.ACTION_VIEW);
                                                it.setDataAndType(Uri.parse(path + tuijianModels.get(0).getVideoUrl()), "video/mp4");
                                                startActivity(it);
                                            }
                                        });
                                    } else if (tuijianModels.size() == 1) {
                                        video_type1.setText(tuijianModels.get(0).getVideoType());
                                        video_name1.setText(tuijianModels.get(0).getTitle());
                                        Picasso.with(getContext()).load(path + tuijianModels.get(0).getPhoto()).fit().error(R.drawable.default_icon_rect).into(iv_video1_bg);
                                        Picasso.with(getContext()).load(R.drawable.default_icon_rect).into(iv_video2_bg);
                                    }

                                }
                                //照片墙
                                if (classinfoModel.getPhotoWall() != null) {
                                    rl_container.setVisibility(View.VISIBLE);
                                    no_photowalll.setVisibility(View.GONE);
                                    ZhaopianModel zhaopianModel = classinfoModel.getPhotoWall();
                                    if (zhaopianModel.getPhotoThumbnailList() != null && !zhaopianModel.getPhotoThumbnailList().isEmpty()) {

                                        photos.clear();
                                        pageradapter.notifyDataSetChanged();
                                        adapterData();
                                    } else {
                                        rl_container.setVisibility(View.GONE);
                                        no_photowalll.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        super.failure(error);
                    }
                });
    }

    private PagerAdapter pageradapter;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.searchContent:
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                Intent intent = new Intent(getContext(), PantnerActivity.class);
                intent.putExtra("classId_first", classId_first);
                startActivity(intent);
                break;
            case R.id.re_honor:
                HonorActivity.startHonorActivity(getContext(), classId_first);
                break;
            case R.id.week_rel:
                Intent intent1 = new Intent(getContext(), VideomoreActivity.class);//本周推荐（更多视频）
                startActivity(intent1);
                break;
            case R.id.fl_right:
                Intent intent2 = new Intent(getContext(), Message2Activity.class);
                startActivity(intent2);
                break;
            case R.id.ll_left:
                getActivity().finish();
                break;
            case R.id.re_photowall:
                Intent photowall = new Intent(getContext(), PhotoWallActivity.class);
                photowall.putExtra("classId", classId_first);
//                startActivity(photowall);
                startActivityForResult(photowall, 001);
                break;
            case R.id.honor_lin:
                Intent honor = new Intent(getContext(), HonorActivity.class);
                honor.putExtra("classId", classId_first);
                startActivity(honor);
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 001) {
                if (saveclassModel != null) {
                    classinfo(saveclassModel.getClassId(), saveclassModel.getClassWeek());
                } else {
                    classModels.clear();
                    getallfirst();
                }
            }

        }
    }

    @Subscribe
    public void updateClass(UpdateClass clazz) {
        if (clazz.getStatus() == 0) {
            //更新班级姓名
            //更新班级姓名
            for (ClassModel model : classModels) {
                if (model.getClassCode().equals(clazz.getModel().getClassCode())) {
                    model.setClassName(clazz.getModel().getClassName());
                    model.setClassRole(clazz.getModel().getClassRole());
                    model.setClassCode(clazz.getModel().getClassCode());
                    model.setClassId(clazz.getModel().getClassId());
                    break;
                }
            }
            tv_title.getAdapter().notifyDataSetChanged();
            tv_title.setSelected(tv_title.getSelectedIndex());
        } else if (clazz.getStatus() == 1) {
            //添加新班级
            ClassModel model = new ClassModel();
            model.setClassId(clazz.getModel().getClassId());
            model.setClassCode(clazz.getModel().getClassCode());
            model.setClassName(clazz.getModel().getClassName());
            model.setClassRole(clazz.getModel().getClassRole());
            model.setClassWeek("0");
            this.classModels.add(model);
            tv_title.getAdapter().notifyDataSetChanged();
        } else if (clazz.getStatus() == 2) {
            //删除班级
            for (ClassModel model : classModels) {
                if (model.getClassCode().equals(clazz.getModel().getClassCode())) {
                    this.classModels.remove(model);
                    tv_title.getAdapter().notifyDataSetChanged();
                    break;
                }
            }

            if (!classModels.isEmpty()) {
                tv_title.setSelected(0);
            } else {
                if (deleteClass != null) {
                    deleteClass.deletClass();
                }
            }

        }

    }

    @Override
    public void onRefresh() {
        if (saveclassModel != null) {
            classinfo(saveclassModel.getClassId(), saveclassModel.getClassWeek());
        } else {
            classModels.clear();
            getallfirst();
        }
        gethasemail();
    }

    @Override
    public void onResume() {
        super.onResume();
        gethasemail();
    }

    private void getallfirst() {
        service.getfirst(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), 10, new RequestCallback<ResponseData<ClassinfoModel>>() {
            @Override
            public void success(ResponseData<ClassinfoModel> classinfoModelResponseData, Response response) {
                try {
                    refresh.setRefreshing(false);
                    page = 1;
                    classModels.clear();
                    spinner_title.attachDataSource(dataset);
                    typecode = 0;
                    partneradapter.setType(typecode);
                    com.github.snowdream.android.util.Log.i("第一次加载的输出信息=" + classinfoModelResponseData.getData().toString());
                    if (classinfoModelResponseData.getData() != null) {
                        final ClassinfoModel classinfoModel = classinfoModelResponseData.getData();
                        //班级加载
                        if (classinfoModel.getClassInfoList() != null && !classinfoModel.getClassInfoList().isEmpty()) {
                            classModels.addAll(classinfoModel.getClassInfoList());
                            classId_first = classModels.get(0).getClassId();
                            tv_title.attachCustomSource(new ArrowSpinnerAdapter<ClassModel>(getContext(), classModels, R.layout.selector_class_item) {
                                @Override
                                public void convert(ViewHolder holder, ClassModel data, int position) {
                                    ImageView iv_icon = holder.getView(R.id.iv_icon);
                                    boolean selected = tv_title.getSelectedIndex() == position;
                                    int icon;
                                    switch (data.getClassRole()) {
                                        case 1:
                                            icon = selected ? R.drawable.class_zongjiaolian_re : R.drawable.class_zongjiaolian;
                                            break;
                                        case 2:
                                            icon = selected ? R.drawable.class_jiaolian_re : R.drawable.class_jiaolian;
                                            break;
                                        case 3:
                                            icon = selected ? R.drawable.class_zhujiao_re : R.drawable.class_zhujiao;
                                            break;
                                        default:
                                            icon = selected ? R.drawable.class_xueyuan_re : R.drawable.class_xueyuan;
                                            break;
                                    }
                                    iv_icon.setImageDrawable(ContextCompat.getDrawable(getContext(), icon));
                                    int color = selected ? 0xFF000000 : 0xFFFFFFFF;
                                    TextView tv_role = holder.getView(R.id.tv_role_name);
                                    int role = data.getClassRole();
                                    tv_role.setText(role == 1 ? "总教练" : role == 2 ? "教练" : role == 3 ? "助教" : role == 4 ? "学员" : "");
                                    tv_role.setTextColor(color);
                                    TextView tv_number = holder.getView(R.id.tv_number);
                                    tv_number.setText(data.getClassCode());
                                    tv_number.setTextColor(color);
                                    TextView tv_class_name = holder.getView(R.id.tv_class_name);
                                    tv_class_name.setText(data.getClassName());
                                    tv_class_name.setTextColor(color);
                                    ImageView iv_sel = holder.getView(R.id.iv_select);
                                    iv_sel.setVisibility(selected ? View.VISIBLE : View.INVISIBLE);
                                    RelativeLayout rl_bg = holder.getView(R.id.rl_bg);
                                    rl_bg.setBackgroundColor(selected ? 0xFFFFFFFF : 0x00FFFFFF);
                                }

                                @Override
                                public String getText(int position) {
                                    if (classModels != null && !classModels.isEmpty()) {
                                        classId_first = classModels.get(position).getClassId();
                                        return classModels.get(position).getClassName();
                                    } else {
                                        return "尚未开班";
                                    }
                                }
                            });
                        }
                        //荣誉榜
                        if (classinfoModel.getHonor() != null) {
                            RongyuModel rongyuModel = classinfoModel.getHonor();
                            group_name.setText(rongyuModel.getGroupName());
                            if (!TextUtils.isEmpty(rongyuModel.getGroupLossPre())) {
                                jianzhongbi_tv.setText("总减重比" + rongyuModel.getGroupLossPre() + " %");
                            } else {
                                jianzhongbi_tv.setText("总减重比" + " %");
                            }
                            student_tv.setText(rongyuModel.getStuName());

                            if (!TextUtils.isEmpty(rongyuModel.getStuPhoto())) {
                                Picasso.with(getContext()).load(path + rongyuModel.getStuPhoto())
                                        .fit().error(R.drawable.img_default)
                                        .placeholder(R.drawable.img_default).into(studenticon);
                            } else {
                                Picasso.with(getContext()).load(R.drawable.img_default).into(studenticon);
                            }
                            if (!TextUtils.isEmpty(rongyuModel.getLossPre())) {
                                student_jianzhong.setText("减重比" + rongyuModel.getLossPre() + " %");
                            } else {
                                student_jianzhong.setText("减重比" + " %");
                            }
                            if (!TextUtils.isEmpty(rongyuModel.getPysPre())) {
                                student_jianzhi.setText("减脂比" + rongyuModel.getPysPre() + " %");
                            } else {
                                student_jianzhi.setText("减脂比" + " %");
                            }
                        }

                        //本周推荐
                        if (classinfoModel.getListRec() != null) {
                            tuijianModels.clear();
                            tuijianModels.addAll(classinfoModel.getListRec());
                            if (tuijianModels.size() >= 2) {
                                video_type1.setText(tuijianModels.get(0).getVideoType());
                                video_name1.setText(tuijianModels.get(0).getTitle());
                                Picasso.with(getContext()).load(path + tuijianModels.get(0).getPhoto()).fit().error(R.drawable.default_icon_rect).placeholder(R.drawable.default_icon_rect).into(iv_video1_bg);
                                iv_imagevideo1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent it = new Intent(Intent.ACTION_VIEW);
                                        it.setDataAndType(Uri.parse(path + tuijianModels.get(0).getVideoUrl()), "video/mp4");
                                        startActivity(it);
                                    }
                                });
                                video_type2.setText(tuijianModels.get(1).getVideoType());
                                video_name2.setText(tuijianModels.get(1).getTitle());
                                Picasso.with(getContext()).load(path + tuijianModels.get(1).getPhoto()).fit().error(R.drawable.default_icon_rect).placeholder(R.drawable.default_icon_rect).into(iv_video2_bg);
                                iv_imagevideo2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent it = new Intent(Intent.ACTION_VIEW);
                                        it.setDataAndType(Uri.parse(path + tuijianModels.get(0).getVideoUrl()), "video/mp4");
                                        startActivity(it);
                                    }
                                });
                            } else if (tuijianModels.size() == 1) {
                                video_type1.setText(tuijianModels.get(0).getVideoType());
                                video_name1.setText(tuijianModels.get(0).getTitle());
                                Picasso.with(getContext()).load(path + tuijianModels.get(0).getPhoto()).fit().error(R.drawable.default_icon_rect).placeholder(R.drawable.default_icon_rect).into(iv_video1_bg);
                                Picasso.with(getContext()).load(R.drawable.default_icon_rect).into(iv_video2_bg);
                            }
                        }
                        //照片墙
                        if (classinfoModel.getPhotoWall() != null) {
                            rl_container.setVisibility(View.VISIBLE);
                            no_photowalll.setVisibility(View.GONE);
                            ZhaopianModel zhaopianModel = classinfoModel.getPhotoWall();
                            if (zhaopianModel.getPhotoThumbnailList() != null && !zhaopianModel.getPhotoThumbnailList().isEmpty()) {
                                photos.clear();
                                photos.addAll(zhaopianModel.getPhotoThumbnailList());

                                adapterData();
                            } else {
                                rl_container.setVisibility(View.GONE);
                                no_photowalll.setVisibility(View.VISIBLE);
                            }
                        }
                        //班级赛况
                        if (classinfoModel.getPartnersList() != null) {
                            partnersModels.clear();
                            Log.e("234", classinfoModel.getPartnersList().toString());
                            partnersModels.addAll(classinfoModel.getPartnersList());
                            partneradapter.notifyDataSetChanged();

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }

            }

            @Override
            public void failure(RetrofitError error) {
                try {
                    refresh.setRefreshing(false);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                super.failure(error);
            }
        });
    }

    private int px;

    private void adapterData() {
        px = getResources().getDisplayMetrics().widthPixels / 4;
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewPager.getLayoutParams();
        params.width = px;
        params.height = px;
        viewPager.setLayoutParams(params);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return photos.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                ImageView imageView = new ImageView(getContext());
                ViewPager.LayoutParams params = new ViewPager.LayoutParams();
                params.width = px;
                params.height = px;
                imageView.setLayoutParams(params);
                Picasso.with(getContext()).load(path + photos.get(position)).fit().error(R.drawable.default_icon_square)
                        .placeholder(R.drawable.default_icon_square).into(imageView);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        com.github.snowdream.android.util.Log.i("path" + position);
                        Intent in = new Intent(getContext(), PictureMoreActivity.class);
                        in.putStringArrayListExtra("images", photos);
                        in.putExtra("position", position);
                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(v, v.getWidth() / 2, v.getHeight() / 2, 0, 0);
                        ActivityCompat.startActivity(getContext(), in, optionsCompat.toBundle());
                    }
                });
                container.addView(imageView, position);
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((ImageView) object);
            }

            @Override
            public float getPageWidth(int position) {
                return super.getPageWidth(position);
            }
        });
    }


    public interface DeleteClass {
        void deletClass();

        void interceptTouch(boolean touch);
    }
}
