package com.softtek.lai.module.bodygame3.activity.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.bodygame3.activity.model.FcStDataModel;
import com.softtek.lai.module.bodygame3.activity.view.FormData;
import com.softtek.lai.module.bodygame3.head.model.MeasuredDetailsModel;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by lareina.qiao on 1/4/2017.
 */
public class FuceCheckExpandableListAdapter implements ExpandableListAdapter {
    Context context;
    String filest, images;
    int isWhatePic; //????

    int firststatus; // ????
    int IsEdit;
    private String[] groupArray = new String[]{"group1", "group2", "group3", "group4"};
    private List<List<String>> childArray;
    private MeasuredDetailsModel fcStDataModel;


    public FuceCheckExpandableListAdapter(Context context, List<List<String>> childArray, MeasuredDetailsModel fcStDataModel, int IsEdit) {
        this.context = context;
        this.childArray = childArray;
        this.fcStDataModel = fcStDataModel;
//        this.filest = filest;
//        this.images = images;
//        this.isWhatePic = isWhatePic;
//        this.firststatus = firststatus;
        this.IsEdit = IsEdit;

    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        return groupArray.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childArray.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupArray[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childArray.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = convertView;
        GroupHolder holder = null;
        if (view == null) {
            holder = new GroupHolder();
            view = LayoutInflater.from(context).inflate(R.layout.expandlist_group_check, null);
            holder.groupName = (TextView) view.findViewById(R.id.tv_group_name);
            holder.arrow = (ImageView) view.findViewById(R.id.arrow);
            holder.im_pic_icon = (ImageView) view.findViewById(R.id.im_pic_icon);//拍照相机（不用）
            holder.im_pic = (ImageView) view.findViewById(R.id.im_pic);
            holder.group1 = (LinearLayout) view.findViewById(R.id.group1);
            holder.group2 = (LinearLayout) view.findViewById(R.id.group2);
            holder.group3 = (LinearLayout) view.findViewById(R.id.group3);
            holder.tv_write_nick = (TextView) view.findViewById(R.id.tv_write_nick);//昵称
            holder.iv_write_head = (ImageView) view.findViewById(R.id.iv_write_head);//头像
            holder.tv_write_phone = (TextView) view.findViewById(R.id.tv_write_phone);//电话 tv_start_time, tv_end_time;
            holder.tv_start_time = (TextView) view.findViewById(R.id.tv_start_time);
            holder.tv_end_time = (TextView) view.findViewById(R.id.tv_end_time);
            holder.tv_write_class = (TextView) view.findViewById(R.id.tv_write_class);

            holder.im_state = (ImageView) view.findViewById(R.id.im_audit_states); // 初始录入状态  （如 已通过）
            holder.im_right5 = (ImageView) view.findViewById(R.id.im_right5); //拍照上传后面的 箭头
//            holder.tv_retest_write_weekth = (TextView) view.findViewById(R.id.tv_retest_write_weekth);//第几周
            view.setTag(holder);
        } else {
            holder = (GroupHolder) view.getTag();
        }
        switch (groupPosition) {
            case 0:
                holder.group1.setVisibility(View.VISIBLE);
                holder.group2.setVisibility(View.GONE);
                holder.group3.setVisibility(View.GONE);
                holder.tv_write_nick.setText(fcStDataModel.getUserName());
                holder.tv_write_phone.setText(fcStDataModel.getMobile());
                holder.tv_write_class.setText("所属班级：" + fcStDataModel.getClassName());
                holder.tv_start_time.setText(fcStDataModel.getStartDate());
                holder.tv_end_time.setText(fcStDataModel.getEndDate());

                if (!TextUtils.isEmpty(fcStDataModel.getPhoto())) {
                    Picasso.with(context).load(AddressManager.get("photoHost") + fcStDataModel.getPhoto()).placeholder(R.drawable.img_default).centerCrop()
                            .fit().into(holder.iv_write_head);
                } else {
                    Picasso.with(context).load(R.drawable.img_default).centerCrop()
                            .fit().into(holder.iv_write_head);
                }
//                if (childArray.get(groupPosition).size() == 3) {
//                    holder.tv_retest_write_weekth.setVisibility(View.GONE);
//                } else {
//                    FormData formData = new FormData();
//                    if (TextUtils.isEmpty(formData.formdata(fcStDataModel.getWeekNum()))) {
//                        holder.tv_retest_write_weekth.setVisibility(View.GONE);
//                    } else {
//                        holder.tv_retest_write_weekth.setText("(第" + formData.formdata(fcStDataModel.getWeekNum()) + "周)");
//                    }
//                }
                switch (firststatus) {
                    case 1:
                        //未录入
                        holder.im_state.setImageResource(R.drawable.nocomit_fc_icon);
                        break;
                    case 2:
                        //待审核
                        holder.im_state.setImageResource(R.drawable.reseted);
                        break;
                    case 3:
                        //审核通过
                        holder.im_state.setImageResource(R.drawable.passed
                        );
                        break;
                }
                break;
            case 1:
                holder.group2.setVisibility(View.VISIBLE);
                holder.group1.setVisibility(View.GONE);
                holder.group3.setVisibility(View.GONE);
                if (IsEdit != 1) {
                    holder.im_right5.setVisibility(View.INVISIBLE);
                }

                if (!TextUtils.isEmpty(fcStDataModel.getThumbnail())) {
                    Picasso.with(context).load(AddressManager.get("photoHost") + fcStDataModel.getThumbnail()).centerCrop().fit().placeholder(R.drawable.default_icon_square).into(holder.im_pic);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).centerCrop()
                            .fit().into(holder.im_pic);
                }
//                switch (isWhatePic)  ////0没有图片1网络图片2文件图片
//                {
//                    case 0:
//                        //不存在图片
//                        holder.im_pic_icon.setVisibility(View.VISIBLE);
//                        holder.im_pic.setVisibility(View.GONE);
//                        break;
//                    case 1:
//                        holder.im_pic_icon.setVisibility(View.GONE);
//                        holder.im_pic.setVisibility(View.VISIBLE);
//                        Picasso.with(context).load(AddressManager.get("photoHost") + images).centerCrop().fit().placeholder(R.drawable.default_icon_square).into(holder.im_pic);
//                        break;
//                    case 2:
//                        holder.im_pic_icon.setVisibility(View.GONE);
//                        holder.im_pic.setVisibility(View.VISIBLE);
//                        Picasso.with(context).load(new File(filest)).centerCrop().fit().placeholder(R.drawable.default_icon_square).into(holder.im_pic);
//                        break;
//                }
                break;
            case 2:
                holder.group2.setVisibility(View.GONE);
                holder.group1.setVisibility(View.GONE);
                holder.group3.setVisibility(View.GONE);
                break;
            case 3:
                holder.group2.setVisibility(View.GONE);
                holder.group1.setVisibility(View.GONE);
                holder.group3.setVisibility(View.VISIBLE);
                //判断是否已经打开列表
                if (isExpanded) {
                    holder.arrow.setBackgroundResource(R.drawable.arrow_up_icon);
                } else {
                    holder.arrow.setBackgroundResource(R.drawable.arrow_down_icon);
                }
                break;
        }

        return view;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = convertView;
        ChildHolder holder = null;
        if (view == null) {
            holder = new ChildHolder();
            view = LayoutInflater.from(context).inflate(R.layout.expandlist_item, null);
            holder.childName = (TextView) view.findViewById(R.id.tv_child_name);
            holder.tv_value = (TextView) view.findViewById(R.id.tv_value);
            holder.im_aciton = (ImageView) view.findViewById(R.id.im_aciton);
            holder.re_body = (RelativeLayout) view.findViewById(R.id.re_body);
            holder.tv_danwei = (TextView) view.findViewById(R.id.tv_danwei);
            view.setTag(holder);
        } else {
            holder = (ChildHolder) view.getTag();
        }
        if (IsEdit == 1) {
            holder.im_aciton.setBackgroundResource(R.drawable.action_right);
        }
        switch (groupPosition) {
            case 0:
                if (childArray.get(groupPosition).size() == 3) {
                    switch (childPosition) {
                        case 0:
                            holder.tv_value.setText("0.0".equals(fcStDataModel.getInitWeight()) ? "" : fcStDataModel.getInitWeight());
                            holder.tv_danwei.setText("斤");
                            break;
                        case 1:
                            holder.tv_value.setText("0.0".equals(fcStDataModel.getWeight()) ? "" : fcStDataModel.getWeight());
                            holder.tv_danwei.setText("斤");
                            break;
                        case 2:
                            holder.tv_value.setText("0.0".equals(fcStDataModel.getPysical()) ? "" : fcStDataModel.getPysical()); //体脂
                            holder.tv_danwei.setText("%");
                            break;
                        case 3:
                            holder.tv_value.setText("0.0".equals(fcStDataModel.getFat()) ? "" : fcStDataModel.getFat());
                            holder.tv_danwei.setText("    ");
                            break;
                    }
                } else {
                    switch (childPosition) {
                        case 0:
                            holder.tv_value.setText("0.0".equals(fcStDataModel.getInitWeight()) ? "" : fcStDataModel.getInitWeight());
                            holder.tv_danwei.setText("斤");
                            break;
                        case 1:
                            holder.tv_value.setText("0.0".equals(fcStDataModel.getWeight()) ? "" : fcStDataModel.getWeight());
                            holder.tv_danwei.setText("斤");
                            break;
                        case 2:
                            holder.tv_value.setText("0.0".equals(fcStDataModel.getPysical()) ? "" : fcStDataModel.getPysical());
                            holder.tv_danwei.setText("%");
                            break;
                        case 3:
                            holder.tv_value.setText("0.0".equals(fcStDataModel.getFat()) ? "" : fcStDataModel.getFat());
                            holder.tv_danwei.setText("    ");
                            break;
                    }
                }
                break;
            case 3:
                switch (childPosition) {
                    case 0:
                        holder.tv_value.setText(fcStDataModel.getCircum());
                        holder.tv_danwei.setText("cm");

                        break;
                    case 1:
                        holder.tv_value.setText(fcStDataModel.getWaistline());
                        holder.tv_danwei.setText("cm");

                        break;
                    case 2:
                        holder.tv_value.setText(fcStDataModel.getHiplie());
                        holder.tv_danwei.setText("cm");

                        break;
                    case 3:
                        holder.tv_value.setText(fcStDataModel.getUpArmGirth());
                        holder.tv_danwei.setText("cm");

                        break;
                    case 4:
                        holder.tv_value.setText(fcStDataModel.getUpLegGirth());
                        holder.tv_danwei.setText("cm");

                        break;
                    case 5:
                        holder.tv_value.setText(fcStDataModel.getDoLegGirth());
                        holder.tv_danwei.setText("cm");
                        break;
                    case 6: //BMI
                        holder.tv_value.setText(fcStDataModel.getBmi());
                        holder.tv_danwei.setText("kg/m2");
                        break;
                    case 7://去脂体重
                        holder.tv_value.setText(fcStDataModel.getFatFreeMass());
                        holder.tv_danwei.setText("kg");
                        break;
//                    case 8://内脏脂肪指数
//                        holder.tv_value.setText(fcStDataModel.getViscusFatIndex());
//                        holder.tv_danwei.setText("");
//                        break;
                    case 8://身体水分率
                        holder.tv_value.setText(fcStDataModel.getBodyWaterRate());
                        holder.tv_danwei.setText("%");
                        break;
                    case 9: //身体水分
                        holder.tv_value.setText(fcStDataModel.getBodyWater());
                        holder.tv_danwei.setText("kg");
                        break;
                    case 10: //肌肉量
                        holder.tv_value.setText(fcStDataModel.getMuscleMass());
                        holder.tv_danwei.setText("kg");
                        break;
                    case 11: //骨量
                        holder.tv_value.setText(fcStDataModel.getBoneMass());
                        holder.tv_danwei.setText("kg");
                        break;
                    case 12: //基础代谢
                        holder.tv_value.setText(fcStDataModel.getBasalMetabolism());
                        holder.tv_danwei.setText("kcal/day");
                        break;
                    case 13: //身体年龄
                        holder.tv_value.setText(fcStDataModel.getPhysicalAge());
                        holder.tv_danwei.setText("");
                        break;

                }
                break;
        }

//
        holder.childName.setText(childArray.get(groupPosition).get(childPosition));
//
        return view;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }


    private TextView getTextView() {
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 64);
        TextView textView = new TextView(context);
        textView.setLayoutParams(lp);
        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        textView.setPadding(36, 0, 0, 0);
        textView.setTextSize(20);
        return textView;
    }

    class GroupHolder {
        public TextView groupName;
        public TextView tv_write_nick, tv_write_phone, tv_write_class, tv_start_time, tv_end_time;//昵称 电话 班级  班级开始时间 结束时间
        public ImageView arrow, im_pic_icon, im_pic, iv_write_head, //拍照审核照片   、  用户头像
                im_state, im_right5; // 审核状态
        public LinearLayout group1;
        public LinearLayout group2;
        public LinearLayout group3;
    }

    class ChildHolder {
        public TextView childName;
        public TextView tv_value;
        public TextView tv_danwei;
        public ImageView im_aciton;
        public RelativeLayout re_body;
    }

}
