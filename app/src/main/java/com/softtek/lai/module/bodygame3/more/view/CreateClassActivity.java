package com.softtek.lai.module.bodygame3.more.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
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
import com.softtek.lai.module.bodygame3.more.model.City;
import com.softtek.lai.module.bodygame3.more.model.ClassModel;
import com.softtek.lai.module.bodygame3.more.model.LaiClass;
import com.softtek.lai.module.bodygame3.more.model.SmallRegion;
import com.softtek.lai.module.bodygame3.more.net.MoreService;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.ListViewUtil;
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
    @Required(order = 2, message = "请选择小区及城市")
    @InjectView(R.id.tv_xiaoqu)
    TextView tv_xiaoqu;
    @InjectView(R.id.tv_city)
    TextView tv_city;

    @InjectView(R.id.rl_date)
    RelativeLayout rl_date;
    @Required(order = 3, message = "请选择第一次复测日期")
    @InjectView(R.id.tv_class_time)
    TextView tv_class_time;

    @InjectView(R.id.lv_group)
    ListView lv_group;
    @InjectView(R.id.tv_add_group)
    TextView tv_add_group;

    @InjectView(R.id.rl_class_mail)
    RelativeLayout mClassMailContent;
    @InjectView(R.id.tv_class_mail)
    TextView mClassMail;
    @InjectView(R.id.rl_entry_goal)
    RelativeLayout mEntryGoalContent;
    @InjectView(R.id.tv_entry_goal)
    TextView mEntryGoal;
    private Dialog entryGoalDialog;//分享对话框



    int currentYear;
    int currentMonth;
    int currentDay;

    private List<String> groups;
    private EasyAdapter<String> adapter;

    private List<SmallRegion> left = new ArrayList<>();

    private MoreService service;
    LaiClass clazz;

    @Override
    protected void initViews() {
        tv_title.setText("开班");
        tv_right.setText("确定");
        tv_add_group.setOnClickListener(this);
        rl_date.setOnClickListener(this);
        mClassMailContent.setOnClickListener(this);
        rl_class_name.setOnClickListener(this);
        mEntryGoalContent.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        rl_area.setOnClickListener(this);

    }

    @Override
    protected void initDatas() {
        clazz = new LaiClass();
        clazz.setClassMasterId(UserInfoModel.getInstance().getUserId());
        service = ZillaApi.NormalRestAdapter.create(MoreService.class);
        groups = new ArrayList<>();
        groups.add("默认小组");
        adapter = new EasyAdapter<String>(this, groups, R.layout.item_add_group) {
            @Override
            public void convert(ViewHolder holder, String data, final int position) {
                TextView groupName = holder.getView(R.id.tv_group_name);
                groupName.setText(data);
                //侧滑操作
                final HorizontalScrollView hsv = holder.getView(R.id.hsv);
                TextView tv_editor = holder.getView(R.id.tv_editor);
                tv_editor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //关闭
                        hsv.post(new Runnable() {
                            @Override
                            public void run() {
                                hsv.smoothScrollTo(0, 0);
                            }
                        });
                        Intent updateGroupIntent = new Intent(CreateClassActivity.this, EditorTextActivity.class);
                        updateGroupIntent.putExtra("flag", EditorTextActivity.UPDATE_GROUP_NAME);
                        updateGroupIntent.putExtra("position", position);
                        updateGroupIntent.putExtra("name", groups.get(position));
                        updateGroupIntent.putStringArrayListExtra("groups", (ArrayList<String>) groups);
                        startActivityForResult(updateGroupIntent, 102);
                    }
                });
                TextView tv_delete = holder.getView(R.id.tv_delete);
                if (groups.size() != 1) {
                    tv_delete.setVisibility(View.VISIBLE);
                    tv_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            groups.remove(position);
                            adapter.notifyDataSetChanged();
                            ListViewUtil.setListViewHeightBasedOnChildren(lv_group);
                        }
                    });
                } else {
                    tv_delete.setVisibility(View.GONE);
                }

                RelativeLayout container = holder.getView(R.id.rl_container);
                ViewGroup.LayoutParams params = container.getLayoutParams();
                params.width = DisplayUtil.getMobileWidth(CreateClassActivity.this);
                container.setLayoutParams(params);
                final LinearLayout ll_operation = holder.getView(R.id.ll_operation);
                hsv.setOnTouchListener(new View.OnTouchListener() {
                    int dx;
                    int lastX;

                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        switch (motionEvent.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                lastX = (int) motionEvent.getX();
                                break;
                            case MotionEvent.ACTION_MOVE:
                                dx = (int) motionEvent.getX() - lastX;
                                break;
                            case MotionEvent.ACTION_UP:
                                int width = ll_operation.getWidth() / 2;
                                boolean show = dx < 0 ? dx <= -width : !(dx >= width);
                                if (show) {
                                    hsv.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            hsv.smoothScrollTo(hsv.getMaxScrollAmount(), 0);
                                        }
                                    });

                                } else {
                                    hsv.post(new Runnable() {
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
        int afterDay = currentDay;
        String currentDate = currentYear + "年" + (currentMonth < 10 ? "0" + currentMonth : currentMonth) + "月" + (afterDay < 10 ? "0" + afterDay : afterDay) + "日";
        tv_class_time.setText(currentDate);
        clazz.setStartDate(DateUtil.getInstance("yyyy年MM月dd日").convertDateStr(currentDate, DateUtil.yyyy_MM_dd));
        service.getRegionalAndCitys(UserInfoModel.getInstance().getToken(), new RequestCallback<ResponseData<List<SmallRegion>>>() {
            @Override
            public void success(ResponseData<List<SmallRegion>> data, Response response) {
                left = data.getData();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add_group:
                Intent addGroupIntent = new Intent(this, EditorTextActivity.class);
                addGroupIntent.putStringArrayListExtra("groups", (ArrayList<String>) groups);
                addGroupIntent.putExtra("flag", EditorTextActivity.ADD_GROUP_NAME);
                startActivityForResult(addGroupIntent, 100);
                break;
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
                break;
            case R.id.ll_left:
                finish();
                break;
            case R.id.rl_area:
                showBottomSheet();
                break;
            case R.id.rl_class_mail:
                Intent addClassMail = new Intent(this, EditorTextActivity.class);
                addClassMail.putExtra("flag", EditorTextActivity.ADD_CLASS_MAIL);
                startActivityForResult(addClassMail, 103);
                break;
            case R.id.rl_entry_goal:
                showEntryGoalDialog();
                break;


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
                        dialogDismiss();
                    }
                });
                addWeight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addWeight.setTextColor(getResources().getColor(R.color.mytoolbar_green));
                        lossWeight.setTextColor(getResources().getColor(R.color.word));
                        mEntryGoal.setText("增重");
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
            if (requestCode == 100) {
                String value = data.getStringExtra("value");
                groups.add(value);
                adapter.notifyDataSetChanged();
                ListViewUtil.setListViewHeightBasedOnChildren(lv_group);
            } else if (requestCode == 101) {
                String value = data.getStringExtra("value");
                tv_class_name.setText(value);
                if (clazz != null) {
                    clazz.setClassName(value);
                }
            } else if (requestCode == 102) {
                String value = data.getStringExtra("value");
                int position = data.getIntExtra("position", -1);
                if (position >= 0 && position < groups.size()) {
                    groups.set(position, value);
                    adapter.notifyDataSetChanged();
                }
            } else if (requestCode == 103) {
                String value = data.getStringExtra("value");
                mClassMail.setText(value);
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
                String date = year + "年" + (month < 10 ? ("0" + month) : month) + "月" + (day < 10 ? ("0" + day) : day) + "日";
                //输出当前日期
                tv_class_time.setText(date);
                if (clazz != null) {
                    String date1 = DateUtil.getInstance("yyyy年MM月dd日").convertDateStr(date, DateUtil.yyyy_MM_dd);
                    clazz.setStartDate(date1);
                }

            }
        });
        dialog.show();

    }

    BottomSheetDialog dialog;

    private void showBottomSheet() {
        View view = LayoutInflater.from(this).inflate(R.layout.city_village, null);
        DoubleListView<SmallRegion, City> dlv = (DoubleListView<SmallRegion, City>) view.findViewById(R.id.dlv);
        dlv.leftAdapter(new SimpleTextAdapter<SmallRegion>(this, left) {
            @Override
            public String getText(SmallRegion data) {
                return data.getRegionalName();
            }
        }).rightAdapter(new SimpleTextAdapter<City>(this, null) {
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
                if (clazz != null) {
                    clazz.setCityId(childItem.getCityId());
                }
                tv_xiaoqu.setText(item.getRegionalName());
                tv_city.setText(childItem.getCityName());
                dialog.dismiss();
            }
        });
        //初始化选中.
        if (left != null && !left.isEmpty()) {
            dlv.setLeftList(left, 0);
            dlv.setRightList(left.get(0).getRegionalCityList(), -1);
        }
        dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);
        dialog.show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                dialog = null;
            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < groups.size(); i++) {
            builder.append(groups.get(i));
            if (i != groups.size() - 1) {
                builder.append(",");
            }
        }
        if (clazz != null) {
            clazz.setClassGroup(builder.toString());
            dialogShow("正在创建班级...");

//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//
//                    try {
//                        EMGroupManager.EMGroupOptions option = new EMGroupManager.EMGroupOptions();
//                        option.maxUsers = 200;
//                        option.style = EMGroupManager.EMGroupStyle.EMGroupStylePrivateMemberCanInvite;//私有群，群成员也能邀请人进群；EMGroupStylePrivateMemberCanInvite
//                        String[] members = {};
//                        EMGroup group = EMClient.getInstance().groupManager().createGroup(clazz.getClassName(), "", members, "", option);
//
//                        String groupId = group.getGroupId();
//                        clazz.setHxGroupId(groupId);

            service.creatClass(UserInfoModel.getInstance().getToken(), clazz, new RequestCallback<ResponseData<LaiClass>>() {
                @Override
                public void success(final ResponseData<LaiClass> data, Response response) {
                    dialogDissmiss();
                    if (data.getStatus() == 200) {
                        UserModel user = UserInfoModel.getInstance().getUser();
                        user.setHasThClass(1);
                        UserInfoModel.getInstance().saveUserCache(user);
                        Intent intent = new Intent(CreateClassActivity.this, ContactsActivity.class);
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

//                                runOnUiThread(new Runnable() {
//                                    public void run() {
//                                        dialogDissmiss();
//                                    }
//                                });
                }

                @Override
                public void failure(RetrofitError error) {
                    super.failure(error);
                    dialogDissmiss();
                    Toast.makeText(CreateClassActivity.this, "创建群组失败", Toast.LENGTH_LONG).show();
//                                runOnUiThread(new Runnable() {
//                                    public void run() {
//                                        dialogDissmiss();
//                                        Toast.makeText(CreateClassActivity.this, "创建群组失败", Toast.LENGTH_LONG).show();
//                                    }
//                                });
                }
            });


//                    } catch (final HyphenateException e) {
//                        e.printStackTrace();
//                        runOnUiThread(new Runnable() {
//                            public void run() {
//                                dialogDissmiss();
//                                Toast.makeText(CreateClassActivity.this, "创建群组失败:" + e.getMessage(), Toast.LENGTH_LONG).show();
//                            }
//                        });
//                    }
//
//                }
//            }).start();
        }
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        String msg = failedRule.getFailureMessage();
        Snackbar.make(tv_title, msg, Snackbar.LENGTH_SHORT).setDuration(2000).show();
    }
}
