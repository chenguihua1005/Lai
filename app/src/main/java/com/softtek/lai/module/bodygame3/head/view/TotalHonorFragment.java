package com.softtek.lai.module.bodygame3.head.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.head.adapter.HonorAdapter;
import com.softtek.lai.module.bodygame3.head.model.HonorRankModel;
import com.softtek.lai.module.bodygame3.head.model.ListGroupModel;
import com.softtek.lai.module.bodygame3.head.model.ListTopModel;
import com.softtek.lai.module.bodygame3.head.presenter.WeekHonorManager;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;


/**
 * Created by lareina.qiao on 11/25/2016.
 */
@InjectLayout(R.layout.fragment_totalhonor)
public class TotalHonorFragment extends LazyBaseFragment implements WeekHonorManager.WeekHonnorCallback {

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


    //    EasyAdapter<ListGroupModel> honorGroupRankAdapter;
    private WeekHonorManager weekHonorManager;


    private LinearLayout linear_showMenu;//顶部横条
    private TextView group_info_tv;//小组排名总信息
    private HonorAdapter adapter;
    private List<ListGroupModel> groupModelList = new ArrayList<>();//ListGroupModel
    private List<ListGroupModel> classMemberModelList = new ArrayList<ListGroupModel>();
    private List<List<ListGroupModel>> list_Son = new ArrayList<>();// 子层数据

    private List<String> parentsTitle = new ArrayList<String>();


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

        listHonorrank.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        adapter = new HonorAdapter(getContext(), parentsTitle, groupModelList, classMemberModelList, list_Son, ByWhichRatio);
        listHonorrank.getRefreshableView().setAdapter(adapter);
        listHonorrank.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ExpandableListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                weekHonorManager.getWeekHonnorInfo(UID, ClassId, ByWhichRatio, SortTimeType, WhichTime, true);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {

            }
        });


        listHonorrank.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (i != 1) {
                    Intent intent = new Intent(getContext(), GroupRankingActivity.class);
                    //classid
                    intent.putExtra("ClassId", ClassId);
                    //ByWhichRatio排序类型；ByFatRatio：按减脂排序ByWeightRatio：按减重比排序
                    intent.putExtra("ByWhichRatio", ByWhichRatio);
                    //SortTimeType按什么时间拍训；
                    intent.putExtra("SortTimeType", SortTimeType);
                    ListGroupModel model = groupModelList.get(i - 2);
                    intent.putExtra("ListGroupModel", model);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void lazyLoad() {
        String token = UserInfoModel.getInstance().getToken();
        UID = UserInfoModel.getInstance().getUserId();
        weekHonorManager = new WeekHonorManager(this);
        loadData(false);
    }

    private void loadData(boolean is_first) {
        try {
            listHonorrank.setRefreshing();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void getModel(HonorRankModel model) {
        try {
            listHonorrank.onRefreshComplete();
            parentsTitle.clear();
            groupModelList.clear();
            classMemberModelList.clear();
            list_Son.clear();


            //请求不到数据的时候全屏显示“暂无数据”
            if (model == null) {
                listHonorrank.setEmptyView(ll_no_data);
                adapter.notifyDataSetChanged();
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
                String str_group = "小组排名 ByWeightRatio".equals(ByWhichRatio) ? "本班共减重" + (TextUtils.isEmpty(model.getTotalLoss()) ? "--" : model.getTotalLoss()) + "斤" + " 人均减重" + (TextUtils.isEmpty(model.getAvgLoss()) ? "--" : model.getAvgLoss()) + "斤" : "本班共减脂" + (TextUtils.isEmpty(model.getTotalLoss()) ? "--" : model.getTotalLoss()) + "%" + "  人均减脂" + (TextUtils.isEmpty(model.getAvgLoss()) ? "--" : model.getAvgLoss()) + "%";
                parentsTitle.add(str_group);
                list_Son.add(groupModelList);
            }

            if (classMemberModelList != null && classMemberModelList.size() > 0) {
                parentsTitle.add("班级排名");
                list_Son.add(classMemberModelList);
            }


            adapter = null;
            adapter = new HonorAdapter(getContext(), parentsTitle, groupModelList, classMemberModelList, list_Son, ByWhichRatio);
            listHonorrank.getRefreshableView().setAdapter(adapter);
            for (int i = 0; i < parentsTitle.size(); i++) {
//                listHonorrank.getRefreshableView().expandGroup(i);
                listHonorrank.getRefreshableView().expandGroup(i);
            }


        } catch (Exception e) {
            e.printStackTrace();
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
}


