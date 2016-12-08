package com.softtek.lai.module.bodygame3.head.view;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.github.snowdream.android.util.Log;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.head.model.PhotoWallListModel;
import com.softtek.lai.module.bodygame3.head.model.PhotoWallslistModel;
import com.softtek.lai.module.bodygame3.head.net.HeadService;
import com.softtek.lai.module.community.adapter.HealthyCommunityAdapter;
import com.softtek.lai.module.community.adapter.PhotosAdapter;
import com.softtek.lai.module.community.model.HealthyCommunityModel;
import com.softtek.lai.module.community.model.HealthyRecommendModel;
import com.softtek.lai.module.community.presenter.RecommentHealthyManager;
import com.softtek.lai.module.picture.view.PictureMoreActivity;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CircleImageView;
import com.softtek.lai.widgets.CustomGridView;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_photo_wall)
public class PhotoWallActivity extends BaseActivity implements PullToRefreshBase.OnRefreshListener2<ListView> {
    @InjectView(R.id.ptrlv)
    PullToRefreshListView ptrlv;
    @InjectView(R.id.empty)
    FrameLayout empty;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    private RecommentHealthyManager community;
    private HealthyCommunityAdapter adapter;
    private EasyAdapter<PhotoWallslistModel> photoWallListModelEasyAdapter;
    PhotoWallListModel photoWallListModel;
    PhotoWallslistModel photowallItemModel;
    List<PhotoWallslistModel> photoWallItemModels=new ArrayList<PhotoWallslistModel>();
    private List<HealthyCommunityModel> communityModels=new ArrayList<>();
    int pageIndex=1;
    int totalPage=0;
    HeadService headService;

    @Override
    protected void initViews() {
        ptrlv.setOnRefreshListener(this);
        ptrlv.setMode(PullToRefreshBase.Mode.BOTH);
        ptrlv.setEmptyView(empty);
        ILoadingLayout startLabelse = ptrlv.getLoadingLayoutProxy(true,false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setRefreshingLabel("正在刷新数据");// 刷新时
        startLabelse.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        ILoadingLayout endLabelsr = ptrlv.getLoadingLayoutProxy(false, true);
        endLabelsr.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
        endLabelsr.setRefreshingLabel("正在刷新数据");
        endLabelsr.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
    }

    @Override
    protected void initDatas() {
        headService= ZillaApi.NormalRestAdapter.create(HeadService.class);
        doGetData();
        photoWallListModelEasyAdapter=new EasyAdapter<PhotoWallslistModel>(this,photoWallItemModels,R.layout.photowall_list_item) {
            @Override
            public void convert(ViewHolder holder, PhotoWallslistModel data, int position) {
                TextView tv_name= holder.getView(R.id.tv_name);
                tv_name.setText(data.getUserName());//用户名
                CircleImageView civ_header_image=holder.getView(R.id.civ_header_image);
                if (!TextUtils.isEmpty(data.getUserPhoto())) {//头像
                    Picasso.with(getParent()).load(AddressManager.get("photoHost")+data.getUserPhoto()).fit().error(R.drawable.img_default)
                            .into(civ_header_image);
                    Log.i("照片墙动态测试头像",AddressManager.get("photoHost")+data.getUserPhoto());
                }
                TextView tv_content=holder.getView(R.id.tv_content);
                tv_content.setText(data.getContent());//正文
                CheckBox cb_focus=holder.getView(R.id.cb_focus);
                cb_focus.setChecked("1".equals(data.getIsFocus()));
                CustomGridView photos=holder.getView(R.id.photos);
                String[] imgs = data.getUserThPhoto().split(",");
                final ArrayList<String> list = new ArrayList<>();
                for (int i = 0; i < imgs.length; i++) {
                    list.add(imgs[i]);
                }
                photos.setAdapter(new PhotosAdapter(list, PhotoWallActivity.this));
                photos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                        Intent in = new Intent(PhotoWallActivity.this, PictureMoreActivity.class);
                        in.putStringArrayListExtra("images", list);
                        in.putExtra("position", position);
                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(v, v.getWidth() / 2, v.getHeight() / 2, 0, 0);
                        ActivityCompat.startActivity(PhotoWallActivity.this, in, optionsCompat.toBundle());
                    }
                });

            }
        }
        ;
        ptrlv.setAdapter(photoWallListModelEasyAdapter);
    }

    private void doGetData() {
        headService.doGetPhotoWalls(UserInfoModel.getInstance().getToken(), Long.parseLong("3399"), "C4E8E179-FD99-4955-8BF9-CF470898788B", "1", "10", new RequestCallback<ResponseData<PhotoWallListModel>>() {
            @Override
            public void success(ResponseData<PhotoWallListModel> photoWallListModelResponseData, Response response) {
                Util.toastMsg(photoWallListModelResponseData.getMsg());
                int status=photoWallListModelResponseData.getStatus();
                switch (status)
                {
                    case 200:
                        photoWallListModel=photoWallListModelResponseData.getData();
                        if (photoWallListModel!=null)
                        {
                            empty.setVisibility(View.GONE);
                            photoWallItemModels.addAll(photoWallListModel.getPhotoWallslist());
                            photoWallListModelEasyAdapter.notifyDataSetChanged();

                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

    }

}
