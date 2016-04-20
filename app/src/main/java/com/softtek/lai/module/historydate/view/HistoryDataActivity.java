package com.softtek.lai.module.historydate.view;

import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.historydate.adapter.HistoryDataAdapter;
import com.softtek.lai.module.historydate.model.HistoryDataItemModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_history_data)
public class HistoryDataActivity extends BaseActivity implements AdapterView.OnItemClickListener,View.OnClickListener{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    @InjectView(R.id.ptrlv)
    PullToRefreshListView ptrlv;
    @InjectView(R.id.footer)
    LinearLayout ll_footer;
    @InjectView(R.id.cb_all)
    CheckBox cb_all;
    @InjectView(R.id.tv_delete)
    TextView tv_delete;

    private List<HistoryDataItemModel> dataItemModels=new ArrayList<>();
    private HistoryDataAdapter adapter;

    @Override
    protected void initViews() {
        tv_title.setText("历史数据测量");
        tv_right.setText("编辑");
        ptrlv.setOnItemClickListener(this);
        ll_left.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        cb_all.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        for(int i=0;i<20;i++){
            dataItemModels.add(new HistoryDataItemModel());
        }
        adapter=new HistoryDataAdapter(this,dataItemModels,cb_all);
        ptrlv.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private boolean editOrCompleted=false;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
            case R.id.fl_right:
                if(dataItemModels.isEmpty()){
                    break;
                }
                for(HistoryDataItemModel model:dataItemModels){
                    model.setShow(model.isShow()?false:true);
                    model.setChecked(false);
                }
                editOrCompleted=dataItemModels.get(0).isShow();
                tv_right.setText(editOrCompleted?"完成":"编辑");
                if(editOrCompleted)
                    ll_footer.setVisibility(View.VISIBLE);
                else
                    ll_footer.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
                break;
            case R.id.tv_delete:
                //提交删除选项
                break;
            case R.id.cb_all:
                for(HistoryDataItemModel model:dataItemModels){
                    model.setChecked(cb_all.isChecked());
                }
                adapter.notifyDataSetChanged();
                break;
        }
    }

}
