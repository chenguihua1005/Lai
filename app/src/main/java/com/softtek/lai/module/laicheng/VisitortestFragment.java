package com.softtek.lai.module.laicheng;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Regex;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.activity.model.ActtypeModel;
import com.softtek.lai.module.bodygame3.activity.view.CreateActActivity;
import com.softtek.lai.module.bodygame3.home.view.MoreFragment;
import com.softtek.lai.module.bodygame3.more.model.ClassModel;
import com.softtek.lai.module.bodygame3.more.view.MoreHasFragment;
import com.softtek.lai.module.laicheng.model.BleMainData;
import com.softtek.lai.module.laicheng.model.VisitorModel;
import com.softtek.lai.module.laicheng.model.Visitsmodel;
import com.softtek.lai.module.laicheng.presenter.VisitorPresenter;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.ShareUtils;
import com.softtek.lai.utils.SoftInputUtil;
import com.softtek.lai.widgets.CustomDialog;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.InjectView;
import butterknife.OnClick;
import zilla.libcore.file.AddressManager;
import zilla.libcore.lifecircle.LifeCircleInject;

import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.fragment_visitortest)
public class VisitortestFragment extends LazyBaseFragment<VisitorPresenter> implements VisitorPresenter.VisitorView, View.OnClickListener, Validator.ValidationListener {
    private VisitortestFragment.VisitorVoiceListener listener;
    private VisitortestFragment.ShakeOFF shakeOFF;

//    @LifeCircleInject
//     ValidateLife validateLife;

    private Validator validator;
    @InjectView(R.id.bt_again)
    Button bt_again;
    private LinearLayout.LayoutParams parm;
    @InjectView(R.id.tv_weight)
    TextView tv_weight;//体重
    @InjectView(R.id.tv_weight_caption)
    TextView tv_weight_caption;//状态
    @InjectView(R.id.tv_body_fat_rate)
    TextView tv_body_fat_rate;//体脂率
    @InjectView(R.id.tv_bmi)
    TextView tv_bmi;//BMI;
    @InjectView(R.id.tv_internal_fat_rate)
    TextView tv_internal_fat_rate;//内脂率
    @InjectView(R.id.iv_voice)
    ImageView iv_voice;
    @InjectView(R.id.tv_info_state)
    TextView mBleState;


    @InjectView(R.id.bt_create)
    Button bt_create;//
    @InjectView(R.id.bt_history)
    Button bt_history;

    //访客信息
    @InjectView(R.id.ll_visitor)
    LinearLayout ll_visitor;
    @InjectView(R.id.tv_name)
    TextView tv_name;
    @InjectView(R.id.tv_phoneNo)
    TextView tv_phoneNo;
    @InjectView(R.id.tv_age)
    TextView tv_age;
    @InjectView(R.id.tv_gender)
    TextView tv_gender;
    @InjectView(R.id.tv_height)
    TextView tv_height;


    VisitorModel visitorModel = new VisitorModel();
    private int gender = 0;
    private int visitorId;
    private String date;
//    private boolean isPlay = true;

    public VisitortestFragment() {
        // Required empty public constructor
    }


    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onValidationSucceeded() {
   Log.i("chenggong","4566");
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
      Log.i("shibai","12444");
       if(failedView.getId()==et_mobile.getId()){
           Util.toastMsg("手机号码不为空");
       }
    }

//    @Override
//    public void onValidationSucceeded() {
//
//    }
//
//    @Override
//    public void onValidationFailed(View failedView, Rule<?> failedRule) {
//        v.onValidationFailed(failedView, failedRule);
//    }

    public interface VisitorVoiceListener {
        void onVisitorVoiceListener();
    }

    public interface ShakeOFF {
        void setOnShakeOFF();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof VisitortestFragment.VisitorVoiceListener) {
            listener = (VisitortestFragment.VisitorVoiceListener) context;
            shakeOFF = (VisitortestFragment.ShakeOFF) context;
        }
    }

    @Override
    protected void initViews() {
        bt_again.setOnClickListener(this);
        bt_create.setOnClickListener(this);
        bt_history.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        validator = new Validator(this);
        validator.setValidationListener(this);
        setPresenter(new VisitorPresenter(this));
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/wendy.ttf");
        tv_weight.setTypeface(tf);
    }

    //语音
    @OnClick(R.id.iv_voice)
    public void onClick() {
        if (MainBaseActivity.isVoiceHelp) {
            iv_voice.setImageDrawable(getResources().getDrawable(R.drawable.voice_icon_off));
        } else {
            iv_voice.setImageDrawable(getResources().getDrawable(R.drawable.voice_icon));
        }
        if (listener != null) {
            listener.onVisitorVoiceListener();
        }
    }

    //    访客基本信息弹框
    EditText et_old;

    @Required(order = 1, messageResId = R.string.phoneValidateNull)
    @Regex(order = 2, patternResId = R.string.phonePattern, messageResId = R.string.phoneValidate)
    EditText et_mobile;

    private void showTypeDialog() {
        final Dialog dialog = new Dialog(getContext(), R.style.Dialog);
        dialog.show();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.info_visitor, null);
        RelativeLayout ll_area = (RelativeLayout) view.findViewById(R.id.rl_area);
        final EditText et_name = (EditText) view.findViewById(R.id.et_name);
        et_old = (EditText) view.findViewById(R.id.et_old);
        final EditText et_height = (EditText) view.findViewById(R.id.et_height);
        et_mobile = (EditText) view.findViewById(R.id.et_mobile);
        RadioGroup rg_up = (RadioGroup) view.findViewById(R.id.rg_up);
        Button btn_commit = (Button) view.findViewById(R.id.btn_commit);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        //设置dialog的宽高为屏幕的宽高
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, height);
        dialog.setContentView(view, layoutParams);
        et_old.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et_old.getWindowToken(), 0);
                showDate();
            }
        });

        rg_up.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int groupId = group.getCheckedRadioButtonId();
                switch (groupId) {
                    case R.id.rb_male:
                        gender = 0;//男
                        break;
                    case R.id.rb_female:
                        gender = 1; //女
                        break;
                }
            }
        });
        ll_area.setClickable(true);
        btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                visitorModel.setName(et_name.getText().toString());
//                visitorModel.setHeight(Float.parseFloat(et_height.getText().toString()));
//                visitorModel.setAge(et_old.getText().toString());
//                visitorModel.setPhoneNo(et_mobile.getText().toString());
//                visitorModel.setGender(gender);
//                getPresenter().commitData(UserInfoModel.getInstance().getToken(), visitorModel);
                et_mobile.setError(null);
                validator.validate();
                dialog.dismiss();
//                shakeOFF.setOnShakeOFF();
            }
        });
        ll_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    //日期控件
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date curDate = new Date(System.currentTimeMillis());//获取当前时间
    String str = formatter.format(curDate);

    private void showDate() {
        final Calendar c = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), null, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(c.getTime().getTime());
        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                datePickerDialog.cancel();
            }
        });
        datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatePicker datePicker = datePickerDialog.getDatePicker();
                int year = datePicker.getYear();
                int month = datePicker.getMonth() + 1;
                int day = datePicker.getDayOfMonth();
                date = year + "-" + (month < 10 ? ("0" + month) : month) + "-" + (day < 10 ? ("0" + day) : day);
                Log.i("日期", date);
                int compare = date.compareTo(str);
                Log.e("132", compare + "");
                if (compare <= 0) {
                    Log.i("日期", date);
                    et_old.setText(date);
                }

            }
        });
        datePickerDialog.show();
    }

    public VisitorModel getVisitorModel() {
        return visitorModel;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_again:
                break;
            case R.id.bt_create:
//                showTypeDialog();
//                startActivity(new Intent(getActivity(),VisitorinfoActivity.class));
                Intent in=new Intent(getActivity(),VisitorinfoActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(in);
                break;
            case R.id.bt_history:
                Intent intent = new Intent(getActivity(), VisithistoryActivity.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    public void commit(Visitsmodel visitsmodel, VisitorModel model) {
        if (model != null) {
            ll_visitor.setVisibility(View.VISIBLE);
            tv_name.setText(model.getName());
            tv_phoneNo.setText(model.getPhoneNo());
            tv_age.setText(model.getBirthDate());
            if (0 == model.getGender()) {
                tv_gender.setText("男");
            } else {
                tv_gender.setText("女");
            }
            tv_height.setText(model.getGender());

        }
        if (visitsmodel != null) {
            visitorId = visitsmodel.getVisitorId();
            model.setVisitorId(visitorId);
        }
    }

    @SuppressLint("SetTextI18n")
    public void UpdateData(BleMainData data) {
        tv_weight.setText(data.getWeight_item().getValue() + "");//体重
        tv_weight_caption.setText(data.getWeight_con().getCaption());//状态
        tv_body_fat_rate.setText(data.getBodyfatrate() + "%");
        tv_bmi.setText(data.getBmi() + "");
        tv_internal_fat_rate.setText(data.getVisceralfatindex() + "%");
    }

    public void refreshVoiceIcon(){
        if (MainBaseActivity.isVoiceHelp) {
            iv_voice.setImageDrawable(getResources().getDrawable(R.drawable.voice_icon));
        }else {
            iv_voice.setImageDrawable(getResources().getDrawable(R.drawable.voice_icon_off));
        }
    }
}
