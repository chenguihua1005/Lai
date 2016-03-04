package com.softtek.lai.module.home.File.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.home.tab.TabMainActivity;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_creatfile)
public class CreatFlleActivity extends BaseActivity{

//    private EditText nickname;
//    private EditText birth;
//    private EditText sex;
//    private EditText height;
//    private EditText weight;
//    private TextView tiaoguo;


      @InjectView(R.id.tiaoguo)
      TextView tiaoguo;


    //private String SexData[] = {"男", "女" };//性别数据
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        nickname= (EditText) findViewById(R.id.nickname);
//        birth= (EditText) findViewById(R.id.birth);
//        sex= (EditText) findViewById(R.id.sex);
//        height= (EditText) findViewById(R.id.height);
//        weight= (EditText) findViewById(R.id.weight);
//        tiaoguo= (TextView) findViewById(R.id.tiaoguo);
          tiaoguo.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent=new Intent(CreatFlleActivity.this, TabMainActivity.class);
                  startActivity(intent);
              }
          });


//        nickname
//        height
//        weight

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
//
//
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

//                            .create();
//                    WindowManager.LayoutParams wlp = dialog.getWindow().getAttributes();
//                    wlp.gravity = Gravity.BOTTOM;
//                    wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
//                    dialog.getWindow().setAttributes(wlp);
//                    dialog.show();
//            }
//       });
//
   }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {

    }

}
