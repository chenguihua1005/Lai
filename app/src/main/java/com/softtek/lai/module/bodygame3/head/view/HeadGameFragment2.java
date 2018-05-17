package com.softtek.lai.module.bodygame3.head.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyTypeAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.ggx.widgets.nicespinner.ArrowSpinnerAdapter;
import com.ggx.widgets.nicespinner.ListDialog;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.head.model.ChooseModel;
import com.softtek.lai.module.bodygame3.head.model.ClassModel;
import com.softtek.lai.module.bodygame3.head.model.ClassinfoModel;
import com.softtek.lai.module.bodygame3.head.model.NewsModel;
import com.softtek.lai.module.bodygame3.head.model.PartnersModel;
import com.softtek.lai.module.bodygame3.head.model.PartnertotalModel;
import com.softtek.lai.module.bodygame3.head.model.RongyuModel;
import com.softtek.lai.module.bodygame3.head.model.SaveclassModel;
import com.softtek.lai.module.bodygame3.head.model.TuijianModel;
import com.softtek.lai.module.bodygame3.head.model.ZhaopianModel;
import com.softtek.lai.module.bodygame3.head.net.HeadService;
import com.softtek.lai.module.bodygame3.home.event.SaveClassModel;
import com.softtek.lai.module.bodygame3.home.event.UpdateClass;
import com.softtek.lai.module.bodygame3.photowall.PhotoWallActivity;
import com.softtek.lai.module.customermanagement.view.GymClubActivity;
import com.softtek.lai.module.laiClassroom.ClassroomActivity;
import com.softtek.lai.module.laiClassroom.VideoDetailActivity;
import com.softtek.lai.module.message2.view.Message2Activity;
import com.softtek.lai.picture.LookBigPicActivity;
import com.softtek.lai.picture.bean.EaluationPicBean;
import com.softtek.lai.picture.util.EvaluateUtil;
import com.softtek.lai.utils.ACache;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CircleImageView;
import com.softtek.lai.widgets.MyRelative;
import com.softtek.lai.widgets.RoundImageView;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.lifecircle.LifeCircle;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.ui.LayoutInjectUtil;
import zilla.libcore.util.Util;

import static android.app.Activity.RESULT_OK;

@InjectLayout(R.layout.fragment_head_game_fragment2)
public class HeadGameFragment2 extends Fragment implements View.OnClickListener, PullToRefreshBase.OnRefreshListener2<ListView> {
    //toolbar标题
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.spinner_title1)
    ListDialog tv_title;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    @InjectView(R.id.iv_right)
    ImageView iv_right;

    @InjectView(R.id.ptrlv)
    PullToRefreshListView ptrlv;


    RelativeLayout re_photowall;
    LinearLayout honor_lin;
    TextView searchContent;
    TextView week_group;
    TextView week_stu;
    TextView group_name;//组名
    TextView jianzhongbi_tv;//总减重比
    ImageView iv_group;//图标
    TextView student_tv;//优秀学员名字
    CircleImageView studenticon;//优秀学员头像
    TextView student_jianzhong;//优秀学员减重比
    TextView student_jianzhi;//优秀学员减脂比
    RelativeLayout iv_imagevideo1;//本周推荐1
    RelativeLayout iv_imagevideo2;
    TextView video_type1;
    TextView video_name1;
    TextView video_type2;
    TextView video_name2;
    RelativeLayout re_honor;//荣誉榜
    ViewPager viewPager;
    MyRelative rl_container;
    RelativeLayout week_rel;
    TextView no_photowalll;
    ImageView iv_video2_bg;
    ImageView iv_video1_bg;
    TextView no_tuijian;
    LinearLayout lin_tuijian;
    private List<PartnersModel> partnersModels = new ArrayList<>();
    private List<TuijianModel> tuijianModels = new ArrayList<>();
    public int typecode;
    private List<ClassModel> classModels = new ArrayList<>();
    private String classId_first = "";
    String path = AddressManager.get("photoHost");
    private ArrayList<String> photos = new ArrayList<>();
    HeadService service;

    EasyTypeAdapter<PartnersModel> adapter;

    private DeleteClass deleteClass;

    private int page = 1;
    private String classnum;
    private SaveclassModel saveclassModel;
    private List<String> dataset = new LinkedList<>(Arrays.asList("累计减重斤数", "累计减重比", "累计体脂比"));

    public static final String SAVE_CLASS_DIR = "save_class_dir";
    public static final String SAVE_CLASS = "save_class";
    private String classId;

    public void setDeleteClass(DeleteClass deleteClass) {
        this.deleteClass = deleteClass;
    }

    public static HeadGameFragment2 getInstance(DeleteClass deleteClass, String info) {
        Bundle bundle = new Bundle();
        bundle.putString("classId", info);
        HeadGameFragment2 fragment = new HeadGameFragment2();
        fragment.setArguments(bundle);
        fragment.setDeleteClass(deleteClass);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View contentView = inflater.inflate(LayoutInjectUtil.getInjectLayoutId(this), container, false);
        LifeCircle.onCreate(this);
        ButterKnife.inject(this, contentView);
        initViews();
        initDatas();
        return contentView;
    }

    public void refresh(){
        if (ptrlv != null) {
            ptrlv.setRefreshing();
        }
    }

//    @Override
    protected void initViews() {
        classId = getArguments().getString("classId");
        fl_right.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        ptrlv.setOnRefreshListener(this);
        ptrlv.setMode(PullToRefreshBase.Mode.BOTH);
        ILoadingLayout startLabelse = ptrlv.getLoadingLayoutProxy(true, false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setRefreshingLabel("正在刷新数据");// 刷新时
        startLabelse.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
//        ILoadingLayout endLabelsr = ptrlv.getLoadingLayoutProxy(false, true);
//        endLabelsr.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
//        endLabelsr.setRefreshingLabel("正在刷新数据");
//        endLabelsr.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        View headView = View.inflate(getContext(), R.layout.bodygame3_head, null);
        re_photowall = (RelativeLayout) headView.findViewById(R.id.re_photowall);
        honor_lin = (LinearLayout) headView.findViewById(R.id.honor_lin);
        searchContent = (TextView) headView.findViewById(R.id.searchContent);
        group_name = (TextView) headView.findViewById(R.id.group_name);
        jianzhongbi_tv = (TextView) headView.findViewById(R.id.jianzhongbi_tv);
        iv_group = (ImageView) headView.findViewById(R.id.iv_group);
        student_tv = (TextView) headView.findViewById(R.id.student_tv);
        week_group = (TextView) headView.findViewById(R.id.week_group);
        week_stu = (TextView) headView.findViewById(R.id.week_stu);
        studenticon = (CircleImageView) headView.findViewById(R.id.iv_studenticon);
        student_jianzhong = (TextView) headView.findViewById(R.id.student_jianzhong);
        student_jianzhi = (TextView) headView.findViewById(R.id.student_jianzhi);
        iv_imagevideo1 = (RelativeLayout) headView.findViewById(R.id.iv_imagevideo1);
        iv_imagevideo2 = (RelativeLayout) headView.findViewById(R.id.iv_imagevideo2);
        video_type1 = (TextView) headView.findViewById(R.id.video_type1);
        video_name1 = (TextView) headView.findViewById(R.id.video_name1);
        video_type2 = (TextView) headView.findViewById(R.id.video_type2);
        video_name2 = (TextView) headView.findViewById(R.id.video_name2);
        re_honor = (RelativeLayout) headView.findViewById(R.id.re_honor);//荣誉榜
        viewPager = (ViewPager) headView.findViewById(R.id.viewpager);
        rl_container = (MyRelative) headView.findViewById(R.id.rl_container);
        week_rel = (RelativeLayout) headView.findViewById(R.id.week_rel);
        no_photowalll = (TextView) headView.findViewById(R.id.no_photowalll);
        iv_video2_bg = (ImageView) headView.findViewById(R.id.iv_video2_bg);
        iv_video1_bg = (ImageView) headView.findViewById(R.id.iv_video1_bg);
        no_tuijian = (TextView) headView.findViewById(R.id.no_tuijian);
        lin_tuijian = (LinearLayout) headView.findViewById(R.id.lin_tuijian);

        ptrlv.getRefreshableView().addHeaderView(headView);
        service = ZillaApi.NormalRestAdapter.create(HeadService.class);
        searchContent.setOnClickListener(this);
        re_honor.setOnClickListener(this);
        week_rel.setOnClickListener(this);
        honor_lin.setOnClickListener(this);
        re_photowall.setOnClickListener(this);

    }

    private static final int EMPTY = 0;
    private static final int DATA = 1;

//    @Override
    protected void initDatas() {
        SaveClassModel temp = (SaveClassModel) ACache.get(getContext(), SAVE_CLASS_DIR).getAsObject(SAVE_CLASS);
        if (temp != null) {
            classId_first = temp.classId;
            EventBus.getDefault().post(temp);
        }
        viewPager.setOffscreenPageLimit(4);
        viewPager.setPageMargin(10);
        adapter = new EasyTypeAdapter<PartnersModel>(partnersModels) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                int type = getItemViewType(position);
                PartnersModel partnersModel = partnersModels.get(position);
                ViewHolder holder;
                if (type == DATA) {
                    holder = ViewHolder.get(getContext(), R.layout.partner_list, convertView, parent);
                    CircleImageView civ = holder.getView(R.id.head_img);
                    Picasso.with(getContext()).load(AddressManager.get("photoHost") + partnersModel.getStuImg())
                            .fit().error(R.drawable.img_default)
                            .placeholder(R.drawable.img_default).into(civ);
                    ImageView exi_iv = holder.getView(R.id.exi_iv);
                    exi_iv.setVisibility("1".equals(partnersModel.getIsRetire()) ? View.VISIBLE : View.GONE);
                    TextView paiming = holder.getView(R.id.paiming);
                    paiming.setText(partnersModel.getRanking());
                    TextView name_tv = holder.getView(R.id.name_tv);
                    name_tv.setText(partnersModel.getStuName());
                    ImageView fale = holder.getView(R.id.fale);
                    if (partnersModel.getStuGender().equals("1")) {
                        fale.setImageResource(R.drawable.female_iv);
                    } else if (partnersModel.getStuGender().equals("0")) {
                        fale.setImageResource(R.drawable.male_iv);
                    } else if (partnersModel.getStuGender().equals("2")) {

                    }
                    TextView group_tv = holder.getView(R.id.group_tv);
                    group_tv.setText("(" + partnersModel.getGroupName() + ")");
                    TextView weight_first = holder.getView(R.id.weight_first);
                    weight_first.setText("初始体重" + partnersModel.getWeight() + "斤");

                    TextView tv_bi = holder.getView(R.id.tv_bi);
                    TextView jianzhong_tv = holder.getView(R.id.jianzhong_tv);
                    TextView jianzhong_tv2 = holder.getView(R.id.jianzhong_tv2);
                    if (typecode == 0) {//Int	排序类型：0:体重,1:减重比,2:体脂比
                        tv_bi.setText("累计减重斤数");
                        jianzhong_tv.setText(partnersModel.getLoss());
                        jianzhong_tv2.setText("斤");
                    } else if (typecode == 1) {
                        tv_bi.setText("累计减重比");
                        jianzhong_tv.setText(partnersModel.getLoss());
                        jianzhong_tv2.setText("%");
                    } else {
                        tv_bi.setText("累计体脂比");
                        weight_first.setText("初始体脂" + partnersModel.getWeight() + "%");
                        jianzhong_tv.setText(partnersModel.getLoss());
                        jianzhong_tv2.setText("%");
                    }
                } else {
                    holder = ViewHolder.get(getContext(), R.layout.empty_view, convertView, parent);
                }
                return holder.getConvertView();
            }

            @Override
            public int getViewTypeCount() {
                return 2;
            }

            @Override
            public int getItemViewType(int position) {
                if (partnersModels.isEmpty()) {
                    return EMPTY;
                }
                PartnersModel model = partnersModels.get(position);
                return model.isNotData ? EMPTY : DATA;
            }
        };

        ptrlv.setAdapter(adapter);
        //点击小伙伴进入小伙伴个人详情页
//        ptrlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                int index = position - 2;
//                if (index < 0) {
//                    return;
//                }
//                PartnersModel partnersModel = partnersModels.get(position - 2);
//                if (partnersModel.isNotData) {
//                    return;
//                }
//                String stu_id = partnersModel.getAccountId();
//                long stu_ids = Long.parseLong(stu_id);
//                Intent intent = new Intent(getContext(), PersonDetailActivity2.class);
//                intent.putExtra("AccountId", stu_ids);
//                intent.putExtra("ClassId", classId_first);
//                startActivity(intent);
//            }
//        });

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
                page = 1;
                classinfo(classId_first, classnum);
                SaveClassModel saveClassModel = new SaveClassModel();
                saveClassModel.classId = classId_first;
                saveClassModel.classWeek = classnum;
                ACache.get(getContext(), SAVE_CLASS_DIR).put(SAVE_CLASS, saveClassModel);
                EventBus.getDefault().post(saveClassModel);

            }
        });

        rl_container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return viewPager.dispatchTouchEvent(event);
            }
        });
        onPullDownToRefresh(null);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    //是否有消息
    private void getHasEmail() {
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
        service.getpartnertype(classId_first, UserInfoModel.getInstance().getToken(), classId_first, sorttype, pagesize,
                pageindex, new RequestCallback<ResponseData<PartnertotalModel>>() {
                    @Override
                    public void success(ResponseData<PartnertotalModel> partnersModelResponseData, Response response) {
                        try {
                            ptrlv.onRefreshComplete();
                            if (200 == partnersModelResponseData.getStatus()) {
                                PartnertotalModel partnertotalModel = partnersModelResponseData.getData();
                                if (partnertotalModel.getPartnersList() != null && !partnertotalModel.getPartnersList().isEmpty()) {
                                    partnersModels.clear();
                                    partnersModels.addAll(partnertotalModel.getPartnersList());
                                    adapter.notifyDataSetChanged();
                                } else {
                                    page--;
                                }
                            } else {
                                page--;
                                Util.toastMsg(partnersModelResponseData.getMsg());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        page--;
                        try {
                            ptrlv.onRefreshComplete();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        super.failure(error);
                    }
                });
    }

    private void classinfo(String classId_first, String classnum) {
        ZillaApi.NormalRestAdapter.create(HeadService.class).choose(classId_first, UserInfoModel.getInstance().getToken(), classId_first,
                classnum, 10, new RequestCallback<ResponseData<ChooseModel>>() {
                    @Override
                    public void success(ResponseData<ChooseModel> chooseModelResponseData, Response response) {
                        try {
                            ptrlv.onRefreshComplete();
                            if (chooseModelResponseData.getData() != null) {
                                final ChooseModel classinfoModel = chooseModelResponseData.getData();
                                //荣誉榜
                                if (classinfoModel.getHonor() != null) {
                                    RongyuModel rongyuModel = classinfoModel.getHonor();


                                    String replaceStr = "";
                                    int Target = rongyuModel.getTarget();
                                    if (Target == 1) {
                                        replaceStr = "增";
                                    } else if (Target == 0) {
                                        replaceStr = "减";
                                    }
                                    group_name.setText(rongyuModel.getGroupName());
                                    if (!TextUtils.isEmpty(rongyuModel.getGroupLossPre())) {
                                        jianzhongbi_tv.setText("总体重" + rongyuModel.getGroupLossPre());
                                    } else {
                                        jianzhongbi_tv.setText("总体重 %");
                                    }

                                    if (!TextUtils.isEmpty(rongyuModel.getClassWeek())) {
                                        week_group.setText("第" + rongyuModel.getClassWeek() + "周小组第一");
                                        week_stu.setText("第" + rongyuModel.getClassWeek() + "周优秀学员");
                                    } else {
                                        week_group.setText("第 周小组第一");
                                        week_stu.setText("第 周优秀学员");
                                    }
                                    student_tv.setText(rongyuModel.getStuName());

                                    if (StringUtils.isNotEmpty(rongyuModel.getStuPhoto())) {
                                        Picasso.with(getContext()).load(path + rongyuModel.getStuPhoto()).fit()
                                                .error(R.drawable.img_default)
                                                .placeholder(R.drawable.img_default).into(studenticon);
                                    }
                                    if (!TextUtils.isEmpty(rongyuModel.getLossPre())) {
                                        student_jianzhong.setText(replaceStr + "重比" + rongyuModel.getLossPre());
                                    } else {
                                        student_jianzhong.setText(replaceStr + "重比 %");
                                    }
                                    if (!TextUtils.isEmpty(rongyuModel.getPysPre())) {
                                        student_jianzhi.setText(replaceStr + "脂比" + rongyuModel.getPysPre());
                                    } else {
                                        student_jianzhi.setText(replaceStr + "脂比 %");
                                    }
                                }

                                //班级赛况
//                                partnersModels.clear();
//                                if (classinfoModel.getPartnersList() != null && !classinfoModel.getPartnersList().isEmpty()) {
//                                    partnersModels.addAll(classinfoModel.getPartnersList());
//                                } else {
//                                    PartnersModel model = new PartnersModel();
//                                    model.isNotData = true;
//                                    partnersModels.add(model);
//                                }
//                                adapter.notifyDataSetChanged();

                                //本周推荐
                                if (classinfoModel.getListRec() != null && !classinfoModel.getListRec().isEmpty()) {
                                    lin_tuijian.setVisibility(View.VISIBLE);
                                    no_tuijian.setVisibility(View.GONE);
                                    iv_imagevideo2.setVisibility(View.VISIBLE);
                                    tuijianModels.clear();
                                    tuijianModels.addAll(classinfoModel.getListRec());
                                    if (tuijianModels.size() >= 2) {
                                        video_type1.setText(tuijianModels.get(0).getVideoType());
                                        video_name1.setText(tuijianModels.get(0).getTitle());
                                        Picasso.with(getContext()).load(path + tuijianModels.get(0).getPhoto()).fit().error(R.drawable.default_icon_rect).placeholder(R.drawable.default_icon_rect).into(iv_video1_bg);
                                        iv_imagevideo1.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent intent3 = new Intent(getContext(), VideoDetailActivity.class);
                                                intent3.putExtra("articleId", tuijianModels.get(0).getVideoId());
                                                intent3.putExtra("cover", AddressManager.get("photoHost") + tuijianModels.get(0).getPhoto());
                                                intent3.putExtra("videoUrl", AddressManager.get("photoHost") + tuijianModels.get(0).getVideoUrl());
                                                startActivity(intent3);
                                            }
                                        });
                                        video_type2.setText(tuijianModels.get(1).getVideoType());
                                        video_name2.setText(tuijianModels.get(1).getTitle());
                                        video_type2.setBackgroundResource(R.drawable.background_tuijian2);
                                        Picasso.with(getContext()).load(path + tuijianModels.get(1).getPhoto()).fit().error(R.drawable.default_icon_rect).placeholder(R.drawable.default_icon_rect).into(iv_video2_bg);
                                        iv_imagevideo2.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent intent4 = new Intent(getContext(), VideoDetailActivity.class);
                                                intent4.putExtra("articleId", tuijianModels.get(1).getVideoId());
                                                intent4.putExtra("cover", AddressManager.get("photoHost") + tuijianModels.get(1).getPhoto());
                                                intent4.putExtra("videoUrl", AddressManager.get("photoHost") + tuijianModels.get(1).getVideoUrl());
                                                startActivity(intent4);
                                            }
                                        });
                                    } else if (tuijianModels.size() == 1) {
                                        video_type1.setText(tuijianModels.get(0).getVideoType());
                                        video_name1.setText(tuijianModels.get(0).getTitle());
                                        Picasso.with(getContext()).load(path + tuijianModels.get(0).getPhoto()).fit().error(R.drawable.default_icon_rect).placeholder(R.drawable.default_icon_rect).into(iv_video1_bg);
                                        iv_imagevideo1.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent intent4 = new Intent(getContext(), VideoDetailActivity.class);
                                                intent4.putExtra("articleId", tuijianModels.get(0).getVideoId());
                                                intent4.putExtra("cover", AddressManager.get("photoHost") + tuijianModels.get(0).getPhoto());
                                                intent4.putExtra("videoUrl", AddressManager.get("photoHost") + tuijianModels.get(0).getVideoUrl());
                                                startActivity(intent4);
                                            }
                                        });
                                    }
                                } else {
                                    lin_tuijian.setVisibility(View.GONE);
                                    no_tuijian.setVisibility(View.VISIBLE);
                                }
                                //照片墙
                                if (classinfoModel.getPhotoWall() != null) {
                                    rl_container.setVisibility(View.VISIBLE);
                                    no_photowalll.setVisibility(View.GONE);
                                    ZhaopianModel zhaopianModel = classinfoModel.getPhotoWall();
                                    if (zhaopianModel.getPhotoThumbnailList() != null && !zhaopianModel.getPhotoThumbnailList().isEmpty()) {
                                        photos.clear();
                                        viewPager.removeAllViews();
                                        photos.addAll(zhaopianModel.getPhotoThumbnailList());
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
                        try {
                            ptrlv.onRefreshComplete();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
                Intent intent1 = new Intent(getContext(), ClassroomActivity.class);//本周推荐（更多视频）
                startActivity(intent1);
                break;
            case R.id.fl_right:
                Intent intent2 = new Intent(getContext(), Message2Activity.class);
                startActivity(intent2);
                break;
            case R.id.ll_left:
                if (!classId.equals("")) {
                    startActivity(new Intent(getActivity(), GymClubActivity.class));
                } else {
                    getActivity().finish();
                }
                break;
            case R.id.re_photowall:
                Intent photowall = new Intent(getContext(), PhotoWallActivity.class);
                photowall.putExtra("classId", classId_first);
                if (saveclassModel != null) {
                    photowall.putExtra("classRole", saveclassModel.getClassRole());
                }
                startActivityForResult(photowall, 1);
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
            if (requestCode == 1) {
                if (saveclassModel != null) {
                    classinfo(saveclassModel.getClassId(), saveclassModel.getClassWeek());
                } else {
                    classModels.clear();
                    getAllfirst(classId_first);
                }
            }

        }
    }

    @Subscribe
    public void updateClass(UpdateClass clazz) {
        if (clazz.getStatus() == 0) {
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
            tv_title.notifChange();

        } else if (clazz.getStatus() == 2) {
            //删除班级
            Iterator<ClassModel> iter = classModels.iterator();
            while (iter.hasNext()) {
                ClassModel model = iter.next();
                if (model.getClassId().equals(clazz.getModel().getClassId())) {
                    iter.remove();
                    break;
                }
            }
            tv_title.notifChange();
            if (!classModels.isEmpty()) {
                tv_title.setSelected(0);
                ClassModel model = classModels.get(0);
                classId_first = model.getClassId();
                classnum = model.getClassWeek();
                saveclassModel = new SaveclassModel();
                saveclassModel.setClassName(model.getClassName());
                saveclassModel.setClassCode(model.getClassCode());
                saveclassModel.setClassId(model.getClassId());
                saveclassModel.setClassWeek(model.getClassWeek());
                saveclassModel.setClassRole(model.getClassRole());
                SaveClassModel saveClassModel = new SaveClassModel();
                saveClassModel.classId = classId_first;
                saveClassModel.classWeek = classnum;
                ACache.get(getContext(), SAVE_CLASS_DIR).put(SAVE_CLASS, saveClassModel);
                EventBus.getDefault().post(saveClassModel);

            } else {
                ACache.get(getContext(), SAVE_CLASS_DIR).clear();
                if (deleteClass != null) {
                    deleteClass.deletClass();
                }
            }

        }

    }


    @Override
    public void onResume() {
        super.onResume();
        getHasEmail();
    }

    private void getAllfirst(final String classId) {
        service.getfirst(classId, UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), 10, classId, new RequestCallback<ResponseData<ClassinfoModel>>() {
            @Override
            public void success(ResponseData<ClassinfoModel> classinfoModelResponseData, Response response) {
                try {
                    ptrlv.onRefreshComplete();
                    page = 1;
                    classModels.clear();
                    typecode = 0;
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
                                    int icon = R.drawable.class_xueyuan;

                                    boolean isWorker = data.isWorker();//是否是俱乐部工作人员，true-是，false-否
                                    if (isWorker) {
                                        icon = R.drawable.worker;
                                    }
                                    switch (data.getClassRole()) {
                                        case 1:
                                            icon = R.drawable.class_zongjiaolian;
                                            break;
                                        case 2:
                                            icon = R.drawable.class_jiaolian;
                                            break;
                                        case 3:
                                            icon = R.drawable.class_zhujiao;
                                            break;
                                        case 4:
                                            icon = R.drawable.class_xueyuan;
                                            break;
                                    }
                                    iv_icon.setImageDrawable(ContextCompat.getDrawable(getContext(), icon));
                                    TextView tv_number = holder.getView(R.id.tv_number);
                                    tv_number.setText("班级编号:" + data.getClassCode());
                                    TextView tv_class_name = holder.getView(R.id.tv_class_name);
                                    tv_class_name.setText(data.getClassName());
                                    RadioButton iv_sel = holder.getView(R.id.iv_select);
                                    iv_sel.setChecked(selected);
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
                            SaveClassModel saveClassModel = (SaveClassModel) ACache.get(getContext(), SAVE_CLASS_DIR).getAsObject(SAVE_CLASS);
                            if (saveClassModel != null) {
                                for (int i = 0, j = classModels.size(); i < j; i++) {
                                    ClassModel model = classModels.get(i);
                                    if (model.getClassId().equals(saveClassModel.classId)) {
                                        saveclassModel = new SaveclassModel();
                                        saveclassModel.setClassName(classModels.get(i).getClassName());
                                        saveclassModel.setClassCode(classModels.get(i).getClassCode());
                                        saveclassModel.setClassId(classModels.get(i).getClassId());
                                        saveclassModel.setClassWeek(classModels.get(i).getClassWeek());
                                        saveclassModel.setClassRole(classModels.get(i).getClassRole());
                                        tv_title.setSelected(i);
                                        classId_first = saveClassModel.classId;
                                        EventBus.getDefault().post(saveClassModel);
                                        break;
                                    }
                                }
                            } else {
                                saveclassModel = new SaveclassModel();
                                saveclassModel.setClassName(classModels.get(0).getClassName());
                                saveclassModel.setClassCode(classModels.get(0).getClassCode());
                                saveclassModel.setClassId(classModels.get(0).getClassId());
                                saveclassModel.setClassWeek(classModels.get(0).getClassWeek());
                                saveclassModel.setClassRole(classModels.get(0).getClassRole());
                                saveClassModel = new SaveClassModel();
                                saveClassModel.classId = classId_first;
                                ACache.get(getContext(), SAVE_CLASS_DIR).put(SAVE_CLASS, saveClassModel);

                                EventBus.getDefault().post(saveClassModel);
                            }
                        }
                        //荣誉榜
                        if (classinfoModel.getHonor() != null) {
                            RongyuModel rongyuModel = classinfoModel.getHonor();

                            String replaceStr = "";
                            int target = rongyuModel.getTarget();
                            if (target == 1) {
                                replaceStr = "增";
                            } else if (target == 0) {
                                replaceStr = "减";
                            }

                            group_name.setText(rongyuModel.getGroupName());
                            if (!TextUtils.isEmpty(rongyuModel.getGroupLossPre())) {
                                jianzhongbi_tv.setText("总体重" + rongyuModel.getGroupLossPre());
                            } else {
                                jianzhongbi_tv.setText("总体重 %");
                            }
                            if (!TextUtils.isEmpty(rongyuModel.getClassWeek())) {
                                week_group.setText("第" + rongyuModel.getClassWeek() + "周小组第一");
                                week_stu.setText("第" + rongyuModel.getClassWeek() + "周优秀学员");
                            } else {
                                week_group.setText("第 周小组第一");
                                week_stu.setText("第 周优秀学员");
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
                                student_jianzhong.setText(replaceStr + "重比" + rongyuModel.getLossPre());
                            } else {
                                student_jianzhong.setText(replaceStr + "重比 %");
                            }
                            if (!TextUtils.isEmpty(rongyuModel.getPysPre())) {
                                student_jianzhi.setText(replaceStr + "脂比" + rongyuModel.getPysPre());
                            } else {
                                student_jianzhi.setText(replaceStr + "脂比 %");
                            }
                        }

                        //本周推荐
                        if (classinfoModel.getListRec() != null && !classinfoModel.getListRec().isEmpty()) {
                            lin_tuijian.setVisibility(View.VISIBLE);
                            no_tuijian.setVisibility(View.GONE);
                            iv_imagevideo2.setVisibility(View.VISIBLE);
                            tuijianModels.clear();
                            tuijianModels.addAll(classinfoModel.getListRec());
                            if (tuijianModels.size() >= 2) {
                                video_type1.setText(tuijianModels.get(0).getVideoType());
                                video_name1.setText(tuijianModels.get(0).getTitle());
                                Picasso.with(getContext()).load(path + tuijianModels.get(0).getPhoto()).fit().error(R.drawable.default_icon_rect).placeholder(R.drawable.default_icon_rect).into(iv_video1_bg);
                                iv_imagevideo1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent3 = new Intent(getContext(), VideoDetailActivity.class);
                                        intent3.putExtra("articleId", tuijianModels.get(0).getVideoId());
                                        intent3.putExtra("cover", AddressManager.get("photoHost") + tuijianModels.get(0).getPhoto());
                                        intent3.putExtra("videoUrl", AddressManager.get("photoHost") + tuijianModels.get(0).getVideoUrl());
                                        startActivity(intent3);
                                    }
                                });
                                video_type2.setText(tuijianModels.get(1).getVideoType());
                                video_name2.setText(tuijianModels.get(1).getTitle());
                                video_type2.setBackgroundResource(R.drawable.background_tuijian2);
                                Picasso.with(getContext()).load(path + tuijianModels.get(1).getPhoto()).fit().error(R.drawable.default_icon_rect).placeholder(R.drawable.default_icon_rect).into(iv_video2_bg);
                                iv_imagevideo2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent4 = new Intent(getContext(), VideoDetailActivity.class);
                                        intent4.putExtra("articleId", tuijianModels.get(1).getVideoId());
                                        intent4.putExtra("cover", AddressManager.get("photoHost") + tuijianModels.get(1).getPhoto());
                                        intent4.putExtra("videoUrl", AddressManager.get("photoHost") + tuijianModels.get(1).getVideoUrl());
                                        startActivity(intent4);
                                    }
                                });
                            } else if (tuijianModels.size() == 1) {
                                video_type1.setText(tuijianModels.get(0).getVideoType());
                                video_name1.setText(tuijianModels.get(0).getTitle());
                                Picasso.with(getContext()).load(path + tuijianModels.get(0).getPhoto()).fit().error(R.drawable.default_icon_rect).placeholder(R.drawable.default_icon_rect).into(iv_video1_bg);
                                iv_imagevideo1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent4 = new Intent(getContext(), VideoDetailActivity.class);
                                        intent4.putExtra("articleId", tuijianModels.get(0).getVideoId());
                                        intent4.putExtra("cover", AddressManager.get("photoHost") + tuijianModels.get(0).getPhoto());
                                        intent4.putExtra("videoUrl", AddressManager.get("photoHost") + tuijianModels.get(0).getVideoUrl());
                                        startActivity(intent4);
                                    }
                                });
                            }
                        } else {
                            lin_tuijian.setVisibility(View.GONE);
                            no_tuijian.setVisibility(View.VISIBLE);
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
//                        partnersModels.clear();
//                        if (classinfoModel.getPartnersList() != null && !classinfoModel.getPartnersList().isEmpty()) {
//                            partnersModels.addAll(classinfoModel.getPartnersList());
//                        } else {
//                            PartnersModel model = new PartnersModel();
//                            model.isNotData = true;
//                            partnersModels.add(model);
//                        }
//                        adapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void failure(RetrofitError error) {
                try {
                    ptrlv.onRefreshComplete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                super.failure(error);
            }
        });
    }

    private int px;

    private void adapterData() {
        px = (getResources().getDisplayMetrics().widthPixels * 2) / 9;
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewPager.getLayoutParams();
        params.width = px;
        params.height = px;
        viewPager.setLayoutParams(params);
        pageradapter = new PagerAdapter() {
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
                RoundImageView imageView = new RoundImageView(getContext());
                ViewPager.LayoutParams params = new ViewPager.LayoutParams();
                params.width = px;
                params.height = px;
                imageView.setLayoutParams(params);
                Picasso.with(getContext()).load(path + photos.get(position)).fit().error(R.drawable.default_icon_square)
                        .placeholder(R.drawable.default_icon_square).into(imageView);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), LookBigPicActivity.class);
                        Bundle bundle = new Bundle();
                        List<EaluationPicBean> list = EvaluateUtil.setupCoords4(getContext(), (ImageView) v, photos, position);
                        bundle.putSerializable(LookBigPicActivity.PICDATALIST, (Serializable) list);
                        intent.putExtras(bundle);
                        intent.putExtra(LookBigPicActivity.CURRENTITEM, position);
                        startActivity(intent);
                        ((Activity) getContext()).overridePendingTransition(0, 0);
                    }
                });
                container.addView(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((ImageView) object);
            }

            @Override
            public int getItemPosition(Object object) {
                return POSITION_NONE;
            }

            @Override
            public float getPageWidth(int position) {
                return super.getPageWidth(position);
            }
        };
        viewPager.setAdapter(pageradapter);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        page = 1;
        /*if (saveclassModel != null) {
            classinfo(saveclassModel.getClassId(), saveclassModel.getClassWeek());
        } else {

        }*/
        classModels.clear();
        getAllfirst(classId_first);
        getHasEmail();
    }


    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        page++;
//        updatepartner(typecode, 10, page);//按类型分页加载小伙伴
    }


    public interface DeleteClass {
        void deletClass();
    }
}
