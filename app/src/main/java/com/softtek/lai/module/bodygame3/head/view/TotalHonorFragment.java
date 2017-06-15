package com.softtek.lai.module.bodygame3.head.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.UserInfoModel;
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


    private LinearLayout linear_showMenu;//顶部横条
    private TextView group_info_tv;//小组排名总信息


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
        newAdapter();
//        ListView refreshableView = listHonorrank.getRefreshableView();
//        View view = LayoutInflater.from(getContext()).inflate(R.layout.honnor_rank_head, null);
//        linear_showMenu = (LinearLayout) view.findViewById(R.id.linear_showMenu);
//
//        group_info_tv = (TextView) view.findViewById(R.id.group_info_tv);
//
//
//        refreshableView.addHeaderView(view);
//        listHonorrank.setAdapter(honorGroupRankAdapter);
        listHonorrank.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listHonorrank.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                weekHonorManager.getWeekHonnorInfo(UID, ClassId, ByWhichRatio, SortTimeType, WhichTime, true);
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

    private void newAdapter() {
        honorGroupRankAdapter = new EasyAdapter<ListGroupModel>(getContext(), groupModelList, R.layout.item_honor_group) {
            @Override
            public void convert(ViewHolder holder, ListGroupModel data, int position) {
                if (TextUtils.isEmpty(data.getRanking())) {
                    holder.getConvertView().setVisibility(View.GONE);
                    return;
                }
                TextView tv_coach_type = holder.getView(R.id.tv_coach_type);
                tv_coach_type.setText(data.getCoachType());
                TextView tv_rank_number = holder.getView(R.id.tv_rank_number);
                tv_rank_number.setText(data.getRanking());
                TextView tv_group_name = holder.getView(R.id.tv_group_name);
                //返回的是“xx组”，这里只要“xx”。但是返回的应该是小组名，我要加组字
//                String substring = data.getGroupName().substring(0, data.getGroupName().toCharArray().length - 1);
                tv_group_name.setText(data.getGroupName());

                //减重、脂
                TextView loss_total_tv = holder.getView(R.id.loss_total_tv);
                loss_total_tv.setText("ByWeightRatio".equals(ByWhichRatio) ? "减重" + data.getLoss() + "斤" : "减脂" + data.getLoss() + "%");

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

            //前三名状态重置
//            setTop1Wating();
//            setTop2Wating();
//            setTop3Wating();

            //请求不到数据的时候全屏显示“暂无数据”
            if (model == null) {
                groupModelList.clear();
                newAdapter();
                listHonorrank.setAdapter(honorGroupRankAdapter);
                listHonorrank.setEmptyView(ll_no_data);
                return;
            }

//            if (model.getList_top3() != null && model.getList_top3().size() > 0) {
                //list中显示减脂还是减重
//                for (ListTopModel topModel : model.getList_top3()) {
//                    switch (topModel.getRanking()) {
//                        case "1":
//                            tv_top1_name.setText(topModel.getUserName());
//                            tv_top1_per.setText("ByWeightRatio".equals(ByWhichRatio) ? getString(R.string.lose_weight) + topModel.getLossPer() + "%" : getString(R.string.lose_fat) + topModel.getLossPer() + "%");
//                            tv_top1_jianzhong.setText("ByWeightRatio".equals(ByWhichRatio) ? getString(R.string.lose_weight_ratio) + topModel.getLoss() + "斤" : getString(R.string.lose_fat_ratio) + topModel.getLoss() + "%");
//                            setImage(civ_top1, topModel.getUserIconUrl());
//                            break;
//                        case "2":
//                            tv_top2_name.setText(topModel.getUserName());
//                            tv_top2_per.setText("ByWeightRatio".equals(ByWhichRatio) ? getString(R.string.lose_weight) + topModel.getLossPer() + "%" : getString(R.string.lose_fat) + topModel.getLossPer() + "%");
//                            tv_top2_jianzhong.setText("ByWeightRatio".equals(ByWhichRatio) ? getString(R.string.lose_weight_ratio) + topModel.getLoss() + "斤" : getString(R.string.lose_fat_ratio) + topModel.getLoss() + "%");
//                            setImage(civ_top2, topModel.getUserIconUrl());
//                            break;
//                        case "3":
//                            tv_top3_name.setText(topModel.getUserName());
//                            tv_top3_per.setText("ByWeightRatio".equals(ByWhichRatio) ? getString(R.string.lose_weight) + topModel.getLossPer() + "%" : getString(R.string.lose_fat) + topModel.getLossPer() + "%");
//                            tv_top3_jianzhong.setText("ByWeightRatio".equals(ByWhichRatio) ? getString(R.string.lose_weight_ratio) + topModel.getLoss() + "斤" : getString(R.string.lose_fat_ratio) + topModel.getLoss() + "%");
//                            setImage(civ_top3, topModel.getUserIconUrl());
//                            break;
//                    }
//                }
//            }


            if (model.getList_group() != null && model.getList_group().size() > 0) {
                //更新list数据
                groupModelList.clear();
                groupModelList.addAll(model.getList_group());
                newAdapter();
                listHonorrank.setAdapter(honorGroupRankAdapter);
            } else {
                groupModelList.clear();
                groupModelList.add(new ListGroupModel());
                honorGroupRankAdapter.notifyDataSetChanged();
            }

            group_info_tv.setText("ByWeightRatio".equals(ByWhichRatio) ? "本班共减重" + (TextUtils.isEmpty(model.getTotalLoss()) ? "--" : model.getTotalLoss()) + "斤" + " 人均减重" + (TextUtils.isEmpty(model.getAvgLoss()) ? "--" : model.getAvgLoss()) + "斤" : "本班共减脂" + (TextUtils.isEmpty(model.getTotalLoss()) ? "--" : model.getTotalLoss()) + "%" + "  人均减脂" + (TextUtils.isEmpty(model.getAvgLoss()) ? "--" : model.getAvgLoss()) + "%");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setImage(CircleImageView civ, String endUrl) {
        String basePath = AddressManager.get("photoHost");
        if (StringUtils.isNotEmpty(endUrl)) {
            Picasso.with(getContext()).load(basePath + endUrl).fit().placeholder(R.drawable.img_default).error(R.drawable.img_default).into(civ);
        } else {
            Picasso.with(getContext()).load(R.drawable.img_default).into(civ);
        }
    }

//    private void setTop1Wating() {
//        civ_top1.setImageResource(R.drawable.img_default);
//        tv_top1_name.setText("");
//        tv_top1_per.setText(R.string.waiting);
//        tv_top1_jianzhong.setText("");
//    }
//
//    private void setTop2Wating() {
//        civ_top2.setImageResource(R.drawable.img_default);
//        tv_top2_name.setText("");
//        tv_top2_per.setText(R.string.waiting);
//        tv_top2_jianzhong.setText("");
//    }
//
//    private void setTop3Wating() {
//        civ_top3.setImageResource(R.drawable.img_default);
//        tv_top3_name.setText("");
//        tv_top3_per.setText(R.string.waiting);
//        tv_top3_jianzhong.setText("");
//    }

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


