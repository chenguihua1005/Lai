package com.softtek.lai.module.retest.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.bodygame.Counselor;
import com.softtek.lai.module.retest.Adapter.ClassAdapter;
import com.softtek.lai.module.retest.model.Banji;
import com.softtek.lai.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by lareina.qiao on 3/18/2016.
 */
@InjectLayout(R.layout.activity_retest)
public class Retest extends BaseActivity {
    //标题栏
    @InjectView(R.id.tv_right)
    TextView bar_right;
    @InjectView(R.id.tv_title)
    TextView bar_title;
    @InjectView(R.id.tv_left)
    TextView tv_left;
    @InjectView(R.id.list_class)
    ListView listView;
    private List<Banji> banjiList=new ArrayList<Banji>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initbanji();
        ClassAdapter classAdapter=new ClassAdapter(Retest.this,R.layout.listview_retest_class,banjiList);
//        ListView listView=(ListView)findViewById(R.id.list_class);
        listView.setAdapter(classAdapter);
        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Retest.this, Counselor.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        tv_left.setBackgroundResource(R.drawable.back_h);
        bar_title.setText("复测");
        tv_left.setLayoutParams(new Toolbar.LayoutParams(DisplayUtil.dip2px(this,15),DisplayUtil.dip2px(this,20)));



    }
    private void initbanji(){
        Banji lis1=new Banji("一月班","复仇者联盟",18);
        banjiList.add(lis1);
        Banji lis2=new Banji("一月班","健康俱乐部",8);
        banjiList.add(lis2);
        Banji lis3=new Banji("二月班","复仇者联盟",10);
        banjiList.add(lis3);
        Banji lis4=new Banji("二月班","健康俱乐部",18);
        banjiList.add(lis4);


    }
}
