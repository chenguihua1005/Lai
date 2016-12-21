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
    private CircleImageView civ_top1;
    private CircleImageView civ_top2;
    private CircleImageView civ_top3;
    private TextView tv_top1_name;
    private TextView tv_top2_name;
    private TextView tv_top3_name;
    private TextView tv_top1_per;
    private TextView tv_top2_per;
    private TextView tv_top3_per;
    private HonorRankModel honorRankModel;


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
        ListView refreshableView = listHonorrank.getRefreshableView();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.head_honnor_rank_month, null);
        civ_top1 = (CircleImageView) view.findViewById(R.id.civ_top1);
        civ_top2 = (CircleImageView) view.findViewById(R.id.civ_top2);
        civ_top3 = (CircleImageView) view.findViewById(R.id.civ_top3);
        tv_top1_name = (TextView) view.findViewById(R.id.tv_top1_name);
        tv_top2_name = (TextView) view.findViewById(R.id.tv_top2_name);
        tv_top3_name = (TextView) view.findViewById(R.id.tv_top3_name);
        tv_top1_per = (TextView) view.findViewById(R.id.tv_top1_per);
        tv_top2_per = (TextView) view.findViewById(R.id.tv_top2_per);
        tv_top3_per = (TextView) view.findViewById(R.id.tv_top3_per);

        refreshableView.addHeaderView(view);
        listHonorrank.setAdapter(honorGroupRankAdapter);
        listHonorrank.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listHonorrank.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                loadData(true);
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
//                    intent.putExtra("WhichTime", WhichTime);
                    if (honorRankModel != null && honorRankModel.getList_group() != null && honorRankModel.getList_group().size() != 0) {
                        intent.putExtra("GroupId", honorRankModel.getList_group().get(i - 2).getGroupId());
                        intent.putExtra("ListGroupModel", honorRankModel.getList_group().get(i - 2));
                    }
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
        if (StringUtils.isEmpty(token)) {

        } else {
            weekHonorManager = new WeekHonorManager(this);
        }
        loadData(true);
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
                //返回的是“xx组”，这里只要“xx”。但是返回的应该是小组名，我要加组字
//                String substring = data.getGroupName().substring(0, data.getGroupName().toCharArray().length - 1);
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

            //不为null，list数据为零，显示“虚位以待”
            if (model.getList_top3() == null || model.getList_top3().size() == 0) {
                setTop1Wating();
                setTop2Wating();
                setTop3Wating();
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
                //只有第一名时，剩下两个显示虚位以待
                if (model.getList_top3().size() == 1) {
                    setTop2Wating();
                    setTop3Wating();
                }
                //只有前两名时，剩下一个显示虚位以待
                if (model.getList_top3().size() == 2) {
                    setTop3Wating();
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

    private void setTop1Wating() {
        civ_top1.setImageResource(R.drawable.img_default);
        tv_top1_name.setText("");
        tv_top1_per.setText(R.string.waiting);
    }

    private void setTop2Wating() {
        civ_top2.setImageResource(R.drawable.img_default);
        tv_top2_name.setText("");
        tv_top2_per.setText(R.string.waiting);
    }

    private void setTop3Wating() {
        civ_top3.setImageResource(R.drawable.img_default);
        tv_top3_name.setText("");
        tv_top3_per.setText(R.string.waiting);
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


