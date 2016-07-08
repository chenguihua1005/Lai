package com.softtek.lai.module.bodygame2.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.widgets.CostomerListView;
import com.softtek.lai.widgets.ObservableScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_class)
public class ClassFragment extends LazyBaseFragment implements View.OnClickListener, ObservableScrollView.ScrollViewListener {
    @InjectView(R.id.lin_class_select)
    LinearLayout lin_class_select;

    @InjectView(R.id.scroll)
    ObservableScrollView scroll;

    @InjectView(R.id.rel_title_more)
    RelativeLayout rel_title_more;

    @InjectView(R.id.lin_select_type)
    LinearLayout lin_select_type;

    @InjectView(R.id.rel_title)
    RelativeLayout rel_title;

    @InjectView(R.id.list_student)
    CostomerListView list_student;

    @InjectView(R.id.img_class_down)
    ImageView img_class_down;

    @InjectView(R.id.text_select_type)
    TextView text_select_type;

    private PopupWindow popTitleMore;
    private PopupWindow popSelectType;
    private PopupWindow popTitleSelect;

    private LinearLayout lin_invite_student;
    private LinearLayout lin_invite_ass;
    private LinearLayout lin_create_class;

    private RelativeLayout rel_jzjs;
    private RelativeLayout rel_jzbfb;
    private RelativeLayout rel_tzl;
    private RelativeLayout rel_ywbh;

    private ImageView img_jzjs;
    private ImageView img_jzbfb;
    private ImageView img_tzl;
    private ImageView img_ywbh;

    private int select_type = 1;         //1:减重斤数  2：减重百分比   3:体制率  4：腰围变化

    private List<ClassSelectModel> select_class_list;
    private List<ClassMainStudentModel> student_list;

    private int lastY = 0;
    float old_y;
    private int touchEventId = -9983761;
    private float alapa = 0;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            View scroller = (View) msg.obj;
            if (msg.what == touchEventId) {
                System.out.println("lastY:" + lastY + "   scroller.getScrollY():" + scroller.getScrollY());
                if (lastY == scroller.getScrollY()) {
                    handleStop(scroller);
                } else {
                    handler.sendMessageDelayed(handler.obtainMessage(touchEventId, scroller), 5);
                    lastY = scroller.getScrollY();

                }
            } else {

            }
        }
    };

    private void handleStop(View scorll) {
//        System.out.println("位置：" + scorll.getScrollY());
//        if (scorll.getScrollY() < 400) {
//            rel_title.setAlpha(0);
//        } else {
//            rel_title.setAlpha(1);
//        }
    }

    @Override
    protected void initViews() {
        lin_class_select.setOnClickListener(this);
        rel_title_more.setOnClickListener(this);
        lin_select_type.setOnClickListener(this);
        rel_title.setAlpha(0);
        scroll.setScrollViewListener(this);
        scroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        handler.sendMessageDelayed(handler.obtainMessage(touchEventId, v), 5);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void initDatas() {
        student_list = new ArrayList<ClassMainStudentModel>();
        for (int i = 0; i < 10; i++) {
            ClassMainStudentModel m = new ClassMainStudentModel();
            m.setOrder(i + "");
            m.setImg(i + "");
            m.setWeight(i + "");
            m.setName("Tom" + i);
            m.setZname("Jim" + i);
            m.setValue(i + "");
            m.setCount(i + "");
            if (i % 2 == 0) {
                m.setType1("1");
                m.setType2("1");
                m.setGender("1");
            } else {
                m.setType1("0");
                m.setType2("0");
                m.setGender("0");
            }
            student_list.add(m);
        }
        ClassMainStudentAdapter adapter = new ClassMainStudentAdapter(getContext(), student_list);
        list_student.setAdapter(adapter);
    }

    private void initSelectTypePop() {
        img_jzjs.setImageResource(R.drawable.img_bg_jzjs_select);
        img_jzbfb.setImageResource(R.drawable.img_bg_jzbfb_select);
        img_tzl.setImageResource(R.drawable.img_bg_tzl_select);
        img_ywbh.setImageResource(R.drawable.img_bg_ywbh_select);

        if (select_type == 1) {
            img_jzjs.setImageResource(R.drawable.img_bg_jzjs_selected);
        } else if (select_type == 2) {
            img_jzbfb.setImageResource(R.drawable.img_bg_jzbfb_selected);
        } else if (select_type == 3) {
            img_tzl.setImageResource(R.drawable.img_bg_tzl_selected);
        } else if (select_type == 4) {
            img_ywbh.setImageResource(R.drawable.img_bg_ywbh_selected);
        }

        rel_jzjs.setOnClickListener(this);
        rel_jzbfb.setOnClickListener(this);
        rel_tzl.setOnClickListener(this);
        rel_ywbh.setOnClickListener(this);
    }

    @Override
    protected void lazyLoad() {
        Log.i("ClassFragment 加载数据");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rel_jzjs://减重斤数
                popSelectType.dismiss();
                select_type = 1;
                text_select_type.setText("按减重斤数");
                break;
            case R.id.rel_jzbfb://减重百分比
                popSelectType.dismiss();
                select_type = 2;
                text_select_type.setText("按减重百分比");
                break;
            case R.id.rel_tzl://体制率
                popSelectType.dismiss();
                select_type = 3;
                text_select_type.setText("按体制率");
                break;
            case R.id.rel_ywbh://腰围变化
                popSelectType.dismiss();
                select_type = 4;
                text_select_type.setText("按腰围变化");
                break;
            case R.id.lin_invite_student://邀请学员
                popTitleMore.dismiss();

                break;
            case R.id.lin_invite_ass://邀请助教
                popTitleMore.dismiss();

                break;
            case R.id.lin_create_class://创建班级
                popTitleMore.dismiss();

                break;
            case R.id.lin_select_type://选择展示类型
                if (popSelectType != null && popSelectType.isShowing()) {
                    popSelectType.dismiss();
                } else {
                    View view = getActivity().getLayoutInflater().inflate(R.layout.popview_select_type, null);
                    rel_jzjs = (RelativeLayout) view.findViewById(R.id.rel_jzjs);
                    rel_jzbfb = (RelativeLayout) view.findViewById(R.id.rel_jzbfb);
                    rel_tzl = (RelativeLayout) view.findViewById(R.id.rel_tzl);
                    rel_ywbh = (RelativeLayout) view.findViewById(R.id.rel_ywbh);

                    img_jzjs = (ImageView) view.findViewById(R.id.img_jzjs);
                    img_jzbfb = (ImageView) view.findViewById(R.id.img_jzbfb);
                    img_tzl = (ImageView) view.findViewById(R.id.img_tzl);
                    img_ywbh = (ImageView) view.findViewById(R.id.img_ywbh);

                    initSelectTypePop();

                    popSelectType = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, true);
                    popSelectType.setOutsideTouchable(true);
                    popSelectType.setBackgroundDrawable(new BitmapDrawable());
                    popSelectType.showAsDropDown(lin_select_type, DisplayUtil.dip2px(getContext(), -95), 0);

                }
                break;
            case R.id.lin_class_select://选择班级
                if (popTitleSelect != null && popTitleSelect.isShowing()) {
                    popTitleSelect.dismiss();
                } else {
                    View view = getActivity().getLayoutInflater().inflate(R.layout.popview_title_class, null);
                    ListView list_class_select = (ListView) view.findViewById(R.id.list_class_select);
                    select_class_list = new ArrayList<ClassSelectModel>();
                    for (int i = 0; i < 8; i++) {
                        ClassSelectModel m = new ClassSelectModel();
                        m.setClassId(i + "");
                        m.setClassMonth(i + "");
                        m.setClassName("测试测试小号班" + i);
                        select_class_list.add(m);
                    }
                    lin_class_select.setBackgroundColor(Color.WHITE);
                    img_class_down.setVisibility(View.GONE);
                    ClassSelectAdapter adapter = new ClassSelectAdapter(getContext(), select_class_list);
                    list_class_select.setAdapter(adapter);
                    popTitleSelect = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, true);
                    popTitleSelect.setOutsideTouchable(true);
                    popTitleSelect.setBackgroundDrawable(new BitmapDrawable());
                    popTitleSelect.showAsDropDown(lin_class_select);
                    popTitleSelect.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            lin_class_select.setBackgroundColor(Color.TRANSPARENT);
                            img_class_down.setVisibility(View.VISIBLE);
                        }
                    });

                }
                break;
            case R.id.rel_title_more://右上角更多按钮
                if (popTitleMore != null && popTitleMore.isShowing()) {
                    popTitleMore.dismiss();
                } else {
                    View view = getActivity().getLayoutInflater().inflate(R.layout.popview_title_more, null);
                    lin_invite_student = (LinearLayout) view.findViewById(R.id.lin_invite_student);
                    lin_invite_ass = (LinearLayout) view.findViewById(R.id.lin_invite_ass);
                    lin_create_class = (LinearLayout) view.findViewById(R.id.lin_create_class);

                    lin_invite_student.setOnClickListener(this);
                    lin_invite_ass.setOnClickListener(this);
                    lin_create_class.setOnClickListener(this);

                    popTitleMore = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, true);
                    popTitleMore.setOutsideTouchable(true);
                    popTitleMore.setBackgroundDrawable(new BitmapDrawable());
                    popTitleMore.showAsDropDown(rel_title_more, DisplayUtil.dip2px(getContext(), -45), 0);

                }
                break;
        }
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        System.out.println("scrollY:" + y + "    oldScrollY:" + oldy + "   scrollView:" + scrollView.getScrollY() + "   y:" + scrollView.getY());
        old_y = y;
        if (oldy < y && scrollView.getScrollY() >= 300) {
            alapa = alapa + (y-oldy)/100.0f;
            if (alapa >= 1) {
                alapa = 1;
            }
            rel_title.setAlpha(alapa);
        } else if (scrollView.getScrollY() < 300 && oldy > y) {
            alapa = alapa - (oldy-y)/100.0f;
            if (alapa <= 0) {
                alapa = 0;
            }
            rel_title.setAlpha(alapa);
        }
        if(scrollView.getScrollY()==0){
            rel_title.setAlpha(0);
        }
        if(scrollView.getScrollY()>500){
            rel_title.setAlpha(1);
        }
    }
}
