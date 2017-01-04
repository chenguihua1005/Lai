package com.softtek.lai.module.bodygame3.head.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyTypeAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.ggx.widgets.nicespinner.ArrowSpinner3;
import com.ggx.widgets.nicespinner.ArrowSpinnerAdapter;
import com.ggx.widgets.nicespinner.NiceSpinner;
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
import com.softtek.lai.module.bodygame3.home.event.UpdateClass;
import com.softtek.lai.module.bodygame3.photowall.PhotoWallActivity;
import com.softtek.lai.module.message2.view.Message2Activity;
import com.softtek.lai.module.picture.view.PictureMoreActivity;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CircleImageView;
import com.softtek.lai.widgets.MyRelative;
import com.softtek.lai.widgets.RoundImageView;
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
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

import static android.app.Activity.RESULT_OK;

@InjectLayout(R.layout.fragment_head_game_fragment2)
public class HeadGameFragment2 extends LazyBaseFragment implements View.OnClickListener,PullToRefreshBase.OnRefreshListener2<ListView>{
    //toolbar标题
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.spinner_title1)
    ArrowSpinner3 tv_title;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    @InjectView(R.id.iv_right)
    ImageView iv_right;

    @InjectView(R.id.ptrlv)
    PullToRefreshListView ptrlv;


    RelativeLayout re_photowall;
    LinearLayout honor_lin;
    NiceSpinner spinner_title;
    TextView searchContent;
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
    ImageView iv_types;
    TextView no_photowalll;
    ImageView iv_video2_bg;
    ImageView iv_video1_bg;


    private List<PartnersModel> partnersModels = new ArrayList<>();
    private List<TuijianModel> tuijianModels = new ArrayList<>();
    public int typecode;
    private List<ClassModel> classModels = new ArrayList<>();
    private String classId_first="";
    String path = AddressManager.get("photoHost");
    private ArrayList<String> photos = new ArrayList<>();
    HeadService service;

    EasyTypeAdapter<PartnersModel>  adapter;

    private DeleteClass deleteClass;

    private int page = 1;
    private String classnum;
    private SaveclassModel saveclassModel;
    private List<String> dataset = new LinkedList<>(Arrays.asList("按减重斤数", "按减重比", "按体脂比"));


    public void setDeleteClass(DeleteClass deleteClass) {
        this.deleteClass = deleteClass;
    }

    public static HeadGameFragment2 getInstance(DeleteClass deleteClass) {
        HeadGameFragment2 fragment = new HeadGameFragment2();
        fragment.setDeleteClass(deleteClass);
        return fragment;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initViews() {
        fl_right.setOnClickListener(this);
        ll_left.setOnClickListener(this);


        ptrlv.setOnRefreshListener(this);
        ptrlv.setMode(PullToRefreshBase.Mode.BOTH);
        ILoadingLayout startLabelse = ptrlv.getLoadingLayoutProxy(true,false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setRefreshingLabel("正在刷新数据");// 刷新时
        startLabelse.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        ILoadingLayout endLabelsr = ptrlv.getLoadingLayoutProxy(false, true);
        endLabelsr.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
        endLabelsr.setRefreshingLabel("正在刷新数据");
        endLabelsr.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        View headView= View.inflate(getContext(),R.layout.bodygame3_head,null);
        re_photowall= (RelativeLayout) headView.findViewById(R.id.re_photowall);
        honor_lin= (LinearLayout) headView.findViewById(R.id.honor_lin);
        spinner_title= (NiceSpinner) headView.findViewById(R.id.spinner_title);
        searchContent= (TextView) headView.findViewById(R.id.searchContent);
        group_name= (TextView) headView.findViewById(R.id.group_name);
        jianzhongbi_tv= (TextView) headView.findViewById(R.id.jianzhongbi_tv);
        iv_group= (ImageView) headView.findViewById(R.id.iv_group);
        student_tv= (TextView) headView.findViewById(R.id.student_tv);
        studenticon= (CircleImageView) headView.findViewById(R.id.iv_studenticon);
        student_jianzhong= (TextView) headView.findViewById(R.id.student_jianzhong);
        student_jianzhi= (TextView) headView.findViewById(R.id.student_jianzhi);
        iv_imagevideo1= (RelativeLayout) headView.findViewById(R.id.iv_imagevideo1);
        iv_imagevideo2=(RelativeLayout) headView.findViewById(R.id.iv_imagevideo2);
        video_type1= (TextView) headView.findViewById(R.id.video_type1);
        video_name1= (TextView) headView.findViewById(R.id.video_name1);
        video_type2= (TextView) headView.findViewById(R.id.video_type2);
        video_name2=(TextView) headView.findViewById(R.id.video_name2);
        re_honor= (RelativeLayout) headView.findViewById(R.id.re_honor);//荣誉榜
        viewPager= (ViewPager) headView.findViewById(R.id.viewpager);
        rl_container= (MyRelative) headView.findViewById(R.id.rl_container);
        week_rel=(RelativeLayout) headView.findViewById(R.id.week_rel);
        iv_types= (ImageView) headView.findViewById(R.id.iv_types);
        no_photowalll= (TextView) headView.findViewById(R.id.no_photowalll);
        iv_video2_bg= (ImageView) headView.findViewById(R.id.iv_video2_bg);
        iv_video1_bg= (ImageView) headView.findViewById(R.id.iv_video1_bg);

        ptrlv.getRefreshableView().addHeaderView(headView);
        service = ZillaApi.NormalRestAdapter.create(HeadService.class);
        searchContent.setOnClickListener(this);
        re_honor.setOnClickListener(this);
        week_rel.setOnClickListener(this);
        honor_lin.setOnClickListener(this);
        re_photowall.setOnClickListener(this);
    }

    private static final int EMPTY=0;
    private static final int DATA=1;

    @Override
    protected void initDatas() {
        classId_first= SharedPreferenceService.getInstance().get("default_classId","");
        viewPager.setOffscreenPageLimit(4);
        viewPager.setPageMargin(10);

        spinner_title.attachDataSource(dataset); //类型（体重比，体脂，减重比）
        typecode = 0;

        spinner_title.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                partnersModels.clear();
                String typename = dataset.get(i);
                if ("按减重斤数".equals(typename)) {
                    typecode = 0;
                    iv_types.setImageResource(R.drawable.weightphoto);

                } else if ("按减重比".equals(typename)) {
                    typecode = 1;
                    iv_types.setImageResource(R.drawable.jianzhong_iv);


                } else if ("按体脂比".equals(typename)) {
                    typecode = 2;
                    iv_types.setImageResource(R.drawable.jianzhiphoto);

                }
                page = 1;
                updatepartner(typecode, 10, page);//按类型分页加载小伙伴
            }
        });
        adapter=new EasyTypeAdapter<PartnersModel>(partnersModels) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                int type=getItemViewType(position);
                PartnersModel partnersModel=partnersModels.get(position);
                ViewHolder holder;
                if(type==DATA){
                    holder=ViewHolder.get(getContext(),R.layout.partner_list,convertView,parent);
                    CircleImageView civ=holder.getView(R.id.head_img);
                    Picasso.with(getContext()).load(AddressManager.get("photoHost") + partnersModel.getStuImg())
                            .fit().error(R.drawable.img_default)
                            .placeholder(R.drawable.img_default).into(civ);
                    ImageView exi_iv=holder.getView(R.id.exi_iv);
                    exi_iv.setVisibility("1".equals(partnersModel.getIsRetire())?View.VISIBLE:View.GONE);
                    TextView paiming=holder.getView(R.id.paiming);
                    paiming.setText(partnersModel.getRanking());
                    TextView name_tv=holder.getView(R.id.name_tv);
                    name_tv.setText(partnersModel.getStuName());
                    ImageView fale=holder.getView(R.id.fale);
                    if (partnersModel.getStuGender().equals("1")) {
                        fale.setImageResource(R.drawable.female_iv);
                    } else if (partnersModel.getStuGender().equals("0")) {
                        fale.setImageResource(R.drawable.male_iv);
                    } else if (partnersModel.getStuGender().equals("2")) {

                    }
                    TextView group_tv=holder.getView(R.id.group_tv);
                    group_tv.setText("(" + partnersModel.getGroupName() + ")");
                    TextView weight_first=holder.getView(R.id.weight_first);
                    weight_first.setText("初始体重" + partnersModel.getWeight() + "斤");

                    TextView tv_bi=holder.getView(R.id.tv_bi);
                    TextView jianzhong_tv=holder.getView(R.id.jianzhong_tv);
                    TextView jianzhong_tv2=holder.getView(R.id.jianzhong_tv2);
                    if (typecode == 0) {//Int	排序类型：0:体重,1:减重比,2:体脂比
                        tv_bi.setText("减重斤数");
                        jianzhong_tv.setText(partnersModel.getLoss());
                        jianzhong_tv2.setText("斤");
                    } else if (typecode == 1) {
                        tv_bi.setText("减重比");
                        jianzhong_tv.setText(partnersModel.getLoss());
                        jianzhong_tv2.setText("%");
                    } else {
                        tv_bi.setText("体脂比");
                        weight_first.setText("初始体脂" + partnersModel.getWeight() + "%");
                        jianzhong_tv.setText(partnersModel.getLoss());
                        jianzhong_tv2.setText("%");
                    }
                }else {
                    holder=ViewHolder.get(getContext(),R.layout.empty_view,convertView,parent);
                }
                return holder.getConvertView();
            }

            @Override
            public int getViewTypeCount() {
                return 2;
            }

            @Override
            public int getItemViewType(int position) {
                if (partnersModels.isEmpty()){
                    return EMPTY;
                }
                PartnersModel model=partnersModels.get(position);
                return model.isNotData?EMPTY:DATA;
            }
        };

        ptrlv.setAdapter(adapter);
        //点击小伙伴进入小伙伴个人详情页
        ptrlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PartnersModel partnersModel = partnersModels.get(position-1);
                if(partnersModel.isNotData){
                    return;
                }
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
                SharedPreferenceService.getInstance().put("default_classId",classId_first);
                classnum = classModels.get(i).getClassWeek();
                saveclassModel = new SaveclassModel();
                saveclassModel.setClassName(classModels.get(i).getClassName());
                saveclassModel.setClassCode(classModels.get(i).getClassCode());
                saveclassModel.setClassId(classModels.get(i).getClassId());
                saveclassModel.setClassWeek(classModels.get(i).getClassWeek());
                saveclassModel.setClassRole(classModels.get(i).getClassRole());
                page=1;
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
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if(ptrlv!=null){
                    ptrlv.setRefreshing();
                }
            }
        },300);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    //是否有消息
    private void gethasemail() {

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
                        try {
                            ptrlv.onRefreshComplete();
                            if (200 == partnersModelResponseData.getStatus()) {
                                PartnertotalModel partnertotalModel = partnersModelResponseData.getData();
                                if (partnertotalModel.getPartnersList() != null&&!partnertotalModel.getPartnersList().isEmpty()) {
                                    partnersModels.addAll(partnertotalModel.getPartnersList());
                                    adapter.notifyDataSetChanged();
                                }else {
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
        ZillaApi.NormalRestAdapter.create(HeadService.class).choose(UserInfoModel.getInstance().getToken(), classId_first,
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
                                partnersModels.clear();
                                if (classinfoModel.getPartnersList() != null&&!classinfoModel.getPartnersList().isEmpty()) {
                                    partnersModels.addAll(classinfoModel.getPartnersList());
                                }else {
                                    PartnersModel model=new PartnersModel();
                                    model.isNotData=true;
                                    partnersModels.add(model);
                                }
                                adapter.notifyDataSetChanged();

                                //本周推荐
                                if (classinfoModel.getListRec() != null) {
                                    tuijianModels.addAll(classinfoModel.getListRec());
                                    if (tuijianModels.size() >= 2) {
                                        iv_imagevideo2.setVisibility(View.VISIBLE);
                                        iv_imagevideo1.setVisibility(View.VISIBLE);
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
                                        iv_imagevideo1.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent it = new Intent(Intent.ACTION_VIEW);
                                                it.setDataAndType(Uri.parse(path + tuijianModels.get(0).getVideoUrl()), "video/mp4");
                                                startActivity(it);
                                            }
                                        });
                                        Picasso.with(getContext()).load(R.drawable.default_icon_rect).into(iv_video2_bg);
                                        iv_imagevideo2.setVisibility(View.GONE);
                                    }

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
                                        pageradapter.notifyDataSetChanged();
                                        //adapterData();
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
                        ptrlv.onRefreshComplete();
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
                    getallfirst(classId_first);
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
            tv_title.notifChange();

        } else if (clazz.getStatus() == 2) {
            //删除班级
            for (ClassModel model : classModels) {
                if (model.getClassId().equals(clazz.getModel().getClassId())) {
                    this.classModels.remove(model);
                    break;
                }
            }
            tv_title.notifChange();
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
    public void onResume() {
        super.onResume();
        gethasemail();
    }

    private void getallfirst(String classId) {
        service.getfirst(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), 10,classId, new RequestCallback<ResponseData<ClassinfoModel>>() {
            @Override
            public void success(ResponseData<ClassinfoModel> classinfoModelResponseData, Response response) {
                try {
                    ptrlv.onRefreshComplete();
                    page = 1;
                    classModels.clear();
                    spinner_title.attachDataSource(dataset);
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
                                jianzhongbi_tv.setText("总减重比 %");
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
                                student_jianzhong.setText("减重比 %");
                            }
                            if (!TextUtils.isEmpty(rongyuModel.getPysPre())) {
                                student_jianzhi.setText("减脂比" + rongyuModel.getPysPre() + " %");
                            } else {
                                student_jianzhi.setText("减脂比 %");
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
                                        it.setDataAndType(Uri.parse(path + tuijianModels.get(1).getVideoUrl()), "video/mp4");
                                        startActivity(it);
                                    }
                                });
                            } else if (tuijianModels.size() == 1) {
                                video_type1.setText(tuijianModels.get(0).getVideoType());
                                video_name1.setText(tuijianModels.get(0).getTitle());
                                Picasso.with(getContext()).load(path + tuijianModels.get(0).getPhoto()).fit().error(R.drawable.default_icon_rect).placeholder(R.drawable.default_icon_rect).into(iv_video1_bg);
                                Picasso.with(getContext()).load(R.drawable.default_icon_rect).into(iv_video2_bg);
                                iv_imagevideo1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent it = new Intent(Intent.ACTION_VIEW);
                                        it.setDataAndType(Uri.parse(path + tuijianModels.get(0).getVideoUrl()), "video/mp4");
                                        startActivity(it);
                                    }
                                });
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
                        if (classinfoModel.getPartnersList() != null&&!classinfoModel.getPartnersList().isEmpty()) {
                            partnersModels.addAll(classinfoModel.getPartnersList());
                        }else {
                            PartnersModel model=new PartnersModel();
                            model.isNotData=true;
                            partnersModels.add(model);
                        }
                        adapter.notifyDataSetChanged();
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
                        com.github.snowdream.android.util.Log.i("path" + position);
                        Intent in = new Intent(getContext(), PictureMoreActivity.class);
                        in.putStringArrayListExtra("images", photos);
                        in.putExtra("position", position);
                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(v, v.getWidth() / 2, v.getHeight() / 2, 0, 0);
                        ActivityCompat.startActivity(getContext(), in, optionsCompat.toBundle());
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
        page=1;
        if (saveclassModel != null) {
            classinfo(saveclassModel.getClassId(), saveclassModel.getClassWeek());
        } else {
            classModels.clear();
            getallfirst(classId_first);
        }
        gethasemail();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        page++;
        updatepartner(typecode, 10, page);//按类型分页加载小伙伴
    }


    public interface DeleteClass {
        void deletClass();
    }
}
