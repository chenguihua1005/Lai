package com.softtek.lai.module.bodygame3.head.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.softtek.lai.R;
import com.softtek.lai.module.bodygame3.head.model.ClassMemberModel;
import com.softtek.lai.module.bodygame3.head.model.ListGroupModel;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by jessica.zhang on 6/16/2017.
 */

public class HonorAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> parents = new ArrayList<String>();

    private List<ListGroupModel> groupModelList = new ArrayList<ListGroupModel>();
    private List<ClassMemberModel> classMemberModelList = new ArrayList<ClassMemberModel>();
    private String ByWhichRatio = "";

    public HonorAdapter(Context context, List<String> titiesList, List<ListGroupModel> groupModelList, List<ClassMemberModel> classMemberModelList, String ByWhichRatio) {
        this.context = context;
        this.parents = titiesList;
        this.groupModelList = groupModelList;
        this.classMemberModelList = classMemberModelList;
        this.ByWhichRatio = ByWhichRatio;
    }

    @Override
    public int getGroupCount() {
        return parents.size();
    }

    @Override
    public int getChildrenCount(int i) {
        int count = 0;
        if (i == 0) {
            count = groupModelList.size();
        } else if (i == 1) {
            count = classMemberModelList.size();
        }
        return count;
    }

    @Override
    public Object getGroup(int i) {
        if (i == 0) {
            return groupModelList;
        } else if (i == 1) {
            return classMemberModelList;
        }
        return null;
    }

    //获取父项的某个子项
    @Override
    public Object getChild(int i, int i1) {
        if (i == 0) {
            return groupModelList.get(i1);
        } else if (i == 1) {
            return classMemberModelList.get(i1);
        }
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int parentPos, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.expandable_parent_item, viewGroup, false);
        }

        if (parentPos < parents.size()) {
            TextView textView = (TextView) view.findViewById(R.id.group_name);
            textView.setText(parents.get(parentPos));
        }
        return view;
    }

    @Override
    public View getChildView(int parentPos, int childPos, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            if (parentPos == 0) {
                view = LayoutInflater.from(context).inflate(R.layout.item_honor_group, viewGroup, false);
            } else if (parentPos == 1) {
                view = LayoutInflater.from(context).inflate(R.layout.classrank_item, viewGroup, false);
            }
        }

        if (parentPos == 0) {
            final ListGroupModel groupModel = groupModelList.get(childPos);
            Log.i("WeekHonorFragment", "parentPos = " + parentPos + "当前子项数据 = " + new Gson().toJson(groupModel));
//            if (TextUtils.isEmpty(groupModel.getRanking())) {
//                holder.getConvertView().setVisibility(View.GONE);
//                return;
//            }
            TextView tv_coach_type = (TextView) view.findViewById(R.id.tv_coach_type);
            tv_coach_type.setText(groupModel.getCoachType());
            TextView tv_rank_number = (TextView) view.findViewById(R.id.tv_rank_number);
            tv_rank_number.setText(groupModel.getRanking());
            TextView tv_group_name = (TextView) view.findViewById(R.id.tv_group_name);
            //返回的是“xx组”，这里只要“xx”。但是返回的应该是小组名，我要加组字
//                String substring = groupModel.getGroupName().substring(0, groupModel.getGroupName().toCharArray().length - 1);

            //减重、脂
            TextView loss_total_tv = (TextView) view.findViewById(R.id.loss_total_tv);
            loss_total_tv.setText("ByWeightRatio".equals(ByWhichRatio) ? "减重" + groupModel.getLoss() + "斤" : "减脂" + groupModel.getLoss() + "%");

            tv_group_name.setText(groupModel.getGroupName());
            CircleImageView civ_trainer_header = (CircleImageView) view.findViewById(R.id.civ_trainer_header);
            setImage(civ_trainer_header, groupModel.getCoachIco());
//                Log.e("curry", "convert: " + groupModel.getCoachIco());
            TextView tv_trainer_name = (TextView) view.findViewById(R.id.tv_trainer_name);
            tv_trainer_name.setText(groupModel.getCoachName());
            TextView tv_per_number = (TextView) view.findViewById(R.id.tv_per_number);
            if (TextUtils.isEmpty(groupModel.getLossPer())) {
                tv_per_number.setText("--");
            } else {
                tv_per_number.setText(groupModel.getLossPer());
            }
            TextView tv_by_which = (TextView) view.findViewById(R.id.tv_by_which);
            tv_by_which.setText("ByWeightRatio".equals(ByWhichRatio) ? context.getString(R.string.weight_per) : context.getString(R.string.fat_per));

        } else if (parentPos == 1) {
            final ClassMemberModel data = classMemberModelList.get(childPos);
            Log.i("WeekHonorFragment", "parentPos = " + parentPos + "当前子项数据 = " + new Gson().toJson(data));

            CircleImageView civ = (CircleImageView) view.findViewById(R.id.head_img);
            Picasso.with(context).load(AddressManager.get("photoHost") + data.getUserIconUrl())
                    .fit().error(R.drawable.img_default)
                    .placeholder(R.drawable.img_default).into(civ);
            ImageView role_img = (ImageView) view.findViewById(R.id.role_img);
//                role_img.setVisibility("1".equals(data.getIsRetire()) ? View.VISIBLE : View.GONE);


            TextView paiming = (TextView) view.findViewById(R.id.paiming);
            paiming.setText(data.getRanking());
            TextView name_tv = (TextView) view.findViewById(R.id.name_tv);
            name_tv.setText(data.getUserName());
            ImageView fale = (ImageView) view.findViewById(R.id.fale);
            if (data.getGender() == 1) {
                fale.setImageResource(R.drawable.female_iv);
            } else if (data.getGender() == 0) {
                fale.setImageResource(R.drawable.male_iv);
            }


            TextView group_tv = (TextView) view.findViewById(R.id.group_tv);
            group_tv.setText("(" + data.getCGName() + ")");
            TextView weight_first = (TextView) view.findViewById(R.id.weight_first);
            if ("ByWeightRatio".equals(ByWhichRatio)) {
                weight_first.setText("初始" + data.getInitWeight() + "斤.减重：" + data.getLoss() + "斤");
            } else {
                weight_first.setText("初始" + data.getInitWeight() + ".减重：" + data.getLoss());
            }

            TextView tv_bi = (TextView) view.findViewById(R.id.tv_bi);
            TextView jianzhong_tv = (TextView) view.findViewById(R.id.jianzhong_tv);
            TextView jianzhong_tv2 = (TextView) view.findViewById(R.id.jianzhong_tv2);
            if ("ByWeightRatio".equals(ByWhichRatio)) {
                tv_bi.setText("减重比");
                jianzhong_tv.setText(data.getLossPer());
            } else {
                tv_bi.setText("减脂比");
                jianzhong_tv.setText(data.getLossPer());
            }
        }


        return view;
    }

    //子项是否可选中，如果需要设置子项的点击事件，需要返回true
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }


    private void setImage(CircleImageView civ, String endUrl) {
        String basePath = AddressManager.get("photoHost");
        if (StringUtils.isNotEmpty(endUrl)) {
            Picasso.with(context).load(basePath + endUrl).fit().placeholder(R.drawable.img_default).error(R.drawable.img_default).into(civ);
        } else {
            Picasso.with(context).load(R.drawable.img_default).into(civ);
        }
    }
}
