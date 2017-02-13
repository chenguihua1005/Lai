package com.softtek.lai.module.community.view;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.SystemUtils;
import com.softtek.lai.widgets.MyPullToListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_topic_detail)
public class TopicDetailActivity extends BaseActivity {

    @InjectView(R.id.ptrlv)
    MyPullToListView ptrlv;

    @InjectView(R.id.rl_toolbar)
    RelativeLayout rl_toolbar;

    private List<String> datas;

    @Override
    protected void initViews() {
        tintManager.setStatusBarTintColor(android.R.color.transparent);
        if(DisplayUtil.getSDKInt()>=Build.VERSION_CODES.KITKAT){
            RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) rl_toolbar.getLayoutParams();
            params.topMargin= DisplayUtil.getStatusHeight(this);
            rl_toolbar.setLayoutParams(params);
        }
        ptrlv.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        ILoadingLayout endLabelsr = ptrlv.getLoadingLayoutProxy(false, true);
        endLabelsr.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
        endLabelsr.setRefreshingLabel("正在刷新数据");
        endLabelsr.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
    }

    @Override
    protected void initDatas() {
        datas=new ArrayList<>();
        for (int i=0;i<10;i++){
            datas.add("item"+i);
        }

        View head=getLayoutInflater().inflate(R.layout.topic_detail_head,null);
        ptrlv.getRefreshableView().addHeaderView(createHeadView());
        ptrlv.setAdapter(new EasyAdapter<String>(this,datas,android.R.layout.simple_list_item_1) {
            @Override
            public void convert(ViewHolder holder, String data, int position) {
                TextView tv=holder.getView(android.R.id.text1);
                tv.setText(data);
            }
        });
    }

    private View createHeadView(){
        RelativeLayout rl=new RelativeLayout(this);
        rl.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,126,
                getResources().getDisplayMetrics())));
        ImageView iv=new ImageView(this);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.aa));
        rl.addView(iv);

        return rl;
    }
}
