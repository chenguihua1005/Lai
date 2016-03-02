package com.softtek.lai.module.home.Archives;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.home.tab.TabMainActivity;

public class NewArchivesActivity extends AppCompatActivity {

    private EditText nickname;
    private EditText birth;
    private EditText sex;
    private EditText height;
    private EditText weight;
    private TextView tiaoguo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_archives);
        nickname= (EditText) findViewById(R.id.nickname);
        birth= (EditText) findViewById(R.id.nickname);
        sex= (EditText) findViewById(R.id.nickname);
        height= (EditText) findViewById(R.id.nickname);
        weight= (EditText) findViewById(R.id.nickname);
        tiaoguo= (TextView) findViewById(R.id.tiaoguo);
        tiaoguo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NewArchivesActivity.this, TabMainActivity.class);
                startActivity(intent);
            }
        });
    }
}
