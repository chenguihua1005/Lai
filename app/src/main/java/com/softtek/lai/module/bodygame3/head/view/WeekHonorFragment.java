package com.softtek.lai.module.bodygame3.head.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
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
import com.softtek.lai.module.bodygame3.head.model.HonorRankModel;
import com.softtek.lai.module.bodygame3.head.model.ListGroupModel;
import com.softtek.lai.module.bodygame3.head.model.ListdateModel;
import com.softtek.lai.module.bodygame3.head.presenter.HonorPresenter;
import com.softtek.lai.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by lareina.qiao on 11/24/2016.
 */
@InjectLayout(R.layout.fragment_weekhonor)
public class WeekHonorFragment extends LazyBaseFragment<HonorPresenter> implements HonorPresenter.HonorView {
    private static final String TAG = "WeekHonorFragment";


    private String ByWhichRatio = "ByWeightRatio";
    private String ClassId = "C4E8E179-FD99-4955-8BF9-CF470898788B";
    private String SortTimeType = "ByWeek";
    private Long UID = 333L;
    private int WhichTime = 1;
    private boolean is_first = true;

    @InjectView(R.id.list_honorrank)
    PullToRefreshExpandableListView listHonorrank;//列表  PullToRefreshExpandableListView
    @InjectView(R.id.ll_weight_per)
    LinearLayout ll_weight_per;
    @InjectView(R.id.ll_fat_per)
    LinearLayout ll_fat_per;
    @InjectView(R.id.iv_weight_per)
    ImageView iv_weight_per;
    @InjectView(R.id.iv_fat_per)
    ImageView iv_fat_per;
    @InjectView(R.id.tv_weight_per)
    TextView tv_weight_per;
    @InjectView(R.id.tv_fat_per)
    TextView tv_fat_per;
    @InjectView(R.id.ll_no_data)
    LinearLayout ll_no_data;
    @InjectView(R.id.ll_menu)
    LinearLayout ll_menu;


    @InjectView(R.id.arrow_spinner)
    ListDialogHonor arrow;

    private HonorAdapter adapter;
    private List<ListGroupModel> groupModelList = new ArrayList<>();//ListGroupModel
    private List<ListGroupModel> classMemberModelList = new ArrayList<ListGroupModel>();
    private List<List<ListGroupModel>> list_Son = new ArrayList<>();// 子层数据

    private List<String> parentsTitle = new ArrayList<String>();

    List<ListdateModel> spinnerData = new ArrayList<>();
    private int selectedSpinner = 0;

    public static WeekHonorFragment getInstance(String classId) {
        WeekHonorFragment fragment = new WeekHonorFragment();
        Bundle data = new Bundle();
        data.putString("classId", classId);
        fragment.setArguments(data);
        return fragment;
    }

    /**
     * 每次切换都会执行
     */
    @Override
    protected void initViews() {
        setPresenter(new HonorPresenter(this));
        Bundle bundle = getArguments();     //提交的话取消注释
        ClassId = bundle.getString("classId");
        selectWeight();

//        ListView refreshableView = listHonorrank.getRefreshableView();
//        View view = LayoutInflater.from(getContext()).inflate(R.layout.honnor_rank_head, null);
//        linear_showMenu = (LinearLayout) view.findViewById(R.id.linear_showMenu);
//        arrow = (ListDialog) view.findViewById(R.id.arrow_spinner);
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
                WhichTime = Integer.parseInt(((ListdateModel) spinnerData.get(i)).getDateValue());
                String dateName = ((ListdateModel) spinnerData.get(i)).getDateName();
                arrow.setText(dateName);
                WhichTime = Integer.parseInt(((ListdateModel) spinnerData.get(i)).getDateValue());
                loadData(is_first);
            }
        });


        //加上就先显示空的头部，这种效果不要。、
        //注释掉后第一种情况可以解决;第二种应该是因为没有给list一条空数据所以没有显示空头部；第三种情况依然正确。
        // 所以给list一条空数据就应该显示空的头部了


        listHonorrank.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

        adapter = new HonorAdapter(getContext(), parentsTitle, groupModelList, classMemberModelList, list_Son, ByWhichRatio);
        listHonorrank.getRefreshableView().setAdapter(adapter);
        listHonorrank.getRefreshableView().setEmptyView(ll_no_data);
        listHonorrank.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ExpandableListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
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
                        ListdateModel listdateModel = spinnerData.get(selectedSpinner);
                        intent.putExtra("listDataModel", listdateModel);
//                    ListGroupModel model = groupModelList.get(i - 2);
                        intent.putExtra("ListGroupModel", groupModel);
                        startActivity(intent);
                    } else if (2 == groupModel.getType()) {//getPresenter().doGetClassMemberInfo(AccountId, classid);
                        Intent intent = new Intent(getContext(), PersonDetailActivity2.class);
                        intent.putExtra("AccountId", Long.parseLong(groupModel.getUserId()));
                        startActivity(intent);
                    }

                return true;
            }
        });

    }


    /**
     * 每次切换都会执行
     */
    @Override
    protected void initDatas() {

    }

    /**
     * 请求数据，每次切换到时候也会执行(切换fragment的时候，数据应该不会丢失，所以请求一次周数就行了)
     * 确定只执行一次
     */
    @Override
    protected void lazyLoad() {
        UID = UserInfoModel.getInstance().getUserId();
        loadData(is_first);
    }


    private void loadData(final boolean is_first) {
        //第一次请求会请求两次，第一次不让显示刷新效果，所以不用setrefreshing()。
        // 以后的请求都是一次一次来的，要有刷新效果，所以都用setRefreshing()调用这个方法后，会自动调用他的刷新方法，网络请求在刷新方法里。。
        if (is_first) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getPresenter().getHonorData(UID, ClassId, ByWhichRatio, SortTimeType, WhichTime, is_first);
                }
            }, 500);

        } else {
            try {
                listHonorrank.setRefreshing();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    @OnClick({R.id.ll_weight_per, R.id.ll_fat_per})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_weight_per:
                ByWhichRatio = "ByWeightRatio";
                loadData(false);
                selectWeight();
                break;
            case R.id.ll_fat_per:
                ByWhichRatio = "ByFatRatio";
                loadData(false);
                selectFat();
                break;
        }
    }

    private void selectWeight() {
        iv_weight_per.setImageResource(R.drawable.weight_per_select);
        iv_fat_per.setImageResource(R.drawable.fat_per_unselect);
        tv_weight_per.setTextColor(getResources().getColor(R.color.orange));
        tv_fat_per.setTextColor(getResources().getColor(R.color.grey_honor));
    }

    private void selectFat() {
        iv_weight_per.setImageResource(R.drawable.weight_per_unselect);
        iv_fat_per.setImageResource(R.drawable.fat_per_select);
        tv_weight_per.setTextColor(getResources().getColor(R.color.grey_honor));
        tv_fat_per.setTextColor(getResources().getColor(R.color.orange));
    }


    @Override
    public void getHonorModel(HonorRankModel model) {
        Log.i(TAG, "UID= " + UID + "classId = " + ClassId + "byWhichRatio = " + ByWhichRatio + " sortTimeType= " + SortTimeType + "  whichTime = " + WhichTime);
        Log.i(TAG, "获取到的数据 = " + new Gson().toJson(model));

//        listHonorrank.onRefreshComplete();
        ll_menu.setVisibility(View.VISIBLE);
        parentsTitle.clear();
        groupModelList.clear();
        classMemberModelList.clear();
        list_Son.clear();
        try {
            //请求不到数据的时候全屏显示“暂无数据”
            if (model == null) {
                ll_menu.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
                return;
            }
            //放在外面(获取周的list)，因为第一次给true的时候只传回来list_date,其他list为空
            if (model.getList_date() != null) {
                //周数list的size不等于0，有周数，再次请求，默认请求第一周的，减重的
                if (model.getList_date().size() != 0) {
                    WhichTime = Integer.parseInt(model.getList_date().get(0).getDateValue());

                    spinnerData.clear(); //.踢馆周期
                    spinnerData.addAll(model.getList_date());

                    arrow.setSelected(0);
                    arrow.notifChange();

                    int px = DisplayUtil.dip2px(getContext(), 50 * 6);
                    arrow.setHeight(px);

                    //首次后设置为false
                    is_first = false;
                    //没有周数，第一次，全屏显示“暂无数据”return。非第一次，不return
                } else {
                    if (is_first) {
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
            if (spinnerData == null || spinnerData.size() == 0) {
                ll_menu.setVisibility(View.GONE);
                listHonorrank.setEmptyView(ll_no_data);
                return;
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

//            班级排名
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
                String str_group = "ByWeightRatio".equals(ByWhichRatio) ? "小组排名（本班共减重" + (TextUtils.isEmpty(model.getTotalLoss()) ? "--" : model.getTotalLoss()) + "斤" + " 人均减重" + (TextUtils.isEmpty(model.getAvgLoss()) ? "--" : model.getAvgLoss()) + "斤）" : "小组排名（本班共减脂" + (TextUtils.isEmpty(model.getTotalLoss()) ? "--" : model.getTotalLoss()) + "%" + "  人均减脂" + (TextUtils.isEmpty(model.getAvgLoss()) ? "--" : model.getAvgLoss()) + "%）";
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
            adapter = new HonorAdapter(getContext(), parentsTitle, groupModelList, classMemberModelList, list_Son, ByWhichRatio);
            listHonorrank.getRefreshableView().setAdapter(adapter);
            for (int i = 0; i < parentsTitle.size(); i++) {
                listHonorrank.getRefreshableView().expandGroup(i);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hidenLoading() {
        listHonorrank.onRefreshComplete();
    }
}
