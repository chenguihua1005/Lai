package com.softtek.lai.module.act.view;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.act.adapter.ActZKAdapter;
import com.softtek.lai.module.act.model.ActDetiallistModel;
import com.softtek.lai.module.act.model.ActZKModel;
import com.softtek.lai.module.act.model.ActZKPersonModel;
import com.softtek.lai.module.act.presenter.ActManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jarvis on 4/27/2016.
 *
 */
@InjectLayout(R.layout.fragment_act)
public class ActFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener2<ListView>, ActManager.GetActivitySituationCallBack {
    @InjectView(R.id.zk_list)
    PullToRefreshListView zk_list;

    @InjectView(R.id.img_group)
    ImageView img_group;

    @InjectView(R.id.img_person)
    ImageView img_person;
    @InjectView(R.id.img_no)
    ImageView img_no;
    @InjectView(R.id.img_loss)
    ImageView img_loss;

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

    ActManager actManager;
    int pageIndex = 1;
    int totalPage = 1;
    String userId;
    String id;
    ActZKAdapter adapter;
    ActZKModel actZKModel;
    private List<ActDetiallistModel> list = new ArrayList<ActDetiallistModel>();

    @Override
    protected void initViews() {
        zk_list.setMode(PullToRefreshBase.Mode.BOTH);
        zk_list.setOnRefreshListener(this);
        rel_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = actZKModel.getActType();
                ActZKPersonModel models = actZKModel.getActDetial();
                ActDetiallistModel model = new ActDetiallistModel(models.getActDImg(), models.getActDName(), models.getActDOrder(), models.getActDTotal(), models.getActId());
                if ("0".equals(type)) {
                    Intent intent = new Intent(getActivity(), ActGroupPersonActivity.class);
                    intent.putExtra("actDetiallistModel", model);
                    intent.putExtra("id", id);
                    intent.putExtra("type", "0");
                    startActivity(intent);
                } else if ("1".equals(type)) {
                    Intent intent = new Intent(getActivity(), ActGroupPersonActivity.class);
                    intent.putExtra("actDetiallistModel", model);
                    intent.putExtra("id", id);
                    intent.putExtra("type", "1");
                    startActivity(intent);
                }
            }
        });
        zk_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long ids) {
                ActDetiallistModel actDetiallistModel = list.get(position - 1);
                String type = actZKModel.getActType();
                if ("0".equals(type)) {
                    Intent intent = new Intent(getActivity(), ActGroupPersonActivity.class);
                    intent.putExtra("actDetiallistModel", actDetiallistModel);
                    intent.putExtra("id", id);
                    intent.putExtra("type", "0");
                    startActivity(intent);
                } else if ("1".equals(type)) {
                    Intent intent = new Intent(getActivity(), ActGroupPersonActivity.class);
                    intent.putExtra("actDetiallistModel", actDetiallistModel);
                    intent.putExtra("id", id);
                    intent.putExtra("type", "1");
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void initDatas() {
        actManager = new ActManager(this);
        userId = UserInfoModel.getInstance().getUser().getUserid();
        id = getActivity().getIntent().getStringExtra("id");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (zk_list != null)
                    zk_list.setRefreshing();
            }
        }, 500);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex = 1;
        actManager.getActivitySituation(pageIndex + "", userId, id);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex++;
        if (pageIndex <= totalPage) {
            actManager.getActivitySituation(pageIndex + "", userId, id);
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

    private void setHeadView(ActZKModel model) {
        try {
            ActZKPersonModel actZKPersonModel = model.getActDetial();
            String m_type = model.getActType();
            String path = AddressManager.get("photoHost");
            if ("1".equals(m_type) || "0".equals(m_type)) {
                rel_group.setVisibility(View.VISIBLE);
                img_person.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(actZKPersonModel.getActDImg())) {
                    Picasso.with(getContext()).load(path + actZKPersonModel.getActDImg()).placeholder(R.drawable.img_group_default_big).fit().error(R.drawable.img_group_default_big).into(img_group);
                } else {
                    Picasso.with(getContext()).load("www").placeholder(R.drawable.img_group_default_big).fit().error(R.drawable.img_group_default_big).into(img_group);
                }
            } else {
                rel_group.setVisibility(View.GONE);
                img_person.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(actZKPersonModel.getActDImg())) {
                    Picasso.with(getContext()).load(path + actZKPersonModel.getActDImg()).placeholder(R.drawable.img_default).fit().error(R.drawable.img_default).into(img_person);
                } else {
                    Picasso.with(getContext()).load("www").placeholder(R.drawable.img_default).fit().error(R.drawable.img_default).into(img_person);
                }
            }

            float distance;
            if ("0".equals(m_type)) {
                rel_group.setVisibility(View.VISIBLE);
                img_person.setVisibility(View.GONE);
                text_value.setText(actZKPersonModel.getActDTotal() + "步");
            } else if ("1".equals(m_type)) {
                rel_group.setVisibility(View.VISIBLE);
                img_person.setVisibility(View.GONE);
                text_value.setText(actZKPersonModel.getActDTotal() + "公里");
            } else if ("2".equals(m_type)) {
                rel_group.setVisibility(View.GONE);
                img_person.setVisibility(View.VISIBLE);
                text_value.setText(actZKPersonModel.getActDTotal() + "步");
            } else {
                rel_group.setVisibility(View.GONE);
                img_person.setVisibility(View.VISIBLE);
                text_value.setText(actZKPersonModel.getActDTotal() + "公里");
            }
            text_name.setText(actZKPersonModel.getActDName());
            text_order.setText("第" + actZKPersonModel.getActDOrder() + "名");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getActivitySituation(String type, ActZKModel model) {
        try {
            if ("true".equals(type)) {
                if (rel_head != null) {
                    rel_head.setVisibility(View.VISIBLE);
                }
                if (zk_list != null) {
                    zk_list.setVisibility(View.VISIBLE);
                }
                if (img_no != null) {
                    img_no.setVisibility(View.GONE);
                }
                if (img_mo_message != null) {
                    img_mo_message.setVisibility(View.GONE);
                }
                if (img_loss != null) {
                    img_loss.setVisibility(View.GONE);
                }
                actZKModel = model;
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
                    list = model.getActDetiallist();
                    String target = model.getTarget();
                    double target_value = 0;
                    if (!TextUtils.isEmpty(target)) {
                        target_value = Double.parseDouble(model.getTarget());
                    }
                    adapter = new ActZKAdapter(getContext(), list, model.getActType(), target_value);
                    zk_list.setAdapter(adapter);
                    setHeadView(model);
                } else {
                    list.addAll(model.getActDetiallist());
                    adapter.notifyDataSetChanged();
                }
            } else if ("102".equals(type)) {
                if (rel_head != null) {
                    rel_head.setVisibility(View.GONE);
                }
                if (zk_list != null) {
                    zk_list.setVisibility(View.GONE);
                }
                if (img_no != null) {
                    img_no.setVisibility(View.GONE);
                }
                if (img_mo_message != null) {
                    img_mo_message.setVisibility(View.GONE);
                }
                if (img_loss != null) {
                    img_loss.setVisibility(View.VISIBLE);
                }
                if (zk_list != null) {
                    zk_list.onRefreshComplete();
                }
                if (pageIndex == 1) {
                    pageIndex = 1;
                } else {
                    pageIndex--;
                }
            } else if ("103".equals(type)) {
                if (rel_head != null) {
                    rel_head.setVisibility(View.GONE);
                }
                if (zk_list != null) {
                    zk_list.setVisibility(View.GONE);
                }
                if (img_no != null) {
                    img_no.setVisibility(View.GONE);
                }
                if (img_mo_message != null) {
                    img_mo_message.setVisibility(View.VISIBLE);
                }
                if (img_loss != null) {
                    img_loss.setVisibility(View.GONE);
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
                if (rel_head != null) {
                    rel_head.setVisibility(View.GONE);
                }
                if (zk_list != null) {
                    zk_list.setVisibility(View.GONE);
                }
                if (img_no != null) {
                    img_no.setVisibility(View.VISIBLE);
                }
                if (img_mo_message != null) {
                    img_mo_message.setVisibility(View.GONE);
                }
                if (img_loss != null) {
                    img_loss.setVisibility(View.GONE);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
