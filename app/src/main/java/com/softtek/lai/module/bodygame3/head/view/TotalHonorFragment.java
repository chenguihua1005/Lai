package com.softtek.lai.module.bodygame3.head.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.head.adapter.HonorAdapter;
import com.softtek.lai.module.bodygame3.head.model.HonorRankModel;
import com.softtek.lai.module.bodygame3.head.model.ListGroupModel;
import com.softtek.lai.module.bodygame3.head.presenter.HonorPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import zilla.libcore.ui.InjectLayout;


/**
 * Created by lareina.qiao on 11/25/2016.
 */
@InjectLayout(R.layout.fragment_totalhonor)
public class TotalHonorFragment extends LazyBaseFragment<HonorPresenter> implements HonorPresenter.HonorView {
    private static final String TAG = "TotalHonorFragment";

    private String ByWhichRatio = "ByWeightRatio";
    private String ClassId = "C4E8E179-FD99-4955-8BF9-CF470898788B";
    private String SortTimeType = "ByTotal";
    private Long UID = 333L;
    private int WhichTime = 1;

    @InjectView(R.id.list_honorrank)
    PullToRefreshExpandableListView listHonorrank;//列表
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


    private HonorAdapter adapter;
    private List<ListGroupModel> groupModelList = new ArrayList<>();
    private List<ListGroupModel> classMemberModelList = new ArrayList<>();
    private List<List<ListGroupModel>> list_Son = new ArrayList<>();// 子层数据

    private List<String> parentsTitle = new ArrayList<>();


    public static TotalHonorFragment getInstance(String classId) {
        TotalHonorFragment fragment = new TotalHonorFragment();
        Bundle data = new Bundle();
        data.putString("classId", classId);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    protected void initViews() {
        Bundle bundle = getArguments();
        ClassId = bundle.getString("classId");
        selectWeight();

        setPresenter(new HonorPresenter(this));

        listHonorrank.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        adapter = new HonorAdapter(getContext(), parentsTitle, list_Son, ByWhichRatio);
        listHonorrank.getRefreshableView().setAdapter(adapter);
        listHonorrank.getRefreshableView().setEmptyView(ll_no_data);
        listHonorrank.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ExpandableListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                Log.i(TAG, " 第一次加载。。。。。");
                getPresenter().getHonorData(UID, ClassId, ByWhichRatio, SortTimeType, WhichTime, true);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {

            }
        });


        listHonorrank.getRefreshableView().setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int parentPos, int childPos, long l) {
                ListGroupModel groupModel = list_Son.get(parentPos).get(childPos);
                if (!"nodata".equals(groupModel.getUserId())) {
                    if (1 == groupModel.getType()) {
                        Intent intent = new Intent(getContext(), GroupRankingActivity.class);
                        intent.putExtra("ClassId", ClassId);
                        //ByWhichRatio排序类型；ByFatRatio：按减脂排序ByWeightRatio：按减重比排序
                        intent.putExtra("ByWhichRatio", ByWhichRatio);
                        //SortTimeType按什么时间拍训；
                        intent.putExtra("SortTimeType", SortTimeType);
                        intent.putExtra("ListGroupModel", groupModel);
                        startActivity(intent);
                    } else if (2 == groupModel.getType()) {//getPresenter().doGetClassMemberInfo(AccountId, classid);
                        Intent intent = new Intent(getContext(), PersonDetailActivity2.class);
                        intent.putExtra("AccountId", Long.parseLong(groupModel.getUserId()));
                        startActivity(intent);
                    }
                }

                return true;
            }
        });
        listHonorrank.getRefreshableView().setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {

                return groupPosition==0?true:false;
            }
        });
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void lazyLoad() {
        UID = UserInfoModel.getInstance().getUserId();
        loadData();
    }

    private void loadData() {
        try {
            listHonorrank.setRefreshing();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @OnClick({R.id.ll_weight_per, R.id.ll_fat_per})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_weight_per:
                ByWhichRatio = "ByWeightRatio";
                loadData();
                selectWeight();
                break;
            case R.id.ll_fat_per:
                ByWhichRatio = "ByFatRatio";
                loadData();
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
        int count = 0;//计算次奥组排名和班级排名，当都不存在的时候，不显示列表
        try {

            parentsTitle.clear();
            groupModelList.clear();
            classMemberModelList.clear();
            list_Son.clear();


            //请求不到数据的时候全屏显示“暂无数据”
            if (model == null) {
                adapter = null;
                adapter = new HonorAdapter(getContext(), parentsTitle, list_Son, ByWhichRatio);
                listHonorrank.getRefreshableView().setAdapter(adapter);
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
                String str_group = "ByWeightRatio".equals(ByWhichRatio) ? "小组排名（本班共减重" + (TextUtils.isEmpty(model.getTotalLoss()) ? "--" : model.getTotalLoss()) + "斤" + " 人均减重" + (TextUtils.isEmpty(model.getAvgLoss()) ? "--" : model.getAvgLoss()) + "斤)" : "小组排名（本班共减脂" + (TextUtils.isEmpty(model.getTotalLoss()) ? "--" : model.getTotalLoss()) + "%" + "  人均减脂" + (TextUtils.isEmpty(model.getAvgLoss()) ? "--" : model.getAvgLoss()) + "%）";
                parentsTitle.add(str_group);
                list_Son.add(groupModelList);

                count++;
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
                count++;
            } else {
                parentsTitle.add("班级排名");
                ListGroupModel clsModel = new ListGroupModel();
                clsModel.setType(2);//班级排名没数据的项目
                clsModel.setUserId("nodata");
                classMemberModelList.add(clsModel);
                list_Son.add(classMemberModelList);
            }


            if (count > 0) {
                adapter = null;
                adapter = new HonorAdapter(getContext(), parentsTitle, list_Son, ByWhichRatio);
                listHonorrank.getRefreshableView().setAdapter(adapter);
                for (int i = 0; i < parentsTitle.size(); i++) {
                    listHonorrank.getRefreshableView().expandGroup(i);
                }
            } else {

                parentsTitle.clear();
                groupModelList.clear();
                classMemberModelList.clear();
                list_Son.clear();
                adapter = new HonorAdapter(getContext(), parentsTitle, list_Son, ByWhichRatio);
                listHonorrank.getRefreshableView().setAdapter(adapter);
                listHonorrank.setEmptyView(ll_no_data);

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


