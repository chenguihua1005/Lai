package com.softtek.lai.module.laiClassroom;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
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
public class VideoDetailActivity extends BaseActivity2<VideoDetailPresenter> implements VideoDetailPresenter.VideoDetailView,AdapterView.OnItemClickListener {

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
    private List<VideoDetailModel.VideoList> datas = new ArrayList<>();

    @Override
    protected void initViews() {
        String videoImage = getIntent().getStringExtra("cover");
        String videoUrl = getIntent().getStringExtra("videoUrl");
        Picasso.with(this).load(videoImage).fit().into(playerView.mPlayerThumb);
        playerView.init()
//                .setSkipTip(1000*60*1)
                .setVideoSource(null, videoUrl, null, null, null)
                .setMediaQuality(IjkPlayerView.MEDIA_QUALITY_HIGH);
    }

    @Override
    protected void initDatas() {
        setPresenter(new VideoDetailPresenter(this));
        adapter = new EasyAdapter<VideoDetailModel.VideoList>(this, datas, R.layout.recommend_item) {
            @Override
            public void convert(ViewHolder holder, final VideoDetailModel.VideoList data, int position) {
                TextView tv_title = holder.getView(R.id.tv_title);
                tv_title.setText(data.getTitle());
                TextView tv_hotnum = holder.getView(R.id.tv_hotnum);
                tv_hotnum.setText(String.valueOf(data.getClicks()));
                CommentTextView tv_subject = holder.getView(R.id.tv_subject);
                tv_subject.setHighlightColor(ContextCompat.getColor(VideoDetailActivity.this, android.R.color.transparent));
                SpannableString ss = new SpannableString(data.getTopic());
                ss.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        Intent intent = new Intent(VideoDetailActivity.this, SubjectdetailActivity.class);
                        intent.putExtra("topictitle", data.getTopic());
                        intent.putExtra("topicId", data.getTopicId());
                        startActivity(intent);
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setColor(0xFF75BA2B);
                        ds.setUnderlineText(false);//去除超链接的下划线
                    }
                }, 0, ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                tv_subject.setText(ss);
                tv_subject.setMovementMethod(LinkMovementMethod.getInstance());
                ImageView iv_single = holder.getView(R.id.iv_single);
                if (TextUtils.isEmpty(data.getVideoImg())) {
                    Picasso.with(VideoDetailActivity.this).load(R.drawable.default_icon_rect).placeholder(R.drawable.default_icon_rect).into(iv_single);
                } else {
                    Picasso.with(VideoDetailActivity.this).load(AddressManager.get("photoHost") + data.getVideoImg())
                            .fit()
                            .placeholder(R.drawable.default_icon_rect)
                            .error(R.drawable.default_icon_rect)
                            .into(iv_single);
                }
            }
        };
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
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
        cb_shoucang.setChecked(data.getIsMark() == 1);
        cb_shoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb_shoucang.setEnabled(false);
                Log.i("选中状态" + cb_shoucang.isChecked());
                if (!cb_shoucang.isChecked()) {
                    cb_shoucang.setChecked(false);
                    Log.i("取消收藏");
                    getPresenter().unLike(getIntent().getStringExtra("articleId"));
                } else {
                    cb_shoucang.setChecked(true);
                    Log.i("加入收藏");
                    getPresenter().doLike(getIntent().getStringExtra("articleId"));
                }
            }
        });
        datas.addAll(data.getVideoList());
        adapter.notifyDataSetChanged();
        if(!getPresenter().isWifiConnected(this)){
            StringBuilder builder=new StringBuilder();
            builder.append("正在使用非 WIFI 网络, 播放将产生流量费用\n视频时长 ");
            builder.append(data.getVideoTime());
            builder.append(" | 流量 约 ");
            builder.append(data.getVideoSize());
            new AlertDialog.Builder(this).setMessage(builder)
                    .setNegativeButton("稍后播放",null)
                    .setPositiveButton("继续播放", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(playerView!=null){
                                playerView.start();
                            }
                        }
                    }).create().show();
        }else {
            if(playerView!=null){
                playerView.start();
            }
        }
    }

    @Override
    public void canLike() {
        cb_shoucang.setEnabled(true);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        VideoDetailModel.VideoList video=datas.get(position);
        //跳转视频详情
        Intent intent = new Intent(this, VideoDetailActivity.class);
        intent.putExtra("articleId", video.getArticleId());
        intent.putExtra("cover", AddressManager.get("photoHost") + video.getVideoImg());
        intent.putExtra("videoUrl", AddressManager.get("videoHost")+video.getVideoUrl());
        startActivity(intent);
    }
}
