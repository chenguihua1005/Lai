package com.softtek.lai.module.act.view;

import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.act.adapter.ActZKPAdapter;
import com.softtek.lai.module.act.model.ActDetiallistModel;
import com.softtek.lai.module.act.model.ActZKP1Model;
import com.softtek.lai.module.act.model.ActZKPModel;
import com.softtek.lai.module.act.presenter.ActManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_act_group_person)
public class ActGroupPersonActivity extends BaseActivity implements View.OnClickListener, ActManager.GetActRGStepOrderCallBack, PullToRefreshBase.OnRefreshListener2<ListView> {
    @InjectView(R.id.zk_list)
    PullToRefreshListView zk_list;

    @InjectView(R.id.img_group)
    ImageView img_group;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.text_name)
    TextView text_name;

    @InjectView(R.id.text_value)
    TextView text_value;

    @InjectView(R.id.text_order)
    TextView text_order;

    @InjectView(R.id.rel_group)
    RelativeLayout rel_group;

    @InjectView(R.id.rel_head)
    RelativeLayout rel_head;

    @InjectView(R.id.img_mo_message)
    ImageView img_mo_message;
    @InjectView(R.id.img_loss)
    ImageView img_loss;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    ActManager actManager;
    int pageIndex = 1;
    int totalPage = 1;
    String userId;
    ActZKPAdapter adapter;
    String m_tpye;
    String target;
    ActDetiallistModel actDetiallistModel;
    String id;
    private List<ActZKP1Model> list = new ArrayList<ActZKP1Model>();

    @Override
    protected void initViews() {
        zk_list.setMode(PullToRefreshBase.Mode.BOTH);
        zk_list.setOnRefreshListener(this);
        ll_left.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        actManager = new ActManager(this);
        userId = UserInfoModel.getInstance().getUser().getUserid();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (zk_list != null)
//                    zk_list.setRefreshing();
//            }
//        }, 500);
        actDetiallistModel = (ActDetiallistModel) getIntent().getSerializableExtra("actDetiallistModel");
        id = getIntent().getStringExtra("id");
        m_tpye = getIntent().getStringExtra("type");
        setHeadView();
        tv_title.setText(actDetiallistModel.getActDName());
        target = actDetiallistModel.getActDTotal();
        actManager.getActRGStepOrder(pageIndex + "", actDetiallistModel.getActId(), id);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex = 1;
        actManager.getActRGStepOrder(pageIndex + "", actDetiallistModel.getActId(), id);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex++;
        if (pageIndex <= totalPage) {
            actManager.getActRGStepOrder(pageIndex + "", actDetiallistModel.getActId(), id);
        } else {
            pageIndex--;
            if (zk_list != null) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        zk_list.onRefreshComplete();
                    }
                }, 200);
            }
        }
    }

    private void setHeadView() {
        String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
        if (!TextUtils.isEmpty(actDetiallistModel.getActDImg())) {
            Picasso.with(this).load(path + actDetiallistModel.getActDImg()).placeholder(R.drawable.img_group_default_big).fit().centerCrop().error(R.drawable.img_group_default_big).into(img_group);
        } else {
            Picasso.with(this).load("www").placeholder(R.drawable.img_group_default_big).fit().centerCrop().error(R.drawable.img_group_default_big).into(img_group);
        }
        text_name.setText(actDetiallistModel.getActDName());
        text_order.setText("第" + actDetiallistModel.getActDOrder() + "名");
        if ("0".equals(m_tpye)) {
            text_value.setText(actDetiallistModel.getActDTotal() + "步");
        } else {
            text_value.setText(actDetiallistModel.getActDTotal() + "公里");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
        }
    }

    @Override
    public void getActRGStepOrder(String type, ActZKPModel model) {
        try {
            if ("true".equals(type)) {
                rel_head.setVisibility(View.VISIBLE);
                zk_list.setVisibility(View.VISIBLE);
                img_loss.setVisibility(View.GONE);
                img_mo_message.setVisibility(View.GONE);
                if (zk_list != null) {
                    zk_list.onRefreshComplete();
                }
                String pages = model.getPageCount();
                if (!"".equals(pages)) {
                    totalPage = Integer.parseInt(pages);
                } else {
                    totalPage = 1;
                }

                if (pageIndex == 1) {
                    list = model.getAccDetiallist();
                    adapter = new ActZKPAdapter(this, list, m_tpye, Float.parseFloat(target));
                    zk_list.setAdapter(adapter);
                } else {
                    list.addAll(model.getAccDetiallist());
                    adapter.notifyDataSetChanged();
                }
            } else if ("102".equals(type)) {
                rel_head.setVisibility(View.GONE);
                zk_list.setVisibility(View.GONE);
                img_loss.setVisibility(View.VISIBLE);
                img_mo_message.setVisibility(View.GONE);
                if (pageIndex == 1) {
                    rel_head.setVisibility(View.GONE);
                    zk_list.setVisibility(View.GONE);
                }
                if (zk_list != null) {
                    zk_list.onRefreshComplete();
                }
                if (pageIndex == 1) {
                    pageIndex = 1;
                } else {
                    pageIndex--;
                }
            } else {
                rel_head.setVisibility(View.GONE);
                zk_list.setVisibility(View.GONE);
                img_loss.setVisibility(View.GONE);
                img_mo_message.setVisibility(View.VISIBLE);
                if (pageIndex == 1) {
                    rel_head.setVisibility(View.GONE);
                    zk_list.setVisibility(View.GONE);
                }
                if (zk_list != null) {
                    zk_list.onRefreshComplete();
                }
                if (pageIndex == 1) {
                    pageIndex = 1;
                } else {
                    pageIndex--;
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
