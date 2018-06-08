package com.softtek.lai.module.bodygame3.more.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ggx.widgets.drop.DoubleListView;
import com.ggx.widgets.drop.SimpleTextAdapter;
import com.ggx.widgets.view.CheckTextView;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.home.event.UpdateClass;
import com.softtek.lai.module.bodygame3.more.adapter.ListRecyclerViewAdapter;
import com.softtek.lai.module.bodygame3.more.model.ClassModel;
import com.softtek.lai.module.bodygame3.more.model.LaiClass;
import com.softtek.lai.module.bodygame3.more.net.MoreService;
import com.softtek.lai.module.customermanagement.model.ClubAndCityModel;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.BottomSheetDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_create_class)
public class CreateClassActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener {

    @LifeCircleInject
    ValidateLife validateLife;

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
    @Required(order = 1, message = "请输入班级名称")
    @InjectView(R.id.tv_class_name)
    TextView tv_class_name;

    @InjectView(R.id.rl_area)
    RelativeLayout rl_area;
    @InjectView(R.id.tv_area)
    TextView mArea;
//    @Required(order = 2, message = "请选择小区及城市")
//    @InjectView(R.id.tv_xiaoqu)
//    TextView tv_xiaoqu;
//    @InjectView(R.id.tv_city)
//    TextView tv_city;

    @InjectView(R.id.rl_date)
    RelativeLayout rl_date;
    @Required(order = 3, message = "请选择第一次复测日期")
    @InjectView(R.id.tv_class_time)
    TextView tv_class_time;
    @InjectView(R.id.rl_entry_goal)
    RelativeLayout mEntryGoalContent;
    @InjectView(R.id.tv_entry_goal)
    TextView mEntryGoal;
    @InjectView(R.id.tv_club_name)
    TextView mClubName;
    @InjectView(R.id.rl_club_name)
    RelativeLayout mClubNameContent;
    private Dialog entryGoalDialog;
    private ListRecyclerViewAdapter adapter;
    private AlertDialog clubDialog;
    private List<String> clubName = new ArrayList<>();


    int currentYear;
    int currentMonth;
    int currentDay;

    private List<ClubAndCityModel.RegionalCitiesBean> left = new ArrayList<>();
    private List<ClubAndCityModel.ClubsBean> clubs = new ArrayList<>();

    private MoreService service;
    LaiClass clazz;

    @Override
    protected void initViews() {
        tv_title.setText("开班");
        tv_right.setText("确定");
        rl_date.setOnClickListener(this);
        rl_class_name.setOnClickListener(this);
        mEntryGoalContent.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        rl_area.setOnClickListener(this);
        mClubNameContent.setOnClickListener(this);

    }

    @Override
    protected void initDatas() {
        clazz = new LaiClass();
        service = ZillaApi.NormalRestAdapter.create(MoreService.class);
        currentYear = DateUtil.getInstance().getCurrentYear();
        currentMonth = DateUtil.getInstance().getCurrentMonth();
        currentDay = DateUtil.getInstance().getCurrentDay();
        int afterDay = currentDay;
        String currentDate = currentYear + "-" + (currentMonth < 10 ? "0" + currentMonth : currentMonth) + "-" + (afterDay < 10 ? "0" + afterDay : afterDay);
        tv_class_time.setText(currentDate);
        clazz.setStartDate(DateUtil.getInstance("yyyy-MM-dd").convertDateStr(currentDate, DateUtil.yyyy_MM_dd));
        service.getRegionalAndCitys(UserInfoModel.getInstance().getToken(), new RequestCallback<ResponseData<ClubAndCityModel>>() {
            @Override
            public void success(ResponseData<ClubAndCityModel> data, Response response) {
                left = data.getData().getRegionalCities();
                clubs = data.getData().getClubs();
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });

        adapter = new ListRecyclerViewAdapter(clubName, new ListRecyclerViewAdapter.ItemListener() {
            @Override
            public void onItemClick(String item, View view, int i) {
                mClubName.setText(clubName.get(i));
                adapter.setIndex(i);
                adapter.notifyDataSetChanged();
                clazz.setClubId(clubs.get(i).getClubId());
                String areaName = clubs.get(i).getRegionName() + " " + clubs.get(i).getCityName();
                clazz.setCityId(clubs.get(i).getCityId());
                mArea.setText(areaName);
                clubDialog.dismiss();
            }
        }, this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_class_name:
                Intent addClassNameIntent = new Intent(this, EditorTextActivity.class);
                addClassNameIntent.putExtra("flag", EditorTextActivity.UPDATE_CLASS_NAME);
                addClassNameIntent.putExtra("name", tv_class_name.getText());
                startActivityForResult(addClassNameIntent, 101);
                break;
            case R.id.rl_date:
                showDateDialog();
                break;
            case R.id.fl_right:
                validateLife.validate();
//                finish();
                break;
            case R.id.ll_left:
                finish();
                break;
            case R.id.rl_area:
//                showBottomSheet();
                break;
            case R.id.rl_entry_goal:
                showEntryGoalDialog();
                break;
            case R.id.rl_club_name:
                showClubNameDialog();

        }
    }

    private void showEntryGoalDialog(){
            if (entryGoalDialog == null) {
                entryGoalDialog = new Dialog(this, R.style.custom_dialog);
                entryGoalDialog.setCanceledOnTouchOutside(true);
                Window win = entryGoalDialog.getWindow();
                WindowManager.LayoutParams params = new WindowManager.LayoutParams();
                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                params.x = 120;
                params.y = 100;
                assert win != null;
                win.setAttributes(params);
                entryGoalDialog.setContentView(R.layout.entry_goal_dialog);
                final TextView lossWeight = entryGoalDialog.findViewById(R.id.tv_loss_weight);
                final TextView addWeight = entryGoalDialog.findViewById(R.id.tv_add_weight);
                lossWeight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lossWeight.setTextColor(getResources().getColor(R.color.mytoolbar_green));
                        addWeight.setTextColor(getResources().getColor(R.color.word));
                        mEntryGoal.setText("减重");
                        clazz.setTarget(0);
                        dialogDismiss();
                    }
                });
                addWeight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addWeight.setTextColor(getResources().getColor(R.color.mytoolbar_green));
                        lossWeight.setTextColor(getResources().getColor(R.color.word));
                        mEntryGoal.setText("增重");
                        clazz.setTarget(1);
                        dialogDismiss();
                    }
                });
                entryGoalDialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogDismiss();
                    }
                });
                entryGoalDialog.findViewById(R.id.space).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogDismiss();
                    }
                });
            }
        entryGoalDialog.show();
    }

    private void dialogDismiss() {
        if (entryGoalDialog != null && entryGoalDialog.isShowing()) {
            entryGoalDialog.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 101) {
                String value = data.getStringExtra("value");
                tv_class_name.setText(value);
                if (clazz != null) {
                    clazz.setClassName(value);
                }
            }
        }
    }

    private void showDateDialog() {
        Calendar c = Calendar.getInstance();
//        c.add(Calendar.DAY_OF_YEAR,0);
        final DatePickerDialog dialog =
                new DatePickerDialog(this, null, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMinDate(c.getTime().getTime());
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                //通过mDialog.getDatePicker()获得dialog上的DatePicker组件，然后可以获取日期信息
                DatePicker datePicker = dialog.getDatePicker();
                int year = datePicker.getYear();
                int month = datePicker.getMonth() + 1;
                int day = datePicker.getDayOfMonth();
                if (year < currentYear) {
                    return;
                } else if (year == currentYear) {
                    if (month < currentMonth) {
                        return;
                    } else if (month == currentMonth) {
                        if (day < currentDay) {
                            return;
                        }
                    }
                }
                String date = year + "-" + (month < 10 ? ("0" + month) : month) + "-" + (day < 10 ? ("0" + day) : day);
                //输出当前日期
                tv_class_time.setText(date);
                if (clazz != null) {
                    String date1 = DateUtil.getInstance("yyyy-MM-dd").convertDateStr(date, DateUtil.yyyy_MM_dd);
                    clazz.setStartDate(date1);
                }

            }
        });
        dialog.show();
    }

//    BottomSheetDialog areaDialog;

//    private void showBottomSheet() {
//        View view = LayoutInflater.from(this).inflate(R.layout.city_village, null);
//        DoubleListView<ClubAndCityModel.RegionalCitiesBean, ClubAndCityModel.RegionalCitiesBean.RegionalCityListBean> dlv = view.findViewById(R.id.dlv);
//        dlv.leftAdapter(new SimpleTextAdapter<ClubAndCityModel.RegionalCitiesBean>(this, left) {
//            @Override
//            public String getText(ClubAndCityModel.RegionalCitiesBean data) {
//                return data.getRegionalName();
//            }
//        }).rightAdapter(new SimpleTextAdapter<ClubAndCityModel.RegionalCitiesBean.RegionalCityListBean>(this, null) {
//            @Override
//            public String getText(ClubAndCityModel.RegionalCitiesBean.RegionalCityListBean data) {
//                return data.getCityName();
//            }
//
//            @Override
//            protected void initView(CheckTextView textView) {
//                textView.setBackgroundResource(android.R.color.white);
//            }
//        }).onLeftItemClickListener(new DoubleListView.OnLeftItemClickListener<ClubAndCityModel.RegionalCitiesBean, ClubAndCityModel.RegionalCitiesBean.RegionalCityListBean>() {
//            @Override
//            public List<ClubAndCityModel.RegionalCitiesBean.RegionalCityListBean> provideRightList(ClubAndCityModel.RegionalCitiesBean leftAdapter, int position) {
//                return leftAdapter.getRegionalCityList();
//            }
//        }).onRightItemClickListener(new DoubleListView.OnRightItemClickListener<ClubAndCityModel.RegionalCitiesBean, ClubAndCityModel.RegionalCitiesBean.RegionalCityListBean>() {
//            @Override
//            public void onRightItemClick(ClubAndCityModel.RegionalCitiesBean item, ClubAndCityModel.RegionalCitiesBean.RegionalCityListBean childItem) {
//                if (clazz != null) {
//                    clazz.setCityId(childItem.getCityId());
//                }
//                tv_xiaoqu.setText(item.getRegionalName());
//                tv_city.setText(childItem.getCityName());
//                areaDialog.dismiss();
//            }
//        });
//        初始化选中.
//        if (left != null && !left.isEmpty()) {
//            dlv.setLeftList(left, 0);
//            dlv.setRightList(left.get(0).getRegionalCityList(), -1);
//        }
//        areaDialog = new BottomSheetDialog(this);
//        areaDialog.setContentView(view);
//        areaDialog.show();
//        areaDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialogInterface) {
//                areaDialog = null;
//            }
//        });
//    }

    private void showClubNameDialog() {
        clubName.clear();
        for (int i = 0; i < clubs.size(); i++) {
            clubName.add(clubs.get(i).getClubName());
        }
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_list, null);
        RecyclerView recyclerView = dialogView.findViewById(R.id.rcv_content);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        clubDialog = new AlertDialog.Builder(this).create();
        clubDialog.setView(dialogView, 0, 0, 0, 0);
        if (clubName.size() < 1){
            Toast.makeText(this,"当前没有俱乐部",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!clubDialog.isShowing()) {
            clubDialog.show();
        }
    }

    @Override
    public void onValidationSucceeded() {
        if (mClubName.getText().toString().trim().equals("")){
            Toast.makeText(this,"请选择俱乐部",Toast.LENGTH_SHORT).show();
            return;
        }
        if (mEntryGoal.getText().toString().trim().equals("")){
            Toast.makeText(this,"请选择参赛目标",Toast.LENGTH_SHORT).show();
            return;
        }
        if (clazz != null) {
//            clazz.setGroupName("未分组");
            dialogShow("正在创建班级...");

            service.creatClass(UserInfoModel.getInstance().getToken(), clazz, new RequestCallback<ResponseData<LaiClass>>() {
                @Override
                public void success(final ResponseData<LaiClass> data, Response response) {
                    dialogDissmiss();
                    if (data.getStatus() == 200) {
                        UserModel user = UserInfoModel.getInstance().getUser();
                        user.setHasThClass(1);
                        UserInfoModel.getInstance().saveUserCache(user);
//                        Intent intent = new Intent(CreateClassActivity.this, ContactsActivity.class);
                        Intent intent = new Intent(CreateClassActivity.this, CreateGroupActivity.class);
                        intent.putExtra("classId", data.getData().getClassId());
                        intent.putExtra("createClass", true);
                        startActivity(intent);
                        ClassModel classModel = new ClassModel();
                        classModel.setClassId(data.getData().getClassId());
                        classModel.setClassCode(data.getData().getClassCode());
                        classModel.setClassName(clazz.getClassName());
                        classModel.setClassMasterName(UserInfoModel.getInstance().getUser().getNickname());
                        classModel.setClassRole(1);
                        classModel.setClassStatus(0);
                        List<String> meausres = new ArrayList<>(12);
                        for (int i = 0; i < 12; i++) {
                            meausres.add(DateUtil.getInstance(DateUtil.yyyy_MM_dd).jumpDateByDay(
                                    clazz.getStartDate(), i * 7));
                        }
                        classModel.setClassMeasureDateList(meausres);
                        EventBus.getDefault().post(new UpdateClass(1, classModel));
                    } else {
                        Util.toastMsg(data.getMsg());
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    super.failure(error);
                    dialogDissmiss();
                    Toast.makeText(CreateClassActivity.this, "创建群组失败", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        String msg = failedRule.getFailureMessage();
        Snackbar.make(tv_title, msg, Snackbar.LENGTH_SHORT).setDuration(2000).show();
    }
}
