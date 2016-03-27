package com.softtek.lai.module.retest.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.retest.Adapter.QueryAdapter;
import com.softtek.lai.module.retest.Adapter.StudentAdapter;
import com.softtek.lai.module.retest.Audit;
import com.softtek.lai.module.retest.Write;
import com.softtek.lai.module.retest.eventModel.BanJiEvent;
import com.softtek.lai.module.retest.Adapter.ClassAdapter;
import com.softtek.lai.module.retest.eventModel.BanjiStudentEvent;
import com.softtek.lai.module.retest.model.Banji;
import com.softtek.lai.module.retest.model.BanjiStudent;
import com.softtek.lai.module.retest.model.Student;
import com.softtek.lai.module.retest.present.RetestPre;
import com.softtek.lai.module.retest.present.RetestclassImp;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

/**
 * Created by lareina.qiao on 3/18/2016.
 */
@InjectLayout(R.layout.activity_retest)
public class Retest extends BaseActivity implements View.OnClickListener{
    private RetestPre retestPre;
    //标题栏
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.tv_title)
    TextView bar_title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.iv_email)
    ImageView iv_email;


    @InjectView(R.id.Iv_fold)
    ImageView Iv_fold;
    @InjectView(R.id.list_class)
    ListView list_class;
    @InjectView(R.id.list_query)
    ListView list_query;
    //展开班级列表
    @InjectView(R.id.ll_classlist)
    RelativeLayout ll_classlist;

    private List<Banji> banjiList=new ArrayList<Banji>();
    private List<BanjiStudent>banjiStudentList=new ArrayList<BanjiStudent>();
    private ClassAdapter classAdapter;
    private StudentAdapter studentAdapter;
    boolean h=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
        System.out.println("dsadasdsadasda>>》》》》》》》》》》》》》》-----------");
        classAdapter=new ClassAdapter(this,banjiList);
        studentAdapter=new StudentAdapter(this,banjiStudentList);
        list_class.setAdapter(classAdapter);
//        queryAdapter=new QueryAdapter(this,banjiStudentList);

//        ListView listView=(ListView)findViewById(R.id.list_class);
        System.out.println("dsadasdsadasda>>》》》》》》》》》》》》》》==============");

        list_query.setAdapter(studentAdapter);
        ll_classlist.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        iv_email.setOnClickListener(this);
        list_class.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Banji banji=banjiList.get(position);
                retestPre.doGetBanjiStudent(banji.getClassId());
                list_class.setVisibility(View.INVISIBLE);
                Iv_fold.setImageResource(R.drawable.unfold);
                h=false;
            }
        });
        list_query.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BanjiStudent banjiStudent=banjiStudentList.get(position);
                if (banjiStudent.getAMStatus()=="")
                {
                    Intent intent=new Intent(Retest.this,Write.class);
                    startActivity(intent);

                }
                else {
                    Intent intent=new Intent(Retest.this, Audit.class);
                    startActivity(intent);
                }
            }
        });
        ll_classlist.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:

                        if (h==false) {
                            list_class.setVisibility(View.VISIBLE);
                            Iv_fold.setImageResource(R.drawable.retract);
                            h=true;
                        }
                        else {
                            list_class.setVisibility(View.INVISIBLE);
                            Iv_fold.setImageResource(R.drawable.unfold);
                            h=false;
                        }
                        break;

                    case MotionEvent.ACTION_BUTTON_PRESS:
                        list_class.setVisibility(View.VISIBLE);
                        break;
                    case MotionEvent.ACTION_UP:
//                        list_class.setVisibility(View.INVISIBLE);
                        break;
                }
                return false;
            }

        });

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
//        tv_right.setText("搜索");
        iv_email.setImageResource(R.drawable.retestsearch);
//        tv_right.setBackgroundResource(R.drawable.search);
        retestPre =new RetestclassImp();
        retestPre.doGetRetestclass(36);




    }


    @Subscribe
    public void onEvent(BanJiEvent banji){
        System.out.println("dsadasdsadasda>>》》》》》》》》》》》》》》"+banji.getBanjis());
        banjiList=banji.getBanjis();
        Log.i("sdfsdfsdfsdfsd",""+banjiList);
        classAdapter.updateData(banjiList);

    }
    @Subscribe
    public void onEvent1(BanjiStudentEvent banjiStudent){
        banjiStudentList=banjiStudent.getBanjiStudents();
        Log.i("sdfsdfsdfsdfsd",""+banjiStudentList);
        studentAdapter.updateData(banjiStudentList);
//        List<Student> students=student.getStudents();
//        for (Student st:students){
//            String month=st.getStartDate().substring(5,7);
//            Student lis=new Student(st.getPhoto(),st.getUserName(),st.getMobile(),tomonth(month),st.getWeekth(),st.getAMStatus());
//            studentList.add(lis);
//            queryAdapter.updateData(studentList);
//        }


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
            case R.id.iv_email:
            {
                Intent intent=new Intent(Retest.this,QueryActivity.class);
                startActivity(intent);

            }
            break;
            case R.id.ll_left:
            {
                finish();
            }
            break;

        }

    }
}
