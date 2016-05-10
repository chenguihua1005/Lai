package com.softtek.lai.module.personalPK.view;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.personalPK.model.PKCreatModel;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_create_pk)
public class CreatePKActivity extends BaseActivity implements View.OnClickListener{




    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;

    //选择筹码和规则
    @InjectView(R.id.cb_targer_km)
    CheckBox cb_targer_km;
    @InjectView(R.id.cb_target_bushu)
    CheckBox cb_target_bushu;
    @InjectView(R.id.rb_naixi)
    CheckBox cb_naixi;
    @InjectView(R.id.rb_naixicao)
    CheckBox cb_naixicao;
    @InjectView(R.id.rb_zidingyi)
    CheckBox cb_zidingyi;

    //公里数单选按钮组
    @InjectView(R.id.rg_km)
    RadioGroup rg_km;
    @InjectView(R.id.five_km)
    RadioButton five_km;
    @InjectView(R.id.ten_km)
    RadioButton ten_km;
    @InjectView(R.id.twenty_one_km)
    RadioButton twenty_one_km;
    @InjectView(R.id.forty_two_km)
    RadioButton forty_two_km;

    //自定义规则接受框
    @InjectView(R.id.et_content)
    EditText et_content;

    PKCreatModel model;


    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        cb_targer_km.setOnClickListener(this);
        cb_target_bushu.setOnClickListener(this);
        cb_naixi.setOnClickListener(this);
        cb_naixicao.setOnClickListener(this);
        cb_zidingyi.setOnClickListener(this);
        rg_km.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                changeColor(checkedId);
                switch (checkedId){
                    case R.id.five_km:
                        model.setTarget(5000);
                        break;
                    case R.id.ten_km:
                        model.setTarget(10000);
                        break;
                    case R.id.twenty_one_km:
                        model.setTarget(21000);
                        break;
                    case R.id.forty_two_km:
                        model.setTarget(42000);
                        break;
                }
            }
        });
        tv_title.setText("选择PK挑战规则和筹码");
        tv_right.setText("下一步");
    }

    @Override
    protected void initDatas() {
        model=new PKCreatModel();
        cb_targer_km.setChecked(true);
        cb_naixi.setChecked(true);
        model.setTargetType(Constants.KM);
        model.setChipType(Constants.NAIXI);
        model.setChip("");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
            case R.id.fl_right:
                //下一步
                Intent pk=new Intent(this,SearchActivity.class);
                saveValue();
                pk.putExtra("pkmodel",model);
                startActivity(pk);
                break;
            case R.id.cb_targer_km:
                if(cb_targer_km.isChecked()){
                    cb_target_bushu.setChecked(false);
                    model.setTargetType(Constants.KM);
                }else{
                    cb_target_bushu.setChecked(true);
                    model.setTargetType(Constants.BUSHU);
                }
                break;
            case R.id.cb_target_bushu:
                if(cb_target_bushu.isChecked()){
                    cb_targer_km.setChecked(false);
                    model.setTargetType(Constants.BUSHU);
                    model.setTarget(0);
                }else{
                    cb_targer_km.setChecked(true);
                    model.setTargetType(Constants.KM);
                }
                break;
            case R.id.rb_naixi:
                cb_naixi.setChecked(true);
                cb_naixicao.setChecked(false);
                cb_zidingyi.setChecked(false);
                model.setChipType(Constants.NAIXI);
                model.setChip("");
                break;
            case R.id.rb_naixicao:
                cb_naixicao.setChecked(true);
                cb_naixi.setChecked(false);
                cb_zidingyi.setChecked(false);
                model.setChipType(Constants.NAIXICAO);
                model.setChip("");
                break;
            case R.id.rb_zidingyi:
                cb_zidingyi.setChecked(true);
                cb_naixi.setChecked(false);
                cb_naixicao.setChecked(false);
                model.setChipType(Constants.ZIDINGYI);
                model.setChip(et_content.getText().toString());
                break;

        }
    }

    private void changeColor(int id){
        switch (id){
            case R.id.five_km:
                five_km.setTextColor(0xFFFFFFFF);
                ten_km.setTextColor(0xFF6F6F6F);
                twenty_one_km.setTextColor(0xFF6F6F6F);
                forty_two_km.setTextColor(0xFF6F6F6F);
                break;
            case R.id.ten_km:
                five_km.setTextColor(0xFF6F6F6F);
                ten_km.setTextColor(0xFFFFFFFF);
                twenty_one_km.setTextColor(0xFF6F6F6F);
                forty_two_km.setTextColor(0xFF6F6F6F);
                break;
            case R.id.twenty_one_km:
                five_km.setTextColor(0xFF6F6F6F);
                ten_km.setTextColor(0xFF6F6F6F);
                twenty_one_km.setTextColor(0xFFFFFFFF);
                forty_two_km.setTextColor(0xFF6F6F6F);
                break;
            case R.id.forty_two_km:
                five_km.setTextColor(0xFF6F6F6F);
                ten_km.setTextColor(0xFF6F6F6F);
                twenty_one_km.setTextColor(0xFF6F6F6F);
                forty_two_km.setTextColor(0xFFFFFFFF);
                break;
        }
    }

    private void saveValue(){
        //先检查选择的比赛规则
        if(cb_targer_km.isChecked()){
            model.setTargetType(Constants.KM);
            int id=rg_km.getCheckedRadioButtonId();
            switch (id){
                case R.id.five_km:
                    model.setTarget(5000);
                    break;
                case R.id.ten_km:
                    model.setTarget(10000);
                    break;
                case R.id.twenty_one_km:
                    model.setTarget(21000);
                    break;
                case R.id.forty_two_km:
                    model.setTarget(42000);
                    break;
            }
        }else{
            model.setTargetType(Constants.BUSHU);
            model.setTarget(0);
        }
        //再检查选择筹码
        if(cb_naixi.isChecked()){
            model.setChipType(Constants.NAIXI);
            model.setChip("");
        }else if(cb_naixicao.isChecked()){
            model.setChipType(Constants.NAIXICAO);
            model.setChip("");
        }else{
            model.setChipType(Constants.ZIDINGYI);
            model.setChip(et_content.getText().toString());
        }


    }
}
