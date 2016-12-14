package com.softtek.lai.module.bodygame3.activity.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ggx.widgets.nicespinner.ArrowSpinner2;
import com.ggx.widgets.nicespinner.ArrowSpinner3;
import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame3.activity.model.TodaysModel;
import com.softtek.lai.module.bodygame3.head.model.ClassModel;
import com.softtek.lai.module.bodygame3.head.view.HeadGameFragment;
import com.softtek.lai.module.bodygame3.head.view.SearchClassActivity;
import com.softtek.lai.module.bodygame3.home.event.UpdateClass;
import com.softtek.lai.module.bodygame3.home.view.ActivityFragment;
import com.softtek.lai.widgets.MySwipRefreshView;
import com.softtek.lai.widgets.materialcalendarview.CalendarMode;
import com.softtek.lai.widgets.materialcalendarview.MaterialCalendarView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_no_class_fragment)
public class NoClassFragment extends LazyBaseFragment implements View.OnClickListener {
    //    @InjectView(R.id.spinner_title1)
//    ArrowSpinner3 spinner_title;
    private AddClass addClass;
    @InjectView(R.id.spinner_title1)
    ArrowSpinner3 spinner_title1;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.material_calendar)
    MaterialCalendarView material_calendar;
    private int classrole;

    public void setAddClass(AddClass addClass) {
        this.addClass = addClass;
    }

    public static NoClassFragment getInstance(AddClass addClass) {
        NoClassFragment fragment = new NoClassFragment();
        fragment.setAddClass(addClass);
        return fragment;
    }

    @Override
    protected void lazyLoad() {


    }

    @Override
    protected void initViews() {
        //日历
//        material_calendar.setDatepageChangeListener(this);
        final Calendar instance = Calendar.getInstance();
        material_calendar.setSelectedDate(instance.getTime());
        Calendar instance1 = Calendar.getInstance();
        instance1.set(instance1.get(Calendar.YEAR) - 1, Calendar.JANUARY, 1);

        Calendar instance2 = Calendar.getInstance();
        instance2.set(instance2.get(Calendar.YEAR) + 1, Calendar.DECEMBER, 31);

        material_calendar.state().edit()
                .setMinimumDate(instance1.getTime())
                .setMaximumDate(instance2.getTime())
                .setCalendarDisplayMode(CalendarMode.MONTHS)//周模式(WEEKS)或月模式（MONTHS）
                .commit();
        material_calendar.setShowOtherDates(0);

        spinner_title1.setVisibility(View.GONE);
        tv_title.setText("尚未开班");
        Bundle bundle = getArguments();
        classrole = bundle.getInt("classrole");
        if (classrole == Constants.HEADCOACH) {
            fl_right.setVisibility(View.VISIBLE);
        } else {
            fl_right.setVisibility(View.GONE);
        }
        ll_left.setOnClickListener(this);

    }

    @Override
    protected void initDatas() {

    }

    @Subscribe
    public void updateClass(UpdateClass clazz) {
        if (clazz.getStatus() == 1) {
            //添加新班级
            Log.i("新增班级接受通知。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
            if (addClass != null) {
                addClass.addclass(clazz.getModel().getClassRole());
            }

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                getActivity().finish();
                break;
        }
    }


    public interface AddClass {
        void addclass(int classrole);
    }
}
