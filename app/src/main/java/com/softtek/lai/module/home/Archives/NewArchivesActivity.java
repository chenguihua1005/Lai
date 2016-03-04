package com.softtek.lai.module.home.Archives;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.home.tab.TabMainActivity;

import java.util.Date;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

//@InjectLayout(R.layout.activity_new_archives)
public class NewArchivesActivity extends BaseActivity {

//    @InjectView(R.id.nickname)
//    LinearLayout nickname;

    private EditText nickname;
    private EditText birth;
    private EditText sex;
    private EditText height;
    private EditText weight;
    private TextView tiaoguo;
//    @InjectView(R.id.tiaoguo)
//    LinearLayout tiaoguo;


    private String SexData[] = {"男", "女" };//性别数据
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_archives);

//        用户会话：token,
//                应用标识：appid，
//        昵称：nickname(string),
//                生日：brithday(string   yyyy-MM-dd),
//                身高：height(int),
//        体重：weight(int),
//        性别 :gender(int 0女，1男)


        nickname= (EditText) findViewById(R.id.nickname);
        birth= (EditText) findViewById(R.id.birth);
        sex= (EditText) findViewById(R.id.sex);
        height= (EditText) findViewById(R.id.height);
        weight= (EditText) findViewById(R.id.weight);
        tiaoguo= (TextView) findViewById(R.id.tiaoguo);
        tiaoguo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NewArchivesActivity.this, TabMainActivity.class);
                startActivity(intent);
            }
        });


//        nickname
//        height
//        weight

       birth.setOnFocusChangeListener(new android.view.View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    DatePickerDialog dialog=new DatePickerDialog(
                            NewArchivesActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            String Date = Integer.toString(year)+"年"+Integer.toString(monthOfYear+1)+"月"+Integer.toString(dayOfMonth)+"日";
                            birth.setText(Date);
                        }
                    },1980,8,17);
                   // Window window=dialog.getWindow();
                    WindowManager.LayoutParams wlp=dialog.getWindow().getAttributes();
                    wlp.gravity=Gravity.BOTTOM;
                    wlp.width=WindowManager.LayoutParams.MATCH_PARENT;
                    dialog.getWindow().setAttributes(wlp);
                    dialog.show();

                } else {
                    // 此处为失去焦点时的处理内容
                }
            }
        });


//       sex.setOnFocusChangeListener(new android.view.View.
//                OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//
//                    // 此处为得到焦点时的处理内容
//                    Dialog dialog=new android.app.AlertDialog.Builder(NewArchivesActivity.this)
//                            .setSingleChoiceItems(SexData,0,
//                                    new DialogInterface.OnClickListener(){
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                                sex.setText(SexData[which]);
//                                        }
//                                    })
//                            .setNegativeButton("取消",null)
//                            .setPositiveButton("确认",null).create();
//                    WindowManager.LayoutParams wlp=dialog.getWindow().getAttributes();
//                    wlp.gravity=Gravity.BOTTOM;
//                    wlp.width=WindowManager.LayoutParams.MATCH_PARENT;
//                    dialog.getWindow().setAttributes(wlp);
//                    dialog.show();
//                } else {
//                    // 此处为失去焦点时的处理内容
//                }
//            }
//       });
        sex.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {

                    Dialog dialog = new AlertDialog.Builder(NewArchivesActivity.this)
                            .setSingleChoiceItems(SexData, 0,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            sex.setText(SexData[which]);
                                            dialog.dismiss();
                                        }
                                    })
//                        .setNegativeButton("取消",null)
//                        .setPositiveButton("确认",null)
                            .create();
                    WindowManager.LayoutParams wlp = dialog.getWindow().getAttributes();
                    wlp.gravity = Gravity.BOTTOM;
                    wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    dialog.getWindow().setAttributes(wlp);
                    dialog.show();
                }


        });

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {

    }
}
