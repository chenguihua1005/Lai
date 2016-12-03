package com.softtek.lai.module.bodygame3.conversation.adapter;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.conversation.model.FriendModel;
import com.softtek.lai.module.bodygame3.conversation.service.ContactService;
import com.softtek.lai.utils.DisplayUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.util.Util;

/**
 * Created by jessica.zhang on 2016/11/30.
 */

public class FriendAdapter extends BaseAdapter {
    public static final String TAG = "NewFriendActivity";

    private LayoutInflater inflater;
    private List<FriendModel> friendslist;
    private Context context;
    private Handler handler;

    public FriendAdapter(Context context, List<FriendModel> list, Handler handler) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.friendslist = list;

        this.handler = handler;
    }

    public void updateData(List<FriendModel> list) {
        this.friendslist = list;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return friendslist.size();
    }

    @Override
    public FriendModel getItem(int i) {
        return friendslist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder holder;
        //观察convertView随ListView滚动情况
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.friend_item, viewGroup, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }
        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        final FriendModel friendModel = friendslist.get(position);
        Log.i(TAG, "friendModel = " + new Gson().toJson(friendModel));
        String photo = friendModel.getPhoto();
        String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
        if ("".equals(photo)) {
            Picasso.with(context).load("111").fit().error(R.drawable.img_default).into(holder.head_img);
        } else {
            Picasso.with(context).load(path + photo).fit().error(R.drawable.img_default).into(holder.head_img);
        }
        if (friendModel != null) {
            if (!TextUtils.isEmpty(friendModel.getUserName())) {
                holder.tv_name.setText(friendModel.getUserName());
            }
            int classRole = friendModel.getClassRole();//班级角色：1：开班教练，2：组别教练， 3：组别助教 4：学员
            String classRole_name = "";
            if (1 == classRole) {
                classRole_name = "开班教练";
            } else if (2 == classRole) {
                classRole_name = "组别教练";
            } else if (3 == classRole) {
                classRole_name = "组别助教";
            } else if (4 == classRole) {
                classRole_name = "学员";
            }

            holder.tv_role.setText("(" + classRole_name + ")");
            holder.tv_classname.setText(friendModel.getClassName());

            int status = friendModel.getStatus();
            if (0 == status) {
                holder.status_linear.setVisibility(View.GONE);
                holder.agree_linear.setVisibility(View.VISIBLE);
                holder.btn_agree.setText("同意");
            } else if (1 == status) {
                holder.status_linear.setVisibility(View.VISIBLE);
                holder.agree_linear.setVisibility(View.GONE);
                holder.tv_status.setText("已同意");
            } else if (-1 == status) {
                holder.status_linear.setVisibility(View.VISIBLE);
                holder.agree_linear.setVisibility(View.GONE);
                holder.tv_status.setText("已拒绝");
            }


        }

        //移除好友申请信息
        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "token =" + UserInfoModel.getInstance().getToken() + "friendModel.getApplyId() = " + friendModel.getApplyId());
                ContactService service = ZillaApi.NormalRestAdapter.create(ContactService.class);
                service.removeFriendApplyInfo(UserInfoModel.getInstance().getToken(), friendModel.getApplyId(), new Callback<ResponseData>() {
                    @Override
                    public void success(ResponseData responseData, Response response) {
                        int status = responseData.getStatus();
                        if (200 == status) {
                            Util.toastMsg("success");
                            handler.sendEmptyMessage(0);
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        ZillaApi.dealNetError(error);
                        Util.toastMsg("success");
                    }
                });


            }
        });

        holder.agree_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i(TAG, "token = " + UserInfoModel.getInstance().getToken() + " friendModel.getApplyId() = " + friendModel.getApplyId() + " HxAccountId = " + friendModel.getHxAccountId());
                ContactService service = ZillaApi.NormalRestAdapter.create(ContactService.class);
                service.reviewFriendApplication(UserInfoModel.getInstance().getToken(), friendModel.getApplyId(), 1, new Callback<ResponseData>() {
                    @Override
                    public void success(ResponseData responseData, Response response) {
                        int status = responseData.getStatus();
                        if (200 == status) {
                            Util.toastMsg("success");
                            // 环信同意好友请求

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        EMClient.getInstance().contactManager().acceptInvitation(friendModel.getHxAccountId());
                                        Log.i(TAG, "接受好友请求成功");
                                    } catch (HyphenateException e) {
                                        e.printStackTrace();
                                        Log.i(TAG, "接受好友请求失败");
                                    }
                                }
                            }).start();

                            handler.sendEmptyMessage(0);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        ZillaApi.dealNetError(error);
                        Util.toastMsg("success");
                    }
                });
            }
        });

        ViewGroup.LayoutParams params = holder.container.getLayoutParams();
        params.width = DisplayUtil.getMobileWidth(context);
        holder.container.setLayoutParams(params);

        holder.hsv.setOnTouchListener(new View.OnTouchListener() {
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
                        int width = holder.ll_operation.getWidth() / 2;
                        boolean show = dx < 0 ? dx <= -width : !(dx >= width);
                        if (show) {
                            holder.hsv.post(new Runnable() {
                                @Override
                                public void run() {
                                    holder.hsv.smoothScrollTo(holder.hsv.getMaxScrollAmount(), 0);
                                }
                            });

                        } else {
                            holder.hsv.post(new Runnable() {
                                @Override
                                public void run() {
                                    holder.hsv.smoothScrollTo(0, 0);
                                }
                            });

                        }
                        break;
                }
                return false;
            }
        });

        return convertView;
    }

    /**
     * 存放控件
     */
    private class ViewHolder {
        private ImageView head_img;//头像图标
        private TextView tv_name;//姓名
        private TextView tv_role;//角色
        private TextView tv_classname;//班级名称
        public TextView tv_status;//是否同意状态
        public LinearLayout status_linear;//
        //同意按钮
        public LinearLayout agree_linear;
        private TextView btn_agree;

        private HorizontalScrollView hsv;
        private TextView tv_delete;
        LinearLayout container;//左边固定的
        LinearLayout ll_operation;

        public ViewHolder(View view) {
            this.head_img = (ImageView) view.findViewById(R.id.head_img);
            this.tv_name = (TextView) view.findViewById(R.id.tv_name);
            this.tv_role = (TextView) view.findViewById(R.id.tv_role);
            this.tv_classname = (TextView) view.findViewById(R.id.tv_classname);
            this.tv_status = (TextView) view.findViewById(R.id.tv_status);
            this.status_linear = (LinearLayout) view.findViewById(R.id.status_linear);

            //同意按钮
            this.agree_linear = (LinearLayout) view.findViewById(R.id.agree_linear);
            this.btn_agree = (TextView) view.findViewById(R.id.btn_agree);

            this.hsv = (HorizontalScrollView) view.findViewById(R.id.hsv);
            this.tv_delete = (TextView) view.findViewById(R.id.tv_delete);
            this.container = (LinearLayout) view.findViewById(R.id.rl_container);
            this.ll_operation = (LinearLayout) view.findViewById(R.id.ll_operation);
        }
    }
}
