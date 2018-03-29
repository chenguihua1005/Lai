package com.softtek.lai.module.laicheng;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.healthyreport.HealthyReportActivity;
import com.softtek.lai.module.laicheng.adapter.HistoryTestRecyclerView;
import com.softtek.lai.module.laicheng.model.HistoryModel;
import com.softtek.lai.module.laicheng.presenter.HistoryVisitorPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_visithistory)
public class VisithistoryActivity extends BaseActivity<HistoryVisitorPresenter> implements View.OnClickListener, HistoryVisitorPresenter.HistoryVisitorView, PullToRefreshBase.OnRefreshListener {
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.iv_email)
    ImageView iv_email;

    @InjectView(R.id.ptrlv)
    PullToRefreshListView ptrlv;
    @InjectView(R.id.ll_nomessage)
    RelativeLayout im_nomessage;
    @InjectView(R.id.rl_search)
    RelativeLayout rl_search;
    @InjectView(R.id.et_input)
    EditText et_input;
    @InjectView(R.id.imageView10)
    ImageView imageView10;
    private List<HistoryModel> historyModelList = new ArrayList<>();
    private List<HistoryModel> historyNewmodels = new ArrayList<>();
    HistoryTestRecyclerView mAdapter;
    private static final int SINCE_LAICHEN = 1;//莱称来源
    private static final int VISITOR = 1;
    private String inputWord = "";
    EasyAdapter<HistoryModel> historyAdapter;

    @Override
    protected void initViews() {
        iv_email.setVisibility(View.GONE);
        ll_left.setVisibility(View.VISIBLE);
        tv_title.setText("访客历史记录");
        ll_left.setOnClickListener(this);
        imageView10.setOnClickListener(this);

        ptrlv.setOnRefreshListener(this);
        ptrlv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        ILoadingLayout startLabelse = ptrlv.getLoadingLayoutProxy(true, false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setRefreshingLabel("正在刷新数据");// 刷新时
        startLabelse.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        //监听回车事件

        et_input.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyEvent.KEYCODE_ENTER == keyCode && keyEvent.ACTION_DOWN == keyEvent.getAction()) {
                    if (TextUtils.isEmpty(et_input.getText())) {
                        getPresenter().GetData();
                    } else {
//                        historyNewmodels.clear();
                        historyModelList.clear();
                        for (HistoryModel model : historyNewmodels) {
                            if (model.getVisitor().getName().contains(et_input.getText().toString()) ||
                                    model.getVisitor().getPhoneNo().contains(et_input.getText().toString())) {
//                                historyNewmodels.add(model);
                                historyModelList.add(model);
                            }
                        }
//                        historyModelList.addAll(historyNewmodels);
                        historyAdapter.notifyDataSetChanged();// 设置adapt的过滤关键词
                    }
                    return true;
                }
                return false;
            }

        });

        et_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();
                if (text.trim().length() == 0) {
                    getPresenter().GetData();
                    return;
                } else {
//                    historyNewmodels.clear();
                    historyModelList.clear();
//                    adapter.getFilter().filter(et_input.getText().toString().trim()); // 设置ListView的过滤关键词
                    for (HistoryModel model : historyNewmodels) {
                        if (model.getVisitor().getName() != null && model.getVisitor().getPhoneNo() != null) {
                            if (model.getVisitor().getName().contains(text) ||
                                    model.getVisitor().getPhoneNo().contains(text)) {
                                historyModelList.add(model);
//                                historyNewmodels.add(model);
                            }
                        }else if (model.getVisitor().getPhoneNo() == null && model.getVisitor().getName() != null){
                            if (model.getVisitor().getName().contains(text)){
//                                historyNewmodels.add(model);
                                historyModelList.add(model);
                            }
                        }else if (model.getVisitor().getPhoneNo() != null && model.getVisitor().getName() == null){
                            if (model.getVisitor().getPhoneNo().contains(text)){
//                                historyNewmodels.add(model);
                                historyModelList.add(model);
                            }
                        }
                    }
//                    historyModelList.addAll(historyNewmodels);
                    historyAdapter.notifyDataSetChanged();
                }

            }
        });

    }

    @Override
    protected void initDatas() {
        setPresenter(new HistoryVisitorPresenter(this));
        dialogShow("正在加载...");
        getPresenter().GetData();
        historyAdapter = new EasyAdapter<HistoryModel>(this, historyModelList, R.layout.visitor_history_item_list) {
            @Override
            public void convert(ViewHolder holder, HistoryModel data, int position) {
                TextView tv_visittime = (TextView) holder.getView(R.id.tv_visittime);
                tv_visittime.setText(data.getMeasuredTime());
                TextView tv_visitor = (TextView) holder.getView(R.id.tv_visitor);
                tv_visitor.setText(data.getVisitor().getName());
                TextView tv_phoneNo = (TextView) holder.getView(R.id.tv_phoneNo);
                tv_phoneNo.setText(data.getVisitor().getPhoneNo());
                TextView tv_gender = (TextView) holder.getView(R.id.tv_gender);
                ImageView mChengIcon = (ImageView)holder.getView(R.id.iv_cheng_icon);
                if (data.getSourceType() == 5){
                    mChengIcon.setImageDrawable(getResources().getDrawable(R.drawable.laicheng_icon));
                }else if (data.getSourceType() == 6){
                    mChengIcon.setImageDrawable(getResources().getDrawable(R.drawable.laicheng_lite_icon));
                }
                if (data.getVisitor().getGender() == 0) {
                    tv_gender.setText("男");
                } else {
                    tv_gender.setText("女");
                }
                TextView tv_age = (TextView) holder.getView(R.id.tv_age);
                tv_age.setText(data.getVisitor().getAge() + "岁");
                TextView tv_height = (TextView) holder.getView(R.id.tv_height);
                tv_height.setText(data.getVisitor().getHeight() + "cm");
            }
        };
        ptrlv.setAdapter(historyAdapter);
        ptrlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(VisithistoryActivity.this, HealthyReportActivity.class);
                intent.putExtra("reportId", historyModelList.get(position-1).getRecordId());
                intent.putExtra("since", HealthyReportActivity.SINCE_LAICHEN);
                intent.putExtra("isVisitor", HealthyReportActivity.VISITOR);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.imageView10:
                if (TextUtils.isEmpty(et_input.getText())) {
                    getPresenter().GetData();
                } else {
//                    historyNewmodels.clear();
                    historyModelList.clear();
                    for (HistoryModel model : historyNewmodels) {
                        if (
                                model.getVisitor().getName().contains(et_input.getText().toString()) ||
                                model.getVisitor().getPhoneNo().contains(et_input.getText().toString())) {
//                            historyNewmodels.add(model);
                            historyModelList.add(model);
                        }
                    }
//                    historyModelList.addAll(historyNewmodels);
                    historyAdapter.notifyDataSetChanged();
                }
                break;
        }
    }


    @Override
    public void getInfo(List<HistoryModel> historyModels) {
        ptrlv.onRefreshComplete();
        if (historyModels.isEmpty()) {
            im_nomessage.setVisibility(View.VISIBLE);
            ptrlv.setVisibility(View.GONE);

        } else {
            historyModelList.clear();
            historyNewmodels.clear();
            historyModelList.addAll(historyModels);
            historyNewmodels.addAll(historyModels);
            historyAdapter.notifyDataSetChanged();
            Log.i("ddd", historyModelList.toString());
            rl_search.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void dissmiss() {
        dialogDissmiss();
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        getPresenter().GetData();
    }
}
