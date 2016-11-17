package com.softtek.lai.module.bodygame3.more.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.utils.DateUtil;
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
        tv_add_group.setOnClickListener(this);
        rl_date.setOnClickListener(this);
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
                startActivityForResult(new Intent(this, EditorTextActivity.class), 100);
                break;
            case R.id.rl_date:
                showDateDialog();
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
                    String tip;
                    tip = "PK开始时间必须大于当前时间";
                    //showTip(tip);
                } else {
                    //输出当前日期
                    tv_class_time.setText(date);


                }
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH) + 1).show();

    }
}
