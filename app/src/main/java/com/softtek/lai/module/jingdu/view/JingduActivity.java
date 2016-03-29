package com.softtek.lai.module.jingdu.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.jingdu.Adapter.RankAdapter;
import com.softtek.lai.module.jingdu.EventModel.RankEvent;
import com.softtek.lai.module.jingdu.model.Rank;
import com.softtek.lai.module.jingdu.presenter.GetProinfoImpl;
import com.softtek.lai.module.jingdu.presenter.IGetProinfopresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_jingdu)
public class JingduActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_right)
    TextView tv_right;

    @InjectView(R.id.list_rank)
    ListView list_rank;

    private List<Rank>rankList=new ArrayList<Rank>();
    private IGetProinfopresenter iGetProinfopresenter;

    private Rank rank;
    private RankAdapter rankAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        rankAdapter=new RankAdapter(JingduActivity.this,R.layout.rank_item,rankList);
        list_rank.setAdapter(rankAdapter);
        ll_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

   // Double.parseDouble("160")-Double.parseDouble("120")
    @Override
    protected void initViews() {
        iGetProinfopresenter=new GetProinfoImpl();
        iGetProinfopresenter.getproinfo("7","1");
    }

    @Override
    protected void initDatas() {
        tv_right.setText("分享");
    }

    @Subscribe
    public void onEvent(RankEvent rankEvent){
        System.out.println("rankEvent.getRanks()》》》》》》》》》》》》》》"+rankEvent.getRanks());
        List<Rank>ranks=rankEvent.getRanks();
        for (Rank rk:ranks)
        {
            System.out.println("》》》》》》》"+"AccountId:"+rk.getAccountId()+"ClassId:"+rk.getClassId()+"OrderNum:"+rk.getOrderNum()+"UserName:"+rk.getUserName()+"LossAfter:"+rk.getLossAfter()+"LossBefor:"+rk.getLossBefor()+"LossWeght:"+rk.getLossWeght());
            Rank r1=new Rank(rk.getLossWeght(),rk.getAccountId(),rk.getClassId(),rk.getOrderNum(),rk.getUserName(),rk.getLossAfter(),rk.getLossBefor());
            rankList.add(r1);

        }
        rankAdapter.updateData(rankList);
       Log.i("rankList>>>>>>>>>>>>>>",""+rankList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
            //分享功能逻辑
            case R.id.tv_right:

                break;
        }
    }
}
