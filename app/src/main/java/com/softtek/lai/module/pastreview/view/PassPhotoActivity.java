package com.softtek.lai.module.pastreview.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.widgets.CircleImageView;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_pass_photo)
public class PassPhotoActivity extends BaseActivity implements View.OnClickListener,PullToRefreshBase.OnRefreshListener2<ListView>,  AdapterView.OnItemClickListener{
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ptrlvpassclasslist)
    PullToRefreshListView ptrlvpassclasslist;
    @Override
    protected void initViews() {
        tv_title.setText("我的相册");
        ll_left.setOnClickListener(this);
        ptrlvpassclasslist.setMode(PullToRefreshBase.Mode.BOTH);
        ptrlvpassclasslist.setOnItemClickListener(this);
        ptrlvpassclasslist.setOnRefreshListener(this);
//        View view = getLayoutInflater().inflate(R.layout.loadphotolist_header_layout, null, false);
//        im_uploadphoto_banner_list = (ImageView) view.findViewById(R.id.im_uploadphoto_banner_list);
//        cir_downphoto_head_list = (CircleImageView) view.findViewById(R.id.cir_downphoto_head_list);
//        imtest_list = (ImageView) view.findViewById(R.id.imtest_list);
//        tv_downphoto_nick = (TextView) view.findViewById(R.id.tv_downphoto_nick);
//        ptrlvpassclasslist.getRefreshableView().addHeaderView(view);
    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ll_left:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

    }
}
