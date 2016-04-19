package com.softtek.lai.module.retest.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.retest.AuditActivity;
import com.softtek.lai.module.retest.WriteActivity;
import com.softtek.lai.module.retest.adapter.ClassAdapter;
import com.softtek.lai.module.retest.adapter.StudentAdapter;
import com.softtek.lai.module.retest.eventModel.BanJiEvent;
import com.softtek.lai.module.retest.eventModel.BanjiStudentEvent;
import com.softtek.lai.module.retest.model.BanjiModel;
import com.softtek.lai.module.retest.model.BanjiStudentModel;
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
public class  RetestActivity extends BaseActivity implements View.OnClickListener{
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
    @InjectView(R.id.ll_shousuolist)
    LinearLayout ll_shousuolist;
    @InjectView(R.id.ll_shousuo)
    LinearLayout ll_shousuo;
    //选择班级
    @InjectView(R.id.selectclass)
    TextView selectclass;
    private static final int GET_BODY=2;
    UserInfoModel userInfoModel=UserInfoModel.getInstance();
    long loginid=Long.parseLong(userInfoModel.getUser().getUserid());
    private List<BanjiModel> banjiModelList=new ArrayList<BanjiModel>();
    private List<BanjiStudentModel>banjiStudentModelList=new ArrayList<BanjiStudentModel>();
    private ClassAdapter classAdapter;
    private StudentAdapter studentAdapter;
    boolean h=false;
    long ClassId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
        classAdapter=new ClassAdapter(this,banjiModelList);
        studentAdapter=new StudentAdapter(this,banjiStudentModelList);
        list_class.setAdapter(classAdapter);
//        queryAdapter=new QueryAdapter(this,banjiStudentList);

//        ListView listView=(ListView)findViewById(R.id.list_class);

        list_query.setAdapter(studentAdapter);
        ll_classlist.setOnClickListener(this);
        ll_shousuo.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        iv_email.setOnClickListener(this);
        list_class.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BanjiModel banjiModel=banjiModelList.get(position);
                retestPre.doGetBanjiStudent(banjiModel.getClassId());
                ClassId=banjiModel.getClassId();
                list_class.setVisibility(View.INVISIBLE);
                ll_shousuo.setVisibility(View.INVISIBLE);
                ll_shousuolist.setVisibility(View.INVISIBLE);
                Iv_fold.setImageResource(R.drawable.unfold);
                String[] clas=banjiModel.getStartDate().split("-");
                selectclass.setText(tomonth(clas[1]));
                h=false;
                for(int i=0;i<parent.getChildCount();i++){
                    ImageView iv= (ImageView) parent.getChildAt(i).findViewById(R.id.rbtn_retest);
                    iv.setImageResource(R.drawable.radiocir);
                }
                ImageView iv= (ImageView) view.findViewById(R.id.rbtn_retest);
                iv.setImageResource(R.drawable.radiosel);
            }
        });
        //学员列表
        list_query.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BanjiStudentModel banjiStudentModel=banjiStudentModelList.get(position);
                if (banjiStudentModel.getAMStatus()=="")
                {
                    Intent intent=new Intent(RetestActivity.this,WriteActivity.class);
                    intent.putExtra("accountId",banjiStudentModel.getAccountId());
                    intent.putExtra("classId",banjiStudentModel.getClassId());
                    intent.putExtra("typeDate",banjiStudentModel.getTypeDate());
                    //开班时间，判断班级名称（几月班）
                    intent.putExtra("StartDate",banjiStudentModel.getStartDate());
                    //开始周期
                    intent.putExtra("CurrStart",banjiStudentModel.getCurrStart());
                    //结束周期
                    intent.putExtra("CurrEnd",banjiStudentModel.getCurrEnd());
                    //昵称
                    intent.putExtra("UserName",banjiStudentModel.getUserName());
                    //手机号
                    intent.putExtra("Mobile",banjiStudentModel.getMobile());
                    //头像
                    intent.putExtra("Photo",banjiStudentModel.getPhoto());
                    //第几周期
                    intent.putExtra("Weekth",banjiStudentModel.getWeekth());

                    startActivityForResult(intent,GET_BODY);

                }
                else {

                    Intent intent=new Intent(RetestActivity.this, AuditActivity.class);
                    intent.putExtra("accountId",banjiStudentModel.getAccountId());
                    intent.putExtra("classId",banjiStudentModel.getClassId());
                    intent.putExtra("typeDate",banjiStudentModel.getTypeDate());
                    intent.putExtra("loginid","36");
                    //开班时间，判断班级名称（几月班）
                    intent.putExtra("StartDate",banjiStudentModel.getStartDate());
                    //开始周期
                    intent.putExtra("CurrStart",banjiStudentModel.getCurrStart());
                    //结束周期
                    intent.putExtra("CurrEnd",banjiStudentModel.getCurrEnd());
                    //昵称
                    intent.putExtra("UserName",banjiStudentModel.getUserName());
                    //手机号
                    intent.putExtra("Mobile",banjiStudentModel.getMobile());
                    //头像
                    intent.putExtra("Photo",banjiStudentModel.getPhoto());
                    //第几周期
                    intent.putExtra("Weekth",banjiStudentModel.getWeekth());
                    Log.i("zhouqizhouqi"+banjiStudentModel.getWeekth());
                    startActivityForResult(intent,GET_BODY);
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
                            ll_classlist.setVisibility(View.VISIBLE);
                            ll_shousuo.setVisibility(View.VISIBLE);
                            ll_shousuolist.setVisibility(View.VISIBLE);
                            Iv_fold.setImageResource(R.drawable.retract);
                            h=true;
                        }
                        else {
                            list_class.setVisibility(View.INVISIBLE);
                            ll_shousuo.setVisibility(View.INVISIBLE);
                            ll_shousuolist.setVisibility(View.INVISIBLE);
                            Iv_fold.setImageResource(R.drawable.unfold);
                            h=false;
                        }
                        break;

                    case MotionEvent.ACTION_BUTTON_PRESS:
                        list_class.setVisibility(View.VISIBLE);
                        ll_shousuo.setVisibility(View.VISIBLE);
                        break;
                    case MotionEvent.ACTION_UP:
//                        list_class.setVisibility(View.INVISIBLE);
                        break;
                }
                return false;
            }

        });
        ll_shousuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (h==false)
                {
                    list_class.setVisibility(View.VISIBLE);
                    ll_classlist.setVisibility(View.VISIBLE);
                    ll_shousuo.setVisibility(View.VISIBLE);
                    ll_shousuolist.setVisibility(View.VISIBLE);
                    Iv_fold.setImageResource(R.drawable.retract);
                    h=true;
                }
                else {
                    list_class.setVisibility(View.INVISIBLE);
                    ll_shousuo.setVisibility(View.INVISIBLE);
                    ll_shousuolist.setVisibility(View.INVISIBLE);
                    Iv_fold.setImageResource(R.drawable.unfold);
                    h=false;
                }
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
        iv_email.setVisibility(View.VISIBLE);
        iv_email.setImageResource(R.drawable.retestsearch);
        retestPre =new RetestclassImp();
        //获取班级列表，参数助教顾问id
        String id=UserInfoModel.getInstance().getUser().getUserid();
        retestPre.doGetRetestclass(loginid);
        Log.i("id="+id);
//        retestPre.doGetRetestclass(Integer.parseInt(id));





    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //身体围度值传递
        if (requestCode==GET_BODY&&resultCode==RESULT_OK){
            retestPre.doGetBanjiStudent(ClassId);
            studentAdapter.notifyDataSetChanged();

        }
    }


    @Subscribe
    public void onEvent(BanJiEvent banji){
        banjiModelList=banji.getBanjiModels();
        classAdapter.updateData(banjiModelList);

    }
    @Subscribe
    public void onEvent1(BanjiStudentEvent banjiStudent){
        banjiStudentModelList=banjiStudent.getBanjiStudentModels();
        studentAdapter.updateData(banjiStudentModelList);
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
                Intent intent=new Intent(RetestActivity.this,QueryActivity.class);
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
