package com.softtek.lai.module.retest;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.retest.model.RetestAudit;
import com.softtek.lai.module.retest.model.RetestWrite;
import com.softtek.lai.module.retest.present.RetestPre;
import com.softtek.lai.module.retest.present.RetestclassImp;
import com.softtek.lai.utils.DisplayUtil;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_audit)
public class Audit extends BaseActivity implements View.OnClickListener{
    //标题栏
    @InjectView(R.id.tv_title)
    TextView title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_right)
    TextView tv_right;

    //信息保存控件
    @InjectView(R.id.tv_write_chu_weight)
    TextView tv_write_chu_weight;
    private RetestPre retestPre;
    private RetestAudit retestAudit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_right.setOnClickListener(this);
        tv_write_chu_weight.setOnClickListener(this);
    }

    @Override
    protected void initViews() {


    }

    @Override
    protected void initDatas() {

        title.setText(R.string.AuditBarT);
        tv_right.setText(R.string.AuditBarR);
        retestPre=new RetestclassImp();
        retestAudit=new RetestAudit("3","55","36","63","36","36","36","36","36","36","36","36");
        retestPre.doGetAudit(36,3,"2016-03-28");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tv_right:
                retestPre.doPostWrite("36","3","2016-03-28",retestAudit);
                break;
            case R.id.tv_write_chu_weight:
                show_information();
                break;
        }

    }
    public void show_information() {
        final Dialog circum_dialog = new Dialog(this);
        circum_dialog.setTitle("选择胸围");
        circum_dialog.setContentView(R.layout.dimension_dialog);
        Button b1 = (Button) circum_dialog.findViewById(R.id.button1);
        Button b2 = (Button) circum_dialog.findViewById(R.id.button2);
        final NumberPicker np1 = (NumberPicker) circum_dialog.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = (NumberPicker) circum_dialog.findViewById(R.id.numberPicker2);
        np1.setMaxValue(220);
        np1.setValue(100);
        np1.setMinValue(50);
        np1.setWrapSelectorWheel(false);
        np2.setMaxValue(9);
        np2.setValue(5);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(false);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_write_chu_weight.setText(String.valueOf(np1.getValue())+"."+String.valueOf(np2.getValue())); //set the value to textview
                circum_dialog.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circum_dialog.dismiss();
            }
        });
        circum_dialog.show();
        circum_dialog.setCanceledOnTouchOutside(false);
    }

}
