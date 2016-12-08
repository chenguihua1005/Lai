package com.softtek.lai.module.bodygame3.head.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.ggx.widgets.nicespinner.ArrowSpinner2;
import com.ggx.widgets.nicespinner.ArrowSpinnerAdapter;
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
 * Created by lareina.qiao on 11/24/2016.
 */
@InjectLayout(R.layout.fragment_weekhonor)
public class WeekHonorFragment extends LazyBaseFragment implements WeekHonorManager.WeekHonnorCallback {

    private String ByWhichRatio = "ByWeightRatio";
    private String ClassId = "C4E8E179-FD99-4955-8BF9-CF470898788B";
    private String SortTimeType = "ByWeek";
    private Long UID = 333L;
    private int WhichTime = 7;

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
    private ArrowSpinner2 spinner;

    public static WeekHonorFragment getInstance() {
        WeekHonorFragment fragment = new WeekHonorFragment();
        Bundle data = new Bundle();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    protected void initViews() {
        honorGroupRankAdapter = new EasyAdapter<ListGroupModel>(getContext(), groupModelList, R.layout.item_honor_group) {
            @Override
            public void convert(ViewHolder holder, ListGroupModel data, int position) {
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
                tv_by_which.setText("ByWeightRatio".equals(ByWhichRatio) ? "人均减重比" : "人均减脂比");
            }
        };
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
        spinner = (ArrowSpinner2) view.findViewById(R.id.spinner);

        refreshableView.addHeaderView(view);
        listHonorrank.setAdapter(honorGroupRankAdapter);
        listHonorrank.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listHonorrank.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                weekHonorManager.getWeekHonnorInfo(UID, ClassId, ByWhichRatio, SortTimeType, 7, false);
            }
        });

        listHonorrank.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

    }

    @Override
    protected void initDatas() {
        final List<String> datas = new ArrayList<>();
        datas.add("第一体馆周");
        datas.add("第二体馆周");
        datas.add("第三体馆周");
        datas.add("第四体馆周");
        datas.add("第五体馆周");
        datas.add("第六体馆周");
        datas.add("第七体馆周");
        datas.add("第八体馆周");
        datas.add("第九体馆周");
        datas.add("第十体馆周");
        datas.add("第十一体馆周");
        datas.add("第十二体馆周");
        List<ListView> oneObjectList = new ArrayList<>();
        oneObjectList.add(new ListView(getContext()));
//        spinner.attachCustomSource(new ArrowSpinnerAdapter<ListView>(getContext(), oneObjectList, R.layout.spinner_week_list) {
//            @Override
//            public void convert(ViewHolder holder, ListView data, int position) {
//
//
//
//                TextView tv_class_name = holder.getView(R.id.tv_classed);
//                tv_class_name.setText(data);
//            }
//
//            @Override
//            public String getText(int position) {
//                //根据position返回当前值给标题
//                return datas.get(position);
//            }
//
//        });

        final EasyAdapter<String> adapter = new EasyAdapter<String>(getContext(), datas, R.layout.class_title) {

            @Override
            public void convert(ViewHolder holder, String data, int position) {
                TextView tv_classed = holder.getView(R.id.tv_classed);
                tv_classed.setText(data);
            }
        };
        spinner.attachCustomSource(new ArrowSpinnerAdapter<ListView>(getContext(), oneObjectList, R.layout.spinner_week_list) {
            @Override
            public void convert(ViewHolder holder, ListView listViews, int position) {
                ListView listView = holder.getView(R.id.listView);
                listView.setAdapter(adapter);
            }

            @Override
            public String getText(int position) {
                return null;
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                WhichTime = i + 1;
                lazyLoad();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected void lazyLoad() {

        String token = UserInfoModel.getInstance().getToken();
        if (StringUtils.isEmpty(token)) {

        } else {
            weekHonorManager = new WeekHonorManager(this);
        }
        weekHonorManager.getWeekHonnorInfo(UID, ClassId, ByWhichRatio, SortTimeType, WhichTime, false);
        listHonorrank.setRefreshing();
    }


    @Override
    public void getModel(HonorRankModel model) {
        listHonorrank.onRefreshComplete();
        groupModelList.clear();
        groupModelList.addAll(model.getList_group());
        honorGroupRankAdapter.notifyDataSetChanged();
        for (ListTopModel topModel : model.getList_top3()) {
            switch (topModel.getRanking()) {
                case "1":
                    tv_top1_name.setText(topModel.getUserName());
                    tv_top1_per.setText("ByWeightRatio".equals(ByWhichRatio) ? "减重" + topModel.getLossPer() + "%" : "减脂" + topModel.getLossPer() + "%");
                    setImage(civ_top1, topModel.getUserIconUrl());
                    break;
                case "2":
                    tv_top2_name.setText(topModel.getUserName());
                    tv_top2_per.setText("ByWeightRatio".equals(ByWhichRatio) ? "减重" + topModel.getLossPer() + "%" : "减脂" + topModel.getLossPer() + "%");
                    setImage(civ_top1, topModel.getUserIconUrl());
                    break;
                case "3":
                    tv_top3_name.setText(topModel.getLossPer());
                    tv_top3_per.setText("ByWeightRatio".equals(ByWhichRatio) ? "减重" + topModel.getLossPer() + "%" : "减脂" + topModel.getLossPer() + "%");
                    setImage(civ_top1, topModel.getUserIconUrl());
                    break;
            }
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
                lazyLoad();
                iv_weight_per.setImageResource(R.drawable.weight_per_select);
                iv_fat_per.setImageResource(R.drawable.fat_per_unselect);
                break;
            case R.id.ll_fat_per:
                ByWhichRatio = "ByFatRatio";
                lazyLoad();
                iv_weight_per.setImageResource(R.drawable.weight_per_unselect);
                iv_fat_per.setImageResource(R.drawable.fat_per_select);
                break;
        }
    }
}
