package com.softtek.lai.module.bodygame3.home.view;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.softtek.lai.R;

public class TestActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        recyclerView= (RecyclerView) findViewById(R.id.recycleView);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(20, 20, 20, 20);
            }
        });
        recyclerView.setHasFixedSize(true);
        GridLayoutManager glm=new GridLayoutManager(this,2);
        glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position==0?2:1;
            }
        });
        recyclerView.setLayoutManager(glm);
        recyclerView.setAdapter(new NumberedAdapter(30));
    }
}
