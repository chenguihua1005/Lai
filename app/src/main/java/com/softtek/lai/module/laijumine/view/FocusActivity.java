package com.softtek.lai.module.laijumine.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.head.net.HeadService;
import com.softtek.lai.module.community.view.PersionalActivity;
import com.softtek.lai.module.laijumine.model.FocusInfoModel;
import com.softtek.lai.module.laijumine.net.MineSevice;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_focus)
public class FocusActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener2<ListView> {
    @InjectView(R.id.list_focus)
    PullToRefreshListView list_focus;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_nomessage)
    RelativeLayout ll_nomessage;

    int positions = 1, focusnum = 0;
    MineSevice mineSevice;
    HeadService headService;
    EasyAdapter<FocusInfoModel> easyAdapter;
    List<FocusInfoModel> focusInfoModels = new ArrayList<FocusInfoModel>();

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        list_focus.setOnItemClickListener(this);
        list_focus.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        list_focus.setOnRefreshListener(this);
        list_focus.setEmptyView(ll_nomessage);
        ILoadingLayout startLabelse = list_focus.getLoadingLayoutProxy(true, false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setRefreshingLabel("正在刷新数据");// 刷新时
        startLabelse.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        ILoadingLayout endLabelsr = list_focus.getLoadingLayoutProxy(false, true);
        endLabelsr.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
        endLabelsr.setRefreshingLabel("正在加载数据");
        endLabelsr.setReleaseLabel("松开立即加载");// 下来达到一定距离时，显示的提示
    }

    @Override
    protected void initDatas() {
        mineSevice = ZillaApi.NormalRestAdapter.create(MineSevice.class);
        headService = ZillaApi.NormalRestAdapter.create(HeadService.class);
        easyAdapter = new EasyAdapter<FocusInfoModel>(this, focusInfoModels, R.layout.acitivity_fans_list) {
            @Override
            public void convert(ViewHolder holder, final FocusInfoModel data, final int position) {
                final CircleImageView cir_photo = holder.getView(R.id.cir_photo);
                if (!TextUtils.isEmpty(data.getPhoto())) {
                    Picasso.with(getBaseContext()).load(AddressManager.get("photoHost") + data.getPhoto())
                            .centerCrop().fit().placeholder(R.drawable.img_default).error(R.drawable.img_default)
                            .into(cir_photo);
                } else {
                    cir_photo.setImageResource(R.drawable.img_default);
                }
                final TextView tv_fansname = holder.getView(R.id.tv_fansname);
                tv_fansname.setText(data.getUserName());
                TextView tv_fanssignature = holder.getView(R.id.tv_fanssignature);
                tv_fanssignature.setText(data.getSignature());
                final ImageView im_guanzhu = holder.getView(R.id.im_guanzhu);
                if (data.isFocus()) {
                    im_guanzhu.setImageResource(R.drawable.add_focus_icon);
                } else {
                    im_guanzhu.setImageResource(R.drawable.focused_icon);
                }
                im_guanzhu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        im_guanzhu.setClickable(false);
                        if (!data.isFocus()) {//当前为已关注状态，用户点击提示“确定不再关注此人”，确定请求取消关注，变为加关注状态。
                            AlertDialog.Builder dialog;
                            dialog = new AlertDialog.Builder(FocusActivity.this);
                            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialogInterface) {
                                    im_guanzhu.setClickable(true);
                                }
                            });
                            dialog.setTitle("确定不再关注此人？").setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    im_guanzhu.setClickable(true);
                                }
                            }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i)
                                {
                                    im_guanzhu.setImageResource(R.drawable.add_focus_icon);
                                    //取消关注
                                    headService.doCancleFocusAccount(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(),
                                            Long.parseLong(data.getFocusAccountId()), new RequestCallback<ResponseData>() {
                                                @Override
                                                public void success(ResponseData responseData, Response response) {
                                                    try
                                                    {
                                                        int status = responseData.getStatus();
                                                        im_guanzhu.setClickable(true);
                                                        switch (status) {
                                                            case 200:
                                                                tv_title.setText("关注（" + (--focusnum) + "）");
                                                                focusInfoModels.get(position).setFocus(true);
                                                                break;
                                                            default:
                                                                im_guanzhu.setImageResource(R.drawable.focused_icon);
                                                                Util.toastMsg(responseData.getMsg());
                                                                break;
                                                        }
                                                    } catch (Exception e)
                                                    {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            });
                                }
                            }).create().show();

//
                        } else {//当前未关注，请求关注接口，成功变为已关注状态
                            im_guanzhu.setImageResource(R.drawable.focused_icon);
                            headService.doFocusAccount(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(),
                                    Long.parseLong(data.getFocusAccountId()), new RequestCallback<ResponseData>() {
                                        @Override
                                        public void success(ResponseData responseData, Response response) {
                                            try {
                                                int status = responseData.getStatus();
                                                im_guanzhu.setClickable(true);
                                                switch (status) {
                                                    case 200:
                                                        tv_title.setText("关注（" + (++focusnum) + "）");
                                                        focusInfoModels.get(position).setFocus(false);
                                                        break;
                                                    case 501:
                                                        tv_title.setText("关注（" + (++focusnum) + "）");
                                                        focusInfoModels.get(position).setFocus(false);
                                                        break;
                                                    default:
                                                        im_guanzhu.setImageResource(R.drawable.add_focus_icon);
                                                        Util.toastMsg(responseData.getMsg());
                                                        break;
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                        }
                    }
                });
            }
        };
        list_focus.setAdapter(easyAdapter);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override

            public void run() {
                list_focus.setRefreshing();

            }

        }, 300);

    }

    private void doGetData() {
        mineSevice.GetFocusPelist(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), new RequestCallback<ResponseData<List<FocusInfoModel>>>() {
            @Override
            public void success(ResponseData<List<FocusInfoModel>> listResponseData, Response response) {
                int status = listResponseData.getStatus();
                try {
                    list_focus.onRefreshComplete();
                    switch (status) {
                        case 200:
                            focusInfoModels.addAll(listResponseData.getData());
                            easyAdapter.notifyDataSetChanged();
                            focusnum=focusInfoModels.size();
                            tv_title.setText("关注（"+focusnum+"）");
                            break;
                        default:
                            focusnum=0;
                            Util.toastMsg(listResponseData.getMsg());
                            tv_title.setText("关注（"+focusnum+"）");
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
        }
    }

    private int Focus = 1;

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent personal = new Intent(this, PersionalActivity.class);
        positions = i - 1;//记住跳转的用户列表位置
        personal.putExtra("isFocus", focusInfoModels.get(positions).isFocus() ? 0 : 1);
        personal.putExtra("personalName", focusInfoModels.get(positions).getUserName());
        personal.putExtra("personalId", focusInfoModels.get(positions).getFocusAccountId());
        startActivityForResult(personal, Focus);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        focusInfoModels.clear();
        doGetData();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Focus && resultCode == RESULT_OK) {
            //从个人动态页返回，判断该用户关注状态是否改变，是则改变该用户被关注状态
            if (data.getIntExtra("isFocus", 0) == (focusInfoModels.get(positions).isFocus() ? 0 : 1)) {
            } else {
                if (data.getIntExtra("isFocus", 0) == 0) {
                    tv_title.setText("关注（" + (--focusnum) + "）");
                    focusInfoModels.get(positions).setFocus(true);//未关注
                } else {
                    tv_title.setText("关注（" + (++focusnum) + "）");
                    focusInfoModels.get(positions).setFocus(false);//已关注
                }
                easyAdapter.notifyDataSetChanged();
            }
        }
    }
}
