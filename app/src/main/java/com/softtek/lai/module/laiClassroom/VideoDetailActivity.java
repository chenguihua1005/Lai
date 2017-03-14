package com.softtek.lai.module.laiClassroom;

import android.content.res.Configuration;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dl7.player.media.IjkPlayerView;
import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity2;
import com.softtek.lai.module.laiClassroom.model.VideoDetailModel;
import com.softtek.lai.module.laiClassroom.presenter.VideoDetailPresenter;
import com.softtek.lai.widgets.CommentTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_video_detail)
public class VideoDetailActivity extends BaseActivity2<VideoDetailPresenter> implements VideoDetailPresenter.VideoDetailView{

    @InjectView(R.id.player_view)
    IjkPlayerView playerView;
    @InjectView(R.id.tv_net)
    TextView tv_net;
    @InjectView(R.id.video_title)
    TextView video_title;
    @InjectView(R.id.tv_hot)
    TextView tv_hot;
    @InjectView(R.id.cb_shoucang)
    CheckBox cb_shoucang;


    @InjectView(R.id.lv)
    ListView lv;
    private EasyAdapter<VideoDetailModel.VideoList> adapter;
    private List<VideoDetailModel.VideoList> datas=new ArrayList<>();

//    private static final String VIDEO_URL = "http://flv2.bn.netease.com/videolib3/1611/28/GbgsL3639/SD/movie_index.m3u8";
//    private static final String VIDEO_HD_URL = "http://flv2.bn.netease.com/videolib3/1611/28/GbgsL3639/HD/movie_index.m3u8";
    @Override
    protected void initViews() {
        String videoImage=getIntent().getStringExtra("cover");
        String videoUrl=getIntent().getStringExtra("videoUrl");
        Picasso.with(this).load(videoImage).fit().into(playerView.mPlayerThumb);
        playerView.init()
                //.setTitle("这是个跑马灯TextView，标题要足够长才会跑。-(゜ -゜)つロ 乾杯~")
//                .setSkipTip(1000*60*1)
                .setVideoSource(null, videoUrl, null, null, null)
                .setMediaQuality(IjkPlayerView.MEDIA_QUALITY_HIGH);
    }

    @Override
    protected void initDatas() {
        setPresenter(new VideoDetailPresenter(this));
        if(!getPresenter().isWifiConnected(this)){
            String str="正在使用非 WIFI 网络, 播放将产生流量费用 视频时长 03:41 | 流量 约 XX MB \" 用户需点击\"继续播放\" 按钮, 才会播放视频. ";
            Log.i(str);
            tv_net.setVisibility(View.VISIBLE);
        }
        adapter=new EasyAdapter<VideoDetailModel.VideoList>(this,datas,R.layout.recommend_item) {
            @Override
            public void convert(ViewHolder holder, VideoDetailModel.VideoList data, int position) {
                TextView tv_title=holder.getView(R.id.tv_title);
                tv_title.setText(data.getTitle());
                TextView tv_hotnum=holder.getView(R.id.tv_hotnum);
                tv_hotnum.setText(String.valueOf(data.getClicks()));
                CommentTextView tv_subject=holder.getView(R.id.tv_subject);
                tv_subject.setHighlightColor(ContextCompat.getColor(VideoDetailActivity.this,android.R.color.transparent));
                SpannableString ss=new SpannableString(data.getTopic());
                ss.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {

                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setColor(0xFF75BA2B);
                        ds.setUnderlineText(false);//去除超链接的下划线
                    }
                }, 0, ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE );
                tv_subject.setText(ss);
                tv_subject.setMovementMethod(LinkMovementMethod.getInstance());
                ImageView iv_single=holder.getView(R.id.iv_single);
                if(TextUtils.isEmpty(data.getVideoImg())){
                    Picasso.with(VideoDetailActivity.this).load(R.drawable.default_icon_rect).placeholder(R.drawable.default_icon_rect).into(iv_single);
                }else {
                    Picasso.with(VideoDetailActivity.this).load(AddressManager.get("photoHost")+data.getVideoImg())
                            .fit()
                            .placeholder(R.drawable.default_icon_rect)
                            .error(R.drawable.default_icon_rect)
                            .into(iv_single);
                }
            }
        };
        lv.setAdapter(adapter);
        getPresenter().getVideoDetailData(getIntent().getStringExtra("articleId"));
    }


    @Override
    public void onResume() {
        super.onResume();
        playerView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        playerView.onPause();
    }

    @Override
    protected void onDestroy() {
        playerView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        playerView.configurationChanged(newConfig);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (playerView.handleVolumeKey(keyCode)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (playerView.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void getData(VideoDetailModel data) {
        playerView.setTitle(data.getTitle());
        video_title.setText(data.getTitle());
        tv_hot.setText(String.valueOf(data.getClicks()));
        cb_shoucang.setChecked(data.getIsMark()==1);
        cb_shoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb_shoucang.setEnabled(false);
                Log.i("选中状态"+cb_shoucang.isChecked());
                if(!cb_shoucang.isChecked()){
                    cb_shoucang.setChecked(false);
                    Log.i("取消收藏");
                    getPresenter().unLike(getIntent().getStringExtra("articleId"));
                }else {
                    cb_shoucang.setChecked(true);
                    Log.i("加入收藏");
                    getPresenter().doLike(getIntent().getStringExtra("articleId"));
                }
            }
        });
        datas.addAll(data.getVideoList());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void canLike() {
        cb_shoucang.setEnabled(true);
    }
}
