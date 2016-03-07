package com.softtek.lai.module.home.File.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ggx.ruler_lib.RulerView;
import com.softtek.lai.R;


public class Height extends AppCompatActivity {

    TextView tv;
    RulerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_height);
        tv= (TextView) findViewById(R.id.tv);
        rv= (RulerView) findViewById(R.id.rv);
        //rv.setCallback(this);
        rv.setCallback(new RulerView.RulerCallback() {
            @Override
            public void resultNum(int num) {
                tv.setText("身高"+num+"cm");
            }
        });
    }
}
