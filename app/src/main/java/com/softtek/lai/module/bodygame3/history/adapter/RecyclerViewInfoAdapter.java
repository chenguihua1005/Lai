package com.softtek.lai.module.bodygame3.history.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.history.model.CommentEvent;
import com.softtek.lai.module.bodygame3.history.net.HistoryService;
import com.softtek.lai.module.picture.view.PictureMoreActivity;
import com.softtek.lai.module.bodygame3.photowall.model.PhotoWallslistModel;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CircleImageView;
import com.softtek.lai.widgets.CustomGridView;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;


public class RecyclerViewInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<PhotoWallslistModel> myItems = new ArrayList<>();
    private ItemListener myListener;
    private Context mContext;
    private View mPopView;
    private int width;

    private static final int ITEM = 1;
    private static final int FOOTER = 2;
    private static final int EMPTY = 3;

    public RecyclerViewInfoAdapter(List<PhotoWallslistModel> items,
                                   ItemListener listener,
                                   Context context, View popView) {
        myItems = items;
        myListener = listener;
        mContext = context;
        mPopView = popView;
        width = DisplayUtil.getMobileWidth(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_history_info, parent, false);
            return new ViewHolder(view);
        } else if (viewType == FOOTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.up_load_view, parent, false);
            return new FooterHolder(view);
        } else if (viewType == EMPTY) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_view, parent, false);
            return new EmptyHolder(view);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return myItems.size() == 0 ? 0 : myItems.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        int type;
        if (position + 1 == getItemCount()) {
            type = getItemCount() < 6 ? EMPTY : FOOTER;
        } else {
            type = ITEM;
        }
        return type;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //绑定数据
        if (holder instanceof RecyclerViewInfoAdapter.ViewHolder) {
            ((ViewHolder) holder).setData(myItems.get(position));
        }
    }

    public interface ItemListener {
        void onItemClick(PhotoWallslistModel item, int pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public PhotoWallslistModel item;
        private ImageView mPopImg;
        private LinearLayout mZanLayout;
        private LinearLayout mCommentLayout;
        private TextView mUsername;
        private CheckBox mIsFocus;
        private CircleImageView mHeaderImg;
        private TextView mContent;
        private CustomGridView mPhotos;
        private TextView mDate;
        private String path = AddressManager.get("photoHost");
        private boolean isMyselfFocus;
        private boolean isFocus;
        private EasyAdapter<String> gridAdapter;
        private HistoryService service;
        private boolean hasZaned;


        public ViewHolder(View itemView) {
            super(itemView);
            mPopImg = (ImageView) itemView.findViewById(R.id.iv_pop_img);
            mZanLayout = (LinearLayout) itemView.findViewById(R.id.ll_zan_person);
            mCommentLayout = (LinearLayout) itemView.findViewById(R.id.ll_comment_person);
            mUsername = (TextView) itemView.findViewById(R.id.tv_name);
            mIsFocus = (CheckBox) itemView.findViewById(R.id.cb_focus);
            mHeaderImg = (CircleImageView) itemView.findViewById(R.id.civ_header_image);
            mContent = (TextView) itemView.findViewById(R.id.tv_content);
            mPhotos = (CustomGridView) itemView.findViewById(R.id.cgv_photos);
            mDate = (TextView) itemView.findViewById(R.id.tv_date);

            if (mZanLayout.getChildCount() <= 1) {
                mZanLayout.setVisibility(View.GONE);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (myListener != null) {
                        myListener.onItemClick(item, pos);
                    }
                }
            });
        }

        private void setData(final PhotoWallslistModel item) {
            isMyselfFocus = item.getAccountid().equals(UserInfoModel.getInstance().getUser().getUserid());
            isFocus = item.getIsFocus().equals("1");
            service = ZillaApi.NormalRestAdapter.create(HistoryService.class);

            //用户名
            mUsername.setText(item.getUserName());
//            关注
//            if (isMyselfFocus) {
//                mIsFocus.setVisibility(View.INVISIBLE);
//            } else if (isFocus) {
            if (isFocus) {
                mIsFocus.setChecked(true);
            }

            mIsFocus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (!mIsFocus.isChecked()) {
                        service.doCancleFocusAccount(
                                UserInfoModel.getInstance().getToken(),
                                UserInfoModel.getInstance().getUserId(),
                                Long.parseLong(item.getAccountid()),
                                new RequestCallback<ResponseData>() {
                                    @Override
                                    public void success(ResponseData responseData, Response response) {
                                        if (responseData.getStatus()!=200){
                                            mIsFocus.setChecked(true);
                                        }
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        super.failure(error);
                                    }
                                });
                    } else {
                        service.doFocusAccount(
                                UserInfoModel.getInstance().getToken(),
                                UserInfoModel.getInstance().getUserId(),
                                Long.parseLong(item.getAccountid()),
                                new RequestCallback<ResponseData>() {
                                    @Override
                                    public void success(ResponseData responseData, Response response) {
                                       if (responseData.getStatus()!= 200){
                                           mIsFocus.setChecked(false);
                                       }
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        super.failure(error);
                                    }
                                }
                        );
                    }
                }
            });

            //用户头像
            if (!TextUtils.isEmpty(item.getUserThPhoto())) {
                Picasso.with(mContext).load(path + item.getUserThPhoto()).fit().error(R.drawable.img_default)
                        .placeholder(R.drawable.img_default).into(mHeaderImg);
            }
            //3个按钮
            mPopImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initPopupWindow(v, mPopView, item);
                }
            });

            //日期
            mDate.setText(item.getCreatedate());

            //发表留言内容
            if (item.getIsHasTheme().equals("1")) {
                String content = item.getContent();
                SpannableString ss = new SpannableString(content);
                ss.setSpan(new ForegroundColorSpan(0xFFFFA200), 0, 7, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
                mContent.setText(ss);
            } else {
                mContent.setText(item.getContent());
            }


            //点赞的人
            if (item.getPraiseNameList().size() > 0) {
                String name = "";
                mZanLayout.setVisibility(View.VISIBLE);
                for (int i = 0; i < item.getPraiseNameList().size(); i++) {
                    name = item.getPraiseNameList().get(i) + ",";
                    if (i == item.getPraiseNameList().size() - 1) {
                        name = name.substring(0, name.length() - 1);
                    }
                }
                TextView zanText = new TextView(mContext);
                zanText.setText(name);
                zanText.setTextColor(0xFF576a80);
                mZanLayout.addView(zanText);
            }

            //评论
            if (!item.getCommendsNum().equals("0")) {
                for (int i = 0; i < item.getPhotoWallCommendsList().size(); i++) {
                    String commendsName = item.getPhotoWallCommendsList().get(i).getCommentUserName();
                    String commendsContent = item.getPhotoWallCommendsList().get(i).getCommnets();
                    TextView commendText = new TextView(mContext);
                    commendText.setText(commendsName + ":" + commendsContent);
                    mCommentLayout.addView(commendText);
                }
            }

            //照片墙的缩略图
            if (item.getThumbnailPhotoList().size() > 0) {
                gridAdapter = new EasyAdapter<String>(mContext, item.getThumbnailPhotoList(), R.layout.grid_list) {
                    @Override
                    public void convert(com.ggx.widgets.adapter.ViewHolder holder, String data, int position) {
                        ImageView iv_grid = holder.getView(R.id.iv_grid);
                        Picasso.with(mContext).load(path + data).placeholder(R.drawable.default_icon_rect)
                                .error(R.drawable.default_icon_rect).into(iv_grid);
                    }
                };
                mPhotos.setAdapter(gridAdapter);
                mPhotos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent in = new Intent(mContext, PictureMoreActivity.class);
                        in.putStringArrayListExtra("images", (ArrayList<String>) item.getThumbnailPhotoList());
                        in.putExtra("position", i);
                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
                        ActivityCompat.startActivity(mContext, in, optionsCompat.toBundle());
                    }
                });
            }
        }

        private void initPopupWindow(View v, View popView, final PhotoWallslistModel model) {
            final LinearLayout mZan = (LinearLayout) popView.findViewById(R.id.ll_zan);
            final LinearLayout mComment = (LinearLayout) popView.findViewById(R.id.ll_comment);
            final LinearLayout mReport = (LinearLayout) popView.findViewById(R.id.ll_report);
            int popupWidth = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int popupHeight = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            popView.measure(popupWidth, popupHeight);
            popupHeight = popView.getMeasuredHeight();
            popupWidth = popView.getMeasuredWidth();
            final PopupWindow popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, popupHeight, true);
            int[] location = new int[2];
            v.getLocationOnScreen(location);
            final int mSettingWidth = location[0];
            final int mSettingHigh = location[1];
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, mSettingWidth - popupWidth, mSettingHigh + v.getHeight() / 2 - popupHeight / 2);
            popupWindow.setTouchable(true);
            popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                }
            });
            popupWindow.update();
            if (model.getPraiseNameList() != null) {
                if (hasZaned || model.getPraiseNameList().contains(UserInfoModel.getInstance().getUser().getNickname())) {
                    mZan.setClickable(false);
                } else {
                    mZan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                            hasZaned = true;
                            mZan.setClickable(false);
                            if (mZanLayout.getChildCount() > 0) {
                                mZanLayout.setVisibility(View.VISIBLE);
                            }
                            TextView zanText = new TextView(mContext);
                            String name = "," + UserInfoModel.getInstance().getUser().getNickname();
                            if (mZanLayout.getChildCount() == 1) {
                                name = name.substring(1, name.length());
                            }
                            zanText.setText(name);
                            mZanLayout.addView(zanText);

                            service.postZan(UserInfoModel.getInstance().getToken(),
                                    UserInfoModel.getInstance().getUserId(),
                                    UserInfoModel.getInstance().getUser().getNickname(),
                                    model.getHealtId(),
                                    new RequestCallback<ResponseData>() {
                                        @Override
                                        public void success(ResponseData responseData, Response response) {

                                        }

                                        @Override
                                        public void failure(RetrofitError error) {
                                            hasZaned = false;
                                            if (mZan.getChildCount() < 2)
                                                mZan.removeViewAt(mCommentLayout.getChildCount() - 1);
                                            mZan.setVisibility(View.GONE);
                                            super.failure(error);
                                        }
                                    });


                        }
                    });
                }
            }


            mComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                    CommentEvent event = new CommentEvent();
                    event.setAccountId(UserInfoModel.getInstance().getUserId());
                    event.setHealthId(model.getHealtId());
                    event.setView(mCommentLayout);
                    EventBus.getDefault().post(event);
                }
            });

            mReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

    }

    public class FooterHolder extends RecyclerView.ViewHolder {

        public FooterHolder(View view) {
            super(view);
        }
    }

    public class EmptyHolder extends RecyclerView.ViewHolder {

        public EmptyHolder(View view) {
            super(view);
        }
    }
}
                                