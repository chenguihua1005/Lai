package com.softtek.lai.module.bodygame3.home.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ggx.widgets.adapter.ViewHolder;
import com.ggx.widgets.nicespinner.ArrowSpinnerAdapter;
import com.ggx.widgets.nicespinner.ListDialog;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.module.bodygame3.head.adapter.HonorFragmentAdapter;
import com.softtek.lai.module.bodygame3.head.model.ClassModel;
import com.softtek.lai.module.bodygame3.head.model.HonorFragmentModel;
import com.softtek.lai.module.bodygame3.head.model.SaveclassModel;
import com.softtek.lai.module.bodygame3.head.view.HonorRuleActivity;
import com.softtek.lai.module.bodygame3.home.HonorFragment;
import com.softtek.lai.module.bodygame3.home.event.SaveClassModel;
import com.softtek.lai.module.bodygame3.home.event.UpdateClass;
import com.softtek.lai.module.customermanagement.view.GymClubActivity;
import com.softtek.lai.widgets.NoSlidingViewPage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jessica.zhang on 12/26/2017.
 */

@InjectLayout(R.layout.fragment_honor_roll_tab)
public class HonorTabFragment extends LazyBaseFragment implements View.OnClickListener {
//    @InjectView(R.id.pull)
//    MySwipRefreshView pull;

    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.spinner_title_honor)
    ListDialog tv_title;

    @InjectView(R.id.tv_right)
    TextView tv_right;

    @InjectView(R.id.tab)
    TabLayout tab;
    @InjectView(R.id.tab_content)
    NoSlidingViewPage tab_content;
    List<HonorFragmentModel> fragmentList = new ArrayList<>();

    private String classId = "";
    private String tagClassId = "";

    private ArrayList<ClassModel> classModels = new ArrayList<>();
    private int classrole;
    private SaveclassModel saveclassModel;

    private ClassModel classModel;

    boolean isFirst = true;

    private boolean needRefreshTitle = true;//正常切换体馆周和月的时候，不用刷新Tab页的班级列表.默认是刷新的


    public HonorTabFragment() {

    }

    @Override
    protected void lazyLoad() {
//        pull.setRefreshing(true);
//        pull.setRefreshing(false);
    }

    @Override
    protected void initViews() {
        if (getArguments() != null) {
            tagClassId = getArguments().getString("classId");
        }

    }

    @Override
    protected void initDatas() {
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver, new IntentFilter(UPDATE_CLASSLIST));
        saveclassModel = new SaveclassModel();

        tv_right.setText(R.string.rule);
        int selector = 0;

        String[] tabtitle = {"体重周榜", "体重月榜", "体重总榜", "体脂周榜", "体脂月榜", "体脂总榜"};
        String[] ByWhichRatio = {"ByWeightRatio", "ByFatRatio"};
        String[] SortTimeType = {"ByWeek", "ByMonth", "ByTotal"};

        for (int i = 0; i < 6; i++) {
            Bundle bundle = new Bundle();
            bundle.putString("classId", classId);
            bundle.putString("from", "tab");//来自tab页
            if (i >= 0 && i < 3) {
                bundle.putString("ByWhichRatio", ByWhichRatio[0]);
            } else {
                bundle.putString("ByWhichRatio", ByWhichRatio[1]);
            }

            if (i == 0 || i == 3) {
                bundle.putString("SortTimeType", SortTimeType[0]);
            } else if (i == 1 || i == 4) {
                bundle.putString("SortTimeType", SortTimeType[1]);
            } else {
                bundle.putString("SortTimeType", SortTimeType[2]);
            }

            fragmentList.add(new HonorFragmentModel(tabtitle[i], HonorFragment.newInstance(bundle)));
        }

        tab_content.setAdapter(new HonorFragmentAdapter(getChildFragmentManager(), fragmentList));
        tab.setupWithViewPager(tab_content);
        tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        tab_content.setOffscreenPageLimit(4);
        tab_content.setCurrentItem(selector, false);


        ll_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);

        EventBus.getDefault().register(this);


        tv_title.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                classId = classModels.get(i).getClassId();
                SharedPreferenceService.getInstance(getContext()).put("ClassId", classId);

                classrole = classModels.get(i).getClassRole();

                saveclassModel.setClassId(classModels.get(i).getClassId());
                saveclassModel.setClassName(classModels.get(i).getClassName());
                saveclassModel.setClassRole(classModels.get(i).getClassRole());
                saveclassModel.setClassWeek(classModels.get(i).getClassWeek());
                saveclassModel.setClassCode(classModels.get(i).getClassCode());
//                lazyLoad();

                if (needRefreshTitle) {
                    Intent intent = new Intent(HonorFragment.UPDATE_HONOR_VIEW);
                    intent.putExtra("classId", classId);
                    LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                SharedPreferenceService.getInstance(getContext()).put("ClassId", "");

                if (!tagClassId.equals("")) {
                    startActivity(new Intent(getActivity(), GymClubActivity.class));
                } else {
                    getActivity().finish();
                }
                break;
            case R.id.tv_right:
                startActivity(new Intent(getContext(), HonorRuleActivity.class));
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
    }

    public static final String UPDATE_CLASSLIST = "UPDATE_CLASSLIST";
    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction().equalsIgnoreCase(UPDATE_CLASSLIST)) {
                if (classModels != null) {
                    classModels.clear();
                    classModels = intent.getParcelableArrayListExtra("classList");
                    needRefreshTitle = intent.getBooleanExtra("needRefreshTitle", needRefreshTitle);

                    if (classModels != null && classModels.size() > 0) {
                        //班级列表
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
                                if (classModels != null && classModels.size() > 0) {
//                                    classId = classModels.get(position).getClassId();
                                    return classModels.get(position).getClassName();
                                } else {
                                    return "null班级";
                                }
                            }
                        });

                        if (TextUtils.isEmpty(classId)) {
                            classId = classModels.get(0).getClassId();
                            classrole = classModels.get(0).getClassRole();
                            tv_title.setSelected(0);
                            tv_title.notifChange();

                        } else {
                            for (int i = 0, j = classModels.size(); i < j; i++) {
                                ClassModel model = classModels.get(i);
                                if (model.getClassId().equals(classId)) {
                                    tv_title.setSelected(i);
                                    tv_title.notifChange();
                                    break;
                                }
                            }
                        }


//                            tv_title.attachCustomSource(new ArrowSpinnerAdapter<ClassModel>(getContext(), classModels, R.layout.selector_class_item) {
//                                @Override
//                                public void convert(ViewHolder holder, ClassModel data, int position) {
//                                    ImageView iv_icon = holder.getView(R.id.iv_icon);
//                                    boolean selected = tv_title.getSelectedIndex() == position;
//
//                                    boolean isWorker = data.isWorker();//是否是俱乐部工作人员，true-是，false-否
//                                    int icon = R.drawable.class_xueyuan;
//                                    if (isWorker) {
//                                        icon = R.drawable.worker;
//                                    }
//                                    switch (data.getClassRole()) {
//                                        case 1:
//                                            icon = R.drawable.class_zongjiaolian;
//                                            break;
//                                        case 2:
//                                            icon = R.drawable.class_jiaolian;
//                                            break;
//                                        case 3:
//                                            icon = R.drawable.class_zhujiao;
//                                            break;
//                                        case 4:
//                                            icon = R.drawable.class_xueyuan;
//                                            break;
//                                    }
//                                    iv_icon.setImageDrawable(ContextCompat.getDrawable(getContext(), icon));
//                                    TextView tv_number = holder.getView(R.id.tv_number);
//                                    tv_number.setText("班级编号:" + data.getClassCode());
//                                    TextView tv_class_name = holder.getView(R.id.tv_class_name);
//                                    tv_class_name.setText(data.getClassName());
//                                    RadioButton iv_sel = holder.getView(R.id.iv_select);
//                                    iv_sel.setChecked(selected);
//                                }
//
//                                @Override
//                                public String getText(int position) {
//                                    if (classModels != null && !classModels.isEmpty()) {
//                                        classId = classModels.get(position).getClassId();
//                                        return classModels.get(position).getClassName();
//                                    } else {
//                                        return "暂无班级";
//                                    }
//                                }
//                            });

//                            tv_title.notifChange();
//                            tv_title.setSelected(0);

//                        tv_title.getAdapter().notifyDataSetChanged();
//                        tv_title.setSelected(tv_title.getSelectedIndex());

//                        tv_title.getAdapter().notifyDataSetChanged();
//                        tv_title.setSelected(0);
                    } else {
                        //如果班级是空
                        tv_title.setText("暂无班级");
                    }
                }
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    //更新班级
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
            if (classModels.isEmpty()) {
                classId = "";
                this.classModel = null;
            } else {
                tv_title.setSelected(0);
                this.classModel = classModels.get(0);
                classId = this.classModel.getClassId();
            }
            lazyLoad();
        }
    }


    private boolean isSelector;

    @Subscribe
    public void classSelect(SaveClassModel saveClassModel) {
        isSelector = true;
        classId = saveClassModel.classId;

        Intent intent = new Intent(HonorFragment.UPDATE_HONOR_VIEW);
        intent.putExtra("classId", classId);
        SharedPreferenceService.getInstance(getContext()).put("ClassId", classId);
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
    }

}
