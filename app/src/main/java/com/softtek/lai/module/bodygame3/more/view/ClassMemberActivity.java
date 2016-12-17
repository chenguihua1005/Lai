package com.softtek.lai.module.bodygame3.more.view;

import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.more.model.ClassGroup;
import com.softtek.lai.module.bodygame3.more.model.ClassMember;
import com.softtek.lai.module.bodygame3.more.model.Member;
import com.softtek.lai.module.bodygame3.more.net.MoreService;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.utils.StringUtil;
import com.softtek.lai.widgets.BottomSheetDialog;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_class_member)
public class ClassMemberActivity extends BaseActivity {

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.lv)
    ListView lv;

    EasyAdapter<Member> adapter;
    private List<Member> members = new ArrayList<>();
    private List<ClassGroup> groups;
    private String classId;
    private String classHxId;

    @Override
    protected void initViews() {
        tv_title.setText("人员管理");
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initDatas() {
        classId = getIntent().getStringExtra("classId");
        classHxId = getIntent().getStringExtra("classHxId");
        adapter = new EasyAdapter<Member>(this, members, R.layout.item_class_member) {
            @Override
            public void convert(ViewHolder holder, Member data, final int position) {
                //业务逻辑
                CircleImageView head_image = holder.getView(R.id.head_image);
                if (TextUtils.isEmpty(data.getPhoto())) {
                    Picasso.with(ClassMemberActivity.this).load(R.drawable.img_default).into(head_image);
                } else {
                    Picasso.with(ClassMemberActivity.this).load(AddressManager.get("photoHost") + data.getPhoto())
                            .fit().error(R.drawable.img_default).placeholder(R.drawable.img_default).into(head_image);
                }
                TextView tv_name = holder.getView(R.id.tv_name);
                tv_name.setText(StringUtil.showName(data.getUserName(), data.getMobile()));
                TextView tv_group_name = holder.getView(R.id.tv_group_name);
                tv_group_name.setText("(");
                tv_group_name.append(data.getCGName());
                tv_group_name.append(")");
                //侧滑操作
                final HorizontalScrollView hsv = holder.getView(R.id.hsv);
                TextView tv_trans_group = holder.getView(R.id.tv_trans_group);
                TextView tv_delete = holder.getView(R.id.tv_delete);
                if (data.getClassRole() == 2) {//教练不可以被转组移除
                    tv_trans_group.setVisibility(View.GONE);
                    tv_delete.setVisibility(View.GONE);
                } else {
                    tv_trans_group.setVisibility(View.VISIBLE);
                    tv_delete.setVisibility(View.VISIBLE);
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
                            chooseGroup(position);
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
                            removeMember(members.get(position));
                        }
                    });
                }
                RelativeLayout container = holder.getView(R.id.rl_container);
                ViewGroup.LayoutParams params = container.getLayoutParams();
                params.width = DisplayUtil.getMobileWidth(ClassMemberActivity.this);
                container.setLayoutParams(params);
                final LinearLayout ll_operation = holder.getView(R.id.ll_operation);
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

            }
        };

        lv.setAdapter(adapter);
        dialogShow("载入人员");
        ZillaApi.NormalRestAdapter.create(MoreService.class)
                .getClassesMembers(UserInfoModel.getInstance().getToken(),
                        classId,
                        new Callback<ResponseData<ClassMember>>() {
                            @Override
                            public void success(ResponseData<ClassMember> data, Response response) {
                                dialogDissmiss();
                                if (data.getStatus() == 200) {
                                    try {
                                        groups = data.getData().getGroups();
                                        members.addAll(data.getData().getMembers());
                                        adapter.notifyDataSetChanged();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Util.toastMsg(data.getMsg());
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                dialogDissmiss();
                                ZillaApi.dealNetError(error);
                            }
                        });
    }

    private void setListViewHeight(int count) {
        ViewGroup.LayoutParams params = lv.getLayoutParams();
        params.height = count * DisplayUtil.dip2px(ClassMemberActivity.this, 50)
                + (lv.getDividerHeight() * (count - 1));
        lv.setLayoutParams(params);
    }

    private void removeMember(final Member member) {
        new AlertDialog.Builder(this)
                .setTitle("温馨提示")
                .setMessage("此操作将会删除该人员改为您确定要移除该成员")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogShow("正在移除");
                        ZillaApi.NormalRestAdapter.create(MoreService.class)
                                .removeFromGroup(UserInfoModel.getInstance().getToken(),
                                        member.getAccountId(),
                                        classId,
                                        member.getCGId(),
                                        new Callback<ResponseData>() {
                                            @Override
                                            public void success(ResponseData responseData, Response response) {
                                                dialogDissmiss();
                                                if (responseData.getStatus() == 200) {
                                                    members.remove(member);
                                                    adapter.notifyDataSetChanged();
                                                    int count = adapter.getCount();
                                                    setListViewHeight(count);

                                                    //环信移除个人
                                                    //把username从群组里删除
                                                    new Thread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            try {
                                                                EMClient.getInstance().groupManager().removeUserFromGroup(classHxId, member.getHxAccountId());//需异步处理
                                                            } catch (HyphenateException e) {
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    }).start();

                                                } else {
                                                    Util.toastMsg(responseData.getMsg());
                                                }
                                            }

                                            @Override
                                            public void failure(RetrofitError error) {
                                                dialogDissmiss();
                                                ZillaApi.dealNetError(error);
                                            }
                                        });
                    }
                }).show();
    }

    BottomSheetDialog dialog;

    private void chooseGroup(final int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.pop_trans_view, null);
        final ListView lv = (ListView) view.findViewById(R.id.lv);
        View footer = LayoutInflater.from(this).inflate(R.layout.trans_group_footer, null);
        lv.addFooterView(footer);
        lv.setAdapter(new EasyAdapter<ClassGroup>(this, groups, android.R.layout.simple_list_item_single_choice) {
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
                        if (lv.getFirstVisiblePosition() != 0) {
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
        final Member member = members.get(position);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lv.getCheckedItemPosition()==-1){
                    return;
                }
                final ClassGroup group = groups.get(lv.getCheckedItemPosition());
                ZillaApi.NormalRestAdapter.create(MoreService.class)
                        .turnToAnotherGroup(
                                UserInfoModel.getInstance().getToken(),
                                member.getAccountId(),
                                classId,
                                group.getCGId(),
                                new RequestCallback<ResponseData>() {
                                    @Override
                                    public void success(ResponseData responseData, Response response) {
                                        if (responseData.getStatus() == 200) {
                                            member.setCGId(group.getCGId());
                                            member.setCGName(group.getCGName());
                                            adapter.notifyDataSetChanged();
                                            Snackbar.make(tv_title, "转组成功", Snackbar.LENGTH_SHORT).setDuration(1000).show();
                                        } else {
                                            Snackbar.make(tv_title, "转组失败", Snackbar.LENGTH_SHORT).setDuration(1000).show();
                                        }
                                    }
                                }

                        );
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
        dialog = new BottomSheetDialog(this);
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
