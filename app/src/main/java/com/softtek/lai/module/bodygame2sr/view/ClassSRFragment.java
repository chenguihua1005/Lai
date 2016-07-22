package com.softtek.lai.module.bodygame2sr.view;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame2.adapter.ClassMainSRStudentAdapter;
import com.softtek.lai.module.bodygame2.adapter.ClassMainStudentAdapter;
import com.softtek.lai.module.bodygame2.adapter.ClassSelectAdapter;
import com.softtek.lai.module.bodygame2.model.ClassChangeModel;
import com.softtek.lai.module.bodygame2.model.ClassDetailModel;
import com.softtek.lai.module.bodygame2.model.ClassListModel;
import com.softtek.lai.module.bodygame2.model.ClassMainModel;
import com.softtek.lai.module.bodygame2.model.ClassModel;
import com.softtek.lai.module.bodygame2.model.ClmListModel;
import com.softtek.lai.module.bodygame2.model.DyNoticeModel;
import com.softtek.lai.module.bodygame2.model.DySysModel;
import com.softtek.lai.module.bodygame2.model.MemberChangeModel;
import com.softtek.lai.module.bodygame2.present.ClassMainManager;
import com.softtek.lai.module.bodygame2.view.BodyGameSPActivity;
import com.softtek.lai.module.bodygame2.view.DYActivity;
import com.softtek.lai.module.bodygame2.view.PersonalDataActivity;
import com.softtek.lai.module.bodygame2sr.present.ClassMainSRManager;
import com.softtek.lai.module.counselor.model.ClassIdModel;
import com.softtek.lai.module.counselor.net.CounselorService;
import com.softtek.lai.module.counselor.view.AssistantListActivity;
import com.softtek.lai.module.counselor.view.CreateCounselorClassActivity;
import com.softtek.lai.module.counselor.view.InviteStudentActivity;
import com.softtek.lai.module.grade.model.BannerUpdateCallBack;
import com.softtek.lai.module.grade.net.GradeService;
import com.softtek.lai.module.grade.presenter.GradeImpl;
import com.softtek.lai.module.grade.presenter.IGrade;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.ListViewUtil;
import com.softtek.lai.utils.StringUtil;
import com.softtek.lai.widgets.ObservableScrollView;
import com.squareup.picasso.Picasso;
import com.sw926.imagefileselector.ImageFileCropSelector;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.fragment_class_sr)
public class ClassSRFragment extends LazyBaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, ObservableScrollView.ScrollViewListener, ClassMainSRManager.ClassMainCallback {
    @InjectView(R.id.lin_class_select)
    LinearLayout lin_class_select;

    @InjectView(R.id.scroll)
    ObservableScrollView scroll;
    @InjectView(R.id.text_more)
    TextView text_more;

    @InjectView(R.id.rel_title_more)
    RelativeLayout rel_title_more;

    @InjectView(R.id.lin_select_type)
    LinearLayout lin_select_type;

    @InjectView(R.id.rel_title)
    RelativeLayout rel_title;

    @InjectView(R.id.rel)
    RelativeLayout rel;

    @InjectView(R.id.rel_sy)
    RelativeLayout rel_sy;

    @InjectView(R.id.pull)
    SwipeRefreshLayout pull;

    @InjectView(R.id.text_no_student)
    TextView text_no_student;

    @InjectView(R.id.list_student)
    ListView list_student;
    @InjectView(R.id.text_class_name)
    TextView text_class_name;

    @InjectView(R.id.img_class_down)
    ImageView img_class_down;

    @InjectView(R.id.text_select_type)
    TextView text_select_type;

    @InjectView(R.id.img_banner)
    ImageView img_banner;

    @InjectView(R.id.text_class_count)
    TextView text_class_count;
    @InjectView(R.id.text_loss)
    TextView text_loss;
    @InjectView(R.id.text_fcl)
    TextView text_fcl;

    @InjectView(R.id.rel_gg)
    RelativeLayout rel_gg;
    @InjectView(R.id.img_gg)
    ImageView img_gg;
    @InjectView(R.id.rel_xtxx)
    RelativeLayout rel_xtxx;
    @InjectView(R.id.img_xtxx)
    ImageView img_xtxx;

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

    @InjectView(R.id.rel_no_class)
    RelativeLayout rel_no_class;

    private PopupWindow popTitleMore;
    private PopupWindow popSelectType;
    private PopupWindow popTitleSelect;

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
    private String select_class_id="-1";

    private List<ClassListModel> select_class_list = new ArrayList<ClassListModel>();
    private List<ClmListModel> student_list;

    ClassMainSRManager classMainManager;

    DyNoticeModel dyNoticeModel;
    DySysModel dySysModel;

    ClassMainSRStudentAdapter adapter;
    ClassSelectAdapter adapters;

    View view_class;


    View view;

    ListView list_class_select;


    UserInfoModel model;

    private boolean has_class = false;


    private ProgressDialog progressDialog;
    private MessageReceiver mMessageReceiver;

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

        lin_class_select.setOnClickListener(this);
        rel_title_more.setOnClickListener(this);
        lin_select_type.setOnClickListener(this);
        rel_gg.setOnClickListener(this);
        text_more.setOnClickListener(this);
        rel_xtxx.setOnClickListener(this);
        rel_title.setAlpha(0f);
        rel_sy.setAlpha(1f);
        scroll.setScrollViewListener(this);
        list_student.setEmptyView(text_no_student);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("加载中");
        classMainManager = new ClassMainSRManager(this);
        view = getActivity().getLayoutInflater().inflate(R.layout.popview_select_type, null);
        rel_jzjs = (RelativeLayout) view.findViewById(R.id.rel_jzjs);
        rel_jzbfb = (RelativeLayout) view.findViewById(R.id.rel_jzbfb);
        rel_tzl = (RelativeLayout) view.findViewById(R.id.rel_tzl);
        rel_ywbh = (RelativeLayout) view.findViewById(R.id.rel_ywbh);


        img_jzjs = (ImageView) view.findViewById(R.id.img_jzjs);
        img_jzbfb = (ImageView) view.findViewById(R.id.img_jzbfb);
        img_tzl = (ImageView) view.findViewById(R.id.img_tzl);
        img_ywbh = (ImageView) view.findViewById(R.id.img_ywbh);

        view_class = getActivity().getLayoutInflater().inflate(R.layout.popview_title_class, null);
        list_class_select = (ListView) view_class.findViewById(R.id.list_class_select);

        adapters = new ClassSelectAdapter(getContext(), select_class_list);
        list_class_select.setAdapter(adapters);

        list_student.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("------");
                ClmListModel clmListModel = student_list.get(position);
                String isHasAssi = clmListModel.getIsHasAssi();
                if (!"0".equals(isHasAssi)) {
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
        if (has_class) {
            scroll.scrollTo(0, 0);
            if (getContext() instanceof BodyGameSRActivity) {
                BodyGameSRActivity activity = (BodyGameSRActivity) getContext();
                activity.setAlpha(0);
            }
        }else {
            if (getContext() instanceof BodyGameSRActivity) {
                BodyGameSRActivity activity = (BodyGameSRActivity) getContext();
                activity.setAlpha(1);
            }
        }
        super.onVisible();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
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
        if (getContext() instanceof BodyGameSRActivity) {
            BodyGameSRActivity activity = (BodyGameSRActivity) getContext();
            activity.setAlpha(0);
        }
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
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void lazyLoad() {
        Log.i("ClassSRFragment 加载数据");
        text_class_name.setFocusable(true);
        text_class_name.setFocusableInTouchMode(true);
        text_class_name.requestFocus();
        scroll.setFocusable(false);
        dialogShow("加载");
        classMainManager.doClassMainIndex(model.getUser().getUserid());//固定值fanny帐号，作测试用
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_more:
                Intent intents = new Intent(getActivity(), DYActivity.class);
                intents.putExtra("classId", select_class_id);
                startActivity(intents);
                break;
            case R.id.rel_xtxx://系统消息
                System.out.println("dySysModel:" + dySysModel);
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
            case R.id.rel_gg://公告
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
                dialogShow("加载中");
                classMainManager.doClMemberChange(model.getUser().getUserid(), select_class_id, select_type + "");
                break;
            case R.id.rel_jzbfb://减重百分比
                popSelectType.dismiss();
                select_type = 1;
                text_select_type.setText("按减重百分比");
                dialogShow("加载中");
                classMainManager.doClMemberChange(model.getUser().getUserid(), select_class_id, select_type + "");
                break;
            case R.id.rel_tzl://体制率
                popSelectType.dismiss();
                select_type = 2;
                text_select_type.setText("按体脂率");
                dialogShow("加载中");
                classMainManager.doClMemberChange(model.getUser().getUserid(), select_class_id, select_type + "");
                break;
            case R.id.rel_ywbh://腰围变化
                popSelectType.dismiss();
                select_type = 3;
                text_select_type.setText("按腰围变化");
                dialogShow("加载中");
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
            case R.id.lin_class_select://选择班级
                if (popTitleSelect != null && popTitleSelect.isShowing()) {
                    popTitleSelect.dismiss();
                    text_class_name.setTextColor(Color.WHITE);
                } else {
                    view_class = getActivity().getLayoutInflater().inflate(R.layout.popview_title_class, null);
                    list_class_select = (ListView) view_class.findViewById(R.id.list_class_select);

                    adapters = new ClassSelectAdapter(getContext(), select_class_list);
                    list_class_select.setAdapter(adapters);

                    lin_class_select.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.app_list_corner_round_top));
                    img_class_down.setImageResource(R.drawable.img_bg_more_down);
                    adapters.notifyDataSetChanged();
                    text_class_name.setTextColor(Color.BLACK);
                    popTitleSelect = new PopupWindow(view_class, LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, true);
                    popTitleSelect.setOutsideTouchable(true);
                    popTitleSelect.setBackgroundDrawable(new BitmapDrawable());
                    popTitleSelect.showAsDropDown(lin_class_select);
                    popTitleSelect.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            text_class_name.setTextColor(Color.WHITE);
                            lin_class_select.setBackgroundColor(Color.TRANSPARENT);
                            img_class_down.setImageResource(R.drawable.img_bg_more_down_1);
                        }
                    });
                    list_class_select.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            popTitleSelect.dismiss();
                            text_class_name.setTextColor(Color.WHITE);
                            ClassListModel classListModel = select_class_list.get(position);
                            select_class_id = classListModel.getClassId();
                            SharedPreferenceService.getInstance().put("classId", select_class_id);
                            text_class_name.setText(classListModel.getClassName());
                            dialogShow("加载中");
                            classMainManager.doClassChangeById(select_class_id, model.getUser().getUserid());
                        }
                    });
                }
                break;
            case R.id.rel_title_more://右上角更多按钮
                if (popTitleMore != null && popTitleMore.isShowing()) {
                    popTitleMore.dismiss();
                } else {
                    View view = getActivity().getLayoutInflater().inflate(R.layout.popview_title_more, null);
                    lin_invite_student = (LinearLayout) view.findViewById(R.id.lin_invite_student);
                    lin_invite_ass = (LinearLayout) view.findViewById(R.id.lin_invite_ass);
                    lin_create_class = (LinearLayout) view.findViewById(R.id.lin_create_class);

                    lin_invite_student.setOnClickListener(this);
                    lin_invite_ass.setOnClickListener(this);
                    lin_create_class.setOnClickListener(this);

                    popTitleMore = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, true);
                    popTitleMore.setOutsideTouchable(true);
                    popTitleMore.setBackgroundDrawable(new BitmapDrawable());
                    popTitleMore.showAsDropDown(rel_title_more, DisplayUtil.dip2px(getContext(), -45), 0);

                }
                break;
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

        if (getContext() instanceof BodyGameSRActivity) {
            BodyGameSRActivity activity = (BodyGameSRActivity) getContext();
            activity.setAlpha(alpha);
            rel_title.setAlpha(alpha);
        }
    }

    @Override
    public void getClassMain(String type, ClassMainModel classMainModel) {
        rel_sy.setVisibility(View.VISIBLE);
        rel.setVisibility(View.VISIBLE);
        try {
            if ("200".equals(type)) {
                pull.setEnabled(true);
                has_class = true;
                lin_class_select.setVisibility(View.VISIBLE);
                rel_title_more.setVisibility(View.GONE);
                if (getContext() instanceof BodyGameSRActivity) {
                    BodyGameSRActivity activity = (BodyGameSRActivity) getContext();
                    activity.setAlpha(0);
                }
                rel_title.setAlpha(0f);
                scroll.setVisibility(View.VISIBLE);
                rel_no_class.setVisibility(View.GONE);

                text_more.setVisibility(View.VISIBLE);

                select_class_list.clear();
                select_class_list.addAll(classMainModel.getClasslist());
                adapters.notifyDataSetChanged();
                text_class_name.setText(select_class_list.get(0).getClassName());
                select_class_id = select_class_list.get(0).getClassId();
                SharedPreferenceService.getInstance().put("classId", select_class_id);
                student_list = classMainModel.getClmlist();
                adapter = new ClassMainSRStudentAdapter(getContext(), student_list, "0");
                adapter.type = select_type + "";
                list_student.setAdapter(adapter);
                ListViewUtil.setListViewHeightBasedOnChildren(list_student);
                if(student_list.size()==0) {
                    dialogDissmiss();
                    pull.setRefreshing(false);
                }

                ClassDetailModel details = classMainModel.getClassDetail();
                String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
                if ("".equals(details.getClassBanner())) {
                    Picasso.with(getContext()).load("111").fit().error(R.drawable.default_icon_rect).into(img_banner);
                } else {
                    Picasso.with(getContext()).load(path + details.getClassBanner()).fit().error(R.drawable.default_icon_rect).into(img_banner);
                }
                if ("0".equals(details.getClmCnt())) {
                    text_class_count.setText("--");
                } else {
                    text_class_count.setText(details.getClmCnt());
                }
                if ("0.0".equals(details.getTotalloss())) {
                    text_loss.setText("--");
                } else {
                    text_loss.setText(details.getTotalloss() + "斤");
                }
                if ("0".equals(details.getRtest())) {
                    text_fcl.setText("--");
                } else {
                    text_fcl.setText(details.getRtest() + "%");
                }

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
                        Picasso.with(getContext()).load("111").fit().error(R.drawable.img_default).into(img);
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

            } else if ("100".equals(type)) {
                pull.setEnabled(false);
                has_class = false;
                pull.setRefreshing(false);
                dialogDissmiss();
                lin_class_select.setVisibility(View.GONE);
                rel_title_more.setVisibility(View.GONE);
                rel_title.setAlpha(1f);
                if (getContext() instanceof BodyGameSRActivity) {
                    BodyGameSRActivity activity = (BodyGameSRActivity) getContext();
                    activity.setAlpha(1);
                }
                scroll.setVisibility(View.GONE);
                rel_no_class.setVisibility(View.VISIBLE);
            } else {
                pull.setEnabled(false);
                pull.setRefreshing(false);
                dialogDissmiss();
                has_class = false;
                lin_class_select.setVisibility(View.GONE);
                rel_title_more.setVisibility(View.GONE);
                if (getContext() instanceof BodyGameSRActivity) {
                    BodyGameSRActivity activity = (BodyGameSRActivity) getContext();
                    activity.setAlpha(0);
                }
                rel_title.setAlpha(0f);
                scroll.setVisibility(View.VISIBLE);
                rel_no_class.setVisibility(View.GONE);

                text_more.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            pull.setRefreshing(false);
            dialogDissmiss();
        }
    }

    @Override
    public void getStudentList(MemberChangeModel memberChangeModel) {
        System.out.println("memberChangeModel:" + memberChangeModel);
        if (memberChangeModel != null) {
            list_student.setVisibility(View.VISIBLE);
            student_list = memberChangeModel.getClmlist();
            adapter = new ClassMainSRStudentAdapter(getContext(), student_list, "1");
            adapter.type = select_type + "";
            list_student.setAdapter(adapter);
            ListViewUtil.setListViewHeightBasedOnChildren(list_student);
            if(student_list.size()==0) {
                dialogDissmiss();
                pull.setRefreshing(false);
            }
        } else {
            list_student.setVisibility(View.GONE);
            pull.setRefreshing(false);
            dialogDissmiss();
        }
    }

    @Override
    public void getClassChange(ClassChangeModel classChangeModel) {
        try {
            if (classChangeModel != null) {
                text_more.setVisibility(View.VISIBLE);
                select_class_list.clear();
                select_class_list.addAll(classChangeModel.getClasslist());
                adapters.notifyDataSetChanged();

                select_type = 0;
                text_select_type.setText("按减重斤数");
                initSelectTypePop();

                student_list = classChangeModel.getClmlist();
                adapter = new ClassMainSRStudentAdapter(getContext(), student_list, "0");
                adapter.type = select_type + "";
                list_student.setAdapter(adapter);
                ListViewUtil.setListViewHeightBasedOnChildren(list_student);
                if(student_list.size()==0) {
                    dialogDissmiss();
                    pull.setRefreshing(false);
                }

                ClassDetailModel details = classChangeModel.getClassDetail();
                String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
                if ("".equals(details.getClassBanner())) {
                    Picasso.with(getContext()).load("111").fit().error(R.drawable.default_icon_rect).into(img_banner);
                } else {
                    Picasso.with(getContext()).load(path + details.getClassBanner()).fit().error(R.drawable.default_icon_rect).into(img_banner);
                }
                if ("0".equals(details.getClmCnt())) {
                    text_class_count.setText("--");
                } else {
                    text_class_count.setText(details.getClmCnt());
                }
                if ("0.0".equals(details.getTotalloss())) {
                    text_loss.setText("--");
                } else {
                    text_loss.setText(details.getTotalloss() + "斤");
                }
                if ("0".equals(details.getRtest())) {
                    text_fcl.setText("--");
                } else {
                    text_fcl.setText(details.getRtest() + "%");
                }


                dyNoticeModel = classChangeModel.getDyNotice();
                dySysModel = classChangeModel.getDySys();

                if (dySysModel.getPhoto() == null) {
                    dySysModel = null;
                }
                if (dyNoticeModel.getPhoto() == null) {
                    dyNoticeModel = null;
                }
                img_gg.setImageResource(R.drawable.img_gg_select);
                img_xtxx.setImageResource(R.drawable.img_xt_unselect);

                if (dyNoticeModel != null) {
                    rel_no_message.setVisibility(View.GONE);
                    rel_message.setVisibility(View.VISIBLE);
                    img_lb.setVisibility(View.GONE);
                    img.setVisibility(View.VISIBLE);
                    if ("".equals(dyNoticeModel.getPhoto())) {
                        Picasso.with(getContext()).load("111").fit().error(R.drawable.img_default).into(img);
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
            } else {
                pull.setRefreshing(false);
                dialogDissmiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            pull.setRefreshing(false);
            dialogDissmiss();
        }
    }

    @Override
    public void onRefresh() {
        classMainManager.doClassChangeById(select_class_id, model.getUser().getUserid());
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
