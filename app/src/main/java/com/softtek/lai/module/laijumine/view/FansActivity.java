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
import com.softtek.lai.module.laijumine.model.FansInfoModel;
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

@InjectLayout(R.layout.activity_fans)
public class FansActivity extends BaseActivity implements PullToRefreshBase.OnRefreshListener2<ListView>, AdapterView.OnItemClickListener {

    @InjectView(R.id.list_fans)
    PullToRefreshListView list_fans;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_nomessage)
    RelativeLayout ll_nomessage;

    EasyAdapter<FansInfoModel> adapter;
    MineSevice mineSevice;
    HeadService headService;
    int positions = 1;
    List<FansInfoModel> fansInfoModels = new ArrayList<FansInfoModel>();

    @Override
    protected void initViews() {
        list_fans.setOnItemClickListener(this);
        list_fans.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        list_fans.setOnRefreshListener(this);
        list_fans.setEmptyView(ll_nomessage);
        ILoadingLayout startLabelse = list_fans.getLoadingLayoutProxy(true, false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setRefreshingLabel("正在刷新数据");// 刷新时
        startLabelse.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        ILoadingLayout endLabelsr = list_fans.getLoadingLayoutProxy(false, true);
        endLabelsr.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
        endLabelsr.setRefreshingLabel("正在加载数据");
        endLabelsr.setReleaseLabel("松开立即加载");// 下来达到一定距离时，显示的提示
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void initDatas() {
        mineSevice = ZillaApi.NormalRestAdapter.create(MineSevice.class);
        headService = ZillaApi.NormalRestAdapter.create(HeadService.class);
        adapter = new EasyAdapter<FansInfoModel>(this, fansInfoModels, R.layout.acitivity_fans_list) {
            @Override
            public void convert(ViewHolder holder, FansInfoModel data, final int position) {
                if (data != null) {
                    CircleImageView cir_photo = holder.getView(R.id.cir_photo);
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
                    if (data.getIsFocus() == 0) {
                        im_guanzhu.setImageResource(R.drawable.add_focus_icon);
                    } else {
                        im_guanzhu.setImageResource(R.drawable.focused_icon);
                    }
                    im_guanzhu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            im_guanzhu.setClickable(false);
                            if (fansInfoModels.get(position).getIsFocus() == 0) {
                                headService.doFocusAccount(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(),
                                        Long.parseLong(fansInfoModels.get(position).getAccountId()), new RequestCallback<ResponseData>() {
                                            @Override
                                            public void success(ResponseData responseData, Response response) {
                                                try {
                                                    int status = responseData.getStatus();
                                                    im_guanzhu.setClickable(true);
                                                    switch (status) {
                                                        case 200:
                                                            fansInfoModels.get(position).setIsFocus(1);
                                                            im_guanzhu.setImageResource(R.drawable.focused_icon);
                                                            break;
                                                        case 501:
                                                            fansInfoModels.get(position).setIsFocus(1);
                                                            im_guanzhu.setImageResource(R.drawable.focused_icon);
                                                            break;
                                                        default:
                                                            Util.toastMsg(responseData.getMsg());
                                                            break;
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });

                            } else {
                                AlertDialog.Builder dialog;
                                dialog = new AlertDialog.Builder(FansActivity.this);
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
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        headService.doCancleFocusAccount(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(),
                                                Long.parseLong(fansInfoModels.get(position).getAccountId()), new RequestCallback<ResponseData>() {
                                                    @Override
                                                    public void success(ResponseData responseData, Response response) {
                                                        try {
                                                            int status = responseData.getStatus();
                                                            im_guanzhu.setClickable(true);
                                                            switch (status) {
                                                                case 200:
                                                                    fansInfoModels.get(position).setIsFocus(0);
                                                                    im_guanzhu.setImageResource(R.drawable.add_focus_icon);
                                                                    break;
                                                                default:
                                                                    Util.toastMsg(responseData.getMsg());
                                                                    break;
                                                            }
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                });
                                    }
                                }).create().show();


                            }
                        }
                    });
                }
            }
        };
        list_fans.setAdapter(adapter);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override

            public void run() {

                list_fans.setRefreshing();

            }

        }, 300);

    }

    /*
    * 获取列表数据
    * */
    private void doGetData() {
        mineSevice.GetLovePelist(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), new RequestCallback<ResponseData<List<FansInfoModel>>>() {
            @Override
            public void success(ResponseData<List<FansInfoModel>> listResponseData, Response response) {
                int status = listResponseData.getStatus();
                tv_title.setText("粉丝（" + listResponseData.getData().size()+ "）");
                list_fans.onRefreshComplete();
                switch (status) {
                    case 200:
                            fansInfoModels.addAll(listResponseData.getData());
                            adapter.notifyDataSetChanged();
                        break;
                    default:
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }
        });
    }

    /*下拉刷新*/
    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        fansInfoModels.clear();
        doGetData();
    }

    /*上拉加载*/
    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

    }

    private int Fans = 1;

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent personal = new Intent(this, PersionalActivity.class);
        positions = i - 1;//记住跳转的用户列表位置
        personal.putExtra("isFocus", fansInfoModels.get(positions).getIsFocus());
        personal.putExtra("personalName", fansInfoModels.get(positions).getUserName());
        personal.putExtra("personalId", fansInfoModels.get(positions).getAccountId());
        startActivityForResult(personal, Fans);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Fans && resultCode == RESULT_OK) {
            //从个人动态页返回，判断该用户关注状态是否改变，是则改变该用户被关注状态
            if (data.getIntExtra("isFocus", 0) == fansInfoModels.get(positions).getIsFocus()) {
            } else {
                fansInfoModels.get(positions).setIsFocus(data.getIntExtra("isFocus", 0));
                adapter.notifyDataSetChanged();
            }
        }
    }
}
