package com.softtek.lai.module.bodygame3.head.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.ggx.widgets.nicespinner.ArrowSpinner4;
import com.ggx.widgets.nicespinner.ArrowSpinnerAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.head.model.HonorRankModel;
import com.softtek.lai.module.bodygame3.head.model.ListGroupModel;
import com.softtek.lai.module.bodygame3.head.model.ListTopModel;
import com.softtek.lai.module.bodygame3.head.model.ListdateModel;
import com.softtek.lai.module.bodygame3.head.presenter.WeekHonorManager;
import com.softtek.lai.utils.DisplayUtil;
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
@InjectLayout(R.layout.fragment_monthhonor)
public class MonthHonorFragment extends LazyBaseFragment implements WeekHonorManager.WeekHonnorCallback {

    private String ByWhichRatio = "ByWeightRatio";
    private String ClassId = "C4E8E179-FD99-4955-8BF9-CF470898788B";
    private String SortTimeType = "ByMonth";
    private Long UID = 333L;
    private int WhichTime = 1;

    @InjectView(R.id.list_honorrank)
    PullToRefreshListView listHonorrank;//列表
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


    EasyAdapter<ListGroupModel> honorGroupRankAdapter;
    private List<ListGroupModel> groupModelList = new ArrayList<>();
    private WeekHonorManager weekHonorManager;
    private CircleImageView civ_top1;
    private CircleImageView civ_top2;
    private CircleImageView civ_top3;
    private TextView tv_top1_name;
    private TextView tv_top2_name;
    private TextView tv_top3_name;
    private TextView tv_top1_per;
    private TextView tv_top2_per;
    private TextView tv_top3_per;
    private ArrowSpinner4 spinner;
    private HonorRankModel honorRankModel;

    List<ListdateModel> spinnerData = new ArrayList<>();
    List<String> spinnerData2 = new ArrayList<>();
    private ArrowSpinnerAdapter spinnerAdapter;
    private boolean is_first = true;


    public static MonthHonorFragment getInstance(String classId) {
        MonthHonorFragment fragment = new MonthHonorFragment();
        Bundle data = new Bundle();
        data.putString("classId", classId);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    protected void initViews() {
        Bundle bundle = getArguments();
//        ClassId = bundle.getString("classId");
        selectWeight();
        newAdapter();
        ListView refreshableView = listHonorrank.getRefreshableView();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.head_honnor_rank, null);
        civ_top1 = (CircleImageView) view.findViewById(R.id.civ_top1);
        civ_top2 = (CircleImageView) view.findViewById(R.id.civ_top2);
        civ_top3 = (CircleImageView) view.findViewById(R.id.civ_top3);
        tv_top1_name = (TextView) view.findViewById(R.id.tv_top1_name);
        tv_top2_name = (TextView) view.findViewById(R.id.tv_top2_name);
        tv_top3_name = (TextView) view.findViewById(R.id.tv_top3_name);
        tv_top1_per = (TextView) view.findViewById(R.id.tv_top1_per);
        tv_top2_per = (TextView) view.findViewById(R.id.tv_top2_per);
        tv_top3_per = (TextView) view.findViewById(R.id.tv_top3_per);
        spinner = (ArrowSpinner4) view.findViewById(R.id.spinner);

        refreshableView.addHeaderView(view);
        listHonorrank.setAdapter(honorGroupRankAdapter);

        listHonorrank.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listHonorrank.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                loadData(is_first);
            }
        });

        listHonorrank.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 1) {
                    Intent intent = new Intent(getContext(), GroupRankingActivity.class);
                    intent.putExtra("ClassId", ClassId);
                    intent.putExtra("ByWhichRatio", ByWhichRatio);
                    intent.putExtra("SortTimeType", SortTimeType);
                    intent.putExtra("WhichTime", WhichTime);
                    if (honorRankModel != null && honorRankModel.getList_group() != null && honorRankModel.getList_group().size() != 0) {
                        intent.putExtra("GroupId", honorRankModel.getList_group().get(i - 2).getGroupId());
                        intent.putExtra("ListGroupModel", honorRankModel.getList_group().get(i - 2));
                    }
                    startActivity(intent);
                }
                Log.e("curry", "onItemClick: " + i);
            }
        });

    }

    @Override
    protected void initDatas() {
        //根据position返回当前值给标题
        spinnerAdapter = new ArrowSpinnerAdapter<String>(getContext(), spinnerData2, R.layout.class_title) {
            @Override
            public void convert(ViewHolder holder, String data, int position) {
                TextView tv_class_name = holder.getView(R.id.tv_classed);
                tv_class_name.setText(data);
            }

            @Override
            public String getText(int position) {
                //根据position返回当前值给标题
                return spinnerData2 == null || spinnerData2.size() == 0 ? "" : spinnerData2.get(position);
            }

        };
        spinner.attachCustomSource(spinnerAdapter);
        spinner.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                WhichTime = i + 1;
                loadData(is_first);
            }
        });
    }

    @Override
    protected void lazyLoad() {
        String token = UserInfoModel.getInstance().getToken();
        UID = UserInfoModel.getInstance().getUserId();
        if (StringUtils.isEmpty(token)) {
        } else {
            weekHonorManager = new WeekHonorManager(this);
        }
        loadData(is_first);
    }

    private void loadData(boolean is_first) {
        listHonorrank.setRefreshing();
        weekHonorManager.getWeekHonnorInfo(UID, ClassId, ByWhichRatio, SortTimeType, WhichTime, is_first);
    }

    private void newAdapter() {
        honorGroupRankAdapter = new EasyAdapter<ListGroupModel>(getContext(), groupModelList, R.layout.item_honor_group) {
            @Override
            public void convert(ViewHolder holder, ListGroupModel data, int position) {
                if (TextUtils.isEmpty(data.getRanking())) {
                    holder.getConvertView().setVisibility(View.GONE);
                    return;
                }
                TextView tv_rank_number = holder.getView(R.id.tv_rank_number);
                tv_rank_number.setText(data.getRanking());
                TextView tv_group_name = holder.getView(R.id.tv_group_name);
                tv_group_name.setText(data.getGroupName());
                CircleImageView civ_trainer_header = holder.getView(R.id.civ_trainer_header);
                setImage(civ_trainer_header, data.getCoachIco());
//                Log.e("curry", "convert: " + data.getCoachIco());
                TextView tv_trainer_name = holder.getView(R.id.tv_trainer_name);
                tv_trainer_name.setText(data.getCoachName());
                TextView tv_per_number = holder.getView(R.id.tv_per_number);
                tv_per_number.setText(data.getLossPer());
                TextView tv_by_which = holder.getView(R.id.tv_by_which);
                tv_by_which.setText("ByWeightRatio".equals(ByWhichRatio) ? getString(R.string.weight_per) : getString(R.string.fat_per));
            }
        };
    }

    @Override
    public void getModel(HonorRankModel model) {
        try {
            listHonorrank.onRefreshComplete();
            //请求不到数据的时候全屏显示“暂无数据”
            if (model == null) {
                groupModelList.clear();
                newAdapter();
                listHonorrank.setAdapter(honorGroupRankAdapter);
                listHonorrank.setEmptyView(ll_no_data);
                return;
            }
            //放在外面(获取周的list)，因为第一次给true的时候只传回来list_date,其他list为空
            if (model.getList_date() != null) {
                //周数list的size不等于0，有周数，再次请求，默认请求第一周的，减重的
                if (model.getList_date().size() != 0) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadData(false);
                        }
                    }, 500);
                    spinnerData.clear();
                    spinnerData = model.getList_date();
    //                for (int i = spinnerData.size() - 1; i >= 0; i--) {
    //                    spinnerData2.add(spinnerData.get(i).getDateName());
    //                }
                    for (int i = 0; i < spinnerData.size(); i++) {
                        spinnerData2.add(spinnerData.get(i).getDateName());
                    }
                    spinner.attachCustomSource(spinnerAdapter);
                    //动态设置下拉框的高度
                    switch (spinnerData.size()) {
                        case 1:
                            spinner.setPop4Height(DisplayUtil.dip2px(getContext(), 38));
                            break;
                        case 2:
                            spinner.setPop4Height(DisplayUtil.dip2px(getContext(), 75));
                            break;
                    }
                    //首次后设置为false
                    is_first = false;
                    //没有周数，第一次，全屏显示“暂无数据”return。非第一次，不return
                } else {
                    if (is_first) {
                        groupModelList.clear();
                        newAdapter();
                        listHonorrank.setAdapter(honorGroupRankAdapter);
                        listHonorrank.setEmptyView(ll_no_data);
                        return;
                    }
                }

            }
            //不为null，list数据为零，显示“虚位以待”
            if (model.getList_top3() == null || model.getList_top3().size() == 0) {
                civ_top1.setImageResource(R.drawable.img_default);
                civ_top2.setImageResource(R.drawable.img_default);
                civ_top3.setImageResource(R.drawable.img_default);
                tv_top1_name.setText("");
                tv_top2_name.setText("");
                tv_top3_name.setText("");
                tv_top1_per.setText("虚位以待");
                tv_top2_per.setText("虚位以待");
                tv_top3_per.setText("虚位以待");
                groupModelList.clear();
                groupModelList.add(new ListGroupModel());
                honorGroupRankAdapter.notifyDataSetChanged();
            } else {
                honorRankModel = model;
                //更新list数据
                groupModelList.clear();
                groupModelList.addAll(model.getList_group());
                newAdapter();
                listHonorrank.setAdapter(honorGroupRankAdapter);
    //            honorGroupRankAdapter.notifyDataSetChanged();
                //list中显示减脂还是减重
                for (ListTopModel topModel : model.getList_top3()) {
                    switch (topModel.getRanking()) {
                        case "1":
                            tv_top1_name.setText(topModel.getUserName());
                            tv_top1_per.setText("ByWeightRatio".equals(ByWhichRatio) ? getString(R.string.lose_weight) + topModel.getLossPer() + "%" : getString(R.string.lose_fat) + topModel.getLossPer() + "%");
                            setImage(civ_top1, topModel.getUserIconUrl());
                            break;
                        case "2":
                            tv_top2_name.setText(topModel.getUserName());
                            tv_top2_per.setText("ByWeightRatio".equals(ByWhichRatio) ? getString(R.string.lose_weight) + topModel.getLossPer() + "%" : getString(R.string.lose_fat) + topModel.getLossPer() + "%");
                            setImage(civ_top2, topModel.getUserIconUrl());
                            break;
                        case "3":
                            tv_top3_name.setText(topModel.getUserName());
                            tv_top3_per.setText("ByWeightRatio".equals(ByWhichRatio) ? getString(R.string.lose_weight) + topModel.getLossPer() + "%" : getString(R.string.lose_fat) + topModel.getLossPer() + "%");
                            setImage(civ_top3, topModel.getUserIconUrl());
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setImage(CircleImageView civ, String endUrl) {
        String basePath = AddressManager.get("photoHost");
        if (StringUtils.isNotEmpty(endUrl)) {
            Picasso.with(getContext()).load(basePath + endUrl).into(civ);
        }
    }

    @OnClick({R.id.ll_weight_per, R.id.ll_fat_per})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_weight_per:
                ByWhichRatio = "ByWeightRatio";
                loadData(is_first);
                selectWeight();
                break;
            case R.id.ll_fat_per:
                ByWhichRatio = "ByFatRatio";
                loadData(is_first);
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

