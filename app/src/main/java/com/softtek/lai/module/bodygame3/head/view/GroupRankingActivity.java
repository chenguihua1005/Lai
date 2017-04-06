package com.softtek.lai.module.bodygame3.head.view;

import android.content.Intent;
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
import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame3.head.model.HonorGroupRankModel;
import com.softtek.lai.module.bodygame3.head.model.ListGroupModel;
import com.softtek.lai.module.bodygame3.head.model.ListGroupRankingModel;
import com.softtek.lai.module.bodygame3.head.model.ListdateModel;
import com.softtek.lai.module.bodygame3.head.presenter.GroupRankingManager;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

import static com.softtek.lai.R.id.tv_init_weight;


@InjectLayout(R.layout.activity_group_ranking)
public class GroupRankingActivity extends BaseActivity implements GroupRankingManager.GroupRankingCallback {

    private String ByWhichRatio = "ByWeightRatio";
    private String ClassId = "C4E8E179-FD99-4955-8BF9-CF470898788B";
    private String SortTimeType = "ByWeek";
    private int WhichTime = 7;
    private String GroupId;


    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.list_group_ranking)
    ListView list_group_ranking;//列表
    @InjectView(R.id.tv_group_name)
    TextView tv_group_name;
    @InjectView(R.id.tv_rank_number)
    TextView tv_rank_number;
    @InjectView(R.id.civ_trainer_header)
    CircleImageView civ_trainer_header;
    @InjectView(R.id.tv_trainer_name)
    TextView tv_trainer_name;
    @InjectView(R.id.tv_per_number)
    TextView tv_per_number;
    @InjectView(R.id.tv_by_which)
    TextView tv_by_which;
    @InjectView(R.id.tv_role_name)
    TextView tv_role_name;

    @InjectView(R.id.group_total_tv)
    TextView group_total_tv;


    EasyAdapter<ListGroupRankingModel> honorGroupRankAdapter;
    private List<ListGroupRankingModel> groupRankingModelList = new ArrayList<>();

    private GroupRankingManager groupRankingManager;
    private ListGroupModel listGroupModel;
    private HonorGroupRankModel honorGroupRankModel;
    private TextView tv_ranking_date;
    private String whichName;


    @Override
    protected void initViews() {
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        honorGroupRankAdapter = new EasyAdapter<ListGroupRankingModel>(this, groupRankingModelList, R.layout.item_group_ranking) {
            @Override
            public void convert(ViewHolder holder, ListGroupRankingModel data, int position) {
                TextView tv_rank_number = holder.getView(R.id.tv_rank_number);
                tv_rank_number.setText(data.getNum());
                CircleImageView civ_trainer_header = holder.getView(R.id.civ_trainer_header);
                setImage(civ_trainer_header, data.getPhoto());
                ImageView iv_gender = holder.getView(R.id.iv_gender);
                setImage2(iv_gender, data.getGender());
                TextView tv_trainer_name = holder.getView(R.id.tv_trainer_name);
                tv_trainer_name.setText(data.getUserName());
                TextView tv_group_name = holder.getView(R.id.tv_group_name);
                tv_group_name.setText(listGroupModel.getGroupName());
                TextView tv_init = holder.getView(tv_init_weight);
                String init = TextUtils.isEmpty(data.getInitWeight()) ? "--" : data.getInitWeight();
                if ("ByFatRatio".equals(ByWhichRatio)) {
                    tv_init.setText(getString(R.string.init_fat, init));
                } else {
                    tv_init.setText(getString(R.string.init_weight, init));
                }
//                }
                TextView tv_per_number = holder.getView(R.id.tv_per_number);
                if (TextUtils.isEmpty(data.getLossPer())) {
                    tv_per_number.setText("--");
                } else {
                    tv_per_number.setText(data.getLossPer());
                }
                TextView tv_by_which = holder.getView(R.id.tv_by_which);
                tv_by_which.setText("ByWeightRatio".equals(ByWhichRatio) ? getString(R.string.lose_weight_per) : getString(R.string.lose_fat_per));

                TextView loss_tv = holder.getView(R.id.loss_tv);
                loss_tv.setText("ByWeightRatio".equals(ByWhichRatio) ? getString(R.string.lose_weight_ratio) + (TextUtils.isEmpty(data.getLoss()) ? "--" : data.getLoss()) + "斤" : getString(R.string.lose_fat_ratio) + (TextUtils.isEmpty(data.getLoss()) ? "--" : data.getLoss()) + "%");

            }
        };
        list_group_ranking.setAdapter(honorGroupRankAdapter);
        View footView = LayoutInflater.from(this).inflate(R.layout.group_ranking_footer, null);
        tv_ranking_date = (TextView) footView.findViewById(R.id.tv_ranking_date);
        list_group_ranking.addFooterView(footView);
        list_group_ranking.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //不为null&&不让点击FooterView
                if (honorGroupRankModel != null && i != honorGroupRankModel.getGrouplist().size()) {
                    Intent intent = new Intent(GroupRankingActivity.this, PersonDetailActivity2.class);
                    intent.putExtra("ClassId", ClassId);
                    intent.putExtra("comeFrom", Constants.FROM_OLD_CLASS);
                    intent.putExtra("AccountId", Long.parseLong(honorGroupRankModel.getGrouplist().get(i).getAccountId()));
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void initDatas() {
        Intent intent = getIntent();
        ClassId = intent.getStringExtra("ClassId");
        ByWhichRatio = intent.getStringExtra("ByWhichRatio");
        SortTimeType = intent.getStringExtra("SortTimeType");
        ListdateModel listdateModel = (ListdateModel) intent.getSerializableExtra("listDataModel");
        if (listdateModel != null) {
            WhichTime = Integer.parseInt(listdateModel.getDateValue());
            whichName = listdateModel.getDateName();

        }
        listGroupModel = (ListGroupModel) intent.getSerializableExtra("ListGroupModel");
        if (listGroupModel != null) {
            GroupId = listGroupModel.getGroupId();
            tv_role_name.setText(listGroupModel.getCoachType());
        }
        groupRankingManager = new GroupRankingManager(this);
        groupRankingManager.getWeekHonnorInfo(ClassId, ByWhichRatio, SortTimeType, WhichTime, GroupId);


        if (listGroupModel != null) {
            //返回的是“xx组”，这里只要“xx”。但是返回的应该是小组名，我要加组字
//                String substring = data.getGroupName().substring(0, data.getGroupName().toCharArray().length - 1);
            tv_group_name.setText(listGroupModel.getGroupName());
            if ("ByWeek".equals(SortTimeType)) {
                tv_title.setText(getString(R.string.title_ranking_group, whichName));
                tv_rank_number.setText(getString(R.string.week_ranking, listGroupModel.getRanking()));
            } else if ("ByMonth".equals(SortTimeType)) {
                tv_title.setText(getString(R.string.title_ranking_group, whichName));
                tv_rank_number.setText(getString(R.string.month_ranking, listGroupModel.getRanking()));
            } else if ("ByTotal".equals(SortTimeType)) {
                tv_title.setText(R.string.title_group_total);
            }
            setImage(civ_trainer_header, listGroupModel.getCoachIco());
//            Log.e("curry", "success: " + listGroupModel.toString());
            tv_trainer_name.setText(listGroupModel.getCoachName());
            if (TextUtils.isEmpty(listGroupModel.getLossPer())) {
                tv_per_number.setText("--");
            } else {
                tv_per_number.setText(listGroupModel.getLossPer());
            }
            tv_by_which.setText("ByWeightRatio".equals(ByWhichRatio) ? getString(R.string.weight_per) : getString(R.string.fat_per));
        }

    }

    @Override
    public void getModel(HonorGroupRankModel model) {
        if (model == null || model.getGrouplist() == null) {
            return;
        }
        honorGroupRankModel = model;
        groupRankingModelList.clear();
        groupRankingModelList.addAll(model.getGrouplist());
        honorGroupRankAdapter.notifyDataSetChanged();
        tv_ranking_date.setText(getString(R.string.ranking_date) + model.getStartDate() + "～" + model.getEndDate());

        if (group_total_tv != null) {
            group_total_tv.setText("ByWeightRatio".equals(ByWhichRatio) ? "( 总减重" + model.getTotalLoss() + "斤" + "  人均减重" + model.getAvgLoss() + "斤 )" : "( 总减脂" + model.getTotalLoss() + "%" + "  人均减脂" + model.getAvgLoss() + "%" + " )");
        }
    }

    private void setImage(CircleImageView civ, String endUrl) {
        String basePath = AddressManager.get("photoHost");
        if (StringUtils.isNotEmpty(endUrl)) {
            Picasso.with(this).load(basePath + endUrl).into(civ);
        }

        if (StringUtils.isNotEmpty(endUrl)) {
//            Picasso.with(getContext()).load(basePath + endUrl).into(civ);
            Picasso.with(this).load(basePath + endUrl).fit().placeholder(R.drawable.img_default).error(R.drawable.img_default).into(civ);
        } else {
            Picasso.with(this).load(R.drawable.img_default).into(civ);
        }
    }

    private void setImage2(ImageView iv, String flag) {
        iv.setVisibility(View.VISIBLE);
        switch (flag) {
            case "0":
                iv.setImageResource(R.drawable.male_iv);
                break;
            case "1":
                iv.setImageResource(R.drawable.female_iv);
                break;
            case "2":
                iv.setVisibility(View.INVISIBLE);
                break;
        }
    }
}
