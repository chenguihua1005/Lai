/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.File.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.ggx.ruler_lib.RulerView;
import com.softtek.lai.R;


public class HeightActivity extends AppCompatActivity {

    TextView tv;
    RulerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimension_record);
        tv = (TextView) findViewById(R.id.tv);
        rv = (RulerView) findViewById(R.id.rv);
        rv.setCallback(new RulerView.RulerCallback() {
            @Override
            public void resultNum(int num) {
                tv.setText(num + "cm");
            }
        });
    }
}
