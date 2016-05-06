package com.softtek.lai.module.personalPK.view;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.widgets.CircleImageView;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_pkdetail)
public class PKDetailActivity extends BaseActivity implements OnClickListener {

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.cb_zan_left)
    CheckBox cb_zan_left;
    @InjectView(R.id.cb_zan_right)
    CheckBox cb_zan_right;
    @InjectView(R.id.tv_status)
    TextView tv_status;
    @InjectView(R.id.tv_is_accept)
    TextView tv_is_accept;
    @InjectView(R.id.sender1_header)
    CircleImageView sender1_header;
    @InjectView(R.id.sender2_header)
    CircleImageView sender2_header;
    @InjectView(R.id.sender1)
    ImageView sender1;
    @InjectView(R.id.sender2)
    ImageView sender2;
    @InjectView(R.id.tv_pk_name1)
    TextView tv_pk_name1;
    @InjectView(R.id.tv_pk_name2)
    TextView tv_pk_name2;
    @InjectView(R.id.tv_bushu1)
    TextView tv_bushu1;
    @InjectView(R.id.tv_bushu2)
    TextView tv_bushu2;
    @InjectView(R.id.iv_type)
    ImageView iv_type;
    @InjectView(R.id.tv_content)
    TextView tv_content;
    @InjectView(R.id.tv_time)
    TextView tv_time;
    @InjectView(R.id.btn_cancle_pk)
    TextView btn_cancle_pk;

    @Override
    protected void initViews() {
        tv_title.setText("PK挑战详情");
        ll_left.setOnClickListener(this);
        btn_cancle_pk.setOnClickListener(this);
        cb_zan_left.setOnClickListener(this);
        cb_zan_right.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
            case R.id.btn_cancle_pk:
                break;
            case R.id.cb_zan_left:
                break;
            case R.id.cb_zan_right:
                break;
        }
    }
}
