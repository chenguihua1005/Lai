package com.softtek.lai.module.File.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ggx.ruler_lib.RulerView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.File.model.File;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_dimension_record)
public class DimensionRecordActivity extends BaseActivity implements OnClickListener{

    //toolbar布局控件
    @InjectView(R.id.tv_left)
    TextView tv_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.tv_right)
    TextView tv_right;
    //TextView
    @InjectView(R.id.tv_circum)
    TextView tv_circum;

    @InjectView(R.id.tv_waistline)
    TextView tv_waistline;

    @InjectView(R.id.tv_hiplie)
    TextView tv_hiplie;

    @InjectView(R.id.tv_uparmgirth)
    TextView tv_uparmgirth;

    @InjectView(R.id.tv_upleggirth)
    TextView tv_upleggirth;

    @InjectView(R.id.tv_doleggirth)
    TextView tv_doleggirth;



    @InjectView(R.id.ll_circum)
    RelativeLayout ll_circum;

    @InjectView(R.id.ll_waistline)
    RelativeLayout ll_waistline;

    @InjectView(R.id.ll_hiplie)
    RelativeLayout ll_hiplie;

    @InjectView(R.id.ll_uparmgirth)
    RelativeLayout ll_uparmgirth;

    @InjectView(R.id.ll_upleggirth)
    RelativeLayout ll_upleggirth;

    @InjectView(R.id.ll_doleggirth)
    RelativeLayout ll_doleggirth;

    private File file;//存储用户表对象


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ll_circum.setOnClickListener(this);
        ll_waistline.setOnClickListener(this);
        ll_hiplie.setOnClickListener(this);
        ll_uparmgirth.setOnClickListener(this);
        ll_upleggirth.setOnClickListener(this);
        ll_doleggirth.setOnClickListener(this);


        tv_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {

        file= (File) getIntent().getSerializableExtra("file");
        tv_circum.setText(file.getCircum()==0?"":file.getCircum()+"");
        tv_waistline.setText(file.getWaistline()==0?"":file.getWaistline()+"");
        tv_uparmgirth.setText(file.getUparmgirth()==0?"":file.getUparmgirth()+"");
        tv_doleggirth.setText(file.getDoleggirth()==0?"":file.getDoleggirth()+"");
        tv_upleggirth.setText(file.getUpleggirth()==0?"":file.getUpleggirth()+"");
        tv_hiplie.setText(file.getHiplie()==0?"":file.getHiplie()+"");
        tv_left.setText("返回");
        tv_title.setText("添加记录");
        tv_right.setText("保存");
    }
    public void show_height_dialog() {
        final Dialog height_dialog = new Dialog(this);
        height_dialog.setTitle("选择腰围");
        height_dialog.setContentView(R.layout.dialog);
        Button b1 = (Button) height_dialog.findViewById(R.id.button1);
        Button b2 = (Button) height_dialog.findViewById(R.id.button2);
        final NumberPicker np = (NumberPicker) height_dialog.findViewById(R.id.numberPicker1);
        np.setMaxValue(220);
        np.setMinValue(50);
        np.setWrapSelectorWheel(false);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_circum.setText(String.valueOf(np.getValue())); //set the value to textview
                height_dialog.dismiss();
            }
        });
        height_dialog.show();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_circum:
                    show_height_dialog();
                break;
            case R.id.ll_waistline:

                break;
            case R.id.ll_hiplie:

                break;
            case R.id.ll_uparmgirth:

                break;
            case R.id.ll_upleggirth:

                break;
            case R.id.ll_doleggirth:

                break;


            case R.id.tv_left:
                startActivity(new Intent(DimensionRecordActivity.this,CreatFlleActivity.class));
                break;
            //保存记录......
            case R.id.tv_right:
                /*getIntent().putExtra("","token");
                String token= SharedPreferenceService.getInstance().get("token","");*/

                double circum=Double.parseDouble(tv_circum.getText().toString().equals("")?"0":(tv_circum.getText().toString()));
                double waistline=Double.parseDouble(tv_waistline.getText().toString().equals("")?"0":tv_waistline.getText().toString());
                double hiplie=Double.parseDouble(tv_hiplie.getText().toString().equals("")?"0":tv_hiplie.getText().toString());
                double uparmgirth=Double.parseDouble(tv_uparmgirth.getText().toString().equals("")?"0":tv_uparmgirth.getText().toString());
                double tupleggirth=Double.parseDouble(tv_upleggirth.getText().toString().equals("")?"0":tv_upleggirth.getText().toString());
                double doleggirth=Double.parseDouble(tv_doleggirth.getText().toString().equals("")?"0":tv_doleggirth.getText().toString());
                file.setCircum(circum);
                file.setWaistline(waistline);
                file.setHiplie(hiplie);
                file.setUparmgirth(uparmgirth);
                file.setUpleggirth(tupleggirth);
                file.setDoleggirth(doleggirth);
                Intent intent=new Intent();
                intent.putExtra("file",file);
                setResult(RESULT_OK,intent);
                finish();
                break;

        }
    }


}
