package com.softtek.lai.module.bodygame3.more.view;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.ggx.widgets.drop.DoubleListView;
import com.ggx.widgets.drop.SimpleTextAdapter;
import com.ggx.widgets.model.FilterType;
import com.ggx.widgets.view.CheckTextView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.more.model.City;
import com.softtek.lai.module.bodygame3.more.model.SmallRegion;
import com.softtek.lai.module.bodygame3.more.net.MoreService;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.ListViewUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
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

    @InjectView(R.id.rl_area)
    RelativeLayout rl_area;
    @InjectView(R.id.tv_xiaoqu)
    TextView tv_xiaoqu;
    @InjectView(R.id.tv_city)
    TextView tv_city;

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

    private List<SmallRegion> left=new ArrayList<>();

    private MoreService service;

    @Override
    protected void initViews() {
        tv_title.setText("开班");
        tv_right.setText("确定");
        tv_add_group.setOnClickListener(this);
        rl_date.setOnClickListener(this);
        rl_class_name.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        rl_area.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        service= ZillaApi.NormalRestAdapter.create(MoreService.class);
        groups = new ArrayList<>();
        groups.add("未命名小组");
        adapter = new EasyAdapter<String>(this, groups, R.layout.item_add_group) {
            @Override
            public void convert(ViewHolder holder, String data, final int position) {
                TextView groupName = holder.getView(R.id.tv_group_name);
                groupName.setText(data);
                //侧滑操作
                final HorizontalScrollView hsv=holder.getView(R.id.hsv);
                TextView tv_editor=holder.getView(R.id.tv_editor);
                tv_editor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //关闭
                        hsv.post(new Runnable() {
                            @Override
                            public void run() {
                                hsv.smoothScrollTo(0,0);
                            }
                        });
                        Intent updateGroupIntent=new Intent(CreateClassActivity.this, EditorTextActivity.class);
                        updateGroupIntent.putExtra("flag",EditorTextActivity.UPDATE_GROUP_NAME);
                        updateGroupIntent.putExtra("position",position);
                        updateGroupIntent.putExtra("name",groups.get(position));
                        startActivityForResult(updateGroupIntent, 102);
                    }
                });
                TextView tv_delete=holder.getView(R.id.tv_delete);
                tv_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        groups.remove(position);
                        adapter.notifyDataSetChanged();
                        ListViewUtil.setListViewHeightBasedOnChildren(lv_group);
                    }
                });
                RelativeLayout container=holder.getView(R.id.rl_container);
                ViewGroup.LayoutParams params= container.getLayoutParams();
                params.width= DisplayUtil.getMobileWidth(CreateClassActivity.this);
                container.setLayoutParams(params);
                final LinearLayout ll_operation=holder.getView(R.id.ll_operation);
                hsv.setOnTouchListener(new View.OnTouchListener() {
                    int dx;
                    int lastX;
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        switch (motionEvent.getAction()){
                            case MotionEvent.ACTION_DOWN:
                                lastX= (int) motionEvent.getX();
                                break;
                            case MotionEvent.ACTION_MOVE:
                                dx= (int)motionEvent.getX()-lastX;
                                break;
                            case MotionEvent.ACTION_UP:
                                int width=ll_operation.getWidth()/2;
                                boolean show=dx<0?dx<=-width:!(dx>=width);
                                if(show){
                                    hsv.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            hsv.smoothScrollTo(hsv.getMaxScrollAmount(),0);
                                        }
                                    });

                                }else {
                                    hsv.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            hsv.smoothScrollTo(0,0);
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
        service.getRegionalAndCitys(UserInfoModel.getInstance().getToken(), new RequestCallback<ResponseData<List<SmallRegion>>>() {
            @Override
            public void success(ResponseData<List<SmallRegion>> data, Response response) {
                left=data.getData();
            }
        });
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
            case R.id.rl_area:
                showBottomSheet();
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
    BottomSheetDialog dialog;
    private void showBottomSheet(){
        View view= LayoutInflater.from(this).inflate(R.layout.city_village,null);
        DoubleListView<SmallRegion,City> dlv= (DoubleListView<SmallRegion, City>) view.findViewById(R.id.dlv);
        dlv.leftAdapter(new SimpleTextAdapter<SmallRegion>(this,left) {
            @Override
            public String getText(SmallRegion data) {
                return data.getRegionalName();
            }
        }).rightAdapter(new SimpleTextAdapter<City>(this,null) {
            @Override
            public String getText(City data) {
                return data.getCityName();
            }

            @Override
            protected void initView(CheckTextView textView) {
                textView.setBackgroundResource(android.R.color.white);
            }
        }).onLeftItemClickListener(new DoubleListView.OnLeftItemClickListener<SmallRegion, City>() {
            @Override
            public List<City> provideRightList(SmallRegion leftAdapter, int position) {
                return leftAdapter.getRegionalCityList();
            }
        }).onRightItemClickListener(new DoubleListView.OnRightItemClickListener<SmallRegion, City>() {
            @Override
            public void onRightItemClick(SmallRegion item, City childItem) {
                tv_xiaoqu.setText(item.getRegionalName());
                tv_city.setText(childItem.getCityName());
                dialog.dismiss();
            }
        });
        //初始化选中.
        if(left!=null&&!left.isEmpty()){
            dlv.setLeftList(left, 1);
            dlv.setRightList(left.get(1).getRegionalCityList(), -1);
        }
        dialog=new BottomSheetDialog(this);
        dialog.setContentView(view);
        dialog.show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                dialog=null;
            }
        });
    }
}
