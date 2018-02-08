package com.softtek.lai.module.bodygame3.more.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.more.model.ClassGroup;
import com.softtek.lai.module.bodygame3.more.model.Member;
import com.softtek.lai.module.bodygame3.more.net.MoreService;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.utils.StringUtil;
import com.softtek.lai.widgets.BottomSheetDialog;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.util.Util;

/**
 * Created by jerry.guan on 11/19/2016.
 */

public class MemberExpandableAdapter extends BaseExpandableListAdapter {

    private Map<String, List<Member>> datas;
    private List<String> parents;
    private List<ClassGroup> groups;
    private Context context;
    private int headPX = 68;
    private String classId;
    private String classHxId;
    private TextView tv_title;
    private ProgressDialog pDialog;

    private Handler handler;

    public MemberExpandableAdapter(Context context, Map<String, List<Member>> datas, List<String> parents,
                                   String classId, String classHxId, List<ClassGroup> groups, TextView tv_title, Handler handler, ProgressDialog pDialog) {
        this.context = context;
        this.datas = datas;
        this.parents = parents;
        this.groups = groups;
        this.classId = classId;
        this.classHxId = classHxId;
        this.tv_title = tv_title;
        headPX = DisplayUtil.dip2px(context, 34);
//        pDialog = new ProgressDialog(context);
//        pDialog.setCanceledOnTouchOutside(false);
        //progressDialog.setCancelable(false);

        this.handler = handler;
        this.pDialog = pDialog;
    }

    //父项的数量
    @Override
    public int getGroupCount() {
        return datas.size();
    }

    //子项的数量
    @Override
    public int getChildrenCount(int i) {
        return datas.get(parents.get(i)).size();
    }

    //获取某个父项
    @Override
    public Object getGroup(int i) {
        return datas.get(parents.get(i));
    }

    //获取父项的某个子项
    @Override
    public Object getChild(int i, int i1) {
        return datas.get(parents.get(i)).get(i1);
    }

    //获取某个父项的ID
    @Override
    public long getGroupId(int i) {
        return i;
    }

    //获取子项的id
    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    //  按函数的名字来理解应该是是否具有稳定的id，这个方法目前一直都是返回false，没有去改动过
    @Override
    public boolean hasStableIds() {
        return false;
    }

    //  获得父项显示的view
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

    //  获得子项显示的view
    @Override
    public View getChildView(int parentPos, int childPos, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_class_member, viewGroup, false);
        }

        final Member member = datas.get(parents.get(parentPos)).get(childPos);
        //业务逻辑
        CircleImageView head_image = (CircleImageView) view.findViewById(R.id.head_image);
        if (!TextUtils.isEmpty(member.getPhoto())) {
            Picasso.with(context).load(AddressManager.get("photoHost") + member.getPhoto())
                    .placeholder(R.drawable.img_default)
                    .resize(headPX, headPX).centerCrop().error(R.drawable.img_default).into(head_image);
        } else {
            Picasso.with(context).load("111").fit().error(R.drawable.img_default).into(head_image);
        }
        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_name.setText(StringUtil.showName(member.getUserName(), member.getMobile()));
        TextView tv_group_name = (TextView) view.findViewById(R.id.tv_group_name);
        tv_group_name.setText("(");
        tv_group_name.append(member.getCGName());
        tv_group_name.append(" ");
        int role = member.getClassRole();
        tv_group_name.append(role == 1 ? "总教练" : role == 2 ? "教练" : role == 3 ? "助教" : role == 4 ? "学员" : "未知");
        tv_group_name.append(")");
        //侧滑操作
        final HorizontalScrollView hsv = (HorizontalScrollView) view.findViewById(R.id.hsv);
        TextView tv_trans_group = (TextView) view.findViewById(R.id.tv_trans_group);
        TextView tv_delete = (TextView) view.findViewById(R.id.tv_delete);
        if (member.getClassRole() == 1) {//总教练不可以被转组移除   （old :教练不可以被转组移除  ）
            tv_trans_group.setVisibility(View.VISIBLE);
            tv_delete.setVisibility(View.GONE);
        } else {
            tv_trans_group.setVisibility(View.VISIBLE);
            tv_delete.setVisibility(View.VISIBLE);
        }
            tv_trans_group.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //关闭
                    hsv.post(new Runnable() {
                        @Override
                        public void run() {
                            hsv.smoothScrollTo(0, 0);
                        }
                    });
                    chooseGroup(member);
                }
            });
            tv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hsv.post(new Runnable() {
                        @Override
                        public void run() {
                            hsv.smoothScrollTo(0, 0);
                        }
                    });
                    removeMember(member);
                }
            });
        RelativeLayout container = (RelativeLayout) view.findViewById(R.id.rl_container);
        ViewGroup.LayoutParams params = container.getLayoutParams();
        params.width = DisplayUtil.getMobileWidth(context);
        container.setLayoutParams(params);
        final LinearLayout ll_operation = (LinearLayout) view.findViewById(R.id.ll_operation);
        hsv.setOnTouchListener(new View.OnTouchListener() {
            int dx;
            int lastX;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = (int) motionEvent.getX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        dx = (int) motionEvent.getX() - lastX;
                        break;
                    case MotionEvent.ACTION_UP:
                        int width = ll_operation.getWidth() / 2;
                        boolean show = dx < 0 ? dx <= -width : !(dx >= width);
                        if (show) {
                            hsv.post(new Runnable() {
                                @Override
                                public void run() {
                                    hsv.smoothScrollTo(hsv.getMaxScrollAmount(), 0);
                                }
                            });

                        } else {
                            hsv.post(new Runnable() {
                                @Override
                                public void run() {
                                    hsv.smoothScrollTo(0, 0);
                                }
                            });

                        }
                        break;
                }
                return false;
            }
        });
        return view;
    }

    //子项是否可选中，如果需要设置子项的点击事件，需要返回true
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    private void removeMember(final Member member) {
        new AlertDialog.Builder(context)
                .setTitle("温馨提示")
                .setMessage("您确定要移除该成员?")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        pDialog.setMessage("移除成员");
                        pDialog.show();


                        ZillaApi.NormalRestAdapter.create(MoreService.class)
                                .removeFromGroup(classId, UserInfoModel.getInstance().getToken(),
                                        member.getAccountId(),
                                        classId,
                                        member.getCGId(),
                                        new Callback<ResponseData>() {
                                            @Override
                                            public void success(ResponseData responseData, Response response) {
                                                if (responseData.getStatus() == 200) {
                                                    Log.i("MemberExpandableAdapter", "移除成功。。。。。。。。。。。。");
                                                    Util.toastMsg(responseData.getMsg());
                                                    Message msg = new Message();
                                                    msg.obj = member;
                                                    msg.what = 0x0011;
                                                    handler.sendMessage(msg);
                                                    //环信移除个人

                                                } else {
                                                    Util.toastMsg(responseData.getMsg());
                                                    Log.i("MemberExpandableAdapter", "移除失败。。。。。。。。。。。。" + responseData.getMsg());
                                                    Message msg = new Message();
                                                    msg.what = 0x0012;
                                                    handler.sendMessage(msg);
                                                }
                                            }

                                            @Override
                                            public void failure(RetrofitError error) {
                                                Log.i("MemberExpandableAdapter", "移除失败。。。。。。。。。。。。" + error.toString());
                                                Message msg = new Message();
                                                msg.what = 0x0012;
                                                handler.sendMessage(msg);
                                                ZillaApi.dealNetError(error);
                                            }
                                        });



 /*                       new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
//                                    EMClient.getInstance().groupManager().removeUserFromGroup(classHxId, member.getHxAccountId());//需异步处理
                                    ZillaApi.NormalRestAdapter.create(MoreService.class)
                                            .removeFromGroup(classId, UserInfoModel.getInstance().getToken(),
                                                    member.getAccountId(),
                                                    classId,
                                                    member.getCGId(),
                                                    new Callback<ResponseData>() {
                                                        @Override
                                                        public void success(ResponseData responseData, Response response) {
                                                            if (responseData.getStatus() == 200) {

                                                                Log.i("MemberExpandableAdapter", "移除成功。。。。。。。。。。。。");

                                                                Message msg = new Message();
                                                                msg.obj = member;
                                                                msg.what = 0x0011;
                                                                handler.sendMessage(msg);
                                                                //环信移除个人
                                                                //把username从群组里删除

                                                            } else {
//                                                                Util.toastMsg(responseData.getMsg());
                                                                Log.i("MemberExpandableAdapter", "移除失败。。。。。。。。。。。。" + responseData.getMsg());
                                                                Message msg = new Message();
                                                                msg.what = 0x0012;
                                                                handler.sendMessage(msg);
                                                            }
                                                        }

                                                        @Override
                                                        public void failure(RetrofitError error) {
//                                                            Looper.prepare();
//                                                            if (pDialog != null && pDialog.isShowing()) {
//                                                                pDialog.dismiss();
//                                                            }
//                                                            Looper.loop();

                                                            Log.i("MemberExpandableAdapter", "移除失败。。。。。。。。。。。。" + error.toString());

                                                            Message msg = new Message();
                                                            msg.what = 0x0012;
                                                            handler.sendMessage(msg);
                                                            ZillaApi.dealNetError(error);
                                                        }
                                                    });


                                } catch (Exception e) {
                                    Looper.prepare();
                                    if (pDialog != null && pDialog.isShowing()) {
                                        pDialog.dismiss();
                                    }
                                    Looper.loop();
                                    e.printStackTrace();
                                    Util.toastMsg("移除失败");
                                    Message msg = new Message();
                                    msg.what = 0x0012;
                                    handler.sendMessage(msg);
                                }
                            }
                        }).start();*/


                    }
                }).show();
    }

    BottomSheetDialog dialog;

    private void chooseGroup(final Member member) {
        View view = LayoutInflater.from(context).inflate(R.layout.pop_trans_view, null);
        final ListView lv = (ListView) view.findViewById(R.id.lv);
        View footer = LayoutInflater.from(context).inflate(R.layout.trans_group_footer, null);
        lv.addFooterView(footer);
        lv.setAdapter(new EasyAdapter<ClassGroup>(context, groups, android.R.layout.simple_list_item_single_choice) {
            @Override
            public void convert(ViewHolder holder, ClassGroup data, int position) {
                CheckedTextView tv = holder.getView(android.R.id.text1);
                tv.setText(data.getCGName());
            }
        });
        lv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int select = lv.getFirstVisiblePosition();
                        if (select != 0 && select < groups.size()) {
                            lv.getParent().requestDisallowInterceptTouchEvent(true);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return false;
            }
        });
        TextView tv_ok = (TextView) footer.findViewById(R.id.tv_ok);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int select = lv.getCheckedItemPosition();
                if (select == -1 || select >= groups.size()) {
                    return;
                }
                final ClassGroup group = groups.get(lv.getCheckedItemPosition());
                if (group.getCGId().equals(member.getCGId())) {
                    Snackbar.make(tv_title, "转组成功", Snackbar.LENGTH_SHORT).setDuration(1000).show();
                } else {
                    pDialog.setMessage("转组中");
                    pDialog.show();
                    ZillaApi.NormalRestAdapter.create(MoreService.class)
                            .turnToAnotherGroup(classId,
                                    UserInfoModel.getInstance().getToken(),
                                    member.getAccountId(),
                                    classId,
                                    group.getCGId(),
                                    new RequestCallback<ResponseData>() {
                                        @Override
                                        public void success(ResponseData responseData, Response response) {
                                            if (pDialog != null && pDialog.isShowing()) {
                                                pDialog.dismiss();
                                            }
                                            if (responseData.getStatus() == 200) {
                                                datas.get(member.getCGName()).remove(member);
                                                member.setCGId(group.getCGId());
                                                member.setCGName(group.getCGName());
                                                datas.get(member.getCGName()).add(member);
                                                notifyDataSetChanged();
                                                Snackbar.make(tv_title, "转组成功", Snackbar.LENGTH_SHORT).setDuration(1000).show();
                                            } else {
                                                Snackbar.make(tv_title, "转组失败", Snackbar.LENGTH_SHORT).setDuration(1000).show();
                                            }
                                        }

                                        @Override
                                        public void failure(RetrofitError error) {
                                            if (pDialog != null && pDialog.isShowing()) {
                                                pDialog.dismiss();
                                            }
                                            super.failure(error);
                                        }
                                    }

                            );
                }
                dialog.dismiss();
            }
        });
        TextView tv_cancel = (TextView) footer.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog = new BottomSheetDialog(context);
        dialog.setContentView(view);
        dialog.show();

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                dialog = null;
            }
        });
    }
}
