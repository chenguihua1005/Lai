package com.softtek.lai.module.bodygame3.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ggx.widgets.adapter.ViewHolder;
import com.ggx.widgets.nicespinner.ArrowSpinnerAdapter;
import com.ggx.widgets.nicespinner.ListDialogHonor;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.head.adapter.HonorAdapter;
import com.softtek.lai.module.bodygame3.head.model.ClassModel;
import com.softtek.lai.module.bodygame3.head.model.HonorRankModel;
import com.softtek.lai.module.bodygame3.head.model.ListGroupModel;
import com.softtek.lai.module.bodygame3.head.model.ListdateModel;
import com.softtek.lai.module.bodygame3.head.presenter.HonorPresenter;
import com.softtek.lai.module.bodygame3.head.view.GroupRankingActivity;
import com.softtek.lai.module.bodygame3.head.view.PersonDetailActivity2;
import com.softtek.lai.module.bodygame3.home.view.HonorTabFragment;
import com.softtek.lai.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jessica.zhang on 12/21/2017.
 */

@InjectLayout(R.layout.fragment_honor_roll)
public class HonorFragment extends LazyBaseFragment<HonorPresenter> implements HonorPresenter.HonorView {

    private String ByWhichRatio = "ByWeightRatio";
    static private String ClassId = "";
    private String SortTimeType = "ByWeek";
    private Long UID = 333L;
    private int WhichTime = 1;
    private boolean is_first = true;

    @InjectView(R.id.list_honorrank)
    PullToRefreshExpandableListView listHonorrank;//列表

    @InjectView(R.id.ll_no_data)
    LinearLayout ll_no_data;
    @InjectView(R.id.ll_menu)
    LinearLayout ll_menu;

    @InjectView(R.id.arrow_spinner_honor)
    ListDialogHonor arrow;

    private HonorAdapter adapter;
    private List<ListGroupModel> groupModelList = new ArrayList<>();//ListGroupModel
    private List<ListGroupModel> classMemberModelList = new ArrayList<>();
    private List<List<ListGroupModel>> list_Son = new ArrayList<>();// 子层数据

    private List<String> parentsTitle = new ArrayList<>();

    List<ListdateModel> spinnerData = new ArrayList<>();
    private int selectedSpinner = 0;

    private String from = "";

    private boolean needRefreshTitle = true;//正常切换体馆周和月的时候，不用刷新Tab页的班级列表.默认是刷新的


    public HonorFragment() {

    }

    public static HonorFragment newInstance(Bundle bundle) {
        HonorFragment fragment = new HonorFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void lazyLoad() {
        Log.i("HonorFragment", "lazyLoad()......");
        UID = UserInfoModel.getInstance().getUserId();
        loadData(is_first);
    }


    @Override
    protected void initViews() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            ClassId = bundle.getString("classId");
            ByWhichRatio = bundle.getString("ByWhichRatio");
            SortTimeType = bundle.getString("SortTimeType");
            from = bundle.getString("from");

            Log.i("honor", "from = " + from + "  ClassId = " + ClassId + " ByWhichRatio= " + ByWhichRatio + " SortTimeType= " + SortTimeType);
        }

        arrow.setTintColor(R.color.black);
        arrow.attachCustomSource(new ArrowSpinnerAdapter<ListdateModel>(getContext(), spinnerData, R.layout.selector_rankdate_item) {
            @Override
            public void convert(ViewHolder holder, ListdateModel data, int position) {
                boolean selected = arrow.getSelectedIndex() == position;

                TextView tv_class_name = holder.getView(R.id.tv_date_name);
                tv_class_name.setText(data.getDateName());
                RadioButton iv_sel = holder.getView(R.id.iv_select);
                iv_sel.setChecked(selected);
            }

            @Override
            public String getText(int position) {
                if (spinnerData != null && !spinnerData.isEmpty()) {
                    return spinnerData.get(position).getDateName();
                } else {
                    return "";
                }
            }
        });

        arrow.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                needRefreshTitle = false;//切换体馆周和月的时候不需要刷新

                selectedSpinner = i;

                WhichTime = Integer.parseInt((spinnerData.get(i)).getDateValue());
                String dateName = (spinnerData.get(i)).getDateName();
                arrow.setText(dateName);
                WhichTime = Integer.parseInt((spinnerData.get(i)).getDateValue());
                loadData(is_first);
            }
        });


        //加上就先显示空的头部，这种效果不要。、
        //注释掉后第一种情况可以解决;第二种应该是因为没有给list一条空数据所以没有显示空头部；第三种情况依然正确。
        // 所以给list一条空数据就应该显示空的头部了


        listHonorrank.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

        adapter = new HonorAdapter(getContext(), parentsTitle, list_Son, ByWhichRatio);
        listHonorrank.getRefreshableView().setAdapter(adapter);
        listHonorrank.getRefreshableView().setEmptyView(ll_no_data);
        listHonorrank.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ExpandableListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                Log.i("HonorFragment", "刷新......onPullDownToRefresh() is running......");
                if (!TextUtils.isEmpty(from) && from.equals("tab")) {
                    ClassId = SharedPreferenceService.getInstance(getContext()).get("ClassId", "");
                }
                getPresenter().getHonorData(UID, ClassId, ByWhichRatio, SortTimeType, WhichTime, is_first);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {

            }
        });


        listHonorrank.getRefreshableView().setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int parentPos, int childPos, long l) {
                ListGroupModel groupModel = list_Son.get(parentPos).get(childPos);
                if (!"nodata".equals(groupModel.getUserId()))
                    if (1 == groupModel.getType()) {
                        Intent intent = new Intent(getContext(), GroupRankingActivity.class);
                        intent.putExtra("ClassId", ClassId);
                        intent.putExtra("ByWhichRatio", ByWhichRatio);
                        intent.putExtra("SortTimeType", SortTimeType);
                        if (!"ByTotal".equals(SortTimeType)) {
                            ListdateModel listdateModel = spinnerData.get(selectedSpinner);
                            intent.putExtra("listDataModel", listdateModel);
                        }
                        intent.putExtra("ListGroupModel", groupModel);
                        startActivity(intent);
                    } else if (2 == groupModel.getType()) {
                        Intent intent = new Intent(getContext(), PersonDetailActivity2.class);
                        intent.putExtra("AccountId", Long.parseLong(groupModel.getUserId()));
                        intent.putExtra("ClassId", ClassId);
                        startActivity(intent);
                    }

                return true;
            }
        });
        listHonorrank.getRefreshableView().setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {

                return groupPosition == 0 ? true : false;
            }
        });


        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver, new IntentFilter(UPDATE_HONOR_VIEW));


    }

    @Override
    protected void initDatas() {

        setPresenter(new HonorPresenter(this));

    }

    private void loadData(final boolean is_first) {
        //第一次请求会请求两次，第一次不让显示刷新效果，所以不用setrefreshing()。
        // 以后的请求都是一次一次来的，要有刷新效果，所以都用setRefreshing()调用这个方法后，会自动调用他的刷新方法，网络请求在刷新方法里。。
        if (is_first) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.i("HonorFragment", "is_first...  刷新界面...   new Handler().postDelayed(new Runnable() ");
                    if (!TextUtils.isEmpty(from) && from.equals("tab")) {
                        ClassId = SharedPreferenceService.getInstance(getContext()).get("ClassId", "");
                    }
                    getPresenter().getHonorData(UID, ClassId, ByWhichRatio, SortTimeType, WhichTime, is_first);
                }
            }, 500);

        } else {
            Log.i("HonorFragment", "不是第一次...  刷新界面...   new Handler().postDelayed(new Runnable() ");
            try {
                listHonorrank.setRefreshing();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void getHonorModel(HonorRankModel model) {
        ll_menu.setVisibility(View.VISIBLE);
        parentsTitle.clear();
        groupModelList.clear();
        classMemberModelList.clear();
        list_Son.clear();
        //请求不到数据的时候全屏显示“暂无数据”
        if (model == null) {
            ll_menu.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            Intent intent = new Intent(HonorTabFragment.UPDATE_CLASSLIST);
            intent.putParcelableArrayListExtra("classList", null);
            LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
            return;
        }

//        获取班级列表
        ArrayList<ClassModel> list_class = new ArrayList<>();//班级列表
        if (model.getList_class() != null) {
            list_class.addAll(model.getList_class());

//            if (!TextUtils.isEmpty(from) && from.equals("tab") && TextUtils.isEmpty(ClassId)) {
            if (!TextUtils.isEmpty(from) && from.equals("tab") && list_class.size() > 0) {
                ClassId = list_class.get(0).getClassId();
                Intent intent = new Intent(HonorTabFragment.UPDATE_CLASSLIST);
                intent.putParcelableArrayListExtra("classList", list_class);
                intent.putExtra("needRefreshTitle", needRefreshTitle);
                LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
            }
        }

        //放在外面(获取周的list)，因为第一次给true的时候只传回来list_date,其他list为空
        if (model.getList_date() != null) {
            //周数list的size不等于0，有周数，再次请求，默认请求第一周的，减重的
            if (model.getList_date().size() != 0) {
                if (WhichTime == 1) {
                    WhichTime = Integer.parseInt(model.getList_date().get(0).getDateValue());

                    spinnerData.clear(); //.踢馆周期
                    spinnerData.addAll(model.getList_date());

                    arrow.setSelected(0);
                    arrow.notifChange();


                    int px = DisplayUtil.dip2px(getContext(), 50 * 6);
                    arrow.setHeight(px);
                }

                //首次后设置为false
                is_first = false;
                //没有周数，第一次，全屏显示“暂无数据”return。非第一次，不return
            } else {
                if (is_first && !SortTimeType.equals("ByTotal")) {
                    parentsTitle.clear();
                    groupModelList.clear();
                    classMemberModelList.clear();
                    list_Son.clear();

                    adapter.notifyDataSetChanged();
                    listHonorrank.setEmptyView(ll_no_data);
                    return;
                }

            }

        }
        //日期列表没有时间列表，则不显示
        if (!SortTimeType.equals("ByTotal")) {
            if (spinnerData == null || spinnerData.size() == 0) {
                ll_menu.setVisibility(View.GONE);
                listHonorrank.setEmptyView(ll_no_data);
                return;
            }
        } else {
            ll_menu.setVisibility(View.GONE);
        }


        if (model.getList_group() != null && model.getList_group().size() > 0) {
            groupModelList.clear();
            groupModelList.addAll(model.getList_group());
            for (int i = 0; i < groupModelList.size(); i++) {
                ListGroupModel tempModel = groupModelList.get(i);
                tempModel.setType(1);
            }
        } else {
            groupModelList.clear();
        }

        //班级排名
        if (model.getList_all() != null && model.getList_all().size() > 0) {
            classMemberModelList.clear();
            classMemberModelList.addAll(model.getList_all());
            for (int i = 0; i < classMemberModelList.size(); i++) {
                ListGroupModel tempModel = classMemberModelList.get(i);
                tempModel.setType(2);
            }
        } else {
            classMemberModelList.clear();
        }

        if (groupModelList != null && groupModelList.size() > 0) {
            String str_group = "ByWeightRatio".equals(ByWhichRatio) ? "小组排名（本班体重变化共" + (TextUtils.isEmpty(model.getTotalLoss()) ? "--" : model.getTotalLoss()) + "斤" + " 人均" + (TextUtils.isEmpty(model.getAvgLoss()) ? "--" : model.getAvgLoss()) + "斤）" : "小组排名（本班体脂变化共" + (TextUtils.isEmpty(model.getTotalLoss()) ? "--" : model.getTotalLoss()) + "%" + "  人均" + (TextUtils.isEmpty(model.getAvgLoss()) ? "--" : model.getAvgLoss()) + "%）";
            parentsTitle.add(str_group);
            list_Son.add(groupModelList);
        } else {
            parentsTitle.add("小组排名");
            ListGroupModel groupModel = new ListGroupModel();
            groupModel.setType(1);//小组排名没数据的项目
            groupModel.setUserId("nodata");
            groupModelList.add(groupModel);
            list_Son.add(groupModelList);
        }

        if (classMemberModelList != null && classMemberModelList.size() > 0) {
            parentsTitle.add("班级排名");
            list_Son.add(classMemberModelList);
        } else {
            parentsTitle.add("班级排名");
            ListGroupModel clsModel = new ListGroupModel();
            clsModel.setType(2);//班级排名没数据的项目
            clsModel.setUserId("nodata");
            classMemberModelList.add(clsModel);
            list_Son.add(classMemberModelList);
        }
        adapter = null;
        adapter = new HonorAdapter(getContext(), parentsTitle, list_Son, ByWhichRatio);
        listHonorrank.getRefreshableView().setAdapter(adapter);
        for (int i = 0; i < parentsTitle.size(); i++) {
            listHonorrank.getRefreshableView().expandGroup(i);
        }
    }

    @Override
    public void hidenLoading() {
        listHonorrank.onRefreshComplete();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
    }

    public static final String UPDATE_HONOR_VIEW = "UPDATE_HONOR_VIEW";

    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction().equalsIgnoreCase(UPDATE_HONOR_VIEW)) {
                ClassId = intent.getStringExtra("classId");
                try {
                    listHonorrank.setRefreshing();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

}
