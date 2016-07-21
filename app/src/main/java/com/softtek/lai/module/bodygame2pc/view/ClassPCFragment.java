package com.softtek.lai.module.bodygame2pc.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame2.adapter.ClassMainPCStudentAdapter;
import com.softtek.lai.module.bodygame2.model.ClmListModel;
import com.softtek.lai.module.bodygame2.model.DyNoticeModel;
import com.softtek.lai.module.bodygame2.model.DySysModel;
import com.softtek.lai.module.bodygame2.model.MemberChangeModel;
import com.softtek.lai.module.bodygame2.view.DYActivity;
import com.softtek.lai.module.bodygame2.view.PersonalDataActivity;
import com.softtek.lai.module.bodygame2pc.model.PCClassMainModel;
import com.softtek.lai.module.bodygame2pc.model.PCClmDetailModel;
import com.softtek.lai.module.bodygame2pc.present.PCClassMainManager;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.ListViewUtil;
import com.softtek.lai.utils.StringUtil;
import com.softtek.lai.widgets.ObservableScrollView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_class_pc)
public class ClassPCFragment extends LazyBaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, ObservableScrollView.ScrollViewListener, PCClassMainManager.ClassMainCallback {

    @InjectView(R.id.scroll)
    ObservableScrollView scroll;
    @InjectView(R.id.text_more)
    TextView text_more;

//    @InjectView(R.id.rel_title_more)
//    RelativeLayout rel_title_more;

    @InjectView(R.id.lin_select_type)
    LinearLayout lin_select_type;

    @InjectView(R.id.rel_title)
    RelativeLayout rel_title;
    @InjectView(R.id.rel_person)
    RelativeLayout rel_person;

    @InjectView(R.id.rel)
    RelativeLayout rel;

    @InjectView(R.id.rel_sy)
    RelativeLayout rel_sy;

    @InjectView(R.id.pull)
    SwipeRefreshLayout pull;

    @InjectView(R.id.list_student)
    ListView list_student;
    @InjectView(R.id.text_class_name)
    TextView text_class_name;

    @InjectView(R.id.text_select_type)
    TextView text_select_type;

    @InjectView(R.id.img_banner)
    ImageView img_banner;


    /*@InjectView(R.id.rel_gg)
    RelativeLayout rel_gg;*/
    @InjectView(R.id.img_gg)
    ImageView img_gg;
    /*@InjectView(R.id.rel_xtxx)
    RelativeLayout rel_xtxx;*/
    @InjectView(R.id.img_xtxx)
    ImageView img_xtxx;

    @InjectView(R.id.civ_header)
    ImageView civ_header;

    @InjectView(R.id.cb_gender)
    CheckBox cb_gender;
    @InjectView(R.id.cb_star)
    CheckBox cb_star;
    @InjectView(R.id.cb_fc)
    CheckBox cb_fc;

    @InjectView(R.id.text_count)
    TextView text_count;

    @InjectView(R.id.text_name)
    TextView text_name;
    @InjectView(R.id.tv_who)
    TextView tv_who;
    @InjectView(R.id.text_values)
    TextView text_values;
    @InjectView(R.id.text_before_weight)
    TextView text_before_weight;

    @InjectView(R.id.text_class_time)
    TextView text_class_time;

    @InjectView(R.id.rel_no_message)
    RelativeLayout rel_no_message;
    @InjectView(R.id.rel_message)
    RelativeLayout rel_message;

    @InjectView(R.id.img)
    ImageView img;

    @InjectView(R.id.img_lb)
    ImageView img_lb;
    @InjectView(R.id.text_value)
    TextView text_value;
    @InjectView(R.id.text_time)
    TextView text_time;

    private PopupWindow popTitleMore;
    private PopupWindow popSelectType;

    private LinearLayout lin_invite_student;
    private LinearLayout lin_invite_ass;
    private LinearLayout lin_create_class;

    private RelativeLayout rel_jzjs;
    private RelativeLayout rel_jzbfb;
    private RelativeLayout rel_tzl;
    private RelativeLayout rel_ywbh;

    private ImageView img_jzjs;
    private ImageView img_jzbfb;
    private ImageView img_tzl;
    private ImageView img_ywbh;

    private int select_type = 0;         //1:减重斤数  2：减重百分比   3:体制率  4：腰围变化
    private String select_class_id;

    private List<ClmListModel> student_list;

    PCClassMainManager classMainManager;

    DyNoticeModel dyNoticeModel;
    DySysModel dySysModel;

    ClassMainPCStudentAdapter adapter;

    private MessageReceiver mMessageReceiver;

    View view;
    UserInfoModel model;

    float cuttent_y;


    @Override
    protected void initViews() {
        int status = DisplayUtil.getStatusHeight(getActivity());
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) rel.getLayoutParams();
        if (DisplayUtil.getSDKInt() > 18) {
            params.topMargin = status;
            rel.setLayoutParams(params);
        }


        RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) rel_sy.getLayoutParams();
        p.height = params.height + status;
        rel_sy.setLayoutParams(p);
        rel_sy.setAlpha(1f);

//        rel_title_more.setOnClickListener(this);
        lin_select_type.setOnClickListener(this);
        rel_person.setOnClickListener(this);
        img_gg.setOnClickListener(this);
        text_more.setOnClickListener(this);
        img_xtxx.setOnClickListener(this);
        img_banner.setOnClickListener(this);
        rel_title.setAlpha(0f);
        rel_sy.setAlpha(1f);
        scroll.setScrollViewListener(this);

        view = getActivity().getLayoutInflater().inflate(R.layout.popview_select_type, null);
        rel_jzjs = (RelativeLayout) view.findViewById(R.id.rel_jzjs);
        rel_jzbfb = (RelativeLayout) view.findViewById(R.id.rel_jzbfb);
        rel_tzl = (RelativeLayout) view.findViewById(R.id.rel_tzl);
        rel_ywbh = (RelativeLayout) view.findViewById(R.id.rel_ywbh);


        img_jzjs = (ImageView) view.findViewById(R.id.img_jzjs);
        img_jzbfb = (ImageView) view.findViewById(R.id.img_jzbfb);
        img_tzl = (ImageView) view.findViewById(R.id.img_tzl);
        img_ywbh = (ImageView) view.findViewById(R.id.img_ywbh);

        list_student.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ClmListModel clmListModel = student_list.get(position);
                String useId = clmListModel.getAccountid();
                UserModel userModel = UserInfoModel.getInstance().getUser();
                if (useId.equals(userModel.getUserid())) {
                    String accountId = clmListModel.getAccountid();
                    Intent intent = new Intent(getActivity(), PersonalDataActivity.class);
                    intent.putExtra("userId", Long.parseLong(accountId));
                    intent.putExtra("classId", Long.parseLong(select_class_id));
                    startActivity(intent);
                }
            }
        });


    }

    @Override
    protected void onVisible() {
        if (getContext() instanceof BodyGamePCActivity) {
            BodyGamePCActivity activity = (BodyGamePCActivity) getContext();
            activity.setAlpha(0);
        }
        text_class_name.setFocusable(true);
        text_class_name.setFocusableInTouchMode(true);
        text_class_name.requestFocus();
        scroll.setFocusable(false);
        scroll.scrollTo(0, 0);
        super.onVisible();
    }

    @Override
    public void onDestroy() {
        getContext().unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    @Override
    protected void initDatas() {
        registerMessageReceiver();
        model = UserInfoModel.getInstance();
        scroll.post(
                new Runnable() {
                    public void run() {
                        //sv_container.fullScroll(ScrollView.FOCUS_UP);
                        scroll.smoothScrollTo(0, 0);
                    }
                });
        if (getContext() instanceof BodyGamePCActivity) {
            BodyGamePCActivity activity = (BodyGamePCActivity) getContext();
            activity.setAlpha(0);
        }
        classMainManager = new PCClassMainManager(this);
        student_list = new ArrayList<ClmListModel>();
        adapter = new ClassMainPCStudentAdapter(getContext(), student_list);
        list_student.setAdapter(adapter);
        pull.setProgressViewOffset(true, -20, DisplayUtil.dip2px(getContext(), 100));
        pull.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        pull.setOnRefreshListener(this);

    }

    private void initSelectTypePop() {
        img_jzjs.setImageResource(R.drawable.img_bg_jzjs_select);
        img_jzbfb.setImageResource(R.drawable.img_bg_jzbfb_select);
        img_tzl.setImageResource(R.drawable.img_bg_tzl_select);
        img_ywbh.setImageResource(R.drawable.img_bg_ywbh_select);

        if (select_type == 0) {
            img_jzjs.setImageResource(R.drawable.img_bg_jzjs_selected);
        } else if (select_type == 1) {
            img_jzbfb.setImageResource(R.drawable.img_bg_jzbfb_selected);
        } else if (select_type == 2) {
            img_tzl.setImageResource(R.drawable.img_bg_tzl_selected);
        } else if (select_type == 3) {
            img_ywbh.setImageResource(R.drawable.img_bg_ywbh_selected);
        }

        rel_jzjs.setOnClickListener(this);
        rel_jzbfb.setOnClickListener(this);
        rel_tzl.setOnClickListener(this);
        rel_ywbh.setOnClickListener(this);
    }

    @Override
    protected void lazyLoad() {
        Log.i("ClassSRFragment 加载数据");
        dialogShow("加载中");
        System.out.println("doClassMainIndex------------");
        classMainManager.doClassMainIndex(model.getUser().getUserid());//固定值fanny帐号，作测试用
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rel_person:
                if ("-1".equals(select_class_id)) {
                    Intent intent = new Intent(getActivity(), StuPersonDateActivity.class);
                    intent.putExtra("classId", select_class_id);
                    getActivity().startActivity(intent);
                }
                break;
            case R.id.text_more:
                if ("-1".equals(select_class_id)) {
                    Intent intents = new Intent(getActivity(), DYActivity.class);
                    intents.putExtra("classId", select_class_id);
                    startActivity(intents);
                }
                break;
            case R.id.img_xtxx://系统消息
                img_xtxx.setImageResource(R.drawable.img_xt_select);
                img_gg.setImageResource(R.drawable.img_gg_unselect);
                if (dySysModel != null) {
                    rel_no_message.setVisibility(View.GONE);
                    rel_message.setVisibility(View.VISIBLE);
                    img_lb.setVisibility(View.VISIBLE);
                    img.setVisibility(View.GONE);
                    Picasso.with(getContext()).load(R.drawable.speaker).fit().error(R.drawable.img_default).into(img);

                    text_value.setText(dySysModel.getDyContent());
                    String time = DateUtil.getInstance().convertDateStr(dySysModel.getCreateDate(), "yyyy年MM月dd日");
                    text_time.setText(time);
                } else {
                    rel_no_message.setVisibility(View.VISIBLE);
                    rel_message.setVisibility(View.GONE);
                }
                break;
            case R.id.img_gg://公告
                img_gg.setImageResource(R.drawable.img_gg_select);
                img_xtxx.setImageResource(R.drawable.img_xt_unselect);
                if (dyNoticeModel != null) {
                    rel_no_message.setVisibility(View.GONE);
                    rel_message.setVisibility(View.VISIBLE);

                    img_lb.setVisibility(View.GONE);
                    img.setVisibility(View.VISIBLE);

                    String paths = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
                    if ("".equals(dyNoticeModel.getPhoto())) {
                        Picasso.with(getContext()).load("111").fit().error(R.drawable.img_default).into(img);
                    } else {
                        Picasso.with(getContext()).load(paths + dyNoticeModel.getPhoto()).fit().error(R.drawable.img_default).into(img);
                    }

                    text_value.setText(dyNoticeModel.getDyContent());
                    String times = DateUtil.getInstance().convertDateStr(dyNoticeModel.getCreateDate(), "yyyy年MM月dd日");
                    text_time.setText(times);
                } else {
                    rel_no_message.setVisibility(View.VISIBLE);
                    rel_message.setVisibility(View.GONE);
                }
                break;
            case R.id.rel_jzjs://减重斤数
                popSelectType.dismiss();
                select_type = 0;
                text_select_type.setText("按减重斤数");
                classMainManager.doClMemberChange(model.getUser().getUserid(), select_class_id, select_type + "");
                break;
            case R.id.rel_jzbfb://减重百分比
                popSelectType.dismiss();
                select_type = 1;
                text_select_type.setText("按减重百分比");
                classMainManager.doClMemberChange(model.getUser().getUserid(), select_class_id, select_type + "");
                break;
            case R.id.rel_tzl://体制率
                popSelectType.dismiss();
                select_type = 2;
                text_select_type.setText("按体脂率");
                classMainManager.doClMemberChange(model.getUser().getUserid(), select_class_id, select_type + "");
                break;
            case R.id.rel_ywbh://腰围变化
                popSelectType.dismiss();
                select_type = 3;
                text_select_type.setText("按腰围变化");
                classMainManager.doClMemberChange(model.getUser().getUserid(), select_class_id, select_type + "");
                break;
            case R.id.lin_invite_student://邀请学员
                popTitleMore.dismiss();

                break;
            case R.id.lin_invite_ass://邀请助教
                popTitleMore.dismiss();

                break;
            case R.id.lin_create_class://创建班级
                popTitleMore.dismiss();

                break;
            case R.id.lin_select_type://选择展示类型
                if (popSelectType != null && popSelectType.isShowing()) {
                    popSelectType.dismiss();
                } else {

                    initSelectTypePop();

                    popSelectType = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, true);
                    popSelectType.setOutsideTouchable(true);
                    popSelectType.setBackgroundDrawable(new BitmapDrawable());
                    popSelectType.showAsDropDown(lin_select_type, DisplayUtil.dip2px(getContext(), -95), 0);

                }
                break;
//            case R.id.rel_title_more://右上角更多按钮
//                if (popTitleMore != null && popTitleMore.isShowing()) {
//                    popTitleMore.dismiss();
//                } else {
//                    View view = getActivity().getLayoutInflater().inflate(R.layout.popview_title_more, null);
//                    lin_invite_student = (LinearLayout) view.findViewById(R.id.lin_invite_student);
//                    lin_invite_ass = (LinearLayout) view.findViewById(R.id.lin_invite_ass);
//                    lin_create_class = (LinearLayout) view.findViewById(R.id.lin_create_class);
//
//                    lin_invite_student.setOnClickListener(this);
//                    lin_invite_ass.setOnClickListener(this);
//                    lin_create_class.setOnClickListener(this);
//
//                    popTitleMore = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT,
//                            LinearLayout.LayoutParams.WRAP_CONTENT, true);
//                    popTitleMore.setOutsideTouchable(true);
//                    popTitleMore.setBackgroundDrawable(new BitmapDrawable());
//                    popTitleMore.showAsDropDown(rel_title_more, DisplayUtil.dip2px(getContext(), -45), 0);
//
//                }
//                break;
        }
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= 0) {
            pull.setEnabled(true);
        } else {
            pull.setEnabled(false);
        }
        float alpha = (1f * y / 1000);
        cuttent_y = y;
        if (getContext() instanceof BodyGamePCActivity) {
            BodyGamePCActivity activity = (BodyGamePCActivity) getContext();
            activity.setAlpha(alpha);
            rel_title.setAlpha(alpha);
        }
    }

    public void getClassMain(PCClassMainModel classMainModel) {
        try {
            if (classMainModel != null) {
                String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");

                PCClmDetailModel model = classMainModel.getClmDetail();
                String startTime = model.getStartDate();
                String endTime = model.getEndDate();
                text_class_time.setText(DateUtil.getInstance(DateUtil.yyyy_MM_dd_HH_mm_ss).getYear(startTime) + "年" +
                        DateUtil.getInstance(DateUtil.yyyy_MM_dd_HH_mm_ss).getMonth(startTime) + "月" + "-" +
                        DateUtil.getInstance(DateUtil.yyyy_MM_dd_HH_mm_ss).getYear(endTime) + "年" +
                        DateUtil.getInstance(DateUtil.yyyy_MM_dd_HH_mm_ss).getMonth(endTime) + "月");

                text_class_name.setText(model.getClassName());
                select_class_id = model.getClassId();
                SharedPreferenceService.getInstance().put("classId", select_class_id);

                text_before_weight.setText("初始体重：" + model.getFirstweight() + "斤");
                String value = StringUtil.getFloatValue(model.getLoss());
                text_values.setText(value + "斤");
                text_name.setText(model.getUserName());
                tv_who.setText(model.getSupName());
                text_count.setText("x" + model.getHonorcnt());

                if ("".equals(model.getPhoto())) {
                    Picasso.with(getActivity()).load("111").fit().error(R.drawable.img_default).into(civ_header);
                } else {
                    Picasso.with(getActivity()).load(path + model.getPhoto()).fit().error(R.drawable.img_default).into(civ_header);
                }

                if ("".equals(model.getClassBanner())) {
                    Picasso.with(getContext()).load("111").fit().error(R.drawable.default_icon_rect).into(img_banner);
                } else {
                    Picasso.with(getContext()).load(path + model.getClassBanner()).fit().error(R.drawable.default_icon_rect).into(img_banner);
                }

                if (model.getGender().equals("1")) {
                    cb_gender.setChecked(true);
                } else {
                    cb_gender.setChecked(false);
                }
                if (model.getIsStar().equals("1")) {
                    cb_star.setChecked(true);
                } else {
                    cb_star.setChecked(false);
                }
                if (model.getIsTest().equals("1")) {
                    cb_fc.setChecked(true);
                } else {
                    cb_fc.setChecked(false);
                }

                select_type = 0;
                text_select_type.setText("按减重斤数");
                initSelectTypePop();

                dyNoticeModel = classMainModel.getDyNotice();
                dySysModel = classMainModel.getDySys();

                if (dySysModel.getPhoto() == null) {
                    dySysModel = null;
                }
                if (dyNoticeModel.getPhoto() == null) {
                    dyNoticeModel = null;
                }
                if (dyNoticeModel != null) {
                    rel_no_message.setVisibility(View.GONE);
                    rel_message.setVisibility(View.VISIBLE);
                    img_lb.setVisibility(View.GONE);
                    img.setVisibility(View.VISIBLE);
                    if ("".equals(dyNoticeModel.getPhoto())) {
                        Picasso.with(getContext()).load(R.drawable.img_default).into(img);
                    } else {
                        Picasso.with(getContext()).load(path + dyNoticeModel.getPhoto()).fit().error(R.drawable.img_default).into(img);
                    }

                    text_value.setText(dyNoticeModel.getDyContent());
                    String time = DateUtil.getInstance().convertDateStr(dyNoticeModel.getCreateDate(), "yyyy年MM月dd日");
                    text_time.setText(time);
                } else {
                    rel_no_message.setVisibility(View.VISIBLE);
                    rel_message.setVisibility(View.GONE);
                }
                student_list.clear();
                student_list.addAll(classMainModel.getClmlist());
                adapter.type = select_type + "";
                adapter.notifyDataSetChanged();
                ListViewUtil.setListViewHeightBasedOnChildren(list_student);
            }else {
                pull.setRefreshing(false);
                dialogDissmiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getStudentList(MemberChangeModel memberChangeModel) {

        if (memberChangeModel != null) {
            list_student.setVisibility(View.VISIBLE);
            student_list = memberChangeModel.getClmlist();
            adapter = new ClassMainPCStudentAdapter(getContext(), student_list);
            adapter.type = select_type + "";
            list_student.setAdapter(adapter);
            ListViewUtil.setListViewHeightBasedOnChildren(list_student);
        } else {
            list_student.setVisibility(View.GONE);
            pull.setRefreshing(false);
            dialogDissmiss();
        }
    }

    @Override
    public void onRefresh() {
        if (model.getUser() == null) {
            return;
        }
        classMainManager.doClassMainIndex(model.getUser().getUserid());//固定值fanny帐号，作测试用
    }

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(Constants.MESSAGE_DISSMISS_ACTION);
        getContext().registerReceiver(mMessageReceiver, filter);

    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Constants.MESSAGE_DISSMISS_ACTION.equals(intent.getAction())) {
                dialogDissmiss();
                pull.setRefreshing(false);
            }
        }
    }

}
