package com.softtek.lai.module.retest.view;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.retest.eventModel.BanJiEvent;
import com.softtek.lai.module.retest.Adapter.ClassAdapter;
import com.softtek.lai.module.retest.model.Banji;
import com.softtek.lai.module.retest.present.RetestPre;
import com.softtek.lai.module.retest.present.RetestclassImp;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by lareina.qiao on 3/18/2016.
 */
@InjectLayout(R.layout.activity_retest)
public class Retest extends BaseActivity implements View.OnClickListener{
    private RetestPre retestPre;
    //标题栏
    @InjectView(R.id.tv_right)
    TextView bar_right;
    @InjectView(R.id.tv_title)
    TextView bar_title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.list_class)
    ListView listView;
    //展开班级列表
    @InjectView(R.id.ll_classlist)
    LinearLayout ll_classlist;
    private List<Banji> banjiList=new ArrayList<Banji>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);

        initbanji();
       // ClassAdapter classAdapter=new ClassAdapter(Retest.this,R.layout.listview_retest_class,banjiList);
//        ListView listView=(ListView)findViewById(R.id.list_class);
        //listView.setAdapter(classAdapter);

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {

        bar_title.setText("复测");
        retestPre =new RetestclassImp();
//        long id=2;
        retestPre.doGetRetestclass(36);



    }

    @Subscribe
    private void initbanji(){


        Banji lis1=new Banji(0,0,"一月班",0,"复仇者联盟",18);
        banjiList.add(lis1);
        Banji lis2=new Banji(0,0,"一月班",0,"健康俱乐部",8);
        banjiList.add(lis2);
        Banji lis3=new Banji(0,0,"二月班",0,"复仇者联盟",10);
        banjiList.add(lis3);
        Banji lis4=new Banji(0,0,"二月班",0,"健康俱乐部",18);
        banjiList.add(lis4);


    }

    @Subscribe
    public void onEvent(BanJiEvent banji){
        System.out.println("dsadasdsadasda>>》》》》》》》》》》》》》》"+banji.getBanjis());


        // Util.toastMsg(banji.getClassName());

//        Banji lis=new Banji();


    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_classlist:
            {

            }
            break;
        }

    }
}
