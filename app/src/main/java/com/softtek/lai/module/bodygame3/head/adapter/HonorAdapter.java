package com.softtek.lai.module.bodygame3.head.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
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

public class HonorAdapter extends BaseExpandableListAdapter {//BaseExpandableListAdapter

    private static final String TAG = "HonorAdapter";
    private Context context;
    private List<String> parents = new ArrayList<String>();

    //    private List<ListGroupModel> groupModelList = new ArrayList<ListGroupModel>(); //ClassMemberModel
    private List<ListGroupModel> groupModelList = new ArrayList<ListGroupModel>();

    private List<ListGroupModel> classMemberModelList = new ArrayList<ListGroupModel>();
    private List<List<ListGroupModel>> son_List = new ArrayList<>();

    private String ByWhichRatio = "";

    private final int TYPE_1 = 1;//小组
    private final int TYPE_2 = 2;//班级

    public HonorAdapter(Context context, List<String> titiesList, List<ListGroupModel> groupModelList, List<ListGroupModel> classMemberModelList, List<List<ListGroupModel>> son_List, String ByWhichRatio) {
        this.context = context;
        this.parents = titiesList;
        this.groupModelList = groupModelList;
        this.classMemberModelList = classMemberModelList;
        this.son_List = son_List;
        this.ByWhichRatio = ByWhichRatio;
    }

    @Override
    public int getGroupCount() {
        return parents != null ? parents.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        //规避框架自动调用不合法参数的错误
        if (groupPosition >= parents.size()) {
            return 0;
        }
        return son_List != null ? son_List.get(groupPosition).size() : 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        if (parents != null && groupPosition < parents.size()) {
            return parents.get(groupPosition);
        } else {
            return null;
        }

    }

    //获取父项的某个子项
    @Override
    public Object getChild(int groupPosition, int childPosition) {//int groupPosition, int childPosition
        return son_List.get(groupPosition).get(childPosition);
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
    public int getChildTypeCount() {
        return 3;
    }

    @Override
    public int getChildType(int groupPosition, int childPosition) {
        int type = son_List.get(groupPosition).get(childPosition).getType();
        if (type == 1) {
            return TYPE_1;
        } else {
            return TYPE_2;
        }
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int parentPos, boolean b, View view, ViewGroup viewGroup) {
        final ViewHolderFather viewHolderFather;
        if (view == null) {
//            view = LayoutInflater.from(context).inflate(R.layout.expandable_parent_item, viewGroup, false);
            view = LayoutInflater.from(context).inflate(R.layout.expandable_parent_item, null);
            viewHolderFather = new ViewHolderFather();
            viewHolderFather.tvFather = (TextView) view.findViewById(R.id.group_name);
            view.setTag(viewHolderFather);
        } else {
            viewHolderFather = (ViewHolderFather) view.getTag();
        }

        if (parentPos < parents.size()) {
            viewHolderFather.tvFather.setText(parents.get(parentPos));
        }

        return view;
    }

    @Override
    public View getChildView(int parentPos, int childPos, boolean b, View view, ViewGroup viewGroup) {
        int type = getChildType(parentPos, childPos);
        if (type == 1) {
            ViewHolderSon1 viewHolderSon1;
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.item_honor_group, null);
                viewHolderSon1 = new ViewHolderSon1();

                viewHolderSon1.tv_coach_type = (TextView) view.findViewById(R.id.tv_coach_type);
                viewHolderSon1.tv_rank_number = (TextView) view.findViewById(R.id.tv_rank_number);
                viewHolderSon1.tv_group_name = (TextView) view.findViewById(R.id.tv_group_name);
                viewHolderSon1.loss_total_tv = (TextView) view.findViewById(R.id.loss_total_tv);
                viewHolderSon1.civ_trainer_header = (CircleImageView) view.findViewById(R.id.civ_trainer_header);
                viewHolderSon1.tv_trainer_name = (TextView) view.findViewById(R.id.tv_trainer_name);
                viewHolderSon1.tv_per_number = (TextView) view.findViewById(R.id.tv_per_number);
                viewHolderSon1.tv_by_which = (TextView) view.findViewById(R.id.tv_by_which);
                view.setTag(viewHolderSon1);
            } else {
                viewHolderSon1 = (ViewHolderSon1) view.getTag();
            }

//            final ListGroupModel groupModel = groupModelList.get(childPos);
            ListGroupModel groupModel = son_List.get(parentPos).get(childPos);

            viewHolderSon1.tv_coach_type.setText(groupModel.getCoachType());
            viewHolderSon1.tv_rank_number.setText(groupModel.getRanking());
            //减重、脂
            viewHolderSon1.loss_total_tv.setText("ByWeightRatio".equals(ByWhichRatio) ? "减重" + groupModel.getLoss() + "斤" : "减脂" + groupModel.getLoss() + "%");

            setImage(viewHolderSon1.civ_trainer_header, groupModel.getCoachIco());
            viewHolderSon1.tv_trainer_name.setText(groupModel.getCoachName());

            viewHolderSon1.tv_group_name.setText(groupModel.getGroupName());
            if (TextUtils.isEmpty(groupModel.getLossPer())) {
                viewHolderSon1.tv_per_number.setText("--");
            } else {
                viewHolderSon1.tv_per_number.setText(groupModel.getLossPer());
            }
            viewHolderSon1.tv_by_which.setText("ByWeightRatio".equals(ByWhichRatio) ? context.getString(R.string.weight_per) : context.getString(R.string.fat_per));

        } else if (type == 2) {

            ListGroupModel data = son_List.get(parentPos).get(childPos);
            ViewHolderSon2 viewHolderSon2;
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.classrank_item, null);
                viewHolderSon2 = new ViewHolderSon2();

                viewHolderSon2.civ = (CircleImageView) view.findViewById(R.id.head_img);
                viewHolderSon2.role_img = (TextView) view.findViewById(R.id.role_img);

                viewHolderSon2.paiming = (TextView) view.findViewById(R.id.paiming);
                viewHolderSon2.name_tv = (TextView) view.findViewById(R.id.name_tv);
                viewHolderSon2.fale = (ImageView) view.findViewById(R.id.fale);

                viewHolderSon2.group_tv = (TextView) view.findViewById(R.id.group_tv);
                viewHolderSon2.weight_first = (TextView) view.findViewById(R.id.weight_first);

                viewHolderSon2.tv_bi = (TextView) view.findViewById(R.id.tv_bi);
                viewHolderSon2.jianzhong_tv = (TextView) view.findViewById(R.id.jianzhong_tv);
                viewHolderSon2.jianzhong_tv2 = (TextView) view.findViewById(R.id.jianzhong_tv2);
                view.setTag(viewHolderSon2);

            } else {
                viewHolderSon2 = (ViewHolderSon2) view.getTag();
            }


            Log.i(TAG, "sonList = " + new Gson().toJson(data));

            Picasso.with(context).load(AddressManager.get("photoHost") + data.getUserIconUrl())
                    .fit().error(R.drawable.img_default)
                    .placeholder(R.drawable.img_default).into(viewHolderSon2.civ);
            switch (data.getClassRole()) {
                case 1:
                    viewHolderSon2.role_img.setBackgroundResource(R.drawable.bg_circle_hornor);
                    viewHolderSon2.role_img.setText("总");
                    break;
                case 2:
                    viewHolderSon2.role_img.setBackgroundResource(R.drawable.bg_circle_hornor);
                    viewHolderSon2.role_img.setText("教");
                    break;
                case 3:
                    viewHolderSon2.role_img.setBackgroundResource(R.drawable.bg_circle_hornor);
                    viewHolderSon2.role_img.setText("助");
                    break;
                case 4:
                    viewHolderSon2.role_img.setBackground(null);
                    viewHolderSon2.role_img.setText("");
                    break;
                default:
                    break;

            }


            switch (data.getRanking()) {
                case "1":
                    Drawable drawable = context.getResources().getDrawable(R.drawable.firstranking);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
                    viewHolderSon2.paiming.setText("");
                    viewHolderSon2.paiming.setCompoundDrawables(null, null, drawable, null);//画在右边
                    break;
                case "2":
                    Drawable drawable2 = context.getResources().getDrawable(R.drawable.secondranking);
                    drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight()); //设置边界
                    viewHolderSon2.paiming.setText("");
                    viewHolderSon2.paiming.setCompoundDrawables(null, null, drawable2, null);//画在右边
                    break;
                case "3":
                    Drawable drawable3 = context.getResources().getDrawable(R.drawable.thirdranking);
                    drawable3.setBounds(0, 0, drawable3.getMinimumWidth(), drawable3.getMinimumHeight()); //设置边界
                    viewHolderSon2.paiming.setText("");
                    viewHolderSon2.paiming.setCompoundDrawables(null, null, drawable3, null);//画在右边
                    break;
                default:
                    viewHolderSon2.paiming.setText(data.getRanking());
                    viewHolderSon2.paiming.setCompoundDrawables(null, null, null, null);//画在右边
                    break;
            }


            viewHolderSon2.name_tv.setText(data.getUserName());
            if (data.getGender() == 1) {
                viewHolderSon2.fale.setImageResource(R.drawable.female_iv);
            } else if (data.getGender() == 0) {
                viewHolderSon2.fale.setImageResource(R.drawable.male_iv);
            }


            viewHolderSon2.group_tv.setText("(" + data.getCGName() + ")");
            if ("ByWeightRatio".equals(ByWhichRatio)) {
//                String str = "初始:" + data.getInitWeight() + "斤.减重:" + data.getLoss() + "斤";
                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append("初始:");

                SpannableString str1 = new SpannableString(data.getInitWeight());
                str1.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.yellow)), 0, str1.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                builder.append(str1);
                builder.append("斤.减重:");

                SpannableString str2 = new SpannableString(data.getLoss());
                str2.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.yellow)), 0, str2.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                builder.append(str2);

                builder.append("斤");
                viewHolderSon2.weight_first.setText(builder);

            } else {
//                String str = "初始:" + data.getInitWeight() + "斤.减重:" + data.getLoss() + "斤";
                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append("初始:");

                SpannableString str1 = new SpannableString(data.getInitWeight());
                str1.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.yellow)), 0, str1.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                builder.append(str1);
                builder.append("%.减脂:");

                SpannableString str2 = new SpannableString(data.getLoss());
                str2.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.yellow)), 0, str2.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                builder.append(str2);

                builder.append("%");
                viewHolderSon2.weight_first.setText(builder);
            }


            if ("ByWeightRatio".equals(ByWhichRatio)) {
                viewHolderSon2.tv_bi.setText("减重比");
                viewHolderSon2.jianzhong_tv.setText(data.getLossPer());
            } else {
                viewHolderSon2.tv_bi.setText("减脂比");
                viewHolderSon2.jianzhong_tv.setText(data.getLossPer());
            }


        }

        return view;
    }

    //子项是否可选中，如果需要设置子项的点击事件，需要返回true
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    private class ViewHolderFather {
        TextView tvFather;
    }

    private class ViewHolderSon1 {
        TextView tv_coach_type;
        TextView tv_rank_number;
        TextView tv_group_name;
        TextView loss_total_tv;
        CircleImageView civ_trainer_header;
        TextView tv_trainer_name;
        TextView tv_per_number;
        TextView tv_by_which;
    }

    private class ViewHolderSon2 {
        CircleImageView civ;
        TextView role_img;
        TextView paiming;
        TextView name_tv;
        ImageView fale;
        TextView group_tv;
        TextView weight_first;
        TextView tv_bi;
        TextView jianzhong_tv;
        TextView jianzhong_tv2;

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
