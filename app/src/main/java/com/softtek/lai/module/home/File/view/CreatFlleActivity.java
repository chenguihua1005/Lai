package com.softtek.lai.module.home.File.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.home.File.model.File;
import com.softtek.lai.module.home.File.presenter.CreateFile;
import com.softtek.lai.module.home.File.presenter.CreateFilepresenter;
import com.softtek.lai.module.home.tab.TabMainActivity;

import butterknife.InjectView;
import zilla.libcore.file.PropertiesManager;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_creatfile)
public class CreatFlleActivity extends BaseActivity implements View.OnClickListener,Validator.ValidationListener{

    private int gener=0;
    private CreateFilepresenter createFilepresenter;
//    ,CreateFile.ICreateFileView
    @LifeCircleInject
    ValidateLife validateLife;

    @InjectView(R.id.nickname)
    EditText nickname;

    @InjectView(R.id.birth)
    EditText birth;

    @InjectView(R.id.sex)
    EditText sex;

    @InjectView(R.id.height)
    EditText height;

    @InjectView(R.id.weight)
    EditText weight;

    @InjectView(R.id.btn_Add_bodydimension)
    Button btn_Add_bodydimension;

    @InjectView(R.id.btn_finish)
    Button btn_finish;

    private String SexData[] = {"男", "女" };//性别数据
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        btn_finish.setOnClickListener(this);
        sex.setOnClickListener(this);
        height.setOnClickListener(this);
//        String nickname=nickname.getText().toString();
//        String birthday=birth.getText().toString();
//        CreateFilepresenter.doFile(nickname,brithday, height, weight, gender);


//        tiaoguo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                  Intent intent=new Intent(CreatFlleActivity.this, TabMainActivity.class);
//                  startActivity(intent);
//            }
//        });


//       birth.setOnFocusChangeListener(new android.view.View.
//                OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    // 此处为得到焦点时的处理内容
//                    DatePickerDialog dialog=new DatePickerDialog(
//                            CreatFlleActivity.this, new DatePickerDialog.OnDateSetListener() {
//                        @Override
//                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                            String Date = Integer.toString(year)+"年"+Integer.toString(monthOfYear+1)+"月"+Integer.toString(dayOfMonth)+"日";
//                            birth.setText(Date);
//                        }
//                    },1980,8,17);
//                   // Window window=dialog.getWindow();
//                    WindowManager.LayoutParams wlp=dialog.getWindow().getAttributes();
//                    wlp.gravity=Gravity.BOTTOM;
//                    wlp.width=WindowManager.LayoutParams.MATCH_PARENT;
//                    dialog.getWindow().setAttributes(wlp);
//                    dialog.show();
//
//                } else {
//                    // 此处为失去焦点时的处理内容
//                }
//            }
//        });

//       sex.setOnClickListener(new View.OnClickListener() {
//
//
//            public void onClick(View v) {
//
//                    Dialog dialog = new AlertDialog.Builder(CreatFlleActivity.this)
//                            .setSingleChoiceItems(SexData, 0,
//                                    new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            sex.setText(SexData[which]);
//                                            dialog.dismiss();
//                                        }
//                                    })
//                        .setNegativeButton("取消",null)
//                        .setPositiveButton("确认",null)
//
//                            .create();
//                    WindowManager.LayoutParams wlp = dialog.getWindow().getAttributes();
//                    wlp.gravity = Gravity.BOTTOM;
//                    wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
//                    dialog.getWindow().setAttributes(wlp);
//                    dialog.show();
//            }
//       });

   }

    @Override
    protected void initViews() {
        setActionBarLayout(R.layout.actionbar);
    }

    @Override
    protected void initDatas() {
        createFilepresenter= new CreateFile(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_finish:
                validateLife.validate();
                break;
            case R.id.sex: {
                if (sex.getText().equals("男")){
                    gener=1;

                }
                else {
                    gener=0;

                }

            }
            case R.id.height:{
                if (sex.getText().equals("男")){
                    gener=1;

                }
                else {
                    gener=0;

                }
            }
        }
    }

    private void setActionBarLayout(int layoutId){
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);

            actionBar.setCustomView(layoutId);
            View v=actionBar.getCustomView();
            v.findViewById(R.id.tv_left).setOnClickListener(this);
            v.findViewById(R.id.tv_right).setOnClickListener(this);
        }
    }

    @Override
    public void onValidationSucceeded() {
//        String appid=PropertiesManager.get("appid");
        String nickn=nickname.getText().toString();
//        String nickname=nickname.getText().toString();
//        String birth=birth.getText().toString();
//        String sex=sex.getText().toString();
//        String height=height.getText().toString();
//        String weight=weight.getText().toString();

        String bir=birth.getText().toString();
        int se=gener;
        int heig=Integer.getInteger(height.getText().toString());
        int weigh=Integer.getInteger(weight.getText().toString());

        File file =new File(nickn,bir,se,heig,weigh);
        file.setNickname(nickn);
        file.setBirthday(bir);
        file.setGender(se);
        file.setHeight(heig);
        file.setWeight(weigh);


        String token=SharedPreferenceService.getInstance().get("token","");
//        createFilepresenter.CreateFile(nickname,birth,sex,height,weight);
//        createFilepresenter.createFile("","",file);

    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView,failedRule);
    }






//    @Override
//    public void toActivity() {
//        startActivity(new Intent(this, TabMainActivity.class));
//    }
}
