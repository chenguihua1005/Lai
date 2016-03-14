package com.softtek.lai.module.File.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
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

    @InjectView(R.id.tv_armgirth)
    TextView tv_armgirth;

    @InjectView(R.id.tv_uparmgirth)
    TextView tv_uparmgirth;

    @InjectView(R.id.tv_upleggirth)
    TextView tv_upleggirth;

    @InjectView(R.id.tv_doleggirth)
    TextView tv_doleggirth;

    @InjectView(R.id.tv_size)
    TextView tv_size;

    @InjectView(R.id.tv_clear)
    TextView tv_clear;

    @InjectView(R.id.rv)
    RulerView rv;
    //布局
    @InjectView(R.id.ll_circum)
    LinearLayout ll_circum;

    @InjectView(R.id.ll_waistline)
    LinearLayout ll_waistline;

    @InjectView(R.id.ll_hiplie)
    LinearLayout ll_hiplie;

    @InjectView(R.id.ll_armgirth)
    LinearLayout ll_armgirth;

    @InjectView(R.id.ll_uparmgirth)
    LinearLayout ll_uparmgirth;

    @InjectView(R.id.ll_upleggirth)
    LinearLayout ll_upleggirth;

    @InjectView(R.id.ll_doleggirth)
    LinearLayout ll_doleggirth;

    private File file;//存储用户表对象


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rv.setCallback(new RulerView.RulerCallback() {
            @Override
            public void resultNum(int num) {
                tv_size.setText(num+"cm");
            }
        });
        ll_circum.setOnClickListener(this);
        ll_waistline.setOnClickListener(this);
        ll_hiplie.setOnClickListener(this);
        ll_armgirth.setOnClickListener(this);
        ll_uparmgirth.setOnClickListener(this);
        ll_upleggirth.setOnClickListener(this);
        ll_doleggirth.setOnClickListener(this);
        tv_clear.setOnClickListener(this);

        tv_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {

        file= (File) getIntent().getSerializableExtra("file");
        tv_circum.setText(file.getCircum()+"");
        tv_left.setText("返回");
        tv_title.setText("添加记录");
        tv_right.setText("保存");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //胸围
            case R.id.ll_circum:
                rv.setCallback(new RulerView.RulerCallback() {
                    @Override
                    public void resultNum(int num) {
                        tv_size.setText(num+"cm");
                        tv_circum.setText(num+"");
                    }
                });
                break;
            case R.id.ll_waistline:
                rv.setCallback(new RulerView.RulerCallback() {
                    @Override
                    public void resultNum(int num) {
                        tv_size.setText(num+"cm");
                        tv_waistline.setText(num+"");
                    }
                });
                break;
            case R.id.ll_hiplie:
                rv.setCallback(new RulerView.RulerCallback() {
                    @Override
                    public void resultNum(int num) {
                        tv_size.setText(num+"cm");
                        tv_hiplie.setText(num+"");
                    }
                });
                break;
            case R.id.ll_armgirth:
                rv.setCallback(new RulerView.RulerCallback() {
                    @Override
                    public void resultNum(int num) {
                        tv_size.setText(num+"cm");
                        tv_armgirth.setText(num+"");
                    }
                });
                break;
            case R.id.ll_uparmgirth:
                rv.setCallback(new RulerView.RulerCallback() {
                    @Override
                    public void resultNum(int num) {
                        tv_size.setText(num+"cm");
                        tv_uparmgirth.setText(num+"");
                    }
                });
                break;
            case R.id.ll_upleggirth:
                rv.setCallback(new RulerView.RulerCallback() {
                    @Override
                    public void resultNum(int num) {
                        tv_size.setText(num+"cm");
                        tv_upleggirth.setText(num+"");
                    }
                });
                break;
            case R.id.ll_doleggirth:
                rv.setCallback(new RulerView.RulerCallback() {
                    @Override
                    public void resultNum(int num) {
                        tv_size.setText(num+"cm");
                        tv_doleggirth.setText(num+"");
                    }
                });
                break;
            case R.id.tv_clear:
                //清除功能......
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
                double armgirth=Double.parseDouble(tv_armgirth.getText().toString().equals("")?"0":tv_armgirth.getText().toString());
                double uparmgirth=Double.parseDouble(tv_uparmgirth.getText().toString().equals("")?"0":tv_uparmgirth.getText().toString());
                double tupleggirth=Double.parseDouble(tv_upleggirth.getText().toString().equals("")?"0":tv_upleggirth.getText().toString());
                double doleggirth=Double.parseDouble(tv_doleggirth.getText().toString().equals("")?"0":tv_doleggirth.getText().toString());
                file.setCircum(circum);
                file.setWaistline(waistline);
                file.setHiplie(hiplie);
                file.setArmgirth(armgirth);
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
