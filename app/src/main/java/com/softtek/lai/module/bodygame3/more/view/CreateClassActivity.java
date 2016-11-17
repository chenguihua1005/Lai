package com.softtek.lai.module.bodygame3.more.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.ListViewUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_create_class)
public class CreateClassActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;

    @InjectView(R.id.rl_class_name)
    RelativeLayout rl_class_name;
    @InjectView(R.id.tv_class_name)
    TextView tv_class_name;



    @InjectView(R.id.rl_date)
    RelativeLayout rl_date;
    @InjectView(R.id.tv_class_time)
    TextView tv_class_time;

    @InjectView(R.id.lv_group)
    ListView lv_group;
    @InjectView(R.id.tv_add_group)
    TextView tv_add_group;

    int currentYear;
    int currentMonth;
    int currentDay;

    private List<String> groups;
    private EasyAdapter<String> adapter;

    @Override
    protected void initViews() {
        tv_title.setText("开班");
        tv_right.setText("确定");
        tv_add_group.setOnClickListener(this);
        rl_date.setOnClickListener(this);
        rl_class_name.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        lv_group.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent updateGroupIntent=new Intent(CreateClassActivity.this, EditorTextActivity.class);
                updateGroupIntent.putExtra("flag",EditorTextActivity.UPDATE_GROUP_NAME);
                updateGroupIntent.putExtra("position",i);
                updateGroupIntent.putExtra("name",groups.get(i));
                startActivityForResult(updateGroupIntent, 102);
            }
        });
    }

    @Override
    protected void initDatas() {
        groups = new ArrayList<>();
        groups.add("未命名小组");
        adapter = new EasyAdapter<String>(this, groups, R.layout.item_add_group) {
            @Override
            public void convert(ViewHolder holder, String data, int position) {
                TextView groupName = holder.getView(R.id.tv_group_name);
                groupName.setText(data);
                final TextView tv_editor=holder.getView(R.id.tv_editor);
                final TextView tv_delete=holder.getView(R.id.tv_delete);
                RelativeLayout container=holder.getView(R.id.rl_container);
                ViewGroup.LayoutParams params= container.getLayoutParams();
                params.width= DisplayUtil.getMobileWidth(CreateClassActivity.this);
                container.setLayoutParams(params);
                final HorizontalScrollView hsv=holder.getView(R.id.hsv);
                hsv.setOnTouchListener(new View.OnTouchListener() {
                    int dx;
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        switch (motionEvent.getAction()){
                            case MotionEvent.ACTION_DOWN:
                                Log.i("MaxScrollAmount()===="+hsv.getMaxScrollAmount());
                                break;
                            case MotionEvent.ACTION_MOVE:
                                dx=hsv.getMaxScrollAmount()-hsv.getScrollX();
                                Log.i("dx============"+dx);
                                break;
                            case MotionEvent.ACTION_UP:
                                if(dx<=hsv.getMaxScrollAmount()/2){
                                    Log.i("dsa="+dx);
                                    new Handler().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            hsv.smoothScrollTo(hsv.getMaxScrollAmount(),0);
                                        }
                                    });

                                }else {
                                    new Handler().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            hsv.smoothScrollTo(0, 0);
                                        }
                                    });
                                }
                                break;
                        }
                        return false;
                    }
                });

            }
        };
        lv_group.setAdapter(adapter);

        //获取当前年月日
        currentYear = DateUtil.getInstance().getCurrentYear();
        currentMonth = DateUtil.getInstance().getCurrentMonth();
        currentDay = DateUtil.getInstance().getCurrentDay();
        int afterDay = currentDay + 1;
        String currentDate = currentYear + "年" + (currentMonth < 10 ? "0" + currentMonth : currentMonth) + "月" + (afterDay < 10 ? "0" + afterDay : afterDay) + "日";
        tv_class_time.setText(currentDate);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add_group:
                Intent addGroupIntent=new Intent(this, EditorTextActivity.class);
                addGroupIntent.putExtra("flag",EditorTextActivity.ADD_GROUP_NAME);
                startActivityForResult(addGroupIntent, 100);
                break;
            case R.id.rl_class_name:
                Intent addClassNameIntent=new Intent(this, EditorTextActivity.class);
                addClassNameIntent.putExtra("flag",EditorTextActivity.UPDATE_CLASS_NAME);
                addClassNameIntent.putExtra("name",tv_class_name.getText());
                startActivityForResult(addClassNameIntent, 101);
                break;
            case R.id.rl_date:
                showDateDialog();
                break;
            case R.id.fl_right:
                //提交
                break;
            case R.id.ll_left:
                finish();
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 100) {
                String value = data.getStringExtra("value");
                groups.add(value);
                adapter.notifyDataSetChanged();
                ListViewUtil.setListViewHeightBasedOnChildren(lv_group);
            }else if(requestCode==101){
                String value = data.getStringExtra("value");
                tv_class_name.setText(value);
            }else if(requestCode==102){
                String value = data.getStringExtra("value");
                int position=data.getIntExtra("position",-1);
                if(position>=0&&position<groups.size()){
                    groups.set(position,value);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    private void showDateDialog() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int month = monthOfYear + 1;
                String date = year + "年" + (month < 10 ? ("0" + month) : month) + "月" + (dayOfMonth < 10 ? ("0" + dayOfMonth) : dayOfMonth) + "日";
                if (year < currentYear || (year == currentYear && monthOfYear + 1 < currentMonth) ||
                        (year == currentYear && monthOfYear + 1 == currentMonth && dayOfMonth <= currentDay)) {
                    String tip = "班级开始日期必须大于当前日期";
                    Snackbar.make(tv_title,tip,Snackbar.LENGTH_SHORT).setDuration(2500).show();
                } else {
                    //输出当前日期
                    tv_class_time.setText(date);


                }
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH) + 1).show();

    }
}
