package com.softtek.lai.module.retest.view;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.retest.Adapter.QueryAdapter;
import com.softtek.lai.module.retest.eventModel.BanJiEvent;
import com.softtek.lai.module.retest.Adapter.ClassAdapter;
import com.softtek.lai.module.retest.eventModel.StudentEvent;
import com.softtek.lai.module.retest.model.Banji;
import com.softtek.lai.module.retest.model.Student;
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
    ListView list_class;
    @InjectView(R.id.list_query)
    ListView list_query;
    //展开班级列表
    @InjectView(R.id.ll_classlist)
    LinearLayout ll_classlist;
    private List<Banji> banjiList=new ArrayList<Banji>();
    private List<Student>studentList=new ArrayList<Student>();
    private   ClassAdapter classAdapter;
    private QueryAdapter queryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);

        initbanji();
        classAdapter=new ClassAdapter(this,banjiList);
        queryAdapter=new QueryAdapter(this,studentList);
//        ListView listView=(ListView)findViewById(R.id.list_class);

        list_class.setAdapter(classAdapter);
        list_query.setAdapter(queryAdapter);

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
//        bar_right.setBackgroundResource(R.drawable.search);
        retestPre =new RetestclassImp();
//        long id=2;
        retestPre.doGetRetestclass(36);
        retestPre.doGetqueryResult("135");



    }

    @Subscribe
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

    @Subscribe
    public void onEvent(BanJiEvent banji){
        System.out.println("dsadasdsadasda>>》》》》》》》》》》》》》》"+banji.getBanjis());
         List<Banji> banjis=banji.getBanjis();
        for(Banji bj:banjis){
//            String a="2016-03-08";
//            String b=a.substring(5,7);
//            if(b.equals("03")){
//                b="三月份";
//
//            }
            String month=bj.getStartDate().substring(5,7);
            Banji lis5=new Banji(tomonth(month),bj.getClassName(),bj.getTotal());
            banjiList.add(lis5);
            bj.getClassName();
            bj.getStartDate();
            bj.getTotal();
        }
    }
    @Subscribe
    public void onEvent1(StudentEvent student){
        System.out.println(">>》》》》》》》》》》》》》》"+student.getStudents());
        studentList=student.getStudents();
        queryAdapter.updateData(studentList);
    }

    public String tomonth(String month){
        if (month.equals("01")){
            month="一月班";
        }
        else if (month.equals("02")){
            month="二月班";
        }else if (month.equals("03"))
        {
            month="三月班";
        }else if (month.equals("04"))
        {
            month="四月班";

        }else if (month.equals("05"))
        {
            month="五月班";
        }else if (month.equals("06"))
        {
            month="六月班";
        }else if (month.equals("07"))
        {
            month="七月班";
        } else if (month.equals("08"))
        {
            month="八月班";
        }else if (month.equals("09"))
        {
            month="九月班";
        }else if (month.equals("10"))
        {
            month="十月班";
        }else if (month.equals("11"))
        {
            month="十一月班";
        }else
        {
            month="十二月班";
        }
        return month;
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
