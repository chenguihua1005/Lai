package com.softtek.lai.module.jingdu.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.jingdu.Adapter.RankAdapter;
import com.softtek.lai.module.jingdu.model.Rank;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_jingdu)
public class JingduActivity extends BaseActivity {

    @InjectView(R.id.list_rank)
    ListView list_rank;

    private List<Rank>rankList=new ArrayList<Rank>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRank();
        RankAdapter adapter=new RankAdapter(JingduActivity.this,R.layout.rank_item,rankList);
        list_rank.setAdapter(adapter);

    }

    private void initRank() {
        Rank rank1=new Rank("1","张",160,120,160-120);
        rankList.add(rank1);
        Rank rank2=new Rank("2","李",160,120,160-120);
        rankList.add(rank2);
        Rank rank3=new Rank("3","王",160,120,160-120);
        rankList.add(rank3);
        Rank rank4=new Rank("4","赵",160,120,160-120);
        rankList.add(rank4);
    }


    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {

    }
}
