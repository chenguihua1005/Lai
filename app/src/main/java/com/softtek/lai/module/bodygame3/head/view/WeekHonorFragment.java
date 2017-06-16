package com.softtek.lai.module.bodygame3.head.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.head.adapter.HonorAdapter;
import com.softtek.lai.module.bodygame3.head.model.ClassMemberModel;
import com.softtek.lai.module.bodygame3.head.model.HonorRankModel;
import com.softtek.lai.module.bodygame3.head.model.ListGroupModel;
import com.softtek.lai.module.bodygame3.head.model.ListdateModel;
import com.softtek.lai.module.bodygame3.head.presenter.WeekHonorManager;
import com.softtek.lai.module.laiClassroom.adapter.TabAdapter;
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
    //    @InjectView(R.id.ptfsc_no_data)
//    PullToRefreshScrollView ptfsc_no_data;
    @InjectView(R.id.ll_no_data)
    LinearLayout ll_no_data;

    private HonorAdapter adapter;
    private List<ListGroupModel> groupModelList = new ArrayList<>();
    private List<ClassMemberModel> classMemberModelList = new ArrayList<ClassMemberModel>();
    private List<String> parentsTitle = new ArrayList<String>();


    private WeekHonorManager weekHonorManager;

    private TextView group_info_tv;//小组排名总信息

    List<ListdateModel> spinnerData = new ArrayList<>();
    List<String> spinnerData2 = new ArrayList<>();
    //    private ArrowSpinnerAdapter spinnerAdapter;
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
        weekHonorManager = new WeekHonorManager(this);
        Bundle bundle = getArguments();     //提交的话取消注释
        ClassId = bundle.getString("classId");
        selectWeight();

        ListView refreshableView = listHonorrank.getRefreshableView();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.honnor_rank_head, null);
        group_info_tv = (TextView) view.findViewById(R.id.group_info_tv);
        refreshableView.addHeaderView(view);


        //加上就先显示空的头部，这种效果不要。、
        //注释掉后第一种情况可以解决;第二种应该是因为没有给list一条空数据所以没有显示空头部；第三种情况依然正确。
        // 所以给list一条空数据就应该显示空的头部了
//        listHonorrank.setAdapter(honorGroupRankAdapter);


        listHonorrank.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

        adapter = new HonorAdapter(getContext(), parentsTitle, groupModelList, classMemberModelList, ByWhichRatio);
        listHonorrank.getRefreshableView().setAdapter(adapter);
        listHonorrank.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ExpandableListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                weekHonorManager.getWeekHonnorInfo(UID, ClassId, ByWhichRatio, SortTimeType, WhichTime, is_first);
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
                    intent.putExtra("ClassId", ClassId);
                    intent.putExtra("ByWhichRatio", ByWhichRatio);
                    intent.putExtra("SortTimeType", SortTimeType);
                    ListdateModel listdateModel = spinnerData.get(selectedSpinner);
                    intent.putExtra("listDataModel", listdateModel);
                    ListGroupModel model = groupModelList.get(i - 2);
                    intent.putExtra("ListGroupModel", model);
                    startActivity(intent);
                }
            }
        });

    }

//    private void newAdapter() {
//        honorGroupRankAdapter = new EasyAdapter<ListGroupModel>(getContext(), groupModelList, R.layout.item_honor_group) {
//            @Override
//            public void convert(ViewHolder holder, ListGroupModel data, int position) {
//                if (TextUtils.isEmpty(data.getRanking())) {
//                    holder.getConvertView().setVisibility(View.GONE);
//                    return;
//                }
//                TextView tv_coach_type = holder.getView(R.id.tv_coach_type);
//                tv_coach_type.setText(data.getCoachType());
//                TextView tv_rank_number = holder.getView(R.id.tv_rank_number);
//                tv_rank_number.setText(data.getRanking());
//                TextView tv_group_name = holder.getView(R.id.tv_group_name);
//                //返回的是“xx组”，这里只要“xx”。但是返回的应该是小组名，我要加组字
////                String substring = data.getGroupName().substring(0, data.getGroupName().toCharArray().length - 1);
//
//                //减重、脂
//                TextView loss_total_tv = holder.getView(R.id.loss_total_tv);
//                loss_total_tv.setText("ByWeightRatio".equals(ByWhichRatio) ? "减重" + data.getLoss() + "斤" : "减脂" + data.getLoss() + "%");
//
//                tv_group_name.setText(data.getGroupName());
//                CircleImageView civ_trainer_header = holder.getView(R.id.civ_trainer_header);
//                setImage(civ_trainer_header, data.getCoachIco());
////                Log.e("curry", "convert: " + data.getCoachIco());
//                TextView tv_trainer_name = holder.getView(R.id.tv_trainer_name);
//                tv_trainer_name.setText(data.getCoachName());
//                TextView tv_per_number = holder.getView(R.id.tv_per_number);
//                if (TextUtils.isEmpty(data.getLossPer())) {
//                    tv_per_number.setText("--");
//                } else {
//                    tv_per_number.setText(data.getLossPer());
//                }
//                TextView tv_by_which = holder.getView(R.id.tv_by_which);
//                tv_by_which.setText("ByWeightRatio".equals(ByWhichRatio) ? getString(R.string.weight_per) : getString(R.string.fat_per));
//            }
//        };
//
//        classRankAdapter = new EasyAdapter<ClassMemberModel>(getContext(), classMemberModelList, R.layout.classrank_item) {
//            @Override
//            public void convert(ViewHolder holder, ClassMemberModel data, int position) {
//                Log.i("WeekHonorFragment", "data " + position + " = " + new Gson().toJson(data));
//                if (TextUtils.isEmpty(data.getRanking())) {
//                    holder.getConvertView().setVisibility(View.GONE);
//                    return;
//                }
//
//                CircleImageView civ = holder.getView(R.id.head_img);
//                Picasso.with(getContext()).load(AddressManager.get("photoHost") + data.getUserIconUrl())
//                        .fit().error(R.drawable.img_default)
//                        .placeholder(R.drawable.img_default).into(civ);
//                ImageView role_img = holder.getView(R.id.role_img);
////                role_img.setVisibility("1".equals(data.getIsRetire()) ? View.VISIBLE : View.GONE);
//
//
//                TextView paiming = holder.getView(R.id.paiming);
//                paiming.setText(data.getRanking());
//                TextView name_tv = holder.getView(R.id.name_tv);
//                name_tv.setText(data.getUserName());
//                ImageView fale = holder.getView(R.id.fale);
//                if (data.getGender() == 1) {
//                    fale.setImageResource(R.drawable.female_iv);
//                } else if (data.getGender() == 0) {
//                    fale.setImageResource(R.drawable.male_iv);
//                }
//
//
//                TextView group_tv = holder.getView(R.id.group_tv);
//                group_tv.setText("(" + data.getCGName() + ")");
//                TextView weight_first = holder.getView(R.id.weight_first);
//                if ("ByWeightRatio".equals(ByWhichRatio)) {
//                    weight_first.setText("初始" + data.getInitWeight() + "斤.减重：" + data.getLoss() + "斤");
//                } else {
//                    weight_first.setText("初始" + data.getInitWeight() + ".减重：" + data.getLoss());
//                }
//
//                TextView tv_bi = holder.getView(R.id.tv_bi);
//                TextView jianzhong_tv = holder.getView(R.id.jianzhong_tv);
//                TextView jianzhong_tv2 = holder.getView(R.id.jianzhong_tv2);
//                if ("ByWeightRatio".equals(ByWhichRatio)) {
//                    tv_bi.setText("减重比");
//                    jianzhong_tv.setText(data.getLossPer());
//                } else {
//                    tv_bi.setText("减脂比");
//                    jianzhong_tv.setText(data.getLossPer());
//                }
//
//            }
//        };
//    }


    /**
     * 每次切换都会执行
     */
    @Override
    protected void initDatas() {


        //根据position返回当前值给标题
//        spinnerAdapter = new ArrowSpinnerAdapter<String>(getContext(), spinnerData2, R.layout.class_title) {
//            @Override
//            public void convert(ViewHolder holder, String data, int position) {
//                TextView tv_class_name = holder.getView(R.id.tv_classed);
//                tv_class_name.setText(data);
//            }
//
//            @Override
//            public String getText(int position) {
//                //根据position返回当前值给标题
//                return spinnerData2 == null || spinnerData2.size() == 0 ? "" : spinnerData2.get(position);
//            }
//
//        };
//        spinner.attachCustomSource(spinnerAdapter);
//
//        spinner.addOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                selectedSpinner = i;
//                WhichTime = Integer.valueOf(spinnerData.get(i).getDateValue());
//                loadData(false);
//            }
//        });
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

    @Override
    public void getModel(HonorRankModel model) {
        parentsTitle.clear();
        groupModelList.clear();
        classMemberModelList.clear();
        try {
            listHonorrank.onRefreshComplete();
            //请求不到数据的时候全屏显示“暂无数据”
            if (model == null) {
//                adapter.notifyDataSetChanged();
//                groups.clear();
//                groups.addAll(temp);
//                for (int i = 0; i < parents.size(); i++) {
//                    lv.getRefreshableView().expandGroup(i);
//                }


//                parentsTitle.clear();
//                groupModelList.clear();
//                classMemberModelList.clear();
                adapter.notifyDataSetChanged();
//                listHonorrank.setEmptyView(ll_no_data);
                return;
            }
            //放在外面(获取周的list)，因为第一次给true的时候只传回来list_date,其他list为空
            if (model.getList_date() != null) {
                //周数list的size不等于0，有周数，再次请求，默认请求第一周的，减重的
                if (model.getList_date().size() != 0) {
//                    WhichTime = Integer.parseInt(model.getList_date().get(0).getDateValue());
                    WhichTime = 8;

                    spinnerData.clear(); //.踢馆周期
                    spinnerData = model.getList_date();
//                    for (int i = 0; i < spinnerData.size(); i++) {
//                        spinnerData2.add(spinnerData.get(i).getDateName());
//                    }
//                    spinner.attachCustomSource(spinnerAdapter);
//                    listHonorrank.setAdapter(honorGroupRankAdapter);
//                    //动态设置下拉框的高度
//                    switch (spinnerData.size()) {
//                        case 1:
//                            spinner.setPop4Height(DisplayUtil.dip2px(getContext(), 38));
//                            break;
//                        case 2:
//                            spinner.setPop4Height(DisplayUtil.dip2px(getContext(), 75));
//                            break;
//                    }
                    //首次后设置为false
                    is_first = false;
                    //没有周数，第一次，全屏显示“暂无数据”return。非第一次，不return
                } else {
                    if (is_first) {
                        parentsTitle.clear();
                        groupModelList.clear();
                        classMemberModelList.clear();
                        adapter.notifyDataSetChanged();
                        listHonorrank.setEmptyView(ll_no_data);
                        return;
                    }
                }

            }
            //不为null，list数据为零，显示“虚位以待”

            if (model.getList_group() != null && model.getList_group().size() > 0) {
                groupModelList.clear();
                groupModelList.addAll(model.getList_group());
            } else {
                groupModelList.clear();
            }

//            班级排名
            if (model.getList_all() != null && model.getList_all().size() > 0) {
                classMemberModelList.clear();
                classMemberModelList.addAll(model.getList_all());
            } else {
                classMemberModelList.clear();
            }

            String str_group = "小组排名 ByWeightRatio".equals(ByWhichRatio) ? "本班共减重" + (TextUtils.isEmpty(model.getTotalLoss()) ? "--" : model.getTotalLoss()) + "斤" + " 人均减重" + (TextUtils.isEmpty(model.getAvgLoss()) ? "--" : model.getAvgLoss()) + "斤" : "本班共减脂" + (TextUtils.isEmpty(model.getTotalLoss()) ? "--" : model.getTotalLoss()) + "%" + "  人均减脂" + (TextUtils.isEmpty(model.getAvgLoss()) ? "--" : model.getAvgLoss()) + "%";
            parentsTitle.add(str_group);
            parentsTitle.add("班级排名");

            Log.i("WeekHonorFragment", "parentsTitle= " + new Gson().toJson(parentsTitle));
            Log.i("WeekHonorFragment", "groupModelList= " + new Gson().toJson(groupModelList));
            Log.i("WeekHonorFragment", "classMemberModelList= " + new Gson().toJson(classMemberModelList));

            adapter.notifyDataSetChanged();
            for (int i = 0; i < parentsTitle.size(); i++) {
                listHonorrank.getRefreshableView().expandGroup(i);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadData(boolean is_first) {
        //第一次请求会请求两次，第一次不让显示刷新效果，所以不用setrefreshing()。
        // 以后的请求都是一次一次来的，要有刷新效果，所以都用setRefreshing()调用这个方法后，会自动调用他的刷新方法，网络请求在刷新方法里。。
        if (is_first) {
            weekHonorManager.getWeekHonnorInfo(UID, ClassId, ByWhichRatio, SortTimeType, WhichTime, is_first);
        } else {
            try {
                listHonorrank.setRefreshing();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void setImage(CircleImageView civ, String endUrl) {
        String basePath = AddressManager.get("photoHost");
        if (StringUtils.isNotEmpty(endUrl)) {
//            Picasso.with(getContext()).load(basePath + endUrl).into(civ);
            Picasso.with(getContext()).load(basePath + endUrl).fit().placeholder(R.drawable.img_default).error(R.drawable.img_default).into(civ);
        } else {
            Picasso.with(getContext()).load(R.drawable.img_default).into(civ);
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
