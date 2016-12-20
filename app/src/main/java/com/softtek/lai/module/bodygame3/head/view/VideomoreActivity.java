package com.softtek.lai.module.bodygame3.head.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.head.model.VideoModel;
import com.softtek.lai.module.bodygame3.head.net.HeadService;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.RequestCallback;
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

@InjectLayout(R.layout.activity_videomore)
public class VideomoreActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.more_view)
    GridView more_view;
    private List<VideoModel> videoModels = new ArrayList<VideoModel>();
    EasyAdapter<VideoModel> adapter;
    String path = AddressManager.get("videoHost");
    private long videoId;

    @Override
    protected void initViews() {
        tv_title.setText("更多视频");
        ll_left.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        ZillaApi.NormalRestAdapter.create(HeadService.class).getvideo(UserInfoModel.getInstance().getToken(), new RequestCallback<ResponseData<List<VideoModel>>>() {
            @Override
            public void success(ResponseData<List<VideoModel>> listResponseData, Response response) {
                videoModels.clear();
                if (200 == listResponseData.getStatus()) {
                    if (listResponseData.getData() != null) {
                        videoModels.addAll(listResponseData.getData());
                        adapter.notifyDataSetChanged();
                    } else {
                        Util.toastMsg(listResponseData.getMsg());
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
        adapter = new EasyAdapter<VideoModel>(this, videoModels, R.layout.grid_video) {
            @Override
            public void convert(ViewHolder holder, VideoModel data, int position) {
                TextView grid_type = holder.getView(R.id.grid_type);
                grid_type.setText(data.getVideoType());
                TextView grid_count = holder.getView(R.id.grid_count);
                grid_count.setText(data.getClickCount() + "人观看");
                TextView grid_name = holder.getView(R.id.grid_name);
                grid_name.setText(data.getTitle());
                ImageView iv_imagevideo1 = holder.getView(R.id.iv_imagevideo1);

                Picasso.with(VideomoreActivity.this)
                        .load(AddressManager.get("photoHost") + data.getPhoto())
                        .centerCrop()
                        .resize(DisplayUtil.dip2px(VideomoreActivity.this, 200), DisplayUtil.dip2px(VideomoreActivity.this, 200))
                        .error(R.drawable.default_icon_rect)
                        .placeholder(R.drawable.default_icon_rect)
                        .into(iv_imagevideo1);


            }
        };
        more_view.setAdapter(adapter);

        more_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                VideoModel videoModel = videoModels.get(i);
                videoId = videoModel.getVideoId();
                int count = videoModel.getClickCount() + 1;
                videoModel.setClickCount(count);
                adapter.notifyDataSetChanged();
                Intent it = new Intent(Intent.ACTION_VIEW);
                it.setDataAndType(Uri.parse(path + videoModel.getVideoUrl()), "video/mp4");
                startActivity(it);
                addcount();

            }
        });
    }

    private void addcount() {
        ZillaApi.NormalRestAdapter.create(HeadService.class).getcount(UserInfoModel.getInstance().getToken(), videoId, new RequestCallback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {


            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);

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
}
