package com.softtek.lai.module.customermanagement.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.more.net.MoreService;
import com.softtek.lai.module.bodygame3.more.view.CreateGroupActivity;
import com.softtek.lai.module.bodygame3.more.view.MakiBottomDialog;
import com.softtek.lai.module.customermanagement.model.ClubAndCityModel;
import com.softtek.lai.module.customermanagement.service.ClubService;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.RequestCallback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jia.lu on 1/10/2018.
 */

public class RestartClassActivity extends MakiBaseActivity implements View.OnClickListener {
    private RelativeLayout mClubContent;
    private RelativeLayout mClassContent;
    private RelativeLayout mCalendarContent;
    private TextView mClubText;
    private TextView mClassText;
    private TextView mCalendarText;
    private TextView mSubmit;
    private LinearLayout mBack;
    private TextView mTitle;

    private int currentYear;
    private int currentMonth;
    private int currentDay;

    private String classId;
    //    private LaiClass clazz;
    private List<ClubAndCityModel.ClubsBean> clubs = new ArrayList<>();
    private MoreService moreService;
    private ClubService clubService;
    private AlertDialog classNameDialog;
    private MakiBottomDialog sheetDialog;
    private List<String> clubName;
    private PostData postData = new PostData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        moreService = ZillaApi.NormalRestAdapter.create(MoreService.class);
        clubService = ZillaApi.NormalRestAdapter.create(ClubService.class);
        setContentView(R.layout.activity_restart_class);
        initView();
        initData();
    }

    private void initView() {
        mClubContent = findViewById(R.id.rl_club_name);
        mClubText = findViewById(R.id.tv_club_name);
        mClassContent = findViewById(R.id.rl_class_name);
        mClassText = findViewById(R.id.tv_class_name);
        mCalendarContent = findViewById(R.id.rl_date);
        mCalendarText = findViewById(R.id.tv_class_time);
        mSubmit = findViewById(R.id.tv_right);
        mBack = findViewById(R.id.ll_left);
        mTitle = findViewById(R.id.tv_title);
        mCalendarContent.setOnClickListener(this);
        mClassContent.setOnClickListener(this);
        mClubContent.setOnClickListener(this);
        mSubmit.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mTitle.setText("重新开班");
        mSubmit.setText("确定");
    }

    private void initData() {
//        clazz = new LaiClass();
        currentYear = DateUtil.getInstance().getCurrentYear();
        currentMonth = DateUtil.getInstance().getCurrentMonth();
        currentDay = DateUtil.getInstance().getCurrentDay();
        int afterDay = currentDay;
        String currentDate = currentYear + "年" + (currentMonth < 10 ? "0" + currentMonth : currentMonth) + "月" + (afterDay < 10 ? "0" + afterDay : afterDay) + "日";
        mCalendarText.setText(currentDate);
        classId = getIntent().getStringExtra("classId");
        moreService.getRegionalAndCitys(UserInfoModel.getInstance().getToken(), new RequestCallback<ResponseData<ClubAndCityModel>>() {
            @Override
            public void success(ResponseData<ClubAndCityModel> data, Response response) {
                if (data.getStatus() == 200) {
                    clubs = data.getData().getClubs();
                } else {
                    Toast.makeText(RestartClassActivity.this, data.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }

    private void showDateDialog() {
        Calendar c = Calendar.getInstance();
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
                mCalendarText.setText(date);
//                if (clazz != null) {
                String date1 = DateUtil.getInstance("yyyy年MM月dd日").convertDateStr(date, DateUtil.yyyy_MM_dd);
                postData.setStartDate(date1);
//                }

            }
        });
        dialog.show();
    }


    private void showClassNameDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_rename, null);
        final EditText mInput = dialogView.findViewById(R.id.edt_rename);
        Button mOk = dialogView.findViewById(R.id.btn_rename);
        Button mCancel = dialogView.findViewById(R.id.btn_cancel);
        TextView mDialogTitle = dialogView.findViewById(R.id.title);
        mDialogTitle.setText("班级名称");
        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInput.getText().toString().trim().equals("")) {
                    Toast.makeText(RestartClassActivity.this, "班级名不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    mClassText.setText(mInput.getText().toString().trim());
                }
                classNameDialog.dismiss();
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classNameDialog.dismiss();
            }
        });
        if (classNameDialog == null) {
            classNameDialog = new AlertDialog.Builder(this).create();
            classNameDialog.setView(dialogView, 0, 0, 0, 0);
        }
        classNameDialog.show();
    }


    private void showClubNameDialog() {
        sheetDialog = new MakiBottomDialog(this);
        View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_bottom_sheet, null);
        ListView listView = dialogView.findViewById(R.id.lv_content);
        clubName = new ArrayList<>();
        for (int i = 0; i < clubs.size(); i++) {
            clubName.add(clubs.get(i).getClubName());
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, clubName);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mClubText.setText(((TextView) view).getText().toString());
//                clazz.setClubId(clubs.get(i).getClubId());
                postData.setClubId(clubs.get(i).getClubId());
                sheetDialog.dismiss();
            }
        });
        sheetDialog.setContentView(dialogView);
        if (clubName.size() < 1) {
            Toast.makeText(this, "当前没有俱乐部", Toast.LENGTH_SHORT).show();
            return;
        }
        sheetDialog.show();
    }

    private void doRestartClass() {
        postData.setClassId(classId);
        postData.setClassName(mClassText.getText().toString());
        dialogShow("加载中...");
        clubService.reEstablishClass(UserInfoModel.getInstance().getToken(), postData, new RequestCallback<ResponseData<RestartResponse>>() {
            @Override
            public void success(ResponseData<RestartResponse> data, Response response) {
                dialogDismiss();
                if (data.getStatus() == 200) {
                    showConfirmDialog(data.getData());
                } else {
                    Toast.makeText(RestartClassActivity.this, data.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                dialogDismiss();
                super.failure(error);
            }
        });
    }

    private AlertDialog confirmDialog;

    private void showConfirmDialog(final RestartResponse response) {
        confirmDialog = new AlertDialog.Builder(this)
                .setMessage("系统会取所有班级成员中今天最新的一条健康记录作为新班级的初始记录，是否确认开班")
                .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(RestartClassActivity.this, CreateGroupActivity.class);
                        intent.putExtra("classId", response.getClassId());
                        intent.putExtra("createClass", true);
                        confirmDialog.dismiss();
                        startActivity(intent);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        confirmDialog.dismiss();
                    }
                }).create();
        confirmDialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_club_name:
                showClubNameDialog();
                break;
            case R.id.rl_class_name:
                showClassNameDialog();
                break;
            case R.id.rl_date:
                showDateDialog();
                break;
            case R.id.tv_right:
                doRestartClass();
                break;
            case R.id.ll_left:
                finish();
                break;
        }
    }

    public static class PostData {
        private String ClassId;
        private String ClassName;
        private String StartDate;
        private String ClubId;

        public String getClassId() {
            return ClassId;
        }

        public void setClassId(String classId) {
            ClassId = classId;
        }

        public String getClassName() {
            return ClassName;
        }

        public void setClassName(String className) {
            ClassName = className;
        }

        public String getStartDate() {
            return StartDate;
        }

        public void setStartDate(String startDate) {
            StartDate = startDate;
        }

        public String getClubId() {
            return ClubId;
        }

        public void setClubId(String clubId) {
            ClubId = clubId;
        }
    }

    public static class RestartResponse {

        /**
         * ClassId : ece4c2f6-6cce-4c5b-84bf-9dc79a75aae3
         * ClassCode : 20171227105518236
         */

        private String ClassId;
        private String ClassCode;

        public String getClassId() {
            return ClassId;
        }

        public void setClassId(String ClassId) {
            this.ClassId = ClassId;
        }

        public String getClassCode() {
            return ClassCode;
        }

        public void setClassCode(String ClassCode) {
            this.ClassCode = ClassCode;
        }
    }
}
